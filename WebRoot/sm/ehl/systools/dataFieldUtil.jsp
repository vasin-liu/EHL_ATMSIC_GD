<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="../../../base/Message.oni"%>
<%
String insrtOrUpdt =request.getParameter("insrtOrUpdt");
String fid = request.getParameter("fid"); 
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>数据表格定义</title>
	<jsp:include page="../../../base/ShareInc.html" />
	<script src="../../../base/js/prototype.js"></script>
	<script src="../../../base/js/table/dataFieldUtil.js"></script>
	<script src="../../../base/js/dhtmlx/xmlCreator.js"></script>
</head> 
 <body onload ="doQuery(<%=fid %>);">
 <div style="padding-left: 15px;text-align: center">
 <fieldset style="width:360;height:350;border:1px solid #CCCCCC" align="center" >
<br>
<legend style="border:0px;"><%=insrtOrUpdt.equals("0")?"数据字段新增":"数据字段编辑"%> <br></legend>
 <table width="100%" align="center" id="dataTable">
   <tr>
     <td width="43%"><div align="right">
       <msg:Common_zh.dataFieldUtil.columnid.desc>
     </div></td>
     <td width="57%"><input name="fid" type="text" id="fid" atrbt="fid" value=<%=fid %> size="18"></td>
   </tr>
   <tr>
     <td><div align="right">
       <msg:Common_zh.dataFieldUtil.tableid.desc>
     </div></td>
     <td><input name="tid" type="text" id='tid' atrbt="tid" size="18" /></td>
   </tr>
   <tr>
     <td><div align="right">
       <msg:Common_zh.dataFieldUtil.fieldname.desc>
     </div></td>
     <td><input name="fldname" type="text" id="fldname" atrbt="fldname" size="18" /></td>
   </tr>
   <tr>
     <td><div align="right">
       <msg:Common_zh.dataFieldUtil.fielddesc.desc>
     </div></td>
     <td><input name="displayname" type="text" id="displayname" atrbt="displayname" size="18" /></td>
   </tr>
   <tr>
     <td><div align="right">
       <msg:Common_zh.dataFieldUtil.columnwidth.desc>
     </div></td>
     <td><input name="width" type="text" id="width"  atrbt="width" size="18" /></td>
     <!-- td width="21%"><select name="widthed" class='select' id="widthed" />
           <option value="px"><msg:Common_zh.dataFieldUtil.realdata.desc></option>
           <option value="pt"><msg:Common_zh.dataFieldUtil.percentdata.desc></option>
     </td-->
   </tr>
   <tr>
     <td><div align="right">
       <msg:Common_zh.dataFieldUtil.disptype.desc>
     </div></td>
     <td><input name="type" type="text" id="type" atrbt="type" size="18" /></td>
   </tr>
   <tr>
     <td><div align="right">
       <msg:Common_zh.dataFieldUtil.alignmode.desc>
     </div></td>
     <td><input name="align" type="text" id="align" atrbt="align" size="18" /></td>
   </tr>
   <tr>
     <td><div align="right">
       <msg:Common_zh.dataFieldUtil.datatype.desc>
     </div></td>
     <td><input name="class" type="text" id="class" atrbt="class" size="18" /></td>
   </tr>
   <tr>
     <td><div align="right">
       <msg:Common_zh.dataFieldUtil.codemap.desc>
     </div></td>
     <td><input name="mapname" type="text" id="mapname" atrbt="mapname" size="18"/></td>
   </tr>
   <tr>
     <td  colspan="2"><br><div align="center"> <img src="../../image/button/btnsave.gif" style="cursor:hand" onClick="modify(<%=insrtOrUpdt %>);"> <img src="../../image/button/btnreset.gif" style="cursor:hand" onClick="reset();"> <img src="../../image/button/btnclose.gif" style="cursor:hand" onClick="window.close();"> </div></td>
 </table>
 </fieldset>
 </div>
  </body>
</html>
