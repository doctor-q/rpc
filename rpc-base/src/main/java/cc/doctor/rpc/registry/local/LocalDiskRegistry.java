package cc.doctor.rpc.registry.local;

import cc.doctor.rpc.service.RpcException;
import cc.doctor.rpc.signature.ServerSig;
import com.thundersdata.rpc.registry.IpPortRegistry;
import com.thundersdata.rpc.service.RpcException;
import com.thundersdata.rpc.signature.ServerSig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;

/**
 * 本地磁盘注册中心
 * 多进程可以根据磁盘共享注册数据
 * 单例
 */
public class LocalDiskRegistry implements IpPortRegistry {
    private static final Logger log = LoggerFactory.getLogger(LocalDiskRegistry.class);

    private static final LocalDiskRegistry registry = new LocalDiskRegistry();
    private static final String ROOT_DIR = "/tmp/registry";

    private LocalMemoryRegistry localRegistry = LocalMemoryRegistry.getLocalRegistry();
    private File root;

    private LocalDiskRegistry() {
    }

    public static LocalDiskRegistry getRegistry() {
        return registry;
    }

    @Override
    public LocalDiskRegistry start() {
        loadData();
        return this;
    }

    @Override
    public void shutdown() {
        for (String file : localRegistry.getContentMap().keySet()) {
            new File(root, file).deleteOnExit();
        }
        localRegistry.shutdown();
    }

    @Override
    public boolean register(String key, ServerSig data) {
        if (data.getHost() == null) {
            data.setHost("127.0.0.1");
        }
        localRegistry.register(key, data);
        File dataFile = new File(root, key);
        if (dataFile.exists()) {
            throw new RpcException("数据文件已存在");
        }
        try {
            Files.write(dataFile.toPath(), data.toIpPort().getBytes());
        } catch (IOException e) {
            log.error("", e);
            return false;
        }
        return true;
    }

    @Override
    public Set<ServerSig> lookup(String key) {
        return localRegistry.lookup(key);
    }

    @Override
    public boolean remove(String key) {
        new File(root, key).deleteOnExit();
        return true;
    }

    private void loadData() {
        if (root == null) {
            root = new File(ROOT_DIR);
            if (!root.exists()) {
                boolean mkdir = root.mkdir();
                if (!mkdir) {
                    throw new RpcException("目录创建失败");
                }
            } else if (!root.isDirectory()) {
                throw new RpcException("存在文件" + ROOT_DIR + "但不是目录");
            }
        }
        File[] files = root.listFiles();
        if (files == null) {
            throw new RpcException("加载文件出错");
        }
        for (File file : files) {
            if (file.isDirectory()) {
                continue;
            }
            try {
                byte[] bytes = Files.readAllBytes(file.toPath());
                String key = file.getName();
                String data = new String(bytes, "UTF-8");
                ServerSig serverSig = ServerSig.parse(data);
                localRegistry.register(key, serverSig);
            } catch (IOException e) {
                log.error("", e);
            }
        }
    }
}
