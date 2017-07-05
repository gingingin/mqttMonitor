package com.yzwy.infrastructure.message;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by gin on 2017/6/1.
 */
public class MessageCache {
    private static MessageCache instance = new MessageCache();

    private Map msgCache;

    private MessageCache()  {
        //todo 建立模版缓存 由80／00+ funcCode +terminalType+deviceType生成唯一key
        InputStream inputStream = null;
        try {
            String path = this.getClass().getClassLoader().getResource("msg_templet.json").getPath();

            inputStream = new FileInputStream(path);
            String text = IOUtils.toString(inputStream,"utf8");
            msgCache = (Map) JSON.parse(text);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static MessageCache getInstance(){
        return instance;
    }

    public  Map getTempletByKey(String uniqueKey){
        return (Map)msgCache.get(uniqueKey);
    }

}
