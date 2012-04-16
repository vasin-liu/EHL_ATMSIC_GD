<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ehl.drpt.dailyRpt.action.DailyRptModifyAction"%>
<%@include file="../../../Message.oni"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@page import="com.ehl.sm.common.Constants"%>
<%
	String insrtOrUpdt = (String)request.getParameter("insrtOrUpdt");
	DailyRptModifyAction dra = new DailyRptModifyAction();
	
	String pname1 = StringHelper.obj2str(session.getAttribute(Constants.PNAME_KEY),"");
	Object[] dpt = dra.getRptDpt(pname1);
	
	dpt = dpt == null ? new Object[]{"000000","测试"}:dpt; 
	String tbdwid = dpt[0].toString();
	String tbdw = dpt[1].toString();
	int tjn = dra.getRptDate()[0];
	int tjy = dra.getRptDate()[1];
	int tjr = dra.getRptDate()[2];
	String tjrq = tjn + "-" + tjy + "-" +tjr;
	System.out.println("insrtOrUpdt=" + insrtOrUpdt);
	System.out.println(tjrq);
	String bh = StringHelper.obj2str(request.getParameter("bh"),"");
%>
<html>
	<head>
		<script type="text/javascript">
		<% 
		if("0".equals(insrtOrUpdt) && !dra.chkTimeWithDept(tbdwid,tjrq)){
		%>
			alert("今天日报已经存在！");
			window.close();
		<% 	
		}
		if("1".equals(insrtOrUpdt) && !dra.chkTimeWithTjrq(tbdwid,bh,tjrq)){%>
			if(!confirm("当前时刻已经不可编辑此记录！")){
				window.close();
			}
		<% 
			insrtOrUpdt = "2";
		}
		
		String istr = "2".equals(insrtOrUpdt)?"readOnly ":"";
		String istyle = "2".equals(insrtOrUpdt)?"style='display:none;' ":"";
		%>	
		</script>
		<title>日报表管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../../../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/dhtmlx/xmlCreator.js"></script>
		<script type="text/javascript" src="../../../base/js/page/PageCtrl.js"></script>
		<script type="text/javascript" src="../../../base/js/style/scroll.js"></script>
		<script type="text/javascript" src="../../js/common/checkedSpecialChar.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../js/drpt/dailyReport.js"></script>
	    <style type="text/css">
			<!--
			*{
				margin: 0 auto;
				padding: 0;
			}
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
				width:60px;
				text-align:center;
				ime-mode:disabled;
			}
			.input2{
				border:0;
				width:40px;
				border-bottom:1px solid #b5d6e6; 
			}
			.input3{
				border:0;
				width:200px;
			}
			.input4{
				border:0;
				width:20px;
				text-align:right;
				ime-mode:disabled;
			}
			.input5{
				border:0;
				width:60px;
				text-align:center;
				ime-mode:disabled;
				border-bottom:1px solid #b5d6e6;
			}
			.input6{
				border:0;
				width:100px;
				text-align:center;
				border-bottom:1px solid #b5d6e6; 
			}
			.input7{
				border:0;
				width:120px;
			}
			input{
				line-height:16px;
				vertical-align:middle;
			}
			tr{
				line-height:18px;
			}
			-->
        </style>
	</head>
	<body onLoad="doQuery('<%=insrtOrUpdt %>','<%= bh %>');">
		<fieldset style="width:100%;height:100%;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:0px;font-size:12px;font-weight:600">
				<%="".equals(bh)?"日报表新增":"日报表编辑"%>
			</legend>
            <div style="color:red;font-size:12px;padding-left:40px;">◆ 表中标记 ※ 部分为公安部要求的周报表项.</div>
            <table width="99%" align="center" class="tableInput" id="dataTable">
                <tr style="display:none">
                    <td>日报表管理</td>
                    <td colspan="7">
                        <input id="bh" type="text" maxLength="32" name="lkid" class="text"
                            value="<%=bh%>" style="width:280"></input>
                        <input id="insrtOrUpdt" value="<%=insrtOrUpdt %>" type="hidden"  />
                    </td>
                </tr>
                <tr>
                	<td class="td_b" colspan="8">
                		<div style="float:left;">
                			填报单位：<input readOnly id="tbdw" value="<%=tbdw %>" class="input3" type="text" />
                			<input id="tbdwid" value="<%=tbdwid %>" type="hidden" />
                		</div>
                		<div style="float:right;">
                			统计日期：<input maxLength="2" readOnly id="tjy" value="<%=tjy<10?("0"+Integer.toString(tjy)):Integer.toString(tjy) %>" class="input4" type="text" />月
                					<input maxLength="2" readOnly id="tjr" value="<%=tjr<10?("0"+Integer.toString(tjr)):Integer.toString(tjr) %>" class="input4" type="text" />日
                		</div>
                	</td>
                </tr>
                <tr>
                    <th width="6%" class="td_r_b">序号</th>
                    <th width="30%" class="td_r_b">项目</th>
                    <th width="10%" class="td_r_b">数量</th>
                    <th width="8%" colspan="2" class="td_r_b">序号</th>
                    <th width="36%" colspan="2" class="td_r_b">项目</th>
                    <th width="10%" class="td_b">数量</th>
                </tr>
                <tr>
                    <td class="td_r_b">1</td>
                    <td class="td_r_b1">投入警力（人次）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" <%=istr %> id="TRJL" class="input1" type="text" onkeydown="changeBlur(event,'XZZYCK')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td colspan="2" class="td_r_b">25</td>
                    <td colspan="2" class="td_r_b1">卸客转运乘客（人次）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input maxLength="8" <%=istr %> id="XZZYCK" class="input1" type="text" onkeydown="changeBlur(event,'CDJC')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">2</td>
                    <td class="td_r_b1">出动警车（辆次）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="CDJC" class="input1" type="text" onkeydown="changeBlur(event,'YDTB')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td colspan="2" class="td_r_b">26</td>
                    <td colspan="2" class="td_r_b1">通报异地超速50%以上、客车超员20%以上交通违法行为（起）</td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="YDTB" class="input1" type="text" onkeydown="changeBlur(event,'CSSB')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">3</td>
                    <td class="td_r_b1">投入测速设备（台）</td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="CSSB" class="input1" type="text" onkeydown="changeBlur(event,'PCAQYHC')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td colspan="2" class="td_r_b">27</td>
                    <td colspan="2" class="td_r_b1">排除安全隐患客车（辆）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="PCAQYHC" class="input1" type="text" onkeydown="changeBlur(event,'GDCSD')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">4</td>
                    <td class="td_r_b1">设置固定测速点（个）</td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="GDCSD" class="input1" type="text" onkeydown="changeBlur(event,'JYTZKCJSR')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td colspan="2" class="td_r_b">28</td>
                    <td colspan="2" class="td_r_b1">建议调整客车驾驶人（人次）</td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="JYTZKCJSR" class="input1" type="text" onkeydown="changeBlur(event,'LDCSD')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">5</td>
                    <td class="td_r_b1">设置流动测速点（个）</td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="LDCSD" class="input1" type="text" onkeydown="changeBlur(event,'QZPLJSRXX')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td colspan="2" class="td_r_b">29</td>
                    <td colspan="2" class="td_r_b1">强制疲劳驾驶人休息（人次）</td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="QZPLJSRXX" class="input1" type="text" onkeydown="changeBlur(event,'ZQFWD')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">6</td>
                    <td class="td_r_b1">设置春运执勤服务点（个）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="ZQFWD" class="input1" type="text" onkeydown="changeBlur(event,'ZHCFLB')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td colspan="2" class="td_r_b">30</td>
                    <td colspan="2" class="td_r_b1">抓获车匪路霸（人）</td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="ZHCFLB" class="input1" type="text" onkeydown="changeBlur(event,'JTWFHJ')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">7</td>
                    <td class="td_r_b1">查处交通违法行为合计（起）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="JTWFHJ" class="input1" type="text" onkeydown="changeBlur(event,'JXXCHD')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td rowspan="10" class="td_r_b">31</td>
                    <td rowspan="10" width="3%" class="td_r_b1" align="center">交通安全宣传情况</td>
                    <td colspan="2" class="td_r_b1">举行宣传活动（场次）</td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="JXXCHD" class="input1" type="text" onkeydown="changeBlur(event,'CSXS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">8</td>
                    <td class="td_r_b1">查处超速行驶（起）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="CSXS" class="input1" type="text" onkeydown="changeBlur(event,'BFXCGP')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td colspan="2" class="td_r_b1">播放宣传光盘（场次）</td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="BFXCGP" class="input1" type="text" onkeydown="changeBlur(event,'KCJY')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">9</td>
                    <td class="td_r_b1">查处客车超员（起）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="KCJY" class="input1" type="text" onkeydown="changeBlur(event,'KDXCL')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td colspan="2" class="td_r_b1">设固定宣传栏（处）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="KDXCL" class="input1" type="text" onkeydown="changeBlur(event,'PLJS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">10</td>
                    <td class="td_r_b1">查处疲劳驾驶（起）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="PLJS" class="input1" type="text" onkeydown="changeBlur(event,'XCH')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td colspan="2" class="td_r_b1">张贴宣传画（张）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="XCH" class="input1" type="text" onkeydown="changeBlur(event,'JHJS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">11</td>
                    <td class="td_r_b1">查处酒后驾驶（起）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="JHJS" class="input1" type="text" onkeydown="changeBlur(event,'XCZL')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td colspan="2" class="td_r_b1">发放宣传资料（份）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="XCZL" class="input1" type="text" onkeydown="changeBlur(event,'WZJS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">12</td>
                    <td class="td_r_b1">查处无证驾驶（起）</td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="WZJS" class="input1" type="text" onkeydown="changeBlur(event,'SJY')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td colspan="2" class="td_r_b1">受教育（人次）</td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="SJY" class="input1" type="text" onkeydown="changeBlur(event,'NYCWFZK')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">13</td>
                    <td class="td_r_b1">查处农用车违法载客（起）</td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="NYCWFZK" class="input1" type="text" onkeydown="changeBlur(event,'DSXC')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b1" width="3%" rowspan="4" align="center">媒体宣传</td>
                    <td class="td_r_b1">电视宣传（条）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="DSXC" class="input1" type="text" onkeydown="changeBlur(event,'DXJDCJSZ')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>	
                <tr>
                    <td class="td_r_b">14</td>
                    <td class="td_r_b1">吊销机动车驾驶证（本）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="DXJDCJSZ" class="input1" type="text" onkeydown="changeBlur(event,'DTXC')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b1">电台宣传（条）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="DTXC" class="input1" type="text" onkeydown="changeBlur(event,'ZKJTWFCL')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                    <td class="td_r_b">15</td>
                    <td class="td_r_b1">暂扣交通违法车辆（辆次）</td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="ZKJTWFCL" class="input1" type="text" onkeydown="changeBlur(event,'BZXC')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b1">报纸宣传（条）</td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="BZXC" class="input1" type="text" onkeydown="changeBlur(event,'JLJTWFJSR')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>	
                <tr>
                    <td class="td_r_b">16</td>
                    <td class="td_r_b1">拘留交通违法驾驶人（人次）</td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="JLJTWFJSR" class="input1" type="text" onkeydown="changeBlur(event,'WLXC')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                    <td class="td_r_b1">网络宣传（条）</td>
                    <td class="td_b"><input  maxLength="8" <%=istr %> id="WLXC" class="input1" type="text" onkeydown="changeBlur(event,'JCKYCL')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
               		<td class="td_r_b">17</td>
                    <td class="td_r_b1">检查客运车辆（辆）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="JCKYCL" class="input1" type="text" onkeydown="changeBlur(event,'ZHS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                	<td colspan="2" class="td_r_b">32</td>
                	<td colspan="2" class="td_r_b1">为群众做好事（件）</td>
                	<td class="td_b"><input  maxLength="8" <%=istr %> id="ZHS" class="input1" type="text" onkeydown="changeBlur(event,'TBKYBM')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
               		<td class="td_r_b">18</td>
                    <td class="td_r_b1">清理驾驶人记分（人）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="TBKYBM" class="input1" type="text" onkeydown="changeBlur(event,'SWSG')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                	<td rowspan="7" class="td_r_b">33</td>
                	<td colspan="2" rowspan="3" class="td_r_b1" align="center">交通事故情况</td>
                	<td class="td_r_b1">交通事故宗数（起）</td>
                	<td class="td_b"><input  maxLength="8" <%=istr %> id="SWSG" class="input1" type="text" onkeydown="changeBlur(event,'SRYSQY')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
               		<td class="td_r_b">19</td>
                    <td class="td_r_b1">深入专业运输企业（个）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="SRYSQY" class="input1" type="text" onkeydown="changeBlur(event,'SWRS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                	<td class="td_r_b1">死亡人数（人次）</td>
                	<td class="td_b"><input  maxLength="8" <%=istr %> id="SWRS" class="input1" type="text" onkeydown="changeBlur(event,'JYYSJSR')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
               		<td class="td_r_b">20</td>
                    <td class="td_r_b1">教育运输企业驾驶人（人次）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="JYYSJSR" class="input1" type="text" onkeydown="changeBlur(event,'SSRS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                	<td class="td_r_b1">受伤人数（人次）</td>
                	<td class="td_b"><input  maxLength="8" <%=istr %> id="SSRS" class="input1" type="text" onkeydown="changeBlur(event,'ELTQYA')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
               		<td class="td_r_b">21</td>
                    <td class="td_r_b1">启动恶劣天气应急预案（次）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="ELTQYA" class="input1" type="text" onkeydown="changeBlur(event,'TDSG')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                	<td colspan="2" rowspan="4" class="td_r_b1" align="center">其中</td>
                	<td class="td_r_b1">特大事故宗数（起）</td>
                	<td class="td_b"><input  maxLength="8" <%=istr %> id="TDSG" class="input1" type="text" onkeydown="changeBlur(event,'YJSDFL')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
               		<td class="td_r_b">22</td>
                    <td class="td_r_b1">应急疏导、分流车辆（辆）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="YJSDFL" class="input1" type="text" onkeydown="changeBlur(event,'TDSWRS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                	<td class="td_r_b1">死亡人数（人次）</td>
                	<td class="td_b"><input  maxLength="8" <%=istr %> id="TDSWRS" class="input1" type="text" onkeydown="changeBlur(event,'ZZWXLD')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
               		<td class="td_r_b">23</td>
                    <td class="td_r_b1">整治危险路段（处）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input  maxLength="8" <%=istr %> id="ZZWXLD" class="input1" type="text" onkeydown="changeBlur(event,'TDSSRS')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                	<td rowspan="2" class="td_r_b1">受伤人数（人次）</td>
                	<td rowspan="2" class="td_b"><input  maxLength="8" <%=istr %> id="TDSSRS" class="input1" type="text" onkeydown="changeBlur(event,'LSZQD')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                	<td class="td_r_b">24</td>
                	<td class="td_r_b1">设置临时执勤点（个）<span style="color:red;font-size:11px;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" <%=istr %> id="LSZQD" class="input1" type="text" onkeydown="changeBlur(event,'TRJL')" onkeypress="checkNum(this,event)" onfocus="this.select();" /></td>
                </tr>
                <tr>
                	<td class="td_r_b">34</td>
                	<td colspan="7" class="td_b" align="center">
                		<div style="width:100%;text-align:center"> 辖区日车流量最大的路段：</div>
                		<div id="ldxx">
                		</div>
                	</td>
                </tr>
                <tr>
                	<td colspan="8" class="td_b">
                		<div>
                			<span style="width:30%">
                				填表人：<input id="TBR" class="input7" type="text"/>
                			</span>
                			<span style="width:30%">
                				审核人：<input id="SHR" class="input7" type="text"/>
                			</span>
                			<span style="width:35%">
                				联系电话：<input id="LXDH" class="input7" type="text" />
                			</span>
                		</div>
                	</td>
                </tr>
                <tr>
                	<td colspan="8" align="center">
                		<table <%="2".equals(insrtOrUpdt)?"width=20%":"width=60%" %>>
                			<tr>
                				<td width="35%" <%=istyle %>>
			                		<span class="search">
			                			<a href="#" onclick="doModify()"
											class="currentLocation"> <span class="lbl">保存</span>
										</a>
			                		</span>
			                	</td>
			                	<td width="35%" <%=istyle %>>
			                		<span class="search">
			                			<a href="#" onclick="doQuery('<%=insrtOrUpdt %>','<%= bh %>');"
											class="currentLocation"> <span class="lbl">重置</span>
										</a>
			                		</span>
			                	</td>
			                	<td <%="2".equals(insrtOrUpdt)?"cospan=3":"width=30%" %>>
			                		<span class="search">
			                			<a href="#" onclick="window.close()"
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
<% if("2".equals(insrtOrUpdt)){ %>
<script language="javascript">
//暂停自动保存 	setInterval(savePm,20000)
</script>
<%} %>
</html>
