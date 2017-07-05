package com.yzwy.infrastructure.mqtt;


import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by gin on 2017/5/2.
 */
public class MqttReconnectThread  implements Runnable{

    private static Logger LOG = LoggerFactory.getLogger(MqttReconnectThread.class);



    private MqttConnector connector;
    private MqttSingletonQueue.mqttCallBack callBack;

    public MqttReconnectThread(MqttConnector connector,MqttSingletonQueue.mqttCallBack callBack) {
        this.connector = connector;
        this.callBack = callBack;
    }



    @Override
    public void run() {
        MqttAsyncClient asyncClient = connector.getMqttClient();
        if (asyncClient == null){
            LOG.warn("MQTT 连接未建立");
        }else if (asyncClient.isConnected()){
            LOG.info("MQTT 连接正常");
        }else {
            LOG.warn("MQTT 连接断开 准备重连");

                LOG.info("MQTT 开始重连 ");
//                connector.connect(this.callBack);

//            }

//            LOG.info("MQTT 已重新连接");

        }
    }
}
