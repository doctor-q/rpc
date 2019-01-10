package cc.doctor.rpc.client;

import cc.doctor.rpc.registry.RedisRegistry;
import cc.doctor.rpc.service.Hello;
import cc.doctor.rpc.service.HelloImpl;
import com.thundersdata.rpc.jetty.client.JettyRegistrantClientBuilder;
import com.thundersdata.rpc.registry.RedisRegistry;
import com.thundersdata.rpc.registry.Registry;
import com.thundersdata.rpc.registry.local.LocalDiskRegistry;
import com.thundersdata.rpc.serialize.protostuff.ProtostuffDeserializer;
import com.thundersdata.rpc.serialize.protostuff.ProtostuffSerializer;
import com.thundersdata.rpc.service.Hello;
import com.thundersdata.rpc.service.HelloImpl;
import com.thundersdata.rpc.signature.ServerSig;
import org.junit.Assert;
import org.junit.Test;

public class RegistrantClientTest {
    @Test
    public void clientProxyTest() {
        ClientProxy clientProxy = new ClientProxy();
//        Registry<ServerSig> registry = LocalDiskRegistry.getRegistry().start();
        RedisRegistry registry = new RedisRegistry();
        registry.setHost("127.0.0.1");
        Client<byte[]> client = new JettyRegistrantClientBuilder()
                .registry(registry)
                .serializer(new ProtostuffSerializer()).build();
        clientProxy.setClient(client);
        clientProxy.setDeserializer(new ProtostuffDeserializer());

        Hello hello = clientProxy.getService(Hello.class);
        String res = hello.sayHello("chen");
        Assert.assertTrue(res.equals(new HelloImpl().sayHello("chen")));
    }
}
