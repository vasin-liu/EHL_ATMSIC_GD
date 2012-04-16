<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../Message.oni"%>
<%
	String insrtOrUpdt = request.getParameter("insrtOrUpdt") == null ? "" : request.getParameter("insrtOrUpdt");
	String noticeId = request.getParameter("noticeId")== null ? "" : request.getParameter("noticeId");
	System.out.println(insrtOrUpdt+":"+noticeId);
%>
<html>
	<head>
		<title>通知管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../../../base/css/style1/tab.css" rel="stylesheet"  type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet"	type="text/css" />
		<link rel="stylesheet" type="text/css" href="../../css/Global.css">
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../../base/js/global.js"></script>
		<script type="text/javascript" src="../../../sm/js/common/list/FillListBox.js"></script>
		<SCRIPT type="text/javascript" src="../../js/ceditpolice/notice.js"></SCRIPT>
	    <script type="text/javascript">
	        var username = '<%=pname%>';
        </script>
	    <style type="text/css">
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
        </style>
	</head>
	<body bgcolor="#FFFFFF" onLoad="doQuery('<%=noticeId%>');">
		<fieldset style="width:95%;height:95%;border:1px solid #a5d1ec" align="center">
			<legend style="border:0px;font-size:12px;font-weight:600">
				<%="".equals(noticeId)?"通知新增":"通知签收"%>
			</legend>
            <table width="95%" align="center" class="table2" id="dataTable">
                <tr>
                	<td class="tdtitle" width="10%">
                		通知单号
                	</td>
                	<td class="tdvalue" width="10%">
                		<input id="NOTICEID" type="text" class="text" value="<%=noticeId %>" readonly="true"></input>
                	</td>
                	<td class="tdtitle" width="10%">
                		报送时间
                	</td>
                	<td class="tdvalue" width="10%">
                		<input id="SENDTIME" type="text" class="text" readonly="true"></input>
                	</td>
                </tr>
                <tr>
                	<td class="tdtitle" width="10%">
                		报送单位
                	</td>
                	<td class="tdvalue" width="50%">
                		<input id="SENDUNIT" type="text" class="text" readonly="true"></input>
                	</td>
                	<td class="tdtitle" width="10%">
                		报送人
                	</td>
                	<td class="tdvalue" width="10%">
                		<input id="SENDPEOPLE" type="text" class="text" readonly="true"></input>
                	</td>
                </tr>
                <tr>
                	<td class="tdtitle" width="10%">
                		接收单位
                	</td>
                	<td class="tdvalue" width="50%">
                		<input id="RECEIVEUNIT" type="text" class="text" readonly="true"></input>
                	</td>
                	<td class="tdtitle" width="10%">
                		接收人
                	</td>
                	<td class="tdvalue" width="10%">
                		<input id="RECEIVEPEOPLE" type="text" class="text" readonly="true"></input>
                	</td>
                </tr>
                <tr>
                	<td class="tdtitle">
                		领导批示
                	</td>
                	<td colspan="3" class="tdvalue">
                		<input id="INSTRUCTIONS" type="text" class="text" readonly="true"></input>
                	</td>
                </tr>
                <tr>
                	<td class="tdtitle">
                		通知内容
                	</td>
                	<td colspan="3" class="tdvalue">
                		<textarea rows="3" id="NOTICECONTENT" class="textwidth" ></textarea>
                	</td>
                </tr>
                <tr>
                	<td colspan="4" align="center" class="tdvalue">
                		<table width="100%">
                			<tr>
                				<%
								if (insrtOrUpdt.equals("0")) {
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
								%>
			                	<td width="34%" align="center">
			                		<span class="lsearch">
			                			<a href="#" onclick="printWord();"
											class="currentLocation"> <span class="lbl">打印</span>
										</a>
			                		</span>
			                		<span class="lsearch">
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