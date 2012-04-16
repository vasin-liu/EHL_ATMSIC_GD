<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appframe.data.sql.DBHandler"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%@include file="../../Message.oni"%>
<%
	
	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptcode = ""; //部门编码
	String deptname = ""; //部门名称
	String personName = ""; //姓名
	String uname = ""; //帐号
	String pid = ""; //办公电话
	String phone = ""; //办公电话
	String mobilephone = ""; //手机
	if (prop != null) {
		deptcode = (String) prop.get("BRANCHID");
		deptname = (String) prop.get("BRANCHNAME");
		personName = (String) prop.get("NAME");
		uname = (String) prop.get("USERNAME");
		pid = (String) prop.get("PERSONID");
		phone = (String) prop.get("PHONE");
		mobilephone = (String) prop.get("MOBILEPHONE");
	}
	
	String jgbh = "";//总队(2位),支队(4位),大队(6位)
	if ("0000".equals(deptcode.substring(2, 6))) {
		jgbh = deptcode.substring(0, 2);
	} else if ("00".equals(deptcode.substring(4, 6))) {
		jgbh = deptcode.substring(0, 4);
	} else {
		jgbh = deptcode.substring(0, 6);
	}
	personName = StringHelper.obj2str(DispatchUtil.getDutyPersonNameByDepId(deptcode),"");
	Object apname = session.getAttribute(Constant.ZBLDXM_VAR);
	apname = apname == null ? "" : apname;
	
	//添加1,查看2,修改3
	String ptype = StringHelper.obj2str(request.getParameter("ptype"),"1");
	String id = StringHelper.obj2str(request.getParameter("id"), "");
	String stype = StringHelper.obj2str(request.getParameter("stype"), "");
	String rwone="",rwtwo="";
	if(ptype.equals("1")){
		rwone = "";
		rwtwo = "";
	}else if(ptype.equals("2")){
		rwone = "disabled";
		rwtwo = "disabled";
	}else if(ptype.equals("3")){
		rwone = "disabled";
		rwtwo = "";
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>协查通报</title>
		<link rel="stylesheet" type="text/css" href="../../../base/css/style1/tab.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
		<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
		<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css">
		<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="../../../base/js/tree/tree.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../js/ccommon/Department.js"></script>
		<script type="text/javascript" src="../../js/cpolicequery/xcbk.js"></script>
		<script type="text/javascript" src="../../js/ccommon/calendar/DateAndSchar.js"></script>
		<script type="text/javascript" src="../../js/ccommon/Flow.js"></script>
		<style type="text/css">
			.btn{
				BORDER-RIGHT: #000000 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #000000 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; 
				FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#0c9ad3, EndColorStr=#085887); 
				BORDER-LEFT: #000000 1px solid; CURSOR: hand; COLOR: #ffffff; PADDING-TOP: 2px; BORDER-BOTTOM: #000000 1px solid
			}
			.table2{
				background:#ffffff;
				border-top: 1 solid #106ead;
				border-right: 1 solid #106ead;
				border-bottom: 1 solid #106ead;
				border-left: 1 solid #106ead;
				border-collapse:collapse;
				font-size:11px;
				text-align: center;
			}
			.tdtitle{
				border-top: 0 solid #000000;
				border-right: 1 solid #a5d1ec;
				border-bottom: 1 solid #a5d1ec;
				border-left: 1 solid #a5d1ec;
				line-height: 16px;
				color: #000000;
				border-collapse : separate;
				empty-cells:show;
				text-align: center;
				width:12%;
				height: 35px;
				
			}
			.tdvalue{
				border-top: 0 solid #000000;
				border-right: 1 solid #a5d1ec;
				border-bottom: 1 solid #a5d1ec;
				border-left: 1 solid #a5d1ec;
				line-height: 16px;
				color: #000000;
				border-collapse : separate;
				empty-cells:show;
				text-align: left;
				width:16%;
				height: 35px;
			}
			
			.textwidth{
				width: 160;
			}
			/*文本框变下划线*/
			.text {
			    font: 14px Tahoma, Verdana;
				border: 0;
				border-bottom: 1 solid black;
				background: ;
				text-align:center;
				/*padding-left:10px;*/
				width:62px;
			}
			
		</style>
	<script type="text/javascript" src="../../js/ccommon/Move.js"></script>
	</head>
	<body bgcolor="#FFFFFF" onload="setGlobel();initPage()">
		<div style="width: 100%;">
			<fieldset style="width:99%;height:95%;border:1px solid #a5d1ec"
				align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr class="wTableTopCenter">
						<td width="5%">
							<div align="center">
								<img src="../../../base/image/cssimg/table/tb.gif" width="16" height="16" alt="img" />
							</div>
						</td>
						<td width="70%" class="currentLocation">
							<span class="currentLocationBold"><b>您当前的位置</b>
							</span>：协查通报
						</td>
					</tr>
				</table>
				<table class="table2" width="100%">
					
					<tr style="display: none">
						<!-- 用户默认值设置 -->
						<td>
							<input id="uname" type="hidden" value="<%=uname%>" />
							<input id="jgid" type="hidden" value="<%=deptcode%>" />
							<input id="jgbh" type="hidden" value="<%=jgbh%>" />
<%--							<input id="jgmc" type="hidden" value="<%=deptname%>" />--%>
							<input id="zbrxm" type="hidden" value="<%=personName%>" />
						</td>
						<!-- 页面值设置 -->
						<td>
							<input id="ptype" type="hidden" value="<%=ptype%>" />
							<input id="id" type="hidden" value="<%=id%>" />
							<input id="stype" type="hidden" value="<%=stype%>" />
						</td>
					</tr>
					<form>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							号牌号码<input id="carnumber" type="hidden" value="" />
						</td>
						<td class="tdvalue" colspan="3" style="white-space: nowrap;" value="">
							<div id="carnumberPreDiv" style="display: inline;"></div>
							<input id="carnumberPost" type="text" size="5" maxlength="6" onblur="setCarNumber()" <%=rwone %>> &nbsp;&nbsp;(<font color="red" size="2">必填</font>，缺位数字用*代替)
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							号牌类型 
						</td>
						<td id="cartypeTd" class="tdvalue" >
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							车辆颜色
						</td>
						<td id="carcolorTd" class="tdvalue">
						</td>
					</tr>
					<tr >
						<td class="tdtitle" bgcolor="#F0FFFF">
							协查通报
						</td>
						<td class="tdvalue" colspan="3">
							<textarea id="content" style="width:556px;height:150px;" <%=rwtwo%>></textarea>
						</td>
					</tr>
					<tr >
						<td class="tdtitle" bgcolor="#F0FFFF">
							联系人单位
						</td>
						<td class="tdvalue">
							<input id="lpdname" type="text" value=""  <%=rwone %> class="textwidth" >&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							联系人姓名
						</td>
						<td class="tdvalue">
							<input id="lpname" type="text" value="" <%=rwone %> class="textwidth" >&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
					</tr>
					<tr >
						<td class="tdtitle" bgcolor="#F0FFFF">
							联系人电话
						</td>
						<td class="tdvalue" colspan="3">
							<input id="lpphone" type="text" value=""  <%=rwone %> class="textwidth" >&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							填报单位
							<input id="frpdcode" type="hidden" value="<%=deptcode%>"  />
						</td>
						<td class="tdvalue">
							<input type="text" id="frpdname" disabled  class="textwidth" value="<%=deptname.replace("公安局交通警察","交警")%>">
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							审核人
						</td>
						<td class="tdvalue">
							<input type="text" id="apname" class="textwidth"  <%=rwone %> value="<%=apname%>">&nbsp;&nbsp;<font size='1' color='red'>※</font>
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							填报时间
						</td>
						<td class="tdvalue">
							<input type="text" id="frtime"   disabled class="textwidth"  >
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							填报人
						</td>
						<td class="tdvalue">
							<input type="text" id="frpname" class="textwidth"  <%=rwone %> value="<%=personName%>">&nbsp;&nbsp;<font size='1' color='red'>※</font>
						</td>
					</tr>
                    <tr id="adTr" style="display: none;" height="35" >
                        <td class="tdtitle" align="center" bgcolor="#F0FFFF">
                       	      接收单位
						 <input type="hidden" id="jgmcId" value="" />
                        </td>
                        <td id="adTd" class="tdvalue" colspan="3" >
                            <input id="jgmc" type="text" value="" style="width: 558px;" readonly="readonly"/>
	                        <img id="adImg" alt="选择机构" src="../../images/popup/btnselect.gif" onclick="setTree('<%=deptcode%>','100','480',null,'11')" style="cursor:hand;"/>&nbsp;&nbsp;<font size="1" color="red">※</font>
                        </td>
	                </tr>
	                <!-- Modified by Liuwx 2011-07-01 -->
	                <!-- 转发单位，只有总队用户才能查看 -->
	                <tr id="forwardTr" style="display: none;" height="35" >
                        <td class="tdtitle" align="center" bgcolor="#F0FFFF">
                       	      转发单位
                        </td>
                        <td id="forwardTd" class="tdvalue" colspan="3" >
                        </td>
	                </tr>
	                <!-- Modification finished -->
	                </form>
				</table>
			</fieldset>
		</div>
		<div id="buttons" style="width:95%;text-align: center;"></div>
	</body>

</html>
