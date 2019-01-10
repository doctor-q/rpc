package cc.doctor.rpc.client;

import cc.doctor.rpc.service.Hello;
import cc.doctor.rpc.service.HelloImpl;
import com.thundersdata.rpc.jetty.client.JettyClientBuilder;
import com.thundersdata.rpc.serialize.protostuff.ProtostuffDeserializer;
import com.thundersdata.rpc.serialize.protostuff.ProtostuffSerializer;
import com.thundersdata.rpc.service.Hello;
import com.thundersdata.rpc.service.HelloImpl;
import org.junit.Assert;
import org.junit.Test;

public class SimpleClientTest {
    @Test
    public void clientProxyTest() {
        ClientProxy clientProxy = new ClientProxy();
        clientProxy.setClient(new JettyClientBuilder()
                .host("127.0.0.1")
                .port(5011)
                .serializer(new ProtostuffSerializer()).build());
        clientProxy.setDeserializer(new ProtostuffDeserializer());

        Hello hello = clientProxy.getService(Hello.class);
        String res = hello.sayHello("chen");
        Assert.assertTrue(res.equals(new HelloImpl().sayHello("chen")));
    }
}
