<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt") == null ? "" : request.getParameter("insrtOrUpdt");
	String bh = request.getParameter("bh")== null ? "" : request.getParameter("bh");
%>
<html>
	<head>
		<title>道路管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../../../base/css/style1/tab.css" rel="stylesheet"  type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet"	type="text/css" />
		<SCRIPT type="text/javascript" src="../../js/road/road.js"></SCRIPT>
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
	    <script type="text/javascript">
	        var username = '<%=pname%>';
	      
        </script>
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
				width:200px;
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
	<body onLoad="doQuery(<%=bh %>);">
		<fieldset style="width:400px;height:220px;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:0px;font-size:12px;font-weight:600">
				<%="".equals(bh)?"道路机构记录新增":"道路机构记录编辑"%>
			</legend>
            <table width="380" align="center" class="tableInput" id="dataTable">
                <tr style="display:none">
                    <td>道路记录</td>
                    <td colspan="2">
                        <input id="BH" type="text" maxLength="32" name="lkid" class="text"
                            value=<%=bh%> style="width:280" readonly="true"></input>
                    </td>
                </tr>
                <tr>
                    <th width="10%" class="td_r_b">序号</th>
                    <th width="30%" class="td_r_b">项目</th>
                    <th width="60%" class="td_r_b">内容</th>
                </tr>
            
                 <tr>
                    <td class="td_r_b">1</td>
                    <td class="td_r_b1">支队</td>
                   <td id="ZD1_DIV" align="left">
						<script language="javascript">
	                    	fillListBox("ZD1_DIV","ZD1","170","select jgid,jgmc from t_sys_department where substr(jgid,3,2)<>'00' and substr(jgid,5,2)='00'","全部");
                        </script>
					</td>
                </tr>
                <tr>
                    <td class="td_r_b">2</td>
                    <td class="td_r_b1">道路名称</td>
                    <td align="left" id="DLMC_TB_DIV">
                        <script language="javascript">
	                    	fillListBox("DLMC_TB_DIV","DLMC","170","select max(dlbh),max(dlmc) from T_OA_DICT_ROAD group by dlmc having count(dlbh)>=1","全部");
                        </script>
                    </td>
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
