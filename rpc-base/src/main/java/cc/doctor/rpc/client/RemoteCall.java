package cc.doctor.rpc.client;

import cc.doctor.rpc.signature.MethodSig;
import com.thundersdata.rpc.signature.MethodSig;
import com.thundersdata.rpc.signature.ServiceSig;

import java.io.Serializable;

/**
 * 远程调用对象
 */
public class RemoteCall implements Serializable {
    /**
     * 服务签名
     */
    private ServiceSig service;

    /**
     * 方法签名
     */
    private MethodSig method;

    /**
     * 参数
     */
    private Object[] params;

    public ServiceSig getService() {
        return service;
    }

    public void setService(ServiceSig service) {
        this.service = service;
    }

    public MethodSig getMethod() {
        return method;
    }

    public void setMethod(MethodSig method) {
        this.method = method;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
