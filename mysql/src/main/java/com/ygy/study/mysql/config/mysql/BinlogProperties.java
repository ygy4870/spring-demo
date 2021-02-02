package com.ygy.study.mysql.config.mysql;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description: 请描述你的文件
 *
 * @author yangguangying
 * @date 2021-02-02
 * <p>
 */
@Getter
@Setter
@ConfigurationProperties("binlog")
public class BinlogProperties {

    private String hostname = "127.0.0.1";
    private int port = 3306;
    // schema设置，测试没生效
    private String schema = "test2";
    private String userName = "root";
    private String password = "123456";
}
