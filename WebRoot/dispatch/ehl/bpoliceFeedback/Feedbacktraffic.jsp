<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	        
	             <tr>
	                <td align="right" class="tdtitle" style="width: 17%">到警时间：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="djsj" name="feedBackForInit" onClick="SelectDateTime(this)" readonly></td>
					
					
					<td align="right" class="tdtitle"  style="width: 17%" ><font id="ajjssjFont">事件结束时间：</font></td>
					<td align="left" class="tdvalue" colspan="3"><input type="text" style="width:120" id="ajjssj" name="feedBackForInit" onClick="SelectDateTime(this)" readonly></td>
					
					
	             </tr>
	             
				 <tr id="descForCase">
					<td align="right" class="tdtitle">案发地址：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="alarmState_feedBack" name="feedBackForInit" onkeydown="keyDown();"></td>
					<td align="right" class="tdtitle">事件等级：</td>
					<td align="left" class="tdvalue" id="sgdj_td">
					
			  	</td>
					<td align="right" class="tdtitle" style="width: 13%">经济损失：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="jjss" name="feedBackForInit" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this,'jjss');"></td>
					
				</tr>
				<tr style="display: inline" id="personForCase1">
					<td align="right" class="tdtitle">轻伤人数：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="qsrs" name="feedBackForInit" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
					<td align="right" class="tdtitle">重伤人数：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="zsrs" name="feedBackForInit" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
					<td align="right" class="tdtitle">死亡人数：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="swrs" name="feedBackForInit" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
					
				</tr>
				<tr style="display: inline" id="personForCase2">
					<td align="right" class="tdtitle">涉案人数：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="sars" name="feedBackForInit" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
					<td align="right" class="tdtitle">抓获人数：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="zhrs" name="feedBackForInit" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
					<td align="right" class="tdtitle">救助人数：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="jzrs" name="feedBackForInit" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
					
				</tr>
				<tr style="display: inline" id="isOrNoCheck"> 
					<td align="right" class="tdtitle">破获刑事案件：</td>
					<td align="left" class="tdvalue"><input type="checkbox" id="phxsaj" name="feedBackForInit_box" ></td>
					<td align="right" class="tdtitle">查处治安案件：</td>
					<td align="left" class="tdvalue"><input type="checkbox" id="cczaaj" name="feedBackForInit_box" ></td>
					<td align="right" class="tdtitle">解决纠纷：</td>
					<td align="left" class="tdvalue"><input type="checkbox" id="jjjf" name="feedBackForInit_box" ></td>
				</tr>
				<!--
				  <tr>  
					<td align="center" valign="bottom" colspan="6">
					  <input type="button" class="" value="出警反馈" id="cjbtn" name="feedBackForOut_btn" onclick="modifyAccident('cj');" style="width:70">&nbsp;
						<input type="button" class="" value="现场反馈" id="ddxcbtn" name="feedBackForArray_btn" onclick="modifyAccident('ddxc');" style="width:70">&nbsp;
					    <input type="button" class="" value="事件结束反馈" id="clwbbtn" name="feedBackForFinish_btn" onclick="modifyAccident('clwb');" style="width:70">&nbsp;
					</td>
				</tr>
				-->
