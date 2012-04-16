<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	   String groupid = request.getParameter("groupid");
	   String groupname = request.getParameter("groupname");
	 %>
<html>
	<head>
	    <title>分组管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../../css/font.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="../../js/classify/GpsGroupEdit.js"></script>
         <script type="text/javascript" src="../../js/classify/GpsGroup.js"></script>
       <script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
        <script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
        <script type="text/javascript" >
         var groupid=  '<%=groupid %>';
             function initpg() {
                 initPage(groupid);
             }
        </script>
	</head>
	<body onLoad="initpg();" style="background:none; padding-top:6px;">
	  <div id="grouptab" style="width:100%;height:100%;text-align:center ;">
			<table style="width:100%;height:100%;align:center;vertical-align:top">
			    <tr >
			        <td align=right   class="backcolor right">
				     <span style="font-size:8pt" >分组名：</span>   
				     </td> 
				       <td align=left colspan=2  class="left">
				       <input name="carnumber" type="text" id="carnumber"  value='<%=groupname %>' readonly>
				     	</td> 
			    </tr>
			     <tr valign="middle" style="line-height:2px" >
			        <td width=100% colspan=3 style="border-top:1 solid blue;line-height:2px;">
				      &nbsp;
				     </td> 
			    </tr>
				<tr>
					<td width=47%  align=right class="backcolor left">
					  <span style="color:blue;font-size:8pt;" >未选车辆</span>
					    <input type="text" style="width:100px" id="querycar" /> <input type="button" id="querycarbtn" value="过滤"  onclick="queryCarnumber();"/>
				        <br style="line-height:3px"/>
						<select id="leftList" name="leftList" multiple size="6" style="width: 90%;height: 200px">
						</select>
					</td>
					<td align="center" width=8% >
						<!-- 通过事件onclick调用JavaScript的moveList函数 -->
						<input type="button" name="to" size="10" value=" >> "
							onclick="moveAll('leftList','rightList')">
						<p>
						    <input type="button" name="backTo" size="10" value="  >  "
								onclick="moveList('leftList','rightList')">
					    <p>			
							<input type="button" name="backTo" size="10" value=" << "
								onclick="moveAll('rightList','leftList')">
						<p>
						    <input type="button" name="backTo" size="10" value="  <  "
								onclick="moveList('rightList','leftList')">
					</td>
					<td align=left width=45%>
					    	  <span style="color:blue;font-size:8pt;" >已选车辆</span> 
					     <br style="line-height:3px"/>
						<select id="rigthlist" name="rightList" multiple size="6" style="width: 90%;height:200px">
						</select>
					</td>
				</tr>
				<tr style="line-height:50px">
				    <td colspan=3 align="right">
                    <input type="image" src="../../images/button/btnOK.gif"   value="保存" onClick="doSave('<%=groupid %>');" />  
                     &nbsp;
                     <input type="image" src="../../images/button/btnclose.gif"
									name="button" value="<msg:Common_zh.Global.close.desc>"
									onClick="doClose();" /> 	
									 &nbsp; &nbsp; &nbsp; &nbsp; 		
                    </td>
                    
				</tr>
			</table>
		 
		</div>
	</body>
</html>
