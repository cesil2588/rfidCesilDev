app.controller('productMasterListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("style", "productSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	if($scope.search.productSeason == undefined){
		$scope.search.productSeason = "all";
	}
	
	if($scope.search.style == undefined){
		$scope.search.style = "all";
	}
	
	var param = generateParam($scope.search);
	
	httpGetList("/productMaster", param, $scope, $http, true);
	
	$http.get('/productMaster/selectList').success(
		function(data) {
			$scope.selectList = angular.fromJson(data);
			
			var flag = true;
			angular.forEach($scope.selectList, function(value, key) {
				if($scope.selectList[key].flag == "productYy"){
					if(flag){
						$scope.search.productYy = $scope.selectList[key].data;
						flag = false;
					}
				}
			});
			
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
		
		httpGetList("/productMaster", param, $scope, $http);
	    	
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
		
		httpGetList("/productMaster", param, $scope, $http);
		
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/productMaster", param, $scope, $http);
	};
	
	$scope.detail = function(obj){
		
//		$window.sessionStorage.setItem("current", angular.toJson(setSearch($scope.search)));
//		
//		$location.url("/productMaster/productDetail?seq=" + obj.productSeq);
		
		detailPopOpen($uibModal, obj, "/productMaster/productDetailPop", "productMasterDetailPopController", "xlg");
	};
	
	$scope.$watch(function(){
		
		return $rootScope.parentCodeList;
		
		}, function() {
			
			if($rootScope.parentCodeList != undefined){
				
				$scope.productSeasonList = $filter('filter')($rootScope.parentCodeList, {codeValue: '10005'})[0];
			} 
	}, true);
	
	$scope.customFilter = function (flag) {
	    return function (item) {
	    	if(flag == 'productYy'){
	    		if (item.flag == 'productYy')
		        {
		            return true;
		        }
		        return false;
	    	} else if(flag == 'season'){
	    		if (item.flag == 'season')
		        {
		            return true;
		        }
	    		return false;
	    	} else if(flag == 'style'){
	    		if (item.flag == 'style')
		        {
		            return true;
		        }
	    		return false;
	    	} else if(flag == 'size'){
	    		if (item.flag == 'size')
		        {
		            return true;
		        }
	    		return false;
	    	}
	    };
	};
	
	/** 상단 검색 */
	$scope.headSearch = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.search.searchYn = true;
		
		httpGetList("/productMaster", param, $scope, $http);
	};
}]);

app.controller('productMasterDetailPopController', ['$scope', '$http', '$location', '$rootScope', '$interval', '$routeParams', 'verificationFactory', '$filter', '$uibModalInstance', 'obj', '$uibModal', function ($scope, $http, $location, $rootScope, $interval, $routeParams, verificationFactory, $filter, $uibModalInstance, obj, $uibModal) {
	$scope.data = obj;
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);
