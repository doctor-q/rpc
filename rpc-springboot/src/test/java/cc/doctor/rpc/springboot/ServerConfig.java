package cc.doctor.rpc.springboot;

import cc.doctor.rpc.jetty.server.JettyServerBuilder;
import cc.doctor.rpc.server.Server;
import com.thundersdata.rpc.jetty.server.JettyServerBuilder;
import com.thundersdata.rpc.serialize.protostuff.ProtostuffDeserializer;
import com.thundersdata.rpc.serialize.protostuff.ProtostuffSerializer;
import com.thundersdata.rpc.server.Server;
import com.thundersdata.rpc.service.DefaultServiceHandler;
import com.thundersdata.rpc.springboot.provider.RpcProviderScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {
    // simple server
    @Bean
    public Server server() {
        return JettyServerBuilder.ofPort(5011)
                .serializer(new ProtostuffSerializer())
                .deserializer(new ProtostuffDeserializer())
                .serviceHandler(new DefaultServiceHandler())
                .build();
    }
}
