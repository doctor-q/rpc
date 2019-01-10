package cc.doctor.rpc.springboot.consumer;

import cc.doctor.rpc.client.ClientProxy;
import cc.doctor.rpc.springboot.utils.SpringBeanUtils;
import com.thundersdata.rpc.client.ClientProxy;
import com.thundersdata.rpc.springboot.utils.SpringBeanUtils;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class ConsumerProxyFactoryBean<T> extends AbstractFactoryBean<T> {
    /**
     * 服务类型
     */
    private Class<T> clazz;

    /**
     * 指定consumer使用的proxy，否则使用默认的proxy
     */
    private String serviceProxyRef;

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }

    @Override
    protected T createInstance() throws Exception {
        ClientProxy clientProxy;
        if (serviceProxyRef != null && !serviceProxyRef.trim().equals("")) {
            clientProxy = (ClientProxy) SpringBeanUtils.getBean(serviceProxyRef);
        } else {
            clientProxy = SpringBeanUtils.getBean(ClientProxy.class);
        }
        return clientProxy.getService(clazz);
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getServiceProxyRef() {
        return serviceProxyRef;
    }

    public void setServiceProxyRef(String serviceProxyRef) {
        this.serviceProxyRef = serviceProxyRef;
    }
}
