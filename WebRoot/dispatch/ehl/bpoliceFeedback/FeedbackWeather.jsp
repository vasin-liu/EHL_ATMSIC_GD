<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
				<tr id="">
	                <td align="right" class="tdtitle">到警时间：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="weatherdjsj" name="feedBackForInit" onClick="SelectDateTime(this)" readonly></td>
					
					
					<td align="right" class="tdtitle"><font id="weatherajjssjFont">事件结束时间：</font></td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="weatherajjssj" name="feedBackForInit" onClick="SelectDateTime(this)" readonly></td>
					
					
	             </tr>
				<tr id="noTraffic"> 
					<td align="right" class="tdtitle">路面情况：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" name="feedBackForInit" id="lmqk_Weather"></td>
					<td align="right" class="tdtitle">影响道路：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" name="feedBackForInit" id="yxdl_Weather"></td>
				</tr>
				<!--  
				<tr>  
					<td align="center" valign="bottom" colspan="6">
						<input type="button" class="" value="出警反馈" id="cjbtn_Weather" name="feedBackForOut_btn" onclick="modifyWeather('cj');" style="width:70">&nbsp;
						<input type="button" class="" value="现场反馈" id="ddxcbtn_Weather" name="feedBackForArray_btn" onclick="modifyWeather('ddxc');" style="width:70">&nbsp;
						<input type="button" class="" value="事件结束反馈" id="clwbbtn_Weather" name="feedBackForFinish_btn" onclick="modifyWeather('clwb');" style="width:90">&nbsp;
					</td>
				</tr>
				-->
