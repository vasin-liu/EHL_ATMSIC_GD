<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<% 
	String insrtOrUpdt = request.getParameter("insrtOrUpdt")==null? "" : request.getParameter("insrtOrUpdt");
	String  did = request.getParameter("did")==null? "" : request.getParameter("did");
	String sysCode = request.getParameter("sysCode");
%>
<html>
<head>
	<title>操作类型维护</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>	
	<script src="../../../base/js/dhtmlx/dhtmlXTree.js"></script>
	<script type="text/javascript" 	src="../../js/user/operation.js"></script>
	<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
</head> 
<body onload ="">
<div style="padding-left: 15px;text-align: center">
	<fieldset style="width:300;height:240;border:1px solid #CCCCCC" align="center" >
	<br>
	    <legend style="border:0px;"><%=insrtOrUpdt.equals("0")?"操作信息新增":"操作信息修改"%></legend>
	    <table width="280" style="text-align:right" >
		     <tr id="hid">
		      <td width="30%">操作编号：</td>
		      <td width="67%"><input type="text" class="text" id="code" maxLength="32" value=<%=did %> readonly /></td>
		      <td width="3%" class="RedFont">※</td>
		     </tr>
		     <br>
		      <tr>
			      <td width="30%">系统名称：</td>
			      <td width="67%" id="tdsys">		      
			      	<script language="javascript">
	  					fillListBox("tdsys","sysname","180","select id,text from t_sys_func where substr(id,3,4)='0000'","未选择","","");
				    </script>
			      </td>
			      <td width="3%" class="RedFont">※</td>
		     </tr>
		     <tr style="display: none" id="funcSelect">
		      <td width="30%">功能名称：</td>		
		      <td width="67%" id="tdfunc" >	    
		      	<script language="javascript">
		      		//2009-09-25 luchunqiang
					// 修改了查询语句  获取叶结点，而非仅仅是最后两位不是00的结点
		      		var sqlLeafNode = 'select t.id,t.text from ';
						sqlLeafNode += '(select id,text, count(id) over( partition by substr(id,0,4)) nodecount  from t_sys_func ) ';
						sqlLeafNode += ' t  where ( substr(t.id,5,2)<>\'00\' OR (  t.nodecount = 1 AND substr(t.id,3,4)<>\'0000\' ))';
  					fillListBox("tdfunc","funcname","180",sqlLeafNode,"未选择","doQuery(<%=did %>,<%=insrtOrUpdt%>);");
			    </script>
			  </td>
			  <td width="3%" class="RedFont">※</td>
		     </tr>
		     <tr>
			      <td width="30%">操作名称：</td>
			      <td width="67%"><input  type="text" id="optname" maxLength="64" class="text"/></td>
			      <td width="3%" class="RedFont">※</td>		
		     </tr>
		     <tr>
		      <td width="30%">实体编号：</td>
		      <td width="67%"><input  type="text" id="btncode" maxLength="64" class="text"/></td>
		      <td width="3%" class="RedFont">※</td>
		     </tr>
		     <tr>
		      <td width="30%">备注：</td>
		      <td width="67%"><input  type="text" id="remark" maxLength="64" class="text"/></td>
		     </tr>
	    	<tr>
		        <td class="tdRight" colspan="2">
		            <div style="text-align:center">
		            <!--addOrUpdate():调用的是../../js/priv/dpo.js文件中的函数addOrUpdate()  -->
		            <!--reset():调用的是../../js/priv/dpo.js文件中的函数reset()  -->  
		               <input type="image" src="../../image/button/btnsave.gif" name="save" value='<msg:Common_zh.Global.save.desc>' onClick="addOrUpdate(<%=insrtOrUpdt%>);"/>
		            <%if(insrtOrUpdt.equals("0")){%> <input type="image" src="../../image/button/btnreset.gif"  name="reset"  value='<msg:Common_zh.Global.reset.desc>' onClick="reset();"/><%}%>
		               <input type="image" src="../../image/button/btnclose.gif" name="button"  value='<msg:Common_zh.Global.close.desc>' onClick="window.close();"/>            
                   </div>
		        </td>
	        </tr>
		</table> 
	</fieldset></div>
</body>
</html>
