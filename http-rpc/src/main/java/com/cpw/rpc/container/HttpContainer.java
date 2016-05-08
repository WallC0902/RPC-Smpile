package com.cpw.rpc.container;

import com.cpw.rpc.invoke.ProviderConfig;
import com.cpw.rpc.proxy.ProviderProxyFactory;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by wufeng on 2016/05/08.
 */
public class HttpContainer extends Container
{
    private static final Logger logger = LoggerFactory.getLogger(HttpContainer.class);

    private AbstractHandler httpHandler;
    private ProviderConfig providerConfig;

    public HttpContainer(AbstractHandler httpHandler)
    {
        this(httpHandler, new ProviderConfig("/invoke",8080));
    }

    public HttpContainer(AbstractHandler httpHandler,ProviderConfig providerConfig)
    {
        this.httpHandler = httpHandler;
        this.providerConfig = providerConfig;
        Container.container = this;
    }



    public void start()
    {
        Server server = new Server();
        try
        {
            SelectChannelConnector connector = new SelectChannelConnector();
            connector.setPort(providerConfig.getPort());
            server.setConnectors(new Connector[]{
                    connector
            });
            server.setHandler(httpHandler);
            server.start();
        }
        catch (Throwable e)
        {
            logger.error("�����쳣", e);
        }
    }

}
