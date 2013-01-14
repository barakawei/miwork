<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp"%>
<h2 class="contentTitle">采购计划</h2>
<form action="${ctx}/purchasing/update?navTabId=list" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone)">
    <input type="hidden" name="id" value="${purchasing.id}">
    <c:forEach items="${purchasing.pds}" var="pd" varStatus="status">
        <input type="hidden" name="pds[${status.index}].goods.id" value="${pd.goods.id}">
        <input type="hidden" name="pds[${status.index}].id" value="${pd.id}">
        <input type="hidden" name="pds[${status.index}].purchasing.id" value="${purchasing.id}">
    </c:forEach>
    <div class="pageContent">
        <div class="pageFormContent" layoutH="97">

            <dl >
                <dt>订单名称</dt>
                <dd>
                    <input type="text" name="orderName" maxlength="50" class="required" value="${purchasing.orderName}" />
                </dd>
            </dl>
            <dl >
                <dt>订单编号</dt>
                <dd>
                    <input type="text" name="orderNumber" maxlength="50" class="required" value="${purchasing.orderNumber}"/>
                </dd>
            </dl>
            <dl >
                <dt>序列号</dt>
                <dd>
                    <input type="text" name="serialNumber" maxlength="50" class="required" value="${purchasing.serialNumber}" />
                </dd>
            </dl>

            <dl >
                <dt>数量</dt>
                <dd>
                    <input type="text" name="orderCount" maxlength="50" class="required" value="${purchasing.orderCount}" />
                </dd>
            </dl>

            <dl >
                <dt>面辅料供应时间</dt>
                <dd>

                    <input type="text" name="applyTime" class="date" dateFmt="yyyy-MM-dd HH:mm" readonly="true" value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${purchasing.applyTime}' type='both'/>">
                    <a class="inputDateButton" href="javascript:;">选择</a>
                </dd>
            </dl>

            <dl>
                <dt>计划人</dt>
                <dd>
                    aaaa
                </dd>
            </dl>

            <dl>
                <dt>排料确认</dt>
                <dd>
                    aaaa
                </dd>
            </dl>

            <dl>
                <dt>日期</dt>
                <dd>
                    <input type="text" name="confirmTime" class="date" dateFmt="yyyy-MM-dd HH:mm" readonly="true" value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${purchasing.confirmTime}' type='both'/>">
                    <a class="inputDateButton" href="javascript:;">选择</a>
                </dd>
            </dl>

            <div class="divider"></div>
            <h3 class="contentTitle">采购明细</h3>

            <table class="table" width="100%" layoutH="138">
                <thead>
                <tr>
                    <th width="100">名称</th>
                    <th width="100">类型</th>
                    <th width="50">型号规格</th>
                    <th width="50">面辅料成分</th>
                    <th width="50">净宽/CM</th>
                    <th width="50">单量</th>
                    <th width="50">颜色</th>
                    <th width="50">单位</th>
                    <th width="50">采购计划</th>
                    <th width="50">库存</th>
                    <th width="50">实需采购</th>
                    <th width="50">面辅料特殊要求</th>

                </tr>
                </thead>
                <tbody>
                <c:forEach items="${purchasing.pds}" var="pd" varStatus="status">
                    <tr class="unitBox" target="pd_id" rel="${pd.id}">
                        <td><input type="text" class="required" name="pds[${status.index}].goods.name" size="20" value="${pd.goods.name}"></td>
                        <td><input type="text" class="required" name="pds[${status.index}].goods.type" size="12" value="${pd.goods.type}"></td>
                        <td><input type="text" name="pds[${status.index}].goods.specification" size="20" value="${pd.goods.specification}"></td>
                        <td><input type="text" name="pds[${status.index}].goods.composition" size="8" value="${pd.goods.composition}"></td>
                        <td><input type="text" class="digits" name="pds[${status.index}].goods.width" size="6" value="${pd.goods.width}"></td>
                        <td><input type="text" class="digits" name="pds[${status.index}].orderCount" size="6" value="${pd.orderCount}"></td>
                        <td><input type="text"  name="pds[${status.index}].goods.color" size="6" value="${pd.goods.color}"></td>
                        <td><input type="text"  class="required" name="pds[${status.index}].goods.unit" size="3" value="${pd.goods.unit}"></td>
                        <td><input type="text" class="number required" name="pds[${status.index}].planPurchasingCount" size="8" value="${pd.planPurchasingCount}"></td>
                        <td><input type="text" class="number" name="pds[${status.index}].warehouseCount" size="8" value="${pd.warehouseCount}"></td>
                        <td><input type="text" class="number required" name="pds[${status.index}].actualPurchasingCount" size="8" value="${pd.actualPurchasingCount}"></td>
                        <td><input type="text" name="pds[${status.index}].specialRequirements" size="20" value="${pd.specialRequirements}"></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li><div class="button"><div class="buttonContent"><button class="close" type="button">关闭</button></div></div></li>
            </ul>
        </div>
    </div>
</form>
