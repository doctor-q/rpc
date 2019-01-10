package cc.doctor.rpc.client;

import cc.doctor.rpc.service.RpcException;
import com.thundersdata.rpc.registry.Registry;
import com.thundersdata.rpc.service.RpcException;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册客户端，维护一套真实客户端列表
 *
 * @param <T> 序列化类型
 */
public abstract class RegistryClient<T, R> implements Client<T> {
    private Registry<R> registry;

    private Map<String, List<Client<T>>> realClientMap = new ConcurrentHashMap<>();

    public RegistryClient(Registry<R> registry) {
        this.registry = registry;
    }

    @Override
    public T remoteCall(RemoteCall remoteCall) {
        String lookupKey = remoteCall.getService().lookupKey();
        if (realClientMap.get(lookupKey) == null) {
            realClientMap.put(lookupKey, new LinkedList<>());
            // loop up and create clients
            Set<R> lookup = registry.lookup(lookupKey);
            if (lookup == null || lookup.isEmpty()) {
                throw new RpcException("服务未注册");
            }
            for (R ipPort : lookup) {
                Client<T> client = createRealClient(ipPort);
                realClientMap.get(lookupKey).add(client);
            }
        }
        Client<T> client = select(realClientMap.get(lookupKey));
        if (client == null) {
            throw new RpcException("未找到客户端");
        }
        return client.remoteCall(remoteCall);
    }


    public abstract Client<T> createRealClient(R ipPort);

    /**
     * 客户端选择，复写此方法可以自定义轮训规则，默认随机选择一个
     *
     * @param clientList 客户端列表
     * @return 选择的客户端
     */
    public Client<T> select(List<Client<T>> clientList) {
        if (clientList == null || clientList.isEmpty()) {
            return null;
        }
        Random random = new Random();
        int nextInt = random.nextInt(clientList.size());
        return clientList.get(nextInt);
    }
}
