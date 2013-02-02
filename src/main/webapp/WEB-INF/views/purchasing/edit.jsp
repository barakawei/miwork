<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp" %>
<h2 class="contentTitle">采购计划</h2>

<form action="${ctx}/purchasing/update?navTabId=list" method="post" class="pageForm required-validate"
      onsubmit="return validateCallback(this, navTabAjaxDone)">
    <input type="hidden" name="id" value="${purchasing.id}">

    <div class="pageContent pds" id="data">
        <div class="pageFormContent" layoutH="97">
            <div class="panel collapse" style="margin:0">
                <h1>订单信息</h1>

                <div>
                    <dl>
                        <dt>单位</dt>
                        <dd>
                            <input type="text" name="orderName" maxlength="50" size="40" class=""
                                   value="${purchasing.orderName}"/>
                        </dd>
                    </dl>
                    <dl>
                        <dt>订单编号</dt>
                        <dd>
                            <input type="text" name="orderNumber" maxlength="50" size="40" class=""
                                   value="${purchasing.orderNumber}"/>
                        </dd>
                    </dl>
                    <dl>
                        <dt>序列号</dt>
                        <dd>
                            <input type="text" name="serialNumber" maxlength="50" size="40" class=""
                                   value="${purchasing.serialNumber}"/>
                        </dd>
                    </dl>

                    <dl>
                        <dt>款号</dt>
                        <dd>
                            <input type="text" name="typeNumber" maxlength="50" size="40" class=""
                                   value="${purchasing.typeNumber}"/>
                        </dd>
                    </dl>

                    <dl>
                        <dt>数量</dt>
                        <dd>
                            <input type="text" name="orderCount" maxlength="100" size="40" class=""
                                   value="${purchasing.orderCount}"/>
                        </dd>
                    </dl>

                    <dl>
                        <dt>面辅料供应时间</dt>
                        <dd>

                            <input type="text" name="applyTime" class="date" dateFmt="yyyy-MM-dd HH:mm" readonly="true"
                                   value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${purchasing.applyTime}' type='both'/>">
                            <a class="inputDateButton" href="javascript:;">选择</a>
                        </dd>
                    </dl>

                    <dl>
                        <dt>计划人</dt>
                        <dd>
                            <input type="text" name="planningUserName" maxlength="100" size="40" class=""
                                   value="${purchasing.planningUserName}"/>
                        </dd>
                    </dl>

                    <dl>
                        <dt>采购预排</dt>
                        <dd>
                            <input type="text" name="planDischarge" maxlength="100" size="40" class=""
                                   value="${purchasing.planDischarge}"/>
                        </dd>
                    </dl>

                    <dl>
                        <dt>计划日期</dt>
                        <dd>
                            <input type="text" name="planDate" class="date" dateFmt="yyyy-MM-dd HH:mm"
                                   readonly="true"
                                   value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${purchasing.planDate}' type='both'/>">
                            <a class="inputDateButton" href="javascript:;">选择</a>
                        </dd>
                    </dl>


                    <dl>
                        <dt>排料确认</dt>
                        <dd>
                            <input type="text" name="dischargeRecognition" size="40" maxlength="50" value="${purchasing.dischargeRecognition}"/>
                        </dd>
                    </dl>

                    <dl>
                        <dt>核准</dt>
                        <dd>
                            <input type="text" name="confirmName" size="40" maxlength="50" value="${purchasing.confirmName}"/>
                        </dd>
                    </dl>

                    <dl>
                        <dt>确认日期</dt>
                        <dd>
                            <input type="text" name="confirmDate" class="date" dateFmt="yyyy-MM-dd HH:mm"
                                   readonly="true"
                                   value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${purchasing.confirmDate}' type='both'/>">
                            <a class="inputDateButton" href="javascript:;">选择</a>
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
                        </ul>
                    </div>
                </div>
                <div class="tabsContent" style="height: 400px;">
                    <div>
                        <table class="list nowrap itemDetail" addButton="新建条目" width="100%">
                            <thead>
                            <tr>
                                <th type="text" name="pds[#index#].goods.name" size="20" fieldClass="">名称</th>
                                <th type="text" name="pds[#index#].goods.type" size="12" fieldClass="">类型</th>
                                <th type="text" name="pds[#index#].goods.specification" size="20">型号规格</th>
                                <th type="text" name="pds[#index#].goods.composition" size="8">面辅料成分</th>
                                <th type="text" name="pds[#index#].goods.width" size="6" defaultVal="0"
                                    fieldClass="digits">净宽/CM
                                </th>
                                <th type="text" name="pds[#index#].orderCount" size="6" defaultVal="0"
                                    fieldClass="digits">单量
                                </th>
                                <th type="text" name="pds[#index#].goods.color" size="6">颜色</th>
                                <th type="text" name="pds[#index#].goods.consume" size="6">预排单耗</th>
                                <th type="text" name="pds[#index#].goods.unit" size="3" fieldClass="">单位</th>
                                <th type="text" name="pds[#index#].goods.loss" size="6">损耗1*%</th>
                                <th type="text" name="pds[#index#].specialRequirements" size="20" fieldClass="">
                                    面辅料特殊要求
                                </th>
                                <th type="text" name="pds[#index#].planPurchasingCount" defaultVal="0.0" size="8"
                                    fieldClass="number ">采购计划
                                </th>

                                <th type="del" width="60">操作</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${purchasing.pds}" var="pd" varStatus="status">
                                <tr class="unitBox">
                                    <td><input type="text" class="" name="pds[${status.index}].goods.name"
                                               size="20" value="${pd.goods.name}"></td>
                                    <td><input type="text" class="" name="pds[${status.index}].goods.type"
                                               size="12" value="${pd.goods.type}"></td>
                                    <td><input type="text" name="pds[${status.index}].goods.specification" size="20"
                                               value="${pd.goods.specification}"></td>
                                    <td><input type="text" name="pds[${status.index}].goods.composition" size="8"
                                               value="${pd.goods.composition}"></td>
                                    <td><input type="text" class="digits" name="pds[${status.index}].goods.width"
                                               size="6" value="${pd.goods.width}"></td>
                                    <td><input type="text" class="digits" name="pds[${status.index}].orderCount"
                                               size="6" value="${pd.orderCount}"></td>
                                    <td><input type="text" name="pds[${status.index}].goods.color" size="6"
                                               value="${pd.goods.color}"></td>
                                    <td><input type="text" name="pds[${status.index}].goods.consume" size="6"
                                               value="${pd.goods.consume}"></td>
                                    <td><input type="text" class="" name="pds[${status.index}].goods.unit"
                                               size="3" value="${pd.goods.unit}"></td>
                                    <td><input type="text" class="" name="pds[${status.index}].goods.loss"
                                               size="3" value="${pd.goods.loss}"></td>
                                    <td><input type="text" name="pds[${status.index}].specialRequirements" size="20"
                                               value="${pd.specialRequirements}"></td>

                                    <td><input type="text" class="number "
                                               name="pds[${status.index}].planPurchasingCount" size="8"
                                               value="${pd.planPurchasingCount}"></td>



                                    <td><a href="javascript:void(0)" class="btnDel ">删除</a></td>
                                    <td style="display: none">
                                        <input type="hidden" name="pds[${status.index}].goods.id"
                                               value="${pd.goods.id}">
                                        <input type="hidden" name="pds[${status.index}].id" value="${pd.id}">
                                        <input type="hidden" name="pds[${status.index}].purchasing.id"
                                               value="${purchasing.id}">
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="tabsFooter">
                    <div class="tabsFooterContent"></div>
                </div>
            </div>
        </div>
        <div class="formBar">
            <ul>
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">保存</button>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="button">
                        <div class="buttonContent">
                            <button class="close" type="button">关闭</button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</form>
