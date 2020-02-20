<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">업체 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<form name="customForm">
				<div class="card card-body" ng-if="data.erpYn == 'Y' && data.type != '3'">
					<div class="row">
						<div class="form-group col-6">
							<h5>업체명</h5>
							{{data.name}}
						</div>
						<div class="form-group col-6">
							<h5>업체코드</h5>
							{{data.code}}
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-3">
							<h5>ERP 거래처코드</h5>
							{{data.customerCode}}
						</div>
						<div class="form-group col-3">
							<h5>ERP 코너명</h5>
							{{data.cornerName}}
						</div>
						<div class="form-group col-3">
							<h5>ERP 코너코드</h5>
							{{data.cornerCode}}
						</div>
						<div class="form-group col-3">
							<h5>ERP 폐점여부</h5>
							{{data.closeYn}}
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-6">
						    <h5>업체구분</h5>
							<span code-name pcode="10002" code="{{data.type}}"></span>
						</div>
						<div class="form-group col-6">
						    <h5>권한</h5>
							<span code-name pcode="10003" code="{{data.roleInfo.role}}"></span>
						</div>
					</div>
					<hr class="mb-4">
					<div class="form-group">
						<h5>주소</h5>
						{{data.address1}}
					</div>
					<div class="form-group">
						<h5>상세 주소</h5>
						{{data.address2}}
					</div>
					<div class="form-group">
						<h5>연락처</h5>
						{{data.telNo}}
					</div>
					<hr class="mb-4">
					<div class="form-group">
					    <h5>사용 여부</h5>
					    {{data.useYn}}
					</div>
					<hr class="mb-4">
					<div class="form-group">
					    <h5>등록 날짜</h5>
					    {{data.regDate | date:'yyyy-MM-dd HH:mm:ss'}}
					</div>
					<div class="form-group">
					    <h5>수정 날짜</h5>
					    {{data.updDate | date:'yyyy-MM-dd HH:mm:ss'}}
					</div>
				</div>
				<div class="card card-body" ng-if="data.erpYn == 'N' || (data.erpYn == 'Y' && data.type == '3')">
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
					
					<hr class="mb-4" ng-if="data.erpYn == 'Y' && data.type == '3'">
					<div class="row" ng-if="data.erpYn == 'Y' && data.type == '3'">
						<div class="form-group col-3">
							<h5>ERP 거래처코드</h5>
							{{data.customerCode}}
						</div>
						<div class="form-group col-3">
							<h5>ERP 코너명</h5>
							{{data.cornerName}}
						</div>
						<div class="form-group col-3">
							<h5>ERP 코너코드</h5>
							{{data.cornerCode}}
						</div>
						<div class="form-group col-3">
							<h5>ERP 폐점여부</h5>
							{{data.closeYn}}
						</div>
					</div>
					<hr class="mb-4">
					<div class="row" ng-if="data.erpYn == 'N'">
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
					<div class="row" ng-if="data.erpYn == 'Y' && data.type == '3'">
						<div class="form-group col-6">
						    <h5>업체구분</h5>
							<span code-name pcode="10002" code="{{data.type}}"></span>
						</div>
						<div class="form-group col-6">
						    <h5>권한</h5>
							<span code-name pcode="10003" code="{{data.roleInfo.role}}"></span>
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
					<hr class="mb-4">
					<div class="form-group">
					    <h5>등록 날짜</h5>
					    {{data.regDate | date:'yyyy-MM-dd HH:mm:ss'}}
					</div>
					<div class="form-group">
					    <h5>수정 날짜</h5>
					    {{data.updDate | date:'yyyy-MM-dd HH:mm:ss'}}
					</div>
				</div>
			</form>
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button ng-click="mod()" class="btn btn-primary" ng-if="data.erpYn == 'N' || (data.erpYn == 'Y' && data.type == '3')">수정 완료</button>
    </div>
</html>