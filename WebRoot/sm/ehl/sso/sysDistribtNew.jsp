<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String insrtOrUpdt =request.getParameter("insrtOrUpdt");
String did = request.getParameter("did"); 
%>
<html>
	<head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>用户操作管理</title>
      <jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>
      <script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
      <link href="../../css/nioa.css" rel="stylesheet" type="text/css">
	  <script type="text/javascript" src="../../../base/js/dhtmlx/xmlCreator.js"></script>
	  <script type="text/javascript" src="../../js/sso/sysDistribt.js"></script>
    
    <style type="text/css">
    body {
	background-color: #FFFFFF;
	}
	.alg{
	text-align: center
	}
	</style>
     </head> 
 <body onload ="doQuery('<%=did %>','<%=insrtOrUpdt %>');">
 <div style="padding-left: 15px;text-align: center">
 <fieldset style="width:300;border:1px solid #CCCCCC" align="center" ><br>
    <legend style="border:0px;"><%=insrtOrUpdt.equals("0")?"用户操作新增":"用户操作编辑"%><br></legend>
   <table width="100%" align="center" class="tableInput" id="dataTable">
     <tr>
      <td width="40%" align="right"><msg:Common_zh.sysDistribt.usercode>：</td>
      <td width="60%"  align="center" style="text-align: left">
          <input type="text" name="BH"  id="BH" value='<%=did %>' readonly />
      </td>
    </tr>
    <tr>
      <td width="40%" align="right">系统用户名：</td>
      <td id="user" align="center" style="text-align: left">&nbsp;</td>
    </tr>
    <tr id="timetr">
      <td width="40%" name="addTime" align="right"><msg:Common_zh.sysDistribt.subURL>：</td>
      <td align="center" style="text-align: left"><input type="text" name="subURL"  id="subURL"/></td>
    </tr>
   
    <tr>
      <td width="40%" id="disposestate" align="right"><msg:Common_zh.sysDistribt.action>：</td>
      <td align="center" id="" style="text-align:left">
	      <select name="action"  id="action"  style="text-align:left;width:155"/>
	          		<option value="请选择"><msg:Common_zh.sysDistribt.option></option>
	          		<option value="add">add</option>
	       			<option value="delete">delete</option>
		   
	     </select></td>
    </tr>   
    <tr>
        <td class="tdRight" colspan="2"><br>
            <div align="center">
            <!--addOrUpdate():调用的是../../js/priv/dpo.js文件中的函数addOrUpdate()  -->
            <!--reset():调用的是../../js/priv/dpo.js文件中的函数reset()  -->  
               <input type="image" src="../../image/button/btnsave.gif" name="save" value='<msg:Common_zh.Global.save.desc>' 
			   onClick="addOrUpdate(<%=insrtOrUpdt %>);""/>
               <input type="image" src="../../image/button/btnreset.gif" name="reset"  value='<msg:Common_zh.Global.reset.desc>' onClick="reset();"/>
               <input type="image" src="../../image/button/btnclose.gif" name="button"  value='<msg:Common_zh.Global.close.desc>' onClick="window.close();"/>            
            </div>
       </td>
    </tr>
</table>
 </fieldset>
 </div>
</body>
</html>
