<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="myApp" ng-controller="indexController">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>Spyder RFID</title>

<script src="${pageContext.request.contextPath}/resources/js/lib/jquery-3.3.1.slim.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/chart.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/angular.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/angular-route.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/angular-animate.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/angular-cookies.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/angular-sanitize.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/angular-chart.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/angular-barcode.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/angular-file-saver.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/angular-file-saver.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/angular-drag-and-drop-lists.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/sockjs.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/stomp.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/angular-tree-view.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/ui-bootstrap-tpls-3.0.3.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/html2canvas.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/pdfmake.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/vfs_fonts.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/FileSaver.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/Blob.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/ng-device-detector.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/re-tree.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/custom/custom.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/config/appInitConfig.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/config/routeProvider.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/factory/verificationFactory.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/factory/excelFactory.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/factory/httpFactory.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/directive/appDirective.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/filter/appFilter.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/mainController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/userController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/companyController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/codeController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/mailController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/bartagController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/bartagOrderController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/productionController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/productionReleaseController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/productionScheduleController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/batchJobController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/productMasterController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/batchLogController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/mailLogController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/boxController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/popupController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/distributionController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/distributionStorageScheduleController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/distributionStoreReleaseController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/distributionStoreReleaseScheduleController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/reissueTagController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/appController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/storeStorageScheduleController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/storeController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/storeReturnController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/storeStorageScheduleController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/reissueTagController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/storeMoveController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/inventoryScheduleController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/errorLogController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/requestLogController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/batchTriggerController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/roleController.js?v=${version}"></script>
<script src="${pageContext.request.contextPath}/resources/js/controller/menuController.js?v=${version}"></script>

<script src="${pageContext.request.contextPath}/resources/js/lib/angular-ui.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/lib/angular-filter.min.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/sb-admin.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/xeicon.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/angular-tree.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/custom.css?v=${version}">


<style type="text/css">
[ng\:cloak], [ng-cloak], .ng-cloak {
	display: none !important;
}

</style>
<base href="/">
</head>
<body ng-cloak class="ng-cloak">
	<div>
		<!-- Navigation -->
		<nav class="navbar navbar-dark bg-inverse navbar-fixed-top d-none d-sm-flex" style="position: fixed;width: 100%; z-index:1001;">
           <!-- Brand and toggle get grouped for better mobile display -->
           <div class="navbar-header">
               <a class="navbar-brand" href="/"  >Spyder RFID</a>
           </div>
           
           <!-- Top Menu Items -->
		<ul class="navbar-nav flex-row ml-md-auto d-md-flex navbar-right" ng-show="authenticated">
				<li class="nav-item pr-3">
					<a class="nav-link" href="" ng-click="goTopMenu('searchPop')"><i class="xi-search" aria-hidden="true"></i> 검색</a>
				</li>
           		<li class="dropdown nav-item pr-3">
                    <a href="" class="nav-link" data-toggle="dropdown"><i class="xi-bell-o"></i> 알림 <span class="badge badge-light">{{userNotiList.length}}</span></a>
                    <ul class="dropdown-menu" style="position:absolute;left:-140%;">
                        <li class="dropdown-item dropdown-linechange" ng-repeat="noti in userNotiList | orderBy : '-regDate' | limitTo : '10'" ng-if="userNotiList.length > 0">
                            <a href="" ng-click="checkNoti($index, noti)"><i ng-class="{'xi-info-o' : noti.checkYn == 'N', 'xi-info' : noti.checkYn == 'Y' }"></i> {{noti.notice}} <small relative-time="noti.regDate"></small></a>
                        </li>
                        <li class="dropdown-item text-center" ng-if="userNotiList.length > 10">
                        	<a href="" ng-click="goTopMenu('userNotiList')"><i class="xi-search"></i> 더 보기</a>
                        </li>
                        <li class="dropdown-item" ng-if="userNotiList.length == 0">
                        	<a href=""><i class="xi-info-o"></i> 알림이 없습니다.</a>
                        </li>
                    </ul>
                </li>
                <li class="nav-item pr-3">
					<a class="nav-link" href="" ng-click="goTopMenu('batchTriggerList')"><i class="xi-wrench" aria-hidden="true"></i> 배치 예약</a>
				</li>
           		<li class="nav-item pr-3">
					<a class="nav-link" href="" ng-click="goTopMenu('userDetail')"><i class="xi-user-o" aria-hidden="true"></i> 사용자 정보</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="#index" ng-click="logout()"><i class="xi-log-out" aria-hidden="true"></i> 로그아웃</a>
				</li>
			</ul>
		
		<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->     
		<div class="navbar-toggleable-sm navbar-ex1-collapse" ng-show="authenticated" >
			<ul class="nav navbar-nav side-nav list-group" id="sidebar" >
				<li class="list-group-item" >
			        <a href="#/main/home"><i class="xi-dashboard-o" aria-hidden="true"></i> 메인 </a>
			    </li>
 				<li class="list-group-item" ng-repeat="sideNav in parentMenuList | orderBy:'parentMenu.rank'" ng-if="sideNav.parentMenu.url == ''&& sideNav.parentMenu.location == 'left' && sideNav.parentMenu.useYn == 'Y'">
 					<a href="" data-toggle="collapse" data-target="#menu{{$index}}" aria-expanded="false" aria-controls="menu{{$index}}" ng-click="collapse($index)"><i class="{{collapseVar[$index]}}" ng-init="collapseVar[$index] = 'xi-angle-down'"></i> {{sideNav.parentMenu.menuName}} </a>
					<ul id="menu{{$index}}" class="list-group collapse" data-parent="#sidebar">
					     <li class="list-group-item" ng-repeat="subChildNav in sideNav.parentMenu.childMenu | orderBy:'rank'" ng-if="subChildNav.useYn == 'Y'">
					        <a href="" ng-click="goMenu(subChildNav)"><i class = "{{subChildNav.iconClass}}"></i> {{subChildNav.menuName}}</a>
					    </li>
					</ul>
				</li>
                <!-- 일반 -->          
                <li class="list-group-item" ng-repeat="sideNav in parentMenuList | orderBy:'parentMenu.rank'" ng-if="sideNav.parentMenu.url != ''&& sideNav.parentMenu.location == 'left' && sideNav.parentMenu.useYn == 'Y'" >
			        <a href="" ng-click="goMenu(sideNav.parentMenu)"><i class = "{{sideNav.parentMenu.iconClass}}" aria-hidden="true"></i> {{sideNav.parentMenu.menuName}} </a>
			    </li>
			</ul>
		</div>
	
        <!-- /.navbar-collapse -->
        </nav>
		<div id="page-wrapper" ng-class="{'login-not-authenticated' : !authenticated}">
			<div ng-view></div>
			<div class="loading-spiner-holder" data-loading >
				<div class="loading-spiner loading">
					<i class="xi-spinner-5 xi-spin xi-5x loading-spin" aria-hidden="true"></i>
				</div>
				<div class="modal-backdrop fade" style="z-index:1080;">
				</div>
			</div>
			
			<!-- Modal Confirm Start -->
			<div class="modal fade bd-example-modal-lg" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index:2000;">
			  	<div class="modal-dialog">
			    	<div class="modal-content">
			      		<div class="modal-header">
			      			<h4 class="modal-title" id="myModalLabel">Spyder</h4>
			        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
			      		</div>
			      		<div class="modal-body">
			      		</div>
			      		<div class="modal-footer">
			        		<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
			        		<button type="button" class="btn btn-primary" id="okBtn">확인</button>
			      		</div>
			    	</div>
			  	</div>
			</div>
			<!-- Modal Confirm End -->
			<!-- Modal Common Start -->
			<div class="modal fade bd-example-modal-lg" id="commonModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="z-index:2000;">
			  	<div class="modal-dialog">
			    	<div class="modal-content">
			      		<div class="modal-header">
			      			<h4 class="modal-title" id="myModalLabel">Spyder</h4>
			        		<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
			      		</div>
			      		<div class="modal-body" id="modalId">
			      		</div>
			      		<div class="modal-footer">
			        		<button type="button" class="btn btn-primary" id="okBtn">확인</button>
			      		</div>
			    	</div>
			  	</div>
			</div>
			<!-- Modal Common End -->
		</div>
	</div>
</body>
</html>