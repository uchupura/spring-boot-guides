package com.guide.multidb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringGuideApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringGuideApplication.class, args);

        /*
        @Component의 Bean 이름은 클래스 이름이고 다른 패키지에 동일한 클래스 생성 시 Bean 이름 중복 오류 발생
        이를 해결하기 위해 클래스 패스까지 빈 이름 생성시 넣는 방법

        CustomBeanNameGenerator beanNameGenerator = new CustomBeanNameGenerator();
        beanNameGenerator.addBasePackages("com.guide.multidb.config.datasource");

        new SpringApplicationBuilder(SpringGuideApplication.class)
                .beanNameGenerator(beanNameGenerator)
                .run(args);
        */
    }
}
