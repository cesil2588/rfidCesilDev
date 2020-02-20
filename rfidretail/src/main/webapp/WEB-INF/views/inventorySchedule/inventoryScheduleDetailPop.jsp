<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">재고 조사 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<form name="customForm">
				<div class="card card-body">
					<div class="row">
						<div class="form-group col-12">
							<h5>재고 조사 정보</h5>
							<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
								<thead>
									<tr class="pointer">
										<th ng-click="sort('createDate')">작업일자</th>
										<th ng-click="sort('workLine')">작업라인</th>
										<th ng-click="sort('companyInfo.name')">업체명</th>
										<th ng-click="sort('explanatory')">비고</th>
										<th ng-click="sort('confirmYn')">확정여부</th>
										<th ng-click="sort('confirmDate')">확정일자</th>
										<th ng-click="sort('completeYn')">완료여부</th>
										<th ng-click="sort('completeDate')">완료일자</th>
										<th>스타일 수량</th>
										<th>태그 수량</th>
										<th>상태</th>
										<th ng-click="sort('regUserInfo.userId')">등록자</th>
										<th ng-click="sort('regDate')">등록일</th>
										<th ng-click="sort('updUserInfo.userId')">수정자</th>
										<th ng-click="sort('updDate')">수정일</th>
									</tr>
								</thead>
								<tbody>
									<tr class="pointer">
										<td ng-click="detail(obj)">{{obj.createDate}}</td>
										<td ng-click="detail(obj)">{{obj.workLine}}</td>
										<td ng-click="detail(obj)">{{obj.companyInfo.name}}</td>
										<td ng-click="detail(obj)">{{obj.explanatory}}</td>
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
											{{getTotalStyleAmount(obj)}}
										</td>
										<td ng-click="detail(obj)">
											{{getTotalTagAmount(obj)}}
										</td>
										<td ng-click="detail(obj)">
											<span ng-if="obj.confirmYn == 'N' && obj.completeYn == 'N'">신규</span>
											<span ng-if="obj.confirmYn == 'Y' && obj.completeYn == 'N'">확정</span>
											<span ng-if="obj.confirmYn == 'Y' && obj.completeYn == 'Y'">완료</span>
										</td>
										<td ng-click="detail(obj)">{{obj.regUserInfo.userId ? obj.regUserInfo.userId : '-'}}</td>
										<td ng-click="detail(obj)">
											<span ng-if="obj.regDate">
												{{obj.regDate | date:'yyyy-MM-dd'}}<br />
												{{obj.regDate | date:'HH:mm:ss'}}
											</span>
											<span ng-if="!obj.regDate">
												-
											</span>
										</td>
										<td ng-click="detail(obj)">{{obj.updUserInfo.userId ? obj.updUserInfo.userId : '-'}}</td>
										<td ng-click="detail(obj)">
											<span ng-if="obj.updDate">
												{{obj.updDate | date:'yyyy-MM-dd'}}<br />
												{{obj.updDate | date:'HH:mm:ss'}}
											</span>
											<span ng-if="!obj.updDate">
												-
											</span>
										</td>
									</tr>
									<tr>
										<td colspan="17" class="text-center" ng-if="list.length == 0">
											No Data
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
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="style in obj.styleList" class="pointer" ng-click="detail(style)">
										<td>{{style.style}}</td>
										<td>{{style.color}}</td>
										<td>{{style.size}}</td>
										<td>{{style.orderDegree}}</td>
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
										<th>오더차수</th>
										<th>일련번호</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="tag in currentStyle.rfidTagList | orderBy : 'rfidTag'" class="pointer">
										<td>{{currentStyle.style}}</td>
										<td>{{currentStyle.color}}</td>
										<td>{{currentStyle.size}}</td>
										<td>{{currentStyle.orderDegree}}</td>
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