app.controller('codeDetailController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory', '$uibModal',
				function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.deleteFlag = false;
	
	$http.get('/code').success(
		function(data) {
			$scope.parentCodeList = angular.fromJson(data);
			
			angular.forEach($scope.parentCodeList, function(value, key) {
				if($scope.parentCodeList[key].codeInfo.length > 1){
					angular.forEach($scope.parentCodeList[key].codeInfo, function(v, k) {
						$scope.parentCodeList[key].codeInfo[k].parentCodeInfo = $scope.parentCodeList[key].codeInfo[0].parentCodeInfo;
					});
				}
			});
			
			$scope.ajaxFinish = true;
		}
	);
	
	$scope.addParentForm = function(){
		
		$scope.deleteFlag = false;
		
		$scope.data = {
			action : "POST",
			status : "parent",
			useYn  : "Y"
		};
	};
	
	$scope.addForm = function(){
		
		$scope.data = angular.copy($scope.currentNode);
		
		if($scope.data != undefined){
			$scope.data.action = "PUT";
			
			if($scope.data.codeSeq && !$scope.data.category){
				$scope.data.status = "child";
			} else {
				$scope.data.status = "parent";
			}
		} 
		
		if($scope.data != undefined && $scope.data.status == "child"){
			
			$scope.deleteFlag = false;
			
			$scope.data = {
				action : "POST",
				status : "child",
				useYn  : "Y",
				parentCodeInfo : $scope.data.parentCodeInfo
			};
		} else if($scope.data != undefined && $scope.data.status == "parent"){
			
			$scope.deleteFlag = false;
			
			var tempParentCodeInfo = "";
			
			angular.forEach($scope.parentCodeList, function(value, key) {
				if($scope.parentCodeList[key].parentCodeSeq == $scope.data.parentCodeSeq){
					if($scope.parentCodeList[key].codeInfo.length > 0){
						angular.forEach($scope.parentCodeList[key].codeInfo, function(v, k) {
							tempParentCodeInfo = $scope.parentCodeList[key].codeInfo[0].parentCodeInfo;
						});
					} else {
						tempParentCodeInfo = $scope.parentCodeList[key];
					}
				}
			});
			
			$scope.data = {
				action : "POST",
				status : "child",
				useYn  : "Y",
				parentCodeInfo : tempParentCodeInfo
			};
		}
	};
	
	$scope.add = function(){
		
		if($scope.data != undefined && $scope.data.status == "child"){
			// 자식 코드
			
			$http({
				method: $scope.data.action, 
				url: '/code/child',
				data: $scope.data,
				headers: {'Content-Type': 'application/json; charset=utf-8'} 
			})
			.success(function(data, status, headers, config) {
				
				if(status == 200) {
					modalOpen($uibModal,  (returnMessage($scope.data)), function(){
						$rootScope.reload();
					});
				} else {
					modalOpen($uibModal,  "에러발생");
				}
			})
			.error(function(data, status, headers, config) {
				console.log(status);
			});
			
		} else if($scope.data != undefined && $scope.data.status == "parent"){
			// 부모 코드
			if($scope.data.action == "PUT"){
				
				if($scope.data.codeInfo.length > 0){
					angular.forEach($scope.data.codeInfo, function(value, key) {
						$scope.data.codeInfo[key] = null;
					});
				}
			}
			
			$http({
				method: $scope.data.action, 
				url: "/code",
				data: $scope.data,
				headers: {'Content-Type': 'application/json; charset=utf-8'} 
			})
			.success(function(data, status, headers, config) {
				
				if(status == 200) {
					modalOpen($uibModal,  (returnMessage($scope.data)), function(){
						$rootScope.reload();
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
		}
	};
	
	$scope.del = function(){
		
		$scope.data.action = "DELETE";
		
		if($scope.data != undefined && $scope.data.status == "child"){
			// 자식 코드
			
			confirmOpen($uibModal, "삭제하시겠습니까?", function(confirm){
				if(confirm){
					$http({
						method: $scope.data.action, 
						url: '/code/child',
						data: $scope.data,
						headers: {'Content-Type': 'application/json; charset=utf-8'} 
					})
					.success(function(data, status, headers, config) {
						
						if(status == 200) {
							modalOpen($uibModal,  (returnMessage($scope.data)), function(){
								$rootScope.reload();
							});
						} else {
							modalOpen($uibModal,  "에러발생");
						}
					})
					.error(function(data, status, headers, config) {
						console.log(status);
					});
				}
			});
			
			
		} else if($scope.data != undefined && $scope.data.status == "parent"){
			
			confirmOpen($uibModal, "삭제하시겠습니까?", function(confirm){
				
				if(confirm){
					
					angular.forEach($scope.data.codeInfo, function(value, key) {
						$scope.data.codeInfo[key] = null;
					});
					
					// 부모 코드
					$http({
						method: $scope.data.action, 
						url: '/code',
						data: $scope.data,
						headers: {'Content-Type': 'application/json; charset=utf-8'} 
					})
					.success(function(data, status, headers, config) {
						
						if(status == 200) {
							modalOpen($uibModal,  (returnMessage($scope.data)), function(){
								$rootScope.reload();
							});
						} else {
							modalOpen($uibModal,  "에러발생");
						}
					})
					.error(function(data, status, headers, config) {
						console.log(status);
					});
				}
			});
		}
		
	};

	$scope.$watch(function(){
		
		return $scope.currentNode;
		
		}, function() {
			
			$scope.data = angular.copy($scope.currentNode);
			
			if($scope.data != undefined){
				$scope.data.action = "PUT";
				
				if($scope.data.codeSeq){
					$scope.data.status = "child";
				} else {
					$scope.data.status = "parent";
				}
				
				$scope.deleteFlag = true;
			} 
		
	}, true);
	
	function returnMessage (obj){
		var message = "자식 코드";
		if(obj.status == "parent"){
			message = "부모 코드";
		}
		if(obj.action == "POST"){
			message = message + " 추가 완료되었습니다.";
		} else if(obj.action == "PUT"){
			message = message + " 수정 완료되었습니다.";
		} else if(obj.action == "DELETE"){
			message = message + " 삭제 완료되었습니다.";
		}
		return message;
	}
}]);