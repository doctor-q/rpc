package cc.doctor.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RpcService {
    /**
     * 服务注册名称，默认是全限定名
     */
    String value() default "";

    /**
     * 版本信息
     */
    String version() default "";
}
