package com.ygy.study.mysql.binlog;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

import java.io.IOException;

/**
 * Description: 请描述你的文件
 *
 * @author yangguangying
 * @date 2021-02-01
 * <p>
 */
public class BinlogClient {

    public static void main(String[] args) {
        String hostname = "127.0.0.1";
        int port = 3306;
        // schema设置，测试没生效
        String schema = "test2";
        String userName = "root";
        String password = "123456";
        BinaryLogClient client = new BinaryLogClient(hostname, port, schema, userName, password);
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );

        new Thread(() -> {
            client.registerEventListener(event -> {
                BinlogParse.parseEvent(event);
            });
            try {
                client.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
