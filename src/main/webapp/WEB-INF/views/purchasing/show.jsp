<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp" %>
<h2 class="contentTitle" >采购计划(${purchasing.orderNumber} \ ${purchasing.serialNumber})
</h2>
<script>

var oldShrinkage;
    $(document).ready(function(){
        oldShrinkage = $(".zipperShrinkage").val();
        if(oldShrinkage=="undefined" || oldShrinkage == null || $.trim(oldShrinkage)==""){
            oldShrinkage = 1;
        }else{
            oldShrinkage = (1+oldShrinkage/100);
        }
        $("input").live("keyup",function(e){
            if (e.which == 40){
                var name = $(this).attr("name");
                var rgExp = /[0-9]+/;
                var row = name.match(rgExp)[0]*1;
                if(row >= 0){
                var key = name.replace(rgExp,row+1);
                $(this).closest("table").find("input[name='"+key+"']").focus();
                }

            }
            if (e.which == 38){
                var name = $(this).attr("name");
                var rgExp = /[0-9]+/;
                var row = name.match(rgExp)[0]*1;
                if(row >= -1){
                    var key = name.replace(rgExp,row-1);
                    $(this).closest("table").find("input[name='"+key+"']").focus();
                }

            }
        });

    });

    $("input").live("change",function(){
        var count = $(this).closest("td").attr("count");
        var count = count * 1 +1;
        $(this).closest("td").attr("count",count);
    });

    $(".checkvalue").live("click",function(){
        var checked = $(this).attr('checked');
        var shrinkage = $(".zipperShrinkage").val()*1;
        if(shrinkage=="undefined" || shrinkage == null || $.trim(shrinkage)==""){
            shrinkage = 1;
        }else{
            shrinkage = (1+shrinkage/100);
        }
            $(this).closest("tr").find(".zipperCount").each(function(){
                var zipper = $(this);
                var old =zipper.val();
                if(old != ""){
                    if(checked == "checked"){
                        var s = old * shrinkage;
                        zipper.val(s.toFixed(2));
                    }else{
                        var sh = old / shrinkage;
                        zipper.val(sh.toFixed(2));

                    }
                }
            });

    });


    $(".zipperShrinkage").live("change",function(){
        var shrinkage = $(this).val();
        if(shrinkage=="undefined" || shrinkage == null || $.trim(shrinkage)==""){
            shrinkage = 1;
        }else{
            shrinkage = (1+shrinkage/100);
        }
        $('input:checked').each(function(){
            $(this).closest("tr").find(".zipperCount").each(function(){
                var zipper = $(this);
                var old =zipper.attr("old");
                if(old == "undefined" || old == null){
                    old = zipper.val();
                }
                if(old != ""){
                   var s = old * shrinkage;
                   zipper.val(s.toFixed(2));
                }
            });

        });

    });


    function convertJson(){
        var hidden = $('<input type="hidden" name="" value="">');
        var zipperCountJson = [];
        var index = 0;
        $(".hiddenInput").html("");

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

    $(".warehouseCount input").live("change",function(){
        var warehouseCount = $(this).val();
        var tr = $(this).closest("tr");
        var planPurchasingCount = tr.find(".planPurchasingCount").text();
        var planEntryCount = tr.find(".planEntryCount input").val();
        var confirmUse= tr.find(".confirmUse").text();
        var actualPurchasingCount = planPurchasingCount*1-warehouseCount*1-planEntryCount*1;
        var needAdd = confirmUse*1-planEntryCount*1-warehouseCount*1;
        if(actualPurchasingCount<=0){
            var date = new Date();
            var str = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
            tr.find(".actualEntryTime").find("input").val(str);


        }
        tr.find(".actualPurchasingCount").text(actualPurchasingCount.toFixed(2));
        tr.find(".needAdd").text(needAdd.toFixed(2));
    });

    $(".planEntryCount input").live("change",function(){
        var planEntryCount = $(this).val();
        var tr = $(this).closest("tr");
        var planPurchasingCount = tr.find(".planPurchasingCount").text();
        var warehouseCount = tr.find(".warehouseCount input").val();
        var confirmUse = tr.find(".confirmUse").text();
        var needAdd = confirmUse*1-planEntryCount*1-warehouseCount*1;
        var actualPurchasingCount = planPurchasingCount*1-warehouseCount*1-planEntryCount*1;
            var date = new Date();
            var str = date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
            tr.find(".actualEntryTime").find("input").val(str);
        tr.find(".actualPurchasingCount").text(actualPurchasingCount.toFixed(2));
        tr.find(".needAdd").text(needAdd.toFixed(2));
    });

    $(".actualUse input").live("change",function(){
        var actualUse = $(this).val();
        var tr = $(this).closest("tr");
        var confirmUse= tr.find(".confirmUse").text();
        var exceedUse= actualUse*1-confirmUse*1;
        tr.find(".exceedUse").text(exceedUse.toFixed(2));
    });

    $(".actualConsume input").live("change",function(){
        var actualConsume = $(this).val();
        var tr = $(this).closest("tr");
        var orderCount= tr.find(".orderCount").text();
        var actualLoss = tr.find(".actualLoss").text();
        var actualUse = tr.find(".actualUse").text();
        var warehouseCount = tr.find(".warehouseCount").text();
        var planEntryCount = tr.find(".planEntryCount").text();
        var confirmUse= actualConsume*orderCount*actualLoss;
        var needAdd = confirmUse*1-planEntryCount*1-warehouseCount*1;
        var exceedUse= actualUse*1-confirmUse*1;
        tr.find(".confirmUse").text(confirmUse.toFixed(2));
        tr.find(".needAdd").text(needAdd.toFixed(2));
        tr.find(".exceedUse").text(exceedUse.toFixed(2));
    });

    $(".consume input").live("change",function(){
        var consume = $(this).val();
        var tr = $(this).closest("tr");
        var orderCount= tr.find(".orderCount").text();
        var loss = tr.find(".loss").text();
        var warehouseCount = tr.find(".warehouseCount").text();
        var planEntryCount = tr.find(".planEntryCount").text();
        var planPurchasingCount= consume*orderCount*loss;
        var actualPurchasingCount = planPurchasingCount*1-warehouseCount*1-planEntryCount*1;
        tr.find(".planPurchasingCount").text(planPurchasingCount.toFixed(2));
        tr.find(".actualPurchasingCount").text(actualPurchasingCount.toFixed(2));
    });


    $(".orderCount input").live("change",function(){
        var orderCount = $(this).val();
        var tr = $(this).closest("tr");
        var consume= tr.find(".consume").text();
        var loss = tr.find(".loss").text();
        var warehouseCount = tr.find(".warehouseCount").text();
        var planEntryCount = tr.find(".planEntryCount").text();
        var actualLoss = tr.find(".actualLoss").text();
        var actualConsume = tr.find(".actualConsume").text();
        var actualUse = tr.find(".actualUse").text();
        var planPurchasingCount= consume*orderCount*loss;
        var actualPurchasingCount = planPurchasingCount*1-warehouseCount*1-planEntryCount*1;
        var confirmUse= actualConsume*orderCount*actualLoss;
        var needAdd = confirmUse*1-planEntryCount*1-warehouseCount*1;
        var exceedUse= actualUse*1-confirmUse*1;
        tr.find(".planPurchasingCount").text(planPurchasingCount.toFixed(2));
        tr.find(".actualPurchasingCount").text(actualPurchasingCount.toFixed(2));
        tr.find(".confirmUse").text(confirmUse.toFixed(2));
        tr.find(".needAdd").text(needAdd.toFixed(2));
        tr.find(".exceedUse").text(exceedUse.toFixed(2));

    });
    var intervalName;

    $(".pdData input").live("change",function(){
        $(".pageForm").submit();
    });
    $(".pdData #calendar .days dd").live("click",function(){
        intervalName = setInterval(handle,1000);
    });
    $(".pdData #calendar button").live("click",function(){
        intervalName = setInterval(handle,1000);
    });

    $(".pdData #suggest li").live("click",function(){
        $(".pageForm").submit();
    });

    function handle(){
        $(".pageForm").submit();
        clearInterval(intervalName);
    }

    function done(json){

    }

</script>

<form action="${ctx}/purchasing/completePurchasing?navTabId=list" method="post" class="pageForm"
      onsubmit="convertJson();return validateCallback(this, done)">
    <input type="hidden" name="id" value="${purchasing.id}">

    <div class="hiddenInput">
    </div>
    <div class="pageContent">
        <div class="pageFormContent"  layoutH="97" >

<sec:authorize ifAllGranted="role_technolog">
        <div class="panel collapse" style="margin:0">
        <h1>订单信息</h1>

        <div>
        <dl>
            <dt>裁剪组别</dt>
            <dd>
                <input type="text" name="cutGroup" maxlength="50" size="40" class=""
                       value="${purchasing.cutGroup}"/>
            </dd>
        </dl>
        <dl>
            <dt>已裁完</dt>
            <dd>
                <input type="checkbox" name="finshCut" size="40" <c:if test="${purchasing.finshCut==1}"> checked="checked" </c:if>
                       value="1"/>
            </dd>
        </dl>

        </div>
        </div>
</sec:authorize>

<sec:authorize ifAllGranted="role_product">
    <div class="panel collapse" style="margin:0">
        <h1>订单信息</h1>

        <div>
            <dl>
                <dt>数据日期</dt>
                <dd>

                    <input type="text" name="dataDate" class="date" dateFmt="yyyy-MM-dd" readonly="true"
                           value="<fmt:formatDate pattern='yyyy-MM-dd' value='${purchasing.dataDate}' type='both'/>">
                    <a class="inputDateButton" href="javascript:;">选择</a>
                </dd>
            </dl>

        </div>
    </div>
</sec:authorize>

<sec:authorize ifAllGranted="role_warehouse">
    <div class="panel collapse" style="margin:0">
        <h1>订单信息</h1>

        <div>
            <dl>
                <dt>入库日期</dt>
                <dd>

                    <input type="text" name="finshDate" class="date" dateFmt="yyyy-MM-dd" readonly="true"
                           value="<fmt:formatDate pattern='yyyy-MM-dd' value='${purchasing.finshDate}' type='both'/>">
                    <a class="inputDateButton" href="javascript:;">选择</a>
                </dd>
            </dl>

        </div>
    </div>
</sec:authorize>

<sec:authorize ifAllGranted="role_purchasing">
    <div class="panel collapse" style="margin:0">
        <h1>订单信息</h1>

        <div>
            <dl>
                <dt>面料合同交期</dt>
                <dd>

                    <input type="text" name="contractDate" class="date" dateFmt="yyyy-MM-dd" readonly="true"
                           value="<fmt:formatDate pattern='yyyy-MM-dd' value='${purchasing.contractDate}' type='both'/>">
                    <a class="inputDateButton" href="javascript:;">选择</a>
                </dd>
            </dl>

        </div>
    </div>
</sec:authorize>

<sec:authorize ifAllGranted="role_director">
<div class="panel collapse" style="margin-bottom:20px;">
<h1>订单信息</h1>

<div style="height: 100px;">
<dl>
    <dt>生产班组</dt>
    <dd>
        <input type="text" name="productGroup" maxlength="50" size="40" class=""
               value="${purchasing.productGroup}"/>
    </dd>
</dl>
<dl>
    <dt>预下线日期</dt>
    <dd>

        <input type="text" name="planUnderlineDate" class="date" dateFmt="yyyy-MM-dd" readonly="true"
               value="<fmt:formatDate pattern='yyyy-MM-dd' value='${purchasing.planUnderlineDate}' type='both'/>">
        <a class="inputDateButton" href="javascript:;">选择</a>
    </dd>
</dl>
    <dl>
        <dt>后整理</dt>
        <dd>
            <input type="radio" name="hou" <c:if test="${purchasing.hou==1}"> checked="checked" </c:if>
                   value="1"/>
            锁钉
            <input type="radio" name="hou" <c:if test="${purchasing.hou==2}"> checked="checked" </c:if>
                   value="2"/>
            水洗
            <input type="radio" name="hou" <c:if test="${purchasing.hou==3}"> checked="checked" </c:if>
                   value="3"/>
            后道包装
        </dd>
    </dl>
    <dl>
        <dt>备注</dt>
        <dd>
            <textarea name="remark" cols="40" rows="2" class="">${purchasing.remark}</textarea>
        </dd>
    </dl>
    <br/>
    <br/>
    <br/>
</div>
</div>
    <div class="divider"></div>
</sec:authorize>


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

                    <div id="data" class="pdData">
                        <table class="list nowrap" width="100%" >
                            <thead>
                            <tr>
                                <th STYLE="BACKGROUND-COLOR: #FC9">名称</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">型号规格</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">面辅料成分</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">净宽/CM</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">单量</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">颜色</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">预排单耗</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">单位</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">损耗1*%</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">面辅料特殊要求</th>
                                <th >采购计划</th>
                                <th STYLE="BACKGROUND-COLOR: #FFFF66">库存</th>
                                <th >实需采购</th>
                                <sec:authorize ifAnyGranted="role_leader,role_purchasing,role_warehouse,role_planning">
                                <th STYLE="BACKGROUND-COLOR: #99CCCC">原单价</th>
                                <th STYLE="BACKGROUND-COLOR: #99CCCC">采购单价</th>
                                </sec:authorize>
                                <th STYLE="BACKGROUND-COLOR: #99CCCC">计划入库时间</th>
                                <th STYLE="BACKGROUND-COLOR: #FFFF66">预入库时间</th>
                                <th STYLE="BACKGROUND-COLOR: #FFFF66">入库时间</th>
                                <th STYLE="BACKGROUND-COLOR: #FFFF66">入库数量</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">名称</th>
                                <th STYLE="BACKGROUND-COLOR: #F66">实测缩率：经%/纬%/门幅</th>
                                <th >需追加</th>
                                <th STYLE="BACKGROUND-COLOR: #339933">排料规格</th>
                                <th STYLE="BACKGROUND-COLOR: #339933">实际门幅</th>
                                <th STYLE="BACKGROUND-COLOR: #339933">实排单耗</th>
                                <th STYLE="BACKGROUND-COLOR: #FC9">实际损耗1*%</th>
                                <th >核定用料</th>
                                <th STYLE="BACKGROUND-COLOR: #FFFF66">实际使用</th>
                                <th >超用料</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${purchasing.pds}" var="pd" varStatus="status">
                            <c:if test="${goodType!=pd.goods.type}">
                                <c:set var="goodType" value="${pd.goods.type}"/>
                                <tr style="background-color: #99CCCC;color:red">
                                    <td colspan="2">${pd.goods.type}</td>
                                    <td colspan="30">&nbsp;</td>
                                </tr>
                            </c:if>
                                <tr class="unitBox">
                                    <!-- 当前节点-->
                                    <!-- 当前处理人-->

                                    <!-- 货物基本信息-->
                                    <td>${pd.goods.name}</td>
                                    <td>${pd.goods.specification}</td>
                                    <td>${pd.goods.composition}</td>
                                    <td>${pd.goods.width}</td>

                                    <!-- 单量-->
                                    <td class="orderCount">

                                        <sec:authorize ifAnyGranted ="role_product">
                                            <input type="text" class="number "
                                                   name="pds[${status.index}].goods.orderCount" size="8"
                                                   value="${pd.goods.orderCount}">
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_product">
                                            ${pd.goods.orderCount}
                                        </sec:authorize>
                                    </td>
                                    <td>${pd.goods.color}</td>
                                    <!-- 预排单耗-->
                                    <td class="consume">

                                        <sec:authorize ifAnyGranted ="role_technolog">
                                            <input type="text" class="number "
                                                   name="pds[${status.index}].goods.consume" size="8"
                                                   value="${pd.goods.consume}">
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_technolog">
                                            ${pd.goods.consume}
                                        </sec:authorize>


                                    </td>
                                    <td>${pd.goods.unit}</td>
                                    <td class="loss">${pd.goods.loss}</td>
                                    <td>${pd.goods.description}</td>
                                    <td class="planPurchasingCount">${pd.planPurchasingCount}</td>

                                    <!-- 库存-->
                                    <td class="warehouseCount">
                                        <sec:authorize ifAnyGranted ="role_warehouse">
                                            <input type="text" class="number "
                                                   name="pds[${status.index}].warehouseCount" size="8"
                                                   value="${pd.warehouseCount}">
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_warehouse">
                                            ${pd.warehouseCount}
                                        </sec:authorize>
                                    </td>

                                    <!--实需采购-->
                                    <td class="actualPurchasingCount">
                                         ${pd.actualPurchasingCount}
                                    </td>

                                    <!--原单价-->

                                    <sec:authorize ifAnyGranted="role_leader,role_purchasing,role_warehouse,role_planning">
                                    <td>

                                    <sec:authorize ifAllGranted="role_purchasing">
                                        <input type="text" class="number "
                                               name="pds[${status.index}].goods.oriPrice" size="8"
                                               value="${pd.goods.oriPrice}">
                                    </sec:authorize>
                                        <sec:authorize ifNotGranted="role_purchasing">
                                            ${pd.goods.oriPrice}
                                        </sec:authorize>
                                    </td>
                                    <!--采购单价-->
                                    <td>
                                        <sec:authorize ifAllGranted="role_purchasing">
                                            <input type="text" class="number "
                                                   name="pds[${status.index}].goods.price" size="8"
                                                   value="${pd.goods.price}">
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_purchasing">
                                            ${pd.goods.price}
                                        </sec:authorize>

                                    </td>

                                    </sec:authorize>

                                    <!-- 计划入库时间-->
                                    <td>

                                        <sec:authorize ifAllGranted="role_purchasing">
                                            <input type="text" name="pds[${status.index}].expectedArrivalTime"
                                                   value="<fmt:formatDate value="${pd.expectedArrivalTime}" pattern="yyyy-MM-dd"/>"
                                                   size="19"  class="date" dateFmt="yyyy-MM-dd" readonly="true">
                                            <a class="inputDateButton" href="javascript:;">选择</a>
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_purchasing">
                                            <fmt:formatDate value="${pd.expectedArrivalTime}" pattern="yyyy-MM-dd"/>
                                        </sec:authorize>
                                    </td>
                                    <!-- 预入库时间-->
                                    <td class="planEntryTime">
                                        <sec:authorize ifAllGranted="role_warehouse">
                                            <input type="text" name="pds[${status.index}].planEntryTime"
                                                   value="<fmt:formatDate value="${pd.planEntryTime}" pattern="yyyy-MM-dd"/>"
                                                   size="19"  class="date" dateFmt="yyyy-MM-dd" readonly="true">
                                            <a class="inputDateButton" href="javascript:;">选择</a>
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_warehouse">
                                            <fmt:formatDate value="${pd.planEntryTime}" pattern="yyyy-MM-dd"/>
                                        </sec:authorize>

                                    </td>

                                    <!-- 入库时间-->
                                    <td class="actualEntryTime">
                                        <sec:authorize ifAllGranted="role_warehouse">
                                                <input type="text" name="pds[${status.index}].actualEntryTime"
                                                       value="<fmt:formatDate value="${pd.actualEntryTime}" pattern="yyyy-MM-dd"/>"
                                                       size="19"  class="date" dateFmt="yyyy-MM-dd" readonly="true">
                                                <a class="inputDateButton" href="javascript:;">选择</a>
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_warehouse">
                                            <fmt:formatDate value="${pd.actualEntryTime}" pattern="yyyy-MM-dd"/>
                                        </sec:authorize>

                                    </td>

                                    <!-- 入库数量-->
                                    <td class="planEntryCount">
                                        <sec:authorize ifAllGranted="role_warehouse">
                                            <input type="text" class="number "
                                                   name="pds[${status.index}].planEntryCount" size="8"
                                                   value="${pd.planEntryCount}">
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_warehouse">
                                            ${pd.planEntryCount}
                                        </sec:authorize>

                                    </td>
                                    <td>${pd.goods.name}</td>


                                    <!-- 质检信息缩率-->
                                    <td>
                                        <sec:authorize ifAllGranted="role_quality">
                                            <input type="text" class=""
                                                   name="pds[${status.index}].shrinkage" size="20"
                                                   value="${pd.shrinkage}">
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_quality">
                                            ${pd.shrinkage}
                                        </sec:authorize>
                                    </td>
                                    <!-- 需追加-->
                                    <td class="needAdd">${pd.goods.needAdd}</td>

                                    <!-- 排料规格-->
                                    <td>

                                        <sec:authorize ifAllGranted="role_technolog">
                                            <input type="text" class=""
                                                   name="pds[${status.index}].goods.dischargeSpec" size="20"
                                                   value="${pd.goods.dischargeSpec}">
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_technolog">
                                            ${pd.goods.dischargeSpec}
                                        </sec:authorize>


                                    </td>
                                    <!-- 实际门幅-->
                                    <td>

                                        <sec:authorize ifAllGranted="role_technolog">
                                            <input type="text" class=""
                                                   name="pds[${status.index}].goods.actualWidth" size="20"
                                                   value="${pd.goods.actualWidth}">
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_technolog">
                                            ${pd.goods.actualWidth}
                                        </sec:authorize>
                                    </td>
                                    <!-- 实排单耗-->
                                    <td class="actualConsume">

                                        <sec:authorize ifAllGranted="role_technolog">
                                            <input type="text" class="number"
                                                   name="pds[${status.index}].goods.actualConsume" size="8"
                                                   value="${pd.goods.actualConsume}">
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_technolog">
                                            ${pd.goods.actualConsume}
                                        </sec:authorize>
                                    </td>
                                    <!-- 实际损耗-->
                                    <td class="actualLoss">${pd.goods.actualLoss}</td>
                                    <!-- 核定用料-->
                                    <td class="confirmUse">${pd.goods.confirmUse}</td>
                                    <!-- 实际使用-->
                                    <td class="actualUse">

                                        <sec:authorize ifAllGranted="role_warehouse">
                                            <input type="text" class="number"
                                                   name="pds[${status.index}].goods.actualUse" size="8"
                                                   value="${pd.goods.actualUse}">
                                        </sec:authorize>
                                        <sec:authorize ifNotGranted="role_warehouse">
                                            ${pd.goods.actualUse}
                                        </sec:authorize>
                                    </td>
                                    <!-- 超用料-->
                                    <td class="exceedUse">${pd.goods.exceedUse}</td>


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


                    <div>
                        <!-- 拉链信息-->
                        <sec:authorize ifAllGranted="role_technolog">
                        <div style="display: inline;">
                       <a  href="${ctx}/purchasing/upload/${purchasing.id}" target="navTab" rel="upload"><span style="color: blue;line-height: 20px;">从EXCEL导入</span></a>
                            </div>
                        <table class="list nowrap itemDetail" addButton="新建条目" width="100%">
                            <thead>
                            <tr>
                                <th type="suggest" name="zippers[#index#].position" lookupPk ="value" postField ="value" lookupGroup="zippers[#index#]" fieldClass="" suggestUrl="${ctx}/dataDict/position" suggestFields="value" size="20">部位</th>
                                <th type="suggest" name="zippers[#index#].name" lookupPk ="value" postField ="value" lookupGroup="zippers[#index#]" fieldClass="" suggestUrl="${ctx}/dataDict/name" suggestFields="value" size="20">名称</th>
                                <th type="suggest" name="zippers[#index#].material" lookupPk ="value" postField ="value" lookupGroup="zippers[#index#]" fieldClass="" suggestUrl="${ctx}/dataDict/material" suggestFields="value" size="20">材质</th>
                                <th type="suggest" name="zippers[#index#].spec" lookupPk ="value" postField ="value" lookupGroup="zippers[#index#]" fieldClass="" suggestUrl="${ctx}/dataDict/spec" suggestFields="value" size="20">规格</th>
                                <th type="checkbox" fieldClass="checkvalue" name="zippers[#index#].checkvalue" defaultVal="1"  value="1" width="60">已含缩率</th>
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
                                        <td colspan="5">${z.position}(数量)</td>
                                        <c:forEach items="${purchasing.countDetailList}" var="cd" varStatus="s">
                                            <c:if test="${type==cd.position}">
                                                <td>${cd.value}</td>
                                            </c:if>
                                        </c:forEach>
                                    </tr>
                                </c:if>
                            <tr class="unitBox">
                                <td><input type="text" class="" origin="true" lookupgroup="zippers[${status.index}]" suggesturl="${ctx}/dataDict/position" suggestfields="value" postfield="value" lookuppk="value" name="zippers[${status.index}].position" size="20" value="${z.position}"></td>
                                <td><input type="text" class="" origin="true" lookupgroup="zippers[${status.index}]" suggesturl="${ctx}/dataDict/name" suggestfields="value" postfield="value" lookuppk="value" name="zippers[${status.index}].name" size="20" value="${z.name}"></td>
                                <td><input type="text" class="" origin="true" lookupgroup="zippers[${status.index}]" suggesturl="${ctx}/dataDict/material" suggestfields="value" postfield="value" lookuppk="value" name="zippers[${status.index}].material" size="20" value="${z.material}"></td>
                                <td><input type="text" class="" origin="true" lookupgroup="zippers[${status.index}]" suggesturl="${ctx}/dataDict/spec" suggestfields="value" postfield="value" lookuppk="value" name="zippers[${status.index}].spec" size="20" value="${z.spec}"></td>
                                <td><input type="checkbox" class="checkvalue" name="zippers[${status.index}].checkvalue"  value="1" <c:if test="${z.checkvalue==1}"> checked="checked" </c:if> /></td>
                                <c:forEach items="${z.zipperCountList}" var="zc" varStatus="s">
                                <td><input type="text" class="zipperCount"  name="zipperCountList" size="6" old="${zc.value}" value="${zc.value}"></td>
                                </c:forEach>
                                <td><a href="javascript:void(0)" class="btnDel">删除</a></td>
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
                            <input type="text" size="10" class="zipperShrinkage" name="zipperShrinkage" value="${purchasing.zipperShrinkage}"/>
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
                                <th>已含缩率</th>
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
                                        <td colspan="5">${z.position}(数量)</td>
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
                                <td><input type="checkbox" name="checkvalue" disabled="disabled" value="1" <c:if test="${z.checkvalue==1}"> checked="checked" </c:if> /></td>
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
            <ul style="float: left;">
                <sec:authorize ifNotGranted="role_planning">
                <li>
                    <div class="buttonActive">
                        <div class="buttonContent">
                            <button type="submit">保存</button>
                        </div>
                    </div>
                </li>
                <span style="color: red;font-size: 25px;">(操作完成后点击“保存”按钮)</span>
                </sec:authorize>
            </ul>
        </div>
    </div>
</form>
