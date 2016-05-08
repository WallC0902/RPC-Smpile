package com.cpw.rpc.serialize.json;

import com.alibaba.fastjson.JSON;
import com.cpw.rpc.exception.RpcException;
import com.cpw.rpc.exception.RpcExceptionCodeEnum;
import com.cpw.rpc.serialize.Parser;
import com.cpw.rpc.serialize.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by wufeng on 2016/05/08.
 */
public class JsonParser implements Parser
{
    private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);

    public static final Parser parser = new JsonParser();

    public Request reqParse(String param) throws RpcException {
        try
        {
            logger.debug("调用参数 {}", param);
            return (Request)JSON.parse(param);
        }
        catch (Exception e)
        {
            logger.error("转换异常 param = {}", param, e);
            throw new RpcException("",e, RpcExceptionCodeEnum.DATA_PARSER_ERROR.getCode(),param);
        }
    }

    public <T> T rsbParse(String result)
    {
        return (T)JSON.parse(result);
    }
}
