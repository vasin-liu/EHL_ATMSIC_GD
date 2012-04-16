<%@page language="java"  import="java.util.*" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ include file="../../../sm/Message.oni"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@page import="com.ehl.sm.common.Constants"%>
<%@page import="fangwei.DES" %>
<%
	
	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	//当前用户信息
	String deptCd = ""; //部门编码
	String deptname = ""; //部门名称
	String uname = ""; //帐号
	String pid = ""; //办公电话
	String phone = ""; //办公电话
	String mobilephone = ""; //手机
	String usercode = ""; //用户代码
	if (prop != null) {
		deptCd = (String) prop.get("BRANCHID");
		deptname = (String) prop.get("BRANCHNAME");
		uname = (String) prop.get("USERNAME");
		pid = (String) prop.get("PERSONID");
		phone = (String) prop.get("PHONE");
		mobilephone = (String) prop.get("MOBILEPHONE");
		usercode = (String) prop.get("USERCODE");
	}
	
	String deptcode = "";
	String deptName = "";
	String jgccbm="";//机构层次编码
	String user = (String)session.getAttribute(Constants.PNAME_KEY);
	String jgbh = "";//总队(2位),支队(4位),大队(6位)
   try{
	   String deptInfo =  DepartmentManage.getDeptInfo(request,"1");
	   deptcode = deptInfo.split(",")[0];
	   deptName = deptInfo.split(",")[1];
	   jgccbm=deptInfo.split(",")[2];//机构层次编码
	if ("0000".equals(deptcode.substring(2, 6))) {
		jgbh = deptcode.substring(0, 2);
	} else if ("00".equals(deptcode.substring(4, 6))) {
		jgbh = deptcode.substring(0, 4);
	} else {
		jgbh = deptcode.substring(0, 6);
	}
   }catch(Exception e){
       e.printStackTrace();
   }
   DES des = new DES("a8e5d4f8");//创建对象，赋予密钥
	
	SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String reTime = format2.format(new Date());
	String encryptResult = des.encrypt(user + "_" + reTime);//2010-10-01 22:12:03");
 
    //System.out.println(reTime + "  " + encryptResult);
  
  %>
<html xmlns:v="urn:schemas-microsoft-com:vml">
	<head>
	    <title>动态信息发布</title>
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/map.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/bubble.css" />
		<link type="text/css" rel="Stylesheet" href="../../../webgis/css/contents.css" />
		<script type="text/javascript">
	    	var deptidForCenter = '<%=deptcode%>';
	    	var deptNameForCenter = '<%=deptname%>';
			var jgccbm = '<%=jgccbm%>';
			var userName = '<%=user%>';
			var encryptResult ='<%=encryptResult%>';
			//var timea ='<%=reTime%>';
			//alert(encryptResult + " and " + timea);
			//jquery的json插件，需要引用jQuery函数
			function jQuery(){}
	    </script>
	    <link href="${contextPath}/dynamicinfo/css/text.css" rel="stylesheet" />
	    <link href="${contextPath}util/artdialog/skins/default.css" rel="stylesheet" />
		<script src="${contextPath}util/artdialog/artDialog.js"></script>
		<script src="${contextPath}util/jquery/jquery.json-2.3.min.js"></script>
		<script src="${contextPath}base/js/new/api.date.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/Util.js"></script>
		<script type="text/javascript" src="../../../webgis/script/map/LoadLibFile.js"></script>
		<script type="text/javascript" src="../../js/dynamicinfo/pcs/PcsMap.js"></script>
		<script type="text/javascript" src="../../../base/js/new/base.js"></script>
		<script type="text/javascript" src="../../js/dynamicinfo/policeRemind/policeRemind.dynamic.update.js"></script>
		<script type="text/javascript" src="../../js/dynamicinfo/policeRemind/policeRemind.dynamic.js"></script>
		<script type="text/javascript" src="../../js/dynamicinfo/DynamicInfoTool.js"></script>
        <!--  <script type="text/javascript" src="../../js/dynamicinfo/forbid/forbidMap.js"></script> -->
		<script type="text/javascript" src="../../js/dynamicinfo/forbid/forbidTool.js"></script>
		<script type="text/javascript" src="../../js/dynamicinfo/tgs/tgsAll.js"></script>
		<script type="text/javascript" src="../../js/dynamicinfo/accNow/accNow.js"></script>
		<script type="text/javascript" src="../../js/dynamicinfo/accaccHistory/accaccHistory.js"></script>
		<script type="text/javascript" src="../../js/dynamicinfo/roadBuildShow/roadBuildShow.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>	
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../../tfm/js/tfm/TfmMap.js"></script>
		<script type="text/javascript" src="../../js/dynamicinfo/tfm/Crowd.js"></script>
		<script type="text/javascript" src="../../../dispatch/js/ceditpolice/editTrfficeCrowd.js"></script>
		<script type="text/javascript" src="../../../cctv/js/device/VidiconInfoMap.js"></script>
		<script type="text/javascript" src="../../js/dynamicinfo/DynamicInfoMap.js"></script>
		
	 <style type="text/css">
	 		A.a01{text-decoration:none;};
			A.a01:link {text-decoration:none;} 
			A.a01:visited {text-decoration:none;} 
			A.a01:hover {text-decoration:none;color:#666666} 
			.currentLocationBold {
				font-size: 14px;
				font-weight: bold;
				color:#FF0000;
			}
			.table3{
				border-top: 0 solid #106ead;
				border-right: 0 solid #106ead;
				border-bottom: 0 solid #106ead;
				border-left: 0 solid #106ead;
				border-collapse:collapse;
				font-size:11px;				
			}
			.dischecked {
				color:gray;
				font-weight: bold;
			}
		</style>
		
	</head>
<!-- 初始化方法位于DynamicInfo.js中 -->
<body align="center">
	<input id="appid" type="hidden" value="${appid}" />
	<input type="hidden" id="jgid" value="<%=deptcode%>" />
	<input type="hidden" id="jgbh" value="<%=jgbh%>" />
	<input type="hidden" id="user_code" value="<%=usercode%>" />
	<input type="hidden" id="uname" value="${uname}" />
	<input type="hidden" id="operate" value="${operate}" />
	  <!-- 地图显示 -->
		 <table width="99%" height="100%" align="center" border="1">
		    <tr>
		        <td width="71%">
			       <table width="100%" height="100%" align="center">
					    <tr>
					       <td colspan='2'>
					          <div id="map" style="position: relative;  width: 100%; height:98%; left: 0px; top: 0px;">
					          </div>
					       </td>
					    </tr>
					</table>
				</td>
		    </tr>
		 </table>
	 </body>
	<script type="text/javascript" src="../../../webgis/script/map/MapConfig.js"></script>
  	<script type="text/javascript" src="../../js/dynamicinfo/DynamicInfoMapConfig.js"></script>
	<script type="text/javascript" src="../../js/DynamicInfo.js"></script>
	<script type="text/javascript" src="../../js/privilege.js"></script>
</html>