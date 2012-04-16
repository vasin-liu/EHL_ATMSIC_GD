<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<html>
  <head>
    <title>事故信息统计</title>	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	<link href="../../css/tira.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/popup/Popup.js"></script>	
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../js/accident/accstat.js"></script>
	<script type="text/javascript" src="../../js/common/tira.js"></script>
	<style type="text/css">
		#tags li.selectTag div {
				background-position: right top;
				color: #000000;
				height: 25px;
				line-height: 25px;
			}
	</style>
  </head>
  
<body class="body" onload="accstat.initTime()">
   <div id="dContainer" class="table_tab" align = "center">
      <ul id="tags" align = "center">
         <li class='selectTag'><div  class="td_2">事故信息统计</div>
         </li>
      </ul>
	<!--查询区域2009-4-15-->
	  <div id="tagContent"  align = "center" >
	     <div id="info" class='tagContent selectTag'>	
	     	<table align ="left" >
		     	<tr>
		     		<td width = "80px">统计方式：</td>
		     		<td>
		     			<div style ="width:150px;border-style :solid ;border-width :1px;border-color:#BFBFBF">
		     				<input type = "radio" name = "statmode" value="1"  checked ="true"></input>事故地点
		     				<input type = "radio" name = "statmode" value="2" ></input>车辆类型
		     				<input type = "radio" name = "statmode" value="3" ></input>事故类型
		     			</div>
		     		</td>
		     		<td width = "60px">&nbsp;&nbsp;时间：</td>
		     		<td>
		     			<div style ="width:100px;border-style :solid ;border-width :1px;border-color:#BFBFBF">
		     				<input type = "radio" name = "timemode" value="1" onclick = "accstat.show('hourAndDate')" checked ="true"></input>小时
		     				<input type = "radio" name = "timemode" value="2" onclick = "accstat.show('hourAndDate')"></input>日
		     				<br>
		     				<input type = "radio" name = "timemode" value="3" onclick = "accstat.show('week')"></input>周
		     				&nbsp;&nbsp;&nbsp;
		     				<input type = "radio" name = "timemode" value="4" onclick = "accstat.show('month')"></input>月
		     			</div>
		     		</td>
		     		<td width="250px" align ="center">
		     			<div id ="hourAndDate" style="width:250">
		     				起止日期：    
		                    <input name="text" style="width :70px"  type="text" class="text" id="STARTTIME" onClick="SelectDate(this,0);" />	              
			                -	              
		                    <input name="text2" style="width :70px" type="text" class="text" id="ENDTIME" onClick="SelectDate(this,0);" />	
		     			</div>
		     			<div id ="week" style ="display:none">
		     				     <script type="text/javascript">
			     			        createYearSelect('week','monthYear,monthMonth','70','',false);
			     			    </script>
		     				
		     			</div>
		     			<div id ="month" style ="display:none">
		     			    <script type="text/javascript">
		     			        createYearSelect('month','year','70');
		     			    </script>
		     			</div>
		     		</td>
		     		<td width = "50px">
		     			<input name="query"  type="image" src="../../image/button/btnstat.gif" onclick = "accstat.doClick()"/>
		     			<input type="image" src="../../image/button/btnchart.gif" onclick = "periodFlowChart();"/>
		     		</td>
		     	</tr>
	     	</table>
	     </div>
      </div>
	<!--查询区域2009-04-15-->	
	 
    <!--数据表格2009-04-15 -->
	<div style="overflow:scroll;border:solid;border-width:1px;height:400px;width:784px;margin-left:5" id="overSpeedList"  align = "center" >
	 </div>
	<!--数据表格2009-04-15-->  	
   </div>																			
</body>
</html>
	   