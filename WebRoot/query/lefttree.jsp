<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.ehl.sm.common.Constants" %>
<%
	Object pname = session.getAttribute(Constants.PNAME_KEY);
	String userName = pname.toString();
%>
<html>
	<head>
		<title>查询及研判系统</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="STYLESHEET" type="text/css" href="../sm/css/dhtmlx/dhtmlXTree.css" />
		<link href="../sm/css/Global.css" rel="stylesheet" type="text/css">
		<link href="css/tira.css" rel="stylesheet" type="text/css">
		<script src="../sm/js/common/dhtmlx/dhtmlXCommon.js"></script>
		<script src="../sm/js/common/dhtmlx/dhtmlXTree.js"></script>
		<style type="text/css">
			<!--
				.table_tree {
					font-size: 12px;
					text-decoration: none;
					height:99%;
					width: 215px;
				    margin-top: 3px;
					border: 1px solid #97BECE;
					}
			-->
		</style>
	</head>
<body style="overflow:hidden;margin-top:0px;margin-left:0px" showUser('<%=userName%>');">
  <div id="lefttreettd" class="table_tree" align = "center"style="width:215; background-color:#ffffff; overflow-x:hidden;overflow-y:auto; margin-left:0">
    <table width="100%" border="0" cellpadding="0" cellspacing="0">               
      <tr>
        <td height="25" valign="top" background="image/back/back_navigation.gif">&nbsp;</td>
      </tr>
      <tr>
        <td valign="top" id="lefttreetd" >
          <div id="lefttreediv" style="width:205; background-color:#ffffff; overflow-x:hidden;overflow-y:auto; margin-left:0">
		    <table>
			  <tr>
			    <td valign="top">
				  <div id="treeboxbox_tree"></div>
			    </td>
			  </tr>
		    </table>
			<script>	
				tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);
				tree.setImagePath("image/tree/");
				tree.enableCheckBoxes(false);
				tree.enableDragAndDrop(false);
				tree.setOnClickHandler(onNodeSelect);
				tree.loadXML("common.treectrl.load.d?showtree=54",onOpenNode);
				      
				function onNodeSelect(){
				  var sID = tree.getSelectedItemId();
				  if (sID.substr(sID.length-4) != "main") //屏蔽根结点
				  {
					  sID = sID + ".jsp";
					  window.open(sID,"moduletarget");
				  }
				}				
				function onOpenNode(){
				  tree.openAllItems();
				}
		    </script>
	      </div>
        </td>
      </tr>        
    </table>
  </div> 
</body>
</html>

