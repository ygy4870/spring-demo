package com.ygy.study.mybatisdemo.config.mysql;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * 使用@ConfigurationProperties注解 提示 “Spring Boot Configuration Annotation Processor not found in classpath”
 * 参考：https://www.cnblogs.com/oukele/p/11390382.html
 * 说是增加依赖：
 *      <artifactId>spring-boot-configuration-processor</artifactId>
 * 但不加，功能上也没有问题，后面再研究下
 */
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

    public String getTypeAliasesPackage() {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage) {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public String getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }
}
