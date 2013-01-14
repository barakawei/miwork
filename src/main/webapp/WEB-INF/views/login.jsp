<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />



<!doctype html> 
<html>
<head>

<title>登录</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

</head>
<body>
	<div class="container">
		<form action="${ctx}/logincheck" method="post" class="form-signin" id="form">
			<input type="text" class="input-block-level" placeholder="Username" name="j_username"
				required value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"> 
				<input type="password" class="input-block-level" placeholder="Password" name="j_password" 
				required > 
				<label class="checkbox">
      			<input type="checkbox" value="remember-me">
                 Remember </label>
                 

				<button class="btn btn-large btn-primary" type="submit">Sign in</button>
		</form>
		<div class="nav-signin">
			<a title="Password Lost and Found" href="lostpassword.html">Lost
				your password?</a>
		</div>
	</div>
</body>

</html>
