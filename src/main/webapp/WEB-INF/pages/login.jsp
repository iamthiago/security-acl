<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login</title>

	<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap.min.css'/>" type="text/css">
	<link rel="stylesheet" href="<c:url value='/resources/css/bootstrap-theme.min.css'/>" type="text/css">
	<link rel="stylesheet" href="<c:url value='/resources/css/login.css'/>" type="text/css">
	
	<script src="<c:url value='/resources/js/libs/jquery/jquery-1.10.2.min.js'/>"></script>
	<script src="<c:url value='/resources/js/libs/bootstrap/bootstrap.min.js'/>"></script>
	
</head>
<body onload='document.f.j_username.focus();'>
	<div class="container">
		<form name='f' action="<c:url value='j_spring_security_check' />" method='post' class="form-signin">
			<h2 class="form-signin-heading">Please sign in</h2>
			<input type='text' name='j_username' class="input-block-level" placeholder="username"/><br>
			<input type='password' name='j_password' class="input-block-level" placeholder="password"/><br>
			<button class="btn btn-large btn-primary" type="submit">Sign in</button>
		</form>
	</div>
</body>
</html>