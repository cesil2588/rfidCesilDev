app.controller('mailController', ['$scope', '$http', '$location', '$rootScope', '$interval', '$uibModal', function ($scope, $http, $location, $rootScope, $interval, $uibModal) {
	
	$scope.ajaxFinish = true;
	
	$scope.data = {
		mailSend : "eschoi@systemk.kr"
	};
	
	$scope.goMailAction = function(){
		
		if($scope.data.mailSend && $scope.data.mailTitle && $scope.data.mailContents){
			$http({
				method: 'POST', 
				url: '/mail/mailSendAction',
				data: $scope.data,
				headers: {'Content-Type': 'application/json; charset=utf-8'} 
			})
			.success(function(){
		    	modalOpen($uibModal,  "전송완료");
			})
			.error(function(data, status, headers, config){
				modalOpen($uibModal,  "에러발생");
			});
		}
	};
	
}]);