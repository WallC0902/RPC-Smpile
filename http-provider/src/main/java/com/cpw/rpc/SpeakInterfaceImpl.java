package com.cpw.rpc;

import com.alibaba.fastjson.JSON;
import com.cpw.rpc.serialize.Request;
import com.cpw.rpc.serialize.json.JsonFormater;
import org.springframework.stereotype.Component;

/**
 * Created by version_z on 2015/8/22.
 */
@Component("speakInterface")
public class SpeakInterfaceImpl implements SpeakInterface
{
    public String speak(People people)
    {
        if (people.getAge() > 18)
        {
            if (people.getSex() == 0)
            {
                return "男 "+people.getAge()+"岁";
            }else
            {
                return "女  "+people.getAge()+"岁";
            }
        }
        else
        {
            return "小朋友";
        }
    }
}
