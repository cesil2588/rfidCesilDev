<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">어플리케이션 목록</a></h2>
			<div class="card card-body" style="margin-bottom:5px;"> 
				<div class="form-row">
					<div class="form-group form-inline">
						&nbsp;&nbsp;
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
		          	<div class="form-inline">
		          		&nbsp;&nbsp;
		          		타입 :
						&nbsp;&nbsp;
						<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.type">
							<option value="all">전체</option>
							<option value="1">생산</option>
							<option value="2">물류</option>
							<option value="3">매장</option>
							<option value="4">컨베이어</option>
						</select>
						&nbsp;&nbsp;
						대표 여부 :
						&nbsp;&nbsp;
						<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.representYn">
							<option value="all">전체</option>
							<option value="Y">Y</option>
							<option value="N">N</option>
						</select>
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
						<th ng-click="sort('version')">버전</th>
						<th ng-click="sort('fileName')">파일명</th>
						<th ng-click="sort('representYn')">대표여부</th>
						<th ng-click="sort('type')">타입</th>
						<th ng-click="sort('useYn')">사용여부</th>
						<th ng-click="sort('downloadCount')">다운로드 횟수</th>
						<th ng-click="sort('regUserInfo.userId')">등록자</th>
						<th ng-click="sort('regDate')">등록일</th>
						<th ng-click="sort('updUserInfo.userId')">수정자</th>
						<th ng-click="sort('updDate')">수정일</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="app in list" class="pointer" ng-if="list.length > 0">
						<td ng-click="goDetail(app)">{{app.version}}</td>
						<td ng-click="goDetail(app)">{{app.fileName}}</td>
						<td ng-click="goDetail(app)">{{app.representYn}}</td>
						<td ng-click="goDetail(app)" ng-switch="app.type">
							<span ng-switch-when="1">생산</span>
							<span ng-switch-when="2">물류</span>
							<span ng-switch-when="3">매장</span>
							<span ng-switch-when="4">컨베이어</span>
						</td>
						<td ng-click="goDetail(app)">{{app.useYn}}</td>
						<td ng-click="goDetail(app)">{{app.downloadCount}}</td>
						<td ng-click="goDetail(app)">{{app.regUserInfo.userId}}</td>
						<td>
							<span ng-if="app.regDate">
								{{app.regDate | date:"yyyy-MM-dd"}}<br />
								{{app.regDate | date:"HH:mm:ss"}}<br />
							</span>
							<span ng-if="!app.regDate">
								-
							</span>
						</td>
						<td ng-click="goDetail(app)" >{{app.updUserInfo.userId}}</td>
						<td>
							<span ng-if="app.updDate">
								{{app.updDate | date:"yyyy-MM-dd"}}<br/>
								{{app.updDate | date:"HH:mm:ss"}}
							</span>
							<span ng-if="!app.updDate">
								-
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="10" class="text-center" ng-if="list.length == 0">
							No Data
						</td>
					</tr>
				</tbody>
			</table>
			<!-- 
			<div class="search-margin">
				<div class="d-flex justify-content-center">
					<form class="form-inline" ng-submit="clickSearch()">
						<div class="form-group">
							<select class="custom-select" ng-model="search.option" style="width:100px;">
								<option value="fileName" ng-selected="true">파일명</option>
							</select>&nbsp;
							<input type="text" class="form-control" placeholder="검색" ng-model="search.text" style="width:300px;" id="inputSearch">&nbsp;
							<button type="submit" class="btn btn-default">검색</button>
						</div>
					</form>
				</div>
			</div>
			-->
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
				<button ng-click="goAdd()" class="btn btn-primary">추가</button>
			</div>
		</div>
	</div>
</div>
</html>