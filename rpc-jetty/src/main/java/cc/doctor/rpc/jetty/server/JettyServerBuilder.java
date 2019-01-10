package cc.doctor.rpc.jetty.server;

import cc.doctor.rpc.server.Server;
import com.thundersdata.rpc.server.Server;
import com.thundersdata.rpc.server.ServerBuilder;
import com.thundersdata.rpc.signature.ServerSig;

public class JettyServerBuilder extends ServerBuilder {

    public static JettyServerBuilder ofPort(int port) {
        JettyServerBuilder jettyServerBuilder = new JettyServerBuilder();
        jettyServerBuilder.port(port);
        return jettyServerBuilder;
    }

    @Override
    public Server build() {
        JettyServer jettyServer = new JettyServer(port);
        jettyServer.setRegistry(registry);
        jettyServer.setServiceHandler(serviceHandler);
        jettyServer.setSerializer(serializer);
        jettyServer.setDeserializer(deserializer);
        return jettyServer;
    }
}
