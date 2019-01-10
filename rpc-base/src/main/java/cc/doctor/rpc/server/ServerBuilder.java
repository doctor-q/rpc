package cc.doctor.rpc.server;

import cc.doctor.rpc.serialize.Deserializer;
import cc.doctor.rpc.service.ServiceHandler;
import com.thundersdata.rpc.registry.Registry;
import com.thundersdata.rpc.serialize.Deserializer;
import com.thundersdata.rpc.serialize.Serializer;
import com.thundersdata.rpc.service.ServiceHandler;

public abstract class ServerBuilder {
    protected int port;
    protected ServiceHandler serviceHandler;
    protected Serializer serializer;
    protected Deserializer deserializer;
    protected Registry registry;

    public ServerBuilder port(int port) {
        this.port = port;
        return this;
    }

    public ServerBuilder serializer(Serializer serializer) {
        this.serializer = serializer;
        return this;
    }

    public ServerBuilder deserializer(Deserializer deserializer) {
        this.deserializer = deserializer;
        return this;
    }

    public ServerBuilder serviceHandler(ServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
        return this;
    }

    public ServerBuilder registry(Registry registry) {
        this.registry = registry;
        return this;
    }

    public abstract Server build();
}
