<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish" id="test">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">출고 작업 목록</a></h2>
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
						<input type="text" ng-model="search.companyInfo" placeholder="출발지명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue, type:'3'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole == 'production'">
					</div>
				</div>
				<div class="form-row">
					<div class="form-inline">
						<!-- 
						확정 여부 :
						&nbsp;&nbsp;
						<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.confirmYn">
							<option value="all">전체</option>
							<option value="Y">확정 완료</option>
							<option value="N">확정 미완료</option>
						</select>
						&nbsp;&nbsp;
						-->
	          			<button ng-click="headSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>
	          		</div>
	          	</div>
          	</div>
          	<h5 ng-if="list.length > 0">총계</h5>
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle" ng-if="list.length > 0">
				<thead>
					<tr class="pointer">
						<th>출고 예정 박스 수량</th>
						<th>출고 확정 박스 수량</th>
						<th>출고 완료 박스 수량</th>
						<th>총 박스 수량</th>
						<th>출고 예정 태그 수량</th>
						<th>출고 확정 태그 수량</th>
						<th>출고 완료 태그 수량</th>
						<th>총 태그 수량</th>
					</tr>
				</thead>
				<tbody>
					<tr class="pointer">
						<td>{{countAll.boxCount.stat1Amount | number:0}}</td>
						<td>{{countAll.boxCount.stat2Amount | number:0}}</td>
						<td>{{countAll.boxCount.stat3Amount | number:0}}</td>
						<td>{{countAll.boxCount.totalAmount | number:0}}</td>
						<td>{{countAll.boxTagCount.stat1Amount | number:0}}</td>
						<td>{{countAll.boxTagCount.stat2Amount | number:0}}</td>
						<td>{{countAll.boxTagCount.stat3Amount | number:0}}</td>
						<td>{{countAll.boxTagCount.totalAmount | number:0}}</td>
					</tr>
				</tbody>
			</table>
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
						<th ng-click="sort('createDate')">작업일자</th>
						<th ng-click="sort('workLine')">작업라인</th>
						<th ng-click="sort('name')">업체명</th>
						<th ng-click="sort('arrivalDate')">도착예정일</th>
						<th>출고 예정<br/>박스 수량</th>
						<th>출고 확정<br/>박스 수량</th>
						<th>출고 완료<br/>박스 수량</th>
						<th>출고 예정<br/>스타일 수량</th>
						<th>출고 확정<br/>스타일 수량</th>
						<th>출고 완료<br/>스타일 수량</th>
						<th>출고 예정<br/>태그 수량</th>
						<th>출고 확정<br/>태그 수량</th>
						<th>출고 완료<br/>태그 수량</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="obj in list" class="pointer" ng-if="list.length > 0">
						<td>
							<div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="customCheck{{$index}}" ng-model="obj.check">
								<label class="custom-control-label" for="customCheck{{$index}}"></label>
							</div>
						</td>
						<td ng-click="detail(obj)">{{obj.createDate}}</td>
						<td ng-click="detail(obj)">{{obj.workLine}}</td>
						<td ng-click="detail(obj)">{{obj.companyName}}</td>
						<td ng-click="detail(obj)">{{obj.arrivalDate}}</td>
						<td ng-click="detail(obj)">{{obj.stat1BoxCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat2BoxCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat3BoxCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat1StyleCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat2StyleCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat3StyleCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat1TagCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat2TagCount}}</td>
						<td ng-click="detail(obj)">{{obj.stat3TagCount}}</td>
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
				<button ng-click="confirmDel()" class="btn btn-danger">삭제</button>
				<button ng-click="excelDownload()" class="btn btn-primary"><i class="xi-download" aria-hidden="true"></i> 엑셀 다운로드</button>
				<button ng-click="confirmComplete()" class="btn btn-primary">확정</button>
			</div>
		</div>
	</div>
</div>
</html>