<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	             <tr id="ExceptionCar">
	                <td align="right" class="tdtitle">到警时间：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="exceptioncardjsj" name="feedBackForInit" onClick="SelectDateTime(this)" readonly></td>
					<td align="right" class="tdtitle"><font id="exceptioncarajjssjFont">事件结束时间：</font></td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="exceptioncarajjssj" name="feedBackForInit" onClick="SelectDateTime(this)" readonly></td>
	             </tr>
				<tr id="ExceptionCar">
					<td align="right" class="tdtitle">司机姓名：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="sjxm_Exception" name="feedBackForInit" onkeydown="keyDown();"></td>
					<td align="right" class="tdtitle">故障车单位：</td>
					<td align="left" class="tdvalue"><input type="text" style="width:120" id="gzcdw_Exception" name="feedBackForInit" onkeydown="keyDown();"></td>
				</tr>
				<!--  
				<tr>  
					<td align="center" valign="bottom" colspan="6">
						<input type="button" class="" value="出警反馈" id="cjbtn_ExceptionCar" name="feedBackForOut_btn" onclick="modifyExceptionCar('cj');" style="width:70">&nbsp;
						<input type="button" class="" value="现场反馈" id="ddxcbtn_ExceptionCar" name="feedBackForArray_btn" onclick="modifyExceptionCar('ddxc');" style="width:70">&nbsp;
						<input type="button" class="" value="事件结束反馈" id="clwbbtn_ExceptionCar" name="feedBackForFinish_btn" onclick="modifyExceptionCar('clwb');" style="width:70">&nbsp;
					</td>
				</tr>
				-->
