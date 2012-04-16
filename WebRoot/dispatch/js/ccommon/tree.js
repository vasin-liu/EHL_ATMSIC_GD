var str="";
var personAry=new Array();

function setTree(paraStr){
	personAry=new Array();
	var paraary=paraStr.split(",");
	if(paraary!=null&&paraary.length>2){		
		var deptId=paraary[0]; 
		var divId=paraary[1];
		var state=paraary[2];
		var top=paraary[3];
		var left=paraary[4];
		var width=paraary[5];
		var height=paraary[6];
		var okmethod=paraary[7];		
	}
	str="";
	var url = "dispatch.PersonTree.setTree.d";	
	url = encodeURI(url);	
	var params = {};
	params["deptId"]=deptId;
	params["divId"]=divId;
	params["state"]=state;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function (xmlHttp){
		var xmlDoc = xmlHttp.responseXML;		
	
		var tree=xmlDoc.getElementsByTagName("tree");
		
		setHtml(tree[0]);
		
		var div=document.createElement("div");
		var table=document.createElement("table");
		table.style.width="100%";
		table.style.height="100%";
		var tbody=document.createElement("tbody");
		var tr=document.createElement("tr");
		var tdtree=document.createElement("td");
		tdtree.style.width="60%";
		var tdchoose=document.createElement("td");
		tdchoose.style.width="40%";
		tdchoose.style.height="100%";
	//	tdchoosexm.style.verticalAlign="text-top";
		var treediv=document.createElement("div");
		treediv.style.border="1px solid #CCCCCC";
		treediv.style.width="100%";
		treediv.style.height="100%";
		treediv.style.overflow="auto";
		var choosediv=document.createElement("div");
		choosediv.id="choosediv";
		choosediv.style.border="1px solid #CCCCCC";
		choosediv.style.height="100%";
		treediv.innerHTML=str;	
		
		
		var choosetable=document.createElement("table");
		choosetable.className="list_table";
		choosetable.style.width="100%";
	//	choosetable.style.height="100%";	
		var choosetitletbody=document.createElement("tbody");
		var choosetbody=document.createElement("tbody");
		choosetbody.id="choosetbody";
		var choosetr=document.createElement("tr");
	//	choosetr.style.height="20px";
		var tdchoosexm=document.createElement("td");
		tdchoosexm.className="list_thead";	
		tdchoosexm.style.verticalAlign="top";
		tdchoosexm.style.width="75%";	
		tdchoosexm.innerHTML="&nbsp;姓名";	
		var tdchoosechk=document.createElement("td");	
		tdchoosechk.className="list_thead";	
		tdchoosechk.style.width="25%";
		tdchoosechk.style.verticalAlign="top";
		tdchoosechk.innerHTML="&nbsp;删除";		
		choosetr.appendChild(tdchoosexm);
		choosetr.appendChild(tdchoosechk);
		choosetitletbody.appendChild(choosetr);
		choosetable.appendChild(choosetitletbody);	
		choosetable.appendChild(choosetbody);	
		choosediv.appendChild(choosetable);
		
		
		tdtree.appendChild(treediv);
		tdchoose.appendChild(choosediv);
		tr.appendChild(tdtree);
		tr.appendChild(tdchoose);
		tbody.appendChild(tr);
		table.appendChild(tbody);
		div.appendChild(table);
	
		if(okmethod&&okmethod!=""){
			openDivWindow(div,top,left,width,height,"getChoosedPerson('"+divId+"');"+okmethod+";closeDivWindow();","closeDivWindow()","请选择人员");
		}else{
			openDivWindow(div,top,left,width,height,"getChoosedPerson('"+divId+"');closeDivWindow();","closeDivWindow()","请选择人员");
		}
	}
	});

}


//function setTree(){
//	var deptId="150200000000";	
//	var url = "tree.tree.setTree.d";	
//	url = encodeURI(url);	
//	var params = {};
//	params["deptId"]=deptId;
//	new Ajax.Request(url, {method:"post", parameters:params, onComplete:setTreeRes});
//}
//function setTreeRes(xmlHttp){
//	var xmlDoc = xmlHttp.responseXML;		
//
//	var tree=xmlDoc.getElementsByTagName("tree");
//	
//	setHtml(tree[0]);
//	
////	var div=document.createElement("div");
////	div.innerHTML=str;	
//	
////	$("bodydiv").appendChild(div);
//
//
//	
//	var div=document.createElement("div");
//	var table=document.createElement("table");
//	table.style.width="100%";
//	table.style.height="100%";
//	var tbody=document.createElement("tbody");
//	var tr=document.createElement("tr");
//	var tdtree=document.createElement("td");
//	tdtree.style.width="60%";
//	var tdchoose=document.createElement("td");
//	tdchoose.style.width="40%";
//	tdchoose.style.height="100%";
////	tdchoosexm.style.verticalAlign="text-top";
//	var treediv=document.createElement("div");
//	treediv.style.border="1px solid #CCCCCC";
//	treediv.style.width="100%";
//	treediv.style.height="100%";
//	treediv.style.overflow="auto";
//	var choosediv=document.createElement("div");
//	choosediv.id="choosediv";
//	choosediv.style.border="1px solid #CCCCCC";
//	choosediv.style.height="100%";
//	treediv.innerHTML=str;		
//	
//	var choosetable=document.createElement("table");
//	choosetable.className="list_table";
//	choosetable.style.width="100%";
////	choosetable.style.height="100%";	
//	var choosetitletbody=document.createElement("tbody");
//	var choosetbody=document.createElement("tbody");
//	choosetbody.id="choosetbody";
//	var choosetr=document.createElement("tr");
////	choosetr.style.height="20px";
//	var tdchoosexm=document.createElement("td");
//	tdchoosexm.className="list_thead";	
//	tdchoosexm.style.verticalAlign="top";
//	tdchoosexm.style.width="75%";	
//	tdchoosexm.innerHTML="&nbsp;姓名";	
//	var tdchoosechk=document.createElement("td");	
//	tdchoosechk.className="list_thead";	
//	tdchoosechk.style.width="25%";
//	tdchoosechk.style.verticalAlign="top";
//	tdchoosechk.innerHTML="&nbsp;删除";		
//	choosetr.appendChild(tdchoosexm);
//	choosetr.appendChild(tdchoosechk);
//	choosetitletbody.appendChild(choosetr);
//	choosetable.appendChild(choosetitletbody);	
//	choosetable.appendChild(choosetbody);	
//	choosediv.appendChild(choosetable);	
//	
//	tdtree.appendChild(treediv);
//	tdchoose.appendChild(choosediv);
//	tr.appendChild(tdtree);
//	tr.appendChild(tdchoose);
//	tbody.appendChild(tr);
//	table.appendChild(tbody);
//	div.appendChild(table);
//		
//	openDivWindow(div,10,10,400,300,"test();closeDivWindow();","closeDivWindow()","请选择人员")
//}


//在右侧选择后的人员表格中添加tr
function createChkdTable(pid,pname,pdeptid){
	var tbody=document.getElementById("choosetbody");
	var tr=document.createElement("tr");	
	var tdxm=document.createElement("td");
	var tdchk=document.createElement("td");
	tdxm.className="list_td";
	tdchk.className="list_td";
	
	tdxm.innerHTML="&nbsp;"+pname;
	tdchk.innerHTML="<input type=checkbox id=\"chkd_"+pid+"\" onclick=\"removePerson('"+pid+"','"+pname+"','"+pdeptid+"')\">";
	
	tr.appendChild(tdxm);
	tr.appendChild(tdchk);
	tbody.appendChild(tr);
}

function choosePerson(pid,pname,pdeptid){
	var person =document.getElementById(pid);
	var chkdperson=document.getElementById("chkd_"+pid);
	if(chkdperson){
		if(person&&(person.checked==false)){	
			removePerson(pid,pname,pdeptid);
		}		
	}else{
		if(person&&person.checked){		
			var str=pid+"#"+pname+"#"+pdeptid;
			personAry.push(str);		
			createChkdTable(pid,pname,pdeptid);
		}
	}
}



function removePerson(pid,pname,pdeptid){
	
	var choosediv=document.getElementById("choosediv");
	choosediv.innerHTML="";
	var choosetable=document.createElement("table");
	choosetable.className="list_table";
	choosetable.style.width="100%";
//	choosetable.style.height="100%";	
	var choosetitletbody=document.createElement("tbody");
	var choosetbody=document.createElement("tbody");
	choosetbody.id="choosetbody";
	var choosetr=document.createElement("tr");
//	choosetr.style.height="20px";
	var tdchoosexm=document.createElement("td");
	tdchoosexm.className="list_thead";	
	tdchoosexm.style.verticalAlign="top";
	tdchoosexm.style.width="75%";	
	tdchoosexm.innerHTML="&nbsp;姓名";	
	var tdchoosechk=document.createElement("td");	
	tdchoosechk.className="list_thead";	
	tdchoosechk.style.width="25%";
	tdchoosechk.style.verticalAlign="top";
	tdchoosechk.innerHTML="&nbsp;删除";		
	choosetr.appendChild(tdchoosexm);
	choosetr.appendChild(tdchoosechk);
	choosetitletbody.appendChild(choosetr);
	choosetable.appendChild(choosetitletbody);	
	choosetable.appendChild(choosetbody);	
	choosediv.appendChild(choosetable);
		
	var str=pid+"#"+pname+"#"+pdeptid;
	var tempAry=new Array();
//	var tbody=document.getElementById("choosetbody");
	
	if(personAry!=null&&personAry.length>0){
		for(var i=0;i<personAry.length;i++){
			var splitstr=personAry[i].split("#");
			if(personAry[i]!=str){
				tempAry.push(personAry[i]);
				createChkdTable(splitstr[0],splitstr[1],splitstr[2]);
			}
		}
	}
	personAry=tempAry;	
}

function test(divId){
	alert(personAry.length);
	var personStr="";
	var personIdStr="";
	for(var i=0;i<personAry.length;i++){
		alert(personAry[i])
		var splitstr=personAry[i].split("#");
		personStr+=splitstr[1]+"；";
		personIdStr+=splitstr[0]+"；";
	}
	
	
}


function getChoosedPerson(divId){
	var personStr="";
	var personIdStr="";
	for(var i=0;i<personAry.length;i++){
		var splitstr=personAry[i].split("#");
		personStr+=splitstr[1]+"；";
		personIdStr+=splitstr[0]+"；";
	}
	
	$(divId).value=personStr;
	$(divId).personId=personIdStr;
}


//生成树html
function setHtml(dept,bm){	
	var space="";		
	if(bm){
		for(var i=0;i<bm.length;i++){
			space+="&nbsp;&nbsp;"		
		}
	}
	var deptson=dept.childNodes;		
	
	for(var j=0;j<deptson.length;j++){	
		var ccbm="";		
		var aid="";
		if(deptson[j].tagName =="dept"){
			ccbm=deptson[j].getAttribute("jgccbm");					
			aid=deptson[j].getAttribute("id");				
			var deptName=deptson[j].getAttribute("text")
			str+=space+"<img id='img_"+aid+"' src='../../../sm/image/tree/tree_open.bmp' onclick=\"Expand('"+aid+"');\" /><input type=checkbox id='chk_"+aid+"' ccbm='"+ccbm+"' name='"+aid+"' onclick=\"clickr('chk_"+aid+"','"+ccbm+"');\"><img id='i_"+aid+"' src='../../../sm/image/tree/folderOpen.gif' /><font  onclick=Expand('"+aid+"');>"+deptName+"<br></font>";
		}		
		
		if(deptson[j].tagName =="person"){		
			ccbm=deptson[j].getAttribute("ccbm");	
			var personid=deptson[j].getAttribute("id");		
			var personname=deptson[j].getAttribute("name");
			var perdept=deptson[j].getAttribute("deptId"); 
			str+=space+"<font style='width:13px'></font><input type=checkbox onclick=\"choosePerson('"+personid+"','"+personname+"','"+perdept+"')\" id='"+personid+"' xm='"+personname+"' ssjg='"+perdept+"' ccbm='"+ccbm+"' name='personchks'><img src='../../../sm/image/tree/book_titel.gif' />"+personname+"<br>";						
		}	
		
		if(aid!=""){			
			str+="<div id='d_"+aid+"'>"		
			setHtml(deptson[j],ccbm);	
			str+="</div>";	
		}
	}	
		
}

//点击选择部门下所有人员

function clickr(oid,dname){
	var chk=document.getElementById(oid);

	var inputs=document.getElementsByTagName("input");

	for(var i=0;i<inputs.length;i++){
		if(inputs[i].type == "checkbox"){
			if(inputs[i].ccbm&&inputs[i].ccbm.length>=dname.length){
				if(inputs[i].ccbm.substring(0,dname.length)==dname){
					if(chk.checked==true){
						inputs[i].checked=true;
						if(inputs[i].name=="personchks"){
							choosePerson(inputs[i].id,inputs[i].xm,inputs[i].ssjg);
						}						
					}else{
						inputs[i].checked=false;
						if(inputs[i].name=="personchks"){
							removePerson(inputs[i].id,inputs[i].xm,inputs[i].ssjg);
						}
					}
				}
			}
		}
	}

}

//机构树展开关闭
function Expand(sid){
	if(document.getElementById("d_"+sid).style.display==""){
		document.getElementById("img_"+sid).src="../../../sm/image/tree/tree_close.bmp";
		document.getElementById("i_"+sid).src="../../../sm/image/tree/folderClosed.gif";
		document.getElementById("d_"+sid).style.display="none";
	}else{
		document.getElementById("img_"+sid).src="../../../sm/image/tree/tree_open.bmp";
		document.getElementById("i_"+sid).src="../../../sm/image/tree/folderOpen.gif";
		document.getElementById("d_"+sid).style.display="";
	}
}	



//获取人员选中信息
function getChkPerson(divId){
	var persons=document.getElementsByName("personchks");
	var personStr="";
	var personIdStr="";
	if(persons!=null){
		for(var i=0;i<persons.length;i++){
			if(persons[i].checked==true){
				personStr+=persons[i].xm+"；";
				personIdStr+=persons[i].id+"；";
			}
		}
		$(divId).value=personStr;
		$(divId).personId=personIdStr;
	}	
}

	
function openDivWindow(obj,top,left,width,height,okmethod,canclemethod,title){
	var useDiv = document.createElement("div");
	useDiv.id="usedivs";
//	useDiv.style.backgroundColor="#DFEAF7";
	useDiv.style.width=width;
	useDiv.style.height=height;
	useDiv.style.textAlign="center";
	useDiv.style.zIndex = 1000;
	useDiv.style.position="absolute";
 	if(top==null||top==""){
 		top=0;
 	}
 	if(left==null||left==""){
 		left=0;
 	}
 	useDiv.style.top=top;
 	useDiv.style.left=left; 	
 	 	
 	useDiv.innerHTML='\
           <div >\
           <table id="poptable" width="100%" height="100%" style="border=0" class="popup" cellpadding="0" cellspacing="0">\
            <tbody>\
              <tr height="5%" class="scrollColThead">\
                <td class="corner" id="topleft"></td>\
                <td class="top">' + title + '</td>\
                <td class="top" align="right" valign="middle">\
                  <img src="../../../sm/image/popup/ok.gif" border="0" style="cursor:pointer" onclick="' + okmethod + '">\
                  <img src="../../../sm/image/popup/cancel.gif" border="0" style="cursor:pointer" onclick="' + canclemethod + '">\
                </td>\
                <td class="corner" id="topright"></td>\
              </tr>\
              <tr height="92%" valign="top">\
                <td class="left"></td>\
                <td id="popup-contents" bgcolor="#DFEAF7" colspan="2">\
                   <div style="weight:180;height:298;overflow-y:scroll;" id="tdcontain"></div>\
                </td>\
                <td class="right"></td>\
              </tr>\
              <tr height="3%">\
                <td id="bottomleft" class="corner"></td>\
                <td class="bottom" colspan="2"></td>\
                <td id="bottomright" class="corner"></td>\
              </tr>\
            </tbody>\
            </table>\
          </div>';	
	document.body.appendChild(useDiv);
	$("tdcontain").style.scrollbarBaseColor="#DFEAF7";

	$("tdcontain").appendChild(obj);	
}

function closeDivWindow(){
	var divPopup = document.getElementById("useDivs");
	if((typeof(divPopup) != "undefined") && (divPopup != null))
	{
    	divPopup.parentNode.removeChild(divPopup);
    }
}	