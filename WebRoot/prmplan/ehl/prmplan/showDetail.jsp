<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="com.ehl.prmplan.manage.PrmplanManage"%>
<%
	String insrtOrUpdt = StringHelper.obj2str(request.getParameter("insrtOrUpdt"),"0");
	String strID = StringHelper.obj2str(request.getParameter("strID"),""); 
	String inner = new PrmplanManage().getBolbList(strID,"1");
%>	
<html>
  <head>
    <title>预案维护</title>	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="../../ShareInc.html" />    
	<link href="../../css/vcas.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../../js/util/global.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
	<script type="text/javascript" src="../../../sm/js/pcs/DepartmentSelect.js"></script>		
	<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>	
	<script type="text/javascript" src="../../js/prmplan/Prmplan.js"></script>
  </head>
<body class="body" onload="queryMes('<%=strID%>','<%=insrtOrUpdt%>');">
	<table width="350" height="200" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td>
	    <br>
		  <span class="titlebar">预案详细信息</span>
			<table width="100%" border="0" align="center" cellpadding="0" cellspacing="1" bgcolor="#000000" id="dataTable"> 
	          <tr style="display:none">
	            <td width="40%" height="30" align="right" class="td_3">顺序号：</td>
	            <td width="60%" align="center" class="td_4">
	              <input id="SXH" name="SXH" type="text" size="30" value="<%=strID %>"  readonly=true />
	              <input type="hidden" id="insertOrUpdate" name="insertOrUpdate" value="<%=insrtOrUpdt%>" />
	              <input type="hidden" id="YYCJ" name="YYCJ" value="" />
	              <input type="hidden" id="delFile" name="delFile" value="" />
				  <span class="RedFont">※</span>	     
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right"class="td_3">预案名称：</td>
	            <td align="left" class="td_4" >
					<input id="yamc" name="yamc" type="text" size="35" readonly/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right"class="td_3">应用场景：</td>
	            <td align="left" class="td_4" >
					<input id="YYCJ_SEC" name="YYCJ_SEC" type="text" size="35" readonly/> 
				</td>
	          </tr>
	          <tr>
	            <td align="right"class="td_3" >摘要描述：</td>
	            <td align="left" class="td_4">
	               <TEXTAREA id="zyms" name="zyms" style="width:88%" id="lxjip" readonly></TEXTAREA>
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">创建单位：</td>
	            <td align="left" class="td_4">
	               <input id="DEPTCODE2" name="DEPTCODE2" type="text" readonly/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">创建人员：</td>
	            <td align="left" class="td_4">
	               <input id="cjry" name="cjry"type="text" size="35" readonly/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">创建日期：</td>
	            <td align="left" class="td_4">
	               <input id="cjrq" name="cjrq" type="text" size="35" readonly"/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">修改日期：</td>
	            <td align="left" class="td_4">
	               <input id="xgrq" name="xgrq" type="text" size="35" readonly/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right"class="td_3">落实单位：</td>
	            <td align="left" class="td_4">
	               <input id="DEPTCODE1" name="DEPTCODE1" type="text" readonly/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">落实时间：</td>
	            <td align="left" class="td_4">
	               <input id="lssj" name="lssj" type="text" size="35" readonly/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">提醒时间：</td>
	            <td align="left" class="td_4">
	               <input id="txsj" name="txsj" type="text" size="35" readonly/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">启动时间：</td>
	            <td align="left" class="td_4">
	               <input id="qdsj" name="qdsj" type="text" size="35" readonly/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">审核意见：</td>
	            <td align="left" class="td_4">
	               <input id="shyj" name="shyj" type="text" size="35" readonly/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">预案附件：</td>
	            <td align="left" class="td_4" id="yafj">
	               <div align="left" id="filelist" class="text" style="overflow:auto;width:100%;height:100;">
						<%=inner%>
				   </div> 
				</td>
	          </tr>
	      </table>
	    </td>
	  </tr>
	</table>									
</body>
</html>
