<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<html>
  <head>
    <title>交通流量统计</title>	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	<link href="../../css/tira.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/popup/Popup.js"></script>	
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDate.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../js/flow/PeriodFlow.js"></script>	
	<script type="text/javascript" src="../../js/common/tira.js"></script>
  </head>
  
<body class="body" onload="initTime();">
   <div id="dContainer" class="table_tab" align = "center">
      <ul id="tags" align = "center">
         <li class='selectTag'><a href='#'  class="td_2">交通流量统计</a>
         </li>
      </ul>
	<!--查询区域2008-9-19-->
	  <div id="tagContent"  align = "center" >
	     <div id="info" class='tagContent selectTag'>	
	        <table  width="100%" border="0">
	           <tr>
	              <td width="100%" height="13" align="left">
	                 <table width="100%" border="0">
	                    <tr>
			               <td width="17%" rowspan="2" align="center" >
			                     <input type="radio" name="returnTag"  value="cross"  onclick="doClick('cross');" checked>&nbsp;路口
			                     <input type="radio" name="returnTag" value="roadSeg" onclick="doClick('seg');">&nbsp;路段
		                    </td>
		                    
						   <td rowspan=2 width="13%" align="right">&nbsp;&nbsp;时间：</td>
				     		<td  rowspan=2>
				     			<div style ="width:100px;border-style :solid ;border-width :1px;border-color:#BFBFBF">
				     				<input type = "radio" name = "timemode" value="1" onclick = "flowstate.show('hourAndDate')" checked ="true"></input>小时
				     				<input type = "radio" name = "timemode" value="2" onclick = "flowstate.show('hourAndDate')"></input>日
		
				     				<br>
				     				<input type = "radio" name = "timemode" value="3" onclick = "flowstate.show('week')"></input>周
		
				     				&nbsp;&nbsp;&nbsp;
				     				<input type = "radio" name = "timemode" value="4" onclick = "flowstate.show('month')"></input>月
		
				     			</div>
				     		</td>
			               <td class="td_2" width="18%"  align="right">
			                 路口/段：             
			               </td>
			               <td width="25%" align="left" id="roadTdId" class="td_2" colspan='3'>
		                        <script language="javascript">
							         fillListBox("roadTdId","roadSelectId","213","SELECT CROSSINGID,CROSSINGNAME FROM T_ROAD_CROSSINGINFO","");
							    </script>   
		                   </td>
		                   <td width="10%" align="center" valign="top">
			   	               <input name="query" type="image" src="../../image/button/btnstat.gif"  onclick="statFlux();"/>
			   			   </td>
                        </tr>
	                    <tr>
	                        <td class="td_2" width="18%"  align="right">
			                    起止日期：                
			                </td>
	               			<td width="250px" align ="left" colspan="3">
				     			<div id ="hourAndDate" style="width:250">
				     				 
				                    <input name="text" style="width :100px"  type="text" class="text" id="STARTTIME" onClick="SelectDate(this,0);" />	              
					                -	              
				                    <input name="text2" style="width :100px" type="text" class="text" id="ENDTIME" onClick="SelectDate(this,0);" />	
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
			     		   <td width="10%" align="center" valign="top">
	    	                  <input name="query" type="image" src="../../image/button/btnchart.gif"  onclick="periodFlowChart('','','');"/>
	    			       </td>
						 </tr>
					  </table>
					</td>
                 </tr>
			 </table>	
      </div>
 </div>
	<!--查询区域2008-9-19-->	
	 
    <!--数据表格2008-9-19 -->
	<!--数据表格2009-04-15 -->
	<div style="overflow:scroll;border:solid;border-width:1px;height:400px;width:784px;margin-left:5" id="tbl-container"  align = "center" >
	    <table id="tabVeh" class="scrollTable" width=1000px cellSpacing=0 cellPadding=0>
	  	  <tr class='scrollColThead' valign="top" style="text-decoration: none;	background-color: #B4C1E2;line-height: 20px;">
	  	      <td  style="width:18%" class="scrollRowThead" style="text-align:center">路口路段</td>
		  	  <td   style="width:3%" align='center'>1时</td>
              <td  style="width:3%" align='center'>2时</td>
              <td  style="width:3%" align='center'>3时</td>
              <td  style="width:3%" align='center'>4时</td>
              <td  style="width:3%" align='center'>5时</td>
              <td  style="width:3%" align='center'>6时</td>
              <td   style="width:3%" align='center'>7时</td>
              <td  style="width:3%" align='center'>8时</td>
              <td  style="width:3%" align='center'>9时</td>
              <td  style="width:3%" align='center'>10时</td>
              <td  style="width:3%" align='center'>11时</td>
              <td  style="width:3%" align='center'>12时</td>
              <td   style="width:3%" align='center'>13时</td>
              <td  style="width:3%" align='center'>14时</td>
              <td  style="width:3%" align='center'>15时</td>
              <td  style="width:3%" align='center'>16时</td>
              <td  style="width:3%" align='center'>17时</td>
              <td  style="width:3%" align='center'>18时</td>
              <td  style="width:3%" align='center'>19时</td>
              <td  style="width:3%" align='center'>20时</td>
              <td  style="width:3%" align='center'>21时</td>
              <td  style="width:3%" align='center'>22时</td>
              <td  style="width:3%" align='center'>23时</td>
              <td  style="width:3%" align='center'>24时</td>
              <td  style="width:10%" align='center'>合计</td>
	  	   </tr>
	  </table>
	 </div>
	<!--数据表格2009-04-15-->  
				      
					
   </div>																			
</body>
</html>
	   