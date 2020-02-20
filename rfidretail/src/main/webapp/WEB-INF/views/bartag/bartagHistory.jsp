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
						<!-- <input type="text" ng-model="search.companyInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue, type:'3', useYn:'Y'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole == 'production'" typeahead-on-select="selectCompany($item)"> -->
						<input type="text" ng-model="search.companyInfo" placeholder="업체명 입력" uib-typeahead="company as company.name for company in companyList | filter:{name:$viewValue, type:'3', useYn:'Y'}" class="form-control" typeahead-show-hint="true" typeahead-min-length="0" ng-readonly="userRole == 'production'" typeahead-on-select="selectItem($item, 'company')">
						&nbsp;&nbsp;
						스타일 :
						&nbsp;&nbsp;
						<select class="custom-select" style="width:200px;margin-right:5px;" ng-model="search.style">
						<option ng-repeat="select in styleList | filter:customFilter('style')" value="{{select.data}}">{{select.data}}</option>
						</select>
						&nbsp;&nbsp;
						컬러 :
						&nbsp;&nbsp;
						<select class="custom-select" style="width:200px;margin-right:5px;" ng-model="search.color">
						<option ng-repeat="select in styleList | filter:customFilter('color')" value="{{select.data}}">{{select.data}}</option>
						</select>
						&nbsp;&nbsp;
						사이즈 :
						&nbsp;&nbsp;
						<select class="custom-select" style="width:200px;margin-right:5px;" ng-model="search.size" ng-change="selectSku()">
						<option ng-repeat="select in styleList | filter:customFilter('size')" value="{{select.data}}">{{select.data}}</option>
						</select>
						&nbsp;&nbsp;
						태그 : 
						&nbsp;&nbsp;
						<select class="custom-select" style="width:300px;margin-right:5px;" ng-model="search.rfidTagPre" ng-change="fillRfidTag()">
						<option ng-repeat="list in tagList.list" value="{{list.rfidTag}}">{{list.rfidTag}}</option>
						</select>
					</div>
					<div class="form-group form-inline">
						&nbsp;&nbsp;
						태그 직접 입력: 
						&nbsp;&nbsp;
						<input type="text" style="width:300px;margin-right:5px;" ng-model="search.rfidTag" class="form-control"  placeholder="태그 입력(32자리)" ng-click="searchReset()">
						&nbsp;&nbsp;
						<button ng-click="headSearch()" class="btn btn-primary">&nbsp;&nbsp;조회&nbsp;&nbsp;</button>
		          	</div>
		       </div> 
		   </div>
		       <br><br>
		    <div>
				<table class="table table-striped table-bordered table-hover text-center custom-align-middle">
					<thead>
						<tr class="pointer">
							<th>스타일</th>
							<th>컬러</th>
							<th>사이즈</th>
							<th>작업일</th>
							<th>등록자</th>
							<th>상태</th>
						</tr>
					</thead>
					<tbody>
						<tr ng-repeat="obj in list" class="pointer" ng-if="list.length > 0">
							<td>{{obj.style}}</td>
							<td>{{obj.color}}</td>
							<td>{{obj.size}}</td>
							<td>{{obj.regDate}}</td>
							<td>{{obj.regUser}}</td>
							<td>{{obj.work}}</td>
						</tr>
						<tr>
							<td colspan="6" class="text-center" ng-if="list.length == 0 || list==null">
								No Data
							</td>
						</tr>
					</tbody>
				</table>
			</div>
	  </div>
</div>
</html>