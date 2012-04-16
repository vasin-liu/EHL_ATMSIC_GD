<%@page language="java" contentType="application/msword;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
	String[] weeklist = new String[20];
	String weekDatas = StringHelper.obj2str(request.getParameter("weekDatas"),"");
	String tbsj = StringHelper.obj2str(request.getParameter("tbsj"),"");
	String mon = "";
	String day = "";
	if(tbsj.length()==10){
		mon = tbsj.substring(5,7);
		day = tbsj.substring(8,10);
	}
	String tbdw = StringHelper.obj2str(request.getParameter("tbdw"),"");
	if("".equals(weekDatas)){
		return;
	}else{
		weeklist = weekDatas.split(",");
	}
%>
<html>
	<head>
		<title>春运交通管理工作统计日报表</title>
		<style>
			<!--
			p.MsoNormal {
				mso-style-parent: "";
				margin-bottom: .0001pt;
				text-align: justify;
				text-justify: inter-ideograph;
				font-size: 10.5pt;
				font-family: "Times New Roman";
				margin-left: 0cm;
				margin-right: 0cm;
				margin-top: 0cm
			}
			table.MsoNormalTable {
				mso-style-parent: "";
				font-size: 10.0pt;
				font-family: "Times New Roman";
			}
			-->
		</style>
	</head>
	<body>
		<p class="MsoNormal" align="center" style="text-align: center">
			<span lang="EN-US" style="font-size: 22.0pt">&nbsp;</span>
		</p>
		<p class="MsoNormal" align="center" style="text-align: center">
			<span style="font-size: 22.0pt; font-family: 宋体">春运交通管理工作统计日报表</span>
		</p>
		<p class="MsoNormal" align="center" style="text-align: center; text-indent: 49.5pt">
			<span lang="EN-US" style="font-size: 22.0pt">&nbsp;</span>
		</p>
		<p class="MsoNormal" style="text-align: center">
			<span style="font-size: 14.0pt; font-family: 楷体_GB2312">填报单位：</span>
			<span lang="EN-US" style="font-size: 14.0pt">
				<%=tbdw %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</span>
			<span style="font-size: 14.0pt; font-family: 楷体_GB2312">填报时间：</span>
			<span lang="EN-US" style="font-size: 14.0pt"><%=mon %></span>
			<span style="font-size: 14.0pt; font-family: 楷体_GB2312">月</span>
			<span lang="EN-US" style="font-size: 14.0pt"><%=day %></span>
			<span style="font-size: 14.0pt; font-family: 楷体_GB2312">日</span>
		</p>
		<div align="center">
			<table class="MsoNormalTable" border="1" cellspacing="0" cellpadding="0" width="612"
				style="border: medium none ; border-collapse: collapse; margin-left: -3.6pt;" id="table1" height="586">
				<tr style="height: 61.15pt">
					<td width="120" style="width: 90.0pt; height: 61.15pt; border: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center" style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">投入警力</span>
						</p>
						<p class="MsoNormal" align="center" style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
						<span style="font-size: 12.0pt; font-family: 宋体">（人次）</span>
					</td>
					<td width="120" style="width: 89.85pt; height: 61.15pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: 1.0pt solid windowtext; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center" style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">出动警车</span>
						</p>
						<p class="MsoNormal" align="center" style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">（辆次）</span>
					</td>
					<td width="132" style="width: 99.15pt; height: 61.15pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: 1.0pt solid windowtext; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center" style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
						<span style="font-size: 12.0pt; font-family: 宋体">启动检查服务站（个）</span>
					</td>
					<td width="120" style="width: 90.0pt; height: 61.15pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: 1.0pt solid windowtext; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center" style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">设置临时执勤点</span>
						</p>
						<p class="MsoNormal" align="center" style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
						<span style="font-size: 12.0pt; font-family: 宋体">（个）</span>
					</td>
					<td width="120" style="width: 90.0pt; height: 61.15pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: 1.0pt solid windowtext; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center" style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
						<span style="font-size: 12.0pt; font-family: 宋体">检查客运车辆（辆）</span>
					</td>
				</tr>
				<tr style="height: 46.35pt">
					<td width="120" style="width: 90.0pt; height: 46.35pt; border-left: 1.0pt solid windowtext; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<br>
						<p class="MsoNormal" align="center" style="text-align: center">
							<b></b>
							<span id="text1" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[0]%></span><b><span lang="EN-US" style="font-size: 12pt;"></span> 
							</b>
					</td>
					<td width="120" style="width: 89.85pt; height: 46.35pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center" style="text-align: center">
							<b>
								<span lang="EN-US" style="font-size: 12.0pt">&nbsp;</span>
							</b>
						</p>&nbsp;&nbsp;&nbsp;&nbsp; 
						<span id="text2" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[1]%></span><p class="MsoNormal" align="center" style="text-align: center">
						<span lang="EN-US" style="font-size: 12.0pt">&nbsp;</span>
					</td>
					<td width="132"
						style="width: 99.15pt; height: 46.35pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center" style="text-align: center">
							<b>&nbsp; </b><span id="text3" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[2]%></span>
					</td>
					<td width="120"
						style="width: 90.0pt; height: 46.35pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center" style="text-align: center">
							<span id="text4" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[3]%></span><b><span lang="EN-US" style="font-size: 12pt;">&nbsp; 
							</span> 
							</b>
					</td>
					<td width="120"
						style="width: 90.0pt; height: 46.35pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center" style="text-align: center">
							<span id="text5" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[4]%></span><b><span lang="EN-US" style="font-size: 12pt;">&nbsp; 
							</span> 
							</b>
					</td>
				</tr>
				<tr style="height: 61.55pt">
					<td width="120"
						style="width: 90.0pt; height: 61.55pt; border-left: 1.0pt solid windowtext; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">共查处交通违法行为（起）</span>
					</td>
					<td width="120"
						style="width: 89.85pt; height: 61.55pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">查处超速行驶</span>
						</p>
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">（起）</span>
					</td>
					<td width="132"
						style="width: 99.15pt; height: 61.55pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">查处客车超员</span>
						</p>
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">（起）</span>
					</td>
					<td width="120"
						style="width: 90.0pt; height: 61.55pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">查处疲劳驾驶（起）</span>
					</td>
					<td width="120"
						style="width: 90.0pt; height: 61.55pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">查处酒后驾驶</span>
						</p>
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">（起）</span>
					</td>
				</tr>
				<tr style="height: 45.6pt">
					<td width="120"
						style="width: 90.0pt; height: 45.6pt; border-left: 1.0pt solid windowtext; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt"> 
							&nbsp; <span id="text6" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[5]%></span>
					</td>
					<td width="120"
						style="width: 89.85pt; height: 45.6pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt"> 
							<span id="text7" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[6]%></span><span lang="EN-US" style="font-size: 12pt;"> 
							</span> 
					</td>
					<td width="132"
						style="width: 99.15pt; height: 45.6pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt"> 
							<span id="text8" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[7]%></span><span lang="EN-US" style="font-size: 12pt;"> 
							</span> 
					</td>
					<td width="120"
						style="width: 90.0pt; height: 45.6pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt"> 
							<span id="text9" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[8]%></span><span lang="EN-US" style="font-size: 12pt;"> 
							</span> 
					</td>
					<td width="120"
						style="width: 90.0pt; height: 45.6pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center" style="text-align: center">
							<b>&nbsp; <span lang="EN-US" style="font-size: 12pt;"></span></b>
							<span id="text10" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[9]%></span>
					</td>
				</tr>
				<tr style="height: 56.35pt">
					<td width="120"
						style="width: 90.0pt; height: 56.35pt; border-left: 1.0pt solid windowtext; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">吊销或暂扣机动车驾驶证（本）</span>
					</td>
					<td width="120"
						style="width: 89.85pt; height: 56.35pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">卸客转运（人）</span>
					</td>
					<td width="132"
						style="width: 99.15pt; height: 56.35pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">深入专业运输企业（个）</span>
					</td>
					<td width="120"
						style="width: 90.0pt; height: 56.35pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">清理驾驶人记分（人）</span>
					</td>
					<td width="120"
						style="width: 90.0pt; height: 56.35pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">排查隐患车辆（辆）</span>
					</td>
				</tr>
				<tr style="height: 39.2pt">
					<td width="120"
						style="width: 90.0pt; height: 39.2pt; border-left: 1.0pt solid windowtext; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt"> 
							<span id="text11" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[10]%></span>
							<span lang="EN-US" style="font-size: 12.0pt"></span>
					</td>
					<td width="120"
						style="width: 89.85pt; height: 39.2pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt"> 
						<span id="text12" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[11]%></span>						&nbsp;&nbsp;
						<span lang="EN-US" style="font-size: 12.0pt"></span>
					</td>
					<td width="132"
						style="width: 99.15pt; height: 39.2pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt"> 
							&nbsp; <span id="text14" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[13]%></span>
					</td>
					<td width="120"
						style="width: 90.0pt; height: 39.2pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt"> 
							<span id="text13" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[12]%></span><span lang="EN-US" style="font-size: 12pt;"> 
							</span> 
					</td>
					<td width="120"
						style="width: 90.0pt; height: 39.2pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt"> 
							<span id="text15" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[14]%></span><span lang="EN-US" style="font-size: 12pt;"> 
							</span> 
					</td>
				</tr>
				<tr style="height: 62.35pt">
					<td width="120"
						style="width: 90.0pt; height: 62.35pt; border-left: 1.0pt solid windowtext; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm" align="center">
						<p class="MsoNormal" style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">整治危险路段</span>
						</p>
						<p class="MsoNormal" style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">（处）</span>
					</td>
					<td width="120"
						style="width: 90.0pt; height: 62.35pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm" align="center">
						<p class="MsoNormal" style="margin: 0cm -4.4pt 0.0001pt -4.1pt; text-align: center; text-indent: 2.4pt; line-height: 20pt;"> 
							<span style="font-size: 12pt; font-family: 宋体;"><br></span></p><p class="MsoNormal" style="margin: 0cm -4.4pt 0.0001pt -4.1pt; text-align: center; text-indent: 2.4pt; line-height: 20pt;"><span style="font-size: 12pt; font-family: 宋体;"><br></span></p><p class="MsoNormal" style="margin: 0cm -4.4pt 0.0001pt -4.1pt; text-align: center; text-indent: 2.4pt; line-height: 20pt;"><span style="font-size: 12pt; font-family: 宋体;">通过广播、电视开展宣传（次）</span> 
						</p>
						<p class="MsoNormal" style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span lang="EN-US" style="font-size: 12.0pt">&nbsp;</span>
					</td>
					<td width="132"
						style="width: 99.15pt; height: 62.35pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">发放宣传材料、设置宣传提示牌</span>
						</p>
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">（份、块）</span>
					</td>
					<td width="120"
						style="width: 90.0pt; height: 62.35pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">启动恶劣天气应急预案（次）</span>
					</td>
					<td width="120"
						style="width: 90.0pt; height: 62.35pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">
							<span style="font-size: 12.0pt; font-family: 宋体">应急疏导、分流车辆（辆）</span>
					</td>
				</tr>
				<tr style="height: 47.5pt">
					<td width="120"
						style="width: 90.0pt; height: 47.5pt; border-left: 1.0pt solid windowtext; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center" style="text-align: center">
							<span id="text20" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[19]%></span><b><span lang="EN-US" style="font-size: 15pt;">&nbsp; 
							</span> 
							</b>
					</td>
					<td width="120"
						style="width: 89.85pt; height: 47.5pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center" style="text-align: center">
							<span id="text16" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[15]%></span><b><span lang="EN-US" style="font-size: 15pt;">&nbsp; 
							</span> 
							</b>
					</td>
					<td width="132"
						style="width: 99.15pt; height: 47.5pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt"> 
							<span id="text17" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[16]%></span><span lang="EN-US" style="font-size: 12pt;"> 
							</span> 
					</td>
					<td width="120"
						style="width: 90.0pt; height: 47.5pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt">&nbsp; 
							<span id="text18" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[17]%></span><span lang="EN-US" style="font-size: 12.0pt"></span>
					</td>
					<td width="120"
						style="width: 90.0pt; height: 47.5pt; border-left: medium none; border-right: 1.0pt solid windowtext; border-top: medium none; border-bottom: 1.0pt solid windowtext; padding-left: 5.4pt; padding-right: 5.4pt; padding-top: 0cm; padding-bottom: 0cm">
						<p class="MsoNormal" align="center"
							style="text-align: center; text-indent: 2.4pt; line-height: 20.0pt; margin-left: -4.1pt; margin-right: -4.4pt; margin-top: 0cm; margin-bottom: .0001pt"> 
							<span id="text19" style="font-size: 12pt; font-family: 宋体;"><%=weeklist[18]%></span><span lang="EN-US" style="font-size: 12pt;"> 
							</span> 
					</td>
				</tr>
			</table>
		</div>
		<p class="MsoNormal" style="text-align: center">
			<span style="font-size: 14.0pt; font-family: 楷体_GB2312">填报人：</span><span
				lang="EN-US" style="font-size: 14.0pt">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</span><span style="font-size: 14.0pt; font-family: 楷体_GB2312">联系电话：</span><span
				lang="EN-US" style="font-size: 14.0pt">&nbsp;&nbsp;&nbsp;&nbsp;
			</span>
		</p>
		<p class="MsoNormal" style="text-align: center">
			<span lang="EN-US">&nbsp;</span>
		</p>
	</body>
</html>
