/**
 *
 * Created with IntelliJ IDEA.
 * User: baraka
 * Date: 13-6-3
 * Time: 下午10:26
 * To change this template use File | Settings | File Templates.
 */

var oldShrinkage;
$(document).ready(function(){
    oldShrinkage = $(".zipperShrinkage").val();
    if(oldShrinkage=="undefined" || oldShrinkage == null || $.trim(oldShrinkage)==""){
        oldShrinkage = 1;
    }else{
        oldShrinkage = (1+oldShrinkage/100);
    }
    $("input").die("keyup");
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

$(".checkvalue").die("click");
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
                console.log(old);
                var s = old * shrinkage;
                console.log(s);
                zipper.val(s.toFixed(2));
            }else{
                var sh = old / shrinkage;
                zipper.val(sh.toFixed(2));

            }
        }
    });

});

$(".zipperShrinkage").die("change");
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
$(".warehouseCount input").die("blur");
$(".warehouseCount input").live("blur",function(){
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
$(".planEntryCount input").die("blur");
$(".planEntryCount input").live("blur",function(){
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
$(".actualUse input").die("blur");
$(".actualUse input").live("blur",function(){
    var actualUse = $(this).val();
    var tr = $(this).closest("tr");
    var confirmUse= tr.find(".confirmUse").text();
    var exceedUse= actualUse*1-confirmUse*1;
    tr.find(".exceedUse").text(exceedUse.toFixed(2));
});
$(".actualConsume input").die("blur");
$(".actualConsume input").live("blur",function(){
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
$(".consume input").die("blur");
$(".consume input").live("blur",function(){
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

$(".orderCount input").die("blur");
$(".orderCount input").live("blur",function(){
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

$(".pdData input").die("change");
$(".pdData input").live("change",function(){
    $(".pageForm").submit();
});

$(".pdData #calendar .days dd").die("click");
$(".pdData #calendar .days dd").live("click",function(){
    intervalName = setInterval(handle,1000);
});

$(".pdData #calendar button").die("click");
$(".pdData #calendar button").live("click",function(){
    intervalName = setInterval(handle,1000);
});

$(".pdData #suggest li").die("click");
$(".pdData #suggest li").live("click",function(){
    $(".pageForm").submit();
});

function handle(){
    $(".pageForm").submit();
    clearInterval(intervalName);
}

function done(json){

}
