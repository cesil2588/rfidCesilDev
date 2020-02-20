<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">재고 목록</a></h2>
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
						<input type="text" ng-model="search.companyInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue, type:'3', useYn:'Y'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole == 'production'">
					</div>
				</div>
				<div class="form-row">
		          	<div class="form-group form-inline">
		          		연도 :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.productYy" placeholder="연도 입력" uib-typeahead="productYy as productYy.data for productYy in productYyList | filter:{data:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'productYy')">
						&nbsp;&nbsp;
		          		시즌 :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.productSeason" placeholder="시즌 입력" uib-typeahead="productSeason as productSeason.data for productSeason in productSeasonList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagSeasonData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'productSeason')">
						&nbsp;&nbsp;
		          		스타일 :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.style" placeholder="스타일 입력" uib-typeahead="style as style.data for style in styleList | filter:{data:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'style')">
						&nbsp;&nbsp;
						컬러 :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.color" placeholder="컬러 입력" uib-typeahead="color as color.data for color in colorList | filter:{data:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'color')">
						&nbsp;&nbsp;
						사이즈 :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.styleSize" placeholder="사이즈 입력" uib-typeahead="size as size.data for size in sizeList | filter:{data:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'styleSize')">
		          	</div>
		        </div>
				<div class="form-row">
		          	<div class="form-inline">
	          			<button ng-click="headSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>
	          		</div>
	          	</div>
          	</div>
          	<!-- 
          	<h5 ng-if="list.length > 0">총계</h5>
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle" ng-if="list.length > 0">
				<thead>
					<tr class="pointer">
						<th>총 수량</th>
						<th>총 입고예정 수량</th>
						<th>총 재고 수량</th>
						<th>총 판매 수량</th>
						<th>총 반품 수량</th>
						<th>총 재발행요청 수량</th>
						<th>총 매장간이동 수량</th>
						<th>총 폐기 수량</th>
					</tr>
				</thead>
				<tbody>
					<tr class="pointer">
						<td>{{countAll.allTotalAmount | number:0}}</td>
						<td>{{countAll.allStat1Amount | number:0}}</td>
						<td>{{countAll.allStat2Amount | number:0}}</td>
						<td>{{countAll.allStat3Amount | number:0}}</td>
						<td>{{countAll.allStat4Amount | number:0}}</td>
						<td>{{countAll.allStat5Amount | number:0}}</td>
						<td>{{countAll.allStat6Amount | number:0}}</td>
						<td>{{countAll.allStat7Amount | number:0}}</td>
					</tr>
				</tbody>
			</table>
			-->
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
						<th ng-click="sort('distributionStorage.companyInfo.name')">매장 업체명</th>
						<th ng-click="sort('distributionStorage.productionStorage.bartagMaster.productYy')">연도</th>
						<th ng-click="sort('distributionStorage.productionStorage.bartagMaster.productSeason')">시즌</th>
						<th ng-click="sort('distributionStorage.productionStorage.bartagMaster.style')">스타일</th>
						<th ng-click="sort('distributionStorage.productionStorage.bartagMaster.color')">컬러</th>
						<th ng-click="sort('distributionStorage.productionStorage.bartagMaster.size')">사이즈</th>
						<th ng-click="sort('totalAmount')">총 수량</th>
						<th ng-click="sort('stat1Amount')">입고예정 수량</th>
						<th ng-click="sort('stat2Amount')">재고 수량</th>
						<th ng-click="sort('stat3Amount')">판매 수량</th>
						<th ng-click="sort('stat4Amount')">반품 수량</th>
						<th ng-click="sort('stat5Amount')">재발행요청 수량</th>
						<th ng-click="sort('stat6Amount')">매장간이동 수량</th>
						<th ng-click="sort('stat7Amount')">폐기 수량</th>
						<th ng-click="sort('regDate')">등록일</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="store in list" ng-click="detail(store)" class="pointer" ng-if="list.length > 0">
						<td>{{store.companyInfo.name}}</td>
						<td>{{store.distributionStorage.productionStorage.bartagMaster.productYy}}</td>
						<td>{{store.distributionStorage.productionStorage.bartagMaster.productSeason}}</td>
						<td>{{store.distributionStorage.productionStorage.bartagMaster.style}}</td>
						<td>{{store.distributionStorage.productionStorage.bartagMaster.color}}</td>
						<td>{{store.distributionStorage.productionStorage.bartagMaster.size}}</td>
						<td>{{store.totalAmount | number:0}}</td>
						<td>{{store.stat1Amount | number:0}}</td>
						<td>{{store.stat2Amount | number:0}}</td>
						<td>{{store.stat3Amount | number:0}}</td>
						<td>{{store.stat4Amount | number:0}}</td>
						<td>{{store.stat5Amount | number:0}}</td>
						<td>{{store.stat6Amount | number:0}}</td>
						<td>{{store.stat7Amount | number:0}}</td>
						<td>{{store.regDate | date:'yyyy-MM-dd'}}<br />
							{{store.regDate | date:'HH:mm:ss'}}</td>
					</tr>
					<tr>
						<td colspan="15" class="text-center" ng-if="list.length == 0">
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
								<option value="style">스타일</option>
								<option value="productYy">연도</option>
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