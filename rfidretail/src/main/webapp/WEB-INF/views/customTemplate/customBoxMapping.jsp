<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">박스 맵핑</h3>
	</div>
	<div class="modal-body" id="modal-body">
		<h5>Total({{total}}건)</h5>
		<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th>
							<div class="custom-control custom-checkbox">
								<input class="custom-control-input" type="checkbox" ng-model="allCheckFlag" ng-click="allCheck()" id="allCheck"/>
								<label class="custom-control-label" for="allCheck">체크</label>
							</div>
						</th>
						<th ng-click="sort('barcode')">바코드</th>
						<th ng-click="sort('type')">내용</th>
						<th ng-click="sort('createDate')">날짜</th>
						<th ng-click="sort('boxNum')">박스번호</th>
						<th ng-click="sort('startCompanyInfo.name')">출발지</th>
						<th ng-click="sort('endCompanyInfo.name')">도착지</th>
						<th ng-click="sort('stat')">상태</th>
						<th ng-click="sort('regUserInfo.userId')">등록자</th>
						<th ng-click="sort('regDate')">등록일</th>
						<th ng-click="sort('updUserInfo.userId')">수정자</th>
						<th ng-click="sort('updDate')">수정일</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="box in list" class="pointer" ng-if="list.length > 0">
						<td>
							<div class="custom-control custom-checkbox">
								<input type="checkbox" class="custom-control-input" id="customCheck{{$index}}" ng-model="box.check">
								<label class="custom-control-label" for="customCheck{{$index}}"></label>
							</div>
						</td>
						<td ng-click="detail(box)">{{box.barcode}}</td>
						<td><span code-name pcode="10001" code="{{box.type}}"></span></td>
						<td>{{box.createDate}}</td>
						<td>{{box.boxNum}}</td>
						<td>{{box.startCompanyInfo.name}}</td>
						<td>{{box.endCompanyInfo.name}}</td>
						<td><span code-name pcode="10010" code="{{box.stat}}"></span></td>
						<td>{{box.regUserInfo.userId ? box.regUserInfo.userId : '-'}}</td>
						<td>
							<span ng-if="box.regDate">
								{{box.regDate | date:'yyyy-MM-dd'}}<br />
								{{box.regDate | date:'HH:mm:ss'}}
							</span>
							<span ng-if="!box.regDate">
								-
							</span>
						</td>
						<td>{{box.updUserInfo.userId ? box.updUserInfo.userId : '-'}}</td>
						<td>
							<span ng-if="box.updDate">
								{{box.updDate | date:'yyyy-MM-dd'}}<br />
								{{box.updDate | date:'HH:mm:ss'}}
							</span>
							<span ng-if="!box.updDate">
								-
							</span>
						</td>
					</tr>
					<tr>
						<td colspan="12" class="text-center" ng-if="list.length == 0">
							No Data
						</td>
					</tr>
				</tbody>
			</table>
			<div class="search-margin">
				<div class="d-flex justify-content-center">
					<form class="form-inline" ng-submit="clickSearch()">
						<div class="form-group">
							<select class="custom-select" ng-model="search.option" style="width:120px;"> 
								<option value="boxNum">박스번호</option>
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
	<div class="modal-footer">
		<select class="custom-select" style="width:140px;margin-right:5px;" ng-model="flag">
			<option value="OP-R">생산처입고</option>
			<option value="OP-R2">해외배송입고</option>
			<option value="10-R">판매반품</option>
		</select>
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button class="btn btn-primary" type="button" ng-click="ok()">확인</button>
    </div>
</html>