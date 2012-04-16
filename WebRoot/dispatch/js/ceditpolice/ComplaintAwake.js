var page=null;
function initAwake(){	
	haveAwake();
	var time=30;
	clearInterval(time); 
	time=setInterval("haveAwake()",30000);
}


function haveAwake(){
	var url = "complaint.complaintawake.dealAwake.d"
	url = encodeURI(url);	
	params="";
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:haveAwakeRes})	
}
function haveAwakeRes(xmlHttp){
	var textDoc = xmlHttp.responseText;
	if(textDoc=="true"){
		var widthv = 720;
		var heightv = 300;
		var xposition = (screen.availWidth - widthv)/2;
		var yposition = (screen.availHeight - heightv)/2;
		var feature = 'height='+heightv+',width='+widthv+',top='+yposition+',left='+xposition+',toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no';	
		if(page==null){
			page=window.open('complaintAwake.jsp',"",feature);	
		}else{
			page.close();
			page=window.open('complaintAwake.jsp',"",feature);	
		}	
	}
}

//提醒页面展示需处理投诉列表
function dealAwakeList(){
	var url = "complaint.complaintawake.dealAwakeList.d"
	url = encodeURI(url);	
	params="";
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:dealAwakeListRes})	
}	
function dealAwakeListRes(xmlHttp){
	var xmlDoc = xmlHttp.responseXML;
	var rows = xmlDoc.getElementsByTagName("row");
	if(rows!=null&&rows.length>0){
		for(var i=0;i<rows.length;i++){
			var results = rows[i].childNodes;
			var cpid=results[0].text;
			var state=results[8].text;
			var tr=document.createElement("tr");
			tr.id="tr_"+i;
			var checktd=document.createElement("td");				
			checktd.innerHTML="<input type='checkbox' value='"+cpid+"' state='"+state+"'  id='row_"+i+"' name='complaintchk'>";
			tr.appendChild(checktd);
			tr.onclick=function(){		
				var chktrStr="row_"+this.id.split("_")[1];
				linkDealPage(chktrStr);
				window.close();
			}
			if(i%2!=0){
				checktd.className="alt";
			}	
			for(var j=1;j<results.length-1;j++){
				var td=document.createElement("td");
				td.className="";									
				td.innerText=results[j].text;
				tr.appendChild(td);
				if(i%2!=0){
					td.className="alt";		
				}
			}
			document.getElementById("awaketbody").appendChild(tr);
		}		
	}
}

function linkDealPage(chkId){
	var chks=document.getElementsByName("complaintchk");	
	if(chks!=null&&chks.length>0){
		for(var i=0;i<chks.length;i++){
			if(chks[i].id==chkId){
				chks[i].checked=true;
				var cpid=chks[i].value;
				var state=chks[i].state;
				openDealPage(cpid,state);
			}else{
				chks[i].checked=false;
			}
		}
	}
}	

function openDealPage(itemValue1,itemValue2){
	window.open("complaintEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&cpid=" + itemValue1 + "&lzzt=" + itemValue2 + "", "", "width=850px,height=700px")
//	window.showModalDialog("complaintEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&cpid=" + itemValue1 + "&lzzt=" + itemValue2 + "", "", "dialogWidth:850px;dialogHeight:700px");

}

//统计需处理的信息数量
function dealListCount(){
	var url = "complaint.complaintawake.dealListCount.d"
	url = encodeURI(url);	
	params="";
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:dealListCountRes})	
}
function dealListCountRes(xmlHttp){
	var textDoc = xmlHttp.responseText;
	document.getElementById('prompt_box_2').innerHTML = "";
	var str="";
	if(textDoc!="0"){
		str = '<li><a href="#" onclick="">'+ '需处理投诉：[' + textDoc + ']</a></li>';
	}
	document.getElementById('prompt_box_2').innerHTML = str;
}
		