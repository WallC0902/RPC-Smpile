package com.cpw.rpc.serialize;

import com.cpw.rpc.exception.RpcException;

/**
 * Created by wufeng on 2016/05/08.
 */
public interface Parser
{
    /**
     *
     * @param param 请求参数
     * @return
     */
    Request reqParse(String param) throws RpcException;

    /**
     *
     * @param result
     * @return
     */
    public <T> T rsbParse(String result);
}
