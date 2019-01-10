package cc.doctor.rpc.springboot.provider;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

public class RpcProviderRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(RpcProviderScan.class.getName()));
        String[] basePackages = annoAttrs.getStringArray("basePackages");
        RpcProviderScanner rpcProviderScanner = new RpcProviderScanner(registry);
        if (resourceLoader != null) {
            rpcProviderScanner.setResourceLoader(resourceLoader);
        }
        // register filter RpcService
        rpcProviderScanner.registerFilters();
        rpcProviderScanner.doScan(basePackages);
    }
}
