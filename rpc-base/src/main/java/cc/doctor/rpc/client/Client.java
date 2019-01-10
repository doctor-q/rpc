package cc.doctor.rpc.client;

/**
 * 客户端执行一个remote call并返回一个待反序列化的东西
 * 客户端可以直接调用远程服务端或者使用注册中心
 * <p>使用注册中心需要配置{@link com.thundersdata.rpc.registry.Registry}</p>
 * <p>直连服务端需要设置client的host与port属性</p>
 *
 * @param <T> 待反序列化类型
 */
public interface Client<T> {

    /**
     * 远程调用
     */
    T remoteCall(RemoteCall remoteCall);
}
