app.controller('rfidTagPopupController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', 
											'$uibModalInstance', 'rfidTag', 'barcode', '$uibModal',
	function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, rfidTag, barcode, $uibModal) {

	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("work", "rfidTagHistorySeq", "desc", angular.fromJson($window.sessionStorage.getItem("popupCurrent")));
	$window.sessionStorage.setItem("popupCurrent", angular.toJson(setSearch($scope.search)));
	var param = generateParam($scope.search);
	
	httpGetList("/rfidTagHistory/search/" + rfidTag, param, $scope, $http, true);
	
	$http.get('/rfidTag/detail/' + rfidTag).success(
		function(data) {
			$scope.data = angular.fromJson(data);
		}
	);
	
	/** 페이징 */
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
		
		if($scope.search.text == undefined){
	    	$scope.search.text = "";
	    }
		
		param = generateParam($scope.search);
	    
		httpGetList("/rfidTagHistory/search/" + barcode, param, $scope, $http, true);
	};
	
	$scope.ok = function () {
	    $uibModalInstance.close();
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);

app.controller('fileUploadPopupController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', 
	'$uibModalInstance', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, $uibModal) {

	$scope.ajaxFinish = true;
	
	$scope.uploadResultList = [];
	
	$scope.doUploadFile = function(){
		
		if($scope.uploadedFile == undefined){
			return;
		}
		
		var file = $scope.uploadedFile;
	    var url = "/storage";
	       
	    var data = new FormData();
	    data.append('uploadfile', file);
	    
	    var config = {
	    	transformRequest: angular.identity,
	    	transformResponse: angular.identity,
		   	headers : {
		   		'Content-Type': undefined
		   	}
	    }
	    
	    $http.post(url, data, config)
	    .success(function(data, status, headers, config) {
	    	$scope.uploadResultList.push(angular.fromJson(data));
	    }).error(function(data, status, headers, config) {
			modalOpen($uibModal,  "에러발생");
		});
	       
	    /*
	    $http.post(url, data, config).then(function (response) {
	    	$scope.uploadResultList.push(angular.fromJson(response));
	    }, function (response) {
	    	$scope.uploadResultList.push(angular.fromJson(response));
	    });
	    */
	};
	
	$scope.getFile = function(fileName){
		var url = "/storage/" + fileName;
		$http.get(url).then(function (response) {
			$scope.lstFiles = response.data;
		}, function (response) {
			alert(response.data);
		});
	};
	
	$scope.ok = function () {
		var fileNames = "";
		
		if($scope.uploadResultList.length == 0){
			modalOpen($uibModal,  "업로드된 텍스트파일이 없습니다.");
			return;
		}
		
		angular.forEach($scope.uploadResultList, function(value, key) {
			
			if($scope.uploadResultList[key].fileName.length > 0){
				
				if(($scope.uploadResultList.length - 1) == key){
					fileNames += $scope.uploadResultList[key].fileName;
				} else {
					fileNames += $scope.uploadResultList[key].fileName + ",";
				}
			}
		});
		
		if(fileNames == ""){
			modalOpen($uibModal,  "텍스트 파일 업로드가 제대로 이뤄지지 않았습니다.");
			return;
		}
		
		confirmOpen($uibModal, "바택 발행 텍스트 업로드 작업 완료 하시겠습니까?", function(b){
			
			if(b){
				$http({
			    	method : 'POST',
			    	url : '/rfidTag/allFiles/textUploadBatch',
			    	data : {fileNames : fileNames},
			    	headers: {'Content-Type': 'application/json; charset=utf-8'} 
			    }).success(function(data, status, headers, config){
			    	
			    	modalOpen($uibModal,  "텍스트 업로드 완료되었습니다.");
			    	$uibModalInstance.close();
			    	
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
			}
		});
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
	$scope.delFile = function(index){
		$scope.uploadResultList.splice(index, 1);
	}
}]);

app.controller('fileUploadReissuePopupController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window', 
	'$uibModalInstance', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, $uibModal) {

	$scope.ajaxFinish = true;
	
	$scope.uploadResultList = [];
	
	$scope.doUploadFile = function(){
		
		if($scope.uploadedFile == undefined){
			return;
		}
		
		var file = $scope.uploadedFile;
	    var url = "/storage";
	       
	    var data = new FormData();
	    data.append('uploadfile', file);
	    
	    var config = {
	    	transformRequest: angular.identity,
	    	transformResponse: angular.identity,
		   	headers : {
		   		'Content-Type': undefined
		   	}
	    }
	       
	    $http.post(url, data, config).success(function(data, status, headers, config) {
	    	$scope.uploadResultList.push(angular.fromJson(data));
	    }).error(function(data, status, headers, config) {
			modalOpen($uibModal,  "에러발생");
		});
	};
	
	$scope.getFile = function(fileName){
		var url = "/storage/" + fileName;
		$http.get(url).then(function (response) {
			$scope.lstFiles = response.data;
		}, function (response) {
			alert(response.data);
		});
	};
	
	$scope.ok = function () {
		var fileNames = "";
		
		if($scope.uploadResultList.length == 0){
			modalOpen($uibModal,  "업로드된 텍스트파일이 없습니다.");
			return;
		}
		
		angular.forEach($scope.uploadResultList, function(value, key) {
			
			if($scope.uploadResultList[key].fileName.length > 0){
				
				if(($scope.uploadResultList.length - 1) == key){
					fileNames += $scope.uploadResultList[key].fileName;
				} else {
					fileNames += $scope.uploadResultList[key].fileName + ",";
				}
			}
		});
		
		if(fileNames == ""){
			modalOpen($uibModal,  "텍스트 파일 업로드가 제대로 이뤄지지 않았습니다.");
			return;
		}
		
		confirmOpen($uibModal, "태그 재발행 텍스트 업로드 작업 완료 하시겠습니까?", function(b){
			
			if(b){
				
				$http({
			    	method : 'POST',
			    	url : '/rfidTag/allFiles/textUploadReissueBatch',
			    	data : {fileNames : fileNames},
			    	headers: {'Content-Type': 'application/json; charset=utf-8'} 
			    }).success(function(data, status, headers, config){
			    	
			    	modalOpen($uibModal,  "업로드 완료되었습니다.");
			    	$uibModalInstance.close();
			    	
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
			}
		});
		
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
	$scope.delFile = function(index){
		$scope.uploadResultList.splice(index, 1);
	}
}]);

app.controller('boxMappingPopupController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window',
	'$uibModalInstance', 'rfidTagList', 'companyInfo', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, rfidTagList, companyInfo, $uibModal) {

	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("boxNum", "boxNum", "desc", angular.fromJson($window.sessionStorage.getItem("popupCurrent")));
	$window.sessionStorage.setItem("popupCurrent", angular.toJson(setSearch($scope.search)));
	
	$scope.flag = "OP-R";
	
	$scope.search.companySeq = companyInfo.companySeq;
	
	var param = generateParam($scope.search);
	
	httpGetList("/box/popup", param, $scope, $http, true);
	
	/** 페이징 */
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
		
		if($scope.search.text == undefined){
	    	$scope.search.text = "";
	    }
		
		param = generateParam($scope.search);
	    
		httpGetList("/box/popup", param, $scope, $http, true);
	};
	
	$scope.ok = function () {
		
		var boxList = [];
		
		var count = 0;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				boxList.push($scope.list[key]);
				count ++;
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 박스가 없습니다.");
			return;
		}
		
		if(count > 1){
			modalOpen($uibModal,  "하나의 박스만 선택 가능합니다.");
			return;
		}
		
		var reqObj = {
			userSeq : $rootScope.user.principal.userSeq,
			arrivalDate : formatNowDate(),
			boxList : [{
				boxSeq : boxList[0].boxSeq,
				invoiceNum : "",
				rfidTagList : []
			}]
		};
		
		for(var i=0; i<rfidTagList.length; i++){
			var rfidTag = {
				rfidTag : rfidTagList[i].rfidTag,
				productionStorageSeq : rfidTagList[i].productionStorageSeq
			}
			reqObj.boxList[0].rfidTagList.push(rfidTag);
			
			if($scope.flag == "OP-R2"){
				reqObj.boxList[0].invoiceNum = "APLU084514850";
			}
		}
		
		confirmOpen($uibModal, "박스 맵핑 작업 완료 하시겠습니까?", function(b){
			
			if(b){
				
				if($scope.flag == "OP-R"){
					
					$http({
						method : 'POST',
						url : "/api/productionRfidTag/releaseList",
						data : reqObj
					}).success(function(data, status, headers, config){
				    	modalOpen($uibModal,  "박스 맵핑 완료되었습니다.");
				    	$uibModalInstance.close();
				    	
				    }).error(function(){
				    	modalOpen($uibModal,  "에러 발생");
				    });
					
				} else {
					
					$http({
						method : 'POST',
						url : "/api/distributionRfidTag/exceptionReleaseList/" + $scope.flag,
						data : reqObj
					}).success(function(data, status, headers, config){
				    	modalOpen($uibModal,  "예외처리 박스 맵핑 완료되었습니다.");
				    	$uibModalInstance.close();
				    	
				    }).error(function(){
				    	modalOpen($uibModal,  "에러 발생");
				    });
				}
			}
		});
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
	/** CheckBox */
	$scope.allCheck = function(){
		angular.forEach($scope.list, function(value, key) {
			if($scope.allCheckFlag){
				$scope.list[key].check = true;
			} else {
				$scope.list[key].check = false;
			}
		});
	};
}]);

app.controller('boxMappingTagPopupController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window',
	'$uibModalInstance', 'boxInfo', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, boxInfo, $uibModal) {
	
	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("boxNum", "rfidSeq", "desc", angular.fromJson($window.sessionStorage.getItem("popupCurrent")));
	$window.sessionStorage.setItem("popupCurrent", angular.toJson(setSearch($scope.search)));
	
	$scope.search.companySeq = boxInfo.startCompanyInfo.companySeq;
	$scope.search.type = boxInfo.type;
	$scope.search.returnType = "10";
	$scope.flag = "OP-R";
	
	var param = generateParam($scope.search);
	
	$http.get('/reissueTag/select/productYy' + param).success(
		function(data) {
			$scope.productYyList = angular.fromJson(data);
			$scope.ajaxFinish = true;
		}	
	);
	
	var rfidListUrl = "";
	var prefixUrl = "";
	var completeUrl = "";
	
	if($scope.search.type == "01"){
		rfidListUrl = "/reissueTag/productionRfidTagList";
		prefixUrl = "/production/";
		completeUrl = "/api/productionRfidTag/releaseList";
		
	} else if($scope.search.type == "02"){
		rfidListUrl = "/reissueTag/distributionRfidTagList";
		prefixUrl = "/distribution/";
		completeUrl = "/api/distributionRfidTag/releaseComplete";
	} else if($scope.search.type == "03"){
		rfidListUrl = "/reissueTag/storeRfidTagList";
		prefixUrl = "/store/";
		completeUrl = "/api/storeRfidTag/return";
	}
	
	$scope.selectItem = function(item, flag){
		
		if(flag == "productYy"){
			$scope.init(flag, item);
			$scope.search.productYy = item.data;
			$scope.search.urlFlag = "productSeason";
		} else if(flag == "productSeason"){
			$scope.init(flag, item);
			$scope.search.productSeason = item.data;
			$scope.search.urlFlag = "style";
		} else if(flag == "style"){
			$scope.init(flag, item);
			$scope.search.style = item.data;
			$scope.search.urlFlag = "color";
		} else if(flag == "color"){
			$scope.init(flag, item);
			$scope.search.color = item.data;
			$scope.search.urlFlag = "size";
		} else if(flag == "styleSize"){
			$scope.init(flag, item);
			$scope.search.styleSize = item.data;
			$scope.search.urlFlag = "orderDegree";
		} else if(flag == "orderDegree"){
			$scope.init(flag, item);
			$scope.search.orderDegree = item.data;
			$scope.search.urlFlag = "additionOrderDegree";
		} else if(flag == "additionOrderDegree"){
			$scope.search.additionOrderDegree = item.data;
			$scope.search.seq = item.seq;
			$scope.search.startRfidSeq = item.startRfidSeq;
			$scope.search.endRfidSeq = item.endRfidSeq;
			$scope.search.urlFlag = "rfidTag";
			
			$http.get(prefixUrl + $scope.search.seq).success(
				function(data){
					$scope.search.storage = angular.fromJson(data);
			});
		}
		
		if($scope.search.urlFlag != 'rfidTag'){
			
			param = generateParam($scope.search);
			
			$http.get('/reissueTag/select/' + $scope.search.urlFlag + param).success(
				function(data) {
					if(flag == "productYy"){
						$scope.productSeasonList = angular.fromJson(data);
					} else if(flag == "productSeason"){
						$scope.styleList = angular.fromJson(data);
					} else if(flag == "style"){
						$scope.colorList = angular.fromJson(data);
					} else if(flag == "color"){
						$scope.sizeList = angular.fromJson(data);
					} else if(flag == "styleSize"){
						$scope.orderDegreeList = angular.fromJson(data);
					} else if(flag == "orderDegree"){
						$scope.additionOrderDegreeList = angular.fromJson(data);
					} else if(flag == "additionOrderDegree"){
						$scope.rfidTagList = angular.fromJson(data);
					}
				}	
			);
		}
	};
	
	$scope.headSearch = function(){
		
		if($scope.search.productYy == undefined || $scope.search.productYy == ""){
			modalOpen($uibModal,  "연도를 선택해주세요.");
			return;
		}
		
		if($scope.search.productSeason == undefined || $scope.search.productSeason == ""){
			modalOpen($uibModal,  "시즌을 선택해주세요.");
			return;
		}
		
		if($scope.search.style == undefined || $scope.search.style == ""){
			modalOpen($uibModal,  "스타일을 선택해주세요.");
			return;
		}
		
		if($scope.search.color == undefined || $scope.search.color == ""){
			modalOpen($uibModal,  "컬러를 선택해주세요.");
			return;
		}
		
		if($scope.search.styleSize == undefined || $scope.search.styleSize == ""){
			modalOpen($uibModal,  "사이즈를 선택해주세요.");
			return;
		}
		
		if($scope.search.orderDegree == undefined || $scope.search.orderDegree == ""){
			modalOpen($uibModal,  "오더차수를 선택해주세요.");
			return;
		}
		
		if($scope.search.additionOrderDegree == undefined || $scope.search.additionOrderDegree == ""){
			modalOpen($uibModal,  "추가발주를 선택해주세요.");
			return;
		}
		
		if($scope.search.seq == undefined || $scope.search.seq == ""){
			modalOpen($uibModal,  "추가발주를 선택해주세요.");
			return;
		}
		
		if($scope.search.startRfidSeq == undefined || $scope.search.startRfidSeq == ""){
			modalOpen($uibModal,  "추가발주를 선택해주세요.");
			return;
		}
		
		if($scope.search.endRfidSeq == undefined || $scope.search.endRfidSeq == ""){
			modalOpen($uibModal,  "추가발주를 선택해주세요.");
			return;
		}
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$http.get(rfidListUrl + param).success(
			function(data) {
				$scope.list = angular.fromJson(data).content;
								
				$scope.current = angular.fromJson(data).number + 1;
				$scope.begin = Math.max(1, $scope.current - 5);
				$scope.end = Math.min($scope.begin + 9, angular.fromJson(data).totalPages);
							
				$scope.total = angular.fromJson(data).totalElements;
				
				angular.forEach($scope.list, function(value, key) {
					$scope.list[key].style = $scope.search.style;
					$scope.list[key].color = $scope.search.color;
					$scope.list[key].size = $scope.search.styleSize;
				});
			}
		);
	};
	
	/** 페이징 */
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
		
		$("#allCheck").prop("checked",false);
		
		$scope.search.page = page - 1;
		
		if($scope.search.text == undefined){
	    	$scope.search.text = "";
	    }
		
		param = generateParam($scope.search);
	    
		httpGetList(rfidListUrl, param, $scope, $http, true);
	};
	
	$scope.result = {
		list : []
	}
	
	$scope.add = function(){
		var count = 0;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				
				var pushFlag = true;
				
				angular.forEach($scope.result.list, function(v, k) {
					if($scope.list[key].rfidTag == $scope.result.list[k].rfidTag){
						pushFlag = false;
					}
					
				});
				
				if($scope.list[key].boxBarcode != undefined){
					pushFlag = false;
				}
				
				if(pushFlag){
					$scope.result.list.push(angular.copy($scope.list[key]));
				}
				count ++;
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 태그가 없습니다.");
			return;
		}
		
	};
	
	$scope.del = function(index){
		$scope.result.list.splice(index, 1);
	};
	
	$scope.complete = function () {
		
		if($scope.result.list.length == 0){
			modalOpen($uibModal,  "선택된 태그가 없습니다.");
			return;
		}
		
		var reqObj = {};
		
		if($scope.search.type == "01"){
			reqObj = {
					userSeq : $rootScope.user.principal.userSeq,
					arrivalDate : formatNowDate(),
					type : "1",
					boxList : [{
						boxSeq : boxInfo.boxSeq,
						barcode : boxInfo.barcode,
						invoiceNum : "",
						rfidTagList : []
					}]
				};
			
			angular.forEach($scope.result.list, function(value, key) {
				var rfidTag = {
					rfidTag : $scope.result.list[key].rfidTag
				}
				reqObj.boxList[0].rfidTagList.push(rfidTag);
				
				if($scope.flag == "OP-R2"){
					reqObj.boxList[0].invoiceNum = randomString(13);
				}
			});
			
		} else if($scope.search.type == "02"){
			
		} else if($scope.search.type == "03"){
			
			reqObj = {
					userSeq : $rootScope.user.principal.userSeq,
					arrivalDate : formatNowDate(),
					returnType : $scope.search.returnType,
					type : "1",
					boxInfo : {
						barcode : boxInfo.barcode,
						styleList : []
					}
				};
			
			angular.forEach($scope.result.list, function(value, key) {
				
				var pushFlag = true;
				
				var rfidTag = {
					rfidTag : $scope.result.list[key].rfidTag
				}
				
				for(index in reqObj.boxInfo.styleList){
					
					if(reqObj.boxInfo.styleList[index].style == $scope.result.list[key].style && 
					   reqObj.boxInfo.styleList[index].color == $scope.result.list[key].color &&
					   reqObj.boxInfo.styleList[index].size == $scope.result.list[key].size){
						
						reqObj.boxInfo.styleList[index].amount ++;
						reqObj.boxInfo.styleList[index].rfidTagList.push(rfidTag);
						
						pushFlag = false;
					}
				}
				
				if(pushFlag){
					var style = {
						style : $scope.result.list[key].style,
						color : $scope.result.list[key].color,
						size : $scope.result.list[key].size,
						amount : 1,
						erpKey : $scope.result.list[key].erpKey,
						rfidTagList : []
					};
					
					style.rfidTagList.push(rfidTag);
					
					reqObj.boxInfo.styleList.push(style);
				}
			});
		}
		
		confirmOpen($uibModal, "박스 맵핑 작업 완료 하시겠습니까?", function(b){
			
			if(b){
				
				$http({
					method : 'POST',
					url : completeUrl,
					data : reqObj
				}).success(function(data, status, headers, config){
				    modalOpen($uibModal,  "박스 맵핑 완료되었습니다.");
				    $uibModalInstance.close();
				    	
				}).error(function(){
					modalOpen($uibModal,  "에러 발생");
				});
			}
		});
	};
	
	$scope.pdaComplete = function () {
		
		if($scope.flag == "OP-R2"){
			modalOpen($uibModal,  "해외예외입고는 PDA 테스트할 수 없습니다.");
			return;
		}
		
		if($scope.result.list.length == 0){
			modalOpen($uibModal,  "선택된 태그가 없습니다.");
			return;
		}
		
		var reqObj = {
				userSeq : $rootScope.user.principal.userSeq,
				arrivalDate : formatNowDate(),
				type : "1",
				boxList : [{
					boxSeq : boxInfo.boxSeq,
					barcode : boxInfo.barcode,
					invoiceNum : "",
					rfidTagList : []
				}]
			};
		
		angular.forEach($scope.result.list, function(value, key) {
			var rfidTag = {
				rfidTag : $scope.result.list[key].rfidTag
			}
			reqObj.boxList[0].rfidTagList.push(rfidTag);
		});
		
		confirmOpen($uibModal, "PDA 박스 맵핑 작업 완료 하시겠습니까?", function(b){
			
			if(b){
				
				$http({
					method : 'POST',
					url : "/api/productionRfidTag/releaseMiddleWareTest",
					data : reqObj
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
						
						reqObj = {
							seq : angular.fromJson(data).header.tempHeaderSeq
						} 
						
						$http({
							method : 'POST',
							url : "/api/productionRfidTag/releaseComplete",
							data : reqObj
						}).success(function(data, status, headers, config){
							
							if(angular.fromJson(data).resultCode == '1000'){
								modalOpen($uibModal,  "PDA 박스 맵핑 완료되었습니다.");
							    $uibModalInstance.close();
							} else {
								modalOpen($uibModal, angular.fromJson(data).resultMessage);
							}
						});
						
					} else {
						modalOpen($uibModal, angular.fromJson(data).resultMessage);
					}
				    	
				}).error(function(){
					modalOpen($uibModal,  "에러 발생");
				});
			}
		});
	};
	
	$scope.init = function(flag, item){
		
		if(flag == "all"){
			$scope.search.productSeason = null;
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "productYy" && $scope.search.productYy != item.data){
			$scope.search.productSeason = null;
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "productSeason" && $scope.search.productSeason != item.data){
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "style" && $scope.search.style != item.data){
			$scope.search.color = null;
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "color" && $scope.search.color != item.data){
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "styleSize" && $scope.search.styleSize != item.data){
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "orderDegree" && $scope.search.orderDegree != item.data){
			$scope.search.additionOrderDegree = null;
		} 
		
		$scope.search.startRfidSeq = null;
		$scope.search.endRfidSeq = null;
		$scope.list = [];
	};
	
	$scope.selectInit = function(){
		$scope.result.list = [];
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
	/** CheckBox */
	$scope.allCheck = function(){
		angular.forEach($scope.list, function(value, key) {
			if($scope.allCheckFlag){
				$scope.list[key].check = true;
			} else {
				$scope.list[key].check = false;
			}
		});
	};
	
	$scope.orderSelect = function() {
		if($scope.flag == "OP-R"){
			completeUrl = "/api/productionRfidTag/releaseList";
		} else {
			completeUrl = "/api/distributionRfidTag/exceptionRelease/" + $scope.flag;
		}
	};
}]);


app.controller('releaseGroupConfirmPopupController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window',
	'$uibModalInstance', 'releaseGroupList', 'type', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, releaseGroupList, type, $uibModal) {

	$scope.ajaxFinish = true;
	
	$scope.list = angular.copy(releaseGroupList);;
	
	var settingDate = new Date();	
	
	$scope.format = 'yyyy-MM-dd';
	
	$scope.startDate = settingDate;
	
	$scope.type = type;
	
	var msg = "";
	var completeUrl = "";
	
	if(type == '01'){
		msg = "출고";
		completeUrl = "/production/releaseGroup";
	} else if(type == '03'){
		msg = "반품";
		completeUrl = "/store/returnGroup";
	}
	
	$scope.dateOptions = {
		formatYear: 'yy',
		maxDate: new Date(2020, 5, 22),
		minDate: new Date(2017, 1, 1),
		startingDay: 1
	};
	
	$scope.startDateOpen = function() {
	    $scope.startDateOpened = true;
	};
	
	$scope.arrivalDateChange = function(){
		
		var count = 0;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				count ++;
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 " + msg + " 정보가 없습니다.");
			return;
		};
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				$scope.list[key].arrivalDate = $scope.startDate.yyyy_mm_dd();
			} 
		});
	};
	
	$scope.ok = function () {
		
		var tagList = [];
		
		var count = 0;
		
		angular.forEach($scope.list, function(value, key) {
			
			if($scope.list[key].check){
				tagList.push($scope.list[key]);
				count ++;
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 " + msg + " 정보가 없습니다.");
			return;
		}
		
		confirmOpen($uibModal, "" + msg + " 확정 작업 완료 하시겠습니까?", function(b){
			
			if(b){
				$http({
					method : 'PUT',
					url : completeUrl,
					data : tagList
				}).success(function(data, status, headers, config){
			    	modalOpen($uibModal,  "" + msg + " 확정 되었습니다.");
			    	$uibModalInstance.close();
			    	
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
			}
		});
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
	/** CheckBox */
	$scope.allCheck = function(){
		angular.forEach($scope.list, function(value, key) {
			if($scope.allCheckFlag){
				$scope.list[key].check = true;
			} else {
				$scope.list[key].check = false;
			}
		});
	};
}]);

app.controller('releaseConfirmPopupController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window',
	'$uibModalInstance', 'releaseList', 'type', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, releaseList, type, $uibModal) {

	$scope.ajaxFinish = true;
	
	$scope.list = angular.copy(releaseList);;
	
	var settingDate = new Date();	
	
	$scope.format = 'yyyy-MM-dd';
	
	$scope.startDate = settingDate;
	
	$scope.type = type;
	
	var msg = "";
	var completeUrl = "";
	
	if(type == '01'){
		msg = "출고";
		completeUrl = "/production/release";
	} else if(type == '03'){
		msg = "반품";
		completeUrl = "/store/return";
	}
	
	$scope.dateOptions = {
		formatYear: 'yy',
		maxDate: new Date(2020, 5, 22),
		minDate: new Date(2017, 1, 1),
		startingDay: 1
	};
	
	$scope.startDateOpen = function() {
	    $scope.startDateOpened = true;
	};
	
	$scope.arrivalDateChange = function(){
		
		var count = 0;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				count ++;
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 " + msg + " 정보가 없습니다.");
			return;
		};
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				$scope.list[key].boxInfo.arrivalDate = $scope.startDate.yyyy_mm_dd();
			} 
		});
	};
	
	$scope.ok = function () {
		
		var tagList = [];
		
		var count = 0;
		
		angular.forEach($scope.list, function(value, key) {
			
			if($scope.list[key].check){
				tagList.push($scope.list[key]);
				count ++;
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 " + msg + " 정보가 없습니다.");
			return;
		}
		
		confirmOpen($uibModal, "" + msg + " 확정 작업 완료 하시겠습니까?", function(b){
			
			if(b){
				$http({
					method : 'PUT',
					url : completeUrl,
					data : tagList
				}).success(function(data, status, headers, config){
			    	modalOpen($uibModal,  "" + msg + " 확정 되었습니다.");
			    	$uibModalInstance.close();
			    	
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
			}
		});
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
	/** CheckBox */
	$scope.allCheck = function(){
		angular.forEach($scope.list, function(value, key) {
			if($scope.allCheckFlag){
				$scope.list[key].check = true;
			} else {
				$scope.list[key].check = false;
			}
		});
	};
	
	$scope.getTotalAmount = function(obj){
	    var totalAmount = 0;
	    
	    angular.forEach(obj.storageScheduleDetailLog, function(value, key) {
	    	totalAmount += obj.storageScheduleDetailLog[key].amount;
		});
	    return totalAmount;
	}
}]);

app.controller('reissueTagPopupController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window',
	'$uibModalInstance', 'companyInfo', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, companyInfo, $uibModal) {

	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("boxNum", "rfidSeq", "desc", angular.fromJson($window.sessionStorage.getItem("popupCurrent")));
	$window.sessionStorage.setItem("popupCurrent", angular.toJson(setSearch($scope.search)));
	
	$scope.search.companySeq = companyInfo.companySeq;
	
	if(companyInfo.role == "production"){
		$scope.search.type = "01";		
	} else if(companyInfo.role == "distribution"){
		$scope.search.type = "02";
	} else if(companyInfo.role == "sales" || companyInfo.role == "special"){
		$scope.search.type = "03";
	}
	
	$scope.search.publishLocation = "1";
	
	var param = generateParam($scope.search);
	
	$http.get('/reissueTag/select/productYy' + param).success(
		function(data) {
			$scope.productYyList = angular.fromJson(data);
			$scope.ajaxFinish = true;
		}	
	);
	
	var rfidListUrl = "";
	var prefixUrl = "";
	var completeUrl = "";
	
	if($scope.search.type == "01"){
		rfidListUrl = "/reissueTag/productionRfidTagList";
		prefixUrl = "/production/";
		completeUrl = "/api/productionRfidTag/releaseList";
	} else if($scope.search.type == "02"){
		rfidListUrl = "/reissueTag/distributionRfidTagList";
		prefixUrl = "/distribution/";
		completeUrl = "/api/distributionRfidTag/releaseComplete";
	} else if($scope.search.type == "03"){
		rfidListUrl = "/reissueTag/storeRfidTagList";
		prefixUrl = "/store/";
		completeUrl = "/api/storeRfidTag/return";
	}
	
	$scope.selectItem = function(item, flag){
		
		if(flag == "productYy"){
			$scope.init(flag, item);
			$scope.search.productYy = item.data;
			$scope.search.urlFlag = "productSeason";
		} else if(flag == "productSeason"){
			$scope.init(flag, item);
			$scope.search.productSeason = item.data;
			$scope.search.urlFlag = "style";
		} else if(flag == "style"){
			$scope.init(flag, item);
			$scope.search.style = item.data;
			$scope.search.urlFlag = "color";
		} else if(flag == "color"){
			$scope.init(flag, item);
			$scope.search.color = item.data;
			$scope.search.urlFlag = "size";
		} else if(flag == "styleSize"){
			$scope.init(flag, item);
			$scope.search.styleSize = item.data;
			$scope.search.urlFlag = "orderDegree";
		} else if(flag == "orderDegree"){
			$scope.init(flag, item);
			$scope.search.orderDegree = item.data;
			$scope.search.urlFlag = "additionOrderDegree";
		} else if(flag == "additionOrderDegree"){
			$scope.search.additionOrderDegree = item.data;
			$scope.search.seq = item.seq;
			$scope.search.startRfidSeq = item.startRfidSeq;
			$scope.search.endRfidSeq = item.endRfidSeq;
			$scope.search.urlFlag = "rfidTag";
			
			$http.get(prefixUrl + $scope.search.seq).success(
				function(data){
					$scope.search.storage = angular.fromJson(data);
			});
		}
		
		if($scope.search.urlFlag != 'rfidTag'){
			
			param = generateParam($scope.search);
			
			$http.get('/reissueTag/select/' + $scope.search.urlFlag + param).success(
				function(data) {
					if(flag == "productYy"){
						$scope.productSeasonList = angular.fromJson(data);
					} else if(flag == "productSeason"){
						$scope.styleList = angular.fromJson(data);
					} else if(flag == "style"){
						$scope.colorList = angular.fromJson(data);
					} else if(flag == "color"){
						$scope.sizeList = angular.fromJson(data);
					} else if(flag == "styleSize"){
						$scope.orderDegreeList = angular.fromJson(data);
					} else if(flag == "orderDegree"){
						$scope.additionOrderDegreeList = angular.fromJson(data);
					} else if(flag == "additionOrderDegree"){
						$scope.rfidTagList = angular.fromJson(data);
					}
				}	
			);
		}
	};
	
	$scope.headSearch = function(){
		
		if($scope.search.productYy == undefined || $scope.search.productYy == ""){
			modalOpen($uibModal,  "연도를 선택해주세요.");
			return;
		}
		
		if($scope.search.productSeason == undefined || $scope.search.productSeason == ""){
			modalOpen($uibModal,  "시즌을 선택해주세요.");
			return;
		}
		
		if($scope.search.style == undefined || $scope.search.style == ""){
			modalOpen($uibModal,  "스타일을 선택해주세요.");
			return;
		}
		
		if($scope.search.color == undefined || $scope.search.color == ""){
			modalOpen($uibModal,  "컬러를 선택해주세요.");
			return;
		}
		
		if($scope.search.styleSize == undefined || $scope.search.styleSize == ""){
			modalOpen($uibModal,  "사이즈를 선택해주세요.");
			return;
		}
		
		if($scope.search.orderDegree == undefined || $scope.search.orderDegree == ""){
			modalOpen($uibModal,  "오더차수를 선택해주세요.");
			return;
		}
		
		if($scope.search.additionOrderDegree == undefined || $scope.search.additionOrderDegree == ""){
			modalOpen($uibModal,  "추가발주를 선택해주세요.");
			return;
		}
		
		if($scope.search.seq == undefined || $scope.search.seq == ""){
			modalOpen($uibModal,  "추가발주를 선택해주세요.");
			return;
		}
		
		if($scope.search.startRfidSeq == undefined || $scope.search.startRfidSeq == ""){
			modalOpen($uibModal,  "추가발주를 선택해주세요.");
			return;
		}
		
		if($scope.search.endRfidSeq == undefined || $scope.search.endRfidSeq == ""){
			modalOpen($uibModal,  "추가발주를 선택해주세요.");
			return;
		}
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		httpGetList(rfidListUrl, param, $scope, $http, true);
	};
	
	/** 페이징 */
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
		
		$("#allCheck").prop("checked",false);
		
		$scope.search.page = page - 1;
		
		if($scope.search.text == undefined){
	    	$scope.search.text = "";
	    }
		
		param = generateParam($scope.search);
	    
		httpGetList(rfidListUrl, param, $scope, $http, true);
	};
	
	$scope.result = {
		list : []
	}
	
	$scope.add = function(){
		var count = 0;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				
				var pushFlag = true;
				
				angular.forEach($scope.result.list, function(v, k) {
					if($scope.list[key].rfidTag == $scope.result.list[k].rfidTag){
						pushFlag = false;
					}
				});
				
				if(pushFlag){
					$scope.result.list.push(angular.copy($scope.list[key]));
				}
				count ++;
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 태그가 없습니다.");
			return;
		}
		
	};
	
	$scope.del = function(index){
		$scope.result.list.splice(index, 1);
	};
	
	$scope.ok = function () {
		
		if($scope.result.list.length == 0){
			modalOpen($uibModal,  "선택된 태그가 없습니다.");
			return;
		}
		
		if($scope.explanatory == undefined || $scope.explanatory == ''){
			modalOpen($uibModal,  "사유를 입력하세요.");
			return;
		}
		
		var locationCheck = false;
		
		var obj = {
			companyInfo : companyInfo,
			explanatory : $scope.explanatory,
			publishLocation : $scope.search.publishLocation,
			rfidTagReissueRequestDetail : []
		}
				
		angular.forEach($scope.result.list, function(value, key) {
			
			if(companyInfo.type == "3" && $scope.search.publishLocation == '2' && $scope.result.list[key].stat != '3' ){
				locationCheck = true;
			}
			
			var detailObj = {
				rfidTag : $scope.result.list[key].rfidTag,
				rfidSeq : $scope.result.list[key].rfidSeq,
			}
			
			obj.rfidTagReissueRequestDetail.push(detailObj);
		});
		
		if(locationCheck){
			modalOpen($uibModal,  "물류센터 재발행은 생산 출고된 태그만 가능합니다.");
			return;
		}
		
		confirmOpen($uibModal, "재발행 요청 작업 완료 하시겠습니까?", function(b){
			if(b){
				$http({
					method : 'POST',
					url : "/reissueTag",
					data : obj
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
						modalOpen($uibModal,  "재발행요청 완료되었습니다.");
						$uibModalInstance.close();
					} else {
						modalOpen($uibModal,  angular.fromJson(data).resultMessage);
					}
					    
				}).error(function(){
					modalOpen($uibModal,  "에러 발생");
				});
			}
		});
	};
	
	$scope.init = function(flag, item){
		
		if(flag == "all"){
			$scope.search.productSeason = null;
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "productYy" && $scope.search.productYy != item.data){
			$scope.search.productSeason = null;
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "productSeason" && $scope.search.productSeason != item.data){
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "style" && $scope.search.style != item.data){
			$scope.search.color = null;
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "color" && $scope.search.color != item.data){
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "styleSize" && $scope.search.styleSize != item.data){
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "orderDegree" && $scope.search.orderDegree != item.data){
			$scope.search.additionOrderDegree = null;
		} 
		
		$scope.search.startRfidSeq = null;
		$scope.search.endRfidSeq = null;
		$scope.list = [];
	};
	
	$scope.selectInit = function(){
		$scope.result.list = [];
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
	/** CheckBox */
	$scope.allCheck = function(){
		angular.forEach($scope.list, function(value, key) {
			if($scope.allCheckFlag){
				$scope.list[key].check = true;
			} else {
				$scope.list[key].check = false;
			}
		});
	};
}]);

app.controller('reissueTagPrintPopupController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window',
	'$uibModalInstance', 'companyInfo', 'objList', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, companyInfo, objList, $uibModal) {

	$scope.ajaxFinish = false;
	
	$scope.search = initSearch("boxNum", "rfidSeq", "desc", angular.fromJson($window.sessionStorage.getItem("popupCurrent")));
	$window.sessionStorage.setItem("popupCurrent", angular.toJson(setSearch($scope.search)));
	
	$scope.search.publishLocation = "2";
	
	$scope.search.popupList = angular.copy(objList);
	
	$scope.result = {
		list : []
	}
	
	$scope.add = function(){
		var count = 0;
		
		angular.forEach($scope.search.popupList, function(value, key) {
			if($scope.search.popupList[key].check){
				
				var pushFlag = true;
				
				angular.forEach($scope.result.list, function(v, k) {
					if($scope.search.popupList[key].rfidTag == $scope.result.list[k].rfidTag){
						pushFlag = false;
					}
				});
				
				if(pushFlag){
					$scope.result.list.push(angular.copy($scope.search.popupList[key]));
				}
				count ++;
			} 
		});
		
		if(count == 0){
			modalOpen($uibModal,  "선택된 태그가 없습니다.");
			return;
		}
		
		var date = new Date;
		
		angular.forEach($scope.result.list, function(value, key) {
			
			var obj = parseRfidTag($scope.result.list[key].rfidTag);
			
			$scope.result.list[key].publishLocation = "2";
			$scope.result.list[key].publishDegree = pad((angular.copy(obj.publishDegree) * 1) + 1, 2);
			$scope.result.list[key].publishRegDate = new Date().yyyymmdd().substring(2);
			$scope.result.list[key].reissueRfidTag = obj.erpKey + 
												  obj.productRfidSeason + 
												  obj.orderDegree +
												  obj.customerCd +
												  $scope.result.list[key].publishLocation +
												  $scope.result.list[key].publishRegDate +
												  $scope.result.list[key].publishDegree +
												  obj.rfidSeq;
			
			$scope.result.list[key].customerCd = obj.customerCd;
			
		});
		
	};
	
	$scope.del = function(index){
		$scope.result.list.splice(index, 1);
	};
	
	$scope.ok = function () {
		
		if($scope.result.list.length == 0){
			modalOpen($uibModal,  "선택된 태그가 없습니다.");
			return;
		}
		
		var reqList = [];
				
		angular.forEach($scope.result.list, function(value, key) {
			var detailObj = {
				item 		: "rftag",
				serial 		: $scope.result.list[key].rfidSeq,
				barcode 	: $scope.result.list[key].productionStorage.bartagMaster.erpKey,
				style		: $scope.result.list[key].style,
				color		: $scope.result.list[key].color,
				sizec		: $scope.result.list[key].size,
				season		: $scope.result.list[key].productRfidSeason,
				ordrep		: $scope.result.list[key].orderDegree.substring(1),
				productc	: $scope.result.list[key].customerCd,
				datec		: $scope.result.list[key].publishRegDate,
				prnrep 		: $scope.result.list[key].publishDegree,
				printc		: $scope.result.list[key].rfidTagReissueRequest.publishLocation
			}
			reqList.push(detailObj);
		});
		
		confirmOpen($uibModal, "재발행 프린트 작업 완료 하시겠습니까?", function(b){
			if(b){
				$http({
					method : 'POST',
					url : "http://localhost:9090/barcodeprint",
					data : reqList,
					headers : {
						'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
					}
				}).success(function(data, status, headers, config){
					
					if(data == "ok"){
						
						modalOpen($uibModal,  "재발행 프린트 완료되었습니다.", function() {
							
							var subReqList = [];
							
							angular.forEach($scope.search.popupList, function(value, key) {
								if($scope.search.popupList[key].check){
									
									angular.forEach($scope.result.list, function(v, k) {
										if($scope.search.popupList[key].rfidTag == $scope.result.list[k].rfidTag){
											$scope.search.popupList[key].reissueRfidTag = $scope.result.list[k].reissueRfidTag;
											
											subReqList.push($scope.search.popupList[key]);
										}
									});
								} 
							});
							
							$http({
								method : 'POST',
								url : "/reissueTag/reissueComplete",
								data : subReqList
							}).success(function(data, status, headers, config){
								    
								$uibModalInstance.close();
								    	
							}).error(function(){
								modalOpen($uibModal,  "에러 발생");
							});
						});
					} else {
						modalOpen($uibModal,  "RFID 프린터 연결 에러 발생");
					}
					    	
				}).error(function(){
					modalOpen($uibModal,  "RFID 프린터 연결이 정상적으로 이뤄지지 않고 있습니다. RFID 데몬 프로그램 및 케이블을 확인해주세요.");
				});
			}
		});
	};
	
	$scope.init = function(flag, item){
		
		if(flag == "all"){
			$scope.search.productSeason = null;
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "productYy" && $scope.search.productYy != item.data){
			$scope.search.productSeason = null;
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "productSeason" && $scope.search.productSeason != item.data){
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "style" && $scope.search.style != item.data){
			$scope.search.color = null;
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "color" && $scope.search.color != item.data){
			$scope.search.styleSize = null;
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "styleSize" && $scope.search.styleSize != item.data){
			$scope.search.orderDegree = null;
			$scope.search.additionOrderDegree = null;
		} else if(flag == "orderDegree" && $scope.search.orderDegree != item.data){
			$scope.search.additionOrderDegree = null;
		} 
		
		$scope.search.startRfidSeq = null;
		$scope.search.endRfidSeq = null;
		$scope.list = [];
	};
	
	$scope.selectInit = function(){
		$scope.result.list = [];
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
	/** CheckBox */
	$scope.allCheck = function(){
		angular.forEach($scope.search.popupList, function(value, key) {
			if($scope.allCheckFlag){
				$scope.search.popupList[key].check = true;
			} else {
				$scope.search.popupList[key].check = false;
			}
		});
	};
}]);


app.controller('bartagOrderCompletePopupController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window',
	'$uibModalInstance', 'bartagOrderList', 'flag', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, bartagOrderList, flag, $uibModal) {

	$scope.ajaxFinish = true;
	
	$scope.list = angular.copy(bartagOrderList);
	
	angular.forEach($scope.list, function(value, key) {
		$scope.list[key].check = false;
	});
	
	$scope.flag = flag;
	
	$scope.autoAmount = function(){
		
		angular.forEach($scope.list, function(value, key) {
			var addAmount = Math.floor($scope.list[key].orderAmount / 500);
			$scope.list[key].nonCheckCompleteAmount = $scope.list[key].orderAmount + addAmount;
		});
	};
	
	$scope.autoAmountPlus = function(){
		
		angular.forEach($scope.list, function(value, key) {
			var addAmount = Math.round($scope.list[key].orderAmount * 0.03);
			$scope.list[key].nonCheckCompleteAmount = $scope.list[key].orderAmount + addAmount;
		});
	};
	
	$scope.ok = function () {
		
		var bartagOrderList = [];
		
		var countCheck = false;
		
		angular.forEach($scope.list, function(value, key) {
			
			bartagOrderList.push($scope.list[key]);
			
			if($scope.list[key].orderAmount > $scope.list[key].nonCheckCompleteAmount || $scope.list[key].nonCheckCompleteAmount == undefined){
				countCheck = true;
			}
		});
		
		if(countCheck){
			if(flag == "list"){
				modalOpen($uibModal,  "RFID 생산 수량 입력이 생산 수량보다 작은 스타일이 있습니다.");
			} else if(flag == "object"){
				modalOpen($uibModal,  "RFID 생산 수량 입력이 생산 수량보다 작은 스타일입니다.");
			}
			return;
		}
		
		confirmOpen($uibModal, "RFID 생산 수량 입력 완료 하시겠습니까?", function(b){
			
			if(b){
				
				$http({
					method : 'PUT',
					url : "/bartag/bartagOrder",
					data : bartagOrderList
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
						modalOpen($uibModal,  "RFID 생산 수량 입력이 완료 되었습니다.");
				    	$uibModalInstance.close();
					} else if(angular.fromJson(data).resultCode == '3002'){
						modalOpen($uibModal,  "로그인 세션이 만료되었습니다.");
					} else if(angular.fromJson(data).resultCode == '3026'){
						if(flag == "list"){
							modalOpen($uibModal,  "이전 RFID 요청 차수가 종료되지 않은 스타일이 있습니다.");
						} else if(flag == "object"){
							modalOpen($uibModal,  "이전 RFID 요청 차수가 종료되지 않은 스타일입니다.");
						}
					} else if(angular.fromJson(data).resultCode == '3028'){
						if(flag == "list"){
							modalOpen($uibModal,  "RFID 생산 입력된 스타일이 있습니다.");
						} else if(flag == "object"){
							modalOpen($uibModal,  "RFID 생산 입력된 스타일입니다.");
						}
						
					} else if(angular.fromJson(data).resultCode == '3029'){
						if(flag == "list"){
							modalOpen($uibModal,  "RFID 종결된 스타일이 있습니다.");
						} else if(flag == "object"){
							modalOpen($uibModal,  "RFID 종결된 스타일입니다.");
						}
					}
					
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
				
			}
		});
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);


app.controller('bartagOrderAdditionPopupController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window',
	'$uibModalInstance', 'bartagOrderList', 'flag', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, bartagOrderList, flag, $uibModal) {

	$scope.ajaxFinish = true;
	
	$scope.list = angular.copy(bartagOrderList);
	
	$scope.additionAllCheckFlag = false;
	
	$scope.flag = flag;
	
	angular.forEach($scope.list, function(value, key) {
		$scope.list[key].tempAdditionAmount = 0;
		$scope.list[key].check = false;
	});
	
	$scope.ok = function () {
		
		var bartagOrderList = [];
		
		var countCheck = false;
		
		angular.forEach($scope.list, function(value, key) {
			
			bartagOrderList.push($scope.list[key]);
			
			if($scope.list[key].tempAdditionAmount == undefined || $scope.list[key].tempAdditionAmount == '0'){
				countCheck = true;
			}
		});
		
		if(countCheck){
			modalOpen($uibModal,  "RFID 추가 확정 수량은 0보다 커야합니다.");
			return;
		}
		
		confirmOpen($uibModal, "RFID 추가 수량 입력 완료 하시겠습니까?", function(b){
			
			if(b){
				
				$http({
					method : 'PUT',
					url : "/bartag/bartagOrder",
					data : bartagOrderList
				}).success(function(data, status, headers, config){
			    	if(angular.fromJson(data).resultCode == '1000'){
			    		modalOpen($uibModal,  "RFID 추가 수량 입력 되었습니다.");
				    	$uibModalInstance.close();
					} else if(angular.fromJson(data).resultCode == '3002'){
						modalOpen($uibModal,  "로그인 세션이 만료되었습니다.");
					} else if(angular.fromJson(data).resultCode == '3026'){
						if(flag == "list"){
							modalOpen($uibModal,  "이전 RFID 요청 차수가 종료되지 않은 스타일이 있습니다.");
						} else if(flag == "object"){
							modalOpen($uibModal,  "이전 RFID 요청 차수가 종료되지 않은 스타일입니다.");
						}
					} else if(angular.fromJson(data).resultCode == '3028'){
						if(flag == "list"){
							modalOpen($uibModal,  "RFID 생산/추가 입력된 스타일이 있습니다.");
						} else if(flag == "object"){
							modalOpen($uibModal,  "RFID 생산/추가 입력된 스타일입니다.");
						}
						
					} else if(angular.fromJson(data).resultCode == '3029'){
						if(flag == "list"){
							modalOpen($uibModal,  "RFID 종결된 스타일이 있습니다.");
						} else if(flag == "object"){
							modalOpen($uibModal,  "RFID 종결된 스타일입니다.");
						}
					}
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
			}
		});
		
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);

app.controller('bartagUpdatePopupController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window',
	'$uibModalInstance', 'bartagOrderList', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, bartagOrderList, $uibModal) {

	$scope.ajaxFinish = true;
	
	$scope.list = angular.copy(bartagOrderList);
	
	$scope.additionAllCheckFlag = false;
	
	angular.forEach($scope.list, function(value, key) {
		$scope.list[key].tempAmount = angular.copy($scope.list[key].amount);
		$scope.list[key].check = false;
	});
	
	$scope.ok = function () {
		
		var objList = [];
		
		var amountCheck = false;
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].tempAmount > $scope.list[key].amount && $scope.list[key].additionOrderDegee == '0'){
				amountCheck = true;
			}
			objList.push($scope.list[key]);
		});
		
		
		if(amountCheck){
			modalOpen($uibModal,  "바택 정보를 선택해주세요.");
	    	return;
		}
		
		confirmOpen($uibModal, "바택 정보를 수정 하시겠습니까?", function(b){
			
			if(b){
				
				$http({
					method : 'PUT',
					url : "/bartag",
					data : objList,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config){
			    	if(angular.fromJson(data).resultCode == '1000'){
			    		modalOpen($uibModal,  "바택 정보가 수정되었습니다.");
			    		$uibModalInstance.close();
			    		
					} else if(angular.fromJson(data).resultCode == '3002'){
						modalOpen($uibModal,  "로그인 세션이 만료되었습니다.");
					} else if(angular.fromJson(data).resultCode == '3014'){
						modalOpen($uibModal,  "찾을 수 없는 바택 정보가 있습니다.");
					} else if(angular.fromJson(data).resultCode == '3028'){
						modalOpen($uibModal,  "이미 발행 시작된 바택 정보가 있습니다.");
					}
			    }).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			    });
			}
		});
	};

	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
	/** CheckBox */
	$scope.allCheck = function(){
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.additionAllCheckFlag){
				$scope.list[key].check = true;
			} else {
				$scope.list[key].check = false;
			}
		});
	};
}]);

app.controller('storeReleaseInitPopupController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory','$window',
	'$uibModalInstance', 'httpService', '$uibModal', function ($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $window, $uibModalInstance, httpService, $uibModal) {

	$scope.ajaxFinish = true;
	
	$scope.search = initSearch("boxNum", "storageScheduleLogSeq", "desc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	$scope.data = {
		referenceNo : "",
		tboxPickList : [],
		key : {
			shipmentNo : ""
		}
	};
	
	$scope.search.rfidYn = "Y";
	$scope.search.dataCriteria = "distribution";
	
	var param;
	
	$scope.wmsTshipmentList = function(){
		
		$http.get('/distribution/wmsTboxPickList').success(
				function(data) {
					
					var res = angular.fromJson(data);
					
					if(res.resultCode == '1000'){
						
						$scope.list = [];
						
						angular.forEach(res.tboxPickList, function(detail) { 
							
							var pushFlag = true;
							
							angular.forEach($scope.list, function(temp) {
								
								if(detail.orderQty == temp.barcode){
									
									detail.referenceNo = detail.tshipment.referenceNo;
									detail.style = detail.stock.substring(0, 12);
									detail.color = detail.stock.substring(12, 15);
									detail.size = detail.stock.substring(15, 18);
									detail.openFlag = false;
									detail.requestFlag = false;
									
									temp.styleList.push(detail);
									
									pushFlag = false;
								}
							});
							
							if(pushFlag){
								var temp = {
									barcode 		: detail.orderQty,
									shipmentNo		: detail.key.shipmentNo,
									styleList 		: []
								}
								
								detail.referenceNo = detail.tshipment.referenceNo;
								detail.style = detail.stock.substring(0, 12);
								detail.color = detail.stock.substring(12, 15);
								detail.size = detail.stock.substring(15, 18);
								detail.openFlag = false;
								detail.requestFlag = false;
								
								temp.styleList.push(detail);
								
								$scope.list.push(temp);
							}
							
						});
						
						console.log($scope.list);
						
					} else {
						modalOpen($uibModal,  res.resultMessage);
					}
				}
			);
	};
	
	$scope.selectList = function(){
		
		$http.get('/company/getCompanyList').success(
				function(data) {
					$scope.tempCompanyList = angular.fromJson(data);
					
					$scope.companyList = [];
					
					angular.forEach($scope.tempCompanyList, function(value, key) {
						if($scope.tempCompanyList[key].type == '5' || $scope.tempCompanyList[key].type == '6'){
							$scope.companyList.push($scope.tempCompanyList[key]);
						}
					});
					
					param = generateParam($scope.search);
					
					$http.get('/' + $scope.search.dataCriteria + '/select/productYy' + param).success(
						function(data) {
							$scope.productYyList = angular.fromJson(data);
						}	
					);
				}
			);
	}
	
	$scope.storeScheduleList = function(){
		
		$http.get('/distribution/storeScheduleAll').success(
			function(data) {
				
				var res = angular.fromJson(data);
					
				angular.forEach(res, function(item) { 
					item.referenceNo = item.completeDate + item.endCompanyInfo.customerCode + item.endCompanyInfo.cornerCode + item.completeSeq;
				});
				
				$scope.storeSchedule = {
					items : res,
					dragging : false
				};
			});
	}
	
	$scope.wmsTshipmentList();
	$scope.storeScheduleList();
	$scope.selectList();
	
	$scope.erpStoreScheduleAdd = function(){
		
		var obj = {
			completeDate 			: $scope.search.completeDate,
			completeSeq				: $scope.search.completeSeq,
			rfidYn					: $scope.search.rfidYn,
			style 					: $scope.search.style,
			color 					: $scope.search.color,
			size 					: $scope.search.styleSize,
			orderAmount 			: $scope.search.orderAmount,
			anotherStyle 			: $scope.search.color + $scope.search.styleSize,
			endCompanyInfo			: $scope.search.endCompanyInfo,
			bundleCd 				: "",
			completeIfSeq 			: 1,
			releaseAmount 			: 0,
			releaseDate 			: "",
			releaseSeq 				: 0,
			releaseSerial 			: 0,
			releaseUserId			: "",
			stat 					: "I",
			sortingAmount 			: $scope.search.orderAmount,
			completeType			: "R",
			tempStartCustomerCode 	: "100000",
			batchYn					: "N",
			erpCompleteYn			: "N",
			referenceNo				: $scope.search.completeDate + $scope.search.endCompanyInfo.customerCode + $scope.search.endCompanyInfo.cornerCode + $scope.search.completeSeq
		}
		
		$http({
			method : 'POST',
			url : "/distribution/erpStoreSchedule",
			data : obj,
			headers : {
				'Content-Type' : 'application/json; charset=utf-8'
			}
		}).success(function(data, status, headers, config){
			
			if(angular.fromJson(data).resultCode == '1000'){
				modalOpen($uibModal,  "새로운 ERP 매장출고 정보가 추가되었습니다.");
				$scope.storeScheduleList();
			} else {
				modalOpen($uibModal,  angular.fromJson(data).resultMessage);
			}
			
		}).error(function(){
		    	modalOpen($uibModal,  "에러 발생");
		});
	};
	
	$scope.erpStoreScheduleDel = function(){
		
		var objList = [];
		
		angular.forEach($scope.storeSchedule.items, function(item) { 
			if(item.selected){
				objList.push(item);
			} 
		});
		
		confirmOpen($uibModal, "ERP 매장출고 정보를 삭제 하시겠습니까?", function(b){
			$http({
				method : 'DELETE',
				url : "/distribution/erpStoreSchedule",
				data : objList,
				headers : {
					'Content-Type' : 'application/json; charset=utf-8'
				}
			}).success(function(data, status, headers, config){
				
				if(angular.fromJson(data).resultCode == '1000'){
					modalOpen($uibModal,  "선택된 ERP 매장출고 정보가 삭제되었습니다.");
					$scope.storeScheduleList();
				} else {
					modalOpen($uibModal,  angular.fromJson(data).resultMessage);
				}
				
			}).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
			});
		});
		
	};
	
	$scope.wmsTboxUpdate = function(obj){
		
		$http({
			method : 'PUT',
			url : "/distribution/wmsTbox",
			data : obj,
			headers : {
				'Content-Type' : 'application/json; charset=utf-8'
			}
		}).success(function(data, status, headers, config){
			
			if(angular.fromJson(data).resultCode == '1000'){
				modalOpen($uibModal,  "수정되었습니다.");
				$scope.wmsTboxList();
			};
			
		}).error(function(){
		    	modalOpen($uibModal,  "에러 발생");
		});
	};
	
	$scope.wmsTboxDelete = function(obj){
		
		confirmOpen($uibModal, "박스 정보를 삭제 하시겠습니까?", function(b){
			
			if(b){
				
				$http({
					method : 'DELETE',
					url : "/distribution/wmsTbox",
					data : obj.styleList,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
						modalOpen($uibModal,  "삭제되었습니다.");
						$scope.wmsTshipmentList();
					};
					
				}).error(function(){
				    	modalOpen($uibModal,  "에러 발생");
				});
			}
		});
	};
	
	$scope.wmsTboxAdd = function(obj){
		
		$http({
			method : 'POST',
			url : "/distribution/wmsTbox",
			data : obj,
			headers : {
				'Content-Type' : 'application/json; charset=utf-8'
			}
		}).success(function(data, status, headers, config){
			
			if(angular.fromJson(data).resultCode == '1000'){
				modalOpen($uibModal,  "추가되었습니다.");
				$scope.wmsReleaseList();
			};
			
		}).error(function(){
		    	modalOpen($uibModal,  "에러 발생");
		});
	};
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	
	$scope.getSelectedItemsIncluding = function(list, item) {
		item.selected = true;
	    return list.items.filter(function(item) { return item.selected; });
	};

	$scope.onDragstart = function(list, event) {
		list.dragging = true;
	};
	
	$scope.onDrop = function(list, items, index) {
		 angular.forEach(items, function(item) { item.selected = false; });
		 
	     list.items = list.items.slice(0, index)
	                  .concat(items)
	                  .concat(list.items.slice(index));
	     
	     return true;
	}
	
	$scope.onSelectDrop = function(list, items, index) {
		
		var item = items[0];
		var duplicationCheck = false;
		
		console.log($scope.data.tboxPickList);
		
		angular.forEach($scope.data.tboxPickList, function(temp) { 
			if(temp.referenceNo == item.referenceNo &&
			   temp.style == item.style &&
			   temp.color == item.color &&
			   temp.size == item.size){
				duplicationCheck = true;
			}
		});
		
		if(duplicationCheck){
			modalOpen($uibModal,  "중복된 출고예정정보 데이터가 있습니다.");
			return;
		}
		
		var obj = {
			style 		: item.style,
			color 		: item.color,
			size		: item.size,
			referenceNo	: item.referenceNo,
			pickQty		: item.orderAmount,
			tempData	: item,
			orderQty    : randomIntegerString(15)
		};
		
		$scope.data.tboxPickList.push(obj);
		
		angular.forEach(items, function(item) { item.selected = false; });
	    list.items = list.items.slice(0, index)
        					   .concat(items)
        					   .concat(list.items.slice(index));
	    return true;
	}

	$scope.onMoved = function(list) {
	     list.items = list.items.filter(function(item) { return !item.selected; });
	};
	
	$scope.tboxDel = function(obj, index){
		
		$scope.data.tboxPickList.splice(index, 1);
		
		angular.forEach($scope.storeSchedule.items, function(item) { 
			if(item.style == obj.style && item.color == obj.color && item.size == obj.size && item.pickQty == obj.pickQty){
				item.selected = false;
			}
		});
		
		if($scope.data.tboxPickList.length == 0){
			$scope.data.referenceNo = "";
			$scope.data.tboxPickList = [];
			$scope.data.key.shipmentNo = "";
		}
	}
	
	$scope.tboxAllDel = function(){
		
		angular.forEach($scope.data.tboxPickList, function(obj) {
			
			angular.forEach($scope.storeSchedule.items, function(item) { 
				if(item.style == obj.style && item.color == obj.color && item.size == obj.size && item.pickQty == obj.pickQty){
					item.selected = false;
				}
			});
		});
		
		$scope.data.referenceNo = "";
		$scope.data.tboxPickList = [];
	};
	
	$scope.wmsShipmentAdd = function(){
		
		var lineCount = 10;
		
		angular.forEach($scope.data.tboxPickList, function(obj) { 
			
			if(obj.referenceNo == "" && obj.shipmentNo == ""){
				modalOpen($uibModal,  "출고예정정보 데이터를 먼저 입력해야만 합니다.");
				return;
			}
			
			if(obj.shipmentNo == undefined || obj.shipmentNo == ""){
				obj.shipmentNo = obj.referenceNo;
			}
			
			obj.key = {
				shipmentNo : obj.shipmentNo,
				lineNo : lineCount
			}
			
			obj.rfidFlag = "N";
			obj.stockNm = obj.style + obj.color + obj.size;
			obj.stock = obj.style + obj.color + obj.size;
			obj.wCode = "LW02";
			lineCount += 10;
			
			obj.tshipment = {
				status : "0",
				editWho : "RFID",
				custOrderTypeCd : "C",
				referenceNo : obj.referenceNo,
				key : {
					wCode : "LW02",
					shipmentNo : obj.shipmentNo
				}
			}
		});
		
		$http({
			method : 'POST',
			url : "/distribution/wmsTshipment",
			data : $scope.data.tboxPickList
		}).success(function(data, status, headers, config){
			
			if(angular.fromJson(data).resultCode == '1000'){
				modalOpen($uibModal,  "WMS 출고예정 정보가 추가 되었습니다.");
				$scope.wmsTshipmentList();
			} else {
				modalOpen($uibModal,  angular.fromJson(data).resultMessage);
			}
			
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
	};
	
	$scope.wmsShipmentDel = function(obj){
		
		confirmOpen($uibModal, "Tshipment 정보를 삭제 하시겠습니까?", function(b){
			
			if(b){
				$http({
					method : 'DELETE',
					url : "/distribution/wmsTshipment",
					data : obj,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
						modalOpen($uibModal,  "삭제되었습니다.");
						$scope.wmsTshipmentList();
					};
					
				}).error(function(){
				    	modalOpen($uibModal,  "에러 발생");
				});
			}
		});
	};
	
	$scope.mappingToggle = function(obj){
		
		if(!obj.openFlag){
			
			if(!obj.requestFlag){
				
				var param = generateParam(obj);
				param += "&styleSize=" + obj.size;
				
				$http.get("/" + $scope.search.dataCriteria + "RfidTag/mapping/select" + param).success(
					function(data){
						obj.mappingList = angular.fromJson(data);
						obj.selectMappingList = [];
						obj.openFlag = true;
						obj.mappingAllCheckFlag = false;
						obj.selectMappingAllCheckFlag = false;
						obj.requestFlag = true;
						obj.dragging = false;
						
						angular.forEach(obj.mappingList, function(item) { item.selected = false; });
				});
				
			} else {
				obj.openFlag = true;
				obj.mappingAllCheckFlag = false;
				obj.selectMappingAllCheckFlag = false;
			}
			
		} else {
			obj.openFlag = false;
			obj.mappingAllCheckFlag = false;
			obj.selectMappingAllCheckFlag = false;
		}
	};
	
	$scope.mappingSelect = function(obj){
		
		var tempList = [];
		
		angular.forEach(obj.mappingList, function(item, index) {
			if(item.check){
				var pushFlag = true;
				
				angular.forEach(obj.selectMappingList, function(checkItem) {
					if(item.rfidTag == checkItem.rfidTag){
						pushFlag = false;
					}
				});
				
				if(pushFlag){
					var tempData = angular.copy(item);
					tempData.check = false;
					obj.selectMappingList.push(tempData);
					obj.mappingAllCheckFlag = false;
					
					tempList.push(item);
				}
			}
		});
		
		angular.forEach(tempList, function(temp) {
			obj.mappingList.splice(obj.mappingList.indexOf(temp), 1);
		});
		
	};
	
	$scope.mappingReturn = function(obj){
		
		var tempList = [];
		
		angular.forEach(obj.selectMappingList, function(item, index) {
			if(item.check){
				
				var pushFlag = true;
				
				angular.forEach(obj.mappingList, function(checkItem) {
					if(item.rfidTag == checkItem.rfidTag){
						pushFlag = false;
					}
				});
				
				if(pushFlag){
					var tempData = angular.copy(item);
					tempData.check = false;
					obj.mappingList.push(tempData);
					obj.selectMappingAllCheckFlag = false;
					
					tempList.push(item);
				}
			}
		});
		
		angular.forEach(tempList, function(temp) {
			obj.selectMappingList.splice(obj.selectMappingList.indexOf(temp), 1);
		});
	};
	
	$scope.allCheck = function(obj, flag){
		
		if(flag == "mapping"){
			angular.forEach(obj.mappingList, function(item) {
				if(obj.mappingAllCheckFlag){
					item.check = true;
				} else {
					item.check = false;
				}
			});
		} else {
			angular.forEach(obj.selectMappingList, function(item) {
				if(obj.selectMappingAllCheckFlag){
					item.check = true;
				} else {
					item.check = false;
				}
			});
		}
	};
	/*
	
	$scope.getMappingSelectedItemsIncluding = function(list, item, flag) {
		
		item.selected = true;
		
		if(flag == "mapping"){
		    return list.mappingList.filter(function(item) { return item.selected; });
		} else {
		    return list.selectMappingList.filter(function(item) { return item.selected; });
		}
	};

	$scope.onMappingDragstart = function(list, event, flag) {
		list.dragging = true;
	};
	
	$scope.onMappingDrop = function(list, items, index, flag) {
		
		 angular.forEach(items, function(item) { item.selected = false; });
		 
		 if(flag == "mapping"){
			 list.mappingList = list.mappingList.slice(0, index)
				              .concat(items)
				              .concat(list.mappingList.slice(index));
		 } else {
			 list.selectMappingList = list.selectMappingList.slice(0, index)
						             .concat(items)
						             .concat(list.selectMappingList.slice(index));
		 }
		 return true;
	}
	
	$scope.onMappingMoved = function(list, flag) {
		if(flag == "mapping"){
		    return list.mappingList.filter(function(item) { return !item.selected; });
		} else {
		    return list.selectMappingList.filter(function(item) { return !item.selected; });
		}
	};
	*/
	
	$scope.mappingClick = function(obj){
		
		if(obj.check){
			obj.check = false;
		} else {
			obj.check = true;
		}
	};
	
	$scope.wmsComplete = function(obj){
		
		if(obj.status == '0'){
			modalOpen($uibModal,  "이미 테스트 완료한 Tshipment 정보 입니다.");
			return;
		}
		
		var countCheck = false;
		
		angular.forEach(obj.boxList, function(box) {
			
			angular.forEach(box.styleList, function(item) {
				if(item.rfidFlag == 'N'){
					countCheck = true;
				}
			});
		});
		
		if(countCheck){
			modalOpen($uibModal,  "입고 테스트가 완료되지 않은 박스 정보가 있습니다.");
			return;
		}
		
		confirmOpen($uibModal, "Tshipment 작업 테스트를 마무리 하시겠습니까?", function(b){
			
			if(b){
				
				obj.status = "0";
				
				$http({
					method : 'PUT',
					url : "/distribution/wmsTshipment",
					data : obj,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
						modalOpen($uibModal,  "Tshipment 작업 테스트 완료하였습니다.");
						$scope.wmsTshipmentList();
					} else {
						modalOpen($uibModal, angular.fromJson(data).resultMessage);
					}
					
				}).error(function(){
			    	modalOpen($uibModal,  "에러 발생");
				});
			}
		});
	};
	
	
	// 박스 개별 테스트
	$scope.wmsTboxTest = function(obj){
		
		var boxCompleteCheck = false;
		var boxMappingCheck = false;
		var boxMappingCompareCheck = false;
		
		angular.forEach(obj.styleList, function(style) { 
			if(style.rfidFlag == 'Y'){
				boxCompleteCheck = true;
			}
			
			if(style.erpStoreSchedule.rfidYn == 'Y' && style.selectMappingList == undefined){
				boxMappingCheck = true;
			}
			
			if(style.erpStoreSchedule.rfidYn == 'Y' && style.selectMappingList != undefined && style.pickQty != style.selectMappingList.length){
				boxMappingCompareCheck = true;
			}
		});
		
		if(boxCompleteCheck){
			modalOpen($uibModal,  "이미 테스트 완료한 Tboxpick 정보 입니다.");
			return;
		}
		
		if(boxMappingCheck){
			modalOpen($uibModal,  "맵핑 작업이 완료되지 않았습니다."); 
			return;
		}
		
		if(boxMappingCompareCheck){
			modalOpen($uibModal,  "출고 예정 수량과 선택된 수량이 맞지 않습니다.");
			return;
		}
		
		confirmOpen($uibModal, "박스 정보를 테스트 하시겠습니까?", function(b){
			
			if(b){
				
				var req = {
					barcodeList : []
				};
				
				req.barcodeList = generateReleaseScheduleReq(obj);
				
				$http({
					method : 'POST',
					url : "/api/distributionRfidTag/releaseSchedule",
					data : req,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}	
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
						
						var boxInfo = generateReleaseCompleteReqObj(obj);
						
						req = {
							userSeq : 1,
							type : 1,
							completeYn : "Y",
							boxInfo : boxInfo
						};
							
						$http({
							method : 'POST',	
							url : "/api/distributionRfidTag/releaseComplete",
							data : req,
							headers : {
								'Content-Type' : 'application/json; charset=utf-8'
							}
						}).success(function(completeData, status, headers, config){
								
							if(angular.fromJson(completeData).resultCode == '1000'){
								
								angular.forEach(obj.styleList, function(style) { 
									style.rfidFlag = "Y";
								});
									
								$http({
									method : 'PUT',
									url : "/distribution/wmsTbox",
									data : obj.styleList,
									headers : {
										'Content-Type' : 'application/json; charset=utf-8'
									}
								}).success(function(detailData, status, headers, config){
										
									if(angular.fromJson(detailData).resultCode == '1000'){
											
										$http.get("/api/distributionRfidTag/releaseCompleteAfter?barcode=" + boxInfo.barcode).success(
											function(completeAfterData){
													
												if(angular.fromJson(completeAfterData).resultCode == '1000'){
													modalOpen($uibModal,  "테스트 완료하였습니다.");
													$scope.wmsTshipmentList();
												} else {
													modalOpen($uibModal, angular.fromJson(completeAfterData).resultMessage);
												}
													
											});
									} else {
										modalOpen($uibModal, angular.fromJson(detailData).resultMessage);
									}
								}).error(function(){
									modalOpen($uibModal,  "에러 발생");
								});
							} else {
								modalOpen($uibModal, angular.fromJson(completeData).resultMessage);
							}
								
						}).error(function(){
							modalOpen($uibModal,  "에러 발생");
						});
						
					} else {
						modalOpen($uibModal, angular.fromJson(data).resultMessage);
					}
					
				}).error(function(){
				    	modalOpen($uibModal,  "에러 발생");
				});
			}
		});
	};
	
	// 종합 테스트
	/*
	$scope.wmsTotalTest = function(obj){
		
		if(obj.status == '0'){
			modalOpen($uibModal,  "이미 테스트 완료한 Tshipment 정보 입니다.");
			return;
		}
		
		var countCheck = false;
		
		angular.forEach(obj.tboxPickList, function(item) {
			if(item.erpStoreSchedule.rfidYn == 'Y' && item.selectMappingList != undefined & item.pickQty != item.selectMappingList.length){
				countCheck = true;
			}
		});
		
		if(countCheck){
			modalOpen($uibModal,  "출고 예정 수량과 선택된 수량이 맞지 않습니다.");
			return;
		}
		
		confirmOpen($uibModal, "Tshipment 정보를 종합 테스트 하시겠습니까?", function(b){
			
			if(b){
				
				var req = {
					referenceNo : obj.referenceNo,
					barcodeList : []
				};
				
				req.barcodeList = generateReleaseTotalScheduleReq(obj);
				
				$http({
					method : 'POST',
					url : "/api/distributionRfidTag/releaseSchedule",
					data : req,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}	
				}).success(function(data, status, headers, config){
					
					if(angular.fromJson(data).resultCode == '1000'){
						
						var boxList = generateReleaseTotalCompleteReq(obj);
						
						boxList.forEach(function(boxInfo) {
							req = {
								userSeq : 1,
								referenceNo : obj.referenceNo,
								type : 1,
								completeYn : "Y",
								boxInfo : boxInfo
							};
							
							$http({
								method : 'POST',
								url : "/api/distributionRfidTag/releaseComplete",
								data : req,
								headers : {
									'Content-Type' : 'application/json; charset=utf-8'
								}
							}).success(function(completeData, status, headers, config){
								
								if(angular.fromJson(completeData).resultCode == '1000'){
									
									obj.status = "0";
									
									$http({
										method : 'PUT',
										url : "/distribution/wmsTshipmentTotal",
										data : obj,
										headers : {
											'Content-Type' : 'application/json; charset=utf-8'
										}
									}).success(function(detailData, status, headers, config){
										
										if(angular.fromJson(detailData).resultCode == '1000'){
											
											$http.get("/api/distributionRfidTag/releaseCompleteAfter?referenceNo=" + obj.referenceNo + "&barcode=" + boxInfo.barcode).success(
													
												function(completeAfterData){
													
													if(angular.fromJson(completeAfterData).resultCode == '1000'){
														modalOpen($uibModal,  "테스트 완료하였습니다.");
														$scope.wmsTshipmentList();
													} else {
														modalOpen($uibModal, angular.fromJson(completeAfterData).resultMessage);
													}
													
												});
										} else {
											modalOpen($uibModal, angular.fromJson(detailData).resultMessage);
										}
									}).error(function(){
								    	modalOpen($uibModal,  "에러 발생");
									});
								} else {
									modalOpen($uibModal, angular.fromJson(completeData).resultMessage);
								}
								
							}).error(function(){
							    	modalOpen($uibModal,  "에러 발생");
							});
						});
						
					} else {
						modalOpen($uibModal, angular.fromJson(data).resultMessage);
					}
					
				}).error(function(){
				    	modalOpen($uibModal,  "에러 발생");
				});
				
			}
		});
	};
	*/
	
	$scope.wmsShipmentInit = function(obj){
		
		if(obj.status == '999'){
			modalOpen($uibModal, "이미 초기화 되어있습니다.");
			return;
		}
		
		$http({
			method : 'PUT',
			url : "/distribution/wmsTshipmentInit",
			data : obj,
			headers : {
				'Content-Type' : 'application/json; charset=utf-8'
			}
		}).success(function(data, status, headers, config){
			
			if(angular.fromJson(data).resultCode == '1000'){
				modalOpen($uibModal, "초기화 완료하였습니다.");
				$scope.wmsTshipmentList();
			};
		}).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
		});
	}
	
	$scope.selectItem = function(item, flag){
		
		if(flag == "productYy"){
			$scope.init(flag, item);
			$scope.search.productYy = item.data;
			$scope.search.urlFlag = "productSeason";
		} else if(flag == "productSeason"){
			$scope.init(flag, item);
			$scope.search.productSeason = item.data;
			$scope.search.urlFlag = "style";
		} else if(flag == "style"){
			$scope.init(flag, item);
			$scope.search.style = item.data;
			$scope.search.urlFlag = "color";
		} else if(flag == "color"){
			$scope.init(flag, item);
			$scope.search.color = item.data;
			$scope.search.urlFlag = "size";
		} else if(flag == "styleSize"){
			$scope.init(flag, item);
			$scope.search.styleSize = item.data;
			$scope.search.urlFlag = "orderDegree";
		} 
		
		param = generateParam($scope.search);
		
		$http.get('/' + $scope.search.dataCriteria + '/select/' + $scope.search.urlFlag + param).success(
			function(data) {
				if(flag == "productYy"){
					$scope.productSeasonList = angular.fromJson(data);
				} else if(flag == "productSeason"){
					$scope.styleList = angular.fromJson(data);
				} else if(flag == "style"){
					$scope.colorList = angular.fromJson(data);
				} else if(flag == "color"){
					$scope.sizeList = angular.fromJson(data);
				} else if(flag == "styleSize"){
					$scope.orderDegreeList = angular.fromJson(data);
				} 
			}	
		);
	};
	
	$scope.init = function(flag, item){
		
		if(flag == "all"){
			$scope.search.productSeason = null;
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
		} else if(flag == "productYy" && $scope.search.productYy != item.data){
			$scope.search.productSeason = null;
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
		} else if(flag == "productSeason" && $scope.search.productSeason != item.data){
			$scope.search.style = null;
			$scope.search.color = null;
			$scope.search.styleSize = null;
		} else if(flag == "style" && $scope.search.style != item.data){
			$scope.search.color = null;
			$scope.search.styleSize = null;
		} else if(flag == "color" && $scope.search.color != item.data){
			$scope.search.styleSize = null;
		} 
	};
	
	
	$scope.changeDataCriteria = function(){
		$http.get('/' + $scope.search.dataCriteria + '/select/productYy' + param).success(
			function(data) {
				$scope.productYyList = angular.fromJson(data);
			}	
		);
	}
	
	// 출고예정정보 조회시 박스목록 생성
	function generateReleaseScheduleReq(obj){
		
		var boxList = [];
		
		angular.forEach(obj.styleList, function(item) {
			
			var boxPushFlag = true;
			
			boxList.forEach(function(boxInfo) {
				 if(item.orderQty == boxInfo.barcode){
					 
					 boxPushFlag = false;
					 
					 var pushItem = {
						style 			: item.style,
						color 			: item.color,
						size  			: item.size,
						amount		 	: item.pickQty,
						referenceNo		: item.referenceNo
					 }
					 
					 boxInfo.styleList.push(pushItem);
				 }
			});
			
			if(boxPushFlag){
				
				var boxInfo = {
					barcode : item.orderQty,
					styleList : []
				};
				
				var pushItem = {
						style 			: item.style,
						color 			: item.color,
						size  			: item.size,
						amount		 	: item.pickQty,
						referenceNo		: item.referenceNo
					 }
				
				boxInfo.styleList.push(pushItem);
				
				boxList.push(boxInfo);
			}
		});
		
		return boxList;
	}
	
	// 출고예정맵핑박스 생성
	function generateReleaseCompleteReqObj(obj){
		
		var boxInfo = {
			barcode : obj.barcode,
			styleList : []
		};
			
		angular.forEach(obj.styleList, function(item) {
			 if(item.orderQty == obj.barcode){
				 
				 var styleInfo = {
					style 				: item.style,
					color 				: item.color,
					size				: item.size,
					referenceNo			: item.referenceNo, 
					erpKey				: item.erpStoreSchedule.rfidYn == 'Y' ? item.selectMappingList[0].erpKey : "",
					count				: item.erpStoreSchedule.rfidYn == 'Y' ? item.selectMappingList.length : item.pickQty,
					rfidYn				: item.erpStoreSchedule.rfidYn,
					rfidTagList			: []
				}
									 
				angular.forEach(item.selectMappingList, function(detailItem) {
					var tagInfo = {
						rfidTag : detailItem.rfidTag
					};
										 
					styleInfo.rfidTagList.push(tagInfo);
				});
				 
				boxInfo.styleList.push(styleInfo);
			}
		});
		
		return boxInfo;
	}
	
	function generateReleaseTotalScheduleReq(obj){
		
		var boxList = [];
		
		angular.forEach(obj.tboxPickList, function(item) {
			
			var boxPushFlag = true;
			
			boxList.forEach(function(boxInfo) {
				 if(item.orderQty == boxInfo.barcode){
					 
					 boxPushFlag = false;
					 
					 boxInfo.styleList.push(item.stock);
				 }
			});
			
			if(boxPushFlag){
				
				var boxInfo = {
					barcode : item.orderQty,
					styleList : []
				};
				
				boxInfo.styleList.push(item.stock);
				
				boxList.push(boxInfo);
			}
		});
		
		return boxList;
	}
	
	function generateReleaseTotalCompleteReq(obj){
		
		var boxList = [];
		
		angular.forEach(obj.tboxPickList, function(item) {
			
			var boxPushFlag = true;
			
			boxList.forEach(function(boxInfo) {
				
				 if(item.orderQty == boxInfo.barcode){
					 
					 boxPushFlag = false;
					 
					 var styleInfo = {
						style 				: item.style,
						color 				: item.color,
						size				: item.size,
						erpKey				: item.erpStoreSchedule.rfidYn == 'Y' ? item.selectMappingList[0].erpKey : "-",
						count				: item.erpStoreSchedule.rfidYn == 'Y' ? item.selectMappingList.length : item.pickQty,
						rfidYn				: item.erpStoreSchedule.rfidYn,
						rfidTagList			: []
					 }
					 
					 angular.forEach(item.selectMappingList, function(detailItem) {
						 var tagInfo = {
							rfidTag : detailItem.rfidTag
						 };
						 
						 styleInfo.rfidTagList.push(tagInfo);
					});
					 boxInfo.styleList.push(styleInfo);
				 }
			});
			
			if(boxPushFlag){
				
				var boxInfo = {
					barcode : item.orderQty,
					styleList : []
				};
				
				var styleInfo = {
					style 				: item.style,
					color 				: item.color,
					size				: item.size,
					erpKey				: item.erpStoreSchedule.rfidYn == 'Y' ? item.selectMappingList[0].erpKey : "",
					count				: item.erpStoreSchedule.rfidYn == 'Y' ? item.selectMappingList.length : item.pickQty,
					rfidYn				: item.erpStoreSchedule.rfidYn,
					rfidTagList			: []
				}
						 
				angular.forEach(item.selectMappingList, function(detailItem) {
					 var tagInfo = {
							 rfidTag : detailItem.rfidTag
					 };
							 
					 styleInfo.rfidTagList.push(tagInfo);
				});
				
				boxInfo.styleList.push(styleInfo);
				
				boxList.push(boxInfo);
			}
		});
		
		return boxList;
	}
}]);