<%@ page language="java"  pageEncoding="UTF-8"
	session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<html>
  <head>
    <title>预案管理</title>	
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<jsp:include page="../../ShareInc.html" />
	<Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	<link href="../../css/vcas.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="../../js/util/global.js"></script>	
	<script type="text/javascript" src="../../../sm/js/common/prototype.js"></script>	
	<script type="text/javascript" src="../../../sm/js/common/calendar/CalendarDate.js"></script>
	<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
	<script type="text/javascript" src="../../js/prmplan/Prmplan.js"></script>	
	<script type="text/javascript" src="../../js/prmplan/PrmplanAlert.js"></script>
	<script type="text/javascript" src="../../js/prmplan/DivWindow.js"></script>
	<script type="text/javascript">
		prmplanAlert();
	</script>
  </head>
<body class="body" onLoad="doFilter('init');">
   <div id="a" class="table_tab" align = "center">
      <ul id="tags" align = "center">
         <li class='selectTag'><a href='#'  class="td_2">预案管理</a></li>
     </ul>
	<!--查询区域2008-9-19-->
	 <div id="tagContent"   align = "center" >
	    <div id="info" class='tagContent selectTag'>	
	       <table  width="100%" border="0">
	          <tr>
	             <td width="70%" height="13">
	                <table width="95%">
	                   <tr>
		                  <td width="20%" class="td_2" align="right">预案名称：</td>
					      <td width="30%" class="td_2" align="left">
						    <input id="YAMC" type="text" size="20"/>				     
						  </td>
						 <td width="20%" class="td_2" align="right">应用场景：</td>
					      <td width="30%" class="td_2" align="left" id="YYCJ_TD">
						     <script language="javascript">
							 	fillListBox("YYCJ_TD","YYCJ","200","SELECT ID,NAME FROM T_PREPLAN_TYPE ORDER BY ID","未选择","");
							 </script>					     
						 </td>
				       </tr>
                   </table>
                </td>
		         <td width="30%">
                  <input name="query" type="image" src="../../image/button/btnquery.gif" onClick="doFilter();" />
                  <input name="insert" type="image" src="../../image/button/btninsert.gif" onClick="showInsertPage();" />
                </td>
              </tr>         
           </table>				
        </div>
      </div>
	<!--查询区域2008-9-19-->	 	
	<!--数据表格2008-9-19 -->
	<div class="table_grid">
	  <table id="dataTable" width="100%" height="98%" border="0" cellpadding="0" cellspacing="1"bgcolor="#FFFFFF">
        <tr class="scrollColThead">
          <td  align="center"class="tr_1">
            <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
              <tr class="tr_1">
                <td width=""  align="center" style="display:none">预案编号</td>
                <td width=""  align="center" style="display:none">预案类型</td>                               
                <td width="18%" align="center">预案名称</td>
                <td width="15%" align="center">创建单位</td>
                <td width="10%" align="center">创建人</td>
                <td width="15%" align="center">创建时间</td>
                <td width="15%" align="center">修改时间</td>
                <td width="15%" align="center">审核意见</td> 
                <td width="12%" align="center">操作</td>
              </tr>
            </table>
          </td>
        </tr>	
        <tr>
          <td align="center" valign="top" id="preplanGrid"></td>
        </tr>                          	            	            	            	            
      </table>
    </div>
  <!--数据表格2008-9-19-->     
  </div> 													
</body>
</html>
