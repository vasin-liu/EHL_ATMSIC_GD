<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.base.table.*"%>
<%@ page import="com.ehl.sm.pcs.*"%>
<!--
    * author：郭田雨
    * date:   2008-03-19
    * version:
-->
<%
	String RYID = request.getParameter("RYID");
%>
<html>
	<head>
		<title>人员管理
		</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../../../base/ShareInc.html" />
		<script type="text/javascript" src="../../js/pcs/PersonManage.js"></script>
	</head>
	<body onLoad="doList('<%=RYID %>');">
	<div style="padding-left: 16px;text-align: center">
		<fieldset style="width:420px;height:480px;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:1px;">人员信息明细</legend>
			<table width="100%" align="center" class="tableInput" id="dataTable">				
				<tr>
				  <td colspan="2" valign="top"><table width="100%" border="0">
                   <tr>
					<td width="20%">姓名：</td>
					<td width="50%"><input type="text" class="text" id="XM" readOnly=true></input></td>
					<td width="30%" rowspan="4">
                         <table>
                            <tr>
                              <td><img id="pic3"  width="112" height="123"   onerror="this.src='../../image/error/noplate.jpg'" title="双击打开原图" onDblClick="showNormallyZP('pic3');"> </td>
                            </tr>
                      <tr style="display:none">
                      <td width="30%" align="right">人员照片：</td>
                      <td align="left"width="43%"><input id="RYIDForAdd"  name="RYIDForAdd" type="hidden" value="<%=RYID %>"/>
                          <input style="width:285px;" type="file"  id="fileObjId" name="fileObjId" href="#" onChange="upLoadPhoto('<%=RYID %>');" />                      </td>
                      <td width="14%">&nbsp;</td>
                    </tr>
                          </table>
                     </td>
				</tr>
				<tr>
					<td>性别：</td>
					<td><input type="text" class="text" id="XB" maxLength="20" readOnly=true></input></td>
					</tr>
				<tr>
					<td>身份证号：</td>
					<td><input type="text" class="text" id="SFZH" readOnly=true></input></td>
					</tr>			
				<tr>
					<td>出生日期：</td>
					<td><input type="text" class="text" id="CSRQ" readOnly=true></input></td>
					</tr>
                  </table></td>
			  </tr>				
				<tr>
					<td width="20%">警号：</td>
					<td width="80%"><input type="text" class="text" id="JH" readOnly=true></input></td>
				</tr>
				<tr>
					<td>警衔：</td>
					<td><input type="text" class="text" id="JX" readOnly=true></input></td>
				</tr>
				<tr>
					<td>所属单位：</td>
					<td><input type="text" class="text" id="SSJG" readOnly=true></input></td>
				</tr>
				<tr>
					<td>现任职务：</td>
					<td><input type="text" class="text" id="XRZW" readOnly=true></input></td>
				</tr>				
				<tr>
					<td>编制类型：</td>
					<td><input type="text" class="text" id="BZLX" readOnly=true></input></td>
				</tr>
				<tr>
					<td>特勤级别：</td>
					<td><input type="text" class="text" id="TQJB" readOnly=true></input></td>
				</tr>
				<tr>
					<td>手机：</td>
					<td><input type="text" class="text" id="SJ" readOnly=true></input></td>
				</tr>
				<tr>
					<td>办公电话：</td>
					<td><input type="text" class="text" id="BGDH" readOnly=true></input></td>
				</tr>
				<tr>
					<td>家庭电话：</td>
					<td><input type="text" class="text" id="JTDH" readOnly=true></input></td>
				</tr>				
				<tr>
					<td>手台：</td>
					<td><input type="text" class="text" id="ST" readOnly=true></input></td>
				</tr>
				<tr>
					<td>手台呼/代号：</td>
					<td><input type="text" class="text" id="STHH" readOnly=true></input></td>
				</tr>				
				<tr>
					<td>手台组号：</td>
					<td><input type="text" class="text" id="STZH" readOnly=true></input></td>
				</tr>
				<tr>
					<td colspan="4"><br>
						<div align="center"><img src="../../image/button/btnclose.gif" style="cursor:hand" onClick="window.close();"></div>					</td>
				</tr>
			</table>
		</fieldset>
		</div>
	</body>
</html>
