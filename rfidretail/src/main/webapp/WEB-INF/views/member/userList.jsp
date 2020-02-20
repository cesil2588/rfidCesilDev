<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">사용자 목록</a></h2>
			<div class="card card-body" style="margin-bottom:5px;"> 
				<div class="form-inline">
					권한 : 
					&nbsp;&nbsp;
					<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.role">
						<option value="all">전체</option>
						<option ng-repeat="role in roleList.codeInfo" value="{{role.codeValue}}">{{role.name}}</option>
					</select>
					&nbsp;&nbsp;
					업체명 : 
					&nbsp;&nbsp;
					<input type="text" ng-model="search.companyInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue, useYn:'Y'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0">
					&nbsp;&nbsp;
					관리자확인여부 : 
					&nbsp;&nbsp;
					<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.checkYn">
						<option value="all">전체</option>
						<option value="Y">Y</option>
						<option value="N">N</option>
					</select>
					&nbsp;&nbsp;
	          		<button ng-click="headSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>
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
						<th ng-click="sort('userId')">아이디</th>
						<th ng-click="sort('companyInfo.name')">업체명</th>
						<th ng-click="sort('companyInfo.role')">권한</th>
						<th ng-click="sort('checkYn')">관리자확인여부</th>
						<th ng-click="sort('useYn')">사용여부</th>
						<th ng-click="sort('lastLoginDate')">최근로그인일시</th>
						<th ng-click="sort('regDate')">등록일시</th>
						<th ng-click="sort('updDate')">수정일시</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="user in list" ng-click="userDetail(user)" class="pointer" ng-if="list.length > 0">
						<td>{{user.userId}}</td>
						<td>{{user.companyInfo.name ? user.companyInfo.name : '-'}}</td>
						<td><span code-name pcode="10003" code="{{user.companyInfo.roleInfo.role}}"></span></td>
						<td>{{user.checkYn}}</td>
						<td>{{user.useYn}}</td>
						<td>
							<span ng-if="user.lastLoginDate">
								{{user.lastLoginDate | date:'yyyy-MM-dd'}}<br />
								{{user.lastLoginDate | date:'HH:mm:ss'}}
							</span>
							<span ng-if="!user.lastLoginDate">
								-
							</span>
						</td>
						<td>
							<span ng-if="user.regDate">
								{{user.regDate | date:'yyyy-MM-dd'}}<br />
								{{user.regDate | date:'HH:mm:ss'}}
							</span>
							<span ng-if="!user.regDate">
								-
							</span>
						</td>
						<td>
							<span ng-if="user.updDate">
								{{user.updDate | date:'yyyy-MM-dd'}}<br />
								{{user.updDate | date:'HH:mm:ss'}}
							</span>
							<span ng-if="!user.updDate">
								-
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="8" class="text-center" ng-if="list.length == 0">
							No Data
						</td>
					</tr>
				</tbody>
			</table>
			<div class="search-margin">
				<div class="d-flex justify-content-center">
					<form class="form-inline" ng-submit="clickSearch()">
						<div class="form-group">
							<select class="custom-select" ng-model="search.option" style="width:100px;"> 
								<option value="userId">아이디</option>
							</select>&nbsp;
							<input type="text" class="form-control" placeholder="검색" ng-model="search.text" style="width:300px;" id="inputSearch">&nbsp;
							<button type="submit" class="btn btn-default">검색</button>
						</div>
					</form>
				</div>
			</div>
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
				<button ng-click="goUserAdd()" class="btn btn-primary">추가</button>
			</div>
		</div>
	</div>
</div>
</html>