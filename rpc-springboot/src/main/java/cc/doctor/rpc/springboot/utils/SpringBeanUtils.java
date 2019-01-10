package cc.doctor.rpc.springboot.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

@Component
public class SpringBeanUtils implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor {

    private static ApplicationContext applicationContext;

    //bean的名称生成器
    private static BeanDefinitionRegistry beanDefinitionRegistry;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringBeanUtils.applicationContext == null) {
            SpringBeanUtils.applicationContext = applicationContext;
        }
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public static Map<String, Object> getBeansOfAnnotation(Class<? extends Annotation> annotationType) {
        return getApplicationContext().getBeansWithAnnotation(annotationType);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return getApplicationContext().getBeansOfType(type);
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        if (SpringBeanUtils.beanDefinitionRegistry == null) {
            SpringBeanUtils.beanDefinitionRegistry = beanDefinitionRegistry;
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    // 注册bean，类名作为bean的名称
    public static void registerBean(Class clazz) {
        BeanDefinitionBuilder bdb = BeanDefinitionBuilder.rootBeanDefinition(clazz);
        //  注册bean
        beanDefinitionRegistry.registerBeanDefinition(clazz.getName(), bdb.getBeanDefinition());
    }
}
