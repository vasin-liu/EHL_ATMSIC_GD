function doOnLoadSearch(jgbh,jgid){
	doSearch();
}

function resetListValue (jgid) {
	$("zhiduiId").value = jgid;
//	$("zhiduiId").disabled = true;
}
/** 
    * desc:查询
    * param:
    * return:
    * version:
    */
function doSearch(){
	
	// 登录用户单位
	var jgid = $('jgid')?$('jgid').value:"";
	// 判断用机构编号
	var jgbh = $('jgbh')?$('jgbh').value:"";
	// 报送单位
	var zhiduiId = $('zhiduiId')?$('zhiduiId').value:"";
	// 报送开始时间
	var sj1 = $('sj1')?$('sj1').value:"";
	// 报送结束时间
	var sj2 = $('sj2')?$('sj2').value:"";
	
	var whereFlg = false;
	var sql = " select NEWS_FILEID,TITLE,to_char(SEND_TIME, 'yyyy-mm-dd HH24:mi'),SEND_DEPARTMENT_NAME,SEND_PERSON,WORD_FILEPATH, " +
				" STREAM_FILEPATH,DETAIL_INFO,SEND_DEPARTMENT_ID ,OTHER_INFO,STATE,TYPE ";
	sql += " from T_OA_NEWSFILE "
	sql += " where 1=1 ";
	// 登录用户支队时的处理
	if(zhiduiId == "") {
		whereFlg = true;
	} else {
		whereFlg = false;
		sql += " and  SEND_DEPARTMENT_ID = '" + zhiduiId + "'  and SEND_DEPARTMENT_ID not in ('440000000000') "; 
	}
	
	if(whereFlg) {
		if ($('sj1') && $('sj2')) {
			if ($('sj1').value!="" && $('sj2').value!="") {
				sql += " and to_char(SEND_TIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value+" 00:00"
					+ "' AND  to_char(SEND_TIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value+" 23:59" + "'  and SEND_DEPARTMENT_ID not in ('440000000000')";
			}else if($('sj1').value!="" && $('sj2').value==""){
				sql += " and to_char(al.reporttime,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value+" 00:00" + "'  and SEND_DEPARTMENT_ID not in ('440000000000')";
			}else if($('sj1').value=="" && $('sj2').value!=""){
				sql += " to_char(al.reporttime,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value+" 23:59" + "'  and SEND_DEPARTMENT_ID not in ('440000000000')";
			}
		}
	} else {
		if ($('sj1') && $('sj2')) {
			if ($('sj1').value!="" && $('sj2').value!="") {
				sql += " AND to_char(SEND_TIME,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value+" 00:00"
					+ "' AND to_char(SEND_TIME,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value+" 23:59" + "'";
			}else if($('sj1').value!="" && $('sj2').value==""){
				sql += " AND to_char(al.reporttime,'yyyy-mm-dd HH24:mi') >= '" + $('sj1').value+" 00:00" + "'";
			}else if($('sj1').value=="" && $('sj2').value!=""){
				sql += " AND to_char(al.reporttime,'yyyy-mm-dd HH24:mi') <= '" + $('sj2').value+" 23:59" + "'";
			}
		}
	}
	sql += " and sbtype='1' ";
	//Modify by Xiayx 2011-9-7
	var state = $("state").value;
	if(state){
		sql += " and state = '"+state+"'";
	}
	//Modification finished
	sql = sql + " order by SEND_TIME desc "
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showPoliceWorkResultsPage","10");
}




function showPoliceWorkResultsPage(xmldoc) {
	var appid = $("appid").value;
	var jgid = $("jgid").value;
	var rows = xmldoc.getElementsByTagName("row");
	var results = null;
	var jgbh = $('jgbh').value;
	var page = $('page');
	
	var str = page?"":"<div style='text-align:center;width:100%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span></div>";
		str += "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='table'>";
		str += "<tr class='titleTopBack'>";
		str += "<td width='4%' class='td_r_b td_font'>序号</td>";
		str += "<td width='15%' class='td_r_b td_font'>报送时间</td>";
		str += "<td width='15%' class='td_r_b td_font'>报送单位</td>";
	    str += "<td width='6%' class='td_r_b td_font'>填报人</td>";
	    str += "<td width='15%' class='td_r_b td_font'>文档文件</td>";
	    str += "<td width='15%' class='td_r_b td_font'>多媒体文件</td>";
	    str += "<td width='8%' class='td_r_b td_font'>材料类型</td>";
	    str += "<td width='8%' class='td_r_b td_font'>材料状态</td>";
	    //Modify by Xiayx 2011-8-30
	    //支队总队都可以查看、处理
//	    // 支队用户时
//	    if (jgbh.length == 4 || jgbh.length == 6) {
//		    str += "<td width='4%' class='td_r_b td_font'>查看</td>";
//	    } else {
//	    	// 总队用户时
//		    str += "<td width='4%' class='td_r_b td_font'>查看</td>";
//		    str += "<td width='4%' class='td_r_b td_font'>处理</td>";
//	    }
	    str += "<td width='4%' class='td_r_b td_font'>查看</td>";
	    if("1001,1006".indexOf(appid) != -1){
	    	str += "<td width='4%' class='td_r_b td_font'>处理</td>";
	    }
	    //Modification finished
	    str += "</tr>";
    if(rows.length<=0) {
	   str += "<tr class='titleTopBack'>";
	   str += "<td class='td_r_b td_font' colspan='" + (jgbh.length == 6?11:(jgbh.length == 2?10:11))	 + "' align='center'>此条件下没有数据</td>";
	   str += "</tr>";
    } else {
      var types = ["信息文件","调研文件"];
      function getFileType(tindex) {
			var type = "待定";
			if (tindex == "1") {
				type = "信息文件";
			} else if (tindex == "2") {
				type = "调研文件";
			}
			return type;
		}
      function getDealState(sindex){
    	  var state = "";
    	  if(sindex == "0"){
    		  state = "最新报送";
    	  }else if(sindex == "1"){
    		  state = "重复材料";
    	  }else if(sindex == "2"){
    		  state = "采用";//省厅
    	  }else if(sindex == "3" ){
    		  state = "未采用";
    	  }else if(sindex == "4"){
    		  state = "采用";//部局
    	  }
    	  return state;
      }
      for(var  i=0;i<rows.length;i++){
		results = rows[i].childNodes;
		str += "<tr align='center' title = '" + results[1].text + "'>";
		str += "<td width='4%' class='td_r_b td_font'>"+(i+1)+"</td>";
		str += "<td width='15%' class='td_r_b td_font'>"+results[2].text+"</td>";
	    str += "<td width='15%' class='td_r_b td_font'>"+results[3].text+"</td>";
	    str += "<td width='8%' class='td_r_b td_font'>"+results[4].text+"</td>";
	    str += "<td width='15%' class='td_r_b td_font'>"+showFile (results[5].text)+"</td>"; // 填报时间
	    str += "<td width='15%' class='td_r_b td_font'>"+showFile (results[6].text)+"</td>";
	    str += "<td width='8%' class='td_r_b td_font'>"+getFileType(results[11].text)+"</td>";
	    str += "<td width='8%' class='td_r_b td_font'>"+getDealState(results[10].text)+"</td>";
	    str += "<td width='4%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\"" + " onClick=\"onZhiduiClick('" + results[0].text+"')\"" + "></td>";
		
		//Modification finished
	    if("1001,1006".indexOf(appid) != -1){
	    	//Modify by Xiayx 2011-8-30
	    	//支队总队都可以处理，支队在总队为处理时，可以修改
	    	// 支队用户时
	    	if (jgbh.length == 4) {
	    		if(results[10].text == "0" && results[8].text == jgid){
	    			str += "<td width='4%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\"" + " onClick=\"onZongduiClick('" + results[0].text+"')\"" + "></td>"
	    		}else{
	    			str += "<td width='4%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/lock.png\" onClick=\"alert('已处理或他人上报信息不可处理！');\" ></td>"
	    		}
	    	} else if(jgbh.length == 2){
	    		str += "<td width='4%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\"" + " onClick=\"onZongduiClick('" + results[0].text+"')\"" + "></td>"
	    	}
	    }
		str += "</tr>";
		}
	}
	str +="</table>";
	var pagingObj = $('pageData').up('tr').next('tr').down('td');
	pagingObj.style.textAlign = "center";
	var tabOjb = document.getElementById("pageData");
	tabOjb.innerHTML = str;
	Popup.prototype.hideTips();
	//Modify by Xiayx 2011-8-30
	//关闭页面，重新加载，跳转到上次页面
	if(window.toPageNum){
		onTurnToPage(toPageNum);
		toPageNum = null;
	}
	//Modification finished
	
}

function onZhiduiClick (newsFileid) {
	window.showModalDialog("newsFilesZhiDui.jsp?temp=1&newsFileid=" + newsFileid, "", "dialogWidth:800px;dialogHeight:600px");
}

function onZongduiClick (newsFileid) {
	window.showModalDialog("newsFilesZongDui.jsp?temp=1&newsFileid=" + newsFileid, "", "dialogWidth:800px;dialogHeight:600px");
	//Modify by Xiayx 2011-8-30
	//关闭页面，重新加载，跳转到上次页面
	window.toPageNum = window.currPage;
	//Modification finished
	doSearch();
}

/**
 * * 显示附件信息的链接<br>
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

function showFile(dataBasefilePath) {
	if(dataBasefilePath == "null"){
		dataBasefilePath = "没有上传附件";
	}else{
		dataBasefilePath = UDload.getAttachHtmls(dataBasefilePath)||"没有上传附件";
	}
	return dataBasefilePath;
}
