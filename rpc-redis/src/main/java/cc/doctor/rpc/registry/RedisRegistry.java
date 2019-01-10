package cc.doctor.rpc.registry;

import cc.doctor.rpc.service.RpcException;
import cc.doctor.rpc.signature.ServerSig;
import cc.doctor.rpc.utils.NetUtils;
import com.thundersdata.rpc.registry.local.LocalMemoryRegistry;
import com.thundersdata.rpc.service.RpcException;
import com.thundersdata.rpc.signature.ServerSig;
import com.thundersdata.rpc.utils.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RedisRegistry implements IpPortRegistry {
    private static final Logger log = LoggerFactory.getLogger(RedisRegistry.class);

    private Jedis jedis;
    private String host;
    private int port = 6379;
    private boolean ssl;
    private String requirePass;
    private int db;
    private String publishHost;

    private LocalMemoryRegistry localMemoryRegistry = LocalMemoryRegistry.getLocalRegistry();

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }

    public void setRequirePass(String requirePass) {
        this.requirePass = requirePass;
    }

    public void setDb(int db) {
        this.db = db;
    }

    @Override
    public RedisRegistry start() {
        if (host == null || port == 0) {
            throw new RpcException("Redis 配置错误");
        }
        jedis = new Jedis(host, port, ssl);
        if (requirePass != null) {
            jedis.auth(requirePass);
        }
        jedis.select(db);
        try {
            InetAddress inetAddress = NetUtils.getLocalHostLANAddress();
            publishHost = inetAddress.getHostAddress();
        } catch (UnknownHostException | SocketException e) {
            log.error("获取本地地址出错", e);
        }
        return this;
    }

    @Override
    public void shutdown() {
        log.info("清除注册信息！");
        for (String service : localMemoryRegistry.getContentMap().keySet()) {
            log.info("remove {}", service);
            remove(service);
        }
        jedis.close();
    }

    @Override
    public boolean register(String key, ServerSig data) {
        if (data.getHost() == null) {
            data.setHost(publishHost);
        }
        jedis.hset(key, data.toIpPort(), "");
        localMemoryRegistry.register(key, data);
        return true;
    }

    @Override
    public Set<ServerSig> lookup(String key) {
        Set<String> hkeys = jedis.hkeys(key);
        return hkeys.stream().map(ServerSig::parse).collect(Collectors.toSet());
    }

    @Override
    public boolean remove(String key) {
        List<String> removed = new LinkedList<>();
        Set<ServerSig> serverSigs = lookup(key);
        for (ServerSig serverSig : serverSigs) {
            if (serverSig.getHost().equals(publishHost)) {
                removed.add(serverSig.toIpPort());
            }
        }
        if (!removed.isEmpty()) {
            jedis.hdel(key, removed.toArray(new String[removed.size()]));
        }
        return true;
    }
}
