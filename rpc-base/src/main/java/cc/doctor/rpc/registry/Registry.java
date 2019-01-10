package cc.doctor.rpc.registry;

import java.util.Set;

/**
 * 注册中心
 *
 * @param <T> 注册数据类型
 */
public interface Registry<T> {
    /**
     * 启动注册中心，初始化工作
     */
    Registry<T> start();

    /**
     * 关闭注册中心，清理工作
     */
    void shutdown();

    /**
     * 保存注册数据
     */
    boolean register(String key, T data);

    /**
     * 查找数据
     */
    Set<T> lookup(String key);

    /**
     * 注销
     */
    boolean remove(String key);
}
