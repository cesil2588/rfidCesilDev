app.controller('bartagGroupListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', 'FileSaver', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal, FileSaver) {
	
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
			
			httpGetList("/bartag/bartagGroup", param, $scope, $http, true, true);
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
		
		httpGetList("/bartag/bartagGroup", param, $scope, $http, true, true);
	    	
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
		
		httpGetList("/bartag/bartagGroup", param, $scope, $http, true, true);
		
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/bartag/bartagGroup", param, $scope, $http, true, true);
		
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
		
		httpGetList("/bartag/bartagGroup", param, $scope, $http, true, true);
	};
	
	$scope.detail = function(obj){
		
		$scope.search.companySeq = obj.companySeq;
		
		$window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch($scope.search)));
		$window.sessionStorage.removeItem("current");
		
		$location.url("/bartag/bartagList?regDate=" + obj.regDate + "&companySeq=" + obj.companySeq);
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
		
		httpGetList("/bartag/bartagGroup", param, $scope, $http, true, true);
	};
	
	/** 발행일자별 엑셀다운로드 */
	$scope.goExcelDate = function (){
		
		if($scope.list.length == 0){
			modalOpen($uibModal,  "검색된 데이터가 없습니다. 다른 조건으로 조회해주세요.");
			return;
		}
		
		param = generateParam($scope.search);
		
		$http({
	    	method : 'GET',
	    	url : '/bartag/bartagExcel/groupDate' + param,
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
	            defaultFileName = defaultFileName + "_바택정보엑셀다운로드.xls";
	            
	    		var file = new Blob([data], { type: "application/vnd.ms-excel;charset=charset=utf-8" });
	    		FileSaver.saveAs(file, defaultFileName);
	    	} else {
	    		modalOpen($uibModal,  "에러 발생");
	    	}
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
		
		if($rootScope.userRole == 'publish'){
			angular.forEach($scope.list, function(value, key) {
				$scope.list[key].bartagStartDate = new Date();
			});
		}
	};
	
	/** 태그발행결과 일괄업로드 */
	$scope.fileUploadPopup = function(){
		
		var modalInstance = $uibModal.open({
	    	animation: true,
	    	ariaLabelledBy: 'modal-title',
	    	ariaDescribedBy: 'modal-body',
	    	templateUrl: '/customTemplate/customFileUpload',
	    	controller: 'fileUploadPopupController',
	    	size: 'lg'
	    });
	    modalInstance.result.then(function (selectedItem) {
//	        $ctrl.selected = selectedItem;
	    }, function () {
//	        $log.info('Modal dismissed at: ' + new Date());
	    });
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
	
	/*
	 * 체크된 바택만 엑셀 다운로드
	 * 아래 소스를 이용하여 비동기로 다운로드 가능, 한글명은 script에서 처리  
	 */
	$scope.checkExcelDownload = function(){
		
		var checkflag = false;
		var checkList = new Array();
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				checkList.push($scope.list[key]);
				checkflag = true;
			}
		});
		
		if(!checkflag){
			modalOpen($uibModal,  "선택된 바택 정보가 없습니다.");
			return;
		}
		
		$http({
	    	method : 'POST',
	    	data : checkList,
	    	url : '/bartag/bartagExcel/groupCheck',
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
	            defaultFileName = defaultFileName + "_바택정보엑셀다운로드.xls";
	            
	    		var file = new Blob([data], { type: "application/vnd.ms-excel;charset=charset=utf-8" });
	    		FileSaver.saveAs(file, defaultFileName);
	    		
	    		httpGetList("/bartag/bartagGroup", param, $scope, $http, true, true);
	    		
	    		$scope.allCheckFlag = false;
	    		
	    	} else {
	    		modalOpen($uibModal,  "에러 발생");
	    	}
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
		
		/*
		if($rootScope.userRole == 'publish'){
			angular.forEach($scope.list, function(value, key) {
				if($scope.list[key].check){
					$scope.list[key].bartagStartDate = new Date();
				}
			});
		}
		*/
	};
	
}]);

app.controller('bartagController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory', '$window', 'uibDateParser', 'FileSaver', 'Blob', '$uibModal', 
				function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, uibDateParser, FileSaver, Blob, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.allCheckFlag = false;
	
	$scope.search = initSearch("name", "bartagSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	if($scope.search.defaultDateType == undefined){
		$scope.search.defaultDateType = "1";
	}
	
	if($scope.search.completeYn == undefined){
		$scope.search.completeYn = "all";
	}
	
	if($scope.search.defaultDate == undefined){
		$scope.search.defaultDate = "1";
	}
	
	$scope.search.option = "productYy";
	
	if($scope.search.size == undefined){  
		$scope.search.size = "10"; 
	}
	
	$scope.search.regDate = $routeParams.regDate;
	$scope.search.companySeq = $routeParams.companySeq;
	
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
			
			var param = generateParam($scope.search);
			
			httpGetList("/bartag", param, $scope, $http, true);
			
			$http.get('/bartag/countAll' + param).success(
				function(data) {
					$scope.countAll = angular.fromJson(data);
				}
			);
			
			$http.get('/bartag/select/productYy' + param).success(
				function(data) {
					$scope.productYyList = angular.fromJson(data);
				}	
			);
			
			$http.get('/bartag/subCompanyList?companySeq=' + $scope.search.companySeq).success(
				function(data) {
					$scope.subCompanyList = angular.fromJson(data);
				}	
			);
		}
	);
	
	/** 바택정보 상세보기 */
	$scope.goBartagDetail = function(obj){
//		$window.sessionStorage.setItem("current", angular.toJson(setSearch($scope.search)));
//		$window.sessionStorage.removeItem("subCurrent");
//		
//		$location.url("/bartag/bartagDetail?seq=" + obj.bartagSeq);
		
		detailPopOpen($uibModal, obj, "/bartag/bartagDetailPop", "bartagDetailPopController", "xxlg");
	};

	/** 발행일자 조회 */
	/* 사용 안함
	$http.get('/barTag/bartagExcel/getCreateDt').success(
			function(data) {
				$scope.createDtList = angular.fromJson(data);
				$scope.excelDate = $scope.createDtList[0];
			}
		);
	*/
	
	$scope.onSelected = function(obj){
		$scope.excelDate = obj;
	};
	
	/** 페이징 */
	$scope.goPage = function(page){
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
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/bartag", param, $scope, $http);
	    
	};
	
	/** 검색 */
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
		
		httpGetList("/bartag", param, $scope, $http);
		
		$http.get('/bartag/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
			}
		);
	};
	
	/** 정렬 */
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/bartag", param, $scope, $http);
	};
	
	/** 상단 검색 */
	$scope.headSearch = function(){
		
		if($scope.search.completeYn == undefined){
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
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/bartag", param, $scope, $http);
		
		$http.get('/bartag/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
			}
		);
	};
	
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/bartag", param, $scope, $http);
		
		$http.get('/bartag/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
			}
		);
	};
	
	/** 발행일자별 엑셀다운로드 */
	$scope.goExcelDate = function (){
		
		if($scope.search.completeYn == undefined){
			return;
		}
		
		if($scope.list.length == 0){
			modalOpen($uibModal,  "검색된 데이터가 없습니다. 다른 조건으로 조회해주세요.");
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
		
//		param = generateParam($scope.search);
		
		param = "?regDate=" + $scope.search.regDate + 
				"&companySeq=" + $scope.search.companySeq +
				"&subCompanyName=" + $scope.search.subCompanyName +
				"&defaultDateType=" + $scope.search.defaultDateType +
				"&completeYn=" + $scope.search.completeYn;
		
		$http({
	    	method : 'GET',
	    	url : '/bartag/bartagExcel/date' + param,
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
	            defaultFileName = defaultFileName + "_바택정보엑셀다운로드.xls";
	            
	    		var file = new Blob([data], { type: "application/vnd.ms-excel;charset=charset=utf-8" });
	    		FileSaver.saveAs(file, defaultFileName);
	    	} else {
	    		modalOpen($uibModal,  "에러 발생");
	    	}
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
		
		if($rootScope.userRole == 'publish'){
			angular.forEach($scope.list, function(value, key) {
				$scope.list[key].bartagStartDate = new Date();
			});
		}
	};
	
	/** 태그발행결과 일괄업로드 */
	$scope.fileUploadPopup = function(){
		
		var modalInstance = $uibModal.open({
	    	animation: true,
	    	ariaLabelledBy: 'modal-title',
	    	ariaDescribedBy: 'modal-body',
	    	templateUrl: '/customTemplate/customFileUpload',
	    	controller: 'fileUploadPopupController',
	    	size: 'lg'
	    });
	    modalInstance.result.then(function (selectedItem) {
//	        $ctrl.selected = selectedItem;
	    }, function () {
//	        $log.info('Modal dismissed at: ' + new Date());
	    });
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
	
	/*
	 * 체크된 바택만 엑셀 다운로드
	 * 아래 소스를 이용하여 비동기로 다운로드 가능, 한글명은 script에서 처리  
	 */
	$scope.checkExcelDownload = function(){
		
		var checkflag = false;
		var checkList = new Array();
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				checkList.push($scope.list[key]);
				checkflag = true;
			}
		});
		
		if(!checkflag){
			modalOpen($uibModal,  "선택된 바택 정보가 없습니다.");
			return;
		}
		
		$http({
	    	method : 'POST',
	    	data : checkList,
	    	url : '/bartag/bartagExcel/check',
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
	            defaultFileName = defaultFileName + "_바택정보엑셀다운로드.xls";
	            
	    		var file = new Blob([data], { type: "application/vnd.ms-excel;charset=charset=utf-8" });
	    		FileSaver.saveAs(file, defaultFileName);
	    		
	    		httpGetList("/bartag", param, $scope, $http);
	    		
	    		$scope.allCheckFlag = false;
	    		
	    	} else {
	    		modalOpen($uibModal,  "에러 발생");
	    	}
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
		
		/*
		if($rootScope.userRole == 'publish'){
			angular.forEach($scope.list, function(value, key) {
				if($scope.list[key].check){
					$scope.list[key].bartagStartDate = new Date();
				}
			});
		}
		*/
	};
	
	$scope.search.testPublishDegree = "01";
	$scope.search.testPublishRegDate = "180623";
	
	/** 선택된 바택 발행완료 */
	$scope.testBartagComplete = function(){
		
		var checkflag = false;
		var checkList = new Array();
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				checkList.push($scope.list[key]);
				checkflag = true;
			}
		});
		
		if(!checkflag){
			modalOpen($uibModal,  "선택된 바택 정보가 없습니다.");
			return;
		}
		
		if($scope.search.testPublishDegree == undefined){
			modalOpen($uibModal,  "발행차수 정보가 없습니다.");
			return;
		}
		
		if($scope.search.testPublishRegDate == undefined){
			modalOpen($uibModal,  "발행일자 정보가 없습니다.");
			return;
		}
		
		$http({
	    	method : 'POST',
	    	url : '/bartag/testBartagComplete?publishDegree=' + $scope.search.testPublishDegree + '&publishRegDate=' + $scope.search.testPublishRegDate,
	    	data: checkList,
	    	headers: {'Content-Type': 'application/json; charset=utf-8'} 
	    }).success(function(data, status, headers, config) {
			if (status == 200) {
				modalOpen($uibModal,  "선택된 바택 발행 작업 완료되었습니다.", function() {
					
					httpGetList("/bartag", param, $scope, $http);
					
					$http.get('/bartag/countAll' + param).success(
						function(data) {
							$scope.countAll = angular.fromJson(data);
						}
					);
					
					$scope.allCheckFlag = false;
				});
			} 
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
	};
	
	/** 모든 바택 발행완료 */
	$scope.testBartagCompleteAll = function(){
		
		if($scope.search.testPublishDegree == undefined){
			modalOpen($uibModal,  "발행차수 정보가 없습니다.");
			return;
		}
		
		if($scope.search.testPublishRegDate == undefined){
			modalOpen($uibModal,  "발행일자 정보가 없습니다.");
			return;
		}
		
		$http({
	    	method : 'POST',
	    	url : '/bartag/testBartagCompleteAll?publishDegree=' + $scope.search.testPublishDegree + '&publishRegDate=' + $scope.search.testPublishRegDate,
	    	headers: {'Content-Type': 'application/json; charset=utf-8'} 
	    }).success(function(data, status, headers, config) {
			if (status == 200) {
				modalOpen($uibModal,  "바택 발행 작업 완료되었습니다.", function() {
					
					httpGetList("/bartag", param, $scope, $http);
					
					$http.get('/bartag/countAll' + param).success(
						function(data) {
							$scope.countAll = angular.fromJson(data);
						}
					);
				});
			} 
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
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
		
		$http.get('/bartag/select/' + $scope.search.urlFlag + param).success(
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

app.controller('bartagDetailPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', '$uibModal', 'FileSaver', 'Blob', '$uibModalInstance', 'obj',
	function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModal, FileSaver, Blob, $uibModalInstance, obj) {

	$scope.ajaxFinish = false;
	
	var seq = obj.bartagSeq;
	
	$scope.search = initSearch("name", "rfidTagSeq", "asc", angular.fromJson($window.sessionStorage.getItem("subCurrent")));
	$window.sessionStorage.setItem("subCurrent", angular.toJson(setSearch($scope.search)));
	
	if($scope.search.stat == undefined){
		$scope.search.stat = "all";
	}
	
	var param = generateParam($scope.search);

	httpGetList("/rfidTag/" + seq, param, $scope, $http, true);
	
	/** 바택 상세조회 */
	$http.get('/bartag/' + seq).success(
		function(data) {
			$scope.data = angular.fromJson(data);
			$scope.company = $scope.data.productionCompanyInfo;
		}
	);
	
	/** 재발행요청리스트 */
	$http.get("/rfidTagReissueRequest/bartag/" + seq).success(
		function (data) {
			$scope.rfidTagReissueRequestList = angular.fromJson(data);
		}
	);
	
	/** 태그상태별 수량 조회 */
	/*
	$http.get('/rfidTag/count' + '?seq=' + seq).success(function (data) {
		$scope.obj = angular.fromJson(data);
	    $scope.ajaxFinish = true;
	});
	*/
	
	/** 태그상태별 수량 클릭시 해당 수량 출력 */
	$scope.tagStat = function(stat){
		$scope.search.stat = stat;
		param = generateParam($scope.search);
		httpGetList("/rfidTag/" + seq, param, $scope, $http, true);
	};
	
	/** 페이징 */
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
		
		if($scope.search.text == undefined){
	    	$scope.search.text = "";
	    }
		
		param = generateParam($scope.search);
		httpGetList("/rfidTag/" + seq, param, $scope, $http);
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
		
		httpGetList("/rfidTag/" + seq, param, $scope, $http);
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
		
		httpGetList("/rfidTag/" + seq, param, $scope, $http);
	};
	
	/** 샘플업로드텍스트 */
	$scope.sample = function(){
		var link = angular.element('<a/>');
	    link.attr({
	    	href : '/bartag/filedownload'
	    })[0].click();
	};

	/** 엑셀 다운로드 */
	$scope.goExcelDownload = function(){
		
		var today = new Date();
	    /*var link = angular.element('<a/>');
	    link.attr({
	    	href : '/bartag/bartagExcel/' + seq
	    })[0].click();*/
		$http({
	    	method : 'GET',
	    	url : '/bartag/bartagExcel/' + seq,
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
	            defaultFileName = defaultFileName + "_바택정보엑셀다운로드.xls";
	            
	    		var file = new Blob([data], { type: "application/vnd.ms-excel;charset=charset=utf-8" });
	    		FileSaver.saveAs(file, defaultFileName);
	    	} else {
	    		modalOpen($uibModal,  "에러 발생");
	    	}
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
	    
	    if($rootScope.userRole != 'publish'){
	    	return;
	    } else if($rootScope.userRole == 'publish' && $scope.data.bartagStartDate == null){
	    	
	    	$scope.data.bartagStartDate = today;
	    	
	    	$http({
		    	method : 'POST',
		    	url : '/bartag/bartagDate',
		    	data: {
		    		'bartagStartDate' : today,
		    		'bartagSeq': seq
		    	},
		    	headers: {'Content-Type': 'application/json; charset=utf-8'} 
		    });
	    }
	};
	
	/** 태그발행결과 업로드 */
	$scope.doUploadFile = function(){
		
		if($scope.data.bartagStartDate == null){
			modalOpen($uibModal,  "엑셀다운로드를 해주십시오.");
			return;
		}
		
		if($scope.uploadedFile == undefined){
			return;
		}

		var list = $scope.list;
		var file = $scope.uploadedFile;
	    var url = "/rfidTag/" + seq + "?flag=" + $scope.flag;
	       
	    var data = new FormData();
	    data.append('uploadfile', file);
	    data.append('fileName', file.name);
	    
	    var config = {
	    	transformRequest: angular.identity,
	    	transformResponse: angular.identity,
		   	headers : {
		   		'Content-Type': undefined
		   	}
	    }
	       
	    $http.post(url, data, config).then(function (response) {
	    	$scope.uploadResult= angular.fromJson(response.data);
	    	$rootScope.reload();
	    }, function (response) {
	    	$scope.uploadResult= angular.fromJson(response.data);
	    	angular.forEach($scope.uploadResult, function(value, key) {
				$scope.uploadResult[key].count = 0;
			});
	    });
	};
	
	/** 업체정보 조회 */
	$scope.clickListener = function(customerCd){
		$http({
			method : 'GET',
		    url : '/bartag/company/' + customerCd,
		    headers: {'Content-Type': 'application/json; charset=utf-8'} 
		}).success(function(data, status, headers, config){  
			$scope.company = angular.fromJson(data);
		});

	};

	/** 최종 확인 */
	$scope.finalConfirm = function(){
		
		confirmOpen($uibModal, "태그발행결과를 최종확인하겠습니까?"+"<br>"+"※ 최종확인된 태그 정보는 생산업체재고로 전송됩니다.", function(b){
			
			if(b){
				
				$http({
			    	method : 'POST',
			    	url : '/rfidTag/finalConfirm/' + seq
			    }).success(function(data, status, headers, config){
			    	if(data){
			    		modalOpen($uibModal,  "최종 발행완료 되었습니다.", function() {
							$(location).attr("href", '#/bartag/bartagList');
						});
			    	} else {
			    		modalOpen($uibModal,  "미발행 태그가 있습니다. 확인해주세요.");
			    	}
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
			}
		});
	};
	
	/** 재발행 최종 확인 */
	$scope.refinalConfirm = function(){
		
		confirmOpen($uibModal, "태그 재발행결과를 최종확인하겠습니까?"+"<br>"+"※ 최종 확인된 태그 재발행 정보는 생산업체재고로 전송됩니다.", function(b){
			
			if(b){
				
				$http({
			    	method : 'POST',
			    	url : '/rfidTag/refinalConfirm/' + seq
			    }).success(function(data, status, headers, config){
			    	if(data){
			    		modalOpen($uibModal,  "최종 재발행완료 되었습니다.", function() {
							$(location).attr("href", '#/bartag/bartagList');
						});
			    	} else {
			    		modalOpen($uibModal,  "재발행 대기,요청 태그가 있습니다. 확인해주세요.");
			    	}
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
			}
		});
	};
	
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
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);

//RFID 태그 이력을 스타일, 컬러 , 사이즈별로 조회하기 위한 controller
app.controller('bartagHistoryController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal)  {

$scope.ajaxFinish = false;
	
	$scope.search = initSearchCustom(angular.fromJson($window.sessionStorage.getItem("groupCurrent")));
		
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
			
			httpGetList("/bartag/bartagHistoryList", param, $scope, $http, true, true);
			
		}	
		
	);
	
	
	$scope.clickSearch = function(){
		
		if($scope.search.text == undefined){
			$scope.search.text = "";
		}
		
		if($scope.search.text == ""){
			return;
		}
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/bartag/bartagHistoryList", param, $scope, $http, true, true);
		
	};
	

	/** 상단 검색 */
	$scope.headSearch = function(){
				
		if($scope.search.companyInfo == undefined){
			$scope.search.companySeq = 0;
		} else {
			$scope.search.companySeq = $scope.search.companyInfo.companySeq;
		}
		
		
		//$scope.search.rfidTag = $scope.rfidTag;
		
		param = generateParam($scope.search);
		
		$window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch($scope.search)));
		
		httpGetList("/bartag/bartagHistoryList", param, $scope, $http, true, true);
		
	};

	
	$scope.selectItem = function(item, flag){
		if(flag == "company"){
			var searchCompany = '?companySeq=' + $scope.search.companyInfo.companySeq;
			$http.get('/bartag/selectSkuList' + searchCompany).success(
					function(data) {	
						$scope.styleList = angular.fromJson(data);
					}
				);
		}
	}
	
	/*$scope.init = function(flag, item){
		if(flag == "style" && $scope.search.style != item.data){
			$scope.search.style = null;
		}
	}*/
	
	$scope.customFilter = function (flag) {
	    return function (item) {
	    	if(flag == 'style'){
	    		if (item.flag == 'style')
		        {
		            return true;
		        }
	    		return false;
	    	} else if(flag == 'color'){
	    		if (item.flag == 'color' && item.rank == $scope.search.style)	//해당 스타일에 해당하는 컬러 정보만 가져오기 위해
		        {
		            return true;
		        }
	    		return false;
	    	} else if(flag == 'size'){
	    		if (item.flag == 'size' && item.rank == $scope.search.style + $scope.search.color)	//해당 스타일과 컬러에 해당하는 사이즈만 가져오기 위해
		        {
		            return true;
		        }
	    		return false;
	    	}
	    };
	};
	
	//검색창에 입력한 스타일, 컬러, 사이즈에 해당하는 태그 정보 가져오기
	$scope.selectSku = function(){
		
		if($scope.search.companyInfo == undefined){
			$scope.search.companySeq = 0;
		} else {
			$scope.search.companySeq = $scope.search.companyInfo.companySeq;
		}
		
		param = generateParam($scope.search);
		
		$http.get('/bartag/bartagOrder/bartagListBySku' + param).success(
				function(data) {	
					$scope.tagList = angular.fromJson(data);
					if($scope.tagList.list.length==undefined)
						alert("해당 스타일, 컬러, 사이즈에 맞는 태그 정보가 없습니다");
				}
			);
	}
	
	//조회된 select창 값을 선택했을때 해당 값이 텍스트 박스에 나타나게
	$scope.fillRfidTag = function(){
		$scope.search.rfidTag = $scope.search.rfidTagPre;
	}
	
	//태그 직접 입력을 위해 텍스트 박스 클릭시 검색창이 모두 초기화
	$scope.searchReset = function(){
		$scope.search = null;
	}
	
}]);
