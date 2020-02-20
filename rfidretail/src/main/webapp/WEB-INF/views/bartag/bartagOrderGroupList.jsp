<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">RFID 생산 작업 목록</a></h2>
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
						&nbsp;&nbsp;
						스타일 :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.style" placeholder="스타일 입력" uib-typeahead="style as style.data for style in styleList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'style')">
						&nbsp;&nbsp;
		          	</div>
		       </div>
		       <div class="form-row">
		          	<div class="form-group form-inline">
		          		기준일 선택 : 
		          		&nbsp;&nbsp;
						<div class="custom-control custom-radio custom-control-inline">
							<input name="inputCheckYn" id="inputCheckYn1" type="radio" ng-model="search.searchDate" value="regDate" class="custom-control-input">
							<label class="custom-control-label" for="inputCheckYn1">등록일자</label>
						</div>
						<div class="custom-control custom-radio custom-control-inline">
							<input name="inputCheckYn" id="inputCheckYn2" type="radio" ng-model="search.searchDate" value="updDate" class="custom-control-input">
							<label class="custom-control-label" for="inputCheckYn2">수정일자</label>
						</div>
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
						<th ng-click="sort('date')">{{search.searchDateFlag == 'regDate' ? '등록일자' : '수정일자'}}</th>
						<th ng-click="sort('productYy')">연도</th>
						<th ng-click="sort('productSeason')">시즌</th>
						<th ng-click="sort('name')">업체명</th>
						<th ng-click="sort('orderAmount')">생산 요청 수량</th>
						<th ng-click="sort('completeAmount')">생산 확정 수량</th>
						<th ng-click="sort('nonCheckCompleteAmount')">생산 미확정 수량</th>
						<th ng-click="sort('additionAmount')">추가 확정 수량</th>
						<th ng-click="sort('nonCheckAdditionAmount')">추가 미확정 수량</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="obj in list" class="pointer" ng-if="list.length > 0">
						<td ng-click="detail(obj)">{{obj.date}}</td>
						<td ng-click="detail(obj)">{{obj.productYy}}</td>
						<td ng-click="detail(obj)">{{obj.productSeason}}</td>
						<td ng-click="detail(obj)">{{obj.name}}</td>
						<td ng-click="detail(obj)">{{obj.orderAmount | number : '0'}}</td>
						<td ng-click="detail(obj)">{{obj.completeAmount | number : '0'}}</td>
						<td ng-click="detail(obj)">{{obj.nonCheckCompleteAmount | number : '0'}}</td>
						<td ng-click="detail(obj)">{{obj.additionAmount | number : '0'}}</td>
						<td ng-click="detail(obj)">{{obj.nonCheckAdditionAmount | number : '0'}}</td>
						<!-- 
						<td ng-click="detail(obj)">
							<uib-progressbar animate="true" striped="true" value="obj.batchPercent" type="{{obj.type}}"><b>{{obj.batchPercent}}%</b></uib-progressbar>
						</td>
						-->
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
								<option value="productYy" ng-selected="true">연도</option>
								<option value="productSeason">시즌</option>
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
		</div>
	</div>
</div>
</html>