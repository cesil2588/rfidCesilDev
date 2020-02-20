app.controller('batchLogListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("jobName", "batchLogSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	//$window.sessionStorage.setItem("subCurrent", angular.toJson(setSearch($scope.search)));
	
	$scope.startTime = initDate($scope.search.startTime);
	$scope.endTime = initDate($scope.search.endTime);
	
	if($scope.search.status == undefined){
		$scope.search.status = "all";
	}

	if($scope.search.defaultDate == undefined){
		$scope.search.defaultDate = "1";
	}
	
	if($scope.search.size == undefined){  
		$scope.search.size = "10"; 
	}
	
	var param = generateParam($scope.search);
	
	$http.get('/batchLog/selectAllJobName').success(
		function(data) {
			$scope.jobNameList = angular.fromJson(data);
	});
	
	httpGetList("/batchLog", param, $scope, $http, true);
	
	var settingDate = new Date();	
	
	if($scope.endTime == ""){
		var today = new Date();
		$scope.endTime = today;
	}
	
	if($scope.startTime == ""){
		settingDate.setMonth(settingDate.getMonth()-1);
		$scope.startTime = settingDate;
	}
	
	$scope.changeSearchDate = function(){
		settingDate = new Date($scope.endTime);		
		
		if($scope.search.defaultDate == "1"){
			settingDate.setMonth(settingDate.getMonth()-1);
			$scope.startTime = settingDate;
		} else if($scope.search.defaultDate == "2"){
			settingDate.setMonth(settingDate.getMonth()-3);
			$scope.startTime = settingDate;
		} else if($scope.search.defaultDate == "3"){
			settingDate.setMonth(settingDate.getMonth()-6);
			$scope.startTime = settingDate;
		} else {
			settingDate.setYear(settingDate.getFullYear()-1);
			$scope.startTime = settingDate;
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
		
		if($scope.search.text == undefined){
	    	$scope.search.text = "";
	    }
		
		param = generateParam($scope.search);
		
		httpGetList("/batchLog", param, $scope, $http);
	    	
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
		
		httpGetList("/batchLog", param, $scope, $http);
		
	};
	
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
		
		httpGetList("/batchLog", param, $scope, $http);
	};
	
	$scope.format = 'yyyyMMdd';
	
	if($rootScope.userRole != 'admin'){
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
	
	
	$scope.startTimeOpen = function() {
	    $scope.search.startTimeOpened = true;
	};
	
	$scope.endTimeOpen = function() {
	    $scope.search.endTimeOpened = true;
	};

	/** 상단 검색 */
	$scope.headSearch = function(){
		
		if($scope.startTime == undefined || $scope.startTime == ""){
			return;
		}
		
		if($scope.endTime == undefined || $scope.endTime == ""){
			return;
		}
		
		$scope.search.startTime = $scope.startTime.yyyymmdd();
		$scope.search.endTime = $scope.endTime.yyyymmdd();
		$scope.search.page = 0;
		
		if($scope.search.jobInfo == undefined){
			$scope.search.command = "";
		} else {
			$scope.search.command = $scope.search.jobInfo.command;
		}
		
		param = generateParam($scope.search);
		
		httpGetList("/batchLog", param, $scope, $http);
	};
	
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/batchLog", param, $scope, $http);
	};
	
	
	$scope.detail = function(obj){
		detailPopOpen($uibModal, obj, "/batchLog/batchLogDetailPop", "batchLogDetailPopController", "xxlg");
	};
}]);


app.controller('batchLogDetailPopController', ['$scope', '$http', '$location', '$rootScope', '$interval', '$routeParams', 'verificationFactory', '$filter', '$uibModalInstance', 'obj', '$uibModal',
	function ($scope, $http, $location, $rootScope, $interval, $routeParams, verificationFactory, $filter, $uibModalInstance, obj, $uibModal) {

	$scope.data = obj;
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
	$scope.tooltip = {
		templateUrl	: '/batchLog/batchLogDetailPopTooltip',
		title		: '에러 로그'
	};

}]);