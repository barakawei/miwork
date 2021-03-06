package com.barakawei.lightwork.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: baraka
 * Date: 13-2-16
 * Time: 上午9:49
 * To change this template use File | Settings | File Templates.
 */
public class DownloadHelper {


    private boolean forOpen = false;

    private boolean clearFile = false;

    private Map<String,String> headers = new HashMap<String, String>();

    private File file;

    private String content;

    private Charset charset;



    public DownloadHelper() {
        super();
        setContentType(null);
    }

    public boolean isForOpen() {
        return forOpen;
    }

    public boolean isClearFile() {
        return clearFile;
    }
    /**
     * 设置是否清理文件
     * @param clearFile
     */
    public void setClearFile(boolean clearFile) {
        this.clearFile = clearFile;
    }

    /**
     * 如果下载对象，浏览器默认能打开的话，可以设置此属性为true在当前浏览器中打开
     * @param forOpen
     */
    public void setForOpen(boolean forOpen) {
        this.forOpen = forOpen;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public File getFile() {
        return file;
    }

    public String getContent() {
        return content;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setContent(String content,Charset charset){
        setCharset(charset);
        setContentLength(content.getBytes(charset).length);
        this.content = content;
    }

    /**
     * 设置要下载的文件
     * @param file
     */
    public void setFile(File file) {
        this.file = file;
        if (file != null) {
            setFileName(file.getName());
            setContentLength(file.length());
        }
    }

    /**
     * 设置要下载的文件名，界面上弹出框的提示,此方法必须在setFile方法之后调用，如果不调用使用默认setFile方法参数的文件名
     * @param fileName
     */
    public void setFileName(String fileName){
        fileName = fileName==null&&getFile()!=null ? getFile().getName() : fileName;
        if(fileName==null)return;
        StringBuilder sb = new StringBuilder();
        if(forOpen){
            sb.append("inline;");
        }else{
            sb.append("attachment;");
        }
        if(!fileName.trim().equals("")){
            sb.append("filename=\"");
            try {
                sb.append(new String(fileName.getBytes("UTF-8"),"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append("downloadfile");
            }
            sb.append("\"");
        }
        headers.put("Content-Disposition", sb.toString());
    }

    /**
     * 设置contentType响应头信息,不指定时默认为application/octet-stream，也必须在setFile方法之后调用
     * @param contentType
     */
    public void setContentType(String contentType){
        StringBuilder sb = new StringBuilder();
        contentType = null == contentType ? "application/octet-stream"
                : contentType;
        sb.append(contentType);
        sb.append(";");
        headers.put("Content-Type", sb.toString());

    }
    /**
     * 设置下载文件大小
     * @param size
     */
    public void setContentLength(long size){
        headers.put("Content-Length", Long.toString(size));
    }
    /**
     * 设置文件编码
     * @param charset
     */
    public void setCharset(Charset charset){
        this.charset = charset;
        String contentType = headers.get("Content-Type");
        contentType += "charset="+charset.name()+";";
        headers.put("Content-Type", contentType);
    }
}
