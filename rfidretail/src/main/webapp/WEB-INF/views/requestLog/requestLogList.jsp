<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">HTTP 요청 로그 목록</a></h2>
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
		          	</div>
	          	</div>
	          	<div class="form-row">
					<div class="form-group form-inline">
						URL :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.urlInfo" placeholder="url 입력" uib-typeahead="url as url.value for url in urlList | filter:{value:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" style="width:300px;">
						&nbsp;&nbsp;
						상태코드 :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.status" placeholder="상태코드 입력" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" style="width:140px;">
						&nbsp;&nbsp;
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
						<th ng-click="sort('createDate')">생성일</th>
						<th ng-click="sort('remoteIp')">요청 IP</th>
						<th ng-click="sort('session')">세션</th>
						<th ng-click="sort('requestMethod')">메소드</th>
						<th ng-click="sort('requestUrl')">URL</th>
						<th ng-click="sort('status')">상태코드</th>
						<th ng-click="sort('regUserInfo.userSeq')">등록자</th>
						<th ng-click="sort('regDate')">등록일</th>
						<th ng-click="sort('updDate')">완료일</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="obj in list" ng-click="detail(obj)" class="pointer" ng-if="list.length > 0">
						<td>{{obj.createDate}}</td>
						<td>{{obj.remoteIp}}</td>
						<td>{{obj.session}}</td>
						<td>{{obj.requestMethod}}</td>
						<td>{{obj.requestUrl}}</td>
						<td>{{obj.status}}</td>
						<td>{{obj.regUserInfo.userId}}</td>
						<td>{{obj.regDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
						<td>{{obj.updDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
					</tr>
					<tr>
						<td colspan="9" class="text-center" ng-if="list.length == 0">
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
		</div>
	</div>
</div>
</html>