package cc.doctor.rpc.springboot.provider;

import cc.doctor.rpc.annotation.RpcService;
import com.thundersdata.rpc.annotation.RpcService;
import com.thundersdata.rpc.springboot.consumer.ConsumerProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

public class RpcProviderScanner extends ClassPathBeanDefinitionScanner {
    private static final Logger log = LoggerFactory.getLogger(RpcProviderScanner.class);


    public RpcProviderScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void registerFilters() {
        // 扫描用RpcService注解表示的service
        AnnotationTypeFilter annotationTypeFilter = new AnnotationTypeFilter(RpcService.class, true, true);
        addIncludeFilter(annotationTypeFilter);
        // exclude package-info.java
        addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {

        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        if (beanDefinitionHolders.isEmpty()) {
            log.warn("没有找到服务，请先声明服务");
        }
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            // 设置实际类型
            genericBeanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
        return beanDefinitionHolders;
    }
}
