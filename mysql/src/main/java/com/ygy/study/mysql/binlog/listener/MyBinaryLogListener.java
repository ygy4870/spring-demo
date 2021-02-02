package com.ygy.study.mysql.binlog.listener;

import com.alibaba.fastjson.JSON;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.ygy.study.mysql.binlog.dto.MyDtsDto;
import com.ygy.study.mysql.config.mysql.BinlogProperties;
import com.ygy.study.mysql.dao.InformationSchemaDao;
import com.ygy.study.mysql.entity.Columns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 监听binlog并进行解析成表实体
 */
@Component
public class MyBinaryLogListener {

    /**
     * Map<tableId : Map<字段位置, 字段名>>
     */
    static Map<Long, Map<Integer, String>> tableColumnsCache = new ConcurrentHashMap<>();
    /**
     * Map<tableId, 表名>
     */
    static Map<Long, String> tableNameCache = new ConcurrentHashMap<>();

    @Autowired
    private InformationSchemaDao informationSchemaDao;
    @Autowired
    private BinlogProperties binlogProperties;

    @PostConstruct
    public void run() {
        BinaryLogClient client = new BinaryLogClient(binlogProperties.getHostname(), binlogProperties.getPort(), binlogProperties.getSchema(),
                binlogProperties.getUserName(), binlogProperties.getPassword());
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );

        new Thread(() -> {
            client.registerEventListener(event -> {
                MyDtsDto myDtsDto = parseEventAndBuildDts(event);
                System.out.println(JSON.toJSON(myDtsDto));
            });
            try {
                client.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public MyDtsDto parseEventAndBuildDts(Event event) {
        if (Objects.isNull(event)) {
            return null;
        }
        EventHeader header = event.getHeader();
        EventData data = event.getData();
        if (data instanceof TableMapEventData) {
            parseTableMapEventData((TableMapEventData) data);
        } else if (data instanceof WriteRowsEventData) {
            return parseWriteRowsEventData(header.getTimestamp(), (WriteRowsEventData) data);
        } else if (data instanceof UpdateRowsEventData) {
            return parseWriteUpdateRowsEventData(header.getTimestamp(), (UpdateRowsEventData) data);
        } else if (data instanceof DeleteRowsEventData) {
            return parseDeleteRowsEventData(header.getTimestamp(), (DeleteRowsEventData) data);
        }

        return null;
    }

    private MyDtsDto parseDeleteRowsEventData(long timestamp, DeleteRowsEventData data) {
        long tableId = data.getTableId();

        MyDtsDto myDtsDto = new MyDtsDto(new Date(timestamp), "delete", tableNameCache.get(tableId));

        Map<Integer, String> columnMap = tableColumnsCache.get(tableId);
        if (Objects.isNull(columnMap)) {
            return  myDtsDto;
        }

        Map<String, Object> before = myDtsDto.getBefore();
        Serializable[] serializables = data.getRows().get(0);
        for (int i = 0; i < serializables.length; i++) {
            String name = columnMap.get(i);
            before.put(name, serializables[i]);
        }

        return myDtsDto;
    }

    private MyDtsDto parseWriteUpdateRowsEventData(long timestamp, UpdateRowsEventData data) {
        long tableId = data.getTableId();

        MyDtsDto myDtsDto = new MyDtsDto(new Date(timestamp), "update", tableNameCache.get(tableId));

        Map<Integer, String> columnMap = tableColumnsCache.get(tableId);
        if (Objects.isNull(columnMap)) {
            return  myDtsDto;
        }

        Map<String, Object> after = myDtsDto.getAfter();
        Map<String, Object> before = myDtsDto.getBefore();
        List<String> updates = myDtsDto.getUpdates();

        for (Map.Entry<Serializable[], Serializable[]> row : data.getRows()) {
            for (int i = 0; i < row.getKey().length; i++) {
                String name = columnMap.get(i);
                before.put(name, row.getKey()[i]);
            }
            for (int i = 0; i < row.getValue().length; i++) {
                String name = columnMap.get(i);
                after.put(name, row.getValue()[i]);
            }
        }

        for (Map.Entry<String, Object> entry : before.entrySet()) {
            if (!entry.getValue().equals(after.get(entry.getKey()))) {
                updates.add(entry.getKey());
            }
        }

        return myDtsDto;
    }

    private void parseTableMapEventData(TableMapEventData data) {
        long tableId = data.getTableId();
        Map<Integer, String> filedPositionMap = tableColumnsCache.get(tableId);
        if (Objects.isNull(filedPositionMap)) {
            tableNameCache.put(tableId, data.getTable());
            List<Columns> columns = informationSchemaDao.listColumnsByTable(data.getDatabase(), data.getTable());
            if (!CollectionUtils.isEmpty(columns)) {
                filedPositionMap = new HashMap<>(64);
                for (Columns column : columns) {
                    filedPositionMap.put(column.getOrdinalPosition()-1, column.getColumnName());
                }
                tableColumnsCache.put(tableId, filedPositionMap);
            }
        }
    }

    private MyDtsDto parseWriteRowsEventData(long timestamp, WriteRowsEventData data) {

        long tableId = data.getTableId();

        MyDtsDto myDtsDto = new MyDtsDto(new Date(timestamp), "insert", tableNameCache.get(tableId));

        Map<Integer, String> columnMap = tableColumnsCache.get(tableId);
        if (Objects.isNull(columnMap)) {
            return  myDtsDto;
        }

        Map<String, Object> after = myDtsDto.getAfter();
        Serializable[] serializables = data.getRows().get(0);
        for (int i = 0; i < serializables.length; i++) {
            String name = columnMap.get(i);
            after.put(name, serializables[i]);
        }

        return myDtsDto;
    }

}
