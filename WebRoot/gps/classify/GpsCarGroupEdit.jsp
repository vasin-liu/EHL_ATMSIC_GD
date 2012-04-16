<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.common.table.*" %>
<%
	String insrtOrUpdt = request.getParameter("insertorupdate");
    String groupid = request.getParameter("groupid");
    String gid = request.getParameter("gid");
%>
<html>
	<head>
		<title>车辆分组管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="../js/classify/GpsGroup.js"></script>
        <script type="text/javascript" src="../../gzzd/js/Prototype.js"></script>
        <script type="text/javascript" src="../../sm/js/common/list/FillListBox.js"></script>
     </head>
<body onLoad="doQueryById('<%=gid%>');">
 <div style="padding-left: 15px;text-align: center">
   <fieldset style="width:300;height:220;border:1px solid #CCCCCC" align="center" >
   <br> 
   <legend style="border:0px;">车辆分组编辑</legend><br>
   <table width="300" align="center" class="tableInput" id="dataTable">
     <tr style="display:none">
      	<td width="31%" align="right">编码：</td>
		<td width="66%" id="tdXtlb">
			<input type="text" name="gid" class="text" id="gid" />
		</td>
		<td width="3%" class="RedFont">※</td>
	</tr>
     <tr style="display:none">
      	<td width="31%" align="right">分组编码：</td>
		<td width="66%" id="tdXtlb">
			<input type="text" name="groupid" class="text" id="groupid" />
		</td>
		<td width="3%" class="RedFont">※</td>
	</tr>
    <tr>
      	<td width="31%" align="right">分组名称：</td>
		<td width="66%" id=grouptd>
        	<script language="javascript">
			fillListBox("grouptd","group","120","SELECT groupid,groupname FROM t_gps_groupinfo","","");
			</script>
	  	</td>
		<td width="3%" class="RedFont">※</td>
	</tr>
	 <tr style="display:none">
      	<td width="31%" align="right">车辆编码：</td>
		<td width="66%" id="tdXtlb">
			<input type="text" name="carcode" class="text" id="carcode" />
		</td>
		<td width="3%" class="RedFont">※</td>
	</tr>
	 <tr style="display:''">
      	<td width="31%">车辆名称：</td>
        <td width="66%" id=HPZLTD>
        	<script language="javascript">
			fillListBox("HPZLTD","HPZL","120","SELECT CARCODE,CARNUMBER FROM T_GPS_CARINFO","","");
			</script>
	  	</td>
	  	<td width="3%" class="RedFont">※</td>
    </tr>
	
	<tr>  
        <td class="tdRight" colspan="2"><br>
	        <div align="center">
		      	<input type="image" src="../../sm/image/button/btnsave.gif" 	name="button"  value="<msg:Common_zh.Global.save.desc>"  onClick="save(<%=insrtOrUpdt %>);"/>
		        <input type="image" src="../../sm/image/button/btnreset.gif"  	name="button"  value="<msg:Common_zh.Global.reset.desc>" onClick="reset();"/>
		        <input type="image" src="../../sm/image/button/btnclose.gif" 	name="button"  value="<msg:Common_zh.Global.close.desc>" onClick="doClose();"/>
            </div>
        </td>
     </tr>
</table> 
</fieldset>
</div>
</body>
</html>
