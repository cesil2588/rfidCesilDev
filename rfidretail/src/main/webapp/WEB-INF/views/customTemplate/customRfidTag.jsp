<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">RFID 태그 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
		<h5>태그 스타일 </h5>
		<div class="form-group">
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th>시즌</th>
						<th>스타일</th>
						<th>컬러</th>
						<th>사이즈</th>
						<th>오더차수</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>{{data.bartagDetail.productSeason}}</td>
						<td>{{data.bartagDetail.style}}</td>
						<td>{{data.bartagDetail.color}}</td>
						<td>{{data.bartagDetail.size}}</td>
						<td>{{data.bartagDetail.orderDegree}}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<h5>태그 발행 정보 </h5>
		<div class="form-group">
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th>RFID태그</th>
						<th>ERP키</th>
						<th>시즌</th>
						<th>오더차수</th>
						<th>생산업체코드</th>
						<th>발행장소</th>
						<th>발행날짜</th>
						<th>발행차수</th>
						<th>일련번호</th>
						<th>상태</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>{{data.detail.rfidTag}}</td>
						<td>{{data.detail.erpKey}}</td>
						<td>{{data.detail.season}}</td>
						<td>{{data.detail.orderDegree}}</td>
						<td>{{data.detail.customerCd}}</td>
						<td>{{data.detail.publishLocation}}</td>
						<td>{{data.detail.publishRegDate}}</td>
						<td>{{data.detail.publishDegree}}</td>
						<td>{{data.detail.rfidSeq}}</td>
						<td ng-switch="data.detail.stat">
							<span ng-switch-when="1">미발행</span>
							<span ng-switch-when="2">발행대기</span> 
							<span ng-switch-when="3">발행완료</span>
							<span ng-switch-when="4">재발행대기</span> 
							<span ng-switch-when="5">재발행</span>
							<span ng-switch-when="6">재발행요청</span> 
							<span ng-switch-when="7">폐기</span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<h5>태그 상태</h5>
		<div class="form-group">
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th>상태</th>
						<th>비고</th>
						<th>등록자</th>
						<th>등록일</th>
						<th>수정자</th>
						<th>수정일</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td><span code-name-fix pcode="10011" code="{{data.statusDetail.stat}}"></span></td>
						<td>{{data.statusDetail.explanatory}}</td>
						<td>{{data.statusDetail.regUserInfo.userId}}</td>
						<td>{{data.statusDetail.regDate | date:"yyyy-MM-dd HH:mm:ss"}}</td>
						<td>{{data.statusDetail.updUserInfo.userId}}</td>
						<td>{{data.statusDetail.updDate | date:"yyyy-MM-dd HH:mm:ss"}}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<h5>태그 이력 목록</h5>
		<div class="form-group">
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th ng-click="sort('work')">상태</th>
						<th ng-click="sort('regUserInfo.userId')">작업자</th>
						<th ng-click="sort('companyInfo.name')">작업업체</th>
						<th ng-click="sort('explanatory')">비고</th>
						<th ng-click="sort('regDate')">등록일</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="item in data.historyDetail" ng-if="data.historyDetail.length > 0">
						<td><span code-name pcode="10008" code="{{item.work}}"></span></td>
						<td>{{item.regUserInfo.userId}}</td>
						<td>{{item.companyInfo.name}}</td>
						<td>{{item.explanatory}}</td>
						<td>{{item.regDate | date:"yyyy-MM-dd HH:mm:ss"}}</td>
					</tr>
					<tr ng-if="list.length == 0">
						<td colspan="4" class="text-center">No Data</td>
					</tr>
				</tbody>
			</table>
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
	</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="ok()">닫기</button>
    </div>
</html>