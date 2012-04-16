  //查询发布信息
  function filter(){
      var params = "";
      var title = document.getElementById("title").value;
      url = 'introduce.introductionManage.filter.d';
	  params = "title=" + title;
	  params = encodeURI(params);
	  new Ajax.Request(url, {method:"post", parameters:params, onComplete:showIntroduction});
  }
  
  function showIntroduction(xmlHttp){
     var xmlDoc = xmlHttp.responseXML;   
     var rows= xmlDoc.getElementsByTagName("row");
     var container = document.getElementById("tdData");
     var innerHtmlStr = "";
     var title = "";
     var content = "";
     var train = "";
     innerHtmlStr = "<table id='tabVeh' class='scrollTable' width=100% cellSpacing=0 cellPadding=0 style=\"table-layout:fixed\">\
			<tr class='scrollColThead' valign='top'\
				style='text-decoration: none; background-color: #B4C1E2; line-height: 20px;'>\
				<td width='18%' align='center'>\
					信息标题\
				</td>\
				<td width='60%' align='center'>\
					信息内容\
				</td>\
				<td width='7%' align='center'>\
					显示顺序\
				</td>\
				<td width='7%' align='center'>\
					修改\
				</td>\
				<td width='7%' align='center'>\
					删除\
				</td>\
			</tr>";
     for(var i = 0; i < rows.length; i ++){
         results = rows[i].getElementsByTagName("col");
         for(var j = 0; j < results.length; j++){
             if(results[j].text == ""){
                 results[j].text = "&nbsp;";
             }
         }
         innerHtmlStr += "<tr  valign='top'\
				style='text-decoration: none; line-height: 20px;'>\
				<td width='18%' nowrap align='left' style=\"overflow:hidden; text-overflow:ellipsis;\" title='" + results[1].text + "'>"+results[1].text+"\
				</td>\
				<td width='60%' nowrap align='left' style=\"overflow:hidden; text-overflow:ellipsis;\" title='" + results[2].text + "'>"+results[2].text+"\
				</td>\
				<td width='7%' align='center'>"+results[3].text+"\
				</td>\
				<td width='5%' align='center'>\
				    <a href='#' onclick=\"openEdit('"+results[0].text+"')\"><img  alt='修改'  border='0'src='../image/button/btnedit1.gif' ></a>\
				</td>\
				<td width='5%' align='center'>\
				    <a href='#' onclick=\"deleteById('"+results[0].text+"')\"><img  alt='删除'  border='0'src='../image/button/btndelete1.gif' ></a>\
				</td>\
			</tr>";
         
     }
	innerHtmlStr +="</table>";
    container.innerHTML = innerHtmlStr;
  }
  //打开编辑页面
  function openEdit(id){
       var returnValuestr = window.showModalDialog("IntroductionEdit.jsp?id=" + id +"&insrtOrUpdt=1","", "dialogWidth:420px;dialogHeight:480px"); 
      // window.open("IntroductionEdit.jsp?id=" + id +"&insrtOrUpdt=1");
	    if(returnValuestr == "fresh"){
	        window.location.reload(true);
	    }  
  }
  //打开编辑页面
  function openAdd(){
       var returnValuestr = window.showModalDialog("IntroductionEdit.jsp?id= &insrtOrUpdt=0","", "dialogWidth:420px;dialogHeight:480px"); 
       //window.open("IntroductionEdit.jsp?id= &insrtOrUpdt=0");
	    if(returnValuestr == "fresh"){
	       window.location.reload(true);
	    }  
  }
  
  //初始加载信息用于编辑
  function loadInfo(id){
      if(id.Trim() == "" || id =="null"){
          return;
      }
      var params = "";
      url = 'introduce.introductionManage.getById.d';
	  params = "id=" + id;
	  params = encodeURI(params);
	  new Ajax.Request(url, {method:"post", parameters:params, onComplete:showForEdit});   
  }
  //顺序是id，标题，内容，显示顺序
  function showForEdit(xmlHttp){
     var xmlDoc = xmlHttp.responseXML;
     var results = xmlDoc.getElementsByTagName("col");   
     document.getElementById("id").value = results[0].text;   
     document.getElementById("title").value = results[1].text;
     document.getElementById("content").value = results[2].text;
     document.getElementById("train").value = results[3].text;      
  }
  
  //重置
  function reset(){
     document.getElementById("id").value = "";   
     document.getElementById("title").value = "";
     document.getElementById("content").value = "";
     document.getElementById("train").value = "";    
  }
  
  //保存编辑或新增信息
  function save(id,insrtOrUpdt){
      window.returnValue = "fresh";
      var id = document.getElementById("id").value;   
      var title = document.getElementById("title").value;
      var content = document.getElementById("content").value;
      var train = document.getElementById("train").value; 
      if(title.Trim() == ""){
          alert("信息标题不能为空！");
          return;
      }
      if(title.length > 20){
          alert("标题只限20字以内！");
          return;
      }
      if(content.length > 500){
          alert("标题只限500字以内！");
          return;
      }
//      if(!checkTextDataForNORMAL(title)){
//	           alert("信息标题包含特殊字符，请重新输入！");
//	           return;
//	  } 
//	  if(!checkTextDataForNORMAL(content)){
//	           alert("信息内容包含特殊字符，请重新输入！");
//	           return;
//	  } 
	  if(!checkMath(train)){
	           alert("显示顺序只能是数字，请重新输入！");
	           return;
	  } 
      var params = "";
      url = 'introduce.introductionManage.save.d';
	  params = "id=" + id +"&title=" +title+ "&content=" +content +"&train=" +train +"&insrtOrUpdt=" +insrtOrUpdt;
	  params = encodeURI(params);
	  new Ajax.Request(url, {method:"post", parameters:params, onComplete:showSaveRes});    
  }
  function showSaveRes(xmlHttp){
      alert(xmlHttp.responseText);
  }
  
  //删除
  function deleteById(id){
      if(!window.confirm("确定删除此信息！")){
          return;
      }
      var params = "";
      url = 'introduce.introductionManage.deleteById.d';
	  params = "id=" + id;
	  params = encodeURI(params);
	  new Ajax.Request(url, {method:"post", parameters:params, onComplete:showDeleteRes});       
  }
  function showDeleteRes(xmlHttp){
      alert(xmlHttp.responseText);
      window.location.reload(true);
  }
  
  //更新发布数据
  function updateStateData(){
        showMsg();
      var params = "";
      url = 'introduce.introductionManage.updateRoadStatus.d';
	  params = encodeURI(params);
	  new Ajax.Request(url, {method:"post", parameters:params, onComplete:showUpdateDataRes});       
  }
  
  function showUpdateDataRes(xmlHttp){
      Popup.prototype.hideTips();	
      alert(xmlHttp.responseText);
      window.location.reload(true);
  }