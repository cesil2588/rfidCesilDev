<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="fade show" ng-show="ajaxFinish">
		<div class="login-background">
			<div class="container-fluid">
				<div class="form-signin">
					<img src="${pageContext.request.contextPath}/resources/img/ci/icon_spyder_logo.png" width="150" height="50" alt="">
					<hr class="mb-4">
				    <div class="text-center row" style="margin-bottom: 5px;">
				    	<div class="col-12">
				    		<p ng-if="uploadResult.data" style="margin-top: 5px;">
						    	<!-- <button class="btn btn-lg btn-secondary btn-block" ng-click="downloadLog(uploadResult.data)"><i class="xi-android" aria-hidden="true"></i> PDA App 다운로드</button>  -->
						    	<!-- <a class="btn btn-lg btn-secondary btn-block" href="${pageContext.request.contextPath}/storage/{{uploadResult.data}}" ng-click="downloadLog()"><i class="xi-android" aria-hidden="true"></i> PDA App 다운로드</a> -->
						    	<a class="btn btn-lg btn-secondary btn-block" href="${pageContext.request.contextPath}/storage/app/{{uploadResult.data}}" ng-click="downloadLog()"><i class="xi-android" aria-hidden="true"></i> PDA App 다운로드</a>						    	
						    	<button type="button" class="btn btn-lg btn-secondary btn-block" ng-click="logout()"><i class="xi-log-out" aria-hidden="true"></i> 로그아웃</button>
							</p>
				    	</div>
				    </div>
			    </div>
			</div>
		</div>
	</div>
</html>