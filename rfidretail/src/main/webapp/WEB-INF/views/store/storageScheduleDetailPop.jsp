<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">입고예정 상세</h3>
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
										<th>생성일자</th>
										<th>바코드</th>
										<th>내용</th>
										<th>출발지</th>
										<th>도착지</th>
										<th>입고여부</th>
										<th>태그 수량</th>
										<th>상태</th>
										<th>등록자</th>
										<th>등록일</th>
										<th>수정자</th>
										<th>수정일</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>{{data.createDate}}</td>
										<td>{{data.boxInfo.barcode}}</td>
										<td>{{data.boxInfo.type == '01' ? '생산' : data.boxInfo.type == '02' ? '물류' : '매장'}}</td>
										<td>{{data.boxInfo.startCompanyInfo.name}}</td>
										<td>{{data.boxInfo.endCompanyInfo.name}}</td>
										<td>{{data.completeYn}}</td>
										<td>
											{{getTotalAmount(data)}}
										</td>
										<td>
											<span ng-if="data.releaseYn == 'Y' && data.completeYn == 'N'">입고 예정</span>
											<span ng-if="data.releaseYn == 'Y' && data.completeYn == 'Y'">입고 완료</span>
										</td>
										<td>{{data.regUserInfo.userId ? data.regUserInfo.userId : '-'}}</td>
										<td>
											<span ng-if="data.regDate">
												{{data.regDate | date:'yyyy-MM-dd'}}<br />
												{{data.regDate | date:'HH:mm:ss'}}
											</span>
											<span ng-if="!data.regDate">
												-
											</span>
										</td>
										<td>{{data.updUserInfo.userId ? data.updUserInfo.userId : '-'}}</td>
										<td>
											<span ng-if="data.updDate">
												{{data.updDate | date:'yyyy-MM-dd'}}<br />
												{{data.updDate | date:'HH:mm:ss'}}
											</span>
											<span ng-if="!data.updDate">
												-
											</span>
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
										<th>RFID여부</th>
										<th>수량</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="style in data.releaseScheduleDetailLog" class="pointer" ng-click="detail(style)">
										<td>{{style.style}}</td>
										<td>{{style.color}}</td>
										<td>{{style.size}}</td>
										<td>{{style.rfidYn}}</td>
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