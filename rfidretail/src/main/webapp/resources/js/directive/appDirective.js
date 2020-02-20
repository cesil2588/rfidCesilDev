/**
 * 파일 업로드
 */
app.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
           var model = $parse(attrs.fileModel);
           var modelSetter = model.assign;
           
           element.bind('change', function(){
              scope.$apply(function(){
                 modelSetter(scope, element[0].files[0]);
              });
              scope.flag = attrs.flag;
              scope.doUploadFile();
           });
        }
     };
 }]);

/**
 * 코드명 가져오기
 */
app.directive('codeName', function ($rootScope) {
	  return {
	    restrict: 'A',
	    replace: true,
	    transclude: true,
	    scope: {code: "@code"},
	    template: '<span>{{codeName}}</span>',
	    link: function (scope, element, attrs) {
	       angular.forEach($rootScope.parentCodeList, function(value, key) {
				if($rootScope.parentCodeList[key].codeValue == attrs.pcode){
					angular.forEach($rootScope.parentCodeList[key].codeInfo, function(v, k) {
						if($rootScope.parentCodeList[key].codeInfo[k].codeValue == attrs.code){
							scope.codeName = $rootScope.parentCodeList[key].codeInfo[k].name;
						}
					});
				}
			});
	    }
	  };
});

/**
 * 코드명 가져오기 fix
 */
app.directive('codeNameFix', function ($rootScope) {
	  return {
	    restrict: 'A',
	    replace: true,
	    transclude: true,
	    scope: {name: "@code"},
	    template: '<span>{{codeName}}</span>',
	    link: function (scope, element, attrs) {
	    	
	    	scope.$watch(function(){
	    		
	    		return scope.name;
	    		
			}, function() {
				
				if(scope.name != undefined && scope.name.trim() != ""){
					
					angular.forEach($rootScope.parentCodeList, function(value, key) {
    					if($rootScope.parentCodeList[key].codeValue == attrs.pcode){
    						angular.forEach($rootScope.parentCodeList[key].codeInfo, function(v, k) {
    							if($rootScope.parentCodeList[key].codeInfo[k].codeValue == scope.name){
    								scope.codeName = $rootScope.parentCodeList[key].codeInfo[k].name;
    							}
    						});
    					}
    				});
				}
						
			
			}, true);
	    	
	    }
	  };
});

/**
 * 뒤로가기 버튼
 */
app.directive('backBtn', function () {
    return {
        restrict: 'E',
        template: '<button class="btn">{{back}}</button>',
        scope: {
            back: '@back',
            icons: '@icons'
        },
        link: function(scope, element, attrs) {
            $(element[0]).on('click', function() {
                history.back();
                scope.$apply();
            });
        }
    };
});

app.directive('backBlockBtn', function () {
    return {
        restrict: 'E',
        template: '<button class="btn btn-lg btn-block">{{back}}</button>',
        scope: {
            back: '@back',
            icons: '@icons'
        },
        link: function(scope, element, attrs) {
            $(element[0]).on('click', function() {
                history.back();
                scope.$apply();
            });
        }
    };
});

/**
 * 서클형 로딩바
 */
app.directive('loading', [ '$http', function($http) {
	return {
		restrict : 'A',
		link : function(scope, elm, attrs) {
			scope.isLoading = function() {
				return $http.pendingRequests.length > 0;
			};

			scope.$watch(scope.isLoading, function(v) {
				if (v) {
					elm.show();
				} else {
					elm.hide();
				}
			});
		}
	};

} ]);

/**
 * 알람 시간 실시간 뷰
 */
app.directive('relativeTime', function($timeout) {
	  
	function update(scope, element) {
	    element.text(getRelativeDateTimeString(scope.actualTime));
	    $timeout(function() { update(scope, element); }, 1000);
	}
	  
	return {
	    scope: {
	    	actualTime: '=relativeTime'
	    },
	    link: function(scope, element) {
	    	update(scope, element);
	    }
	};
});

/**
 * ng-repeat end시 function 호출
 */
app.directive("repeatEnd", function(){
    return {
        restrict: "A",
        link: function (scope, element, attrs) {
            if (scope.$last) {
                scope.$eval(attrs.repeatEnd);
            }
        }
    };
});