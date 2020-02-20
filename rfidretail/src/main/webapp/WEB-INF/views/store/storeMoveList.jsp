<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish" id="test">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">매장간 이동 목록</a></h2>
			<div class="card card-body" style="margin-bottom:5px;"> 
				<div class="form-row">
					<div class="form-group form-inline">
						작업 여부 :
						&nbsp;&nbsp;
						<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.workYn">
							<option value="all">전체</option>
							<option value="Y">작업 완료</option>
							<option value="N">작업 미완료</option>
						</select>
						&nbsp;&nbsp;
						확정 여부 :
						&nbsp;&nbsp;
						<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.confirmYn">
							<option value="all">전체</option>
							<option value="Y">확정 완료</option>
							<option value="N">확정 미완료</option>
						</select>
						&nbsp;&nbsp;
						종결 여부 :
						&nbsp;&nbsp;
						<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.disuseYn">
							<option value="all">전체</option>
							<option value="Y">종결 완료</option>
							<option value="N">종결 미완료</option>
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
						<th style="width:80px;">
							<div class="custom-control custom-checkbox">
								<input class="custom-control-input" type="checkbox" ng-model="allCheckFlag" ng-click="allCheck()" id="allCheck"/>
								<label class="custom-control-label" for="allCheck">체크</label>
							</div>
						</th>
						<th>작업일자</th>
						<th>작업라인</th>
						<th>바코드</th>
						<th>반품타입</th>
						<th>출발업체</th>
						<th>도착업체</th>
						<th>출발업체<br/>작업여부</th>
						<th>출발업체<br/>작업일자</th>
						<th>출발업체<br/>확정여부</th>
						<th>출발업체<br/>확정일자</th>
						<th>도착업체<br/>작업여부</th>
						<th>도착업체<br/>작업일자</th>
						<th>도착업체<br/>확정여부</th>
						<th>도착업체<br/>확정일자</th>
						<th>종결 여부</th>
						<th>요청 태그 수량</th>
						<th>확정 태그 수량</th>
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
						<td ng-click="detail(obj)">{{obj.boxInfo.barcode}}</td>
						<td ng-click="detail(obj)" ng-switch="obj.returnType">
							<span ng-switch-when="10">일반반품</span>
							<span ng-switch-when="15">이동반품</span>
							<span ng-switch-when="20">비용반품</span>
							<span ng-switch-when="40">불량반품</span>
							<span ng-switch-when="80">일반반품대기</span>
							<span ng-switch-when="90">계절반품</span>
						</td>
						<td ng-click="detail(obj)">{{obj.boxInfo.startCompanyInfo.name}}</td>
						<td ng-click="detail(obj)">{{obj.boxInfo.endCompanyInfo.name}}</td>
						<td ng-click="detail(obj)">{{obj.startWorkYn}}</td>
						<td ng-click="detail(obj)">
							<span ng-if="obj.startWorkDate">
								{{obj.startWorkDate | date:'yyyy-MM-dd'}}<br/>
								{{obj.startWorkDate | date:'HH:mm:ss'}}<br/>
							</span>
							<span ng-if="!obj.startWorkDate">
								-
							</span>
						</td>
						<td ng-click="detail(obj)">{{obj.startConfirmYn}}</td>
						<td ng-click="detail(obj)">
							<span ng-if="obj.startConfirmDate">
								{{obj.startConfirmDate | date:'yyyy-MM-dd'}}<br/>
								{{obj.startConfirmDate | date:'HH:mm:ss'}}<br/>
							</span>
							<span ng-if="!obj.startConfirmDate">
								-
							</span>
						</td>
						<td ng-click="detail(obj)">{{obj.endWorkYn}}</td>
						<td ng-click="detail(obj)">
							<span ng-if="obj.endWorkDate">
								{{obj.endWorkDate | date:'yyyy-MM-dd'}}<br/>
								{{obj.endWorkDate | date:'HH:mm:ss'}}<br/>
							</span>
							<span ng-if="!obj.endWorkDate">
								-
							</span>
						</td>
						<td ng-click="detail(obj)">{{obj.endConfirmYn}}</td>
						<td ng-click="detail(obj)">
							<span ng-if="obj.endConfirmDate">
								{{obj.endConfirmDate | date:'yyyy-MM-dd'}}<br/>
								{{obj.endConfirmDate | date:'HH:mm:ss'}}<br/>
							</span>
							<span ng-if="!obj.endConfirmDate">
								-
							</span>
						</td>
						<td ng-click="detail(obj)">{{obj.disuseYn}}</td>
						<td ng-click="detail(obj)">
							{{getTotalAmount(obj, 'amount')}}
						</td>
						<td ng-click="detail(obj)">
							{{getTotalAmount(obj, 'completeAmount')}}
						</td>
					</tr>
					<tr>
						<td colspan="21" class="text-center" ng-if="list.length == 0">
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
				<!-- 
				<button ng-click="delete()" class="btn btn-danger">반품 요청 삭제</button>
				<button ng-click="confirm()" class="btn btn-primary">반품 요청 확정</button>
				-->
			</div>
		</div>
	</div>
</div>
</html>