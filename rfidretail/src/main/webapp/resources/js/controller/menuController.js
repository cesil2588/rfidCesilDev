app.controller('menuDetailController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal)  {
	
	$scope.ajaxFinish = false;
	
	//select box list
	$scope.typeList = ['page','popup','callapse'];
	$scope.sessionTypeList = ['current','groupCurrent'];
	$scope.locationList= ['left','top','center','right','bottom'];
	$scope.sizeList= ['xlg','sm','md','lg','xxlg'];

	//새로고침
	$scope.replace = function() {
		$http.get('/parentMenu').success(
			function(data) {
				$scope.parentMenuList = data;
				angular.forEach($scope.parentMenuList, function(value, key) {
					if($scope.parentMenuList[key].childMenu.length > 0){
						angular.forEach($scope.parentMenuList[key].childMenu, function(v, k) {
							parentMenu = angular.copy($scope.parentMenuList[key]);
							delete parentMenu.childMenu;
							$scope.parentMenuList[key].childMenu[k].parentMenu = parentMenu;
						});
					}
				});	
				$scope.ajaxFinish = true;
			}
		);
	}
	
	$scope.replace();
	
	//부모 메뉴 추가
	$scope.addParentMenu= function(){
		
		$scope.method = "POST"
		$scope.url = "/parentMenu"
			
		$scope.data = {
				type : "page",
				size : "xlg",
				location : "left",
				useYn:"Y",
				sessionType:"current"
		}
		
		$scope.data.rank = Math.max.apply(Math,$scope.parentMenuList.map(function(o){return o.rank})) + 1;
	};
	
	//자식 메뉴 추가
	$scope.addChildMenu = function(){
		
		if($scope.currentNode == null||$scope.currentNode.childMenu == undefined) {
			modalOpen($uibModal,  "상위 메뉴를 선택해 주세요");
			return;
		}
		
		$scope.method = "POST"
		$scope.url = "/childMenu"
		
		//parentMenu 생성
		if($scope.data.childMenu.length == 0) {
			parentMenu = angular.copy($scope.data);
			delete parentMenu.childMenu;
			$scope.parentMenu = parentMenu;
		} else {
			$scope.parentMenu = $scope.data.childMenu[0].parentMenu;			
		}
		
		$scope.data = {
				type : "page",
				size : "xlg",
				location : "left",
				useYn:"Y",
				sessionType:"current",
				parentMenu : $scope.parentMenu
			}
		
		$scope.data.rank = Math.max.apply(Math,$scope.currentNode.childMenu.map(function(o){return o.rank})) + 1;
	};
	
	
	$scope.insertAjax = function() {
		if($scope.data.menuName== null) {
			modalOpen($uibModal,  "메뉴명은 필수 항목 입니다");
			return;
		}
		
		if($scope.data.menuCode== null) {
			modalOpen($uibModal,  "메뉴 코드는 필수 항목 입니다");
			return;
		}
		
		if($scope.data.iconClass== null) {
			modalOpen($uibModal,  "icon class는 필수 항목 입니다");
			return;
		}
	
		$http({
			method: $scope.method, 
			url: $scope.url,
			data: $scope.data,
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
			})
			.success(function(data, status, headers, config) {
				if(status == 200) {
					modalOpen($uibModal,  angular.fromJson(data).resultMessage);
					$scope.replace();
				} else {
					modalOpen($uibModal,  "에러발생");
				}
		})
	};
	
	//menu 추가
	$scope.add = function() {
		$scope.insertAjax();
	}
	
	//menu 수정
	$scope.update = function() {
		if($scope.currentNode.childMenu == undefined) {
			$scope.url = "/childMenu";
		} else {
			$scope.url = "/parentMenu";
		}
		$scope.insertAjax();
	}

	//노드 클릭 감시
	$scope.$watch(function(){
		return $scope.currentNode;
		}, function() {
			$scope.data = angular.copy($scope.currentNode);
			$scope.method = "PUT";
	}, true);
	
}]);