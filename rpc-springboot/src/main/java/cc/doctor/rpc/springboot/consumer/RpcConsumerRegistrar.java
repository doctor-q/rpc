package cc.doctor.rpc.springboot.consumer;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;

public class RpcConsumerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(RpcConsumerScan.class.getName()));
        String[] basePackages = annoAttrs.getStringArray("basePackages");
        Class<? extends Annotation> annotationFilter = annoAttrs.getClass("annotationFilter");
        String serviceProxyRef = annoAttrs.getString("serviceProxyRef");
        RpcConsumerScanner rpcConsumerScanner = new RpcConsumerScanner(registry);
        rpcConsumerScanner.setAnnotationFilter(annotationFilter);
        rpcConsumerScanner.setServiceProxyRef(serviceProxyRef);
        if (resourceLoader != null) {
            rpcConsumerScanner.setResourceLoader(resourceLoader);
        }
        rpcConsumerScanner.registerFilters();
        rpcConsumerScanner.doScan(basePackages);
    }
}
