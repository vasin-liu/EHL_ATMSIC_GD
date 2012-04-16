<%@ page language="java" pageEncoding="UTF-8" session="true"
	buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ page import="com.ehl.base.table.*"%>
<%@ include file="../../../base/Message.oni"%>
<html>
<head>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="this is my page">
	<link href="../../css/nioa.css" rel="stylesheet" type="text/css">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<jsp:include flush="true" page="../../../base/ShareInc.html"></jsp:include>
	<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
	<script type="text/javascript" src="../../js/sysmanage/logManage.js"></script>
	<script language="javascript">
	   		function doOnLoad(){
	    			onLoadToolbar();    			
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
<body onLoad="doOnLoad();" style="overflow: hidden">
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
								<td align="center" valign="bottom"><br><span class="STYLE1"><msg:Common_zh.logManage.jsp.title></span></td>
							</tr>
							<tr>
								<td height="2" valign="top"><hr></td>
							</tr>
							<tr>
								<td height="17" valign="top">
									<!--  -->
									<table width="100%" border="0">
                                    <tr><td width="89%" rowspan="2">
									<div align="left" style="width: 100%; border: 1px solid #BFBFBF;">
									<!--  -->								
								<table width="100%" border="0" align="left">
									<tr>
										<td width="8%" align="right"><msg:Common_zh.logManage.jsp.uname>：</td>
										<td><input type="text" id="pname" maxLength="20" class="text" /></td>
										
										<td width="10%">
										  <div align="right"><msg:Common_zh.logManage.jsp.etime>：</div>
										</td>
										<td width="20%"><input type="text" id="etime" class="text" onClick="SelectDate(this,0);" /></td>
										<td width="2%">----</td>
										<td width="20%"><input type="text" id="etime2" class="text" onClick="SelectDate(this,0);" /></td>
									</tr>
									<tr>
										<td align="right">IP地址：</td>
										<td width="10%"><input type="text" id="ip" class="text" maxlength="32" /></td>
										<td><div align="right"><msg:Common_zh.logManage.jsp.qtime>：</div></td>
										<td><input type="text" id="qtime" class="text" onClick="SelectDate(this,0);" /></td>
										<td>----</td>
										<td><input type="text" id="qtime2" class="text" onClick="SelectDate(this,0);" /></td>
									</tr>
								</table>
								<!--  -->	
								</div></td>
                                <td width="11%">
                                 <input name="button" type="image" src="../../image/button/btnquery.gif" onClick="Filter();" value="查询" />                                                  </td>
                                  </tr>
                                 <tr>
                                 <td>&nbsp;</td>
                                 </tr>
                               </table>
                            <!--  -->								
								</td>
							</tr>
							<tr>
								<td valign="top"><%=PageTableUtil.doDataWinGenerate("d_rz_s")%> <br>
								    <script type="text/javascript">
					                     window.gridName = "t_rz_s";
							    			var grid = eval("mygrid" + window.gridName);
							  				grid.hdr.rows[1].childNodes[0].childNodes[0].innerHTML = ' \
										<span style="vertical-align:super;"><input type="checkbox" id="box" value="0" name="ckb_selectAll1" onclick="checkAllRow(mygridt_rz_s);">全选</span>';
					                </script>
							    </td>
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
