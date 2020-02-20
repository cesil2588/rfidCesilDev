<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">RFID 태그 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
		<div class="form-group">
			<h5>태그 발행 정보 </h5>
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
						<td>{{data.rfidTagDetail.rfidTag}}</td>
						<td>{{data.rfidTagDetail.erpKey}}</td>
						<td>{{data.rfidTagDetail.season}}</td>
						<td>{{data.rfidTagDetail.orderDegree}}</td>
						<td>{{data.rfidTagDetail.customerCd}}</td>
						<td>{{data.rfidTagDetail.publishLocation}}</td>
						<td>{{data.rfidTagDetail.publishRegDate}}</td>
						<td>{{data.rfidTagDetail.publishDegree}}</td>
						<td>{{data.rfidTagDetail.rfidSeq}}</td>
						<td ng-switch="data.rfidTagDetail.stat">
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
		<div class="form-group">
			<h5>위치별 태그 정보</h5>
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th>위치</th>
						<th>맵핑 바코드</th>
						<th>상태</th>
						<th>위치</th>
						<th>맵핑 바코드</th>
						<th>상태</th>
						<th>위치</th>
						<th>맵핑 바코드</th>
						<th>상태</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>생산</td>
						<td>{{data.rfidTagDetail.productionStorageRfidTag.boxBarcode != undefined ? data.rfidTagDetail.productionStorageRfidTag.boxBarcode : '-'}}</td>
						<td ng-switch="data.rfidTagDetail.productionStorageRfidTag.stat">
							<span ng-switch-when="1">입고예정</span>
							<span ng-switch-when="2">입고</span> 
							<span ng-switch-when="3">출고</span>
							<span ng-switch-when="4">재발행요청</span> 
							<span ng-switch-when="5">폐기</span>
							<span ng-switch-when="6">반품검수</span> 
							<span ng-switch-when="7">반품</span>
							<span ng-switch-default>-</span>
						</td>
						<td>물류</td>
						<td>{{data.rfidTagDetail.distributionStorageRfidTag.boxInfo.barcode != undefined ? data.rfidTagDetail.distributionStorageRfidTag.boxInfo.barcode : '-'}}</td>
						<td ng-switch="data.rfidTagDetail.distributionStorageRfidTag.stat">
							<span ng-switch-when="1">입고예정</span>
							<span ng-switch-when="2">입고</span> 
							<span ng-switch-when="3">출고</span>
							<span ng-switch-when="4">재발행대기</span> 
							<span ng-switch-when="5">재발행요청</span>
							<span ng-switch-when="6">반품</span> 
							<span ng-switch-when="7">폐기</span>
							<span ng-switch-default>-</span>
						</td>
						<td>매장</td>
						<td>{{data.rfidTagDetail.storeStorageRfidTag.boxInfo.barcode != undefined ? data.rfidTagDetail.storeStorageRfidTag.boxInfo.barcode : '-'}}</td>
						<td ng-switch="data.rfidTagDetail.storeStorageRfidTag.stat">
							<span ng-switch-when="1">입고예정</span>
							<span ng-switch-when="2">입고</span> 
							<span ng-switch-when="3">판매</span>
							<span ng-switch-when="4">반품</span> 
							<span ng-switch-when="5">재발행요청</span>
							<span ng-switch-when="6">매장간이동</span> 
							<span ng-switch-when="7">폐기</span>
							<span ng-switch-default>-</span>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="form-group">
			<h5>태그 이력 목록</h5>
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th>상태</th>
						<th>작업자</th>
						<th>작업업체</th>
						<th>비고</th>
						<th>등록일</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="obj in data.rfidTagDetail.rfidTagHistoryList" ng-if="data.rfidTagDetail.rfidTagHistoryList.length > 0">
						<td><span code-name pcode="10008" code="{{obj.work}}"></span></td>
						<td>{{obj.regUserInfo.userId}}</td>
						<td>{{obj.companyInfo.name}}</td>
						<td>{{obj.explanatory}}</td>
						<td>{{obj.regDate | date:"yyyy-MM-dd HH:mm:ss"}}</td>
					</tr>
					<tr ng-if="data.rfidTagDetail.rfidTagHistoryList.length == 0">
						<td colspan="4" class="text-center">No Data</td>
					</tr>
				</tbody>
			</table>
		</div>
</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="ok()">닫기</button>
    </div>
</html>