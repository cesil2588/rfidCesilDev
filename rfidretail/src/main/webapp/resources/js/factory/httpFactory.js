app.factory('httpService', ['$http', '$q', function($http, $q){
	return  {
		
		post : function(url, req){
			return 
			$http.post(url, req)
			.then(
					function(data){
						return data;
					}
			);
		},
		
		put : function(url, req){
			return 
			$http.put(url, req)
			.then(
					function(data){
						return data;
					}
			);
		},
		
		delete : function(url, req){
			return 
			$http.delete(url, req)
			.then(
					function(data){
						return data;
					}
			);
		}
	}
}]);