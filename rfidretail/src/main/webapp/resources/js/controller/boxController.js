app.controller('boxWorkGroupListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$q', '$timeout', '$routeParams', 'FileSaver', '$uibModal', function($scope, $http, $location, $rootScope, $window, $filter, $q, $timeout, $routeParams, FileSaver, $uibModal) {
	$scope.ajaxFinish = false;
	
	$scope.allCheckFlag = false;

	$scope.search = initSearch("boxWorkGroupSeq", "boxWorkGroupSeq", "desc", angular.fromJson($window.sessionStorage.getItem("groupCurrent")));
	
	$scope.startDate = initDate($scope.search.startDate);
	$scope.endDate = initDate($scope.search.endDate);
	
	if($scope.search.stat == undefined){
		$scope.search.stat = "all";
	}
	
	if($scope.search.defaultDate == undefined){
		$scope.search.defaultDate = "1";
	}
	
	if($scope.search.size == undefined){  $scope.search.size = "10"; }
	
	$scope.search.type = $routeParams.type;
	$scope.search.tempCompanyList = [];
	
	var param = generateParam($scope.search);
	
	$http.get('/company/getCompanyList').success(
			function(data) {
				$scope.companyList = angular.fromJson(data);
						
				angular.forEach($scope.companyList, function(value, key) {
					
					if($scope.companyList[key].companySeq == $scope.search.startCompanySeq){
						$scope.search.startCompanyInfo = $scope.companyList[key];
					}
					
					if($rootScope.userRole != 'production'){
						if($scope.companyList[key].companySeq == $scope.search.endCompanySeq){
							$scope.search.endCompanyInfo = $scope.companyList[key];
						}
					} else {
						
						/*
						if($scope.companyList[key].customerCode == "100000"){
							$scope.search.endCompanyInfo = $scope.companyList[key];
						}
						*/
						
						if($scope.companyList[key].type == '4' && ($scope.companyList[key].customerCode == "100000" || $scope.companyList[key].customerCode == "100016")){
							$scope.search.tempCompanyList.push($scope.companyList[key]);
						}
					}
				});
				
				if($rootScope.userRole == 'production' || $rootScope.userRole == 'sales' || $rootScope.userRole == 'special'){
					$scope.search.startCompanyInfo = $rootScope.user.principal.companyInfo;
				} 
					
				httpGetList("/box/boxWorkGroup", param, $scope, $http, true);
			}
		);

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

	$(".tempBarcodeDiv").hide();

	$scope.goPage = function(page) {
		
		if ($scope.current == page) {
			return;
		}

		if ($scope.end < page) {
			return;
		}

		if (page == 0) {
			return;
		}

		$scope.search.page = page - 1;

		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;

		httpGetList("/box/boxWorkGroup", param, $scope, $http);

	};

	$scope.clickSearch = function() {
		
		if ($scope.search.text == undefined) {
			$scope.search.text = "";
		}
		
		$scope.search.page = 0;

		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;

		httpGetList("/box/boxWorkGroup", param, $scope, $http);

	};

	$scope.sort = function(sortName) {

		if ($scope.search.sortOrder == "desc") {
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}

		$scope.search.sortName = sortName;

		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;

		httpGetList("/box/boxWorkGroup", param, $scope, $http);
	};

	$scope.detail = function(obj) {
		$window.sessionStorage.setItem("groupCurrent", angular.toJson(setSearch($scope.search)));
		$window.sessionStorage.removeItem("current");
		$location.url("/box/boxList?seq=" + obj.boxWorkGroupSeq + "&type=" + $routeParams.type);
	};

	$scope.goAdd = function(flag) {
		var obj = {
				flag : flag,
				type : $routeParams.type
		};
			
		var modalInstance = $uibModal.open({
			animation: true,
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: '/box/boxAddPop',
			controller: 'boxAddPopController',
			size: 'xxlg',
			resolve: {
				obj: function () {
					return obj;
				}
			}
		});
		modalInstance.result.then(function (callback) {
			if(callback == "ok"){
				httpGetList("/box/boxWorkGroup", param, $scope, $http);
			}
		}, function () {
				
		});
	};
	
	$scope.format = 'yyyyMMdd';
	
	if($rootScope.userRole == 'production' || $rootScope.userRole == 'sales' || $rootScope.userRole == 'special'){
		var minDate = new Date();
		minDate.setYear(settingDate.getFullYear()-1);
		$scope.dateOptions = {
			formatYear: 'yy',
			maxDate: new Date(),
			minDate: minDate ,
			startingDay: 1
		};
	} else {
		$scope.dateOptions = {
			formatYear: 'yy',
			maxDate: new Date(2020, 5, 22),
			minDate: new Date(2017, 1, 1),
			startingDay: 1
		};
	}
	
	
	$scope.startDateOpen = function() {
	    $scope.search.startDateOpened = true;
	};
	
	$scope.endDateOpen = function() {
	    $scope.search.endDateOpened = true;
	};
	
	/** 상단 검색 */
	$scope.headSearch = function(){
		
		if($scope.startDate == undefined || $scope.startDate == ""){
			return;
		}
		
		if($scope.endDate == undefined || $scope.endDate == ""){
			return;
		}
		
		if($scope.search.startCompanyInfo == undefined){
			$scope.search.startCompanySeq = 0;
		} else {
			$scope.search.startCompanySeq = $scope.search.startCompanyInfo.companySeq;
		}
		
		if($scope.search.endCompanyInfo == undefined){
			$scope.search.endCompanySeq = 0;
		} else {
			$scope.search.endCompanySeq = $scope.search.endCompanyInfo.companySeq;
		}
		
		$scope.search.startDate =  $scope.startDate.yyyymmdd();
		$scope.search.endDate =  $scope.endDate.yyyymmdd();
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/box/boxWorkGroup", param, $scope, $http);
		
	};
	
	$scope.del = function() {	
		
		var checkflag = false;
		var checkList = new Array();
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				checkList.push($scope.list[key]);
				checkflag = true;
			}
		});
		
		if(!checkflag){
			modalOpen($uibModal,  "선택된 박스 작업이 없습니다.");
			return;
		}
		
		confirmOpen($uibModal, "체크된 박스 작업을 삭제 하시겠습니까?", function(b){
			if(b){
				$http({
					method : 'DELETE',
					url : '/box/boxWorkGroup',
					data : checkList,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config) {
					if (status == 200) {
						modalOpen($uibModal,  "박스 작업 삭제 완료되었습니다.", function() {
							httpGetList("/box/boxWorkGroup", param, $scope, $http);
							
							$scope.allCheckFlag = false;
						});
					} else {
						modalOpen($uibModal,  "에러발생");
					}
				}).error(function(data, status, headers, config) {
					
					if (status == 409) {
						modalOpen($uibModal,  "출고 예정, 출고 완료 박스는 삭제할 수 없습니다.");
						return;
					}
					console.log(status);
				});
			}
		});
	};
	
	/**
	 * 체크된 박스 PDF 다운로드
	 */
	$scope.goPdfDownload = function(flag){
		
		if(flag == "check"){
			
			var checkflag = false;
			var checkList = new Array();
			var totalCount = 0;

			angular.forEach($scope.list, function(value, key) {
				if($scope.list[key].check){
					checkList.push($scope.list[key]);
					totalCount += $scope.list[key].boxInfo.length;
					checkflag = true;
				}
			});
			var lng = checkList.length;
			$scope.tempLng = checkList.length;
			$scope.tempCheck = checkList;
			
			if(!checkflag){
				modalOpen($uibModal,  "선택된 박스가 없습니다.");
				return;
			}
			
			if(checkflag){
				$(".tempBarcodeDiv").show();
				$(".loading-spiner-holder").show();
			}

			function boxList(j) {
				
				var arr = new Array();
				
				for(var m = 0; m < j; m++) {
					arr.push(checkList[m].boxInfo);
				}
				return arr;
			}

			$scope.boxWorkList = boxList(lng);
			$scope.totalCount = totalCount;
			
		} else {
			// 대량 PDF 다운로드 페이지로 이동
			$location.url("/box/boxPdfDownload");
		}
	}
	
	$scope.currentCount = 0;
	
	$scope.onEnd = function(count){
		
		$scope.currentCount += count;
		
		if($scope.totalCount == $scope.currentCount){
			
			$timeout(function(){
				for(var i = 0; i < $scope.tempLng; i++) {
		        	
		        	//이차원 배열 부분
	                function column(j) {
			            if($scope.boxWorkList[i]==null){
			                return null;
			            }	       
			            
			            if($scope.boxWorkList[i][j] == undefined){
			            	return null;
			            }
			            
			            var obj;
			            var contents="";
			            
			            var html2obj = html2canvas($('#barcodeImg_' + $scope.boxWorkList[i][j].boxSeq + ' .barcode'));
		                var queue  = html2obj.parse();
		                var canvas = html2obj.render(queue);
		                var img = canvas.toDataURL();
		                
		                var startVal;
	                	if($scope.boxWorkList[i][j].startCompanyInfo.name.length > 15){
	                		startVal = [ { text: '출발지', alignment: 'center' }, { text:  $scope.boxWorkList[i][j].startCompanyInfo.name, alignment: 'center', fontSize : 11 }];
	                	} else {
	                		startVal = [ { text: '출발지', alignment: 'center' }, { text:  $scope.boxWorkList[i][j].startCompanyInfo.name, alignment: 'center' }];
	                	}
		                
			            if($scope.boxWorkList[i][j].type == '01'){contents = "생산";} else if ($scope.boxWorkList[i][j].type == '02'){contents = "물류"}else{contents="판매";}			            
			            obj = {
			            	alignment: 'justify',
			            	style: 'tableExample',
			            	table: {
			            		headerRows: 1,
			            		widths: [80, 150],
			            		heights: [17, 17, 17, 17, 17, 80, 10],
			            		body: [
			            			[ { text: '내용', alignment: 'center' }, { text:contents, alignment: 'center' }],
			            			[ { text: '날짜', alignment: 'center' },  { text: $scope.boxWorkList[i][j].createDate, alignment: 'center' }],
			            			[ { text: '박스번호', alignment: 'center' }, { text: $scope.boxWorkList[i][j].boxNum, alignment: 'center' }],
		            				startVal,
			            			[ { text: '도착지', alignment: 'center' }, { text:  $scope.boxWorkList[i][j].endCompanyInfo.name, alignment: 'center' }],
			            			[ { image: img, width: 180, height: 90, colSpan: 2, alignment: 'center' ,border: [true, true , true, false], margin: [0,0,0,-10] }],
		                			[ { text: $scope.boxWorkList[i][j].barcode, alignment: 'center', colSpan: 2, border: [true, false , true, true] }]
			            		]
			            	}
			            };
			            
			            return obj;

					}
	                
	                var tempColumn = [];
					
					function columns(i) {
						var k=i+1;
						
						return{
							columns: [
								column(i+i), column(i+k)
							]
			            }
					}
					
					var c=[];
					for(var k=0;k<$scope.boxWorkList[i].length/2; k++){
						c.push(columns(k));
					}

					var docDefinition = {
						content: [c],
						styles: {
							tableExample: {
								fontSize: 14,
								margin: [-15, 1, 60, 53]
			                }
			            }
			        };
					
					var d = new Date();
			        month = '' + (d.getMonth() + 1),
			        day = '' + d.getDate(),
			        year = d.getFullYear();
				    if (month.length < 2) month = '0' + month;
				    if (day.length < 2) day = '0' + day;
				    pdfMake.createPdf(docDefinition).download("Barcode_" + year+month+day + "_" + $scope.tempCheck[i].boxWorkGroupSeq + ".pdf");
				    
				}
				
				$(".loading-spiner-holder").hide();
				$(".tempBarcodeDiv").hide();
				
				var checkList = new Array();

				angular.forEach($scope.list, function(value, key) {
					if($scope.list[key].check){
						checkList.push($scope.list[key]);
						checkflag = true;
					}
				});
				
				$http({
					method : 'POST',
					url : "/box/boxWorkGroup/updateStat",
					data: checkList,
					headers: {'Content-Type': 'application/json; charset=utf-8'} 
				}).success(function(data, status, headers, config){
					httpGetList("/box/boxWorkGroup", param, $scope, $http);
					
					$scope.allCheckFlag = false;
				});
				
				$scope.currentCount = 0;
				
	        }, 2);
			
		}
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
	
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/box/boxWorkGroup", param, $scope, $http);
	};
	
	$scope.bc = {
			format: 'CODE128',
		    lineColor: '#000000',
		    width: 2,
		    height: 100,
		    displayValue: false,
		    fontOptions: '',
		    font: 'monospace',
		    textAlign: 'center',
		    textPosition: 'bottom',
		    textMargin: 2,
		    fontSize: 20,
		    background: '#ffffff',
		    margin: 0,
		    marginTop: undefined,
		    marginBottom: undefined,
		    marginLeft: undefined,
		    marginRight: undefined,
		    valid: function (valid) {
		    }
	}
	
	/**
	 * 박스바코드 엑셀 다운로드
	 */
	$scope.goExcelDownload = function(){
		
		var checkList = new Array();
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				checkList.push($scope.list[key]);
			}
		});
		
		if(checkList.length == 0){
			modalOpen($uibModal,  "선택된 박스 작업 정보가 없습니다.");
			return;
		}
		
		$http({
	    	method : 'POST',
	    	data : checkList,
	    	url : '/box/boxWorkGroupExcelDownload',
	    	responseType: 'arraybuffer'
	    }).success(function(data, status, headers, config){
	    	if(data){
	    		var type = headers('Content-Type');
	            var disposition = headers('Content-Disposition');
	            if (disposition) {
	                var match = disposition.match(/.*filename=\"?([^;\"]+)\"?.*/);
	                if (match[1])
	                    defaultFileName = match[1];
	            }
	            defaultFileName = defaultFileName.replace(/[<>:"\/\\|?*]+/g, '_');
	            defaultFileName = defaultFileName + "_엑셀다운로드.xls";
	            
	    		var file = new Blob([data], { type: "application/vnd.ms-excel;charset=charset=utf-8" });
	    		FileSaver.saveAs(file, defaultFileName);
	    		
	    		$scope.allCheckFlag = false;
	    		
	    	} else {
	    		modalOpen($uibModal,  "에러 발생");
	    	}
	    }).error(function(){
	    	modalOpen($uibModal,  "에러 발생");
	    });
	};
	
	$scope.$watch(function(){
		
		return $rootScope.parentCodeList;
		
		}, function() {
			
			if($rootScope.parentCodeList != undefined){
				
				$scope.statList = $filter('filter')($rootScope.parentCodeList, {codeValue: '10010'})[0];
				$scope.typeList = $filter('filter')($rootScope.parentCodeList, {codeValue: '10001'})[0];
			} 
	}, true);
	
	if($rootScope.noticeMode){
		modalOpen($uibModal, "바코드 PDF 다운로드는 크롬(Chrome) 웹브라우저로 진행해주시길 바랍니다.익스플로러로 진행 시 정상적으로 바코드 PDF 다운로드가 안 될 수 있습니다.* 바코드 출력 후 바코드 번호와 출력물 검수 확인 부탁드립니다.");
	};
}]);

app.controller('boxListController', ['$scope', '$http', '$location', '$rootScope', '$window', '$filter', '$routeParams', '$uibModal', '$routeParams', function($scope, $http, $location, $rootScope, $window, $filter, $routeParams, $uibModal, $routeParams) {

	$scope.ajaxFinish = false;
	
	$scope.allCheckFlag = false;

	$scope.search = initSearch("boxNum", "boxNum", "asc", angular.fromJson($window.sessionStorage.getItem("current")));
	
	$scope.search.seq = $routeParams.seq;
	$scope.type = $routeParams.type;
	
	if($scope.search.type == undefined){
		$scope.search.type = "all";
	}
	
	if($scope.search.stat == undefined){
		$scope.search.stat = "all";
	}
	
	if($scope.search.size == undefined){  $scope.search.size = "10"; }
	
	var param = generateParam($scope.search);
	
	httpGetList("/box", param, $scope, $http, true);
	
	$(".tempBarcodeDiv").hide();

	$scope.goPage = function(page) {
		$("#allCheck").prop("checked",false);
		
		if ($scope.current == page) {
			return;
		}

		if ($scope.end < page) {
			return;
		}

		if (page == 0) {
			return;
		}

		$scope.search.page = page - 1;

		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;

		httpGetList("/box", param, $scope, $http);

	};

	$scope.clickSearch = function() {
		$("#allCheck").prop("checked",false);
		
		if ($scope.search.text == undefined) {
			$scope.search.text = "";
		}
		
		$scope.search.page = 0;

		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;

		httpGetList("/box", param, $scope, $http);

	};

	$scope.sort = function(sortName) {
		$("#allCheck").prop("checked",false);
		
		if ($scope.search.sortOrder == "desc") {
			$scope.search.sortOrder = "asc";
		} else {
			$scope.search.sortOrder = "desc";
		}

		$scope.search.sortName = sortName;

		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;

		httpGetList("/box", param, $scope, $http);
	};

	$scope.detail = function(obj) {
//		$window.sessionStorage.setItem("current", angular.toJson(setSearch($scope.search)));
//		$location.url("/box/boxDetail?seq=" + obj.boxSeq + "&type=" + $routeParams.type);
		
		detailPopOpen($uibModal, obj, "/box/boxDetailPop", "boxDetailPopController", "xxlg");
	};

	$scope.goAdd = function(flag) {
//		$location.url("/box/boxAdd?flag=" + flag + "&type=" + $routeParams.type);
		
		var obj = {
			flag : flag,
			type : type
		};
		
		var modalInstance = $uibModal.open({
			animation: true,
			ariaLabelledBy: 'modal-title',
			ariaDescribedBy: 'modal-body',
			templateUrl: '/box/boxAddPop',
			controller: 'boxAddPopController',
			size: 'xxlg',
			resolve: {
				obj: function () {
					return obj;
				}
			}
		});
		modalInstance.result.then(function (callback) {
			if(callback == "ok"){
				httpGetList("/box", param, $scope, $http);
			}
		}, function () {
			
		});
	};
	
	/** 상단 검색 */
	$scope.headSearch = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/box", param, $scope, $http);
	};
	
	/**
	 * 체크된 박스 PDF 다운로드
	 */
	$scope.goPdfDownload = function(flag){
		
		if(flag == "check"){
			
			var checkflag = false;
			var checkList = new Array();
			
			angular.forEach($scope.list, function(value, key) {
				if($scope.list[key].check){
					checkList.push($scope.list[key]);
					checkflag = true;
				}
			});
			
			if(!checkflag){
				modalOpen($uibModal,  "선택된 박스가 없습니다.");
				return;
			}
			
			if(checkflag){
				$(".tempBarcodeDiv").show();
			}
			
			function column(i) {
                if(checkList[i]==null){
                    return null;
                }
                
                var html2obj = html2canvas($('#barcodeImg_' + checkList[i].boxSeq + ' .barcode'));
                var queue  = html2obj.parse();
                var canvas = html2obj.render(queue);
                var img = canvas.toDataURL();
                
                var startVal;
                if(checkList[i].startCompanyInfo.name.length > 15){
                	startVal = [ { text: '출발지', alignment: 'center' }, { text:  checkList[i].startCompanyInfo.name, alignment: 'center', fontSize : 11 }];
                } else {
                	startVal = [ { text: '출발지', alignment: 'center' }, { text:  checkList[i].startCompanyInfo.name, alignment: 'center' }];
                }
                
                var obj;
                var contents="";
                if(checkList[i].type == '01'){contents = "생산";} else if (checkList[i].type == '02'){contents = "물류"}else{contents="판매";}
                obj = {
                	alignment: 'justify',
                	style: 'tableExample',
                	table: {
                		headerRows: 1,
                		/*widths: [100, 150],
                		heights: [15, 15, 15, 15, 15, 80, 15],*/
                		widths: [80, 150],
	            		heights: [17, 17, 17, 17, 17, 80, 10],
                		body: [
                			[ { text: '내용', alignment: 'center' }, { text:contents, alignment: 'center' }],
                			[ { text: '날짜', alignment: 'center' },  { text:checkList[i].createDate, alignment: 'center' }],
                			[ { text: '박스번호', alignment: 'center' }, { text: checkList[i].boxNum, alignment: 'center' }],
							startVal,
                			[ { text: '도착지', alignment: 'center' }, { text:  checkList[i].endCompanyInfo.name, alignment: 'center' }],
                			[ { image: img, width: 180, height: 90, colSpan: 2, alignment: 'center' ,border: [true, true , true, false], margin: [0,0,0,-10] }],
                			[ { text: checkList[i].barcode, alignment: 'center', colSpan: 2, border: [true, false , true, true] }]
                		]
                	}
                };
                
                return obj;
			}
			function columns(i) {
				var k=i+1;
				return{
					columns: [
						column(i+i), column(i+k)
					]
                }
			}                         
			var c=[];
			for(var k=0;k<checkList.length/2; k++){
				c.push(columns(k));
			}
			
			var docDefinition = {
				content: [c],
				/*styles: {
					tableExample: {
						fontSize: 15,
						margin: [-25, -8, 50, 56]
                    }
                }*/
				styles: {
					tableExample: {
						fontSize: 14,
						margin: [-15, 1, 60, 53]
	                }
	            }
            };
			
			var d = new Date();
	        month = '' + (d.getMonth() + 1),
	        day = '' + d.getDate(),
	        year = d.getFullYear();
		    if (month.length < 2) month = '0' + month;
		    if (day.length < 2) day = '0' + day;
            pdfMake.createPdf(docDefinition).download("Barcode_"+year+month+day+".pdf");
            
            $(".tempBarcodeDiv").hide();
            
            $scope.allCheckFlag = false;
            
		} else {
			// 대량 PDF 다운로드 페이지로 이동
			$location.url("/box/boxPdfDownload");
		}
	}
	
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
	
	/** 목록 사이즈 지정 */
	$scope.changeSearchSize = function(){
		
		$scope.search.page = 0;
		
		param = generateParam($scope.search);
		
		$scope.allCheckFlag = false;
		
		httpGetList("/box", param, $scope, $http);
	};
	
	$scope.bc = {
			format: 'CODE128',
		    lineColor: '#000000',
		    width: 2,
		    height: 100,
		    displayValue: false,
		    fontOptions: '',
		    font: 'monospace',
		    textAlign: 'center',
		    textPosition: 'bottom',
		    textMargin: 2,
		    fontSize: 20,
		    background: '#ffffff',
		    margin: 0,
		    marginTop: undefined,
		    marginBottom: undefined,
		    marginLeft: undefined,
		    marginRight: undefined,
		    valid: function (valid) {
		    }
	}
	
	$scope.$watch(function(){
		
		return $rootScope.parentCodeList;
		
		}, function() {
			
			if($rootScope.parentCodeList != undefined){
				
				$scope.statList = $filter('filter')($rootScope.parentCodeList, {codeValue: '10010'})[0];
				$scope.typeList = $filter('filter')($rootScope.parentCodeList, {codeValue: '10001'})[0];
			} 
	}, true);
	
	$scope.openBoxMapping = function () {
		
		var objList = [];
		
		angular.forEach($scope.list, function(value, key) {
			if($scope.list[key].check){
				objList.push($scope.list[key]);
			}
		});
		
		if(objList.length > 1 || objList.length == 0){
			modalOpen($uibModal,  "하나의 박스를 선택해주세요.");
	    	return;
		}
			
		var boxMappingModalInstance = $uibModal.open({
	    	animation: true,
	    	ariaLabelledBy: 'modal-title',
	    	ariaDescribedBy: 'modal-body',
	    	templateUrl: '/customTemplate/customBoxMappingTag',
	    	controller: 'boxMappingTagPopupController',
	    	size: 'xlg',
	    	resolve: {
	    		boxInfo: function () {
	    			return objList[0];
	    		}
	    	}
	    });
		boxMappingModalInstance.result.then(function (selectedItem) {
			
			httpGetList("/box", param, $scope, $http);
			
			$scope.allCheckFlag = false;
			
	    }, function () {
//	        $log.info('Modal dismissed at: ' + new Date());
	    });
	};
	
}]);

app.controller('boxAddPopController', ['$scope', '$http', '$location', '$routeParams', '$rootScope', 'verificationFactory', '$filter', '$uibModalInstance', 'obj', '$uibModal', function($scope, $http, $location, $routeParams, $rootScope, verificationFactory, $filter, $uibModalInstance, obj, $uibModal) {

	$scope.ajaxFinish = false;
	
	$scope.flag = obj.flag;
	$scope.type = obj.type;
	
	$scope.data = {
		companyList : []
	}

	$scope.format = 'yyyyMMdd';
	
	$scope.dateOptions = {
		formatYear: 'yy',
		maxDate: new Date(2020, 5, 22),
		minDate: new Date(2018, 1, 1),
		startingDay: 1
	};
	
	$scope.dateOpenAll = function() {
	    $scope.dateOpenedAll = true;
	};
	
	$scope.dateOpenOne = function() {
	    $scope.dateOpenedOne = true;
	};
	
	if($rootScope.userRole == 'production'){
		$scope.data.boxType = "01";
	} else if($rootScope.userRole == 'distribution'){
		$scope.data.boxType = "02";
	} else if($rootScope.userRole == 'sales' || $rootScope.userRole == 'special'){
		$scope.data.boxType = "03";
	} else {
		$scope.data.boxType = "01";
	}
	
	$http.get('/company/getCompanyList').success(
		function(data) {
			$scope.tempCompanyList = angular.fromJson(data);
			
			angular.forEach($scope.tempCompanyList, function(value, key) {
				if($rootScope.userRole == 'production'){
					
					/*
					if($scope.tempCompanyList[key].customerCode == "100000"){
						$scope.data.endCompanyInfo = $scope.tempCompanyList[key];
					}
					*/
					
					//if($scope.tempCompanyList[key].type == '4' && ($scope.tempCompanyList[key].customerCode == "100000" || $scope.tempCompanyList[key].customerCode == "100016")){
					//수출용창고 오픈전까지 박스 일괄 추가 작업 화면에 노출되지 않도록 요청... 스파이더 최영민 과장님(2019.09.11)
					if($scope.tempCompanyList[key].type == '4' && $scope.tempCompanyList[key].customerCode == "100000"){
						$scope.data.companyList.push($scope.tempCompanyList[key]);
					}
				} else if($rootScope.userRole == 'sales' || $rootScope.userRole == 'special'){
					if($scope.tempCompanyList[key].type == '4' || $scope.tempCompanyList[key].type == '5' || $scope.tempCompanyList[key].type == '6'){
						$scope.data.companyList.push($scope.tempCompanyList[key]);
					}
				} else {
					$scope.data.companyList.push($scope.tempCompanyList[key]);
				}
			});
			
			if($rootScope.userRole != 'admin'){
				$scope.data.startCompanyInfo = $rootScope.user.principal.companyInfo;
			}
			
			$scope.ajaxFinish = true;
		}
	);

	$scope.addAll = function() {
		
		if($scope.data.createDate == undefined || $scope.data.createDate == ''){
			modalOpen($uibModal,  "날짜를 입력하세요.");
			return;
		}
		
		if($scope.data.startBoxNum == undefined || $scope.data.startBoxNum == ''){
			modalOpen($uibModal,  "시작 박스번호를 입력하세요.");
			return;
		}
		
		if($scope.data.endBoxNum == undefined || $scope.data.endBoxNum == ''){
			modalOpen($uibModal,  "종료 박스번호를 입력하세요.");
			return;
		}
		
		if($scope.data.startCompanyInfo == undefined || $scope.data.startCompanyInfo == ''){
			modalOpen($uibModal,  "출발지를 입력하세요.");
			return;
		}
		
		if($scope.type != '03'){
			if($scope.data.endCompanyInfo == undefined || $scope.data.endCompanyInfo == ''){
				modalOpen($uibModal,  "도착지를 입력하세요.");
				return;
			}
		} else {
			$scope.data.endCompanyInfo = $scope.data.startCompanyInfo;
		}
		
		if($scope.data.startCompanyInfo.customerCode == undefined || $scope.data.startCompanyInfo.customerCode == ''){
			modalOpen($uibModal,  "해당 업체는 출발지로 선택할 수 없습니다.");
			return;
		}
		
		var startBoxNum = $scope.data.startBoxNum;
		var endBoxNum = $scope.data.endBoxNum;
		
		var maxLength = (endBoxNum - startBoxNum) + startBoxNum;
		
		var reqList = [];
		var count = 1;
		var reqObj;
		var req;
		
		var returnYn = "N";
		
		if($scope.data.boxType == "03" && $scope.data.endCompanyInfo.type == '4'){
			returnYn = "Y";
		}
		
		for(var i=startBoxNum ; i<=maxLength; i++){
			if(count % 500 == 1){
				
				var maxVal = 0;
				if((maxLength - count) < 500){
					maxVal = maxLength;
				} else {
					maxVal = (i + 500) - 1;
				}
				
				reqObj = {
					startBoxNum : pad(i, 4),
					endBoxNum : pad(maxVal, 4),
					startCompanyInfo : $scope.data.startCompanyInfo,
					endCompanyInfo : $scope.data.endCompanyInfo,
					stat : "1",
					type : $scope.data.boxType,
					createDate : $scope.data.createDate.yyyymmdd(),
					boxInfo : []
				};
			}
			
			req = {
				type : $scope.data.boxType,
				boxNum : pad(i, 4),
				startCompanyInfo : $scope.data.startCompanyInfo,
				endCompanyInfo : $scope.data.endCompanyInfo,
				createDate : $scope.data.createDate.yyyymmdd(),
				barcode : $scope.data.boxType + $scope.data.createDate.yyyymmdd().substring(2) + pad(i, 4) + $scope.data.startCompanyInfo.code + $scope.data.endCompanyInfo.code,
				returnYn : returnYn,
				stat : "1"
			}
			reqObj.boxInfo.push(req);
			
			if(count % 500 == 0 || i == maxLength){
				reqList.push(reqObj);
			}
			
			count ++;
		}
		
		$http({
			method : 'POST',
			url : '/box/boxWorkGroup/all',
			data : reqList,
			headers : {
				'Content-Type' : 'application/json; charset=utf-8'
			}
		}).success(function(data, status, headers, config) {
			if (status == 200) {
				modalOpen($uibModal,  "박스 작업 추가 완료되었습니다.", function() {
					$uibModalInstance.close("ok");
				});
			} else {
				modalOpen($uibModal,  "에러발생");
			}
		}).error(function(data, status, headers, config) {

			if (status == 409) {
				modalOpen($uibModal,  "중복된 박스 바코드 값이 있습니다.");
			};
			console.log(status);
		});
	};
	
	$scope.add = function() {
		
		if($scope.data.createDate == undefined || $scope.data.createDate == ''){
			modalOpen($uibModal,  "날짜를 입력하세요.");
			return;
		}
		
		if($scope.data.boxNum == undefined || $scope.data.boxNum == ''){
			modalOpen($uibModal,  "박스번호를 입력하세요.");
			return;
		}
		
		if($scope.data.startCompanyInfo == undefined || $scope.data.startCompanyInfo == ''){
			modalOpen($uibModal,  "출발지를 입력하세요.");
			return;
		}
		
		if($scope.type != '03'){
			if($scope.data.endCompanyInfo == undefined || $scope.data.endCompanyInfo == ''){
				modalOpen($uibModal,  "도착지를 입력하세요.");
				return;
			}
		} else {
			$scope.data.endCompanyInfo = $scope.data.startCompanyInfo;
		}
		
		if($scope.data.startCompanyInfo.customerCode == undefined || $scope.data.startCompanyInfo.customerCode == ''){
			modalOpen($uibModal,  "해당 업체는 출발지로 선택할 수 없습니다.");
			return;
		}
		
		var returnYn = "N";
		
		if($scope.data.boxType == "03" && $scope.data.endCompanyInfo.type == '4'){
			returnYn = "Y";
		}
		
		var reqObj = {
			startBoxNum : pad($scope.data.startBoxNum, 4),
			endBoxNum : pad($scope.data.endBoxNum, 4),
			startCompanyInfo : $scope.data.startCompanyInfo,
			endCompanyInfo : $scope.data.endCompanyInfo,
			createDate : $scope.data.createDate.yyyymmdd(),
			stat : "1",
			type : $scope.data.boxType,
			boxInfo : []
		};
		
		var req = {
			type : $scope.data.boxType,
			boxNum : pad($scope.data.boxNum, 4),
			startCompanyInfo : $scope.data.startCompanyInfo,
			endCompanyInfo : $scope.data.endCompanyInfo,
			createDate : $scope.data.createDate.yyyymmdd(),
			barcode : $scope.data.boxType + $scope.data.createDate.yyyymmdd().substring(2) + pad($scope.data.boxNum, 4) + $scope.data.startCompanyInfo.code + $scope.data.endCompanyInfo.code,
			returnYn : returnYn,
			stat : "1"
		}
		
		reqObj.boxInfo.push(req);

		$http({
			method : 'POST',
			url : '/box/boxWorkGroup',
			data : req,
			headers : {
				'Content-Type' : 'application/json; charset=utf-8'
			}
		}).success(function(data, status, headers, config) {
			if (status == 200) {
				modalOpen($uibModal,  "박스 추가 완료되었습니다.", function() {
					$uibModalInstance.close("ok");
				});
			} else {
				modalOpen($uibModal,  "에러발생");
			}
		}).error(function(data, status, headers, config) {

			if (status == 409) {
				modalOpen($uibModal,  "중복된 박스 바코드 값이 있습니다.");
			};
			console.log(status);
		});
	};

	$scope.showBarcode = function(){
		
		if($scope.data.createDate == undefined || $scope.data.createDate == ''){
			modalOpen($uibModal,  "날짜를 입력하세요.");
			return;
		}
		
		if($scope.data.boxNum == undefined || $scope.data.boxNum == ''){
			modalOpen($uibModal,  "박스번호를 입력하세요.");
			return;
		}
		
		if($scope.data.startCompanyInfo == undefined || $scope.data.startCompanyInfo == ''){
			modalOpen($uibModal,  "출발지를 입력하세요.");
			return;
		}
		
		if($scope.data.endCompanyInfo == undefined || $scope.data.endCompanyInfo == ''){
			modalOpen($uibModal,  "도착지를 입력하세요.");
			return;
		}
		
		$scope.tempCreateDate = $scope.data.createDate.yyyymmdd().substring(2);
		$scope.barcode = $scope.data.boxType + $scope.data.createDate.yyyymmdd().substring(2) + pad($scope.data.boxNum, 4) + $scope.data.startCompanyInfo.code + $scope.data.endCompanyInfo.code;
	};
	
	$scope.bc = {
			format: 'CODE128',
		    lineColor: '#000000',
		    width: 2,
		    height: 100,
		    displayValue: false,
		    fontOptions: '',
		    font: 'monospace',
		    textAlign: 'center',
		    textPosition: 'bottom',
		    textMargin: 2,
		    fontSize: 20,
		    background: '#ffffff',
		    margin: 0,
		    marginTop: undefined,
		    marginBottom: undefined,
		    marginLeft: undefined,
		    marginRight: undefined,
		    valid: function (valid) {
		    }
	}
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);

app.controller('boxDetailPopController', ['$scope','$http','$location','$rootScope','$interval','$routeParams','verificationFactory','$filter', '$uibModal', '$uibModalInstance', 'obj', function($scope, $http, $location, $rootScope, $interval, $routeParams,verificationFactory, $filter, $uibModal, $uibModalInstance, obj) {

	$scope.ajaxFinish = false;
	
	$scope.type = obj.type;
	
	var seq = obj.boxSeq;
	
	$scope.format = 'yyyyMMdd';
	$scope.rfidTagList = [];
	
	$scope.dateOptions = {
		formatYear: 'yy',
		maxDate: new Date(2020, 5, 22),
		minDate: new Date(2018, 1, 1),
		startingDay: 1
	};
	
	$scope.dateOpen = function() {
	    $scope.data.dateOpened = true;
	};
	
	$scope.arrivalDateOpen = function() {
	    $scope.data.arrivalDateOpened = true;
	};

	$http.get('/box/' + seq).success(
	function(data) {
		$scope.data = angular.fromJson(data);
		$scope.data.companyList = [];
		
		$http.get('/company/getCompanyList').success(
			function(data) {
				
				$scope.tempCompanyList = angular.fromJson(data);
				
				angular.forEach($scope.tempCompanyList, function(value, key) {
					if($rootScope.userRole == 'production'){
						if($scope.tempCompanyList[key].type == '4' && ($scope.tempCompanyList[key].customerCode == "100000" || $scope.tempCompanyList[key].customerCode == "100016")){
							$scope.data.companyList.push($scope.tempCompanyList[key]);
						}
					} else if($rootScope.userRole == 'sales' || $rootScope.userRole == 'special'){
						if($scope.tempCompanyList[key].type == '4' || $scope.tempCompanyList[key].type == '5' || $scope.tempCompanyList[key].type == '6'){
							$scope.data.companyList.push($scope.tempCompanyList[key]);
						}
					} else {
						$scope.data.companyList.push($scope.tempCompanyList[key]);
					}
				});
				
				angular.forEach($scope.data.companyList, function(obj, key) {
					if(obj.code == $scope.data.startCompanyCd){
						$scope.data.startCompanyInfo = obj;
					}
					
					if(obj.code == $scope.data.endCompanyCd){
						$scope.data.endCompanyInfo = obj;
					}
				});
				
				angular.forEach($scope.boxTypeList.codeInfo, function(value, key) {
					if($scope.boxTypeList.codeInfo[key].codeValue == $scope.data.type){
						$scope.data.boxType = $scope.boxTypeList.codeInfo[key];
					}
				});
				
				$scope.data.createDate = initDate($scope.data.createDate);
				$scope.tempCreateDate = $scope.data.createDate.yyyymmdd().substring(2);
				
				$scope.barcode = $scope.data.boxType.codeValue + $scope.data.createDate.yyyymmdd().substring(2) + $scope.data.boxNum + $scope.data.startCompanyInfo.code + $scope.data.endCompanyInfo.code;
				
				if($scope.data.stat != "1"){
					
					$http.get('/box/boxMappingTagList/' + seq + "?type=" + $scope.type).success(
						function(data) {
							$scope.styleList = angular.fromJson(data);
					});
				}
				
				$scope.ajaxFinish = true;
			}
		);
	});
	
	$scope.mod = function() {
		
		if($scope.data.createDate == undefined || $scope.data.createDate == ''){
			modalOpen($uibModal,  "날짜를 입력하세요.");
			return;
		}
		
		if($scope.data.boxNum == undefined || $scope.data.boxNum == ''){
			modalOpen($uibModal,  "박스번호를 입력하세요.");
			return;
		}
		
		if($scope.data.startCompanyInfo == undefined || $scope.data.startCompanyInfo == ''){
			modalOpen($uibModal,  "출발지를 입력하세요.");
			return;
		}
		
		if($scope.type != '03'){
			if($scope.data.endCompanyInfo == undefined || $scope.data.endCompanyInfo == ''){
				modalOpen($uibModal,  "도착지를 입력하세요.");
				return;
			}
		} else {
			$scope.data.endCompanyInfo = $scope.data.startCompanyInfo;
		}
		
		var req = {
			boxSeq : $scope.data.boxSeq,
			type : $scope.data.boxType.codeValue,
			boxNum : $scope.data.boxNum,
			startCompanyInfo : $scope.data.startCompanyInfo,
			endCompanyInfo : $scope.data.endCompanyInfo,
			createDate : $scope.data.createDate.yyyymmdd(),
			barcode : $scope.data.boxType.codeValue + $scope.data.createDate.yyyymmdd().substring(2) + $scope.data.boxNum + $scope.data.startCompanyInfo.code + $scope.data.endCompanyInfo.code,
			returnYn : "N",
			stat : "1",
			regUserInfo : $scope.data.regUserInfo,
			regDate : $scope.data.regDate,
			boxWorkGroupSeq : $scope.data.boxWorkGroupSeq
		}

		$http({
			method : 'PUT',
			url : '/box',
			data : req,
			headers : {
				'Content-Type' : 'application/json; charset=utf-8'
			}
		}).success(function(data, status, headers, config) {
			if (status == 200) {
				modalOpen($uibModal,  "박스 수정 완료되었습니다.", function() {
					//$(location).attr("href", '#/box/boxList');
					history.back();
				});

			} else {
				modalOpen($uibModal,  "에러발생");
			}
		}).error(function(data, status, headers, config) {

			if (status == 409) {
				modalOpen($uibModal,  "중복된 박스 바코드 값이 있습니다.");
			};
			console.log(status);
		});

	};
	
	/**
	 * PDF 다운로드 로직 추가
	 */
	$scope.pdfDownload = function(){
		var html2obj = html2canvas($("#barcodeImg .barcode"));
        var queue  = html2obj.parse();
        var canvas = html2obj.render(queue);
        var img = canvas.toDataURL();
        
        var startVal;
        if($scope.data.startCompanyInfo.name.length > 15){
        	startVal = [ { text: '출발지', alignment: 'center' }, { text:  $scope.data.startCompanyInfo.name, alignment: 'center', fontSize : 11 }];
        } else {
        	startVal = [ { text: '출발지', alignment: 'center' }, { text:  $scope.data.startCompanyInfo.name, alignment: 'center' }];
        }
		
        function tableClmn(){
        	var objClmns = {
					alignment: 'justify',
					style: 'tableExample',
					table: {
						headerRows: 1,
						/*widths: [100, 150],
						heights: [20, 20, 20, 20, 20, 80, 25],*/
						widths: [80, 150],
	            		heights: [17, 17, 17, 17, 17, 80, 10],
						body: [
							[ { text: '내용', alignment: 'center' }, { text: $scope.data.boxType.name, alignment: 'center' }],
							[ { text: '날짜', alignment: 'center' },  { text: $scope.tempCreateDate, alignment: 'center' }],
							[ { text: '박스번호', alignment: 'center' }, { text: $scope.data.boxNum, alignment: 'center' }],
							startVal,
							[ { text: '도착지', alignment: 'center' }, { text: $scope.data.endCompanyInfo.name, alignment: 'center' }],
							[ { image: canvas.toDataURL() , width: 180, height: 90, colSpan: 2, alignment: 'center', border: [true, true , true, false], margin: [0,0,0,-10]  }],
							[ { text: $scope.barcode, colSpan: 2, alignment: 'center', border: [true, false , true, true] }]
						]
					}
				};
        	return objClmns;
        }
        
		var docDefinition = {
				content: [
					/*{
						columns: [tableClmn(), tableClmn()]
					},
					{
						columns: [tableClmn(), tableClmn()]
					}
					,{
						columns: [tableClmn(), tableClmn()]
					}*/
					{
						columns: [tableClmn()]
					}
			    ],
			    /*styles: {
			        tableExample: {
			        	fontSize: 14,
			        	margin: [-25, -10, 50, 38]
			        }
			    }*/
			    styles: {
					tableExample: {
						fontSize: 14,
						margin: [-15, 1, 60, 53]
	                }
	            }
		};
		var d = new Date();
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();
	    if (month.length < 2) month = '0' + month;
	    if (day.length < 2) day = '0' + day;
        pdfMake.createPdf(docDefinition).download("Barcode_"+year+month+day+".pdf");
	};

	$scope.del = function() {
		
		confirmOpen($uibModal, "박스 삭제 하시겠습니까?", function(b){
			if(b){
				$http({
					method : 'DELETE',
					url : '/box',
					data : $scope.data,
					headers : {
						'Content-Type' : 'application/json; charset=utf-8'
					}
				}).success(function(data, status, headers, config) {
					if (status == 200) {
						modalOpen($uibModal,  "박스 삭제 완료되었습니다.", function() {
							//$(location).attr("href", '#/box/boxList');
							history.back();
						});
					} else {
						modalOpen($uibModal,  "에러발생");
					}
				}).error(function(data, status, headers, config) {
					console.log(status);
				});
			}
		});
	};
	
	$scope.$watch(function(){
		
		return $rootScope.parentCodeList;
		
		}, function() {
			
			if($rootScope.parentCodeList != undefined){
				$scope.boxTypeList = $filter('filter')($rootScope.parentCodeList, {codeValue : '10001'})[0];
				
			} 
	}, true);
	
	$scope.showBarcode = function(){
		
		if($scope.data.createDate == undefined || $scope.data.createDate == ''){
			modalOpen($uibModal,  "날짜를 입력하세요.");
			return;
		}
		
		if($scope.data.boxNum == undefined || $scope.data.boxNum == ''){
			modalOpen($uibModal,  "박스번호를 입력하세요.");
			return;
		}
		
		if($scope.data.startCompanyInfo == undefined || $scope.data.startCompanyInfo == ''){
			modalOpen($uibModal,  "출발지를 입력하세요.");
			return;
		}
		
		if($scope.data.endCompanyInfo == undefined || $scope.data.endCompanyInfo == ''){
			modalOpen($uibModal,  "도착지를 입력하세요.");
			return;
		}
		
		$scope.tempCreateDate = $scope.data.createDate.yyyymmdd().substring(2);
		$scope.barcode = $scope.data.boxType.codeValue + $scope.data.createDate.yyyymmdd().substring(2) + $scope.data.boxNum + $scope.data.startCompanyInfo.code + $scope.data.endCompanyInfo.code;
	};
	
	$scope.bc = {
			format: 'CODE128',
		    lineColor: '#000000',
		    width: 2,
		    height: 100,
		    displayValue: false,
		    fontOptions: '',
		    font: 'monospace',
		    textAlign: 'center',
		    textPosition: 'bottom',
		    textMargin: 2,
		    fontSize: 20,
		    background: '#ffffff',
		    margin: 0,
		    marginTop: undefined,
		    marginBottom: undefined,
		    marginLeft: undefined,
		    marginRight: undefined,
		    valid: function (valid) {
		    }
	};
	
	$scope.openRfidTagDetail = function (obj) {
		if(obj.rfidTag != undefined){
			 var modalInstance = $uibModal.open({
			    	animation: true,
			    	ariaLabelledBy: 'modal-title',
			    	ariaDescribedBy: 'modal-body',
			    	templateUrl: '/customTemplate/customRfidTag',
			    	controller: 'rfidTagPopupController',
			    	size: 'xlg',
			    	resolve: {
			    		rfidTag: function () {
			    			return obj.rfidTag;
			    		},
			    		barcode: function (){
			    			return obj.erpKey + obj.rfidSeq;
			    		}
			    	}
			    });
			    modalInstance.result.then(function (selectedItem) {
//			        $ctrl.selected = selectedItem;
			    }, function () {
//			        $log.info('Modal dismissed at: ' + new Date());
			    });
		}
	};
	
	$scope.detail = function(style){
		$scope.currentStyle = style;
	};
	
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
}]);