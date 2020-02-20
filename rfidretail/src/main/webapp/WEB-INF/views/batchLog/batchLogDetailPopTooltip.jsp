<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
	<h5>에러 로그 메시지</h5>   
	<div class="form-group">
		<textarea class="form-control" rows="16" readonly>{{step.errorMessage}}</textarea>
	</div>
</html>