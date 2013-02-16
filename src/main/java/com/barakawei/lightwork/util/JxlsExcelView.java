package com.barakawei.lightwork.util;

/**
 * Created with IntelliJ IDEA.
 * User: baraka
 * Date: 13-2-16
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */

import net.sf.jxls.transformer.XLSTransformer;

import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.LocalizedResourceHelper;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.AbstractView;

public class JxlsExcelView extends AbstractView {

    private static String content_type="application/vnd.ms-excel; charset=UTF-8";
    private static String extension=".xls";

    private String fileName;
    private String location;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public JxlsExcelView() {
        setContentType(content_type);
    }

    protected void renderMergedOutputModel(Map map, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        HSSFWorkbook workbook;
        LocalizedResourceHelper helper=new LocalizedResourceHelper(getApplicationContext());
        Locale userLocale=RequestContextUtils.getLocale(request);
        Resource inputFile=helper.findLocalizedResource(location, extension, userLocale);
        XLSTransformer transformer=new XLSTransformer();
        workbook=(HSSFWorkbook)transformer.transformXLS(inputFile.getInputStream(), map);
        response.setContentType(getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        ServletOutputStream out=response.getOutputStream();
        workbook.write(out);
        out.flush();
    }

}
