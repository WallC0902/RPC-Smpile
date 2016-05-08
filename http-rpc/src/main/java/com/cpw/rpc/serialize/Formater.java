package com.cpw.rpc.serialize;

/**
 * Created by wufeng on 2016/05/08.
 */
public interface Formater
{
    /**
     *
     * @param clazz 请求的接口
     * @param method 请求的方法
     * @param param 请求的参数
     * @return
     */
    String reqFormat(Class clazz,String method,Object param);

    /**
     *
     * @param param 响应的结果
     * @return
     */
    String rsbFormat(Object param);
}
