app.controller('productionReleaseGroupListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', 'FileSaver', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, FileSaver, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.allCheckFlag = false;
	
	$scope.search = initSearchCustom(angular.fromJson($window.sessionStorage.getItem("groupCurrent")));
	
	$scope.startDate = initDate($scope.search.startDate);
	$scope.endDate = initDate($scope.search.endDate);
	
	if($scope.search.defaultDate == undefined){
		$scope.search.defaultDate = "1";
	}
	
	if($scope.search.confirmYn == undefined){
		$scope.search.confirmYn = "all";
	}
	
	if($scope.search.size == undefined){  $scope.search.size = "10"; }
	
	var param;
	
	$http.get('/company/getCompanyList').success(
		function(data) {
			$scope.companyList = angular.fromJson(data);
				
			angular.forEach($scope.companyList, function(value, key) {
				if($scope.companyList[key].companySeq == $scope.search.companySeq){
					$scope.search.companyInfo = $scope.companyList[key];
				}
			});
			
			if($rootScope.userRole == 'production'){
				$scope.search.companyInfo = $rootScope.user.principal.companyInfo;
			}
			
			if($scope.search.companyInfo == undefined){
				$scope.search.companySeq = 0;
			} else {
				$scope.search.companySeq = $scope.search.companyInfo.companySeq;
			}
			
			param = generateParam($scope.search);
			
			httpGetList("/production/releaseGroup", param, $scope, $http, true);
			
			$http.get('/production/releaseGroup/countAll' + param).success(
					function(data) {
						$scope.countAll = angular.fromJson(data);
				});
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
		
		$scope.allCheckFlag = false;
		
		httpGetList("/production/releaseGroup", param, $scope, $http);
	    	
		$http.get('/production/releaseGroup/countAll' + param).success(
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
		
		$scope.allCheckFlag = false;
		
		httpGetList("/production/releaseGroup", param, $scope, $http);
		
		$http.get('/production/releaseGroup/countAll' + param).success(
				function(data) {
					$scope.countAll = angular.fromJson(data);
			});
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/production/releaseGroup", param, $scope, $http);
		
		$http.get('/production/releaseGroup/countAll' + param).success(
				function(data) {
					$scope.countAll = angular.fromJson(data);
			});
	};
	
	$scope.format = 'yyyyMMdd';
	
	/*
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
	*/
	
	$scope.dateOptions = {
		formatYear: 'yy',
		maxDate: new Date(2020, 5, 22),
		minDate: new Date(2017, 1, 1),
		startingDay: 1
	};
	
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
		
		$scope.allCheckFlag = false;
		
		httpGetList("/production/releaseGroup", param, $scope, $http);
		
		$http.get('/production/releaseGroup/countAll' + param).success(
				function(data) {
					$scope.countAll = angular.fromJson(data);
			});
	};
	
	$scope.detail = function(obj){
		
		$scope.search.companySeq = obj.startCompanySeq;
		
		$window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch($scope.search)));
		$window.sessionStorage.removeItem("current");
		
		$location.url("/production/productionReleaseList?createDate=" + obj.createDate + "&workLine=" + obj.workLine + "&companySeq=" + obj.startCompanySeq);
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
		
		httpGetList("/production/releaseGroup", param, $scope, $http);
		
		$http.get('/production/releaseGroup/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
		});
	};
	
	/** 출고삭제 */
	$scope.confirmDel = function(){
		
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
			modalOpen($uibModal,  "출고 작업을 선택해주세요.");
	    	return;
		}
		
		if(confirmCheck){
			modalOpen($uibModal,  "출고 확정된 작업이 있습니다.");
	    	return;
		}
		
		if(completeCheck){
			modalOpen($uibModal,  "출고 완료된 작업이 있습니다.");
	    	return;
		}
		
		confirmOpen($uibModal, "출고 작업을 삭제 하시겠습니까?", function(b){
			
			if(b){
				
				$http({
			    	method : 'DELETE',
			    	url : '/production/releaseGroup/',
			    	data : objList,
			    	headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
			    }).success(function(data, status, headers, config){
			    	if(data){
			    		modalOpen($uibModal,  "출고 작업이 삭제 되었습니다.", function() {
			    			
			    			httpGetList("/production/releaseGroup", param, $scope, $http);
			    			
			    			$http.get('/production/releaseGroup/countAll' + param).success(
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
	
	/** 출고확정 */
	$scope.confirmComplete = function(){
		
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
			modalOpen($uibModal,  "출고 작업을 선택해주세요.");
	    	return;
		}
		
		if(confirmCheck){
			modalOpen($uibModal,  "출고 확정된 작업이 있습니다.");
	    	return;
		}
		
		if(completeCheck){
			modalOpen($uibModal,  "출고 완료된 작업이 있습니다.");
	    	return;
		}
		
		var releaseGroupConfirmModalInstance = $uibModal.open({
	    	animation: true,
	    	ariaLabelledBy: 'modal-title',
	    	ariaDescribedBy: 'modal-body',
	    	templateUrl: '/customTemplate/customReleaseGroupConfirm',
	    	controller: 'releaseGroupConfirmPopupController',
	    	size: 'xxlg',
	    	resolve: {
	    		releaseGroupList: function () {
	    			return objList;
	    		},
	    		type : function(){
	    			return "01";
	    		}
	    	}
	    });
		releaseGroupConfirmModalInstance.result.then(function () {
//	        $ctrl.selected = selectedItem;
			
			httpGetList("/production/releaseGroup", param, $scope, $http);
			
			$http.get('/production/releaseGroup/countAll' + param).success(
					function(data) {
						$scope.countAll = angular.fromJson(data);
				});
			
			$scope.allCheckFlag = false;
	    }, function () {
//	        $log.info('Modal dismissed at: ' + new Date());
	    });
	};
	
	$scope.excelDownload = function(){
		
		var checkflag = false;
		var checkList = new Array();
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				checkList.push($scope.list[key]);
				checkflag = true;
			}
		});
		
		if(!checkflag){
			modalOpen($uibModal,  "선택된 출고 작업이 없습니다.");
			return;
		}
		
		$http({
	    	method : 'POST',
	    	data : checkList,
	    	url : '/production/release/groupExcelDownload',
	    	responseType: 'arraybuffer'
	    }).success(function(data, status, headers, config){
	    	if(data){
	    		var type = headers('Content-Type');
	            var disposition = headers('Content-Disposition');
	            if (disposition) {
	                var match = disposition.match(/.*filename=\"?([^;\"]+)\"?.*/);
	                if (match[1])
	                    defaultFileName = match[1];
	            }
	            defaultFileName = defaultFileName.replace(/[<>:"\/\\|?*]+/g, '_');
	            defaultFileName = defaultFileName + ".xls";
	            
	    		var file = new Blob([data], { type: "application/vnd.ms-excel;charset=charset=utf-8" });
	    		FileSaver.saveAs(file, defaultFileName);
	    		
	    		$scope.allCheckFlag = false;
	    		
	    	} else {
	    		modalOpen($uibModal,  "에러 발생");
	    	}
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
	}
}]);

app.controller('productionReleaseListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$routeParams', 'FileSaver', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $routeParams, FileSaver, $uibModal) {
	
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
	
//	$scope.search.createDate = $routeParams.createDate;
//	$scope.search.workLine = $routeParams.workLine;
//	$scope.search.companySeq = $routeParams.companySeq;
	if($scope.search.size == undefined){  $scope.search.size = "10"; }
	
	var param;
	
	$http.get('/company/getCompanyList').success(
		function(data) {
			$scope.companyList = angular.fromJson(data);
				
			angular.forEach($scope.companyList, function(value, key) {
				if($scope.companyList[key].companySeq == $scope.search.companySeq){
					$scope.search.companyInfo = $scope.companyList[key];
				}
			});
			
			if($rootScope.userRole == 'production'){
				$scope.search.companyInfo = $rootScope.user.principal.companyInfo;
			}
			
			if($scope.search.companyInfo == undefined){
				$scope.search.companySeq = 0;
			} else {
				$scope.search.companySeq = $scope.search.companyInfo.companySeq;
			}
			
			param = generateParam($scope.search);
			
			httpGetList("/production/release", param, $scope, $http, true);
			
			$http.get('/production/release/select/style' + param).success(
				function(data) {
					$scope.styleList = angular.fromJson(data);
				}	
			);
			
			$http.get('/production/release/countAll' + param).success(
				function(data) {
					$scope.countAll = angular.fromJson(data);
			});
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
		
		httpGetList("/production/release", param, $scope, $http);
	    	
		$http.get('/production/release/countAll' + param).success(
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
		
		httpGetList("/production/release", param, $scope, $http);
		
		$http.get('/production/release/countAll' + param).success(
				function(data) {
					$scope.countAll = angular.fromJson(data);
			});
		
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/production/release", param, $scope, $http);
		
		$http.get('/production/release/countAll' + param).success(
				function(data) {
					$scope.countAll = angular.fromJson(data);
			});
	};
	
	$scope.format = 'yyyyMMdd';
	
	$scope.dateOptions = {
		formatYear: 'yy',
		maxDate: new Date(2020, 5, 22),
		minDate: new Date(2017, 1, 1),
		startingDay: 1
	};
	
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
		
		httpGetList("/production/release", param, $scope, $http);
		
		$http.get('/production/release/countAll' + param).success(
				function(data) {
					$scope.countAll = angular.fromJson(data);
			});
	};
	
	$scope.detail = function(obj){
		
//		$window.sessionStorage.setItem("current", angular.toJson(setSearch($scope.search)));
//		$window.sessionStorage.removeItem("subCurrent");
//		
//		$location.url("/production/productionReleaseDetail?seq=" + obj.storageScheduleLogSeq);
		
		detailPopOpen($uibModal, obj, "/production/productionReleaseDetailPop", "productionReleaseDetailPopController", "xxlg");

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
		
		httpGetList("/production/release", param, $scope, $http);
		
		$http.get('/production/release/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
		});
	};
	
	/** 
	 * 출고 요청 작업 확정
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
			modalOpen($uibModal,  "선택된 출고 요청이 없습니다.");
			return;
		}
		
		if(confirmFlag){
			modalOpen($uibModal,  "출고 확정된 작업이 있습니다.");
			return;
		}
		
		if(completeFlag){
			modalOpen($uibModal,  "출고 완료된 작업이 있습니다.");
			return;
		}
		
		var releaseConfirmModalInstance = $uibModal.open({
	    	animation: true,
	    	ariaLabelledBy: 'modal-title',
	    	ariaDescribedBy: 'modal-body',
	    	templateUrl: '/customTemplate/customReleaseConfirm',
	    	controller: 'releaseConfirmPopupController',
	    	size: 'xlg',
	    	resolve: {
	    		releaseList: function () {
	    			return checkList;
	    		},
	    		type: function(){
	    			return "01";
	    		}
	    	}
	    });
		releaseConfirmModalInstance.result.then(function () {
//	        $ctrl.selected = selectedItem;
			
			httpGetList("/production/release", param, $scope, $http);
			
			$scope.allCheckFlag = false;
	    }, function () {
//	        $log.info('Modal dismissed at: ' + new Date());
	    });
	};
	
	/** 
	 * 출고 요청 작업 삭제
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
			modalOpen($uibModal,  "선택된 출고 요청이 없습니다.");
			return;
		}
		
		if(confirmFlag){
			modalOpen($uibModal,  "출고 확정된 작업이 있습니다.");
			return;
		}
		
		if(completeFlag){
			modalOpen($uibModal,  "출고 완료된 작업이 있습니다.");
			return;
		}
		
		confirmOpen($uibModal, "선택된 출고 요청을 삭제 하시겠습니까?", function(b){
    		
			if(b){
				
				$http({
					method : 'DELETE',
					url : "/production/release",
					data : checkList,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
				    	modalOpen($uibModal,  "선택된 출고 요청을 삭제 완료되었습니다.", function() {
				    		
				    		httpGetList("/production/release", param, $scope, $http);
				    		
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
	
	/** 
	 * 출고 확정 작업 삭제
	 */
	$scope.deleteConfirm = function(){
		
		var count = 0;
		var checkList = [];
		var confirmFlag = false;
		var completeFlag = false;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				count ++;
				
				checkList.push($scope.list[key]);
				
				if($scope.list[key].completeYn == "Y"){
					completeFlag = true;
				}
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 출고 요청이 없습니다.");
			return;
		}
		
		if(completeFlag){
			modalOpen($uibModal,  "출고 완료된 작업이 있습니다.");
			return;
		}
		
		confirmOpen($uibModal, "선택된 출고 요청을 삭제 하시겠습니까?", function(b){
    		
			if(b){
				
				$http({
					method : 'DELETE',
					url : "/production/releaseConfirm",
					data : checkList,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
				    	modalOpen($uibModal,  "선택된 출고 요청을 삭제 완료되었습니다.", function() {
				    		
				    		httpGetList("/production/release", param, $scope, $http);
				    		
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
		
		$http.get('/production/release/select/' + $scope.search.urlFlag + param).success(
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
	
	$scope.getTotalAmount = function(obj){
	    var totalAmount = 0;
	    
	    angular.forEach(obj.storageScheduleDetailLog, function(value, key) {
	    	totalAmount += obj.storageScheduleDetailLog[key].amount;
		});
	    return totalAmount;
	}
	
	$scope.excelDownload = function(){
		
		var checkflag = false;
		var checkList = new Array();
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				checkList.push($scope.list[key]);
				checkflag = true;
			}
		});
		
		if(!checkflag){
			modalOpen($uibModal,  "선택된 출고가 없습니다.");
			return;
		}
		
		$http({
	    	method : 'POST',
	    	data : checkList,
	    	url : '/production/release/excelDownload',
	    	responseType: 'arraybuffer'
	    }).success(function(data, status, headers, config){
	    	if(data){
	    		var type = headers('Content-Type');
	            var disposition = headers('Content-Disposition');
	            if (disposition) {
	                var match = disposition.match(/.*filename=\"?([^;\"]+)\"?.*/);
	                if (match[1])
	                    defaultFileName = match[1];
	            }
	            defaultFileName = defaultFileName.replace(/[<>:"\/\\|?*]+/g, '_');
	            defaultFileName = defaultFileName + ".xls";
	            
	    		var file = new Blob([data], { type: "application/vnd.ms-excel;charset=charset=utf-8" });
	    		FileSaver.saveAs(file, defaultFileName);
	    		
	    		$scope.allCheckFlag = false;
	    		
	    	} else {
	    		modalOpen($uibModal,  "에러 발생");
	    	}
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
	}
}]);

app.controller('productionReleaseDetailPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', '$uibModal', 'FileSaver', 'Blob', '$uibModalInstance', 'obj',
	function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModal, FileSaver, Blob, $uibModalInstance, obj) {

	$scope.ajaxFinish = false;
	
	var seq = obj.storageScheduleLogSeq;
	
	$http.get('/production/release/' + seq).success(
		function(data) {
			$scope.data = angular.fromJson(data);
			$scope.ajaxFinish = true;
	});
	
	$scope.detail = function(style){
		$scope.currentStyle = style;
	};
	
	$scope.getTotalAmount = function(obj){
		
		if(obj == undefined){
			return;
		}
		
	    var totalAmount = 0;
	    
	    angular.forEach(obj.storageScheduleDetailLog, function(value, key) {
	    	totalAmount += obj.storageScheduleDetailLog[key].amount;
		});
	    return totalAmount;
	}
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);