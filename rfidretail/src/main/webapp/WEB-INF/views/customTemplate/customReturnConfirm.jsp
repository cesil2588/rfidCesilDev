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
						<th style="width:80px;">
							<div class="custom-control custom-checkbox">
								<input class="custom-control-input" type="checkbox" ng-model="allCheckFlag" ng-click="allCheck()" id="allCheck"/>
								<label class="custom-control-label" for="allCheck">체크</label>
							</div>
						</th>
						<th ng-click="sort('boxInfo.barcode')">바코드</th>
						<th ng-click="sort('returnType')">반품타입</th>
						<th ng-click="sort('boxInfo.startCompanyInfo.name')">출발지</th>
						<th ng-click="sort('boxInfo.endCompanyInfo.name')">도착지</th>
						<th ng-click="sort('boxInfo.arrivalDate')">도착 예정일</th>
						<th ng-click="sort('confirmYn')">확정여부</th>
						<th ng-click="sort('confirmDate')">확정일자</th>
						<th ng-click="sort('completeYn')">물류입고여부</th>
						<th ng-click="sort('completeDate')">물류입고일자</th>
						<th>태그 수량</th>
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
						<td ng-click="detail(obj)">{{obj.boxInfo.barcode}}</td>
						<td ng-click="detail(obj)" ng-switch="obj.returnType">
							<span ng-switch-when="10">일반반품</span>
							<span ng-switch-when="20">비용반품</span>
							<span ng-switch-when="40">불량반품</span>
							<span ng-switch-when="90">계절반품</span>
						</td>
						<td ng-click="detail(obj)">{{obj.boxInfo.startCompanyInfo.name}}</td>
						<td ng-click="detail(obj)">{{obj.boxInfo.endCompanyInfo.name}}</td>
						<td ng-click="detail(obj)">
							<span ng-if="obj.boxInfo.arrivalDate">
								{{obj.boxInfo.arrivalDate | date:'yyyy-MM-dd'}}
							</span>
							<span ng-if="!obj.boxInfo.arrivalDate">
								-
							</span>
						</td>
						<td ng-click="detail(obj)">{{obj.confirmYn}}</td>
						<td ng-click="detail(obj)">
							<span ng-if="obj.confirmDate">
								{{obj.confirmDate | date:'yyyy-MM-dd'}}<br/>
								{{obj.confirmDate | date:'HH:mm:ss'}}<br/>
							</span>
							<span ng-if="!obj.confirmDate">
								-
							</span>
						</td>
						<td ng-click="detail(obj)">{{obj.completeYn}}</td>
						<td ng-click="detail(obj)">
							<span ng-if="obj.completeDate">
								{{obj.completeDate | date:'yyyy-MM-dd'}}<br/>
								{{obj.completeDate | date:'HH:mm:ss'}}<br/>
							</span>
							<span ng-if="!obj.completeDate">
								-
							</span>
						</td>
						<td ng-click="detail(obj)">
							{{getTotalAmount(obj)}}
						</td>
						<td ng-click="detail(obj)">
							<span ng-if="obj.confirmYn == 'N' && obj.completeYn == 'N'">반품 예정</span>
							<span ng-if="obj.confirmYn == 'Y' && obj.completeYn == 'N'">반품 확정</span>
							<span ng-if="obj.confirmYn == 'Y' && obj.completeYn == 'Y'">반품 완료</span>
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