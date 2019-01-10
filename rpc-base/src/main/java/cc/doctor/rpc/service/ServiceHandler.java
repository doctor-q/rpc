package cc.doctor.rpc.service;


import com.thundersdata.rpc.signature.ServiceSig;

import java.util.Collection;

/**
 * 维护服务端提供的服务列表，提供服务分发的功能
 */
public interface ServiceHandler {

    /**
     * 添加服务
     */
    void addService(Object service);

    /**
     * 添加服务且指定名字
     */
    void addService(String name, Object service);

    /**
     * 添加服务指定服务签名
     */
    void addService(ServiceSig signature, Object service);

    /**
     * 获取服务名列表
     */
    Collection<ServiceSig> getServiceList();

    /**
     * 获取服务的版本列表
     */
    Collection<String> getServiceVersionList(String service);

    /**
     * 获取服务
     */
    Object getService(ServiceSig service);
}
