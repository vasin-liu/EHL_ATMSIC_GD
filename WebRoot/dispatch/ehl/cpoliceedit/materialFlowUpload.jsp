<%@page import="com.appframe.data.sql.DBHandler"%>
<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.appframe.utils.StringHelper"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="com.ehl.dispatch.common.*"%>
<%@ page import="com.ehl.dispatch.cdispatch.action.MaterialInfoAction"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ page import="com.appframe.data.sql.DBHandler"%>
<%@page import="com.ehl.sm.common.Constants"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%@include file="../../Message.oni"%>
<%
	String title = FlowUtil.getFuncText("570601");
	String insrtOrUpdt = StringHelper.obj2str(
			request.getParameter("insrtOrUpdt"), "");
	String message = StringHelper.obj2str(
			request.getParameter("message"), "");
	String alarmId = StringHelper.obj2str(
			request.getParameter("alarmId"), "");
	String hideBtn = StringHelper.obj2str(
			request.getParameter("hideBtn"), "");
	String readContol = StringHelper.obj2str(
			request.getParameter("readContol"), "");
	String xbflg = StringHelper.obj2str(request.getParameter("xbflg"),
			"");

	String depttype = StringHelper.obj2str(
			request.getParameter("depttype"), "");
	String deptcodeStr = StringHelper.obj2str(
			request.getParameter("deptcode"), "");

	Hashtable prop = DispatchUtil.getCurrentUserData(request);

	//当前用户信息
	String deptcode = ""; //部门编码
	String deptname = ""; //部门名称
	String uname = ""; //帐号
	String pid = ""; //办公电话
	String phone = ""; //办公电话
	String mobilephone = ""; //手机
	if (prop != null) {
		deptcode = (String) prop.get("BRANCHID");
		deptname = (String) prop.get("BRANCHNAME");
		pname = (String) prop.get("NAME");
		uname = (String) prop.get("USERNAME");
		pid = (String) prop.get("PERSONID");
		phone = (String) prop.get("PHONE");
		mobilephone = (String) prop.get("MOBILEPHONE");
	}
	// new MaterialInfoAction().doReceivedMaterialInfo(alarmId, deptcode);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat sdf0 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
	Date date = new Date();
	String daytime = sdf.format(date);
	String daytime0 = sdf0.format(date);

	//Modified by Liuwx 2011-07-11
	String[] printStrObj = DepartmentManage.getDeptInfo(request, "1")
			.split(",");//获取单位信息串
	String printjgid = printStrObj[0];//单位编码
	Object result = DBHandler
			.getSingleResult("select replace(f_get_dept(jgid),'公安局交通警察','交警') from t_sys_department where jgid="
					+ printjgid);
	String printjgmc = StringHelper.obj2str(result, "");
	System.out.println(">>>>>>>>>>>>>>>>>机构名称:" + printjgmc
			+ "<<<<<<<<<<<<<<<<<<<<<");
	//String jgid="441905000000";//12位机构编码
	//Modification finished

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
	String personName = StringHelper.obj2str(
			DispatchUtil.getDutyPersonNameByDepId(deptcode), "");
	pname = personName;
	System.out.println("**************");
	System.out.println(pname);
	System.out.println("**************");
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
			src="../../../base/js/list/FillListBox.js">
	
</script>
		<script type="text/javascript"
			src="../../../webgis/script/map/Util.js">
	
</script>
		<script type="text/javascript"
			src="../../../webgis/script/map/LoadLibFile.js">
	
</script>
		<script type="text/javascript" src="../../../base/js/global.js">
	
</script>
		<script type="text/javascript"
			src="../../../base/js/calendar/CalendarDateTime.js">
	
</script>
		<script type="text/javascript" src="../../../base/js/tree/tree.js">
	
</script>
		<script type="text/javascript"
			src="../../js/ceditpolice/DepartmentSelect.js">
	
</script>
		<script type="text/javascript" src="../../../base/js/popup/Popup.js">
	
</script>
		<script type="text/javascript" src="../../../base/js/prototype.js">
	
</script>
		<script type="text/javascript"
			src="../../js/ceditpolice/materialInfo.js">
	
</script>
		<script type="text/javascript" src="../../js/ccommon/RoadDept.js">
	
</script>
		<script type="text/javascript" src="../../js/ccommon/Flow.js">
	
</script>
		<script type="text/javascript" src="../../js/ccommon/content.js">
	
</script>

		<style type="text/css">
.cb_text {
	width: 160px;
}

.flow_text {
	border: none;
	border-bottom: 1 solid #a5d3ef;
}

.tdtop_a {
	border-top: 1 solid #000000;
}

.tdtitle_a {
	border-top: 1 solid #a5d3ef;
	border-bottom: 1 solid #a5d3ef;
	border-left: 1 solid #a5d3ef;
	border-right: 1 solid #a5d3ef;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: right;
}

.tdvalue_a {
	border-bottom: 1 solid #a5d3ef;
	border-left: 1 solid #a5d3ef;
	border-right: 1 solid #a5d3ef;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: left;
}

.table_a {
	border-top: 1 solid #a5d3ef;
	border-left: 1 solid #a5d3ef;
}

.tdtitle_b {
	border-top: 1 solid #a5d3ef;
	border-bottom: 1 solid #a5d3ef;
	border-left: 1 solid #a5d3ef;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: left;
}

.tdvalue_b {
	border-left: 1 solid #a5d3ef;
	border-bottom: 1 solid #a5d3ef;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: left;
}

.tdtitle {
	border-top: 0 solid #000000;
	border-right: 1 solid #a5d3ef;
	border-bottom: 1 solid #a5d3ef;
	border-left: 1 solid #a5d3ef;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: right;
	width: 9%;
}

.tdtitle1 {
	border-top: 0 solid #000000;
	border-right: 1 solid #a5d3ef;
	border-bottom: 1 solid #a5d3ef;
	border-left: 2px solid #106ead;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: right;
	width: 9%;
}

.tdvalue {
	border-top: 0 solid #000000;
	border-right: 1 solid #a5d3ef;
	border-bottom: 1 solid #a5d3ef;
	border-left: 1 solid #a5d3ef;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: left;
	width: 16%;
}

.tdvalue1 {
	border-top: 0 solid #000000;
	border-right: 2px solid #106ead;
	border-bottom: 1 solid #a5d3ef;
	border-left: 1 solid #a5d3ef;
	line-height: 16px;
	color: #000000;
	border-collapse: separate;
	empty-cells: show;
	text-align: left;
	width: 16%;
}

.table2 {
	background: #ffffff;
	border-top: 1 solid #a5d3ef;
	border-right: 0 solid #000000;
	border-bottom: 1 solid #a5d3ef;
	border-left: 0 solid #000000;
	border-collapse: collapse;
	font-size: 11px;
	text-align: center;
}

.ltitle {
	background-color: #106ead;
	font-size: 12px;
	font-weight: bold;
	color: #000;
	text-align: left;
	padding-left: 15px;
	text-decoration: none;
	display: block;
	top: 0px;
	left: 0px;
	position: relative;
}

.ltitle span {
	display: block;
	top: -1px;
	left: 14px;
	position: absolute;
	color: #fff;
	cursor: pointer;
}

td {
	line-height: 23px;
}

/*宽高行高背景不显示超过对象尺寸的内容*/
.lsearch {
	width: 82px;
	height: 22px;
	line-height: 22px;
	background: url(../../images/dispatch/btn.png) no-repeat;
	overflow: hidden;
}

/*块对象高度固定背景字体无装饰行高不显示超过对象尺寸的内容*/
.lsearch a {
	display: block;
	height: 22px;
	background: url(../../images/dispatch/btn.png) center;
	text-decoration: none;
	line-height: 22px;
	overflow: hidden;
}

/*高度固定背景行高*/
.lsearch a:hover {
	height: 22px;
	background: url(../../images/dispatch/btn.png) center center;
	line-height: 22px;
}

/*块对象宽高固定外补丁内补丁文字的位置颜色鼠标光标类型*/
.lsearch .lbl {
	display: block;
	width: 79px;
	height: 15px;
	padding-left: 3px;
	padding-top: 0px;
	margin: 0 auto;
	text-align: center;
	color: #ffffff;
	cursor: pointer;
}

/*颜色滤镜效果*/
.lsearch a:hover .lbl {
	color: #dae76b;
	filter: glow(color =   #ffffff, strength =   1);
}

.tableinput33 {
	width: 160px;
	border: none;
	border-bottom: 1px solid #000;
	font-size: 12px;
	color: #FF0000;
	text-align: center;
}

.tableinput {
	border: none;
	border-bottom: 1px solid #000;
	font-size: 12px;
	color: #FF0000;
	text-align: center;
}

/*宽高行高背景不显示超过对象尺寸的内容*/
.lbackBt {
	width: 112px;
	height: 22px;
	line-height: 22px;
	background: url(../../images/dispatch/backbt.png) no-repeat;
	overflow: hidden;
}

/*块对象高度固定背景字体无装饰行高不显示超过对象尺寸的内容*/
.lbackBt a {
	display: block;
	height: 22px;
	background: url(../../images/dispatch/backbt.png) center;
	text-decoration: none;
	line-height: 22px;
	overflow: hidden;
}

/*高度固定背景行高*/
.lbackBt a:hover {
	height: 22px;
	background: url(../../images/dispatch/backbt.png) center center;
	line-height: 22px;
}

/*块对象宽高固定外补丁内补丁文字的位置颜色鼠标光标类型*/
.lbackBt .lbl {
	display: block;
	width: 112px;
	height: 15px;
	padding-top: 0px;
	margin: 0 auto;
	text-align: center;
	color: #ffffff;
	cursor: pointer;
}

/*颜色滤镜效果*/
.lbackBt a:hover .lbl {
	color: #dae76b;
	filter: glow(color =   #ffffff, strength =   1);
}

.table3 {
	border-top: 0 solid #106ead;
	border-right: 0 solid #106ead;
	border-bottom: 0 solid #106ead;
	border-left: 0 solid #106ead;
	border-collapse: collapse;
	font-size: 11px;
}

.table4 {
	border-top: 1 solid green;
	border-right: 1 solid green;
	border-bottom: 1 solid green;
	border-left: 1 solid green;
	border-collapse: collapse;
	font-size: 11px;
}

/*文本框变下划线*/
.textline {
	font: 14px Tahoma, Verdana;
	border: 0;
	border-bottom: 0 solid black;
	background: ;
	text-align: center;
	text-color: red;
	/*padding-left:10px;*/
	width: 190px;
}

/*文本框变下划线*/
.text1 {
	font: 14px Tahoma, Verdana;
	border: 0;
	border-bottom: 1 solid black;
	background: ;
	text-align: center;
	/*padding-left:10px;*/
	width: 62px;
}

</style>
	<script src="../../../base/js/jquery.js"></script>
	<script src="../../../base/js/ajaxupload.js"></script>
    <script type="text/javascript">
    $(function() {
        // 创建AJAX方式上传文件
        var $uploadBtn = $("#uploadBtn");
        new AjaxUpload($uploadBtn, {
            action: 'dispatch.cmaterialInfo.XBUploadFile.d',
            name: 'uploadfile',
            onSubmit: function(file, ext){
                $("#info").html("<img src='../../images/alarm/alarm-xbloader.gif'>开始上传");
            },
            onComplete: function(file, response){
            var results = response.split(" ### ")
	            if(response != undefined || response != "" || response != null) {
		            if(results[0].length != 0) {
		            	if(results[1] != undefined) {
		           		 	document.getElementById("fjlocal").value = results[1]
		            	}
			            document.getElementById("fjid").value = results[0];
			            document.getElementById("upflag").value = results[3];
			            var k = window.dialogArguments;
			            if(k != null) { 
			            	if(k.document.getElementById("fjid").value != "") {
			            		deleteAttachmentFile(k.document.getElementById("fjid").value, '<%=alarmIdValue%>');
			            	}
						 	k.document.getElementById("fjid").value = document.getElementById("fjid").value ;
						 	k.document.getElementById("fjlocal").value = document.getElementById("fjlocal").value;
						 	k.document.getElementById("filebutton").value = " 修 改 ";
						 	k.document.getElementById("fjwz").value = results[2];
						 	window.close();
						} 
		            } else {
		            	alert("上传失败！请重试。")
		            }
	            }
            }
        });
    });
    </script>

	</head>
	<body onunload="if(document.getElementById('upflag').value != 'true') { deleteAttachmentFile($('fjid').value, '<%=alarmIdValue%>')}">
		<table align="center">
			<tr>
				<td>
					<div id="info" align="center">请选择您要上传的文件！</div>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input type="text" width="200px" id="uploadText" size=78" readonly="readonly" style="border: 1px #7B9EBD solid">
					<input type="button" width="200px" id="uploadBtn" style="border: 1px #7B9EBD solid; width: 70px" value=" 浏览... ">
					<input type="hidden" id="fjid" name="fjid" value="">
					<input type="hidden" id="upflag" name="upflag" value="">
					<input type="hidden" id="fjlocal" name="fjlocal" value="">
					<input type="hidden" id="parent" name="parent" value="">
				</td>
			</tr>
		</table>
	</body>
</html>

