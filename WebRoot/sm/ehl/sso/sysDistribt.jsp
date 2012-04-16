<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.ehl.base.table.*"%>
<%@ include file="../../../base/Message.oni"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /><br>
	<title><msg:Common_zh.sysDistribt.jsp.title></title>
	<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>
	<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../js/sso/sysDistribt.js"></script>
	<script language="javascript">
				function doOnLoad(){
	    			onLoadToolbar();
	    			adjustHeight('systable,systd');
	    			
	    		}
	     	</script>
	<style type="text/css">
	body {
		background-color: #FFFFFF;
		margin-left:0;
		margin-right:0;
	}
	</style>
</head>
<body onLoad="doOnLoad();" style="overflow: hidden">
<table id="systable" width="802" border="0" style="txt-align:center"  cellpadding="0"
	cellspacing="0" style="position: absolute; top: 2" height="503">
	<tr>
        <td>
           <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
	              <td width="3"><img src="../../image/back/back_topleft.gif" width="3" height="1" /></td>
	             <td background="../../image/back/back_topmid.gif"><img src="" alt="" name="" width="32" height="1" align="left" /></td>
	              <td width="3"><img src="../../image/back/back_topright.gif" width="3" height="1" /></td>
              </tr>
          </table>
       </td>
   </tr>
	<tr>
		<td align="center" valign="top">
		<table width="100%" border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
				<table  border="0" cellspacing="0" cellpadding="0">
					<tr>
					    <td id="systd" width="3" background="../../image/back/back_midleft.gif" height="496"></td>
						<td>
						<table width="100%" height="88%" border="0" align="center">
							<tr>
								<td height="25" valign="top">
								<div id="toolbar_zone"
									style="width: 100%; border: 1px solid #BFBFBF;" />

								<div id="modify"></div>
								</td>
							</tr>							
							<tr>
								<td align="center" valign="bottom"><br><span class="STYLE1"><msg:Common_zh.sysDistribt.jsp.title>
								</span>
								</td>
							</tr>							
							<tr>
								<td valign="top">
								<hr>
								<%
									String tableScript = PageTableUtil.doDataWinGenerate("d_sysdistribt_s");
								%> <%=tableScript%> <br>
								<div id="personbt"></div>
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
			  </td>
				<td width="2" background="../../image/back/back_midright.gif"></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
			  
                  <td width="6"><img src="../../image/back/back_bottleft.gif" width="6" height="7" /></td>
            <td background="../../image/back/back_bottmid.gif"><img src="" alt="" name="" width="32" height="7" align="left" />
			</td>
             <td width="6" valign="top"><img src="../../image/back/back_bottright.gif" width="6" height="7" /></td>
            </tr>
           </table>
        </td>
   </tr></table>
</body>
</html>
