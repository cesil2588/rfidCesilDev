<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">권한 목록</a></h2>
 			<div class="card card-body" style="margin-bottom:5px;"> 
				<div class="form-inline">
					권한 타입 :
	          		&nbsp;&nbsp;
					<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.role">
						<option value="all">전체</option>
						<option  ng-repeat="select in selectList | unique:'role'" value="{{select.role}}">{{select.role}}</option>
					</select>
					&nbsp;&nbsp;
					사용여부 : 
					&nbsp;&nbsp;
					<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.useYn">
						<option value="all">전체</option>
						<option value="Y">Y</option>
						<option value="N">N</option>
						
					</select>
					&nbsp;&nbsp;
	          		<button ng-click="headSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>
	          	</div>
          	</div>
			<div class="form-inline">
			<h5 style="float:left;">Total({{total}}건)</h5>
          			<select class="custom-select" style="margin-left: auto;" ng-init="search.size = '10'" ng-model="search.size" ng-change="changeSearchSize()">
						<option value="5">5건 보기</option>
						<option value="10">10건 보기</option>
						<option value="20">20건 보기</option>
					</select>   
          	</div>
			
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th ng-click="sort('roleName')">권한명</th>
						<th ng-click="sort('role')">권한타입</th>
						<th ng-click="sort('useYn')">사용여부</th>
						<th ng-click="sort('regDate')">등록일</th>
						<th ng-click="sort('regUserInfo.userSeq')">등록자</th>
						<th ng-click="sort('updDate')">수정일</th>
						<th ng-click="sort('updUserInfo.userSeq')">수정자</th>
						
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="roleList in list" ng-click="detail(roleList)" class="pointer" ng-if="list.length > 0">
						<td>{{roleList.roleName}}</td>
						<td>{{roleList.role}}</td>
						<td>{{roleList.useYn}}</td>
						<td>{{roleList.regDate | date:'yyyy-MM-dd'}}<br />
							{{roleList.regDate | date:'HH:mm:ss'}}</td>
						<td>{{roleList.regUserInfo.userId}}</td>
						<td>{{roleList.updDate | date:'yyyy-MM-dd'}}<br/>
							{{roleList.updDate | date:'HH:mm:ss'}}</td>
						<td>{{roleList.updUserInfo.userId}}</td>
					</tr>
					<tr>
						<td colspan="12" class="text-center" ng-if="list.length == 0">
							No Data
						</td>
					</tr>
				</tbody>
			</table>
			<button type="submit" class="btn btn-default" style="float:right;" ng-click="add()">권한 추가</button>
			<div class="search-margin">
				<div class="d-flex justify-content-center" >
					<form class="form-inline" ng-submit="clickSearch()">
						<div class="form-group">
							<select class="custom-select" ng-model="search.option" style="width:100px;">
								<option value="roleName" ng-selected="true">권한명</option> 
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
		</div>
	</div>
</div>
</html>