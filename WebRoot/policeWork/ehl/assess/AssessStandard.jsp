<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.ehl.dispatch.common.FlowUtil"%>
<%@ include file="../../../base/Message.oni"%>
<%
String title = FlowUtil.getFuncText("880901");	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title><%=title %></title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="../../../base/css/style1/tab.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/form.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/font.css" rel="stylesheet" type="text/css" />
		<link href="../../../base/css/style1/link.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../js/assess/assessStandard.js"></script>
	    <style type="text/css">
			{
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
				border-bottom: 1 solid black;
				width:60px;
				text-align:center;
				ime-mode:disabled;
			}
			tr{
				line-height:18px;
			}
			/*宽高行高背景不显示超过对象尺寸的内容*/
			.lsearch{
			     width:82px; 
				 height:22px; 
				 line-height:22px; 
				 background:url(../../../dispatch/images/dispatch/btn.png) no-repeat; 
				 overflow:hidden;
			}
			/*块对象高度固定背景字体无装饰行高不显示超过对象尺寸的内容*/
			.lsearch a{ 
			     display:block; 
				 height:22px; 
				 background:url(../../../dispatch/images/dispatch/btn.png) center; 
				 text-decoration:none; 
				 line-height:22px;
				 overflow:hidden;
			}
			/*高度固定背景行高*/
			.lsearch a:hover{ 
			     height:22px; 
				 background:url(../../../dispatch/images/dispatch/btn.png) center center; 
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
	<body onload="initAssessStandardInfo()" >
		<fieldset style="width:100%;height:100%;border:1px solid #CCCCCC" align="center">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr class="wTableTopCenter">
						<td width="5%" >
							<div align="center">
								<img src="../../../base/image/cssimg/table/tb.gif" width="16" height="16" alt="img" />
							</div>
						</td>
						<td width="70%" class="currentLocation">
							<span class="currentLocationBold">您当前的位置</span>：<%=title %>
						</td>
					</tr>
				</table>
            <div style="color:red;font-size:12px;padding-left:0px;display: none">&nbsp;&nbsp;◆ 表中标记 ※ 部分为各个支队、大队报送的一条该项目信息所评分值。</div>
            <table width="99%" align="center" class="tableInput" id="dataTable">
                <tr style="display:none">
                    <td>评估基准设定</td>
                    <td colspan="7">
                        <input id="bh" type="text" maxLength="32" name="lkid" class="text" style="width:280"></input>
                        <input id="insrtOrUpdt"  type="hidden"  />
                    </td>
                </tr>
          
                <tr>
                    <th width="6%" class="td_r_b">序号</th>
                    <th width="30%" class="td_r_b">评分项目</th>
                    <th width="10%" class="td_r_b">分值</th>
                </tr>
                <tr>
                    <td class="td_r_b">1</td>
                    <td class="td_r_b1">及时报送重特大交通事故信息（加分）<span style="color:red;font-size:11px;display: none;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" id="acc_001024"  class="input1" type="text" />分</td>
                </tr>
                <tr>
                    <td class="td_r_b">2</td>
                    <td class="td_r_b1">及时报送交通拥堵信息（加分）<span style="color:red;font-size:11px;display: none;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" id="crowd_001002"   class="input1" type="text" />分</td>
                </tr>
                <tr>
                    <td class="td_r_b">3</td>
                    <td class="td_r_b1">及时报送施工占道信息（加分）<span style="color:red;font-size:11px;display: none;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" id="build_001023" class="input1" type="text" />分</td>
                </tr>
                <tr>
                    <td class="td_r_b">4</td>
                    <td class="td_r_b1">报送信息被省厅采（加分）<span style="color:red;font-size:11px;display: none;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" id="news_001004"  class="input1" type="text" />分</td>
                </tr>
                <tr>
                    <td class="td_r_b">5</td>
                    <td class="td_r_b1">报送信息被部局采用（加分）<span style="color:red;font-size:11px;display: none;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" id="news_001006"  class="input1" type="text" />分</td>
                </tr>
                <tr>
                    <td class="td_r_b">6</td>
                    <td class="td_r_b1">报送调研信息被省厅采用（加分）<span style="color:red;font-size:11px;display: none;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" id="news_001008"  class="input1" type="text" />分</td>
                </tr>
                 <tr>
                    <td class="td_r_b">7</td>
                    <td class="td_r_b1">报送调研信息被部局采用（加分）<span style="color:red;font-size:11px;display: none;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" id="news_001009"  class="input1" type="text" />分</td>
                </tr>
                <tr>
                    <td class="td_r_b">8</td>
                    <td class="td_r_b1">及时回应和处理群众或交通电台反映的拥堵报料信息（加分）<span style="color:red;font-size:11px;display: none;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" id="baoliao_001001"  class="input1" type="text" />分</td>
                </tr>
                <tr>
                    <td class="td_r_b">9</td>
                    <td class="td_r_b1">未准确回应和处理群众或交通电台反映的拥堵报料信息（扣分）<span style="color:red;font-size:11px;display: none;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" id="baoliao_001007"  class="input1" type="text" />分</td>
                </tr>
                <tr>
                    <td class="td_r_b">10</td>
                    <td class="td_r_b1">超时未回应和处理群众或交通电台反映的拥堵报料信息（扣分）<span style="color:red;font-size:11px;display: none;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" id="baoliao_001005"  class="input1" type="text" />分</td>
                </tr>
                <tr>
                    <td class="td_r_b">11</td>
                    <td class="td_r_b1">漏报、错报重特大交通事故、交通拥堵、施工占道信息（扣分）<span style="color:red;font-size:11px;display: none;">※</span></td>
                    <td class="td_r_b"><input maxLength="8" id="kou_001003"  class="input1" type="text" />分</td>
                </tr>
                <tr>
                	<td colspan="8" align="center">
                		<table >
                			<tr>
                				<td width="35%" >
			                		<span class="lsearch">
			                			<a href="#" onclick="doModifyAssessStandard()" class="currentLocation"> <span class="lbl">保存</span>
										</a>
			                		</span>
			                	</td>
			                	<td width="35%" >
			                		<span class="lsearch">
			                			<a href="#" onclick="resetValue();" class="currentLocation"> <span class="lbl">重置</span>
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
