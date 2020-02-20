<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">메일 로그 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<form name="customForm">
				<div class="card card-body">
					<div class="row">
						<div class="form-group col-3">
							<h5>보낸 이메일</h5>
							{{data.mailFrom}}
						</div>
						<div class="form-group col-3">
							<h5>받는 이메일</h5>
							{{data.mailSend}}
						</div>
						<div class="form-group col-3">
							<h5>타입</h5>
							<span code-name-fix pcode="10009" code="{{data.type}}"></span>
						</div>
						<div class="form-group col-3">
							<h5>등록일시</h5>
							{{data.sendDate | date:'yyyy-MM-dd HH:mm:ss'}}
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-3">
							<h5>제목</h5>
							{{data.mailTitle}}
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-12">
							<h5>내용</h5>
							{{data.mailContents}}
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-3">
							<h5>전송 결과</h5>
							{{data.stat == '1' ? '성공' : '실패'}}
						</div>
					</div>
					<hr class="mb-4" ng-if="data.stat != '1'">
					<div class="row" ng-if="data.stat != '1'">
						<div class="form-group col-12">
							<h5>에러 메시지</h5>
							{{data.errorMessage}}
						</div>
					</div>
				</div>
			</form>
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
    </div>
</html>