<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.ehl.dispatch.common.*" %>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ include file="../../../base/Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt") == null ? "" : request.getParameter("insrtOrUpdt");
	String proStr = !insrtOrUpdt.equals("0") ? "readOnly " : "";
	String deafultArea = com.appframe.common.Setting.getString("deafultArea");
	String old_cpid = StringHelper.obj2str(request.getParameter("cpid"),"");
	String lzzt = StringHelper.obj2str(request.getParameter("lzzt"),"000000");
	System.out.println("流转状态："+lzzt);
	String[] strObj = DepartmentManage.getDeptInfo(request, "1").split(",");//获取机构信息串
	String jgid = strObj[0];//机构编码
	String jgmc = strObj[1];//机构名称
	String ccbm = strObj[2];//机构层次编码
	
	String YWCCBRTREE = (lzzt.equals("000064") || lzzt.equals("000067"))?"setTree('440000000000,YWCCBR,000073,200,200,400,300');":"";
	System.out.println("YWCCBRTREE："+YWCCBRTREE);
	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptcode = ""; 	  //部门编码
	String deptname = ""; 	  //部门名称
	String personname = "";	  //姓名
	String uname = ""; 		  //帐号
	String pid = ""; 		  //人员ID
	String phone = ""; 		  //办公电话
	String mobilephone = "";  //手机
	if(prop != null){
		deptcode = (String)prop.get("BRANCHID");
		deptname = (String)prop.get("BRANCHNAME");
		personname = (String)prop.get("NAME");
		uname = (String)prop.get("USERNAME");
		pid=(String)prop.get("PERSONID");
		phone = (String)prop.get("PHONE");
		mobilephone = (String)prop.get("MOBILEPHONE");
	}
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat sdf0 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    Date date = new Date();
    String daytime = sdf.format(date);
    String daytime0 = sdf0.format(date);
    String cpid = "".equals(old_cpid)?(deptcode.substring(0,6) + daytime0):old_cpid;
    String show_cpid = cpid.substring(6);
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>警情投诉管理</title>
		<jsp:include page="../../../base/ShareInc.html" />
		<link rel="stylesheet" type="text/css" href="../../css/pagetag/pagetag.css">
	 	<link rel="stylesheet" type="text/css" href="../../css/Global.css">
	 	<link rel="stylesheet" type="text/css" href="../../css/tree.css">
	 	<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
	 	<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/ComplaintInfo.js"></script>
		<style> 
			body{
				margin-left:0;
				margin-right:0;
				height:100%;
				overflow:hidden;
			}
			.tdtitle_a{
				border-top: 0 solid #a5diec;
				border-right: 1 solid #a5diec;
				border-bottom: 1 solid #a5diec;
				border-left: 1 solid #a5diec;
				line-height: 16px;
				color: #000000;
				border-collapse : separate;
				empty-cells:show;
				text-align: right;
				
			}
			.tdvalue_a{
				border-top: 0 solid #a5diec;
				border-right: 1 solid #a5diec;
				border-bottom: 1 solid #a5diec;
				border-left: 1 solid #a5diec;
				line-height: 16px;
				color: #000000;
				border-collapse : separate;
				empty-cells:show;
				text-align: left;
			}
			.btn{
				BORDER-RIGHT: #000000 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #000000 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; 
				FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#0c9ad3, EndColorStr=#085887); 
				BORDER-LEFT: #000000 1px solid; CURSOR: hand; COLOR: #ffffff; PADDING-TOP: 2px; BORDER-BOTTOM: #000000 1px solid
			}
			.btn3_mouseover {
				BORDER-RIGHT: #2C59AA 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #2C59AA 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#D7E7FA); BORDER-LEFT: #2C59AA 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #2C59AA 1px solid
			}
			ul,li{margin: 0; padding: 0;width:100%;}
		</style>
	</head>
	<script language=javascript>
		function checkword(objectname){
			var strUrl = "complaint.complaintmanage.showWord.d";
			strUrl = encodeURI(strUrl);
			var params = "ID=01";
			new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseWord(objectname)});
		}
		var showResponseWord=function(objectname){
			return function(xmlHttp){ 
				//alert(objectname);
				//alert(xmlHttp.responseText);
				var wordvalue=document.getElementById(objectname).value.toLowerCase();
				var alltxt=xmlHttp.responseText;
				var alltxtpp=alltxt.toLowerCase();
				var alltxt_xiang=alltxt.split("|");
				var alltxt_xiang1=alltxtpp.split("|");
				var inhtml="<ul>"
				var isyou=0;
				for (i=0;i<alltxt_xiang1.length;i++)
				{
				    //if (alltxt_xiang1[i].substr(0,wordvalue.length)==wordvalue)
				    //{
				       	if(alltxt_xiang[i]!=""){
				       		inhtml=inhtml+"<li onclick=\"document.getElementById('"+objectname+"').value=this.innerHTML;document.getElementById('showmenu').style.display='none';\" onmouseover=\"this.style.backgroundColor='#99C4F2'\" onmouseout=\"this.style.backgroundColor='#FFFFFF'\">"+alltxt_xiang[i]+"</li>";
				        	isyou=1;
				        }
				    //}
				}
				inhtml=inhtml+"</ul>";
				if (isyou==1)
				{
				    if(document.getElementById("showmenu").style.display == ""){
				    	   document.getElementById("showmenu").innerHTML="";
				    	   document.getElementById("showmenu").style.display="none";
				    }
				    else{
				    	   document.getElementById("showmenu").innerHTML=inhtml;
				     document.getElementById("showmenu").style.display="";
				     var xy = getAbsPoint($(objectname));
				     $('showmenu').style.left = xy.x-15  + "px";  //弹出left位置（相对于输入框
				     $('showmenu').style.top = xy.y-130 + "px"; //弹出top位置（相对于输入框
				     $(objectname).focus();					
				    }
				    
				}
				else
				{
				    document.getElementById("showmenu").innerHTML="";
				    document.getElementById("showmenu").style.display="none";
				}
				/*if (wordvalue=="")
				{
				    document.getElementById("showmenu").innerHTML="";
				    document.getElementById("showmenu").style.display="none";
				}*/
			};
		}
		function getAbsPoint(e){
		   var x = e.offsetLeft;
		   var y = e.offsetTop;
		   while(e = e.offsetParent){
		      x += e.offsetLeft;
		      y += e.offsetTop;
		   }
		   return {"x": x, "y": y};
		}
		function closeword()
		{
			document.getElementById("showmenu").innerHTML="";
			document.getElementById("showmenu").style.display="none";
		}
		function insertword(objectname){
			var value = $(objectname).value;
			if(value!=""){
				var params = {};
				params["NAME"] = value;
				var url = "complaint.complaintmanage.insertWord.d";
				url = encodeURI(url);
				new Ajax.Request(url, {method:"post", parameters:params, onComplete:showResultWordInsert(objectname)});
			}
		}
		var showResultWordInsert=function(objectname){
			return function(xmlHttp){ 
				alert(xmlHttp.responseText);
				closeword();
				checkword(objectname);
			};
		}
	</script>
	<!-- <body bgcolor="#FFFFFF" text="#000000" onload="init(_</%=lzzt %/>);"> -->
	<body style='overflow:hidden;overflow-x:hidden' bgcolor="#FFFFFF" text="#000000" onload="getInfo('<%=lzzt %>');">
	<%--onload="getInfo('<%=lzzt %>');"--%>
		<table id="dpTable" width="717" border="0" cellpadding="0" cellspacing="0" style="txt-align:center;position:absolute;top:2;height:516">
			<tr>
				<td style="txt-align:center" valign="top">
					<table width="100%" border="0" style="txt-align:center" cellpadding="0" cellspacing="0">
						<tr>
							<td>
								<fieldset style="width:99%;height:95%;border:1px solid #a5d1ec" align="center" id="fieldset">
								<b><legend id="title_text" style="border:0px;font-size: 11pt;">警情投诉管理</legend></b>
								<table width="100%" height="88%" border="0" align="center">
									<tr>
										<td width="1%">
										<td width="99%">
											<div id="tagContentdNo">
											    	<span style="width:98%; color:blue; text-align:right">总队值班电话：35220800</span>
													<div id="block" style="height:100%;width:680px;overflow:hidden;offsetTop:0px;border: 1px solid #99C4F2">
														<table class="table2" width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
															<tbody class='scrollContent'>
																<tr bgColor="#ffffff">
																	<td width="10%" align="left" class="tdtitle_a" style="color:#000000">
																		登记人：
																	</td>
																	<td width="23%" class="tdvalue_a">
																		<input type="text" id="DJR_XM" readOnly value="<%=personname%>" style="width:100%;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>
																		<input type="hidden" id="DJRID" value="<%=pid%>"/>
																		<input type="hidden" id="DJJGID" value="<%=jgid%>"/>
																		<input type="hidden" id="LZZT" />
																		<input type="hidden" id="TSDJSJ" value="<%=daytime%>"/>
																		<input type="hidden" id="ZDJGID" onpropertychange=""/>
																		<input type="hidden" id="ZDJGID" />
																		<input type="hidden" id="DDJGID" />
																		<input type="hidden" id="ZHKJSR" />
																		<input type="hidden" id="ZHCJSR" />
																		<input type="hidden" id="JLDJSR" />
																		<input type="hidden" id="YWCZGBM" />
																		<input type="hidden" id="YWCJSR" />
																		<input type="hidden" id="RYID" value="<%=pid%>"/>
																		<input type="hidden" id="JGID" value="<%=jgid%>"/>
																		<input type="hidden" id="OLD_LZZT" value="<%=lzzt%>"/>
																		<input type="hidden" id="JGMC" value="<%=jgmc%>"/>
																		<input type="hidden" id="deafultArea" value="<%=deafultArea%>"/>
																		<input type="hidden" id="insrtOrUpdt" value="<%=insrtOrUpdt%>"/>
																		<input type="hidden" id="OLD_CPID" value="<%=old_cpid %>"/>
																		<input type="hidden" id="CPID" value="<%=cpid %>"/>
																		<input type="hidden" id="UNITNAME" value="<%=deptname %>"/>
																		<input type="hidden" id="PERSONNAME" value="<%=personname %>"/>
																		<input type="hidden" id="DJJGMC"/>
																		
																	</td>
																	<td width="17%" align="left" class="tdtitle_a" style="color:#000000">
																		处理单号：
																	</td>
																	<td width="15%" class="tdvalue_a" colspan="2">
																		<input type="text" value="<%=show_cpid%>" id="SHOW_CPID" readOnly style="border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>
																	</td>
																	<td width="25%" class="tdvalue_a" style="color:#000000;text-align:right;">
																		<input type="text" id="TSDJSJ_Y" value="<%=daytime0.substring(0,4)%>" readOnly style="width:40px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>年
																		<input type="text" id="TSDJSJ_M" value="<%=daytime0.substring(4,6)%>" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>月
																		<input type="text" id="TSDJSJ_D" value="<%=daytime0.substring(6,8)%>" readOnly style="width:20px;border-top: 0 solid #000000;border-bottom: 1 solid #000000;border-left: 0 solid #000000;border-right: 0 solid #000000;"/>日
																	</td>
																</tr>
																<tr id="tr_1">
																	<td align="left" class="tdtitle_a" width="23%">
																		投诉人：
																	</td>
																	<td class="tdvalue_a" width="10%">
																		<input type="text" id="TSRXM" class="textwidth" />
																	</td>
																	<td align="left" class="tdtitle_a" width="17%">
																		联系电话：
																	</td>
																	<td class="tdvalue_a" width="6%">
																		<input type="text" id="TSRDH" class="textwidth" />
																	</td>
																	<td align="left" class="tdtitle_a" width="12%">
																		被投诉辖区：
																	</td>
																	<td class="tdvalue_a" id="TSJGS" width="25%">
																		<script type="text/javascript">
																			fillListBox("TSJGS","TSJG","150","select jgid,jgmc from t_sys_department order by jgid","请选择","");
																		</script>
																	</td>
																</tr>
																<tr id="tr_2">
																	<td align="left" class="tdtitle_a">
																		投诉业务类别：
																	</td>
																	<td colspan="5" class="tdvalue_a">
																		<div style="width:130px;height:100%;float:left">
																			<input type="checkbox" value="101000" name="TSYWLB" id="TSYWLB_1" onclick="optionCtrl('TSYWLB_1')" tagName="交通违法"/><b>交通违法</b>
																			<div id="TSYWLB_1_d">
																				<input type="checkbox" value="101001" name="TSYWLB" id="TSYWLB_2" tagName="测速处罚"/>测速处罚
																				<br />
																				<input type="checkbox" value="101002" name="TSYWLB" id="TSYWLB_3" tagName="现场执法纠违"/>现场执法纠违
																				<br />
																				<input type="checkbox" value="101003" name="TSYWLB" id="TSYWLB_4" tagName="交通违法数据更新"/>交通违法数据更新
																			</div>
																		</div>
																		<div style="width:130px;height:100%;float:left">
																			<input type="checkbox" value="102000" name="TSYWLB" id="TSYWLB_5" onclick="optionCtrl('TSYWLB_5')" tagName="事故处理"><b>事故处理</b>
																			<div id="TSYWLB_5_d">
																				<input type="checkbox" value="102001" name="TSYWLB" id="TSYWLB_6" tagName="办案拖拉不结案"/>办案拖拉不结案
																				<br />
																				<input type="checkbox" value="102002" name="TSYWLB" id="TSYWLB_7" tagName="定责不公"/>定责不公
																				<br />
																				<input type="checkbox" value="102003" name="TSYWLB" id="TSYWLB_8" tagName="赔偿问题未解决"/>赔偿问题未解决
																			</div>
																		</div>
																		<div style="width:130px;height:100%;float:left">
																			<input type="checkbox" value="103000" name="TSYWLB" id="TSYWLB_9" onclick="optionCtrl('TSYWLB_9')" tagName="车管办证"><b>车管办证</b>
																			<div id="TSYWLB_9_d">
																				<input type="checkbox" value="103001" name="TSYWLB" id="TSYWLB_10" tagName="驾驶证注销迁转"/>驾驶证注销迁转
																				<br />
																				<input type="checkbox" value="103002" name="TSYWLB" id="TSYWLB_11" tagName="车辆过户"/>车辆过户
																				<br />
																				<input type="checkbox" value="103003" name="TSYWLB" id="TSYWLB_12" tagName="考驾照"/>考驾照
																			</div>
																		</div>
																		<div style="width:130px;height:100%;float:left">
																			<input type="checkbox" value="104000" name="TSYWLB" id="TSYWLB_13" tagName="其他"><b>其他</b>
																			<div id="TSYWLB_13_d"></div>
																		</div>
																	</td>
																</tr>
																<tr id="tr_16">
																	<td align="left" class="tdtitle_a">
																		投诉业务类别：
																	</td>
																	<td colspan="5" class="tdvalue_a">
																		<input type="hidden" id="TSYWZ" />
																		<input type="text" name="TSYWZLB" id="TSYWZLB" class="textwidth"/>
																	</td>
																</tr>
																<tr id="tr_3">
																	<td align="left" class="tdtitle_a">
																		驾驶证号：
																	</td>
																	<td class="tdvalue_a">
																		<input type="text" id="TSRJZ" class="textwidth" />
																	</td>
																	<td align="left" class="tdtitle_a">
																		车牌号：
																	</td>
																	<td class="tdvalue_a">
																		<span id="TSRCP_TD">
													                    	<script language="javascript">
														                       fillListBox("TSRCP_TD","TSRCP_AERA","40","select distinct substr(dm, 0, 1) as a,substr(dm, 0, 1) from t_sys_code where dmlb = '011006' and dm not like '<%=deafultArea%>%' order by a","<%=deafultArea%>");
													                        </script>
													                    </span>
												                    	<input <%= proStr %> id="TSRCP" style="width:65px" class="textwidth" type="text" maxLength="6"/>
																		<%--<input type="text" id="TSRCP" class="textwidth" />--%>
																	</td>
																	<td align="left" class="tdtitle_a">
																		被投诉警员警号：
																	</td>
																	<td class="tdvalue_a">
																		<input type="text" id="TSJH" class="textwidth" />
																	</td>
																</tr>
																<tr id="tr_4">
																	<td align="left" class="tdtitle_a">
																		投诉内容：
																	</td>
																	<td colspan="5" class="tdvalue_a">
																		<textarea rows="3" name="TSNR" id="TSNR" class="textwidth" ></textarea>
																	</td>
																</tr>
																<!-- 杜（追加） -->
																<form  id="complaintEditForm" name="complaintEditForm" action="complaint.complaintmanage.saveComplaintInfo.d" enctype="multipart/form-data" method="post" target="temp">
																	<tr id="tr_18">
																	    <td align="left" class="tdtitle_a">附件描述：</td>
																		<td class="tdvalue_a" align="left" id="fileInfoList">
																		    <input type="hidden" id="insertYwid" name="insertYwid" value="<%=cpid %>"/>
																			<input type="text" id="fileInfo0" name="fileInfo0" maxlength="128" class="textwidth" />
																		</td>
																		<td align="left" class="tdtitle_a">
																			 附件路径：
																		</td>
																		<td align='left' dclass="currentLocation" colspan="3" id="fileList" border-top="#000000;">
																			<input ID="mfile0" type="file" name="mfile0" style="width:200px;" onkeydown="return false;" onchange="setDisable();" />
																			<input id="buttonId" type="button" value="继续添加" onclick="addFile();" disabled/>
																		</td>
																	</tr>
																</form>	
																<!-- 杜（追加） -->
																<tr id="tr_5">
																	<td align="left" class="tdtitle_a">
																		交通指挥科意见：
																	</td>
																	<td class="tdvalue_a" colspan="5">
																		<div id="trafficKeNotion"> </div>
																		<input type="hidden" id="ZHKLD" />
																		<input id="ZHKYJ" type="text" name="ZHKYJ" class="textwidth" onclick="checkword('ZHKYJ')" style="width:82%;"/>
																		<input type="button" id="ZHKYJinsert" value="添加常用语" class="btn" onclick="insertword('ZHKYJ');"/>
																		<div style="background-color:#FFFFFF; position:absolute; width:300px; height:80px; z-index:1; border:1px solid #000000; display:none; overflow:auto;" id="showmenu" onblur="closeword()"></div>
																		<script type="text/javascript">
																			$('ZHKYJ').hide();
																			$('ZHKYJinsert').hide();
																		</script>
																	</td>
																</tr>
																<tr id="tr_6">
																	<td align="left" class="tdtitle_a">
																		交通指挥处意见：
																	</td>
																	<td class="tdvalue_a" colspan="5">
																		<div id="trafficChuNotion"> </div>
																		<input type="hidden" id="ZHCLD" />
																		<input type="text" name="ZHCYJ" id="ZHCYJ" class="textwidth" onclick="checkword('ZHCYJ')" style="width:82%;"/>
																		<input type="button" id="ZHCYJinsert" value="添加常用语" class="btn" onclick="insertword('ZHCYJ');"/>
																		<div style="background-color:#FFFFFF; position:absolute; width:300px; height:80px; z-index:1; border:1px solid #000000; display:none; overflow:auto;" id="showmenu" onblur="closeword()"></div>
																		<script type="text/javascript">
																			$('ZHCYJ').hide();
																			$('ZHCYJinsert').hide();
																		</script>
																	</td>
																</tr>
																<tr id="tr_7">
																	<td align="left" class="tdtitle_a">
																		业务处意见：
																	</td>
																	<td class="tdvalue_a" colspan="5">
																		<div id="departmentNotion"> </div>
																		<input type="hidden" id="YWCZG" />
																		<input type="text" name="YWCYJ" id="YWCYJ" class="textwidth" onclick="checkword('YWCYJ')" style="width:82%;"/>
																		<input type="button" id="YWCYJinsert" value="添加常用语" class="btn" onclick="insertword('YWCYJ');"/>
																		<div style="background-color:#FFFFFF; position:absolute; width:300px; height:80px; z-index:1; border:1px solid #000000; display:none; overflow:auto;" id="showmenu" onblur="closeword()"></div>
																		<script type="text/javascript">
																			$('YWCYJ').hide();
																			$('YWCYJinsert').hide();
																		</script>
																	</td>
																</tr>
																<tr id="tr_8">
																	<td align="left" class="tdtitle_a">
																		局领导意见：
																	</td>
																	<td class="tdvalue_a" colspan="5">
																		<div id="leadingNotion"> </div>
																		<input type="hidden" id="JLD" />
																		<input type="text" name="JLDYJ" id="JLDYJ" class="textwidth" onclick="checkword('JLDYJ')" style="width:82%;"/>
																		<input type="button" id="JLDYJinsert" value="添加常用语" class="btn" onclick="insertword('JLDYJ');"/>
																		<div style="background-color:#FFFFFF; position:absolute; width:300px; height:80px; z-index:1; border:1px solid #000000; display:none; overflow:auto;" id="showmenu" onblur="closeword()"></div>
																		<script type="text/javascript">
																			$('JLDYJ').hide();
																			$('JLDYJinsert').hide();
																		</script>
																	</td>
																</tr>
																<tr id="tr_9">
																	<td align="left" class="tdtitle_a">
																		总队办理意见：
																	</td>
																	<td colspan="5" class="tdvalue_a">
																		<input type="hidden" id="ZDBLLD" />
																		<input type="text" name="ZDBLYJ" id="ZDBLYJ" class="textwidth" />
																	</td>
																</tr>
																<tr id="tr_17">
																	<td align="left" class="tdtitle_a">
																		总队联系人：
																	</td>
																	<td class="tdvalue_a">
																		<input type="hidden" id="CBR" />
																		<input type="text" name="YWCCBR" id="YWCCBR" class="textwidth" style="width:100px;border:0px" readOnly onClick="<%=YWCCBRTREE %>"/>
																	</td>
																	<td align="left" class="tdtitle_a">
																		联系电话：
																	</td>
																	<td colspan="3" class="tdvalue_a">
																		<input type="text" name="LXDH" id="LXDH" class="textwidth" style="width:100px;border:0px"/>
																	</td>
																</tr>
																<tr id="tr_10">
																	<td align="left" class="tdtitle_a">
																		支队意见：
																	</td>
																	<td colspan="5" class="tdvalue_a">
																		<input type="hidden" id="ZDLD" />
																		<input type="text" name="ZDYJ" id="ZDYJ" class="textwidth"/>
																	</td>
																</tr>
																<!-- 杜（追加） -->
																<form  id="ZdFileUploadForm" name="ZdFileUploadForm" action="complaint.complaintmanage.saveZdFileInfo.d" enctype="multipart/form-data" method="post" target="temp">
																<input type="hidden" id="insertYwid" name="insertYwid" value="<%=cpid %>"/>
																	<tr id="tr_19">
																		<td align="left" class="tdtitle_a">
																			支队办理结果附件：
																		</td>
																		<td colspan="5" class="tdvalue_a" id="fileZdResult">
																			<input type="file" disabled name="ZDAttachmentFile" id="ZDAttachmentFile" class="textwidth" onkeydown="return false;"/>
																		</td>
																	</tr>
																</form>
																<!-- 杜（追加） -->
																<tr id="tr_11">
																	<td align="left" class="tdtitle_a">
																		办理结果：
																	</td>
																	<td colspan="5" class="tdvalue_a" valign="top">
																		<span style="border-left:56px solid black;"></span>
																		<input type="checkbox" value="1" name="ISREPLYMASSES" id="ISREPLYMASSES_1" onclick="single('1')">
																		已答复群众
																		<input type="checkbox" value="0" name="ISREPLYMASSES" id="ISREPLYMASSES_2" onclick="single('2')">
																		未答复群众
																		<br>
																		<span style="height:50px">情况说明：</span><textarea rows="3" name="BLJGSM" id="BLJGSM" class="textwidth" style="width:85%"></textarea>
																				<%--<input type="text" id="BLJGSM" class="textwidth" style="width:85%"/>--%>
																		<span style="border-left:12px solid black;"></span>经办人：<input type="text" name="JBLXR" id="JBLXR" class="textwidth" style="width:150px" />
																		<br>
																		<span style="border-left:24px solid black;"></span>日期：<input type="text" name="SPRQ" id="SPRQ" class="textwidth" style="width:150px" readOnly onClick="SelectDate(this,0,'up');"/>
																	</td>
																</tr>
																<tr id="tr_12">
																	<td colspan="6" class="tdvalue_a">
																		无用信息行
																	</td>
																</tr>
																<tr id="tr_13">
																	<td colspan="6" class="tdvalue_a">
																		<%--<span style="border-left:20px solid black;"></span>经办单位：
																		--%><input type="hidden" name="JBDW" id="JBDW" class="textwidth" style="width:350px;border:0px" />
																		<%--<span style="border-left:44px solid black;"></span>电话：
																		--%><input type="hidden" name="JBLXRDH" id="JBLXRDH" class="textwidth" style="width:150px;border:0px" />
																		<%--<span style="border-left:12px solid black;"></span>审批人：
																		--%><input type="hidden" name="SPR" id="SPR" class="textwidth" style="width:131px;border:0px" />
																	</td>
																</tr>
																<tr id="tr_14">
																	<td colspan="6" class="tdvalue_a">
																		<span style="border-left:65px solid black;"></span>修改人：
																		<input type="text" name="XGR" id="XGR" class="textwidth" style="width:167px;border:0px" />
																		修改时间：
																		<input type="text" name="XGSJ" id="XGSJ" class="textwidth" style="width:131px;border:0px" readOnly onClick="SelectDate(this,0,'up');"/>
																	</td>
																</tr>
																<tr id="tr_15" class="Noprint" >
																	<td align="right" colspan="6" class="Noprint" >
																		<input type="button" id="btn_1" class="btn"/>
																		<input type="button" id="btn_2" class="btn"/>
																		<input type="button" id="btn_3" class="btn"/>
																		<input type="button" id="btn_4" class="btn"/>
																		<input type="button" id="btn_5" value="打印" class="btn"/>
																		<%--<input type="button" id="btn_6" value="重置" />--%>
																		<input type="button" value="关闭" id="close" onclick="window.close();" class="btn"
																			onmouseover="this.className='btn'"
																			onmouseout="this.className='btn'"
																			onmousedown="this.className='btn'"
																			onmouseup="this.className='btn'"/>
																	</td>
																</tr>
															</tbody>
														</table>
													</div>
												
											</div>
										</td>
									</tr>
								</table>
								</fieldset>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
</html>