app.controller('bartagOrderGroupListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = initSearchCustom(angular.fromJson($window.sessionStorage.getItem("groupCurrent")));
	
	$scope.search.option = "productYy";
	if($scope.search.size == undefined){  
		$scope.search.size = "10"; 
	}
	
	$scope.startDate = initDate($scope.search.startDate);
	$scope.endDate = initDate($scope.search.endDate);
	
	if($scope.search.defaultDate == undefined){
		$scope.search.defaultDate = "1";
	}
	
	if($scope.search.searchDate == undefined){
		$scope.search.searchDate = "regDate";
		$scope.search.searchDateFlag = "regDate";
	}
	
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
			
			httpGetList("/bartag/bartagOrderGroup", param, $scope, $http, true, true);
			//RFID 생산 작업 목록에서 스타일 검색 추가...생산 업체 요청...Cesil..2019/11/27
			$http.get('/bartag/bartagOrder/select/stylePerCom' + param).success(
				function(data) {
					$scope.styleList = angular.fromJson(data);
				}	
			);
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
		
		httpGetList("/bartag/bartagOrderGroup", param, $scope, $http, true, true);
	    	
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
		
		httpGetList("/bartag/bartagOrderGroup", param, $scope, $http, true, true);
		
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/bartag/bartagOrderGroup", param, $scope, $http, true, true);
		
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
				
		$scope.search.searchDateFlag = angular.copy($scope.search.searchDate);
		
		$scope.search.startDate =  $scope.startDate.yyyymmdd();
		$scope.search.endDate =  $scope.endDate.yyyymmdd();
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch($scope.search)));
		
		httpGetList("/bartag/bartagOrderGroup", param, $scope, $http, true, true);
		
	};
	
	$scope.detail = function(obj){
		
		$scope.search.companySeq = obj.companySeq;
		
		$window.sessionStorage.removeItem("current");
		
		var params = "?date=" + obj.date + "&companySeq=" + obj.companySeq + "&productYy=" + obj.productYy + "&productSeason=" + obj.productSeason + "&searchDate=" + $scope.search.searchDate;
		if($scope.search.style!= undefined && $scope.search.style != null && $scope.search.style != "")
			params = params + "&style=" + $scope.search.style;
		$location.url("/bartag/bartagOrderList"+ params);
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
		
		httpGetList("/bartag/bartagOrderGroup", param, $scope, $http, true, true);
	};
	
	$scope.selectItem = function(item, flag){
				if(flag == "style"){
			$scope.init(flag, item);
			$scope.search.style = item.data;
			$scope.search.urlFlag = "stylePerCom";
		}
				
		param = generateParam($scope.search);
		
		$http.get('/bartag/bartagOrder/select/' + $scope.search.urlFlag + param).success(
				function(data) {
					if(flag == "style"){
						$scope.styleList = angular.fromJson(data);
					} 
				}	
			);
	}
	
	$scope.init = function(flag, item){
		if(flag == "style" && $scope.search.style != item.data){
			$scope.search.style = null;
		}
	}
	
}]);

app.controller('bartagOrderListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$routeParams', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $routeParams, $uibModal) {
	
	$scope.ajaxFinish = false;
	$scope.allCheckFlag = false;
	
	$scope.search = initSearch("style", "updDate", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	if($scope.search.stat == undefined){
		$scope.search.stat = "all";
	}
	
	if($scope.search.size == undefined){  
		$scope.search.size = "10"; 
	}
	
	$scope.search.date = $routeParams.date;
	$scope.search.companySeq = $routeParams.companySeq;
	$scope.search.productYy = $routeParams.productYy;
	$scope.search.productSeason = $routeParams.productSeason;
	$scope.search.searchDate = $routeParams.searchDate;
	if($routeParams.style != undefined)
		$scope.search.style = $routeParams.style;
	
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
			
			httpGetList("/bartag/bartagOrder", param, $scope, $http, true);
			
			$http.get('/bartag/bartagOrder/select/style' + param).success(
				function(data) {
					$scope.styleList = angular.fromJson(data);
				}	
			);
			
			$http.get('/bartag/bartagOrder/subCompanyList?companySeq=' + $scope.search.companySeq).success(
				function(data) {
					$scope.subCompanyList = angular.fromJson(data);
				}	
			);
			
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
		$scope.search.subCompanyName = subCompany.value;
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/bartag/bartagOrder", param, $scope, $http, true);
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
		
		httpGetList("/bartag/bartagOrder", param, $scope, $http, true);
		
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
		
		httpGetList("/bartag/bartagOrder", param, $scope, $http, true);
	};
	
	/** 상단 검색 */
	$scope.headSearch = function(){
		
		$scope.search.page = 0;
		$scope.search.subCompanyName = subCompany.value;
		param = generateParam($scope.search);
		$scope.allCheckFlag = false;
		httpGetList("/bartag/bartagOrder", param, $scope, $http, true);
	};
	
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/bartag/bartagOrder", param, $scope, $http, true);
	};
	
	$scope.detail = function(obj){
		detailPopOpen($uibModal, obj, "/bartag/bartagOrderDetailPop", "bartagOrderDetailPopController", "xxlg");
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
		
		$http.get('/bartag/bartagOrder/select/' + $scope.search.urlFlag + param).success(
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
	
	$scope.getNewData = function(obj){
		
		var currentDate = new Date();
		currentDate.setDate(currentDate.getDate() - 1);
		
		if($scope.search.searchDate == "regDate"){
			if(obj.regDate > currentDate){
				return true;
			} else {
				return false;
			}
		} else if($scope.search.searchDate == "updDate"){
			
			if(obj.updDate > currentDate){
				return true;
			} else {
				return false;
			}
		}
	}
	
	// 생산 수량 입력
	$scope.completeAmountPop = function(){
		
		var objList = [];
		var completeAmountCheck = false;
		var completeCheck = false;
		var additionAmountCheck = false;
		var additionCheck = false;
		var additionCompleteCheck = false;
		var endCheck = false;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				objList.push($scope.list[key]);
				
				if($scope.list[key].stat == '2'){
					completeAmountCheck = true;
				}
				
				if($scope.list[key].stat == '3'){
					completeCheck = true;
				}
				
				if($scope.list[key].stat == '4'){
					additionCheck = true;
				}
				
				if($scope.list[key].stat == '5'){
					additionCompleteCheck = true;
				}

				if($scope.list[key].stat == '6'){
					endCheck = true;
				}
			}
		});
		
		if(objList.length == 0){
			modalOpen($uibModal,  "RFID 생산 스타일을 선택해주세요.");
	    	return;
		}
		
		if(completeAmountCheck){
			modalOpen($uibModal,  "RFID 생산 수량이 입력된 스타일이 있습니다.");
	    	return;
		}
		
		if(completeCheck){
			modalOpen($uibModal,  "RFID 생산 수량이 확정된 스타일이 있습니다.");
	    	return;
		}
		
		if(additionCheck){
			modalOpen($uibModal,  "RFID 추가 수량이 입력된 스타일이 있습니다.");
	    	return;
		}
		
		if(additionCompleteCheck){
			modalOpen($uibModal,  "RFID 추가 생산이 확정된 스타일이 있습니다.");
	    	return;
		}
		
		if(endCheck){
			modalOpen($uibModal,  "RFID 생산 종결된 스타일이 있습니다.");
	    	return;
		}
		
		var bartagOrderCompleteModalInstance = $uibModal.open({
	    	animation: true,
	    	ariaLabelledBy: 'modal-title',
	    	ariaDescribedBy: 'modal-body',
	    	templateUrl: '/customTemplate/customBartagOrderComplete',
	    	controller: 'bartagOrderCompletePopupController',
	    	size: 'xlg',
	    	resolve: {
	    		bartagOrderList: function () {
	    			return objList;
	    		},
	    		flag : function(){
	    			return "list" 
	    		}
	    	}
	    });
		bartagOrderCompleteModalInstance.result.then(function () {
	        
			httpGetList("/bartag/bartagOrder", param, $scope, $http, true);
			$scope.allCheckFlag = false;
			
	    }, function () {
//	        $log.info('Modal dismissed at: ' + new Date());
	    });
	}
	
	// 추가 수량 입력
	$scope.additionAmountPop = function(){
		
		var objList = [];
		var nonCompleteCheck = false;
		var completeAmountCheck = false;
		var additionCheck = false;
		var endCheck = false;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				objList.push($scope.list[key]);
				
				if($scope.list[key].stat == '1'){
					nonCompleteCheck = true;
				}
				
				if($scope.list[key].stat == '2'){
					completeAmountCheck = true;
				}
				
				if($scope.list[key].stat == '4'){
					additionCheck = true;
				}
				
				if($scope.list[key].stat == '6'){
					endCheck = true;
				}
				
			}
		});
		
		if(objList.length == 0){
			modalOpen($uibModal,  "RFID 생산 스타일을 선택해주세요.");
	    	return;
		}
		
		if(nonCompleteCheck){
			modalOpen($uibModal,  "RFID 생산 수량이 입력 안된 스타일이 있습니다.");
	    	return;
		}
		
		if(completeAmountCheck){
			modalOpen($uibModal,  "RFID 생산 수량이 입력된 스타일이 있습니다.");
	    	return;
		}
		
		if(additionCheck){
			modalOpen($uibModal,  "RFID 추가 수량이 입력된 스타일이 있습니다.");
	    	return;
		}
		
		if(endCheck){
			modalOpen($uibModal,  "RFID 생산 요청 종료된 스타일이 있습니다.");
	    	return;
		}
		
		var bartagOrderAddtionModalInstance = $uibModal.open({
	    	animation: true,
	    	ariaLabelledBy: 'modal-title',
	    	ariaDescribedBy: 'modal-body',
	    	templateUrl: '/customTemplate/customBartagOrderAddition',
	    	controller: 'bartagOrderAdditionPopupController',
	    	size: 'xlg',
	    	resolve: {
	    		bartagOrderList: function () {
	    			return objList;
	    		},
	    		flag : function(){
	    			return "list" 
	    		}
	    	}
	    });
		bartagOrderAddtionModalInstance.result.then(function () {
	        
			httpGetList("/bartag/bartagOrder", param, $scope, $http, true);
			$scope.allCheckFlag = false;
			
	    }, function () {
//	        $log.info('Modal dismissed at: ' + new Date());
	    });
	};
	
	// 확정 처리
	$scope.complete = function(){
		var objList = [];
		
		var completeAmountCheck = false;
		var completeCheck = false;
		var additionCompleteCheck = false;
		var endCheck = false;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				objList.push($scope.list[key]);
				
				if($scope.list[key].stat == '1'){
					completeAmountCheck = true;
				}
				
				if($scope.list[key].stat == '3'){
					completeCheck = true;
				}
				
				if($scope.list[key].stat == '5'){
					additionCompleteCheck = true;
				}

				if($scope.list[key].stat == '6'){
					endCheck = true;
				}
			}
		});
		
		if(objList.length == 0){
			modalOpen($uibModal,  "RFID 생산 스타일을 선택해주세요.");
	    	return;
		}
		
		if(completeAmountCheck){
			modalOpen($uibModal,  "RFID 생산 수량이 입력 안된 스타일이 있습니다.");
	    	return;
		}
		
		if(completeCheck){
			modalOpen($uibModal,  "RFID 생산 수량이 확정된 스타일이 있습니다.");
	    	return;
		}
		
		if(additionCompleteCheck){
			modalOpen($uibModal,  "RFID 추가 생산이 확정된 스타일이 있습니다.");
	    	return;
		}
		
		if(endCheck){
			modalOpen($uibModal,  "RFID 생산 종결된 스타일이 있습니다.");
	    	return;
		}
		
		confirmOpen($uibModal, "선택된 스타일 확정 완료 하시겠습니까?", function(b){
			
			if(b){
				$http({
					method : 'POST',
					url : "/bartag/bartagOrder",
					data : objList,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config){
			    	if(angular.fromJson(data).resultCode == '1000'){
			    		modalOpen($uibModal,  "선택된 스타일이 확정 완료 되었습니다.", function() {
			    			
			    			httpGetList("/bartag/bartagOrder", param, $scope, $http, true);
			    			$scope.allCheckFlag = false;
						});
			    		
					} else if(angular.fromJson(data).resultCode == '3002'){
						modalOpen($uibModal,  "로그인 세션이 만료되었습니다.");
					} else if(angular.fromJson(data).resultCode == '3014'){
						modalOpen($uibModal,  "찾을 수 없는 바택 정보가 있습니다.");
					} else if(angular.fromJson(data).resultCode == '3028'){
						modalOpen($uibModal,  "이미 발행 시작된 바택 정보가 있습니다.");
					}
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
			}
		});
	};
}]);

app.controller('bartagOrderDetailPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', '$uibModal', 'FileSaver', 'Blob', '$uibModalInstance', 'obj', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModal, FileSaver, Blob, $uibModalInstance, obj) {

	$scope.ajaxFinish = false;
	
	$scope.search = {
		size : "10"
	}
	
	var param;
	
	var seq = obj.bartagOrderSeq;
	
	$http.get('/bartag/bartagOrder/' + seq).success(
		function(data) {
			$scope.data = angular.fromJson(data);
			
			$scope.current = angular.fromJson(data).bartagList.number + 1;
			$scope.begin = Math.max(1, $scope.current - 5);
			$scope.end = Math.min($scope.begin + 9, angular.fromJson(data).bartagList.totalPages);
					
			$scope.total = angular.fromJson(data).bartagList.totalElements;
			
			$scope.ajaxFinish = true;
	});
	
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
		
		$http.get('/bartag/bartagOrder/' + seq + param).success(
			function(data) {
				$scope.data = angular.fromJson(data);
						
				$scope.current = angular.fromJson(data).bartagList.number + 1;
				$scope.begin = Math.max(1, $scope.current - 5);
				$scope.end = Math.min($scope.begin + 9, angular.fromJson(data).bartagList.totalPages);
								
				$scope.total = angular.fromJson(data).bartagList.totalElements;
		});
	};
	
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		$http.get('/bartag/bartagOrder/' + seq + param).success(
			function(data) {
				$scope.data = angular.fromJson(data);
					
				$scope.current = angular.fromJson(data).bartagList.number + 1;
				$scope.begin = Math.max(1, $scope.current - 5);
				$scope.end = Math.min($scope.begin + 9, angular.fromJson(data).bartagList.totalPages);
							
				$scope.total = angular.fromJson(data).bartagList.totalElements;
		});
	};
	
	$scope.detail = function(obj){
		
//		$window.sessionStorage.setItem("current", angular.toJson(setSearch($scope.search)));
//		$window.sessionStorage.removeItem("subCurrent");
//		
//		$location.url("/bartag/bartagDetail?seq=" + obj.bartagSeq);
		
		detailPopOpen($uibModal, obj, "/bartag/bartagDetailPop", "bartagDetailPopController", "xxlg");
	};
	
	// 바택 삭제
	$scope.deleteBartag = function(){
		
		var objList = [];
		var statCheck = false;
		
		angular.forEach($scope.data.bartagList.content, function(value, key) {
			if($scope.data.bartagList.content[key].check){
				objList.push($scope.data.bartagList.content[key]);
				
				if($scope.data.bartagList.content[key].stat != '0'){
					statCheck = true;
				}
			}
		});
		
		if(objList.length == 0){
			modalOpen($uibModal,  "바택 정보를 선택해주세요.");
	    	return;
		}
		
		if(statCheck){
			modalOpen($uibModal,  "미확정인 바택정보만 삭제할 수 있습니다.");
	    	return;
		}
		
		var additionCheck = true;
		var maxAddtionOrderDegree = $scope.data.bartagOrder.additionOrderDegree;
		var additionOrderList = [];
		
		for(var i=0; i<objList.length; i++){
			if(objList[i].additionOrderDegree == maxAddtionOrderDegree){
				additionCheck = false;
			}
			additionOrderList.push(objList[i].additionOrderDegree);
		}
		
		if(additionCheck){
			modalOpen($uibModal,  "마지막 바택 정보가 포함되어 있어야 합니다.");
	    	return;
		}
		
		additionOrderList.sort(function(a, b) {
		    return b - a;
		});
		
		var orderCount = additionOrderList[0];
		var orderCheck = false;
		
		for(var j=0; j<additionOrderList.length; j++){
			if(additionOrderList[j] != orderCount){
				orderCheck = true;
			}
			orderCount --;
		}
		
		if(orderCheck){
			modalOpen($uibModal,  "마지막 바택 정보를 포함하여 뒤에서부터 순서대로 선택되어야 합니다.");
	    	return;
		}
		
		confirmOpen($uibModal, "선택된 바택 정보를 삭제 하시겠습니까?", function(b){
			
			if(b){
				
				$http({
					method : 'DELETE',
					url : "/bartag",
					data : objList,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config){
			    	if(angular.fromJson(data).resultCode == '1000'){
			    		modalOpen($uibModal,  "선택된 바택 정보가 삭제되었습니다.", function() {
			    			
			    			$http.get('/bartag/bartagOrder/' + seq).success(
					    		function(data) {
					    				$scope.data = angular.fromJson(data);
					    						
					    				$scope.current = angular.fromJson(data).bartagList.number + 1;
					    				$scope.begin = Math.max(1, $scope.current - 5);
					    				$scope.end = Math.min($scope.begin + 9, angular.fromJson(data).bartagList.totalPages);
					    								
					    				$scope.total = angular.fromJson(data).bartagList.totalElements;
					    		});
							
							$scope.allCheckFlag = false;
						});
			    		
					} else if(angular.fromJson(data).resultCode == '3002'){
						modalOpen($uibModal,  "로그인 세션이 만료되었습니다.");
					} else if(angular.fromJson(data).resultCode == '3014'){
						modalOpen($uibModal,  "찾을 수 없는 바택 정보가 있습니다.");
					} else if(angular.fromJson(data).resultCode == '3028'){
						modalOpen($uibModal,  "이미 발행 시작된 바택 정보가 있습니다.");
					}
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
			}
		});
		
	};
	
	// 바택 수정
	$scope.updateBartagPop = function(){
		
		var objList = [];
		var statCheck = false;
		
		angular.forEach($scope.data.bartagList.content, function(value, key) {
			if($scope.data.bartagList.content[key].check){
				objList.push($scope.data.bartagList.content[key]);
				
				if($scope.data.bartagList.content[key].stat != '0'){
					statCheck = true;
				}
			}
		});
		
		if(objList.length == 0){
			modalOpen($uibModal,  "바택 정보를 선택해주세요.");
	    	return;
		}
		
		if(statCheck){
			modalOpen($uibModal,  "미확정인 바택정보만 수정할 수 있습니다.");
	    	return;
		}
		
		var additionCheck = true;
		var maxAddtionOrderDegree = $scope.data.bartagOrder.additionOrderDegree;
		var additionOrderList = [];
		
		for(var i=0; i<objList.length; i++){
			if(objList[i].additionOrderDegree == maxAddtionOrderDegree){
				additionCheck = false;
			}
			additionOrderList.push(objList[i].additionOrderDegree);
		}
		
		if(additionCheck){
			modalOpen($uibModal,  "마지막 바택 정보가 포함되어 있어야 합니다.");
	    	return;
		}
		
		additionOrderList.sort(function(a, b) {
		    return b - a;
		});
		
		var orderCount = additionOrderList[0];
		var orderCheck = false;
		
		for(var j=0; j<additionOrderList.length; j++){
			if(additionOrderList[j] != orderCount){
				orderCheck = true;
			}
			orderCount --;
		}
		
		if(orderCheck){
			modalOpen($uibModal,  "마지막 바택 정보를 포함하여 뒤에서부터 순서대로 선택되어야 합니다.");
	    	return;
		}
		
		var bartagUpdateModalInstance = $uibModal.open({
	    	animation: true,
	    	ariaLabelledBy: 'modal-title',
	    	ariaDescribedBy: 'modal-body',
	    	templateUrl: '/customTemplate/customBartagUpdate',
	    	controller: 'bartagUpdatePopupController',
	    	size: 'xlg',
	    	resolve: {
	    		bartagOrderList: function () {
	    			return objList;
	    		}
	    	}
	    });
		
		bartagUpdateModalInstance.result.then(function () {
	        
			$http.get('/bartag/bartagOrder/' + seq).success(
				function(data) {
					$scope.data = angular.fromJson(data);
						
					$scope.current = angular.fromJson(data).bartagList.number + 1;
					$scope.begin = Math.max(1, $scope.current - 5);
					$scope.end = Math.min($scope.begin + 9, angular.fromJson(data).bartagList.totalPages);
								
					$scope.total = angular.fromJson(data).bartagList.totalElements;
			});
			
	    }, function () {
//	        $log.info('Modal dismissed at: ' + new Date());
	    });
		
	};
	
	/** CheckBox */
	$scope.allCheck = function(){
		angular.forEach($scope.data.bartagList.content, function(value, key) {
			if($scope.allCheckFlag){
				$scope.data.bartagList.content[key].check = true;
			} else {
				$scope.data.bartagList.content[key].check = false;
			}
		});
	};
	
	// 생산 수량 입력
	$scope.completeAmountPop = function(){
		
		var objList = [];
		objList.push($scope.data.bartagOrder);
		
		if($scope.data.bartagOrder.stat == '2'){
			modalOpen($uibModal,  "RFID 생산 수량 입력된 스타일입니다.");
	    	return;
		}
		
		if($scope.data.bartagOrder.stat == '3'){
			modalOpen($uibModal,  "RFID 생산 수량 확정된 스타일입니다.");
	    	return;
		}
		
		if($scope.data.bartagOrder.stat == '4'){
			modalOpen($uibModal,  "RFID 추가 생산 입력된 스타일입니다.");
	    	return;
		}
		
		if($scope.data.bartagOrder.stat == '5'){
			modalOpen($uibModal,  "RFID 추가 생산 확정된 스타일입니다.");
	    	return;
		}
		
		if($scope.data.bartagOrder.stat == '6'){
			modalOpen($uibModal,  "RFID 생산 종결된 스타일입니다.");
	    	return;
		}
		
		var bartagOrderCompleteModalInstance = $uibModal.open({
	    	animation: true,
	    	ariaLabelledBy: 'modal-title',
	    	ariaDescribedBy: 'modal-body',
	    	templateUrl: '/customTemplate/customBartagOrderComplete',
	    	controller: 'bartagOrderCompletePopupController',
	    	size: 'xlg',
	    	resolve: {
	    		bartagOrderList: function () {
	    			return objList;
	    		},
	    		flag : function(){
	    			return "object" 
	    		}
	    	}
	    });
		bartagOrderCompleteModalInstance.result.then(function () {
	        
			$http.get('/bartag/bartagOrder/' + seq).success(
				function(data) {
					$scope.data = angular.fromJson(data);
						
					$scope.current = angular.fromJson(data).bartagList.number + 1;
					$scope.begin = Math.max(1, $scope.current - 5);
					$scope.end = Math.min($scope.begin + 9, angular.fromJson(data).bartagList.totalPages);
								
					$scope.total = angular.fromJson(data).bartagList.totalElements;
			});
			
	    }, function () {
//	        $log.info('Modal dismissed at: ' + new Date());
	    });
	}
	
	// 추가 수량 입력
	$scope.additionAmountPop = function(){
		
		var objList = [];
		objList.push($scope.data.bartagOrder);
		
		if($scope.data.bartagOrder.stat == '1'){
			modalOpen($uibModal,  "RFID 생산 수량 입력이 안된 스타일입니다.");
	    	return;
		}
		
		if($scope.data.bartagOrder.stat == '2'){
			modalOpen($uibModal,  "RFID 생산 수량 입력된 스타일입니다.");
	    	return;
		}
		
		if($scope.data.bartagOrder.stat == '4'){
			modalOpen($uibModal,  "RFID 추가 수량 입력된 스타일입니다.");
	    	return;
		}
		
		if($scope.data.bartagOrder.stat == '6'){
			modalOpen($uibModal,  "RFID 생산 종결된 스타일입니다.");
	    	return;
		}
		
		var bartagOrderAddtionModalInstance = $uibModal.open({
	    	animation: true,
	    	ariaLabelledBy: 'modal-title',
	    	ariaDescribedBy: 'modal-body',
	    	templateUrl: '/customTemplate/customBartagOrderAddition',
	    	controller: 'bartagOrderAdditionPopupController',
	    	size: 'xlg',
	    	resolve: {
	    		bartagOrderList: function () {
	    			return objList;
	    		},
	    		flag : function(){
	    			return "object" 
	    		}
	    	}
	    });
		bartagOrderAddtionModalInstance.result.then(function () {
	        
			$http.get('/bartag/bartagOrder/' + seq).success(
				function(data) {
					$scope.data = angular.fromJson(data);
							
					$scope.current = angular.fromJson(data).bartagList.number + 1;
					$scope.begin = Math.max(1, $scope.current - 5);
					$scope.end = Math.min($scope.begin + 9, angular.fromJson(data).bartagList.totalPages);
									
					$scope.total = angular.fromJson(data).bartagList.totalElements;
			});
			
	    }, function () {
//	        $log.info('Modal dismissed at: ' + new Date());
	    });
	};
	
	// 확정 처리
	$scope.complete = function(){
		
		var objList = [];
		var statCheck = false;
		
		angular.forEach($scope.data.bartagList.content, function(value, key) {
			if($scope.data.bartagList.content[key].check){
				objList.push($scope.data.bartagList.content[key]);
				
				if($scope.data.bartagList.content[key].stat != '0'){
					statCheck = true;
				}
			}
		});
		
		if(objList.length == 0){
			modalOpen($uibModal,  "바택 정보를 선택해주세요.");
	    	return;
		}
		
		if(statCheck){
			modalOpen($uibModal,  "미확정인 바택정보만 확정 할 수 있습니다.");
	    	return;
		}
		
		confirmOpen($uibModal, "선택된 바택 확정 완료 하시겠습니까?", function(b){
			
			if(b){
				$http({
					method : 'POST',
					url : "/bartag",
					data : objList,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config){
			    	if(angular.fromJson(data).resultCode == '1000'){
			    		modalOpen($uibModal,  "선택된 바택이 확정 완료 되었습니다.", function() {
			    			
			    			$http.get('/bartag/bartagOrder/' + seq).success(
			    				function(data) {
			    					$scope.data = angular.fromJson(data);
			    								
			    					$scope.current = angular.fromJson(data).bartagList.number + 1;
			    					$scope.begin = Math.max(1, $scope.current - 5);
			    					$scope.end = Math.min($scope.begin + 9, angular.fromJson(data).bartagList.totalPages);
			    										
			    					$scope.total = angular.fromJson(data).bartagList.totalElements;
			    			});
						});
			    		
					} else if(angular.fromJson(data).resultCode == '3002'){
						modalOpen($uibModal,  "로그인 세션이 만료되었습니다.");
					} else if(angular.fromJson(data).resultCode == '3014'){
						modalOpen($uibModal,  "찾을 수 없는 바택 정보가 있습니다.");
					} else if(angular.fromJson(data).resultCode == '3028'){
						modalOpen($uibModal,  "이미 발행 시작된 바택 정보가 있습니다.");
					}
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
			}
		});
	};
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);
