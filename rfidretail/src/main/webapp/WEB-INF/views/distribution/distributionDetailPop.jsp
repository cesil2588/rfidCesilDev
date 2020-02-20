<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">재고 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr>
						<th>연도</th>
						<th>시즌</th>
						<th>스타일</th>
						<th>컬러</th>
						<th>사이즈</th>
						<th>오더차수</th>
						<th>추가발주</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>{{data.productionStorage.bartagMaster.productYy}}</td>
						<td>{{data.productionStorage.bartagMaster.productSeason}}</td>
						<td>{{data.productionStorage.bartagMaster.style}}</td>
						<td>{{data.productionStorage.bartagMaster.color}}</td>
						<td>{{data.productionStorage.bartagMaster.size}}</td>
						<td>{{data.productionStorage.bartagMaster.orderDegree}}</td>
						<td>{{data.productionStorage.bartagMaster.additionOrderDegree}}</td>
					</tr>
				</tbody>
			</table>
			<h5>태그 상태별 수량 정보</h5>
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr>
						<th>총계</th>
						<th>입고 예정</th>
						<th>입고</th>
						<th>출고</th>
						<th>재발행대기</th>
						<th>재발행요청</th>
						<th>반품</th>
						<th>폐기</th>
					</tr>
				</thead>
				<tbody>	
					<tr>
						<td><a href="" ng-click="tagStat('all')">{{data.totalAmount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('1')">{{data.stat1Amount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('2')">{{data.stat2Amount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('3')">{{data.stat3Amount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('4')">{{data.stat4Amount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('5')">{{data.stat5Amount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('6')">{{data.stat6Amount | number:0}}</a></td>
						<td><a href="" ng-click="tagStat('7')">{{data.stat7Amount | number:0}}</a></td>
					</tr>
				</tbody>
			</table>
			<h5 ng-if="testMode">태그 목록</h5>
			<div class="form-group" ng-if="testMode">
				<!-- <div class="card card-body" style="overflow:scroll; height:400px;"> -->
				<div class="card card-body">
					<table class="table table-bordered table-hover text-center custom-align-middle">
					<thead>
						<tr class="pointer">
							<th style="width: 90px;">
							<div class="custom-control custom-checkbox">
								<input class="custom-control-input" ng-show="checkboxFlag" type="checkbox" ng-model="allCheckFlag" ng-click="allCheck()" id="allCheck"/>
								<label class="custom-control-label" ng-show="checkboxFlag" for="allCheck">체크</label>
								<span ng-show="!checkboxFlag">체크</span>
							</div>
							</th>
							<th style="width: 180px;">바코드</th>
							<th style="width: 300px;" ng-click="sort('rfidTag')">RFID태그</th>
							<th ng-click="sort('boxInfo.barcode')">박스바코드</th>
							<th style="width: 110px;" ng-click="sort('stat')">상태</th>
							<th ng-click="sort('regUserInfo.userId')">등록자</th>
							<th style="width: 180px;" ng-click="sort('regDate')">등록일</th>
							<th ng-click="sort('updUserInfo.userId')">수정자</th>
							<th ng-click="sort('updDate')">수정일</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="rfid in list" ng-if="list.length > 0">
							<td>
								<div ng-show="rfid.stat == '1' || rfid.stat == '2'" class="custom-control custom-checkbox">
									<!--  
							  		<input type="checkbox" class="custom-control-input" id="customCheck{{$index}}" ng-click="checkBoxSelected(rfid)">
							  		<label class="custom-control-label" for="customCheck{{$index}}"></label>
							  		-->
							  		
							  		<input type="checkbox" class="custom-control-input" id="customCheck{{$index}}" ng-model="rfid.check" ng-change="checkBoxSelected(rfid)">
							  		<label class="custom-control-label" for="customCheck{{$index}}"></label>
								</div>
							</td>
							<td ng-click="openRfidTagDetail(rfid)" class="pointer">{{rfid.erpKey}}{{rfid.rfidSeq}}</td>
							<td ng-click="openRfidTagDetail(rfid)" class="pointer">{{rfid.rfidTag}}</td>
							<td>{{rfid.boxInfo.barcode ? rfid.boxInfo.barcode : '-'}}</td>
							<td ng-switch="rfid.stat">
								<span ng-switch-when="1">입고 예정</span>
								<span ng-switch-when="2">입고</span>
								<span ng-switch-when="3">출고</span>
								<span ng-switch-when="4">재발행대기</span>
								<span ng-switch-when="5">재발행요청</span>
								<span ng-switch-when="6">반품</span>
								<span ng-switch-when="7">폐기</span>
							</td>
							<td>{{rfid.regUserInfo.userId}}</td>
							<td>
								<span ng-if="rfid.regDate">
									{{rfid.regDate | date:"yyyy-MM-dd"}}<br />
									{{rfid.regDate | date:"HH:mm:ss"}}
								</span>
								<span ng-if="!rfid.regDate">
									-
								</span>
							</td>
							<td>{{rfid.updUserInfo.userId ? rfid.updUserInfo.userId : '-'}}</td>
							<td>
								<span ng-if="rfid.updDate">
									{{rfid.updDate | date:"yyyy-MM-dd"}}<br />
									{{rfid.updDate | date:"HH:mm:ss"}}
								</span>
								<span ng-if="!rfid.updDate">
									- 
								</span>
							</td>
						</tr>
						<tr ng-if="list.length == 0">
							<td colspan="9" class="text-center">
								No Data
							</td>
						</tr>
					</tbody>
				</table>
				</div>
			</div>
			<div ng-if="testMode">
				<nav class="text-center">
					<ul class="pagination justify-content-center">
						<li class="page-item">
							<a href="" aria-label="Previous" ng-click="goPage(begin, statValue, statNum)" class="page-link"><span aria-hidden="true">&laquo;</span></a>
				    	</li>
				    	<li class="page-item">
							<a href="" aria-label="Previous" ng-click="goPage(current - 1, statValue, statNum)" class="page-link"><span aria-hidden="true">&lt;</span></a>
				    	</li>
				    	<li ng-repeat="pageNum in [begin, end] | makeRange" ng-class="{'active' : current == pageNum}" class="page-item"><a href="" ng-click="goPage(pageNum, statValue, statNum)" class="page-link">{{pageNum}}</a></li>
				    	<li class="page-item">
				    		<a href="" aria-label="Next" ng-click="goPage(current + 1, statValue, statNum)" class="page-link"><span aria-hidden="true">&gt;</span></a>
				    	</li>
				    	<li class="page-item">
				    		<a href="" aria-label="Next" ng-click="goPage(end, statValue, statNum)" class="page-link"><span aria-hidden="true">&raquo;</span></a>
				    	</li>
				  	</ul>
				</nav>
			</div>
			<!-- 
			<div class="search-margin" ng-if="testMode">
				<div class="d-flex justify-content-center">
					<form class="form-inline" ng-submit="clickSearch()">
						<div class="form-group">
							<select class="custom-select" ng-model="search.option" style="width:120px;">
								<option ng-selected="true" value="barcode">바코드</option>
								<option value="rfidTag">RFID태그</option>
							</select>&nbsp;
							<input type="text" class="form-control" placeholder="검색" ng-model="search.text" style="width:300px;" id="inputSearch">&nbsp;
							<button type="submit" class="btn btn-default">검색</button>
						</div>
					</form>
				</div>
			</div>
			-->
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
    </div>
</html>
