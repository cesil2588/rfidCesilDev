/**
 * 회원가입 유효성 체크
 */
app.factory("verificationFactory", ["$http","$q", function ($http, $q) {
	
	var returnObj = {
		result : ""
	};
	
    function ajaxUserIdVerification(userId){
    	
    	var deferred = $q.defer();
    	
    	if(userId == "" || userId == undefined){
    		returnObj.result = false;
    		deferred.resolve(returnObj);
    		return deferred.promise;
		} 
			
		$http.get('/member/userIdVerification/' + userId).then(
			function(response) {
						
				var returnCount = angular.fromJson(response.data);
						
				if(returnCount == "0"){
					returnObj.result = true;
				} else {
					returnObj.result = false;
				}
				deferred.resolve(returnObj);
			}
		);
    	
    	return deferred.promise;
    }

    function ajaxPasswordVerification(password) {
    	
        if(password == "" || password == undefined || password.length == 0){
			return false;
		}
		
		//if(rtn_engnum_mix_chk(password) == "num" || rtn_engnum_mix_chk(password) == "eng" || !rtn_engnum_chk(password)){
        if(!rtn_engnum_mix_chk(password)){
			return false;
		}
		return true;
    }

    function ajaxPasswordCheckVerification(password, passwordCheck, passwordValid) {
    	
    	if(passwordCheck == "" || passwordCheck == undefined || passwordCheck.length == 0){
			return false;
		}
		
		if(password != passwordCheck){
			return false;
		}
		
		if(passwordValid){
			return true;
		} else {
			return false;
		}
    }
    
    function ajaxEmailCheckVerification(email){
    	
    	var deferred = $q.defer();
    	
    	if(email == undefined || email == "" || email.length == 0 || email.indexOf(".") == -1){
    		returnObj.result = false;
    		deferred.resolve(returnObj);
    		return deferred.promise;
    	}
    	
		$http.get('/member/emailVerification/' + email).then(
			function(response) {
						
				var returnCount = angular.fromJson(response.data);
						
				if(returnCount == "0"){
					returnObj.result = true;
				} else {
					returnObj.result = false;
				}
				deferred.resolve(returnObj);
			}
		);
    	
    	return deferred.promise;
	}
    
    function ajaxUserNotEmailCheckVerification(userSeq, email){
    	
    	var deferred = $q.defer();
    	
    	if(email == undefined || email == "" || email.length == 0 || email.indexOf(".") == -1){
    		returnObj.result = false;
    		deferred.resolve(returnObj);
    		return deferred.promise;
    	}
    	
		$http.get('/member/emailVerification/' + userSeq + '/'+ email).then(
			function(response) {
						
				var returnCount = angular.fromJson(response.data);
						
				if(returnCount == "0"){
					returnObj.result = true;
				} else {
					returnObj.result = false;
				}
				deferred.resolve(returnObj);
			}
		);
    	
    	return deferred.promise;
	}
    
    return {
    	ajaxUserIdVerification				: ajaxUserIdVerification,
    	ajaxPasswordVerification			: ajaxPasswordVerification,
    	ajaxPasswordCheckVerification		: ajaxPasswordCheckVerification,
    	ajaxEmailCheckVerification			: ajaxEmailCheckVerification,
    	ajaxUserNotEmailCheckVerification 	: ajaxUserNotEmailCheckVerification
    };
}]);