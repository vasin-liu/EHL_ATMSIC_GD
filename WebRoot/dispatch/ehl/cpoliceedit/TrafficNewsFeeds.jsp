<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@page import="com.ehl.dispatch.common.DispatchUtil"%>
<%@include file="../../Message.oni"%>
<%
	String[] strObj = DepartmentManage.getDeptInfo(request, "1").split(
	",");//获取单位信息串
	String jgid = strObj[0];//单位编码
	String jgmc = strObj[1];//机构名称
	String ccbm = strObj[2];//机构层次编码
	//String jgid="441905000000";//12位机构编码
	String jgbh;//总队(2位),支队(4位),大队(6位)
	if ("0000".equals(jgid.substring(2, 6))) {
	jgbh = jgid.substring(0, 2);
	} else if ("00".equals(jgid.substring(4, 6))) {
	jgbh = jgid.substring(0, 4);
	} else {
	jgbh = jgid.substring(0, 6);
	}
	
	String insrtOrUpdt = StringHelper.obj2str(request.getParameter("insrtOrUpdt"), "");
	String bh = StringHelper.obj2str(request.getParameter("bh"), "");
	String jglx = StringHelper.obj2str(request.getParameter("jglx"), "");
	String readStatic = (!"0".equals(insrtOrUpdt))&& (!"1".equals(insrtOrUpdt)) ? "readonly" : "";
	String disabled = (!"0".equals(insrtOrUpdt))&& (!"1".equals(insrtOrUpdt)) ? "disabled" : "";
	String daytime = "";
	String daytime0 = "";
	String personName = StringHelper.obj2str(DispatchUtil.getDutyPersonNameByDepId(jgid),"");
%>
    
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>交通报料信息</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
		<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css">
		<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
		<link rel="stylesheet" type="text/css" href="../../../base/css/style1/tab.css" />
		<link rel="stylesheet" type="text/css" href="../../../base/css/style1/font.css" />
		<link rel="stylesheet" type="text/css" href="../../../base/css/style1/link.css" />
		<link rel="stylesheet" type="text/css" href="../../../base/css/style1/form.css" />
		<link rel="stylesheet" type="text/css" href="../../../base/css/style1/Popup.css" />
		
		
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../js/ccommon/RoadDept.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/TrafficNewsFeeds.js"></script>
		<script type="text/javascript" src="../../js/ccommon/calendar/DateAndSchar.js"></script>
		<style type="text/css">
			.tdtitle{
				border-top: 0 solid #000000;
				border-right: 1 solid #a5d3ef;
				border-bottom: 1 solid #a5d3ef;
				border-left: 1 solid #a5d3ef;
				line-height: 16px;
				color: #000000;
				border-collapse : separate;
				empty-cells:show;
				text-align: right;
				width:15%;
			}
			
			.tdvalue{
				border-top: 0 solid #000000;
				border-right: 1 solid #a5d3ef;
				border-bottom: 1 solid #a5d3ef;
				border-left: 1 solid #a5d3ef;
				line-height: 16px;
				color: #000000;
				border-collapse : separate;
				empty-cells:show;
				text-align: left;
				width:20%;
			}
			/*宽高行高背景不显示超过对象尺寸的内容*/
			.lsearch{
			     width:82px; 
				 height:22px; 
				 line-height:22px; 
				 background:url(../../images/dispatch/btn.png) no-repeat; 
				 overflow:hidden;
			}
			/*块对象高度固定背景字体无装饰行高不显示超过对象尺寸的内容*/
			.lsearch a{ 
			     display:block; 
				 height:22px; 
				 background:url(../../images/dispatch/btn.png) center; 
				 text-decoration:none; 
				 line-height:22px;
				 overflow:hidden;
			}
			/*高度固定背景行高*/
			.lsearch a:hover{ 
			     height:22px; 
				 background:url(../../images/dispatch/btn.png) center center; 
				 line-height:22px;
			}
			/*块对象宽高固定外补丁内补丁文字的位置颜色鼠标光标类型*/
			.lsearch .lbl{ 
			     display:block;
				 width:79px; 
				 height:15px; 
				 padding-left:3px; 
				 padding-top:0px; 
				 margin:0 auto; 
				 text-align:center; 
				 color:#ffffff; 
				 cursor:pointer;
			}
			/*颜色滤镜效果*/
			.lsearch a:hover .lbl{ 
			     color:#dae76b; 
				 filter:glow(color=#ffffff,strength=1);
			}
			 
			.table3{
				border-top: 1 solid #7B9EBD;
				border-right: 1 solid #7B9EBD;
				border-bottom: 1 solid #7B9EBD;
				border-left: 1 solid #7B9EBD;
				border-collapse:collapse;
				font-size:11px;				
			}
			/*文本框变下划线*/
			.text {
			    font: 14px Tahoma, Verdana;
				border: 0;
				border-bottom: 1 solid black;
				background: ;
				text-align:center;
				/*padding-left:10px;*/
				width:62px;
			}
			.textwidth{
				width: 160;
			}
			#calendarTableDT tr{
				height:12px;
				line-height:100%;
				margin :0px;
			}
			#calendarTableDT td{
				height:12px;
				line-height:100%;
				padding : 0px;
				margin :0px;
			}
			#calendarTableDT th{
				height:12px;
				line-height:100%;
				padding: 5 0 2;
				margin :0px;
			}
		</style>
	</head>
	<body onload="doQueryInfo('<%=bh%>');setReadOnly('<%=insrtOrUpdt %>');" style="overflow:hidden">
		<div style="text-align: center;width:100%;height:100%;">
			<fieldset style="width:100%;border:1px solid #a5d3ef" align="center">
			    <table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr class="wTableTopCenter">
						<td width="5%" >
							<div align="center">
								<img src="../../../base/image/cssimg/table/tb.gif" width="16" height="16" alt="img" />
							</div>
						</td>
						<td width="70%" class="currentLocation">
							<span class="currentLocationBold">您当前的位置</span>：交通报料信息
							<span id="zdsm" style="display:none;">【总队下发时间：<font style="font-size: 10pt;" id="zdxfsj"></font>】</span>
						</td>
					</tr>
				</table>
				<table class="table3" width="100%" border="1" id="block" borderColor='#a5d1ec'>
					<input type="hidden" id="jgbh" value='<%=jgbh%>'>
					<input type="hidden" id="bh" value='<%=bh%>'>
					<input type="hidden" id="jglx" value='<%=jglx%>'>
					<input type="hidden" id="page" value = '<%=insrtOrUpdt %>' >
					<input type="hidden" id="clzt">
					<input type="hidden" id="sfcswhs">
				<tbody>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF" >
							<label id="roadLevelDesc">拥堵道路等级：</label>
						</td>
						<td class="tdvalue" colspan="3"  >
							<select id="roadLevel" style="width: 160px;"  onchange="Road.roadLevel.onchange(this)"></select>&nbsp;&nbsp;<font size='1' color='red'>※</font>
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF" >
							拥堵道路名称：
						</td>
						<td class="tdvalue" id="alarmRoad_TrafficCrowd_td" ></td>
						<td class="tdtitle" bgcolor="#F0FFFF" >
							拥堵路段：
						</td>
						<td class="tdvalue" >
							<input type="text" id="ldmc" class="textwidth" 
								style="border: 1px #7B9EBD solid" name="editinput">&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵起始里程：
						</td>
						<td class="tdvalue">
							K
							<input type="text" id="qslc" class="text" name="editinput" maxlength="6">
							+
							<input type="text" id="qslcm" class="text" name="editinput" maxlength="3" >
							米&nbsp;<font size="1" color="red">※</font>
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵终止里程：
						</td>
						<td class="tdvalue">
							K
							<input type="text" id="zzlc" class="text" name="editinput"
								maxlength="6">
							+
							<input type="text" id="zzlcm" class="text" name="editinput"
								maxlength="3" >
							米&nbsp;<font size="1" color="red">※</font>
							
						</td>
					</tr>
					<tr >
						<td class="tdtitle" bgcolor="#F0FFFF">
							拥堵方向：
							<input id="ldfx" type="hidden" value="0">
						</td>
						<td class="tdvalue" id="showFX" colspan="3">
							<input type="radio" value="0" name="RADIOTYPE" id="RADIOTYPE_1" checked onclick="$('ldfx').value = this.value"><span id="rdForward">下行</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="1" name="RADIOTYPE" id="RADIOTYPE_2" onclick="$('ldfx').value = this.value"><span id="rdBack">上行</span>&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" value="双向拥堵" name="RADIOTYPE" id="RADIOTYPE_3" onclick="$('ldfx').value = this.value"><span id="double">双向拥堵</span>
						</td>
					</tr>
					
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							管辖机构：
						</td>
						<td class="tdvalue" id="daduiTdId">
						<input id="gxdd" type="text"  style="border: 1px #7B9EBD solid;width: 140px;"  readonly ondblclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','gxdd','170','170')" />
						<input id="gxddCode" type="hidden" />
						<img id="searchDept" src="../../images/button/search.bmp" alt="查找管辖大队" style="cursor:hand;width:16px;height:16px;" onclick="RoadDept.show('','','','dlmc','roadLevel','qslc','zzlc','ssld','gxddCode','gxdd',0)"/>
						<img id="deptList" src="../../images/popup/btnselect.gif" alt="全部机构列表" style="cursor:hand;" onclick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','gxdd','202','170')">
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							起始时间：
						</td>
						<td class="tdvalue">
							<input type="text" id="qssj" class="textwidth"
								style="border: 1px #7B9EBD solid" name="editinput"
								value="" readonly onclick="SelectDateTime(this)">
						</td>
					</tr>
					<tr style="display:none;">
						<td class="tdtitle" bgcolor="#F0FFFF">终止时间：</td>
						<td class="tdvalue">
							<input type="text" id="zzsj" class="textwidth" style="border: 1px #7B9EBD solid" name="editinput" 
							value=""  disabled="disabled"  onclick="SelectDateTime(this)">
						</td>
					</tr>
					
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							路况：
						</td>
						<td class="tdvalue" >
							<input type="text" id="lk" class="textwidth" 
								style="border: 1px #7B9EBD solid" name="editinput" value="拥堵" >&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">拥堵原因简述：</td>
						<td class="tdvalue" colspan="1">
							<select id="lkyy" class="textwidth" >
								<option value="其他" selected>其他
								<option value="事故">事故
								<option value="车流量大">车流量大
								<option value="修路">修路
								<option value="故障车">故障车
								<option value="天气">天气
								<option value="群众堵路">群众堵路
							</select>
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							报料人：
						</td>
						<td class="tdvalue">
							<input type="text" id="blr" name="editinput" class="textwidth"
								style="border: 1px #7B9EBD solid" >
						</td>
						<td class="tdtitle" bgcolor="#F0FFFF">
							报料人联系方式：
						</td>
						<td class="tdvalue">
							<input type="text" id="lxfs" class="textwidth" name="editinput"
								style="border: 1px #7B9EBD solid" value="">
						</td>
					</tr>
					<tr>
						<td class="tdtitle" bgcolor="#F0FFFF">
							填报人：
						</td>
						<td class="tdvalue">
							<input type="text" id="lrr" class="textwidth" name="editinput"
								style="border: 1px #7B9EBD solid" value="<%=personName%>">&nbsp;&nbsp;<font size="1" color="red">※</font>
						</td>
						<input type="hidden" id="lrbm"  value="<%=jgmc%>">
						<td class="tdtitle" bgcolor="#F0FFFF">
							报料时间：
						</td>
						<td class="tdvalue">
							<input type="text" id="lrsj" name="editinput" class="textwidth"
								style="border: 1px #7B9EBD solid" readonly="readonly">
						</td>
					</tr>
					<tr height="35" >
                        <td   class="tdtitle" align="center" bgcolor="#F0FFFF" >
                       	      备注情况：
                       	</td>
                        <td class="tdvalue" colspan="3" >
                             <textarea rows="5" id="bz" name="remindInfoValue" style="border: 1px #7B9EBD solid;width:477" ></textarea>&nbsp;&nbsp;<font size="1" color="red">※</font>
                        </td>
	                </tr>
	                
					</tbody>
					<tbody id="ddhc" style="display:none;">
						<tr>
							<td class="tdtitle" bgcolor="#F0FFFF">核实人：</td>
							<td class="tdvalue">
								<input type="text" id="hsr" class="textwidth" name="editinput" style="border: 1px #7B9EBD solid" value="" >&nbsp;&nbsp;<font size="1" color="red">※</font>
							</td>
							<td class="tdtitle" bgcolor="#F0FFFF">核实时间：</td>
							<td class="tdvalue">
								<input type="text" id="hssj" class="textwidth" 
								name="editinput" style="border: 1px #7B9EBD solid" readonly onclick="SelectDateTime(this)" 
							</td>
						</tr>
						<tr>
							<td class="tdtitle" bgcolor="#F0FFFF">拥堵是否结束：</td>
							<td class="tdvalue" colspan="3">
								<select id="sfjs" style="width:160;border: 1px #7B9EBD solid" >
									<option value="0" selected>未结束
									<option value="1">已结束
									<option value="-1">未发现拥堵
								</select>
							</td>
						</tr>
						<tr>
							<td class="tdtitle" bgcolor="#F0FFFF">情况备注：</td>
							<td colspan="3" >
								<textarea  rows="3" style="border: 1px #7B9EBD solid;width:477" id="hsqk" name="editinput"></textarea>&nbsp;&nbsp;<font size="1" color="red">※</font>
							</td>
						</tr>
					</tbody>
					<tr align="center">
						<td colspan="4">
							<span align="center" height="40px" style="color:red;font-size:14px;">
							辖区大队核实上述拥堵信息（根据当前最新情况核改和补充），完成拥堵信息报送和发布。
							</span>
						</td>
					</tr>
					<tbody id="zdhc" style="display:none;">
						<tr align="center" style="height: 60px;vertical-align: bottom;">
							<td colspan="4" >
								<span class="currentLocationBold" align="center" height="40px"><font size="5">总队审核</font></span>
							</td>
						</tr>
						<tr>
							<td class="tdtitle" bgcolor="#F0FFFF">审核人：</td>
							<td class="tdvalue">
								<input type="text" id="scr" class="textwidth" name="editinput" style="border: 1px #7B9EBD solid" value="<%=personName%>">&nbsp;&nbsp;<font size="1" color="red">※</font>
							</td>
							<td class="tdtitle" bgcolor="#F0FFFF">审核时间：</td>
							<td class="tdvalue">
								<input type="text" id="scsj" class="textwidth" name="editinput" style="border: 1px #7B9EBD solid;" readonly onclick="SelectDateTime(this)" 
							</td>
						</tr>
						<tr>
							<td class="tdtitle" bgcolor="#F0FFFF">审核结果：</td>
							<td class="tdvalue" colspan="3">
								<input type="radio" id='cy' name="radios" value="0" checked="checked" />采用和发布
	  							<input type="radio" id='wcy' name="radios" value="-0.2">不采用（不发布）
							</td>
						</tr>
						<tr>
							<td class="tdtitle" bgcolor="#F0FFFF">备注：</td>
							<td colspan="3">
								<textarea  rows="3" style="border: 1px #7B9EBD solid;width:477" id="scyj" name="editinput"></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</fieldset>
			<div style="text-align: center;width: 2%;height: 2%;"></div>
				<div style="text-align:center;height:25px;border-bottom:0 solid #a5d3ef;" id="buttonVegion">
					<span id="print" class="lsearch" style="margin-right:50px;display:none"> 
						<a href="#" onclick="printWord('<%=daytime%>');"><span class="lbl" >打印</span></a> 
					</span>
					<span id="zdqdxf" class="lsearch" style="margin-right:50px;display:none;"> 
						<a href="#" onclick="if(window.confirm('是否确认下发'+$('gxdd').value+'?')){feedbackNews('<%=bh%>','1')}"><span class="lbl">总队下发</span></a>
					</span>
					<span id="ignore" class="lsearch" style="margin-right:50px;display:none;"> 
						<a href="#" onclick="cancelNews('<%=bh%>','4');"><span class="lbl">忽略</span></a> 
					</span>
					<span id="ddqdhs" class="lsearch" style="margin-right:50px;display:none;"> 
						<a href="#" onclick="feedbackNews('<%=bh%>','2');"><span class="lbl">确定</span></a> 
					</span>
					<span id="zdclwb" class="lsearch" style="margin-right:50px;display:none;"> 
						<a href="#" onclick="feedbackNews('<%=bh%>','3');"><span class="lbl">处理完毕</span></a> 
					</span>
					<span id="close" class="lsearch" style="margin-right:50px;"> 
						<a href="#" onclick="window.close();"><span class="lbl">关闭</span></a> 
					</span>
				</div>
		</div>
	</body>
</html>