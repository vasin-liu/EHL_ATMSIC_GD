function selectTag(showContent,selfObj){
	  // 操作标签
		  var tag = document.getElementById("tagsd").getElementsByTagName("li");
		  var taglength = tag.length;
		  
		  for(i=0; i<taglength; i++){
		    tag[i].className = "";
		    tag[i].style.background="url('../../image/sysoption/bg.gif')  right bottom";
		  }
		  selfObj.parentNode.className = "selectTag";
		   selfObj.parentNode.style.background="url('../../image/sysoption/bg2.gif')  right bottom";
		  // 操作内容
		  j=document.getElementById("tagContentd").getElementsByTagName("div");
		  for(i=0; i<j.length; i++){
		    j[i].style.display = "none";
		  }
		  var selectDiv = document.getElementById(showContent);
		  var selectDivChilds = selectDiv.getElementsByTagName("div");
		   for(i=0; i<selectDivChilds.length; i++){
		    selectDivChilds[i].style.display = "block";
		  }
		  selectDiv.style.display = "block";
		 selfObj.style.background = "left bottom";
		 selectDiv.childNodes[0].focus();
	}
/**
*@作者： 郭亮亮
*@日期: 2008-04-09
*@说明: 删除 .通过ajax将获取到的数据的id值（机构ID）传入后台
*/
function doDelete() {
	if (G_jgID != "") {
		if(confirm("确认删除此机构!")) {
		   var strUrl = "pcs.departmentManage.delete.d";
			strUrl = encodeURI(strUrl);
			var params = "JGID=" + G_jgID;
			new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:doFiniDelete});
		}
	}else{
	   alert("请选择要删除的机构！");
	}
}
//删除页面相应节点
function doFiniDelete(xmlHttp){
	var retVal = xmlHttp.responseText;
	try{
		if(retVal=="机构删除成功!")
		{
			 //清空明细
			var ele_Array = document.getElementsByTagName("input");
			for(var i=0;i<ele_Array.length-3;i++){
		        ele_Array[i].value="";
            }
			  //删除SPAN结点
			var spanObj = document.getElementById(G_jgID);
			spanObj.parentNode.removeChild(spanObj);
			//删除BR结点
			var brObj =  document.getElementById(G_divID+G_jgID);
			brObj.parentNode.removeChild(brObj);
			var divObj = document.getElementById(G_divID);
			//删除DIV结点
			divObj.parentNode.removeChild(divObj);
			
		  //如果是删除了最后一个节点，则更改父节点图片
			var parentDivObj = document.getElementById(G_divID.substr(0,G_divID.length-2));
			var parentImgObj = document.getElementById("i"+parentDivObj.name);			
			if(parentDivObj.childNodes.length < 1 && parentImgObj != null){			  
			   parentImgObj.setAttribute("src","../../image/tree/blankLeaf.gif");			   
			}
		 }
	}catch(e){
	   return null;
	}finally {
	    alert(retVal);
	  }
}
//查询数据库为编辑页面读入数据
function doQuery(JGID) {
	if (JGID != "") {
		var url = "pcs.departmentManage.getDataById.d";
		url = encodeURI(url);
		var params = "JGID=" + JGID;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showDataToPage});
	}
}

//数据写入编辑页面
function showDataToPage(xmlHttp) {
	var xmldoc = xmlHttp.responseXML;
	var jgid = document.getElementById("JGID");
	var jgmc = document.getElementById("JGMC");
	var jgzn = document.getElementById("JGZN");
	
	var results = xmldoc.getElementsByTagName("col");
	jgid.readOnly = true;
	jgmc.readOnly = true;
	jgzn.focus();
	document.getElementById("JGMC").value = results[1].text;
	document.getElementById("JGCCBM").value = results[2].text;
	
	var XZJB = document.getElementById("XZJB");
	for (var i = 0; i < XZJB.length; i++) {
        //下拉列表中的值与查询的值相同则选中
		if (XZJB.options[i].text == results[3].text) {			
			XZJB.options[i].selected = true;
		}
	}
	
	document.getElementById("JGZN").value = results[4].text;
	document.getElementById("YWMS").value = results[5].text;
	
	var JGLX = document.getElementById("JGLX");
	for (var i = 0; i < JGLX.length; i++) {
	            //下拉列表中的值与查询的值相同则选中
		if (JGLX.options[i].text == results[6].text) {
			JGLX.options[i].selected = true;
		}
	}
	
	document.getElementById("SCDZ").value = results[7].text;
	document.getElementById("YZBM").value = results[8].text;
	document.getElementById("BZRS").value = results[9].text;
	document.getElementById("ZBMJS").value = results[10].text;
	document.getElementById("ZBZGS").value = results[11].text;
	document.getElementById("QTRS").value = results[12].text;
	document.getElementById("FZR").value = results[13].text;
	document.getElementById("FZRDH").value = results[14].text;
	document.getElementById("FZRSJ").value = results[15].text;
	document.getElementById("ZBDH1").value = results[16].text;
	document.getElementById("ZBDH2").value = results[17].text;
	document.getElementById("ZBDH3").value = results[18].text;
	document.getElementById("CZDH").value = results[19].text;
}
/** 
 * desc:  根据父节点的id获得新增节点的层次编码
 * author：郭田雨
 * date:  
 * version:
 */
function doGetJGCCBM(JGID) {
	var strUrl = "pcs.departmentManage.getJgccbm.d";
	strUrl = encodeURI(strUrl);
	var params = "JGID=" + JGID;
	new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showEditData});
}
function showEditData(xmlHttp) {
	var xmldoc = xmlHttp.responseXML;
	var JGCCBM_input = document.getElementById("JGCCBM");
	var results = xmldoc.getElementsByTagName("col");
	JGCCBM_input.value = results[0].text;
	JGCCBM_input.readOnly = true;
}
/** ---功能 ：增加、编辑 --绑定前端数据到后端进行业务处理 * 时间：2008-04-11*/
function modify(insrtOrUpdt) {

	//验证非法字符
	var ele_Array = document.getElementsByTagName("input");
	for(var i=0;i<ele_Array.length-3;i++){
		   if(!checkNormalStr(ele_Array[i].value)){
		      alert("不允许输入非法字符");
		      return;
		   }
     }
	var dataArray = new Array();
	
	if (document.getElementById("JGID").value == "") {
		alert("请输入机构ID!");
		return;
	}	
    if(checkChineseName(document.getElementById("JGID").value)){
      alert("机构ID不能是汉字！");
      return;
    }
	if (document.getElementById("JGMC").value == "") {
		alert("请输入机构名称!");
		return;
	}
	if (document.getElementById("JGLX").value == "") {
		alert("请选择机构类型!");
		return;
	}
//	if(!checkMath(document.getElementById("JGID").value))
//    {
//        alert("请输入正确的机构ID!");
//		return;
//    }
    //验证负责人手机号码是否正确

    if(!checkMath(document.getElementById("FZRSJ").value))
    {
        alert("手机号码输入有误!");
		return;
    }
    if(document.getElementById("FZRSJ").value!="")
    {
      if(document.getElementById("FZRSJ").value.length!=11)
      {
         alert("手机号码长度错误!");
         return;
      }
    }
    //验证在编制人数输入是否为数字
    if(!checkMath(document.getElementById("BZRS").value))
    {
        alert("在编制人数应为数字!");
		return;
    }
    //验证在编民警数输入是否为数字
    if(!checkMath(document.getElementById("ZBMJS").value))
    {
        alert("在编民警人数应为数字!");
		return;
    }
    if(!checkMath(document.getElementById("YZBM").value))
    {
        alert("邮政编码应为数字!");
		return;
    }
     //验证在编职工数输入是否为数字
    if(!checkMath(document.getElementById("ZBZGS").value))
    {
        alert("在编职工人数应为数字!");
		return;
    }
    //验证其他人数输入是否为数字

    if(!checkMath(document.getElementById("QTRS").value))
    {
        alert("其他人数应为数字!");
		return;
    }
    //验证值班电话
    var tel1=document.getElementById("ZBDH1").value ;
    var tel2=document.getElementById("ZBDH2").value ;
    var tel3=document.getElementById("ZBDH3").value ;
    if(tel1==""&&tel2==""&&tel3==""){
        alert("请至少输入一个值班电话!");
     	document.getElementById("ZBDH1").focus();
     	return;
    } 
	   
	if(!checkImmobilityPhone(document.getElementById("CZDH").value))
    {
        alert("请正确输入传真号码!");
		return;
    }
    dataArray.push(document.getElementById("JGID").value);
	dataArray.push(document.getElementById("JGMC").value);
	dataArray.push(document.getElementById("JGCCBM").value);
	dataArray.push(document.getElementById("XZJB").value);
	dataArray.push(document.getElementById("JGZN").value);
	dataArray.push(document.getElementById("YWMS").value);
	dataArray.push(document.getElementById("JGLX").value);
	dataArray.push(document.getElementById("SCDZ").value);
	dataArray.push(document.getElementById("YZBM").value);
	dataArray.push(document.getElementById("BZRS").value);
	dataArray.push(document.getElementById("ZBMJS").value);
	dataArray.push(document.getElementById("ZBZGS").value);
	dataArray.push(document.getElementById("QTRS").value);
	dataArray.push(document.getElementById("FZR").value);
	dataArray.push(document.getElementById("FZRDH").value);
	dataArray.push(document.getElementById("FZRSJ").value);
	dataArray.push(document.getElementById("ZBDH1").value);
	dataArray.push(document.getElementById("ZBDH2").value);
	dataArray.push(document.getElementById("ZBDH3").value);
	dataArray.push(document.getElementById("CZDH").value);
	dataArray.push(document.getElementById("parentJGID").value);
	
	var url = "pcs.departmentManage.updateData.d";
	url = encodeURI(url);
	var xmlbody = createLineXMLBody(dataArray, "RFWin");
	var fullbody = createFullXMLBody(xmlbody);
	var reEq = /=/g;
	fullbody = fullbody.replace(reEq, "$");
	var params = "xmlStr=" + fullbody + "&insertOrUpdate=" + insrtOrUpdt;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:doFiniSave});
}
function doFiniSave(xmlHttp) {
	var retVal = xmlHttp.responseText;
	alert(retVal);
	window.close();
}

/** 
 * desc:  编辑页面关闭方法,返回页面值
 * author：郭田雨
 * date:   2008-05-10
 * version:
 */
function windowClose(){
		
		//定义返回的行政级别和机构类型变量
		var XZJBValue;
		var JGLXValue;
		
		//行政级别
		var XZJB = document.getElementById("XZJB");
		
		for (var i = 0; i < XZJB.length; i++) {
			if (XZJB.options[i].selected) {
				XZJBValue=XZJB.options[i].text;
			}
		}
		//机构类型
		var JGLX = document.getElementById("JGLX");
		for (var i = 0; i < JGLX.length; i++) {
			if (JGLX.options[i].selected) {
				JGLXValue=JGLX.options[i].text;
			}
		}
		//返回值，以","分隔
		window.returnValue = document.getElementById("JGID").value+","+document.getElementById("JGCCBM").value+
		","+document.getElementById("JGMC").value +","+  XZJBValue
		+","+document.getElementById("JGZN").value +","+ document.getElementById("YWMS").value
		+","+JGLXValue +","+ document.getElementById("SCDZ").value
		+","+document.getElementById("YZBM").value +","+ document.getElementById("BZRS").value
		+","+document.getElementById("ZBMJS").value +","+ document.getElementById("ZBZGS").value
		+","+document.getElementById("QTRS").value +","+ document.getElementById("FZR").value
		+","+document.getElementById("FZRDH").value +","+ document.getElementById("FZRSJ").value
		+","+document.getElementById("ZBDH1").value +","+ document.getElementById("ZBDH2").value
		+","+document.getElementById("ZBDH3").value +","+ document.getElementById("FZRSJ").value
		+","+document.getElementById("CZDH").value;
		window.close();
}
/** 
 * desc:  新增与修改的重置函数
 * author：郭田雨
 * date:   2008-04-09
 * version:
 */
function reset() {
	var input;
	var select;
	var textarea;
	var queryTable = window.document.getElementById("dataTable");
	if (queryTable != null) {
		input = queryTable.getElementsByTagName("input");
		select = queryTable.getElementsByTagName("select");
		textarea = queryTable.getElementsByTagName("textarea");
	} else {
		input = document.getElementsByTagName("input");
		select = document.getElementsByTagName("select");
		textarea = document.getElementsByTagName("textarea");
	}
	for (var i = 0; i < input.length - 3; i++) {
		var item = input[i];
		if (item.type != "button" && !item.readOnly) {
			item.value = "";
		}
	}
	for (var i = 0; i < textarea.length; i++) {
		var item = textarea[i];
		if (item.type != "button" && !item.readOnly) {
			item.value = "";
		}
	}
	for (var i = 0; i < select.length; i++) {
		if (select[i].type != "button" && !select[i].readOnly) {
			//select[i].value = "-1";
			select[i].value = "";
		}
	}
}
/**
  *名称：工具栏组件
  *功能 ： 可对当前文件进行进行增加、修改、删除操作
  */
function disbutton(){
    aToolBar.disableItem("0_info");
	aToolBar.disableItem("0_excel");
	aToolBar.disableItem("0_chart");
}
function onButtonClick(itemId, itemValue) {
	var id = itemId;
	if (id == "0_new") {
	      if(G_jgID == null ||G_jgID  == ""){
	         alert("请选择父机构进行添加！");
	         return;
	      }
		  var Revalue =  window.showModalDialog("DepartmentEdit.jsp?insrtOrUpdt=0&JGID=" + G_jgID + "", "", "dialogWidth:650px;dialogHeight:450px");
		  //新增机构后写入页面
		     if(Revalue!=null&&Revalue!="") {
			   var useData = Revalue.split(",");
			  //alert(useData[0]+"----"+document.getElementById(useData[0]));
			   if(useData[0]!=null&&useData[0]!=""&& document.getElementById(useData[0])==null){
			   
			   //获得父图片
			   var parentIMG = document.getElementById("i"+G_jgID);
			   
			   if(parentIMG.src.indexOf("blankLeaf.gif")>0) {
			     parentIMG.src = "../../image/tree/minusClosed.gif";
			     
			   }
			   //获得父div的ID
			   var parentDivId = useData[1].substring(0,useData[1].length-2);
			   //取得父节点
			   var parentDIV = document.getElementById(parentDivId);
			  
			   //创建左边图片
			   var newIMG = document.createElement("img");
			   
			   newIMG.setAttribute("src", "../../image/tree/blankLeaf.gif");
			   newIMG.setAttribute("id", "i" +useData[0] );
			   newIMG.setAttribute("width", "36");
			   //创建子节点span
			   var newSPAN = document.createElement("span");
			   
			   newSPAN.setAttribute("id",useData[0]); //添加id 与数据库 JGID 相对应
			   newSPAN.style.cursor = "hand"; //添加鼠标显示手型
			   newSPAN.style.color = "black"; //添加字体颜色
			   	
			   //添加image子节点
			   newSPAN.appendChild(newIMG);
				 
				//显示机构名称		
			   newSPAN.innerHTML = loadBlank(parentDivId,newSPAN.innerHTML + useData[2]);
			    //创建div子节点
				var newDIV = document.createElement("div");
				//div添加展现id 与 doOnClick 方法第一个参数相同
			    newDIV.setAttribute("id",useData[1]);
			    newDIV.setAttribute("name",useData[0]);	   	    
				newDIV.style.display = "none";
				
				//添加onClick事件传div 的ID(用来展现) span 的ID 用来获得父节点,此ID与数据库中的JGID相对应
				newSPAN.onclick = new Function("doOnClick('" + useData[1]+ "','" + useData[0] + "')");
				parentDIV.appendChild(newSPAN);
				
				//添加换行
				var newBR = document.createElement("br");
				newBR.setAttribute("id",useData[1]+useData[0]);
				parentDIV.appendChild(newBR);
				//添加div子节点
				parentDIV.appendChild(newDIV);
				doOnClick(parentDivId,G_jgID);
				
			}
		 }
	}
	if (id == "0_edit") {
		if (G_jgID == "") {
			alert("\u8bf7\u9009\u62e9\u8981\u7f16\u8f91\u7684\u8bb0\u5f55");
		} else {
			var Revalue = window.showModalDialog("DepartmentEdit.jsp?insrtOrUpdt=1&JGID=" + G_jgID + "", "", "dialogWidth:650px;dialogHeight:450px");
			//编辑页面后刷新,使页面值与数据库同步
			if(Revalue!=null&&Revalue!="") {
		       var useData = Revalue.split(",");
			   if(useData[0]!=null&&useData[0]!="") {
				    var spanEdit = document.getElementById(useData[0]);
					//显示机构名称	
//					var oldImgObj = document.getElementById("i"+useData[0]);
//					var oldInnerHTML =spanEdit.innerHTML.substring(0,spanEdit.innerHTML.indexOf(">")+1);
//					
//				    spanEdit.innerHTML =oldInnerHTML + useData[2];
				    document.getElementById("JGMC").value = useData[2];
				    document.getElementById("XZJB").value = useData[3]=="全部"?"":useData[3];
				    document.getElementById("JGZN").value = useData[4];
				    document.getElementById("YWMS").value = useData[5];
				    document.getElementById("JGLX").value = useData[6]=="全部"?"":useData[6];
					document.getElementById("SCDZ").value= useData[7];
					document.getElementById("YZBM").value= useData[8];
					document.getElementById("BZRS").value= useData[9];
					document.getElementById("ZBMJS").value= useData[10];
					document.getElementById("ZBZGS").value= useData[11];
					document.getElementById("QTRS").value= useData[12];
					document.getElementById("FZR").value= useData[13];
					document.getElementById("FZRDH").value= useData[14];
					document.getElementById("FZRSJ").value= useData[15];
					document.getElementById("ZBDH1").value= useData[16];
					document.getElementById("ZBDH2").value= useData[17];
					document.getElementById("ZBDH3").value= useData[18];
					document.getElementById("CZDH").value= useData[20];
			    }
		   }
		}
	}
	if (id == "0_delete") {
		doDelete();
	}
	if (id == "0_excel") {
	
	    var dataSql = "SELECT F_GET_FULLDEPT(JGID),F_GET_NAME('013013',XZJB)";
	       dataSql+= ",JGZN,YWMS,F_GET_NAME('013010',JGLX),SCDZ,YZBM,BZRS,ZBMJS,ZBZGS,QTRS";
	       dataSql+= ",FZR,FZRDH,FZRSJ,ZBDH1,ZBDH2,ZBDH3,CZDH FROM T_SYS_DEPARTMENT ";
	    var header = "机构名称,行政级别,机构职能,业务描述,机构类型,所处地址,邮政编码,编制人数,在编民警数";
	        header +=",在编职工数,其他人数,负责人,负责人电话,负责人手机,值班电话1,值班电话2,值班电话3,传真电话";
		saveFieldsAsExcel("机构信息表", "department", header,dataSql);
	}
}
