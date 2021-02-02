package com.ygy.study.mysql.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Description: 请描述你的文件
 *
 * @author yangguangying
 * @date 2021-02-02
 * <p>
 */

@Getter
@Setter
public class Columns {
    private String tableName;
    private String columnName;
    /**
     * 位置，从1开始
     */
    private int ordinalPosition;
    private String dataType;
}
