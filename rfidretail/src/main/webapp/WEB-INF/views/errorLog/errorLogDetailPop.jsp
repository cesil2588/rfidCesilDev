<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">에러 로그 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
		<h5>에러 로그</h5>
		<div class="form-group">
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th>생성일</th>
						<th>에러코드</th>
						<th>타입</th>
						<th>요청 IP</th>
						<th>등록일</th>
						<th>메소드</th>
						<th>URL</th>
					</tr>
				</thead>
				<tbody>
					<tr class="pointer">
						<td>{{obj.createDate}}</td>
						<td>{{obj.errorCode}}</td>
						<td>{{obj.deviceType == '1' ? 'PC' : 'PDA'}}</td>
						<td>{{obj.remoteIp}}</td>
						<td>{{obj.regDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
						<td>{{obj.requestMethod}}</td>
						<td>{{obj.requestUrl}}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<h5>Query</h5>   
		<div class="form-group">
		    <textarea class="form-control" rows="2" readonly ng-if="obj.requestMethod == 'GET'">{{obj.requestQuery}}</textarea>
		    <textarea class="form-control" rows="20" readonly ng-if="obj.requestMethod != 'GET'">{{obj.requestQuery}}</textarea>
		</div>
		<h5>에러 로그 메시지</h5>   
		<div class="form-group">
		    <textarea class="form-control" rows="20" readonly>{{obj.errorMessage}}</textarea>
		</div>
	</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
    </div>
</html>