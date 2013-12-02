

exports.hasAllKeys = function(object, keys){

	for(var i=0; i<keys.length; i++){
		if(!object.hasOwnProperty(keys[i])){
			return false;
		}
	}

	return true;
}
