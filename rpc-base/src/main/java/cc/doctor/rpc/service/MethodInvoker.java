package cc.doctor.rpc.service;

import cc.doctor.rpc.client.RemoteCall;
import cc.doctor.rpc.signature.SignatureUtils;
import com.thundersdata.rpc.client.RemoteCall;
import com.thundersdata.rpc.signature.SignatureUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker {
    private static final Logger logger = LoggerFactory.getLogger(MethodInvoker.class);

    private MethodInvoker() {
    }

    public static Object handle(RemoteCall remoteCall, Object service) {
        // find method
        Method method = SignatureUtils.findMethod(service, remoteCall.getMethod());
        // invoke method
        try {
            return method.invoke(service, remoteCall.getParams());
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("", e);
        }
        return null;
    }
}
