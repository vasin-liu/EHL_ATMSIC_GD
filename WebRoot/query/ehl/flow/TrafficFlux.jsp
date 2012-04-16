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
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../js/flow/TrafficFlux.js"></script>	
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
			               <td width="7%" rowspan="2" align="center" >
			                     <input type="radio" name="returnTag"  value="cross" onClick="doClick(this.value)" checked>&nbsp;路口
			                     <input type="radio" name="returnTag" value="roadSeg" onClick="doClick(this.value)">&nbsp;路段
		                    </td>
		                    
						  
			               <td class="td_2" width="10%"  align="right">
			                 路口/段：             
			               </td>
			               <td width="15%" align="left" id="roadTdId" class="td_2" colspan='3'>
		                        <script language="javascript">
							         fillListBox("roadTdId","roadSelectId","246","SELECT CROSSINGID,CROSSINGNAME FROM T_ROAD_CROSSINGINFO","");
							    </script>   
		                   </td>
		                   <td width="10%" align="center" valign="top">
			   	               <input name="query" type="image" src="../../image/button/btnstat.gif"  onClick="statFlux();"/>
			   			   </td>
                        </tr>
	                    <tr>
	                        
	               			<td width="10%" align="right">通过时间：</td>
	               		    <!-- 时间 -->
						
						    <td width="7%" align="left">
							   <input type="text" id="STARTTIME" class="text"  onClick="SelectDateTime(this,0);" readonly style="width:100px;"/>
						    </td>
						    <td width="1%" align="left">--</td>
						    <td width="7%" align="left">
							   <input type="text" id="ENDTIME" class="text"  onClick="SelectDateTime(this,0);" readonly style="width:100px;"/>
	                        </td>
									  
							<!-- 时间 -->
							<td width="10%" align="center" valign="top">
		    	              <input name="query" type="image"  src="../../image/button/btnchart.gif"  onClick="chart();"/>
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
	<div id="tagContent"  align = "center" style="height:74%;width:781;margin-top:3">
	     <table width="100%" border="1" height="100%" style="margin-left:3;">
	        <tr width="100%" height="100%">
	           <td width="45%"  height="100%" valign="top">
                  <div id="tdData" class="rollDiv">
				      <table id="tabVeh" class="scrollTable" width=100% cellSpacing=0 cellPadding=0>
					  	  <tr class='scrollColThead'  style="text-decoration: none;	background-color: #B4C1E2;line-height: 20px;">
						  	  <td width='40%'  align='center'>路口路段</td>
	                          <td width='20%' align='center'>流量</td>
	                          <td width='20%' align='center'>占有率（%）</td>
	                          <td width='20%' align='center'>平均速度</td>
					  	  </tr>
					  </table>
				  </div>
	           </td>
	           <!--2009-3-20 修改-->
	           
	        </tr>
	    </table> 
	 </div>
	<!--数据表格2008-9-19-->  	
   </div>																			
</body>
</html>
	   