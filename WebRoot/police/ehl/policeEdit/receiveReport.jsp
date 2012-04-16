<%@page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ehl.dispatch.util.*" %>
<%
 	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptcode = ""; //部门编码
	String deptname = ""; //部门名称
	String depttype = ""; //机构类型   
	String pname = ""; //姓名
	String uname = ""; //帐号
	if(prop != null){
		deptcode = (String)prop.get("BRANCHID");
		deptname = (String)prop.get("BRANCHNAME");
		depttype = (String)prop.get("DEPTTYPE");
		pname = (String)prop.get("DEPTTYPE");
		uname = (String)prop.get("USERNAME");
	}
%>
<html>
<%
 	String feedbackid = request.getParameter("feedbackid");
 	String alarmid = request.getParameter("alarmid");
 	String deptcode1 = request.getParameter("deptcode");
%>
<head>
<script type="text/javascript">
   var deptcode = '<%=deptcode1%>';
  // var receivetype ='公安网';
   var receivetype='214003';
   var uname = '<%=deptcode1%>'
   //取得当前时间   
    var now= new Date();   
    var year=now.getYear();   
    var month=now.getMonth()+1;   
    var day=now.getDate();   
    var hour=now.getHours();   
    var minute=now.getMinutes();   
    var second=now.getSeconds();   
    var nowdate=year+"-"+month+"-"+day+" "+hour+":"+minute;   
    var receivetime = nowdate;
    var feedbackid = '<%=feedbackid%>';
    var alarmid = '<%=alarmid%>';
    var depttype1 = '<%=depttype%>';
   // feedbackid = '0000000004';
</script>
<script src="../../js/common/Prototype.js" type="text/javascript"></script>
<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
<script type="text/javascript" src="../../../sm/js/common/global.js"></script>
<script type="text/javascript" src="../../js/common/utility/CalendarDateTime.js"></script>
<script type="text/javascript" src="../../js/editPolice/editReceive.js"></script>
<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
<link rel="stylesheet" type="text/css" href="../../css/Global.css" />
<link rel="stylesheet" type="text/css" href="../../css/vcas.css" />
<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css" />
<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css" /> 
<title>事故接报信息</title>
</head>
<body onLoad="initData();">
    <div style="text-align: center;width: 100%;height: 100%;">
    <fieldset style="width:99%;height:95%;border:1px solid #CCCCCC" align="center">
			<legend style="border:0px;font-size: 12pt;">警情上报审核信息情况</legend>
    <table class="table2" width="100%">
	<tr>
	   <td style="width:30%" class="tdtitle" bgcolor="#99c4f2">接报时间：</td>
	   <td style="width:70%" class="tdvalue">
			<input type="text" id="receive_time" onKeyDown="if(event.keyCode==8){return false;};" onclick="SelectDateTime(this)" readonly style="width:100%"  name="editinput"></input>		
	   </td>
	</tr>
	<tr>
	   <td class="tdtitle" bgcolor="#99c4f2">值班人员：</td>
	   <td class="tdvalue">
			<input type="text" style="width:100%" id="duty_person" name="editinput"></input>		
	   </td>
	</tr>
	<tr>
	   <td class="tdtitle" bgcolor="#99c4f2">上报批准人：</td>
	   <td class="tdvalue">
			<input type="text" style="width:100%" id="pass_person" name="editinput"></input>		
	   </td>
	</tr>
    <tr>
		<td class="tdtitle" bgcolor="#99c4f2">信息核实情况：</td>
		<td class="tdvalue">
			<textarea  rows="5" style="width:100%" id="infoVerify_Accident" name="editinput"></textarea>		
		</td>
	</tr>
	<tr>
		<td class="tdtitle" bgcolor="#99c4f2">向领导汇报情况：</td>
	  <td class="tdvalue"><textarea  rows="5" style="width:100%" id="reportHead_Accident" name="textarea"></textarea>
	  </td>
	</tr>
	 <tr align="center">
	      <td colspan="2">
		   <input type="button" onClick="savaReceive();" value="审核" />
		   &nbsp;&nbsp;
		   <input type="reset" onClick="revalues();" value="重置" />
		  </td> 
	 </tr>
	</table>
	</div>
</body>
</html>
