<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp"%>
<form id="pagerForm" method="post" action="${ctx}/purchasing/list">
    <input type="hidden" name="pageNum" value="${purchasings.number}" />
    <input type="hidden" name="orderField" value="${searchForm.searchMap['orderField']}" />
    <input type="hidden" name="orderDirection" value="${searchForm.searchMap['orderDirection']}" />
    <input type="hidden" name="numPerPage" value="${purchasings.size}" />
</form>

<form id="downloadForm" name = "downloadForm" method="post" action="">
</form>
<script>
    $(".radio").change(function(){
        $("#searchForm").submit();
    })

</script>
<div class="pageHeader">
    <form id="searchForm" onsubmit="return navTabSearch(this);" action="${ctx}/purchasing/list" method="post">
        <div class="searchBar" style="float: left;">

            <table class="searchContent">
                <tr>
                    <td>
                        订单编号:<input type="text" name="searchMap['orderNumber']" value="${searchForm.searchMap['orderNumber']}" />
                    </td>

                    <td>
                        单位:<input type="text" name="searchMap['orderName']" value="${searchForm.searchMap['orderName']}" />
                    </td>
                    <td>
                        序列号:<input type="text" name="searchMap['serialNumber']" value="${searchForm.searchMap['serialNumber']}" />
                    </td>
                </tr>
                <tr>
                    <td>
                        订单状态:
                        <input type="radio" class="radio" name="searchMap['ongoing']" value="1" <c:if test="${searchForm.searchMap['ongoing'] == '1'}">checked='true'</c:if> />未完成
                        <input type="radio" class="radio" name="searchMap['ongoing']" value="0" <c:if test="${searchForm.searchMap['ongoing'] == '0'}">checked='true'</c:if> />已完成
                        <input type="radio" class="radio" name="searchMap['ongoing']" value="2" <c:if test="${searchForm.searchMap['ongoing'] == '2'}">checked='true'</c:if> />预下
                    </td>
                </tr>
            </table>


        </div>
        <div style="float:right;" class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
    </form>
</div>
<div class="pageContent">
<div class="panelBar">
    <ul class="toolBar">
        <li><a class="icon" href="javascript:void(0)" target="download"><span>导出EXCEL</span></a></li>
        <li><a class="edit" href="${ctx}/purchasing/show/{purchasing_id}" target="navTab" rel="pd"><span>货物详情</span></a></li>
        <sec:authorize ifAllGranted="role_planning">
            <li><a class="add" href="${ctx}/purchasing/add" target="navTab"><span>添加</span></a></li>
            <li><a class="icon" href="${ctx}/purchasing/upload" target="navTab" rel="upload"><span>从EXCEL导入</span></a></li>
            <li><a class="edit" href="${ctx}/purchasing/edit/{purchasing_id}" rel="edit" target="navTab"><span>修改</span></a></li>
            <li><a class="delete" href="${ctx}/purchasing/delete/{purchasing_id}" target="ajaxTodo" title="确定要删除吗?"><span>删除</span></a></li>
        </sec:authorize>
        <li class="line">line</li>
    </ul>
</div>
<div id="data">
<table class="table" layoutH="138">
<thead>
<tr>
    <th style="width: 50px" width='50' orderField="complete" >订单状态</th>
    <th style="width: 50px" width='50'orderField="cutGroup"  >裁剪组别</th>
    <th style="width: 36px" width='50' orderField="finshCut" >已裁完</th>
    <th style="width: 47px" width='50' orderField="productGroup">生产班组</th>
    <th style="width: 63px" width='70'>预下线日期</th>
    <th style="width: 150px" width='150' orderField="orderName">客户名称</th>
    <th style="width: 116px" width='125'>订单编号</th>
    <th style="width: 60px" width='60' orderField="serialNumber">序列号</th>
    <th style="width: 110px" width='110'>款号</th>
    <th style="width: 200px" width='200'>数量</th>
    <th style="width: 75px" width='75'>订单合同交期</th>
    <th style="width: 63px" width='70'>下单日期</th>
    <th style="width: 63px" width='70'>数据日期</th>
    <th style="width: 50px" width='50' orderField="finshZipper">拉链数据</th>
    <th style="width: 75px" width='75'>面料合同交期</th>
    <th style="width: 75px" width='75'>面料到位日期</th>
    <th style="width: 75px" width='75'>辅料到位日期</th>
    <th style="width: 70px" width='70'>上线日期</th>
    <th style="width: 70px" width='70'>入库日期</th>
    <th style="width:100px" width='100'>备注</th>

</tr>
</thead>
<tbody>
<c:forEach items="${purchasingList}" var="purchasing">
<tr target="purchasing_id" rel="${purchasing.id}"
    <c:if test="${purchasing.complete==0}">style="color: red"</c:if>
    <c:if test="${purchasing.complete==2}">style="color: blue"</c:if>
    <c:if test="${purchasing.complete==3}">style="color: #32cd32"</c:if>
    <c:if test="${purchasing.complete==4}">style="color: darkturquoise"</c:if>
    <c:if test="${purchasing.complete==5}">style="color: green"</c:if>
    <c:if test="${purchasing.complete==7 || purchasing.complete==8 || purchasing.complete==9}">style="color: #FFA500"</c:if>
    <c:if test="${purchasing.complete==10}">style="color: #9400d3"</c:if>
    <c:if test="${purchasing.complete==6}">style="color: #FFA500"</c:if>


        >

    <td style="width: 50px">
    <c:if test="${purchasing.complete==0}">
        逾期
    </c:if>
        <c:if test="${purchasing.complete==1}">
            待料
        </c:if>
        <c:if test="${purchasing.complete==2}">
            可裁剪
        </c:if>
        <c:if test="${purchasing.complete==3}">
            面辅料已齐
        </c:if>
        <c:if test="${purchasing.complete==4}">
            可生产
        </c:if>
        <c:if test="${purchasing.complete==5}">
            生产中
        </c:if>
        <c:if test="${purchasing.complete==7}">
            锁钉
        </c:if>
        <c:if test="${purchasing.complete==8}">
            水洗
        </c:if>
        <c:if test="${purchasing.complete==9}">
            后道包装
        </c:if>
        <c:if test="${purchasing.complete==10}">
            待发货
        </c:if>
        <c:if test="${purchasing.complete==11}">
            已完成
        </c:if>
    </td>
    <td style="width: 50px">${purchasing.cutGroup}</td>
    <td style="width: 36px">

        <c:if test="${purchasing.finshCut==1}">
           已裁完
        </c:if>
        <c:if test="${purchasing.finshCut!=1}">

        </c:if>

    </td>
    <td style="width: 47px">${purchasing.productGroup}</td>
    <td style="width: 63px"><fmt:formatDate value="${purchasing.planUnderlineDate}" pattern="yyyy-MM-dd"/></td>

        <td style="width: 150px">${purchasing.orderName}</td>
        <td style="width: 116px">${purchasing.orderNumber}</td>
        <td style="width: 60px">${purchasing.serialNumber}</td>
        <td style="width: 110px">${purchasing.typeNumber}</td>
        <td style="width: 200px">${purchasing.orderCount}</td>
        <td style="width: 75px">
            <fmt:formatDate value="${purchasing.applyTime}" pattern="yyyy-MM-dd"/>
        </td>
        <td style="width: 63px"> <fmt:formatDate value="${purchasing.startTime}" pattern="yyyy-MM-dd"/></td>
        <td style="width: 63px"> <fmt:formatDate value="${purchasing.dataDate}" pattern="yyyy-MM-dd"/></td>
    <td style="width: 50px">

        <c:if test="${purchasing.finshZipper==1}">
            已填完
        </c:if>
        <c:if test="${purchasing.finshZipper!=1}">
            ${purchasing.zipperData}
        </c:if>

    </td>
        <td style="width: 75px"> <fmt:formatDate value="${purchasing.contractDate}" pattern="yyyy-MM-dd"/></td>
        <td style="width: 75px">  <fmt:formatDate value="${purchasing.mianDate}" pattern="yyyy-MM-dd"/></td>
        <td style="width: 75px"> <fmt:formatDate value="${purchasing.fuDate}" pattern="yyyy-MM-dd"/></td>
        <td style="width: 70px">
            <fmt:formatDate value="${purchasing.lineDate}" pattern="yyyy-MM-dd"/>
        </td>
    <td style="width: 70px"> <fmt:formatDate value="${purchasing.finshDate}" pattern="yyyy-MM-dd"/></td>
    <td style="width: 100px">${purchasing.remark}</td>

</tr>
</c:forEach>
</tbody>
</table>
    </div>
<div class="panelBar">
    <div class="pages">
        <span>显示</span>
        <select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
            <option <c:if test="${purchasings.size == 500}">selected</c:if> value="500" >500</option>
            <option  <c:if test="${purchasings.size == 1000}">selected</c:if> value="1000">1000</option>
            <option  <c:if test="${purchasings.size == 1500}">selected</c:if> value="1500">1500</option>
        </select>
        <span>条，共${purchasings.totalElements}条</span>
    </div>
    <div class="pagination" targetType="navTab" totalCount="${purchasings.totalElements}" numPerPage="${purchasings.size}" pageNumShown="10" currentPage="${purchasings.number +1 }"></div>

</div>
</div>


