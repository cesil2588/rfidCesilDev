<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">박스 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<form name="customForm">
				<div class="card card-body">
					<div class="row">
						<div class="form-group col-6">
							<h5>내용</h5>
							<select class="custom-select" ng-model="data.boxType" ng-options="code as code.name for code in boxTypeList.codeInfo" ng-if="rfidTagList.length == 0">
							</select>
							<span ng-if="rfidTagList.length > 0"><span code-name pcode="10001" code="{{data.type}}"></span></span>
						</div>
						<div class="form-group col-6">
							<h5>날짜</h5>
							<div class="form-inline">
								<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="data.createDate" is-open="data.dateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:200px;margin-right:5px;" ng-readonly="rfidTagList.length > 0"/>
				          		<span class="input-group-btn" style="margin-right:5px;" ng-if="rfidTagList.length == 0">
					            	<button type="button" class="btn btn-secondary" ng-click="dateOpen()">
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
							<input type="text" class="form-control" id="boxNum" placeholder="박스번호를 입력하세요." ng-model="data.boxNum" ng-readonly="rfidTagList.length > 0">
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-6">
							<h5>출발지</h5>
							<input type="text" ng-model="data.startCompanyInfo" placeholder="출발지 입력" uib-typeahead="company as company.name for company in data.companyList | filter:{name:$viewValue, useYn:'Y'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole != 'admin' || data.stat != '1'">
						</div>
						<div class="form-group col-6" ng-if="type != '03' || (type == '03' && data.startCompanyInfo.name != data.endCompanyInfo.name)">
							<h5>도착지</h5>
							<input type="text" ng-model="data.endCompanyInfo" placeholder="도착지 입력" uib-typeahead="company as company.name for company in data.companyList | filter:{name:$viewValue, useYn:'Y'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="data.stat != '1'">
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
						<div class="form-group col-6" ng-if="data.arrivalDate">
							<h5>도착 예정일</h5>
							{{data.arrivalDate | date:'yyyy-MM-dd'}}
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
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
										<th colspan="2">
											<div><angular-barcode ng-model="barcode" bc-options="bc" bc-class="barcode" bc-type="canvas" id="barcodeImg"></angular-barcode></div>
											<div><h5>{{barcode}}</h5></div>
										</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
					<hr class="mb-4" ng-if="styleList.list.length > 0">
					<div ng-if="styleList.list.length > 0">
						<h5>스타일 목록</h5>
						<div class="form-group">
							<div class="card card-body">
								<table class="table table-bordered table-hover text-center custom-align-middle">
								<thead>
									<tr class="pointer">
										<th>스타일</th>
										<th>컬러</th>
										<th>사이즈</th>
										<th>오더차수</th>
										<th>수량</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="style in styleList.list" class="pointer" ng-click="detail(style)">
										<td>{{style.style}}</td>
										<td>{{style.color}}</td>
										<td>{{style.size}}</td>
										<td>{{style.orderDegree}}</td>
										<td>{{style.amount}}</td>
									</tr>
								</tbody>
							</table>
							</div>
						</div>
						<hr class="mb-4" ng-if="currentStyle">
						<div class="row"  ng-if="currentStyle">
							<div class="form-group col-12">
								<h5>태그 목록</h5>
								<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
									<thead>
										<tr class="pointer">
											<th>스타일</th>
											<th>컬러</th>
											<th>사이즈</th>
											<th>오더차수</th>
											<th>일련번호</th>
										</tr>
									</thead>
									<tbody>
										<tr ng-repeat="tag in currentStyle.rfidTagList | orderBy : 'rfidTag'" class="pointer">
											<td>{{currentStyle.style}}</td>
											<td>{{currentStyle.color}}</td>
											<td>{{currentStyle.size}}</td>
											<td>{{currentStyle.orderDegree}}</td>
											<td>{{tag.rfidTag.substr(27)}}</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button type="button" ng-click="pdfDownload()" class="btn btn-primary">PDF 다운로드</button>
		<!-- <button type="button" ng-if="data.stat == '1'" ng-click="del()" class="btn btn-danger">삭제</button> -->
		<!-- <button type="button" ng-if="data.stat == '1'" ng-click="mod()" class="btn btn-primary">수정</button>  -->
	</div>
</html>