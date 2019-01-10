package cc.doctor.rpc.client;

import cc.doctor.rpc.serialize.Serializer;
import com.thundersdata.rpc.serialize.Serializer;

public abstract class ClientBuilder<T> {
    protected String host;
    protected int port;
    protected Serializer<T> serializer;

    /**
     * set client host
     */
    public ClientBuilder<T> host(String host) {
        this.host = host;
        return this;
    }

    /**
     * set client port
     */
    public ClientBuilder<T> port(int port) {
        this.port = port;
        return this;
    }

    /**
     * set serializer
     */
    public ClientBuilder<T> serializer(Serializer<T> serializer) {
        this.serializer = serializer;
        return this;
    }

    /**
     * build client
     */
    public abstract Client<T> build();
}
