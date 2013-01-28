package com.barakawei.lightwork.util;

import com.barakawei.lightwork.domain.Goods;
import nl.bstoi.poiparser.core.ReadPoiParserException;
import nl.bstoi.poiparser.core.RequiredFieldPoiParserException;
import nl.bstoi.poiparser.core.strategy.annotation.AnnotatedPoiFileParser;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PurchasingExcelParseUtil {

    public static List<Goods> upload(MultipartFile file) {
        Assert.notNull(file, "error_imageFile_null");
        Assert.isTrue(!file.isEmpty(), "error_imageFile_null");
        File tempFile = null;
        try {
            tempFile = File.createTempFile("tempfile", "");
            file.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        List<Goods> rows= null;
        AnnotatedPoiFileParser<Goods> annotationExcelParser = new AnnotatedPoiFileParser<Goods>();
        try {
            rows = annotationExcelParser.readExcelFile(tempFile, "Sheet1 - 1", Goods.class);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (RequiredFieldPoiParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ReadPoiParserException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return rows;
    }
}
