<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp"%>
<form id="pagerForm" method="post" action="${ctx}/purchasing/list">
    <input type="hidden" name="pageNum" value="${purchasings.number}" />
    <input type="hidden" name="numPerPage" value="${purchasings.size}" />
</form>


<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="${ctx}/purchasing/list" method="post">
        <div class="searchBar">

            <table class="searchContent">
                <tr>
                    <td>
                        订单编号<input type="text" name="searchMap['orderNumber']" value="${searchForm.searchMap['orderNumber']}" />
                    </td>

                    <td>
                        订单名称<input type="text" name="searchMap['orderName']" value="${searchForm.searchMap['orderName']}" />
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
        <sec:authorize ifAllGranted="role_planning">
        <li><a class="add" href="${ctx}/purchasing/add" target="navTab"><span>添加</span></a></li>
        <li><a class="delete" href="${ctx}/purchasing/delete/{purchasing_id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
        <li><a class="edit" href="${ctx}/purchasing/edit/{purchasing_id}" target="navTab"><span>修改</span></a></li>
        </sec:authorize>
        <li><a class="edit" href="${ctx}/purchasing/show/{purchasing_id}" target="navTab"><span>查看</span></a></li>
        <li class="line">line</li>
    </ul>
</div>
<table class="table" width="100%" layoutH="138">
<thead>
<tr>
    <th width="100">订单名称</th>
    <th width="100">订单编号</th>
    <th width="50">序列号</th>
    <th width="50">数量</th>
    <th width="50">面辅料供应时间</th>
    <th width="50">计划人</th>
    <th width="50">排料确认</th>
    <th width="50">日期</th>
    <th width="50">开始时间</th>
    <th width="50">结束时间</th>

</tr>
</thead>
<tbody>
<c:forEach items="${purchasings.content}" var="purchasing">
<tr target="purchasing_id" rel="${purchasing.id}">
        <td>${purchasing.orderName}</td>
        <td>${purchasing.orderNumber}</td>
        <td>${purchasing.serialNumber}</td>
        <td>${purchasing.orderCount}</td>
        <td>${purchasing.applyTime}</td>
        <td>${purchasing.planningUser.name}</td>
        <td>${purchasing.dischargeRecognition}</td>
        <td>${purchasing.confirmTime}</td>
        <td>${purchasing.startTime}</td>
        <td>${purchasing.endTime}</td>
</tr>
</c:forEach>
</tbody>
</table>
<div class="panelBar">
    <div class="pages">
        <span>显示</span>
        <select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
            <option <c:if test="${purchasings.size == 20}">selected</c:if> value="20" >20</option>
            <option  <c:if test="${purchasings.size == 50}">selected</c:if> value="50">50</option>
            <option  <c:if test="${purchasings.size == 100}">selected</c:if> value="100">100</option>
        </select>
        <span>条，共${purchasings.totalElements}条</span>
    </div>
    <div class="pagination" targetType="navTab" totalCount="${purchasings.totalElements}" numPerPage="${purchasings.size}" pageNumShown="10" currentPage="${purchasings.number +1 }"></div>

</div>
</div>


