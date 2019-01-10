package cc.doctor.rpc.springboot.provider;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 服务提供扫描
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RpcProviderRegistrar.class)
public @interface RpcProviderScan {
    /**
     * 扫描包
     */
    String[] basePackages();
}
