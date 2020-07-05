package com.ygy.study.iodemo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.ygy.study.iodemo.model.User;

import java.util.ArrayList;
import java.util.List;

public class DataListener extends AnalysisEventListener<User> {

    private static final int BATCH_COUNT = 5;
    List<User> datas = new ArrayList<>(BATCH_COUNT);

    @Override
    public void invoke(User user, AnalysisContext analysisContext) {
        datas.add(user);
        System.out.println(user);
        if (datas.size() >= BATCH_COUNT) {
            // 保存
            // 清理
            datas.clear();
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 保存
    }

}
