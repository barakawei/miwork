<%@ page import="com.barakawei.lightwork.util.UserContextUtil" %>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="taglib.jsp"%>
<!doctype html>
<html>
<head>
<link href="<c:url value='resources/themes/default/style.css'/>" rel="stylesheet" type="text/css" media="screen"/>
<link href="<c:url value='resources/themes/css/core.css'/>" rel="stylesheet" type="text/css" media="screen"/>
<link href="<c:url value='resources/themes/css/print.css'/>" rel="stylesheet" type="text/css" media="print"/>
<link href="<c:url value='resources/css/style.css'/>" rel="stylesheet" type="text/css"/>
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
<%
    String name = UserContextUtil.getCurrentUser().getName();
%>
<title>MiWork</title>
</head>
<body scroll="no">
<div id="layout">

  <div id="header">
    <div class="headerNav">
      <a style="text-decoration:none;font-size:20px;color: #f5f5f5;margin-left: 20px;line-height: 45px;">MiWork</a>
      <ul class="nav">
        <li><a href="#"><%=name%></a></li>
        <li><a href="${ctx}/logout">退出</a></li>
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
            <sec:authorize ifNotGranted="role_admin">
            <li><a href="${ctx}/purchasing/list" target="navTab" rel="list">采购管理</a></li>
            </sec:authorize>
                <sec:authorize ifAllGranted="role_admin">
              <li><a href="${ctx}/user/list" target="navTab" rel="userList">用户管理</a></li>
                </sec:authorize>
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
                    <h2 class="contentTitle">工作一览</h2>

                    <div class="pageContent sortDrag" selector="h1" layoutH="42">

                        <c:if test="${purchasings.size() >0}">
                        <div class="panel">
                            <h1>待办工作</h1>
                            <div>
                                <table class="list" width="98%">
                                    <thead>
                                    <tr>
                                        <th>工作流程</th>
                                        <th>编号</th>
                                        <th>名称</th>
                                        <th>开始时间</th>
                                        <th>待办任务数</th>
                                        <th>当前进度</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach items="${purchasings}" var="p">
                                    <tr target="purchasing_id" rel="${p.id}">
                                        <td>采购</td>
                                        <td>
                                        <a class="edit" href="${ctx}/purchasing/show/${p.id}" target="navTab"><span>${p.orderNumber}</span></a>
                                        </td>
                                        <td>${p.orderName}</td>
                                        <td>
                                          <fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${p.startTime}' type='both'/>
                                        </td>
                                        <td>${p.toDoList.size()}</td>
                                        <td>${p.progress}%</td>
                                    </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            </div>
                            </c:if>

                        <c:if test="${otherPurchasings.size() >0}">
                        <div class="panel" >
                            <h1>进度跟踪</h1>
                            <div>
                                <table class="list" width="98%">
                                    <thead>
                                    <tr>
                                        <th>工作流程</th>
                                        <th>编号</th>
                                        <th>名称</th>
                                        <th>开始时间</th>
                                        <th>总任务数</th>
                                        <th>完成任务数</th>
                                        <th>当前进度</th>
                                    </tr>
                                    </thead>

                                    <tbody>
                                    <c:forEach items="${otherPurchasings}" var="p">
                                        <tr target="purchasing_id" rel="${p.id}">
                                            <td>采购</td>
                                            <td>
                                                <a class="edit" href="${ctx}/purchasing/show/${p.id}" target="navTab"><span>${p.orderNumber}</span></a>
                                            </td>
                                            <td>${p.orderName}</td>
                                            <td>
                                                <fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${p.startTime}' type='both'/>
                                            </td>
                                            <td>${p.pds.size()}</td>
                                            <td>${p.completedList.size()}</td>
                                            <td>${p.progress}%</td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                       </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>


<div id="footer">Copyright &copy; 2013 MiWork All Rights Reserved.</div>

</body>

</html>
