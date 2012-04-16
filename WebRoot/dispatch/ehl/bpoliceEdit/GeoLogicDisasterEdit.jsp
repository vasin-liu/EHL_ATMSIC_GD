<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tr>
	<td class="tdtitle">
		发生时间：
	</td>
	<td class="tdvalue">
		<input type="text" id="GeoLogicDisasterTime" name="editinput"  onclick="SelectDateTime(this)" readonly
			class="textwidth" >
	</td>
	<td class="tdtitle">
		影响程度：

	</td>
	<td class="tdvalue">
		<input type="text" id="GeoLogicDisasterLevel" name="editinput"
			class="textwidth">
	</td>
	<td class="tdtitle">
		波及范围：

	</td>
	<td class="tdvalue">
		<input type="text" id="GeoLogicDisasterAffectExent" name="editinput"
			class="textwidth">
	</td>
</tr>
<tr>
	<td class="tdtitle">
		报警人：
	</td>
	<td class="tdvalue">
		<input type="text" id="GeoLogicDisasterAlarmPerson" name="editinput"
			class="textwidth" >
	</td>
	<td class="tdtitle" >
		备注：

	</td>
	<td class="tdvalue" colspan="3">
		<input type="text" id="GeoLogicDisasterRemark" name="editinput" class="textwidth" >
	</td>
	
</tr>