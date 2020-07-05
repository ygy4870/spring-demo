package com.ygy.study.iodemo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.ygy.study.iodemo.excel.DataListener;
import com.ygy.study.iodemo.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class IoDemoApplicationTests {


    /**
     * 基本的导出
     */
    @Test
    public void test1(){
//        准备数据
        List<User> users = new ArrayList<>();
        users.add(new User("张三", 20, 1));
        users.add(new User("李四", 20, 1));
        users.add(new User("王五", 20, 1));
        users.add(new User("麻溜", 20, 1));

        String fileName =  "test001.xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, User.class).sheet("模板").doWrite(users);

    }

    /**
     * 最简单的读
     */
    @Test
    public void simpleRead() {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 写法1：
        String fileName = "test001.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
//        EasyExcel.read(fileName, User.class, new DataListener()).sheet().doRead();

        // 写法2：
//        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read(fileName, User.class, new DataListener()).build();
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheet);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }


    }


}
