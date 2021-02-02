package com.ygy.study.mysql.dao;

import com.ygy.study.mysql.entity.Columns;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface InformationSchemaDao {

    @Select("select table_name,column_name,ordinal_position ,data_type\n" +
            "        from information_schema.columns\n" +
            "        where table_schema=#{tableSchema} and table_name=#{tableName}")
    List<Columns> listColumnsByTable(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);
}
