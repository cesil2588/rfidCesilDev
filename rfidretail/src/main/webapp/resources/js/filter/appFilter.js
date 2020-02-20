app.filter('makeRange', function() {
	
    return function(input) {
        var lowBound, highBound;
        switch (input.length) {
        case 1:
            lowBound = 0;
            highBound = parseInt(input[0]) - 1;
            break;
        case 2:
            lowBound = parseInt(input[0]);
            highBound = parseInt(input[1]);
            break;
        default:
            return input;
        }
        var result = [];
        for (var i = lowBound; i <= highBound; i++)
            result.push(i);
        return result;
    };
});

app.filter('stringToHex', function () {
	  
	return function(input) {
		return Number(input).toString(16);
	};
});

app.filter('hexToString', function () {
		  
	return function(input) {
		
		var hex  = input.toString();  
		var str = '';  
		for (var n = 0; n < hex.length; n += 2) {  
			str += String.fromCharCode(parseInt(hex.substr(n, 2), 16));  
		}  
		
		return str;
	};
});

app.filter('percentage', ['$filter', function ($filter) {
	 return function (input, decimals) {
		 
		var calc = $filter('number')(input * 100, decimals);
		
		if(calc == ""){
			calc = "0.00";
		}
	    return calc + '%';
	 };
}]);

app.filter('myLimitTo', [function(){
    return function(obj, limit){
        var keys = Object.keys(obj);
        if(keys.length < 1){
            return [];
        }

        var ret = new Object,
        count = 0;
        angular.forEach(keys, function(key, arrayIndex){
           if(count >= limit){
                return false;
            }
            ret[key] = obj[key];
            count++;
        });
        return ret;
    };
}]);
