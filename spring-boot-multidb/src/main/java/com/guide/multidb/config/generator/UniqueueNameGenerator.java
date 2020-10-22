package com.guide.multidb.config.generator;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class UniqueueNameGenerator extends AnnotationBeanNameGenerator {
    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String beanName = definition.getBeanClassName();
        String returnValue = super.generateBeanName(definition, registry);
        return returnValue;
    }
}
