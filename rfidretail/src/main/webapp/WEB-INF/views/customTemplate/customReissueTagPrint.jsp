<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">태그 재발행 프린트</h3>
	</div>
	<div class="modal-body" id="modal-body">
		<h5>재발행 대상 태그 목록</h5>
		<div class="form-group">
			<div class="card card-body">
				<table class="table table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th style="width: 90px;">
							<div class="custom-control custom-checkbox">
								<input class="custom-control-input" type="checkbox" ng-model="allCheckFlag" ng-click="allCheck()" id="allCheck"/>
								<label class="custom-control-label" for="allCheck">체크</label>
							</div>
						</th>
						<th ng-click="sort('createDate')">발행일자</th>
						<th ng-click="sort('lineSeq')">라인번호</th>
						<th ng-click="sort('seq')">일련번호</th>
						<th ng-click="sort('productRfidSeason')">시즌</th>
						<th ng-click="sort('style')">스타일</th>
						<th ng-click="sort('color')">컬러</th>
						<th ng-click="sort('size')">사이즈</th>
						<th ng-click="sort('orderDegree')">오더차수</th>
						<th>바코드</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="rfid in search.popupList track by $index" ng-if="search.popupList.length > 0">
						<td>
							<div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="customCheck{{$index}}" ng-model="rfid.check" ng-change="checkBoxSelected(rfid)">
							  	<label class="custom-control-label" for="customCheck{{$index}}"></label>
							</div>
						</td>
						<td class="pointer">{{rfid.createDate}}</td>
						<td class="pointer">{{rfid.lineSeq}}</td>
						<td class="pointer">{{rfid.seq}}</td>
						<td class="pointer">{{rfid.productRfidSeason}}</td>
						<td class="pointer">{{rfid.style}}</td>
						<td class="pointer">{{rfid.color}}</td>
						<td class="pointer">{{rfid.size}}</td>
						<td class="pointer">{{rfid.orderDegree}}</td>
						<td class="pointer">{{rfid.productionStorage.bartagMaster.erpKey}}{{rfid.rfidSeq}}</td>
					</tr>
					<tr ng-if="search.popupList.length == 0">
						<td colspan="11" class="text-center">
							No Data
						</td>
					</tr>
				</tbody>
			</table>
			</div>
		</div>
		<div class="text-center">
			<div class="form-group">
				<button class="btn btn-primary justify-content-center" type="button" ng-click="add()"><i class="xi-arrow-bottom" aria-hidden="true"></i> 추가</button>
			</div>
		</div>
		<h5>선택된 태그 목록</h5>
		<div class="form-group">
			<div class="card card-body">
				<table class="table table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th>이전 RFID 태그</th>
						<th>발행장소</th>
						<th>발행일자</th>
						<th>발행차수</th>
						<th>신규 RFID 태그</th>
						<th>작업</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="rfid in result.list track by $index" ng-if="result.list.length > 0">
						<td class="pointer">{{rfid.rfidTag}}</td>
						<td class="pointer">{{rfid.publishLocation}}</td>
						<td class="pointer">{{rfid.publishRegDate}}</td>
						<td class="pointer">{{rfid.publishDegree}}</td>
						<td class="pointer">{{rfid.reissueRfidTag}}</td>
						<td>
							<button class="btn btn-danger justify-content-center" type="button" ng-click="del($index)">삭제</button>
						</td>
					</tr>
					<tr ng-if="result.list.length == 0">
						<td colspan="6" class="text-center">
							No Data
						</td>
					</tr>
				</tbody>
			</table>
			</div>
		</div>
		<div class="form-row">
			<div class="form-group">
				<button class="btn btn-primary" type="button" ng-click="selectInit()">선택된 태그 초기화</button>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button class="btn btn-primary" type="button" ng-click="ok()">재발행 프린트 요청</button>
    </div>
</html>