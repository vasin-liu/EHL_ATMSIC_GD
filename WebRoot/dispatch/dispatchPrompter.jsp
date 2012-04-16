<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage" %>
<%@ page import="com.ehl.sm.common.Constants"%>
<%@page import="com.ehl.dispatch.common.*" %>
<jsp:directive.page import="com.appframe.data.sql.DBHandler"/>
<%
 	Hashtable prop = DispatchUtil.getCurrentUserData(request);
	
	String path = request.getHeader("host");
	System.out.println(path);
	//当前用户信息
	String deptcode = ""; 	  //部门编码
	String deptname = ""; 	  //部门名称
	String pname = ""; 		  //姓名
	String uname = ""; 		  //帐号
	String pid = ""; 		  //办公电话
	String phone = ""; 		  //办公电话
	String mobilephone = "";  //手机
	if(prop != null){
		deptcode = (String)prop.get("BRANCHID");
		deptname = (String)prop.get("BRANCHNAME");
		pname = (String)prop.get("NAME");
		uname = (String)prop.get("USERNAME");
		pid=(String)prop.get("PERSONID");
		phone = (String)prop.get("PHONE");
		mobilephone = (String)prop.get("MOBILEPHONE");
		
	}
	
	String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串
    String jgid=strObj[0];//单位编码
    String jgmc=strObj[1];//机构名称
    String ccbm=strObj[2];//机构层次编码
    //String jgid="441905000000";//12位机构编码
    String jgbh;//总队(2位),支队(4位),大队(6位)
    if("0000".equals(jgid.substring(2,6))){
    	jgbh = jgid.substring(0,2);
    }else if("00".equals(jgid.substring(4,6))){
    	jgbh = jgid.substring(0,4);
    }else{
    	jgbh = jgid.substring(0,6);
    }
    System.out.println("机构查询条件: "+jgbh);
%>
<html>
	<%
		//已上报内容
		String sql ="select a.alarmid, a.alarmregion, (select jgmc from t_sys_department where jgid = "
			+ "a.REPORTUNIT) as jgmc, a.title, (case when al.verifysituation is not null then"
			+ " 1 when al.reportsituation is not null then 2 when al.alarmdesc is not null "
			+ "then 3 else 0 end) from T_ATTEMPER_ALARMFLOW al, T_ATTEMPER_ALARM a where "
			+ "substr(al.flowid, 7)=(select max(substr(flowid, 7)) max from t_attemper_alarmflow"
			+ " where alarmid=al.alarmid group by alarmid) and al.alarmid=a.alarmid and "
			+ "a.EVENTSOURCE = '002022' and a.EVENTTYPE = '001024' and al.RECIVEUNIT = '" + deptcode + "'";
		
		sql = "select a.alarmid, (select jgmc from t_sys_department where jgid=al.reportunit) as reportunit, to_char(a.reporttime,'yyyy-mm-dd hh24:mi'), a.title, (case when al.verifysituation is not null then"
		+ " 1 when al.reportsituation is not null then 2 when al.alarmdesc is not null "
		+ "then 3 else 0 end), (case when substr(al.reportunit, 5, 2) = '00' then 1 when "
		+ "substr(al.reportunit, 3, 4) = '0000' then 2 else 0 end) from T_ATTEMPER_ALARMFLOW al, "
		+ "T_ATTEMPER_ALARM a where "
		+ "substr(al.flowid, 7)=(select max(substr(flowid, 7)) max from t_attemper_alarmflow"
		+ " where alarmid=al.alarmid group by alarmid) and al.alarmid=a.alarmid and "
		+ "a.EVENTSOURCE = '002022' and a.EVENTTYPE = '001024' and al.RECIVEUNIT = '"
		+ deptcode + "' and 1=(case when substr(al.reportunit,3,4)= '0000' and al.reportsituation is not null then 0 else 1 end) order by a.reporttime desc";
		
		System.out.println("弹出页面sql++++++++++++++++++"+sql);
		Object[][] result = DBHandler.getMultiResult(sql);
		
		sql = "select a.alarmid, (select jgmc from t_sys_department where jgid=al.reportunit) as reportunit, to_char(a.reporttime,'yyyy-mm-dd hh24:mi'), a.title, (case when al.verifysituation is not null then"
		+ " 1 when al.reportsituation is not null then 2 when al.alarmdesc is not null "
		+ "then 3 else 0 end), (case when substr(al.reportunit, 5, 2) = '00' then 1 when "
		+ "substr(al.reportunit, 3, 4) = '0000' then 2 else 0 end) from T_ATTEMPER_ALARMFLOW al, "
		+ "T_ATTEMPER_ALARM a where "
		+ "substr(al.flowid, 7)=(select max(substr(flowid, 7)) max from t_attemper_alarmflow"
		+ " where alarmid=al.alarmid group by alarmid) and al.alarmid=a.alarmid and "
		+ "a.EVENTSOURCE = '002022' and a.EVENTTYPE = '001024' and al.reportsituation is not null and al.verifysituation is null"
		+ " and substr(al.reportunit,3,4)= '0000' and a.reportunit='" + deptcode + "' order by a.reporttime desc";
		
		System.out.println("弹出页面sql++++++++++++++++++"+sql);
		Object[][] result1 = DBHandler.getMultiResult(sql);
		
		String crowdsql ="select taa.ALARMID,taa.ALARMREGION,taa.ROADNAME,taa.ROADDIRECTION,"
			+ " (select name from t_attemper_code where id=tcc.CONGESTIONTYPE)"
			+ " from T_ATTEMPER_ALARM taa,T_ATTEMPER_CONGESTION tcc"
			+ " where taa.ALARMID like '" + jgbh + "%' and taa.ALARMID=tcc.ALARMID and taa.CaseEndTime is null";
		System.out.println("弹出页面中，拥堵信息sql++++++++++++++++++"+crowdsql);
		Object[][] crowdresult = DBHandler.getMultiResult(crowdsql);
	%>
	<head>
		<title>更新信息</title>
		<link href="../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
	 	<script type="text/javascript" src="../base/js/prototype.js"></script>
		<script type="text/javascript" src="js/ceditpolice/materialInfo.js"></script>
		<script type="text/javascript" src="js/ceditpolice/editTrfficeCrowd.js"></script>	
		<script type="text/javascript" language="javascript">
			function get_cookie(Name){ 
				var search = Name + "="
				var returnvalue = "";
				if (document.cookie.length > 0){
					offset = document.cookie.indexOf(search)
					if (offset != -1){ 
						offset += search.length 
						end = document.cookie.indexOf(";", offset); 
						if (end == -1) 
						end = document.cookie.length; 
						returnvalue=unescape(document.cookie.substring(offset, end)) 
					} 
				} 
				return returnvalue; 
			}
			
			window.onunload = function(){
				document.cookie = "popped=";
			}
		</script>
		<meta http-equiv="refresh" content="60"> 
	</head>
<body>
	<div id="tdData" class="scrollDivWarinng" >
		<table class="table" >
	<%if(result != null && result.length>0){  %>
			待处理事项：
			<tr>
				<td class="wTableTopCenter" width="19%" style="text-align:center;border-bottom: 1 solid #A5D1EC;">上报单位</td>
				<td class="wTableTopCenter" width="19%" style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">上报时间</td>
				<td class="wTableTopCenter" width="19%" style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">警情标题</td>
				<td class="wTableTopCenter" width="19%" style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">状态</td>
				<td class="wTableTopCenter" width="8%" style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">批复</td>
				
			</tr>
			<%
				
					for(int i=0;i<result.length;i++){
						int zt = Integer.parseInt(result[i][4].toString());
						int zt1 = Integer.parseInt(result[i][5].toString());
						if(zt == 0)	result[i][4] = "新增警情";
						else if(zt == 1)	result[i][4] = "处理结果";
						else if(zt == 2 && zt1 == 0)	result[i][4] = "大队领导批复";
						else if(zt == 2 && zt1 == 1)	result[i][4] = "支队领导批复";
						else if(zt == 2 && zt1 == 2)	result[i][4] = "总队领导批复";
						else result[i][4] = "警情续报";
			%>
			<tr>
				<td class="wTableCenterCenter" style="font-size:11px;border-bottom: 1 solid #A5D1EC;"><%=result[i][1] %></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><%=result[i][2] %></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><%=result[i][3] %></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><%=result[i][4] %></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><input type="image" src="images/button/update.gif" onClick="onButtonClick0('flow2','<%=result[i][0] %>')"></td>
			</tr>
			<%  
					}
				} 
			%>
		
		</table>
		<br>
		<table class="table" >
		<%if(result1 != null && result1.length>0){  %>
			待办理事项：
			<tr>
				<td class="wTableTopCenter" width="19%" style="text-align:center;border-bottom: 1 solid #A5D1EC;">上报单位</td>
				<td class="wTableTopCenter" width="19%" style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">上报时间</td>
				<td class="wTableTopCenter" width="19%" style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">警情标题</td>
				<td class="wTableTopCenter" width="19%" style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">状态</td>
				<td class="wTableTopCenter" width="8%" style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">办理情况</td>
				
			</tr>
			<%
				
					for(int i=0;i<result1.length;i++){
						int zt = Integer.parseInt(result1[i][4].toString());
						int zt1 = Integer.parseInt(result1[i][5].toString());
						if(zt == 0)	result1[i][4] = "新增警情";
						else if(zt == 1)	result1[i][4] = "处理结果";
						else if(zt == 2 && zt1 == 0)	result1[i][4] = "大队领导批复";
						else if(zt == 2 && zt1 == 1)	result1[i][4] = "支队领导批复";
						else if(zt == 2 && zt1 == 2)	result1[i][4] = "总队领导批复";
						else result1[i][4] = "警情续报";
			%>
			<tr>
				<td class="wTableCenterCenter" style="font-size:11px;border-bottom: 1 solid #A5D1EC;"><%=result1[i][1] %></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><%=result1[i][2] %></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><%=result1[i][3] %></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><%=result1[i][4] %></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><input type="image" src="images/button/update.gif" onClick="onButtonClick0('flow1','<%=result1[i][0] %>')"></td>
			</tr>
			<%  
					}
				} 
			%>
		</table>
		<br>
		<%if(crowdresult!=null){ %>
		<table class="table" >
			拥堵信息：
			<tr>
				<td class="wTableTopCenter" width="19%"style="text-align:center;border-bottom: 1 solid #A5D1EC;">提交大队</td>
				<td class="wTableTopCenter" width="19%"style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">路段名称</td>
				<td class="wTableTopCenter" width="19%"style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">方向</td>
				<td class="wTableTopCenter" width="19%"style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">交通状况</td>
				<td class="wTableTopCenter" width="19%"style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">解除拥堵</td>
				<!-- <td class="wTableTopCenter" width="8%"style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">处理</td>
				<td class="wTableTopCenter" width="8%"style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">批复</td>
				<td class="wTableTopCenter" width="8%"style="text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;">续报</td> -->
				
			</tr>
			<%
				if(crowdresult != null && crowdresult.length>0){ 
					for(int i=0;i<crowdresult.length;i++){
			%>
			<tr>
				<td class="wTableCenterCenter" style="font-size:11px;border-bottom: 1 solid #A5D1EC;"><%=crowdresult[i][1] %></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><%=crowdresult[i][2] %></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><%=crowdresult[i][3] %></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><%=crowdresult[i][4] %></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><input type="image" src="images/button/update.gif" onClick="doCancelCrowd('<%=crowdresult[i][0] %>')"></td>
				<!-- <td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><input type="image" src="images/button/update.gif" onClick="onButtonClick('flow','<%=crowdresult[i][0] %>')"></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><input type="image" src="images/button/update.gif" onClick="onButtonClick('flow2','<%=crowdresult[i][0] %>')"></td>
				<td class="wTableCenterCenter" style="font-size:11px;text-align:center;border-left: 1 solid #A5D1EC;border-bottom: 1 solid #A5D1EC;" ><input type="image" src="images/button/update.gif" onClick="onButtonClick('flow1','<%=crowdresult[i][0] %>')"></td> -->
			</tr>
			<%  
					}
				} 
			%>
		</table>
		<%}%>
		<%if( (!(crowdresult != null && crowdresult.length>0)) && (!(result1 != null && result1.length>0)) && (!(result != null && result.length>0)) ){ %>
		暂时无更新！
		<%}%>
		<%--<input type="button" value="刷新" onclick="location.reload();">--%>
	</div>
</body>
</html>