<%@ page import="com.barakawei.lightwork.util.UserContextUtil" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp" %>
<h2 class="contentTitle">采购计划</h2>

<form action="${ctx}/purchasing/create?navTabId=list" method="post" class="pageForm required-validate"
      onsubmit="return validateCallback(this, navTabAjaxDone)">
    <div class="pageContent">
        <div class="pageFormContent" layoutH="97">
            <div class="panel collapse" style="margin:0">
                <h1>订单信息</h1>

                <div>
                    <p>
                        <label>单位</label>
                        <input type="text" name="orderName" maxlength="50" class=""/>
                    </p>
                    <dl>
                        <dt>订单编号</dt>
                        <dd>
                            <input type="text" name="orderNumber" maxlength="50" class=""/>
                        </dd>
                    </dl>
                    <dl>
                        <dt>序列号</dt>
                        <dd>
                            <input type="text" name="serialNumber" maxlength="50" class=""/>
                        </dd>
                    </dl>

                    <dl>
                        <dt>数量</dt>
                        <dd>
                            <input type="text" name="orderCount" maxlength="50" class=""/>
                        </dd>
                    </dl>

                    <dl>
                        <dt>面辅料供应时间</dt>
                        <dd>
                            <input type="text" name="applyTime" class="date" dateFmt="yyyy-MM-dd HH:mm"
                                   readonly="true"/>
                            <a class="inputDateButton" href="javascript:;">选择</a>
                        </dd>
                    </dl>

                    <dl>
                        <dt>计划人</dt>
                        <dd>
                            <%
                                String name = UserContextUtil.getCurrentUser().getName();
                            %>
                            <%=name%>
                        </dd>
                    </dl>

                    <!--
                    <dl>
                        <dt>排料确认</dt>
                        <dd>
                            <input type="text" name=" dischargeRecognition" maxlength="50" value="${purchasing.dischargeRecognition}"/>

                        </dd>
                    </dl>

                    <dl>
                        <dt>日期</dt>
                        <dd>
                            <input type="text" name="confirmTime" class="date" dateFmt="yyyy-MM-dd HH:mm"
                                   readonly="true"/>
                            <a class="inputDateButton" href="javascript:;">选择</a>
                        </dd>
                    </dl>
                    -->
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
                            <tbody></tbody>
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
                            <button type="submit">添加</button>
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
