<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<div class="fade show" ng-show="ajaxFinish">
		<div class="login-background">
			<div class="container-fluid">
				<div class="form-signin">
					<img src="${pageContext.request.contextPath}/resources/img/ci/icon_spyder_logo.svg" width="150" height="50" alt="" class="d-none d-sm-block">
					<img src="${pageContext.request.contextPath}/resources/img/ci/icon_spyder_logo.png" width="150" height="50" alt="" class="d-sm-none">
					<hr class="mb-4">
					<h2 class="form-signin-heading">로그인</h2>
					<form role="form" ng-submit="login()" style="margin-bottom: 5px;">
					    <label for="j_username" class="sr-only">아이디</label>
					    <input type="text" class="form-control" ng-model="credentials.userId" placeholder="아이디" name="j_username" id="j_username" required autofocus>
					    <label for="j_password" class="sr-only">비밀번호</label>
					    <input type="password" class="form-control" ng-model="credentials.password" placeholder="비밀번호" name="j_password" id="j_password" required>
					    <!-- 
					    <div class="checkbox">
					    	<label>
					            <input type="checkbox" name="_spring_security_remember_me" id="_spring_security_remember_me"> 로그인 유지
					        </label>
					    </div>
					    -->
					    <button type="submit" class="btn btn-lg btn-primary btn-block">로그인</button>
				    </form>
				    <!-- 
				    <div class="text-center" style="margin-bottom: 5px;">
				    	<button type="button" class="btn btn-lg btn-success btn-block" ng-click="join();">회원가입</button>
				    </div>
				    -->
				    <div class="text-center row d-none d-sm-flex" style="margin-bottom: 5px;">
				    	<div class="col-6">
				    		<button type="button" class="btn btn-lg btn-secondary btn-block" ng-click="userIdFind();">아이디 찾기</button>
				    	</div>
				    	<div class="col-6">
				    		<button type="button" class="btn btn-lg btn-secondary btn-block" ng-click="passwordFind();">패스워드 찾기</button>
				    	</div>
				    </div>
				    <div class="text-center alert alert-danger" ng-show="error == 'Bad credentials'">
						패스워드를 확인하여 다시 시도해주세요.
					</div>
					<div class="text-center alert alert-danger" ng-show="error == '3000'">
						등록된 사용자가 아닙니다. 
					</div>
					<div class="text-center alert alert-danger" ng-show="error == '3001'">
						사용 중지된 사용자입니다. 
					</div>
					<div class="text-center alert alert-danger" ng-show="error == '3002'">
						관리자 승인 이후에 사용하실 수 있습니다. 
					</div>
			    </div>
			</div>
		</div>
	</div>
</html>