<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

		<tr>
			<td class="tdtitle">
					报警车号牌种类：

			</td>
			<td class="tdvalue" id="compleaccidentAlarmCarTypeTd">
				<!--<input type="text" id="accidentAlarmCarType" class="textwidth">-->
				
				<script language="javascript">
                      fillListBox("compleaccidentAlarmCarTypeTd","compleaccidentAlarmCarType","111","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='011001'","请选择");
                 </script>		
			</td>
			<td class="tdtitle">
				号牌号码：

			</td>
			<td class="tdvalue" id="">
				<font id="compleaccidentAlarmCarCodeTd">
				<script language="javascript">
                      fillListBox("compleaccidentAlarmCarCodeTd","compleaccidentAlarmCarCodeDept","56","select distinct substr(dm,1,1),substr(dm,1,1) from t_sys_code where dmlb='011006'","");
                   </script>	
				</font>
				<input type="text" style="width:55" id="compleaccidentAlarmCarCode" name="compleeditinput" class="textwidth">
			</td>
			<td class="tdtitle">
				车辆类型：

			</td>
			<td class="tdvalue" id="compleaccidentAlarmCarGenreTd">
				<!--<input type="text" id="accidentAlarmCarGenre" class="textwidth">-->
				<script language="javascript">
                   fillListBox("compleaccidentAlarmCarGenreTd","compleaccidentAlarmCarGenre","111","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='011008'","请选择");
                </script>	
			</td>
		</tr>
		
		<tr style="display: inline" id="personForCase1">
			<td align="right" class="tdtitle">轻伤人数：</td>
			<td align="left" class="tdvalue"><input type="text" class="textwidth" id="compleqsrs" name="compleeditinput" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
			<td align="right" class="tdtitle">重伤人数：</td>
			<td align="left" class="tdvalue"><input type="text" class="textwidth" id="complezsrs" name="compleeditinput" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
			<td align="right" class="tdtitle">死亡人数：</td>
			<td align="left" class="tdvalue"><input type="text" class="textwidth" id="compleswrs" name="compleeditinput" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
		</tr>
		<tr style="display: inline" id="personForCase2">
			<td align="right" class="tdtitle">涉案人数：</td>
			<td align="left" class="tdvalue"><input type="text" class="textwidth" id="complesars" name="compleeditinput" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
			<td align="right" class="tdtitle">抓获人数：</td>
			<td align="left" class="tdvalue"><input type="text" class="textwidth" id="complezhrs" name="compleeditinput" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
			<td align="right" class="tdtitle">救助人数：</td>
			<td align="left" class="tdvalue"><input type="text" class="textwidth" id="complejzrs" name="compleeditinput" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this);"></td>
		</tr>
		
		
		<tr>
			<td class="tdtitle">
					影响程度：

			</td>
			<td class="tdvalue" id="" colspan="">
				
				<input type="text" id="compleaccidentAffectLevel" name="compleeditinput" class="textwidth">
			</td>
			<td align="right" class="tdtitle">经济损失：</td>
			<td align="left" class="tdvalue"><input type="text" class="textwidth" id="complejjss" name="compleeditinput" onkeydown="keyDown();" onKeyPress="keyPress()" onblur="isValidate(this,'jjss');"></td>
			<td align="right" class="tdtitle"></td>
			<td align="left" class="tdvalue"></td>		
		</tr>
		
		
		<tr style="display: inline" id="isOrNoCheck"> 
			<td align="right" class="tdtitle">破获刑事案件：</td>
			<td align="left" class="tdvalue"><input type="checkbox" id="complephxsaj" name="compleeditinput_box" ></td>
			<td align="right" class="tdtitle">查处治安案件：</td>
			<td align="left" class="tdvalue"><input type="checkbox" id="complecczaaj" name="compleeditinput_box" ></td>
			<td align="right" class="tdtitle">解决纠纷：</td>
			<td align="left" class="tdvalue"><input type="checkbox" id="complejjjf" name="compleeditinput_box" ></td>
		</tr>
		<tr>
			<td class="tdtitle">
					备注：

			</td>
			<td class="tdvalue" id="" colspan="5">
				
				<input type="text" id="compleaccidentremark" name="compleeditinput" class="textwidth">
			</td>
		</tr>
		
