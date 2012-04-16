<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	             <tr id="">
	                <td align="right" class="tdtitle">到警时间：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="firedjsj" name="feedBackForInit" onClick="SelectDateTime(this)" readonly></td>
					
					
					<td align="right" class="tdtitle"><font id="fireajjssjFont">事件结束时间：</font> </td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="fireajjssj" name="feedBackForInit" onClick="SelectDateTime(this)" readonly></td>
					
					
	             </tr>
				<tr id="descForCase">
					<td align="right" class="tdtitle">影响道路：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" name="feedBackForInit" id="yxdl_FireAndBlast"></td>
					<td align="right" class="tdtitle">经济损失：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="jjss_FireAndBlast" name="feedBackForInit" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this,'jjss');"></td>
				</tr>
				<tr style="display: inline" id="personForCase1">
					<td align="right" class="tdtitle">轻伤人数：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="qsrs_FireAndBlast" name="feedBackForInit" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
					<td align="right" class="tdtitle">重伤人数：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="zsrs_FireAndBlast" name="feedBackForInit" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
					<td align="right" class="tdtitle">死亡人数：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="swrs_FireAndBlast" name="feedBackForInit" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
				</tr>
				<!--  
				<tr>  
					<td align="center" valign="bottom" colspan="6">
						<input type="button" class="" value="出警反馈" id="cjbtn_FireAndBlast" name="feedBackForOut_btn" onclick="modifyFireAndBlast('cj');" style="width:70">&nbsp;
						<input type="button" class="" value="现场反馈" id="ddxcbtn_FireAndBlast" name="feedBackForArray_btn" onclick="modifyFireAndBlast('ddxc');" style="width:70">&nbsp;
						<input type="button" class="" value="事件结束反馈" id="clwbbtn_FireAndBlast" name="feedBackForFinish_btn" onclick="modifyFireAndBlast('clwb');" style="width:70">&nbsp;
					</td>
				</tr>
				-->
				