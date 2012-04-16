<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<html>
  <head>
    <title>事故信息查询</title>	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include flush="true" page="../../ShareInc.html"></jsp:include>
	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	<link href="../../css/tira.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/popup/Popup.js"></script>	
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDate.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/page/PageCtrl.js"></script>
	<script type="text/javascript" src="../../js/accident/AccidentQuery.js"></script>
	<script type="text/javascript" src="../../js/common/tira.js"></script>	
  </head>
  
<body class="body" onload="loadTime()">
   <div id="dContainer" class="table_tab" align = "center">
      <ul id="tags">
         <li class='selectTag'><a href='#'  class="td_2">事故信息查询</a>
         </li>
      </ul>
	<!--查询区域2009-4-23-->
	  <div id="tagContent"  align = "center" >
	     <div id="info" class='tagContent selectTag'>	
               <table width="100%" border="0">
                    <tr>
		               <td width="13%" align="right" class="td_2">
		                  事故编号：
		               </td>
		               <td width="10%" align="left" class="td_2">
		               <input name="text" type="text" class="text" style="width:80" id="SGBH" />	   
		                  </td>
		               <td width="13%" align="right" class="td_2">
		                 发生时间：
		               </td>
		               <td width="25%" align="left" class="td_2">
							<input name="text" style="width :75px"  type="text" class="text" id="START_SGFSSJ" onClick="SelectDate(this,0);" />
							 －<input name="text" style="width :75px"  type="text" class="text" id="END_SGFSSJ" onClick="SelectDate(this,0);" />
		  			       </td>
		               <td class="td_2" width="10%" align="right">
		                 事故类型：
		               </td>
		               <td width="14%" align="left" id="SGLXTD" class="td_2">
		                      <script type="text/javascript">
		                           fillListBox("SGLXTD","SGLX","100","select DMZ,DMSM1 from t_tira_code where dmlb='300403'","","","");
						   </script> 
		               </td>
                   </tr>
                   <tr>
		               <td class="td_2" width="13%" align="right">
		                  行政区划：
		               </td>
		               <td width="10%" align="left" class="td_2">
		               <input name="text" type="text" class="text" style="width:80" id="XZQH" />	   
		                  </td>
		               <td class="td_2" width="13%" align="right">
		                 认定原因：
		               </td>
		               <td width="40%" align="left" class="td_2" id="SGRDYYTD" colspan="4">
		                      <script type="text/javascript">
		                           fillListBox("SGRDYYTD","SGRDYY","354","select DMZ,DMSM1 from t_tira_code where dmlb='300406' order by DMSM1","","","");
						   </script> 
						</td>
		                  <td width="10%" align="center" valign="bottom" rowspan=2>
		   			   </td>
		                  <td width="14%" align="right" valign="top" rowspan=2>
		   	              <input name="query"  type="image" src="../../image/button/btnquery.gif"  onClick="accidentInfoQuery();"/>
		   			   </td>
                </tr>
         </table>	             
	     </div>
      </div>
	<!--查询区域2008-9-19-->	
	 
    <!--数据表格2008-9-19 -->
	<div id="tagContent"  align = "center" style="height:78%;width:781;margin-top:3">
	     <table width="100%" border="0" height="100%" style="margin-left:3;">
	        <tr width="100%" height="100%">
	           <td width="45%"  height="100%" valign="top">
				  <div id="tdData" class="rollDiv">
				      <table id="tabVeh" class="scrollTable" width=100% cellSpacing=0 cellPadding=0>
					  	  <tr class='scrollColThead' valign="top" style="text-decoration: none;	background-color: #B4C1E2;line-height: 20px;">
						  	 <td width='10%'  align='center'>事故编号</td>
	                         <td width='10%' align='center'>行政区划</td>
	                         <td width='10%' align='center'>事故类型</td>
	                         <td width='20%' align='center'>事故发生时间</td>
	                         <td width='20%' align='center'>事故发生地点</td>
	                         <td width='30%' align='center'>事故认定原因</td>
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