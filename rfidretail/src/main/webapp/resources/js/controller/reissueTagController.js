app.controller('reissueTagGroupListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', '$routeParams', 'FileSaver', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal, $routeParams, FileSaver) {
	
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
	
	$scope.type = $routeParams.type;
	$scope.search.type = $routeParams.type;
	
	var param;
	
	$http.get('/company/getCompanyList').success(
			function(data) {
				$scope.tempCompanyList = angular.fromJson(data);
					
				$scope.companyList = [];
				
				angular.forEach($scope.tempCompanyList, function(value, key) {
					if($scope.tempCompanyList[key].type == '3' || $scope.tempCompanyList[key].type == '5' || $scope.tempCompanyList[key].type == '6'){
						$scope.companyList.push($scope.tempCompanyList[key]);
					}
				});
				
				if($rootScope.userRole == 'production' || $rootScope.userRole == 'sales' || $rootScope.userRole == 'special'){
					$scope.search.companyInfo = $rootScope.user.principal.companyInfo;
				}
				
				if($scope.search.companyInfo == undefined){
					$scope.search.companySeq = 0;
				} else {
					$scope.search.companySeq = $scope.search.companyInfo.companySeq;
				}
				
				param = generateParam($scope.search);
				
				httpGetList("/reissueTag/reissueTagGroup", param, $scope, $http, true, true);
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
		
		httpGetList("/reissueTag/reissueTagGroup", param, $scope, $http, true, true);
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
		
		httpGetList("/reissueTag/reissueTagGroup", param, $scope, $http, true, true);
		
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/reissueTag/reissueTagGroup", param, $scope, $http, true, true);
		
	};
	
	$scope.format = 'yyyyMMdd';
	
	if($rootScope.userRole == 'production' || $rootScope.userRole == 'sales' || $rootScope.userRole == 'special'){
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
		
		httpGetList("/reissueTag/reissueTagGroup", param, $scope, $http, true, true);
	};
	
	$scope.detail = function(obj){
		
		$scope.search.companySeq = obj.companySeq;
		
		$window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch($scope.search)));
		$window.sessionStorage.removeItem("current");
		
		$location.url("/reissueTag/reissueTagList?createDate=" + obj.createDate + "&companySeq=" + obj.companySeq + "&type=" + $scope.type);
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
		
		httpGetList("/reissueTag/reissueTagGroup", param, $scope, $http, true, true);
	};
	
	/**
	 * 태그 재발행 요청(발행업체)
	 */
	$scope.openReissueModal = function(){
		
		if($scope.search.companyInfo == undefined){
			modalOpen($uibModal,  "업체를 선택해주세요.");
			return;
		}
			
		var reissueTagModalInstance = $uibModal.open({
	    	animation: true,
	    	ariaLabelledBy: 'modal-title',
	    	ariaDescribedBy: 'modal-body',
	    	templateUrl: '/customTemplate/customReissueTag',
	    	controller: 'reissueTagPopupController',
	    	size: 'xlg',
	    	resolve: {
	    		companyInfo: function () {
	    			return $scope.search.companyInfo;
	    		}
	    	}
	    });
		reissueTagModalInstance.result.then(function (selectedItem) {
//	        $ctrl.selected = selectedItem;
			httpGetList("/reissueTag/reissueTagGroup", param, $scope, $http);
	    }, function () {
//	        $log.info('Modal dismissed at: ' + new Date());
	    });
	};
	
	/**
	 * 태그 재발행(물류센터)
	 */
	$scope.openReissuePrintModal = function(){
		
		var count = 0;
		var objList = [];
		var confirmFlag = false;
		var completeFlag = false;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				count ++;
				
				if(($scope.list[key].stat1Count * 1) > 0){
					confirmFlag = true;
				}
				
				var obj = {
					createDate : $scope.list[key].createDate,
					companySeq : $scope.list[key].companySeq
				}
				objList.push(obj);
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 재발행 요청 정보가 없습니다.");
			return;
		}
		
		if(confirmFlag){
			modalOpen($uibModal,  "확정되지 않은 재발행 요청이 있습니다.");
			return;
		}
		
		$http({
			method : 'POST',
			url : '/reissueTag/detailList',
			data : objList
		}).success(function(data, status, headers, config){
			
			var reissueTagPrintModalInstance = $uibModal.open({
		    	animation: true,
		    	ariaLabelledBy: 'modal-title',
		    	ariaDescribedBy: 'modal-body',
		    	templateUrl: '/customTemplate/customReissueTagPrint',
		    	controller: 'reissueTagPrintPopupController',
		    	size: 'xlg',
		    	resolve: {
		    		companyInfo: function () {
		    			return $scope.search.companyInfo;
		    		},
		    		objList : function () {
		    			return data;
		    		}
		    	}
		    });
			
			reissueTagPrintModalInstance.result.then(function (selectedItem) {
//		        $ctrl.selected = selectedItem;
				httpGetList("/reissueTag/reissueTagGroup", param, $scope, $http);
		    }, function () {
//		        $log.info('Modal dismissed at: ' + new Date());
		    });
		}).error(function(){
			modalOpen($uibModal,  "에러 발생");
		});
			
	};
	
	/** 태그재발행결과 일괄업로드 */
	$scope.fileUploadPopup = function(){
		
		var modalInstance = $uibModal.open({
	    	animation: true,
	    	ariaLabelledBy: 'modal-title',
	    	ariaDescribedBy: 'modal-body',
	    	templateUrl: '/customTemplate/customFileUploadReissue',
	    	controller: 'fileUploadReissuePopupController',
	    	size: 'lg'
	    });
	    modalInstance.result.then(function (selectedItem) {
//	        $ctrl.selected = selectedItem;
	    }, function () {
//	        $log.info('Modal dismissed at: ' + new Date());
	    });
	};
	
	/*
	 * 체크된 재발행 요청만 엑셀 다운로드
	 * 아래 소스를 이용하여 비동기로 다운로드 가능, 한글명은 script에서 처리  
	 */
	$scope.reissueGroupExcelDownload = function(){
		
		var checkList = new Array();
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				checkList.push($scope.list[key]);
			}
		});
		
		if(checkList.length == 0){
			modalOpen($uibModal,  "선택된 재발행 요청 작업 정보가 없습니다.");
			return;
		}
		
		$http({
	    	method : 'POST',
	    	data : checkList,
	    	url : '/reissueTag/reissueGroupExcelDownload',
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
	            defaultFileName = defaultFileName + "_바택재발행정보엑셀다운로드.xls";
	            
	    		var file = new Blob([data], { type: "application/vnd.ms-excel;charset=charset=utf-8" });
	    		FileSaver.saveAs(file, defaultFileName);
	    		
	    		$scope.allCheckFlag = false;
	    		
	    	} else {
	    		modalOpen($uibModal,  "에러 발생");
	    	}
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
	};
	
	/** 
	 * 재발행 요청 작업 그룹 확정
	 */
	$scope.confirmGroup = function(){
		
		var count = 0;
		var checkList = [];
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				count ++;
				
				checkList.push($scope.list[key]);
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 재발행 요청 작업이 없습니다.");
			return;
		}
		
		
		confirmOpen($uibModal, "이미 확정, 완료된 재발행요청은 확정되지 않습니다. 선택된 재발행 요청 작업을 확정 하시겠습니까?", function(b){
    		
			if(b){
				
				$http({
					method : 'PUT',
					url : "/reissueTag/reissueTagGroup",
					data : checkList,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
				    	modalOpen($uibModal,  "선택된 재발행 요청 작업을 확정 완료되었습니다.", function() {
				    		
				    		httpGetList("/reissueTag/reissueTagGroup", param, $scope, $http, true, true);
				    		
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
	 * 재발행 요청 작업 그룹 삭제
	 */
	$scope.deleteGroup = function(){
		
		var count = 0;
		var checkList = [];
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				count ++;
				
				checkList.push($scope.list[key]);
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 재발행 요청 작업이 없습니다.");
			return;
		}
		
		
		confirmOpen($uibModal, "이미 확정, 완료된 재발행요청은 삭제되지 않습니다. 선택된 재발행 요청 작업을 삭제 하시겠습니까?", function(b){
    		
			if(b){
				
				$http({
					method : 'DELETE',
					url : "/reissueTag/reissueTagGroup",
					data : checkList,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
				    	modalOpen($uibModal,  "선택된 재발행 요청 작업을 삭제 완료되었습니다.", function() {
				    		
				    		httpGetList("/reissueTag/reissueTagGroup", param, $scope, $http, true, true);
				    		
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
}]);

app.controller('reissueTagListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$routeParams', 'FileSaver', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $routeParams, FileSaver, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("boxNum", "rfidTagReissueRequestSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	if($scope.search.confirmYn == undefined){
		$scope.search.confirmYn = "all";
	}
	
	if($scope.search.completeYn == undefined){
		$scope.search.completeYn = "all";
	}
	
	$scope.search.createDate = $routeParams.createDate;
	$scope.search.companySeq = $routeParams.companySeq;
	$scope.type = $routeParams.type;
	
	if($scope.search.size == undefined){  $scope.search.size = "10"; }
	
	var param;
	
	param = generateParam($scope.search);
	
	httpGetList("/reissueTag", param, $scope, $http, true);
	
	$http.get('/reissueTag/countAll' + param).success(
		function(data) {
			$scope.countAll = angular.fromJson(data);
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
		
		httpGetList("/reissueTag", param, $scope, $http, true);
		
		$http.get('/reissueTag/countAll' + param).success(
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
		
		httpGetList("/reissueTag", param, $scope, $http, true);
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/reissueTag", param, $scope, $http, true);
		
		$http.get('/reissueTag/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
		});
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
		
		httpGetList("/reissueTag", param, $scope, $http, true);
		
		$http.get('/reissueTag/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
		});
	};
	
	$scope.detail = function(obj){
		
//		$window.sessionStorage.setItem("current", angular.toJson(setSearch($scope.search)));
//		$window.sessionStorage.removeItem("subCurrent");
//		
//		$location.url("/reissueTag/reissueTagDetail?seq=" + obj.rfidTagReissueRequestSeq);
		
		detailPopOpen($uibModal, obj, "/reissueTag/reissueTagDetailPop", "reissueTagDetailPopController", "xxlg");
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
		
		httpGetList("/reissueTag", param, $scope, $http, true);
		
		$http.get('/reissueTag/countAll' + param).success(
			function(data) {
				$scope.countAll = angular.fromJson(data);
		});
	};
	
	/*
	 * 체크된 재발행 요청만 엑셀 다운로드
	 * 아래 소스를 이용하여 비동기로 다운로드 가능, 한글명은 script에서 처리  
	 */
	$scope.reissueExcelDownload = function(){
		
		var checkList = new Array();
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				checkList.push($scope.list[key]);
			}
		});
		
		if(checkList.length == 0){
			modalOpen($uibModal,  "선택된 재발행 요청 정보가 없습니다.");
			return;
		}
		
		$http({
	    	method : 'POST',
	    	data : checkList,
	    	url : '/reissueTag/reissueExcelDownload',
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
	            defaultFileName = defaultFileName + "_바택재발행정보엑셀다운로드.xls";
	            
	    		var file = new Blob([data], { type: "application/vnd.ms-excel;charset=charset=utf-8" });
	    		FileSaver.saveAs(file, defaultFileName);
	    		
	    		$scope.allCheckFlag = false;
	    		
	    	} else {
	    		modalOpen($uibModal,  "에러 발생");
	    	}
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
	};
	
	/** 
	 * 재발행 요청 작업 그룹 확정
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
			modalOpen($uibModal,  "선택된 재발행 요청이 없습니다.");
			return;
		}
		
		if(confirmFlag){
			modalOpen($uibModal,  "이미 확정된 재발행 요청이 있습니다.");
			return;
		}
		
		if(completeFlag){
			modalOpen($uibModal,  "이미 완료된 재발행 요청이 있습니다.");
			return;
		}
		
		confirmOpen($uibModal, "선택된 재발행 요청을 확정 하시겠습니까?", function(b){
    		
			if(b){
				
				$http({
					method : 'PUT',
					url : "/reissueTag",
					data : checkList,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
				    	modalOpen($uibModal,  "선택된 재발행 요청을 확정 완료되었습니다.", function() {
				    		
				    		httpGetList("/reissueTag", param, $scope, $http, true);
				    		
				    		$http.get('/reissueTag/countAll' + param).success(
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
	 * 재발행 요청 삭제
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
			modalOpen($uibModal,  "선택된 재발행 요청이 없습니다.");
			return;
		}
		
		if(confirmFlag){
			modalOpen($uibModal,  "이미 확정된 재발행 요청이 있습니다.");
			return;
		}
		
		if(completeFlag){
			modalOpen($uibModal,  "이미 완료된 재발행 요청이 있습니다.");
			return;
		}
		
		confirmOpen($uibModal, "선택된 재발행 요청을 삭제 하시겠습니까?", function(b){
    		
			if(b){
				
				$http({
					method : 'DELETE',
					url : "/reissueTag",
					data : checkList,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
				    	modalOpen($uibModal,  "선택된 재발행 요청을 삭제 완료되었습니다.", function() {
				    		
				    		httpGetList("/reissueTag", param, $scope, $http, true);
				    		
				    		$http.get('/reissueTag/countAll' + param).success(
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
	
}]);

app.controller('reissueTagDetailPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', '$uibModal', 'FileSaver', 'Blob', '$uibModalInstance', 'obj',
	function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModal, FileSaver, Blob, $uibModalInstance, obj) {

	$scope.ajaxFinish = false;
	
	var seq = obj.rfidTagReissueRequestSeq;
	
	$http.get('/reissueTag/' + seq).success(
		function(data) {
			$scope.obj = angular.fromJson(data);
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
	    
	    angular.forEach(obj.storageDetailLog, function(value, key) {
	    	totalAmount += obj.storageDetailLog[key].amount;
		});
	    return totalAmount;
	}
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);