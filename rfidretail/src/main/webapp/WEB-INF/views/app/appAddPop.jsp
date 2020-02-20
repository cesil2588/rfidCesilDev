<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">어플리케이션 추가</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<form name="customForm">
				<div class="card card-body">
					<div class="form-group">
					    <h5>어플리케이션 업로드</h5>
						<span class="btn btn-primary"><i class="xi-upload" aria-hidden="true"></i><input style="width: 140px;" type="file" class="input" id="myfile" file-model="uploadedFile" accept=".apk, .msi">파일 업로드</input></span>
		    			
		    			<p ng-if="uploadResult" style="margin-top: 5px;">
					    	<a href="${pageContext.request.contextPath}/storage/{{uploadResult.fileName}}">{{uploadResult.fileName}}</a>
					    	&nbsp;&nbsp;
					      	<button class="btn btn-xs" ng-click="delFile()">
								 <i class="xi-close" aria-hidden="true"></i>
							</button>
						</p>
				    </div>
				    <hr class="mb-4">
					<div class="row">
						<div class="form-group col-3">
							<h5>버전</h5>
							<input type="text" class="form-control" id="version" placeholder="버전을 입력하세요." ng-model="data.version">
						</div>
						<div class="form-group col-3">
						    <h5>타입</h5>
						    <div class="custom-control custom-radio custom-control-inline">
								<input name="inputType1" id="inputType1" type="radio" ng-model="data.type" value="1" class="custom-control-input">
								<label class="custom-control-label" for="inputType1">생산</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input name="inputType2" id="inputType2" type="radio" ng-model="data.type" value="2" class="custom-control-input">
								<label class="custom-control-label" for="inputType2">물류</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input name="inputType3" id="inputType3" type="radio" ng-model="data.type" value="3" class="custom-control-input">
								<label class="custom-control-label" for="inputType3">매장</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input name="inputType4" id="inputType4" type="radio" ng-model="data.type" value="4" class="custom-control-input">
								<label class="custom-control-label" for="inputType4">컨베이어</label>
							</div>
						</div>
						<div class="form-group col-3">
						    <h5>대표 App 여부</h5>
						    <div class="custom-control custom-radio custom-control-inline">
								<input name="inputCheckYn" id="inputCheckYn1" type="radio" ng-model="data.representYn" value="Y" class="custom-control-input">
								<label class="custom-control-label" for="inputCheckYn1">Y</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input name="inputCheckYn" id="inputCheckYn2" type="radio" ng-model="data.representYn" value="N" class="custom-control-input">
								<label class="custom-control-label" for="inputCheckYn2">N</label>
							</div>
						</div>
						<div class="form-group col-3">
						    <h5>사용 여부</h5>
						    <div class="custom-control custom-radio custom-control-inline">
								<input name="inputUseYn" id="inputUseYn1" type="radio" ng-model="data.useYn" value="Y" class="custom-control-input">
								<label class="custom-control-label" for="inputUseYn1">사용</label>
							</div>
							<div class="custom-control custom-radio custom-control-inline">
								<input name="inputUseYn" id="inputUseYn1" type="radio" ng-model="data.useYn" value="N" class="custom-control-input">
								<label class="custom-control-label" for="inputUseYn1">사용 안함</label>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
		<button type="button" ng-click="add()" class="btn btn-primary">저장</button>
    </div>
</html>