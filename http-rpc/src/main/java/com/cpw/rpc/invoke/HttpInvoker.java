package com.cpw.rpc.invoke;

import com.cpw.rpc.exception.RpcException;
import com.cpw.rpc.exception.RpcExceptionCodeEnum;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.Args;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wufeng on 2016/05/08.
 */
public class HttpInvoker implements Invoker
{
    private static final HttpClient httpClient = getHttpClient();

    public static final Invoker invoker = new HttpInvoker();

    public String request(String request, ConsumerConfig consumerConfig) throws RpcException {
        HttpPost post = new HttpPost(consumerConfig.getUrl());
        post.setHeader("Connection", "Keep-Alive");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("data", request));
        try
        {
            post.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
            HttpResponse response = httpClient.execute(post);
            if (response.getStatusLine().getStatusCode() == 200)
            {
                return EntityUtils.toString(response.getEntity(),"UTF-8");
            }
            throw new RpcException(RpcExceptionCodeEnum.INVOKE_REQUEST_ERROR.getCode(),request);
        }
        catch (Exception e)
        {
            throw new RpcException("http 调用异常",e, RpcExceptionCodeEnum.INVOKE_REQUEST_ERROR.getCode(),request);
        }

    }

    public void response(String response, OutputStream outputStream) throws RpcException
    {
        try {
            outputStream.write(response.getBytes("UTF-8"));
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HttpClient getHttpClient() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        //连接池最大生成连接数200
        cm.setMaxTotal(200);
        // 默认设置route最大连接数为20
        cm.setDefaultMaxPerRoute(20);
        // 指定专门的route，设置最大连接数为80
        HttpHost localhost = new HttpHost("localhost", 8080);
        cm.setMaxPerRoute(new HttpRoute(localhost), 50);
        // 创建httpClient
        return HttpClients.custom()
                .setConnectionManager(cm)
                .build();

    }

    public static String EntityToString(
            final HttpEntity entity, final Charset defaultCharset) throws IOException, ParseException {
        Args.notNull(entity, "Entity");
        final InputStream instream = entity.getContent();
        if (instream == null) {
            return null;
        }
        try {
            Args.check(entity.getContentLength() <= Integer.MAX_VALUE,
                    "HTTP entity too large to be buffered in memory");
            int i = (int)entity.getContentLength();
            if (i < 0) {
                i = 4096;
            }
            Charset charset = null;
            try {
                final ContentType contentType = ContentType.get(entity);
                if (contentType != null) {
                    charset = contentType.getCharset();
                }
            } catch (final UnsupportedCharsetException ex) {
                throw new UnsupportedEncodingException(ex.getMessage());
            }
            if (charset == null) {
                charset = defaultCharset;
            }
            if (charset == null) {
                charset = HTTP.DEF_CONTENT_CHARSET;
            }
            final Reader reader = new InputStreamReader(instream, charset);
            final CharArrayBuffer buffer = new CharArrayBuffer(i);
            final char[] tmp = new char[1024];
            int l;
            while((l = reader.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
            return buffer.toString();
        } finally {
//            instream.close();
        }
    }
}
