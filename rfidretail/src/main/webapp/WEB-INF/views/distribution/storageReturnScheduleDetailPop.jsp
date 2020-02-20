<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">반품 입고 미결번호별 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<form name="customForm">
				<div class="card card-body">
					<div class="row">
						<div class="form-group col-12">
							<h5>박스 정보</h5>
							<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
								<thead>
									<tr class="pointer">
										<th ng-click="sort('style')">스타일</th>
										<th ng-click="sort('color')">컬러</th>
										<th ng-click="sort('size')">사이즈</th>
										<th ng-click="sort('orderDegree')">오더차수</th>
										<th ng-click="sort('amount')">수량</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="obj in data" class="pointer" ng-if="data.length > 0">
										<td>{{obj.style}}</td>
										<td>{{obj.color}}</td>
										<td>{{obj.size}}</td>
										<td>{{obj.orderDegree}}</td>
										<td>{{obj.amount}}</td>
									</tr>
									<tr class="pointer" ng-if="data.length == 0">
										<td colspan="5">데이터 없음</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</form>
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
    </div>
</html>