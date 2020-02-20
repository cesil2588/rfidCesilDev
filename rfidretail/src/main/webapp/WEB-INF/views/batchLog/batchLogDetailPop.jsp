<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">배치 로그 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<form name="customForm">
				<div class="card card-body">
					<div class="row">
						<div class="form-group col-3">
							<h5>배치 이름</h5>
							{{data.jobName}}
						</div>
						<div class="form-group col-3">
							<h5>배치 결과</h5>
							{{data.status}}
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-3">
							<h5>생성 시간</h5>
							{{data.createTime | date:'yyyy-MM-dd HH:mm:ss'}}
						</div>
						<div class="form-group col-3">
							<h5>시작 시간</h5>
							{{data.startTime | date:'yyyy-MM-dd HH:mm:ss'}}
						</div>
						<div class="form-group col-3">
							<h5>종료 시간</h5>
							{{data.endTime | date:'yyyy-MM-dd HH:mm:ss'}}
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-12">
							<h5>배치 스텝 상세</h5>
							<table class="table table-striped table-bordered table-hover text-center pointer custom-align-middle">
								<thead>
									<tr>
										<th>스텝 명</th>
										<th>결과</th>
										<th>Read Count</th>
										<th>Read Skip Count</th>
										<th>Process Skip Count</th>
										<th>Write Count</th>
										<th>Write Skip Count</th>
										<th>Commit Count</th>
										<th>Rollback Count</th>
										<th>시작 일시</th>
										<th>종료 일시</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="step in data.batchLogStep" ng-if="data.batchLogStep.length > 0">
										<td>{{step.name}}</td>
										<td>{{step.status}}&nbsp;&nbsp;<button uib-popover-template="tooltip.templateUrl" popover="{{tooltip.step.errorMessage}}" type="button" class="badge badge-primary" ng-if="step.status == 'FAILED' || step.status == 'ABANDONED'" popover-class="custom-popover-lg">상세 보기</button></td>
										<td>{{step.readCount}}</td>
										<td>{{step.readSkipCount}}</td>
										<td>{{step.processSkipCount}}</td>
										<td>{{step.writeCount}}</td>
										<td>{{step.writeSkipCount}}</td>
										<td>{{step.commitCount}}</td>
										<td>{{step.rollbackCount}}</td>
										<td>{{step.startTime | date:'yyyy-MM-dd HH:mm:ss'}}</td>
										<td>{{step.endTime | date:'yyyy-MM-dd HH:mm:ss'}}</td>
									</tr>
									<tr>
										<td colspan="11" class="text-center" ng-if="data.batchLogStep.length == 0">
											No Data
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