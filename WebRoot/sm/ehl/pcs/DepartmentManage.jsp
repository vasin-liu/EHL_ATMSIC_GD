<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ include file="../../../base/Message.oni"%>

<html>
<head>
	<%
	    String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取机构信息串
	    String jgid="",jgmc="",ccbm="";
		try{
		     jgid=strObj[0];//单位编码
		     jgmc=strObj[1];//单位名称
		     ccbm=strObj[2];//单位层次编码
	    }catch(Exception e){
	%>
	    <script type="text/javascript">
	    	alert("登陆用户对应机构不存在\n请检查用户表和机构表数据！");
	    </script>
	<%
	    }
	 %>
	 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	 <title>组织机构树</title>
	 <jsp:include page="../../../base/ShareInc.html" />
	 <Link Rel="STYLESHEET" Href="../../css/pagetag/pagetag.css" Type="text/css">
	 <script type="text/javascript" src="../../js/pcs/DepartmentTree.js"></script>
	 <script type="text/javascript" src="../../js/pcs/DepartmentManage.js"></script>
	 <script language="javascript">
	//定义全局标量
	var G_jgID = <%=jgid%>;
	var G_divID  = "";
	function doOnLoad(){
	 	  onLoadToolbar();
	 	  isHaveRoot();
	 	  doDepartInfo('<%=jgid%>');
	 	  adjustHeight('dpTable,dpTD');
	 	}
	 	
	 </script>
	 <style> 
	   body {
	  margin-left:0;
	  margin-right:0;
	   }
	</style> 
</head>
<body onLoad="doOnLoad();" bgcolor="#FFFFFF" text="#000000" style="height:500">
  <table id="dpTable" width="800" border="0"  cellpadding="0" cellspacing="0" style="txt-align:center;position:absolute;top:2;height:516">
	<tr>
      <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
	        <td width="3"><img src="../../image/back/back_topleft.gif" width="3" height="1" /></td>
	        <td background="../../image/back/back_topmid.gif"><img src="" alt="22" name="" width="32" height="1" align="left" /></td>
	        <td width="3"><img src="../../image/back/back_topright.gif" width="3" height="1" /></td>
          </tr>
        </table>
      </td>
    </tr>
	<tr>
	  <td style="txt-align:center" valign="top">
	    <table border="0" style="txt-align:center" cellpadding="0" cellspacing="0">
		  <tr>
		    <td id="dpTD" width="3" background="../../image/back/back_midleft.gif" ></td>
			<td>
			  <table width="100%" height="88%" border="0" align="center">
				<tr>
				  <td height="25" valign="top" colspan="3">
					<div id="toolbar_zone" style="width: 100%; border: 1px solid #BFBFBF;" />
					<div id="modify"></div>
				  </td>
				</tr>
				<tr>
				  <td colspan=3 align="center" valign="bottom">
					 <span class="STYLE1">机构管理</span>
				  </td>
				</tr>
				<tr>
				  <td colspan=3 valign="top">
					<hr>
				  </td>
				</tr>
				<tr>
				  <td width="20%">
					<div id="treeDiv" class="divTreeStyle">
						<table width="100%"  border="0" cellpadding="0" cellspacing="0">
						  <tr>
							<td height="5">&nbsp;
							</td>
							<td height="10">
							</td>
						  </tr>
						  <tr>
							<td>
							</td>
							<td valign="top">
							  <span id=<%=jgid%> style="color:black;cursor:hand"
								onclick="doOnClick('<%=ccbm %>','<%=jgid%>')"> 
							    <img id=<%="i" + jgid %> src="../../image/tree/plusOpen.gif"><%=jgmc %>
							  </span>
							  <br>
							  <div id="<%=ccbm %>" name="<%=jgid%>" style="display:none">
							  </div>
							</td>
						  </tr>
						</table>
					  </div>
					</td>
					<td width="1%">
					<td width="79%">
					  <ul id="tagsd" align = "center">
							<li class='selectTag'><a href='#' onclick="selectTag('b',this)"><font style = font-size:12px;>机构信息</font></a></li><br>
							<li><a href='#' onclick="selectTag('a',this)"><font style = font-size:12px;>所属人员</font></a></li><br>
							<li><a href='#' onclick="selectTag('c',this)"><font style = font-size:12px;>所属车辆</font></a></li><br>
					   </ul>
					   <table style="height:23">
					      <tr>
					         <td></td>
					      </tr>
					   </table>
					   <div id="tagContentd"  align = "center" valign="top">
					      <div id='a'  class='tagContentd' valign="top">
					         <div class="rollDiv" style="offsetTop:0px;" >
						         <table id="tabList" class="scrollTable"  cellSpacing=0 cellPadding=0 border=0>
						             <tr>
								      <td rowspan="3">
									      <div id="tableContainer" class="tableContainer">
										<table border="0" cellpadding="0" cellspacing="0" width="100%" class="scrollTable">
										<thead class="fixedHeader" id="fixedHeader">
											<tr>
												<th>姓名</th>
												<th>性别</th>
												<th>出生日期</th>
												<th>身份证号</th>
												<th>警衔</th>
											</tr>
										</thead>
										</table>
										</div>
                                      </td>
								    </tr>
						         </table>
					         </div>
					      </div>
					      <div id='c'  class='tagContentd' valign="top">
					         <div class="rollDiv" style="offsetTop:0px;" >
						         <table id="tabList" class="scrollTable"  cellSpacing=0 cellPadding=0 border=0>
						             <tr>
								      <td rowspan="3">
									      <div id="tableContainerCar" class="tableContainer">
										<table border="0" cellpadding="0" cellspacing="0" width="100%" class="scrollTable">
										<thead class="fixedHeader" id="fixedHeader">
											<tr>
												<th>号牌种类</th>
												<th>号牌号码</th>
												<th>车辆种类</th>
												<th>所属机构</th>												
											</tr>
										</thead>
										</table>
										</div>
                                      </td>
								    </tr>
						         </table>
					         </div>
					      </div>					      
					      <div id='b'  class='tagContentd selectTag' valign="top">
					         <div  style="height:350;width:530;overflow:hidden;offsetTop:0px;zindex:-1;border: 1px solid #99C4F2" >
								<table  width="100%" height="360" border="0" align="center" cellpadding="0" cellspacing="1" >
								  <tbody class='scrollContent'>
									  <tr>
										<td width="16%" align="right" bgcolor="#99C4F2">
										  机构编码：

			
										</td>
									    <td width="32%" bgcolor="#FFFFFF">
										  <input type="text" id="JGID" class="text"	style="width:170px;border:0px" readOnly=true />
										</td>
										<td width="16%" align="right" bgcolor="#99C4F2">
										  机构名称：

			
										</td>
										<td width="32%" bgcolor="#FFFFFF">
										  <input type="text" id="JGMC" class="text"	style="width:170px;border:0px" readOnly=true />
										</td>
									  </tr>
									  <tr>
										<td align="right" bgcolor="#99C4F2">
										  行政级别：

			
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" name="XZJB" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
										<td align="right" bgcolor="#99C4F2">
										  机构职能：

			
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" id="JGZN" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
									  </tr>
									  <tr>
										<td align="right" bgcolor="#99C4F2">
										  业务描述：

			
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" name="YWMS" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
										<td align="right" bgcolor="#99C4F2">
										  机构类型：

			
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" id="JGLX" class="text"	style="width:170px;border:0px" readOnly=true />
										</td>
									  </tr>
									  <tr>
										<td align="right" bgcolor="#99C4F2" >
										  所处地址：

			
										</td>
										<td bgcolor="#FFFFFF" colspan="3">
										  <input type="text" name="SCDZ" class="text" style="width:250px;border:0px" readOnly=true />
										</td>
									  </tr>
									  <tr>
									    <td align="right" bgcolor="#99C4F2">
											邮政编码：


			
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" id="YZBM" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
										<td align="right" bgcolor="#99C4F2">
										  编制人数：

			
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" name="BZRS" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
									  </tr>
									  <tr>
									    <td align="right" bgcolor="#99C4F2">
										  在编民警：


			
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" id="ZBMJS" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
										<td align="right" bgcolor="#99C4F2">
										  在编职工：

			
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" name="ZBZGS" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
									  </tr>
									  <tr>
									    <td align="right" bgcolor="#99C4F2">
										  其他人数：


			
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" id="QTRS" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
										<td align="right" bgcolor="#99C4F2">
										  负责人：
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" name="FZR" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
									  </tr>
									  <tr>
									    <td align="right" bgcolor="#99C4F2">
										  负责人电话：
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" id="FZRDH" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
										<td align="right" bgcolor="#99C4F2">
										  负责人手机：
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" name="FZRSJ" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
										
									  </tr>
									  <tr>
									    <td align="right" bgcolor="#99C4F2">
										  值班电话1：


			
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" id="ZBDH1" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
										<td align="right" bgcolor="#99C4F2">
										  值班电话2：

			
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" name="ZBDH2" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
										
									  </tr>
									  <tr>
									    <td align="right" bgcolor="#99C4F2">
										  值班电话3：


			
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" id="ZBDH3" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
										<td align="right" bgcolor="#99C4F2">
										  传真：

			
										</td>
										<td bgcolor="#FFFFFF">
										  <input type="text" name="CZDH" class="text" style="width:170px;border:0px" readOnly=true />
										</td>
										
									  </tr>
								  </tbody>
								</table>
					         </div>
					      </div>
					   </div>
					</td>
				</tr>
			  </table>																
			</td>
			<td width="3" background="../../image/back/back_midright.gif"></td>
			</tr>
		  </table>
		</td>
	  </tr>
	  <tr>
        <td>
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="6"><img src="../../image/back/back_bottleft.gif" width="6" height="7" /></td>
              <td background="../../image/back/back_bottmid.gif"><img src="" alt="" name="" width="32" height="7" align="left" /></td>
              <td width="6" valign="top"><img src="../../image/back/back_bottright.gif" width="6" height="7" /></td>
            </tr>
         </table>
        </td>
      </tr>
  </table>
</body>
</html>
