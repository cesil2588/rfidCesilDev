app.run(function($rootScope, $http, $route, $window){
	
	$rootScope.getUserNotiList = function(){
		
		$http.get('/userNoti/pushList').success(
			function(data, status, headers, config) {
			
			$rootScope.userNotiList = angular.fromJson(data);
		})
		.error(function(data, status, headers, config) {
			console.log(status);
		});
	};
	
	$rootScope.reload = function(){
		$route.reload();
		$window.sessionStorage.removeItem("current");
	};
	
	$rootScope.$on("$routeChangeError", function (event, current, previous, eventObj) {
        if (eventObj.authenticated === false) {
            $location.path("/");
        }
    });
	
	$rootScope.getCode = function(){
		$http.get('/code/findAll').success(
			function(data) {
				$rootScope.parentCodeList = angular.fromJson(data);
							
				angular.forEach($rootScope.parentCodeList, function(value, key) {
					if($rootScope.parentCodeList[key].codeInfo.length > 1){
						angular.forEach($rootScope.parentCodeList[key].codeInfo, function(v, k) {
							$rootScope.parentCodeList[key].codeInfo[k].parentCodeInfo = $rootScope.parentCodeList[key].codeInfo[0].parentCodeInfo;
						});
					}
				});
			}
		);
	};
	
	$rootScope.testMode = true;
	//$rootScope.testMode = false;
	$rootScope.noticeMode = false;
	
});

app.controller('indexController', ['$scope', '$http', '$location', '$rootScope', '$window', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $uibModal) {
	
	$scope.logout = function() {
		
		$http({
			method: 'POST', 
			url: '/member/logout',
			data: {},
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
		}).success(function(data, status, headers, config) {
			
			if(status == 200) {
				$rootScope.authenticated = false;
				$location.url("/");
			} else {
				modalOpen($uibModal,  "에러발생");
			}
			
		}).error(function(data, status, headers, config) {
		    $rootScope.authenticated = false;
		    $location.url("/");
		});
	};
	
	
	$scope.client;
	
	$scope.disconnection = function(){
		
		if ($scope.client != null) {
			
			$scope.client.disconnect();
			$scope.resData = {};
	    }
	};
	
	
	$scope.connection = function(){
		
		$scope.disconnection();
		
//		var protocol = $location.protocol()
		
//		var prefixUrl = protocol == "http" ? "ws://" + location.host : "wss://" + location.host;
		
//		var socket = new SockJS(prefixUrl + '/hello');
		
		var socket = new SockJS('/hello');
		$scope.client = Stomp.over(socket);
		$scope.client.debug = null;
		$scope.client.connect({}, function(frame) {
			$scope.client.subscribe('/user/queue/notiReceive', function(data){
				
				var noti = angular.fromJson(data.body);
				
				$rootScope.userNotiList.push(noti);
				
		    	$scope.$apply();
		    	
		    });
		});
	};
	
	$scope.$watch(function(){
		
		return $rootScope.authenticated;
		
		}, function() {
		
		if($rootScope.authenticated != undefined){
			
			if($rootScope.authenticated){
				if($location.url() != "/main/home"){
//					$rootScope.getPhysicalReaderList();
				}
				
				$rootScope.getCode();
				$rootScope.getUserNotiList();
				$scope.connection();
			} else {
				$scope.disconnection();
			}
		} 
		
	}, true);
	
	$scope.checkNoti = function(index, obj){
		
		$http({
			method: 'PUT', 
			url: '/userNoti/check',
			data: obj,
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
		}).success(function(data, status, headers, config) {
			
			if(status == 200) {
				$rootScope.userNotiList.splice(index, 1);
			} else {
				modalOpen($uibModal,  "에러발생");
			}
			
		}).error(function(data, status, headers, config) {
			modalOpen($uibModal,  "에러발생");
		});
	};
	
	$scope.goMenu = function(obj){
		
		switch(obj.type){
			case "popup" :
				
				switch(url) {
				case "/member/userDetailPop" :
					detailPopOpen($uibModal, $rootScope.user.principal.userSeq, "/member/userDetailPop", "userDetailPopController", "xlg");
					break;
				case "/main/searchPop" :
					detailPopOpen($uibModal, $scope.textSearch, "/main/searchPop", "searchPopController", "xxlg");
					break;
				}
				
				break;
				
			case "page" :
				
				$window.sessionStorage.removeItem(obj.sessionType);
		    	$location.url(obj.url);
		    	break;
		}
	}
	
	
	$scope.goTopMenu = function(menu, flag){
		
		switch(menu){
			case "userDetail" :
				detailPopOpen($uibModal, $rootScope.user.principal.userSeq, "/member/userDetailPop", "userDetailPopController", "xlg");
				break;
		    	
			case "batchTriggerList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/batchTrigger/batchTriggerList");
		    	break;
		    	
			case "searchPop" :
				detailPopOpen($uibModal, $scope.textSearch, "/main/searchPop", "searchPopController", "xxlg");
				break;
				
			case "userNotiList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/member/userNotiList");
		    	break;
		    	
			case "batchJobDetail" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/batchJob/batchJobDetail");
		    	break;
		    	
			case "productMasterList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/productMaster/productList");
		    	break;
		    	
			case "batchLogList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/batchLog/batchLogList");
		    	break;
		    	
			case "mailLogList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/mailLog/mailLogList");
		    	break;
		    	
			case "boxWorkGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/box/boxWorkGroupList?type=" + flag);
		    	break;
		    	
			case "distributionList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/distribution/distributionList");
		    	break;
		    	
			case "storageScheduleList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/distribution/storageScheduleList");
		    	break;
		    	
			case "appList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/app/appList");
		    	break;
		    	
			case "releaseList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/production/releaseList");
		    	break;
		    	
			case "productionScheduleList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/production/productionScheduleList");
		    	break;
		    	
			case "productionReleaseGroupList" :
//				$window.sessionStorage.removeItem("groupCurrent");
//		    	$location.url("/production/productionReleaseGroupList");
				$window.sessionStorage.removeItem("current");
		    	$location.url("/production/productionReleaseList");
		    	break;
		    	
			case "distributionStorageScheduleGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/distribution/storageScheduleGroupList");
		    	break;
		    	
			case "releaseScheduleGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/distribution/releaseScheduleGroupList");
		    	break;
		    	
			case "storeScheduleGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/distribution/storeScheduleGroupList");
		    	break;
		    	
			case "storeReleaseGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/distribution/storeReleaseGroupList");
		    	break;
		    	
			case "storeStorageScheduleGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/store/storageScheduleGroupList");
		    	break;
		    	
			case "storeList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/store/storeList");
		    	break;
		    	
			case "storeReturnGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/store/storeReturnGroupList");
		    	break;
		    	
			case "storeBoxGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/store/storeBoxGroupList");
		    	break;
		    	
			case "reissueTagGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/reissueTag/reissueTagGroupList?type=" + flag);
		    	break;
		    	
			case "inventoryScheduleGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/inventorySchedule/inventoryScheduleGroupList");
		    	break;
		    	
			case "storeMoveGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/store/storeMoveGroupList?flag=" + flag);
		    	break;
		    	
			case "errorLogList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/errorLog/errorLogList");
		    	break;
		    	
			case "requestLogList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/requestLog/requestLogList");
		    	break;
		    	
			case "batchTriggerList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/batchTrigger/batchTriggerList");
		    	break;
		    	
			case "searchPop" :
				detailPopOpen($uibModal, $scope.textSearch, "/main/searchPop", "searchPopController", "xxlg");
				break;

			case "batchJobDetail" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/batchJob/batchJobDetail");
		    	break;
		    	
			case "productMasterList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/productMaster/productList");
		    	break;
		    	
			case "batchLogList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/batchLog/batchLogList");
		    	break;
		    	
			case "mailLogList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/mailLog/mailLogList");
		    	break;
		    	
			case "boxWorkGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/box/boxWorkGroupList?type=" + flag);
		    	break;
		    	
			case "distributionList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/distribution/distributionList");
		    	break;
		    	
			case "appList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/app/appList");
		    	break;
		    	
			case "productionScheduleList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/production/productionScheduleList");
		    	break;
		    	
			case "productionReleaseGroupList" :
//				$window.sessionStorage.removeItem("groupCurrent");
//		    	$location.url("/production/productionReleaseGroupList");
				$window.sessionStorage.removeItem("current");
		    	$location.url("/production/productionReleaseList");
		    	break;
		    	
			case "distributionStorageScheduleGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/distribution/storageScheduleGroupList");
		    	break;
		    	
			case "storeScheduleGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/distribution/storeScheduleGroupList");
		    	break;
		    	
			case "storeReleaseGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/distribution/storeReleaseGroupList");
		    	break;
		    	
			case "storeStorageScheduleGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/store/storageScheduleGroupList");
		    	break;
		    	
			case "storeList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/store/storeList");
		    	break;
		    	
			case "storeReturnGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/store/storeReturnGroupList");
		    	break;
		    	
			case "reissueTagGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/reissueTag/reissueTagGroupList?type=" + flag);
		    	break;
		    	
			case "inventoryScheduleGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/inventorySchedule/inventoryScheduleGroupList");
		    	break;
		    	
			case "storeMoveGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/store/storeMoveGroupList?flag=" + flag);
		    	break;
		    	
			case "errorLogList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/errorLog/errorLogList");
		    	break;
		    	
			case "requestLogList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/requestLog/requestLogList");
		    	break;
		    	
			case "batchTriggerList" :
				$window.sessionStorage.removeItem("current");
		    	$location.url("/batchTrigger/batchTriggerList");
		    	break;
		    	
			case "searchPop" :
				detailPopOpen($uibModal, $scope.textSearch, "/main/searchPop", "searchPopController", "xxlg");
				break;
				
			/*//반품 입고 예정정보 조회
			case "distributionStorageReturnScheduleGroupList" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/distribution/storageReturnScheduleGroupList");
		    	break;*/
		    	
			/*	//태그 이력 조회
			case "bartagHistory" :
				$window.sessionStorage.removeItem("groupCurrent");
		    	$location.url("/bartag/bartagHistory");
		    	break;	*/
			   	
		}
	}
    
    $scope.sidebarToggle = function(){
    	
    	var flag = $scope.sidebarFlag;
    	
		if(flag == "true" || flag == true){
			$("#sidebar").css("left", "0px");
			$(".body-contents").css("padding-left", "0px");
			$(".sidebar-toggle").css("left", "0px");
			
			$window.sessionStorage.setItem("sidebarToggle", $scope.sidebarFlag);
			$scope.sidebarFlag = false;

		} else {
			$("#sidebar").css("left", "220px");
			$(".body-contents").css("padding-left", "220px");
			$(".sidebar-toggle").css("left", "220px");
			
			$window.sessionStorage.setItem("sidebarToggle", $scope.sidebarFlag);
			$scope.sidebarFlag = true;
			
		}
	};
	
    if($window.sessionStorage.getItem("sidebarToggle")){
		$scope.sidebarFlag = $window.sessionStorage.getItem("sidebarToggle");
		$scope.sidebarToggle();
    } else {
    	$scope.sidebarFlag = false;
		$scope.sidebarToggle();
    }
    
    $scope.collapseVar = [];
    
    $scope.collapse = function(x) {
    	
    	if($scope.collapseVar[x] == 'xi-angle-down') {
    		$scope.collapseVar[x] = 'xi-angle-up'
    	} else {
    		$scope.collapseVar[x] = 'xi-angle-down'
    	}
    	
    	angular.forEach($scope.collapseVar,function(key,value) {
    		if(value != x) {
    			$scope.collapseVar[value] =  'xi-angle-down';
    		}
    	})
    	
	};
}]);

app.controller('homeController', ['$scope', '$http', '$location', '$interval', '$rootScope', '$timeout', '$window', '$uibModal', function ($scope, $http, $location, $interval, $rootScope, $timeout, $window, $uibModal) {
	
	var dailySettingDate = new Date();
	var companySettingDate = new Date();
	var companyStyleSettingDate = new Date();
	var dailyBartagOrderSettingDate = new Date();
	var productionStyleSettingDate = new Date();
	var productionReleaseSettingDate = new Date();
	var distributionStorageGroupSettingDate = new Date();
	var storeStorageCompanyGroupSettingDate = new Date();
	
	$scope.search = {
		dailyGroupStatsDefaultDate : "1",
		companyGroupStatsDefaultDate : "1",
		companyStyleGroupStatsDefaultDate : "1",
		companyStyleGroupStyle : {
			height : 0
		},
		dailyBartagOrderGroupStatsDefaultDate : "1",
		productionStyleGroupStatsDefaultDate : "1",
		productionReleaseGroupStatsDefaultDate : "1",
		distributionStorageGroupStatsDefaultDate : "1",
		storeStorageCompanyGroupStatsDefaultDate : "1",
		dailyGroupStatsStartDateOpened : false,
	    dailyGroupStatsEndDateOpened : false,
	    companyGroupStatsStartDateOpened : false,
	    companyGroupStatsEndDateOpened : false,
	    companyStyleGroupStatsStartDateOpened : false,
	    companyStyleGroupStatsEndDateOpened : false,
	    dailyBartagOrderGroupStatsStartDateOpened : false,
	    dailyBartagOrderGroupStatsEndDateOpened : false,
	    productionStyleGroupStatsStartDateOpened : false,
	    productionStyleGroupStatsEndDateOpened : false,
	    productionReleaseGroupStatsStartDateOpened : false,
	    productionReleaseGroupStatsEndDateOpened : false,
	    distributionStorageGroupStatsStartDateOpened : false,
	    distributionStorageGroupStatsEndDateOpened : false,
	    storeStorageCompanyGroupStatsStartDateOpened : false,
	    storeStorageCompanyGroupStatsEndDateOpened : false
	}
	
	if($scope.search.bartagPieProductSeason == undefined){
		$scope.search.bartagPieProductSeason = "all";
	}
	
	if($scope.search.bartagPieProductYy == undefined){
		$scope.search.bartagPieProductYy = "all";
	}
	
	$http.get('/productMaster/selectList').success(
		function(data) {
			$scope.selectList = angular.fromJson(data);
				
			var flag = true;
			angular.forEach($scope.selectList, function(value, key) {
				if($scope.selectList[key].flag == "productYy"){
					if(flag){
						$scope.search.productYy = $scope.selectList[key].data;
						flag = false;
					}
				}
			});
				
		}
	);
	
	var today = new Date();
	
	if($scope.search.dailyGroupStatsEndDate == undefined || $scope.search.dailyGroupStatsEndDate == ""){
		$scope.search.dailyGroupStatsEndDate = today;
	}
	
	if($scope.search.companyGroupStatsEndDate == undefined || $scope.search.companyGroupStatsEndDate == ""){
		$scope.search.companyGroupStatsEndDate = today;
	}
	
	if($scope.search.companyStyleGroupStatsEndDate == undefined || $scope.search.companyStyleGroupStatsEndDate == ""){
		$scope.search.companyStyleGroupStatsEndDate = today;
	}
	
	if($scope.search.dailyBartagOrderGroupStatsEndDate == undefined || $scope.search.dailyBartagOrderGroupStatsEndDate == ""){
		$scope.search.dailyBartagOrderGroupStatsEndDate = today;
	}
	
	if($scope.search.productionStyleGroupStatsEndDate == undefined || $scope.search.productionStyleGroupStatsEndDate == ""){
		$scope.search.productionStyleGroupStatsEndDate = today;
	}
	
	if($scope.search.productionReleaseGroupStatsEndDate == undefined || $scope.search.productionReleaseGroupStatsEndDate == ""){
		$scope.search.productionReleaseGroupStatsEndDate = today;
	}
	
	if($scope.search.distributionStorageGroupStatsEndDate == undefined || $scope.search.distributionStorageGroupStatsEndDate == ""){
		$scope.search.distributionStorageGroupStatsEndDate = today;
	}
	
	if($scope.search.storeStorageCompanyGroupStatsEndDate == undefined || $scope.search.storeStorageCompanyGroupStatsEndDate == ""){
		$scope.search.storeStorageCompanyGroupStatsEndDate = today;
	}
	
	dailySettingDate.setMonth(dailySettingDate.getMonth()-1);
	$scope.search.dailyGroupStatsStartDate = dailySettingDate;
	
	companySettingDate.setMonth(companySettingDate.getMonth()-1);
	$scope.search.companyGroupStatsStartDate = companySettingDate;
	
	companyStyleSettingDate.setMonth(companyStyleSettingDate.getMonth()-1);
	$scope.search.companyStyleGroupStatsStartDate = companyStyleSettingDate;
	
	dailyBartagOrderSettingDate.setMonth(dailyBartagOrderSettingDate.getMonth()-1);
	$scope.search.dailyBartagOrderGroupStatsStartDate = dailyBartagOrderSettingDate;
	
	productionStyleSettingDate.setMonth(productionStyleSettingDate.getMonth()-1);
	$scope.search.productionStyleGroupStatsStartDate = productionStyleSettingDate;
	
	productionReleaseSettingDate.setMonth(productionReleaseSettingDate.getMonth()-1);
	$scope.search.productionReleaseGroupStatsStartDate = productionReleaseSettingDate;
	
	distributionStorageGroupSettingDate.setMonth(distributionStorageGroupSettingDate.getMonth()-1);
	$scope.search.distributionStorageGroupStatsStartDate = distributionStorageGroupSettingDate;
	
	storeStorageCompanyGroupSettingDate.setMonth(storeStorageCompanyGroupSettingDate.getMonth()-1);
	$scope.search.storeStorageCompanyGroupStatsStartDate = storeStorageCompanyGroupSettingDate;
	
	$scope.dateOptions = {
		formatYear: 'yy',
		maxDate: new Date(2020, 5, 22),
		minDate: new Date(2017, 1, 1),
		startingDay: 1
	};
	
	
	$scope.changeStatsSearchDate = function(flag){
		
		if(flag == "changeDailyGroup"){
			
			dailySettingDate = new Date($scope.search.dailyGroupStatsEndDate);	
			
			if($scope.search.dailyGroupStatsDefaultDate == "1"){
				dailySettingDate.setMonth(dailySettingDate.getMonth()-1);
				$scope.search.dailyGroupStatsStartDate = dailySettingDate;
			} else if($scope.search.dailyGroupStatsDefaultDate == "2"){
				dailySettingDate.setMonth(dailySettingDate.getMonth()-3);
				$scope.search.dailyGroupStatsStartDate = dailySettingDate;
			} else if($scope.search.dailyGroupStatsDefaultDate == "3"){
				dailySettingDate.setMonth(dailySettingDate.getMonth()-6);
				$scope.search.dailyGroupStatsStartDate = dailySettingDate;
			} else {
				dailySettingDate.setYear(dailySettingDate.getFullYear()-1);
				$scope.search.dailyGroupStatsStartDate = dailySettingDate;
			}
			
		} else if(flag == "changeCompanyGroup"){
			
			companySettingDate = new Date($scope.search.companyGroupStatsEndDate);
			
			if($scope.search.companyGroupStatsDefaultDate == "1"){
				companySettingDate.setMonth(companySettingDate.getMonth()-1);
				$scope.search.companyGroupStatsStartDate = companySettingDate;
			} else if($scope.search.companyGroupStatsDefaultDate == "2"){
				companySettingDate.setMonth(companySettingDate.getMonth()-3);
				$scope.search.companyGroupStatsStartDate = companySettingDate;
			} else if($scope.search.companyGroupStatsDefaultDate == "3"){
				companySettingDate.setMonth(companySettingDate.getMonth()-6);
				$scope.search.companyGroupStatsStartDate = companySettingDate;
			} else {
				companySettingDate.setYear(companySettingDate.getFullYear()-1);
				$scope.search.companyGroupStatsStartDate = companySettingDate;
			}
			
		} else if(flag == "changeCompanyStyleGroup"){
			
			companyStyleSettingDate = new Date($scope.search.companyStyleGroupStatsEndDate);
			
			if($scope.search.companyStyleGroupStatsDefaultDate == "1"){
				companyStyleSettingDate.setMonth(companyStyleSettingDate.getMonth()-1);
				$scope.search.companyStyleGroupStatsStartDate = companyStyleSettingDate;
			} else if($scope.search.companyStyleGroupStatsDefaultDate == "2"){
				companyStyleSettingDate.setMonth(companyStyleSettingDate.getMonth()-3);
				$scope.search.companyStyleGroupStatsStartDate = companyStyleSettingDate;
			} else if($scope.search.companyStyleGroupStatsDefaultDate == "3"){
				companyStyleSettingDate.setMonth(companyStyleSettingDate.getMonth()-6);
				$scope.search.companyStyleGroupStatsStartDate = companyStyleSettingDate;
			} else {
				companyStyleSettingDate.setYear(companyStyleSettingDate.getFullYear()-1);
				$scope.search.companyStyleGroupStatsStartDate = companyStyleSettingDate;
			}
			
		} else if(flag == "changeDailyBartagOrderGroup"){
			
			dailyBartagOrderSettingDate = new Date($scope.search.dailyBartagOrderGroupStatsEndDate);	
			
			if($scope.search.dailyBartagOrderGroupStatsDefaultDate == "1"){
				dailyBartagOrderSettingDate.setMonth(dailyBartagOrderSettingDate.getMonth()-1);
				$scope.search.dailyBartagOrderGroupStatsStartDate = dailyBartagOrderSettingDate;
			} else if($scope.search.dailyBartagOrderGroupStatsDefaultDate == "2"){
				dailyBartagOrderSettingDate.setMonth(dailyBartagOrderSettingDate.getMonth()-3);
				$scope.search.dailyBartagOrderGroupStatsStartDate = dailyBartagOrderSettingDate;
			} else if($scope.search.dailyBartagOrderGroupStatsDefaultDate == "3"){
				dailyBartagOrderSettingDate.setMonth(dailyBartagOrderSettingDate.getMonth()-6);
				$scope.search.dailyBartagOrderGroupStatsStartDate = dailyBartagOrderSettingDate;
			} else {
				dailyBartagOrderSettingDate.setYear(dailyBartagOrderSettingDate.getFullYear()-1);
				$scope.search.dailyBartagOrderGroupStatsStartDate = dailyBartagOrderSettingDate;
			}
			
		} else if(flag == "changeProductionStyleGroup"){
			
			productionStyleSettingDate = new Date($scope.search.productionStyleGroupStatsEndDate);
			
			if($scope.search.productionStyleGroupStatsDefaultDate == "1"){
				productionStyleSettingDate.setMonth(productionStyleSettingDate.getMonth()-1);
				$scope.search.productionStyleGroupStatsStartDate = productionStyleSettingDate;
			} else if($scope.search.productionStyleGroupStatsDefaultDate == "2"){
				productionStyleSettingDate.setMonth(productionStyleSettingDate.getMonth()-3);
				$scope.search.productionStyleGroupStatsStartDate = productionStyleSettingDate;
			} else if($scope.search.productionStyleGroupStatsDefaultDate == "3"){
				productionStyleSettingDate.setMonth(productionStyleSettingDate.getMonth()-6);
				$scope.search.productionStyleGroupStatsStartDate = productionStyleSettingDate;
			} else {
				productionStyleSettingDate.setYear(productionStyleSettingDate.getFullYear()-1);
				$scope.search.productionStyleGroupStatsStartDate = productionStyleSettingDate;
			}
			
		} else if(flag == "changeProductionReleaseGroup"){
			
			productionRelaseSettingDate = new Date($scope.search.productionReleaseGroupStatsEndDate);
			
			if($scope.search.productionReleaseGroupStatsDefaultDate == "1"){
				productionReleaseSettingDate.setMonth(productionReleaseSettingDate.getMonth()-1);
				$scope.search.productionReleaseGroupStatsStartDate = productionReleaseSettingDate;
			} else if($scope.search.productionReleaseGroupStatsDefaultDate == "2"){
				productionReleaseSettingDate.setMonth(productionReleaseSettingDate.getMonth()-3);
				$scope.search.productionReleaseGroupStatsStartDate = productionReleaseSettingDate;
			} else if($scope.search.productionReleaseGroupStatsDefaultDate == "3"){
				productionReleaseSettingDate.setMonth(productionReleaseSettingDate.getMonth()-6);
				$scope.search.productionReleaseGroupStatsStartDate = productionReleaseSettingDate;
			} else {
				productionReleaseSettingDate.setYear(productionReleaseSettingDate.getFullYear()-1);
				$scope.search.productionReleaseGroupStatsStartDate = productionReleaseSettingDate;
			}
			
		} 
		//물류차트 날짜 변경
		else if(flag == "changeDistributionStorageGroup"){
			
			distributionStorageGroupSettingDate = new Date($scope.search.distributionStorageGroupStatsEndDate);
			
			if($scope.search.distributionStorageGroupStatsDefaultDate == "1"){
				distributionStorageGroupSettingDate.setMonth(distributionStorageGroupSettingDate.getMonth()-1);
				$scope.search.distributionStorageGroupStatsStartDate = distributionStorageGroupSettingDate;
			} else if($scope.search.distributionStorageGroupStatsDefaultDate == "2"){
				distributionStorageGroupSettingDate.setMonth(distributionStorageGroupSettingDate.getMonth()-3);
				$scope.search.distributionStorageGroupStatsStartDate = distributionStorageGroupSettingDate;
			} else if($scope.search.distributionStorageGroupStatsDefaultDate == "3"){
				distributionStorageGroupSettingDate.setMonth(distributionStorageGroupSettingDate.getMonth()-6);
				$scope.search.distributionStorageGroupStatsStartDate = distributionStorageGroupSettingDate;
			} else {
				distributionStorageGroupSettingDate.setYear(distributionStorageGroupSettingDate.getFullYear()-1);
				$scope.search.distributionStorageGroupStatsStartDate = distributionStorageGroupSettingDate;
			}
			
		}
		//매장차트 날짜 변경
		else if(flag == "changeStoreStorageCompanyGroup"){
			
			storeStorageCompanyGroupSettingDate = new Date($scope.search.storeStorageCompanyGroupStatsEndDate);
			
			if($scope.search.storeStorageCompanyGroupStatsDefaultDate == "1"){
				storeStorageCompanyGroupSettingDate.setMonth(storeStorageCompanyGroupSettingDate.getMonth()-1);
				$scope.search.storeStorageCompanyGroupStatsStartDate = storeStorageCompanyGroupSettingDate;
			} else if($scope.search.storeStorageCompanyGroupStatsDefaultDate == "2"){
				storeStorageCompanyGroupSettingDate.setMonth(storeStorageCompanyGroupSettingDate.getMonth()-3);
				$scope.search.storeStorageCompanyGroupStatsStartDate = storeStorageCompanyGroupSettingDate;
			} else if($scope.search.storeStorageCompanyGroupStatsDefaultDate == "3"){
				storeStorageCompanyGroupSettingDate.setMonth(storeStorageCompanyGroupSettingDate.getMonth()-6);
				$scope.search.storeStorageCompanyGroupStatsStartDate = storeStorageCompanyGroupSettingDate;
			} else {
				storeStorageCompanyGroupSettingDate.setYear(storeStorageCompanyGroupSettingDate.getFullYear()-1);
				$scope.search.storeStorageCompanyGroupStatsStartDate = storeStorageCompanyGroupSettingDate;
			}
			
		}
	};
	
	$scope.statsOpen = function(flag) {
		
		if(flag == "dailyGroupStatsStart"){
			$scope.search.dailyGroupStatsStartDateOpened = true;
		} else if(flag == "dailyGroupStatsEnd"){
			$scope.search.dailyGroupStatsEndDateOpened = true;
		} else if(flag == "companyGroupStatsStart"){
			$scope.search.companyGroupStatsStartDateOpened = true;
		} else if(flag == "companyGroupStatsEnd"){
			$scope.search.companyGroupStatsEndDateOpened = true;
		} else if(flag == "companyStyleGroupStatsStart"){
			$scope.search.companyStyleGroupStatsStartDateOpened = true;
		} else if(flag == "companyStyleGroupStatsEnd"){
			$scope.search.companyStyleGroupStatsEndDateOpened = true;
		} else if(flag == "dailyBartagOrderGroupStatsStart"){
			$scope.search.dailyBartagOrderGroupStatsStartDateOpened = true;
		} else if(flag == "dailyBartagOrderGroupStatsEnd"){
			$scope.search.dailyBartagOrderGroupStatsEndDateOpened = true;
		} else if(flag == "productionStyleGroupStatsStart"){
			$scope.search.productionStyleGroupStatsStartDateOpened = true;
		} else if(flag == "productionStyleGroupStatsEnd"){
			$scope.search.productionStyleGroupStatsEndDateOpened = true;
		} else if(flag == "productionReleaseGroupStatsStart"){
			$scope.search.productionReleaseGroupStatsStartDateOpened = true;
		} else if(flag == "productionReleaseGroupStatsEnd"){
			$scope.search.productionReleaseGroupStatsEndDateOpened = true;
		}
		//물류차트 시작,종료 날짜
		else if(flag == "distributionStorageGroupStatsStart"){
			$scope.search.distributionStorageGroupStatsStartDateOpened = true;
		} else if(flag == "distributionStorageGroupStatsEnd"){
			$scope.search.distributionStorageGroupStatsEndDateOpened = true;
		}
		//매장차트 시작,종료 날짜
		else if(flag == "storeStorageCompanyGroupStatsStart"){
			$scope.search.storeStorageCompanyGroupStatsStartDateOpened = true;
		} else if(flag == "storeStorageCompanyGroupStatsEnd"){
			$scope.search.storeStorageCompanyGroupStatsEndDateOpened = true;
		}
	};
	

	$scope.dailyGroupStatsOptions = {
			legend: {
				disply: true
			},
			title: {
	            display: true,
	            fontSize: 20
	        },
			tooltips: {
				callbacks: {
					label: function(tooltipItem, data) {
						
						var dataset = data.datasets[tooltipItem.datasetIndex];
						var currentValue = dataset.data[tooltipItem.index];
						
						var returnText = [];
						angular.forEach($scope.tempDailyGroupStatsList, function(value, key) {
							if($scope.tempDailyGroupStatsList[key].regDate == data.labels[tooltipItem.index]){
								var precentage = Math.floor((($scope.tempDailyGroupStatsList[key].amount/currentValue) * 100)+0.5);
								returnText.push($scope.tempDailyGroupStatsList[key].name + " (" + numberWithCommas($scope.tempDailyGroupStatsList[key].amount) + ", " + precentage + "%)");
							}
						});
						
						returnText.push("총 수량 (" + numberWithCommas(currentValue) + ", 100%)");
			            return returnText;
					}
				}
			},
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero:true,
						userCallback: function(value, index, values) {
							value = value.toString();
							value = value.split(/(?=(?:...)*$)/);
							value = value.join(',');
							return value;
						}
					}
				}],
				xAxes: [{
					ticks: {
					}
				}]
			}
		};
	
	$scope.companyGroupStatsOptions = {
			legend: {
				disply: true
			},
			title: {
	            display: true,
	            fontSize: 20
	        },
			tooltips: {
				callbacks: {
					label: function(tooltipItem, data) {
						var dataset = data.datasets[tooltipItem.datasetIndex];
				        var total = dataset.data.reduce(function(previousValue, currentValue, currentIndex, array) {
				            return previousValue + currentValue;
				        });
				        
				        var companyTotal = 0;
				        
				        for(var i=0; i<data.datasets.length; i++){
				        	companyTotal += data.datasets[i].data[tooltipItem.index];
				        }
				        
				        var title;
				        
				        if(dataset.label == "stat1Amount"){
				        	title = "미발행";
				        } else if(dataset.label == "stat2Amount"){
				        	title = "발행대기";
				        } else if(dataset.label == "stat3Amount"){
				        	title = "발행완료";
				        } else if(dataset.label == "stat4Amount"){
				        	title = "재발행대기"
				        } else if(dataset.label == "stat5Amount"){
				        	title = "재발행완료"
				        } else if(dataset.label == "stat6Amount"){
				        	title = "재발행요청"
				        } else if(dataset.label == "stat7Amount"){
				        	title = "폐기"
				        }
				        
				        var currentValue = dataset.data[tooltipItem.index];
				        
				        var precentage = Math.floor(((currentValue/companyTotal) * 100)+0.5);      
				        return title + " " + numberWithCommas(currentValue) + " (" + precentage + "%" + ")";
					}
				}
			},
			scales: {
				xAxes: [{
					ticks: {
						beginAtZero:true,
						userCallback: function(value, index, values) {
							value = value.toString();
							value = value.split(/(?=(?:...)*$)/);
							value = value.join(',');
							return value;
						}
					},
					stacked: true
				}],
				yAxes: [{
					stacked: true
				}]
			}
		};
	
	$scope.companyStyleGroupStatsOptions = {
			legend: {
				disply: true
			},
			title: {
	            display: true,
	            fontSize: 20
	        },
	        maintainAspectRatio: false,
			tooltips: { 
				callbacks: {
					label: function(tooltipItem, data) {
						var dataset = data.datasets[tooltipItem.datasetIndex];
				        var total = dataset.data.reduce(function(previousValue, currentValue, currentIndex, array) {
				            return previousValue + currentValue;
				        });
				        
				        var styleTotal = 0;
				        
				        for(var i=0; i<data.datasets.length; i++){
				        	styleTotal += data.datasets[i].data[tooltipItem.index];
				        }
				        
				        var title;
				        
				        if(dataset.label == "stat1Amount"){
				        	title = "미발행";
				        } else if(dataset.label == "stat2Amount"){
				        	title = "발행대기";
				        } else if(dataset.label == "stat3Amount"){
				        	title = "발행완료";
				        } else if(dataset.label == "stat4Amount"){
				        	title = "재발행대기"
				        } else if(dataset.label == "stat5Amount"){
				        	title = "재발행완료"
				        } else if(dataset.label == "stat6Amount"){
				        	title = "재발행요청"
				        } else if(dataset.label == "stat7Amount"){
				        	title = "폐기"
				        }
				        
				        var currentValue = dataset.data[tooltipItem.index];
				        var precentage = Math.floor(((currentValue/styleTotal) * 100)+0.5);         
				        
				        return title + " " + numberWithCommas(currentValue) + " (" + precentage + "%" + ")";
					}
				}
			},
			scales: {
				xAxes: [{
					ticks: {
						beginAtZero:true,
						userCallback: function(value, index, values) {
							value = value.toString();
							value = value.split(/(?=(?:...)*$)/);
							value = value.join(',');
							return value;
						}
					},
					stacked: true
				}],
				yAxes: [{
					stacked: true
				}]
			}
		};
	
	$scope.bartagPieGroupStatsOptions = {
			legend: {
				disply: true
			},
			title: {
	            display: true,
	            fontSize: 20
	        },
	        maintainAspectRatio: false,
			tooltips: { 
				callbacks: {
					label: function(tooltipItem, data) {
						
						var dataset = data.datasets[tooltipItem.datasetIndex];
				        var total = dataset.data.reduce(function(previousValue, currentValue, currentIndex, array) {
				            return previousValue + currentValue;
				        });
						
						var label = data.labels[tooltipItem.index];	
						
						var styleTotal = 0;
				        
				        for(var i=0; i<data.datasets.length; i++){
				        	styleTotal += data.datasets[i].data[tooltipItem.index];
				        }
				        
                        var currentValue = dataset.data[tooltipItem.index];
				        var precentage = Math.floor(((currentValue/total) * 100)+0.5);         
				        
				        return label + " " + numberWithCommas(currentValue) + " (" + precentage + "%" + ")";
					}
				}
			}
		};
	
	$scope.dailyBartagOrderGroupStatsOptions = {
			legend: {
				disply: true
			},
			title: {
	            display: true,
	            fontSize: 20
	        },
			tooltips: {
				callbacks: {
					label: function(tooltipItem, data) {
						
						var dataset = data.datasets[tooltipItem.datasetIndex];
						var currentValue = dataset.data[tooltipItem.index];
						
						var returnText = [];
						angular.forEach($scope.tempDailyBartagOrderGroupStatsList, function(value, key) {
							if($scope.tempDailyBartagOrderGroupStatsList[key].createDate == data.labels[tooltipItem.index]){
								var precentage = Math.floor((($scope.tempDailyBartagOrderGroupStatsList[key].orderAmount/currentValue) * 100)+0.5);
								returnText.push($scope.tempDailyBartagOrderGroupStatsList[key].name + " (" + numberWithCommas($scope.tempDailyBartagOrderGroupStatsList[key].orderAmount) + ", " + precentage + "%)");
							}
						});
						
						returnText.push("총 수량 (" + numberWithCommas(currentValue) + ", 100%)");
			            return returnText;
					}
				}
			},
			scales: {
				yAxes: [{
					ticks: {
						beginAtZero:true,
						userCallback: function(value, index, values) {
							value = value.toString();
							value = value.split(/(?=(?:...)*$)/);
							value = value.join(',');
							return value;
						}
					}
				}],
				xAxes: [{
					ticks: {
					}
				}]
			}
		};
	
	$scope.productionStyleGroupStatsOptions = {
			legend: {
				disply: true
			},
			title: {
	            display: true,
	            fontSize: 20
	        },
	        maintainAspectRatio: false,
			tooltips: { 
				callbacks: {
					label: function(tooltipItem, data) {
						var dataset = data.datasets[tooltipItem.datasetIndex];
				        var total = dataset.data.reduce(function(previousValue, currentValue, currentIndex, array) {
				            return previousValue + currentValue;
				        });
				        
				        var styleTotal = 0;
				        
				        for(var i=0; i<data.datasets.length; i++){
				        	styleTotal += data.datasets[i].data[tooltipItem.index];
				        }
				        
				        var title;
				        
				        if(dataset.label == "stat1Amount"){
				        	title = "발행미검수";
				        } else if(dataset.label == "stat2Amount"){
				        	title = "입고";
				        } else if(dataset.label == "stat3Amount"){
				        	title = "출고";
				        } else if(dataset.label == "stat4Amount"){
				        	title = "재발행요청"
				        } else if(dataset.label == "stat5Amount"){
				        	title = "폐기"
				        } else if(dataset.label == "stat6Amount"){
				        	title = "반품미검수"
				        } else if(dataset.label == "stat7Amount"){
				        	title = "반품"
				        }
				        
				        var currentValue = dataset.data[tooltipItem.index];
				        var precentage = Math.floor(((currentValue/styleTotal) * 100)+0.5);         
				        
				        return title + " " + numberWithCommas(currentValue) + " (" + precentage + "%" + ")";
					}
				}
			},
			scales: {
				xAxes: [{
					ticks: {
						beginAtZero:true,
						userCallback: function(value, index, values) {
							value = value.toString();
							value = value.split(/(?=(?:...)*$)/);
							value = value.join(',');
							return value;
						}
					},
					stacked: true
				}],
				yAxes: [{
					stacked: true
				}]
			}
		};
	
	$scope.productionReleaseGroupStatsOptions = {
			legend: {
				disply: true
			},
			title: {
	            display: true,
	            fontSize: 20
	        },
	        maintainAspectRatio: false,
			tooltips: { 
				callbacks: {
					label: function(tooltipItem, data) {
				        var dataset = data.datasets[tooltipItem.datasetIndex];
				        var total = dataset.data.reduce(function(previousValue, currentValue, currentIndex, array) {
				            return previousValue + currentValue;
				        });
						
						var label = data.labels[tooltipItem.index];	
						
                        var currentValue = dataset.data[tooltipItem.index];
				        var precentage = Math.floor(((currentValue/total) * 100)+0.5);         
				        
				        return label + " " + numberWithCommas(currentValue) + " (" + precentage + "%" + ")";
					}
				}
			}
		};
	
	//물류차트 옵션 값 설정
	$scope.distributionStorageGroupStatsOptions = {
			legend: {
				display: false
			},
			title: {
	            display: true,
	            fontSize: 20
	        },
	        maintainAspectRatio: false,
			tooltips: {
				callbacks: {
					label: function(tooltipItem, data) {
						var dataset = data.datasets[tooltipItem.datasetIndex];
				        var total = dataset.data.reduce(function(previousValue, currentValue, currentIndex, array) {
				            return previousValue + currentValue;
				        });
				        
				        var companyTotal = 0;
				        
				        for(var i=0; i<data.datasets.length; i++){
				        	companyTotal += data.datasets[i].data[tooltipItem.index];
				        }
				        
				        var title;
				        
				        if(dataset.label == "confirmAmount"){
				        	title = "입고예정";
				        } else if(dataset.label == "completeAmount"){
				        	title = "입고완료";
				        } 
				        
				        var currentValue = dataset.data[tooltipItem.index];
				        var precentage = Math.floor(((currentValue/companyTotal) * 100)+0.5);         
				        
				        return title + " " + numberWithCommas(currentValue) + " (" + precentage + "%" + ")";    
					}
				}
			},
			scales: {
				xAxes: [{
					ticks: {
						beginAtZero:true,
						userCallback: function(value, index, values) {
							value = value.toString();
							value = value.split(/(?=(?:...)*$)/);
							value = value.join(',');
							return value;
						}
					},
					stacked: true
				}],
				yAxes: [{
					stacked: true
				}]
			}
		};
	
	//매장차트 옵션 값 설정
	$scope.storeStorageCompanyGroupStatsOptions = {
			legend: {
				display: false
			},
			title: {
	            display: true,
	            fontSize: 20
	        },
	        maintainAspectRatio: false,
			tooltips: {
				callbacks: {
					label: function(tooltipItem, data) {
						var dataset = data.datasets[tooltipItem.datasetIndex];
				        var total = dataset.data.reduce(function(previousValue, currentValue, currentIndex, array) {
				            return previousValue + currentValue;
				        });
				        
				        var companyTotal = 0;
				        
				        for(var i=0; i<data.datasets.length; i++){
				        	companyTotal += data.datasets[i].data[tooltipItem.index];
				        }
				        
				        var title;
				        
				        if(dataset.label == "releaseAmount"){
				        	title = "입고예정";
				        } else if(dataset.label == "completeAmount"){
				        	title = "입고완료";
				        } 
				        
				        var currentValue = dataset.data[tooltipItem.index];
				        var precentage = Math.floor(((currentValue/companyTotal) * 100)+0.5);         
				        
				        return title + " " + numberWithCommas(currentValue) + " (" + precentage + "%" + ")";    
					}
				}
			},
			scales: {
				xAxes: [{
					ticks: {
						beginAtZero:true,
						userCallback: function(value, index, values) {
							value = value.toString();
							value = value.split(/(?=(?:...)*$)/);
							value = value.join(',');
							return value;
						}
					},
					stacked: true
				}],
				yAxes: [{
					stacked: true
				}]
			}
		};
	
	$scope.dailyGroupStatsOnClick = function (points, evt) {
		
		if(points.length > 0){
			var index = points[0]._index;
			
		    var obj = {
			    page : 0,
				startDate : $scope.dailyGroupStatsLabels[index],
				endDate : $scope.dailyGroupStatsLabels[index]
		    };
		    
		    $window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch(obj)));
		    
		    $(location).attr("href", '#/bartag/bartagGroupList');
		}
	};
	
	$scope.companyGroupStatsOnClick = function (points, evt) {
		
		if(points.length > 0){
			var index = points[0]._index;
			
		    var obj = {
			    page : 0,
				startDate : $scope.search.reqCompanyGroupStatsStartDate,
				endDate : $scope.search.reqCompanyGroupStatsEndDate,
				companySeq : $scope.companyGroupStatsCompanySeq[index]
		    };
		    
		    $window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch(obj)));
		    
		    $(location).attr("href", '#/bartag/bartagGroupList');
		}
	};
	
	$scope.companyStyleGroupStatsOnClick = function (points, evt) {
		
		if(points.length > 0){
			var index = points[0]._index;
			
			var obj = {
				bartagSeq : $scope.companyStyleGroupStatsBartagList[index]
			};
		    
		    detailPopOpen($uibModal, obj, "/bartag/bartagDetailPop", "bartagDetailPopController", "xxlg");
		}
	};
	
	$scope.dailyBartagOrderGroupStatsOnClick = function (points, evt) {
		
		if(points.length > 0){
			var index = points[0]._index;
			
		    var obj = {
			    page : 0,
			    startDate : $scope.dailyBartagOrderGroupStatsLabels[index],
				endDate : $scope.dailyBartagOrderGroupStatsLabels[index]
		    };
		    
		    $window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch(obj)));
		    
		    $(location).attr("href", '#/bartag/bartagOrderGroupList');
		}
	};
	
	$scope.productionStyleGroupStatsOnClick = function (points, evt) {
		
		if(points.length > 0){
			var index = points[0]._index;
			
			var obj = {
				productionStorageSeq : $scope.productionStyleGroupStatsBartagList[index]
			};
		    
		    detailPopOpen($uibModal, obj, "/production/productionDetailPop", "productionDetailPopController", "xxlg");
		}
	};
	
	//물류차트 클릭시 화면 이동
	$scope.distributionStorageGroupStatsOnClick = function (points, evt) {
			
			if(points.length > 0){
				var index = points[0]._index;

				var obj = {
					    page : 0,
					    startDate : $scope.search.reqDistributionStorageGroupStatsStartDate,
						endDate : $scope.search.reqDistributionStorageGroupStatsEndDate,
						companySeq : $scope.distributionStorageGroupStatsData4[index]
				    };
				
				    $window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch(obj)));
			    
				$(location).attr("href", '#/distribution/storageScheduleGroupList');
			}
		};
		
	//매장차트 클릭시 화면 이동
	$scope.storeStorageCompanyGroupStatsOnClick = function (points, evt) {
			
			if(points.length > 0){
				var index = points[0]._index;

				var obj = {
					    page : 0,
					    startDate : $scope.search.reqStoreStorageCompanyGroupStatsStartDate,
						endDate : $scope.search.reqStoreStorageCompanyGroupStatsEndDate,
						companySeq : $scope.storeStorageCompanyGroupStatsData4[index]
				    };
				
				    $window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch(obj)));
			    
				$(location).attr("href", '#/store/storageScheduleGroupList');
			}
		};
	
	$scope.dailyGroupStatsHeadSearch = function(){
		
		if($scope.search.dailyGroupStatsStartDate == undefined || $scope.search.dailyGroupStatsStartDate == ""){
			return;
		}
		
		if($scope.search.dailyGroupStatsEndDate == undefined || $scope.search.dailyGroupStatsEndDate == ""){
			return;
		}
		
		$scope.search.reqDailyGroupStatsStartDate =  $scope.search.dailyGroupStatsStartDate.yyyymmdd();
		$scope.search.reqDailyGroupStatsEndDate =  $scope.search.dailyGroupStatsEndDate.yyyymmdd();
		
		$scope.dailyGroupStatsSeries = [];
		$scope.dailyGroupStatsData = [];
		$scope.dailyGroupStatsLabels = [];
		$scope.dailyGroupStatsCompanySeq = [];
		$scope.dailyGroupStatsDatasetOverride = [];
		
		$http.get('/stats/dailyGroupStats?startDate=' + $scope.search.reqDailyGroupStatsStartDate + '&endDate=' + $scope.search.reqDailyGroupStatsEndDate).success(
			function(data) {
				$scope.tempDailyGroupStatsList = angular.fromJson(data);
				$scope.dailyGroupStatsList = [];
					
				angular.forEach($scope.tempDailyGroupStatsList, function(value, key) {
					
					var pushFlag = true;
					
					angular.forEach($scope.dailyGroupStatsList, function(v, k) {
						if($scope.dailyGroupStatsList[k].regDate == $scope.tempDailyGroupStatsList[key].regDate){
							$scope.dailyGroupStatsList[k].amount = $scope.dailyGroupStatsList[k].amount + $scope.tempDailyGroupStatsList[key].amount;
							$scope.dailyGroupStatsList[k].companyList.push({
								companySeq : $scope.tempDailyGroupStatsList[key].companySeq,
								name : $scope.tempDailyGroupStatsList[key].name
							});
							
							pushFlag = false;
						}
					});
					
					if(pushFlag){
						$scope.dailyGroupStatsList.push({
							amount : $scope.tempDailyGroupStatsList[key].amount,
							regDate : $scope.tempDailyGroupStatsList[key].regDate,
							companyList : [{
								companySeq : $scope.tempDailyGroupStatsList[key].companySeq,
								name : $scope.tempDailyGroupStatsList[key].name
							}]
						})
					}
				});
				
				var amount = 0;
				
				angular.forEach($scope.dailyGroupStatsList, function(value, key) {
					amount = amount + $scope.dailyGroupStatsList[key].amount;
					$scope.dailyGroupStatsData.push($scope.dailyGroupStatsList[key].amount);
					$scope.dailyGroupStatsLabels.push($scope.dailyGroupStatsList[key].regDate);
				});
				
				$scope.dailyGroupStatsOptions.title.text = formatDate($scope.search.dailyGroupStatsStartDate) + " ~ " + formatDate($scope.search.dailyGroupStatsEndDate) + " 검색 수량 : " + numberWithCommas(amount);
			}
		);
	};
	
	$scope.companyGroupStatsHeadSearch = function(){
		
		if($scope.search.companyGroupStatsStartDate == undefined || $scope.search.companyGroupStatsStartDate == ""){
			return;
		}
		
		if($scope.search.companyGroupStatsEndDate == undefined || $scope.search.companyGroupStatsEndDate == ""){
			return;
		}
		
		$scope.search.reqCompanyGroupStatsStartDate =  $scope.search.companyGroupStatsStartDate.yyyymmdd();
		$scope.search.reqCompanyGroupStatsEndDate =  $scope.search.companyGroupStatsEndDate.yyyymmdd();
		
		$scope.companyGroupStatsSeries = [];
		$scope.companyGroupStatsData = [];
		$scope.companyGroupStatsDataStat1 = [];
		$scope.companyGroupStatsDataStat2 = [];
		$scope.companyGroupStatsDataStat3 = [];
		$scope.companyGroupStatsDataStat4 = [];
		$scope.companyGroupStatsDataStat5 = [];
		$scope.companyGroupStatsDataStat6 = [];
		$scope.companyGroupStatsDataStat7 = [];
		$scope.companyGroupStatsLabels = [];
		$scope.companyGroupStatsCompanySeq = [];
		$scope.companyGroupStatsDatasetOverride = [];
		
		$http.get('/stats/companyGroupStats?startDate=' + $scope.search.reqCompanyGroupStatsStartDate + '&endDate=' + $scope.search.reqCompanyGroupStatsEndDate).success(
			function(data) {
				$scope.companyGroupStatsList = angular.fromJson(data);
				var amount = 0;
				angular.forEach($scope.companyGroupStatsList, function(value, key) {
					amount = amount + $scope.companyGroupStatsList[key].totalAmount;
					$scope.companyGroupStatsDataStat1.push($scope.companyGroupStatsList[key].stat1Amount);
					$scope.companyGroupStatsDataStat2.push($scope.companyGroupStatsList[key].stat2Amount);
					$scope.companyGroupStatsDataStat3.push($scope.companyGroupStatsList[key].stat3Amount);
					$scope.companyGroupStatsDataStat4.push($scope.companyGroupStatsList[key].stat4Amount);
					$scope.companyGroupStatsDataStat5.push($scope.companyGroupStatsList[key].stat5Amount);
					$scope.companyGroupStatsDataStat6.push($scope.companyGroupStatsList[key].stat6Amount);
					$scope.companyGroupStatsDataStat7.push($scope.companyGroupStatsList[key].stat7Amount);
					$scope.companyGroupStatsLabels.push($scope.companyGroupStatsList[key].name);
					$scope.companyGroupStatsCompanySeq.push($scope.companyGroupStatsList[key].companySeq);
					$scope.companyGroupStatsDatasetOverride.push({
						label: "stat1Amount",
						backgroundColor: "rgb(255, 99, 132)",
						borderWidth: 0
					}); 
					
					$scope.companyGroupStatsDatasetOverride.push({
						label: "stat2Amount",
						backgroundColor: "rgb(255, 205, 86)",
						borderWidth: 0
					});
					
					$scope.companyGroupStatsDatasetOverride.push({
						label: "stat3Amount",
						backgroundColor: "rgb(54, 162, 235)",
						borderWidth: 0
					});
					
					$scope.companyGroupStatsDatasetOverride.push({
						label: "stat4Amount",
						backgroundColor: "#00ADF9",
						borderWidth: 0
					});
					
					$scope.companyGroupStatsDatasetOverride.push({
						label: "stat5Amount",
						backgroundColor: "#4D5360",
						borderWidth: 0
					});
					
					$scope.companyGroupStatsDatasetOverride.push({
						label: "stat6Amount",
						backgroundColor: "#803690",
						borderWidth: 0
					});
					
					$scope.companyGroupStatsDatasetOverride.push({
						label: "stat7Amount",
						backgroundColor: "#DCDCDC",
						borderWidth: 0
					});
				});
				
				$scope.companyGroupStatsData.push($scope.companyGroupStatsDataStat1);
				$scope.companyGroupStatsData.push($scope.companyGroupStatsDataStat2);
				$scope.companyGroupStatsData.push($scope.companyGroupStatsDataStat3);
				$scope.companyGroupStatsData.push($scope.companyGroupStatsDataStat4);
				$scope.companyGroupStatsData.push($scope.companyGroupStatsDataStat5);
				$scope.companyGroupStatsData.push($scope.companyGroupStatsDataStat6);
				$scope.companyGroupStatsData.push($scope.companyGroupStatsDataStat7);
				
				$scope.companyGroupStatsOptions.title.text = formatDate($scope.search.companyGroupStatsStartDate) + " ~ " + formatDate($scope.search.companyGroupStatsEndDate) + " 검색 수량 : " + numberWithCommas(amount);
			}
		);
	};
	
	$scope.companyStyleGroupStatsHeadSearch = function(){
		
		if($scope.search.companyStyleGroupStatsStartDate == undefined || $scope.search.companyStyleGroupStatsStartDate == ""){
			return;
		}
		
		if($scope.search.companyStyleGroupStatsEndDate == undefined || $scope.search.companyStyleGroupStatsEndDate == ""){
			return;
		}
		
		if($scope.search.companyStyleInfo == undefined){
			$scope.search.companyStyleSeq = 0;
		} else {
			$scope.search.companyStyleSeq = $scope.search.companyStyleInfo.companySeq;
		}
		
		$scope.search.reqCompanyStyleGroupStatsStartDate =  $scope.search.companyStyleGroupStatsStartDate.yyyymmdd();
		$scope.search.reqCompanyStyleGroupStatsEndDate =  $scope.search.companyStyleGroupStatsEndDate.yyyymmdd();
		
		$scope.companyStyleGroupStatsSeries = [];
		$scope.companyStyleGroupStatsData = [];
		$scope.companyStyleGroupStatsData1 = [];
		$scope.companyStyleGroupStatsData2 = [];
		$scope.companyStyleGroupStatsData3 = [];
		$scope.companyStyleGroupStatsData4 = [];
		$scope.companyStyleGroupStatsData5 = [];
		$scope.companyStyleGroupStatsData6 = [];
		$scope.companyStyleGroupStatsData7 = [];
		$scope.companyStyleGroupStatsLabels = [];
		$scope.companyStyleGroupStatsBartagList = [];
		$scope.companyStyleGroupStatsDatasetOverride = [];
		
		
		$http.get("/stats/companyStyleGroupStats?startDate=" + $scope.search.reqCompanyStyleGroupStatsStartDate + "&endDate=" + $scope.search.reqCompanyStyleGroupStatsEndDate + "&companySeq=" + $scope.search.companyStyleSeq).success(
			function(data) {
				$scope.companyStyleGroupStatsList = angular.fromJson(data);
				var amount = 0;
				angular.forEach($scope.companyStyleGroupStatsList, function(value, key) {
					
					amount = amount + $scope.companyStyleGroupStatsList[key].totalAmount;
					
					$scope.companyStyleGroupStatsData1.push($scope.companyStyleGroupStatsList[key].stat1Amount);
					$scope.companyStyleGroupStatsData2.push($scope.companyStyleGroupStatsList[key].stat2Amount);
					$scope.companyStyleGroupStatsData3.push($scope.companyStyleGroupStatsList[key].stat3Amount);
					$scope.companyStyleGroupStatsData4.push($scope.companyStyleGroupStatsList[key].stat4Amount);
					$scope.companyStyleGroupStatsData5.push($scope.companyStyleGroupStatsList[key].stat5Amount);
					$scope.companyStyleGroupStatsData6.push($scope.companyStyleGroupStatsList[key].stat6Amount);
					$scope.companyStyleGroupStatsData7.push($scope.companyStyleGroupStatsList[key].stat7Amount);
					
					$scope.companyStyleGroupStatsLabels.push($scope.companyStyleGroupStatsList[key].style + " | " + $scope.companyStyleGroupStatsList[key].color + " | " + $scope.companyStyleGroupStatsList[key].size);
					$scope.companyStyleGroupStatsBartagList.push($scope.companyStyleGroupStatsList[key].bartagSeq);
					
					$scope.companyStyleGroupStatsDatasetOverride.push({
						label: "stat1Amount",
						backgroundColor: "rgb(255, 99, 132)",
						borderWidth: 0
					}); 
					
					$scope.companyStyleGroupStatsDatasetOverride.push({
						label: "stat2Amount",
						backgroundColor: "rgb(255, 205, 86)",
						borderWidth: 0
					});
					
					$scope.companyStyleGroupStatsDatasetOverride.push({
						label: "stat3Amount",
						backgroundColor: "rgb(54, 162, 235)",
						borderWidth: 0
					});
					
					$scope.companyStyleGroupStatsDatasetOverride.push({
						label: "stat4Amount",
						backgroundColor: "#00ADF9",
						borderWidth: 0
					});
					
					$scope.companyStyleGroupStatsDatasetOverride.push({
						label: "stat5Amount",
						backgroundColor: "#4D5360",
						borderWidth: 0
					});
					
					$scope.companyStyleGroupStatsDatasetOverride.push({
						label: "stat6Amount",
						backgroundColor: "#803690",
						borderWidth: 0
					});
					
					$scope.companyStyleGroupStatsDatasetOverride.push({
						label: "stat7Amount",
						backgroundColor: "#DCDCDC",
						borderWidth: 0
					});
				});
				
				$scope.companyStyleGroupStatsData.push($scope.companyStyleGroupStatsData1);
				$scope.companyStyleGroupStatsData.push($scope.companyStyleGroupStatsData2);
				$scope.companyStyleGroupStatsData.push($scope.companyStyleGroupStatsData3);
				$scope.companyStyleGroupStatsData.push($scope.companyStyleGroupStatsData4);
				$scope.companyStyleGroupStatsData.push($scope.companyStyleGroupStatsData5);
				$scope.companyStyleGroupStatsData.push($scope.companyStyleGroupStatsData6);
				$scope.companyStyleGroupStatsData.push($scope.companyStyleGroupStatsData7);
			
				if($scope.companyStyleGroupStatsList.length > 0){
					
					var multiNum = 0;
					if($scope.companyStyleGroupStatsList.length == 1){
						multiNum = 300;
					} else if($scope.companyStyleGroupStatsList.length < 10 && $scope.companyStyleGroupStatsList.length > 1){
						multiNum = 80;
					} else {
						multiNum = 30;
					}
					
					$("#companyStyleGroupStatsDiv").height($scope.companyStyleGroupStatsList.length * multiNum);
					
					$scope.companyStyleGroupStatsOptions.title.text = formatDate($scope.search.companyStyleGroupStatsStartDate) + " ~ " + formatDate($scope.search.companyStyleGroupStatsEndDate) + " 건수 : " + numberWithCommas($scope.companyStyleGroupStatsList.length) + " 검색 수량 : " + numberWithCommas(amount);
				}
			}
		);
	};
	
	$scope.bartagPieGroupStatsHeadSearch = function(){
		
		if($scope.search.bartagPieInfo == undefined){
			$scope.search.bartagPieCompanySeq = 0;
		} else {
			$scope.search.bartagPieCompanySeq = $scope.search.bartagPieInfo.companySeq;
		}
		
		$scope.search.reqBartagPieProductYy =  $scope.search.bartagPieProductYy;
		$scope.search.reqBartagPieProductSeason =  $scope.search.bartagPieProductSeason;
		
		$scope.bartagPieGroupStatsSeries = [];
		$scope.bartagPieGroupStatsData = [];
		$scope.bartagPieGroupStatsLabels = [];
		$scope.bartagPieGroupStatsDatasetOverride = [];
		
		$http.get("/stats/bartagPieGroupStats?productYy=" + $scope.search.reqBartagPieProductYy + "&productSeason=" + $scope.search.reqBartagPieProductSeason + "&companySeq=" + $scope.search.bartagPieCompanySeq).success(
			function(data) {
				$scope.bartagPieGroupStatsList = angular.fromJson(data);
				
				var amount = 0;
				var stat1Amount = 0;
				var stat2Amount = 0;
				var stat3Amount = 0;
				var stat4Amount = 0;
				var stat5Amount = 0;
				var stat6Amount = 0;
				var stat7Amount = 0;
				
				angular.forEach($scope.bartagPieGroupStatsList, function(value, key) {
					
					amount += $scope.bartagPieGroupStatsList[key].amount;
					stat1Amount += $scope.bartagPieGroupStatsList[key].stat1Amount;
					stat2Amount += $scope.bartagPieGroupStatsList[key].stat2Amount;
					stat3Amount += $scope.bartagPieGroupStatsList[key].stat3Amount;
					stat4Amount += $scope.bartagPieGroupStatsList[key].stat4Amount;
					stat5Amount += $scope.bartagPieGroupStatsList[key].stat5Amount;
					stat6Amount += $scope.bartagPieGroupStatsList[key].stat6Amount;
					stat7Amount += $scope.bartagPieGroupStatsList[key].stat7Amount;
				});
				
				$scope.bartagPieGroupStatsData.push(stat1Amount);
				$scope.bartagPieGroupStatsData.push(stat2Amount);
				$scope.bartagPieGroupStatsData.push(stat3Amount);
				$scope.bartagPieGroupStatsData.push(stat4Amount);
				$scope.bartagPieGroupStatsData.push(stat5Amount);
				$scope.bartagPieGroupStatsData.push(stat6Amount);
				$scope.bartagPieGroupStatsData.push(stat7Amount);
				
				$scope.bartagPieGroupStatsLabels.push("미발행");
				$scope.bartagPieGroupStatsLabels.push("발행대기");
				$scope.bartagPieGroupStatsLabels.push("발행완료");
				$scope.bartagPieGroupStatsLabels.push("재발행대기");
				$scope.bartagPieGroupStatsLabels.push("재발행완료");
				$scope.bartagPieGroupStatsLabels.push("요청");
				$scope.bartagPieGroupStatsLabels.push("폐기");
				
				if($scope.bartagPieGroupStatsList.length > 0){
					
					$("#bartagPieGroupStatsDiv").height(376);
					$scope.bartagPieGroupStatsOptions.title.text = "총 발행 수량 : " + numberWithCommas(amount);
				} else {
					
					$("#bartagPieGroupStatsDiv").height(150);
					$scope.bartagPieGroupStatsOptions.title.text = "";
				}
			}
		);
	};
	
	$scope.companyStyle = function(){
		$http.get('/stats/companyStyle').success(
			function(data) {
				$scope.companyStyleList = angular.fromJson(data);
				
				if($rootScope.userRole == 'production'){
					$scope.search.productionStyleInfo = $rootScope.user.principal.companyInfo;
					$scope.search.productionReleaseInfo = $rootScope.user.principal.companyInfo;
				}
			}
		);
	};
	
	$scope.dailyBartagOrderGroupStatsHeadSearch = function(){
		
		if($scope.search.dailyBartagOrderGroupStatsStartDate == undefined || $scope.search.dailyBartagOrderGroupStatsStartDate == ""){
			return;
		}
		
		if($scope.search.dailyBartagOrderGroupStatsEndDate == undefined || $scope.search.dailyBartagOrderGroupStatsEndDate == ""){
			return;
		}
		
		$scope.search.reqDailyBartagOrderGroupStatsStartDate =  $scope.search.dailyBartagOrderGroupStatsStartDate.yyyymmdd();
		$scope.search.reqDailyBartagOrderGroupStatsEndDate =  $scope.search.dailyBartagOrderGroupStatsEndDate.yyyymmdd();
		
		$scope.dailyBartagOrderGroupStatsSeries = [];
		$scope.dailyBartagOrderGroupStatsData = [];
		$scope.dailyBartagOrderGroupStatsLabels = [];
		$scope.dailyBartagOrderGroupStatsCompanySeq = [];
		$scope.dailyBartagOrderGroupStatsDatasetOverride = [];
		
		var companySeq = 0;
		
		if($rootScope.userRole == 'production'){
			companySeq = $rootScope.user.principal.companyInfo.companySeq;
		}
		
		$http.get('/stats/dailyBartagOrderGroupStats?startDate=' + $scope.search.reqDailyBartagOrderGroupStatsStartDate + '&endDate=' + $scope.search.reqDailyBartagOrderGroupStatsEndDate + '&companySeq=' + companySeq).success(
			function(data) {
				$scope.tempDailyBartagOrderGroupStatsList = angular.fromJson(data);
				$scope.dailyBartagOrderGroupStatsList = [];
					
				angular.forEach($scope.tempDailyBartagOrderGroupStatsList, function(value, key) {
					
					var pushFlag = true;
					
					angular.forEach($scope.dailyBartagOrderGroupStatsList, function(v, k) {
						if($scope.dailyBartagOrderGroupStatsList[k].createDate == $scope.tempDailyBartagOrderGroupStatsList[key].createDate){
							$scope.dailyBartagOrderGroupStatsList[k].orderAmount = $scope.dailyBartagOrderGroupStatsList[k].orderAmount + $scope.tempDailyBartagOrderGroupStatsList[key].orderAmount;
							$scope.dailyBartagOrderGroupStatsList[k].companyList.push({
								companySeq : $scope.tempDailyBartagOrderGroupStatsList[key].companySeq,
								name : $scope.tempDailyBartagOrderGroupStatsList[key].name
							});
							
							pushFlag = false;
						}
					});
					
					if(pushFlag){
						$scope.dailyBartagOrderGroupStatsList.push({
							orderAmount : $scope.tempDailyBartagOrderGroupStatsList[key].orderAmount,
							createDate : $scope.tempDailyBartagOrderGroupStatsList[key].createDate,
							companyList : [{
								companySeq : $scope.tempDailyBartagOrderGroupStatsList[key].companySeq,
								name : $scope.tempDailyBartagOrderGroupStatsList[key].name
							}]
						})
					}
				});
				
				var orderAmount = 0;
				
				angular.forEach($scope.dailyBartagOrderGroupStatsList, function(value, key) {
					orderAmount = orderAmount + $scope.dailyBartagOrderGroupStatsList[key].orderAmount;
					$scope.dailyBartagOrderGroupStatsData.push($scope.dailyBartagOrderGroupStatsList[key].orderAmount);
					$scope.dailyBartagOrderGroupStatsLabels.push($scope.dailyBartagOrderGroupStatsList[key].createDate);
				});
				
				$scope.dailyBartagOrderGroupStatsOptions.title.text = formatDate($scope.search.dailyBartagOrderGroupStatsStartDate) + " ~ " + formatDate($scope.search.dailyBartagOrderGroupStatsEndDate) + " 검색 수량 : " + numberWithCommas(orderAmount);
			}
		);
	}
	
	$scope.productionStyleGroupStatsHeadSearch = function(){
		
		if($scope.search.productionStyleGroupStatsStartDate == undefined || $scope.search.productionStyleGroupStatsStartDate == ""){
			return;
		}
		
		if($scope.search.productionStyleGroupStatsEndDate == undefined || $scope.search.productionStyleGroupStatsEndDate == ""){
			return;
		}
		
		if($scope.search.productionStyleInfo == undefined){
			$scope.search.productionStyleSeq = 0;
		} else {
			$scope.search.productionStyleSeq = $scope.search.productionStyleInfo.companySeq;
		}
		
		$scope.search.reqProductionStyleGroupStatsStartDate =  $scope.search.productionStyleGroupStatsStartDate.yyyymmdd();
		$scope.search.reqProductionStyleGroupStatsEndDate =  $scope.search.productionStyleGroupStatsEndDate.yyyymmdd();
		
		$scope.productionStyleGroupStatsSeries = [];
		$scope.productionStyleGroupStatsData = [];
		$scope.productionStyleGroupStatsData1 = [];
		$scope.productionStyleGroupStatsData2 = [];
		$scope.productionStyleGroupStatsData3 = [];
		$scope.productionStyleGroupStatsData4 = [];
		$scope.productionStyleGroupStatsData5 = [];
		$scope.productionStyleGroupStatsData6 = [];
		$scope.productionStyleGroupStatsData7 = [];
		$scope.productionStyleGroupStatsLabels = [];
		$scope.productionStyleGroupStatsBartagList = [];
		$scope.productionStyleGroupStatsDatasetOverride = [];
		
		
		$http.get("/stats/productionStyleGroupStats?startDate=" + $scope.search.reqProductionStyleGroupStatsStartDate + "&endDate=" + $scope.search.reqProductionStyleGroupStatsEndDate + "&companySeq=" + $scope.search.productionStyleSeq).success(
			function(data) {
				$scope.productionStyleGroupStatsList = angular.fromJson(data);
				var amount = 0;
				angular.forEach($scope.productionStyleGroupStatsList, function(value, key) {
					
					amount = amount + $scope.productionStyleGroupStatsList[key].totalAmount;
					
					$scope.productionStyleGroupStatsData1.push($scope.productionStyleGroupStatsList[key].stat1Amount);
					$scope.productionStyleGroupStatsData2.push($scope.productionStyleGroupStatsList[key].stat2Amount);
					$scope.productionStyleGroupStatsData3.push($scope.productionStyleGroupStatsList[key].stat3Amount);
					$scope.productionStyleGroupStatsData4.push($scope.productionStyleGroupStatsList[key].stat4Amount);
					$scope.productionStyleGroupStatsData5.push($scope.productionStyleGroupStatsList[key].stat5Amount);
					$scope.productionStyleGroupStatsData6.push($scope.productionStyleGroupStatsList[key].stat6Amount);
					$scope.productionStyleGroupStatsData7.push($scope.productionStyleGroupStatsList[key].stat7Amount);
					
					$scope.productionStyleGroupStatsLabels.push($scope.productionStyleGroupStatsList[key].style + " | " + $scope.productionStyleGroupStatsList[key].color + " | " + $scope.productionStyleGroupStatsList[key].size);
					$scope.productionStyleGroupStatsBartagList.push($scope.productionStyleGroupStatsList[key].productionStorageSeq);
					
					$scope.productionStyleGroupStatsDatasetOverride.push({
						label: "stat1Amount",
						backgroundColor: "rgb(255, 99, 132)",
						borderWidth: 0
					}); 
					
					$scope.productionStyleGroupStatsDatasetOverride.push({
						label: "stat2Amount",
						backgroundColor: "rgb(255, 205, 86)",
						borderWidth: 0
					});
					
					$scope.productionStyleGroupStatsDatasetOverride.push({
						label: "stat3Amount",
						backgroundColor: "rgb(54, 162, 235)",
						borderWidth: 0
					});
					
					$scope.productionStyleGroupStatsDatasetOverride.push({
						label: "stat4Amount",
						backgroundColor: "#00ADF9",
						borderWidth: 0
					});
					
					$scope.productionStyleGroupStatsDatasetOverride.push({
						label: "stat5Amount",
						backgroundColor: "#4D5360",
						borderWidth: 0
					});
					
					$scope.productionStyleGroupStatsDatasetOverride.push({
						label: "stat6Amount",
						backgroundColor: "#803690",
						borderWidth: 0
					});
					
					$scope.productionStyleGroupStatsDatasetOverride.push({
						label: "stat7Amount",
						backgroundColor: "#DCDCDC",
						borderWidth: 0
					});
				});
				
				$scope.productionStyleGroupStatsData.push($scope.productionStyleGroupStatsData1);
				$scope.productionStyleGroupStatsData.push($scope.productionStyleGroupStatsData2);
				$scope.productionStyleGroupStatsData.push($scope.productionStyleGroupStatsData3);
				$scope.productionStyleGroupStatsData.push($scope.productionStyleGroupStatsData4);
				$scope.productionStyleGroupStatsData.push($scope.productionStyleGroupStatsData5);
				$scope.productionStyleGroupStatsData.push($scope.productionStyleGroupStatsData6);
				$scope.productionStyleGroupStatsData.push($scope.productionStyleGroupStatsData7);
				
				if($scope.productionStyleGroupStatsList.length > 0){
					
					var multiNum = 0;
					
					if($scope.productionStyleGroupStatsList.length == 1){
						multiNum = 300;
					} else if($scope.productionStyleGroupStatsList.length < 10 && $scope.productionStyleGroupStatsList.length > 1){
						multiNum = 60;
					} else {
						multiNum = 30;
					}
					
					$("#productionStyleGroupStatsDiv").height($scope.productionStyleGroupStatsList.length * multiNum);
					
					$scope.productionStyleGroupStatsOptions.title.text = formatDate($scope.search.productionStyleGroupStatsStartDate) + " ~ " + formatDate($scope.search.productionStyleGroupStatsEndDate) + " 건수 : " + numberWithCommas($scope.productionStyleGroupStatsList.length) + " 검색 수량 : " + numberWithCommas(amount);
				}
			}
		);
	};
	
	$scope.productionReleaseGroupStatsHeadSearch = function(){
		
		if($scope.search.productionReleaseGroupStatsStartDate == undefined || $scope.search.productionReleaseGroupStatsStartDate == ""){
			return;
		}
		
		if($scope.search.productionReleaseGroupStatsEndDate == undefined || $scope.search.productionReleaseGroupStatsEndDate == ""){
			return;
		}
		
		if($scope.search.productionReleaseInfo == undefined){
			$scope.search.productionReleaseSeq = 0;
		} else {
			$scope.search.productionReleaseSeq = $scope.search.productionReleaseInfo.companySeq;
		}
		
		$scope.search.reqProductionReleaseGroupStatsStartDate =  $scope.search.productionReleaseGroupStatsStartDate.yyyymmdd();
		$scope.search.reqProductionReleaseGroupStatsEndDate =  $scope.search.productionReleaseGroupStatsEndDate.yyyymmdd();
		
		$scope.productionReleaseGroupStatsSeries = [];
		$scope.productionReleaseGroupStatsData = [];
		$scope.productionReleaseGroupStatsLabels = [];
		
		$http.get("/stats/productionReleaseGroupStats?startDate=" + $scope.search.reqProductionReleaseGroupStatsStartDate + "&endDate=" + $scope.search.reqProductionReleaseGroupStatsEndDate + "&companySeq=" + $scope.search.productionReleaseSeq).success(
			function(data) {
				$scope.productionReleaseGroupStatsList = angular.fromJson(data);

				var amount = 0;
				var nonConfirmAmount = 0;
				var confirmAmount = 0;
				var completeAmount = 0;
				var disuseAmount = 0;
				
				angular.forEach($scope.productionReleaseGroupStatsList, function(value, key) {
					
					amount += $scope.productionReleaseGroupStatsList[key].amount;
					nonConfirmAmount += $scope.productionReleaseGroupStatsList[key].nonConfirmAmount;
					confirmAmount += $scope.productionReleaseGroupStatsList[key].confirmAmount;
					completeAmount += $scope.productionReleaseGroupStatsList[key].completeAmount;
					disuseAmount += $scope.productionReleaseGroupStatsList[key].disuseAmount;
				});
				
				$scope.productionReleaseGroupStatsData.push(nonConfirmAmount);
				$scope.productionReleaseGroupStatsData.push(confirmAmount);
				$scope.productionReleaseGroupStatsData.push(completeAmount);
				$scope.productionReleaseGroupStatsData.push(disuseAmount);
				
				$scope.productionReleaseGroupStatsLabels.push("미확정");
				$scope.productionReleaseGroupStatsLabels.push("확정완료");
				$scope.productionReleaseGroupStatsLabels.push("물류입고완료");
				$scope.productionReleaseGroupStatsLabels.push("폐기");
				
				
				if($scope.productionReleaseGroupStatsList.length > 0){
					
					$("#productionReleaseGroupStatsDiv").height(376);
					$scope.productionReleaseGroupStatsOptions.title.text = formatDate($scope.search.productionReleaseGroupStatsStartDate) + " ~ " + formatDate($scope.search.productionReleaseGroupStatsEndDate) + " 검색 수량 : " + numberWithCommas(amount);
					
				} else {
					
					$("#productionReleaseGroupStatsDiv").height(150);
					$scope.productionReleaseGroupStatsOptions.title.text = "";
				}
			}
		);
	};
	
	//물류차트 조회시 호출되는 함수
	$scope.distributionStorageGroupStatsHeadSearch = function(){
		
		if($scope.search.distributionStorageGroupStatsStartDate == undefined || $scope.search.distributionStorageGroupStatsStartDate == ""){
			return;
		}
		if($scope.search.distributionStorageGroupStatsEndDate == undefined || $scope.search.distributionStorageGroupStatsEndDate == ""){
			return;
		}

		$scope.search.reqDistributionStorageGroupStatsStartDate =  $scope.search.distributionStorageGroupStatsStartDate.yyyymmdd();
		$scope.search.reqDistributionStorageGroupStatsEndDate =  $scope.search.distributionStorageGroupStatsEndDate.yyyymmdd();
		
		$scope.distributionStorageGroupStatsSeries = [];
		$scope.distributionStorageGroupStatsData = [];
		$scope.distributionStorageGroupStatsData1 = [];
		$scope.distributionStorageGroupStatsData2 = [];
		$scope.distributionStorageGroupStatsData3 = [];
		$scope.distributionStorageGroupStatsData4 = [];
		$scope.distributionStorageGroupStatsLabels = [];
		$scope.distributionStorageGroupStatsDatasetOverride = [];
	
		$http.get("/stats/distributionStorageCompanyGroupStats?startDate="+$scope.search.reqDistributionStorageGroupStatsStartDate+"&endDate="+$scope.search.reqDistributionStorageGroupStatsEndDate+"&searchType="+$scope.search.distributionStorageStatsSearchGroup).success(
			function(data){
					$scope.distributionStorageGroupStatsList = angular.fromJson(data);
					var amount = 0;
					var searchType = "박스"; //검 색 기준에 따른 결과 타이틀 변경을 위한 변수
				    if($scope.search.distributionStorageStatsSearchGroup=="tag") searchType = "태그";
				    else if($scope.search.distributionStorageStatsSearchGroup=="style") searchType = "스타일";
					
					angular.forEach($scope.distributionStorageGroupStatsList, function(value, key){
						
						amount = amount + $scope.distributionStorageGroupStatsList[key].totalAmount;
						
						$scope.distributionStorageGroupStatsData1.push($scope.distributionStorageGroupStatsList[key].totalAmount);
						$scope.distributionStorageGroupStatsData2.push($scope.distributionStorageGroupStatsList[key].confirmAmount);
						$scope.distributionStorageGroupStatsData3.push($scope.distributionStorageGroupStatsList[key].completeAmount);
						$scope.distributionStorageGroupStatsData4.push($scope.distributionStorageGroupStatsList[key].companySeq);					
						$scope.distributionStorageGroupStatsLabels.push($scope.distributionStorageGroupStatsList[key].companyName);
	
					});
					
					$scope.distributionStorageGroupStatsDatasetOverride.push({
						label: "confirmAmount",
						backgroundColor: "rgb(255, 99, 132)",
						borderWidth: 0
					});
					
					$scope.distributionStorageGroupStatsDatasetOverride.push({
						label: "completeAmount",
						backgroundColor: "rgb(54, 162, 235)",
						borderWidth: 0
					});
					
					$scope.distributionStorageGroupStatsData.push($scope.distributionStorageGroupStatsData2);
					$scope.distributionStorageGroupStatsData.push($scope.distributionStorageGroupStatsData3);
					
					if($scope.distributionStorageGroupStatsList.length > 0){
						
						var multiNum = 0;
						
						if($scope.distributionStorageGroupStatsList.length == 1){
							multiNum = 300;
						} else if($scope.distributionStorageGroupStatsList.length < 10 && $scope.distributionStorageGroupStatsList.length > 1){
							multiNum = 60;
						} else {
							multiNum = 30;
						}
						
						$("#distributionStorageGroupStatsDiv").height($scope.distributionStorageGroupStatsList.length * multiNum);
						$scope.distributionStorageGroupStatsOptions.title.text = formatDate($scope.search.distributionStorageGroupStatsStartDate) + " ~ " + formatDate($scope.search.distributionStorageGroupStatsEndDate) + " "+ searchType + " 검색 수량 : " + numberWithCommas(amount);
					}else{ 
						$("#distributionStorageGroupStatsDiv").height(0); 
						//데이터 없을때 차트 노출되는 현상 막기위해 설정
					}
				}
			);
		
	};
	
	//매장차트 조회시 호출되는 함수
	$scope.storeStorageCompanyGroupStatsHeadSearch = function(){
		
		if($scope.search.storeStorageCompanyGroupStatsStartDate == undefined || $scope.search.storeStorageCompanyGroupStatsStartDate == ""){
			return;
		}
		if($scope.search.storeStorageCompanyGroupStatsEndDate == undefined || $scope.search.storeStorageCompanyGroupStatsEndDate == ""){
			return;
		}

		$scope.search.reqStoreStorageCompanyGroupStatsStartDate =  $scope.search.storeStorageCompanyGroupStatsStartDate.yyyymmdd();
		$scope.search.reqStoreStorageCompanyGroupStatsEndDate =  $scope.search.storeStorageCompanyGroupStatsEndDate.yyyymmdd();
		
		$scope.storeStorageCompanyGroupStatsSeries = [];
		$scope.storeStorageCompanyGroupStatsData = [];
		$scope.storeStorageCompanyGroupStatsData1 = [];
		$scope.storeStorageCompanyGroupStatsData2 = [];
		$scope.storeStorageCompanyGroupStatsData3 = [];
		$scope.storeStorageCompanyGroupStatsData4 = [];
		$scope.storeStorageCompanyGroupStatsLabels = [];
		$scope.storeStorageCompanyGroupStatsDatasetOverride = [];
	
		$http.get("/stats/storeStorageCompanyGroupStats?startDate="+$scope.search.reqStoreStorageCompanyGroupStatsStartDate+"&endDate="+$scope.search.reqStoreStorageCompanyGroupStatsEndDate+"&searchType="+$scope.search.storeStorageCompanyGroupStatsSearchGroup).success(
			function(data){
					$scope.storeStorageCompanyGroupStatsList = angular.fromJson(data);
					var amount = 0;
					var searchType = "박스"; //검 색 기준에 따른 결과 타이틀 변경을 위한 변수
				    if($scope.search.storeStorageCompanyGroupStatsSearchGroup=="tag") searchType = "태그";
				    else if($scope.search.storeStorageCompanyGroupStatsSearchGroup=="style") searchType = "스타일";
					
					angular.forEach($scope.storeStorageCompanyGroupStatsList, function(value, key){
						
						amount = amount + $scope.storeStorageCompanyGroupStatsList[key].totalAmount;
							
						$scope.storeStorageCompanyGroupStatsData1.push($scope.storeStorageCompanyGroupStatsList[key].totalAmount);
						$scope.storeStorageCompanyGroupStatsData2.push($scope.storeStorageCompanyGroupStatsList[key].releaseAmount);
						$scope.storeStorageCompanyGroupStatsData3.push($scope.storeStorageCompanyGroupStatsList[key].completeAmount);
						$scope.storeStorageCompanyGroupStatsData4.push($scope.storeStorageCompanyGroupStatsList[key].companySeq);					
						$scope.storeStorageCompanyGroupStatsLabels.push($scope.storeStorageCompanyGroupStatsList[key].companyName);
	
					});
					
					$scope.storeStorageCompanyGroupStatsDatasetOverride.push({
						label: "releaseAmount",
						backgroundColor: "rgb(255, 99, 132)",
						borderWidth: 0
					});
					
					$scope.storeStorageCompanyGroupStatsDatasetOverride.push({
						label: "completeAmount",
						backgroundColor: "rgb(54, 162, 235)",
						borderWidth: 0
					});
					
					$scope.storeStorageCompanyGroupStatsData.push($scope.storeStorageCompanyGroupStatsData2);
					$scope.storeStorageCompanyGroupStatsData.push($scope.storeStorageCompanyGroupStatsData3);
					
					if($scope.storeStorageCompanyGroupStatsList.length > 0){
						
						var multiNum = 0;
						
						if($scope.storeStorageCompanyGroupStatsList.length == 1){
							multiNum = 300;
						} else if($scope.storeStorageCompanyGroupStatsList.length < 10 && $scope.storeStorageCompanyGroupStatsList.length > 1){
							multiNum = 60;
						} else {
							multiNum = 30;
						}
						
						$("#storeStorageCompanyGroupStatsDiv").height($scope.storeStorageCompanyGroupStatsList.length * multiNum);
						$scope.storeStorageCompanyGroupStatsOptions.title.text = formatDate($scope.search.storeStorageCompanyGroupStatsStartDate) + " ~ " + formatDate($scope.search.storeStorageCompanyGroupStatsEndDate) + " "+ searchType + " 검색 수량 : " + numberWithCommas(amount);
					}else{ 
						$("#storeStorageCompanyGroupStatsDiv").height(0); 
						//데이터 없을때 차트 노출되는 현상 막기위해 설정
					}
				}
			);
		
	};
	
	$scope.companyStyle();
	
	$scope.showTab = function(flag){
		$scope.currentTab = flag;
		
		if(flag == 'bartagChart'){
			$scope.dailyGroupStatsHeadSearch();
			$scope.companyGroupStatsHeadSearch();
			$scope.companyStyleGroupStatsHeadSearch();
			$scope.bartagPieGroupStatsHeadSearch();
		} else if(flag == 'productionChart'){
			$scope.dailyBartagOrderGroupStatsHeadSearch();
			$scope.productionStyleGroupStatsHeadSearch();
			$scope.productionReleaseGroupStatsHeadSearch();
		//물류차트 탭 클릭시 조회된 화면 노출되도록
		} else if(flag == 'distributionChart'){
			$scope.distributionStorageGroupStatsHeadSearch();
			$scope.search.distributionStorageStatsSearchGroup = "box";
		//매장차트 탭 클릭시 조회된 화면 노출되도록
		} else if(flag == 'storeChart'){
			$scope.storeStorageCompanyGroupStatsHeadSearch();
			$scope.search.storeStorageCompanyGroupStatsSearchGroup = "box";
		}; 
	};
	
	$scope.customFilter = function (flag) {
	    return function (item) {
	    	if(flag == 'productYy'){
	    		if (item.flag == 'productYy')
		        {
		            return true;
		        }
		        return false;
	    	} else if(flag == 'season'){
	    		if (item.flag == 'season')
		        {
		            return true;
		        }
	    		return false;
	    	} else if(flag == 'style'){
	    		if (item.flag == 'style')
		        {
		            return true;
		        }
	    		return false;
	    	} else if(flag == 'size'){
	    		if (item.flag == 'size')
		        {
		            return true;
		        }
	    		return false;
	    	}
	    };
	};
	
	$scope.customSeasonFilter = function(obj){
	    if(obj == 'A'){
	    	return "SEASONLESS";
	    } else if(obj == 'P'){
	    	return "SPRING";
	    } else if(obj == 'M'){
	    	return "SUMMER";
	    } else if(obj == 'F'){
	    	return "FALL";
	    } else if(obj == 'W'){
	    	return "WINTER";
	    }
	}
	
	$scope.$watch(function(){
		
		return $rootScope.userRole;
		
		}, function() {
			
			if($rootScope.userRole != undefined){
				
				if($rootScope.userRole == 'production'){
					$scope.showTab('productionChart');
					
				} else if($rootScope.userRole == 'sales' || $rootScope.userRole == 'special'){
					$scope.showTab('storeChart');
					
				} else if($rootScope.userRole == 'distribution'){
					$scope.showTab('distributionChart');
					
				} else {
					$scope.showTab('bartagChart');
				}
			} 
	}, true);
}]);

app.controller('searchPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', '$uibModalInstance', 'obj', '$uibModal',
	function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, obj, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = {
		searchType : "boxBarcode"
	};
	
	$http.get('/search/select/style').success(
		function(data) {
			$scope.styleList = angular.fromJson(data);
		}	
	);
	
	
	$scope.headSearch = function(){
		
		if($scope.search.searchType == "rfidTag" && ($scope.search.style == undefined || $scope.search.style == "")){
			modalOpen($uibModal, "스타일을 입력해주세요.");
			return;
		}
		
		if($scope.search.searchType == "rfidTag" && ($scope.search.color == undefined || $scope.search.color == "")){
			modalOpen($uibModal, "컬러를 입력해주세요.");
			return;
		}
		
		if($scope.search.searchType == "rfidTag" && ($scope.search.styleSize == undefined || $scope.search.styleSize == "")){
			modalOpen($uibModal, "사이즈를 입력해주세요.");
			return;
		}
		
		if($scope.search.text == undefined || $scope.search.text == ""){
			modalOpen($uibModal, "검색어를 입력해주세요.");
			return;
		}
		
		if($scope.search.searchType == "boxBarcode" && $scope.search.text.length < 10){
			modalOpen($uibModal, "최소 10자리 이상 입력해주세요.");
			return;
		}
		
		if($scope.search.searchType == "rfidTag" && $scope.search.text.length < 3){
			modalOpen($uibModal, "최소 3자리 이상 입력해주세요.");
			return;
		}
		
		var param = generateParam($scope.search);
		
		$http.get('/search/searchAll/' + $scope.search.text + param).success(
			function(data) {
				$scope.data = angular.fromJson(data);
				
				if($scope.search.searchType == "rfidTag" && $scope.data.rfidTagList.length > 0){
					$scope.search.tempStyle = angular.copy($scope.search.style);
					$scope.search.tempColor = angular.copy($scope.search.color);
					$scope.search.tempSize = angular.copy($scope.search.styleSize);
				}
			}
		);
	};
	
	$scope.getTotalAmount = function(obj){
	    var totalAmount = 0;
	    
	    angular.forEach(obj.storageScheduleDetailLog, function(value, key) {
	    	totalAmount += obj.storageScheduleDetailLog[key].amount;
		});
	    return totalAmount;
	}
	
	$scope.searchDetail = function(obj){
		detailPopOpen($uibModal, obj, "/main/searchDetailPop", "searchDetailPopController", "xlg");
	}
	
	$scope.ok = function () {
	    $uibModalInstance.close();
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
	$scope.selectItem = function(item, flag){
		
		if(flag == "productYy"){
			$scope.init(flag, item);
			$scope.search.productYy = item.data;
			$scope.search.urlFlag = "productSeason";
		} else if(flag == "productSeason"){
			$scope.init(flag, item);
			$scope.search.productSeason = item.data;
			$scope.search.urlFlag = "style";
		} else if(flag == "style"){
			$scope.init(flag, item);
			$scope.search.style = item.data;
			$scope.search.urlFlag = "color";
		} else if(flag == "color"){
			$scope.init(flag, item);
			$scope.search.color = item.data;
			$scope.search.urlFlag = "size";
		} else if(flag == "styleSize"){
			$scope.init(flag, item);
			$scope.search.styleSize = item.data;
			$scope.search.urlFlag = "orderDegree";
		} 
		
		param = generateParam($scope.search);
		
		$http.get('/search/select/' + $scope.search.urlFlag + param).success(
			function(data) {
				if(flag == "productYy"){
					$scope.productSeasonList = angular.fromJson(data);
				} else if(flag == "productSeason"){
					$scope.styleList = angular.fromJson(data);
				} else if(flag == "style"){
					$scope.colorList = angular.fromJson(data);
				} else if(flag == "color"){
					$scope.sizeList = angular.fromJson(data);
				} else if(flag == "styleSize"){
					$scope.orderDegreeList = angular.fromJson(data);
				} 
			}	
		);
	};
	
	$scope.init = function(flag, item){
		
		if(flag == "all"){
			$scope.search.productSeason = null;
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
		} else if(flag == "productYy" && $scope.search.productYy != item.data){
			$scope.search.productSeason = null;
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
		} else if(flag == "productSeason" && $scope.search.productSeason != item.data){
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
		} else if(flag == "style" && $scope.search.style != item.data){
			$scope.search.color = null;
			$scope.search.styleSize = null;
		} else if(flag == "color" && $scope.search.color != item.data){
			$scope.search.styleSize = null;
		} 
	};
}]);

app.controller('searchDetailPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', '$uibModalInstance', 'obj', '$uibModal',
	function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, obj, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$http({
		method : 'POST',
		url : '/search/searchRfidTag',
		data : obj,
		headers : {
			'Content-Type' : 'application/json; charset=utf-8'
		}
	}).success(function(data, status, headers, config) {
		$scope.data = angular.fromJson(data);
	}).error(function(data, status, headers, config) {
		modalOpen($uibModal,  "에러발생");
	});
	
	$scope.ok = function () {
	    $uibModalInstance.close();
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);
