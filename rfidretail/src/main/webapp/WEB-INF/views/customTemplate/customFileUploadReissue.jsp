<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">바택재발행결과 업로드</h3>
	</div>
	<div class="modal-body" id="modal-body">
	    <span class="btn btn-primary"><i class="xi-upload" aria-hidden="true"></i><input style="width: 140px;" type="file" class="input" id="myfile" file-model="uploadedFile" accept=".txt">파일 업로드</input></span>
	    
	    <p ng-repeat="file in uploadResultList">
	      	<a href="${pageContext.request.contextPath}/storage/{{file.fileName}}">{{file.fileName}}</a>
	      	<span class="pull-right">
				<button class="btn btn-xs" ng-click="delFile($index)">
				 	<i class="xi-close" aria-hidden="true"></i>
				</button>
			</span>
	    </p>
	</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button class="btn btn-primary" type="button" ng-click="ok()">확인</button>
    </div>
</html>