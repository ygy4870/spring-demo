package com.ygy.study.iodemo.model;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class CampusTemplate {

    @ExcelProperty(value ="分公司名称",index = 1)
    private String branchName;
    @ExcelProperty(value ="校区ID",index = 2)
    private String campusId;
    @ExcelProperty(value ="校区名称",index = 3)
    private String campusName;
    @ExcelProperty(value ="机构名称",index = 4)
    private String institutionName;
    @ExcelProperty(value ="办学地址",index = 5)
    private String schoolAddress;
    @ExcelProperty(value ="审批机关",index = 6)
    private String examineOrganization;
    @ExcelProperty(value ="登记注册机关",index = 7)
    private String registrationAuthority;
    @ExcelProperty(value ="办学许可编号",index = 8)
    private String schoolLicenseNumber;
    @ExcelProperty(value ="办学许可有效期",index = 9)
    private String schoolLicenseTime;
    @ExcelProperty(value ="统一社会信用代码",index = 10)
    private String socialCode;
    @ExcelProperty(value ="营业执照有效期",index = 11)
    private String businessLicenseTime;
}
