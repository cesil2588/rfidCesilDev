function hex_to_ascii(str1){
	if(str1 == undefined){
		return;
	}
	var hex  = str1.toString();
	var str = '';
	for (var n = 0; n < hex.length; n += 2) {
		str += String.fromCharCode(parseInt(hex.substr(n, 2), 16));
	}
	return str;
}

function readerStop($http){

	$http({
		method: 'POST',
		url: '/monitering/readerStop',
		headers: {'Content-Type': 'application/json; charset=utf-8'}
	})
	.success(function(data, status, headers, config) {

		if(angular.fromJson(data).message == "success") {
			console.log("reader stop");
		} else {
			console.log("reader not stop");
		}
	})
	.error(function(data, status, headers, config) {
		console.log(status);
	});

}

function readerStart($http){

	$http({
		method: 'POST',
		url: '/monitering/readerStart',
		headers: {'Content-Type': 'application/json; charset=utf-8'}
	})
	.success(function(data, status, headers, config) {

		if(angular.fromJson(data).message == "success") {
			console.log("reader start");
		} else {
			console.log("reader not start");
		}
	})
	.error(function(data, status, headers, config) {
		console.log(status);
	});
}

function userLoginCheck($http, $rootScope, $location, $window, credentials, callback){

	var headers = credentials ? {authorization : "Basic "
		+ btoa(credentials.userId + ":" + credentials.password)
	} : {};

	$http.get('/member/userAuth', {headers : headers}).success(function(data) {
		if (data.name) {
			$rootScope.authenticated = true;
		    $rootScope.user = data;
		    $rootScope.userRole = data.authorities[0].authority;

		    var currentRole = angular.fromJson($window.sessionStorage.getItem("role"));

		    if(currentRole != undefined && $rootScope.userRole != currentRole){
		    	roleCheck($rootScope, $window);
		    } else if(currentRole == undefined || $rootScope.parentMenuList == undefined){
		    	roleCheck($rootScope, $window);
		    }

		    /*
			angular.forEach($rootScope.parentMenuList, function(key, value){
				if((key.parentMenu.url.indexOf($location.path()) != -1) && key.parentMenu.url != ''){
					$rootScope.authenticated = true;
				}

				angular.forEach(key.parentMenu.childMenu,function(key,value) {
					if((key.url.indexOf($location.path()) != -1)){
						$rootScope.authenticated = true;
					}
				})
			});

			if($rootScope.authenticated == false) {
				$location.url("/");
			}
			*/

		    callback && callback();
		} else {
		    $rootScope.authenticated = false;
		    $location.url("/");
		}
		}).error(function() {
		    $rootScope.authenticated = false;
		});

}

function roleCheck($rootScope, $window) {
	$rootScope.parentMenuList = $rootScope.user.principal.companyInfo.roleInfo.menuMapping;
	$window.sessionStorage.setItem("role", angular.toJson($rootScope.userRole));

//}
}

/*
 * 특수문자 체크 로직 업데이트
 * 2018-05-15 최의선
 */
function rtn_engnum_mix_chk(str)	{
	var pattern1 = /[0-9]/;	// 숫자
	var pattern2 = /[a-zA-Z]/;	// 문자
	var pattern3 = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+┼<>@\#$%&\'\"\\\(\=]/gi;	// 특수문자
	if(!pattern1.test(str) || !pattern2.test(str) || !pattern3.test(str) || str.length < 8) {
		//alert("비밀번호는 8자리 이상 문자, 숫자, 특수문자로 구성하여야 합니다.");
		return false;
	} else {
		return true;
	}

}

function rtn_engnum_chk(str){
	for( var i = 0; i <= str.length -1 ; i++ ){
  		if('a' <= str.charAt(i) && str.charAt(i) <= 'z' || str.charAt(i) >= '0' && str.charAt(i) <= '9'){

  		} else {
  			return false;
  		}
 	}
	return true;
}

function specialCharRemove(obj) {
	var val = obj.value;
	var pattern = /[^(가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9)]/gi;   // 특수문자 제거

	//var pattern = /[^(0-9)]/gi;   // 숫자이외는 제거

	if(pattern.test(val)){
	obj.value = val.replace(pattern,"");
	}
}

function jobLogReturnUrl(jobLog){
	if(jobLog.category == "member" && (jobLog.command == "insert" || jobLog.command == "update")){
		return "/member/userDetail?seq=" + jobLog.targetSeq;
	}

	if(jobLog.category == "physicalReader" && (jobLog.command == "insert" || jobLog.command == "update")){
		return "/reader/physicalReaderDetail?seq=" + jobLog.targetSeq;
	}

	if(jobLog.category == "externalSend" && (jobLog.command == "insert" || jobLog.command == "update")){
		return "/send/externalSendDetail?seq=" + jobLog.targetSeq;
	}
}

function showObj(obj) {
	var str = "";
	for(key in obj) {
		str += key+"="+obj[key]+"\n";
	}

	console.log(str);
	return;
}

function isEmpty(obj) {
    for(var prop in obj) {
        if(obj.hasOwnProperty(prop))
            return false;
    }
    return true;
}

String.prototype.ltrim = function() { return this.replace(/^[ ]*/g, ''); };
String.prototype.rtrim = function() { return this.replace(/[ ]*$/g, ''); };
String.prototype.trim = function() { return this.replace(/^\s+|\s+$/g, '');};
Date.prototype.yyyymmdd = function() {
	var mm = this.getMonth() + 1; // getMonth() is zero-based
	var dd = this.getDate();

	return [this.getFullYear(),
		(mm>9 ? '' : '0') + mm,
	    (dd>9 ? '' : '0') + dd
	].join('');
};

Date.prototype.yyyy_mm_dd = function() {
	var mm = this.getMonth() + 1; // getMonth() is zero-based
	var dd = this.getDate();

	return [this.getFullYear(),
		(mm>9 ? '' : '0') + mm,
		(dd>9 ? '' : '0') + dd
	].join('-');
};

/**
 * String to Date
 * @param str
 * @returns
 */
function parse(str){
	var y = str.substr(0,4),
	m = str.substr(4,2) - 1,
	d = str.substr(6,2);
	var D = new Date(y,m,d);
	return (D.getFullYear() == y && D.getMonth() == m && D.getDate() == d) ? D : 'invalid date';
}

/**
 * parameter 자동 생성
 * @param obj
 * @returns
 */
function generateParam(obj){

	var addPath = "";

	var current = obj;

	if(current != undefined){

		if(current.page != undefined && current.page != ""){
			addPath += "page=" + current.page;
		}

		if(current.text != undefined && current.text != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "search=" + current.text;
		}

		if(current.option != undefined && current.option != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "option=" + current.option;
		}

		if((current.sortName != undefined && current.sortName != "") && (current.sortOrder != undefined && current.sortOrder != "")){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "sort=" + current.sortName + "," + current.sortOrder;
		}

		if(current.startDate != undefined && current.startDate != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "startDate=" + current.startDate;
		}

		if(current.endDate != undefined && current.endDate != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "endDate=" + current.endDate;
		}

		if(current.startTime != undefined && current.startTime != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "startTime=" + current.startTime;
		}

		if(current.endTime != undefined && current.endTime != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "endTime=" + current.endTime;
		}

		if(current.customerCd != undefined && current.customerCd != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "customerCd=" + current.customerCd;
		}

		if(current.completeYn != undefined && current.completeYn != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "completeYn=" + current.completeYn;
		}

		if(current.productYy != undefined && current.productYy != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "productYy=" + current.productYy;
		}

		if(current.productSeason != undefined && current.productSeason != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "productSeason=" + current.productSeason;
		}

		if(current.style != undefined && current.style != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "style=" + current.style;
		}

		if(current.styleSize != undefined && current.styleSize != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "styleSize=" + current.styleSize;
		}

		if(current.color != undefined && current.color != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "color=" + current.color;
		}

		if(current.role != undefined && current.role != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "role=" + current.role;
		}

		if(current.checkYn != undefined && current.checkYn != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "checkYn=" + current.checkYn;
		}

		if(current.companySeq != undefined && current.companySeq != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "companySeq=" + current.companySeq;
		}

		if(current.type != undefined && current.type != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "type=" + current.type;
		}

		if(current.startCompanySeq != undefined && current.startCompanySeq != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "startCompanySeq=" + current.startCompanySeq;
		}

		if(current.endCompanySeq != undefined && current.endCompanySeq != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "endCompanySeq=" + current.endCompanySeq;
		}

		if(current.stat != undefined && current.stat != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "stat=" + current.stat;
		}

		if(current.subCompanyName != undefined && current.subCompanyName != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "subCompanyName=" + current.subCompanyName;
		}

		if(current.defaultDateType != undefined && current.defaultDateType != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "defaultDateType=" + current.defaultDateType;
		}

		if(current.seq != undefined && current.seq != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "seq=" + current.seq;
		}

		if(current.reissueYn != undefined && current.reissueYn != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "reissueYn=" + current.reissueYn;
		}

		if(current.representYn != undefined && current.representYn != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "representYn=" + current.representYn;
		}

		if(current.confirmYn != undefined && current.confirmYn != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "confirmYn=" + current.confirmYn;
		}

		if(current.createDate != undefined && current.createDate != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "createDate=" + current.createDate;
		}

		if(current.workLine != undefined && current.workLine != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "workLine=" + current.workLine;
		}

		if(current.arrivalDate != undefined && current.arrivalDate != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "arrivalDate=" + current.arrivalDate;
		}

		if(current.orderDegree != undefined && current.orderDegree != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "orderDegree=" + current.orderDegree;
		}

		if(current.startRfidSeq != undefined && current.startRfidSeq != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "startRfidSeq=" + current.startRfidSeq;
		}

		if(current.endRfidSeq != undefined && current.endRfidSeq != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "endRfidSeq=" + current.endRfidSeq;
		}

		if(current.productionStorageSeq != undefined && current.productionStorageSeq != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "productionStorageSeq=" + current.productionStorageSeq;
		}

		if(current.completeDate != undefined && current.completeDate != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "completeDate=" + current.completeDate;
		}

		if(current.completeSeq != undefined && current.completeSeq != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "completeSeq=" + current.completeSeq;
		}

		if(current.completeType != undefined && current.completeType != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "completeType=" + current.completeType;
		}

		if(current.size != undefined && current.size != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "size=" + current.size;
		}

		if(current.regDate != undefined && current.regDate != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "regDate=" + current.regDate;
		}

		if(current.referenceNo != undefined && current.referenceNo != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "referenceNo=" + current.referenceNo;
		}

		if(current.pickQty != undefined && current.pickQty != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "pickQty=" + current.pickQty;
		}

		if(current.orderQty != undefined && current.orderQty != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "orderQty=" + current.orderQty;
		}

		if(current.command != undefined && current.command != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "command=" + current.command;
		}

		if(current.status != undefined && current.status != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "status=" + current.status;
		}

		if(current.requestUrl != undefined && current.requestUrl != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "requestUrl=" + current.requestUrl;
		}

		if(current.searchDate != undefined && current.searchDate != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "searchDate=" + current.searchDate;
		}

		if(current.date != undefined && current.date != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "date=" + current.date;
		}

		if(current.searchType != undefined && current.searchType != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "searchType=" + current.searchType;
		}

		if(current.useYn != undefined && current.useYn != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "useYn=" + current.useYn;
		}

		if(current.rfidTag != undefined && current.rfidTag != ""){
			if(addPath.length > 0){
				addPath = addPath + "&";
			}
			addPath += "rfidTag=" + current.rfidTag;
		}

		if(addPath.length > 0){
			addPath = "?" + addPath;
		}
	}

	return addPath;
}

/**
 * 뒤로가기 시 날짜 검색 셋팅
 * @param obj
 * @returns
 */
function initDate(obj){
	if(obj == undefined || obj == ""){
		return "";
	}

	return parse(obj);
}

/**
 * 뒤로가기 시 검색 초기데이터 셋팅
 * @param option
 * @param sortName
 * @param param
 * @returns
 */
function initSearch(option, sortName, sortOrder, param){

	var obj;
	if(param){
		if(param.sortName != sortName){
			obj = {
				option : option,
				sortName : sortName,
				sortOrder : sortOrder,
				page : 0
			};
		} else {
			obj = param;
		}
	} else {
		obj = {
				option : option,
				sortName : sortName,
				sortOrder : sortOrder,
				page : 0
			};
	}

	return obj;

}

/**
 * 뒤로가기 시 검색 초기데이터 셋팅(커스텀)
 * @param option
 * @param sortName
 * @param sortOrder
 * @param param
 * @returns
 */
function initSearchCustom(param){

	var obj;
	if(param){
		obj = param;
	} else {
		obj = {
				page : 0
		};
	}

	return obj;

}

/**
 * 상세페이지 이동 시 검색 데이터 셋팅
 * @param search
 * @returns
 */
function setSearch(search){
	var obj = {
		page : search.page,
		text : search.text,
		option : search.option,
		sortName : search.sortName,
		sortOrder : search.sortOrder,
		startDate : search.startDate,
		endDate : search.endDate,
		startTime : search.startTime,
		endTime : search.endTime,
		customerCd : search.customerCd,
		completeYn : search.completeYn,
		searchYn : search.searchYn,
		productYy : search.productYy,
		productSeason : search.productSeason,
		style : search.style,
		styleSize : search.styleSize,
		color : search.color,
		role : search.role,
		checkYn : search.checkYn,
		companySeq : search.companySeq,
		type : search.type,
		startCompanySeq : search.startCompanySeq,
		endCompanySeq : search.endCompanySeq,
		stat : search.stat,
		subCompanyName : search.subCompanyName,
		defaultDateType : search.defaultDateType,
		defaultDate : search.defaultDate,
		seq : search.seq,
		reissueYn : search.reissueYn,
		representYn : search.representYn,
		confirmYn : search.confirmYn,
		createDate : search.createDate,
		workLine : search.workLine,
		arrivalDate : search.arrivalDate,
		orderDegree : search.orderDegree,
		startRfidSeq : search.startRfidSeq,
		endRfidSeq : search.endRfidSeq,
		productionStorageSeq : search.productionStorageSeq,
		size : search.size,
		regDate : search.regDate,
		searchDate : search.searchDate,
		date : search.date
	}

	return obj;
}

/**
 * 공통 AJAX 리스트 통신
 * @param flag
 * @param search
 * @returns
 */
function httpGetList(url, param, scope, http, init, chartFlag){

	http.get(url + param).success(
		function(data) {
			scope.list = angular.fromJson(data).content;
			scope.current = angular.fromJson(data).number + 1;
			scope.begin = Math.max(1, scope.current - 5);
			scope.end = Math.min(scope.begin + 9, angular.fromJson(data).totalPages);

			scope.total = angular.fromJson(data).totalElements;

			if(chartFlag){
				angular.forEach(scope.list, function(value, key) {
					scope.list[key].type = chartPercent(scope.list[key]);
				});
			}

			if(init){
				scope.ajaxFinish = true;
			}
		}
	);
}

/**
 * 공통 AJAX POST, PUT, DELETE 통신
 * @param method, url, req
 * @returns returnObj
 */
function httpPost(method, url, req, $http, callback){

	var returnObj;

	$http({
		method : method,
		url : url,
		data : req,
		headers : {
			'Content-Type' : 'application/json; charset=utf-8'
		}
	}).success(callback).error(function(){
	    modalCall("에러 발생");
	});
};

/**
 * Modal confirm 콜백 공통
 * @param message
 * @param callback
 * @returns
 */
function confirmCall(massage, callback){

	$('#confirmModal').modal({
		show:true,
		backdrop: true,
	    keyboard: false,
	});

	$('#confirmModal .modal-body').html("<p>" + massage + "</p>");

	$("#confirmModal").find("#okBtn").off().on("click", function(){
		$('#confirmModal').modal('hide');
		if (callback) callback(true);
	});
	$("#confirmModal").find("#cancelBtn").off().on("click", function(event){
		$('#confirmModal').modal('hide');
		if (callback) callback(false);
	});
	$("#confirmModal").find("#closeBtn").off().on("click", function(event){
		$('#confirmModal').modal('hide');
		if (callback) callback(false);
	});
}

/**
 * Common Moal 공통 모달
 * @param massage
 * @returns
 */
function modalCall(massage, callback){
	$('#commonModal').modal({
		show:true,
		backdrop: true,
		keyboard: false,
	});
	$("#commonModal").find("#modalId").html("<p>" + massage + "</p>");
	$("#commonModal").find("#okBtn").off().on("click", function(event){
		$('#commonModal').modal('hide');
		if (callback) callback(true);
	});
}

/**
 * 자릿수 채우기
 * @param n
 * @param width
 * @returns
 */
function pad(n, width) {
	n = n + '';
	return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
}

/**
 * 시간계산
 * @param dt
 * @returns
 */
function getRelativeDateTimeString(dt){
	dt = new Date(dt);

    if(!dt) return "알수 없는 시간 전";

    var delta = parseInt(((new Date().getTime()) - dt.getTime()) / 1000);
    if (delta < 0) return "지금";
    if (delta < 1 * 60) return delta == 1 ? "1초 전" : delta + "초 전";
    if (delta < 2 * 60) return "1분 전";
    if (delta < 45 * 60) return Math.floor(delta/60) + "분 전";
    if (delta < 90 * 60) return "1시간 전";
    if (delta < 24 * (60*60)) return Math.floor(delta/60/60) + "시간 전";
    if (delta < 48 * (60*60)) return "어제";
    if (delta < 30 * (24 * (60*60))) return Math.floor(delta/60/60/24) + "일 전";
    if (delta < 12 * (30 * (24 * (60*60))))
    {
        var months = Math.floor(delta/60/60/24/30);
        return (months <= 1) ? "1달 전" : (months + "달 전");
    }
    else
    {
        var years = Math.floor(delta/60/60/24/365);
        return (years <= 1) ? "1년 전" : (years + "년 전");
    }
}

/**
 * 화폐 단위(3자리 콤마)
 * @param x
 * @returns
 */
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

/**
 * yyyy-MM-dd 변경
 * @param date
 * @returns
 */
function formatDate(date) {
	var d = new Date(date),
	month = '' + (d.getMonth() + 1),
	day = '' + d.getDate(),
	year = d.getFullYear();

	if (month.length < 2) month = '0' + month;
	if (day.length < 2) day = '0' + day;

	return [year, month, day].join('-');
}

/**
 * 현재 날짜 yyyy-MM-dd로 변경
 * @param date
 * @returns
 */
function formatNowDate() {
	var d = new Date(),
	month = '' + (d.getMonth() + 1),
	day = '' + d.getDate(),
	year = d.getFullYear();

	if (month.length < 2) month = '0' + month;
	if (day.length < 2) day = '0' + day;

	return [year, month, day].join('-');
}

/**
 * 차트용 퍼센트 색상
 * @param obj
 * @returns
 */
function chartPercent(obj) {

	var value = obj.batchPercent * 1;
    var type;

    if (value < 25) {
      type = 'danger';
    } else if (value < 50) {
      type = 'warning';
    } else if (value < 75) {
      type = 'info';
    } else {
      type = 'success';
    }

    return type;
};

function parseRfidTag(rfidTag){
	var erpKey = rfidTag.substring(0, 10);
	var productRfidSeason = rfidTag.substring(10, 13);
	var orderDegree = rfidTag.substring(13, 15);
	var customerCd = rfidTag.substring(15, 18);
	var publishLocation = rfidTag.substring(18, 19);
	var publishRegDate = rfidTag.substring(19, 25);
	var publishDegree = rfidTag.substring(25, 27);
	var rfidSeq = rfidTag.substring(27, 32);

	var obj = {
			"erpKey" : erpKey,
			"productRfidSeason" : productRfidSeason,
			"orderDegree" : orderDegree,
			"customerCd" : customerCd,
			"publishLocation" : publishLocation,
			"publishRegDate" : publishRegDate,
			"publishDegree" : publishDegree,
			"rfidSeq" : rfidSeq
		}

	return obj;
};

// 배열 중복 제거
function duplicateCheck(arr) {
    var tempArr = [];
    for (var i = 0; i < arr.length; i++) {
        if (tempArr.length == 0) {
            tempArr.push(arr[i]);
        } else {
            var duplicatesFlag = true;
            for (var j = 0; j < tempArr.length; j++) {
                if (tempArr[j] == arr[i]) {
                    duplicatesFlag = false;
                    break;
                }
            }
            if (duplicatesFlag) {
                tempArr.push(arr[i]);
            }
        }
    }
    return tempArr;
}

// 랜덤값 생성
function randomString(length) {

	var chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZ";
	var string_length = length;
	var randomstring = '';

	for (var i=0; i<string_length; i++) {
		var rnum = Math.floor(Math.random() * chars.length);
		randomstring += chars.substring(rnum,rnum+1);
	}
	//document.randform.randomfield.value = randomstring;
	return randomstring;
}

// 랜덤값 생성(숫자만)
function randomIntegerString(length) {

	var chars = "0123456789";
	var string_length = length;
	var randomstring = '';

	for (var i=0; i<string_length; i++) {
		var rnum = Math.floor(Math.random() * chars.length);
		randomstring += chars.substring(rnum,rnum+1);
	}
	//document.randform.randomfield.value = randomstring;
	return randomstring;
}

// 상세 팝업 페이지 오픈
function detailPopOpen($uibModal, obj, templateUrl, controller, size){

	var modalInstance = $uibModal.open({
		animation: true,
		ariaLabelledBy: 'modal-title',
		ariaDescribedBy: 'modal-body',
		templateUrl: templateUrl,
		controller: controller,
		size: size,
		resolve: {
			obj: function () {
				return obj;
			}
		}
	});
	modalInstance.result.then(function (selectedItem) {

	}, function () {

	});
}

function detailPopOpenBack($uibModal,obj,templateUrl,controller,size,back,param,$scope,$http) {
	var modalInstance = $uibModal.open({
		ariaLabelledBy: 'modal-title',
		ariaDescribedBy: 'modal-body',
		templateUrl : templateUrl,
		controller :controller,
		size : size,
		resolve: {
			obj: function () {
				return obj;
			}
		}
	})

	modalInstance.result.then(function(flag) {
		if(flag != null || flag != undefined) {
			httpGetList("/role",param, $scope, $http);
		}else {
			$scope.originalObj = null;
			return $scope.originalObj;
		}
	})
}

function modalOpen($uibModal, message, callback){

	var modalInstance = $uibModal.open({
		animation: true,
		ariaLabelledBy: 'modal-title',
		ariaDescribedBy: 'modal-body',
		templateUrl: '/customTemplate/customModal',
		controller: function($scope, $uibModalInstance) {
	        $scope.message = message;

	        $scope.ok = function () {
	        	$uibModalInstance.close(true);
	    	};

	    	$scope.cancel = function () {
	    		$uibModalInstance.close(false);
	    	};
	    }
	});

	modalInstance.result.then(function (b) {
		if(callback) callback(b);
	});
}

function confirmOpen($uibModal, message, callback){

	var modalInstance = $uibModal.open({
		animation: true,
		ariaLabelledBy: 'modal-title',
		ariaDescribedBy: 'modal-body',
		templateUrl: '/customTemplate/customConfirm',
		controller: function($scope, $uibModalInstance) {
	        $scope.message = message;

	        $scope.ok = function () {
	        	$uibModalInstance.close(true);
	    	};

	    	$scope.cancel = function () {
	    		$uibModalInstance.close(false);
	    	};
	    }
	});

	modalInstance.result.then(function (b) {
		if(callback) callback(b);
	});
};

