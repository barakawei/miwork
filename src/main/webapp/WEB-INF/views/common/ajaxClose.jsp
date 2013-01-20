<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="taglib.jsp"%>
{
	"statusCode":"${statusCode}", 
	"message":"${message}", 
	"navTabId":"${param.navTabId}", 
	"rel":"${param.rel}",
	"callbackType":"closeCurrent",
	"forwardUrl":"${param.forwardUrl}"
}