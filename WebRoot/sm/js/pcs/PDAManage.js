function onButtonClick(itemId, itemValue) {
		var id = itemId;
		if (id == "0_new") {
//			
 		}
		if (id == "0_edit") {
//			
		}
		if (id == "0_delete") {
		//	doDelete();
		}
		if (id == "0_filter") {
			Filter();
		}

		if (id == "0_fresh") {
			fresh();
		}
		
		if (id == "0_excel") {
			saveAsExcel("PDA", "pda", mygridt_pda_s);
		}
		
		if (id == "0_info") {
			if (getPId() == undefined) {
				//alert("请选择一条记录！");
			} 
			else {
				
				window.showModalDialog("PDAInfo.jsp?deviceid=" + getPId() + "", "", "dialogWidth:380px;dialogHeight:460px");
			}
		}
	}
	
	
function getPId() {
	var iRowsNum = mygridt_pda_s.getRowsNum();                      //获取表格总的行数
	var array = new Array();                                //定义数组array
	for (var i = 0; i < iRowsNum; i += 1) {                       //循环遍历数据表的行


		if (mygridt_pda_s.cells2(i, 0).getValue() == 1) {         //判断表格的复选框是否为被选中的状态，"1"为选中的状态，"0"为非选中的状态

			var strId = mygridt_pda_s.cells2(i, 1).getValue();    //获取i 行 1 列单元格数据

			array.push(strId);
		}                   
	}
	if (array.length >= 2 || array.length < 1) {
		alert("\u8bf7\u9009\u62e9\u4e00\u6761\u6570\u636e");
		return;
	}
	return array[0];
}	
	
//  function f_handphone_02(){
//    var rId = mygridt_handphone_s.getSelectedId();
//	var str = mygridt_handphone_s.cells2(rId, 1).getValue();
//	str = encodeURI(str);
//	
//	window.showModalDialog("HandPhoneList.jsp?insrtOrUpdt=1&STID=" + str + "", "", "dialogWidth:380px;dialogHeight:350px");
//  }

//  function f_handphone_04() {
//	var pId = mygridt_handphone_s.getSelectedId();
//	var str = mygridt_handphone_s.cells2(pId, 3).getValue();
//	window.showModalDialog("PersonList.jsp?RYID=" + str + "", "", "dialogWidth:380px;dialogHeight:540px");
//	
//  }
  //获取机构 展示其明细
  function f_pda_04(){
    var rId = mygridt_pda_s.getSelectedId();
	var str = mygridt_pda_s.cells2(rId, 3).getValue();
	str = encodeURI(str);
	
	window.showModalDialog("DepartmentInfo.jsp?jgid=" + str + "", "", "dialogWidth:650px;dialogHeight:450px");
  }

  function f_pda_02(){
    var rId = mygridt_pda_s.getSelectedId();
	var str = mygridt_pda_s.cells2(rId, 1).getValue();
	str = encodeURI(str);
	window.showModalDialog("PDAInfo.jsp?deviceid=" + str + "", "", "dialogWidth:380px;dialogHeight:460px");
  }
  
    function f_pda_06(){
    var rId = mygridt_pda_s.getSelectedId();
	var str = mygridt_pda_s.cells2(rId, 5).getValue();
	str = encodeURI(str);
	
	window.showModalDialog("PersonList.jsp?RYID=" + str + "", "", "dialogWidth:400px;dialogHeight:530px");
  }


function doQuery(deviceid) {
	
	if (deviceid != "") {
		var url = "pcs.PDAManage.getDataById.d";
		url = encodeURI(url);
		var params = "deviceid=" + deviceid;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:showPDAInfo});
	}
}

function showPDAInfo(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	
	var deviceid=document.getElementById("deviceid");
	var deptname=document.getElementById("deptname");
	var policename=document.getElementById("policename");
	var devicestate=document.getElementById("devicestate");
	var longitude=document.getElementById("longitude");
	var latitude=document.getElementById("latitude");
	var velocity=document.getElementById("velocity");
	var angle=document.getElementById("angle");
	var gpstime=document.getElementById("gpstime");
	var ondutytime=document.getElementById("ondutytime");
	var offdutytime=document.getElementById("offdutytime");
	
	deviceid.value = results[1].text;
	deptname.value = results[2].text;
	policename.value = results[3].text;
	devicestate.value = results[4].text;
	longitude.value = results[5].text;
	latitude.value = results[6].text;
	velocity.value = results[7].text;
	angle.value = results[8].text;
	gpstime.value = results[9].text;
	ondutytime.value = results[10].text;
	offdutytime.value = results[11].text;
}	


function Filter() {
	var deviceid = document.getElementById("deviceid").value;
	var policename = document.getElementById("policename").value;
	var devicestate = document.getElementById("devicestate").value;
	var SSJG = document.getElementById("ssjgtxt").value;
	var strUrl = "pcs.PDAManage.filter.d";
	if(SSJG==null||SSJG==""){
		G_jgID="";
	
	}
	strUrl = encodeURI(strUrl);
	var params = "deviceid=" + deviceid + "&&policename=" + policename + "&&devicestate=" + devicestate + "&&SSJG=" + G_jgID;
	params = encodeURI(params);                                   //进行编码转换
	mygridt_pda_s.clearAll();                                   //把表格的内容全部清空
	mygridt_pda_s.loadXML(strUrl + "?" + params, pageLoaded);    //重新加载参数
}

function fresh(){
	Filter();
}	
















