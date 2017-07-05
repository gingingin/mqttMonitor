package com.yzwy.infrastructure.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//import com.yzwy.plugins.activemq.Comsumer;
//import com.yzwy.plugins.activemq.Producter;

/**
 * Created by gin on 2017/4/23.
 */

@Component
public class MqttSingletonQueue {

//    private static final Logger LOG = LoggerFactory.getLogger(MqttSingletonQueue.class);
    private static Logger LOG = LoggerFactory.getLogger(MqttSingletonQueue.class);


//    @Autowired
//    private MessageSender messageSender;

    //mqtt连接检查时间间隔 2分钟
    private static Integer mqttConnectCheckTime = 2;

//    private static MqttSingletonQueue instance = new MqttSingletonQueue();

    private ScheduledExecutorService executorService;



//    @PostConstruct
    public void init() {
        mqttCallBack callBack = new mqttCallBack();
        System.out.println("start mqtt connection:");
        MqttConnector.getConnector().connect(callBack);

//        //开两条线程消费解析
//        initConsumer();
        executorService = ScheduleThreadPool.getInstance().getExecutor();
        //定时检查连接 重新连接  固定频率 2分钟检查一次
        executorService.scheduleAtFixedRate(new MqttReconnectThread(MqttConnector.getConnector(),callBack),2,
                mqttConnectCheckTime,
                TimeUnit.MINUTES);
    }

//    private MqttSingletonQueue() {
//    }



//    public static MqttSingletonQueue getInstance(){
//        return instance;
//    }

    class mqttCallBack implements MqttCallback{

        public mqttCallBack() {


        }

        @Override
        public void connectionLost(Throwable throwable) {
            LOG.warn("MQTT 主要连接断开，进入重连机制");
            throwable.printStackTrace();
            try {
//                MqttConnector connector = MqttConnector.getConnector();
                //触发一次重新连接
//                ScheduleThreadPool.getInstance().getExecutor().submit(new MqttReconnectThread(connector,this))
//                        .get();
            } catch (Exception e) {
                LOG.error("MQTT 重连接异常："+e.getMessage());
                e.printStackTrace();
            }
        }

        @Override
        public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {

                LOG.debug("接收消息Qos : " + mqttMessage.getQos());
                LOG.debug("接收消息Topics : " + s);
                String msgStr = MqttStringUtil.printfBytes(mqttMessage.getPayload());
                LOG.debug("接收消息内容 ["+mqttMessage.getPayload().length+"]: " + msgStr);
                //mqtt收到的消息存入队列中

//

        }

        @Override
        public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
            LOG.debug("发送完成");
        }
    }


//    private class ConsumerMq implements Runnable{
//        Comsumer comsumer;
//        public ConsumerMq(Comsumer comsumer){
//            this.comsumer = comsumer;
//        }
//
//        @Override
//        public void run() {
//            while(true){
//                comsumer.getMessage("receivedmqttmsg");
//            }
//        }
//    }
}
