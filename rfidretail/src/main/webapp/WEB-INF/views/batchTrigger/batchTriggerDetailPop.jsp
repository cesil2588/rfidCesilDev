<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">배치 예약 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<form name="customForm">
				<div class="card card-body">
					<div class="row">
						<div class="form-group col-3">
							<h5>작업 명</h5>
							<p ng-switch="data.type">
								<span ng-switch-when="1">발행 업로드 배치</span>
								<span ng-switch-when="3">일괄 생산 태그 입고 배치</span>
								<span ng-switch-when="4">재발행 업로드 배치</span>
							</p>
						</div>
						<div class="form-group col-3">
							<h5>상태</h5>
							{{data.status}}
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-3">
							<h5>등록 시간</h5>
							{{data.scheduleDate | date:'yyyy-MM-dd HH:mm:ss'}}
						</div>
						<div class="form-group col-3">
							<h5>시작 시간</h5>
							{{data.startDate | date:'yyyy-MM-dd HH:mm:ss'}}
						</div>
						<div class="form-group col-3">
							<h5>종료 시간</h5>
							{{data.endDate | date:'yyyy-MM-dd HH:mm:ss'}}
						</div>
					</div>
					<hr class="mb-4" ng-if="(data.type == '1' || data.type == '4') && data.detailSet.length > 0">
					<div class="row" ng-if="(data.type == '1' || data.type == '4') && data.detailSet.length > 0">
						<div class="form-group col-12">
							<h5>배치 작업 결과 상세</h5>
							<table class="table table-striped table-bordered table-hover text-center custom-align-middle pointer">
								<thead>
									<tr>
										<th>파일 다운로드</th>
										<th>생성 일자</th>
										<th>총 수량</th>
										<th>성공 수량</th>
										<th>실패 수량</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="detail in data.detailSet">
										<td>
											<a href="${pageContext.request.contextPath}/storage/{{detail.explanatory}}">{{detail.explanatory}}</a>&nbsp;&nbsp;<button uib-popover-template="tooltip.templateUrl" popover="{{tooltip.detail.resultMessage}}" type="button" class="badge badge-primary" ng-if="detail.resultMessage.length > 0" popover-class="custom-popover-lg">상세 보기</button>
										</td>
										<td>{{detail.createDate}}</td>
										<td>{{detail.totalAmount}}</td>
										<td>{{detail.successAmount}}</td>
										<td>{{detail.failAmount}}</td>
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