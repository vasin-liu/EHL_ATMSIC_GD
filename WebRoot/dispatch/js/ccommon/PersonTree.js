var str="";
function setTree(deptId,divId,top,left,width,height){
	str="";
	var url = "pcs.tree.setTree.d";	
	url = encodeURI(url);	
	var params = {};
	params["deptId"]=deptId;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function (xmlHttp){
		var xmlDoc = xmlHttp.responseXML;		
	
		var tree=xmlDoc.getElementsByTagName("tree");
		
		setHtml(tree[0]);
		
		var div=document.createElement("div");
		div.innerHTML=str;		
		openDivWindow(div,top,left,width,height,"getChkPerson('"+divId+"');closeDivWindow();","closeDivWindow()","请选择人员")
	}
	});

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
			str+=space+"<img id='img_"+aid+"' src='../../../sm/image/tree/tree_open.bmp' onclick=\"Expand('"+aid+"');\" /><input type=checkbox id='chk_"+aid+"' ccbm='"+ccbm+"' name='"+aid+"' onclick=\"clickr('chk_"+aid+"','"+ccbm+"');\"><input type=hidden id='"+aid+"' value='"+deptName+"' ccbm='"+ccbm+"' name='personchks'><img id='i_"+aid+"' src='../../../sm/image/tree/folderOpen.gif' /><font  onclick=Expand('"+aid+"');>"+deptName+"<br></font>";
		}		
		
		if(deptson[j].tagName =="person"){		
			ccbm=deptson[j].getAttribute("ccbm");	
			var personid=deptson[j].getAttribute("id");		
			var personname=deptson[j].getAttribute("name");
			var perdept=deptson[j].getAttribute("deptId"); 
			//str+=space+"<font style='width:13px'></font><input type=checkbox id='"+personid+"' xm='"+personname+"' ccbm='"+ccbm+"' name='personchks'><img  src='../../../sm/image/tree/book_titel.gif' />"+personname+"<br>";						
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
					}else{
						inputs[i].checked=false;
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
	//alert(persons[0].id+"  "+persons[0].value);
	var personStr="";
	var personIdStr="";
	if(persons!=null){
		for(var i=0;i<persons.length;i++){
			//if(persons[i].checked==true){
			if($('chk_'+persons[i].id).checked==true){
				personStr+=persons[i].value+"；";
				personIdStr+=persons[i].id+";";
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