package com.ygy.study.swaggerdemo.swaggerconfig;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {

        // 默认
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                ;

        // 指定 限定url
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // 指定包下的url才生成swagger
                .apis(RequestHandlerSelectors.basePackage("com.ygy.study.swaggerdemo.rest"))
                .paths(Predicates.or(PathSelectors.ant("/user/add"),
                        PathSelectors.ant("/user/find/*")))

                .build()
                .apiInfo(apiInfo())//
                ;

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Spring Boot 项目集成 Swagger 实例文档",
                "test",
                "API V1.0",
                "Terms of service",
                new Contact("名字", "https://itweknow.cn", "test@gmail.com"),
                "Apache", "http://www.apache.org/", Collections.emptyList());
    }

}
