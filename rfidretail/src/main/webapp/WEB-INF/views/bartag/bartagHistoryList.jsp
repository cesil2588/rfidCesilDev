<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">태그 이력 조회</a></h2>
			<div class="card card-body" style="margin-bottom:5px;"> 
				<div class="form-row">
					<div class="form-group form-inline">
		          		&nbsp;&nbsp;
		          		업체명 : 
						&nbsp;&nbsp;
						<input type="text" ng-model="search.companyInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue, type:'3', useYn:'Y'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole == 'production'" typeahead-on-select="selectCompany($item)">
						&nbsp;&nbsp;
						스타일 :
						&nbsp;&nbsp;
						<input type="text" ng-model="search.style" placeholder="스타일 입력" uib-typeahead="style as style.data for style in styleList | filter:{data:$viewValue}" typeahead-template-url="customTemplate/customBartagData" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" typeahead-on-select="selectItem($item, 'style')">
						&nbsp;&nbsp;
						태그 : 
						&nbsp;&nbsp;
						<input type="text" name="rfidTag" ng-model="search.rfidTag" class="form-control" placeholder="태그 입력" width="500">
						&nbsp;&nbsp;
						<button ng-click="headSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>
		          	</div>
		       </div> 
		       <br><br>
			<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
				<thead>
					<tr class="pointer">
						<th>작업일</th>
						<th>등록자</th>
						<th>상태</th>
					</tr>
				</thead>
				<tbody>
					<tr ng-repeat="obj in list" class="pointer" ng-if="list.length > 0">
						<td>{{obj.regDate}}</td>
						<td>{{obj.regUser}}</td>
						<td>{{obj.work}}</td>
					</tr>
					<tr>
						<td colspan="3" class="text-center" ng-if="list.length == 0">
							No Data
						</td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
</html>