<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2>배치 설정</h2>
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th ng-click="sort('command')">명렁어</th>
						<th ng-click="sort('cron')">크론 표현식</th>
						<th ng-click="sort('delay')">딜레이 타임</th>
						<th ng-click="sort('jobType')">배치 타입</th>
						<th>사용 여부</th>
						<th>테스트</th>
						<th ng-click="sort('regUserInfo.userId')">등록자</th>
						<th ng-click="sort('regDate')">등록일</th>
						<th ng-click="sort('updUserInfo.userId')">수정자</th>
						<th ng-click="sort('updDate')">수정일</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="batch in list" ng-if="list.length > 0">
						<td ng-click="detail(batch)" class="pointer">{{batch.command}}</td>
						<td>{{batch.cron}}</td>
						<td>{{batch.delay}}</td>
						<td>{{batch.jobType}}</td>
						<td>
							<label class="switch">
							  	<input type="checkbox" ng-model="batch.useYn" ng-change="changeFlag(batch)" ng-true-value="'Y'" ng-false-value="'N'">
							  	<span class="slider round"></span>
							</label>
						</td>
						<td>
							<button type="button" class="btn btn-primary" ng-click="actionTest(batch)">테스트 실행</button>
						</td>
						<td>{{batch.regUserInfo.userId}}</td>
						<td>{{batch.regDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
						<td>{{batch.updUserInfo.userId}}</td>
						<td>{{batch.updDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
					</tr>
					<tr>
						<td colspan="10" class="text-center" ng-if="list.length == 0">
							No Data
						</td>
					</tr>
				</tbody>
			</table>
			<form name="customForm">
				<div class="card card-body">
					<div class="row">
						<div class="form-group col-3">
							<h5>명령어</h5>
							<input type="text" class="form-control" name="command" id="command" placeholder="명령어를 입력하세요." ng-model="data.command">
						</div>
						<div class="form-group col-3">
							<h5>배치타입</h5>
							<div class="custom-control custom-radio custom-control-inline">
								<input name="inputBatchType" id="inputBatchType1" type="radio" ng-model="data.jobType" value="cron" class="custom-control-input">
								<label class="custom-control-label" for="inputBatchType1">크론타입</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input name="inputBatchType" id="inputBatchType2" type="radio" ng-model="data.jobType" value="delay" class="custom-control-input">
								<label class="custom-control-label" for="inputBatchType2">딜레이타입</label>
							</div>
						</div>
						<div class="form-group col-3">
							<h5>크론 표현식</h5>
							<input type="text" class="form-control" name="cron" id="cron" placeholder="크론 표현식을 입력하세요." ng-model="data.cron">
						</div>
						<div class="form-group col-3">
							<h5>딜레이 표현식</h5>
							<input type="text" class="form-control" name="delay" id="delay" placeholder="딜레이 표현식을 입력하세요." ng-model="data.delay">
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-3">
							<h5>사용여부</h5>
							<div class="custom-control custom-radio custom-control-inline">
								<input name="inputUseYn" id="inputUseYn1" type="radio" ng-model="data.useYn" value="Y" class="custom-control-input">
								<label class="custom-control-label" for="inputUseYn1">사용</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input name="inputUseYn" id="inputUseYn2" type="radio" ng-model="data.useYn" value="N" class="custom-control-input">
								<label class="custom-control-label" for="inputUseYn2">사용안함</label>
							</div>
						</div>
					</div>
					<div class="align-self-end">
						<button class="btn" ng-click="init()">초기화</button>
						<button class="btn btn-danger" ng-click="del()" ng-if="flag == 'mod'">삭제</button>
						<button class="btn btn-primary" ng-click="clickSubmit()">{{flag == 'add' ? '저장' : '수정완료'}}</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
</html>