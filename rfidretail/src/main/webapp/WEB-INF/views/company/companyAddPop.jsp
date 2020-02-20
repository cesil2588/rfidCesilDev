<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<form ng-submit="clickSubmit()" name="customForm">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">업체 추가</h3>
	</div>
	<div class="modal-body" id="modal-body">
				<div class="card card-body">
					<div class="row">
						<div class="form-group col-6">
							<h5>업체명</h5>
							<input type="text" class="form-control" name="name" id="name" placeholder="업체명을 입력하세요." ng-model="data.name" required>
						</div>
						<div class="form-group col-6">
							<h5>업체코드</h5>
							<input type="text" class="form-control" name="code" id="code" placeholder="업체코드를 입력하세요." ng-model="data.code" maxlength="3" required>
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-6">
							<h5>업체구분</h5>
							<div class="custom-control custom-radio custom-control-inline" ng-repeat="compayType in companyTypeList.codeInfo">
								<input name="inputCompanyType" id="inputCompanyType{{$index}}" type="radio" ng-model="data.type" value="{{compayType.codeValue}}" class="custom-control-input">
								<label class="custom-control-label" for="inputCompanyType{{$index}}">{{compayType.name}}</label>
							</div>
						</div>
						<div class="form-group col-6">
						    <h5>권한</h5>
							<div class="custom-control custom-radio custom-control-inline" ng-repeat="compayRole in companyRoleList.codeInfo">
								<input name="inputRole" id="inputRole{{$index}}" type="radio" ng-model="data.roleInfo.role" value="{{compayRole.codeValue}}" class="custom-control-input">
								<label class="custom-control-label" for="inputRole{{$index}}">{{compayRole.name}}</label>
							</div>
						</div>
					</div>
					<hr class="mb-4">
					<div class="form-group">
						<h5>주소</h5>
						<input type="text" class="form-control" id="address1" placeholder="주소를 입력하세요." ng-model="data.address1">
					</div>
					<div class="form-group">
						<h5>상세 주소</h5>
						<input type="text" class="form-control" id="address2" placeholder="주소를 입력하세요." ng-model="data.address2">
					</div>
					<div class="form-group">
						<h5>연락처</h5>
						<input type="tel" class="form-control" name="telNo" id="telNo" placeholder="연락처를 입력하세요." ng-model="data.telNo" pattern="\d{2,3}-\d{3,4}-\d{4}" maxlength="13" required>
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
				</div>
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button type="submit" class="btn btn-primary">저장</button>
    </div>
    </form>
</html>