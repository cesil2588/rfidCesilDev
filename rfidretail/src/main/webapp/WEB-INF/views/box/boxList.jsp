<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish" id="test">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">박스 목록</a></h2>
			<div class="card card-body" style="margin-bottom:5px;"> 
				<div class="form-row">
					<div class="form-group form-inline">
						상태 : 
						&nbsp;&nbsp;
						<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.stat">
							<option value="all">전체</option>
							<option ng-repeat="stat in statList.codeInfo" value="{{stat.codeValue}}">{{stat.name}}</option>
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
						<th>
							<div class="custom-control custom-checkbox">
								<input class="custom-control-input" type="checkbox" ng-model="allCheckFlag" ng-click="allCheck()" id="allCheck"/>
								<label class="custom-control-label" for="allCheck">체크</label>
							</div>
						</th>
						<th ng-click="sort('barcode')">바코드</th>
						<th ng-click="sort('type')">내용</th>
						<th ng-click="sort('createDate')">날짜</th>
						<th ng-click="sort('boxNum')">박스번호</th>
						<th ng-click="sort('startCompanyInfo.name')">출발지</th>
						<th ng-click="sort('endCompanyInfo.name')">도착지</th>
						<th ng-click="sort('stat')">상태</th>
						<th ng-click="sort('arrivalDate')">도착 예정일</th>
						<th ng-click="sort('regUserInfo.userId')">등록자</th>
						<th ng-click="sort('regDate')">등록일</th>
						<th ng-click="sort('updUserInfo.userId')">수정자</th>
						<th ng-click="sort('updDate')">수정일</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="box in list" class="pointer" ng-if="list.length > 0">
						<td>
							<div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="customCheck{{$index}}" ng-model="box.check">
								<label class="custom-control-label" for="customCheck{{$index}}"></label>
							</div>
						</td>
						<td ng-click="detail(box)">{{box.barcode}}</td>
						<td ng-click="detail(box)"><span code-name pcode="10001" code="{{box.type}}"></span></td>
						<td ng-click="detail(box)">{{box.createDate}}</td>
						<td ng-click="detail(box)">{{box.boxNum}}</td>
						<td ng-click="detail(box)">{{box.startCompanyInfo.name}}</td>
						<td ng-click="detail(box)" ng-if="type != '03'">
							{{box.endCompanyInfo.name}}
						</td>
						<td ng-click="detail(box)" ng-if="type == '03'">
							{{box.startCompanyInfo.name == box.endCompanyInfo.name ? '-' : box.endCompanyInfo.name}}
						</td>
						<td ng-click="detail(box)" ng-switch="box.stat">
							<span ng-switch-when="1">생성</span>
							<span ng-switch-when="2">출고</span>
							<span ng-switch-when="3">출고완료</span>
							<span ng-switch-when="4">폐기</span>
						</td>
						</td>
						<td ng-click="detail(box)">
							<span ng-if="box.arrivalDate">
								{{box.arrivalDate | date:'yyyy-MM-dd'}}
							</span>
							<span ng-if="!box.arrivalDate">
								-
							</span>
						</td>
						<td ng-click="detail(box)">{{box.regUserInfo.userId ? box.regUserInfo.userId : '-'}}</td>
						<td ng-click="detail(box)">
							<span ng-if="box.regDate">
								{{box.regDate | date:'yyyy-MM-dd'}}<br />
								{{box.regDate | date:'HH:mm:ss'}}
							</span>
							<span ng-if="!box.regDate">
								-
							</span>
						</td>
						<td ng-click="detail(box)">{{box.updUserInfo.userId ? box.updUserInfo.userId : '-'}}</td>
						<td ng-click="detail(box)">
							<span ng-if="box.updDate">
								{{box.updDate | date:'yyyy-MM-dd'}}<br />
								{{box.updDate | date:'HH:mm:ss'}}
							</span>
							<span ng-if="!box.updDate">
								-
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="13" class="text-center" ng-if="list.length == 0">
							No Data
						</td>
					</tr>
				</tbody>
			</table>
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
				<button ng-click="goPdfDownload('check')" class="btn btn-primary">PDF 다운로드</button>
				<button ng-click="openBoxMapping()" ng-if="testMode" class="btn btn-primary">태그 맵핑</button>
				
				<!-- <button ng-click="goPdfDownload('all')" class="btn btn-lg btn-primary">대량 PDF 다운로드</button> -->
				<!-- <button ng-click="goAdd('one')" class="btn btn-lg btn-primary">박스 개별 추가</button>
				<button ng-click="goAdd('all')" class="btn btn-lg btn-primary">박스 일괄 추가</button>
				
				-->
			</div>
			<div class="tempBarcodeDiv">
				<div ng-repeat="check in list">
					<angular-barcode ng-model="check.barcode" bc-options="bc" bc-class="barcode" bc-type="canvas" id="barcodeImg_{{check.boxSeq}}"></angular-barcode>
				</div>
			</div>
		</div>
	</div>
</div>
</html>