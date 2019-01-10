package cc.doctor.rpc.server;

import com.thundersdata.rpc.service.ServiceHandler;

public interface Server {
    /**
     * 启动服务
     */
    void start();

    /**
     * 停止服务
     */
    void stop();

    /**
     * 服务是否已启动
     */
    boolean isStarted();

    /**
     * 服务是否已停止
     */
    boolean isStopped();

    void block() throws InterruptedException;

    /**
     * 注册服务
     */
    void registerService();

    /**
     * 获取{@link ServiceHandler}
     */
    ServiceHandler serviceHandler();
}
