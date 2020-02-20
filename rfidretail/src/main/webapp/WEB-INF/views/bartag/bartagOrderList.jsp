<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">RFID 생산 스타일 목록</a></h2>
			<div class="card card-body" style="margin-bottom:5px;"> 
		       <div class="form-row">
		          	<div class="form-group form-inline">
		          		업체명 : 
						&nbsp;&nbsp;
						<input type="text" ng-model="search.companyInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue, type:'3', useYn:'Y'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole == 'production'" typeahead-on-select="selectCompany($item)">
						&nbsp;&nbsp;
						생산공장명 :
						&nbsp;&nbsp;
						<input type="text" id="subCompany" ng-model="search.subCompanyInfo" placeholder="생산공장명 입력" uib-typeahead="subCompany as subCompany.name for subCompany in subCompanyList | filter:{name:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0">
						&nbsp;&nbsp;
						상태 :
						&nbsp;&nbsp;
						<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.stat">
							<option value="all">전체</option>
							<option value="1">초기생성</option>
							<option value="2">수량입력</option>
							<option value="3">확정완료</option>
							<option value="4">추가수량입력</option>
							<option value="5">추가완료</option>
							<option value="6">종결</option>
						</select>
		          	</div>
		    	</div>
		    	<div class="form-row">
		          	<div class="form-group form-inline">
		          		스타일 :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.style" placeholder="스타일 입력" uib-typeahead="style as style.data for style in styleList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'style')">
						&nbsp;&nbsp;
						컬러 :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.color" placeholder="컬러 입력" uib-typeahead="color as color.data for color in colorList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'color')">
						&nbsp;&nbsp;
						사이즈 :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.styleSize" placeholder="사이즈 입력" uib-typeahead="size as size.data for size in sizeList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'styleSize')">
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
						<th>
							<div class="custom-control custom-checkbox">
								<input class="custom-control-input" type="checkbox" ng-model="allCheckFlag" ng-click="allCheck()" id="allCheck"/>
								<label class="custom-control-label" for="allCheck">체크</label>
							</div>
						</th>
						<th ng-click="sort('productYy')">연도</th>
						<th ng-click="sort('productSeason')">시즌</th>
						<th ng-click="sort('style')">스타일</th>
						<th ng-click="sort('color')">컬러</th>
						<th ng-click="sort('size')">사이즈</th>
						<th ng-click="sort('orderDegree')">오더차수</th>
						<th ng-click="sort('orderAmount')">생산수량</th>
						<th ng-click="sort('completeAmount')">생산 확정 수량</th>
						<th ng-click="sort('nonCheckCompleteAmount')">생산 미확정 수량</th>
						<th ng-click="sort('additionAmount')">추가 확정 수량</th>
						<th ng-click="sort('nonCheckAdditionAmount')">추가 미확정 수량</th>
						<th ng-click="sort('productionCompanyInfo.name')">생산업체</th>
						<th ng-click="sort('detailProductionCompanyName')">생산공장</th>
						<th ng-click="sort('stat')">상태</th>
						<th ng-click="sort('regDate')">등록일자</th>
						<th ng-click="sort('updDate')">수정일자</th>
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
						<td ng-click="detail(obj)">{{obj.productYy}}</td>
						<td ng-click="detail(obj)">{{obj.productSeason}}</td>
						<td ng-click="detail(obj)">{{obj.style}}&nbsp;<span class="badge badge-success" ng-class="{'badge-new' : search.searchDate == 'regDate', 'badge-update' : search.searchDate == 'updDate'}" ng-if="getNewData(obj)">{{search.searchDate == 'regDate' ? 'NEW' : 'UPDATE'}}</span></td>
						<td ng-click="detail(obj)">{{obj.color}}</td>
						<td ng-click="detail(obj)">{{obj.size}}</td>
						<td ng-click="detail(obj)">{{obj.orderDegree}}</td>
						<td ng-click="detail(obj)">{{obj.orderAmount | number:0}}</td>
						<td ng-click="detail(obj)">{{obj.completeAmount | number:0}}</td>
						<td ng-click="detail(obj)">{{obj.nonCheckCompleteAmount | number:0}}</td>
						<td ng-click="detail(obj)">{{obj.additionAmount | number:0}}</td>
						<td ng-click="detail(obj)">{{obj.nonCheckAdditionAmount | number:0}}</td>
						<td ng-click="detail(obj)">{{obj.productionCompanyInfo.name}}</td>
						<td ng-click="detail(obj)">{{obj.detailProductionCompanyName ? obj.detailProductionCompanyName : '-'}}</td>
						<td ng-switch="obj.stat">
							<span ng-switch-when="1">초기생성</span>
							<span ng-switch-when="2">수량입력</span>
							<span ng-switch-when="3">확정완료</span>
							<span ng-switch-when="4">추가수량입력</span>
							<span ng-switch-when="5">추가완료</span>
							<span ng-switch-when="6">종결</span>
						</td>
						<td>
							<span ng-if="obj.regDate">
								{{obj.regDate | date:"yyyy-MM-dd"}}<br />
								{{obj.regDate | date:"HH:mm:ss"}}
							</span>
							<span ng-if="!obj.regDate">
								-
							</span>
						</td>
						<td>
							<span ng-if="obj.updDate">
								{{obj.updDate | date:"yyyy-MM-dd"}}<br />
								{{obj.updDate | date:"HH:mm:ss"}}
							</span>
							<span ng-if="!obj.updDate">
								-
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="17" class="text-center" ng-if="list.length == 0">
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
								<option value="color">컬러</option>
								<option value="size">사이즈</option>
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
				<button ng-click="completeAmountPop()" class="btn btn-primary">생산 수량 입력</button>
				<button ng-click="additionAmountPop()" class="btn btn-primary">추가 수량 입력</button>
				<button ng-click="complete()" class="btn btn-primary">확정</button>
			</div>
		</div>
	</div>
</div>
</html>