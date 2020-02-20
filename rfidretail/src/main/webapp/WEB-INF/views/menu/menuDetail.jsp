<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated&&ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">메뉴 관리</a></h2>
			<form name="customForm">
				<div class="row">
					<div class="col">
						<div class="card">
							<div class="card-body" style="min-height:426px;">
								<div
							      data-angular-treeview="true"
							      data-tree-model="parentMenuList"
							      data-node-id="parentMenuSeq"
							      data-node-label="menuName"
							      data-node-children="childMenu" data-ng-click="test($event,this)" style="overflow-y:scroll;min-height:340px;max-height:640px;">
							    </div>
							    <div ng-if="parentMenuList.length == 0" style="min-height:340px;">
							    	상위코드를 추가해주세요.
							    </div>
							    <div class="pull-right">
							    	<button ng-click="addParentMenu()" class="btn btn-primary">상위 메뉴 추가</button>
									<button ng-click="addChildMenu()" class="btn btn-primary">하위 메뉴 추가</button>
								</div>
						    </div>
					    </div>
					</div>
					<div class="col">
						<div class="card" ng-if="data">
							<div class="card-body" style="padding:3em">
								<!-- child 메뉴의 경우 -->
								<div class="row" ng-if="data.c == 'child'">
									<div class="form-group col-6">
										<label for="data.parentMenu.menuName" ng-readonly="data.parentMenu.menuName">상위 메뉴명</label>
										<input type="text" class="form-control" name="menuName" id="menuName" ng-model="data.parentMenu.menuName" ng-readonly="true">
									</div>
									<div class="form-group col-6">
										<label for="data.parentMenu.menuCode">상위 메뉴코드</label>
										<input type="text" class="form-control" id="menuCode" ng-model="data.parentMenu.menuCode" ng-readonly="true">
									</div>
								</div>
								<div class="row">
									<div class="form-group col-6"> 
										<label for="menuName">메뉴명</label>
										<input type="text" class="form-control" name="menuName" id="menuName" ng-model="data.menuName" ng-readonly="updateCheck">
									</div>
									<div class="form-group col-6">
										<label for="menuCode">메뉴코드</label>
										<input type="text" class="form-control" id="menuCode" ng-model="data.menuCode" ng-readonly="updateCheck">
									</div>
								</div>
								<div class="row">
									<div class="form-group col-12">
										<label for="url">URL</label>
										<input type="text" class="form-control" id="url" ng-model="data.url" style="width:100%;">
									</div>
								</div>
								<div class="row">
									<div class="form-group col-6">
										<label for="type">타입</label>
									
										<select class="form-control" ng-model="data.type" id="type">
											<option ng-repeat="typeList in typeList" value="{{typeList}}">{{typeList}}</option>
										</select>
									</div>
								</div>
								<!-- parentMenu 형태가 page일 경우 -->
								<div class="row"  ng-if="data.type == 'page'">
									<div class="form-group col-6">
										<label for="sessionType">세션 타입</label>
										<select class="form-control" ng-model="data.sessionType" id="sessionType">
											<option ng-repeat="sessionTypeList in sessionTypeList" value="{{sessionTypeList}}">{{sessionTypeList}}</option>
										</select>
									</div>
								</div>
								<!-- parentMenu 형태가 popup일 경우 -->
								<div class="row"  ng-if="data.type == 'popup'">
									<div class="form-group col-6">
										<label for="size">사이즈</label>
										<select class="form-control" ng-model="data.size" id="size">
											<option ng-repeat="sizeList in sizeList" value="{{sizeList}}">{{sizeList}}</option>
										</select>
									</div>
									
									<div class="form-group col-6">
										<label for="mappingController">맵핑 컨트롤러</label>
										<input type="text" class="form-control" id="mappingController" ng-model="data.mappingController">
									</div>
								</div>
								<!-- parentMenu 형태가 collapse 경우 -->
								<div class="row"  ng-if="data.type == 'collapse'">
								</div>
								<div class="row">
									<div class="form-group col-6">
										<label for="location">위치</label>
										<select class="form-control" ng-model="data.location" id="location">
											<option ng-repeat="locationList in locationList" value="{{locationList}}">{{locationList}}</option>
										</select>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-6">
										<label for="iconClass">icon 클래스</label>
										<input type="text" class="form-control" name="iconClass" id="iconClass" ng-model="data.iconClass">
									</div>
									<div class="form-group col-6">
										<label for="changeIconClass">변경시 icon 클래스</label>
										<input type="text" class="form-control" id="changeIconClass" ng-model="data.changeIconClass">
									</div>
								</div>
								<div class="row">
									<div class="form-group col-6">
									<label for="useYn">사용여부</label><br/>
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
								
									<div class="pull-right" ng-if="method == 'PUT'">
										<button ng-click="update()" class="btn btn-primary">수정 완료</button>
									</div>
									<div class="pull-right" ng-if="method == 'POST'">
										<button ng-click="add()" class="btn btn-primary">저장</button>
									</div>
								</div>
							</div>
						</div>
					</div>					
				</div>
			</form>
			
		</div>
	</div>
</div>
</html>