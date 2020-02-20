app.controller('batchTriggerListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("jobName", "batchTriggerSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	//$window.sessionStorage.setItem("subCurrent", angular.toJson(setSearch($scope.search)));
	
	$scope.startDate = initDate($scope.search.startDate);
	$scope.endDate = initDate($scope.search.endDate);
	
	if($scope.search.status == undefined){
		$scope.search.status = "all";
	}
	
	if($scope.search.type == undefined){
		$scope.search.type = "all";
	}

	if($scope.search.defaultDate == undefined){
		$scope.search.defaultDate = "1";
	}
	
	if($scope.search.size == undefined){  
		$scope.search.size = "10"; 
	}
	
	var param = generateParam($scope.search);
	
	httpGetList("/batchTrigger", param, $scope, $http, true);
	
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
		
		if($scope.search.text == undefined){
	    	$scope.search.text = "";
	    }
		
		param = generateParam($scope.search);
		
		httpGetList("/batchTrigger", param, $scope, $http);
	    	
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
		
		httpGetList("/batchTrigger", param, $scope, $http);
		
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
		
		httpGetList("/batchTrigger", param, $scope, $http);
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
		
		$scope.search.startDate = $scope.startDate.yyyymmdd();
		$scope.search.endDate = $scope.endDate.yyyymmdd();
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/batchTrigger", param, $scope, $http);
	};
	
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/batchTrigger", param, $scope, $http);
	};
	
	
	$scope.detail = function(obj){
		detailPopOpen($uibModal, obj, "/batchTrigger/batchTriggerDetailPop", "batchTriggerDetailPopController", "xxlg");
	};
}]);


app.controller('batchTriggerDetailPopController', ['$scope', '$http', '$location', '$rootScope', '$interval', '$routeParams', 'verificationFactory', '$filter', '$uibModalInstance', 'obj', '$uibModal',
	function ($scope, $http, $location, $rootScope, $interval, $routeParams, verificationFactory, $filter, $uibModalInstance, obj, $uibModal) {

	$scope.data = obj;
	
	if($scope.data.type == "1" || $scope.data.type == "4"){
		
		var fileList = $scope.data.explanatory.split(",");
		
		$scope.data.fileList = [];
		
		fileList.forEach(function(fileName){
			$scope.data.fileList.push(fileName);
		});
	}
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
	$scope.tooltip = {
		templateUrl	: '/batchTrigger/batchTriggerDetailPopTooltip',
		title		: '결과 로그'
	};
	
}]);