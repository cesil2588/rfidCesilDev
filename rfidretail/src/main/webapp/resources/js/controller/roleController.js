app.controller('roleListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal)  {
	
	$scope.ajaxFinish = false;
	
	
	$scope.search = initSearch('roleName', 'role', 'desc', angular.fromJson($window.sessionStorage.getItem('current')));
	
	
	$scope.changeSearchSize = function(){
		
		param = generateParam($scope.search);
		
		httpGetList('/role', param, $scope, $http, true);
	};
	
	if($scope.search.role == undefined){
		$scope.search.role = 'all';
	}

	if($scope.search.useYn == undefined){
		$scope.search.useYn = 'all';
	}
	
	var param = generateParam($scope.search);
	
	httpGetList('/role', param, $scope, $http, true);
	
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
		
		httpGetList('/role', param, $scope, $http);
	    	
	};
	
	$scope.clickSearch = function(){
		
		if($scope.search.text == undefined){
			$scope.search.text = '';
		}
		
		if($scope.search.text == ''){
			return;
		}
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList('/role', param, $scope, $http);
		
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == 'desc'){
			$scope.search.sortOrder = 'asc';
		} else {
			$scope.search.sortOrder = 'desc';
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList('/role', param, $scope, $http);
	};
	
	$scope.headSearch = function(){
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.search.searchYn = true;
		
		httpGetList('/role', param, $scope, $http);
	};

	$scope.selectList = new Array();
	
	$http.get('/role/selectList').success(
	function(data) {
		$scope.selectList = data;
		$scope.ajaxFinish = true; 
	}
);
	
	$scope.detail = function(obj){
		obj.c = "update";
		detailPopOpenBack($uibModal,obj,'/role/roleDetailPop','roleDetailPopController','xlg','/role',param,$scope,$http);
	};
	
	$scope.add = function(){
		obj = {};
		obj.c = "add";
		detailPopOpenBack($uibModal,obj,'/role/roleDetailPop','roleDetailPopController','xlg','/role',param,$scope,$http);
	};
	
}]);

app.controller('roleDetailPopController', ['$scope', '$http', '$location', '$rootScope', '$interval', '$routeParams', 'verificationFactory', '$filter', '$uibModalInstance', 'obj', '$uibModal', '$timeout', function ($scope, $http, $location, $rootScope, $interval, $routeParams, verificationFactory, $filter, $uibModalInstance, obj, $uibModal, $timeout) {	
	
	$scope.parentList = null;
	
	//모든 paremtMenu
	$http.get('/parentMenu/useList').success(
		function(data) {
			$scope.parentList = data;
	})

	//권한 추가
	if (obj.c == 'add') {
		$http.get('/role/addList').success(
			function(data) {
				if(data.length < 1) {
					modalOpen($uibModal,  '등록할 수 있는 권한이 없습니다 ');
					$uibModalInstance.dismiss('cancel');
				}
				
				$scope.roleList = data;
				$scope.roleName = data[0].roleName;
				$scope.optionChange();
			}
		)
	//권한 수정
	} else {
		$http.get('/role/selectList').success(
			function(data) {
				$scope.roleList = data;
				$scope.roleName = obj.roleName;
				$scope.optionChange();
			}
		)
	}

	//권한 옵션 변경 시
	$scope.optionChange = function() {
		angular.forEach($scope.roleList, function(key,value) {
			if(key.roleName == $scope.roleName) {
				$scope.originalObj = new Object(key);
				obj = new Object(Object.assign($scope.originalObj,{c:obj.c}));
			}	
		})
		$scope.ajaxFinish = true;
	};
	
	//상위 menu 추가
	$scope.addMenu = function() {
		
		//메뉴 추가 가능 객체인지 check
		if(!$scope.menuCheckflag) {
			return;
		}
		
		//입력된 menuName으로 객체 검색
		angular.forEach($scope.parentList,function(key,value) {
			if(key.menuName == $scope.searchMenu) {
				$scope.searchMenu = key;
			}
		})
		
		$scope.originalObj.menuMapping.push({parentMenu : $scope.searchMenu});
		$scope.searchMenu = '';
	}
	
	//상위 메뉴 클릭시 자식 메뉴 출력
	$scope.onSelected = function(parentMenu) {
		$scope.childMenu = parentMenu.childMenu;
	}
	
	//parent menu 삭제
	$scope.deleteMenu = function(deleteMenuSeq) {
		$scope.originalObj.menuMapping.splice(deleteMenuSeq,1);
		$scope.searchMenu = '';
	}
	
	//drag&drop
	$scope.onDrop = function(index,menuMapping) {
		angular.forEach($scope.originalObj.menuMapping,function(key,value) {
			if(key.parentMenu.parentMenuSeq == menuMapping.parentMenu.parentMenuSeq) {
				if(index > value) {--index;}
				$scope.originalObj.menuMapping.splice(value,1);
			}
		});
		$scope.originalObj.menuMapping.splice(index,0,menuMapping);
	}
	
	//menuMapping에 role을 넣어주는 작업 
	$scope.inputRole = function() {
		
		if($scope.originalObj.role == null) {
			return;
		}
				
		angular.forEach($scope.originalObj.menuMapping, function(value,key) {
			if(value.roleInfo == undefined) {
				roleInfoIn = angular.copy($scope.originalObj);
				delete roleInfoIn.menuMapping;
				value.roleInfo = roleInfoIn;
			}
		})
		
		$scope.insertAjaxFn();
	}

	$scope.insertAjaxFn = function() {
		$http({
			method: $scope.method, 
			url: '/role',
			data: $scope.originalObj,
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
			})
			.success(function(data, status, headers, config) {
				if(status == 200) {
					$uibModalInstance.close('cancel');
					modalOpen($uibModal,  angular.fromJson(data).resultMessage);
				} else {
					modalOpen($uibModal,  '에러발생');
				}
			
		})
			
	}
	
	//update 전 존재하는 menuMapping 삭제
	$scope.deleteMenuMapping = function(role,callback) {
		//menuMapping 수정
		$http({
		method: 'delete', 
		url: '/menuMapping',
		data: JSON.stringify(role),
		headers: {'Content-Type': 'application/json; charset=utf-8'} 
		})
		.success(function(data, status, headers, config) {
			if(status == 200) {
				callback();
			} else {
				modalOpen($uibModal,  '에러발생');
			}
		})
	}
	
	//권한 수정 
	$scope.updateRole = function() {
		if($scope.originalObj.role == null) {
			return;
		}
		//rank 재설정
		angular.forEach($scope.originalObj.menuMapping,function(key,value) {
			key.rank = value;
		});
		
		$scope.method='PUT';
		$scope.deleteMenuMapping($scope.originalObj.role,$scope.inputRole);
	}
	
	//권한 추가 
	$scope.addRole = function() {
		
		//rank 재설정
		angular.forEach($scope.originalObj.menuMapping,function(key,value) {
			key.rank = value;
		});

		$scope.method='POST';
		
		$scope.inputRole();
	}
	
	//뒤로가기
	$scope.cancel = function () {
		$uibModalInstance.close();
	};
	
	//자동 검색 check
	$scope.$watch(function(){
		return $scope.searchMenu;
		
		}, function() {
			$scope.ajaxParentMenuVerification();
	}, true);
	
	$scope.searchMenu = '';
	
	//자동 검색 Function
	$scope.ajaxParentMenuVerification = function() {
		
		$scope.menuCheckflag = false;
		
		if($scope.searchMenu.length < 1|| $scope.searchMenu == undefined) {
			$scope.checkMessage = '검색값을 입력해 주세요';
			return;
		}
		
		$scope.checkMessage = '검색된 메뉴가 없습니다'

		angular.forEach($scope.parentList, function(key,value){
			if($scope.searchMenu == key.menuName) {
				
				$scope.menuCheckflag = true;
				
				angular.forEach($scope.originalObj.menuMapping, function(key,value) {
					if(key.parentMenu.menuName == $scope.searchMenu) {
						$scope.checkMessage = '이미 맵핑된 메뉴 입니다.';
						$scope.menuCheckflag = false;
					}					
				})
				console.log($scope.originalObj);
				if($scope.menuCheckflag) {
					if($scope.originalObj.menuMapping == null) {
						$scope.originalObj.menuMapping = new Array();
					}
					$scope.checkMessage = '추가 버튼을 눌러주세요'
				}
			}
		})
	}
}]);
