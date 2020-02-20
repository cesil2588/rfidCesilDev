app.controller('appListController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory', '$window', 'uibDateParser', 'FileSaver', 'Blob', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, uibDateParser, FileSaver, Blob, $uibModal) {
	
	$scope.ajaxFinish = false; 
	$scope.allCheckFlag = false;
	
	$scope.search = initSearch("fileName", "appSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	$scope.startDate = initDate($scope.search.startDate);
	$scope.endDate = initDate($scope.search.endDate);
	
	if($scope.search.defaultDate == undefined){
		$scope.search.defaultDate = "1";
	}
	
	if($scope.search.type == undefined){
		$scope.search.type = "all";
	}
	
	if($scope.search.representYn == undefined){
		$scope.search.representYn = "all";
	}
	
	if($scope.search.size == undefined){  
		$scope.search.size = "10"; 
	}
	
	var param = generateParam($scope.search);
	
	httpGetList("/app", param, $scope, $http, true);
	
	var settingDate = new Date();		
	
	if($scope.startDate == ""){
		settingDate.setMonth(settingDate.getMonth()-1);
		$scope.startDate = settingDate;
	}
	
	if($scope.endDate == ""){
		var today = new Date();
		$scope.endDate = today;
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
		
		param = generateParam($scope.search);
		
		httpGetList("/app", param, $scope, $http);
	    
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
		
		httpGetList("/app", param, $scope, $http);
		
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
		
		httpGetList("/app", param, $scope, $http);
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
		
		$scope.search.startDate =  $scope.startDate.yyyymmdd();
		$scope.search.endDate =  $scope.endDate.yyyymmdd();
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/app", param, $scope, $http);
		
	};
	
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/app", param, $scope, $http);
	};
	
	/** App 상세보기 */
	$scope.goDetail = function(obj){
//		$window.sessionStorage.setItem("current", angular.toJson(setSearch($scope.search)));
//		$window.sessionStorage.removeItem("subCurrent");
//		
//		$location.url("/app/appDetail?seq=" + obj.appSeq);
		
		var modalInstance = $uibModal.open({
			animation: true,
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: '/app/appDetailPop',
			controller: 'appDetailPopController',
			size: 'xlg',
			resolve: {
				obj: function () {
					return obj;
				}
			}
		});
		modalInstance.result.then(function (callback) {
			if(callback == "ok"){
				httpGetList("/app", param, $scope, $http);
			}
		}, function () {
			
		});
	};
	
	$scope.goAdd = function(){
//		$location.url("/app/appAdd");
		
		var modalInstance = $uibModal.open({
			animation: true,
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: '/app/appAddPop',
			controller: 'appAddPopController',
			size: 'xlg'
		});
		modalInstance.result.then(function (callback) {
			if(callback == "ok"){
				httpGetList("/app", param, $scope, $http);
			}
		}, function () {
			
		});
	};
}]);

app.controller('appAddPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', '$uibModal', 'FileSaver', 'Blob', '$uibModalInstance', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModal, FileSaver, Blob, $uibModalInstance) {

	$scope.ajaxFinish = true;
	
	$scope.data = {
		type : "1",
		representYn : "Y",
		useYn : "Y"
	};
	
	/** 앱 등록 */
	$scope.add = function(){
		
		if($scope.uploadResult == "" || $scope.uploadResult == undefined){
			modalOpen($uibModal,  "파일 업로드를 마무리해주세요.");
			return;
		}
		
		if($scope.data.version == "" || $scope.data.version == undefined){
			modalOpen($uibModal,  "버전을 입력해주세요.");
			return;
		}
		
		$scope.data.fileName = $scope.uploadResult.fileName;
		$scope.data.downloadCount = 0;
		
		$http({
			method : 'POST',
			url : '/app',
			data : $scope.data,
			headers : {
				'Content-Type' : 'application/json; charset=utf-8'
			}
		}).success(function(data, status, headers, config) {
			if (status == 200) {
				modalOpen($uibModal,  "어플리케이션 추가 완료되었습니다.", function() {
					$uibModalInstance.close("ok");
				});
			} else {
				modalOpen($uibModal,  "에러발생");
			}
		}).error(function(data, status, headers, config) {
			
			if(status == 406){
				modalOpen($uibModal,  "대표 어플리케이션이 없습니다. 대표 설정을 확인해주세요.");
			} else {
				modalOpen($uibModal,  "에러발생");
			}
		});
	};
	
	/** PDA App apk 업로드 */
	$scope.doUploadFile = function(){
		
		if($scope.uploadedFile == undefined){
			return;
		}
		
		var file = $scope.uploadedFile;
	    var url = "/storage";
	       
	    var data = new FormData();
	    data.append('uploadfile', file);
	    
	    var config = {
	    	transformRequest: angular.identity,
	    	transformResponse: angular.identity,
		   	headers : {
		   		'Content-Type': undefined
		   	}
	    }
	     
	    $http.post(url, data, config)
	    .success(function(data, status, headers, config) {
	    	$scope.uploadResult = angular.fromJson(data);
	    }).error(function(data, status, headers, config) {
			modalOpen($uibModal,  "에러발생");
		});
	};
	
	$scope.delFile = function(){
		$scope.uploadResult = "";
	}
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
}]);

app.controller('appDetailPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', '$uibModal', 'FileSaver', 'Blob', '$uibModalInstance', 'obj', 
	function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModal, FileSaver, Blob, $uibModalInstance, obj) {

	$scope.data = obj;
	
	$scope.uploadResult = {
		fileName : $scope.data.fileName
	}
	
	/** 앱 수정 */
	$scope.mod = function(){
		
		if($scope.uploadResult == "" || $scope.uploadResult == undefined){
			modalOpen($uibModal,  "파일 업로드를 마무리해주세요.");
			return;
		}
		
		if($scope.data.version == "" || $scope.data.version == undefined){
			modalOpen($uibModal,  "버전을 입력해주세요.");
			return;
		}
		
		$scope.data.fileName = $scope.uploadResult.fileName;
		
		$http({
			method : 'PUT',
			url : '/app',
			data : $scope.data,
			headers : {
				'Content-Type' : 'application/json; charset=utf-8'
			}
		}).success(function(data, status, headers, config) {
			if (status == 200) {
				modalOpen($uibModal,  "어플리케이션 수정 완료되었습니다.", function() {
					$uibModalInstance.close("ok");
				});
			} else {
				modalOpen($uibModal,  "에러발생");
			}
		}).error(function(data, status, headers, config) {
			
			if(status == 406){
				modalOpen($uibModal,  "대표 어플리케이션이 없습니다. 대표 설정을 확인해주세요.");
			} else {
				modalOpen($uibModal,  "에러발생");
			}
		});
	};
	
	/** PDA App apk 업로드 */
	$scope.doUploadFile = function(){
		
		if($scope.uploadedFile == undefined){
			return;
		}
		
		var file = $scope.uploadedFile;
	    var url = "/storage";
	       
	    var data = new FormData();
	    data.append('uploadfile', file);
	    
	    var config = {
	    	transformRequest: angular.identity,
	    	transformResponse: angular.identity,
		   	headers : {
		   		'Content-Type': undefined
		   	}
	    }
	       
	    $http.post(url, data, config)
	    .success(function(data, status, headers, config) {
	    	$scope.uploadResult = angular.fromJson(data);
	    }).error(function(data, status, headers, config) {
			modalOpen($uibModal,  "에러발생");
		});
	};
	
	$scope.delFile = function(){
		$scope.uploadResult = "";
	}
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
}]);

app.controller('mobileDownloadController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', '$uibModal', 'FileSaver', 'Blob', 'deviceDetector',
	function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModal, FileSaver, Blob, deviceDetector) {

	$scope.ajaxFinish = false;
	
	$rootScope.mobileYn = true;
	
	$scope.appType = "";
	
	if($rootScope.userRole == 'production'){
		$scope.appType = "1";
	} else if($rootScope.userRole == 'distribution'){
		$scope.appType = "2";
	} else if($rootScope.userRole == 'sales' || $rootScope.userRole == 'special'){
		$scope.appType = "3";
	} else {
		modalOpen($uibModal,  "해당 사용자는 PDA 다운로드 화면에 접속 할 수 없습니다.", function() {
			$scope.logout();
		});
	}
	
	$http.get('/app/represent?appType=' + $scope.appType).success(
		function(data) {
			
			$scope.data = angular.fromJson(data);
			
			$scope.uploadResult = {
				data : $scope.data.fileName
			};
			
			$scope.ajaxFinish = true;
	});
	
	$scope.downloadLog = function(fileName) {
		
		if($scope.appType == ""){
			
			modalOpen($uibModal,  "해당 사용자는 PDA 앱 다운로드를 할 수 없습니다.", function() {
				$scope.logout();
				return;
			});
		}
		
		$http({
			method : 'POST',
			url : '/app/download/' + $scope.data.appSeq,
			headers : {
				'Content-Type' : 'application/json; charset=utf-8'
			}
		})
	}
}]);