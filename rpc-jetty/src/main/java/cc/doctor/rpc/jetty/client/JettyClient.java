package cc.doctor.rpc.jetty.client;

import cc.doctor.rpc.signature.MethodSig;
import com.thundersdata.rpc.client.ByteClient;
import com.thundersdata.rpc.client.RemoteCall;
import com.thundersdata.rpc.serialize.Serializer;
import com.thundersdata.rpc.service.RpcException;
import com.thundersdata.rpc.signature.MethodSig;
import com.thundersdata.rpc.signature.ServiceSig;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JettyClient implements ByteClient {
    private static final Logger log = LoggerFactory.getLogger(JettyClient.class);

    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();

    private String host;
    private Integer port;
    private Serializer<byte[]> byteSerializer;

    @Override
    public byte[] remoteCall(RemoteCall remoteCall) {
        if (host == null || port == null) {
            throw new RpcException();
        }
        if (byteSerializer == null) {
            throw new RpcException();
        }
        HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager);
        ServiceSig service = remoteCall.getService();
        MethodSig method = remoteCall.getMethod();
        String uri = String.format("/%s/%s/%s", service.getName(), service.getVersion(), method.getName());
        HttpPost post = new HttpPost(String.format("http://%s:%s%s", host, port, uri));
        post.setEntity(new ByteArrayEntity(byteSerializer.serialize(remoteCall)));
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpResponse = httpClientBuilder.build().execute(post);
            StatusLine statusLine = closeableHttpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode / 100 != 2) {
                // 释放连接
                closeableHttpResponse.close();
                throw new RpcException();
            } else {
                return EntityUtils.toByteArray(closeableHttpResponse.getEntity());
            }
        } catch (IOException e) {
            throw new RpcException(e);
        } finally {
            if (closeableHttpResponse != null) {
                try {
                    closeableHttpResponse.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Serializer<byte[]> getByteSerializer() {
        return byteSerializer;
    }

    public void setByteSerializer(Serializer<byte[]> byteSerializer) {
        this.byteSerializer = byteSerializer;
    }
}
