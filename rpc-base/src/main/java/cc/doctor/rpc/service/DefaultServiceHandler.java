package cc.doctor.rpc.service;

import cc.doctor.rpc.signature.SignatureUtils;
import com.thundersdata.rpc.signature.ServiceSig;
import com.thundersdata.rpc.signature.SignatureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultServiceHandler implements ServiceHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultServiceHandler.class);

    /**
     * 服务名->版本->服务存根
     */
    private Map<ServiceSig, Object> registry = new ConcurrentHashMap<>();

    @Override
    public void addService(Object service) {
        ServiceSig serviceSig = SignatureUtils.getServiceSig(service.getClass());
        addService(serviceSig, service);
    }

    @Override
    public void addService(String name, Object service) {
        ServiceSig signature = new ServiceSig();
        signature.setName(name);
        addService(signature, service);
    }

    @Override
    public void addService(ServiceSig signature, Object service) {
        log.info("Regist service {} version {}", signature.getName(), signature.getVersion());
        registry.put(signature, service);
    }

    @Override
    public Collection<ServiceSig> getServiceList() {
        return registry.keySet();
    }

    @Override
    public Collection<String> getServiceVersionList(String service) {
        List<String> versions = new LinkedList<>();
        for (ServiceSig serviceSig : registry.keySet()) {
            if (serviceSig.getName().equals(service)) {
                versions.add(serviceSig.getVersion());
            }
        }
        return versions;
    }

    @Override
    public Object getService(ServiceSig service) {
        return registry.get(service);
    }

}
