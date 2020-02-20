<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2>엑셀 다운로드</h2>
			<div class="form-group">
				<h5>바텍정보 엑셀 다운로드</h5>
			</div>
			<div class="form-group" style="height:40px;">
				<div class="float-sm-left">
					<button ng-click="excelDown()" class="btn btn-lg btn-primary">다운로드</button>					
				</div>
			</div>
		</div>
	</div>
</div>
</html>