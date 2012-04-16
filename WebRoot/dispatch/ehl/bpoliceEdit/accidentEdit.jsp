<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

		<tr>
			<td class="tdtitle">
					车辆号牌种类：
			</td>
			<td class="tdvalue" id="accidentAlarmCarTypeTd">
				<!--<input type="text" id="accidentAlarmCarType" class="textwidth">-->
				
				<script language="javascript">
                      fillListBox("accidentAlarmCarTypeTd","accidentAlarmCarType","111","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='011001'","请选择");
                   </script>		
			</td>
			<td class="tdtitle">
				号牌号码：
			</td>
			<td class="tdvalue" id="">
				<font id="accidentAlarmCarCodeTd">
				<script language="javascript">
                      fillListBox("accidentAlarmCarCodeTd","accidentAlarmCarCodeDept","56","select distinct substr(dm,1,1),substr(dm,1,1) from t_sys_code where dmlb='011006'","");
                   </script>	
				</font>
				<input type="text" style="width:55" id="accidentAlarmCarCode" name="editinput" class="textwidth">
			</td>
			<td class="tdtitle">
				车辆类型：
			</td>
			<td class="tdvalue" id="accidentAlarmCarGenreTd">
				<!--<input type="text" id="accidentAlarmCarGenre" class="textwidth">-->
				<script language="javascript">
                      fillListBox("accidentAlarmCarGenreTd","accidentAlarmCarGenre","111","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='011008'","请选择");
                   </script>	
			</td>
		</tr>
		
		
		<tr>
			<td class="tdtitle">
					影响程度：
			</td>
			<td class="tdvalue" id="" colspan="">
				
				<input type="text" id="accidentAffectLevel" name="editinput" class="textwidth">
			</td>
			<td class="tdtitle">
					备注：
			</td>
			<td class="tdvalue" id="" colspan="3">
				
				<input type="text" id="accidentremark" name="editinput" class="textwidth">
			</td>
				
		</tr>
		
