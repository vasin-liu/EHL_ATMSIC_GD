<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="com.ehl.prmplan.manage.PrmplanManage"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="com.ehl.prmplan.util.DispatchUtil"%>
<%
	String insrtOrUpdt = StringHelper.obj2str(request.getParameter("insrtOrUpdt"),"0");
	String strID = StringHelper.obj2str(request.getParameter("strID"),""); 
	String inner = new PrmplanManage().getBolbList(strID,"");
	String[] strObj = DepartmentManage.getDeptInfo(request,"1").split(",");//获取机构信息串
    String jgid=strObj[0];//机构编码
    String jgmc=strObj[1];//机构名称
    String ccbm=strObj[2];//机构层次编码
	
	Hashtable userInfo = DispatchUtil.getCurrentUserData(request);
	
	//当前用户信息
	String deptcode = ""; 	//部门编码
	String deptname = ""; 	//部门名称
	String pname = ""; 		//姓名
	String uname = ""; 		//帐号
	if(userInfo != null){
		deptcode = (String)userInfo.get("DEPTID");
		deptname = (String)userInfo.get("DEPTNAME");
		pname = (String)userInfo.get("USERNAME");
		uname = (String)userInfo.get("PNAME");
	}
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
	<script type="text/javascript">
		var insertOrUpdate = "<%=insrtOrUpdt%>";
		var deptid = '<%=deptcode%>';
		var deptname = '<%=deptname%>';
		var pname = '<%=uname%>';
		var creatTime = getSysdate(0);
	</script>
  </head>
<body class="body">
	<table width="350" height="200" border="0" align="center" cellpadding="0" cellspacing="0">
	  <tr>
	    <td>
	    <br>
		  <span class="titlebar"><%=insrtOrUpdt.equals("0")?"新增预案":"编辑预案"%></span>
		    <form id="userform" name="userform" onSubmit="return checkType();" method="post"  action="prmplan.prmplanManage.insertOrUpdate.d" enctype="multipart/form-data">
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
					<input id="yamc" name="yamc" type="text" size="35" /> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right"class="td_3">应用场景：</td>
	            <td align="left" class="td_4" >
				  <table align="left" border="0">
				    <tr width="100%">
					  <td align="left" id="YYCJ_TD">
	 			        <script language="javascript">
						 	fillListBox("YYCJ_TD","YYCJ_SEC","247","SELECT ID,NAME FROM T_PREPLAN_TYPE ORDER BY ID","未选择","queryMes('<%=strID%>','<%=insrtOrUpdt%>');");
						</script>	
					  </td>
					  <td><span class="RedFont">※</span></td>
				    </tr>
				  </table>	
				</td>
	          </tr>
	          <tr>
	            <td align="right"class="td_3" >摘要描述：</td>
	            <td align="left" class="td_4">
	               <TEXTAREA id="zyms" name="zyms" style="width:88%" id="lxjip"></TEXTAREA>
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">创建单位：</td>
	            <td align="left" class="td_4">
	               <table width="95%">
						<tr>
						    <td width="97%"><input id="DEPTCODE2" type="text" onpropertychange="$('cjdw').value=G_jgID;" class="text" style="" readonly=true></input></td>
							<td width="3%">
								<img src="../../../sm/image/popup/btnselect.gif" alt="选择机构"  style="cursor:hand;" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','DEPTCODE2','40','152')">
							</td>
						</tr>
					</table>
	               <input id="cjdw" name="cjdw" type="hidden"/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">创建人员：</td>
	            <td align="left" class="td_4">
	               <input id="cjry" name="cjry"type="text" size="35" /> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">创建日期：</td>
	            <td align="left" class="td_4">
	               <input id="cjrq" name="cjrq" type="text" size="35" onClick="SelectDateTime(this,0);"/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">修改日期：</td>
	            <td align="left" class="td_4">
	               <input id="xgrq" name="xgrq" type="text" size="35" onClick="SelectDateTime(this,0);"/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right"class="td_3">落实单位：</td>
	            <td align="left" class="td_4">
	               <table width="95%">
						<tr>
						    <td width="90%"><input id="DEPTCODE1" type="text" onpropertychange="$('lsdw').value=G_jgID;" class="text" style="" readonly=true></input></td>
							<td width="2%">
								<img src="../../../sm/image/popup/btnselect.gif" alt="选择机构"  style="cursor:hand;" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','DEPTCODE1','40','152')">
							</td>
						</tr>
					</table>
	               <input id="lsdw" name="lsdw" type="hidden"/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">落实时间：</td>
	            <td align="left" class="td_4">
	               <input id="lssj" name="lssj" type="text" size="35" onClick="SelectDateTime(this,0);"/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">提醒时间：</td>
	            <td align="left" class="td_4">
	               <input id="txsj" name="txsj" type="text" size="35" onClick="SelectDateTime(this,0);"/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">启动时间：</td>
	            <td align="left" class="td_4">
	               <input id="qdsj" name="qdsj" type="text" size="35" onClick="SelectDateTime(this,0);"/> 
				</td>
	          </tr>
	          <tr>
	            <td height="30" align="right" class="td_3">审核意见：</td>
	            <td align="left" class="td_4">
	               <input id="shyj" name="shyj" type="text" size="35" /> 
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
	          <tr>
	            <td height="30" align="right" class="td_3"></td>
	            <td align="left" class="td_4" align="right">
	               <input id="openFile" type="button" value="添加附件" onClick="addFileItem();"/> 
				</td>
	          </tr>
			  <tr>
			  	<td height="30" align="right" class="td_3"></td>
			    <td align="right"  class="td_4">      
			      <input type="image" src="../../image/button/btnsave.gif"  onKeyDown="if(event.keyCode==13 || event.keyCode == 32) checkType();"/>
			      <a href="#" onClick="doClose();"><img style="border:0" src="../../image/button/btnimpcancel.gif"/></a>
				</td>
			  </tr>
	      </table>
		  </form>
	    </td>
	  </tr>
	</table>									
</body>
</html>
