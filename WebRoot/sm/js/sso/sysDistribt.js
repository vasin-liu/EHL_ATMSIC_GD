   /**
    * Copyright (c) 2008, EHL Technologies, Inc.
    * All right reserved.
    * 说明：实现对单点登录用户操作（t_sys_distribt）表进行 新增、编辑、删除、刷新操作
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
	 var url = "common.ssosysdistribt.loadFilter.d";
		url = encodeURI(url);
		mygridt_sysdistribt_s.clearAll();//把表格的内容全部清空
		mygridt_sysdistribt_s.loadXML(url ,pageLoaded);
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
	    * param: did为布控编码
	    * author：zhaoyu
	    * date:   2008-04-24
	    * version:
	    */
	function doQuery(did,insrtOrUpdt){
	    fillListBox("user","userCode","155","SELECT usercode,username FROM t_sys_user","请选择用户！","showList('"+did+"','"+insrtOrUpdt+"')");
	}
	function showList(did,insrtOrUpdt) {
	//如果是增加
		if(insrtOrUpdt == "0"){
		    var table = document.getElementsByTagName("table"); 
			    table[0].childNodes[0].childNodes[0].style.display= "none";
			    table[0].createCaption().appendChild(document.createTextNode(""));
		}
		if (insrtOrUpdt == "1") {
		     var table = document.getElementsByTagName("table"); 
		     table[0].createCaption().appendChild(document.createTextNode(""));
			var url = "common.ssosysdistribt.getDataById.d";
			url = encodeURI(url);
			var params = "did=" + did;
			
			new Ajax.Request(url, {method:"post", parameters:params, onComplete:showQ, onSuccess:doGetXMLFini});
		}
	}
	function showQ(xmlHttp) {
	
	//将查询返回的数据展示在页面
		var xmldoc = xmlHttp.responseXML;
		var BH  = document.getElementById("BH");
		var userCode     = document.getElementById("userCode");
		var subURL  = document.getElementById("subURL");
		var action  = document.getElementById("action");
		var results   = xmldoc.getElementsByTagName("col");
		
		BH.value = results[0].text;
		userCode.value = results[1].text;		
		subURL.value = results[2].text;		
		action.value = results[3].text;
	}
	function doGetXMLFini() {
	}
	
	  /** 
	    * desc:  增加或编辑. 绑定前端数据,通过ajax传到后端进行相应的业务处理 
	    * param:int insrtOrUpdt  0 是新增操作，1 是编辑操作
	    * return:
	    * author：zhaoyu
	    * date:   2008-3-24
	    * version:
	    */
	function addOrUpdate(insrtOrUpdt) {
	
	//获取页面信息，传入后台进行处理，顺序系统用户编号,用户操作路径，用户操作类型       window.returnValue="fresh";
		var BH  = document.getElementById("BH");
		var userCode = document.getElementById("userCode");
		var subURL   = document.getElementById("subURL");
		var action   = document.getElementById("action");
		var dataArray = new Array();
		    if(userCode.value==null||userCode.value.Trim()==""){
		       alert("请选择用户名！");
		       return;
		     }
		     if(subURL.value==null||subURL.value.Trim()==""){
		        alert("请填写操作路径");
		        return;
		     }
		   //过滤非法字符
		      if(!checkNormalStr(subURL.value)){
		         alert("您输入的操作路径无效，请重新输入!");
		         subURL.focus();
		         return;
		       }
		      if(action.value==null||action.value.Trim()==""){
		        alert("请选择操作类型");
		        return;
		     }
		      
		   	dataArray.push(BH.value);	
			dataArray.push(userCode.value);	
			dataArray.push(subURL.value);
			for(var i=0;i<action.length;i+=1){
			  if(action.options[i].selected == true){
			     dataArray.push(action.options[i].value);
			  }
			
			}
		
		var url = "common.ssosysdistribt.addOrUpdate.d";
		url = encodeURI(url);
		var xmlbody = createLineXMLBody(dataArray, "RFWin");
		var fullbody = createFullXMLBody(xmlbody);
		var reEq = /=/g;
		fullbody = fullbody.replace(reEq, "$");
		var params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insrtOrUpdt;
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
	    * desc:   删除 .通过ajax将获取到的数据的id值传入后台
	    * author：zhaoyu
	    * date:   2008-04-24
	    * version:
	    */
	function doDelete() {
		if(getDelId()!=""){  
			if (confirm("您确定删除此项记录吗?")) {
				var url = "common.ssosysdistribt.delete.d?getparam=" + getDelId();
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
		var iRowsNum = mygridt_sysdistribt_s.getRowsNum();                      		
		var delArray = new Array(); 
		for (var i = 0; i < iRowsNum; i++) {  
           //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
			if (mygridt_sysdistribt_s.cells2(i, 0).getValue() == 1) {         
                //获取单元格数据
				var strId = mygridt_sysdistribt_s.cells2(i, 1).getValue();    
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
		var iRowsNum = mygridt_sysdistribt_s.getRowsNum();                     
		var delArray = new Array(); 
		//循环遍历数据表的行
		for (var i = 0; i < iRowsNum; i+=1) {                       
            //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态
			if ( mygridt_sysdistribt_s.cells2(i, 0).getValue() == 1) {         
              //获取单元格数据
			  var strId = mygridt_sysdistribt_s.cells2(i, 1).getValue();    
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
    * desc:  加载工具栏组件 ，进行增加、修改、删除、转存Excel、查询操作
    * author：zhaoyu
    * date:   2008-03-18
    * version:
    */
    function disbutton(){
      aToolBar.disableItem("0_excel");
	  aToolBar.disableItem("0_chart");
	  aToolBar.disableItem("0_info");
    }

		function onButtonClick(itemId, itemValue) {//工具栏的按钮单击事件（按钮的id,按钮的值）
			var id = itemId;			
			if (id == "0_new") {//触发新增事件，打开新增页面
				var returnvalue = window.showModalDialog("sysDistribtNew.jsp?insrtOrUpdt=0&did=", "", "dialogWidth:350px;dialogHeight:280px");
			     if(returnvalue =="fresh"){
			            loadFilter();
			         }
			}
			if (id == "0_edit") {//触发编辑事件，打开编辑页面
			 if(getRFId()==undefined)
			    {
			      }
			      else{
				     var returnvalue = window.showModalDialog("sysDistribtNew.jsp?insrtOrUpdt=1&did=" + getRFId() + "", "", "dialogWidth:350px;dialogHeight:280px");
			         if(returnvalue =="fresh"){
			            loadFilter();
			         }
			}}
			if (id == "0_search") {
			}
			if (id == "0_excel") {
			    saveAsExcel('单点登录用户操作','sysDistribt',mygridt_sysdistribt_s);
			}
			if (id == "0_delete") {
				doDelete();//触发删除事件
			}
			if (id == "0_filter") {
				Filter();//触发过滤事件
			}
			if (id == "0_fresh") {
				freshs(); //触发刷新事件
			}
			
		}

		
		
	
	