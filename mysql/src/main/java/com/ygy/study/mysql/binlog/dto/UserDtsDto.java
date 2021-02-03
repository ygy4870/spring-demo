package com.ygy.study.mysql.binlog.dto;

import com.ygy.study.mysql.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Description: 请描述你的文件
 *
 * @author yangguangying
 * @date 2021-02-03
 * <p>
 */
@Getter
@Setter
public class UserDtsDto {
    private Date executeTime;
    private String changeType;
    private String table;
    private User before;
    private User after;
    private List<String> updates;
}
