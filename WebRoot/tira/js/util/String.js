
String.prototype.lTrim = function(){
	str = this;
 	var i;
    for(i=0;i<str.length;i++)
    {
        if(str.charAt(i)!=" "&&str.charAt(i)!=" ")break;
    }
    str=str.substring(i,str.length);
    return str;
}

String.prototype.rTrim = function(){
	str = this;
	var i;
    for(i=str.length-1;i>=0;i--)
    {
        if(str.charAt(i)!=" "&&str.charAt(i)!=" ")break;
    }
    str=str.substring(0,i+1);
    return str;
}

String.prototype.trim = function(){ 
	return this.rTrim(this.lTrim());
}
