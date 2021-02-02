package com.ygy.study.mysql.binlog.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Description: 请描述你的文件
 *
 * @author yangguangying
 * @date 2021-02-02
 * <p>
 */
@Getter
@Setter
public class MyDtsDto {
    private Date executeTime;
    private String changeType;
    private String table;
    private Map<String, Object> before = new HashMap<>();
    private Map<String, Object> after = new HashMap<>();
    private List<String> updates = new ArrayList<>();

    public MyDtsDto(){}
    public MyDtsDto(Date executeTime, String changeType, String table) {
        this.executeTime = executeTime;
        this.changeType = changeType;
        this.table = table;
    }
}
