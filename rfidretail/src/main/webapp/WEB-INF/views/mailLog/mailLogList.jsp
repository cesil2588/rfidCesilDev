<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">메일 로그 목록</a></h2>
			<div class="card card-body" style="margin-bottom:5px;"> 
				<div class="form-row">
					<div class="form-group form-inline">
						날짜 선택 : 
						&nbsp;&nbsp;
						<select class="custom-select" style="width:120px;margin-right:5px;" ng-model="search.defaultDate" ng-change="changeSearchDate()">
							<option value="1">1달전</option>
							<option value="2">3달전</option>
							<option value="3">6달전</option>
							<option value="4">1년전</option>
						</select>
						&nbsp;&nbsp;
						<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="startDate" is-open="search.startDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:140px;margin-right:5px;"/>
		          		<span class="input-group-btn">
			            	<button type="button" class="btn btn-secondary" ng-click="startDateOpen()">
			              		달력 <i class="xi-calendar"></i>
			            	</button>
		          		</span>
		          		&nbsp;&nbsp;~&nbsp;&nbsp;
						<input type="text" class="form-control" uib-datepicker-popup="{{format}}" ng-model="endDate" is-open="search.endDateOpened" datepicker-options="dateOptions" ng-required="true" close-text="Close" style="width:140px;margin-right:5px;"/>
		          		<span class="input-group-btn" style="margin-right:5px;">
			            	<button type="button" class="btn btn-secondary" ng-click="endDateOpen()">
			              		달력 <i class="xi-calendar"></i>
			            	</button>
		          		</span>
					</div>
				</div>
				<div class="form-row">
		          	<div class="form-inline">
		          		&nbsp;&nbsp;
						타입 : 
						&nbsp;&nbsp;
						<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="search.type">
							<option value="all">전체</option>
							<option value="1">회원가입</option>
							<option value="2">바택발행</option>
							<option value="3">태그발행완료</option>
							<option value="4">태그재발행완료</option>
							<option value="5">태그재발행요청</option>
						</select>
						&nbsp;&nbsp;
						전송결과 : 
						&nbsp;&nbsp;
						<div class="custom-control custom-radio custom-control-inline">
							<input name="inputStat" id="inputStat1" type="radio" ng-model="search.stat" value="1" class="custom-control-input" ng-checked="true">
							<label class="custom-control-label" for="inputStat1">성공</label>
						</div>
						<div class="custom-control custom-radio custom-control-inline">
							<input name="inputStat" id="inputStat2" type="radio" ng-model="search.stat" value="2" class="custom-control-input">
							<label class="custom-control-label" for="inputStat2">실패</label>
						</div>
		          		&nbsp;&nbsp;
		          		<button ng-click="headSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>
		          	</div>
		        </div>
          	</div>
			<div class="btn-toolbar justify-content-between pb-1">
          		<div class="btn-group">
					<h5>Total({{total}}건)</h5>          		
          		</div>
          		<div class="input-group">
          			<select class="custom-select" style="width:120px;" ng-model="search.size" ng-change="changeSearchSize()">
						<option value="10" ng-selected="true">10건 보기</option>
						<option value="20">20건 보기</option>
						<option value="50">50건 보기</option>
						<option value="100">100건 보기</option>
					</select>   
          		</div>
          	</div>
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th ng-click="sort('mailTitle')">제목</th>
						<th ng-click="sort('type')">타입</th>
						<th ng-click="sort('mailFrom')">보낸 이메일</th>
						<th ng-click="sort('mailSend')">받는 이메일</th>
						<th ng-click="sort('stat')">전송 결과</th>
						<th ng-click="sort('sendDate')">등록 일시</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="mailLog in list" ng-click="detail(mailLog)" class="pointer" ng-if="list.length > 0">
						<td>{{mailLog.mailTitle}}</td>
						<td><span code-name pcode="10009" code="{{mailLog.type}}"></span></td>
						<td>{{mailLog.mailFrom}}</td>
						<td>{{mailLog.mailSend}}</td>
						<td>{{mailLog.stat == '1' ? '성공' : '실패'}}</td>
						<td>{{mailLog.sendDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
					</tr>
					<tr>
						<td colspan="6" class="text-center" ng-if="list.length == 0">
							No Data
						</td>
					</tr>
				</tbody>
			</table>
			<div class="search-margin">
				<div class="d-flex justify-content-center">
					<form class="form-inline" ng-submit="clickSearch()">
						<div class="form-group">
							<select class="custom-select" ng-model="search.option" style="width:100px;"> 
								<option ng-selected="true" value="mailFrom">보낸이</option>
								<option value="mailSend">받는이</option>
							</select>&nbsp;
							<input type="text" class="form-control" placeholder="검색" ng-model="search.text" style="width:300px;" id="inputSearch">&nbsp;
							<button type="submit" class="btn btn-default">검색</button>
						</div>
					</form>
				</div>
			</div>
			<div>
				<nav class="text-center">
					<ul class="pagination justify-content-center">
						<li class="page-item">
							<a href="" aria-label="Previous" ng-click="goPage(begin)" class="page-link"><span aria-hidden="true">&laquo;</span></a>
				    	</li>
				    	<li class="page-item">
							<a href="" aria-label="Previous" ng-click="goPage(current - 1)" class="page-link"><span aria-hidden="true">&lt;</span></a>
				    	</li>
				    	<li ng-repeat="pageNum in [begin, end] | makeRange" ng-class="{'active' : current == pageNum}" class="page-item"><a href="" ng-click="goPage(pageNum)" class="page-link">{{pageNum}}</a></li>
				    	<li class="page-item">
				    		<a href="" aria-label="Next" ng-click="goPage(current + 1)" class="page-link"><span aria-hidden="true">&gt;</span></a>
				    	</li>
				    	<li class="page-item">
				    		<a href="" aria-label="Next" ng-click="goPage(end)" class="page-link"><span aria-hidden="true">&raquo;</span></a>
				    	</li>
				  	</ul>
				</nav>
			</div>
		</div>
	</div>
</div>
</html>