<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show" ng-show="ajaxFinish">
	<div class="login-background">
		<div class="container-fluid">
			<div class="form-default">
				<div class="panel">
					<img src="${pageContext.request.contextPath}/resources/img/ci/icon_spyder_logo.svg" width="150" height="50" alt="">
					<hr class="mb-4">
					<h2 class="form-signin-heading">회원가입</h2>
					<form ng-submit="clickSubmit()" name="customForm">
						<div class="form-group">
						    <h5>아이디</h5>
						    <input type="text" class="form-control" name="userId" id="userId" placeholder="아이디를 입력하세요." ng-model="data.userId" ng-keyup="ajaxUserIdVerification(customForm.userId.$valid)" aria-describedby="userIdStatus" ng-class="{'is-invalid' : (customForm.userId.$invalid && !customForm.userId.$pristine) || (data.userId.length > 0 && !data.userIdValid), 'is-valid' : data.userId.length > 0 && data.userIdValid}">
  							<div ng-show="data.userId.length > 0 && data.userIdValid" class="alert alert-success" role="alert">사용할 수 있는 아이디입니다.</div>
							<div ng-show="data.userId.length > 0 && !data.userIdValid" class="alert alert-danger" role="alert">중복된 아이디 입니다.</div>
						</div>
						<div class="form-group">
						    <h5>패스워드</h5>
						    <input type="password" class="form-control" name="password" id="password" placeholder="패스워드를 입력하세요." ng-minlength="8" ng-model="data.password" ng-keyup="ajaxPasswordVerification(customForm.password.$valid)" aria-describedby="passwordStatus" ng-class="{'is-invalid' : (customForm.password.$invalid && !customForm.password.$pristine) || (data.password.length > 7 && !data.passwordValid), 'is-valid' : data.password.length > 7 && data.passwordValid}">
						    <div ng-show="customForm.password.$error.minlength" class="alert alert-danger" role="alert">패스워드가 너무 짧습니다.</div>
							<div ng-show="data.password.length > 7 && !data.passwordValid" class="alert alert-danger" role="alert">규칙에 맞게 다시 입력하세요. (8자 이상 영문자 + 숫자 + 특수문자)</div>
							<div ng-show="data.password.length > 7 && data.passwordValid" class="alert alert-success" role="alert">패스워드가 검증되었습니다.</div>
						</div>
						<div class="form-group">
						    <h5>패스워드 확인</h5>
						    <input type="password" class="form-control" name="passwordCheck" id="passwordCheck" placeholder="패스워드를 다시 입력하세요." ng-model="data.passwordCheck" ng-keyup="ajaxPasswordCheckVerification(customForm.passwordCheck.$valid)" aria-describedby="passwordCheckStatus" ng-class="{'is-invalid' : (customForm.passwordCheck.$invalid && !customForm.passwordCheck.$pristine) || (data.passwordCheck.length > 0 && !data.passwordCheckValid), 'is-valid' : data.passwordCheck.length > 0 && data.passwordCheckValid}">
							<div ng-show="data.passwordCheck.length > 0 && !data.passwordCheckValid" class="alert alert-danger" role="alert">패스워드 확인을 다시 입력하세요.</div>
							<div ng-show="data.passwordCheck.length > 0 && data.passwordCheckValid" class="alert alert-success" role="alert">패스워드 확인이 검증되었습니다.</div>
						</div>
						<div class="form-group">
						    <h5>이름</h5>
						    <input type="text" class="form-control" name="name" id="name" placeholder="이름을 입력하세요." ng-model="data.name" maxlength="10">
						</div>
						<div class="form-group">
						    <h5>이메일</h5>
						    <input type="email" class="form-control" name="email" id="email" placeholder="이메일을 입력하세요." ng-model="data.email" ng-keyup="ajaxEmailCheckVerification(customForm.email.$valid)" aria-describedby="emailStatus" ng-class="{'is-invalid' : (customForm.email.$invalid && !customForm.email.$pristine) || (data.email.length > 0 && !data.emailValid), 'is-valid' : data.email.length > 0 && data.emailValid}">
						    <div ng-show="customForm.email.$invalid && !customForm.email.$pristine" class="alert alert-danger" role="alert">이메일을 제대로 입력하세요.</div>
						    <div ng-show="data.email.length > 0 && !data.emailValid && data.email.indexOf('.') == -1" class="alert alert-danger" role="alert">이메일을 제대로 입력하세요.</div>
							<div ng-show="data.email.length > 0 && !data.emailValid && data.email.indexOf('.') != -1" class="alert alert-danger" role="alert">중복된 이메일입니다.</div>
							<div ng-show="data.email.length > 0 && data.emailValid" class="alert alert-success" role="alert">사용할 수 있는 이메일입니다.</div>
						</div>
						<div class="form-group">
						    <h5>연락처</h5>
						    <input type="tel" class="form-control" name="telNo" id="telNo" placeholder="연락처를 입력하세요." ng-model="data.telNo" pattern="\d{2,3}-\d{3,4}-\d{4}" maxlength="13">
						    <!-- 
						    <div ng-show="customForm.email.$invalid && !customForm.email.$pristine" class="alert alert-danger" role="alert">이메일을 제대로 입력하세요.</div>
						    <div ng-show="data.email.length > 0 && !data.emailValid && data.email.indexOf('.') == -1" class="alert alert-danger" role="alert">이메일을 제대로 입력하세요.</div>
							<div ng-show="data.email.length > 0 && !data.emailValid && data.email.indexOf('.') != -1" class="alert alert-danger" role="alert">중복된 이메일입니다.</div>
							<div ng-show="data.email.length > 0 && data.emailValid" class="alert alert-success" role="alert">사용할 수 있는 이메일입니다.</div>
							-->
						</div>
						<div class="form-group">
							<h5>업체명</h5>
							<input type="text" ng-model="data.companyInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0">
						</div>
						<div class="form-group">
							<button type="submit" class="btn btn-lg btn-primary btn-block">완료</button>
						</div>
						<div class="form-group">
							<back-block-btn back="뒤로 가기"></back-block-btn>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</html>