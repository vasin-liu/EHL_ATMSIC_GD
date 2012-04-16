<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.appframe.utils.StringHelper"%>
<%@include file="../../../base/Message.oni"%>
<%
	String alarmId = StringHelper.obj2str(request.getParameter("alarmId"),""); 
	String depttype = StringHelper.obj2str(request.getParameter("depttype"),""); 
	String deptcodeStr = StringHelper.obj2str(request.getParameter("deptcode"),"");
	String deafultArea = com.appframe.common.Setting.getString("deafultArea");
	String showType = StringHelper.obj2str(request.getParameter("showtype"),"");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>道路交通事故接报信息表</title>
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
	 	<link rel="stylesheet" type="text/css" href="../../css/vcas.css">
	 	<link rel="STYLESHEET" type="text/css" href="../../../sm/css/popup/Popup.css">
	 	<link rel="stylesheet" type="text/css" href="../../../sm/css/Global.css"> 
	 	<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
	 	<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDateTime.js"></script>
		<script type="text/javascript" src="../../js/editPolice/editPolice.js"></script>
		<script type="text/javascript" src="../../js/editPolice/ModifyAccident.js"></script>
		<script type="text/javascript">
			var deafultArea = '<%=deafultArea%>';
			var depttype = '<%=depttype%>';
			var deptcode = '<%=deptcodeStr%>';
			var alarmId = '<%=alarmId%>';
			var showType = '<%=showType%>';
			if(!alarmId){
				window.onload = function (){
					getNewInfo('Accident');
					$("btnreceive").innerHTML = "";
					$("btnreceive").className = '';
					//$("signSite_Accident").style.display="none";
				};
			}else{
				window.onload = function (){
					getAccInfo(alarmId);
					if(depttype=='2'){
						$("btnreceive").innerHTML = "";
						$("btnreceive").className = '';
					}
				};
			}
		</script>
	</head>
<body>
	<div style="text-align: center;width: 100%;height: 100%;">
		<fieldset style="width:99%;height:95%;border:1px solid #CCCCCC" align="center">
			<legend style="border:0px;font-size: 12pt;">道路交通事故接报信息表</legend>
			<table class="table2" width="100%">
				<tr bgcolor="#99c4f2">
					<td class="tdtitle" colspan="3">
						<input type="hidden" id="insertOrUpdate_Accident" class="textwidth" name="editinput">
						<legend style="border:0px;font-size:14px">填报信息</legend>
					</td>
					<td>
						<div class="search" id="btnreceive">
							<a href="javascript:#" onclick="receiveReport('_Accident',$('flowId_Accident').value,$('alarmId_Accident').value);"><span class="lbl" id="ReceiveEvent_Accident" >接收上报</span></a>
						</div>
					</td>
				</tr>
				<tr>
					<td class="tdtitle" style="display : none">填报单号：</td>
					<td class="tdvalue" style="display : none">
						<input type="text" onKeyDown="if(event.keyCode==8){return false;};" id="alarmId_Accident" class="textwidth" readonly name="editinput" >
						<input type="hidden" id="flowId_Accident" class="textwidth" readonly name="editinput" >
					</td>
					<td class="tdtitle">填报时间：</td>
					<td class="tdvalue">
						<input type="text" id="feedBackTime_Accident" onKeyDown="if(event.keyCode==8){return false;};" readonly onclick="SelectDateTime(this)" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle">填报人员：</td>
					<td class="tdvalue">
						<input type="text" id="feedBackPerson_Accident" class="textwidth" name="editinput" >
					</td>
				</tr>
				<tr>
					<td class="tdtitle">填报单位：</td>
					<td class="tdvalue">
						<input type="text" id="feedBackUnit_Accident" class="textwidth" name="editinput" readonly>
						<input type="hidden" id="feedBackUnitId_Accident" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle" style="display : none">上报批准人：</td>
					<td class="tdvalue" style="display : none">
						<input type="text" id="pzr_Accident" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle" style="display:none">接警方式：</td>
					<td class="tdvalue" id="ReceiveType_td_Accident" style="display:none">
						<script>
							fillListBox("ReceiveType_td_Accident","ReceiveType_Accident","111","select id,name from t_attemper_code where id like '214%'","请选择");
						</script>
					</td>
					<td class="tdtitle"></td>
					<td class="tdvalue"></td>
				</tr>
				<tr style="display : none">
					<td class="tdtitle">接警单位：</td>
					<td class="tdvalue">
						<input type="text" id="ReceiveUnit_Accident" class="textwidth" name="editinput">
						<input type="hidden" id="ReceiveUnitId_Accident" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle">接警时间：</td>
					<td class="tdvalue">
						<input type="text" id="ReceiveTime_Accident" class="textwidth" onclick="SelectDateTime(this)" name="editinput" onKeyDown="if(event.keyCode==8){return false;};" readonly>
					</td>
					<td class="tdtitle" id="">接警人员：</td>
					<td class="tdvalue">
						<input type="text" id="ReceivePerson_Accident" class="textwidth" name="editinput">
					</td>
				</tr>
				<tr bgcolor="#99c4f2">
					<td class="tdtitle" colspan="4">
						<legend style="border:0px;font-size:14px">
							事故信息
						</legend>
					</td>
				</tr>
				<tr style="display : none">
					<td class="tdtitle">经度：</td>
					<td class="tdvalue">
						<input type="text" id="X_Accident" class="textwidth" name="editinput" >
					</td>
					<td class="tdtitle">纬度：</td>
					<td class="tdvalue">
						<input type="text" id="Y_Accident" class="textwidth" name="editinput" > 
					</td>
					<td class="tdtitle">警情状态：</td>
					<td class="tdvalue">
						<input type="text" id="alarmState_Accident" class="textwidth" name="editinput">
						<input type="hidden" id="eventType_Accident" class="textwidth" name="editinput">
						<input type="hidden" id="eventSource_Accident" class="textwidth" name="editinput">
						<input type="hidden" id="superUnit_Accident" class="textwidth" name="editinput">
					</td> 	
				</tr>
				<tr>
					<td class="tdtitle">事故时间:</td>
					<td class="tdvalue">
						<input type="text" id="alarmTime_Accident" class="textwidth" onclick="SelectDateTime(this)" onKeyDown="if(event.keyCode==8){return false;};" name="editinput" readonly>
					</td>
					<td class="tdtitle" style="display : none">事故地点：</td>
					<td class="tdvalue" style="display : none">
						<input type="text" id="AlarmAddress_Accident" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle">营运大客事故：</td>
					<td class="tdvalue"><input type="checkbox" id="yydksg_Accident" name="editinput"></td>
				</tr>
				<tr style="display : none">
					<td class="tdtitle">道路名称:</td>
					<td class="tdvalue" id ="dlmc_Accident_td">
						<script>
							fillListBox("dlmc_Accident_td","roadName_Accident","111","SELECT BH,DLMC FROM T_OA_DICT_ROAD","请选择");
						</script>
					</td>
					<td class="tdtitle">公里数：</td>
					<td class="tdvalue">
						<input type="text" id="kmvalue_Accident" title="请输入大于0且小于1000的整数" onblur="isValidate(this);" class="textwidth" name="editinput" value=0>
					</td>
					<td class="tdtitle">米数：</td>
					<td class="tdvalue">
						<select id="mvalue_Accident" style="width:111px;">
							<option value="0" selected>0米</option>
							<option value="100">100米</option>
							<option value="200">200米</option>
							<option value="300">300米</option>
							<option value="400">400米</option>
							<option value="500">500米</option>
							<option value="600">600米</option>
							<option value="700">700米</option>
							<option value="800">800米</option>
							<option value="900">900米</option>
						</select>
					</td>
				</tr>
				<tr>
					<td class="tdtitle">死亡人数：</td>
					<td class="tdvalue">
						<input type="text" id="dcswrs_Accident" onblur="isValidate(this);" class="textwidth" name="editinput"> 
					</td>
					<td class="tdtitle">涉及危化事故：</td>
					<td class="tdvalue"><input type="checkbox" id="whsg_Accident" name="editinput"></td>
				</tr>
				<tr style="display:none">
					<td class="tdtitle">是否造成拥堵：</td>
					<td class="tdvalue" id ="dlmc_Accident_td">
						<input type="checkbox" id="zcyd_Accident" name="editinput">
					</td>
					<td class="tdtitle">拥堵公里数：</td>
					<td class="tdvalue">
						<input type="text" id="ydtkm_Accident" title="请输入大于0且小于1000的整数" onblur="isValidate(this);" class="textwidth" name="editinput" value=0>
					</td>
				</tr>
				<tr>
					<td class="tdtitle">受伤人数：</td>
					<td class="tdvalue">
						<input type="text" id="ssrs_Accident" onblur="isValidate(this);" class="textwidth" name="editinput">
					</td>
					<td class="tdtitle">涉外事故：</td>
					<td class="tdvalue"><input type="checkbox" id="swsg_Accident" name="editinput"></td>
				</tr>
				<tr>
					<td class="tdtitle"></td>
					<td class="tdvalue"></td>
					<td class="tdtitle">涉及军警事故：</td>
					<td class="tdvalue">
						<input type="checkbox" id="sjjj_Accident" name="editinput">
					</td>
				</tr>
				<tr>
					<td class="tdtitle"></td>
					<td class="tdvalue"></td>
					<td class="tdtitle">高速公路事故：</td>
					<td class="tdvalue">
						<input type="radio" id="gsgl_Accident" name="editradio">
					</td>
				</tr>
				<tr>
					<td class="tdtitle"></td>
					<td class="tdvalue"></td>
					<td class="tdtitle">国省道事故：</td>
					<td class="tdvalue">
						<input type="radio" id="gsd_Accident" name="editradio">
					</td>
				</tr>
				<tr bgcolor="#99c4f2">
					<td class="tdtitle" colspan="2">
						<legend style="border:1px;font-size:14px">
							事故车辆
						</legend>
					</td>
					<td>
						<div class="search" id="btnaddCar_Accident">
							<a href="javascript:#" onclick="addCar('_Accident');"><span class="lbl" id="addCar_Accident" >增加车辆</span></a>
						</div>
					</td>
					<td>
						<div class="search" id="btndelCar_Accident">
							<a href="javascript:#" onclick="delCar('_Accident');"><span class="lbl" id="delCar_Accident" >删除车辆</span></a>
						</div>
					</td> 
				</tr>
				<tbody id="accCarList">
					<tr id="1Car_Accident" carId="">
						<td class="tdtitle">号牌种类：</td>
						<td id="hpzltd_Accident">
							<script language="javascript">
								fillListBox("hpzltd_Accident","1hpzl_Accident","211","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='011001'","","");
							</script>
						</td>
						<td class="tdtitle">车辆号牌：</td>
						<td class="tdvalue">
							<span id="HPHMT_TD">
		                    	<script language="javascript">
			                       fillListBox("HPHMT_TD","1hphmt_Accident","40","select distinct substr(dm, 0, 1) as a,substr(dm, 0, 1) from t_sys_code where dmlb = '011006' order by a",deafultArea,"");
		                        </script>
	                    	</span>
							<input type="text" id="1hphm_Accident" class="textwidth" name="editinput" style="width:171px"> 
						</td>
						<td class="tdtitle" style="display:none;">驾驶人姓名：</td>
						<td class="tdvalue" style="display:none;"><input type="text" id="1drvname_Accident" class="textwidth" name="editinput"></td> 	
					</tr>
				</tbody>
				<tr bgcolor="#99c4f2">
					<td class="tdtitle" colspan="4">
						<legend style="border:0px;font-size:14px">
							事故描述
						</legend>
					</td>
				</tr>
				<tr style="display:none;">
					<td class="tdtitle">事故原因：</td>
					<td class="tdvalue" id="sgyy_td_Accident">
						<script language="javascript">
							fillListBox("sgyy_td_Accident","sgyy_Accident","111","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='011011'","","");
						</script>
					</td>
					<td class="tdtitle">事故形态:</td>
					<td class="tdvalue" id="sgxt_td_Accident">
						<script language="javascript">
							fillListBox("sgxt_td_Accident","sgxt_Accident","111","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='011012'","","");
						</script>
					</td>
					<td class="tdtitle">事故等级:</td>
					<td class="tdvalue" id="sgdj_td_Accident">
						<script language="javascript">
							fillListBox("sgdj_td_Accident","sgdj_Accident","111","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='011013'","","");
						</script>
					</td>	
				</tr>
				<tr style="display:none;">
					<td class="tdtitle">下落不明人数：</td>
					<td class="tdvalue"><input type="text" id="xlbmrs_Accident" onblur="isValidate(this);" class="textwidth" name="editinput"></td> 	
				</tr>
				<tr style="display:none;">
					<td class="tdtitle">抢救无效死亡人数：</td>
					<td class="tdvalue">
						<input type="text" id="qjwxswrs_Accident" onblur="isValidate(this);" class="textwidth" name="editinput"> 
					</td>
					<td class="tdtitle"></td>
					<td class="tdvalue"></td>
					<td class="tdtitle"></td>
					<td class="tdvalue"></td>
				</tr>
				<tr>
					<td class="tdtitle">事故简要情况：<br><font style="color: #FF0000;">(内容应及时编辑补充完整)</font></td>
					<td class="tdvalue" colspan="3">
						<textarea  rows="5" style="width:100%" id="alarmDesc_Accident" name="editinput"></textarea>
					</td>
				</tr>
				<%
					if(!alarmId.equals("")){
				%>
				<tr bgcolor="#99c4f2">
					<td class="tdtitle" colspan="4">
						<legend style="border:0px;font-size:14px">
							支队审核处理情况
						</legend>
					</td>
				</tr>
				<tr>
					<td class="tdtitle">接报时间：</td>
					<td class="tdvalue">
						<input type="text" id="receiveTime_zhd_Accident" class="textwidth" name="editinput" readonly>
					</td>
					<td class="tdtitle">值班人员：</td>
					<td class="tdvalue">
						<input type="text" id="dutyperson_zhd_Accident" class="textwidth" name="editinput" readonly>
					</td>
					<td class="tdtitle" style="display : none"></td>
					<td class="tdvalue" style="display : none"></td>
				</tr>
				<tr>
					<td class="tdtitle">批准人：</td>
					<td class="tdvalue">
						<input type="text" id="receivePzr_zhd_Accident" class="textwidth" name="editinput" readonly>
					</td>
					<td class="tdtitle"></td>
					<td class="tdvalue"></td>
					<td class="tdtitle" style="display : none"></td>
					<td class="tdvalue" style="display : none"></td>
				</tr>
				<tr>
					<td class="tdtitle">信息核实情况：</td>
					<td class="tdvalue" colspan="3">
						<textarea  rows="5" style="width:100%" id="infoVerify_zhd_Accident" name="editinput"></textarea>
					</td>
				</tr>
				<tr>
					<td class="tdtitle">向领导汇报情况：</td>
					<td class="tdvalue" colspan="3">
						<textarea  rows="5" style="width:100%" id="reportHead_zhd_Accident" name="editinput"></textarea>
					</td>
				</tr>
				<tr bgcolor="#99c4f2">
					<td class="tdtitle" colspan="4">
						<legend style="border:0px;font-size:14px">
							部局处置情况
						</legend>
					</td>
				</tr>
				<tr>
					<td class="tdtitle">接报时间：</td>
					<td class="tdvalue">
						<input type="text" id="receiveTime_zd_Accident" class="textwidth" name="editinput" readonly>
					</td>
					<td class="tdtitle">值班人员：</td>
					<td class="tdvalue">
						<input type="text" id="dutyperson_zd_Accident" class="textwidth" name="editinput" readonly>
					</td>
					<td class="tdtitle" style="display:none"></td>
					<td class="tdvalue" style="display:none"></td>
				</tr>
				<tr>
					<td class="tdtitle">批准人：</td>
					<td class="tdvalue">
						<input type="text" id="receivePzr_zd_Accident" class="textwidth" name="editinput" readonly>
					</td>
					<td class="tdtitle"></td>
					<td class="tdvalue"></td>
					<td class="tdtitle" style="display:none"></td>
					<td class="tdvalue" style="display:none"></td>
				</tr>
				<tr>
					<td class="tdtitle">信息核实情况：</td>
					<td class="tdvalue" colspan="3">
						<textarea  rows="5" style="width:100%" id="infoVerify_zd_Accident" name="editinput"></textarea>
					</td>
				</tr>
				<tr>
					<td class="tdtitle">向领导汇报情况：</td>
					<td class="tdvalue" colspan="3">
						<textarea  rows="5" style="width:100%" id="reportHead_zd_Accident" name="editinput"></textarea>
					</td>
				</tr>
				<%
					}
				%>
			</table>
			<div style="text-align: right">
<!--				<input type="button" value="定位案发地点" id="signSite_Accident" onclick="showMap('Accident',0);" style="display:none;">-->
				<input type="button" value="上报警情" id="saveData_Accident" onclick="modifyAccidnet('_Accident');">
			</div>
		</fieldset>
	</div>
</body>
</html>
