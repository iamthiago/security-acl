<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="footer">
	<div class="container">
		<p class="text-muted">© Copyright 2014</p>
	</div>
</div>

<div ng-controller="ModalDemoCtrl">
    <script type="text/ng-template" id="myModalContent.html">
        <div class="modal-body text-center">
            <img src="<c:url value='/resources/images/loading.gif'/>">
        </div>
    </script>
</div>