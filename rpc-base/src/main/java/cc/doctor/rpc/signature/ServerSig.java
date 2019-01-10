package cc.doctor.rpc.signature;

import java.io.Serializable;

public class ServerSig implements Serializable {
    private String host;
    private int port;

    public ServerSig() {
    }

    public ServerSig(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String toIpPort() {
        return String.format("%s:%s", host, port);
    }

    public static ServerSig parse(String data) {
        ServerSig serverSig = new ServerSig();
        String[] split = data.split(":");
        if (split.length == 1) {
            serverSig.setPort(Integer.parseInt(split[0]));
        } else if (split.length == 2) {
            serverSig.setHost(split[0]);
            serverSig.setPort(Integer.parseInt(split[1]));
        }
        return serverSig;
    }
}
