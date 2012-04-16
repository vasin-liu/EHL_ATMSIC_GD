var privatepersonidary=new Array();


function initDispose(){
	watching.setRowClickHandler(clickPoliceRow);	
	var btns=document.getElementsByName("optbtn");
	for(var i=0;i<btns.length;i++){
		btns[i].disabled=true;
	}
	$("fklist").innerHTML="<div  style='border:1px solid #CCCCCC;width:100%;height:115px;overflow:scroll; '></div>";;
}	
	
function disposeTypeLevel(newdisposealarmType,levelvalue,thintypevalue){	
	if(isAccident(newdisposealarmType)){
		fillListBox("disposealarmLevelTd","disposealarmLevel","161","select id,name from t_attemper_code where id like '006%'","请选择","levelValue('"+levelvalue+"','"+thintypevalue+"')");
		fillListBox("disposeThinTypeTd","disposeThinType","161","select id,name from t_attemper_code where id like '007%'","请选择","levelValue('"+levelvalue+"','"+thintypevalue+"')");
	}
	else{
		fillListBox("disposealarmLevelTd","disposealarmLevel","161","select id,name from t_attemper_code where id like '011%'","请选择","levelValue('"+levelvalue+"')");
	}			
}	
	
function levelValue(levlevalue,thintypevalue){
	$("disposealarmLevel").value= levlevalue;
	$("disposeThinType").value= thintypevalue;
//	$("disposeThinType").disabled=true;
}	
	
function clickPoliceRow(alarmid){
	$("divAlarmInfo").style.display="none";
	$("divEditAlarm").style.display="none";
	$("divFeedBack").style.display="";
	
	$("pjbutton").onclick=function(){
		assignPolice(alarmid);
	}
	$("cjbutton").onclick=function(){
		outPolice(alarmid);
	}	
	$("ddbutton").onclick=function(){
		arrivePolice(alarmid);
	}
	$("fkbutton").onclick=function(){
		feedbackPolice(alarmid);
	}
	$("clbutton").onclick=function(){
		finishPolice(alarmid)
	}
	$("hfbutton").onclick=function(){
		trafficrevertPolice(alarmid)
	}
	
	$("tcddbutton").onclick=function(){
		exitPolice(alarmid)
	}
	
	getPoliceBaseInfo(alarmid)
//	$("btnAffairProcess").onclick=watching.btnClickUIsetting;
	var btnAffairProcess=$("btnAffairProcess");
	btnAffairProcess.style.border ="1 black";
 	btnAffairProcess.style.borderStyle = "solid";
 	btnAffairProcess.style.backgroundColor = "#9999AA";   
 	btnAffairProcess.style.color = "blue";
 	btnAffairProcess.style.fontSize = "9pt";
 	
 	$("sg").onclick=function(){
// 		getAccidentPerson(privatepersonidary);
 		openduty("accident");
 	
 	}
 	$("gq").onclick=function(){
// 		getDutyPersonList(privatepersonidary);
 		openduty("duty");
 	}
 	$("cl").onclick=function(){
// 		getDutyPersonList(privatepersonidary);
 		openduty("car");
 	}
 	var btns=document.getElementsByName("optbtn");
	for(var i=0;i<btns.length;i++){
		btns[i].disabled=false;
	}
	$("disposeDesc").value="";
	$("zydy").value=""
	$("tydy").value=""
	
}	


//得到警情信息	
function getPoliceBaseInfo(alarmid){
	var url = "dispatch.dispose.getPoliceBaseInfos.d";	
	var params="alarmId="+alarmid+"&&attemper="+pname+"&&attemperid="+pid+"&&mobilephone="+mobilephone; 
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getPoliceBaseInfoRes});
}
function getPoliceBaseInfoRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("col");	
	
	$("disposealarmTime").value= res[1].text;
	$("disposealarmSite").value= res[2].text;
	$("disposealarmType").value= res[3].text;
	
//	$("disposealarmLevel").value = res[4].text;
	$("disposealarmDesc").value= res[5].text;
	$("disposealarmType").disabled=true;
	
	$("pjtime").value= res[6].text;
	$("cjtime").value= res[7].text;
	$("ddtime").value= res[8].text;
	$("fktime").value= res[15].text;
	$("cltime").value= res[9].text;
	$("hftime").value= res[10].text;
	
	$("disposessrs").value= res[17].text;
	$("disposeswrs").value= res[18].text;
	
	$("weatherinfo").value= res[11].text;
	$("disposeRoadType").value= res[13].text;
	$("disposeResult").value= res[14].text;
	
	if($("disposeRoadType").value!=null&&$("disposeRoadType").value!=""){		
	}else{		
		$("disposeRoadType").value= "212008"
	}
	if($("disposeResult").value!=null&&$("disposeResult").value!=""){
	}else{		
		$("disposeResult").value= "213003"
	}
	if(res[19]!=null&&res[19].text!=null&&res[19].text!=""){
		$("alarmperson").value= res[19].text;
	}else{
		$("alarmperson").value= "";
	}
	
//	$("disposeThinType").value= res[16].text;
	disposeTypeLevel(res[3].text,res[4].text,res[12].text)
	
	var htmlStr="<div  style='border:1px solid #CCCCCC;width:100%;height:115px;overflow:scroll; '>";
	var desc=res[16].text.split("*NextRow*");	
	for(var i=0;i<desc.length;i++){	
		if(desc[i].split("\n").length>1){
			var temphtml="";
			for(var n=0;n<desc[i].split("\n").length-1;n++){
				if(n!=desc[i].split("\n").length-2){
					temphtml+=desc[i].split("\n")[n]+"<br>";
				}else{
					temphtml+=desc[i].split("\n")[n];
				}
			}
			desc[i]=temphtml;
		}
		htmlStr+=desc[i]+"<br>"	;	
	}
	htmlStr+="</div>";
	
	$("fklist").innerHTML=htmlStr;
	
	var personres=xmlDoc.getElementsByTagName("person");
//	var personidary=new Array();
//	var personinameary=new Array();
	var person=new Array();
	var personidstr="";
	var personnamestr="";
	var personstr="";
	for(var i=0;i<personres.length;i++){
//		personidary.push(personres[i].getAttribute("pid"));
//		personinameary.push(personres[i].getAttribute("pname"));
		personidstr+=personres[i].getAttribute("pid")+",";		
		personnamestr+=personres[i].getAttribute("pname")+",";
		perstatestr=personres[i].getAttribute("pstate");
		personstr+=personres[i].getAttribute("pname")+"("+perstatestr+")"+",";
	}
	person.push(personidstr);
	person.push(personnamestr);
	person.push(personstr);
	showChkPerson(person,true);
	var carres=xmlDoc.getElementsByTagName("car");
//	var caridary=new Array();
//	var carnumary=new Array();
	var car=new Array();
	var caridstr="";
	var carnumstr="";
	var carstate="";
	for(var i=0;i<carres.length;i++){
		caridstr+=carres[i].getAttribute("cid")+","
		carnumstr+=carres[i].getAttribute("cnum")+",";		
		carstate+=carres[i].getAttribute("cstate")+",";	
	}
	car.push(caridstr);
	car.push(carnumstr);
	car.push(carstate);
	showChkCar(car,true);
	var desc=xmlDoc.getElementsByTagName("desc");
	var desctxt=desc[0].text;
//	$("fklist").innerHTML=desctxt;
//	privatepersonidary=personidary;
//	privatecaridary=caridary;
//	getAccidentPerson(personidary);
//	$("sg").checked=true;
//	getDutyCar(caridary);
	watching.readNoticedAffair();
	
}	

//事故组
function getAccidentPerson(personidary){
	
	var url = "dispatch.dispose.getAccidentPerson.d";		
	var params="deptId="+deptcode;
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function (xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;		 
	var res=xmlDoc.getElementsByTagName("dutypost");
	var html="";
	if(res!=null&&res.length>0){
		for(var i=0;i<res.length;i++){
			var post=res[i].getElementsByTagName("row")[0].text;			
			html+="<input type='checkbox' id='post_"+i+"' name='postName' chkid='"+i+"' onclick='checkGroup(\""+i+"\")'>"+post+"<br><div style='padding-left:15px;'>";
			var personStr=res[i].getElementsByTagName("row")[1].text;
			var personList=personStr.split(",");
			for(var j=0;j<personList.length;j++){
				var personName=personList[j].split("|")[0];
				var personId=personList[j].split("|")[1];
				var personstate=personList[j].split("|")[2];
				if(personidary!=null&&personidary.length>0){
					var tag=true;
					for(var n=0;n<personidary.length;n++){
						if(personId==personidary[n]){
							if(personstate=="1"){		
								html+="<input type='checkbox' name='personxm' checked value='"+personId+"' xm='"+personName+"' chkid='"+i+"_chk'><img src='../../images/alarm/dutyPersonbusyS.jpg'>"+personName+"<br>";
							}else{
								html+="<input type='checkbox' name='personxm' checked value='"+personId+"' xm='"+personName+"' chkid='"+i+"_chk'><img src='../../images/alarm/dutyPersonfreeS.jpg'>"+personName+"<br>";
							}
							//html+="<input type='checkbox' checked name='personxm' value='"+personId+"' xm='"+personName+"' chkid='"+i+"_chk'>"+personName+"<br>";
							tag=false;
						}
					}
					if(tag){
						if(personstate=="1"){		
							html+="<input type='checkbox' name='personxm' value='"+personId+"' xm='"+personName+"' chkid='"+i+"_chk'><img src='../../images/alarm/dutyPersonbusyS.jpg'>"+personName+"<br>";
						}else{
							html+="<input type='checkbox' name='personxm' value='"+personId+"' xm='"+personName+"' chkid='"+i+"_chk'><img src='../../images/alarm/dutyPersonfreeS.jpg'>"+personName+"<br>";
						}
//						html+="<input type='checkbox' name='personxm' value='"+personId+"' xm='"+personName+"' chkid='"+i+"_chk'>"+personName+"<br>";
					}
				}else{			
					if(personstate=="1"){		
						html+="<input type='checkbox' name='personxm' value='"+personId+"' xm='"+personName+"' chkid='"+i+"_chk'><img src='../../images/alarm/dutyPersonbusyS.jpg'>"+personName+"<br>";
					}else{
						html+="<input type='checkbox' name='personxm' value='"+personId+"' xm='"+personName+"' chkid='"+i+"_chk'><img src='../../images/alarm/dutyPersonfreeS.jpg'>"+personName+"<br>";
					}
				}
				
			}
			html+="</div>"		
		}
	}	
	$("infolist").innerHTML=html;
}	
});
}


//勤务人员列表
function getDutyPersonList(personidary){
	
//	alert(personidary.length)
	var url = "dispatch.dispose.getDutyPerson.d";	
	
	var params="deptId="+deptcode;
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function (xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;		
	var html="";
	var res=xmlDoc.getElementsByTagName("dept");
	if(res!=null&&res.length>0){
		for(var i=0;i<res.length;i++){
			var dept=res[i].getElementsByTagName("row")[0].text;			
			html+="<div style='padding-left:5px' id='' >"+"<span ondblclick=Expand(\"person_+"+i+"\");><img id=\"img_person_+"+i+"\" src=\"../../images/tree/minusClosed.gif\" /'>"+dept+"</span>";
			html+="<div style='padding-left:12px' id='person_+"+i+"'><table>";
			var persons=res[i].getElementsByTagName("row")	
			for(var j=1;j<persons.length;j++){
				var person=persons[j].text;
				var personid=person.split("|")[1];
				var personxm=person.split("|")[0];
				var personstate=person.split("|")[2];
				var tag=true;				
				if(personidary!=null&&personidary!=""&&personidary.length>0){
					for(var n=0;n<personidary.length;n++){					
						if(personidary[n]==personid){
							tag=false;
							break;
						}
					}
				}
				if(tag){
					html+="<tr>";
					html+="<td>";
					if(personstate=="1"){
						html+="<input type='checkbox' value='"+personid+"'  xm='"+personxm+"' name='personxm'><img src='../../images/alarm/dutyPersonbusyS.jpg'>"
					}else{
						html+="<input type='checkbox' value='"+personid+"'  xm='"+personxm+"' name='personxm'><img src='../../images/alarm/dutyPersonfreeS.jpg'>"
					}
					html+="</td>";
					html+="<td>";
					html+=personxm;
					html+="</td>";
					html+="</tr>";
				}else{
					html+="<tr>";
					html+="<td>";
					if(personstate=="1"){
						html+="<input type='checkbox' checked value='"+personid+"'  xm='"+personxm+"' name='personxm'><img src='../../images/alarm/dutyPersonbusyS.jpg'>"
					}else{
						html+="<input type='checkbox' checked value='"+personid+"'  xm='"+personxm+"' name='personxm'><img src='../../images/alarm/dutyPersonfreeS.jpg'>"
					}
					html+="</td>";
					html+="<td>";
					html+=personxm;
					html+="</td>";
					html+="</tr>";
				}		
			}
			html+="</table></div>";
			html+="</div>"
		}
	}
	
	
	
//	var res=xmlDoc.getElementsByTagName("dutypost");
//	html+="<table>"
//	if(res!=null&&res.length>0){
//		for(var i=0;i<res.length;i++){			
//			
//			var person=res[i].text.split(",");	
//			
//			
//			for(var n=0;n<person.length;n++){
//				var personxm=person[n].split("|")[0];	
//				var personid=person[n].split("|")[1];	
//				var tag=true;
//				if(personxm!=null&&personxm!=""){
//					if(personidary!=null&&personidary!=""){
//						for(var j=0;j<personidary.length;j++){					
//							if(personidary[j]==personid){
//								tag=false;
//								break;
//							}
//						}
//					}
//					
//					if(tag){
//						html+="<tr>";
//						html+="<td>";
//						html+="<input type='checkbox' value='"+personid+"'  xm='"+personxm+"' name='personxm'>"
//						html+="</td>";
//						html+="<td>";
//						html+=personxm;
//						html+="</td>";
//						html+="</tr>";
//					}else{
//						html+="<tr>";
//						html+="<td>";
//						html+="<input type='checkbox' value='"+personid+"' checked xm='"+personxm+"' name='personxm'>"
//						html+="</td>";
//						html+="<td>";
//						html+=personxm;
//						html+="</td>";
//						html+="</tr>";
//					}
//				}
//			}
//		}	
//	}
//	html+="</table>"
	$("infolist").innerHTML=html;
}	
});
}

//PCS人员列表
function getPcsPersonList(personidary){

	var url = "dispatch.dispose.getPcsPerson.d";	
	
	var params="deptId="+deptcode;
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function (xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;		
	var html="";
	var res=xmlDoc.getElementsByTagName("dept");
	if(res!=null&&res.length>0){
		for(var i=0;i<res.length;i++){
			var dept=res[i].getElementsByTagName("row")[0].text;			
			html+="<div style='padding-left:5px' id='' >"+"<span ondblclick=Expand(\"person_+"+i+"\");><img id=\"img_person_+"+i+"\" src=\"../../images/tree/minusClosed.gif\"/'>"+dept+"</span>";
			html+="<div style='padding-left:12px' id='person_+"+i+"'><table>";
			var persons=res[i].getElementsByTagName("row")	
			for(var j=1;j<persons.length;j++){
				var person=persons[j].text;
				var personid=person.split("|")[1];
				var personxm=person.split("|")[0];
//				var personstate=person.split("|")[2];
				var tag=true;				
				if(personidary!=null&&personidary!=""&&personidary.length>0){
					for(var n=0;n<personidary.length;n++){					
						if(personidary[n]==personid){
							tag=false;
							break;
						}
					}
				}
				if(tag){
					html+="<tr>";
					html+="<td>";
					html+="<input type='checkbox' value='"+personid+"'  xm='"+personxm+"' name='personxm'>"					
					html+="</td>";
					html+="<td>";
					html+=personxm;
					html+="</td>";
					html+="</tr>";
				}else{
					html+="<tr>";
					html+="<td>";
					html+="<input type='checkbox' checked value='"+personid+"'  xm='"+personxm+"' name='personxm'>"			
					html+="</td>";
					html+="<td>";
					html+=personxm;
					html+="</td>";
					html+="</tr>";
				}		
			}
			html+="</table></div>";
			html+="</div>"
		}
	}

	$("infolist").innerHTML=html;
}	
});
}

//车辆列表
function getDutyCar(caridary){
	
//	alert(personidary.length)
	var url = "dispatch.dispose.getDutyCar.d";	
	
	var params="deptId="+deptcode;
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function (xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("dutypost");
	var html="";
	html+="<table>"
	if(res!=null&&res.length>0){
		for(var i=0;i<res.length;i++){			
			var tag=true;
			var car=res[i].text.split("|")[0];	
			var carid=res[i].text.split("|")[1];	
			var carstate=res[i].text.split("|")[2];	
			if(car!=null&&car!=""){
				if(caridary!=null&&caridary.length>0){
					for(var j=0;j<caridary.length;j++){					
						if(caridary[j]==carid){
							tag=false;
							break;
						}
					}
				}
				var imghtml="";
				if(carstate=="1"){
					imghtml="<img src='../../images/alarm/dutyCarbusyS.ico'>";
				}else{
					imghtml="<img src='../../images/alarm/dutyCarfreeS.ico'>";
				}
				if(tag){
					html+="<tr>";
					html+="<td>";
					html+="<input type='checkbox' value='"+carid+"'  num='"+car+"' name='chkcar'>"+imghtml;
					html+="</td>";
					html+="<td>";
					html+=car;
					html+="</td>";
					html+="</tr>";
				}else{
					html+="<tr>";
					html+="<td>";
					html+="<input type='checkbox' value='"+carid+"' checked num='"+car+"' name='chkcar'>"+imghtml;
					html+="</td>";
					html+="<td>";
					html+=car;
					html+="</td>";
					html+="</tr>";
				}
			}
		}	
	}
	html+="</table>"
	$("infolist").innerHTML=html;
}	
});
}

//事故组选择
function checkGroup(chkid){
	var personArray=new Array();
	var postchk=$("post_"+chkid);
	var personList=document.getElementsByName("personxm");
	for(var i=0;i<personList.length;i++){
		var chk=personList[i];
		if(chk.chkid==(chkid+"_chk")){
			if(postchk.checked==true){			
				chk.checked=true;
			}else{
				chk.checked=false;
			}
		}
	}
}


function checkedcar(){
	var carArray=new Array();
	var carList=document.getElementsByName("chkcar");
	for(var i=0;i<carList.length;i++){
		var chk=carList[i];
		if(chk.checked){			
			carArray.push(chk)
		}
	}
	return carArray;
}


function checkedperson(){
	var personArray=new Array();
	var personList=document.getElementsByName("personxm");
	for(var i=0;i<personList.length;i++){
		var chk=personList[i];
		if(chk.checked){			
			personArray.push(chk)
		}
	}
	return personArray;
}

//打开子页面
function openduty(flag){	
//	var page=window.open("../policeDispose/dutyCheck.jsp?flag="+flag,"","width=468px,height=468px");	
	var sonArray=window.showModalDialog("../policeDispose/dutyCheck.jsp?flag="+flag+"&&tmp=" + Math.random(),"","dialogWidth:210px;dialogHeight:430px")
	if(flag=="car"){
		showChkCar(sonArray);
	}else{
		showChkPerson(sonArray);
	}	
}

function showChkPerson(personArray,tag){
	if(tag){
		$("personlist").innerHTML="";
	}
	if(personArray!=null&&personArray.length>0){
		var html="";
		html+="<table>"
		var personIds=personArray[0];
		var personNames=personArray[1];
		var personstrs=personArray[2];
		var pids=personIds.split(",");
		var pnames=personNames.split(",");
		var pstrs=personstrs.split(",");
		for(var i=0;i<pids.length-1;i++){			
			html+="<tr>";
			html+="<td>";
			html+="<input type='checkbox' checked value='"+pids[i]+"'  xm='"+pnames[i]+"' name='personxm'>";
			html+="</td>";
			html+="<td>";
			html+=pstrs[i];
			html+="</td>";
			html+="</tr>";
		}
		html+="</table>"
		$("personlist").innerHTML=($("personlist").innerHTML+html);
	}
}

function showChkCar(carArray,tag){
	if(tag){
		$("carlist").innerHTML="";
	}
	if(carArray!=null&&carArray.length>0){
		var html="";
		html+="<table>"
		var carIds=carArray[0];
		var carNums=carArray[1];
		var carstrs=carArray[2];
		var cids=carIds.split(",");
		var cnums=carNums.split(",");
		var cstrs=carstrs.split(",");
		for(var i=0;i<cids.length-1;i++){			
			html+="<tr>";
			html+="<td>";
			html+="<input type='checkbox' checked value='"+cids[i]+"' num='"+cnums[i]+"' name='chkcar'>"
			html+="</td>";
			html+="<td>";
			html+=cstrs[i];
			html+="</td>";
			html+="</tr>";
		}
		html+="</table>"
		$("carlist").innerHTML=html;
//		alert(personNames)
	}
}

function initPersons(flag){
	if(flag=="accident"){
		$("textdiv").innerHTML="事故人员："
		$("checkdiv").style.display="none";
		getAccidentPerson();
	}else if(flag=="duty"){
		$("textdiv").innerHTML="执勤人员："
		getDutyPersonList();
		$("rychk").onclick=rychkclick;
	}else if(flag=="car"){
		$("textdiv").innerHTML="执勤车辆："
		$("checkdiv").style.display="none";
		getDutyCar()
	}
}

function rychkclick(){
	if($("rychk").checked){
		$("infolist").innerHTML="";
		getPcsPersonList();
	}else{
		$("infolist").innerHTML="";
		getDutyPersonList();
	}
}

function checkPersonOk(){
	var personIds="";
	var personNames="";
	var personArray=checkedperson();
	if(personArray!=null&&personArray.length>0){
		for(var i=0;i<personArray.length;i++){
			personIds+=personArray[i].value+",";
			personNames+=personArray[i].xm+",";
		}
	}
	var personTempAry=new Array();
	personTempAry.push(personIds);
	personTempAry.push(personNames);
	personTempAry.push(personNames);
	window.returnValue = personTempAry ;
	window.close();
}
function checkCarOk(){
	var carIds="";
	var carNum="";
	var carArray=checkedcar();
	if(carArray!=null&&carArray.length>0){
		for(var i=0;i<carArray.length;i++){
			carIds+=carArray[i].value+",";
			carNum+=carArray[i].num+",";
		}
	}
	var carTempAry=new Array();
	carTempAry.push(carIds);
	carTempAry.push(carNum);
	carTempAry.push(carNum);
	window.returnValue = carTempAry ;	
	window.close();
}

function checkedOk(flag){
	if(flag=="car"){
		checkCarOk();
	}else{
		checkPersonOk();
	}
}

function getCheckPerson(){
	var personArray=new Array();
	var personList=document.getElementsByName("personxm");
	for(var i=0;i<personList.length;i++){
		var chk=personList[i];			
		personArray.push(chk);		
	}
	return personArray;
}

function getCheckCar(){
	var carArray=new Array();
	var carList=document.getElementsByName("chkcar");
	for(var i=0;i<carList.length;i++){
		var chk=carList[i];	
		carArray.push(chk);		
	}
	return carArray;
}


function finishChkPerson(){
	var personAry=new Array();
	var personsId="";
	var personsName="";
	var persons=document.getElementsByName("personxm");
	for(var i=0;i<persons.length;i++){
		if(persons[i].checked==true){
			personAry.push(persons[i]);
			personsId+=persons[i].value+",";
			personsName+=persons[i].xm+",";
		}
	}
	var returnstr=personsId+"#"+personsName;
	return  returnstr;
}

function finishChkAllPerson(){
	var tag=true;	
	var persons=document.getElementsByName("personxm");
	if(persons!=null&&persons.length>0){
		for(var i=0;i<persons.length;i++){
			if(persons[i].checked==false){
				tag=false;
				break;
			}
		}
	}
	return tag;
}

function finishChkCar(){
	var carAry=new Array();
	var carsId="";
	var carsNum="";
	var cars=document.getElementsByName("chkcar");
	for(var i=0;i<cars.length;i++){
		if(cars[i].checked==true){
			carAry.push(cars[i]);
			carsId+=cars[i].value+",";
			carsNum+=cars[i].num+",";
		}
	}
	var returnstr=carsId+"#"+carsNum;
	return  returnstr;
}

function finishChkAllCar(){
	var tag=true;
	var cars=document.getElementsByName("chkcar");
	if(cars!=null&&cars.length>0){
		for(var i=0;i<cars.length;i++){
			if(cars[i].checked==false){
				tag=false;
				break;
			}
		}
	}
	return tag;
}

//得到一条警情车辆人员最新状态
function getPersonAndCar(alarmid){
	var url = "dispatch.dispose.getPersonAndCar.d";	
	var params={};
	params["alarmId"]=alarmid ;
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:getPersonAndCarRes});
}
function getPersonAndCarRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;
		
	var personres=xmlDoc.getElementsByTagName("person");
//	var personidary=new Array();
//	var personinameary=new Array();
	var person=new Array();
	var personidstr="";
	var personnamestr="";
	var personstr="";
	for(var i=0;i<personres.length;i++){
//		personidary.push(personres[i].getAttribute("pid"));
//		personinameary.push(personres[i].getAttribute("pname"));
		personidstr+=personres[i].getAttribute("pid")+",";		
		personnamestr+=personres[i].getAttribute("pname")+",";
		perstatestr=personres[i].getAttribute("pstate");
		personstr+=personres[i].getAttribute("pname")+"("+perstatestr+")"+",";
	}
	person.push(personidstr);
	person.push(personnamestr);
	person.push(personstr);
	showChkPerson(person,true);
	var carres=xmlDoc.getElementsByTagName("car");
//	var caridary=new Array();
//	var carnumary=new Array();
	var car=new Array();
	var caridstr="";
	var carnumstr="";
	var carstate="";
	for(var i=0;i<carres.length;i++){
		caridstr+=carres[i].getAttribute("cid")+","
		carnumstr+=carres[i].getAttribute("cnum")+",";		
		carstate+=carres[i].getAttribute("cstate")+",";	
	}
	car.push(caridstr);
	car.push(carnumstr);
	car.push(carstate);
	showChkCar(car,true);
}

//派警	
function assignPolice(alarmid){
	var personArray=getCheckPerson();
	var carArray=getCheckCar();
	var personIds="";
	var personNames="";
	var carIds="";
	var carNum="";
	if(personArray!=null&&personArray.length>0){
		for(var i=0;i<personArray.length;i++){
			personIds+=personArray[i].value+",";
			personNames+=personArray[i].xm+",";
		}
	}
	if(carArray!=null&&carArray.length>0){
		for(var i=0;i<carArray.length;i++){
			carIds+=carArray[i].value+",";
			carNum+=carArray[i].num+",";
		}
	}
	
	var url = "dispatch.dispose.assignPolice.d";	
	var params={};
	params["alarmId"]=alarmid ;
	params["person"]=pname ;	
	params["phone"]=mobilephone ;	
	params["deptId"]=deptcode ;	
	params["attemper"]=pname ;
	params["eventLevel"]=$("disposealarmLevel").value ;	
	params["weatherinfo"]=$("weatherinfo").value ;	
	params["disposeRoadType"]=$("disposeRoadType").value ;	
	params["disposeResult"]=$("disposeResult").value ;	
	params["disposeThinType"]=$("disposeThinType").value
	params["personIds"]=personIds ;
	params["personNames"]=personNames ;
	params["carIds"]=carIds ;
	params["carNums"]=carNum ;
	params["attemperid"]=pid ;
	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:assignPoliceRes});
}
function assignPoliceRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;
	if(xmlHttp.responseText=="null"){
		alert("该警情正被调度");
		return;
	}
	if(xmlHttp.responseText=="kong"){
		alert("请先选择警力");
		return;
	}
	
	var res=xmlDoc.getElementsByTagName("col");
	$("pjtime").value=res[0].text;
	var html="<div  style='border:1px solid #CCCCCC;width:100%;height:115px;overflow:scroll; '>";
	var desc=res[1].text.split("*NextRow*");	
	
	for(var i=0;i<desc.length;i++){
		if(desc[i].split("\n").length>1){
			var temphtml="";
			for(var n=0;n<desc[i].split("\n").length-1;n++){
				if(n!=desc[i].split("\n").length-2){
					temphtml+=desc[i].split("\n")[n]+"<br>";
				}else{
					temphtml+=desc[i].split("\n")[n];
				}
			}
			desc[i]=temphtml;
		}
		html+=desc[i]+"<BR>"
	}
	html+="</div>";
	$("fklist").innerHTML="";
	$("fklist").innerHTML=html;
	watching.readNoticedAffair();
	getPersonAndCar(res[2].text);
}	

//出警	
function outPolice(alarmid){	
	var personstr=finishChkPerson();	
	var persons=personstr.split("#");
	var carstr=finishChkCar();
	var cars=carstr.split("#");
	if(personstr=="#"&&carstr=="#"){
		alert("请选择出警人员或车辆")
		return;
	}
	var url = "dispatch.dispose.outPolice.d";	
	var params={};
	params["alarmId"]=alarmid ;
	params["person"]=pname ;	
	params["phone"]=mobilephone ;	
	params["deptId"]=deptcode ;	
	params["attemper"]=pname ;
	params["eventLevel"]=$("disposealarmLevel").value ;	
	params["weatherinfo"]=$("weatherinfo").value ;	
	params["disposeRoadType"]=$("disposeRoadType").value ;	
	params["disposeResult"]=$("disposeResult").value ;	
	params["disposeThinType"]=$("disposeThinType").value
	params["attemperid"]=pid ;
	params["personIds"]=persons[0] ;
	params["personNames"]=persons[1] ;
	params["carIds"]=cars[0] ;
	params["carNums"]=cars[1] ;
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:outPoliceRes});
}
function outPoliceRes(xmlHttp){	
	if(xmlHttp.responseText=="null"){
		alert("该警情正被调度");
		return;
	}
	var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("col");
//	var desc=xmlDoc.getElementsByTagName("desc");
	$("cjtime").value=res[0].text;
	var html="<div  style='border:1px solid #CCCCCC;width:100%;height:115px;overflow:scroll; '>";
	
	var desc=res[1].text.split("*NextRow*");
	
	for(var i=0;i<desc.length;i++){
		if(desc[i].split("\n").length>1){
			var temphtml="";
			for(var n=0;n<desc[i].split("\n").length-1;n++){
				if(n!=desc[i].split("\n").length-2){
					temphtml+=desc[i].split("\n")[n]+"<br>";
				}else{
					temphtml+=desc[i].split("\n")[n];
				}
			}
			desc[i]=temphtml;
		}
		html+=desc[i]+"<BR>"
	}
	html+="</div>";
	$("fklist").innerHTML="";
	$("fklist").innerHTML=html;
	watching.readNoticedAffair();
	getPersonAndCar(res[2].text);
//	for(var i=0;i<desc.length;i++){
//		html+=desc[i]+"<br>";
//	}
//	$("fklist").innerHTML=html;
}	


//到达	
function arrivePolice(alarmid){	
	var personstr=finishChkPerson();	
	var persons=personstr.split("#");
	var carstr=finishChkCar();
	var cars=carstr.split("#");
	if(personstr=="#"&&carstr=="#"){
		alert("请选择到达人员或车辆")
		return;
	}
	var url = "dispatch.dispose.arrivePolice.d";	
	var params={};
	params["alarmId"]=alarmid ;
	params["person"]=pname ;	
	params["phone"]=mobilephone ;	
	params["deptId"]=deptcode ;	
	params["attemper"]=pname ;
	params["eventLevel"]=$("disposealarmLevel").value ;	
	params["weatherinfo"]=$("weatherinfo").value ;	
	params["disposeRoadType"]=$("disposeRoadType").value ;	
	params["disposeResult"]=$("disposeResult").value ;	
	params["disposeThinType"]=$("disposeThinType").value
	params["attemperid"]=pid ;
	params["personIds"]=persons[0] ;
	params["personNames"]=persons[1] ;
	params["carIds"]=cars[0] ;
	params["carNums"]=cars[1] ;
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:arrivePoliceRes});
}
function arrivePoliceRes(xmlHttp){	
	if(xmlHttp.responseText=="null"){
		alert("该警情正被调度");
		return;
	}
	var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("col");
//	var desc=xmlDoc.getElementsByTagName("desc");
	$("ddtime").value=res[0].text;
	var html="<div  style='border:1px solid #CCCCCC;width:100%;height:115px;overflow:scroll; '>";
	
	var desc=res[1].text.split("*NextRow*");
	
	for(var i=0;i<desc.length;i++){
		if(desc[i].split("\n").length>1){
			var temphtml="";
			for(var n=0;n<desc[i].split("\n").length-1;n++){
				if(n!=desc[i].split("\n").length-2){
					temphtml+=desc[i].split("\n")[n]+"<br>";
				}else{
					temphtml+=desc[i].split("\n")[n];
				}
			}
			desc[i]=temphtml;
		}
		html+=desc[i]+"<BR>"
	}
	html+="</div>";
	$("fklist").innerHTML="";
	$("fklist").innerHTML=html;
	watching.readNoticedAffair();
	getPersonAndCar(res[2].text);
}	

//反馈
function feedbackPolice(alarmid){
	if(!isNumber($("disposessrs").value)){
		alert("受伤人数请输入数字！");
		return;
	}
	if(!isNumber($("disposeswrs").value)){
		alert("死亡人数请输入数字！");
		return;
	}
	var url = "dispatch.dispose.feedbackPolice.d";	
	var zydy="";
	if($("zydy").value!=null&&$("zydy").value!=""){
		zydy=$("zydy")[$("zydy").selectedIndex].text
	}
	var tydy="";
	if($("tydy").value!=null&&$("tydy").value!=""){
		tydy=$("tydy")[$("tydy").selectedIndex].text
	}
	var desc="";
//	desc=zydy+tydy+$("disposeDesc").value ;	
	desc=$("disposeDesc").value ;	
	var params={};
	params["alarmId"]=alarmid ;
	params["person"]=pname ;	
	params["phone"]=mobilephone ;	
	params["deptId"]=deptcode ;	
	params["attemper"]=pname ;
	params["eventLevel"]=$("disposealarmLevel").value ;	
	params["weatherinfo"]=$("weatherinfo").value ;	
	params["disposeRoadType"]=$("disposeRoadType").value ;	
	params["disposeResult"]=$("disposeResult").value ;	
	params["backdesc"]=	desc;
	params["disposessrs"]=$("disposessrs").value;
	params["disposeswrs"]=$("disposeswrs").value;
	params["disposeThinType"]=$("disposeThinType").value
	params["attemperid"]=pid ;
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:feedbackPoliceRes});
}
function feedbackPoliceRes(xmlHttp){	
	if(xmlHttp.responseText=="null"){
		alert("该警情正被调度");
		return;
	}
	var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("col");
//	var desc=xmlDoc.getElementsByTagName("desc");
	$("fktime").value=res[0].text;
	var html="<div  style='border:1px solid #CCCCCC;width:100%;height:115px;overflow:scroll; '>";
	
	var desc=res[1].text.split("*NextRow*");
	
	for(var i=0;i<desc.length-1;i++){		
		if(desc[i].split("\n").length>1){
			var temphtml="";
			for(var n=0;n<desc[i].split("\n").length-1;n++){
				if(n!=desc[i].split("\n").length-2){
					temphtml+=desc[i].split("\n")[n]+"<br>";
				}else{
					temphtml+=desc[i].split("\n")[n];
				}
			}
			desc[i]=temphtml;
		}
		html+=desc[i]+"<BR>"
	}
	html+="</div>";
	$("fklist").innerHTML="";
	$("fklist").innerHTML=html;
	$("disposeDesc").value="";
	$("zydy").value=""
	$("tydy").value=""
	watching.readNoticedAffair();
}

//撤离	
function finishPolice(alarmid){	
	var personstr=finishChkPerson();	
	var persons=personstr.split("#");
	var carstr=finishChkCar();
	var cars=carstr.split("#");
	if(personstr=="#"&&carstr=="#"){
		alert("请选择撤离人员或车辆")
		return;
	}
	var allperson=finishChkAllPerson();
	var allcar=finishChkAllCar();
	
	var url = "dispatch.dispose.finishPolice.d";	
	var params={};
	params["alarmId"]=alarmid ;
	params["person"]=pname ;	
	params["phone"]=mobilephone ;	
	params["deptId"]=deptcode ;	
	params["attemper"]=pname ;
	params["eventLevel"]=$("disposealarmLevel").value ;	
	params["weatherinfo"]=$("weatherinfo").value ;	
	params["disposeRoadType"]=$("disposeRoadType").value ;	
	params["disposeResult"]=$("disposeResult").value ;	
	params["disposeThinType"]=$("disposeThinType").value
	params["attemperid"]=pid ;
	params["personIds"]=persons[0] ;
	params["personNames"]=persons[1] ;
	params["carIds"]=cars[0] ;
	params["carNums"]=cars[1] ;
	params["allperson"]=allperson ;
	params["allcar"]=allcar ;
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:finishPoliceRes});
}
function finishPoliceRes(xmlHttp){	
	if(xmlHttp.responseText=="null"){
		alert("该警情正被调度");
		return;
	}
	var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("col");
//	var desc=xmlDoc.getElementsByTagName("desc");
	$("cltime").value=res[0].text;
	var html="<div  style='border:1px solid #CCCCCC;width:100%;height:115px;overflow:scroll; '>";
	
	var desc=res[1].text.split("*NextRow*");
	
	for(var i=0;i<desc.length;i++){
		if(desc[i].split("\n").length>1){
			var temphtml="";
			for(var n=0;n<desc[i].split("\n").length-1;n++){
				if(n!=desc[i].split("\n").length-2){
					temphtml+=desc[i].split("\n")[n]+"<br>";
				}else{
					temphtml+=desc[i].split("\n")[n];
				}
			}
			desc[i]=temphtml;
		}
		html+=desc[i]+"<BR>"
	}
	html+="</div>";
	$("fklist").innerHTML="";
	$("fklist").innerHTML=html;
	watching.readNoticedAffair();
	getPersonAndCar(res[2].text);
}

//恢复	trafficrevert
function trafficrevertPolice(alarmid){
	var personstr=trafficrevertChkPerson();
	var persons=personstr.split("#");
	var carstr=trafficrevertChkCar();
	var cars=carstr.split("#");
	
	var url = "dispatch.dispose.trafficrevertPolice.d";	
	var params={};
	params["alarmId"]=alarmid ;
	params["person"]=pname ;	
	params["phone"]=mobilephone ;	
	params["deptId"]=deptcode ;	
	params["attemper"]=pname ;
	params["eventLevel"]=$("disposealarmLevel").value ;	
	params["weatherinfo"]=$("weatherinfo").value ;	
	params["disposeRoadType"]=$("disposeRoadType").value ;	
	params["disposeResult"]=$("disposeResult").value ;	
	params["disposeThinType"]=$("disposeThinType").value
	params["attemperid"]=pid ;
	params["personIds"]=persons[0] ;
	params["personNames"]=persons[1] ;
	params["carIds"]=cars[0] ;
	params["carNums"]=cars[1] ;
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:trafficrevertPoliceRes});
}
function trafficrevertPoliceRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;		
	if(xmlHttp.responseText=="null"){
		alert("该警情正被调度");
		return;
	}
	var res=xmlDoc.getElementsByTagName("col");
//	var desc=xmlDoc.getElementsByTagName("desc");
	$("hftime").value=res[0].text;
	var html="<div  style='border:1px solid #CCCCCC;width:100%;height:115px;overflow:scroll; '>";
	
	var desc=res[1].text.split("*NextRow*");
	
	for(var i=0;i<desc.length;i++){
		html+=desc[i]+"<BR>"
	}
	html+="</div>";
	$("fklist").innerHTML="";
	$("fklist").innerHTML=html;
	watching.readNoticedAffair();
	var btns=document.getElementsByName("optbtn");
	for(var i=0;i<btns.length;i++){
		btns[i].disabled=true;
	}
	cleanDisposeInfo();
}



//退出调度
function exitPolice(alarmid){
	var url = "dispatch.dispose.exitPolice.d";	
	var params={};
	params["alarmId"]=alarmid ;
	params["person"]=pname ;	
	params["phone"]=mobilephone ;	
	params["deptId"]=deptcode ;	
	
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:exitPoliceRes});
}
function exitPoliceRes(xmlHttp){	
	var xmlDoc = xmlHttp.responseXML;		
	var res=xmlDoc.getElementsByTagName("col");
	var html="<div  style='border:1px solid #CCCCCC;width:100%;height:115px;overflow:scroll; '>";
	
	var desc=res[1].text.split("*NextRow*");
	
	for(var i=0;i<desc.length;i++){
		if(desc[i].split("\n").length>1){
			var temphtml="";
			for(var n=0;n<desc[i].split("\n").length-1;n++){
				if(n!=desc[i].split("\n").length-2){
					temphtml+=desc[i].split("\n")[n]+"<br>";
				}else{
					temphtml+=desc[i].split("\n")[n];
				}
			}
			desc[i]=temphtml;
		}
		html+=desc[i]+"<BR>"
	}
	html+="</div>";
	$("fklist").innerHTML="";
	$("fklist").innerHTML=html;
	watching.readNoticedAffair();
	var btns=document.getElementsByName("optbtn");
	for(var i=0;i<btns.length;i++){
		btns[i].disabled=true;
	}
}


function trafficrevertChkPerson(){
	var personAry=new Array();
	var personsId="";
	var personsName="";
	var persons=document.getElementsByName("personxm");
	for(var i=0;i<persons.length;i++){
		personAry.push(persons[i]);
		personsId+=persons[i].value+",";
		personsName+=persons[i].xm+",";
	}
	var returnstr=personsId+"#"+personsName;
	return  returnstr;
}

function trafficrevertChkCar(){
	var carAry=new Array();
	var carsId="";
	var carsNum="";
	var cars=document.getElementsByName("chkcar");
	for(var i=0;i<cars.length;i++){
		carAry.push(cars[i]);
		carsId+=cars[i].value+",";
		carsNum+=cars[i].num+",";		
	}
	var returnstr=carsId+"#"+carsNum;
	return  returnstr;
}	

function cleanDisposeInfo(){
	var inputs=document.getElementsByName("disposeinput");
	for(var i=0;i<inputs.length;i++){
		inputs[i].value="";
	}
	$("disposealarmType").value="";
	$("disposealarmLevel").value="";
	$("disposeThinType").value="";
	$("disposeRoadType").value="";
	$("weatherinfo").value="";
	$("disposeResult").value="";
	$("zydy").value="";
	$("tydy").value="";
	$("disposeDesc").value="";
	$("personlisttd").innerHTML='<div id="personlist" style="border: 1px solid #CCCCCC;overflow-Y: scroll;width:100%;height:100;"></div>';
	$("carlisttd").innerHTML='<div id="carlist" style="border: 1px solid #CCCCCC;overflow-Y: scroll;width:100%;height:78;"><div>';
	$("fklist").innerHTML="<div  style='border:1px solid #CCCCCC;width:100%;height:115px;overflow:scroll; '></div>";;
}
//验证数字	
function isNumber(inputValue) {
	if (inputValue.replace(/[\d+]/ig, "").length > 0) {
		return false;
	}
	return true;
}	

function Expand(sid){
	if(document.all(sid).style.display==""){			
		document.all(sid).style.display="none";		
		document.all("img_"+sid).src="../../images/tree/plusOpen.gif";		
	}else{
		document.all(sid).style.display="";
		document.all("img_"+sid).src="../../images/tree/minusClosed.gif";
	}
}
