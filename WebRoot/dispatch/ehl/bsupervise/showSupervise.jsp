<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>督办信息</title>
		<script type="text/javascript" src="../../js/Supervise/supervise.js"></script>
		<STYLE  type="text/css">
			/*固定表头样式*/
			.vcasscrollColThead {
			    position: relative; 
			    top: expression
			    (this.parentElement.parentElement.parentElement.scrollTop);
			    z-index:2;
			    overflow: hidden; 
			    height:25px;
			    width:100%
			    text-overflow:ellipsis
			}
		</STYLE>
	</head>
	<body>
		<fieldset style="width:100%;height:90%;border:1px solid #CCCCCC" align="center">
			<br>
			<legend style="border:0px;">督办信息</legend>
			<!--数据表格2008-9-19 -->
			<div class="vcastable_grid" style="width:100%;">
				<table id="vcasdataTable" width="100%" height="98%" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
					<tr class="vcasscrollColThead">
						<td align="center" class="vcastr_1">
							<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#FFFFFF">
								<tr class="vcastr_1">
									<td width="0%" align="center" style="display:none">督办编号</td>
									<td width="20%" align="center">督办时间</td>
									<td width="14%" align="center">督办人</td>
									<td width="18%" align="center">督办目标单位</td>
									<td width="23%" align="center">督办内容</td>
									<td width="20%" align="center">对应事件编号</td>
									<td width="5%" align="center">查看</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td align="center" valign="top" id="superviseGrid"></td>
					</tr>
				</table>
			</div>
			<!--数据表格2008-9-19-->
		</fieldset>
	</body>
</html>
