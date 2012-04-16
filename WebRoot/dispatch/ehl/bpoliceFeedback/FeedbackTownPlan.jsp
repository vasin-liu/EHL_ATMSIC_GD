<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	             <tr id="">
	                <td align="right" class="tdtitle">到警时间：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="townplandjsj" name="feedBackForInit" onClick="SelectDateTime(this)" readonly></td>
					<td align="right" class="tdtitle"><font id="townplanajjssjFont">事件结束时间：</font></td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="townplanajjssj" name="feedBackForInit" onClick="SelectDateTime(this)" readonly></td>
	             </tr>
				<tr id="noTraffic"> 
					<td align="right" class="tdtitle">波及范围：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="bjfw_TownPlan" name="feedBackForInit"></td>
					<td align="right" class="tdtitle">影响道路：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="yxdl_TownPlan" name="feedBackForInit"></td>
				</tr>
				
				<!--  <tr>  
					<td align="center" valign="bottom" colspan="6">
						<input type="button" class="" value="出警反馈" id="cjbtn_TownPlan" name="feedBackForOut_btn" onclick="modifyTownPlan('cj');" style="width:70">&nbsp;
						<input type="button" class="" value="现场反馈" id="ddxcbtn_TownPlan" name="feedBackForArray_btn" onclick="modifyTownPlan('ddxc');" style="width:70">&nbsp;
						<input type="button" class="" value="事件结束反馈" id="clwbbtn_TownPlan" name="feedBackForFinish_btn" onclick="modifyTownPlan('clwb');" style="width:70">&nbsp;
					</td>
				</tr>
				-->
