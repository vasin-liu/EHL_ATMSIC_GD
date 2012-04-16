	/**
	 *  作者：ldq
	 *  功能：toolbar禁用公共接口. 
	 *  日期：2008-6-5
	/**
	 * 函数功能：根据权限加载工具条.
	 * 参数说明：功能编码.
	 */
	function onLoadToolbar(fileURL) {
		//alert("test-->2:"+voidOptStr);
		aToolBar = new dhtmlXToolbarObject("toolbar_zone", "100%", 25, "");//创建工具栏实例
	
		aToolBar.setOnClickHandler(onButtonClick);                         //调用工具栏的点击事件onButtonClick
		
		if(typeof fileURL != "undefined" && fileURL.indexOf("xml") > -1){
		   aToolBar.loadXML(fileURL);                   //解析xml生成工具栏
	
		}else{
		   aToolBar.loadXML("../../../sm/xml/toolbar.xml");    
		}
		
		aToolBar.showBar();
	    setTimeout("enablePrivItem()",100);
	}
	
	function enablePrivItem(){
	   var url = "common.toolbar.createToolbar.d?funcid="+getFunccode();
	   url = encodeURI(url);
	   var params = "";
	   new Ajax.Request(url, {method:"post", parameters:params, onComplete:showToolbar});
	}
	 
	function showToolbar(xmlHttp){
	    var xmlHttp = xmlHttp;
		var xmldoc = xmlHttp.responseXML;
	    var results = xmldoc.getElementsByTagName("col");
		for(var i=0;i<results.length; i++){
		  aToolBar.enableItem(results[i].text);
		}
	}
	
		
	/**
	 * 函数功能：从URL中提取funccode.
	 * 返回值：  功能编码 funccode.
	 */		
	function getFunccode() {
		var url = window.location.href;
		var inner = url.indexOf(".jsp");
		var useURL = "";
		var target = url.substring(0, inner).split("/");
		for (var i = 5; i < target.length; i++) {
			if (i == (target.length) - 1) {
				useURL += target[i];
			} else {
				useURL += target[i] + "/";
			}
		}
		return useURL;
	}
	
