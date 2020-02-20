app.controller('loginController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'deviceDetector', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, deviceDetector, $uibModal) {
	
	var authenticate = function(credentials, callback) {

		var headers = credentials ? {authorization : "Basic "
			+ btoa(credentials.userId + ":" + credentials.password)
		} : {};
	
		$http.get('/member/userAuth', {headers : headers}).success(function(data) {
			if (data.name) {
		        $rootScope.authenticated = true;
		        $rootScope.user = data;	
		        $rootScope.userRole = data.authorities[0].authority;	//이전 $rootScope.userRole값이 초기화 되지 않아서 불러온 데이터의 userRole을  setting
			} else {
		        $rootScope.authenticated = false;
		    }
				callback && callback();
		    }).error(function(data) {
		    	$rootScope.authErrorMsg = data.trim();
		    	$rootScope.authenticated = false;
		    	callback && callback();
		});

	}
	
	$rootScope.authenticated = false;
	
	authenticate($scope.credentials, function() {
        if ($rootScope.authenticated) {
        	if(deviceDetector.isMobile()){
        		$rootScope.mobileYn = true;
        		$location.url("/app/mobileDownload");
        	} else {
        		$rootScope.mobileYn = false;
        		$location.url("/main/home");
        	}
        } else {
        	$location.url("/");
        }
        $scope.ajaxFinish = true;
    });

	$scope.credentials = {};
	
	$scope.login = function() {
		
		authenticate($scope.credentials, function() {
			
	        if ($rootScope.authenticated) {	
	        	if(deviceDetector.isMobile()){
	        		$rootScope.mobileYn = true;
	        		$location.url("/app/mobileDownload");
	        	} else {
	        		$rootScope.mobileYn = false;
	        		$location.url("/main/home");
	        	}
	        	$scope.error = '';
	        } else {
	        	$location.url("/");
	        	$scope.error = $rootScope.authErrorMsg;
	        }
	    });
	};
	
	$scope.join = function(){
		$location.url('/member/join');
	};
	
	$scope.userIdFind = function(){
		$location.url('/member/userIdFind');
	};
	
	$scope.passwordFind = function(){
		$location.url('/member/passwordFind');
	};
	
	/*
	$scope.login = function(){
		
		$http({
			method: 'POST', 
			url: '/member/adminTestLogin',
			data: $scope.credentials,
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
		})
		.success(function(data, status, headers, config) {
			
				authenticate($scope.credentials, function() {
			        if ($rootScope.authenticated) {
			        	$location.url("/main/home");
			        } else {
			        	$location.url("/");
			        }
				});
				
		})
		.error(function(data, status, headers, config) {
			console.log(status);
		});
	};
	*/
}]);

app.controller('joinController', ['$scope', '$http', '$location', '$routeParams', '$timeout', '$rootScope', 'verificationFactory', '$uibModal',
				function ($scope, $http, $location, $routeParams, $timeout, $rootScope, verificationFactory, $uibModal) {

	$scope.data = {
		email : "",
		companyInfo : {}
	}
	
	$scope.ajaxFinish = false;
	
	$http.get('/company/getCompanyList').success(
		function(data) {
			$scope.companyList = angular.fromJson(data);
			$scope.data.companyInfo = $scope.companyList[0];
			$scope.ajaxFinish = true;
		}
	);
		
	$scope.clickSubmit = function(){
		
		if($scope.data.userIdValid && $scope.data.passwordValid && $scope.data.passwordCheckValid && $scope.data.emailValid){
			
			$http({
				method: 'POST', 
				url: '/member',
				data: $scope.data,
				headers: {'Content-Type': 'application/json; charset=utf-8'} 
			})
			.success(function(data, status, headers, config) {
				
				if(status == 200) {
					modalOpen($uibModal,  "회원가입 완료되었습니다.", function(){
						$(location).attr("href", '#/main/home');
					});
				} else {
					modalOpen($uibModal,  "에러발생");
				}
			})
			.error(function(data, status, headers, config) {
				
				if(status == 401){
					modalOpen($uibModal,  "회원가입 완료. 관리자 승인 처리 중입니다.", function(){
						$(location).attr("href", '#/');
					});
				}
				console.log(status);
			});
			
		}
	};
	
	$scope.ajaxUserIdVerification = function(){
		verificationFactory.ajaxUserIdVerification($scope.data.userId).then(function(data){
			$scope.data.userIdValid = data.result;
		});
	};
	
	$scope.ajaxPasswordVerification = function(){
		
		$scope.data.passwordValid = verificationFactory.ajaxPasswordVerification($scope.data.password);
		$scope.ajaxPasswordCheckVerification();
	};
	
	$scope.ajaxPasswordCheckVerification = function(){
		$scope.data.passwordCheckValid = verificationFactory.ajaxPasswordCheckVerification($scope.data.password, $scope.data.passwordCheck, $scope.data.passwordValid);
	};
	
	$scope.ajaxEmailCheckVerification = function(){
		verificationFactory.ajaxEmailCheckVerification($scope.data.email).then(function(data){
			$scope.data.emailValid = data.result;
		});
	}
}]);

app.controller('userIdFindController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', '$uibModal',
    function ($scope, $http, $location, $routeParams, $rootScope, $uibModal) {
	
	$scope.ajaxFinish = true;
	
	$scope.find = function(){
		
		$http({
			method: 'POST', 
			url: '/member/findUserId',
			data: $scope.data,
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
		})
		.success(function(data, status, headers, config) {
			
			if(status == 200 && !isEmpty(data)) {
				$scope.user = angular.fromJson(data);
				$scope.success = true;
				$scope.error = false;
				
			} else if(status == 200 && isEmpty(data)) {
				
				$scope.success = false;
				$scope.error = true;
				
			} else {
				alert("에러발생");
			}
		})
		.error(function(data, status, headers, config) {
			console.log(status);
		});
	};
}]);

app.controller('passwordFindController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', '$uibModal',
    function ($scope, $http, $location, $routeParams, $rootScope, $uibModal) {
	$scope.ajaxFinish = true;
	
	$scope.data = {
//		userId : "test2",
//		email : "test@test.com"
	}
	
	$scope.find = function(){
		
		$scope.data.userEmailInfo = [{
			email : $scope.data.email
		}];
		
		$http({
			method: 'POST', 
			url: '/member/findPassword',
			data: $scope.data,
			headers: {'Content-Type': 'application/json; charset=utf-8'} 
		})
		.success(function(data, status, headers, config) {
			
			if(status == 200 && angular.fromJson(data).result) {
				$scope.user = angular.fromJson(data);
				$scope.success = true;
				$scope.error = false;
				
			} else if(status == 200 && !angular.fromJson(data).result) {
				
				$scope.success = false;
				$scope.error = true;
				
			} else {
				alert("에러발생");
			}
		})
		.error(function(data, status, headers, config) {
			console.log(status);
		});
	};
}]);

app.controller('userListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = {
		option : "userId",
		sortOrder : "asc",
		sortName : "userSeq"
	};
	
	$scope.search = initSearch("userId", "userSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	if($scope.search.checkYn == undefined){
		$scope.search.checkYn = "all";
	}
	
	if($scope.search.role == undefined){
		$scope.search.role = "all";
	}
	
	if($scope.search.size == undefined){  
		$scope.search.size = "10"; 
	}
	
	var param = generateParam($scope.search);
	
	httpGetList("/member", param, $scope, $http, true);
	
	$http.get('/company/getCompanyList').success(
		function(data) {
			$scope.companyList = angular.fromJson(data);
				
			angular.forEach($scope.companyList, function(value, key) {
				if($scope.companyList[key].companySeq == $scope.search.companySeq){
					$scope.search.companyInfo = $scope.companyList[key];
				}
			});
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
		
		httpGetList("/member", param, $scope, $http);
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
		
		httpGetList("/member", param, $scope, $http);
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/member", param, $scope, $http);
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
		
		httpGetList("/member", param, $scope, $http);
		
	};
	
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/member", param, $scope, $http);
	};
	
	$scope.userDetail = function(obj){
		
		var modalInstance = $uibModal.open({
			animation: true,
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: '/member/userDetailPop',
			controller: 'userDetailPopController',
			size: 'xlg',
			resolve: {
				obj: function () {
					return obj;
				}
			}
		});
		modalInstance.result.then(function (callback) {
			if(callback == "ok"){
				httpGetList("/member", param, $scope, $http);
			}
		}, function () {
			
		});
	};
	
	$scope.goUserAdd = function(){
		
		var modalInstance = $uibModal.open({
			animation: true,
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: '/member/userAddPop',
			controller: 'userAddPopController',
			size: 'xlg'
		});
		modalInstance.result.then(function (callback) {
			if(callback == "ok"){
				httpGetList("/member", param, $scope, $http);
			}
		}, function () {
			
		});
	};
	
	$scope.$watch(function(){
		
		return $rootScope.parentCodeList;
		
		}, function() {
			
			if($rootScope.parentCodeList != undefined){
				
				$scope.roleList = $filter('filter')($rootScope.parentCodeList, {codeValue: '10003'})[0];
			} 
	}, true);
}]);

app.controller('userAddPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory', '$uibModalInstance', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $uibModalInstance, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.data = {
		email : "",
		useYn : "Y",
		checkYn : "Y",
		userEmailInfo : []
	}
	
	$http.get('/company/getCompanyList').success(
		function(data) {
			$scope.companyList = angular.fromJson(data);
			$scope.data.companyInfo = $scope.companyList[0];
			$scope.ajaxFinish = true;
		}
	);
	
	$scope.add = function(){
		
		if($scope.data.userIdValid && $scope.data.passwordValid && $scope.data.passwordCheckValid){
		
			if($scope.data.companyInfo.companySeq == undefined){
				modalOpen($uibModal,  "등록할 업체를 선택해주세요.");
				return;
			}
			
			$http({
				method: 'POST', 
				url: '/member/admin',
				data: $scope.data,
				headers: {'Content-Type': 'application/json; charset=utf-8'} 
			})
			.success(function(data, status, headers, config) {
				
				if(status == 200) {
					modalOpen($uibModal,  "사용자 추가 완료되었습니다.", function(){
						$uibModalInstance.close("ok");
					});
				} else {
					modalOpen($uibModal,  "에러발생");
				}
			})
			.error(function(data, status, headers, config) {
				if(status == 409){
					modalOpen($uibModal,  "해당 업체는 이미 생성된 사용자 정보가 있습니다.");
				} else {
					modalOpen($uibModal,  "에러발생");
				}
			});
			
		}
	};
	
	$scope.ajaxUserIdVerification = function(){
		verificationFactory.ajaxUserIdVerification($scope.data.userId).then(function(data){
			$scope.data.userIdValid = data.result;
		});
	};
	
	$scope.ajaxPasswordVerification = function(){
		
		$scope.data.passwordValid = verificationFactory.ajaxPasswordVerification($scope.data.password);
		$scope.ajaxPasswordCheckVerification();
	};
	
	$scope.ajaxPasswordCheckVerification = function(){
		$scope.data.passwordCheckValid = verificationFactory.ajaxPasswordCheckVerification($scope.data.password, $scope.data.passwordCheck, $scope.data.passwordValid);
	};
	
	$scope.ajaxEmailCheckVerification = function(){
		
		if($scope.data.email == undefined){
			return;
		}
		
		verificationFactory.ajaxEmailCheckVerification($scope.data.email).then(function(data){
			$scope.data.emailValid = data.result;
		});
		
		var checkFlag = true;
		
		angular.forEach($scope.data.userEmailInfo, function(value, key) {
			if($scope.data.userEmailInfo[key].email == $scope.data.email){
				checkFlag = false;
			}
		});
		
		$scope.data.emailValid = checkFlag;
	}
	
	$scope.delEmail = function(index){
		$scope.data.userEmailInfo.splice(index, 1);
	}
	
	$scope.addEmail = function(){
	
		if($scope.data.email == undefined){
			return;
		}
		
		if($scope.data.userEmailInfo.length == 3){
			modalOpen($uibModal,  "이메일 주소는 3개까지 연동 가능합니다.");
			return;
		}
		
		if($scope.data.emailValid){
			var pushFlag = true;
			
			angular.forEach($scope.data.userEmailInfo, function(value, key) {
				if($scope.data.userEmailInfo[key].email == $scope.data.email){
					pushFlag = false;
				}
			});
			
			if(pushFlag){
				$scope.data.userEmailInfo.push({
					email   : $scope.data.email,
					regDate : new Date()
				});
			}
		}
	}
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);

app.controller('userDetailPopController', ['$scope', '$http', '$location', '$rootScope', '$interval', '$routeParams', 'verificationFactory', '$uibModalInstance', 'obj', '$uibModal', function ($scope, $http, $location, $rootScope, $interval, $routeParams, verificationFactory, $uibModalInstance, obj, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.initProcess = function(){	
		
		$scope.oriUserId = angular.copy($scope.data.userId);
//	    $scope.oriEmailInfo = angular.copy($scope.data.userEmailInfo);
	    
	    $scope.data.userIdValid = true;
	    $scope.data.emailValid = true;
	    
	    if($rootScope.userRole == 'admin'){
	    	$http.get('/company/getCompanyList').success(
			    function(data) {
			    	$scope.companyList = angular.fromJson(data);
			    		
			    	angular.forEach($scope.companyList, function(value, key) {
						if($scope.data.companyInfo.companySeq == $scope.companyList[key].companySeq){
							$scope.data.companyInfo = $scope.companyList[key];
						}
					});
			    }
	    	);
	    } 
	}
	
	if(obj.userSeq == undefined){
		
		var seq = obj;
		
		$http.get('/member/' + seq).success(
				function(data) {
				    $scope.data = angular.fromJson(data);
				    $scope.initProcess();
				}
			);
	} else {
		$scope.data = obj;
		$scope.initProcess();
	}
	
    $scope.modUser = function(){
    	
    	if($scope.data.userIdValid){
    		
    		if($scope.data.companyInfo.companySeq == undefined){
				modalOpen($uibModal,  "등록할 업체를 선택해주세요.");
				return;
			}
		
			$http({
				method: 'PUT', 
				url: '/member',
				data: $scope.data,
				headers: {'Content-Type': 'application/json; charset=utf-8'} 
			})
			.success(function(data, status, headers, config) {
				if(status == 200) {
					
					modalOpen($uibModal,  "사용자 수정 완료되었습니다.", function(){
						if($rootScope.userRole == 'admin'){
							$uibModalInstance.close("ok");
						}
					});
					
				} else {
					modalOpen($uibModal,  "에러발생");
				}
			})
			.error(function(data, status, headers, config) {
				modalOpen($uibModal,  "에러발생");
			});
    	}
		
	}; 
	
	$scope.delUser = function(){
		
		confirmOpen($uibModal, "사용자를 삭제하시겠습니까?", function(b){
			
			if(b){
		
				$http({
					method: 'DELETE', 
					url: '/member',
					data: $scope.data,
					headers: {'Content-Type': 'application/json; charset=utf-8'} 
				})
				.success(function(data, status, headers, config) {
					if(status == 200) {
						
						modalOpen($uibModal,  "사용자 삭제 완료되었습니다.", function(){
							$uibModalInstance.close("ok");
						});
						
					} else {
						modalOpen($uibModal,  "에러발생");
					}
				})
				.error(function(data, status, headers, config) {
					modalOpen($uibModal,  "에러발생");
				});
			}
		});
		
	};
	
	$scope.ajaxUserIdVerification = function(){
		
		if($scope.data.userId == $scope.oriUserId){
			$scope.data.userIdValid = true;
			return;
		}
		
		verificationFactory.ajaxUserIdVerification($scope.data.userId).then(function(data){
			$scope.data.userIdValid = data.result;
		});
	};
	
	$scope.ajaxPasswordVerification = function(){
		$scope.data.passwordValid = verificationFactory.ajaxPasswordVerification($scope.data.password);
	};
	
	$scope.ajaxEmailCheckVerification = function(){
		
		if($scope.data.email == undefined){
			return;
		}
		
		verificationFactory.ajaxUserNotEmailCheckVerification($scope.data.userSeq, $scope.data.email).then(function(data){
			$scope.data.emailValid = data.result;
		});
		
		var checkFlag = true;
		
		angular.forEach($scope.data.userEmailInfo, function(value, key) {
			if($scope.data.userEmailInfo[key].email == $scope.data.email){
				checkFlag = false;
			}
		});
		
		$scope.data.emailValid = checkFlag;
	}
	
	$scope.delEmail = function(index){
		$scope.data.userEmailInfo.splice(index, 1);
	}
	
	$scope.addEmail = function(){
	
		if($scope.data.email == undefined){
			return;
		}
		
		if($scope.data.userEmailInfo.length == 3){
			modalOpen($uibModal,  "이메일 주소는 3개까지 연동 가능합니다.");
			return;
		}
		
		var pushFlag = true;
		
		angular.forEach($scope.data.userEmailInfo, function(value, key) {
			if($scope.data.userEmailInfo[key].email == $scope.data.email){
				pushFlag = false;
			}
		});
		
		if(pushFlag){
			$scope.data.userEmailInfo.push({
				email   : $scope.data.email,
				regDate : new Date()
			});
		}
	}
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);



app.controller('userNotiListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$uibModal', function ($scope, $http, $location, $rootScope, $window, $filter, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("notice", "userNotiSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	$scope.startDate = initDate($scope.search.startDate);
	$scope.endDate = initDate($scope.search.endDate);
	
	if($scope.search.defaultDate == undefined){
		$scope.search.defaultDate = "1";
	}
	
	if($scope.search.checkYn == undefined){
		$scope.search.checkYn = "all";
	}
	
	if($scope.search.size == undefined){  
		$scope.search.size = "10"; 
	}
	
	var param = generateParam($scope.search);
	
	httpGetList("/userNoti", param, $scope, $http, true);
	
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
		
		param = generateParam($scope.search);
		
		httpGetList("/userNoti", param, $scope, $http, true);
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
		
		httpGetList("/userNoti", param, $scope, $http, true);
	};
	
	$scope.sort = function(sortName){
		
		if($scope.search.sortOrder == "desc"){
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}
		
		$scope.search.sortName = sortName;
		
		param = generateParam($scope.search);
		
		httpGetList("/userNoti", param, $scope, $http, true);
	};
	
	$scope.format = 'yyyyMMdd';
	
	$scope.dateOptions = {
		formatYear: 'yy',
		maxDate: new Date(2020, 5, 22),
		minDate: new Date(2017, 1, 1),
		startingDay: 1
	};
	
	
	$scope.startDateOpen = function() {
	    $scope.search.startDateOpened = true;
	};
	
	$scope.endDateOpen = function() {
	    $scope.search.endDateOpened = true;
	};
	
	/** 상단 검색 */
	$scope.headSearch = function(){
		
		$scope.search.startDate =  $scope.startDate.yyyymmdd();
		$scope.search.endDate =  $scope.endDate.yyyymmdd();
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/userNoti", param, $scope, $http, true);
	};
	
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList("/userNoti", param, $scope, $http, true);
	};
	
	$scope.userDetail = function(obj){
		
		$window.sessionStorage.setItem("current", angular.toJson(setSearch($scope.search)));
		
		$location.url("/member/userNotiDetail?seq=" + obj.userNotiSeq);
	};
	
}]);
