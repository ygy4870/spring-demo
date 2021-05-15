package com.ygy.study.mysql.dao;

import com.ygy.study.mysql.TwoColorBall;
import com.ygy.study.mysql.RandomRedBlue;
import com.ygy.study.mysql.entity.Columns;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface InformationSchemaDao {

    @Select("select table_name,column_name,ordinal_position ,data_type\n" +
            "        from information_schema.columns\n" +
            "        where table_schema=#{tableSchema} and table_name=#{tableName}")
    List<Columns> listColumnsByTable(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);

    @Insert("insert into caipiao(red,blue,term) value(#{red}, #{blue}, #{term})")
    int insert(TwoColorBall p);

    @Select("select * from caipiao")
    List<TwoColorBall> listAll();

    @Update("update caipiao set total=#{total} where term=#{term}")
    int updateTotal(@Param("total") int total, @Param("term") String term);

    @Select("SELECT count(*)/(SELECT count(*) from caipiao) from caipiao where red like CONCAT('%',blue,'%')")
    double redBlueRepeatHistoryRate();

    @Select("SELECT count(*) from caipiao where " +
            "( " +
            " if(red like concat('%',#{red1},'%'), 1, 0) + " +
            " if(red like concat('%',#{red2},'%'), 1, 0) + " +
            " if(red like concat('%',#{red3},'%'), 1, 0) + " +
            " if(red like concat('%',#{red4},'%'), 1, 0) + " +
            " if(red like concat('%',#{red5},'%'), 1, 0) + " +
            " if(red like concat('%',#{red6},'%'), 1, 0) + " +
            " if(blue=#{blue}, 1, 0)" +
            ") >= #{number} limit 1")
    int exitHistory(RandomRedBlue redBlue);

    @Select("SELECT * from caipiao where " +
            "( " +
            " if(red like concat('%',#{red1},'%'), 1, 0) + " +
            " if(red like concat('%',#{red2},'%'), 1, 0) + " +
            " if(red like concat('%',#{red3},'%'), 1, 0) + " +
            " if(red like concat('%',#{red4},'%'), 1, 0) + " +
            " if(red like concat('%',#{red5},'%'), 1, 0) + " +
            " if(red like concat('%',#{red6},'%'), 1, 0) + " +
            " if(blue=#{blue}, 1, 0)" +
            ") >= #{number} limit 1")
    TwoColorBall getExitHistory(RandomRedBlue redBlue);

    @Select("select count(*) from caipiao where total=#{total}")
    int countEqualTotalValue(@Param("total") int total);

    @Select("select * from caipiao where term=#{term}")
    TwoColorBall getByTerm(@Param("term") String term);

    @Select(" select count(*) from " +
            " (" +
            "  select * from caipiao order by term desc limit #{termNumber} " +
            " ) tmp " +
            " where red like concat('%',#{number},'%');")
    int calcCountByNumber(@Param("number") String number, @Param("termNumber") int termNumber);

    @Insert("insert into number_info(number,total_count,count30,count50,count100) value(#{number},#{totalCount},#{count30},#{count50},#{count100})")
    int insertNumberInfo(@Param("number") String number, @Param("totalCount") int totalCount, @Param("count30") int count30, @Param("count50") int count50, @Param("count100") int count100);
}
