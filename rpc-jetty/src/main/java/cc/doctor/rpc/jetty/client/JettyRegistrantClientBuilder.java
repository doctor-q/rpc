package cc.doctor.rpc.jetty.client;

import cc.doctor.rpc.signature.ServerSig;
import com.thundersdata.rpc.client.ClientBuilder;
import com.thundersdata.rpc.client.RegistrantClientBuilder;
import com.thundersdata.rpc.signature.ServerSig;

public class JettyRegistrantClientBuilder extends RegistrantClientBuilder<byte[], ServerSig> {

    @Override
    public ClientBuilder<byte[]> createRealClientBuilder(ServerSig serverSig) {
        return new JettyClientBuilder().host(serverSig.getHost()).port(serverSig.getPort()).serializer(serializer);
    }
}
