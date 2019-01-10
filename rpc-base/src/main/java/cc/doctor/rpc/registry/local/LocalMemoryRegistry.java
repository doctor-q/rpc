package cc.doctor.rpc.registry.local;


import cc.doctor.rpc.signature.ServerSig;
import com.thundersdata.rpc.registry.IpPortRegistry;
import com.thundersdata.rpc.signature.ServerSig;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 本地内存注册中心
 */
public class LocalMemoryRegistry implements IpPortRegistry {

    private static LocalMemoryRegistry localRegistry = new LocalMemoryRegistry();

    private Map<String, ServerSig> contentMap = new ConcurrentHashMap<>();

    private LocalMemoryRegistry() {
    }

    public static LocalMemoryRegistry getLocalRegistry() {
        return localRegistry;
    }

    public Map<String, ServerSig> getContentMap() {
        return contentMap;
    }

    @Override
    public boolean register(String key, ServerSig data) {
        if (data.getHost() == null) {
            data.setHost("127.0.0.1");
        }
        contentMap.put(key, data);
        return true;
    }

    @Override
    public LocalMemoryRegistry start() {
        return this;
    }

    @Override
    public void shutdown() {
        contentMap.clear();
    }

    @Override
    public Set<ServerSig> lookup(String key) {
        return Collections.singleton(contentMap.get(key));
    }

    @Override
    public boolean remove(String key) {
        contentMap.remove(key);
        return true;
    }
}
