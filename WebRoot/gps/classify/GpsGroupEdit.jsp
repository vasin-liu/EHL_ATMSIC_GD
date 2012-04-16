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
        <script type="text/javascript" src="../js/classify/GpsGroupEdit.js"></script>
         <script type="text/javascript" src="../js/classify/GpsGroup.js"></script>
       <script type="text/javascript" src="../../sm/js/common/prototype.js"></script>
        <script type="text/javascript" src="../../sm/js/common/list/FillListBox.js"></script>
        <script type="text/javascript" >
         var groupid=  '<%=groupid %>';
             function initpg() {
                 initPage(groupid);
             }
        </script>
	</head>
	<body onLoad="initpg();">
	  <div id="grouptab">
		 
			<table>
			    <tr valign="top">
			        <td width="15%" align="right">
				       分组名：
				    </td>
				    <td width="10%">
				       <input name="carnumber" type="text" id="carnumber" size="15" value='<%=groupname %>' readonly>
				     </td> 
			    </tr>
				<tr>
					<td>
					    未选车辆
					    <input type="text" id="querycar" /> 
				        <input type="button" id="querycarbtn" value="过滤" onclick="queryCarnumber();"/>
					   
						<select id="leftList" name="leftList" multiple size="6" style="width: 200px;height: 200px">
							
						</select>
					</td>
					<td align="center">
						<!-- 通过事件onclick调用JavaScript的moveList函数 -->
						<input type="button" name="to" size="10" value=" >> "
							onclick="moveAll('leftList','rightList')">
						<p>
						    <input type="button" name="backTo" size="10" value="   > "
								onclick="moveList('leftList','rightList')">
					    <p>			
							<input type="button" name="backTo" size="10" value=" << "
								onclick="moveAll('rightList','leftList')">
						<p>
						    <input type="button" name="backTo" size="10" value="   < "
								onclick="moveList('rightList','leftList')">
					</td>
					<td>
					     已选车辆
						<select id="rigthlist" name="rightList" multiple size="6" style="width: 200px;height: 200px">
						</select>
					</td>
				</tr>
				<tr>
				    <td></td>
				    <td  align="center">
                    <input type="image" src="../images/button/btnOK.gif"   value="保存" onClick="doSave('<%=groupid %>');" />  
                     &nbsp;
                     <input type="image" src="../../sm/image/button/btnclose.gif"
									name="button" value="<msg:Common_zh.Global.close.desc>"
									onClick="doClose();" /> 			
                    </td>
                    <td>
                    </td>
				</tr>
			</table>
		 
		</div>
	</body>
</html>
