<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="taglib.jsp"%>
<!doctype html>
<html>
<head>
<link href="<c:url value='resources/themes/default/style.css'/>" rel="stylesheet" type="text/css" media="screen"/>
<link href="<c:url value='resources/themes/css/core.css'/>" rel="stylesheet" type="text/css" media="screen"/>
<link href="<c:url value='resources/themes/css/print.css'/>" rel="stylesheet" type="text/css" media="print"/>
<!--[if IE]>
<link href="<c:url value='resources/themes/css/ieHack.css'/>" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<script src="<c:url value='resources/js/jquery-1.7.2.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='resources/js/dwz.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='resources/js/speedup.js'/>" type="text/javascript"></script>
<script src="<c:url value='resources/js/jquery.cookie.js'/>" type="text/javascript"></script>
<script src="<c:url value='resources/js/jquery.validate.min.js'/>" type="text/javascript"></script>
<script src="<c:url value='resources/js/jquery.bgiframe.js'/>" type="text/javascript"></script>
<script src="${ctx}/resources/js/dwz.regional.zh.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
  DWZ.init("<c:url value='resources/dwz.frag.xml'/>", {
    loginUrl:"login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
    //		loginUrl:"login.html",	// 跳到登录页面
    statusCode:{ok:200, error:300, timeout:301}, //【可选】
    pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
    debug:false,	// 调试模式 【true|false】
    callback:function(){
      initEnv();
    }
  });
});

</script>

<title></title>
</head>
<body scroll="no">
<div id="layout">
  <div id="header">
    <div class="headerNav">
      <a class="logo">lightwork</a>
      <ul class="nav">
        <li><a href="changepwd.html" target="dialog" width="600">设置</a></li>
        <li><a href="login.html">退出</a></li>
      </ul>
    </div>
  </div>
  <!-- navMenu -->


  <div id="leftside">
    <div id="sidebar_s">
      <div class="collapse">
        <div class="toggleCollapse"><div></div></div>
      </div>
    </div>
    <div id="sidebar">
      <div class="toggleCollapse"><h2>主菜单</h2><div>collapse</div></div>

      <div class="accordion" fillSpace="sidebar">
        <div class="accordionHeader">
          <h2><span>Folder</span>我的工作</h2>
        </div>
        <div class="accordionContent">
          <ul class="tree treeFolder">
            <li><a href="${ctx}/purchasing/task" target="navTab" rel="task">待办任务</a></li>
            <li><a href="${ctx}/purchasing/list" target="navTab" rel="list">采购计划</a></li>
          </ul>
        </div>
      </div>

    </div>
  </div>
    <div id="container">
        <div id="navTab" class="tabsPage">
            <div class="tabsPageHeader">
                <div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
                    <ul class="navTab-tab">
                        <li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
                    </ul>
                </div>
                <div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
                <div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
                <div class="tabsMore">more</div>
            </div>
            <ul class="tabsMoreList">
                <li><a href="javascript:;">我的主页</a></li>
            </ul>
            <div class="navTab-panel tabsPageContent layoutBox">
                <div class="page unitBox">
                    <div class="accountInfo">
                        <div class="alertInfo">
                        </div>
                        <div class="right">
                        </div>
                    </div>

                    <div class="pageFormContent" layoutH="80" style="margin-right:230px">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div id="footer">Copyright &copy; 2013 lightwork </div>

</body>

</html>
