<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp" %>
<h2 class="contentTitle">采购计划
</h2>
<script>
    function convertJson(){
        var hidden = $('<input type="hidden" name="" value="">');
        var zipperCountJson = [];
        var index = 0;
        $(".zipperCount").each(function(i){
            i = i+1;
            var json= {};
            json.value = $(this).val();
            zipperCountJson.push(json);
            if(i%11 == 0 && i != 1){
                var input = hidden.clone();
                input.attr("name","zippers["+index+"].zipperCount");
                input.val(JSON.stringify(zipperCountJson));
                $(".hiddenInput").append(input);
                zipperCountJson=[];
                index++;
            }
        });

        var countDetailJson= [];
        $(".countDetail").each(function(i){
            var json= {};
            json.value = $(this).val();
            json.name = $(this).attr("nameStr");
            json.type= $(this).attr("typeStr");
            json.position= $(this).attr("positionStr");
            countDetailJson.push(json);
        });
        if($(".countDetail").length > 0){
            var input = hidden.clone();
            input.attr("name","countDetail");
            input.val(JSON.stringify(countDetailJson));
            $(".hiddenInput").append(input);
        }
    }
</script>
<form action="${ctx}/purchasing/completePurchasing?navTabId=list" method="post" class="pageForm required-validate"
      onsubmit="convertJson();return validateCallback(this, navTabAjaxDone)">
    <div class="hiddenInput">
    <input type="hidden" name="id" value="${purchasing.id}">

    </div>
    <div class="pageContent">
        <div class="pageFormContent"  layoutH="97" >

            <div class="tabs">
                <div class="tabsHeader">
                    <div class="tabsHeaderContent">
                        <ul>
                            <li class="selected"><a href="javascript:void(0)"><span>货物</span></a></li>
                            <li class="selected"><a href="javascript:void(0)"><span>拉链</span></a></li>
                        </ul>
                    </div>
                </div>
                <div class="tabsContent" style="height: 430px;" >

                    <div id="data">
                        <table class="list nowrap" width="100%" >
                            <thead>
                            <tr>
                                <th>当前节点</th>
                                <th>当前处理人</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">名称</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">类型</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">型号规格</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">面辅料成分</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">净宽/CM</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">单量</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">颜色</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">预排单耗</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">单位</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">损耗1*%</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">面辅料特殊要求</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">采购计划</th>
                                <th STYLE="BACKGROUND-COLOR: #09C">库存</th>
                                <th STYLE="BACKGROUND-COLOR: #09C">实需采购</th>
                                <th >原单价</th>
                                <th >采购单价</th>
                                <th STYLE="BACKGROUND-COLOR: #09C">计划入库时间</th>
                                <th STYLE="BACKGROUND-COLOR: #FF0">入库数量</th>
                                <th STYLE="BACKGROUND-COLOR: #FF0">入库时间</th>
                                <th STYLE="BACKGROUND-COLOR: #F66">质检结果</th>
                                <th STYLE="BACKGROUND-COLOR: #F66">实测缩率：经%/纬%</th>
                                <th >需追加</th>
                                <sec:authorize ifAnyGranted="role_leader,role_quality">
                                    <th>审批信息</th>
                                </sec:authorize>
                                <th >排料规格</th>
                                <th >实际门幅</th>
                                <th >实排单耗</th>
                                <th >实际损耗</th>
                                <th >核定用料</th>
                                <th >实际使用</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${purchasing.pds}" var="pd" varStatus="status">
                                <tr class="unitBox">
                                    <!-- 当前节点-->
                                    <td>${pd.task.name == null ?"结束":pd.task.name}</td>
                                    <!-- 当前处理人-->
                                    <td>${pd.currentUserName}</td>

                                    <!-- 货物基本信息-->
                                    <td>${pd.goods.name}</td>
                                    <td>${pd.goods.type}</td>
                                    <td>${pd.goods.specification}</td>
                                    <td>${pd.goods.composition}</td>
                                    <td>${pd.goods.width}</td>
                                    <td>${pd.orderCount}</td>
                                    <td>${pd.goods.color}</td>
                                    <td>${pd.goods.consume}</td>
                                    <td>${pd.goods.unit}</td>
                                    <td>${pd.goods.loss}</td>
                                    <td>${pd.specialRequirements}</td>
                                    <td>${pd.planPurchasingCount}</td>

                                    <!-- 库存-->
                                    <td>
                                        <sec:authorize ifAnyGranted ="role_purchasing,role_warehouse">
                                            <input type="text" class="number "
                                                   name="pds[${status.index}].warehouseCount" size="8"
                                                   value="${pd.warehouseCount}">
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_purchasing,role_warehouse">
                                            ${pd.warehouseCount}
                                        </sec:authorize>
                                    </td>

                                    <!--实需采购-->
                                    <td>
                                        <sec:authorize ifAllGranted="role_purchasing">
                                            <input type="text" class="number "
                                                   name="pds[${status.index}].actualPurchasingCount" size="8"
                                                   value="${pd.actualPurchasingCount}">
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_purchasing">
                                            ${pd.actualPurchasingCount}
                                        </sec:authorize>
                                    </td>

                                    <!--原单价-->
                                    <td>${pd.goods.oriPrice}</td>
                                    <!--采购单价-->
                                    <td>${pd.goods.price}</td>


                                    <!-- 计划入库时间-->
                                    <td>
                                        <sec:authorize ifAllGranted="role_purchasing">
                                            <input type="text" name="pds[${status.index}].expectedArrivalTime"
                                                   value="<fmt:formatDate value="${pd.expectedArrivalTime}" pattern="yyyy-MM-dd HH:mm"/>"
                                                   size="19"  class="date" dateFmt="yyyy-MM-dd HH:mm" readonly="true">
                                            <a class="inputDateButton" href="javascript:;">选择</a>
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_purchasing">
                                            <fmt:formatDate value="${pd.expectedArrivalTime}" pattern="yyyy-MM-dd HH:mm"/>
                                        </sec:authorize>

                                    </td>

                                    <!-- 入库数量-->
                                    <td>
                                        <sec:authorize ifAllGranted="role_warehouse">
                                            <c:if test="${pd.expectedArrivalTime != null}">
                                            <input type="text" class="number "
                                                   name="pds[${status.index}].planEntryCount" size="8"
                                                   value="${pd.planEntryCount}">
                                            </c:if>
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_warehouse">
                                            ${pd.planEntryCount}
                                        </sec:authorize>

                                    </td>

                                    <!-- 入库时间-->
                                    <td>
                                        <sec:authorize ifAllGranted="role_warehouse">
                                            <c:if test="${pd.expectedArrivalTime != null}">
                                            <input type="text" name="pds[${status.index}].planEntryTime"
                                                   value="<fmt:formatDate value="${pd.planEntryTime}" pattern="yyyy-MM-dd HH:mm"/>"
                                                   size="19"  class="date" dateFmt="yyyy-MM-dd HH:mm" readonly="true">
                                            <a class="inputDateButton" href="javascript:;">选择</a>
                                            </c:if>
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_warehouse">
                                            <fmt:formatDate value="${pd.planEntryTime}" pattern="yyyy-MM-dd HH:mm"/>
                                        </sec:authorize>

                                    </td>

                                    <!-- 质检结果-->
                                    <td>
                                        <c:if test="${pd.task.taskDefinitionKey == 'quality'}">
                                            <sec:authorize ifAllGranted="role_quality">
                                                <c:if test="${pd.planEntryTime != null}">
                                                <select name="pds[${status.index}].qualified" class="combox">
                                                    <option value="true">合格</option>
                                                    <option value="false">不合格</option>
                                                </select>
                                                </c:if>
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

                                    <!-- 质检信息缩率-->
                                    <td>
                                        <sec:authorize ifAllGranted="role_quality">
                                            <c:if test="${pd.planEntryTime != null}">
                                            <input type="text" class="number"
                                                   name="pds[${status.index}].shrinkage" size="20"
                                                   value="${pd.shrinkage}">
                                            </c:if>
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_quality">
                                            ${pd.shrinkage}
                                        </sec:authorize>
                                    </td>
                                    <!-- 需追加-->
                                    <td>${pd.goods.needAdd}</td>

                                    <!-- 审批信息-->
                                    <sec:authorize ifAnyGranted="role_leader,role_quality">
                                        <td>
                                            <sec:authorize ifAllGranted="role_leader">
                                                <c:if test="${pd.qualified == false}">
                                                <textarea name="pds[${status.index}].reason" cols="20" rows="2"></textarea>
                                                </c:if>
                                            </sec:authorize>

                                            <sec:authorize ifAllGranted="role_quality">
                                                ${pd.reason}
                                            </sec:authorize>

                                        </td>
                                    </sec:authorize>
                                    <!-- 排料规格-->
                                    <td>${pd.goods.dischargeSpec}</td>
                                    <!-- 实际门幅-->
                                    <td>${pd.goods.actualWidth}</td>
                                    <!-- 实排单耗-->
                                    <td>${pd.goods.actualConsume}</td>
                                    <!-- 实际损耗-->
                                    <td>${pd.goods.actualLoss}</td>
                                    <!-- 核定用料-->
                                    <td>${pd.goods.confirmUse}</td>
                                    <!-- 实际使用-->
                                    <td>${pd.goods.actualUse}</td>


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
                        <div style="display: inline;">
                       <a  href="${ctx}/purchasing/upload/${purchasing.id}" target="navTab" rel="upload"><span style="color: blue;line-height: 20px;">从EXCEL导入</span></a>
                            </div>
                        <table class="list nowrap itemDetail" addButton="新建条目" width="100%">
                            <thead>
                            <tr>
                                <th type="suggest" name="zippers[#index#].position" lookupPk ="value" postField ="value" lookupGroup="zippers[#index#]" fieldClass="required" suggestUrl="${ctx}/dataDict/position" suggestFields="value" size="20">部位</th>
                                <th type="suggest" name="zippers[#index#].name" lookupPk ="value" postField ="value" lookupGroup="zippers[#index#]" fieldClass="" suggestUrl="${ctx}/dataDict/name" suggestFields="value" size="20">名称</th>
                                <th type="suggest" name="zippers[#index#].material" lookupPk ="value" postField ="value" lookupGroup="zippers[#index#]" fieldClass="" suggestUrl="${ctx}/dataDict/material" suggestFields="value" size="20">材质</th>
                                <th type="suggest" name="zippers[#index#].spec" lookupPk ="value" postField ="value" lookupGroup="zippers[#index#]" fieldClass="" suggestUrl="${ctx}/dataDict/spec" suggestFields="value" size="20">规格</th>
                                <c:forEach items="${purchasing.countDetailList}" var="cd" end="10" varStatus="status">
                                <th type="text" fieldClass="zipperCount" name="zipperCountList"  size="6">${cd.name}</th>
                                </c:forEach>
                                <th type="del" width="60">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:set var="type" value=""/>
                            <c:forEach items="${purchasing.zippers}" var="z" varStatus="status">
                                <c:if test="${type!=z.position}">
                                    <c:set var="type" value="${z.position}"/>
                                    <tr style="background-color: #FC9">
                                        <td colspan="4">${z.position}(数量)</td>
                                        <c:forEach items="${purchasing.countDetailList}" var="cd" varStatus="s">
                                            <c:if test="${type==cd.position}">
                                                <td>${cd.value}</td>
                                            </c:if>
                                        </c:forEach>
                                    </tr>
                                </c:if>
                            <tr class="unitBox">
                                <td><input type="text" class="required" origin="true" lookupgroup="zippers[${status.index}]" suggesturl="${ctx}/dataDict/position" suggestfields="value" postfield="value" lookuppk="value" name="zippers[${status.index}].position" size="20" value="${z.position}"></td>
                                <td><input type="text" class="" origin="true" lookupgroup="zippers[${status.index}]" suggesturl="${ctx}/dataDict/name" suggestfields="value" postfield="value" lookuppk="value" name="zippers[${status.index}].name" size="20" value="${z.name}"></td>
                                <td><input type="text" class="" origin="true" lookupgroup="zippers[${status.index}]" suggesturl="${ctx}/dataDict/material" suggestfields="value" postfield="value" lookuppk="value" name="zippers[${status.index}].material" size="20" value="${z.material}"></td>
                                <td><input type="text" class="" origin="true" lookupgroup="zippers[${status.index}]" suggesturl="${ctx}/dataDict/spec" suggestfields="value" postfield="value" lookuppk="value" name="zippers[${status.index}].spec" size="20" value="${z.spec}"></td>
                                <c:forEach items="${z.zipperCountList}" var="zc" varStatus="s">
                                <td><input type="text" class="zipperCount"  name="zipperCountList" size="6" value="${zc.value}"></td>
                                </c:forEach>
                                <td><a href="javascript:void(0)" class="btnDel ">删除</a></td>
                                <td style="display: none">
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
                            <input type="text" size="10" class="" name="actualShrinkage" value="${purchasing.actualShrinkage}"/>
                            </p>
                            <p>
                            <label>拉链已含缩率</label>
                            <input type="text" size="10" class="" name="zipperShrinkage" value="${purchasing.zipperShrinkage}"/>
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
                                <c:forEach items="${purchasing.countDetailList}" var="cd" end="10" varStatus="status">
                                 <th>${cd.name}</th>
                                </c:forEach>
                            </tr>
                            </thead>
                            <tbody>
                            <c:set var="type" value=""/>
                            <c:forEach items="${purchasing.zippers}" var="z" varStatus="status">
                                <c:if test="${type!=z.position}">
                                    <c:set var="type" value="${z.position}"/>
                                    <tr class="unitBox" style="background-color: #FC9">
                                        <td colspan="4">${z.position}(数量)</td>
                                        <c:forEach items="${purchasing.countDetailList}" var="cd" varStatus="s">
                                            <c:if test="${type==cd.position}">
                                            <sec:authorize ifAllGranted="role_product">
                                            <td><input nameStr="${cd.name}" typeStr="${cd.type}" positionStr="${cd.position}" type="text" class="countDetail"  name="zipperCountList" size="6" value="${cd.value}"></td>
                                                </sec:authorize>
                                                <sec:authorize ifNotGranted=" role_product">
                                                        <td>${cd.value}</td>
                                                </sec:authorize>
                                            </c:if>
                                        </c:forEach>
                                    </tr>
                                </c:if>
                            <tr class="unitBox">
                                <td>${z.position}</td>
                                <td>${z.name}</td>
                                <td>${z.material}</td>
                                <td>${z.spec}</td>
                                <c:forEach items="${z.zipperCountList}" var="zc" varStatus="s">
                                    <td>${zc.value}</td>
                                </c:forEach>

                                <sec:authorize ifAllGranted="role_product">
                                <td style="display: none">
                                    <input type="hidden" name="zippers[${status.index}].id"
                                           value="${z.id}">
                                    <input type="hidden" name="zippers[${status.index}].purchasing.id"
                                           value="${purchasing.id}">
                                </td>
                                </sec:authorize>
                            </tr>
                           </c:forEach>
                            </tbody>

                        </table>
                            <div>
                                <p>
                                    <label>面料实际缩率</label>
                                     ${purchasing.actualShrinkage}
                                </p>
                                <p>
                                    <label>拉链已含缩率</label>
                                    ${purchasing.zipperShrinkage}
                                </p>
                            </div>
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
