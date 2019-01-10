package cc.doctor.rpc.service;

public class RpcException extends RuntimeException {
    public RpcException() {
    }

    public RpcException(String msg) {
        super(msg);
    }

    public RpcException(Exception e) {
        super(e);
    }
}
