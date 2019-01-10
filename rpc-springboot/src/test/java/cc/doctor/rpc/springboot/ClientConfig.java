package cc.doctor.rpc.springboot;

import cc.doctor.rpc.client.Client;
import cc.doctor.rpc.client.ClientProxy;
import com.thundersdata.rpc.client.Client;
import com.thundersdata.rpc.client.ClientProxy;
import com.thundersdata.rpc.jetty.client.JettyClientBuilder;
import com.thundersdata.rpc.serialize.protostuff.ProtostuffDeserializer;
import com.thundersdata.rpc.serialize.protostuff.ProtostuffSerializer;
import com.thundersdata.rpc.springboot.consumer.RpcConsumerScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    // simple client
    @Bean
    public Client<byte[]> client() {
        return new JettyClientBuilder()
                .host("127.0.0.1")
                .port(5011)
                .serializer(new ProtostuffSerializer()).build();
    }

    @Bean
    public ClientProxy clientProxy(Client<byte[]> client) {
        ClientProxy clientProxy = new ClientProxy();
        clientProxy.setClient(client);
        clientProxy.setDeserializer(new ProtostuffDeserializer());
        return clientProxy;
    }
}
