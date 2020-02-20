<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">매장간 이동 상세</h3>
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
										<th ng-click="sort('createDate')">작업일자</th>
										<th ng-click="sort('workLine')">작업라인</th>
										<th ng-click="sort('boxInfo.barcode')">바코드</th>
										<th ng-click="sort('returnType')">반품타입</th>
										<th ng-click="sort('boxInfo.startCompanyInfo.name')">출발업체</th>
										<th ng-click="sort('boxInfo.endCompanyInfo.name')">도착업체</th>
										<th ng-click="sort('startWorkYn')">출발업체<br/>작업여부</th>
										<th ng-click="sort('startWorkDate')">출발업체<br/>작업일자</th>
										<th ng-click="sort('startConfirmYn')">출발업체<br/>확정여부</th>
										<th ng-click="sort('startConfirmDate')">출발업체<br/>확정일자</th>
										<th ng-click="sort('endWorkYn')">도착업체<br/>작업여부</th>
										<th ng-click="sort('endWorkDate')">도착업체<br/>작업일자</th>
										<th ng-click="sort('endConfirmYn')">도착업체<br/>확정여부</th>
										<th ng-click="sort('endConfirmDate')">도착업체<br/>확정일자</th>
										<th ng-click="sort('disuseYn')">종결 여부</th>
										<th>요청 태그 수량</th>
										<th>확정 태그 수량</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td ng-click="detail(obj)">{{obj.createDate}}</td>
										<td ng-click="detail(obj)">{{obj.workLine}}</td>
										<td ng-click="detail(obj)">{{obj.boxInfo.barcode}}</td>
										<td ng-click="detail(obj)" ng-switch="obj.returnType">
											<span ng-switch-when="10">일반반품</span>
											<span ng-switch-when="15">이동반품</span>
											<span ng-switch-when="20">비용반품</span>
											<span ng-switch-when="40">불량반품</span>
											<span ng-switch-when="80">일반반품대기</span>
											<span ng-switch-when="90">계절반품</span>
										</td>
										<td ng-click="detail(obj)">{{obj.boxInfo.startCompanyInfo.name}}</td>
										<td ng-click="detail(obj)">{{obj.boxInfo.endCompanyInfo.name}}</td>
										<td ng-click="detail(obj)">{{obj.startWorkYn}}</td>
										<td ng-click="detail(obj)">
											<span ng-if="obj.startWorkDate">
												{{obj.startWorkDate | date:'yyyy-MM-dd'}}<br/>
												{{obj.startWorkDate | date:'HH:mm:ss'}}<br/>
											</span>
											<span ng-if="!obj.startWorkDate">
												-
											</span>
										</td>
										<td ng-click="detail(obj)">{{obj.startConfirmYn}}</td>
										<td ng-click="detail(obj)">
											<span ng-if="obj.startConfirmDate">
												{{obj.startConfirmDate | date:'yyyy-MM-dd'}}<br/>
												{{obj.startConfirmDate | date:'HH:mm:ss'}}<br/>
											</span>
											<span ng-if="!obj.startConfirmDate">
												-
											</span>
										</td>
										<td ng-click="detail(obj)">{{obj.endWorkYn}}</td>
										<td ng-click="detail(obj)">
											<span ng-if="obj.endWorkDate">
												{{obj.endWorkDate | date:'yyyy-MM-dd'}}<br/>
												{{obj.endWorkDate | date:'HH:mm:ss'}}<br/>
											</span>
											<span ng-if="!obj.endWorkDate">
												-
											</span>
										</td>
										<td ng-click="detail(obj)">{{obj.endConfirmYn}}</td>
										<td ng-click="detail(obj)">
											<span ng-if="obj.endConfirmDate">
												{{obj.endConfirmDate | date:'yyyy-MM-dd'}}<br/>
												{{obj.endConfirmDate | date:'HH:mm:ss'}}<br/>
											</span>
											<span ng-if="!obj.endConfirmDate">
												-
											</span>
										</td>
										<td ng-click="detail(obj)">{{obj.disuseYn}}</td>
										<td ng-click="detail(obj)">
											{{getTotalAmount(obj, 'amount')}}
										</td>
										<td ng-click="detail(obj)">
											{{getTotalAmount(obj, 'completeAmount')}}
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-12">
							<h5>스타일 목록</h5>
							<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
								<thead>
									<tr class="pointer">
										<th>스타일</th>
										<th>컬러</th>
										<th>사이즈</th>
										<th>오더차수</th>
										<th>수량</th>
										<th>확정 수량</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="style in obj.storeMoveDetailLog" class="pointer" ng-click="detail(style)">
										<td>{{style.style}}</td>
										<td>{{style.color}}</td>
										<td>{{style.size}}</td>
										<td>{{style.orderDegree}}</td>
										<td>{{style.amount}}</td>
										<td>{{style.completeAmount}}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<hr class="mb-4" ng-if="currentStyle">
					<div class="row"  ng-if="currentStyle">
						<div class="form-group col-12">
							<h5>태그 목록</h5>
							<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
								<thead>
									<tr class="pointer">
										<th>스타일</th>
										<th>컬러</th>
										<th>사이즈</th>
										<th>오더차수</th>
										<th>일련번호</th>
										<th>상태</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="tag in currentStyle.storeMoveSubDetailLog | orderBy : 'rfidTag'" class="pointer">
										<td>{{currentStyle.style}}</td>
										<td>{{currentStyle.color}}</td>
										<td>{{currentStyle.size}}</td>
										<td>{{currentStyle.orderDegree}}</td>
										<td>{{tag.rfidTag.substr(27)}}</td>
										<td ng-switch="tag.stat">
											<span ng-switch-when="1">요청</span>
											<span ng-switch-when="2">확정완료</span>
											<span ng-switch-when="3">신규</span>
										</td>
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