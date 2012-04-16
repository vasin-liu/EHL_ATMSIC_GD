<%@ page language="java" pageEncoding="UTF-8" session="true"
	buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<%@page import="com.ehl.base.table.*"%>
<%@ include file="../../../base/Message.oni"%>
<html>
  <head>
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="this is my page">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="../../../base/css/style2/Global.css" >
	<link rel="STYLESHEET" type="text/css" href="../../../base/css/style2/dhtmlx/dhtmlXGrid.css">
	<link rel="STYLESHEET" type="text/css" href="../../../base/css/style2/dhtmlx/dhtmlXGrid_skins.css">
	<script src="../../../base/js/dhtmlx/dhtmlXGrid.js"></script>
	<script src="../../../base/js/dhtmlx/dhtmlXGridCell.js"></script>
	<script src="../../../base/js/dhtmlx/dhtmlXGrid_selection.js"></script>
	<script src="../../../base/js/prototype.js"></script>
  </head>
<body>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="82%" align="left" valign="top">
    <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
      <tr>
        <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="17"><img src="../../image/back01_01.gif" width="24" height="31" /></td>
            <td background="../../image/back01_05.gif"><strong>工作区</strong></td>
            <td width="22"><img src="../../image/back01_03.gif" width="22" height="31" /></td>
          </tr>
        </table></td>
      </tr>
      <tr>
        <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="15" background="../../image/back02_01.gif">&nbsp;</td>
            <td>
       	<%
       	
			String tableScript = PageTableUtil.doDataWinGenerate("02");
	 	%>
		<%=tableScript %>
			</td>
            <td width="14" background="../../image/back02_03.gif">&nbsp;</td>
          </tr>
        </table>
        </td>
      </tr>
      <tr>
        <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="23"><img src="../../image/back03_01.gif" width="23" height="24" /></td>
            <td background="../../image/back003_03.gif">&nbsp;</td>
            <td width="22"><img src="../../image/back03_03.gif" width="22" height="24" /></td>
          </tr>
        </table>
        </td>
      </tr>
    </table>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>
