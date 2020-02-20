<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">바택정보 목록</a></h2>
			<div class="card card-body" style="margin-bottom:5px;"> 
				<div class="form-row">
					<div class="form-group form-inline">
						업체명 : 
						&nbsp;&nbsp;
						<input type="text" ng-model="search.companyInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue, type:'3', useYn:'Y'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole == 'production'" typeahead-on-select="selectCompany($item)">
						&nbsp;&nbsp;
						생산공장명 :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.subCompanyInfo" placeholder="생산공장명 입력" uib-typeahead="subCompany as subCompany.name for subCompany in subCompanyList | filter:{name:$viewValue}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0">
						&nbsp;&nbsp;
						바택발행 상태 :
						&nbsp;&nbsp;
						<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.completeYn">
							<option value="all">전체</option>
							<option value="Y">발행 종료</option>
							<option value="N">발행 미완료</option>
						</select>
		          	</div>
		        </div>
		        <div class="form-row">
		          	<div class="form-group form-inline">
		          		연도 :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.productYy" placeholder="연도 입력" uib-typeahead="productYy as productYy.data for productYy in productYyList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'productYy')">
						&nbsp;&nbsp;
		          		시즌 :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.productSeason" placeholder="시즌 입력" uib-typeahead="productSeason as productSeason.data for productSeason in productSeasonList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagSeasonData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'productSeason')">
						&nbsp;&nbsp;
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
		          		&nbsp;&nbsp;
		          		<button ng-click="goExcelDate()" class="btn btn-primary" style="float: right;"><i class="xi-download" aria-hidden="true"></i>엑셀 전체다운로드</button>
		          	</div>
		        </div>
          	</div>
          	<h5 ng-if="list.length > 0">총계</h5>
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle" ng-if="list.length > 0">
				<thead>
					<tr class="pointer">
						<th>총 발주 수량</th>
						<th>총 수량</th>
						<th>총 미발행 수량</th>
						<th>총 발행 대기 수량</th>
						<th>총 발행 완료 수량</th>
						<th>총 재발행 대기 수량</th>
						<th>총 재발행 완료 수량</th>
						<th>총 재발행 요청 수량</th>
						<th>총 폐기 수량</th>
					</tr>
				</thead>
				<tbody>
					<tr class="pointer">
						<td>{{countAll.allAmount | number:0}}</td>
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
						<th ng-click="sort('createDate')">생성일자</th>
						<th ng-click="sort('seq')">일련번호</th>
						<th ng-click="sort('lineSeq')">라인번호</th>
						<th ng-click="sort('productYy')">연도</th>
						<th ng-click="sort('productSeason')">시즌</th>
						<th ng-click="sort('erpKey')">ERP키</th>
						<th ng-click="sort('style')">스타일</th>
						<th ng-click="sort('color')">컬러</th>
						<th ng-click="sort('size')">사이즈</th>
						<th ng-click="sort('orderDegree')">오더차수</th>
						<th ng-click="sort('amount')">발행수량</th>
						<th ng-click="sort('productionCompanyInfo.name')">생산업체</th>
						<th ng-click="sort('detailProductionCompanyName')">생산공장</th>
						<th ng-click="sort('stat')">상태</th>
						<th ng-click="sort('bartagStartDate')">발행시작</th>
						<th ng-click="sort('bartagEndDate')">발행종료</th>
						<th style="width: 100px;" ng-click="sort('reissueRequestYn')">재발행<br>요청여부</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="bar in list" class="pointer" ng-if="list.length > 0">
						<td>
							<div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="customCheck{{$index}}" ng-model="bar.check">
								<label class="custom-control-label" for="customCheck{{$index}}"></label>
							</div>
						</td>
						<td ng-click="goBartagDetail(bar)">
							{{bar.regDate | date:"yyyyMMdd"}}
						</td>	
						<td ng-click="goBartagDetail(bar)" >{{bar.createDate}}</td>
						<td ng-click="goBartagDetail(bar)" >{{bar.seq}}</td>
						<td ng-click="goBartagDetail(bar)" >{{bar.lineSeq}}</td>
						<td ng-click="goBartagDetail(bar)" >{{bar.productYy}}</td>
						<td ng-click="goBartagDetail(bar)" >{{bar.productSeason}}</td>
						<td ng-click="goBartagDetail(bar)" >{{bar.erpKey}}</td>
						<td ng-click="goBartagDetail(bar)" >{{bar.style}}</td>
						<td ng-click="goBartagDetail(bar)" >{{bar.color}}</td>
						<td ng-click="goBartagDetail(bar)" >{{bar.size}}</td>
						<td ng-click="goBartagDetail(bar)" >{{bar.orderDegree}}</td>
						<td ng-click="goBartagDetail(bar)" >{{bar.amount | number:0}}</td>
						<td ng-click="goBartagDetail(bar)" >{{bar.productionCompanyInfo.name}}</td>
						<td ng-click="goBartagDetail(bar)" >{{bar.detailProductionCompanyName ? bar.detailProductionCompanyName : '-'}}</td>
						<td ng-switch="bar.stat">
							<span ng-switch-when="1">초기 생성</span>
							<span ng-switch-when="2">발행 준비</span>
							<span ng-switch-when="3">발행 시작</span>
							<span ng-switch-when="4">발행 중</span>
							<span ng-switch-when="5">발행 종료</span>
							<span ng-switch-when="6">종결 처리</span>
							<span ng-switch-when="7">폐기</span>
						</td>
						<td>
							<span ng-if="bar.bartagStartDate">
								{{bar.bartagStartDate | date:"yyyy-MM-dd"}}<br />
								{{bar.bartagStartDate | date:"HH:mm:ss"}}<br />
							</span>
							<span ng-if="!bar.bartagStartDate">
								-
							</span>
						</td>
						<td>
							<span ng-if="bar.bartagEndDate">
								{{bar.bartagEndDate | date:"yyyy-MM-dd"}}<br/>
								{{bar.bartagEndDate | date:"HH:mm:ss"}}
							</span>
							<span ng-if="!bar.bartagEndDate">
								-
							</span>
						</td>
						<td>{{bar.reissueRequestYn}}</td>
					</tr>
					<tr>
						<td colspan="19" class="text-center" ng-if="list.length == 0">
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
				<back-btn back="뒤로 가기"></back-btn>
				&nbsp;&nbsp;
				<button ng-click="checkExcelDownload()" class="btn btn-primary" style="float: right;"><i class="xi-download" aria-hidden="true"></i>엑셀 개별다운로드</button>
				&nbsp;&nbsp;
				<!-- 
				<span class="btn btn-primary pull-right"><i class="xi-upload" aria-hidden="true"></i><input style="width: 140px;" type="file" class="input" id="myfile" file-model="uploadedFile" accept=".txt">태그발행결과 일괄업로드</input></span>
				-->
				<button ng-click="fileUploadPopup()" class="btn btn-primary pull-right" ng-if="userRole != 'production'"><i class="xi-upload" aria-hidden="true"></i>태그발행결과 일괄업로드</button>
				&nbsp;&nbsp;
			</div>
			<div class="form-row" ng-if="testMode">
				<div class="form-group form-inline">
					<input type="text" class="form-control" id="testPublishDegree" placeholder="발행차수를 입력하세요." ng-model="search.testPublishDegree">
					&nbsp;&nbsp;
					<input type="text" class="form-control" id="testPublishRegDate" placeholder="발행일자를 입력하세요." ng-model="search.testPublishRegDate">
					&nbsp;&nbsp;
					<button ng-click="testBartagComplete()" class="btn btn-primary" style="float: right;"><i class="xi-wrench" aria-hidden="true"></i> 선택된 바택 발행완료</button>
					&nbsp;&nbsp;
					<button ng-click="testBartagCompleteAll()" class="btn btn-primary" style="float: right;"><i class="xi-wrench" aria-hidden="true"></i> 일괄 바택 발행완료</button>
				</div>
			</div>
		</div>
	</div>
</div>
</html>