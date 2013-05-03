<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/taglib.jsp"%>
<!doctype html> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet" media="screen"></link>
<link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" media="screen"></link>

<script  type="text/javascript" src="<c:url value="/resources/js/jquery-1.8.3.min.js"/>"></script>
<title>登录</title>
</head>
<body>
  	<header>
  		<div class="navbar navbar-inverse navbar-fixed-top" >
  			 <div class="navbar-inner">
  			 <div class="container"> 
  			 	<a class="brand" href="#">圣华盾</a>
  			 	<div class="nav-collapse collapse navbar-inverse-collapse">	 	
  			 	</div>			 	
  			 </div>	
  			 </div>
  		</div> 
  	</header>
	<div class="content">
		<div class="row">
	<div class="container">
		<form action="${pageContext.request.contextPath}/logincheck" method="post" class="form-signin" id="form">
			<input type="text" class="input-block-level" placeholder="账号" name="j_username"
				required value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"> 
				<input type="password" class="input-block-level" placeholder="密码" name="j_password"
				required >
                <div class="remember">
                    <input type="checkbox" id = "_spring_security_remember_me" name = "_spring_security_remember_me"><label class="remember-password" for = "_spring_security_remember_me" >记住密码</label>
                </div>
				<button class="btn btn-large btn-primary" type="submit">登录</button>
		</form>
	</div>
	</div>	
	</div>
	<footer>
  		<div class="footer navbar navbar-fixed-bottom">
  			<div class="container"> 
    			<p class="muted credit">&copy; 2013 MiWork </p>
  			</div>
  		</div>	
  	</footer>
</body>

</html>
