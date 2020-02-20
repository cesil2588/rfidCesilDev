<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">매장 출고예정 상세</h3>
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
										<th>마감일자</th>
										<th>마감타입</th>
										<th>마감번호</th>
										<th>도착지</th>
										<th>스타일</th>
										<th>컬러</th>
										<th>사이즈</th>
										<th>주문수량</th>
										<th>출고수량</th>
										<th>상태</th>
										<th>등록자</th>
										<th>등록일</th>
										<th>수정자</th>
										<th>수정일</th>
									</tr>
								</thead>
								<tbody>
									<tr class="pointer">
										<td>{{data.erpStoreSchedule.completeDate}}</td>
										<td>{{data.erpStoreSchedule.completeType}}</td>
										<td>{{data.erpStoreSchedule.completeSeq}}</td>
										<td>{{data.erpStoreSchedule.endCompanyInfo.name}}</td>
										<td>{{data.erpStoreSchedule.style}}</td>
										<td>{{data.erpStoreSchedule.color}}</td>
										<td>{{data.erpStoreSchedule.size}}</td>
										<td>{{data.erpStoreSchedule.orderAmount}}</td>
										<td>{{data.erpStoreSchedule.releaseAmount}}</td>
										<td>{{data.erpStoreSchedule.stat}}</td>
										<td>{{data.erpStoreSchedule.regUserInfo.userId ? data.erpStoreSchedule.regUserInfo.userId : '-'}}</td>
										<td>
											<span ng-if="data.erpStoreSchedule.regDate">
												{{data.erpStoreSchedule.regDate | date:'yyyy-MM-dd'}}<br />
												{{data.erpStoreSchedule.regDate | date:'HH:mm:ss'}}
											</span>
											<span ng-if="!data.erpStoreSchedule.regDate">
												-
											</span>
										</td>
										<td>{{data.erpStoreSchedule.updUserInfo.userId ? data.erpStoreSchedule.updUserInfo.userId : '-'}}</td>
										<td>
											<span ng-if="data.erpStoreSchedule.updDate">
												{{data.erpStoreSchedule.updDate | date:'yyyy-MM-dd'}}<br />
												{{data.erpStoreSchedule.updDate | date:'HH:mm:ss'}}
											</span>
											<span ng-if="!data.erpStoreSchedule.updDate">
												-
											</span>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<hr class="mb-4" ng-if="data.releaseScheduleDetailLogList.length > 0">
					<div class="row" ng-if="data.releaseScheduleDetailLogList.length > 0">
						<div class="form-group col-12">
							<h5>스타일 목록</h5>
							<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
								<thead>
									<tr class="pointer">
										<th>스타일</th>
										<th>컬러</th>
										<th>사이즈</th>
										<th>수량</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="style in data.releaseScheduleDetailLogList" class="pointer" ng-click="detail(style)">
										<td>{{style.style}}</td>
										<td>{{style.color}}</td>
										<td>{{style.size}}</td>
										<td>{{style.amount}}</td>
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
										<th>일련번호</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="tag in currentStyle.releaseScheduleSubDetailLog | orderBy : 'rfidTag'" class="pointer">
										<td>{{currentStyle.style}}</td>
										<td>{{currentStyle.color}}</td>
										<td>{{currentStyle.size}}</td>
										<td>{{tag.rfidTag.substr(27)}}</td>
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