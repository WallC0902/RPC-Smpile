package com.cpw.rpc.container;

/**
 * Created by wufeng on 2016/05/08.
 */
public abstract class Container
{
    public static volatile boolean isStart = false;

    public abstract void start();

    public static volatile Container container = null;
}
