package eop.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Map;

public class ResourceBeanProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Map map = PublicCode.systemMap;
        Map mapLinkage = PublicCode.linkageMap;
        if (bean instanceof ReloadableResourceBundleMessageSource) {
            // 初始化StringUtil的MessageSource
            MessageSource ms = (ReloadableResourceBundleMessageSource) bean;
            CustomizedProperty.setMessageSource(ms);
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
