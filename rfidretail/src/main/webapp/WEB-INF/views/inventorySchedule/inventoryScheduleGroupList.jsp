<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish" id="test">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">재고 조사 목록</a></h2>
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
						업체명 : 
						&nbsp;&nbsp;
						<input type="text" ng-model="search.companyInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole == 'sales' || userRole == 'special'">
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
			<table class="table table-striped table-bordered text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th rowspan="2" style="width:80px;">
							<div class="custom-control custom-checkbox">
								<input class="custom-control-input" type="checkbox" ng-model="allCheckFlag" ng-click="allCheck()" id="allCheck"/>
								<label class="custom-control-label" for="allCheck">체크</label>
							</div>
						</th>
						<th ng-click="sort('createDate')" rowspan="2">생성 일자</th>
						<th ng-click="sort('name')" rowspan="2">업체명</th>
						<th rowspan="2">구분</th>
						<th colspan="4">수량</th>
					</tr>
					<tr class="pointer">
						<th>요청 미확정</th>
						<th>요청 확정</th>
						<th>작업 완료</th>
						<th>전체</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat-start="obj in list" class="pointer" ng-if="list.length > 0">
						<td rowspan="3">
							<div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="customCheck{{$index}}" ng-model="obj.check">
								<label class="custom-control-label" for="customCheck{{$index}}"></label>
							</div>
						</td>
						<td ng-click="detail(obj)" rowspan="3">{{obj.createDate}}</td>
						<td ng-click="detail(obj)" rowspan="3">{{obj.name}}</td>
						<td ng-click="detail(obj)">-</td>
						<td ng-click="detail(obj)">{{obj.stat1HeaderCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat2HeaderCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat3HeaderCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat1HeaderCount + obj.stat2HeaderCount + obj.stat3HeaderCount}}</td>
					</tr>
					<tr class="pointer" >
						<td ng-click="detail(obj)">스타일</td>
						<td ng-click="detail(obj)">{{obj.stat1StyleCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat2StyleCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat3StyleCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat1StyleCount + obj.stat2StyleCount + obj.stat3StyleCount}}</td>
					</tr>
					<tr class="pointer" ng-repeat-end>
						<td ng-click="detail(obj)">태그</td>
						<td ng-click="detail(obj)">{{obj.stat1TagCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat2TagCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat4TagCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat3TagCount + obj.stat4TagCount}}</td>
					</tr>
					<tr>
						<td colspan="8" class="text-center" ng-if="list.length == 0">
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
				<!-- 
				<button ng-click="deleteGroup()" class="btn btn-danger">반품 요청 작업 삭제</button>
				<button ng-click="confirmGroup()" class="btn btn-primary">반품 요청 작업 확정</button>
				-->
			</div>
		</div>
	</div>
</div>
</html>