package com.ygy.study.iodemo.pdf;


import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class PdfUtil {

    static Logger logger = LoggerFactory.getLogger(PdfUtil.class);

    /**
     * 获取字体设置路径
     */
    public static String getFontPath() {
        String classpath = PdfUtil.class.getClassLoader().getResource("").getPath();
        String fontPath = classpath + "fonts";
        return fontPath;
    }

    /**
     * PDF文件生成
     */
    public static void convertToPDF(PdfWriter writer, Document document, String htmlString) {
        String fontPath = getFontPath();//获取字体路径
        document.open();
        try {
            XMLWorkerHelper.getInstance().parseXHtml(
                    writer,
                    document,
                    new ByteArrayInputStream(htmlString.getBytes()),
                    XMLWorkerHelper.class.getResourceAsStream("/default.css"),
                    Charset.forName("UTF-8"),
                    new XMLWorkerFontProvider(fontPath)
            );
        } catch (IOException e) {
            logger.error("convertToPDF err.", e);
            throw new RuntimeException("PDF文件生成异常", e);
        } finally {
            document.close();
        }
    }

    /**
     * 写入文件流，生成pdf文件
     *
     * @param fileName         写入的文件全路径
     * @param templateFileName 模板文件名
     * @param data
     * @return
     */
    public static String exportToFile(String fileName, String templateFileName, Object data) {
        String htmlData = FreeMarkerUtil.getContent(templateFileName, data);
        FileOutputStream outputStream = null;
        try {
            //设置输出路径
            outputStream = new FileOutputStream(fileName);
            //设置文档大小
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            //设置页眉页脚
            //输出为PDF文件
            convertToPDF(writer, document, htmlData);
        } catch (Exception e) {
            logger.error("exportToFile err.fileName:{}, templateFileName:{}", fileName, templateFileName, e);
            throw new RuntimeException("PDF export to File fail", e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
        return fileName;
    }

    /**
     * 写入HttpServletResponse流，用作下载
     *
     * @param response
     * @param templateFileName  模板文件名
     * @param data
     * @return
     */
    public static OutputStream exportToResponse(HttpServletResponse response, String templateFileName, Object data) {
        String htmlData = FreeMarkerUtil.getContent(templateFileName, data);
        try {
            OutputStream out = response.getOutputStream();
            //设置文档大小
            Document document = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(document, out);
            //设置页眉页脚
            //输出为PDF文件
            convertToPDF(writer, document, htmlData);
            return out;
        } catch (Exception e) {
            logger.error("exportToResponse err. templateFileName:{}", templateFileName, e);
            throw new RuntimeException("PDF export to response fail", e);
        }
    }

}
