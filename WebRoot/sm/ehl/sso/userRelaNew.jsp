<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String insrtOrUpdt =request.getParameter("insrtOrUpdt");
String did = request.getParameter("did"); 
%>
<html>
	<head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>用户登录管理</title>
      <jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
      <script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
      <link href="../../css/nioa.css" rel="stylesheet" type="text/css">
	  <script type="text/javascript" src="../../../base/js/dhtmlx/xmlCreator.js"></script>
	  <script type="text/javascript" src="../../js/sso/userRela.js"></script>
    
    <style type="text/css">
    body {
	background-color: #FFFFFF;
	}
	</style>
	
     </head> 
 <body>
 <div style="padding-left: 15px;text-align: center">
 <fieldset style="width:300;border:1px solid #CCCCCC" align="center" ><br>
    <legend style="border:0px;"><%=insrtOrUpdt.equals("0")?"用户信息新增":"用户信息编辑"%> <br></legend>
   <table width="100%" class="tableInput" id="dataTable">   
    <tr id="">
      <td width="40%" style="text-align:right"><msg:Common_zh.userRela.bh>：</td>      
      <td id="dispose" width="60%" style="text-align:left"><input type="text" name="BH"  id="BH" value='<%=did %>' readonly /></td>
    </tr>
    <tr>
      <td width="40%" style="text-align:right"><msg:Common_zh.userRela.sysusername>：</td>
      <td id="user" width="60%" style="text-align:left">&nbsp;
	      <script type="text/javascript">
	           fillListBox("user","userCode","155","SELECT usercode,username FROM t_user","请选择用户！","doQuery('<%=did %>','<%=insrtOrUpdt %>')");
	      </script>
      </td>
    </tr>
    
    <tr>
      <td width="40%" style="text-align:right"><msg:Common_zh.userRela.subusername>：</td>
      <td style="text-align:left" width="60%"><input type="text" name="subUserName" id="subUserName"/></td>
    </tr>  
    <tr>
        <td  class="tdRight" colspan="2"><br>
            <div align="center">
            <!--addOrUpdate():调用的是../../js/priv/dpo.js文件中的函数addOrUpdate()  -->
            <!--reset():调用的是../../js/priv/dpo.js文件中的函数reset()  -->  
               <input type="image" src="../../image/button/btnsave.gif" name="save" value='<msg:Common_zh.Global.save.desc>' onClick="addOrUpdate(<%=insrtOrUpdt %>);""/>
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
