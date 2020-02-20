<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">사용자 추가</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<form name="customForm">
				<div class="card card-body">
					<div class="row">
						<div class="form-group col-6">
							<h5>아이디</h5>
							<input type="text" class="form-control" name="userId" id="userId" placeholder="아이디를 입력하세요." ng-model="data.userId" ng-keyup="ajaxUserIdVerification(customForm.userId.$valid)" aria-describedby="userIdStatus" ng-class="{'is-invalid' : (customForm.userId.$invalid && !customForm.userId.$pristine) || (data.userId.length > 0 && !data.userIdValid), 'is-valid' : data.userId.length > 0 && data.userIdValid}">
		  					<div ng-show="data.userId.length > 0 && data.userIdValid" class="alert alert-success mt-1" role="alert">사용할 수 있는 아이디입니다.</div>
							<div ng-show="data.userId.length > 0 && !data.userIdValid" class="alert alert-danger mt-1" role="alert">중복된 아이디 입니다.</div>
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-6">
							<h5>패스워드</h5>
							<input type="password" class="form-control" name="password" id="password" placeholder="패스워드를 입력하세요." ng-minlength="8" ng-model="data.password" ng-keyup="ajaxPasswordVerification(customForm.password.$valid)" aria-describedby="passwordStatus" ng-class="{'is-invalid' : (customForm.password.$invalid && !customForm.password.$pristine) || (data.password.length > 7 && !data.passwordValid), 'is-valid' : data.password.length > 7 && data.passwordValid}">
							<div ng-show="customForm.password.$error.minlength" class="alert alert-danger mt-1" role="alert">패스워드가 너무 짧습니다.</div>
							<div ng-show="data.password.length > 7 && !data.passwordValid" class="alert alert-danger mt-1" role="alert">패스워드 규칙에 맞게 다시 입력하세요. (8자 이상 영문자 + 숫자 + 특수문자)</div>
							<div ng-show="data.password.length > 7 && data.passwordValid" class="alert alert-success mt-1" role="alert">패스워드가 검증되었습니다.</div>
						</div>
						<div class="form-group col-6">
							<h5>패스워드 확인</h5>
							<input type="password" class="form-control" name="passwordCheck" id="passwordCheck" placeholder="패스워드를 다시 입력하세요." ng-model="data.passwordCheck" ng-keyup="ajaxPasswordCheckVerification(customForm.passwordCheck.$valid)" aria-describedby="passwordCheckStatus" ng-class="{'is-invalid' : (customForm.passwordCheck.$invalid && !customForm.passwordCheck.$pristine) || (data.passwordCheck.length > 0 && !data.passwordCheckValid), 'is-valid' : data.passwordCheck.length > 0 && data.passwordCheckValid}">
							<div ng-show="data.passwordCheck.length > 0 && !data.passwordCheckValid" class="alert alert-danger mt-1" role="alert">패스워드 확인을 다시 입력하세요.</div>
							<div ng-show="data.passwordCheck.length > 0 && data.passwordCheckValid" class="alert alert-success mt-1" role="alert">패스워드 확인이 검증되었습니다.</div>
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-6">
							<h5>연동 이메일</h5>
							<ul class="list-group">
							  	<li class="list-group-item" ng-repeat="info in data.userEmailInfo">
							  		{{info.email}}
							  		<span class="pull-right">
				                        <button class="btn btn-xs" ng-click="delEmail($index)">
				                            <i class="xi-close" aria-hidden="true"></i>
				                        </button>
				                    </span>
							  	</li>
							</ul>
							<h5 style="margin-top:10px;">이메일 추가</h5>
							<div class="form-inline">
								<input type="email" class="form-control col-9" name="email" id="email" placeholder="이메일을 입력하세요." ng-model="data.email" ng-keyup="ajaxEmailCheckVerification(customForm.email.$valid)" aria-describedby="emailStatus" ng-class="{'is-valid' : (customForm.email.$invalid && !customForm.email.$pristine) || (data.email.length > 0 && !data.emailValid && data.email != oriEmail), 'is-valid' : data.email.length > 0 && data.emailValid && data.email != oriEmail}" ng-pattern='/^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i'>
								<span class="col-1"></span>
								<button class="btn btn-primary col-2" ng-click="addEmail()">추가</button>
							</div>
							<div ng-show="customForm.email.$invalid && !customForm.email.$pristine" class="alert alert-danger mt-1">이메일을 제대로 입력하세요.</div>
							<div ng-show="data.email.length > 0 && data.emailValid" class="alert alert-success mt-1">사용할 수 있는 이메일입니다.</div>
							<div ng-show="data.email.length > 0 && !data.emailValid" class="alert alert-danger mt-1">중복된 이메일입니다.</div>
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-6">
							<h5>업체명</h5>
							<input type="text" ng-model="data.companyInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0">
						</div>
						<div class="form-group col-6">
							<h5>접근 권한</h5>
					    	<span code-name-fix pCode="10003" code="{{data.companyInfo.roleInfo.role}}"></span>						
						</div>
					</div>
					<hr class="mb-4">
					<div class="form-group">
					    <h5>사용 여부</h5>
					    <div class="custom-control custom-radio custom-control-inline">
							<input name="inputUseYn" id="inputUseYn1" type="radio" ng-model="data.useYn" value="Y" class="custom-control-input">
							<label class="custom-control-label" for="inputUseYn1">사용</label>
						</div>
						<div class="custom-control custom-radio custom-control-inline">
							<input name="inputUseYn" id="inputUseYn2" type="radio" ng-model="data.useYn" value="N" class="custom-control-input">
							<label class="custom-control-label" for="inputUseYn2">사용안함</label>
						</div>
					</div>
					<div class="form-group">
					    <h5>관리자 확인 여부</h5>
					    <div class="custom-control custom-radio custom-control-inline">
							<input name="inputCheckYn" id="inputCheckYn1" type="radio" ng-model="data.checkYn" value="Y" class="custom-control-input">
							<label class="custom-control-label" for="inputCheckYn1">사용</label>
						</div>
						<div class="custom-control custom-radio custom-control-inline">
							<input name="inputCheckYn" id="inputCheckYn2" type="radio" ng-model="data.checkYn" value="N" class="custom-control-input">
							<label class="custom-control-label" for="inputCheckYn2">사용안함</label>
						</div>
					</div>
				</div>
			</form>
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button type="button" ng-click="add()" class="btn btn-primary">저장</button>
    </div>
</html>