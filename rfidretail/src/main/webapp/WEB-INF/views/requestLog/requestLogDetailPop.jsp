<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">HTTP 요청 로그 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
		<h5>HTTP 요청 로그</h5>
		<div class="form-group">
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th ng-click="sort('createDate')">생성일</th>
						<th ng-click="sort('remoteIp')">요청 IP</th>
						<th ng-click="sort('session')">세션</th>
						<th ng-click="sort('requestMethod')">메소드</th>
						<th ng-click="sort('requestUrl')">URL</th>
						<th ng-click="sort('status')">상태코드</th>
						<th ng-click="sort('regUserInfo.userSeq')">등록자</th>
						<th ng-click="sort('regDate')">등록일</th>
						<th ng-click="sort('updDate')">완료일</th>
					</tr>
				</thead>
				<tbody>
					<tr class="pointer">
						<td>{{obj.createDate}}</td>
						<td>{{obj.remoteIp}}</td>
						<td>{{obj.session}}</td>
						<td>{{obj.requestMethod}}</td>
						<td>{{obj.requestUrl}}</td>
						<td>{{obj.status}}</td>
						<td>{{obj.regUserInfo.userId}}</td>
						<td>{{obj.regDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
						<td>{{obj.updDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<h5>Request Query</h5>
		<div class="form-group">
		    <textarea class="form-control" rows="2" readonly ng-if="obj.requestMethod == 'GET'">{{obj.requestQuery}}</textarea>
		    <textarea class="form-control" rows="20" readonly ng-if="obj.requestMethod != 'GET'">{{obj.requestQuery}}</textarea>
		</div>
		<h5>Response Body</h5>
		<div class="form-group">
		    <textarea class="form-control" rows="20" readonly>{{obj.responseBody}}</textarea>
		</div>
		<h5>헤더 메시지</h5>
		<div class="form-group">
		    <textarea class="form-control" rows="14" readonly>{{obj.header}}</textarea>
		</div>
		<h5>User-Agent</h5>
		<div class="form-group">
		    <textarea class="form-control" rows="2" readonly>{{obj.userAgent}}</textarea>
		</div>
	</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
    </div>
</html>