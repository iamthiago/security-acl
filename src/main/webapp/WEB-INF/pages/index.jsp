<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html lang="en" ng-app="securityApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

	<title>This is secure!</title>

	<jsp:include page="includes/css-include.jsp"/>

</head>
<body>
	<div id="wrap" ng-controller="IndexController">
		<jsp:include page="includes/nav.jsp"/>
		<div ng-view class="container"></div>
	</div>

	<jsp:include page="includes/footer.jsp"/>
	<jsp:include page="includes/js-include.jsp"/>
</body>
</html>