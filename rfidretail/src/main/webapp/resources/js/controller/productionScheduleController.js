app.controller('productionScheduleListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.allCheckFlag = false;
	
	$scope.search = initSearch("style", "productionStorageSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	$scope.startDate = initDate($scope.search.startDate);
	$scope.endDate = initDate($scope.search.endDate);
	
	if($scope.search.defaultDate == undefined){
		$scope.search.defaultDate = "1";
	}
	
	if($scope.search.size == undefined){  $scope.search.size = "10"; }
	
	var param = generateParam($scope.search);
	
	$http.get('/bartag/subCompanyList').success(
		function(data) {
			$scope.subCompanyList = angular.fromJson(data);
		}	
	);
	
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
			
			if($scope.search.subCompanyInfo == undefined){
				$scope.search.subCompanyName = "";
			} else {
				$scope.search.subCompanyName = $scope.search.subCompanyInfo.name; 
			}
			
			httpGetList("/production/schedule", param, $scope, $http, true);
			
			$http.get('/production/select/productYy' + param).success(
					function(data) {
						$scope.productYyList = angular.fromJson(data);
					}	
				);
			
			/*
			$http.get('/production/schedule/countAll' + param).success(
				function(data) {
					$scope.countAll = angular.fromJson(data);
				}
			);
			*/
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
		
		httpGetList("/production/schedule", param, $scope, $http);
	    	
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
		
		httpGetList("/production/schedule", param, $scope, $http);
		
		/*
		$http.get('/production/schedule/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
			}
		);
		*/
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
		
		httpGetList("/production/schedule", param, $scope, $http);
		
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
		
		if($scope.search.subCompanyInfo == undefined){
			$scope.search.subCompanyName = "";
		} else {
			$scope.search.subCompanyName = $scope.search.subCompanyInfo.name; 
		}
		
		$scope.search.startDate =  $scope.startDate.yyyymmdd();
		$scope.search.endDate =  $scope.endDate.yyyymmdd();
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/production/schedule", param, $scope, $http);
		
		/*
		$http.get('/production/schedule/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
			}
		);
		*/
	};
	
	$scope.detail = function(obj){
		
//		$window.sessionStorage.setItem("current", angular.toJson(setSearch($scope.search)));
//		$window.sessionStorage.removeItem("subCurrent");
//		
//		$location.url("/production/productionScheduleDetail?seq=" + obj.productionStorageSeq);
		
		detailPopOpen($uibModal, obj, "/production/productionScheduleDetailPop", "productionScheduleDetailPopController", "xxlg");
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
		
		httpGetList("/production/schedule", param, $scope, $http);
		
		/*
		$http.get('/production/schedule/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
			}
		);
		*/
	};
	
	/** 입고검수 */
	$scope.inspection = function(){
		
		var checkFlag = false;
		var amountFlag = false;
		
		var checkList = [];
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				checkList.push($scope.list[key]);
				checkFlag = true;
			}
		});
		if(!checkFlag){
			modalOpen($uibModal,  "선택된 스타일이 없습니다.");
			return;
		}
		
		angular.forEach(checkList, function(value, key) {
			if(checkList[key].nonCheckAmount > 0){
				amountFlag = true;
			}
		});
		
		if(!amountFlag){
			modalOpen($uibModal,  "발행 미검수 태그가 없습니다.");
			return;
		}
		
		confirmOpen($uibModal, "선택된 스타일 입고 검수 하시겠습니까?", function(b){
    		
			if(b){
				$http({
					method : 'POST',
					url : "/productionRfidTag/inspectionWebList",
					data : checkList
				}).success(function(data, status, headers, config){
			    	modalOpen($uibModal,  "선택된 스타일 입고 검수 완료되었습니다.", function() {
			    		
			    		httpGetList("/production/schedule", param, $scope, $http, true);
						
						$http.get('/production/schedule/countAll' + param).success(
							function(data) {
								$scope.countAll = angular.fromJson(data);
							}
						);
						
						$scope.allCheckFlag = false;
					});
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
			}
		});
	};
	
	/** 일괄 태그 입고 검수 **/
	$scope.inspectionBatch = function(){
		
		if($scope.search.companyInfo == undefined){
			modalOpen($uibModal,  "업체를 선택해주세요.");
			return;
		}
		
		$scope.search.companySeq = $scope.search.companyInfo.companySeq;
		
		confirmOpen($uibModal, "태그 일괄 입고 검수 예약 하시겠습니까?", function(b){
			
			if(b){
				$http.get('/production/scheduleBatch?companySeq=' + $scope.search.companySeq).success(
						function(data) {
							modalOpen($uibModal,  "태그 일괄 입고 검수 예약 완료되었습니다.");
					}).error(function(data, status, headers, config){
				    	if(status == 406){
							modalOpen($uibModal,  "예약된 태그 일괄 입고 검수 작업이 있습니다. 익일 다시 시도해주세요.");
						} else {
							modalOpen($uibModal,  "에러발생");
						}
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
		
		$http.get('/production/select/' + $scope.search.urlFlag + param).success(
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

app.controller('productionScheduleDetailPopController', ['$scope', '$http', '$location', '$rootScope', '$interval', '$routeParams', 'verificationFactory', '$filter', '$window', '$uibModal', '$uibModalInstance', 'obj', function ($scope, $http, $location, $rootScope, $interval, $routeParams, verificationFactory, $filter, $window, $uibModal, $uibModalInstance, obj) {
	
	$scope.ajaxFinish = false;
	
	var seq = obj.productionStorageSeq;
	var statValue = $scope.statValue;
	var statNum = $scope.statNum;
	$scope.allCheckFlag = false;

	$scope.search = initSearch("barcode", "rfidTag", "asc", angular.fromJson($window.sessionStorage.getItem("subCurrent")));
	$window.sessionStorage.setItem("subCurrent", angular.toJson(setSearch($scope.search)));
	
	if($scope.search.stat == undefined){
		$scope.search.stat = "all";
	}
	
	var param = generateParam($scope.search);
	
	$http.get('/production/' + seq).success(
		function(data) {
		    $scope.data = angular.fromJson(data);
		    
		    $scope.ajaxFinish = true;
		}
	);
	
	if($rootScope.testMode){
		httpGetList("/productionRfidTag/" + seq, param, $scope, $http);
	}
	
	/** 태그상태별 수량 조회 */
	/*
	$http.get('/productionRfidTag/count' + '?seq=' + seq).success(function (data) {
		$scope.obj = angular.fromJson(data);
	    $scope.ajaxFinish = true;
	});
	*/
	
	$scope.tagStat = function(obj){
		
		$scope.allCheckFlag = false;
		
		if(!$rootScope.testMode){
			return;
		}
		
		$scope.search.stat = obj;
		param = generateParam($scope.search);
		httpGetList("/productionRfidTag/" + seq, param, $scope, $http, true);
	};
	
	
	/** 재발행요청리스트 */
	/*
	$http.get("/rfidTagReissueRequest/production/" + seq).success(
		function (data) {
			$scope.rfidTagReissueRequestList = angular.fromJson(data);
		}
	);
	*/

	/** 페이징 */
	$scope.goPage = function(page, statValue, statNum){
		$("#allCheck").prop("checked",false);
		
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
		
		if($scope.search.text == undefined){
	    	$scope.search.text = "";
	    }
	    
		if(statValue == undefined){
			param = "?page=" + $scope.search.page + "&search=" + $scope.search.text + "&option=" + $scope.search.option + "&sort=" + $scope.search.sortName + "," +$scope.search.sortOrder;
			httpGetList("/productionRfidTag/" + seq, param, $scope, $http);
		} else {
			param = param = "?seq=" + seq + "&page=" + $scope.search.page + "&stat=" + statNum + "&sort=" + $scope.search.sortName + "," +$scope.search.sortOrder;
			httpGetList("/productionRfidTag/stat", param, $scope, $http, true);
		}
	};
	
	/** 검색 */
	$scope.clickSearch = function(){
		
		if($scope.search.text == undefined){
			$scope.search.text = "";
		}
		
		if($scope.search.text == ""){
			return;
		}
		
		param = "?search=" + $scope.search.text + "&option=" + $scope.search.option;
		
		httpGetList("/productionRfidTag/" + seq, param, $scope, $http);
	};
	
	/** 정렬 */
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		if($scope.search.text == undefined){
			$scope.search.text = "";
		}
		
		param = "?search=" + $scope.search.text + "&option=" + $scope.search.option + "&sort=" + $scope.search.sortName + "," + $scope.search.sortOrder;
		
		httpGetList("/productionRfidTag/" + seq, param, $scope, $http);
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
		
	$scope.openReissueModal = function(){
		
		$scope.checkList = [];
		
		var flag = false;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				$scope.checkList.push($scope.list[key]);
				flag = true;
			}
		});
		if(!flag){
			modalOpen($uibModal,  "선택된 태그가 없습니다.");
			return;
		}
		
		if($rootScope.userRole == 'production' || $rootScope.userRole == 'admin'){
			
			$scope.explanatory = "";
			
			$('#reissueModal').modal({
				show:true,
				backdrop: true,
			    keyboard: false,
			});
		} else {
			modalOpen($uibModal,  "권한이 없습니다.");
		}
	};
	
	/** 재발행요청 */
	$scope.reBartag = function(){
		
		var obj = {
			productionStorage : $scope.data,
			companyInfo : $scope.data.bartagMaster.productionCompanyInfo,
			reissueYn : "N",
			explanatory : $scope.explanatory,
			rfidTagReissueRequestDetail : []
		}
		
		angular.forEach($scope.checkList, function(value, key) {
			var detailObj = {
				rfidTag : $scope.checkList[key].rfidTag,
			}
			obj.rfidTagReissueRequestDetail.push(detailObj);
		});
		
		$http({
			method : 'POST',
			url : "/productionRfidTag/reBartag",
			data : obj
		}).success(function(data, status, headers, config){
			$('#reissueModal').modal('hide');
			
	    	modalOpen($uibModal,  "재발행요청 완료되었습니다.");
	    	
	    	$scope.checkList = [];
	    	
	    	// 재발행요청 완료시 처리
	    	$http.get('/production/' + $routeParams.seq).success(
		    		function(data) {
		    			$scope.data = angular.fromJson(data);
		    		}
		    	);
	    	
	    	httpGetList("/productionRfidTag/" + seq, param, $scope, $http);
	    	
	    	$http.get("/rfidTagReissueRequest/production/" + seq).success(
	    		function (data) {
	    			$scope.rfidTagReissueRequestList = angular.fromJson(data);
	    		}
	    	);
	    	
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
	};
	
	/** 전체재발행요청 */
	$scope.reBartagAll = function(){
		if($rootScope.userRole == 'production' || $rootScope.userRole == 'admin'){
	    	confirmOpen($uibModal, "전체 재발행 요청을 하시겠습니까?", function(b){
	    		
	    		if(b){
	    			$http.get("/productionRfidTag/findTag?seq=" + seq).success(function(data) {
						console.log(angular.fromJson(data));
						var allData = angular.fromJson(data);
						$http({
							method : 'POST',
							url : "/productionRfidTag/reBartag",
							data : allData
						}).success(function(data, status, headers, config){
					    	modalOpen($uibModal,  "전체 재발행요청 완료되었습니다.", function() {
								$(location).attr("href", '#/production/productionScheduleList');
							});
					    }).error(function(){
					    	modalOpen($uibModal,  "에러 발생");
					    });
					});
	    		}
			});
	    } else {
	    	modalOpen($uibModal,  "권한이 없습니다.");
	    	return;
	    }
	};
	
	/** 입고검수 */
	$scope.inspection = function(){
		
		if($rootScope.userRole == 'production' || $rootScope.userRole == 'admin'){
	    	
			confirmOpen($uibModal, "전체 입고 검수 하시겠습니까?", function(b){
				
				if(b){
					if($scope.data.nonCheckAmount > 0){
		    			$http({
							method : 'POST',
							url : "/productionRfidTag/inspectionWeb/" + seq
						}).success(function(data, status, headers, config){
					    	modalOpen($uibModal,  "전체 입고 검수 완료되었습니다.", function() {
								$(location).attr("href", '#/production/productionScheduleList');
							});
					    }).error(function(){
					    	modalOpen($uibModal,  "에러 발생");
					    });
		    		} else {
		    			modalOpen($uibModal,  "발행 미검수 태그가 없습니다.");
		    		}
				}
			});
	    } else {
	    	modalOpen($uibModal,  "권한이 없습니다.");
	    	return;
	    }
	};
	
	$scope.$watch(function(){
		
		return $scope.list;
		
		}, function() {
			
			if($scope.list != undefined){
				$scope.checkboxFlag = false;
				
				angular.forEach($scope.list, function(value, key) {
					if($scope.list[key].stat === '1' || $scope.list[key].stat === '2'){
						$scope.checkboxFlag = true;
					}
				});
			} 
		
	}, true);
	
	$scope.openRfidTagDetail = function (obj) {
		if(obj.rfidTag != undefined){
			 var modalInstance = $uibModal.open({
			    	animation: true,
			    	ariaLabelledBy: 'modal-title',
			    	ariaDescribedBy: 'modal-body',
			    	templateUrl: '/customTemplate/customRfidTag',
			    	controller: 'rfidTagPopupController',
			    	size: 'xlg',
			    	resolve: {
			    		rfidTag: function () {
			    			return obj.rfidTag;
			    		},
			    		barcode: function (){
			    			return obj.erpKey + obj.rfidSeq;
			    		}
			    	}
			    });
			    modalInstance.result.then(function (selectedItem) {
//			        $ctrl.selected = selectedItem;
			    }, function () {
//			        $log.info('Modal dismissed at: ' + new Date());
			    });
		}
	};
	
	$scope.openBoxMapping = function () {
		
		var objList = [];
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				objList.push($scope.list[key]);
			}
		});
		
		if(objList.length == 0){
			modalOpen($uibModal,  "태그를 선택해주세요.");
	    	return;
		}
			
		var boxMappingModalInstance = $uibModal.open({
	    	animation: true,
	    	ariaLabelledBy: 'modal-title',
	    	ariaDescribedBy: 'modal-body',
	    	templateUrl: '/customTemplate/customBoxMapping',
	    	controller: 'boxMappingPopupController',
	    	size: 'xlg',
	    	resolve: {
	    		rfidTagList: function () {
	    			return objList;
	    		}
	    	}
	    });
		boxMappingModalInstance.result.then(function (selectedItem) {
//	        $ctrl.selected = selectedItem;
	    }, function () {
//	        $log.info('Modal dismissed at: ' + new Date());
	    });
	};
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);

