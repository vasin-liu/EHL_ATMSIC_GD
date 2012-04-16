
//上传下载文件
var UDload = {
	formId : "UploadForm",//表单ID
	fileId : "uploadFile",//文件ID
	fwidth : 240,//文件宽度 file width
	ofwidth : 80,//添加文件按钮/删除文件按钮宽度 operate file width
	bwidth : 5,//文件和添加文件/删除文件按钮之间的距离 between width
	height : 20,//高度，包括文件高度、添加文件按钮高度、删除文件按钮高度
	maxnum : 5,//最大文件数
	curnum : 0,//当前文件数
	fonum : 0,//文件序号 file order number
	getFile : function(){
		var style = 'height:'+UDload.height+'px;width:'+UDload.fwidth+'px;margin-right:'+UDload.bwidth+'px';
		var onkd = "if(event.keyCode==8){this.select();}else{return false;}";
		return '<input type="file" id="'+UDload.fileId+(++UDload.fonum)+'" name="'+UDload.fileId+'" style="'+style+'" onkeydown="'+onkd+'" oncontextmenu="return false;" />';
	},
	getBtn : function(type){
		var ids = ["add","delete"];
		var fnames = ["add","delete"];
		var values = ["添加","删除"];
		return '<input type="button" id="'+ids[type]+'File" style="height: '+UDload.height+'px;width:'+UDload.ofwidth+'px;" value="'+values[type]+'附件" onclick="UDload.'+fnames[type]+'File(this)"/>';
	},
	getLine : function(type){
		return UDload.getFile() + UDload.getBtn(type);
	},
	getForm : function(width){
		if(width){
			UDload.fwidth = width - UDload.bwidth ;
			if(UDload.maxnum != 1){
				UDload.fwidth -= UDload.ofwidth;
			}
		}
		var form  = '<form id="'+UDload.formId+'" action="dispatch.fileUpDownLoad.upload.d" encType="multipart/form-data"  method="post" target="uploadFrame" style="padding:0px;margin:0px;">';
			form += '<table cellspace="0px" cellpadding="0px"><tbody><tr><td>'+(UDload.maxnum == 1 ? UDload.getFile() : UDload.getLine(0))+'</td></tr></tbody></table>';
		 	form += '<iframe id="uploadFrame" name="uploadFrame" style="display:none"></iframe>';
			form += '</form>';
		UDload.curnum++;
		return form;
	},
	addFile : function(afel) {
		if (afel) {
			if (UDload.curnum < UDload.maxnum) {
				var line = UDload.getLine(1);
				var td = document.createElement("td");
				var tr = document.createElement("tr");
				var tbody = afel.parentNode.parentNode.parentNode;
				tbody.appendChild(tr);
				tr.appendChild(td);
				td.innerHTML = line;
				UDload.curnum++;
			}
		}
	},
	deleteFile : function(dfel){
		if(dfel){
			var tr = dfel.parentNode.parentNode;
			var tbody = tr.parentNode;
			tbody.removeChild(tr);
			UDload.curnum--;
		}
	},
	isSelectFile : function(){
		var form = $(UDload.formId);
		if(form){
			var els = form.elements;
			for ( var i = 0; i < els.length; i++) {
				if(els[i].type == "file" && els[i].value != ""){
					return true;
				}
			}
		}
		return false;
	},
	getFileSize : function(filePath){
		var fileSize;
	   var image = new Image();
	   try{
		   image.dynsrc=filePath;
		   fileSize = image.fileSize;
	   }catch(e){
		   fileSize = 0;
	   }
	   return fileSize;  
	},
	checkSize : function(){
		var maxSize = 10 * 1024 * 1024;
		var formEl = $(UDload.formId);
		var fels = formEl.elements;
		var size;
		for ( var i = 0; i < fels.length; i ++) {
			if(fels[i].type != "file"){
				continue;
			}
			size = UDload.getFileSize(fels[i].value);
			if( size >= maxSize){
				alert("单个附件不超过10兆！");
				fels[i].focus();
				return false;
			}
		}
		return true;
	},
	upload : function(){
		if(UDload.isSelectFile()){
			if(UDload.checkSize()){
				$(UDload.formId).submit();
			}
		}else{
			UDload.callBack("true");
		}
	},
	cbfname : null,
	result : null,
	callBack : function(result) {
		if (result == "true") {
			if (UDload.cbfname) {
				var type = typeof UDload.cbfname;
				if(type == "string"){
					eval(UDload.cbfname);
				}else if(type == "function"){
					UDload.cbfname();
				}
			}
		} else if (result == "false") {
			alert(UDload.result);
		}
	},
	addParam : function(fname,param){
		if(fname && param){
			var rbl = "(",rbr = ")";
			var index = fname.indexOf(rbr);
			if(index == -1){
				fname += rbl + param + rbr;
			}else{
				fname = fname.substring(0,index) + "," + param + rbr;
			}
		}
		return fname;
	},
	download : function(fname){
		if(fname){
			var url = "dispatch.fileUpDownLoad.download.d";
			new Ajax.Request(url,{
				method:"post",
				parameters : {
					fileName : fname,
					isOpen : false
				},
				onComplete:function(xmlHttp){
					var result = xmlHttp.responseText;
					if(result!="null"){
						alert(result);
					}
				}
			});
		}
	},
	//附件下载
	apaths : [],//多个附件路径
	rapaths :[],//被删除的附件路径
	astates : ["download"],//附件状态。只读、下载、删除、添加
	astyle : "inline",//附件显示方式。attach style :inline(多个附件在一行显示)|block(一个附件占一行)
	adstyle : "simple",//附件删除方式。attach delete style :simple简单方式(附件名称可下载，附件后有X可以删除)|clear清楚方式(在附件一行的末端显示下载和删除的按钮)
	target : "../FileDownload.jsp",//下载链接页面
	//分解附件路径
	resolveApaths : function(apath){
		UDload.apaths = apath.replace(/\\/g, "/").split(",");
	},
	//获取文件后缀
	getFileSubfix : function(apath){
		var index = apath.lastIndexOf(".");
		return apath.substring(index+1);
	},
	//获取附件名称，通过附件路径
	getAttachName:function(apath){
		if(apath){
			var index = apath.lastIndexOf("/");
			if(index != -1){
				return apath.substring(index+1);
			}
		}
		return apath;
	},
	//装饰附件
	decorateAttach : function(aname){
		return "<a title='"+aname+"' target='"+UDload.TEMP_IFRAME_ID+"' style='cursor:hand;text-decoration:underline;margin-left:10px;display:inline;float:left;' >"+aname+"</a>";
	},
	//下载请求链接
	DOWNLOAD_URL: "dispatch.fileUpDownLoad.download.updown",
	//为附件添加下载功能
	appendDownLoad : function(attach,apath){
		var href = UDload.DOWNLOAD_URL + "?fileName="+apath+"&isOpen=false";
		href = " href='"+encodeURI(href)+"' ";
		attach = attach.substring(0,2) + href + attach.substring(2);
		return attach;
	},
	//下载附件，该方法
//	attachDownLoad : function(fileName){return;
//		var url = UDload.DOWNLOAD_URL;
//		var params = {fileName:fileName,isOpen:false};
//		Submit.urlToForm(url,params,UDload.TEMP_IFRAME_ID);
//	},
	//获取附件删除功能
	getDelete : function(apath){
		switch(UDload.adstyle){
		case "simple":
			return "<span title='删除' apath='"+apath+"' style='color:red;font-weight:bolder;cursor:hand;margin-left:5;' onclick='UDload.removeAttach(this.parentNode,this.apath);'>X</span>";
		break;
		case "clear":
//			var onclick = 'UDload.attachDownLoad("'+apath+'")';
			return "<div style='float:right;display:inline;'>"
//				+"<input type='button' value='下载' onclick='"+onclick+"' />"
				+"<input type='button' value='删除' style='width:40px;' onclick='UDload.removeAttach(this.parentNode.parentNode,this.apath);' apath='"+apath+"' />"
				+"</div>";
		break;
		}
	},
	//为附件添加删除功能
	appendDelete : function(attach, apath){
		return attach + UDload.getDelete(apath);
	},
	//删除附件
	removeAttach:function(el,apath){
		var pnode = el.parentNode;
		pnode.removeChild(el);
		UDload.rapaths.push(apath);
		UDload.curnum--;
		if(!$(UDload.formId) && UDload.curnum < UDload.maxnum){
			pnode.innerHTML = pnode.innerHTML + UDload.getForm();
		}
	},
	//为附件添加添加附件功能
	appendAdd:function(attach,width){
		return attach + UDload.getForm(width);
	},
	//为附件设置容器
	setContainer:function(attach){
		var container = "<div style='display:block;";
		if(UDload.astyle == "block"){
			container += "width:"+(UDload.fwidth+(UDload.maxnum==1?0:UDload.ofwidth))+";";
		}else if(UDload.astyle == "inline"){
			container += "overflow:visible;";
		}
		return container + "'>"+attach+"&nbsp;</div>";//此处&nbsp;不可省略，否则UDload.astyle == "inline"时，附件不在div容器内，原因不明。
	},
	//获取附件HTML
	getAttachHtml : function(apath){
		var attach;
		if(apath){
			var aname = UDload.getAttachName(apath);
			attach = UDload.decorateAttach(aname);
			if(UDload.astates.getIndex("download")!=-1){
				attach = UDload.appendDownLoad(attach,apath);
			}
			if(UDload.astates.getIndex("delete")!=-1){
				attach = UDload.appendDelete(attach,apath);
			}
			attach = UDload.setContainer(attach);
			UDload.curnum++;
		}
		return attach;
	},
	//获取多个附件HTML
	getAttachHtmls : function(apath, isRead, target) {
		//设置成员变量
		if(apath){
			UDload.resolveApaths(apath);
		}
		UDload.astates.push(isRead?"read":"write");
		if(target){
			UDload.target = target;
		}
		UDload.createTempIFRAME();
		//获取HTML
		var attachs = "";
		for ( var i = 0; i < UDload.apaths.length; i++) {
			attachs += UDload.getAttachHtml(UDload.apaths[i]) || "";
		}
		if(UDload.astates.getIndex("add")!=-1 && UDload.maxnum > UDload.curnum){
			attachs = UDload.appendAdd(attachs);
		}
		return attachs;
	},
	// 上传路径，由当前附件路径apaths、移出附件路径rapaths、新添加附件路径result组合而成
	getUpapath : function() {
		var upapaths = [];
		if (UDload.apaths.length > 0) {
			upapaths = UDload.apaths.slice(0);
			var apath, index;
			for ( var i = 0; i < UDload.rapaths.length; i++) {
				apath = UDload.rapaths[i];
				index = upapaths.getIndex(apath);
				upapaths.splice(index, 1);
			}
		}
		if (UDload.result) {
			upapaths.push.apply(upapaths, UDload.result);
		}
		return upapaths.join(",");
	},
	//设置下载模式
	setDownloadModel : function(model,width,num){
		UDload.fwidth = width||550;
		UDload.maxnum = num||2;
		UDload.astates.push("delete","add");
		if(model == "clear"){
			UDload.astyle = "block";
			UDload.adstyle = "clear";
		}else if(model == "simple"){
			UDload.astyle = "inline";
			UDload.adstyle = "simple";
		}
	},
	//创建临时隐藏框架
	//通过文档追加创建的框架，无法使用，需要用innerHTML方式。
	TEMP_IFRAME_CONTAINER_ID : "temp_iframe_container_id",
	TEMP_IFRAME_ID : "temp_iframe_id",
	createTempIFRAME : function(){
		var ctnid = UDload.TEMP_IFRAME_CONTAINER_ID;
		var ctn = $(ctnid);
		if(!ctn){
			ctn = document.createElement("div");
			ctn.style.display = "none";
			document.body.appendChild(ctn);
		}
		var id= UDload.TEMP_IFRAME_ID;
		ctn.innerHTML = '<iframe id="'+id+'" name="'+id+'" style="display:none;"></iframe>';
	},
	showError : function(){
		alert("下载文件名无效！");
	}
};





Array.prototype.getIndex = function(object) {
	var length = this.length;
	for ( var i = 0; i < length; i++) {
		if (this[i] == object) {
			return i;
		}
	}
	return -1;
};