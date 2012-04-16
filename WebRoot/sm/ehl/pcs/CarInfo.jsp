<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String CLBM = request.getParameter("CLBM");
%>
<html>
	<head>
		<title>车辆管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../../../base/ShareInc.html" />
		<script type="text/javascript" src="../../js/pcs/CarManage.js"></script>
	</head>
	<body onload="showInfo('<%=CLBM %>');">
	<div style="padding-left: 16px;text-align: center">
		<fieldset style="width:340px;height:420px;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:1px;">
				车辆详细信息
			</legend>
			<table width="100%" height="420" align="center" id="dataTable" boder=1>
				<tr>
					 <td width="30%" align="right">号牌种类：&nbsp;</td>
				     <td width="65%" id="HPZLTD">
				          <input type="text" class="text" id="HPZL" readOnly></input>
                     </td>
                     <td style="width:5px">&nbsp;
                     </td>
				</tr>
				<tr>
				     <td align="right" >
						号牌号码：&nbsp;
					 </td>
					 <td style="text-align:left">
				        <input type="text" class="text" id="HPHM" readOnly></input>
					 </td>
					 <td>&nbsp;
                     </td>
				</tr>
				<tr>
					 <td  align="right">车辆种类：&nbsp;</td>
				     <td id="CLZLTD">
				       <input type="text" class="text" id="CLZL" readOnly></input>
                     </td>
                     <td>&nbsp;
                     </td>
				</tr>
				<tr>
					 <td align="right">车辆品牌：&nbsp;</td>
					 <td id="PersonXB" >
					     <input type="text" class="text" id="CLPP" readOnly></input>
		  	         </td>
		  	         <td>&nbsp;
                     </td>
				</tr>
				<tr>
					 <td  align="right">车辆型号：&nbsp;</td>
					 <td id="PersonXB" >
					     <input type="text" class="text" id="CLXH" readOnly></input>
		  	         </td>
		  	         <td>&nbsp;
                     </td>
				</tr>
				<tr>
					 <td align="right">车架号：&nbsp;</td>
				     <td ><input type="text" class="text" id="CJH" readOnly></input></td>
				     <td>&nbsp;
                     </td>
				</tr>
				<tr>
					 <td  align="right">发动机号：&nbsp;</td>
				     <td><input type="text" class="text" id="FDJH" maxLength="50" readOnly></input></td>
				     <td>&nbsp;
                     </td>
				</tr>
				<tr>
					 <td  align="right">核定载客：&nbsp;</td>
				     <td ><input type="text" class="text" id="HDZKL" maxLength="50" readOnly></input></td>
				     <td>&nbsp;
                     </td>
				</tr>
				<tr>
					 <td  align="right">购置日期：&nbsp;</td>
					 <td id="" >
						<input  type="text" id="GZRQ" name="put" class="text" onclick="SelectDate(this,0);" readOnly/>
				     </td>
				     <td>&nbsp;
                     </td>
				</tr>
				<tr>
					 <td  align="right">司机姓名：&nbsp;</td>
					 <td id="RYXRZW" >
                         <input  type="text" id="SJXM"  class="text" readOnly="true"/>
				    </td>
				    <td>&nbsp;
                     </td>
				</tr>
				<tr>
					<td  align="right">联系电话：&nbsp;</td>
				    <td><input type="text" class="text" id="LXDH" maxLength="11" readOnly /></td>
				    <td>&nbsp;
                     </td>
				</tr>
				<tr>
					<td  align="right">有无GPS：&nbsp;</td>
				    <td>
				        <input  type="text" id="GPS"  class="text" readOnly="true"/>
				   </td>
				   <td>&nbsp;
                   </td>
				</tr>
				<tr>
					<td align="right">呼叫号码：&nbsp;</td>
				    <td>
				        <input  type="text" id="HJHM"  class="text" readOnly="true"/>
				   </td>
				   <td>&nbsp;
                   </td>
				</tr>
				<tr>
				    <td align="right">车辆状态：&nbsp;</td>
				    <td  id="CLZTTD">
				       <input  type="text" id="CLZT"  class="text" readOnly="true"/>
                    </td>
                    <td>&nbsp;
                    </td>
				</tr>
				<tr>
					<td align="right">设备状况：&nbsp;</td>
				    <td>
                      <TEXTAREA STYLE="overflow:hidden" ID="SBZK"  class="text" readOnly="true">
                      </TEXTAREA>                     
                    </td>
                    <td>&nbsp;
                    </td>
				</tr>
				<tr>
					<td align="right">人员状况：&nbsp;</td>
				    <td>
				       <TEXTAREA STYLE="overflow:hidden" ID="RYZK" class="text" readOnly="true">
                     </TEXTAREA> 
                     </td>
                     <td>&nbsp;
                     </td>
				</tr>
				<tr>
					<td  align="right">车载台组号：&nbsp;</td>
				    <td>
				       <input  type="text" id="CZTZH"  class="text" readOnly="true"/>
                     </td>
                     <td>&nbsp;
                     </td>
				</tr>
				<tr>
					<td align="right">车载台：&nbsp;</td>
				    <td >
				       <input  type="text" id="CZT"  class="text" readOnly="true"/>
                     </td>
                     <td>&nbsp;
                     </td>
				</tr>
				<tr>
					 <td align="right">所属单位：&nbsp;</td>
				     <td  ><input type="text" class="text" id="SSJG" readOnly="true"></input></td>
				     <td>&nbsp;
                     </td>
				</tr>
				<tr>
					<td class="tdRight" colspan="2"><br>
						<div align="right">							
							<img src="../../image/button/btnclose.gif" style="cursor:hand" onClick="window.close();">
						</div>
					</td>
				</tr>
		</table>
		</fieldset>
		</div>
	</body>
</html>
