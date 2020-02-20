<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<form ng-submit="goMailAction()">
			<h2><a href="" ng-click="reload()">메일 전송</a></h2>
			<table class="table table-bordered">
				<tr>
					<th class="pointer">보낼 이메일</th>
					<td style="width: 600px;"><input style="width: 600px;" type="text" ng-model='data.mailSend' id="mailSend" name="mailSend"/></td>
				</tr>
				<tr>
					<th class="pointer">제목</th>
					<td><input style="width: 600px;" type="text" ng-model="data.mailTitle" id="mailTitle" name="mailTitle"/></td>
				</tr>
				<!-- <tr>
					<th class="pointer">첨부파일</th>
					<td><input type="file" style="width: 600px;" type="text" ng-model="" id="" name=""/></td>
				</tr> -->
				<tr>
					<th class="pointer" style="width: 200px;height: 200px;">내용</th>
					<td style="width: 600px;height: 200px;"><textarea ng-model="data.mailContents" id="mailContents" name="mailContents" cols="73" rows="10" /></td>
				</tr>
			</table>
			
			<div class="pull-right">
				<button type="submit" class="btn btn-lg btn-primary">전송</button>
			</div>			
			</form>
		</div>
	</div>
</div>
</html>