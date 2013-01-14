<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='sec'
	uri='http://www.springframework.org/security/tags'%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!doctype html> 
<html>
<head>


<title>My JSP 'admin.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css" href="styles.css">

</head>

<body>
	<sec:authentication property="principal.username" />
	<sec:authorize access="hasAuthority('add')">
		<a href="${ctx}/add">add</a>
	</sec:authorize>
	<sec:authorize ifAllGranted="delete">
		<a href="${ctx}/delete">delete</a>
	</sec:authorize>
	<sec:authorize ifAllGranted="save">
		<a href="${ctx}/save">save</a>
	</sec:authorize>
	<sec:authorize ifAllGranted="update">
		<a href="${ctx}/update">update</a>
	</sec:authorize>
	<sec:authorize access="hasRole('del')">
		<a href="${ctx}/del">del</a>
	</sec:authorize>
	<sec:authorize access="hasAuthority('changePassword')">
		<a href="${ctx}/changePassword">changePassword</a>
	</sec:authorize>
	这是home员界面。
	<br>
<form method="post">
  <div class="control-group">
   
    <div class="controls">
      <input type="text" name="some_field" required data-validation-required-message=
    "You must agree to the terms and conditions"/>
      <p class="help-block"></p>
    </div>
  </div>
  <button class="btn btn-large btn-primary" type="submit">test</button>
</form>

</body>
</html>
