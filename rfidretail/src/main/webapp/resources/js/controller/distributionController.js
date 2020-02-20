app.controller('distributionListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("style", "distributionStorageSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
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
			
			httpGetList("/distribution", param, $scope, $http, true);
			
			$http.get('/distribution/select/productYy' + param).success(
					function(data) {
						$scope.productYyList = angular.fromJson(data);
					}	
				);
			
			/*
			$http.get('/distribution/countAll' + param).success(
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
		
		httpGetList("/distribution", param, $scope, $http);
	    	
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
		
		httpGetList("/distribution", param, $scope, $http);
		
		/*
		$http.get('/distribution/countAll' + param).success(
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
		
		httpGetList("/distribution", param, $scope, $http);
		
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
		
		$scope.search.searchYn = true;
		
		httpGetList("/distribution", param, $scope, $http);
		
		/*
		$http.get('/distribution/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
			}
		);
		*/
	};
	
	$scope.detail = function(obj){
		detailPopOpen($uibModal, obj, "/distribution/distributionDetailPop", "distributionDetailPopController", "xxlg");
	};
	
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/distribution", param, $scope, $http);
		
		/*
		$http.get('/distribution/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
			}
		);
		*/
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
		
		$http.get('/distribution/select/' + $scope.search.urlFlag + param).success(
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

app.controller('distributionDetailPopController', ['$scope', '$http', '$location', '$rootScope', '$interval', '$routeParams', 'verificationFactory', '$filter', '$window', '$uibModal', '$uibModalInstance', 'obj', function ($scope, $http, $location, $rootScope, $interval, $routeParams, verificationFactory, $filter, $window, $uibModal, $uibModalInstance, obj) {
	
	$scope.allCheckFlag = false;

	$scope.search = initSearch("barcode", "rfidTag", "asc", angular.fromJson($window.sessionStorage.getItem("subCurrent")));
	$window.sessionStorage.setItem("subCurrent", angular.toJson(setSearch($scope.search)));
	
	if($scope.search.stat == undefined){
		$scope.search.stat = "all";
	}
	
	var seq = obj.distributionStorageSeq;
	
	var param = generateParam($scope.search);
	
	$scope.data = obj;
	
	if($rootScope.testMode){
		httpGetList("/distributionRfidTag/" + seq, param, $scope, $http);
	}
	
	/** 태그상태별 수량 조회 */
	/*
	$http.get('/productionRfidTag/count' + '?seq=' + seq).success(function (data) {
		$scope.obj = angular.fromJson(data);
	    $scope.ajaxFinish = true;
	});
	*/
	
	/** 태그상태별 수량 클릭시 해당 수량 출력 */
	$scope.tagStat = function(obj){
		$scope.search.stat = obj;
		param = generateParam($scope.search);
		httpGetList("/distributionRfidTag/" + seq, param, $scope, $http, true);
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
	    
		param = generateParam($scope.search);
		
		httpGetList("/distributionRfidTag/" + seq, param, $scope, $http, true);
	};
	
	/** 검색 */
	$scope.clickSearch = function(){
		
		if($scope.search.text == undefined){
			$scope.search.text = "";
		}
		
		if($scope.search.text == ""){
			return;
		}
		
		param = generateParam($scope.search);
		
		httpGetList("/distributionRfidTag/" + seq, param, $scope, $http, true);
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
		
		param = generateParam($scope.search);
		
		httpGetList("/distributionRfidTag/" + seq, param, $scope, $http, true);
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
		
	/*$scope.checkBoxSelected = function(object){
		if(object.check){
			console.log(object);
		}
	};*/
	
	$scope.checkList = [];
	
	$scope.openReissueModal = function(){
		
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
	
	
	/** 입고검수 */
	$scope.inspection = function(){
		
		if($rootScope.userRole == 'production' || $rootScope.userRole == 'admin'){
	    	
			confirmOpen($uibModal, "전체 입고 검수 하시겠습니까?", function(b){
				
				if(b){
					if($scope.data.nonCheckAmount > 0){
		    			$http({
							method : 'POST',
							url : "/distributionRfidTag/inspectionAll/" + seq
						}).success(function(data, status, headers, config){
					    	modalOpen($uibModal,  "전체 입고 검수 완료되었습니다.", function() {
								$(location).attr("href", '#/distribution/distributionList');
							});
					    }).error(function(){
					    	modalOpen($uibModal,  "에러 발생");
					    });
		    		} else {
		    			modalOpen($uibModal,  "입고 미검수 태그가 없습니다.");
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
			
		angular.forEach($scope.checkList, function(value, key) {
			var detailObj = {
				rfidTag : $scope.checkList[key].rfidTag,
			}
			objList.push(detailObj);
		});
		
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

