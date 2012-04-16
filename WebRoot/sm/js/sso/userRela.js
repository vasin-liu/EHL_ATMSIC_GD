   /**
	    * Copyright (c) 2008, EHL Technologies, Inc.
	    * All right reserved.
	    * 说明：实现对布控车辆名单表进行 新增、编辑、过滤、查看明细、删除、刷新操作
	    * 作者： zhaoyu
	    * 日期:  2008-03-20
	    */
	    
	    
	  /** 
	    * desc:  初始页面时过滤数据
	    * param: did为布控编码	
	    * return:
	    * author：zhaoyu
	    * date:   2008-03-25
	    * version:
	    */	
	function loadFilter(){	
	 var url = "common.ssouserrela.loadFilter.d";
		url = encodeURI(url);
		mygridt_userrela_s.clearAll();//把表格的内容全部清空
		mygridt_userrela_s.loadXML(url ,pageLoaded);
	}
	
	/** 
    * desc:  刷新
    * author：zhaoyu
    * date:   2008-03-25
    * version:
    */
function freshs() {
	loadFilter();
}
	 
	  /** 
	    * desc:  编辑前端数据
	    * author：zhaoyu
	    * date:   2008-03-20
	    * version:
	    */
	function doQuery(did,insrtOrUpdt) {	   
	   var table = document.getElementsByTagName("table"); 
	   //当增加时
	   if(insrtOrUpdt == "0"){
	       table[0].childNodes[0].childNodes[0].style.display= "none";
	        table[0].createCaption().appendChild(document.createTextNode(""));	        	      	       
	   }
	    //当修改时
	   if(insrtOrUpdt=="1"){
	     table[0].createCaption().appendChild(document.createTextNode(""));
	   }
		if (did != "") {
			var url = "common.ssouserrela.getDataById.d";
			url = encodeURI(url);
			var params = "did=" + did;
			new Ajax.Request(url, {method:"post", parameters:params, onComplete:showQueryResponse, onSuccess:doGetXMLFini});
		}
	}
	function showQueryResponse(xmlHttp) {
	//将查询返回的数据展示在页面，顺序是id，usercode，subusername
		var xmldoc = xmlHttp.responseXML;		 
		var id = document.getElementById("BH");	   
		var subUserName  = document.getElementById("subUserName");				
		var results   = xmldoc.getElementsByTagName("col");
		id.value = results[0].text;		
		subUserName.value = results[2].text;				 
		   for (var i=0;i<userCode.length;i+=1){
		        if(userCode.options[i].value == results[1].text){
		            userCode.options[i].selected=true;
		        }
		    }		
		
	}
	function doGetXMLFini() {
	}
	
	  /** 
	    * desc:  增加或编辑. 绑定前端数据,通过ajax传到后端进行相应的业务处理 
	    * param:int insrtOrUpdt  0 是新增操作，1 是编辑操作	
	    * return:
	    * author：fengda
	    * date:   2007-10-14
	    * version:
	    */
	function addOrUpdate(insrtOrUpdt) {	
	//获取页面信息，传入后台进行处理，顺序记录编号,系统用户编号,子系统用户名
		window.returnValue = "fresh";
		var id = document.getElementById("BH");
		var subUserName = document.getElementById("subUserName");
		var dataArray = new Array();				
		    if(userCode.value==null||userCode.value.Trim()==""){
		       alert("请选择用户名！");		       
		       return;
		     }		     		    
		      if(subUserName.value==null||subUserName.value.Trim()==""){
		        alert("请填写子系统用户名");
		        subUserName.focus();
		        return;
		     }
		     if(!checkNormalStr(subUserName.value)){
		         alert("您的子系统用户名无效，请重新输入!");
		         subUserName.focus();
		         return;
		       }
		    dataArray.push(id.value);	
			dataArray.push(userCode.value);				
			dataArray.push(subUserName.value);						   				
		var url = "common.ssouserrela.addOrUpdate.d?userCode="+userCode.value;
		     url += "&subUserName="+subUserName.value;
		     url +="&insertOrUpdate=" + insrtOrUpdt+"&id="+id.value;
		url = encodeURI(url);
		//var xmlbody = createLineXMLBody(dataArray, "RFWin");
		//var fullbody = createFullXMLBody(xmlbody);
		//var reEq = /=/g;
		//fullbody = fullbody.replace(reEq, "$");
		//alert(fullbody);
		//var params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insrtOrUpdt;
		var params = "";
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showNew, onSuccess:doFiniSave});
	}
	
	function showNew(xmlHttp) {	   
		alert(xmlHttp.responseText); 		
		
	}
	function doFiniSave() {
	    loadFilter();
	  //alert("\u6570\u636e\u5f55\u5165\u6210\u529f");
	}
	  /** 
	    * desc:   删除 .通过ajax将获取到的数据的id值（权限编码）传入后台
	    * author：zhaoyu
	    * date:   2008-04-24
	    * version:
	    */
	function doDelete() {
		if(getDelId()!=""){ 
			if (confirm("您确定删除此项记录吗?")) {
				var url = "common.ssouserrela.delete.d?getparam=" + getDelId();
				url = encodeURI(url);
				var params = "delArray=" + getDelId();
				new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResponseDelete, onSuccess:doFiniDelete});
			} else {
			return;
			}
		}else{
	 alert("请至少选择一项记录进行此操作");
	 }
	}
	  /** 
	    * desc:   获取选中记录的唯一标识,并放入数组，用于删除记录
	    * author：zhaoyu
	    * date:   2008-04-24
	    * version:
	    */
	function getDelId() {	
	    //获取表格总的行数	
		var iRowsNum = mygridt_userrela_s.getRowsNum();                      		
		var delArray = new Array();
		//循环遍历数据表的行	
		for (var i = 0; i < iRowsNum; i++) { 
		    //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态	                      
			if (mygridt_userrela_s.cells2(i, 0).getValue() == 1) {  
			    //获取单元格数据       
				var strId = mygridt_userrela_s.cells2(i, 1).getValue();    
				delArray.push(strId); 
			}
		}
		return delArray;
	}
	function showResponseDelete(xmlHttp) {
		alert(xmlHttp.responseText);
		freshs();
	}
	function doFiniDelete() {
		//alert("\u6b64\u8bb0\u5f55\u88ab\u6210\u529f\u5220\u9664");
	}

    
 /** 
    * desc:  重置
    * author：zhaoy
    * date:   2008-03-18
    * version:
    */
    function reset() {
	    var input;
	    var select;
	    var textarea;
	    var queryTable = window.document.getElementById("dataTable");
	    if (queryTable != null) {
	        input = queryTable.getElementsByTagName('input');
	        select = queryTable.getElementsByTagName("select");
	        textarea = queryTable.getElementsByTagName('textarea');
	    } else {
	        input = document.getElementsByTagName("input");
	        select = document.getElementsByTagName("select");
	        textarea = document.getElementsByTagName('textarea');
	    }
	    for (var i = 0; i < input.length-3; i++) {
	        var item = input[i];
	        if (item.type != 'button' && !item.readOnly) {
	            item.value = '';
	        }
	    }
	    for (var i = 0; i < textarea.length; i++) {
	        var item = textarea[i];
	        if (item.type != 'button' && !item.readOnly) {
	            item.value = '';
	        }
	    }
	    for (var i = 0; i < select.length; i++) {
			if (select[i].type != 'button' && !select[i].readOnly) {
				select[i].value = '-1';
			}
	    }
   }
  
  /** 
    * desc:  获得数据表格中加亮显示的记录的id(布控编码),限制一条数据
    * author：zhaoyu
    * date:   2008-03-18
    * version:
    */
	function getRFId() {
	    //获取表格总的行数
		var iRowsNum = mygridt_userrela_s.getRowsNum();                      
		//var rowid = mygridt_qx_s.getSelectedId();
		var delArray = new Array();   
		//循环遍历数据表的行
		for (var i = 0; i < iRowsNum; i+=1) {                       
            //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
			if ( mygridt_userrela_s.cells2(i, 0).getValue() == 1) {         
			    var strId = mygridt_userrela_s.cells2(i, 1).getValue(); 
				delArray.push(strId);  
			}  
		}
		if(delArray.length >= 2||delArray.length<1){
			alert("请选择一条记录");
			return;
		}
		return delArray;
	}
	
   /** 
    * desc:  工具栏的按钮单击事件（按钮的id,按钮的值）
    * author：zhaoyu
    * date:   2008-03-18
    * version:
    */
	function onButtonClick(itemId, itemValue) {
			var id = itemId;			
			if (id == "0_new") {//新增			 
				 var returnvalue = window.showModalDialog("userRelaNew.jsp?insrtOrUpdt=0&did=", "", "dialogWidth:350px;dialogHeight:260px");
				 if(returnvalue == "fresh"){
				           loadFilter();
				  }
			
			}
			if (id == "0_edit") {//编辑
			  if(getRFId()==undefined){
			    return;
			  }else{
				    var returnvalue = window.showModalDialog("userRelaNew.jsp?insrtOrUpdt=1&did=" + getRFId() + "", "", "dialogWidth:350px;dialogHeight:260px");
			         if(returnvalue == "fresh"){
			           loadFilter();
			         }						
			  }
			}

			if (id == "0_excel") {
			    saveAsExcel('单点登录用户关系','userRela',mygridt_userrela_s);
			}
			if (id == "0_delete") {
				doDelete();//删除
			}
			if (id == "0_filter") {
				Filter();//过滤
			}
			if (id == "0_fresh") {
				freshs();//刷新
			}
			
		}

		
		
	
	