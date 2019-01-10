package cc.doctor.rpc.springboot.provider;

import cc.doctor.rpc.annotation.RpcService;
import cc.doctor.rpc.server.Server;
import cc.doctor.rpc.service.ServiceHandler;
import cc.doctor.rpc.springboot.utils.SpringBeanUtils;
import com.thundersdata.rpc.annotation.RpcService;
import com.thundersdata.rpc.server.Server;
import com.thundersdata.rpc.service.DefaultServiceHandler;
import com.thundersdata.rpc.service.RpcException;
import com.thundersdata.rpc.service.ServiceHandler;
import com.thundersdata.rpc.springboot.utils.SpringBeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 自动注册服务
 * service必须被定义为一个bean
 */
@Service
public class ServiceBeanRegistry implements InitializingBean {

    @Autowired(required = false)
    private Server server;

    private void autoRegistry() {
        if (server == null) {
            throw new RpcException("服务端未定义");
        }
        ServiceHandler serviceHandler = server.serviceHandler();
        Map<String, Object> beansOfAnnotation = SpringBeanUtils.getBeansOfAnnotation(RpcService.class);
        for (Object service : beansOfAnnotation.values()) {
            serviceHandler.addService(service);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        autoRegistry();
    }
}
