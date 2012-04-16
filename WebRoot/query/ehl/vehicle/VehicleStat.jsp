<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
	<%@ page import="com.ehl.query.common.Constants" %>
<html>
  <head>
    <title>车辆统计</title>	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	<link href="../../css/tira.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/popup/Popup.js"></script>	
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDateTime.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../js/vehicle/VehicleStat.js"></script>	
	<script type="text/javascript" src="../../js/common/tira.js"></script>	
	 
  </head>
  
<body class="body" >
   <div id="dContainer" class="table_tab" align = "center">
      <ul id="tags" align = "center">
         <li class='selectTag'><a href='#'  class="td_2">车辆统计</a>
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
			              
			               <td width="1%" align="right" class="td_2">
			                  行政辖区：	                
			               </td>
			               <td width="5%" align="left" id="XZXQTD" class="td_2">
		                         <script type="text/javascript">
		                            fillListBox("XZXQTD","XZXQ","130","select DMZ,DMSM1 from T_TIRA_CODE where dmlb='300203' AND DMZ LIKE '%<%= Constants.XZQH_FROND4 %>%'","","","");
							   </script>       
		                   </td>
		                   <td class="td_2" width="7%" align="right">
			                  车辆状态：	              
			               </td>
			               <td width="5%" align="left" id="stateTdId" class="td_2">
		                        <script language="javascript">
							        fillListBox("stateTdId","stateId","185","select dmz,dmsm1 from T_TIRA_CODE where dmlb='300117'","","","");
							   </script>   
		                   </td>
	    	               <td width="10%" align="center" valign="top">
	    	                <input name="query" type="image" src="../../image/button/btnstat.gif"  onClick="statVeh();"/>
	    			       </td>
	    			       
                        </tr>
                        <tr>
                            <td class="td_2" width="5%"  align="right">
			                 统计类型：	              
			               </td>
			               <td width="5%" align="left"  class="td_2">
		                         <select id="statTypeId" style="width:130" onchange="selectChange('statTypeId');">
		                             <option value="CLLX">车辆类型</option>
		                             <option value="HPZL">号牌种类</option>
		                         </select>     
		                   </td>
			               <td class="td_2" width="5%"  align="right">
			                 车辆类型：                 
			               </td>
			               <td width="5%" align="left" id="kindTdId" class="td_2">
		                         <script type="text/javascript">
		                            initKindSelect();
							   </script>       
		                   </td>
			             
		                   <td width="10%" align="center" valign="top">
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
						  	 <td width='40%'  align='center'>车辆分类</td>
	                          <td width='30%' align='center'>数量</td>
	                          <td width='30%' align='center'>所占比例</td>
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
	   