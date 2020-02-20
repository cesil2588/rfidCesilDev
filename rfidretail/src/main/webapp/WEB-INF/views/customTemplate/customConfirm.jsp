<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">Spyder</h3>
	</div>
	<div class="modal-body" id="modal-body">
		{{message}}
	</div>
	<div class="modal-footer">
		<button type="button" class="btn btn-default" ng-click="cancel()">취소</button>
		<button type="button" class="btn btn-primary" ng-click="ok()">확인</button>
    </div>
</html>