<%@ page language="java" import="java.util.*" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="../../css/zt.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">  
<link rel="STYLESHEET" type="text/css" href="../../../sm/css/dhtmlx/dhtmlXToolbar.css"> 
<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script> 
<script type="text/javascript" src="../../../sm/js/common/dhtmlx/dhtmlXCommon.js"></script>
<script type="text/javascript" src="../../../sm/js/common/dhtmlx/dhtmlXProtobar.js"></script> 
<script type="text/javascript" src="../../../sm/js/common/dhtmlx/dhtmlXToolbar.js"></script> 
<script type="text/javascript" src="../../../sm/js/common/toolbar/toolbarManager.js"></script>
<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
<script type="text/javascript" src="../../js/classify/UniteTable.js"></script>
<script type="text/javascript" src="../../js/DhtmlPaging.js"></script>
<script type="text/javascript" src="../../js/classify/GpsGroup.js"></script>
<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
 <script type="text/javascript">
	function getFunccode() 
	{
		return '<%= request.getParameter("funURI") %>';
	}
</script>
<title>GPS车辆分组信息表</title>
</head>
<body onLoad="load();">
	<center><div id="toolbar_zone" style="width: 700; border: 1px solid Silver;">
	</div></center><br>
	<div style="width:100% ;text-align:center" >
			<font style="color:#0000FF;size:14pt;font-weight:800">GPS车辆分组信息表</font>
	</div>
	&nbsp;&nbsp;<hr style="height:1px"/>
	 <div id="tagContent" style="width:100%"  align = "center" >
	       <table  align=center border="0">
	          <tr align = "center"> 
               <td  align="right">分组名：
				</td>
		         <td id="grouptd"  > 
				   <script language="javascript">
			           fillListBox("grouptd","groupname","120","SELECT groupid,groupname FROM t_gps_groupinfo","","","","gps");
			        </script>
				 </td>
				 <td width="20px">&nbsp;&nbsp;</td>
		         <td  align="right">车辆号码：</td>
				 <td  >
				    <input name="carnumber" type="text" id="carnumber" size="15">
				 </td> 
		         <td  align="left">
                    <input type="image" src="../../images/button/btnquery.gif" name="btnnQuery" id='btniQuery' value="查询"  />   			
                 </td> 
	          </tr>         
           </table>				
      </div>
	<div id="group" style="text-align:center">
	</div>
	<div id="divPagingEle" style="text-align:center">
	</div>
</body>
</html>