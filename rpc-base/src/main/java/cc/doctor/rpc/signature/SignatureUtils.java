package cc.doctor.rpc.signature;

import cc.doctor.rpc.annotation.RpcService;
import com.thundersdata.rpc.annotation.RpcService;
import com.thundersdata.rpc.service.RpcException;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

/**
 * 获取服务和方法的签名
 */
public class SignatureUtils {

    private SignatureUtils() {
    }

    public static ServiceSig getServiceSig(Class<?> clazz) {
        ServiceSig signature = new ServiceSig();
        String name = clazz.getName();
        // todo 多interface
        if (!clazz.isInterface()) {
            Class[] interfaces = clazz.getInterfaces();
            if (interfaces.length > 0) {
                name = interfaces[0].getName();
            }
        }
        if (clazz.isAnnotationPresent(RpcService.class)) {
            RpcService annotation = clazz.getAnnotation(RpcService.class);
            if (!annotation.value().equals("")) {
                name = annotation.value();
            }
            signature.setVersion(annotation.version());
        }
        signature.setName(name);
        return signature;
    }

    public static MethodSig getMethodSig(Method method) {
        MethodSig methodSig = new MethodSig();
        String name = method.getName();
        methodSig.setName(name);
        List<ParameterSig> parameterSigs = new LinkedList<>();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            ParameterSig parameterSig = getParameterSig(parameters[i]);
            parameterSigs.add(parameterSig);
        }
        methodSig.setParameterTypeSigs(parameterSigs);
        return methodSig;
    }

    public static ParameterSig getParameterSig(Parameter parameter) {
        ParameterSig parameterSig = new ParameterSig();
        String name = parameter.getName();
        parameterSig.setName(name);
        parameterSig.setParamClass(parameter.getType());
        return parameterSig;
    }

    public static Method findMethod(Object service, MethodSig methodSig) {
        Class<?> aClass = service.getClass();
        for (Method method : aClass.getMethods()) {
            MethodSig sig = getMethodSig(method);
            if (sig.equals(methodSig)) {
                return method;
            }
        }
        throw new RpcException("方法未找到！");
    }
}
