<%@ page language="java" import="java.util.*" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.common.table.*"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
<link href="../../css/duty.css" rel="stylesheet" type="text/css">
<link href="zt.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="../../sm/css/Global.css">
<link rel="STYLESHEET" type="text/css" href="../../sm/css/popup/Popup.css">
<link rel="STYLESHEET" type="text/css" href="../../sm/css/dhtmlx/dhtmlXGrid.css">
<link rel="STYLESHEET" type="text/css" href="../../sm/css/dhtmlx/dhtmlXGrid_skins.css">
<link rel="STYLESHEET" type="text/css" href="../../sm/css/dhtmlx/dhtmlXToolbar.css">
<link rel="STYLESHEET" type="text/css" href="../../sm/css/dhtmlx/dhtmlXTree.css">
<script type="text/javascript" src="../../sm/js/common/prototype.js"></script>
<script type="text/javascript" src="../../sm/js/common/dhtmlx/xmlCreator.js"></script>
<script type="text/javascript" src="../../sm/js/common/dhtmlx/dhtmlXCommon.js"></script>
<script type="text/javascript" src="../../sm/js/common/dhtmlx/dhtmlXProtobar.js"></script>
<script type="text/javascript" src="../../sm/js/common/dhtmlx/dhtmlXToolbar.js"></script>
<script type="text/javascript" src="../../sm/js/common/dhtmlx/dhtmlXGrid.js"></script>
<script type="text/javascript" src="../../sm/js/common/dhtmlx/dhtmlXGridCell.js"></script>
<script type="text/javascript" src="../../sm/js/common/dhtmlx/dhtmlXGrid_start.js"></script>
<script type="text/javascript" src="../../sm/js/common/dhtmlx/dhtmlXGrid_selection.js"></script>
<script type="text/javascript" src="../../sm/js/common/dhtmlx/dhtmlXGrid_drag.js"></script>
<script type="text/javascript" src="../../sm/js/common/dhtmlx/dhtmlXTreeGrid.js"></script>   
<script type="text/javascript" src="../../sm/js/common/dhtmlx/dhtmlXGrid_excell_link.js"></script>
<script type="text/javascript" src="../../sm/js/common/output/SaveAsExcel.js"></script>
<script type="text/javascript" src="../../sm/js/common/popup/Popup.js"></script>
<script type="text/javascript" src="../../sm/js/common/global.js"></script>
<script type="text/javascript" src="../../sm/js/common/toolbar/toolbarManager.js"></script>
<script type="text/javascript" src="../../sm/js/common/page/PageCtrl.js"></script>
<script type="text/javascript" src="../../sm/js/common/list/FillListBox.js"></script>
<script type="text/javascript" src="../js/classify/UniteTable.js"></script>
<script type="text/javascript" src="../js/classify/GpsGroup.js"></script>
<script type="text/javascript" src="../../sm/toolbar.js"></script>
<title>GPS车辆分组信息表</title>
</head>
<body onLoad="load();">
	<center><div id="toolbar_zone" style="width: 700; border: 1px solid Silver;">
	</div></center><br>
	 <div id="tagContent"   align = "center" >
	    <div id="info" class='tagContent selectTag'>	
	       <table  width="100%" border="0">
	          <tr>
               <td width="15%" height="20" align="right">分组名：
				</td>
		         <td id="grouptd" width="10%"> 
				   <script language="javascript">
			           fillListBox("grouptd","groupname","120","SELECT groupid,groupname FROM t_gps_groupinfo","","");
			        </script>
				 </td>
		         <td width="15%" height="20" align="right">
				  车辆编号：
				 </td>
				 <td width="10%">
				    <input name="carnumber" type="text" id="carnumber" size="15">
				 </td> 
		         <td  align="left">
                    <input type="image" src="../images/button/btnquery.gif" name="btnnQuery" id='btniQuery' value="查询" onClick="doQueryByCon();" />   			
                 </td>
                 
	          </tr>         
           </table>				
        </div>
      </div>
	<br>
	<center><caption><H3><font color="#0000FF">GPS车辆分组信息表</font></H3></caption></center>
	<div id="group">
	</div>
</body>
</html>