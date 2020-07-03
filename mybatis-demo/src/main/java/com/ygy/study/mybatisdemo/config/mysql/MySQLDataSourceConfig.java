package com.ygy.study.mybatisdemo.config.mysql;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties(MySQLConfigProperties.class)
public class MySQLDataSourceConfig {

    @Autowired
    private MySQLConfigProperties mySQLConfigProperties;

    @Bean(name = "mysqlDataSource")
    @ConditionalOnProperty(prefix = "mysql", value = "enabled", havingValue = "true")
    public DataSource mysqlDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(mySQLConfigProperties.getDriverClassName());
        dataSource.setUrl(mySQLConfigProperties.getUrl());
        dataSource.setUsername(mySQLConfigProperties.getUserName());
        dataSource.setPassword(mySQLConfigProperties.getPassword());
        dataSource.setMaxActive(mySQLConfigProperties.getMaxActive());
        dataSource.setInitialSize(mySQLConfigProperties.getInitialSize());
        dataSource.setMinIdle(mySQLConfigProperties.getMinIdle());
        dataSource.setMaxWait(mySQLConfigProperties.getMaxWait());
        return dataSource;
    }

    @Bean(name = "mysqlTransactionManager")
    public DataSourceTransactionManager mysqlTransactionManager() {
        return new DataSourceTransactionManager(mysqlDataSource());
    }

    @Bean(name = "mysqlSessionFactory")
    public SqlSessionFactory mysqlSessionFactory() throws Exception {

        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();

        sqlSessionFactoryBean.setDataSource(mysqlDataSource());

        sqlSessionFactoryBean.setTypeAliasesPackage(mySQLConfigProperties.getTypeAliasesPackage());

        sqlSessionFactoryBean.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources(mySQLConfigProperties.getMapperLocations()));

        //该配置将数据库中下划线自动转成驼峰命名的变量，并且只针对自定义的实体类生效，对map不生效的
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

        return sqlSessionFactoryBean.getObject();
    }

}
