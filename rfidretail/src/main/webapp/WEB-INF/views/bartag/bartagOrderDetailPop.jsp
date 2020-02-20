<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">RFID 생산 스타일 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th ng-click="sort('regDate')">등록일자</th>
						<th ng-click="sort('createDate')">생성일자</th>
						<th ng-click="sort('createSeq')">일련번호</th>
						<th ng-click="sort('createNo')">라인번호</th>
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
						<th ng-click="sort('updDate')">수정일</th>
					</tr>
				</thead>
				<tbody>
					<tr class="pointer">
						<td ng-click="detail(obj)">
							<span ng-if="data.bartagOrder.regDate">
								{{data.bartagOrder.regDate | date:"yyyyMMdd"}}
							</span>
						</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.createDate}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.createSeq}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.createNo}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.productYy}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.productSeason}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.style}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.color}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.size}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.orderDegree}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.orderAmount | number:0}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.completeAmount | number:0}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.nonCheckCompleteAmount | number:0}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.additionAmount | number:0}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.nonCheckAdditionAmount | number:0}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.productionCompanyInfo.name}}</td>
						<td ng-click="detail(obj)">{{data.bartagOrder.detailProductionCompanyName ? data.bartagOrder.detailProductionCompanyName : '-'}}</td>
						<td ng-switch="data.bartagOrder.stat">
							<span ng-switch-when="1">초기생성</span>
							<span ng-switch-when="2">수량입력</span>
							<span ng-switch-when="3">확정완료</span>
							<span ng-switch-when="4">추가수량입력</span>
							<span ng-switch-when="5">추가완료</span>
							<span ng-switch-when="6">종결</span>
						</td>
						<td>
							<span ng-if="data.bartagOrder.updDate">
								{{data.bartagOrder.updDate | date:"yyyy-MM-dd"}}<br/>
								{{data.bartagOrder.updDate | date:"HH:mm:ss"}}
							</span>
							<span ng-if="!data.bartagOrder.updDate">
								-
							</span>
						</td>
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
						<th ng-click="sort('createDate')">발행일자</th>
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
					<tr ng-repeat="bar in data.bartagList.content" class="pointer" ng-if="data.bartagList.content.length > 0">
						<td>
							<div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="customCheck{{$index}}" ng-model="bar.check">
								<label class="custom-control-label" for="customCheck{{$index}}"></label>
							</div>
						</td>
						<td ng-click="detail(bar)">
							{{bar.regDate | date:"yyyyMMdd"}}
						</td>	
						<td ng-click="detail(bar)" >{{bar.createDate}}</td>
						<td ng-click="detail(bar)" >{{bar.seq}}</td>
						<td ng-click="detail(bar)" >{{bar.lineSeq}}</td>
						<td ng-click="detail(bar)" >{{bar.productYy}}</td>
						<td ng-click="detail(bar)" >{{bar.productSeason}}</td>
						<td ng-click="detail(bar)" >{{bar.erpKey}}</td>
						<td ng-click="detail(bar)" >{{bar.style}}</td>
						<td ng-click="detail(bar)" >{{bar.color}}</td>
						<td ng-click="detail(bar)" >{{bar.size}}</td>
						<td ng-click="detail(bar)" >{{bar.orderDegree}}</td>
						<td ng-click="detail(bar)" >{{bar.amount | number:0}}</td>
						<td ng-click="detail(bar)" >{{bar.productionCompanyInfo.name}}</td>
						<td ng-click="detail(bar)" >{{bar.detailProductionCompanyName ? bar.detailProductionCompanyName : '-'}}</td>
						<td ng-switch="bar.stat">
							<span ng-switch-when="0">미확정</span>
							<span ng-switch-when="1">초기 생성</span>
							<span ng-switch-when="2">발행 준비</span>
							<span ng-switch-when="3">발행 시작</span>
							<span ng-switch-when="4">발행 미완료</span>
							<span ng-switch-when="5">발행 종료</span>
							<span ng-switch-when="6">종결 처리</span>
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
						<td colspan="19" class="text-center" ng-if="data.bartagList.content.length == 0">
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
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button ng-click="completeAmountPop()" class="btn btn-primary">생산 수량 입력</button>
		<button ng-click="additionAmountPop()" class="btn btn-primary">추가 수량 입력</button>
		<button ng-click="deleteBartag()" class="btn btn-danger">삭제</button>
		<button ng-click="updateBartagPop()" class="btn btn-primary">수정</button>
		<button ng-click="complete()" class="btn btn-primary">확정</button>
    </div>
</html>
