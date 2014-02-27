<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Cleartech - BDA</a>
		</div>
		<div class="collapse navbar-collapse">
			<div ng-controller="MenuController">
				<ul class="nav navbar-nav">
					<choice-tree ng-model="myTree"></choice-tree>
				</ul>
			</div>
		</div>
	</div>
</div>