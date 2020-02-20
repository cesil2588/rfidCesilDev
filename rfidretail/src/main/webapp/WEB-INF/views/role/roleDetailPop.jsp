<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<head>
	<style>
		   #mapping_menu_list{margin-bottom: 1em;
		    padding: 12px 8px;
		    padding-bottom: 20px !ie7;
		    width: auto;
		    width: 650px !ie7;
		    height: 200px;
		    max-height: 600px;
		    overflow: auto;
		    font-family: Consolas,Menlo,Monaco,Lucida Console,Liberation Mono,DejaVu Sans Mono,Bitstream Vera Sans Mono,Courier New,monospace,sans-serif;
		    font-size: 13px;
		    border-radius: 3px;
		    }
		    
		    h6:hover {
		    	font-weight: bolder;
		    }
		    
		    #test:hover {
		    	background-color: #EAEAEA;
		    }
	</style>
</head>
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">권한 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<form name="customForm">
				<div class="card card-body">
					
					<div class="row">
						<div class="form-group col-6">
							<h5>권한명</h5>							
								<select class="form-control col-9" ng-model="roleName" ng-change="optionChange()">
									<option ng-repeat="roleObj in roleList">{{roleObj.roleName}}</option>
								</select>
						</div>
						<div class="form-group col-6">
							<h5>권한 타입</h5>
							<input type="text" class="form-control col-9" ng-model="originalObj.role" readonly="readonly">
						</div>
					</div>
					
					<hr class="mb-4">
					
					<div class="row">
						<div class="form-group col-8">
							<h5>메뉴 검색</h5>
							<input type="text" class="form-control col-6" name="parentMenu" id="parentMenu" autocomplete="off"
								style="display: inline; float: left; margin-right: 10px;"
								ng-model="searchMenu" placeholder="상위 메뉴 검색" 
								uib-typeahead="parent.menuName as parent.menuName for parent in parentList | filter:$viewValue | limitTo:8"
								typeahead-show-hint="true" typeahead-min-length="0" 
							>
							<button class="btn btn-primary col-1" ng-click="addMenu()">추가</button>
						</div>
						<div class="form-group col-6"> ▶ {{checkMessage}}</div>
					</div>
					
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-6">
						    <h5>맵핑 상위 메뉴</h5>
						    <div id="mapping_menu_list" >									
								<div class="form-group col-11">
									<ul class="list-group" dnd-list="originalObj" dnd-drop="onDrop(index,item)">
									    <li class="list-group-item col-12" ng-repeat="menuMapping in originalObj.menuMapping | filter:itemSearch" id = "test"style="margin:0 auto;"
									    dnd-draggable="menuMapping"
								        dnd-selected="onSelected(menuMapping.parentMenu)" ng-if = "menuMapping.parentMenu.useYn = 'Y'">
										     <i class = "{{menuMapping.parentMenu.iconClass}}"></i> {{menuMapping.parentMenu.menuName}}
  									     <button class="btn" ng-click="deleteMenu($index)" style="float:right;padding:3px;font-size: 10px">삭제</button>
									    </li>
									</ul>
								</div>
						    </div>
						</div>
						<div class="form-group col-6">
						    <h5>맵핑 하위 메뉴</h5>
						    <div id="mapping_menu_list">
								<div class="form-group col-11" ng-if="childMenu != null && childMenu.length != 0">
									<ul class="list-group" >
									    <li class="list-group-item col-12" ng-repeat="child in childMenu | filter:itemSearch"  style="margin:0 auto">
									        <i class = "{{child.iconClass}}"></i> {{child.menuName}}
									    </li>
									    
									</ul>
								</div>
							    <div class="form-group col-11" ng-if="childMenu == null || childMenu.length == 0">
									<ul class="list-group">
									    <li  class="list-group-item col-12"  style="margin:0 auto">
									        	하위 메뉴가 없습니다.
									    </li>
									    
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="row">
					<div class="form-group col-3">
						<h5>사용 여부</h5>
						<div class="custom-control custom-radio custom-control-inline">
							<input name="inputUseYn" id="inputUseYn1" type="radio" ng-model="originalObj.useYn" value="Y" class="custom-control-input">
							<label class="custom-control-label" for="inputUseYn1">사용</label>
						</div>
						<div class="custom-control custom-radio custom-control-inline">
							<input name="inputUseYn" id="inputUseYn2" type="radio" ng-model="originalObj.useYn" value="N" class="custom-control-input">
							<label class="custom-control-label" for="inputUseYn2">미사용</label>
						</div>
					</div>
					</div>
					<hr>
					<div class="row">
						<div class="form-group col-3">
						    <h5>등록자</h5>
						    {{originalObj.regUserInfo.userId}}
						</div>
						<div class="form-group col-3">
						    <h5>등록 일시</h5>
						    {{originalObj.regDate | date:'yyyy-MM-dd HH:mm:ss'}}
						</div>
						<div class="form-group col-3">
						    <h5>수정자</h5>
						    {{originalObj.updUserInfo.userId}}
						</div>		
						<div class="form-group col-3">
						    <h5>등록 일시</h5>
						    {{originalObj.updDate | date:'yyyy-MM-dd HH:mm:ss'}}
						</div>
					</div>		
				</div>
			</form>
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button class="btn btn-primary col-1" ng-if="originalObj.c == 'update'" ng-click="updateRole()">수정 완료</button>
		<button class="btn btn-primary col-1" ng-if="originalObj.c == 'add'" ng-click="addRole()">권한 추가</button>
    </div>
</html>