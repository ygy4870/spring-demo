package com.ygy.study.mysql.binlog.demo;

import com.github.shyiko.mysql.binlog.BinaryLogFileReader;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Description: 请描述你的文件
 *
 * @author yangguangying
 * @date 2021-01-30
 * <p>
 */

/**
 * --------------------------------------------------
 *
 *   1、my.ini 在5.7版本中默认不存在，在安装路径下手动创建即可
 *
 *   2、my.ini 增加如下：
 * 	# 打开binlog
 * 	log_bin=mysql-binlog
 * 	# Server Id.数据库服务器id，这个id用来在主从服务器中标记唯一mysql服务器，MySQL 5.7 开始，开启 binlog 后，--server-id 参数也必须指定，否则 MySQL 服务器会启动失败
 * 	server-id=1
 *
 *   3、binlog是二进制，可用mysqlbinlog.exe解析
 * 	D:\mysql-5.7.30-winx64\bin>mysqlbinlog.exe --no-defaults ../data/mysql-binlog.000001
 * 	D:\mysql-5.7.30-winx64\bin>mysqlbinlog.exe --no-defaults --base64-output=decode-rows -v ../data/mysql-binlog.000001
 * 	D:\mysql-5.7.30-winx64\bin>mysqlbinlog.exe --no-defaults --base64-output=decode-rows -vv ../data/mysql-binlog.000001
 *
 *   4、show
 * 	# 查看log
 * 	show binlog events in  'mysql-binlog.000001';
 *
 * 	# 获取binlog文件列表
 * 	show binary logs;
 *
 * 	# 查看当前log
 * 	show binlog events;
 *
 * 	# 查看日志格式，binlog日志格式有三种：STATEMENT、ROW、MIXED
 * 	show variables like 'binlog_format';
 *
 *   5、修改binlog格式，在my.ini配置中修改
 * 	binlog_format=STATEMENT
 *
 *
 *   6、代码解析binlog、生成原始sql、生成回滚sql
 * 	https://segmentfault.com/a/1190000016679734
 * 	https://blog.csdn.net/weixin_39116497/article/details/107752395
 *
 *
 * ---------------------------------------------------------------
 */
public class BinlogParse {

    public static void main(String[] args) throws IOException {
        // statement格式解析
        String statementBinlogFilePath = "D:\\mysql-5.7.30-winx64\\data\\mysql-binlog.000002";
        parseSQL(statementBinlogFilePath);

        // row格式解析
//        String rowBinlogFilePath = "D:\\mysql-5.7.30-winx64\\data\\mysql-binlog.000003";
//        parseSQL(rowBinlogFilePath);

    }

    public static void parseEvent(Event event) {
        if (Objects.isNull(event)) {
            return;
        }
        EventData data = event.getData();
        if (data instanceof QueryEventData) {
            QueryEventData queryEventData = (QueryEventData) data;
            System.out.println(queryEventData);
        }
        else if (data instanceof XidEventData) {
            XidEventData xidEventData = (XidEventData) data;
            System.out.println(xidEventData);
        }
        else if (data instanceof TableMapEventData) {
            TableMapEventData tableMapEventData = (TableMapEventData) data;
            System.out.println(tableMapEventData);
        }
        else if (data instanceof WriteRowsEventData) {
            WriteRowsEventData writeRowsEventData = (WriteRowsEventData) data;
            System.out.println(writeRowsEventData);
        }
        else if (data instanceof UpdateRowsEventData) {
            UpdateRowsEventData updateRowsEventData = (UpdateRowsEventData) data;
            System.out.println(updateRowsEventData);
        }
        else if (data instanceof DeleteRowsEventData) {
            DeleteRowsEventData deleteRowsEventData = (DeleteRowsEventData) data;
            System.out.println(deleteRowsEventData);
        }
    }

    public static void parseSQL(String filePath) throws IOException {
        File binlogFile = new File(filePath);
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        BinaryLogFileReader reader = new BinaryLogFileReader(binlogFile, eventDeserializer);
        try {
            for (Event event; (event = reader.readEvent()) != null; ) {
                parseEvent(event);
            }
        } finally {
            reader.close();
        }
    }

}
