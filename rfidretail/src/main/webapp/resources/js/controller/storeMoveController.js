app.controller('storeMoveGroupListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', '$routeParams', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal, $routeParams) {
	
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
	
	$scope.search.flag = $routeParams.flag;
	
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
				
				if(($rootScope.userRole == 'sales' || $rootScope.userRole == 'special') && $scope.search.flag == 'start'){
					$scope.search.startCompanyInfo = $rootScope.user.principal.companyInfo;
				} else if(($rootScope.userRole == 'sales' || $rootScope.userRole == 'special') && $scope.search.flag == 'end'){
					$scope.search.endCompanyInfo = $rootScope.user.principal.companyInfo;
				}
				
				if($scope.search.startCompanyInfo == undefined){
					$scope.search.startCompanySeq = 0;
				} else {
					$scope.search.startCompanySeq = $scope.search.startCompanyInfo.companySeq;
				}
				
				if($scope.search.endCompanyInfo == undefined){
					$scope.search.endCompanySeq = 0;
				} else {
					$scope.search.endCompanySeq = $scope.search.endCompanyInfo.companySeq;
				}
				
				param = generateParam($scope.search);
				
				httpGetList("/store/storeMoveGroup", param, $scope, $http, true, true);
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
		
		httpGetList("/store/storeMoveGroup", param, $scope, $http, true, true);
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
		
		httpGetList("/store/storeMoveGroup", param, $scope, $http, true, true);
		
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/store/storeMoveGroup", param, $scope, $http, true, true);
		
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
		
		if($scope.search.startCompanyInfo == undefined){
			$scope.search.startCompanySeq = 0;
		} else {
			$scope.search.startCompanySeq = $scope.search.startCompanyInfo.companySeq;
		}
		
		if($scope.search.endCompanyInfo == undefined){
			$scope.search.endCompanySeq = 0;
		} else {
			$scope.search.endCompanySeq = $scope.search.endCompanyInfo.companySeq;
		}
		
		$scope.search.startDate = $scope.startDate.yyyymmdd();
		$scope.search.endDate = $scope.endDate.yyyymmdd();
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/store/storeMoveGroup", param, $scope, $http, true, true);
	};
	
	$scope.detail = function(obj){
		
		$scope.search.companySeq = obj.companySeq;
		
		$window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch($scope.search)));
		$window.sessionStorage.removeItem("current");
		
		$location.url("/store/storeMoveList?createDate=" + obj.createDate + "&startCompanySeq=" + obj.startCompanySeq + "&endCompanySeq=" + obj.endCompanySeq + "&companyType=all");
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
		
		httpGetList("/store/storeMoveGroup", param, $scope, $http, true, true);
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
			    	url : '/store/storeMoveGroup/',
			    	data : objList,
			    	headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
			    }).success(function(data, status, headers, config){
			    	if(data){
			    		modalOpen($uibModal,  "반품 작업이 삭제 되었습니다.", function() {
			    			
			    			httpGetList("/store/storeMoveGroup", param, $scope, $http, true);
			    			
			    			$http.get('/store/storeMoveGroup/countAll' + param).success(
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
	    	url : '/store/storeMoveGroupList',
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
				
				httpGetList("/store/storeMoveGroup", param, $scope, $http, true);
				
				$http.get('/store/storeMoveGroup/countAll' + param).success(
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

app.controller('storeMoveListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$routeParams', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $routeParams, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("boxNum", "storeMoveLogSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	if($scope.search.workYn == undefined){
		$scope.search.workYn = "all";
	}
	
	if($scope.search.confirmYn == undefined){
		$scope.search.confirmYn = "all";
	}
	
	if($scope.search.disuseYn == undefined){
		$scope.search.disuseYn = "all";
	}
	
	$scope.search.createDate = $routeParams.createDate;
	$scope.search.startCompanySeq = $routeParams.startCompanySeq;
	$scope.search.endcompanySeq = $routeParams.endCompanySeq;
	$scope.search.companyType = $routeParams.companyType;
	
	if($scope.search.size == undefined){  
		$scope.search.size = "10"; 
	}
	
	var param;
	
	param = generateParam($scope.search);
	
	httpGetList("/store/storeMove", param, $scope, $http, true);
	
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
		
		httpGetList("/store/storeMove", param, $scope, $http, true);
		
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
		
		httpGetList("/store/storeMove", param, $scope, $http, true);
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/store/storeMove", param, $scope, $http, true);
		
	};
	

	/** 상단 검색 */
	$scope.headSearch = function(){
		
		if($scope.search.companyInfo == undefined){
			$scope.search.companySeq = 0;
		} else {
			$scope.search.companySeq = $scope.search.companyInfo.companySeq;
		}
		
		$scope.search.startDate =  $scope.startDate.yyyymmdd();
		$scope.search.endDate =  $scope.endDate.yyyymmdd();
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/store/storeMove", param, $scope, $http, true);
		
	};
	
	$scope.detail = function(obj){
		
//		$window.sessionStorage.setItem("current", angular.toJson(setSearch($scope.search)));
//		$window.sessionStorage.removeItem("subCurrent");
//		
//		$location.url("/store/storeMoveDetail?seq=" + obj.storeMoveLogSeq);
		
		detailPopOpen($uibModal, obj, "/store/storeMoveDetailPop", "storeMoveDetailPopController", "xxlg");
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
		
		httpGetList("/store/storeMove", param, $scope, $http, true);
		
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
			
			httpGetList("/store/storeMove", param, $scope, $http, true);
			
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
				    		
				    		$http.get('/store/storeMove/countAll' + param).success(
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
	
	$scope.getTotalAmount = function(obj, flag){
	    
		var totalAmount = 0;
	    
	    if(flag == 'amount'){
	    	angular.forEach(obj.storeMoveDetailLog, function(value, key) {
		    	totalAmount += obj.storeMoveDetailLog[key].amount;
			});
	    } else {
	    	angular.forEach(obj.storeMoveDetailLog, function(value, key) {
		    	totalAmount += obj.storeMoveDetailLog[key].completeAmount;
			});
	    }
	    return totalAmount;
	}
}]);

app.controller('storeMoveDetailPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', '$uibModal', 'FileSaver', 'Blob', '$uibModalInstance', 'obj',
	function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModal, FileSaver, Blob, $uibModalInstance, obj) {

	$scope.obj = obj;
	
	$scope.detail = function(style){
		$scope.currentStyle = style;
	};
	
	$scope.getTotalAmount = function(obj, flag){
		
		if(obj == undefined){
			return;
		}
	    
		var totalAmount = 0;
	    
	    if(flag == 'amount'){
	    	angular.forEach(obj.storeMoveDetailLog, function(value, key) {
		    	totalAmount += obj.storeMoveDetailLog[key].amount;
			});
	    } else {
	    	angular.forEach(obj.storeMoveDetailLog, function(value, key) {
		    	totalAmount += obj.storeMoveDetailLog[key].completeAmount;
			});
	    }
	    return totalAmount;
	}
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);