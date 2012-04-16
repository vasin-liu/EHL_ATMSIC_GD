<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="com.ehl.base.Constants" %>
<html>
	<head>
		<title><msg:Common_zh.Global.navigation></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="STYLESHEET" type="text/css" href="../base/css/style2/dhtmlx/dhtmlXTree.css" />
		<link href="css/Global.css" rel="stylesheet" type="text/css">
		<script src="../base/js/global.js"></script>
		<script src="../base/js/dhtmlx/dhtmlXCommon.js"></script>
		<script src="../base/js/dhtmlx/dhtmlXTree.js"></script>
		<script src="js/common/dhtmlx/dhtmlXTree.custom.js"></script>
	</head>

	<body style="overflow:hidden;margin-top:0px;margin-left:0px" onload="adjustHeight('lefttreediv',262);">
	  <table width="220" border="0" cellspacing="0" cellpadding="0" >
	  	   <tr height="2">
	  	   	 <td></td>
	  	   </tr>
           <tr>
             <td id="lefttreettd">
               <table width="100%" border="0" cellpadding="0" cellspacing="0">               
                 <tr>
                   <td valign="top"><img src="image/back/back_navigation.gif" width="220" height="25" /></td>
                 </tr>
                 <tr>
                   <td valign="top">
                     <table width="100%" border="0" cellspacing="0" cellpadding="0">
                       <tr>
                      <td width="3" background="image/back/back_midleft.gif"  height=""><img src="" alt="" width="3" height="" align="center" /></td>
                      <td id="lefttreetd" >
                        <div id="lefttreediv" style="width:210; background-color:#ffffff; overflow-x:hidden;overflow-y:auto; margin-left:0">
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
									tree.loadXML("base.treectrl.load.d?showtree=01",onOpenNode);
									      
									function onNodeSelect(){
									  var sID = tree.getSelectedItemId();
									 
									  if (sID.substr(sID.length-4) != "main") //屏蔽根结点



									  {
										  sID = leftTree.url(sID,tree);
										  window.open(sID,"moduletarget");
									  }
									}
									
									function onOpenNode(){
										tree.initUserData();
									  tree.openAllItems();
									}
								 </script>
						</div>
					  </td>
                       <td width="3" background="image/back/back_midright.gif"><img src="" alt="" width="3" height="" align="center" /></td>
                       </tr>
                     </table>
                   </td>
                 </tr>
                 <tr>
                   <td>
                     <table width="100%" border="0" cellspacing="0" cellpadding="0">
                       <tr height="7">
                         <td width="6"><img src="image/back/back_bottleft.gif" width="6" height="7" /></td>
			             <td background="image/back/back_bottmid.gif"><img src="" alt="" name="" width="" height="7" align="left" /></td>
			             <td width="6" valign="top"><img src="image/back/back_bottright.gif" width="6" height="7" /></td>
		                   
                       </tr>
                     </table>
                   </td>
                 </tr>          
               </table>
             </td>
           </tr>
           <tr><td>&nbsp;</td></tr>
         </table>
	</body>
</html>
