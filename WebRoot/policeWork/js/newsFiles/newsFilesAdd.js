
function doInsertNewsFiles() {
	if(!validateInput()) return;
	if($('title')) {
		if(!$('title').value){
			alert("请填写标题！");
			$('title').focus();
			return;
		}
	}
	if($('sendPeople')) {
		if(!$('sendPeople').value){
			alert("请填写报送人！");
			$('sendPeople').focus();
			return;
		}
	}
	if($('wordFile')) {
		if(!$('wordFile').value){
			alert("文档文件必须输入！");
			$('wordFile').focus();
			return;
		}
	}
	if($('modifyContent')) {
		if(!$('modifyContent').value){
//			alert("请填写内容概要！");
//			$('modifyContent').focus();
//			return;
		} else if($('modifyContent').value.length >= 500) {
			alert("请填写内容概要在500字内！");
			$('modifyContent').focus();
			return;
		}
	}
	if (!confirm("是否确认报送？")) {
		return;
	}
	var formObj = document.getElementById("alarmFileUploadForm");
	var fileObj = document.getElementById("wordFile");
	
	// 提交form数据
	if (fileObj != null && fileObj != "undefined" && fileObj != "") {
		var fileName = fileObj.value;
		if (fileName != null) {
			formObj.submit();
		}
	} else {
		alert("必须上传文档文件！");
		return;
	}
}

/**
 * 输入项目的验证<br/>
 * @return {Boolean}
 */
function validateInput() {
	var input;
	var select;
	var textarea;
	var reg =  /^[^~@\'\;#\$\^&]*$/;
	input = document.getElementsByTagName("input");
	textarea = document.getElementsByTagName("textarea");
	for (var i = 0; i < input.length; i++) {
		var item = input[i];
		if (item.type == "text" && !item.readOnly) {
			if(!reg.test(item.value)){
				alert("不可输入特殊字符,包括:'、;、~、@、#、$、^、&");
				item.focus();
				return false;
			}
		}
	}
	for (var i = 0; i < textarea.length; i++) {
		var item = textarea[i];
		if (item.type != "button" && !item.readOnly) {
			if(!reg.test(item.value)){
				alert("不可输入特殊字符,包括:'、;、~、@、#、$、^、&");
				item.focus();
				return false;	
			}
		}
	}
	return true;
}

function setPageValue (newsFileid) {
	var strUrl = "policeWorks.newsFiles.getNewsFileInfo.d";  //把参数传给后端的java
	strUrl = encodeURI(strUrl);
	var params = "newsFileid=" + newsFileid;
	new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showPageResults});
}

function showPageResults (xmlHttp) {
	
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row")[0].getElementsByTagName("col");
	
	$("title").value = results[1].text;
	$("sendTime").value = results[2].text;
	$("sendUnit").value = results[3].text;
	$("sendPeople").value = results[4].text;
	$("modifyContent").value = results[7].text;
	$("wordFileList").innerHTML = showFile(results[5].text);
	$("streamFileList").innerHTML = showFile(results[6].text);
	
	//Modify by Xiayx 2011-8-28
	var writer = results[12].text;
	var writerEl = $("writer");
	writerEl.readOnly = true;
	writerEl.value = writer;
	//Modification finished
}

function showFile (dataBasefilePath, isRead, fname) {
	 var file;
	 if (dataBasefilePath != "null" && dataBasefilePath != "" && dataBasefilePath != null) {
	    var filePath = dataBasefilePath.replace(/^　$/,'');
	    var str1 = filePath;
		var regstr = "/";
		var regresult = new RegExp(regstr)
		var sss = str1.split(regresult, '100');
		var need = sss[sss.length - 1];
		var a = need.split('.');
			if(a[0]==null || a[0] =="" || a[0]=="undefined" || a[1]==null || a[1] =="" || a[1]=="undefined") {
				file = "没有上传文件";				
				if(isRead == false){
					file = '<input id="'+fname+'" type="file" name="'+fname+'" size="40" onkeydown="if(event.keyCode==8){this.select();}else{return false;}" />';
					var isRequest = "";
					if(fname == "wordFile"){
						isRequest += '<font color="red" size="2">必填</font>，';
					}
					file += '（'+isRequest+'文件大小不能超过50M）';
				}
			} else {
				UDload.createTempIFRAME();
				var attach = UDload.decorateAttach(need);
				file = UDload.appendDownLoad(attach,filePath);
//				file = "<a id='"+fname+"' href=\"#\"   onclick=\"openWPS('" + filePath + "')\" >" + a[0] + "." + a[1] + "</a> ";
				if(isRead == false){
					file += "<span title='删除' style='color:red;font-weight:bolder;cursor:hand;margin-left:4px;' onclick='removeAttach(this,\""+fname+"\");'>X</span>";
				}
			}
		}
	 return file;
}

function removeAttach(el,fname){
	var pnode = el.parentNode;
	var cnodes = pnode.childNodes;
	var attachNode = cnodes[0];
	pnode.removeChild(attachNode);
	pnode.removeChild(el);
	var file = '<input id="'+fname+'" type="file" name="'+fname+'" size="40" />';
	var isRequest = "";
	if(fname == "wordFile"){
		isRequest += '<font color="red" size="2">必填</font>，';
	}
	file += '（'+isRequest+'文件大小不能超过50M）';
	pnode.innerHTML = file;
}

function doUpdateNewsFilesInfo(newsFileid) {
	
	
//	var checkedFlg = false;
//	if($("radio1").checked) {
//		state = "1";
//		checkedFlg = true;
//	}
//	if($("radio2").checked) {
//		state = "2";
//		checkedFlg = true;
//	}
//	if($("radio3").checked) {
//		state = "3";
//		checkedFlg = true;
//	}
//	if($("radio4").checked) {
//		state = "4";
//		checkedFlg = true;
//	}
//	if(!checkedFlg) {
//		alert("请选择当前材料的状态！");
//		$("radio1").focus();
//		return;
//	}
	var index= -1;
	var states = document.getElementsByName("radiotype");
	for(var i=0;i<states.length;i++){
		if(states[i].checked){
			index = i;
			break;
		}
	}
	if(index==-1){
		alert("请选择当前材料的状态！");
		$("radio1").focus();
		return;
	}
	var state = states[index].value;
	
	var otherInfo = $("otherInfo").value;
	if($('otherInfo')) {
		if(!$('otherInfo').value){
//			alert("请填写备注信息！");
//			$('otherInfo').focus();
//			return;
		} else if($('otherInfo').value.length >= 500) {
			alert("请填写备注信息在500字内！");
			$('otherInfo').focus();
			return;
		}
	}
	if (!confirm("是否确认报送？")) {
		return;
	}
	var strUrl = "policeWorks.newsFiles.updateNewsFilesInfo.d";  //把参数传给后端的java
	strUrl = encodeURI(strUrl);
	var params = "newsFileid=" + newsFileid + "&otherInfo=" + otherInfo + "&state=" + state;
//	Modify by Xiayx 2011-08-14
//	添加审核人类型approver type，1：总队审核；2：部局审核
	params += "&atype=1";
//	Modify finished
	new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showUpdateResults});
} 

function showUpdateResults (xmlHttp) {
	var xmlDoc = xmlHttp.responseText;
	if(xmlDoc.indexOf('success')>-1) {
		alert("信息文件保存成功！");
		window.close();
	} else {
		alert("信息文件保存失败！");
		window.close();
	} 
}

function setAllPageValue (newsFileid) {
	var strUrl = "policeWorks.newsFiles.getNewsFileInfo.d";  //把参数传给后端的java
	strUrl = encodeURI(strUrl);
	var params = "newsFileid=" + newsFileid;
	new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showSetAllPageValueResults});
}

function showSetAllPageValueResults (xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("row")[0].getElementsByTagName("col");
	var jgid = $("cjgid").value;
	var page = $("page").value;
	$("id").value = results[0].text;
	$("title").value = results[1].text;
	$("sendTime").value = results[2].text;
	$("sendUnit").value = results[3].text;
	$("sendPeople").value = results[4].text;
	$("modifyContent").value = results[7].text;
	$("wordFileList").innerHTML = showFile(results[5].text);
	$("streamFileList").innerHTML = showFile(results[6].text);
	//Modify by Xiayx 2011-8-28
	var writerEl = $("writer");
	writerEl.value = results[12].text;
	//Modification finished
	$("otherInfo").value = results[9].text;
	var state = results[10].text;
	var type = results[11].text;
	if(jgid.substring(2,4)=="00"){
		writerEl.readOnly = true;
		if(state=="1") {
			$("radio1").checked = true;
		} else if (state=="2" || state == "4") {
			if(type == "1"){
				if(results[13].text == "1"){
					$("radio5").checked = true;
				}else{
					$("radio2").checked = true;
				}
			}else{
				$("radio4").checked = true;
			}
		} else if (state=="3") {
			$("radio3").checked = true;
		} 
	}else{
		if(state == "0"){
			$("title").readOnly = false;
			$("modifyContent").readOnly = false;
			$("wordFileList").innerHTML = showFile(results[5].text,page=="3"?false:true,"wordFile");
			$("streamFileList").innerHTML = showFile(results[6].text,page=="3"?false:true,"streamFile");
			$("bt2").style.display = "none";
			$("modify").style.display = "inline";
		}else{
			writerEl.readOnly = true;
		}
		var zdsyTr = $("zdsyTr");
		zdsyTr.parentNode.removeChild(zdsyTr);
	}
}

function modify(){
	var title = $("title");
	if(title.value == ""){
		alert("请填写标题！");
		title.focus();
		return false;
	}
	var content = $("modifyContent").value;
	if(content.value == ""){
		alert("请填写内容！");
		content.focus();
		return false;
	}
	var writer = $("writer").value;
	if(writer.value == ""){
		alert("请填写撰稿人！");
		writer.focus();
		return false;
	}
	var wordFile = $("wordFile");
	if(wordFile && wordFile.value == ""){
		alert("请选择文档文件！");
		wordFile.focus();
		return false;
	}
	var form = $("alarmFileUploadForm");
	form.submit();
}

/**
 * 显示附件信息的链接<br>
 */
function openWPS(fileName) {
	var widthv = 400;
	var heightv = 200;
	var xposition = (screen.availWidth - widthv)/2;
	var yposition = (screen.availHeight - heightv)/2;
	var feature = 'height='+heightv+',width='+widthv+',top='+yposition+',left='+xposition+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no';	
	var param= "../FileDownload.jsp?fileName="+fileName;
	param=encodeURI(param);
	window.open(param, "",feature);
}



