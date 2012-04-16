<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@ page import="com.ehl.dispatch.cdispatch.action.MaterialInfoAction"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%@include file="../../Message.oni"%>
<%
	String title = FlowUtil.getFuncText("570601");
	String insrtOrUpdt = StringHelper.obj2str(request
			.getParameter("insrtOrUpdt"), "");
	String alarmId = StringHelper.obj2str(request
			.getParameter("alarmId"), "");
	String readContol = StringHelper.obj2str(request
			.getParameter("readContol"), "");

	String depttype = StringHelper.obj2str(request
			.getParameter("depttype"), "");
	String deptcodeStr = StringHelper.obj2str(request
			.getParameter("deptcode"), "");

	Hashtable prop = DispatchUtil.getCurrentUserData(request);

	//当前用户信息
	String deptcode = ""; //部门编码
	String deptname = ""; //部门名称
	String uname = ""; //帐号
	String pid = ""; //办公电话
	String phone = ""; //办公电话
	String mobilephone = ""; //手机
	String ZBDH = ""; //值班室电话
	if (prop != null) {
		deptcode = (String) prop.get("BRANCHID");
		deptname = (String) prop.get("BRANCHNAME");
		pname = (String) prop.get("NAME");
		uname = (String) prop.get("USERNAME");
		pid = (String) prop.get("PERSONID");
		phone = (String) prop.get("PHONE");
		mobilephone = (String) prop.get("MOBILEPHONE");
		ZBDH = (String) prop.get("ZBDH");
	}
	// new MaterialInfoAction().doReceivedMaterialInfo(alarmId, deptcode);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat sdf0 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	Date date = new Date();
	String daytime = sdf.format(date);
	String daytime0 = sdf0.format(date);

	String jgbh;//总队(2位),支队(4位),大队(6位)
	if ("0000".equals(deptcode.substring(2, 6))) {
		jgbh = deptcode.substring(0, 2);
	} else if ("00".equals(deptcode.substring(4, 6))) {
		jgbh = deptcode.substring(0, 4);
	} else {
		jgbh = deptcode.substring(0, 6);
	}

	String alarmIdValue = "".equals(alarmId) ? (deptcode
			.substring(0, 6) + daytime0) : alarmId;
	String readStatic = (!"0".equals(insrtOrUpdt))
			&& (!"1".equals(insrtOrUpdt)) ? "readonly" : "";
	String readStatic1 = (!"0".equals(insrtOrUpdt))
			&& (!"1".equals(insrtOrUpdt)) ? "disabled" : "";

	if ("99".equals(readContol)) {
		readStatic = "readonly";
		readStatic1 = "disabled";
	}
	System.out.println("-----------------" + deptcode);
	pname = StringHelper.obj2str(DispatchUtil
			.getDutyPersonNameByDepId(deptcode), "");
	String pjgmc = "";
	String pjgid = Constant.getParent(String.valueOf(session
			.getAttribute(Constant.JGID_VAR)));
	if (pjgid != null) {
		String sql = "select replace(jgmc,'" + Constant.GAJJTJC + "','"
				+ Constant.JJ + "') from t_sys_department where jgid='"
				+ pjgid + "'";
		pjgmc = String.valueOf(FlowUtil.readSingle(sql, null, null));
		pjgmc = pjgmc == null ? "" : pjgmc;
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%=title%></title>
        <link type="text/css" rel="Stylesheet"
            href="../../../webgis/css/map.css" />
        <link type="text/css" rel="Stylesheet"
            href="../../../webgis/css/bubble.css" />
        <link type="text/css" rel="Stylesheet"
            href="../../../webgis/css/contents.css" />
        <link rel="stylesheet" type="text/css" href="../../css/Global.css">
        <link rel="stylesheet" type="text/css"
            href="../../../sm/css/Global.css">
        <link rel="stylesheet" type="text/css" href="../../css/vcas.css">
        <link rel="stylesheet" type="text/css"
            href="../../../base/css/style1/tab.css" />
        <link rel="stylesheet" type="text/css"
            href="../../../base/css/style1/font.css" />
        <link rel="stylesheet" type="text/css"
            href="../../../base/css/style1/link.css" />
        <link rel="stylesheet" type="text/css"
            href="../../../base/css/style1/form.css" />
        <link rel="stylesheet" type="text/css"
            href="../../../base/css/style1/Popup.css" />
        <script type="text/javascript"
            src="../../../base/js/list/FillListBox.js"></script>
        <script type="text/javascript"
            src="../../../webgis/script/map/Util.js"></script>
        <script type="text/javascript"
            src="../../../webgis/script/map/LoadLibFile.js"></script>
        <script type="text/javascript" src="../../../base/js/global.js"></script>
        <script type="text/javascript"
            src="../../../base/js/calendar/CalendarDateTime.js"></script>
        <script type="text/javascript"
            src="../../js/ceditpolice/DepartmentSelect.js"></script>
        <script type="text/javascript" src="../../../base/js/popup/Popup.js"></script>
        <script type="text/javascript" src="../../../base/js/prototype.js"></script>
        <script type="text/javascript"  src="../../js/ceditpolice/alarmlInfoAdd.js"></script>
        <script type="text/javascript" src="../../js/ccommon/Flow.js"> </script>
        <script type="text/javascript" src="../../js/ccommon/RoadDept.js"></script>
        <script type="text/javascript" src="../../../base/js/tree/tree.js"></script>
        <style type="text/css">
            .cb_text{
                width:160px;
            }
            .flow_text{
                border: none;
                border-bottom: 1 solid #a5d3ef;
            }
            .tdtop_a{
                border-top: 1 solid #000000;
            }
            .tdtitle_a{
                border-top: 1 solid #a5d3ef;
                border-bottom: 1 solid #a5d3ef;
                border-left: 1 solid #a5d3ef;
                border-right: 1 solid #a5d3ef;
                line-height: 16px;
                color: #000000;
                border-collapse : separate;
                empty-cells:show;
                text-align: right;
            }
            .tdvalue_a{
                border-bottom: 1 solid #a5d3ef;
                border-left: 1 solid #a5d3ef;
                border-right: 1 solid #a5d3ef;
                line-height: 16px;
                color: #000000;
                border-collapse : separate;
                empty-cells:show;
                text-align: left;
            }
            .table_a{
                border-top: 1 solid #a5d3ef;
                border-left: 1 solid #a5d3ef;
            }
            .tdtitle_b{
                border-top: 1 solid #a5d3ef;
                border-bottom: 1 solid #a5d3ef;
                border-left: 1 solid #a5d3ef;
                line-height: 16px;
                color: #000000;
                border-collapse : separate;
                empty-cells:show;
                text-align: left;
            }
            .tdvalue_b{
                border-left: 1 solid #a5d3ef;
                border-bottom: 1 solid #a5d3ef;
                line-height: 16px;
                color: #000000;
                border-collapse : separate;
                empty-cells:show;
                text-align: left;
            }
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
                width:9%;
            }
            
            .tdtitle1{
                border-top: 0 solid #000000;
                border-right: 1 solid #a5d3ef;
                border-bottom: 1 solid #a5d3ef;
                border-left: 2px solid #106ead;
                line-height: 16px;
                color: #000000;
                border-collapse : separate;
                empty-cells:show;
                text-align: right;
                width:9%;
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
                width:16%;
            }
            .tdvalue1{
                border-top: 0 solid #000000;
                border-right: 2px solid #106ead;
                border-bottom: 1 solid #a5d3ef;
                border-left: 1 solid #a5d3ef;
                line-height: 16px;
                color: #000000;
                border-collapse : separate;
                empty-cells:show;
                text-align: left;
                width:16%;
            }
            .table2{
                background:#ffffff;
                border-top: 1 solid #a5d3ef;
                border-right: 0 solid #000000;
                border-bottom: 1 solid #a5d3ef;
                border-left: 0 solid #000000;
                border-collapse:collapse;
                font-size:11px;
                text-align: center;
            }
            .ltitle {
                background-color:#106ead;
                font-size:12px;
                font-weight:bold;
                color:#000;
                text-align:left;
                padding-left:15px;
                text-decoration: none;
                display:block;
                top:0px;
                left:0px;
                position:relative;
            }
            .ltitle span{
                display:block;
                top:-1px;
                left:14px;
                position:absolute;
                color:#fff;
                cursor:pointer;
            }
            td {
                line-height:23px;
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
            .tableinput33{
                  width:160px;
                 border:none; 
                 border-bottom:1px solid #000; 
                 font-size:12px; 
                 color:#FF0000; 
                 text-align:center;
             }
             .tableinput{
                 border:none; 
                 border-bottom:1px solid #000; 
                 font-size:12px; 
                 color:#FF0000; 
                 text-align:center;
             }
             /*宽高行高背景不显示超过对象尺寸的内容*/
            .lbackBt{
                 width:112px; 
                 height:22px; 
                 line-height:22px; 
                 background:url(../../images/dispatch/backbt.png) no-repeat; 
                 overflow:hidden;
            }
             
            /*块对象高度固定背景字体无装饰行高不显示超过对象尺寸的内容*/
            .lbackBt a{ 
                 display:block; 
                 height:22px; 
                 background:url(../../images/dispatch/backbt.png) center; 
                 text-decoration:none; 
                 line-height:22px;
                 overflow:hidden;
            }
            /*高度固定背景行高*/
            .lbackBt a:hover{ 
                 height:22px; 
                 background:url(../../images/dispatch/backbt.png) center center; 
                 line-height:22px;
            }
             /*块对象宽高固定外补丁内补丁文字的位置颜色鼠标光标类型*/
            .lbackBt .lbl{ 
                 display:block;
                 width:112px; 
                 height:15px; 
                 padding-top:0px; 
                 margin:0 auto; 
                 text-align:center; 
                 color:#ffffff; 
                 cursor:pointer;
            }
            /*颜色滤镜效果*/
            .lbackBt a:hover .lbl{ 
                 color:#dae76b; 
                 filter:glow(color=#ffffff,strength=1);
            }
            .table3{
                
                border-top: 0 solid #106ead;
                border-right: 0 solid #106ead;
                border-bottom: 0 solid #106ead;
                border-left: 0 solid #106ead;
                border-collapse:collapse;
                font-size:11px;
            }

			 /*文本框变下划线*/
			.textline {
			    font: 14px Tahoma,Verdana;
				border: 0;
				border-bottom: 0 solid black;
				background: ;
				text-align:center;
				text-color:red;
				/*padding-left:10px;*/
				width:190px;
			}
			/*文本框变下划线*/
			.text {
			    font: 14px Tahoma, Verdana;
				border: 0;
				border-bottom: 1 solid black;
				text-align:center;
				/*padding-left:10px;*/
				width:78px;
			}
        </style>
		
    </head>
    <body  bgcolor="#ffffff" onload="initPage();">
        <div style="text-align: center;width: 102%;height: 90%;">
            <fieldset style="width:99%;border:1px solid #a5d3ef" align="center">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
		<tr class="wTableTopCenter">
			<td width="5%" >
				<div align="center">
					<img src="../../../base/image/cssimg/table/tb.gif"
						width="16" height="16" alt="img" />
				</div>
			</td>
			<td width="70%" class="currentLocation">
				<span class="currentLocationBold">您当前的位置</span>：<%=title%>
				<%--<font style="font-size: 10pt;"> （编号：<input type="text"  class="textline" id="lookBh" value="<%=alarmIdValue%>" />）</font>
			--%></td>
		</tr>
	</table>
				<div id="showLength" style="color:red;display: none;">
				</div>
                <%--<legend style="width:8%;border:0px;font-size: 11pt;">
                    事故填报<font style="font-size: 10pt;"> （编号：<input type="text"  class="textline" id="lookBh" value="<%=alarmIdValue%>" />）</font>
                </legend>
                --%>
                <table class="table3" width="100%" border="1" id="block" borderColor='#a5d1ec' cellpadding="0px" cellspacing="0px">
                    <tbody id="flowbox">
                        <tr style="display: none">
                            <td>
                                <input type="hidden" id="jgbh" value="<%=jgbh%>"/>
                                <input type="hidden" id="jgid" value="<%=deptcode%>" />
                                 <input type="hidden" id="dname" value="<%=deptname%>" />
                                <input type="hidden" id="pjgmc" value="<%=pjgmc%>" />
                                <input type="hidden" id="czzt1" value="" />
                                <input type="hidden" id="zbrName" value="<%=pname%>" />
                                <input type="hidden" id="czzt2" value="" />
                                <input type="hidden" id="insrtOrUpdt" value="<%=insrtOrUpdt%>" />
                                <input id="ALARMID" type="text" value="<%=alarmId%>" readonly></input>
                                <%--ALARMID--%>
                                <input id="EVENTSOURCE" type="text" value="002022" readonly></input>
                                <%--警情上报系统--%>
                                <input id="EVENTTYPE" type="text" value="001024" readonly></input>
                                <%--重大警情--%>
                                <%--EVENTSTATE  事件状态--%>
                                <input id="ALARMUNIT" type="text" value=<%=deptcode%> readonly></input>
                                <%--报警机构--%>
                                <input id="ALARMREGIONID" type="text" value=<%=deptcode%>
                                    readonly></input>
                                <%--报警辖区编号--%>
                                <input id="ALARMREGION" type="text" value=<%=deptname%> readonly></input>
                                <%--报警辖区--%>
                                <input id="REPORTUNIT" type="text" value=<%=deptcode%> readonly></input>
                                <%--填报单位--%>
                                <input id="REPORTPERSONUN" type="text" value=<%=uname%> readonly></input>
                                <%--填报人--%>
                                <input id="REPORTPERSONXM" type="text" value=<%=pname%> readonly></input>
                                <%--填报人--%>
                                <input id="REPORTPERSONID" type="text" value=<%=pid%> readonly></input>
                                <div style="height:100%" id="flow1"></div>
                                <%--填报人--%>
                            </td>
                        </tr>
                        <tr style="display: none">
                            <td >
                                事件编号
                            </td>
                            <td >
                                <input type="text" id="ALARMIDVALUE" readonly value="<%=alarmIdValue%>" size="25"/>
                            </td>
                            <td >
                                审批人
                            </td>
                            <td >
                                <input class="text" type="text"   id="RESPONSIBLEPERSON" />
                            </td>
                        </tr>
                        
                        <tr height="30">
                        	<td align="center" width="20%" bgcolor="#F0FFFF" >
                                事故标题
                            </td>
                            <td colspan="3">
                                <input type="text" maxlength=200 style="border: 1px #7B9EBD solid;width:588px;"  id="TITLE">&nbsp;&nbsp;<font size="1" color="red">※</font>
                            </td>
                           
                            <!--<td align="center" width="20%" bgcolor="#F0FFFF" >
                                <span style="color: gray">事故编号</span>
                            </td>
                            <td algin="right" id="alarmDept_TrafficRestrain" width="35%">
                                <input type="text" id="glAccNum" name="glAccNum" disabled style="border: 1px #7B9EBD solid;width:120px;" height="30" />
                                 <font color="gray">（指事故系统编号）</font>
                            </td>
                        --></tr>

                        <tr height="30">
                         <td align="center" width="15%" bgcolor="#F0FFFF">
                              接警时间
                            </td>
                            <td>
                                <input type="text" maxlength=100 style="border: 1px #7B9EBD solid;width:200px;"  id="receivetime" readonly onClick="SelectDateTime(this)">&nbsp;&nbsp;<font size="1" color="red">※</font>
                            </td>
                           <td align="center" width="15%" bgcolor="#F0FFFF" >
                                填报时间
                            </td>
                            <td algin="right" > 
                                <input  type="text" id="REPORTTIME" readonly value="<%=daytime%>" style="border: 1px #7B9EBD solid;width:200px;"  />&nbsp;&nbsp;<font size="1" color="red">※</font>
                            </td>
                            
                            
                        </tr>
                        
                        <tr height="30">
                        	<td align="center" width="15%" bgcolor="#F0FFFF" >
                                填报单位
                            </td>
                            <td algin="right" id="alarmDept_TrafficRestrain">
                                <input type="hidden" id="REPORTUNITIDVALUE" value=<%=deptcode%> />
                                <input type="hidden" id="REPORTUNITVALUE" value=<%=deptname%> />
                                <input type="hidden" id="reportunitId" name="reportunitId" />
                                <input type="text" id="reportunitName" name="reportunitName" value="<%=deptname%>"  style="border: 1px #7B9EBD solid;width:200px;" readonly height="30" />&nbsp;&nbsp;<font size="1" color="red">※</font>
                            </td>
                            <td align="center" width="15%" bgcolor="#F0FFFF" >
                              <div>
									管辖大队
							  </div>
                            </td>
                            <td algin="right" id="alarmDept_dd">
                            <%
								if(jgbh.length() == 4) {
							%>
								<div id="sobject_single_div">
									
								</div>
                            <%
								} else {
							%>
							<input type="text" id="sobject_single_div" name="sobject_single_div"  value="<%=deptname%>"  style="border: 1px #7B9EBD solid;width:200px;" readonly height="30" />&nbsp;&nbsp;<font size="1" color="red">※</font>
							<%} %>
							</td>
                        </tr>
                        <tr height="30">
                        
                            <td align="center" bgcolor="#F0FFFF">
                                填 报 人
                            </td>
                            <td algin="right">
                                <input type="hidden" id="REPORTPERSONVALUE" value="<%=pid%>" />
                                <input height="25" type="text" id="reportPersonName" maxlength=20 style="border: 1px #7B9EBD solid;width:200px" name="reportPersonName" value="<%=pname%>"  />&nbsp;&nbsp;<font size="1" color="red">※</font>
                            </td>
                            
                            <td align="center" bgcolor="#F0FFFF">
                                联系电话
                            </td>
                            <td algin="right">
                                <input type="text" maxlength=15 id="TELENUM" name="TELENUM" style="border: 1px #7B9EBD solid;width:200px;" value="<%=ZBDH%>"  height="25" />&nbsp;&nbsp;<font size="1" color="red">※</font>
                            </td>
                        </tr>
                        
                        <tr height="30">
                            <td align="center" bgcolor="#F0FFFF">
                                事故地点
                            </td>
                            <td algin="right">
                                <input height="25" type="text" id="caseHappenPlace" style="border: 1px #7B9EBD solid;width:200px;" readonly="readonly"/>
                                <img id="roadselectImg" alt="选择道路列表" src="../../images/button/search.bmp" style="display: inline;cursor:hand;width:16px;height:16px;" onclick="RoadSelect.show('roadselect','caseHappenPlace');">&nbsp;&nbsp;<font size="1" color="red">※</font>
                                <div id="roadselect" style="position: absolute;top:0px;left:0px;width:270px;height:120px;
                                	border: 1px solid green;background-color:white;display: none;">
                                	<div id="rstitle" style="display:block;height:20px;background-color: green;text-align: right;padding:0;margin: 0;">
                                		<span style="margin: 0 8 0 0;padding: 0; font-size:12px;font-weight:bolder;cursor:hand;" onclick="RoadSelect.close('roadselect');" >X</span>
                                	</div>
                                	<div style="margin: 10 10 5 10">
                                		<label style="">道路等级：</label>
                                		<select id="roadLevelValueId" style="width:80px;" onchange="onchanged();Road.roadLevel.onchange(this);"></select>
                                	</div>
                                	
                                	<div style="margin: 5px 10px;" id="rn">
                                		<label>道路名称：</label>
                                		<div id="alarmRoad_TrafficCrowd_td" style="display: inline;"> </div>
                                	</div>
                                	<div id="accidentPlace" style="margin: 5px 10px;display: inline;">
                                		<label>事故位置：</label>
                                		K<input id="KMVALUE" type="text"  class="text" style="width:70px;display: inline;"  maxlength="6">
										+<input id="MVALUE" type="text" class="text" style="width:60px;" value="0" maxlength="3" >米
                                	</div>
                                	<div style="margin: 5px 10px;display: none;" id="AcDir">
                                		<label>事故方向：</label>
                                		<input type="radio" value="0" name="RADIOTYPE" id="RADIOTYPE_1" checked><span id="rdForward">下行</span><br/>
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<input type="radio" value="1" name="RADIOTYPE" id="RADIOTYPE_2"><span id="rdBack">上行</span><br/>
                                	</div>
                                	<div style="margin: 5px 10px;">
                                		<label id="ldbz">路&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;段：</label>
                                		   &nbsp;&nbsp;&nbsp;<font color="gray" id="tips" style="display: none">（包含：路名、路段、方向）</font>
                                		<TEXTAREA id="roadNote" STYLE="border: 1px #7B9EBD solid;width:228px;" ></textarea>
                                	</div>
                                	<div style="margin: 5 10;text-align: center;">
                                		<input type="button" value="确定" style="margin: 0 10 0 0" onclick="checkNull()" />
                                		<input type="button" value="取消" style="margin: 0 0 0 10" onclick="RoadSelect.close('roadselect');" />
                                	</div>
                                </div>
                            </td>
                            <td align="center" bgcolor="#F0FFFF">
                                事故时间
                            </td>
                            <td algin="right" >
                                <input type="text" id="caseHappenTime" readonly onClick="SelectDateTime(this)" style="border: 1px #7B9EBD solid;width:200px;"/>&nbsp;&nbsp;<font size="1" color="red">※</font>
                            </td>
                        </tr>
                        <tr height="30">
                             <td align="center" bgcolor="#F0FFFF">
                                死亡人数
                            </td>
                            <td algin="right" id="alarmDept_TrafficRestrain">
                                <input height="25" type="text" id="DEATHPERSONCOUNT" onBlur="checkedBoxProcess()" style="border: 1px #7B9EBD solid" maxlength=10 size="10"/>&nbsp;&nbsp;<font size="1" color="red">※</font><font color='black'> （数字）</font>
                            </td>
                            <td align="center" bgcolor="#F0FFFF">
                                受伤人数
                            </td>
                            <td algin="right">
                                <input height="25" type="text" id="BRUISEPERSONCOUNT" style="border: 1px #7B9EBD solid"  maxlength=10 size="10"/>&nbsp;&nbsp;<font size="1" color="red">※</font><font color='black'> （数字）</font>
                            </td>
                        </tr>
                        
	                    <form id="alarmFileUploadForm" name="alarmFileUploadForm" method="post" action="dispatch.alarmInfo.uploadFile.d" enctype="multipart/form-data" >
                        <tr height="30">
                        	<td align="center" bgcolor="#F0FFFF" >
	                                                                                 添加附件<input type="hidden" id="insertYwid" name="insertYwid"  value="<%=alarmIdValue%>" />
	                        </td>
	                        <td id="fileRegion" colspan=3 >
		                        <input type="hidden" id="insertYwid" name="insertYwid"  value="<%=alarmIdValue%>" />
		                        <input id="mfile0" type="file" name="mfile0" style="border: 1px #7B9EBD solid" size="50"  />
								<INPUT TYPE="button" id="addbtn" NAME="addbtn" style='border: 1px #7B9EBD solid;height: 20px;' value="增加附件" 
									onclick="ocAddFile('uploadTable','fileCounter',5);">
								<input type="hidden" id="fileCounter" value="0">（附件大小不能超过50兆）
		                        <table id="uploadTable" class="uploadTable" border="0" cellpadding="0" cellspacing="0"></table>
                         	</td>    
                        </tr>
	                    </form>
	                    
                        <div id="fileRegion">
                        <!-- 杜（追加） -->
                        </div>
                        
                        <tr height="30">
                             <td align="center" bgcolor="#F0FFFF">
                                情况描述
                            </td>
                            <td algin="right" colspan="3">
                                <textarea rows="4" name="eventModify" style="border: 1px #7B9EBD solid" id="eventModify" cols="71" onclick="setDescribe()"></textarea>&nbsp;&nbsp;<font size="1" color="red">※</font>
                            </td>
                        </tr>
                        
                        
                        <tr>
                            <td  colspan="1" align="center" bgcolor="#F0FFFF">
                                事故性质<br/>（必选项，可多选）
                            </td>
                            <td  colspan="3" >
                                <input type="checkbox"   id="ISTHREEUP"
                                    name="eType" />
                                <span class="cb_text">一次死亡3人以上交通事故</span>
                                <input type="checkbox"   id="ISBUS" name="eType" />
                                <span class="cb_text">营运大客车事故</span>
                                <input type="checkbox"   id="ISSCHOOLBUS"
                                    name="eType" />
                                <span >校车事故</span>
                                <br />
                                <input type="checkbox"   id="ISDANAGERCAR"
                                    name="eType" />
                                <span class="cb_text">危化品运输车交通事故</span>
                                <input type="checkbox"   id="ISFOREIGNAFFAIRS"
                                    name="eType" />
                                <span class="cb_text">涉港澳台及涉外事故</span>
                                <input type="checkbox"   id="ISPOLICE"
                                    name="eType" />
                                <span class="cb_text">涉警交通事故</span>
                                <br />
                                <input type="checkbox"   id="ISARMYACC"
                                    name="eType" />
                                <span class="cb_text">涉军交通事故</span>
                                <input id="ISCONGESTION" name="eType" type="checkbox"  />
                                <span >造成严重拥堵</span>
                                <input id="ISOthersItem" name="eType" type="checkbox" style="margin-left: 88px;"/>
                                <span >其他</span>
                                <input id="ISMASSESCASE" name="eType" type="checkbox" style="margin-left: 88px;display: none;"/>
                                <br />
                            </td>
                        </tr>
                        
                        <tr id='flow2_box' style="display: none">
							<td align="center" colspan=1 bgcolor="#F0FFFF">
								支队处警情况
							</td>
							<td colspan=3 >
								<textarea id="flow2" rows="2" style="border: 1px #7B9EBD solid" cols="71"></textarea>&nbsp;&nbsp;<font size="1" color="red">※</font>
							</td>
						</tr>
						<tr height="30" id="zdshr" style="display : none;"> 
                        	<td align="center" width="20%" bgcolor="#F0FFFF">
                               支队审核人
                            </td>
                            <td colspan="3">
                                <input type="text" maxlength=100 style="border: 1px #7B9EBD solid;width:589px;"  id="zdapprover">&nbsp;&nbsp;<font size="1" color="red">※</font>
                            </td>
                         </tr>
						<tr height="30" id="ddshr" style="display : none;">
                        	<td align="center" width="20%" bgcolor="#F0FFFF">
                             大队审核人
                            </td>
                            <td colspan="3">
                                <input type="text" maxlength=100 style="border: 1px #7B9EBD solid;width:589px;"  id="ddapprover">&nbsp;&nbsp;<font size="1" color="red">※</font>
                            </td>
                         </tr>
                        
                        
                        <!-- 杜（追加） -->
                        <div id="showFileId">
                        </div>
                        <!-- 杜（追加） -->

                        <tr style="display: none">
                            <td  >
                                情况描述

                            </td>

                            <td  align="left" colspan="6" >
                                <span style="width:150px">接警时间<input class="text"
                                        type="text" style="width:100px;" id="ALARMTIME" readonly
                                        <%=(("0".equals(insrtOrUpdt) || "1".equals(insrtOrUpdt)) && (!"99"
							.equals(readContol))) ? "onClick=\"SelectDateTime(this)\""
							: ""%> />
                                </span>
                                <span style="width:180px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;接警人<input
                                        class="text" type="text"   maxlength=20 style="width:100px;"
                                        id="RECEIVEPERSON"> </span>
                                <span style="width:100px">&nbsp;&nbsp;死亡人数<input class="text"
                                        type="text"   style="width:40px;" maxlength=10
                                        id="DEATHPERSONCOUNT" onkeypress="checkNum(this,event)">
                                </span>
                                <span style="width:100px">&nbsp;&nbsp;&nbsp;&nbsp;受伤人数<input class="text"
                                        type="text"   style="width:40px;" maxlength=10
                                        id="BRUISEPERSONCOUNT" onkeypress="checkNum(this,event)">
                                </span>
                                <span style="display: none;width:100px">&nbsp;&nbsp;&nbsp;&nbsp;失踪人数<input class="text"
                                        type="text"   style="width:40px;" maxlength=10
                                        id="MISSINGPERSONCOUNT" onkeypress="checkNum(this,event)">
                                </span>
                                <div style="display: none">

                                    <span
                                        style="width:100%;font-weight:900;text-align:center;margin-right:150px;">详细描述</span>
                                    <div>
                                        <span  class="lsearch" style="margin-right:20px;"> <a
                                            href="#" onclick="showDetailInfo();"><span class="lbl">查看范文</span>
                                        </a> </span>
                                        <span id="bt10" class="lsearch" style="margin-right:20px;"> <a
                                            href="#" onclick="writeAble();"><span class="lbl">编辑补充</span>
                                        </a> </span>
                                    </div>
                                </div>
                                
                            </td>
                        </tr>



                        
                    </tbody>
                </table>
            </fieldset>
            <div style="text-align: center;width: 2%;height: 2%;">
            </div>
                <div style="text-align: center;height:25px;border-bottom: 0 solid #a5d3ef;" id="buttonVegion">
                <iframe id="target_upload" name="target_upload" src="" style="display:none"></iframe>
                <span id="bt6" class="lsearch" style="margin-right:70px;"> <a
                    href="#" onclick="printWord('<%=daytime%>');"><span class="lbl">打印</span> </a> </span>
                <span id="bt4" class="lsearch" style="margin-right:70px;"> <a
                    href="#" onclick="insertAlarmInfo('0');"><span class="lbl">保存草稿</span>
                </a> </span>
                <span id="bt8" class="lsearch" style="margin-right:70px;"> <a
                    href="#" onclick="insertAlarmInfo('1');"><span class="lbl">报送</span>
                </a> </span>
             </div>
        </div>
    </body>
</html>