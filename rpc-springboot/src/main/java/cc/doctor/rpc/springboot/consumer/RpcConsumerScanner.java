package cc.doctor.rpc.springboot.consumer;

import cc.doctor.rpc.springboot.AndTypeFilter;
import com.thundersdata.rpc.springboot.AndTypeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Set;

public class RpcConsumerScanner extends ClassPathBeanDefinitionScanner {
    private static final Logger log = LoggerFactory.getLogger(RpcConsumerScanner.class);

    private Class<? extends Annotation> annotationFilter;
    private String serviceProxyRef;

    public RpcConsumerScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void registerFilters() {
        // 指定了扫描注解，扫描该注解下的接口
        if (annotationFilter != null && !annotationFilter.equals(Annotation.class)) {
            addIncludeFilter(new AnnotationTypeFilter(annotationFilter, true, true));
        }
        // 未指定注解，扫描所有接口
        else {
            addIncludeFilter(new AndTypeFilter());
        }
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
            String beanClassName = genericBeanDefinition.getBeanClassName();
            try {
                Class<?> aClass = Class.forName(beanClassName);
                genericBeanDefinition.getPropertyValues().add("clazz", aClass);
                genericBeanDefinition.getPropertyValues().add("serviceProxyRef", serviceProxyRef);
                // 设置实际类型
                genericBeanDefinition.setBeanClass(ConsumerProxyFactoryBean.class);
                genericBeanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
            } catch (ClassNotFoundException e) {
                log.error("", e);
            }
        }
        return beanDefinitionHolders;
    }

    /**
     * 覆盖beanDefinition的判断
     * 必须是一个接口类型和一个顶级类
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    public Class<? extends Annotation> getAnnotationFilter() {
        return annotationFilter;
    }

    public void setAnnotationFilter(Class<? extends Annotation> annotationFilter) {
        this.annotationFilter = annotationFilter;
    }

    public String getServiceProxyRef() {
        return serviceProxyRef;
    }

    public void setServiceProxyRef(String serviceProxyRef) {
        this.serviceProxyRef = serviceProxyRef;
    }
}
