<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../base/Message.oni"%>
<!--
    * author：zhaoyu
    * date:   2008-01-23
    * version:
-->
<% 
   String id=request.getParameter("paramId");
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>
		<script  type="text/javascript" src="../../js/sysmanage/sysParamUpdate.js">  </script>
		<script type="text/javascript"> 
		  function init(){
		    document.getElementsByName("paramValue")[0].focus();
		    doQuery('<%= id%>');
		  }
		</script> 
		<title>系统参数配置</title>
     </head>
<body onload="init();">
	 <form name="iform" action="" method="post"> 
	 <br>
	 <div style="padding-left: 12px;text-align: center">
	   	<fieldset style="width:310;height:180;border:1px solid #CCCCCC" align="center" >
	   	<legend style="border:0px;">参数编辑</legend>
			
		   <table width="100%">
		            <tr height="7">
				        <td width="26%" align="right" ></td>
				        <td width="71%" align="left"></td>
				        <td width="3%" ></td>
				    </tr>
				    <tr>
				        <td  align="right"><font size=2><msg:Common_zh.sysparam.desc>：</font></td>
				        <td  align="left"><input style='text-align:center'  type="text" name="paramDesc" class="text" id="paramDesc"  readonly/></td>
				        <td width="3%" class="RedFont">※</td>
				    </tr>
				     <tr height="5">
				        <td  align="right" ></td>
				        <td  align="left"></td>
				    </tr>
				    <br>
				    <tr>
				        <td  align="right" ><font  size=2><msg:Common_zh.sysparam.value>：</font></td>
				        <td  align="left"><input style='text-align:center' type="text" name="paramValue" class="text" id="paramValue"  /></td>
				        <td  width="3%" class="RedFont">※</td>
				    </tr>
                        <tr height="5">
				        <td  align="right" ></td>
				        <td  align="left"></td>
				    </tr>
		    </table>
		    <table width=100%>
				   <tr>
				        <td colspan="2" align="center">
				     		<a href='#'  onclick="modify();"><img src="../../image/button/btnsave.gif"  border=0/></a>
				         	<a href='#'  onclick="chongzhi();"><img src="../../image/button/btnreset.gif"   border=0/></a>
			               	<a href='#'  onclick="clse();"><img src="../../image/button/btnclose.gif" border=0 /></a>
				        	<input width=1% style="visibility:hidden" type="text" name="paramId" id="paramId" value=<%=id%> />
				         </td>
				   </tr>
				   <br>
		</table>
	</fieldset>
	</div>
 </form> 
</body>
</html>
