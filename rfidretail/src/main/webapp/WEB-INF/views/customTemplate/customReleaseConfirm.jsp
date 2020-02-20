<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">{{type == '01' ? '출고' : '반품'}} 확정</h3>
	</div>
	<div class="modal-body" id="modal-body">
		<div class="card card-body" style="margin-bottom:5px;padding-bottom:1em;"> 
			<div class="form-row">
				<div class="form-group form-inline">
					도착 예정일 설정 :
					&nbsp;&nbsp;
					<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="startDate" is-open="startDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:140px;margin-right:5px;"/>
					<span class="input-group-btn">
						<button type="button" class="btn btn-secondary" ng-click="startDateOpen()">
							달력 <i class="xi-calendar"></i>
						</button>
					</span>
					&nbsp;&nbsp;
					<button class="btn btn-primary" type="button" ng-click="arrivalDateChange()">적용</button>
				</div>
			</div>
		</div>
		<h5>Total({{list.length}}건)</h5>
		<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
			<thead>
				<tr class="pointer">
					<th>
						<div class="custom-control custom-checkbox">
							<input class="custom-control-input" type="checkbox" ng-model="allCheckFlag" ng-click="allCheck()" id="allCheck"/>
							<label class="custom-control-label" for="allCheck">체크</label>
						</div>
					</th>
					<th ng-click="sort('boxInfo.createDate')">작업일자</th>
					<th ng-click="sort('boxInfo.workLine')">작업라인</th>
					<th ng-click="sort('boxInfo.barcode')">바코드</th>
					<th ng-click="sort('boxInfo.startCompanyInfo.name')">업체명</th>
					<th ng-click="sort('confirmYn')">확정여부</th>
					<th ng-click="sort('boxInfo.arrivalDate')">도착예정일</th>
					<th>스타일수량</th>
					<th>태그수량</th>
					<th ng-click="sort('regUserInfo.userId')">등록자</th>
					<th ng-click="sort('updUserInfo.userId')">수정자</th>
					<th>상태</th>
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
					<td ng-click="detail(obj)">{{obj.boxInfo.startCompanyInfo.name}}</td>
					<td ng-click="detail(obj)">{{obj.confirmYn}}</td>
					<td ng-click="detail(obj)">{{obj.boxInfo.arrivalDate | date:'yyyy-MM-dd'}}</td>
					<td ng-click="detail(obj)">{{obj.storageScheduleDetailLog.length}}</td>
					<td ng-click="detail(obj)">{{getTotalAmount(obj)}}</td>
					<td ng-click="detail(obj)">{{obj.regUserInfo.userId}}</td>
					<td ng-click="detail(obj)">{{obj.updUserInfo.userId}}</td>
					<td ng-click="detail(obj)">
						<span ng-if="obj.completeYn == 'N'">{{type == '01' ? '생산' : '매장'}} 출고</span>
						<span ng-if="obj.completeYn == 'Y'">물류 입고 완료</span>
					</td>
				</tr>
				<tr>
					<td colspan="12" class="text-center" ng-if="list.length == 0">
						No Data
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button class="btn btn-primary" type="button" ng-click="ok()">확정 완료</button>
    </div>
</html>