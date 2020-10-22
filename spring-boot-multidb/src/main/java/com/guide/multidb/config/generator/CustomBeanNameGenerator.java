package com.guide.multidb.config.generator;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import java.util.ArrayList;
import java.util.List;

public class CustomBeanNameGenerator implements BeanNameGenerator {
    /**
     * basePackages 외에 scaning된 beanGenerator
     */
    private static final BeanNameGenerator DELEGATE = new AnnotationBeanNameGenerator();

    /**
     * VersioningBeanNameGenerator 대상 package 경로
     */
    private List<String> basePackages = new ArrayList<>();

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String beanName;
        if(isTargetPackageBean(definition)) {
            beanName = getBeanName(definition);
            return beanName;
        }

        beanName = DELEGATE.generateBeanName(definition, registry);
        return beanName;
    }

    private boolean isTargetPackageBean(BeanDefinition definition) {
        String beanClassName = getBeanName(definition);
        return basePackages.stream().anyMatch(beanClassName::startsWith);
    }

    private String getBeanName(BeanDefinition definition) {
        return definition.getBeanClassName();
    }

    public boolean addBasePackages(String path) {
        return this.basePackages.add(path);
    }
}
