package com.ygy.study.iodemo.pdf;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

public class FreeMarkerUtil {

    static Logger logger = LoggerFactory.getLogger(FreeMarkerUtil.class);

    private static Configuration config;
    private static final String UTF_8 = "UTF-8";

    static {

        String classpath = FreeMarkerUtil.class.getClassLoader().getResource("").getPath();
        String templatePath = classpath + "/templates";
        File file = new File(templatePath);
        if (!file.isDirectory()) {
            logger.error("FreeMarkerUtil static err. templates dir not exist. templatePath:", templatePath);
            throw new RuntimeException("PDF模板文件不存在,请检查templates文件夹!");
        }

        config = new Configuration(Configuration.VERSION_2_3_25);
        config.setDefaultEncoding(UTF_8);
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        config.setLogTemplateExceptions(false);
        FileTemplateLoader fileTemplateLoader;
        try {
            fileTemplateLoader = new FileTemplateLoader(file);
        } catch (IOException e) {
            logger.error("FreeMarkerUtil init err.", e);
            throw new RuntimeException("FreeMarkerUtil fileTemplateLoader init error!",e);
        }
        config.setTemplateLoader(fileTemplateLoader);
    }


    /**
     * 将数据填充到模板并返回
     * @param templateFileName 模板文件名
     * @param data 填充的数据
     * @return
     */
    public static String getContent(String templateFileName, Object data) {
        try {
            Template template = config.getTemplate(templateFileName);
            StringWriter writer = new StringWriter();
            template.process(data, writer);
            writer.flush();
            return  writer.toString();
        } catch (Exception e) {
            logger.error("FreeMarkerUtil getContent err. templateFileName:{}", templateFileName, e);
            throw new RuntimeException("FreeMarkerUtil getContent err. templateFileName=" + templateFileName);
        }
    }

}
