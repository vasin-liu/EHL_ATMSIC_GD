<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<tr>
	<td class="tdtitle">
		故障地点：


	</td>
	<td class="tdvalue">
		<input type="text" id="compleexceptionCarAddress" name="compleeditinput" class="textwidth">
	</td>
	<td class="tdtitle">
		故障原因：


	</td>
	<td class="tdvalue">
		<input type="text" id="compleexceptionCause" name="compleeditinput" class="textwidth">
	</td>
	<td class="tdtitle">
		车 型：
	</td>
	<td class="tdvalue" id="compleexceptioCarShapeTd">
		<script language="javascript">
            fillListBox("compleexceptioCarShapeTd","compleexceptioCarShape","111","SELECT dm,dmsm FROM t_sys_code WHERE dmlb='011008'","请选择");
        </script>
		
	</td>
</tr>
<tr>
	
	<td class="tdtitle">
		影响程度：


	</td>
	<td class="tdvalue">
		<input type="text" id="compleexceptioAffectLevel" name="compleeditinput" class="textwidth">
	</td>
	<td class="tdtitle">
		司机姓名：


	</td>
	<td class="tdvalue">
		<input type="text" id="compleexceptionDriver" name="compleeditinput" class="textwidth">
	</td>
	<td class="tdtitle">
		故障车单位：

	</td>
	<td class="tdvalue">
		<input type="text" id="compleexceptionCarDept" name="compleeditinput" class="textwidth">
	</td>
	</tr>
	<tr>
		<td class="tdtitle">
			备注：

	
		</td>
		<td class="tdvalue" colspan="5">
			<input type="text" id="compleexceptionRemark" name="compleeditinput" class="textwidth">
		</td>
	</tr>
