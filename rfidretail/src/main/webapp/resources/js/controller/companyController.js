app.controller('companyAddPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory', '$filter', '$uibModalInstance', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $filter, $uibModalInstance, $uibModal) {
	
	$scope.ajaxFinish = true;
	
	$scope.data = {
		email : "",
		role : "admin",
		useYn : "Y",
		type : "1"
	}
	
	$scope.companyTypeList = $filter('filter')($rootScope.parentCodeList, {codeValue: '10002'})[0];
    $scope.companyRoleList = $filter('filter')($rootScope.parentCodeList, {codeValue: '10003'})[0];
	
	$scope.clickSubmit = function(){
		
		$http({
			method: 'POST', 
			url: '/company',
			data: $scope.data,
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
		})
		.success(function(data, status, headers, config) {
			
			if(status == 200) {
				
				modalOpen($uibModal,  "업체 추가 완료되었습니다.", function(){
					$uibModalInstance.close("ok");
				});
			} else {
				modalOpen($uibModal,  "에러발생");
			}
		})
		.error(function(data, status, headers, config) {
			
			if(status == 409){
				modalOpen($uibModal,  "중복된 코드 값이 있습니다.");
			};
			console.log(status);
		});
	};
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};

}]);


app.controller('companyListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("name", "companySeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	if($scope.search.type == undefined){
		$scope.search.type = "all";
	}
	
	if($scope.search.size == undefined){  
		$scope.search.size = "10"; 
	}
	
	var param = generateParam($scope.search);
	
	httpGetList("/company", param, $scope, $http, true);
	
	$http.get('/company/getCompanyList').success(
		function(data) {
			$scope.companyList = angular.fromJson(data);
					
			if($scope.search.searchYn){
				angular.forEach($scope.companyList, function(value, key) {
					if($scope.companyList[key].companySeq == $scope.search.companySeq){
						$scope.search.companyInfo = $scope.companyList[key];
					}
				});
			}
				
			$scope.ajaxFinish = true; 
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
		
		httpGetList("/company", param, $scope, $http);
	    	
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
		
		httpGetList("/company", param, $scope, $http);
		
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/company", param, $scope, $http);
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
		
		$scope.search.searchYn = true;
		
		httpGetList("/company", param, $scope, $http);
		
	};
	
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/company", param, $scope, $http);
	};
	
	$scope.detail = function(obj){
		
//		$window.sessionStorage.setItem("current", angular.toJson(setSearch($scope.search)));
//		
//		$location.url("/company/companyDetail?seq=" + obj.companySeq);
		
		var modalInstance = $uibModal.open({
			animation: true,
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: '/company/companyDetailPop',
			controller: 'companyDetailPopController',
			size: 'xlg',
			resolve: {
				obj: function () {
					return obj;
				}
			}
		});
		modalInstance.result.then(function (callback) {
			if(callback == "ok"){
				httpGetList("/company", param, $scope, $http);
			}
		}, function () {
			
		});
	};
	
	$scope.goAdd = function(){
		var modalInstance = $uibModal.open({
			animation: true,
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: '/company/companyAddPop',
			controller: 'companyAddPopController',
			size: 'xlg'
		});
		modalInstance.result.then(function (callback) {
			if(callback == "ok"){
				httpGetList("/company", param, $scope, $http);
			}
		}, function () {
			
		});
	};
	
	$scope.$watch(function(){
		
		return $rootScope.parentCodeList;
		
		}, function() {
			
			if($rootScope.parentCodeList != undefined){
				
				$scope.roleList = $filter('filter')($rootScope.parentCodeList, {codeValue: '10003'})[0];
				$scope.typeList = $filter('filter')($rootScope.parentCodeList, {codeValue: '10002'})[0];
			} 
	}, true);
}]);

app.controller('companyDetailPopController', ['$scope', '$http', '$location', '$rootScope', '$interval', '$routeParams', 'verificationFactory', '$filter', '$uibModalInstance', 'obj', '$uibModal', function ($scope, $http, $location, $rootScope, $interval, $routeParams, verificationFactory, $filter, $uibModalInstance, obj, $uibModal) {
	
	$scope.data = obj;
	$scope.companyTypeList = $filter('filter')($rootScope.parentCodeList, {codeValue: '10002'})[0];
    $scope.companyRoleList = $filter('filter')($rootScope.parentCodeList, {codeValue: '10003'})[0];
	
    $scope.mod = function(){
    	
    	$scope.data.userInfo = [];
    	
    	$http({
			method: 'PUT', 
			url: '/company',
			data: $scope.data,
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
		})
		.success(function(data, status, headers, config) {
			if(status == 200) {
				modalOpen($uibModal,  "업체 수정 완료되었습니다.", function(){
					$uibModalInstance.close("ok");
				});
				
			} else {
				modalOpen($uibModal,  "에러발생");
			}
		})
		.error(function(data, status, headers, config) {
			
			if(status == 409){
				modalOpen($uibModal,  "중복된 코드 값이 있습니다.");
			};
			
			console.log(status);
		});
		
	}; 
	
	$scope.del = function(){
		
		$http({
			method: 'DELETE', 
			url: '/company',
			data: $scope.data,
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
		})
		.success(function(data, status, headers, config) {
			if(status == 200) {
				modalOpen($uibModal,  "업체 삭제 완료되었습니다.", function(){
					$uibModalInstance.close("ok");
				});
			} else {
				modalOpen($uibModal,  "에러발생");
			}
		})
		.error(function(data, status, headers, config) {
			console.log(status);
		});
		
	};
	
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);