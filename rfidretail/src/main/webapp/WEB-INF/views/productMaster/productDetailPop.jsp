<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="modal-header">
		<h3 class="modal-title" id="modal-title">제품 상세</h3>
	</div>
	<div class="modal-body" id="modal-body">
			<form name="customForm">
				<div class="card card-body">
					<div class="row">
						<div class="form-group col-3">
							<h5>제품년도</h5>
							{{data.productYy}}
						</div>
						<div class="form-group col-3">
							<h5>제품시즌</h5>
							{{data.productSeason}}
						</div>
						<div class="form-group col-3">
							<h5>RFID 제품시즌</h5>
							{{data.productRfidYySeason}}
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-3">
							<h5>ERP키</h5>
							{{data.erpKey}}
						</div>
						<div class="form-group col-3">
							<h5>스타일명</h5>
							{{data.style}}
						</div>
						<div class="form-group col-3">
							<h5>나머지 제품코드</h5>
							{{data.annotherStyle}}
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-3">
						    <h5>컬러</h5>
							{{data.color}}
						</div>
						<div class="form-group col-3">
						    <h5>사이즈</h5>
							{{data.size}}
						</div>
					</div>
					<hr class="mb-4">
					<div class="row">
						<div class="form-group col-3">
						    <h5>상태</h5>
						    {{data.stat == 'C' ? "생성" : data.stac == 'U' ? "수정" : "삭제"}}
						</div>
						<div class="form-group col-3">
						    <h5>등록 날짜</h5>
						    {{data.regDate | date:'yyyy-MM-dd HH:mm:ss'}}
						</div>					
					</div>
				</div>
			</form>
		</div>
	<div class="modal-footer">
		<button class="btn" type="button" ng-click="cancel()">닫기</button>
    </div>
</html>