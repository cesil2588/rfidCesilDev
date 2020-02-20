app.controller('storageScheduleGroupListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', 'FileSaver','$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, FileSaver, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.allCheckFlag = false;
	
	$scope.search = initSearchCustom(angular.fromJson($window.sessionStorage.getItem("groupCurrent")));
	
	$scope.startDate = initDate($scope.search.startDate);
	$scope.endDate = initDate($scope.search.endDate);
	
	if($scope.search.defaultDate == undefined){
		$scope.search.defaultDate = "1";
	}
	
	if($scope.search.size == undefined){  $scope.search.size = "10"; }
	
	var param = generateParam($scope.search);
	
	$http.get('/company/getCompanyList').success(
			function(data) {
				$scope.companyList = angular.fromJson(data);
					
				angular.forEach($scope.companyList, function(value, key) {
					if($scope.companyList[key].companySeq == $scope.search.companySeq){
						$scope.search.companyInfo = $scope.companyList[key];
					}
				});
				
				if($scope.search.companyInfo == undefined){
					$scope.search.companySeq = 0;
				} else {
					$scope.search.companySeq = $scope.search.companyInfo.companySeq;
				}
				
				param = generateParam($scope.search);
				
				httpGetList("/distribution/storageScheduleGroup", param, $scope, $http, true, true);
				
				$http.get('/distribution/storageSchedule/countAll' + param).success(
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
		
		httpGetList("/distribution/storageScheduleGroup", param, $scope, $http, true, true);
		
		$http.get('/distribution/storageSchedule/countAll' + param).success(
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
		
		httpGetList("/distribution/storageScheduleGroup", param, $scope, $http, true, true);
		
		$http.get('/distribution/storageSchedule/countAll' + param).success(
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
		
		httpGetList("/distribution/storageScheduleGroup", param, $scope, $http, true, true);
		
		$http.get('/distribution/storageSchedule/countAll' + param).success(
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
		
		$scope.allCheckFlag = false;
		
		httpGetList("/distribution/storageScheduleGroup", param, $scope, $http, true, true);
		
		$http.get('/distribution/storageSchedule/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
		});
	};
	
	$scope.detail = function(obj){
		
		$window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch($scope.search)));
		$window.sessionStorage.removeItem("current");
		
		$location.url("/distribution/storageScheduleList?arrivalDate=" + obj.arrivalDate + "&companySeq=" + obj.startCompanySeq);
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
		
		httpGetList("/distribution/storageScheduleGroup", param, $scope, $http, true, true);
		
		$http.get('/distribution/storageSchedule/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
		});
	};
	
	/** 
	 * 물류입고처리
	 */
	$scope.storageComplete = function(){
		
		var count = 0;
		var checkList = [];
		var completeCheck = false;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				
				if($scope.list[key].batchPercent == "100"){
					completeCheck = true;
				}
				count ++;
				
				var obj = {
					arrivalDate : $scope.list[key].arrivalDate,
					companySeq	: $scope.list[key].companySeq
				}
				
				checkList.push(obj);
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 입고예정 작업이 없습니다.");
			return;
		}
		
		if(completeCheck){
			modalOpen($uibModal,  "입고작업이 완료된 작업이 있습니다.");
			return;
		}
		
		confirmOpen($uibModal, "선택된 입고예정 작업을 입고 하시겠습니까?", function(b){
    		
			if(b){
				
				$http({
					method : 'POST',
					url : "/distribution/storeScheduleCompleteGroup?userSeq=" + $rootScope.user.principal.userSeq + "&type=1",
					data : checkList
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
				    	modalOpen($uibModal,  "선택된 입고예정박스 입고 완료되었습니다.", function() {
				    		
				    		httpGetList("/distribution/storageScheduleGroup", param, $scope, $http, true, true);
				    		
				    		$http.get('/distribution/storageSchedule/countAll' + param).success(
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
	
	/** 
	 * 물류입고반송
	 */
	$scope.storageReturn = function(){
		
		var count = 0;
		var checkList = [];
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				
				count ++;
				
				checkList.push($scope.list[key]);
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 입고예정 작업이 없습니다.");
			return;
		}
		
		confirmOpen($uibModal, "선택된 입고예정 작업을 입고 반송 하시겠습니까?", function(b){
    		
			if(b){
				
				$http({
					method : 'POST',
					url : "/distribution/storeScheduleReturnGroup?userSeq=" + $rootScope.user.principal.userSeq + "&type=1",
					data : checkList
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
				    	modalOpen($uibModal,  "선택된 입고예정박스 입고 반송 완료되었습니다.", function() {
				    		
				    		httpGetList("/distribution/storageScheduleGroup", param, $scope, $http, true, true);
				    		
				    		$http.get('/distribution/storageSchedule/countAll' + param).success(
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
	
	//해당월 입고내역 다운로드
	$scope.completeBoxInfoExcelDownload = function(){
		$http({
	    	method : 'GET',
	    	url : '/distribution/distributionExcel/completeBoxInfo',
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
	            //defaultFileName = defaultFileName.replace(/[<>:"\/\\|?*]+/g, '_');
	            defaultFileName = defaultFileName + "월_입고 내역 다운로드.xls";
	    		var file = new Blob([data], { type: "application/vnd.ms-excel;charset=utf-8" });
	    		FileSaver.saveAs(file, defaultFileName);
	    	} else {
	    		modalOpen($uibModal,  "에러 발생");
	    	}
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
	};
}]);

app.controller('storageScheduleListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$routeParams', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $routeParams, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.allCheckFlag = false;
	
	$scope.search = initSearch("boxNum", "storageScheduleLogSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	$scope.search.arrivalDate = $routeParams.arrivalDate;
	
	if($scope.search.completeYn == undefined){
		$scope.search.completeYn = "all";
	}
	
	if($scope.search.size == undefined){  $scope.search.size = "10"; }
	
	var param;
	
	$scope.search.arrivalDate = $routeParams.arrivalDate;
	$scope.search.companySeq = $routeParams.companySeq;
	
	param = generateParam($scope.search);
	
	httpGetList("/distribution/storageSchedule", param, $scope, $http, true);
	
	$http.get('/distribution/storage/select/style' + param).success(
		function(data) {
			$scope.styleList = angular.fromJson(data);
		}	
	);
	
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
		
		httpGetList("/distribution/storageSchedule", param, $scope, $http);
	    	
//		$http.get('/distribution/storageSchedule/countAll' + param).success(
//				function(data) {
//					$scope.countAll = angular.fromJson(data);
//			});
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
		
		httpGetList("/distribution/storageSchedule", param, $scope, $http);
		
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
		
		httpGetList("/distribution/storageSchedule", param, $scope, $http);
		
//		$http.get('/distribution/storageSchedule/countAll' + param).success(
//				function(data) {
//					$scope.countAll = angular.fromJson(data);
//			});
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

	/** 상단 검색 */
	$scope.headSearch = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/distribution/storageSchedule", param, $scope, $http);
		
	};
	
	$scope.detail = function(obj){
		
		 detailPopOpen($uibModal, obj, "/distribution/storageScheduleDetailPop", "storageScheduleDetailPopController", "xxlg");
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
		
		httpGetList("/distribution/storageSchedule", param, $scope, $http);
		
//		$http.get('/distribution/storageSchedule/countAll' + param).success(
//			function(data) {
//				$scope.countAll = angular.fromJson(data);
//		});
	};
	
	/**
	 * 물류입고 개별 처리
	 */
	$scope.storageComplete = function(){
		
		var count = 0;
		var checkList = [];
		var completeCheck = false;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				
				if($scope.list[key].completeYn == "Y"){
					completeCheck = true;
				}
				
				count ++;
				
				var obj = {
					barcode : $scope.list[key].boxInfo.barcode
				}
				
				checkList.push(obj);
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 입고예정 박스가 없습니다.");
			return;
		}
		
		if(completeCheck){
			modalOpen($uibModal,  "이미 입고 완료된 박스가 있습니다.");
			return;
		}
		
		confirmOpen($uibModal, "선택된 입고예정박스 입고 하시겠습니까?", function(b){
    		
			if(b){
				
				$http({
					method : 'POST',
					url : "/distribution/storeScheduleComplete?userSeq=" + $rootScope.user.principal.userSeq + "&type=1",
					data : checkList
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
				    	modalOpen($uibModal,  "선택된 입고예정박스 입고 완료되었습니다.", function() {
				    		
				    		httpGetList("/distribution/storageSchedule", param, $scope, $http);
				    		
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
	 * 물류입고반송 개별 처리
	 */
	$scope.storageReturn = function(){
		
		var count = 0;
		var checkList = [];
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				
				count ++;
				
				checkList.push($scope.list[key]);
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 입고예정 박스가 없습니다.");
			return;
		}
		
		confirmOpen($uibModal, "선택된 입고예정박스 입고 반송 하시겠습니까?", function(b){
    		
			if(b){
				
				$http({
					method : 'POST',
					url : "/distribution/storeScheduleReturn?userSeq=" + $rootScope.user.principal.userSeq + "&type=1",
					data : checkList
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
				    	modalOpen($uibModal,  "선택된 입고예정박스 입고 반송 완료되었습니다.", function() {
				    		
				    		httpGetList("/distribution/storageSchedule", param, $scope, $http);
				    		
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
		
		$http.get('/distribution/storage/select/' + $scope.search.urlFlag + param).success(
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
}]);

app.controller('storageScheduleDetailPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', '$uibModal', 'FileSaver', 'Blob', '$uibModalInstance', 'obj', 
	function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModal, FileSaver, Blob, $uibModalInstance, obj) {

	var seq = obj.storageScheduleLogSeq;
	
	$scope.data = obj;
	
	$scope.ajaxFinish = true;
	
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

//반품 입고 예정 정보 조회
app.controller('storageReturnScheduleGroupListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', 'FileSaver', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, FileSaver, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.allCheckFlag = false;
	
	$scope.search = initSearchCustom(angular.fromJson($window.sessionStorage.getItem("groupCurrent")));
	
	$scope.startDate = initDate($scope.search.startDate);
	$scope.endDate = initDate($scope.search.endDate);
	
	if($scope.search.defaultDate == undefined){
		$scope.search.defaultDate = "1";
	}
	
	if($scope.search.size == undefined){  $scope.search.size = "10"; }
	
	var param = generateParam($scope.search);
	
	$http.get('/company/getCompanyList').success(
			function(data) {
				$scope.companyList = angular.fromJson(data);
					
				angular.forEach($scope.companyList, function(value, key) {
					if($scope.companyList[key].companySeq == $scope.search.companySeq){
						$scope.search.companyInfo = $scope.companyList[key];
					}
				});
				
				if($scope.search.companyInfo == undefined){
					$scope.search.companySeq = 0;
				} else {
					$scope.search.companySeq = $scope.search.companyInfo.companySeq;
				}
				
				param = generateParam($scope.search);
				
				httpGetList("/distribution/storageReturnScheduleGroup", param, $scope, $http, true, true);
				
				$http.get('/distribution/storageReturnSchedule/countAll' + param).success(
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
		
		httpGetList("/distribution/storageReturnScheduleGroup", param, $scope, $http, true, true);
		
		$http.get('/distribution/storageReturnSchedule/countAll' + param).success(
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
		
		httpGetList("/distribution/storageReturnScheduleGroup", param, $scope, $http, true, true);
		
		$http.get('/distribution/storageReturnSchedule/countAll' + param).success(
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
		
		httpGetList("/distribution/storageReturnScheduleGroup", param, $scope, $http, true, true);
		
		$http.get('/distribution/storageReturnSchedule/countAll' + param).success(
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
		
		$scope.allCheckFlag = false;
		
		httpGetList("/distribution/storageReturnScheduleGroup", param, $scope, $http, true, true);
		
		$http.get('/distribution/storageReturnSchedule/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
		});
	};
	
	$scope.detail = function(obj){
		
		$window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch($scope.search)));
		$window.sessionStorage.removeItem("current");
		
		$location.url("/distribution/storageReturnScheduleList?arrivalDate=" + obj.arrivalDate + "&companySeq=" + obj.startCompanySeq);
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
		
		httpGetList("/distribution/storageReturnScheduleGroup", param, $scope, $http, true, true);
		
		$http.get('/distribution/storageReturnSchedule/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
		});
	};
	
	//해당월 반품 내역 다운로드
	$scope.completeReturnBoxInfoExcelDownload = function(){	
		$http({
	    	method : 'GET',
	    	url : '/distribution/distributionExcel/completeReturnBoxInfo',
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
	            //defaultFileName = defaultFileName.replace(/[<>:"\/\\|?*]+/g, '_');
	            defaultFileName = defaultFileName + "월_반품 내역 다운로드.xls";
	    		var file = new Blob([data], { type: "application/vnd.ms-excel;charset=utf-8" });
	    		FileSaver.saveAs(file, defaultFileName);
	    	} else {
	    		//modalOpen($uibModal,  "에러 발생");
	    		modalOpen($uibModal,  "반품 내역이 존재하지 않습니다");
	    	}
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
	};
	
}]);

//반품 입고 예정정보 상세
app.controller('storageReturnScheduleListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$routeParams', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $routeParams, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.allCheckFlag = false;
	
	$scope.search = initSearch("boxNum", "storageScheduleLogSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	$scope.search.arrivalDate = $routeParams.arrivalDate;
	
	if($scope.search.completeYn == undefined){
		$scope.search.completeYn = "all";
	}
	
	if($scope.search.size == undefined){  $scope.search.size = "10"; }
	
	var param;
	
	$scope.search.arrivalDate = $routeParams.arrivalDate;
	$scope.search.companySeq = $routeParams.companySeq;
	
	param = generateParam($scope.search);
	
	httpGetList("/distribution/storageReturnSchedule", param, $scope, $http, true);
	
	$http.get('/distribution/storage/select/style' + param).success(
		function(data) {
			$scope.styleList = angular.fromJson(data);
		}	
	);
		
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
		
		httpGetList("/distribution/storageReturnSchedule", param, $scope, $http);
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
		
		httpGetList("/distribution/storageReturnSchedule", param, $scope, $http);
		
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
		
		httpGetList("/distribution/storageReturnSchedule", param, $scope, $http);

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

	/** 상단 검색 */
	$scope.headSearch = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/distribution/storageReturnSchedule", param, $scope, $http);
		
	};
	
	$scope.detail = function(obj){
		 detailPopOpen($uibModal, obj, "/distribution/storageReturnScheduleDetailPop", "storageReturnScheduleDetailPopController", "xxlg");
	};
	
		
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/distribution/storageReturnSchedule", param, $scope, $http);

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
		
		$http.get('/distribution/storage/select/' + $scope.search.urlFlag + param).success(
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

}]);

app.controller('storageReturnScheduleDetailPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', '$uibModal', 'FileSaver', 'Blob', '$uibModalInstance', 'obj',
	function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModal, FileSaver, Blob, $uibModalInstance, obj) {

	var barcode = obj.boxInfo.barcode;
	
	$http.get("/distribution/storageReturnDetailByBarcode/" + barcode).success(	
		
		function(data) {
			$scope.data = angular.fromJson(data.obj);
	});
		
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);