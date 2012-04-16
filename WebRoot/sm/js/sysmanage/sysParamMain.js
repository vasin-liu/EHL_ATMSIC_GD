function selectTag(showContent,selfObj){
	  // 操作标签
		  var tag = document.getElementById("tags").getElementsByTagName("li");
		  var taglength = tag.length;
		  
		  for(i=0; i<taglength; i++){
		    tag[i].className = "";
		    tag[i].style.background="url('../../image/sysoption/bg.gif')  right bottom";
		  }
		  selfObj.parentNode.className = "selectTag";
		   selfObj.parentNode.style.background="url('../../image/sysoption/bg2.gif')  right bottom";
		  
		  // 操作内容
		  j=document.getElementById("tagContent").getElementsByTagName("div");
		  
		  for(i=0; i<j.length; i++){
		    j[i].style.display = "none";
		  }
		  document.getElementById(showContent).style.display = "block";
		 selfObj.style.background = "left bottom";
		 document.getElementById(showContent).childNodes[0].focus();
	}
	
   function   onEscape(id){ 
    var url="sysParamUpdate.jsp?paramId="+id;
    url = encodeURI(url);
    var returnValueStr = window.showModalDialog(url, "", "dialogWidth:340px;dialogHeight:280px");
    if(returnValueStr != null){
	      var param_id = returnValueStr.split(";")[0];
	       var param_type = returnValueStr.split(";")[1];
	      var param_value = returnValueStr.split(";")[2];
	      var param_desc = returnValueStr.split(";")[3];	      	     
		  
	      //获取table
	      var addDiv = document.getElementById(param_type);
	     
	      var addTable = addDiv.childNodes[0];
	     
	     //删除页面被修改的行
		  var oldTr =  document.getElementById(param_id);
		  for (var i=0;i<addTable.rows.length;i++){
		     if(addTable.rows[i].getAttribute("id")==param_id){
		      addTable.deleteRow(i);
		     }
		     }
	       //创建tr
	      var addTr = addTable.insertRow();
	      addTr.setAttribute("id",param_id);
	      var addTd_1 =addTr.insertCell();
	      
	    
	      addTd_1.setAttribute("height","26");
		  addTd_1.setAttribute("align","center");
		  var addTd_1_text=document.createTextNode(param_desc);
		  addTd_1.appendChild(addTd_1_text); 		 		  
		   
		 var addTd_2 = addTr.insertCell();
		  addTd_2.setAttribute("height","26");
		  addTd_2.setAttribute("align","center");
		  var addTd_2_text=document.createTextNode(param_value);
		  addTd_2.appendChild(addTd_2_text); 
	      var addTd_3 = addTr.insertCell();
		  addTd_3.setAttribute("height","26");
		  addTd_3.setAttribute("align","center");  
		    
		  addTd_3.innerHTML = " <a href='#' onClick=\"onEscape('"+param_id+"');\">"+
			                                 "<img  alt='修改值' name='toUpdate' border='0'src='../../image/sysoption/update03.gif' ></a>"
			                                 +"&nbsp;&nbsp;"		 		   
      }
  } 
  
  