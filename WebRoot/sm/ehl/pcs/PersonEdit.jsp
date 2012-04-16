<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.ehl.base.util.CreateSequence"%>
<%@ page import="com.ehl.sm.pcs.DepartmentManage"%>
<%@ include file="../../../base/Message.oni"%>

<%
    String[] strObj=DepartmentManage.getDeptInfo(request,"1").split(",");//获取单位信息串

    String jgid=strObj[0];//单位编码
    String jgmc=strObj[1];//单位名称
    String ccbm=strObj[2];//单位层次编码
 %>
<!--
    * author：郭田雨
    * date:   2008-03-19
    * version:
-->
<%  response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", -10);
	String insrtOrUpdt = request.getParameter("insrtOrUpdt");
	String projName = com.appframe.common.Setting.getString("redirecturl");
	String RYID = "";
	if(insrtOrUpdt.equals("0")){
	    RYID = CreateSequence.getMaxForSeq("seq_t_sys_person",12);//新增人员编号
	}else{
	    RYID = request.getParameter("RYID");//编辑人员编号
	}
%>
<html>
	<head>
		<title>人员管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<jsp:include page="../../../base/ShareInc.html" />
		<link rel="STYLESHEET" type="text/css" href="../../css/popup/Popup.css">
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/dhtmlx/dhtmlXTree.js"></script>
		<script type="text/javascript" src="../../../base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="../../../base/js/calendar/CalendarDate.js"></script>
		<script type="text/javascript" src="../../js/pcs/PersonManage.js"></script>
		<script type="text/javascript" src="../../js/pcs/DepartmentSelect.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript">
			function loadInit(){
		    var deptname="<%=jgmc%>";
	    	$("SSJG").value = deptname;
	    	G_jgID='<%=jgid%>';	
	  	    }
 		      	    
	    </script>		
	</head>
	<body onload="loadInit();">
	<div style="padding-left: 10px;text-align: center;">
		<fieldset style="width:420px;height:540px;border:1px solid #CCCCCC" align="center">
			<legend style="border:1px;">
				<%=insrtOrUpdt.equals("0")?"人员信息新增":"人员信息编辑"%>
			</legend>
			<form  id="uploadform" name="uploadform" style="margin-top:2px;" method="post" action="pcs.personManage.saveFile.d"  enctype="multipart/form-data">
			  <table width="99%" align="center"   class="tableInput" id="dataTable">
                <tr>
                  <td><table width="99%">
                      <tr>
                        <td align="left"><table width="99%" align="left">
                            <tr>
                              <td width="30%" align="left">姓名：</td>
                              <td width="63%" align="left"><input name="text" type="text"  class="text" id="XM" maxlength="20"></td>
                              <td width="7%" class="RedFont"><p align="left">※</p></td>
                            </tr>
                            <tr>
                              <td align="left" >性别：</td>
                              <td id="PersonXB" align="left">
                                   <script language="javascript">
									 		fillListBox("PersonXB","XB","150","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='011005'","未选择","setValue('1');");
						  	        </script>                             
						  	  </td>
                            </tr>
                            <tr>
                              <td align="left" >身份证号：</td>
                              <td align="left"><input name="text22" type="text" class="text" id="SFZH" maxlength="18" onChange="setBirthdate(this,'CSRQ')";></td>
                              <td>&nbsp;</td>
                            </tr>
                             <tr>
                               <td width="23%"  align="left">出生日期：</td>
                               <td align="left" ><input name="text22" type="text" class="text" id="CSRQ" onClick="SelectDate(this,0);" readonly=true></td>
                               <td>&nbsp;</td>
                            </tr>
                            
                        </table></td>
                        <td width="33%" align="left" >
                         <table>
                            <tr>
                              <td><img id="pic3"  width="112" height="123"   onerror="this.src='../../image/error/noplate.jpg'" title="双击打开原图" onDblClick="showNormallyZP('pic3');"> </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                  </table></td>
                </tr> 
				<tr>
                  <td ><table width="99%">
                   <!-- <tr style="valign:center;align:left"> -->
                   <tr style="valign:center;align:left">
                      <td width="30%" align="right">人员照片：</td>
                      <td align="left"width="43%"><input id="RYIDForAdd"  name="RYIDForAdd" type="hidden" value="<%=RYID %>"/>
                          <input style="width:285px;" type="file"  id="fileObjId" name="fileObjId" href="#" onChange="upLoadPhoto('<%=RYID %>');" />                      </td>
                      <td width="14%">&nbsp;</td>
                    </tr>
                   <tr>
                       <td width="30%" align="right">警号：</td>
                       <td align="left"><input name="text2" type="text" style="width:285px;" class="text" id="JH" maxlength="50"></td>
                    </tr>
                      <tr>
                      <td width="30%" align="right">警衔：</td>
                      <td align="left" id="PersonJX"><script language="javascript">
							fillListBox("PersonJX","JX","285","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='013009'","未选择");
						</script></td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td align="right" width="30%" >所属单位：</td>
                      <td align="left">
                      <table width="100%">
                          <tr>
                            <td width="75%" align="left"><input name="text22"style="width:263px;" type="text" class="text" id="SSJG" onClick="reset_dept('SSJG');" readonly="true"></td>
                            <td width="25%" align="left"><div align="left"><img src="../../image/popup/btnselect.gif" alt="选择单位" style="cursor:hand;" onClick="showDepartTree('<%=ccbm %>','<%=jgid %>','<%=jgmc %>','SSJG','40','110')"></div></td>
                          </tr>
                      </table></td>
                       <td width="7%" class="RedFont"><p align="left">※</p></td>
                    </tr>
                    <tr>
                      <td align="right">现任职务：</td>
                      <td align="left" id="RYXRZW"><script language="javascript">
							fillListBox("RYXRZW","XRZW","285","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='013011'","未选择","doQuery('<%=RYID %>');");
						</script></td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td align="right">编制类型：</td>
                      <td align="left" id="RYBZLX"><script language="javascript">
							fillListBox("RYBZLX","BZLX","285","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='013012'","未选择");
						</script>                      </td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td align="right">特勤级别：</td>
                      <td colspan="2"align="left"><table width="100%">
                          <tr>
                            <td align="left" id="SELECTTQJB"><script language="javascript">
							fillListBox("SELECTTQJB","TQJB","200","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='013018'","未选择","doQuery('<%=RYID %>');");
						</script></td>
                            <td align="left"><div align="left">（A~E从高到低）</div></td>
                          </tr>
                      </table> </td>
			        </tr>
			        <tr>
                        <td width="30%" align="right">手机：</td>
                        <td align="left"><input name="text2" type="text" style="width:285px;"class="text" id="SJ" maxlength="11"></td>
                        <td width="7%" class="RedFont"><p align="left">※</p></td>
                    </tr>
                    <tr>
                      <td width="30%"  align="right">办公电话：</td>
                      <td align="left"><input name="text22" type="text" style="width:285px;"class="text" id="BGDH" maxlength="13"></td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td width="30%"  align="right">家庭电话：</td>
                      <td  align="left" ><input name="text22" type="text" style="width:285px;"class="text" id="JTDH" maxlength="13"></td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr style="">
                      <td width="30%" align="right">手台：</td>
                      <td align="left"><input name="text22" type="text" style="width:285px;"class="text" id="ST" maxlength="30"></td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr style="">
                      <td width="30%"  align="right">手台呼/代号：</td>
                      <td align="left"><input name="text22" type="text" style="width:285px;"class="text" id="STHH" maxlength="30"></td>
                      <td>&nbsp;</td>
                    </tr>
                    <tr>
                      <td width="30%"  align="right">手台组号：</td>
                      <td align="left"><input name="text22" type="text" style="width:285px;"class="text" id="STZH" maxlength="30"></td>
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
                </tr>
                <tr>
                  <td class="tdRight" colspan="4"><img src="../../image/button/btnsave.gif" style="cursor:hand" onClick="modify(<%=insrtOrUpdt %>,'<%=RYID %>');">
                      <%if(insrtOrUpdt.equals("0")) {%>
                      <input type="image" src="../../image/button/btnreset.gif" name="button" onClick="reset();" />
                      <%} %>
                      <img src="../../image/button/btnclose.gif" style="cursor:hand" onClick="closeThis();"> </td>
                </tr>
              </table>
			</form>		  
		</fieldset>
		</div>
	</body>
</html>
