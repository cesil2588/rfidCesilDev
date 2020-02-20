app.controller('inventoryScheduleGroupListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = initSearchCustom(angular.fromJson($window.sessionStorage.getItem("groupCurrent")));
	
	$scope.startDate = initDate($scope.search.startDate);
	$scope.endDate = initDate($scope.search.endDate);
	
	if($scope.search.defaultDate == undefined){
		$scope.search.defaultDate = "1";
	}
	
	if($scope.search.size == undefined){  
		$scope.search.size = "10"; 
	}
	
	var param;
	
	$http.get('/company/getCompanyList').success(
			function(data) {
				$scope.tempCompanyList = angular.fromJson(data);
					
				$scope.companyList = [];
				
				angular.forEach($scope.tempCompanyList, function(value, key) {
					if($scope.tempCompanyList[key].type == '5' || $scope.tempCompanyList[key].type == '6'){
						$scope.companyList.push($scope.tempCompanyList[key]);
					}
				});
				
				if($rootScope.userRole == 'sales' || $rootScope.userRole == 'special'){
					$scope.search.companyInfo = $rootScope.user.principal.companyInfo;
				}
				
				if($scope.search.companyInfo == undefined){
					$scope.search.companySeq = 0;
				} else {
					$scope.search.companySeq = $scope.search.companyInfo.companySeq;
				}
				
				param = generateParam($scope.search);
				
				httpGetList("/inventorySchedule/inventoryGroup", param, $scope, $http, true, true);
			}
		);
	
	var settingDate = new Date();	
	
	if($scope.endDate == ""){
		var today = new Date();
		$scope.endDate = today;
	}
	
	if($scope.startDate == ""){
		settingDate.setMonth(settingDate.getMonth()-1);
		$scope.startDate = settingDate;
	}
	
	$scope.changeSearchDate = function(){
		settingDate = new Date($scope.endDate);		
		
		if($scope.search.defaultDate == "1"){
			settingDate.setMonth(settingDate.getMonth()-1);
			$scope.startDate = settingDate;
		} else if($scope.search.defaultDate == "2"){
			settingDate.setMonth(settingDate.getMonth()-3);
			$scope.startDate = settingDate;
		} else if($scope.search.defaultDate == "3"){
			settingDate.setMonth(settingDate.getMonth()-6);
			$scope.startDate = settingDate;
		} else {
			settingDate.setYear(settingDate.getFullYear()-1);
			$scope.startDate = settingDate;
		}
	}
	
	$scope.goPage = function(page){
			
		if($scope.current == page){
	    	return;
	    }
		
		if($scope.end < page){
			return;
		}
		
		if(page == 0){
			return;
		}

		$scope.search.page = page - 1;
		
		param = generateParam($scope.search);
		
		httpGetList("/inventorySchedule/inventoryGroup", param, $scope, $http, true, true);
	};
	
	$scope.clickSearch = function(){
		
		if($scope.search.text == undefined){
			$scope.search.text = "";
		}
		
		if($scope.search.text == ""){
			return;
		}
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/inventorySchedule/inventoryGroup", param, $scope, $http, true, true);
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/inventorySchedule/inventoryGroup", param, $scope, $http, true, true);
	};
	
	$scope.format = 'yyyyMMdd';
	
	if($rootScope.userRole == 'sales' || $rootScope.userRole == 'special'){
		var minDate = new Date();
		minDate.setYear(settingDate.getFullYear()-1);
		$scope.dateOptions = {
			formatYear: 'yy',
			maxDate: new Date(),
			minDate: minDate ,
			startingDay: 1
		};
	} else {
		$scope.dateOptions = {
			formatYear: 'yy',
			maxDate: new Date(2020, 5, 22),
			minDate: new Date(2017, 1, 1),
			startingDay: 1
		};
	}
	
	
	$scope.startDateOpen = function() {
	    $scope.search.startDateOpened = true;
	};
	
	$scope.endDateOpen = function() {
	    $scope.search.endDateOpened = true;
	};

	/** 상단 검색 */
	$scope.headSearch = function(){
		
		if($scope.startDate == undefined || $scope.startDate == ""){
			return;
		}
		
		if($scope.endDate == undefined || $scope.endDate == ""){
			return;
		}
		
		if($scope.search.companyInfo == undefined){
			$scope.search.companySeq = 0;
		} else {
			$scope.search.companySeq = $scope.search.companyInfo.companySeq;
		}
		
		$scope.search.startDate =  $scope.startDate.yyyymmdd();
		$scope.search.endDate =  $scope.endDate.yyyymmdd();
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/inventorySchedule/inventoryGroup", param, $scope, $http, true, true);
	};
	
	$scope.detail = function(obj){
		
		$scope.search.companySeq = obj.companySeq;
		
		$window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch($scope.search)));
		$window.sessionStorage.removeItem("current");
		
		$location.url("/inventorySchedule/inventoryScheduleList?createDate=" + obj.createDate + "&companySeq=" + obj.companySeq);
	};
	
	/** CheckBox */
	$scope.allCheck = function(){
		angular.forEach($scope.list, function(value, key) {
			if($scope.allCheckFlag){
				$scope.list[key].check = true;
			} else {
				$scope.list[key].check = false;
			}
		});
	};
	
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/inventorySchedule/inventoryGroup", param, $scope, $http, true, true);
	};
}]);

app.controller('inventoryScheduleListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$routeParams', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $routeParams, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("boxNum", "inventoryScheduleHeaderSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	if($scope.search.defaultDate == undefined){
		$scope.search.defaultDate = "1";
	}
	
	if($scope.search.confirmYn == undefined){
		$scope.search.confirmYn = "all";
	}
	
	if($scope.search.completeYn == undefined){
		$scope.search.completeYn = "all";
	}
	
	if($scope.search.disuseYn == undefined){
		$scope.search.disuseYn = "all";
	}
	
	$scope.search.createDate = $routeParams.createDate;
	$scope.search.companySeq = $routeParams.companySeq;
	if($scope.search.size == undefined){  $scope.search.size = "10"; }
	
	var param;
	
	param = generateParam($scope.search);
	
	httpGetList("/inventorySchedule", param, $scope, $http, true);
	
	$scope.goPage = function(page){
			
		if($scope.current == page){
	    	return;
	    }
		
		if($scope.end < page){
			return;
		}
		
		if(page == 0){
			return;
		}

		$scope.search.page = page - 1;
		
		param = generateParam($scope.search);
		
		httpGetList("/inventorySchedule", param, $scope, $http, true);
		
	};
	
	$scope.clickSearch = function(){
		
		if($scope.search.text == undefined){
			$scope.search.text = "";
		}
		
		if($scope.search.text == ""){
			return;
		}
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/inventorySchedule", param, $scope, $http, true);
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/inventorySchedule", param, $scope, $http, true);
		
	};
	
	/** 상단 검색 */
	$scope.headSearch = function(){
		
		if($scope.search.companyInfo == undefined){
			$scope.search.companySeq = 0;
		} else {
			$scope.search.companySeq = $scope.search.companyInfo.companySeq;
		}
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/inventorySchedule", param, $scope, $http, true);
	};
	
	$scope.detail = function(obj){
		
//		$window.sessionStorage.setItem("current", angular.toJson(setSearch($scope.search)));
//		$window.sessionStorage.removeItem("subCurrent");
//		
//		$location.url("/inventorySchedule/inventoryScheduleDetail?seq=" + obj.inventoryScheduleHeaderSeq);
		
		detailPopOpen($uibModal, obj, "/inventorySchedule/inventoryScheduleDetailPop", "inventoryScheduleDetailPopController", "xxlg");
	};
	
	/** CheckBox */
	$scope.allCheck = function(){
		angular.forEach($scope.list, function(value, key) {
			if($scope.allCheckFlag){
				$scope.list[key].check = true;
			} else {
				$scope.list[key].check = false;
			}
		});
	};
	
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/inventorySchedule", param, $scope, $http, true);
		
	};
	
	$scope.getTotalStyleAmount = function(obj){
			
			if(obj == undefined){
				return;
			}
			
		    var totalAmount = 0;
		    
		    angular.forEach(obj.styleList, function(value, key) {
		    	totalAmount ++;
			});
		    return totalAmount;
		}
	
	$scope.getTotalTagAmount = function(obj){
		
		if(obj == undefined){
			return;
		}
		
	    var totalAmount = 0;
	    
	    angular.forEach(obj.styleList, function(value, key) {
	    	totalAmount += obj.styleList[key].amount;
		});
	    return totalAmount;
	}
}]);

app.controller('inventoryScheduleDetailPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', '$uibModal', 'FileSaver', 'Blob', '$uibModalInstance', 'obj',
	function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModal, FileSaver, Blob, $uibModalInstance, obj) {

	$scope.obj = obj;
	
	$scope.detail = function(style){
		$scope.currentStyle = style;
	};
	
	$scope.getTotalStyleAmount = function(obj){
		
		if(obj == undefined){
			return;
		}
		
	    var totalAmount = 0;
	    
	    angular.forEach(obj.styleList, function(value, key) {
	    	totalAmount ++;
		});
	    return totalAmount;
	}

	$scope.getTotalTagAmount = function(obj){
		
		if(obj == undefined){
			return;
		}
		
	    var totalAmount = 0;
	    
	    angular.forEach(obj.styleList, function(value, key) {
	    	totalAmount += obj.styleList[key].amount;
		});
	    return totalAmount;
	}
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);