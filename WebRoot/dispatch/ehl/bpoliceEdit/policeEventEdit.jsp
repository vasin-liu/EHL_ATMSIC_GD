<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tr>
	<td class="tdtitle">
		发生时间：
	</td>
	<td class="tdvalue">
		<input type="text" id="PoliceEventTime" name="editinput" class="textwidth" onclick="SelectDateTime(this)" readonly>
	</td>
	<td class="tdtitle">
		影响交通程度：
	</td>
	<td class="tdvalue">
		<input type="text" id="PoliceEventAffectTrafficLevel" name="editinput" class="textwidth">
	</td>
	<td class="tdtitle">
		影响道路：
	</td>
	<td class="tdvalue">
		<input type="text" id="PoliceEventAffectRoad" name="editinput" class="textwidth">
	</td>
	
</tr>
<tr>
	<td class="tdtitle">
		备注：
	</td>
	<td class="tdvalue" colspan="5">
		<input type="text" id="PoliceEventRemark" name="editinput" class="textwidth">
	</td>
	
</tr>