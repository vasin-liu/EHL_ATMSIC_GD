<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:directive.page import="com.appframe.data.sql.DBHandler"/>
<html>
	<%
		//edit by lidq 大队机构类型为2
		String jgid = request.getParameter("jgid") == null ? "" : request.getParameter("jgid");
		String sql ="select a.jgmc, decode( b.tijiao,null,'未提交','已提交' ), decode( b.reportTime,null,'未提交',b.reportTime) " +
		" from (select jgid,jgmc from t_sys_department t where jglx='2' and substr(jgid,1,4)='"+jgid.substring(0,4)+"') a," +
		" (select substr(rzbh,1,6) as  tijiao,to_char(to_date(substr(rzbh,7,12),'yyyy-mm-dd hh24:mi'),'yyyy-mm-dd hh24:mi') as reportTime from t_oa_dayreport t where to_char(tjrq,'yyyy-mm-dd')=to_char(sysdate-1,'yyyy-mm-dd')) b"+
		" where substr(a.jgid,1,6)= b.tijiao(+)";
		Object[][] result = DBHandler.getMultiResult(sql);
	%>
	<head>
		<title>各个大队日报提交状态</title>
		<link href="../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<meta http-equiv="refresh" content="600"> 
	</head>
<body>
	<div id="tdData" class="scrollDivWarinng" >
		<table class="table" >
			<tr>
				<td class="wTableTopCenter" width="40%"style="text-align:center;border-bottom: 1 solid #A5D1EC;">大队名称</td>
				<td class="wTableTopCenter" width="35%"style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">日报表是否提交</td>
				<td class="wTableTopCenter" width="25%"style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">提交时间</td>
			</tr>
			<%
				if(result != null && result.length>0){ 
					for(int i=0;i<result.length;i++){
			            if("未提交".equals(result[i][1].toString())){
			            	result[i][1] = "<font color='red'>"+result[i][1].toString()+"</font>" ;
			            }
			%>
			<tr>
				<td class="wTableCenterCenter" style="font-size:11px;border-bottom: 1 solid #A5D1EC;"><%=result[i][0].toString() %></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><%=result[i][1].toString() %></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><%=result[i][2].toString() %></td>
			</tr>
			<%  
					}
				} 
			%>
		</table>
	</div>
</body>
</html>