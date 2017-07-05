package com.yzwy.infrastructure.mqtt;


import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gin on 2017/5/4.
 */
public class MqttConnector {
    private static Logger LOG = LoggerFactory.getLogger(MqttConnector.class);



//人脸识别阿里服务器固定虚拟机mqtt broker
    private static final String HOST = "tcp://39.108.54.20:1883";
//    private static final String HOST = "tcp://192.168.1.149:1883";

    private static String TOPIC = "/#";
    private static String clientid = "client125";
    private static String userName = "faceDemoPc";
    private static String passWord = "password";

    private MqttAsyncClient asyncClient;
    //单例
    private static MqttConnector connector;

    static {
        try {
            connector = new MqttConnector();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private MqttConnector() throws MqttException {
        //先建立mqtt连接

        asyncClient = new MqttAsyncClient(HOST, clientid, new MemoryPersistence());
        asyncClient.connect(configureMqttOptions());

    }


    public static MqttConnector getConnector(){
        return connector;
    }

    public MqttAsyncClient getMqttClient(){
        return asyncClient;
    }

    public  void connect(MqttSingletonQueue.mqttCallBack callBack){

        String clientUUid =  "faceServer";

        TimerPingSender timer  = new TimerPingSender();
        try {
            LOG.info("Mqtt Connection Begin:================");
            LOG.info("MQTT HOST:"+HOST + " ClientId :"+clientUUid + "timer :"+timer);
             asyncClient = new MqttAsyncClient(HOST,clientUUid,new MemoryPersistence(),timer);
            asyncClient.setCallback(callBack);

            IMqttToken token = null;

            token = asyncClient.connect(configureMqttOptions());
            token.waitForCompletion();

            //心跳包开始
            timer.start();

            //订阅公共topic 筛选后存入消息队列
//            token = asyncClient.subscribe(TOPIC,0);
//            token.waitForCompletion();
//            LOG.info("订阅topic:"+TOPIC);
            LOG.info("Mqtt Connection finished=============");


        } catch (MqttException e) {
            LOG.error("Mqtt建立连接异常："+e);
            e.printStackTrace();
        }

    }

    /**
     * 与识别终端通信的topic
     * @param address
     * @return
     */
    public static String generatePubTopicByAddress(String address){
     return   "/jxtech/recog/sub/"+address;
    }

    /**
     * 与业主app通信 --根据账号名生成topic
     * @param userName
     * @return
     */
    public static String generatePubTopicByUserName(String userName){
        return "/jxtech/sub/"+userName;
    }

    public void disconnect() throws MqttException {
        if (asyncClient != null){
            if (asyncClient.isConnected()){
                asyncClient.disconnectForcibly(1000);

            }else{
//                asyncClient.disconnectForcibly(1000);
            }
        }else {
            LOG.info("MQTT 连接已消失 可以重新连接");
        }

    }

    private static MqttConnectOptions configureMqttOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(null);
        options.setAutomaticReconnect(true);

        // 设置超时时间
//        options.setConnectionTimeout(100);
//         设置会话心跳时间
        options.setKeepAliveInterval(200);
        return options;
    }


    public void pubMsg(String topic,String message) throws MqttException {
        MqttMessage msg = new MqttMessage();
        msg.setQos(1);
        msg.setRetained(false);
        msg.setPayload(message.getBytes());

        IMqttToken token = asyncClient.publish(topic,msg);
        token.waitForCompletion();
        LOG.info("********** pubTopic:"+topic+"*********QOS:"+0
                +"***");
    }

    public void unSubscribeTopic(String topic) throws MqttException {
        IMqttToken mqttToken = asyncClient.unsubscribe(topic);
        mqttToken.waitForCompletion();
    }

    public  void  subscribeTopic(String topic) throws MqttException {
        IMqttToken token = asyncClient.subscribe(topic,0);
        token.waitForCompletion();
        LOG.info("********** subTopic:"+topic+"*********QOS:"+0
                +"***");

    }
}
