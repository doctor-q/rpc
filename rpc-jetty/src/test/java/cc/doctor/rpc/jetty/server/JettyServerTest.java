package cc.doctor.rpc.jetty.server;

import cc.doctor.rpc.server.Server;
import com.thundersdata.rpc.registry.RedisRegistry;
import com.thundersdata.rpc.registry.Registry;
import com.thundersdata.rpc.registry.local.LocalDiskRegistry;
import com.thundersdata.rpc.serialize.protostuff.ProtostuffDeserializer;
import com.thundersdata.rpc.serialize.protostuff.ProtostuffSerializer;
import com.thundersdata.rpc.server.Server;
import com.thundersdata.rpc.service.DefaultServiceHandler;
import com.thundersdata.rpc.service.HelloImpl;
import com.thundersdata.rpc.signature.ServerSig;
import org.junit.Test;

public class JettyServerTest {
    @Test
    public void testServer() throws InterruptedException {
        DefaultServiceHandler serviceHandler = new DefaultServiceHandler();
        serviceHandler.addService(new HelloImpl());

        Registry<ServerSig> registry = LocalDiskRegistry.getRegistry();
        Server server = JettyServerBuilder.ofPort(5011)
                .serviceHandler(serviceHandler)
                .serializer(new ProtostuffSerializer())
                .deserializer(new ProtostuffDeserializer())
                .registry(registry)
                .build();
        server.start();
        server.block();
    }

    @Test
    public void testRedisRegistry() throws InterruptedException {
        DefaultServiceHandler serviceHandler = new DefaultServiceHandler();
        serviceHandler.addService(new HelloImpl());
        RedisRegistry redisRegistry = new RedisRegistry();
        redisRegistry.setHost("127.0.0.1");
        Server server = JettyServerBuilder.ofPort(5011)
                .serviceHandler(serviceHandler)
                .serializer(new ProtostuffSerializer())
                .deserializer(new ProtostuffDeserializer())
                .registry(redisRegistry)
                .build();
        server.start();
        server.block();
    }
}
