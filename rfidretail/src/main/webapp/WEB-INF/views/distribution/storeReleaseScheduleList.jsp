<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish" id="test">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">매장 출고예정 목록</a></h2>
			<div class="card card-body" style="margin-bottom:5px;"> 
				<div class="form-row">
					<div class="form-group form-inline">
						출고 완료 여부 :
						&nbsp;&nbsp;
						<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.completeYn">
							<option value="all">전체</option>
							<option value="Y">완료</option>
							<option value="N">미완료</option>
						</select>
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
						<th ng-click="sort('completeDate')">마감일자</th>
						<th ng-click="sort('completeType')">마감타입</th>
						<th ng-click="sort('completeSeq')">마감번호</th>
						<th ng-click="sort('endCompanyInfo.name')">도착지</th>
						<th ng-click="sort('style')">스타일</th>
						<th ng-click="sort('color')">컬러</th>
						<th ng-click="sort('size')">사이즈</th>
						<th ng-click="sort('orderAmount')">주문수량</th>
						<th ng-click="sort('releaseAmount')">출고수량</th>
						<th ng-click="sort('stat')">상태</th>
						<th ng-click="sort('regUserInfo.userId')">등록자</th>
						<th ng-click="sort('regDate')">등록일</th>
						<th ng-click="sort('updUserInfo.userId')">수정자</th>
						<th ng-click="sort('updDate')">수정일</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="obj in list" class="pointer" ng-if="list.length > 0">
						<td ng-click="detail(obj)">{{obj.completeDate}}</td>
						<td ng-click="detail(obj)">{{obj.completeType}}</td>
						<td ng-click="detail(obj)">{{obj.completeSeq}}</td>
						<td ng-click="detail(obj)">{{obj.endCompanyInfo.name}}</td>
						<td ng-click="detail(obj)">{{obj.style}}</td>
						<td ng-click="detail(obj)">{{obj.color}}</td>
						<td ng-click="detail(obj)">{{obj.size}}</td>
						<td ng-click="detail(obj)">{{obj.orderAmount}}</td>
						<td ng-click="detail(obj)">{{obj.releaseAmount}}</td>
						<td ng-click="detail(obj)">{{obj.stat}}</td>
						<td ng-click="detail(obj)">{{obj.regUserInfo.userId ? obj.regUserInfo.userId : '-'}}</td>
						<td ng-click="detail(obj)">
							<span ng-if="obj.regDate">
								{{obj.regDate | date:'yyyy-MM-dd'}}<br />
								{{obj.regDate | date:'HH:mm:ss'}}
							</span>
							<span ng-if="!obj.regDate">
								-
							</span>
						</td>
						<td ng-click="detail(obj)">{{obj.updUserInfo.userId ? obj.updUserInfo.userId : '-'}}</td>
						<td ng-click="detail(obj)">
							<span ng-if="obj.updDate">
								{{obj.updDate | date:'yyyy-MM-dd'}}<br />
								{{obj.updDate | date:'HH:mm:ss'}}
							</span>
							<span ng-if="!obj.updDate">
								-
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="14" class="text-center" ng-if="list.length == 0">
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
							<select class="custom-select" ng-model="search.option" style="width:120px;"> 
								<option value="boxNum">박스번호</option>
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
				<back-btn back="뒤로 가기"></back-btn>
				<!-- <button ng-click="complete()" class="btn btn-lg btn-primary">선택한 박스 입고</button> -->
			</div>
		</div>
	</div>
</div>
</html>