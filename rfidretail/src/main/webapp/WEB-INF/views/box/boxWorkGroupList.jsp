<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish" id="test">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">박스 작업 목록</a></h2>
			<div class="card card-body" style="margin-bottom:5px;"> 
				<div class="form-row">
					<div class="form-group form-inline">
						날짜 선택 : 
						&nbsp;&nbsp;
						<select class="custom-select" style="width:120px;margin-right:5px;" ng-model="search.defaultDate" ng-change="changeSearchDate()">
							<option value="1">1달전</option>
							<option value="2">3달전</option>
							<option value="3">6달전</option>
							<option value="4">1년전</option>
						</select>
						&nbsp;&nbsp;
						<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="startDate" is-open="search.startDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:140px;margin-right:5px;"/>
		          		<span class="input-group-btn">
			            	<button type="button" class="btn btn-secondary" ng-click="startDateOpen()">
			              		달력 <i class="xi-calendar"></i>
			            	</button>
		          		</span>
		          		&nbsp;&nbsp;~&nbsp;&nbsp;
						<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="endDate" is-open="search.endDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:140px;margin-right:5px;"/>
		          		<span class="input-group-btn" style="margin-right:5px;">
			            	<button type="button" class="btn btn-secondary" ng-click="endDateOpen()">
			              		달력 <i class="xi-calendar"></i>
			            	</button>
		          		</span>
						&nbsp;&nbsp;
						출발지 : 
						&nbsp;&nbsp;
						<input type="text" ng-model="search.startCompanyInfo" placeholder="출발지명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue, type:'3', useYn:'Y'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole == 'production'">
						&nbsp;&nbsp;
						도착지 : 
						&nbsp;&nbsp;
						<input type="text" ng-model="search.endCompanyInfo" placeholder="도착지명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue, type:'4', useYn:'Y'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-if="userRole != 'production'">
						<input type="text" ng-model="search.endCompanyInfo" placeholder="도착지명 입력" uib-typeahead="company as company.name for company in search.tempCompanyList | filter:{name:$viewValue, type:'4', useYn:'Y'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-if="userRole == 'production'">
					</div>
				</div>
				<div class="form-row">
					<div class="form-inline">
	          			<button ng-click="headSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>
	          		</div>
	          	</div>
          	</div>
			<div class="btn-toolbar justify-content-between pb-1">
          		<div class="btn-group">
					<h5>Total({{total}}건)</h5>          		
          		</div>
          		<div class="input-group">
          			<select class="custom-select" style="width:120px;" ng-model="search.size" ng-change="changeSearchSize()">
						<option value="10" ng-selected="true">10건 보기</option>
						<option value="20">20건 보기</option>
						<option value="50">50건 보기</option>
						<option value="100">100건 보기</option>
					</select>   
          		</div>
          	</div>
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th>
							<div class="custom-control custom-checkbox">
								<input class="custom-control-input" type="checkbox" ng-model="allCheckFlag" ng-click="allCheck()" id="allCheck"/>
								<label class="custom-control-label" for="allCheck">체크</label>
							</div>
						</th>
						<th ng-click="sort('createDate')">날짜</th>
						<th ng-click="sort('startCompanyInfo.name')">출발지</th>
						<th ng-click="sort('endCompanyInfo.name')">도착지</th>
						<th ng-click="sort('startBoxNum')">시작번호</th>
						<th ng-click="sort('endBoxNum')">끝번호</th>
						<th ng-click="sort('stat')">상태</th>
						<th ng-click="sort('regUserInfo.userId')">등록자</th>
						<th ng-click="sort('regDate')">등록일</th>
						<th ng-click="sort('updUserInfo.userId')">수정자</th>
						<th ng-click="sort('updDate')">수정일</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="box in list" class="pointer" ng-if="list.length > 0">
						<td>
							<div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="customCheck{{$index}}" ng-model="box.check">
								<label class="custom-control-label" for="customCheck{{$index}}"></label>
							</div>
						</td>
						<td ng-click="detail(box)">{{box.createDate}}</td>
						<td ng-click="detail(box)">{{box.startCompanyInfo.name}}</td>
						<td ng-click="detail(box)" ng-if="search.type != '03'">
							{{box.endCompanyInfo.name}}
						</td>
						<td ng-click="detail(box)" ng-if="search.type == '03'">
							{{box.startCompanyInfo.name == box.endCompanyInfo.name ? '-' : box.endCompanyInfo.name}}
						</td>
						<td ng-click="detail(box)">{{box.startBoxNum}}</td>
						<td ng-click="detail(box)">{{box.endBoxNum}}</td>
						<td ng-click="detail(box)">
							<span ng-if="box.stat == '1'">프린트 내역 없음</span>
							<span ng-if="box.stat == '2'">프린트 완료</span>
						</td>
						<td ng-click="detail(box)">{{box.regUserInfo.userId ? box.regUserInfo.userId : '-'}}</td>
						<td ng-click="detail(box)">
							<span ng-if="box.regDate">
								{{box.regDate | date:'yyyy-MM-dd'}}<br />
								{{box.regDate | date:'HH:mm:ss'}}
							</span>
							<span ng-if="!box.regDate">
								-
							</span>
						</td>
						<td ng-click="detail(box)">{{box.updUserInfo.userId ? box.updUserInfo.userId : '-'}}</td>
						<td ng-click="detail(box)">
							<span ng-if="box.updDate">
								{{box.updDate | date:'yyyy-MM-dd'}}<br />
								{{box.updDate | date:'HH:mm:ss'}}
							</span>
							<span ng-if="!box.updDate">
								-
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="11" class="text-center" ng-if="list.length == 0">
							No Data
						</td>
					</tr>
				</tbody>
			</table>
			<div>
				<nav class="text-center">
					<ul class="pagination justify-content-center">
						<li class="page-item">
							<a href="" aria-label="Previous" ng-click="goPage(begin)" class="page-link"><span aria-hidden="true">&laquo;</span></a>
				    	</li>
				    	<li class="page-item">
							<a href="" aria-label="Previous" ng-click="goPage(current - 1)" class="page-link"><span aria-hidden="true">&lt;</span></a>
				    	</li>
				    	<li ng-repeat="pageNum in [begin, end] | makeRange" ng-class="{'active' : current == pageNum}" class="page-item"><a href="" ng-click="goPage(pageNum)" class="page-link">{{pageNum}}</a></li>
				    	<li class="page-item">
				    		<a href="" aria-label="Next" ng-click="goPage(current + 1)" class="page-link"><span aria-hidden="true">&gt;</span></a>
				    	</li>
				    	<li class="page-item">
				    		<a href="" aria-label="Next" ng-click="goPage(end)" class="page-link"><span aria-hidden="true">&raquo;</span></a>
				    	</li>
				  	</ul>
				</nav>
			</div>
			<div class="pull-right">
				<button type="button" ng-click="del()" class="btn btn-danger">삭제</button>
				<button ng-click="goPdfDownload('check')" class="btn btn-primary">PDF 다운로드</button>
				<button ng-if="search.type == '03'" ng-click="goExcelDownload()" class="btn btn-primary">엑셀 다운로드</button>
				<!-- <button ng-click="goPdfDownload('all')" class="btn btn-lg btn-primary">대량 PDF 다운로드</button> -->
				<!-- <button ng-click="goAdd('one')" class="btn btn-lg btn-primary">박스 개별 추가</button> -->
				<button ng-click="goAdd('all')" class="btn btn-primary">박스 일괄 추가</button>
			</div>
			<span class="tempBarcodeDiv" style="opacity:0">
				<span ng-repeat="boxGroup in boxWorkList">
					<span ng-repeat="check in boxGroup" repeat-end="onEnd(boxGroup.length)">
						<angular-barcode ng-model="check.barcode" bc-options="bc" bc-class="barcode" bc-type="canvas" id="barcodeImg_{{check.boxSeq}}"></angular-barcode>
					</span>				
				</span>
			</span>
		</div>
	</div>
</div>
</html>