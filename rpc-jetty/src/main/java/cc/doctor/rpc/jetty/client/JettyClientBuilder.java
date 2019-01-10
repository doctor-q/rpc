package cc.doctor.rpc.jetty.client;

import cc.doctor.rpc.client.Client;
import cc.doctor.rpc.client.ClientBuilder;
import com.thundersdata.rpc.client.Client;
import com.thundersdata.rpc.client.ClientBuilder;

public class JettyClientBuilder extends ClientBuilder<byte[]> {

    @Override
    public Client<byte[]> build() {
        JettyClient jettyClient = new JettyClient();
        jettyClient.setHost(host);
        jettyClient.setPort(port);
        jettyClient.setByteSerializer(serializer);
        return jettyClient;
    }
}
