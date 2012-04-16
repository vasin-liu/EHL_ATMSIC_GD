<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<html>
  <head>
    <title>设备信息统计</title>	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	<link href="../../css/tira.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/popup/Popup.js"></script>	
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../js/equipment/EquipmentStat.js"></script>	
	<script type="text/javascript" src="../../js/common/tira.js"></script>
  </head>
  
<body class="body" >
   <div id="dContainer" class="table_tab" align = "center">
      <ul id="tags" align = "center">
         <li class='selectTag'><a href='#'  class="td_2">设备统计</a>
         </li>
      </ul>
	<!--查询区域2008-9-19-->
	  <div id="tagContent"  align = "center" >
	     <div id="info" class='tagContent selectTag'>	
	        <table  width="100%" border="0">
	           <tr>
	              <td width="100%" height="13" align="left">
	                 <table width="100%">
	                    <tr>
			              
			               <td width="7%" align="right" class="td_2">
			                  统计方式：	                
			               </td>
			               <td width="5%" align="left" id="statTypeTdId" class="td_2">
		                         <select id='statTypeId' width='130' onchange="changeTableTitle(this.value)"> 
		                            <option value='XZXQ'>行政辖区</option>
		                            <option value='SSXT'>所属系统</option>
		                            <option value='SBSYZT'>设备使用状态</option>
		                            <option value='SBZT'>设备状态</option>
		                         </select>     
		                   </td>
		                   <td width="9%" align="right" class="td_2">
			                 设备使用状态：	                
			               </td>
			               <td width="5%" align="left" id="SBSYZTTD" class="td_2">
		                       <script language="javascript">
							         fillListBox("SBSYZTTD","SBSYZT","100","select DISTINCT id,value from atms_equipment_dict  where type='设备状态'","");
							    </script>           
		                   </td>
		                    <td width="7%" align="right" class="td_2">
			                 设备状态：	                
			               </td>
			               <td width="5%" align="left" id="SBZTTD" class="td_2">
		                       <script language="javascript">
							         fillListBox("SBZTTD","SBZT","100","select DISTINCT id,value from atms_equipment_dict  where type='使用状态'","");
							    </script>           
		                   </td>
		                 
	    	               <td width="7%" align="center" valign="top">
	    	                <input name="query" type="image" src="../../image/button/btnstat.gif"  onClick="statEquipment();"/>
	    			       </td>
	    			       <td width="7%" align="center" valign="top">
		    	              <input name="query" type="image" src="../../image/button/btnchart.gif"  onClick="chart();"/>
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
					  	  <tr class='scrollColThead' valign="top" style="text-decoration: none;	background-color: #B4C1E2;line-height: 20px;">
						  	 <td width='40%'  align='center'>所属辖区</td>
	                          <td width='30%' align='center'>设备状态</td>
	                          <td width='30%' align='center'>数量</td>
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
	   