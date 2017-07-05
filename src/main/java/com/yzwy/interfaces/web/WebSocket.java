package com.yzwy.interfaces.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yzwy.infrastructure.message.MessageUtil;
import com.yzwy.infrastructure.message.RadixUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by gin on 2017/2/22.
 */
@ServerEndpoint("/websocket")
@Component //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
public class WebSocket {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocket.class);


    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private MqttClient client = null;

    private long pubTime = 0;

    private boolean isCount = false;

    public WebSocket() {
    }


    /**
     * json格式：
     *      operation:"mqttSub"/"mqttPub"/"mqttConnect"/"mqttDisconnect"
     *      message
     *      pubTopic
     *      subTopic
     *      qos
     *      mqttURL
     *      clientId
     */

    public void mqttSub(String message) throws MqttException, IOException, InterruptedException {
        JSONObject jsonMap = JSONObject.parseObject(message);
        String topicStr =  jsonMap.getString("subTopic");

//        String[] qosStr = jsonMap.getString("qos").split(",");
        //不同topic之间以逗号分割
        String[] topicFilter = topicStr.trim().split(",");
        if (null != client){
            boolean isContinue = true;
            while (isContinue)
            if (client.isConnected()){
                client.disconnectForcibly(1000,1000);
                Thread.sleep(1000);
            }else {
                client.connect();
                client.subscribe(topicFilter);
                sendMessage("重新订阅主题，重新连接mqtt");
                isContinue = false;
            }

            //todo 每个topic需要对应一个qos?

        }else {
            mqttConnect(message);
            client.subscribe(topicFilter);
            sendMessage("订阅主题");
//            sendMessage("mqtt连接出错或未建立mqtt连接，请点击连接mqtt");
        }
    }

    public void mqttPub(String message) throws MqttException, IOException {
        JSONObject jsonMap = JSONObject.parseObject(message);
//        String topicStr = (String) jsonMap.get("pubTopic");

        if (null != client){
            MqttMessage mg = null;
            try {
                mg = new MqttMessage(RadixUtil.HexStrToByte((String)jsonMap.get
                        ("message")));
            }catch(Exception e){
                LOG.error("mqtt pub信息不是16进制字符串 "+e.getMessage());
                sendMessage("mqtt pub信息要求是16进制字符串并以空格分隔 请重新输入");
                return;
            }


            Integer qos = Integer.parseInt((String) jsonMap.get("qos"));
            mg.setQos(qos);

            String topicStr = jsonMap.getString("pubTopic");
            try {
                MqttTopic.validate(topicStr,false);
            } catch (Exception e){
                LOG.error("发布 pubTopic:"+topicStr+" 错误 不符合格式要求 禁止使用通配符广播(+#)");
                this.sendMessage("发布 pubTopic:"+topicStr+" 错误 不符合格式要求 禁止使用通配符广播(+#)");
                return;
            }

            LOG.info("发布 pubTopic:"+jsonMap.get("pubTopic"));
            this.sendMessage("发布 pubTopic:"+jsonMap.get("pubTopic")+" message :"+jsonMap.get
                    ("message"));
            pubTime = System.currentTimeMillis();


            client.publish(topicStr,mg);
            this.isCount = true;//设置计时开关，只count1次后关闭
        }else {
            sendMessage("mqtt连接出错，请重新连接mqtt");
        }
    }

    public void mqttConnect(String message) throws MqttException, IOException {
        JSONObject jsonMap = JSONObject.parseObject(message);

        if (null == client){
            client = new MqttClient((String)jsonMap.get("mqttURL"),(String)jsonMap.get("clientId")
                    ,new
                    MemoryPersistence());

            client.setCallback(this.getCallbackReference());
            client.connect();
            LOG.info("建立mqtt连接");

            this.sendMessage("建立mqtt连接");
        }else {
            if(client.isConnected()){
                LOG.info("mqtt已连接，可直接订阅主题／发布信息");
                sendMessage("mqtt已连接，可直接订阅主题／发布信息");
            }else {
                LOG.info("mqtt连接已断开，重新连接");

                sendMessage("mqtt连接已断开，重新连接");
                client.reconnect();

                LOG.info("mqtt重新连接成功，现在可订阅主题／发布信息");
                sendMessage("mqtt重新连接成功，现在可订阅主题／发布信息");
            }
        }
    }

    public void mqttDisconnect(String message) throws MqttException, IOException {
        JSONObject jsonMap = JSONObject.parseObject(message);

        if (null == client){

            this.sendMessage("mqtt未连接，不需要断开");
        }else {
            if(client.isConnected()){
                client.disconnectForcibly(2000,2000);
                sendMessage("mqtt已断开");
            }else {
                sendMessage("mqtt连接已断开，不需要重复断开");
            }
        }
    }

    /**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */@OnOpen
    public void onOpen(Session session) throws MqttException {
        this.session = session;
//        System.out.println("有新连接建立" );
        LOG.info("有新连接建立");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws MqttException {
        LOG.info("连接关闭！");
        if (client != null) {
            client.disconnectForcibly(2000, 2000);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, MqttException,
            InterruptedException {
        LOG.info("来自客户端的消息："+message);

//        System.out.println("来自客户端的消息:" + message);
        JSONObject jsonMap = JSONObject.parseObject(message);

        switch (jsonMap.getString("operation")){
            case "mqttConnect":
                this.mqttConnect(message);
                break;
            case "mqttDisconnect":
                this.mqttDisconnect(message);
                break;
            case "mqttSub":
                this.mqttSub(message);
                break;
            case "mqttPub":
                this.mqttPub(message);
                break;
            default:
                sendMessage("无此操作："+jsonMap.getString("operation"));
                break;
        }
        LOG.info("pub Thread:["+Thread.currentThread().getId()+"] self:"+this);


        //发消息
    }



    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) throws IOException, MqttException {
        LOG.info("发生错误");
        this.sendMessage("发生错误,连接关闭");
        client.disconnectForcibly(2000,2000);
//        error.printStackTrace();
        LOG.warn(error.getMessage());
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public  void sendMessage(String message) throws  IOException {

        this.session.getBasicRemote().sendText(message);
//                 this.session.getAsyncRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public void receivdMqMsg(String message) throws IOException {
        if (this.isCount){
            long subTime = System.currentTimeMillis();
            message = message+" 延迟："+(subTime-pubTime)+"ms";
            this.isCount = false;
//            System.out.println("callback-websocket Thread:["+Thread.currentThread().getId()+"] self:"+this);
        }else{
//            System.out.println("callback-websocket Thread:["+Thread.currentThread().getId()+"] self:"+this);
        }

        sendMessage(message);
    }

    mqttCallBak getCallbackReference() {
        return new mqttCallBak();
    }

    private class mqttCallBak implements MqttCallbackExtended {
        @Override
        public void connectComplete(boolean b, String s) {
            LOG.info("mqtt连接建立成功");
//            System.out.println();
//            try {
//
//                sendMessage("mqtt连接建立成功");
//            } catch (IOException e) {
//                e.printStackTrace();
//
//            }
        }

        @Override
        public void connectionLost(Throwable throwable) {
            LOG.info("mqtt连接丢失");
            try {
                sendMessage("mqtt连接丢失");
            } catch (IOException e) {
//                e.printStackTrace();
                LOG.warn(e.getMessage());

            }
        }

        @Override
        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
            LOG.info("接收消息Qos : " + mqttMessage.getQos());
            String message = RadixUtil.bytesToHexStr
                    (mqttMessage.getPayload());
            LOG.info("接收消息内容 ["+mqttMessage.getPayload().length+"]: [" + message+"]");
            Map resultMap = new HashMap();
            try{
                Map analyzeMap = MessageUtil.analyzeMessage(message);
                resultMap.put("analyzedMsg",analyzeMap);

            } catch (Exception e){
                //todo
            }

            resultMap.put("rowMsg",message);
            resultMap.put("topic",s);
            resultMap.put("msgLength",mqttMessage.getPayload().length);


            String jsonString = JSON.toJSONString(resultMap);

            receivdMqMsg(jsonString);

//            receivdMqMsg("subTopic:"+s+"\n content["+mqttMessage.getPayload().length+"]: " + RadixUtil.bytesToHexStr
//                    (mqttMessage
//                    .getPayload
//                    ()));

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            LOG.info("消息发送成功");

        }


    }

}
