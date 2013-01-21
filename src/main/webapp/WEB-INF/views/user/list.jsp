<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp"%>
<form id="pagerForm" method="post" action="${ctx}/user/list">
    <input type="hidden" name="pageNum" value="${users.number}" />
    <input type="hidden" name="numPerPage" value="${users.size}" />
</form>


<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="${ctx}/user/list" method="post">
        <div class="searchBar">

            <table class="searchContent">
                <tr>
                    <td>
                        姓名<input type="text" name="searchMap['name']" value="${searchForm.searchMap['name']}" />
                    </td>

                </tr>
            </table>
            <div class="subBar">
                <ul>
                    <li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
                </ul>
            </div>
        </div>
    </form>
</div>
<div class="pageContent">
<div class="panelBar">
    <ul class="toolBar">
        <sec:authorize ifAllGranted="role_admin">
        <li><a class="add" href="${ctx}/user/add" target="navTab"><span>添加</span></a></li>
        <li><a class="delete" href="${ctx}/user/delete/{user_id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
        <li><a class="edit" href="${ctx}/user/edit/{user_id}" target="navTab"><span>修改</span></a></li>
        </sec:authorize>
    </ul>
</div>
<table class="table" width="100%" layoutH="138">
<thead>
<tr>
    <th width="100">账号</th>
    <th width="100">姓名</th>
    <th width="50">角色</th>
    <th width="50">邮箱</th>
    <th width="50">电话</th>
    <th width="50">手机</th>

</tr>
</thead>
<tbody>
<c:forEach items="${users.content}" var="user">
<tr target="user_id" rel="${user.id}">
        <td>${user.account}</td>
        <td>${user.name}</td>
        <td>${user.roles[0].description}</td>
        <td>${user.email}</td>
        <td>${user.telephone}</td>
        <td>${user.phone}</td>
</tr>
</c:forEach>
</tbody>
</table>
<div class="panelBar">
    <div class="pages">
        <span>显示</span>
        <select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
            <option <c:if test="${users.size == 20}">selected</c:if> value="20" >20</option>
            <option  <c:if test="${users.size == 50}">selected</c:if> value="50">50</option>
            <option  <c:if test="${users.size == 100}">selected</c:if> value="100">100</option>
        </select>
        <span>条，共${users.totalElements}条</span>
    </div>
    <div class="pagination" targetType="navTab" totalCount="${users.totalElements}" numPerPage="${users.size}" pageNumShown="10" currentPage="${users.number +1 }"></div>

</div>
</div>


