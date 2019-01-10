package cc.doctor.rpc.springboot.consumer;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 客户端存根扫描
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RpcConsumerRegistrar.class)
public @interface RpcConsumerScan {
    /**
     * 扫描包
     */
    String[] basePackages();

    /**
     * 扫描到的service属于哪一个proxy
     */
    String serviceProxyRef() default "";

    /**
     * 根据注解过滤service
     */
    Class<? extends Annotation> annotationFilter() default Annotation.class;
}
