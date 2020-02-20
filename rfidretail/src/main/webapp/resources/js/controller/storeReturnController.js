app.controller('storeReturnGroupListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal) {
	
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
				
				httpGetList("/store/returnGroup", param, $scope, $http, true, true);
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
		
		httpGetList("/store/returnGroup", param, $scope, $http, true, true);
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
		
		httpGetList("/store/returnGroup", param, $scope, $http, true, true);
		
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/store/returnGroup", param, $scope, $http, true, true);
		
	};
	
	$scope.format = 'yyyyMMdd';
	
	if($rootScope.userRole == 'production'){
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
		
		httpGetList("/store/returnGroup", param, $scope, $http, true, true);
	};
	
	$scope.detail = function(obj){
		
		$scope.search.companySeq = obj.companySeq;
		
		$window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch($scope.search)));
		$window.sessionStorage.removeItem("current");
		
		$location.url("/store/storeReturnList?createDate=" + obj.createDate + "&companySeq=" + obj.companySeq);
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
		
		httpGetList("/store/returnGroup", param, $scope, $http, true, true);
	};
	
	/** 반품삭제 */
	$scope.deleteGroup = function(){
		
		var objList = [];
		var confirmCheck = false;
		var completeCheck = false;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				objList.push($scope.list[key]);
				
				if($scope.list[key].confirmYn == 'Y'){
					confirmCheck = true;
				}
				
				if($scope.list[key].completeYn == 'Y'){
					completeCheck = true;
				}
			}
		});
		
		if(objList.length == 0){
			modalOpen($uibModal,  "반품 작업을 선택해주세요.");
	    	return;
		}
		
		if(confirmCheck){
			modalOpen($uibModal,  "반품 확정된 작업이 있습니다.");
	    	return;
		}
		
		if(completeCheck){
			modalOpen($uibModal,  "반품 완료된 작업이 있습니다.");
	    	return;
		}
		
		confirmOpen($uibModal, "반품 작업을 삭제 하시겠습니까?", function(b){
			
			if(b){
				
				$http({
			    	method : 'DELETE',
			    	url : '/store/returnGroup/',
			    	data : objList,
			    	headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
			    }).success(function(data, status, headers, config){
			    	if(data){
			    		modalOpen($uibModal,  "반품 작업이 삭제 되었습니다.", function() {
			    			
			    			httpGetList("/store/returnGroup", param, $scope, $http, true);
			    			
			    			$http.get('/store/returnGroup/countAll' + param).success(
			    					function(data) {
			    						$scope.countAll = angular.fromJson(data);
			    				});
			    			
			    			$scope.allCheckFlag = false;
						});
			    	} else {
			    		modalOpen($uibModal,  "에러 발생");
			    	}
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
			}
		});
		
	}
	
	/** 반품확정 */
	$scope.confirmGroup = function(){
		
		var objList = [];
		var confirmCheck = false;
		var completeCheck = false;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				objList.push($scope.list[key]);
				
				if($scope.list[key].confirmYn == 'Y'){
					confirmCheck = true;
				}
				
				if($scope.list[key].completeYn == 'Y'){
					completeCheck = true;
				}
			}
		});
		
		if(objList.length == 0){
			modalOpen($uibModal,  "반품 작업을 선택해주세요.");
	    	return;
		}
		
		if(confirmCheck){
			modalOpen($uibModal,  "반품 확정된 작업이 있습니다.");
	    	return;
		}
		
		if(completeCheck){
			modalOpen($uibModal,  "반품 완료된 작업이 있습니다.");
	    	return;
		}
		
		$http({
	    	method : 'POST',
	    	url : '/store/returnGroupList',
	    	data : objList,
	    	headers : {
				'Content-Type' : 'application/json; charset=utf-8'
			}
	    }).success(function(data, status, headers, config){
	    	
	    	var objDetailList = angular.fromJson(data);
	    	
	    	var releaseGroupConfirmModalInstance = $uibModal.open({
		    	animation: true,
		    	ariaLabelledBy: 'modal-title',
		    	ariaDescribedBy: 'modal-body',
		    	templateUrl: '/customTemplate/customReleaseGroupConfirm',
		    	controller: 'releaseGroupConfirmPopupController',
		    	size: 'xlg',
		    	resolve: {
		    		releaseGroupList: function () {
		    			return objDetailList;
		    		},
		    		type: function(){
		    			return "03";
		    		}
		    	}
		    });
			releaseGroupConfirmModalInstance.result.then(function () {
//		        $ctrl.selected = selectedItem;
				
				httpGetList("/store/returnGroup", param, $scope, $http, true);
				
				$http.get('/store/returnGroup/countAll' + param).success(
						function(data) {
							$scope.countAll = angular.fromJson(data);
					});
				
				$scope.allCheckFlag = false;
		    }, function () {
//		        $log.info('Modal dismissed at: ' + new Date());
		    });
	    	
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
	};
}]);

app.controller('storeReturnListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$routeParams', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $routeParams, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("boxNum", "storageScheduleLogSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	$scope.startDate = initDate($scope.search.startDate);
	$scope.endDate = initDate($scope.search.endDate);
	
	if($scope.search.defaultDate == undefined){
		$scope.search.defaultDate = "1";
	}
	
	if($scope.search.confirmYn == undefined){
		$scope.search.confirmYn = "all";
	}
	
	if($scope.search.completeYn == undefined){
		$scope.search.completeYn = "all";
	}
	
	$scope.search.createDate = $routeParams.createDate;
	$scope.search.workLine = $routeParams.workLine;
	$scope.search.companySeq = $routeParams.companySeq;
	if($scope.search.size == undefined){  $scope.search.size = "10"; }
	
	var param;
	
	param = generateParam($scope.search);
	
	httpGetList("/store/return", param, $scope, $http, true);
	
	$http.get('/store/return/countAll' + param).success(
		function(data) {
			$scope.countAll = angular.fromJson(data);
	});
	
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
		
		httpGetList("/store/return", param, $scope, $http, true);
		
		$http.get('/store/return/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
		});
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
		
		httpGetList("/store/return", param, $scope, $http, true);
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/store/return", param, $scope, $http, true);
		
		$http.get('/store/return/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
		});
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
		
		httpGetList("/store/return", param, $scope, $http, true);
		
		$http.get('/store/return/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
		});
	};
	
	$scope.detail = function(obj){
		
//		$window.sessionStorage.setItem("current", angular.toJson(setSearch($scope.search)));
//		$window.sessionStorage.removeItem("subCurrent");
//		
//		$location.url("/store/storeReturnDetail?seq=" + obj.storageScheduleLogSeq);
		
		detailPopOpen($uibModal, obj, "/store/storeReturnDetailPop", "storeReturnDetailPopController", "xxlg");
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
		
		httpGetList("/store/return", param, $scope, $http, true);
		
		$http.get('/store/return/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
		});
	};
	
	/** 
	 * 반품 요청 작업 확정
	 */
	$scope.confirm = function(){
		
		var count = 0;
		var checkList = [];
		var confirmFlag = false;
		var completeFlag = false;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				count ++;
				
				checkList.push($scope.list[key]);
				
				if($scope.list[key].confirmYn == "Y"){
					confirmFlag = true;
				}
				
				if($scope.list[key].completeYn == "Y"){
					completeFlag = true;
				}
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 반품 요청이 없습니다.");
			return;
		}
		
		if(confirmFlag){
			modalOpen($uibModal,  "반품 확정된 작업이 있습니다.");
			return;
		}
		
		if(completeFlag){
			modalOpen($uibModal,  "반품 완료된 작업이 있습니다.");
			return;
		}
		
		var releaseConfirmModalInstance = $uibModal.open({
	    	animation: true,
	    	ariaLabelledBy: 'modal-title',
	    	ariaDescribedBy: 'modal-body',
	    	templateUrl: '/customTemplate/customReturnConfirm',
	    	controller: 'releaseConfirmPopupController',
	    	size: 'xlg',
	    	resolve: {
	    		releaseList: function () {
	    			return checkList;
	    		},
	    		type: function(){
	    			return "03";
	    		}
	    	}
	    });
		releaseConfirmModalInstance.result.then(function () {
//	        $ctrl.selected = selectedItem;
			
			httpGetList("/store/return", param, $scope, $http, true);
			
			$http.get('/store/return/countAll' + param).success(
				function(data) {
					$scope.countAll = angular.fromJson(data);
			});
			
			$scope.allCheckFlag = false;
	    }, function () {
//	        $log.info('Modal dismissed at: ' + new Date());
	    });
	};
	
	/** 
	 * 반품 요청 작업 삭제
	 */
	$scope.delete = function(){
		
		var count = 0;
		var checkList = [];
		var confirmFlag = false;
		var completeFlag = false;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				count ++;
				
				checkList.push($scope.list[key]);
				
				if($scope.list[key].confirmYn == "Y"){
					confirmFlag = true;
				}
				
				if($scope.list[key].completeYn == "Y"){
					completeFlag = true;
				}
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 반품 요청이 없습니다.");
			return;
		}
		
		if(confirmFlag){
			modalOpen($uibModal,  "반품 확정된 작업이 있습니다.");
			return;
		}
		
		if(completeFlag){
			modalOpen($uibModal,  "반품 완료된 작업이 있습니다.");
			return;
		}
		
		confirmOpen($uibModal, "선택된 반품 요청을 삭제 하시겠습니까?", function(b){
    		
			if(b){
				
				$http({
					method : 'DELETE',
					url : "/store/return",
					data : checkList,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
				    	modalOpen($uibModal,  "선택된 반품 요청을 삭제 완료되었습니다.", function() {
				    		
				    		httpGetList("/store/return", param, $scope, $http, true);
				    		
				    		$http.get('/store/return/countAll' + param).success(
				    			function(data) {
				    				$scope.countAll = angular.fromJson(data);
				    		});
				    		
				    		$scope.allCheckFlag = false;
						});
					} else {
						modalOpen($uibModal,  angular.fromJson(data).resultMessage);
					}
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
			}
		});
		
	};
	
	$scope.getTotalAmount = function(obj){
	    var totalAmount = 0;
	    
	    angular.forEach(obj.storageScheduleDetailLog, function(value, key) {
	    	totalAmount += obj.storageScheduleDetailLog[key].amount;
		});
	    return totalAmount;
	}
}]);

app.controller('storeReturnDetailPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', '$uibModal', 'FileSaver', 'Blob', '$uibModalInstance', 'obj',
	function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModal, FileSaver, Blob, $uibModalInstance, obj) {

	$scope.data = obj;
	
	$scope.detail = function(style){
		$scope.currentStyle = style;
	};
	
	$scope.getTotalAmount = function(obj){
		
		if(obj == undefined){
			return;
		}
		
	    var totalAmount = 0;
	    
	    angular.forEach(obj.storageDetailLog, function(value, key) {
	    	totalAmount += obj.storageDetailLog[key].amount;
		});
	    return totalAmount;
	}
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);