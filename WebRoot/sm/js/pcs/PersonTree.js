	/*
	* 获取机构人员树,并以气泡形式弹出
	* deptId-顶层机构编号,divId-机构名称容器id; top-气泡距浏览器顶距离 left-距左侧距离
	* width-气泡宽度  height-气泡高度 ignorePerson-是否忽略人员 "true"-忽略人员
	*/
	var vmlArray = null;
	var index=-1;    //渐变绘线当前路线下标
	deptDiv=null;//vml容器
	var str=new Array();
	function setTree(deptId,divId,top,left,width,height,ignorePerson){
	    index=-1; 
		str=new Array();
		var url = "pcs.tree.setTree.d";	
		url = encodeURI(url);	
		var params = {};
		params["deptId"]=deptId;
		params["ignorePerson"]=ignorePerson;
		new Ajax.Request(url, {method:"post", parameters:params, onComplete:function (xmlHttp){
			var xmlDoc = xmlHttp.responseXML;		
			var tree=xmlDoc.getElementsByTagName("tree");
			setHtml(tree[0]);
			var div = document.createElement("div");
			deptDiv = div;
			vmlArray = str.join("").split("deptsplit");
			//div.innerHTML=str.join("");		
			openDivWindow(deptDiv,top,left,width,height,"getChkPerson('"+divId+"');closeDivWindow();","closeDivWindow()","请选择人员")
		    writeDeptByStep();
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
				var deptName=deptson[j].getAttribute("text");
				if(j != 0){
				    str.push("deptsplit");
				}
				str.push(space);
				str.push("<img id='img_");
				str.push(aid);
				str.push("' src='../../../sm/image/tree/tree_open.bmp' onclick=\"Expand('");
				str.push(aid);
				str.push("');\" /><input type=checkbox id='");
				str.push(aid);
				str.push("' ccbm='");
				str.push(ccbm);
				str.push("' deptname='");
				str.push(deptName);
				str.push("' name='deptchks' onclick=\"clickr('");
				str.push(aid);
				str.push("','");
				str.push(ccbm);
				str.push("');\"><img id='i_");
				str.push(aid);
				str.push("' src='../../../sm/image/tree/folderOpen.gif' /><font  onclick=Expand('");
				str.push(aid);
				str.push("');>");
				str.push(deptName);
				str.push("<br></font>");
			}		
//			if(deptson[j].tagName =="person"){		
//				ccbm=deptson[j].getAttribute("ccbm");	
//				var personid=deptson[j].getAttribute("id");		
//				var personname=deptson[j].getAttribute("name");
//				var perdept=deptson[j].getAttribute("deptId"); 
//				str+=space+"<font style='width:13px'></font><input type=checkbox id='"+personid+"' xm='"+personname+"' ccbm='"+ccbm+"' name='personchks'><img  src='../../../sm/image/tree/book_titel.gif' />"+personname+"<br>";						
//			}	
			if(aid!=""){			
				str.push("<div id='d_");
				str.push(aid);
				str.push("'>");		
				setHtml(deptson[j],ccbm);	
				str.push("</div>");	
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
	    var inputStr = "";
		var inputIdStr = "";
	    var deptchks = document.getElementsByName("deptchks");
		var persons = document.getElementsByName("personchks");
		if(persons.length > 0){
		    for(var i=0;i<persons.length;i++){
		        if(persons[i].checked==true){
					inputStr += persons[i].xm+"；";
					inputIdStr += persons[i].id+"；";
				}
		    }
		}else{
		    for(var i=0;i<deptchks.length;i++){
		        if(deptchks[i].checked == true){
		            inputStr += deptchks[i].deptname+"；";
		            inputIdStr += deptchks[i].id +"；";
		        }
		    }
		}
		$(divId).value=inputStr;
		$(divId).personId=inputIdStr;
	}
	function openDivWindow(obj,top,left,width,height,okmethod,canclemethod,title){
	    var temDiv = document.getElementById("usedivs");
	    var useDiv = null;
	    if(temDiv == null){
	        useDiv = document.createElement("div");
		    useDiv.id="usedivs";
	    }else{
	    
	        useDiv = temDiv;
	        
	    }
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
	
	function writeDeptByStep(){
	    var temArray = new Array();
		for(var i=index+1;i-index<50;i++){
		    if(i< vmlArray.length){
		        temArray.push(vmlArray[i]);
		    }
		}
		var divobj = document.createElement('div');
		deptDiv.appendChild(divobj);
		divobj.innerHTML = '<table>'+temArray.join('')+'</table>';
		//alert(vmlDiv.innerHTML);
		index=i--;
		if(index<vmlArray.length){
			setTimeout(writeDeptByStep,0)
		}
	}