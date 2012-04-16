<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
	<head>
		<title><msg:Common_zh.Global.navigation></title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link rel="STYLESHEET" type="text/css" href="../../../sm/css/dhtmlx/dhtmlXTree.css" />
		<link href="../../../sm/css/help/help_left.css" rel="stylesheet" type="text/css" />
		<script src="../../../sm/js/common/dhtmlx/dhtmlXCommon.js"></script>
		<script src="../../../sm/js/common/dhtmlx/dhtmlXTree.js"></script>
	</head>
 <body bgcolor="ECE9D8">
      <div id="preview1">
        <div id="scrolldoorFrame">
              <ul>
                <li class="q"><a href="#" >目录(C)</a></li>
              </ul>     
        </div>
        <div id="xla">
            <table width="200" border="0" cellspacing="0" cellpadding="0" >
			     <tr>
			       <td id="lefttreettd">
			         <table width="100%" border="0" cellpadding="0" cellspacing="0"> 
			           <tr>
			             <td valign="top">
			               <table width="100%" border="0" cellspacing="0" cellpadding="0">
			                 <tr>
			                <td id="lefttreetd" >
			                  <div id="lefttreediv" style="width:220;height:600; background-color:#ffffff; overflow-x:hidden;overflow-y:auto; margin-left:0">
								  <table>
									<tr>
									  <td valign="top">
										<div id="treeboxbox_tree"></div>
									  </td>
									</tr>
								   </table>
								    <script>				
										tree=new dhtmlXTreeObject("treeboxbox_tree","100%","100%",0);
										tree.setImagePath("../../../sm/image/tree/");
										tree.enableCheckBoxes(false);
										tree.enableDragAndDrop(false);
										tree.setOnClickHandler(onNodeSelect);
										tree.loadXML("../../xml/help.xml",onOpenNode);
										      
										function onNodeSelect(){
										  var sID = tree.getSelectedItemId();
										 
										  if (sID.indexOf("0") == -1) //屏蔽根结点
						
										  {
											  sID = sID +".htm";
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
			             </td>
			           </tr> 
			         </table>
			       </td>
			     </tr>
			   </table>
        </div>
     </div>
</body>
</html>
