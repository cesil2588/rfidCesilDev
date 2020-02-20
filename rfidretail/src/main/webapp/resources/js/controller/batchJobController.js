app.controller('batchJobDetailController', ['$scope', '$http', '$location', '$rootScope', '$interval', '$routeParams', '$uibModal', function ($scope, $http, $location, $rootScope, $interval, $routeParams, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.data = {
		jobType : "cron",
		useYn : "Y"
	}
	
	$scope.flag = "add";
	
	$http.get('/batchJob').success(
		function(data) {
			$scope.list = angular.fromJson(data);
			$scope.ajaxFinish = true;
		}
	);
	
	$scope.clickSubmit = function(){
		
		if($scope.data.command == undefined || $scope.data.command == ''){
			modalOpen($uibModal,  "명령어 입력을 마무리해주세요.");
			return;
		}
		
		if($scope.data.batchType == 'cron' && ($scope.data.cron == undefined || $scope.data.cron == '')){
			modalOpen($uibModal,  "크론 표현식 입력을 마무리해주세요.");
			return;
		}
		
		if($scope.data.batchType == 'delay' && ($scope.data.delay == undefined || $scope.data.delay == '')){
			modalOpen($uibModal,  "딜레이 표현식 입력을 마무리해주세요.");
			return;
		}
		
		var requestMethod;
		$scope.flag == "add" ? requestMethod = "POST" : requestMethod = "PUT";
		
		$http({
			method: requestMethod, 
			url: '/batchJob',
			data: $scope.data,
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
		})
		.success(function(data, status, headers, config) {
			
			if(status == 200) {
				var message;
				$scope.flag == "add" ? message = "배치 저장 완료되었습니다." : message = "배치 수정 완료되었습니다.";
				modalOpen($uibModal,  message, function(){
					$rootScope.reload();
				});
			} else {
				modalOpen($uibModal,  "에러발생");
			}
		})
		.error(function(data, status, headers, config) {
			console.log(status);
		});
	};
	
	$scope.changeFlag = function(batch){
		
		$http({
			method: "PUT", 
			url: '/batchJob',
			data: batch,
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
		})
		.success(function(data, status, headers, config) {
			
			if(status == 200) {
				var message;
				batch.useYn == "Y" ? message = "사용 처리되었습니다." : message = "사용안함 처리되었습니다.";
				
				modalOpen($uibModal,  message);
				
			} else {
				modalOpen($uibModal,  "에러발생");
			}
		})
		.error(function(data, status, headers, config) {
			console.log(status);
		});
	};
	
	$scope.detail = function(batch){
		$scope.flag = "mod";
		$scope.data = angular.copy(batch);
	}
	
	$scope.init = function(){
		$scope.flag = "add";
		$scope.data = {
			jobType : "cron",
			useYn : "Y"
		}
	}
	
	$scope.del = function(){
		
		confirmOpen($uibModal, "배치를 삭제하시겠습니까?", function(b){
			if(b){
				$http({
					method: 'DELETE', 
					url: '/batchJob',
					data: $scope.data,
					headers: {'Content-Type': 'application/json; charset=utf-8'} 
				})
				.success(function(data, status, headers, config) {
					
					if(status == 200) {
						modalOpen($uibModal,  "배치 삭제가 완료되었습니다.", function(){
							$rootScope.reload();
						});
					} else {
						modalOpen($uibModal,  "에러");
					}
				})
				.error(function(data, status, headers, config) {
					modalOpen($uibModal,  "에러");
					console.log(status);
				});
			}
		});
	};
	
	$scope.actionTest = function(batch){
		
		$http({
			method: 'POST', 
			url: '/batchJob/action',
			data: batch,
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
		})
		.success(function(data, status, headers, config) {
			
			if(status == 200) {
				modalOpen($uibModal,  "배치 테스트 실행이 완료되었습니다.");
			} else {
				modalOpen($uibModal,  "에러");
			}
		})
		.error(function(data, status, headers, config) {
			modalOpen($uibModal,  "에러");
			console.log(status);
		});
	};
	
}]);