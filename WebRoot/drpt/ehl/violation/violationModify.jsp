<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage" %>
<%@ include file="../../../Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt") == null ? "" : request.getParameter("insrtOrUpdt");
	System.out.println("insrtOrUpdt："+insrtOrUpdt);
	String proStr = insrtOrUpdt.equals("2") ? "readOnly " : "";
	System.out.println("proStr："+proStr);
	String bh = request.getParameter("bh")== null ? "" : request.getParameter("bh");
	String deafultArea = com.appframe.common.Setting.getString("deafultArea");
	System.out.println("参数："+deafultArea);
%>
<html>
	<head>
		<title>违法信息管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../../../base/css/style1/tab.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet"
			type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet"
			type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/dhtmlx/xmlCreator.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/list/SelectFastPinYin.js"></script>
		<script type="text/javascript" src="../../js/violation/violation.js"></script>
	    <style type="text/css">
			<!--
			table{
				font-size:11px;
			}
			.tableInput{
				border:1px solid #b5d6e6;
			}
			.td_r_b1{ 
			    border-bottom:1px solid #b5d6e6; 
				border-right:1px solid #b5d6e6; 
			}
			.STYLE2 {
				color: #FF0000;
			}
			.input1{
				border:0;
				width:270px;
			}
			.input2{
				border:0;
				width:40px;
				border-bottom:1px solid #b5d6e6; 
			}
			.input3{
				border:0;
				width:100px;
			}
			-->
        </style>
	</head>
	<%
		String deptId = DepartmentManage.getDeptInfo(request,"1");//获取机构信息串
		String depts[] = deptId.split(",");
	%>
	<body onLoad="doQuery(<%= bh %>);">
		<fieldset style="width:480px;height:300px;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:0px;font-size:12px;font-weight:600">
				<%="".equals(bh)?"违法记录新增":"违法记录编辑"%>
			</legend>
            <table width="475" align="center" class="tableInput" id="dataTable">
                <tr style="display:none">
                    <td>违法记录</td>
                    <td colspan="2">
                        <input id="BH" type="text" maxLength="32" name="lkid" class="text"
                            value=<%=bh%> style="width:280" readonly="true"></input>
                        <input id="deafultArea" type="text" value=<%=deafultArea%> readonly></input>
                        <input id="insrtOrUpdt" type="text" value=<%=insrtOrUpdt%> readonly></input>
                    </td>
                </tr>
                <tr>
                    <th width="10%" class="td_r_b">序号</th>
                    <th width="30%" class="td_r_b">项目</th>
                    <th width="60%" class="td_r_b">内容</th>
                </tr>
                <tr>
                    <td class="td_r_b">1</td>
                    <td class="td_r_b1">号牌号码</td>
                    <td class="td_r_b">
	                    <span id="HPHM_TD">
	                    	<script language="javascript">
		                       fillListBox("HPHM_TD","HPHM_AERA","40","select distinct substr(dm, 0, 1) as a,substr(dm, 0, 1) from t_sys_code where dmlb = '011006' and dm not like '<%=deafultArea%>%' order by a","<%=deafultArea%>");
	                        </script>
	                    </span>
                    	<input <%= proStr %> id="HPHM" class="input1" style="width:230px;" type="text" maxLength="6"/>
                    </td>
                </tr>
                <tr>
                    <td class="td_r_b">2</td>
                    <td class="td_r_b1">严重交通违法事实</td>
                    <td class="td_r_b" id="WFZL_TD">
                    	<script language="javascript">
	                       fillListBox("WFZL_TD","WFZL","270","select ID,NAME from T_ATTEMPER_CODE where ID LIKE '215%'","请选择");
                        </script>
                    	<%--<input id="WFZL" class="input1" type="text" />--%>
                    </td>
                </tr>
                <tr>
                    <td class="td_r_b">3</td>
                    <td class="td_r_b1">核载人数</td>
                    <td class="td_r_b"><input <%= proStr %> id="HZRS" class="input1" type="text" maxLength="3" onkeypress="checkNum(event)"/></td>
                </tr>
                <tr>
                    <td class="td_r_b">4</td>
                    <td class="td_r_b1">实载人数</td>
                    <td class="td_r_b"><input <%= proStr %> id="SZRS" class="input1" type="text" maxLength="3" onkeypress="checkNum(event)"/></td>
                </tr>
                <tr>
                    <td class="td_r_b">5</td>
                    <td class="td_r_b1">车属单位（人）</td>
                    <td class="td_r_b"><input <%= proStr %> id="CSDW" class="input1" type="text" maxLength="50"/></td>
                </tr>
                <tr>
                    <td class="td_r_b">6</td>
                    <td class="td_r_b1">查处日期</td>
                    <td class="td_r_b">
                    	<%
						if (!insrtOrUpdt.equals("2")) {
						%>
                    	<input id="CCSJ" class="input1" type="text" id="CCSJ" name="date" readonly onClick="SelectDate(this,0);" />
                    	<script language="javascript">
							var d = new Date(); 
							//d.setTime(d.getTime()-1000*60*60*24);
							d.setTime(d.getTime());
							var year = d.getYear(); 
							var month = d.getMonth()+1; 
							var date = d.getDate(); 
							if(month<10){
								month="0"+month;
							}
							if(date<10){
								date="0"+date;
							}
							$("CCSJ").value=year+"-"+month+"-"+date;
						</script>
						<%}else{%>
							<input id="CCSJ" class="input1" type="text" name="date" readonly/>
						<%}%>
                    </td>
                </tr>
                <tr>
                    <td class="td_r_b">7</td>
                    <td class="td_r_b1">路段名</td>
                    <td class="td_r_b"><input <%= proStr %> id="CCLD" class="input1" type="text" maxLength="50"/></td>
                </tr>
                <tr>
                    <td class="td_r_b">8</td>
                    <td class="td_r_b1">驾驶人</td>
                    <td class="td_r_b"><input <%= proStr %> id="JSR" class="input1" type="text" maxLength="10"/></td>
                </tr>
                <tr>
                    <td class="td_r_b">9</td>
                    <td class="td_r_b1">驾驶证号</td>
                    <td class="td_r_b"><input <%= proStr %> id="JSZ" class="input1" type="text" maxLength="30"/></td>
                </tr>
                <tr>
                    <td class="td_r_b">10</td>
                    <td class="td_r_b1">处理情况</td>
                    <td class="td_r_b"><input <%= proStr %> id="CLQK" class="input1" type="text" maxLength="500"/></td>
                </tr>
                <tr>
                    <td class="td_r_b">11</td>
                    <td class="td_r_b1">录入单位</td>
                    <td class="td_r_b"><input id="JGMC" class="input1" type="text" value=<%=depts[1]%> readonly/></td>
                </tr>
                <tr>
                	<td colspan="3" align="center">
                		<table width="100%">
                			<tr>
                				<%
								if (!insrtOrUpdt.equals("2")) {
								%>
                				<td width="33%" align="center">
			                		<span class="search">
			                			<a href="#" onclick="modify(<%=insrtOrUpdt%>);"
											class="currentLocation"> <span class="lbl">保存</span>
										</a>
			                		</span>
			                	</td>							
			                	<%
			                	}
								if (insrtOrUpdt.equals("0")) {
								%>	
			                	<td width="33%" align="center">
			                		<span class="search">
			                			<a href="#" onclick="reset();"
											class="currentLocation"> <span class="lbl">重置</span>
										</a>
			                		</span>
			                	</td>
			                	<%
								}
								if (insrtOrUpdt.equals("1")) {
								%>	
			                	<td width="33%" align="center">
			                		<span class="search">
			                			<a href="#" onclick="doQuery(<%= bh %>);"
											class="currentLocation"> <span class="lbl">重置</span>
										</a>
			                		</span>
			                	</td>
			                	<%
								}
								%>
			                	<td width="34%" align="center">
			                		<span class="search">
			                			<a href="#" onclick="window.close();"
											class="currentLocation"> <span class="lbl">关闭</span>
										</a>
			                		</span>
                				</td>
                			</tr>
                		</table>
                	</td>
                </tr>
            </table>
		</fieldset>
	</body>
</html>