<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp" %>
<h2 class="contentTitle">采购计划</h2>

<form action="${ctx}/purchasing/completePurchasing?navTabId=list" method="post" class="pageForm required-validate"
      onsubmit="return validateCallback(this, navTabAjaxDone)">
    <input type="hidden" name="id" value="${purchasing.id}">

    <div class="pageContent">
        <div class="pageFormContent"  layoutH="97">
            <div class="panel collapse" style="margin:0">
                <h1>内容</h1>

                <div>
                    <dl>
                        <dt>订单名称</dt>
                        <dd>
                            ${purchasing.orderName}
                        </dd>
                    </dl>
                    <dl>
                        <dt>订单编号</dt>
                        <dd>
                            ${purchasing.orderNumber}
                        </dd>
                    </dl>
                    <dl>
                        <dt>序列号</dt>
                        <dd>
                            ${purchasing.serialNumber}
                        </dd>
                    </dl>

                    <dl>
                        <dt>数量</dt>
                        <dd>
                            ${purchasing.orderCount}
                        </dd>
                    </dl>

                    <dl>
                        <dt>面辅料供应时间</dt>
                        <dd>

                            <fmt:formatDate value="${purchasing.applyTime}" pattern="yyyy-MM-dd HH:mm"/>

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
                            <fmt:formatDate value="${purchasing.confirmTime}" pattern="yyyy-MM-dd HH:mm"/>

                        </dd>
                    </dl>
                </div>
            </div>

            <div class="divider"></div>

            <div class="tabs">
                <div class="tabsHeader">
                    <div class="tabsHeaderContent">
                        <ul>
                            <li class="selected"><a href="javascript:void(0)"><span>货物</span></a></li>
                            <li class="selected"><a href="javascript:void(0)"><span>拉链</span></a></li>
                        </ul>
                    </div>
                </div>
                <div class="tabsContent" style="height: 400px;">

                    <div>
                        <table class="list nowrap" width="100%" >
                            <thead>
                            <tr>
                                <th colspan="2">流程信息</th>
                                <th colspan="2">实际入库信息</th>
                                <sec:authorize ifAnyGranted="role_leader,role_quality">
                                <th colspan="1">审批信息</th>
                                </sec:authorize>
                                <th colspan="3">质检信息</th>
                                <th colspan="2">预入库信息</th>
                                <th colspan="1">采购信息</th>
                                <th colspan="12">货物信息</th>
                            </tr>
                            <tr>
                                <th>当前节点</th>
                                <th>当前处理人</th>
                                <th>实际库数量</th>
                                <th>实际入库时间</th>
                                <sec:authorize ifAnyGranted="role_leader,role_quality">
                                <th>审批结果</th>
                                </sec:authorize>
                                <th>质检结果</th>
                                <th>缩率</th>
                                <th>质检信息</th>
                                <th>预入库数量</th>
                                <th>预入库时间</th>
                                <th>预计到货时间</th>
                                <th>名称</th>
                                <th>类型</th>
                                <th>型号规格</th>
                                <th>面辅料成分</th>
                                <th>净宽/CM</th>
                                <th>单量</th>
                                <th>颜色</th>
                                <th>单位</th>
                                <th>采购计划</th>
                                <th>库存</th>
                                <th>实需采购</th>
                                <th>面辅料特殊要求</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${purchasing.pds}" var="pd" varStatus="status">
                                <tr class="unitBox">
                                    <!-- 当前节点-->
                                    <td>${pd.task.name == null ?"结束":pd.task.name}</td>
                                    <!-- 当前处理人-->
                                    <td>${pd.currentUserName}</td>
                                    <!-- 实际入库数量-->
                                    <td>
                                    <c:if test="${pd.task.taskDefinitionKey == 'warehouseEntryActual'}">
                                        <sec:authorize ifAllGranted="role_warehouse">
                                            <input type="text" class="number "
                                                   name="pds[${status.index}].actualEntryCount" size="8"
                                                   value="${pd.actualEntryCount}">
                                        </sec:authorize>
                                    </c:if>
                                    <c:if test="${pd.task.taskDefinitionKey != 'warehouseEntryActual'}">
                                        ${pd.actualEntryCount}
                                    </c:if>
                                    </td>
                                    <!-- 实际入库时间-->
                                    <td>
                                        <c:if test="${pd.task.taskDefinitionKey == 'warehouseEntryActual'}">
                                            <sec:authorize ifAllGranted="role_warehouse">
                                                <input type="text" name="pds[${status.index}].actualEntryTime"
                                                       value="<fmt:formatDate value="${pd.actualEntryTime}" pattern="yyyy-MM-dd HH:mm"/>"
                                                       size="19" class="date" dateFmt="yyyy-MM-dd HH:mm" readonly="true">
                                                <a class="inputDateButton" href="javascript:;">选择</a>
                                            </sec:authorize>
                                        </c:if>
                                        <c:if test="${pd.task.taskDefinitionKey != 'warehouseEntryActual'}">
                                            <fmt:formatDate value="${pd.actualEntryTime}" pattern="yyyy-MM-dd HH:mm"/>
                                        </c:if>

                                    </td>
                                    <!-- 审批结果-->
                                    <sec:authorize ifAnyGranted="role_leader,role_quality">
                                    <td>
                                        <c:if test="${pd.task.taskDefinitionKey == 'leaderAudit'}">
                                            <sec:authorize ifAllGranted="role_leader">
                                            <textarea name="pds[${status.index}].reason" cols="20" class="required" rows="2"></textarea>
                                            </sec:authorize>
                                        </c:if>

                                        <c:if test="${pd.task.taskDefinitionKey != 'leaderAudit'}">
                                            ${pd.reason}
                                        </c:if>

                                    </td>
                                    </sec:authorize>
                                    <!-- 质检结果-->
                                    <td>
                                        <c:if test="${pd.task.taskDefinitionKey == 'quality'}">
                                        <sec:authorize ifAllGranted="role_quality">
                                        <select name="pds[${status.index}].qualified" class="combox">
                                            <option value="">请选择</option>
                                            <option value="true">合格</option>
                                            <option value="false">不合格</option>
                                        </select>
                                        </sec:authorize>
                                        </c:if>

                                        <c:if test="${pd.task.taskDefinitionKey != 'quality'}">
                                            <c:if test="${pd.qualified == true}">
                                                合格
                                            </c:if>
                                            <c:if test="${pd.qualified == false}">
                                                不合格
                                            </c:if>
                                        </c:if>

                                    </td>
                                    <!-- 缩率-->
                                    <td>
                                        <c:if test="${pd.task.taskDefinitionKey == 'quality'}">
                                            <sec:authorize ifAllGranted="role_quality">
                                                <input type="text" class="number"
                                                       name="pds[${status.index}].shrinkage" size="8"
                                                       value="${pd.shrinkage}">
                                            </sec:authorize>
                                        </c:if>
                                        <c:if test="${pd.task.taskDefinitionKey != 'quality'}">
                                            ${pd.shrinkage}
                                        </c:if>
                                    </td>

                                    <!-- 质检信息-->
                                    <td>
                                        <c:if test="${pd.task.taskDefinitionKey == 'quality'}">
                                            <sec:authorize ifAllGranted="role_quality">
                                                <textarea name="pds[${status.index}].qualityRemark" cols="20" rows="2"></textarea>
                                            </sec:authorize>
                                        </c:if>
                                        <c:if test="${pd.task.taskDefinitionKey != 'quality'}">
                                            ${pd.qualityRemark}
                                        </c:if>
                                    </td>

                                    <!-- 预入库数量-->
                                    <td>
                                        <c:if test="${pd.task.taskDefinitionKey == 'warehouseEntryPlan'}">
                                            <sec:authorize ifAllGranted="role_warehouse">
                                                <input type="text" class="number "
                                                       name="pds[${status.index}].planEntryCount" size="8"
                                                       value="${pd.planEntryCount}">
                                            </sec:authorize>
                                        </c:if>
                                        <c:if test="${pd.task.taskDefinitionKey != 'warehouseEntryPlan'}">
                                            ${pd.planEntryCount}
                                        </c:if>

                                    </td>
                                    <!-- 预入库时间-->
                                    <td>
                                        <c:if test="${pd.task.taskDefinitionKey == 'warehouseEntryPlan'}">
                                            <sec:authorize ifAllGranted="role_warehouse">
                                                <input type="text" name="pds[${status.index}].planEntryTime"
                                                       value="<fmt:formatDate value="${pd.planEntryTime}" pattern="yyyy-MM-dd HH:mm"/>"
                                                       size="19"  class="date" dateFmt="yyyy-MM-dd HH:mm" readonly="true">
                                                <a class="inputDateButton" href="javascript:;">选择</a>
                                            </sec:authorize>
                                        </c:if>
                                        <c:if test="${pd.task.taskDefinitionKey != 'warehouseEntryPlan'}">
                                            <fmt:formatDate value="${pd.planEntryTime}" pattern="yyyy-MM-dd HH:mm"/>
                                        </c:if>

                                    </td>
                                    <!-- 预计到货时间-->
                                    <td>
                                        <c:if test="${pd.task.taskDefinitionKey == 'purchasing'}">
                                        <sec:authorize ifAllGranted="role_purchasing">
                                        <input type="text" name="pds[${status.index}].expectedArrivalTime"
                                               value="<fmt:formatDate value="${pd.expectedArrivalTime}" pattern="yyyy-MM-dd HH:mm"/>"
                                               size="19" class="date" dateFmt="yyyy-MM-dd HH:mm" readonly="true">
                                        <a class="inputDateButton" href="javascript:;">选择</a>
                                        </sec:authorize>
                                        </c:if>
                                        <c:if test="${pd.task.taskDefinitionKey != 'purchasing'}">
                                            <fmt:formatDate value="${pd.expectedArrivalTime}" pattern="yyyy-MM-dd HH:mm"/>
                                        </c:if>

                                    </td>
                                    <!-- 货物基本信息-->
                                    <td>${pd.goods.name}</td>
                                    <td>${pd.goods.type}</td>
                                    <td>${pd.goods.specification}</td>
                                    <td>${pd.goods.composition}</td>
                                    <td>${pd.goods.width}</td>
                                    <td>${pd.orderCount}</td>
                                    <td>${pd.goods.color}</td>
                                    <td>${pd.goods.unit}</td>
                                    <td>${pd.planPurchasingCount}</td>
                                    <td>${pd.warehouseCount}</td>
                                    <td>${pd.actualPurchasingCount}</td>
                                    <td>${pd.specialRequirements}</td>
                                    <td style="display: none">
                                        <input type="hidden" name="pds[${status.index}].id" value="${pd.id}">
                                        <input type="hidden" name="pds[${status.index}].taskId" value="${pd.taskId}">
                                        <input type="hidden" name="pds[${status.index}].purchasing.id"
                                               value="${purchasing.id}">
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>


                    <div>
                        <!-- 拉链信息-->
                        <sec:authorize ifAllGranted="role_technolog">
                        <table class="list nowrap itemDetail" addButton="新建条目" width="100%">
                            <thead>
                            <tr>
                                <th type="suggest" name="zippers[#index#].position" lookupPk ="value" postField ="value" lookupGroup="zippers[#index#]" fieldClass="required" suggestUrl="${ctx}/dataDict/position" suggestFields="value" size="20">部位</th>
                                <th type="suggest" name="zippers[#index#].name" lookupPk ="value" postField ="value" lookupGroup="zippers[#index#]" fieldClass="required" suggestUrl="${ctx}/dataDict/name" suggestFields="value" size="20">名称</th>
                                <th type="suggest" name="zippers[#index#].material" lookupPk ="value" postField ="value" lookupGroup="zippers[#index#]" fieldClass="required" suggestUrl="${ctx}/dataDict/material" suggestFields="value" size="20">材质</th>
                                <th type="suggest" name="zippers[#index#].spec" lookupPk ="value" postField ="value" lookupGroup="zippers[#index#]" fieldClass="required" suggestUrl="${ctx}/dataDict/spec" suggestFields="value" size="20">规格</th>
                                <th type="suggest" name="zippers[#index#].model" lookupPk ="value" postField ="value" lookupGroup="zippers[#index#]" fieldClass="required" suggestUrl="${ctx}/dataDict/model" suggestFields="value" size="20">型号</th>
                                <th type="text" name="zippers[#index#].size"  fieldClass="required"  size="20">尺寸</th>
                                <c:if test="${purchasing.zippers.size() > 0}">
                                <th>数量</th>
                                </c:if>
                                <th type="del" width="60">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${purchasing.zippers}" var="z" varStatus="status">
                            <tr class="unitBox">
                                <td><input type="text" class="required" name="zippers[${status.index}].position" size="20" value="${z.position}"></td>
                                <td><input type="text" class="required" name="zippers[${status.index}].name" size="20" value="${z.name}"></td>
                                <td><input type="text" class="required" name="zippers[${status.index}].material" size="20" value="${z.material}"></td>
                                <td><input type="text" class="required" name="zippers[${status.index}].spec" size="20" value="${z.spec}"></td>
                                <td><input type="text" class="required" name="zippers[${status.index}].model" size="20" value="${z.model}"></td>
                                <td><input type="text" class="required" name="zippers[${status.index}].size" size="20" value="${z.size}"></td>
                                <td>111${z.number}</td>
                                <td style="display: none">
                                    <input type="hidden" name="zippers[${status.index}].number" value="${z.number}">
                                    <input type="hidden" name="zippers[${status.index}].id" value="${z.id}">
                                    <input type="hidden" name="zippers[${status.index}].purchasing.id" value="${purchasing.id}">
                                </td>
                            </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div>
                            <p>
                            <label>面料实际缩率</label>
                            <input type="text" size="10" class="required" name="actualShrinkage" value="${purchasing.actualShrinkage}"/>
                            </p>
                            <p>
                            <label>拉链已含缩率</label>
                            <input type="text" size="10" class="required" name="zipperShrinkage" value="${purchasing.zipperShrinkage}"/>
                            </p>
                        </div>
                            </sec:authorize>
                        <sec:authorize ifNotGranted="role_technolog">
                        <table class="list nowrap " width="100%">
                            <thead>
                            <tr>
                                <th>部位</th>
                                <th>名称</th>
                                <th>材质</th>
                                <th>规格</th>
                                <th>型号</th>
                                <th>尺寸</th>
                                <th>数量</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${purchasing.zippers}" var="z" varStatus="status">
                            <tr class="unitBox">
                                <td>${z.position}</td>
                                <td>${z.name}</td>
                                <td>${z.material}</td>
                                <td>${z.spec}</td>
                                <td>${z.model}</td>
                                <td>${z.size}</td>
                                <sec:authorize ifAllGranted="role_product">
                                <td><input type="text" class="required number" name="zippers[${status.index}].number" size="20" value="${z.number}"></td>
                                <td style="display: none">
                                    <input type="hidden" name="zippers[${status.index}].id"
                                           value="${z.id}">
                                    <input type="hidden" name="zippers[${status.index}].purchasing.id"
                                           value="${purchasing.id}">
                                </td>
                                </sec:authorize>
                                <sec:authorize ifNotGranted="role_product">
                                    <td>${z.number}</td>
                                </sec:authorize>
                            </tr>
                           </c:forEach>
                            </tbody>
                        </table>
                        </sec:authorize>
                    </div>



                </div>
                <div class="tabsFooter">
                    <div class="tabsFooterContent"></div>
                </div>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <sec:authorize ifNotGranted="role_planning">
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">完成</button>
                        </div>
                    </div>
                </li>
                </sec:authorize>
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
