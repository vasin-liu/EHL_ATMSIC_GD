   function modify(){
             var desc=document.all("paramDesc").value;
			var value=document.all("paramValue").value;
			
　			if(desc=="")
			{
    			alert("参数描述不能为空");
    			document.myform.paramDesc.focus();
    			return ;
　			}

  	  			if(value=="")
  			{
  				alert("参数值不能为空");
  				document.myform.paramValue.focus();
  				return ;
  			}
					
           var url = "common.sysparamdata.paramAdd.d";
     	       url = encodeURI(url);
	            var paramType =document.all("paramType").value;	
	            var paramValue =document.all("paramValue").value;
	            var paramDesc =document.all("paramDesc").value;
	            var params = "paramType=" + paramType+"&paramValue="+paramValue+"&paramDesc="+paramDesc;
	            
				new Ajax.Request(url, {method:"post", parameters:params, onComplete:showUpdateWin, onSuccess:doFiniWin});
	  }
	 function doFiniWin() {
			    
	  }
	 function showUpdateWin(xmlHttp) {
	       var xmldoc = xmlHttp.responseXML;
	      
	      var results   = xmldoc.getElementsByTagName("col");
	      if(results!=null&&results.length>0){
	       alert("参数成功保存！");
	     }
	      var param_id = results[0].text;
	       var param_type = results[1].text;
	        var param_value = results[2].text;
	         var param_desc = results[3].text;
	      returnValue = param_id+","+param_type+","+param_value+","+param_desc;
	      window.close();	       
			
	 }
  
    window.onclose=function(){ 
parent.refresh; 
} 
  
  function clse(){
    window.onclose();
    window.close();
  }