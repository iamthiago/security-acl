<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<form role="form" name="itemForm" novalidade>
	<div class="form-group">
		<label for="itemNome">
			Nome
		</label>
		<input type="text" class="form-control" ng-model="item.nome" required />
	</div>
	<div class="form-group">
		<label for="itemDesc">
			Descrição
		</label>
		<input type="text" class="form-control" ng-model="item.descricao" required />
	</div>
	<div class="form-group">
		<label for="itemValor">
			Valor
		</label>
		<input type="text" class="form-control" ng-model="item.valor" required />
	</div>
	
	<a ng-click="addItem(item)" ng-class="itemForm.$invalid ? 'btn btn-danger' : 'btn btn-success'" ng-disabled="itemForm.$invalid">Add Item</a>
</form>

<div class="margin-top">
	<table class="table table-striped table-bordered" id="itemTable" ng-show="items.length > 0">
		<thead>
			<tr>
			  <th>Nome</th>
			  <th>Descrição</th>
			  <th>Valor</th>
			</tr>
		</thead>
		<tbody>
			<tr ng-repeat="i in items">
				<td>{{i.nome}}</td>
				<td>{{i.descricao}}</td>
				<td>{{i.valor}}</td>
				<td><button class="btn btn-default btn-xs" ng-click="deleteItem(i)" tooltip="Apagar"><span class="glyphicon glyphicon-remove-circle"></span></button></td>
			</tr>
		</tbody>
	</table>
</div>