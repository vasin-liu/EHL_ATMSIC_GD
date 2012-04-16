<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.ehl.base.table.*"%>
<%@include file="../../../base/Message.oni"%>
<html>
	<head>
		<title>报表设置</title>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>
		<script src="../../../base/js/dhtmlx/xmlCreator.js"></script>
		<script src="../../../base/js/table/rfConfig.js"></script>
		<script>
		function doOnLoad() {
			onLoadToolbar("0_info,0_excel,0_chart");	
			adjustHeight('blktable,blkid');
    		
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
	<body onLoad="doOnLoad()" style="overflow: hidden">
<table id="blktable" width="802" border="0" align="center" cellpadding="0"
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
		<table  border="0" align="center" cellpadding="0"
			cellspacing="0">
			<tr>
				<td>
				  <table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
					    <td id="blkid" width="3" background="../../image/back/back_midleft.gif" height="496"></td>
						<td>
						<table width="100%" height="100%" border="0" align="center">
							<tr>
								<td height="25" valign="top">
									<div id="toolbar_zone" style="width: 100%; border: 1px solid #BFBFBF;" />
									<div id="modify"></div>
								</td>
							</tr>
							<tr>
								<td align="center" valign="bottom"><br><span class="STYLE1">数据窗口定义</span></td>
							</tr>
							<tr>
								<td height="2" valign="top"><hr></td>
							</tr>
							<tr>
								<td height="17" valign="top">

									<!--  -->
									<table width="100%" border="0">
                                      <tr>
                                        <td width="89%" rowspan="2"><div align="left" style="width: 100%; border: 1px solid #BFBFBF;">
                                            <!--  -->
                                            <table width="91%" border="0" align="center">
                                              <tr>
                                                <td width="11%"><div align="right"> 报表名： </div></td>
                                                <td width="30%"><input name="text" type="text" id="dname" size="18" />
                                                </td>
                                                <td width="15%"><div align="right"> 相关表格编号： </div></td>
                                                <td width="30%"><input name="text2" type="text" id="dvalue" size="18" />
                                                </td>
                                              </tr>
                                            </table>
                                          <!--  -->
                                        </div></td>
                                        <td width="11%">
										<input name="button" type="image" src="../../image/button/btnquery.gif" onClick="doFilter();" value="查询" />
                                        </td>
                                      </tr>
                                    </table></td>
							</tr>
							<tr>
								<td valign="top">	<%
														String tableScript = PageTableUtil.doDataWinGenerate("d_dw_s");
														%>
														<%=tableScript%></td>
							</tr>
							<tr><td>
							    </td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
				<td width="3" background="../../image/back/back_midright.gif" ></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
        <td>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                  <td width="6"><img src="../../image/back/back_bottleft.gif" width="6" height="7" /></td>
            <td background="../../image/back/back_bottmid.gif"><img src="" alt="" name="" width="32" height="7" align="left" /></td>
             <td width="6" valign="top"><img src="../../image/back/back_bottright.gif" width="6" height="7" /></td>
            </tr>
           </table>
        </td>
   </tr>
</table>
</body>
</html>