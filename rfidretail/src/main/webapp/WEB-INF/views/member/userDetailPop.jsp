<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">사용자 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<form name="customForm">
				<div class="card card-body">
					<div class="row">
						<div class="form-group col-6" ng-if="userRole == 'admin'">
							<h5>아이디</h5>
							<input type="text" class="form-control" name="userId" id="userId" placeholder="아이디를 입력하세요." ng-model="data.userId" ng-keyup="ajaxUserIdVerification(customForm.userId.$valid)" aria-describedby="userIdStatus"  ng-class="{'is-valid' : (customForm.userId.$invalid && !customForm.userId.$pristine) || (data.userId.length > 0 && !data.userIdValid && data.userId != oriUserId), 'is-valid' : data.userId.length > 0 && data.userIdValid && data.userId != oriUserId}">
			  				<div ng-show="data.userId.length > 0 && data.userIdValid && data.userId != oriUserId" class="alert alert-success">사용할 수 있는 아이디입니다.</div>
							<div ng-show="data.userId.length > 0 && !data.userIdValid && data.userId != oriUserId" class="alert alert-danger">중복된 아이디 입니다.</div>
						</div>
						<div class="form-group col-6" ng-if="userRole != 'admin'">
							<h5>아이디</h5>
							{{data.userId}}
						</div>
						<!-- 
						<div class="form-group col-6">
						    <h5>이름</h5>
						    <input type="text" class="form-control" name="name" id="name" placeholder="이름을 입력하세요." ng-model="data.name" maxlength="10">
						</div>
						-->
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-6">
							<h5>패스워드</h5>
							<input type="password" class="form-control" id="inputPassword" placeholder="패스워드를 입력하세요." ng-model="data.password">
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
						<div class="form-group col-6" ng-if="userRole == 'admin'">
							<h5>업체명</h5>
							<input type="text" ng-model="data.companyInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0">
						</div>
						<div class="form-group col-6" ng-if="userRole != 'admin'">
							<h5>업체명</h5>
							{{data.companyInfo.name}}
						</div>
						<div class="form-group col-6">
							<h5>접근 권한</h5>
					    	<span code-name-fix pCode="10003" code="{{data.companyInfo.roleInfo.role}}"></span>
						</div>
					</div>
					<hr class="mb-4">
					<div class="form-group" ng-if="userRole == 'admin'">
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
					<div class="form-group" ng-if="userRole == 'admin'">
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
					<hr class="mb-4" ng-if="userRole == 'admin'">
					<div class="form-group">
					    <h5>등록 일시</h5>
					    {{data.regDate | date:'yyyy-MM-dd HH:mm:ss'}}
					</div>
					<div class="form-group">
					    <h5>수정 일시</h5>
					    {{data.updDate | date:'yyyy-MM-dd HH:mm:ss'}}
					</div>
					<div class="form-group">
					    <h5>최근 로그인 일시</h5>
					    {{data.lastLoginDate | date:'yyyy-MM-dd HH:mm:ss'}}
					</div>
				</div>
			</form>
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button ng-click="modUser()" class="btn btn-primary">수정 완료</button>
		<button ng-if="userRole == 'admin' && data.lastLoginDate == undefined && data.checkYn == 'N'" ng-click="delUser()" class="btn btn-danger">삭제</button>
    </div>
</html>