package cc.doctor.rpc.client;

import cc.doctor.rpc.serialize.Deserializer;
import cc.doctor.rpc.signature.MethodSig;
import com.thundersdata.rpc.serialize.Deserializer;
import com.thundersdata.rpc.service.RpcException;
import com.thundersdata.rpc.signature.MethodSig;
import com.thundersdata.rpc.signature.ServiceSig;
import com.thundersdata.rpc.signature.SignatureUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端动态代理，实现直接使用service调用服务
 * 一个Proxy对应一个连接池，必须使用一个连接池初始化
 */
public class ClientProxy implements InvocationHandler {

    /**
     * 反序列化
     */
    private Deserializer deserializer;

    /**
     * 客户端
     */
    private Client client;

    /**
     * 服务代理对象
     */
    private static Map<ServiceSig, Object> proxyMap = new ConcurrentHashMap<>();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (client == null) {
            throw new RpcException("Client not set.");
        }
        if (deserializer == null) {
            throw new RpcException("Deserializer not set");
        }
        Class<?> serviceClass = method.getDeclaringClass();

        RemoteCall remoteCall = new RemoteCall();
        ServiceSig serviceSig = SignatureUtils.getServiceSig(serviceClass);
        MethodSig methodSig = SignatureUtils.getMethodSig(method);
        remoteCall.setService(serviceSig);
        remoteCall.setMethod(methodSig);
        remoteCall.setParams(args);
        Object t = client.remoteCall(remoteCall);
        return deserializer.deserialize(t, method.getGenericReturnType());
    }

    public <T> T getService(Class<T> service) {
        Class[] classes = {service};
        ServiceSig serviceSig = SignatureUtils.getServiceSig(service);
        proxyMap.putIfAbsent(serviceSig, Proxy.newProxyInstance(ClientProxy.class.getClassLoader(), classes, this));
        return (T) proxyMap.get(serviceSig);
    }

    public Deserializer getDeserializer() {
        return deserializer;
    }

    public void setDeserializer(Deserializer deserializer) {
        this.deserializer = deserializer;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
