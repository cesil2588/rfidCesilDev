<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<div class="fade show starter-template" ng-show="authenticated && ajaxFinish">
	<div class="container-fluid">
		<div class="body-contents" ng-class="sidebarFlag ? 'sidebar-true' : 'sidebar-false'">
			<h2><a href="" ng-click="reload()">코드 관리</a></h2>
			<form name="customForm">
				<div class="row">
					<div class="col">
						<div class="card">
							<div class="card-body" style="min-height:426px;">
								<div
							      data-angular-treeview="true"
							      data-tree-model="parentCodeList"
							      data-node-id="parentCodeSeq"
							      data-node-label="name"
							      data-node-children="codeInfo" style="overflow-y:scroll;min-height:340px;max-height:640px;">
							    </div>
							    <div ng-if="parentCodeList.length == 0" style="min-height:340px;">
							    	상위코드를 추가해주세요.
							    </div>
							    <div class="pull-right">
							    	<button ng-click="addParentForm()" class="btn btn-primary">상위 코드 추가</button>
									<button ng-click="addForm()" class="btn btn-primary">하위 코드 추가</button>
								</div>
						    </div>
					    </div>
					</div>
					<div class="col">
						<div class="card" ng-if="data">
							<div class="card-body">
								<div class="form-group">
									<label for="name">코드명</label>
									<input type="text" class="form-control" name="name" id="name" placeholder="코드명을 입력하세요." ng-model="data.name">
								</div>
								<div class="form-group">
									<label for="codeValue">코드값</label>
									<input type="text" class="form-control" id="codeValue" placeholder="코드값을 입력하세요." ng-model="data.codeValue">
								</div>
								<div class="form-group" ng-if="data.status == 'child'">
									<label for="erpCodeValue">ERP 코드값</label>
									<input type="text" class="form-control" id="erpCodeValue" placeholder="ERP코드값을 입력하세요." ng-model="data.erpCodeValue">
								</div>
								<div class="form-group" ng-if="data.status == 'child'">
									<label for="parentCodeSeq">부모코드명</label>
									<input type="text" class="form-control" id="parentCodeSeq" placeholder="부모코드명을 입력하세요." ng-model="data.parentCodeInfo.name" ng-readonly="true">
								</div>
								<div class="form-group" ng-if="data.status == 'child'">
									<label for="parentCodeValue">부모코드값</label>
									<input type="text" class="form-control" id="parentCodeValue" placeholder="부모코드값을 입력하세요." ng-model="data.parentCodeInfo.codeValue" ng-readonly="true">
								</div>
								
								<div class="form-group">
									<label for="sort">정렬순서</label>
									<input type="number" class="form-control" id="sort" placeholder="정렬순서를 입력하세요." ng-model="data.sort">
								</div>
								<div class="form-group">
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
								<div class="pull-right">
									<button ng-click="add()" class="btn btn-primary">완료</button>
									<button ng-click="del()" ng-if="deleteFlag" class="btn btn-danger">삭제</button>
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