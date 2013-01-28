<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp" %>
<form action="${ctx}/purchasing/upload?navTabId=list" method="post" enctype="multipart/form-data" onsubmit="return iframeCallback(this, navTabAjaxDone)">
    <div class="pageContent">
        <div class="pageFormContent" layoutH="97">
            <input type="file" name="file">
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">导入</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button class="close" type="button">取消</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</form>