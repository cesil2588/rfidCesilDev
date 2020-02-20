app.config(function ($routeProvider, $httpProvider, $locationProvider, $provide) {
	$routeProvider
		// home
		.when('/main/home', {templateUrl: '/main/home', controller: 'homeController', resolve: { auth: onlyLoggedIn }})
		
		// 로그인, 가입, 관리자 페이지
		.when('/member/login', {templateUrl: '/member/login', controller: 'loginController'})
		.when('/member/join', {templateUrl: '/member/join', controller: 'joinController'})
		.when('/member/userList', {templateUrl: '/member/userList', controller: 'userListController', resolve: { auth: onlyLoggedIn }})
		
		// 아이디 찾기, 패스워드 찾기 페이지
		.when('/member/userIdFind', {templateUrl: '/member/userIdFind', controller: 'userIdFindController'})
		.when('/member/passwordFind', {templateUrl: '/member/passwordFind', controller: 'passwordFindController'})
		// 알람 페이지
		.when('/member/userNotiList', {templateUrl: '/member/userNotiList', controller: 'userNotiListController', resolve: { auth: onlyLoggedIn }})
		
		// 업체 관리
		.when('/company/companyList', {templateUrl: '/company/companyList', controller: 'companyListController', resolve: { auth: onlyLoggedIn }})
		
		// 코드
		.when('/code/codeDetail', {templateUrl: '/code/codeDetail', controller: 'codeDetailController', resolve: { auth: onlyLoggedIn }})
		
	  	// 바택관리
	  	.when('/bartag/bartagGroupList', {templateUrl: '/bartag/bartagGroupList', controller: 'bartagGroupListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/bartag/bartagList', {templateUrl: '/bartag/bartagList', controller: 'bartagController', resolve: { auth: onlyLoggedIn }})
	  	.when('/bartag/bartagOrderGroupList', {templateUrl: '/bartag/bartagOrderGroupList', controller: 'bartagOrderGroupListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/bartag/bartagOrderList', {templateUrl: '/bartag/bartagOrderList', controller: 'bartagOrderListController', resolve: { auth: onlyLoggedIn }})
	  	//RFID 태그 이력 확인
	  	.when('/bartag/bartagHistory', {templateUrl: '/bartag/bartagHistory', controller: 'bartagHistoryController', resolve: { auth: onlyLoggedIn }})
	  	
	  	// 재발행요청
	  	.when('/reissueTag/reissueTagGroupList', {templateUrl: '/reissueTag/reissueTagGroupList', controller: 'reissueTagGroupListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/reissueTag/reissueTagList', {templateUrl: '/reissueTag/reissueTagList', controller: 'reissueTagListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	// 생산관리
	  	.when('/production/productionScheduleList', {templateUrl: '/production/productionScheduleList', controller: 'productionScheduleListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	.when('/production/productionList', {templateUrl: '/production/productionList', controller: 'productionListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	.when('/production/productionReleaseGroupList', {templateUrl: '/production/productionReleaseGroupList', controller: 'productionReleaseGroupListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/production/productionReleaseList', {templateUrl: '/production/productionReleaseList', controller: 'productionReleaseListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	
	  	// 물류관리
	  	.when('/distribution/distributionList', {templateUrl: '/distribution/distributionList', controller: 'distributionListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	.when('/distribution/storageScheduleGroupList', {templateUrl: '/distribution/storageScheduleGroupList', controller: 'storageScheduleGroupListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/distribution/storageScheduleList', {templateUrl: '/distribution/storageScheduleList', controller: 'storageScheduleListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	.when('/distribution/storeReleaseScheduleGroupList', {templateUrl: '/distribution/storeReleaseScheduleGroupList', controller: 'storeReleaseScheduleGroupListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/distribution/storeReleaseScheduleList', {templateUrl: '/distribution/storeReleaseScheduleList', controller: 'storeReleaseScheduleListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	.when('/distribution/storeReleaseGroupList', {templateUrl: '/distribution/storeReleaseGroupList', controller: 'storeReleaseGroupListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/distribution/storeReleaseList', {templateUrl: '/distribution/storeReleaseList', controller: 'storeReleaseListController', resolve: { auth: onlyLoggedIn }})
	  	//반품입고예정 정보
	  	.when('/distribution/storageReturnScheduleGroupList', {templateUrl: '/distribution/storageReturnScheduleGroupList', controller: 'storageReturnScheduleGroupListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/distribution/storageReturnScheduleList', {templateUrl: '/distribution/storageReturnScheduleList', controller: 'storageReturnScheduleListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	// 판매 관리
	  	.when('/store/storageScheduleGroupList', {templateUrl: '/store/storageScheduleGroupList', controller: 'storeStorageScheduleGroupListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/store/storageScheduleList', {templateUrl: '/store/storageScheduleList', controller: 'storeStorageScheduleListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	.when('/store/storeList', {templateUrl: '/store/storeList', controller: 'storeListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	.when('/store/storeReturnGroupList', {templateUrl: '/store/storeReturnGroupList', controller: 'storeReturnGroupListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/store/storeReturnList', {templateUrl: '/store/storeReturnList', controller: 'storeReturnListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	.when('/store/storeBoxGroupList', {templateUrl: '/store/storeBoxGroupList', controller: 'storeBoxGroupListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/store/storeBoxList', {templateUrl: '/store/storeBoxList', controller: 'storeBoxListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	.when('/store/storeMoveGroupList', {templateUrl: '/store/storeMoveGroupList', controller: 'storeMoveGroupListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/store/storeMoveList', {templateUrl: '/store/storeMoveList', controller: 'storeMoveListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	// 재고 조사
	  	.when('/inventorySchedule/inventoryScheduleGroupList', {templateUrl: '/inventorySchedule/inventoryScheduleGroupList', controller: 'inventoryScheduleGroupListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/inventorySchedule/inventoryScheduleList', {templateUrl: '/inventorySchedule/inventoryScheduleList', controller: 'inventoryScheduleListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	// 박스관리
	  	.when('/box/boxWorkGroupList', {templateUrl: '/box/boxWorkGroupList', controller: 'boxWorkGroupListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/box/boxList', {templateUrl: '/box/boxList', controller: 'boxListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	// 배치 관리
	  	.when('/batchJob/batchJobDetail', {templateUrl: '/batchJob/batchJobDetail', controller: 'batchJobDetailController', resolve: { auth: onlyLoggedIn }})
	  	
	  	// 배치 로그
	  	.when('/batchLog/batchLogList', {templateUrl: '/batchLog/batchLogList', controller: 'batchLogListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	// 메일 로그
	  	.when('/mailLog/mailLogList', {templateUrl: '/mailLog/mailLogList', controller: 'mailLogListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	// 에러 로그
	  	.when('/errorLog/errorLogList', {templateUrl: '/errorLog/errorLogList', controller: 'errorLogListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	// HTTP 요청 로그
	  	.when('/requestLog/requestLogList', {templateUrl: '/requestLog/requestLogList', controller: 'requestLogListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	// 제품 마스터 관리
	  	.when('/productMaster/productList', {templateUrl: '/productMaster/productList', controller: 'productMasterListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	// App 관리
	  	.when('/app/appList', {templateUrl: '/app/appList', controller: 'appListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/app/mobileDownload', {templateUrl: '/app/mobileDownload', controller: 'mobileDownloadController', resolve: { auth: onlyLoggedIn }})
	  	
	  	// 작업 예약 목록
	  	.when('/batchTrigger/batchTriggerList', {templateUrl: '/batchTrigger/batchTriggerList', controller: 'batchTriggerListController', resolve: { auth: onlyLoggedIn }})
	  	
	  	// 권한 목록
	  	.when('/role/roleList', {templateUrl: '/role/roleList', controller: 'roleListController', resolve: { auth: onlyLoggedIn }})
	  	.when('/menu/menuDetail', {templateUrl: '/menu/menuDetail', controller: 'menuDetailController', resolve: { auth: onlyLoggedIn }})
	  	
	    .otherwise({redirectTo: '/', templateUrl: '/member/login', controller: 'loginController' });
	
//	$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	
	$httpProvider.defaults.useXDomain = true;
	delete $httpProvider.defaults.headers.common['X-Requested-With'];
//	$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
	
	//initialize get if not there
    if (!$httpProvider.defaults.headers.get) {
        $httpProvider.defaults.headers.get = {};    
    }    
    
    // 나중에 적용
    /*
    $httpProvider.interceptors.push(function($q) {
        return {
          responseError: function(rejection) {
                if(rejection.status <= 0) {
                    window.location = "/error/error";
                    return;
                }
                return $q.reject(rejection);
            }
        };
    });
    */

    //disable IE ajax request caching
    $httpProvider.defaults.headers.get['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
    // extra
    $httpProvider.defaults.headers.get['Cache-Control'] = 'no-cache';
    $httpProvider.defaults.headers.get['Pragma'] = 'no-cache';
    
    /*
    if(window.history && window.history.pushState){
        $locationProvider.html5Mode(true);
    }
    */
    
    $provide.decorator('$interval', function ($delegate) {
        var originalCancel = $delegate.cancel.bind($delegate);
        $delegate.cancel = function (intervalPromise) {
            var retValue = originalCancel(intervalPromise);
            if (retValue && intervalPromise) {
                intervalPromise.cancelled = true;
            }
            return retValue;
        };
        return $delegate;
    });
});

var onlyLoggedIn = function ($q, $rootScope, $http, $location, $window) {
	userLoginCheck($http, $rootScope, $location, $window, false, function(){
		if (!$rootScope.authenticated) {
	        return $q.reject({ authenticated: false });
	    }
	});
};