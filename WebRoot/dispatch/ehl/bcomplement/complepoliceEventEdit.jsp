<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tr>
	<td class="tdtitle">
		发生时间：

	</td>
	<td class="tdvalue">
		<input type="text" id="complePoliceEventTime" name="compleeditinput" class="textwidth" onclick="SelectDateTime(this)" readonly>
	</td>
	<td class="tdtitle">
		影响交通程度：
	</td>
	<td class="tdvalue">
		<input type="text" id="complePoliceEventAffectTrafficLevel" name="compleeditinput" class="textwidth">
	</td>
	<td class="tdtitle">
		影响道路：

	</td>
	<td class="tdvalue">
		<input type="text" id="complePoliceEventAffectRoad" name="compleeditinput" class="textwidth">
	</td>
	
</tr>
<tr>
	<td class="tdtitle">
		波及范围：

	</td>
	<td class="tdvalue">
		<input type="text" id="complePoliceEventAffectextent" name="compleeditinput" class="textwidth">
	</td>
	<td class="tdtitle">
		备注：

	</td>
	<td class="tdvalue" colspan="3">
		<input type="text" id="complePoliceEventRemark" name="compleeditinput" class="textwidth">
	</td>
	
</tr>