<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<form name="userForm" novalidate>
	<div class="form-group">
		<label for="username">
			User
		</label>
		<input type="text" ng-model="user.usuario" class="form-control" placeholder="username" autocomplete="off" required />
	</div>
	<div class="form-group">
		<label for="password">
			Password
		</label>
		<input type="password" ng-model="user.senha" class="form-control" placeholder="password" autocomplete="off" required />
	</div>
	<div class="checkbox">
		<label>
			<input type="checkbox" ng-model="user.enabled" /> Enabled?
		</label>
	</div>
	
	<h4>Permissions</h4>
	<div>
		<ul>
			<li ng-repeat="p in permissions">
				<div class="checkbox">
					<label>
						<input type="checkbox"> {{p.nome}}
					</label>
				</div>
				<ul ng-show="p.menus.length > 0">
					<li ng-repeat="c in p.menus">
						<div class="checkbox">
							<label>
								<input type="checkbox"> {{c.nome}}
							</label>
						</div>
						
					</li>
				</ul>
			</li>
		</ul>
	</div>
	
	<a ng-click="addUser(user)" class="btn btn-default">Create User</a>
</form>

<div class="margin-top">
	<table class="table table-striped table-bordered" id="userTable">
		<thead>
			<tr>
				<th>Usuário</th>
				<th>Senha</th>
				<th>Habilitado</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="u in users">
				<td>{{u.username}}</td>
				<td>{{u.password}}</td>
				<td>{{u.enabled}}</td>
			</tr>
		</tbody>
	</table>
</div>