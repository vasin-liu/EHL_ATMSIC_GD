

function baseCheck(params,isCheckObj) {
	if (params == undefined || params == null || !(params instanceof Array)) {
		return false;
	}
	for(var i = 0; i < params.length; i++) {
		if(params[i]==undefined || params[i]==null){
			return false;
		}
		if (isCheckObj){
			if($(params[i])==undefined || $(params[i])==null){
				return false;
			}
		}
	}
	return true;
}

