package com.cpw.rpc.serialize.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cpw.rpc.serialize.Formater;
import com.cpw.rpc.serialize.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wufeng on 2016/05/08.
 */
public class JsonFormater implements Formater
{
    public static final Formater formater = new JsonFormater();

    public String reqFormat(Class clazz, String method, Object param)
    {
        Request request = new Request();
        request.setParam(param);
        request.setClazz(clazz);
        request.setMethod(method);
        return JSON.toJSONString(request, SerializerFeature.WriteClassName);
    }

    public String rsbFormat(Object param)
    {
        return JSON.toJSONString(param, SerializerFeature.WriteClassName);
    }

}
