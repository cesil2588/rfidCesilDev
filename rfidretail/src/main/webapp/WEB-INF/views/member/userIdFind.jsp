<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="fade show" ng-show="ajaxFinish">
		<div class="login-background">
			<div class="container-fluid">
				<div class="form-signin">
					<img src="${pageContext.request.contextPath}/resources/img/ci/icon_spyder_logo.svg" width="150" height="50" alt="">
					<hr class="mb-4">
					<h2 class="form-signin-heading">아이디 찾기</h2>
					<form role="form" ng-submit="find()" style="margin-bottom: 5px;" name="customForm">
					    <input type="email" class="form-control" name="email" id="email" placeholder="이메일을 입력하세요." ng-model="data.email" aria-describedby="emailStatus" ng-class="{'is-invalid' : customForm.email.$invalid && !customForm.email.$pristine , 'is-valid' : data.email.length > 0}" required>
					    <!-- <input type="tel" class="form-control" name="telNo" id="telNo" placeholder="연락처를 입력하세요." ng-model="data.telNo" pattern="\d{2,3}-\d{3,4}-\d{4}" maxlength="13" required style="margin-bottom: 5px;"> -->
					    <button type="submit" class="btn btn-lg btn-primary btn-block">아이디 찾기</button>
				    </form>
				    <back-block-btn back="뒤로 가기"></back-block-btn>
				    <div class="text-center alert alert-danger" ng-show="error" style="margin-top: 5px;">
						사용자 정보를 찾을 수 없습니다.
					</div>
					<div class="text-center alert alert-success" ng-show="success" style="margin-top: 5px;">
						아이디는 {{user.userId}} 입니다.
					</div>
			    </div>
			</div>
		</div>
	</div>
</html>