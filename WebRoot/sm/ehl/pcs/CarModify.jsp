<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ include file="../../../base/Message.oni"%>
<%@ page import="com.ehl.base.util.FillListBox"%>
<%
    String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    if(strObj == null || strObj.length < 3){
        out.write("用户对应机构不存在！");
        return;
    }
    String jgid=strObj[0];//单位编码
    String jgmc=strObj[1];//机构名称
    String ccbm=strObj[2];//单位层次编码
 %>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt");
	String CLBM = request.getParameter("CLBM");
	String hpzlStr = request.getParameter("hpzlStr");
	String hplbStr = request.getParameter("hplbStr"); 
	String clzlStr = request.getParameter("clzlStr");
	String loadFunc = "";//
	if(insrtOrUpdt != null && insrtOrUpdt.equals("0")){//新增
	    loadFunc =   "setValue('"+hpzlStr+"','"+hplbStr+"','"+clzlStr+"')";
	}else{
	   loadFunc =   "doQuery('"+CLBM+"')"; 
	}
	
%>
<html>
	<head>
		<title>车辆管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../../../base/ShareInc.html" />
		<link rel="STYLESHEET" type="text/css" href="../../css/popup/Popup.css">
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
		<script type="text/javascript" src="../../../base/js/dhtmlx/dhtmlXTree.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../js/pcs/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../js/pcs/CarManage.js"></script>
		<script type="text/javascript">
			function loadInit(){
		    var deptname="<%=jgmc%>";
	    	$("SSJG").value = deptname;
	    	G_jgID='<%=jgid%>';	
	  	    }
 		      	    
	    </script>	
	</head>
	<body onload="loadInit();<%= loadFunc %>">
	<div style="padding-left: 16px;text-align: center">
		<fieldset style="width:360px;height:420px;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:1px;">
				<%=insrtOrUpdt.equals("0")?"车辆信息新增":"车辆信息编辑"%>
			</legend>
			<table width="100%" height="420" align="center" border='0' >
				<tr>
					 <td width="30%" align="right">号牌种类：&nbsp;&nbsp;</td>
				     <td colspan="2" width="60%" id="HPZLTD">
				        <%= FillListBox.createSelect("ID='HPZL' style='width:220'","select distinct dm,dmsm from t_sys_code where dmlb='011001'","未选择")%>
                     </td>
                     <td align="right" style="width:5%" class="RedFont"><p>※</p></td>
				</tr>
				<tr>
			     	<td align="right">号牌号码：&nbsp;&nbsp;</td>
					<td ID="HPHMTD" width="21%">
					   <%= FillListBox.createSelect("ID='HPHMLB' style='width:100%'","select distinct substr(dm,1,1),substr(dm,1,1) from t_sys_code where dmlb='011006'","未选择")%>
				    </td>
					
			 	  <td><input type="text" id="cardCode" name="cardCode" class="text" /></td>
                  
				 	<td align="right"  class="RedFont"><p>※</p></td>	
				</tr>
				<tr>
					 <td align="right">车辆种类：&nbsp;&nbsp;</td>
				     <td colspan="2" id="CLZLTD">
			            <%= FillListBox.createSelect("ID='CLZL' style='width:220'","select distinct dm,dmsm from t_sys_code where dmlb='013014'","未选择")%>
				     </td>
		              <td>&nbsp;</td>
				</tr>
				<tr>
					 <td align="right">车辆品牌：&nbsp;&nbsp;</td>
					 <td colspan="2" id="PersonXB">
				     <input type="text" class="text" id="CLPP" ></input>		  	         </td>
				      <td>&nbsp;</td>
				</tr>
				<tr>
					 <td align="right">车辆型号：&nbsp;&nbsp;</td>
					 <td colspan="2" id="">
				     <input type="text" class="text" id="CLXH" ></input>		  	         </td>
				      <td>&nbsp;</td>
				</tr>
				<tr>
					 <td align="right">车架号：&nbsp;&nbsp;</td>
				     <td colspan="2"><input type="text" class="text" id="CJH" ></input></td>
				      <td>&nbsp;</td>
				</tr>
				<tr>
					 <td align="right">发动机号：&nbsp;&nbsp;</td>
				     <td colspan="2"><input type="text" class="text" id="FDJH" maxLength="50"></input></td>
				      <td>&nbsp;</td>
				</tr>
				<tr>
					 <td align="right">核定客量：&nbsp;&nbsp;</td>
				     <td colspan="2"><input type="text" class="text" id="HDZKL" maxLength="50"></input></td>
				      <td>&nbsp;</td>
				</tr>
				<tr>
					 <td align="right">购置日期：&nbsp;&nbsp;</td>
					 <td colspan="2" id="">
					 <input  type="text" id="GZRQ" name="put" class="text" onClick="SelectDate(this,0);" readOnly/>				     </td>
				     <td align="right" class="RedFont"><p >※</p></td>	
				</tr>
				<tr>
					 <td align="right">司机姓名：&nbsp;&nbsp;</td>
					 <td colspan="2" id="RYXRZW">
                    <input  type="text" id="SJXM"  class="text" />				    </td>
                     <td>&nbsp;</td>
				</tr>
				<tr>
					<td align="right">联系电话：&nbsp;&nbsp;</td>
				    <td colspan="2"><input type="text" class="text" id="LXDH" maxLength="11"></input></td>
				    <td >&nbsp;</td>
				</tr>
				<tr>
					<td align="right">有无GPS：&nbsp;&nbsp;</td>
				    <td colspan="2">
				        <select class="text" id="GPS">
				           <option value='0' selected>未安装</option>
				           <option value='1' >已安装</option>
				        </select>				   </td>
				    <td >&nbsp;</td>
				</tr>
				<tr>
					 <td align="right">呼叫号码：&nbsp;&nbsp;</td>
					 <td colspan="2" id="RYXRZW">
                    <input  type="text" id="HJHM"  class="text" />				    </td>
                     <td >&nbsp;</td>
				</tr>
				<tr>
				    <td align="right">车辆状态：&nbsp;&nbsp;</td>
				    <td colspan="2" id="CLZTTD">
				        <%= FillListBox.createSelect("ID='CLZT' style='width:220'","select distinct dm,dmsm from t_sys_code where dmlb='013015'","未选择")%>    
                    </td>
                    <td style="width:5%" align="right" class="RedFont"><p >※</p></td>	
				</tr>
				<tr>
					<td align="right">设备状况：&nbsp;&nbsp;</td>
				    <td colspan="2">
                    <TEXTAREA STYLE="overflow:auto" class="text" ID="SBZK">
                      </TEXTAREA>                    </td>
                     <td >&nbsp;</td>
				</tr>
				<tr>
					<td align="right">人员状况：&nbsp;&nbsp;</td>
				    <td colspan="2">
			         <TEXTAREA STYLE="overflow:auto" class="text" ID="RYZK">
                     </TEXTAREA>                     </td>
                     <td >&nbsp;</td>
				</tr>
				<tr>
					<td align="right">车载台组号：&nbsp;&nbsp;</td>
				    <td colspan="2"><input type="text" class="text" id="CZTZH" maxLength="11"></input></td>
                     <td >&nbsp;</td>
				</tr>
				<tr>
					<td  align="right">车载台：&nbsp;&nbsp;</td>
				    <td colspan="2"><input type="text" class="text" id="CZT" maxLength="11"></input></td>
                     <td style="width:5%">&nbsp;</td>
				</tr>
				<tr>
					 <td align="right">所属单位：&nbsp;&nbsp;</td>
				     <td colspan="2"><input type="text" class="text" id="SSJG" onClick="reset_dept('SSJG');" readOnly="true"></input></td>
					 <td  align="left">
					    <table>
					       <tr>
					         <td>
					             <div  align="left"><img src="../../image/popup/btnselect.gif" alt="选择单位" style="cursor:hand;" onClick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','SSJG','110','142')"></div>
					         </td>
					         <td style="text-align:left;width:5%" class="RedFont"><p align="left">※</p>
					         </td>
					       </tr>
					    </table>
				 	    
				 	</td>
				</tr>
				
				<tr>
					<td class="tdRight" colspan="3"><br>
						<div align="right">
							<img src="../../image/button/btnsave.gif" style="cursor:hand" onClick="modify('<%=insrtOrUpdt %>','<%=CLBM %>');">
							<%if(insrtOrUpdt.equals("0")) {%>
							<input type="image" src="../../image/button/btnreset.gif" name="button" onClick="reset();" />
							<%} %>
							<img src="../../image/button/btnclose.gif" style="cursor:hand" onClick="window.close();">						</div>					</td>
				</tr>
		</table>
	  </fieldset>
		</div>
	</body>
</html>
