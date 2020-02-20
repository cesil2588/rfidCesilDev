<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">{{flag == 'all' ? '박스 작업 일괄 추가' : '박스 작업 개별 추가'}}</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<form name="customForm" ng-show="flag == 'all'">
				<div class="card card-body">
					<div class="row">
						<div class="form-group col-6">
							<h5>내용</h5>
							<select class="custom-select" ng-model="data.boxType">
								<option value="01" ng-if="userRole == 'admin' || userRole == 'publishAdmin' || userRole == 'production'">생산</option>
								<option value="02" ng-if="userRole == 'admin' || userRole == 'publishAdmin' || userRole == 'distribution'">물류</option>
								<option value="03" ng-if="userRole == 'admin' || userRole == 'publishAdmin' || userRole == 'sales' || userRole == 'special'">판매</option>
							</select>
						</div>
						<div class="form-group col-6">
							<h5>날짜</h5>
							<div class="form-inline">
								<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="data.createDate" is-open="dateOpenedAll" datepicker-options="dateOptions" close-text="Close" style="width:200px;margin-right:5px;"/>
				          		<span class="input-group-btn" style="margin-right:5px;">
					            	<button type="button" class="btn btn-secondary" ng-click="dateOpenAll()">
					              		달력 <i class="xi-calendar"></i>
					            	</button>
				          		</span>
			          		</div>
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-6">
							<h5>박스번호 시작</h5>
							<input type="number" class="form-control" id="startBoxNum" placeholder="박스번호를 입력하세요." ng-model="data.startBoxNum">
						</div>
						<div class="form-group col-6">
						    <h5>박스번호 종료</h5>
							<input type="number" class="form-control" id="endBoxNum" placeholder="박스번호를 입력하세요." ng-model="data.endBoxNum">
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-6">
							<h5>출발지</h5>
							<input type="text" ng-model="data.startCompanyInfo" placeholder="출발지를 입력하세요." uib-typeahead="company as company.name for company in data.companyList | filter:{name:$viewValue, useYn:'Y'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole != 'admin'">
						</div>
						<div class="form-group col-6" ng-if="type != '03'">
							<h5>도착지</h5>
							<input type="text" ng-model="data.endCompanyInfo" placeholder="도착지를 입력하세요." uib-typeahead="company as company.name for company in data.companyList | filter:{name:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0">
						</div>
					</div>
				</div>
			</form>
			<form name="customForm" ng-show="flag == 'one'">
				<div class="card card-body">
					<div class="row">
						<div class="form-group col-6">
							<h5>내용</h5>
							<select class="custom-select" ng-model="data.boxType" ng-options="code as code.name for code in boxTypeList.codeInfo">
							</select>
						</div>
						<div class="form-group col-6">
							<h5>날짜</h5>
							<div class="form-inline">
								<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="data.createDate" is-open="dateOpenedOne" datepicker-options="dateOptions" close-text="Close" style="width:200px;margin-right:5px;"/>
				          		<span class="input-group-btn" style="margin-right:5px;">
					            	<button type="button" class="btn btn-secondary" ng-click="dateOpenOne()">
					              		달력 <i class="xi-calendar"></i>
					            	</button>
				          		</span>
			          		</div>
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-6">
							<h5>박스번호</h5>
							<input type="number" class="form-control" id="boxNum" placeholder="박스번호를 입력하세요." ng-model="data.boxNum">
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-6">
							<h5>출발지</h5>
							<input type="text" ng-model="data.startCompanyInfo" placeholder="출발지를 입력하세요." uib-typeahead="company as company.name for company in data.companyList | filter:{name:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole != 'admin'">
						</div>
						<div class="form-group col-6" ng-if="type != '03'">
							<h5>도착지</h5>
							<input type="text" ng-model="data.endCompanyInfo" placeholder="도착지를 입력하세요." uib-typeahead="company as company.name for company in data.companyList | filter:{name:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0">
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-6">
							<h5>바코드</h5>
							<div class="form-inline">
								<input type="text" class="form-control col-10" id="barcode" ng-model="barcode" style="flex:0 0 81.8%" ng-readonly="true">&nbsp;&nbsp;
								<button type="button" ng-click="showBarcode()"class="btn btn-primary col-2">출력</button>
							</div>
						</div>
					</div>
					<hr class="mb-4">
					<div class="row" ng-if="barcode != undefined">
						<div class="form-group col-6">
							<h5>출력</h5>
							<table class="table table-bordered table-hover text-center">
								<thead>
									<tr>
										<th>내용</th><td>{{data.boxType.name}}</td>
									</tr>
									<tr>
										<th>날짜</th><td>{{tempCreateDate}}</td>
									</tr>
									<tr>
										<th>박스번호</th><td>{{data.boxNum}}</td>
									</tr>
									<tr>
										<th>출발지</th><td>{{data.startCompanyInfo.name}}</td>
									</tr>
									<tr>
										<th>도착지</th><td>{{data.endCompanyInfo.name}}</td>
									</tr>
									<tr>
										<th colspan="2"><angular-barcode ng-model="barcode" bc-options="bc" bc-class="barcode" bc-type="canvas"></angular-barcode></th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</div>
			</form>
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button type="button" ng-click="addAll()" class="btn btn-primary" ng-if="flag == 'all'">저장</button>
		<button type="button" ng-click="add()" class="btn btn-primary" ng-if="flag != 'all'">저장</button>
	</div>
</html>