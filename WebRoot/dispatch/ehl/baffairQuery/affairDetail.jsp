<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
   <%@page import="com.ehl.dispatch.util.AlarmAffairType" %>
<!-- 
	 * 
	 * 版 权：北京易华录信息技术股份有限公司 2009
	 * 文件名称：affairSDetail.jsp
	 * 摘 要：警情信息展示界面
	 * 当前版本：1.0
	 * 作 者：LChQ  2009-4-14
	 * 修改人：
	 * 修改日期：


 -->

<%
	String alarmId = request.getParameter("ALARMID");
	String alarmType = request.getParameter("ALARMTYPE");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>警情详细信息</title>
		<script type="text/javascript"
			src="../../../sm/js/common/prototype.js">	</script>
		<script type="text/javascript"
			src="../../js/affairQuery/affairDetail.js">	</script>
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">	
		<script type="text/javascript">
			var alarmId = '<%=alarmId%>';
			var alarmType = '<%=alarmType%>';
			
			window.attachEvent("onload",initPageWithData)
		</script>
		<style type="text/css">
.queryTD1 {
	line-height: 16px;
	font-size: 9pt;
	empty-cells: show;
	text-align: right;
	width: 11%;
	border-right: 1 #caa solid;
	border-bottom: 1 #caa solid;
}

.queryTD2 {
	line-height: 16px;
	font-size: 9pt;
	empty-cells: show;
	text-align: left;
	width: 22%;
	border-right: 1 #caa solid;
	border-bottom: 1 #caa solid;
}

.detailTR1 {
	line-height: 16px;
	font-size: 9pt;
	empty-cells: show;
}


.queryListTd {
	line-height: 16px;
	font-size: 9pt;
	text-align: center;
	border-right: 1 #caa solid;
	border-bottom: 1 #caa solid;
}

.queryTD3 {
	line-height: 16px;
	font-size: 9pt;
	empty-cells: show;
	text-align: left;
	border-right: 1 #caa solid;
	border-bottom: 1 #caa solid;
}


.tableList{
	background:#ffffff;
	border-top: 0 solid #CCCCCC;
	border-right: 0 solid #000000;
	border-bottom: 0 solid #000000;
	border-left: 1 solid #caa;
	border-collapse:collapse;
	font-size:11px;
	text-align: center;
}


.tdList{
	border-top: 0 solid #000000;
	border-right: 1 solid #caa;
	border-bottom: 1 solid #caa;
	border-left: 1 solid #caa;
	line-height: 16px;
	color: #000000;
	border-collapse : separate;
	empty-cells:show;
	text-align: center;
	
}

</style>
<style media=print>
	.Noprint{display:none;}
	.PageNext{page-break-after: always;}
</style>
	</head>

	<body>
		<% if( alarmType.equals(AlarmAffairType.accident.getTypeValue()) ||  alarmType.equals(AlarmAffairType.trafficjam.getTypeValue()))
			{ %>
			<jsp:include flush="true" page="accidentAndBlock.jsp"></jsp:include>
			<!--  事故和拥堵 -->
		<% 	} 
			else if( alarmType.equals(AlarmAffairType.troubleCargo.getTypeValue()) )
			{ %>
			<jsp:include flush="true" page="troubleCargoDetail.jsp"></jsp:include>
			<!--  大型车故障 -->
		<% 	} 
			else if( alarmType.equals(AlarmAffairType.disasterWeather.getTypeValue()) )
			{ %>
			<jsp:include flush="true" page="disasterWeatherDetail.jsp"></jsp:include>
			<!--  灾害天气 -->
		<% 	}
			else if( alarmType.equals(AlarmAffairType.citizenCase.getTypeValue()) )
			{ %>
			<jsp:include flush="true" page="citizenCaseDetail.jsp"></jsp:include>
			<!--  市政事件(煤气,自来水等)		 -->
		<% 	} 
			else if( alarmType.equals(AlarmAffairType.geologicalDisaster.getTypeValue()) )
			{ %>
			<jsp:include flush="true" page="geologicalDetail.jsp"></jsp:include>
			<!-- 地质灾害,塌方,泥石流等 -->
		<% 	} 
			else if( alarmType.equals(AlarmAffairType.socialCase.getTypeValue()) )
			{ %>
			<jsp:include flush="true" page="socialCaseDetail.jsp"></jsp:include>
			<!-- 治安问题事件 -->
		<% 	} 
			else if( alarmType.equals(AlarmAffairType.fireAndBoom.getTypeValue()) )
			{ %>
			<jsp:include flush="true" page="fireAndBoomDetail.jsp"></jsp:include>
			<!-- 火灾,爆炸信息 -->
		<% 	}
			else if( alarmType.equals(AlarmAffairType.suspicionCar.getTypeValue()) )
			{ %>
			<jsp:include flush="true" page="suspicionCarDetail.jsp"></jsp:include>
			<!--卡口 嫌疑车辆 -->
		<% 	}%>  
		
		
		
		
	</body>
</html>
