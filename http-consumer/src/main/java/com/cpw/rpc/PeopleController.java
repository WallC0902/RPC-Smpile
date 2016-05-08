package com.cpw.rpc;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by wufeng on 2016/05/08.
 */
@Component("peopleController")
public class PeopleController
{
    @Resource
    private SpeakInterface speakInterface;

    public String getSpeak(Integer age,Integer sex)
    {
        People people = new People();
        people.setAge(age);
        people.setSex(sex);
        return speakInterface.speak(people);
    }
}
