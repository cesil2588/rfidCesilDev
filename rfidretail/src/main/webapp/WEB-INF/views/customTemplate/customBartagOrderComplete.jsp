<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">RFID 생산 수량 입력</h3>
	</div>
	<div class="modal-body" id="modal-body">
		<div class="card card-body" style="margin-bottom:5px;padding-bottom:1em;"> 
			<div class="form-row">
				<div class="form-group">
					<button class="btn btn-primary" type="button" ng-click="autoAmount()">자동 수량 입력</button>&nbsp;&nbsp;<button class="btn btn-primary" type="button" ng-click="autoAmountPlus()">자동 수량 입력(3% 가산)</button> <br />
					<span>※ 자동 수량 입력 버튼 클릭시 생산 수량 500개당 1개의 RFID 태그 수량이 추가되어 표시됩니다.</span><br/>
					<span>※ 자동 수량 입력(3% 가산) 버튼 클릭시 생산 수량의 3%씩 RFID 태그 수량이 추가되어 표시됩니다.(반올림)</span><br/>
				</div>
			</div>
		</div>
		<h5>Total({{list.length}}건)</h5>
		<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th>생성일자</th>
						<th>일련번호</th>
						<th>스타일</th>
						<th>컬러</th>
						<th>사이즈</th>
						<th>오더차수</th>
						<th>생산수량</th>
						<th style="width:200px;">생산 수량 입력</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="obj in list" class="pointer" ng-if="list.length > 0">
						<td>{{obj.createDate}}</td>
						<td>{{obj.createSeq}}</td>
						<td>{{obj.style}}</td>
						<td>{{obj.color}}</td>
						<td>{{obj.size}}</td>
						<td>{{obj.orderDegree}}</td>
						<td>{{obj.orderAmount | number:0}}</td>
						<td>
							<input type="number" class="form-control" ng-model="obj.nonCheckCompleteAmount" min="{{obj.nonCheckCompleteAmount}}">
						</td>
					</tr>
					<tr>
						<td colspan="8" class="text-center" ng-if="list.length == 0">
							No Data
						</td>
					</tr>
				</tbody>
			</table>
	</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button class="btn btn-primary" type="button" ng-click="ok()">생산 수량 입력 완료</button>
    </div>
</html>