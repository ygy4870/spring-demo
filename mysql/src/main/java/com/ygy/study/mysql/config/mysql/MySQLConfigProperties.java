package com.ygy.study.mysql.config.mysql;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 使用@ConfigurationProperties注解 提示 “Spring Boot Configuration Annotation Processor not found in classpath”
 * 参考：https://www.cnblogs.com/oukele/p/11390382.html
 * 说是增加依赖：
 *      <artifactId>spring-boot-configuration-processor</artifactId>
 * 但不加，功能上也没有问题，后面再研究下
 */
@Getter
@Setter
@ConfigurationProperties("mysql")
public class MySQLConfigProperties {

    private String driverClassName;
    private String url;
    private String userName;
    private String password;
    private int maxActive;
    private int initialSize;
    private int minIdle;
    private int maxWait;

    private String typeAliasesPackage;
    private String mapperLocations;

}
