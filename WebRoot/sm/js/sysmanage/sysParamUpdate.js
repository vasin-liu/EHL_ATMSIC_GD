//展示数据到编辑页面function   doQuery(id){

    var url="common.sysparamdata.getDataById.d?paramId="+id;
    url = encodeURI(url);
	var params = "";
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showParamResponse});
   
    }   
    
    function showParamResponse(xmlHttp){
       var xmldoc = xmlHttp.responseXML;
	var paramDesc = document.getElementById("paramDesc");
	var paramValue = document.getElementById("paramValue");
	var results   = xmldoc.getElementsByTagName("col");
	paramDesc.value = results[3].text;
	paramValue.value = results[2].text;
	
}

//验证数据是否只含有数字,字母,汉字,下划线，(暂时未用,保留)有则返回false by linbh -->2008-5-16
function checkNormal(inputVal) {
	var pattern = /^(?:[\u4e00-\u9fa5-[\/\\,:.-\]]*\w*\s*)+$/;
	if (pattern.exec(inputVal)) {
		return true;
	}
	return false;
}
//修改参数
   function modify(){
            var desc=document.getElementById("paramDesc").value;
			var value=document.getElementById("paramValue").value;
			
　			if(desc=="")
			{
    			alert("参数描述不能为空");
    			document.iform.paramDesc.focus();
    			return ;
　			}
  			if(value=="")
  			{
  				alert("参数值不能为空");
  				document.iform.paramValue.focus();
  				return ;
  			}
  			
   var url = "common.sysparamdata.paramUpdate.d";
     	url = encodeURI(url);
	var sid =document.getElementsByName("paramId")[0].value;
	var svalue =document.getElementsByName("paramValue")[0];
	var sdesc =document.getElementsByName("paramDesc")[0].value;
	if(!checkNormal(svalue.value)){
	          
		         alert("您输入的参数值无效，请重新输入!");
		           svalue.focus();
		         
		         return;
		       }
	var params = "paramId=" + sid+"&paramValue="+svalue.value +"&paramDesc="+sdesc;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseWin, onSuccess:doFiniWin});
  }
   function doFiniWin() {
    
   }
  function showResponseWin(xmlHttp) {
	 
	 var xmldoc = xmlHttp.responseXML;
	      
	      var results   = xmldoc.getElementsByTagName("col");
	      if(results!=null&&results.length>0){
	       alert("参数修改成功！");
	      }else{
	        alert("参数修改失败，请重试！");
	      }
	      var param_id = results[0].text;
	       var param_type = results[1].text;
	        var param_value = results[2].text;
	         var param_desc = results[3].text;
	      returnValue = param_id+";"+param_type+";"+param_value+";"+param_desc;
	      window.close();
  }

   function chongzhi(){

    
    var value=document.getElementById("paramValue");
       
        value.value="";
   }
  
  function clse(){
   
    window.close();
  }
  