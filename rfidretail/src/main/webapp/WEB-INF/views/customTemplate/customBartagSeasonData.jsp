<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp">
<a class="dropdown-item pointer">
      <span ng-bind-html="match.model.explanatory | uibTypeaheadHighlight:query"></span>
</a>
</html>