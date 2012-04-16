<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tr>
	<td class="tdtitle">
		故障地点：
	</td>
	<td class="tdvalue">
		<input type="text" id="exceptionCarAddress" name="editinput" class="textwidth">
	</td>
	<td class="tdtitle">
		故障原因：
	</td>
	<td class="tdvalue">
		<input type="text" id="exceptionCause" name="editinput" class="textwidth">
	</td>
	<td class="tdtitle">
		车 型：
	</td>
	<td class="tdvalue" id="exceptioCarShapeTd">
		<script language="javascript">
            fillListBox("exceptioCarShapeTd","exceptioCarShape","111","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='011008'","请选择");
        </script>
		
	</td>
</tr>
<tr>
	
	<td class="tdtitle">
		影响程度：
	</td>
	<td class="tdvalue">
		<input type="text" id="exceptioAffectLevel" name="editinput" class="textwidth">
	</td>
	<td class="tdtitle">
		备注：
	</td>
	<td class="tdvalue" colspan="3">
		<input type="text" id="exceptionRemark" name="editinput" class="textwidth">
	</td>
</tr>