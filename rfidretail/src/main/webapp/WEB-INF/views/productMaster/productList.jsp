<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">제품 마스터 목록</a></h2>
			<div class="card card-body" style="margin-bottom:5px;"> 
				<div class="form-inline">
					제품년도 :
	          		&nbsp;&nbsp;
					<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.productYy" ng-options="select.data as select.data for select in selectList | filter:customFilter('productYy')"></select>
					&nbsp;&nbsp;
					시즌 : 
					&nbsp;&nbsp;
					<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.productSeason">
						<option value="all">전체</option>
						<option ng-repeat="select in selectList | filter:customFilter('season')" value="{{select.data}}">{{select.data}}</option>
					</select>
					&nbsp;&nbsp;
					스타일 : 
					&nbsp;&nbsp;
					<select class="custom-select" style="width:200px;margin-right:5px;" ng-model="search.style">
						<option value="all">전체</option>
						<option ng-repeat="select in selectList | filter:customFilter('style')" value="{{select.data}}">{{select.data}}</option>
					</select>
					&nbsp;&nbsp;
	          		<button ng-click="headSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>
	          	</div>
          	</div>
			<h5>Total({{total}}건)</h5>
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th ng-click="sort('createDate')">생성일자</th>
						<th ng-click="sort('createNo')">순번</th>
						<th ng-click="sort('erpKey')">ERP키</th>
						<th ng-click="sort('style')">스타일</th>
						<th ng-click="sort('annotherStyle')">나머지 제품코드</th>
						<th ng-click="sort('productYy')">제품년도</th>
						<th ng-click="sort('productSeason')">시즌</th>
						<th ng-click="sort('productRfidYySeason')">RFID시즌</th>
						<th ng-click="sort('color')">컬러</th>
						<th ng-click="sort('size')">사이즈</th>
						<th ng-click="sort('stat')">상태</th>
						<th ng-click="sort('regDate')">등록일</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="product in list" ng-click="detail(product)" class="pointer" ng-if="list.length > 0">
						<td>{{product.createDate}}</td>
						<td>{{product.createNo}}</td>
						<td>{{product.erpKey}}</td>
						<td>{{product.style}}</td>
						<td>{{product.annotherStyle}}</td>
						<td>{{product.productYy}}</td>
						<td>{{product.productSeason}}</td>
						<td>{{product.productRfidYySeason}}</td>
						<td>{{product.color}}</td>
						<td>{{product.size}}</td>
						<td>{{product.stat == 'C' ? "생성" : product.stac == 'U' ? "수정" : "삭제"}}</td>
						<td>{{product.regDate | date:'yyyy-MM-dd'}}<br />
							{{product.regDate | date:'HH:mm:ss'}}</td>
					</tr>
					<tr>
						<td colspan="12" class="text-center" ng-if="list.length == 0">
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
								<option value="erpKey" ng-selected="true">ERP키</option> 
								<option value="color">컬러</option>
								<option value="size">사이즈</option>
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