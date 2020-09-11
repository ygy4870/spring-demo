package com.ygy.study.iodemo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.ygy.study.iodemo.model.CampusTemplate;

import java.util.ArrayList;
import java.util.List;

public class CampusTemplateListener extends AnalysisEventListener<CampusTemplate> {

    private static final int BATCH_COUNT = 5;
    List<CampusTemplate> datas = new ArrayList<>(BATCH_COUNT);


    String insertSqlTemplate = "INSERT INTO `campus_contract_print`(`template_code`,\n" +
            "                                    `campus_id`,\n" +
            "                                    `institution_name`,\n" +
            "                                    `school_address`,\n" +
            "                                    `examine_organization`,\n" +
            "                                    `registration_authority`,\n" +
            "                                    `school_license_number`,\n" +
            "                                    `school_license_time`,\n" +
            "                                    `social_code`,\n" +
            "                                    `business_license_time`,\n" +
            "                                    `contact_person`,\n" +
            "                                    `contact_phone_number`,\n" +
            "                                    `contact_address`,\n" +
            "                                    `account_bank`,\n" +
            "                                    `account`,\n" +
            "                                    `create_user_id`,\n" +
            "                                    `update_user_id`,\n" +
            "                                    `create_time`,\n" +
            "                                    `update_time`,\n" +
            "                                    `training_address`,\n" +
            "                                    `flag`,\n" +
            "                                    `not_specify_the_teacher`,\n" +
            "                                    `has_teacher_certification`,\n" +
            "                                    `is_other_refund`,\n" +
            "                                    `other_refunds`,\n" +
            "                                    `default_way`,\n" +
            "                                    `first_way`)\n" +
            "VALUES     ('GUANG_DONG_V2',\n" +
            "            '%s',\n" +
            "            '%s',\n" +
            "            '%s',\n" +
            "            '%s',\n" +
            "            '%s',\n" +
            "            '%s',\n" +
            "            '%s',\n" +
            "            '%s',\n" +
            "            '%s',\n" +
            "            '',\n" +
            "            NULL,\n" +
            "            NULL,\n" +
            "            NULL,\n" +
            "            NULL,\n" +
            "            '',\n" +
            "            '',\n" +
            "            NOW(),\n" +
            "            NOW(),\n" +
            "            NULL,\n" +
            "            '1',\n" +
            "            0,\n" + // 是否指定教师，双师不指定：1-指定，0-不指定
            "            1,\n" + // 是否有教师资格证，星火双师都有：1-有，0-没有
            "            0,\n" +
            "            NULL,\n" +
            "            NULL,\n" +
            "            NULL);\n";

    @Override
    public void invoke(CampusTemplate campusTemplate, AnalysisContext analysisContext) {
        datas.add(campusTemplate);
//        System.out.println(campusTemplate);

        String insertSql = String.format(insertSqlTemplate,
                campusTemplate.getCampusId(),
                campusTemplate.getInstitutionName(),
                campusTemplate.getSchoolAddress(),
                campusTemplate.getExamineOrganization(),
                campusTemplate.getRegistrationAuthority(),
                campusTemplate.getSchoolLicenseNumber(),
                campusTemplate.getSchoolLicenseTime(),
                campusTemplate.getSocialCode(),
                campusTemplate.getBusinessLicenseTime());

        System.out.println(insertSql);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 保存
//        System.out.println("---------------------------------结束");
    }
}
