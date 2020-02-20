<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">바택 작업 목록</a></h2>
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
						<input type="text" ng-model="search.companyInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue, type:'3', useYn:'Y'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole == 'production'" typeahead-on-select="selectCompany($item)">
		          	</div>
		       </div>
		       <div class="form-row">
		          	<div class="form-group form-inline">
						<button ng-click="headSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>
						&nbsp;&nbsp;
						<button ng-click="goExcelDate()" class="btn btn-primary" style="float: right;"><i class="xi-download" aria-hidden="true"></i>엑셀 전체다운로드</button>
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
						<th ng-click="sort('regDate')">등록일자</th>
						<th ng-click="sort('productYy')">연도</th>
						<th ng-click="sort('productSeason')">시즌</th>
						<th ng-click="sort('name')">업체명</th>
						<th ng-click="sort('amount')">생산 수량</th>
						<th ng-click="sort('stat1Amount')">미발행 수량</th>
						<th ng-click="sort('stat2Amount')">발행대기 수량</th>
						<th ng-click="sort('stat3Amount')">발행완료 수량</th>
						<th ng-click="sort('stat4Amount')">재발행대기 수량</th>
						<th ng-click="sort('stat5Amount')">재발행완료 수량</th>
						<th ng-click="sort('stat6Amount')">재발행요청 수량</th>
						<th ng-click="sort('stat7Amount')">폐기 수량</th>
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
						<td ng-click="detail(obj)">{{obj.regDate}}</td>
						<td ng-click="detail(obj)">{{obj.productYy}}</td>
						<td ng-click="detail(obj)">{{obj.productSeason}}</td>
						<td ng-click="detail(obj)">{{obj.name}}</td>
						<td ng-click="detail(obj)">{{obj.amount | number : '0'}}</td>
						<td ng-click="detail(obj)">{{obj.stat1Amount | number : '0'}}</td>
						<td ng-click="detail(obj)">{{obj.stat2Amount | number : '0'}}</td>
						<td ng-click="detail(obj)">{{obj.stat3Amount | number : '0'}}</td>
						<td ng-click="detail(obj)">{{obj.stat4Amount | number : '0'}}</td>
						<td ng-click="detail(obj)">{{obj.stat5Amount | number : '0'}}</td>
						<td ng-click="detail(obj)">{{obj.stat6Amount | number : '0'}}</td>
						<td ng-click="detail(obj)">{{obj.stat7Amount | number : '0'}}</td>
						<!-- 
						<td ng-click="detail(obj)">
							<uib-progressbar animate="true" striped="true" value="obj.batchPercent" type="{{obj.type}}"><b>{{obj.batchPercent}}%</b></uib-progressbar>
						</td>
						-->
					</tr>
					<tr>
						<td colspan="13" class="text-center" ng-if="list.length == 0">
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
			<div class="pull-right form-inline">
				<button ng-click="checkExcelDownload()" class="btn btn-primary" style="float: right;"><i class="xi-download" aria-hidden="true"></i>엑셀 다운로드</button>
				&nbsp;&nbsp;
				<button ng-click="fileUploadPopup()" class="btn btn-primary pull-right" ng-if="userRole != 'production'"><i class="xi-upload" aria-hidden="true"></i>태그발행결과 일괄업로드</button>
			</div>
		</div>
	</div>
</div>
</html>