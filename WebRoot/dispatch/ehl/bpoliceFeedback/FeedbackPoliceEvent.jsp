<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	            <tr id="">
	                <td align="right" class="tdtitle">到警时间：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="policedjsj" name="feedBackForInit" onClick="SelectDateTime(this)" readonly></td>
					<td align="right" class="tdtitle"><font id="policeajjssjFont">事件结束时间：</font></td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="policeajjssj" name="feedBackForInit" onClick="SelectDateTime(this)" readonly></td>
	             </tr>
				<tr style="display: inline">
					<td align="right" class="tdtitle">影响道路：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" name="feedBackForInit" id="yxdl_PoliceEvent"></td>
					<td align="right" class="tdtitle">波及范围：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" name="feedBackForInit" id="bjfw_PoliceEvent"></td>
				</tr>
				<!--  <tr>  
					<td align="center" valign="bottom" colspan="6">
						<input type="button" class="" value="出警反馈" id="cjbtn_PoliceEvent" name="feedBackForOut_btn" onclick="modifyPoliceEvent('cj');" style="width:70">&nbsp;
						<input type="button" class="" value="现场反馈" id="ddxcbtn_PoliceEvent" name="feedBackForArray_btn" onclick="modifyPoliceEvent('ddxc');" style="width:70">&nbsp;
						<input type="button" class="" value="事件结束反馈" id="clwbbtn_PoliceEvent" name="feedBackForFinish_btn" onclick="modifyPoliceEvent('clwb');" style="width:70">&nbsp;
					</td>
				</tr>
				-->
