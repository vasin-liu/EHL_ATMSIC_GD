<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!-- 
	 * 
	 * 版 权：北京易华录信息技术股份有限公司 2009
	 * 文件名称：appraiseDetail.jsp
	 * 摘 要：评价信息展示界面
	 * 当前版本：1.0
	 * 作 者：LChQ  2009-4-20
	 * 修改人：
	 * 修改日期：


 -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>查看评价信息</title>
		<script type="text/javascript"  src="../../js/appraise/appraise.js">	</script>
		<style type="text/css">
		.queryTD1 {
			line-height: 16px;
			font-size: 9pt;
			empty-cells: show;
			text-align: right;
			width: 11%;
			border-right: 1 #caa solid;
			border-bottom: 1 #caa solid;
			
		}
		
		.queryTD2 {
			line-height: 16px;
			font-size: 9pt;
			empty-cells: show;
			text-align: left;
			width: 22%;
			border-right: 1 #silver solid;
			border-bottom: 1 #caa solid;
		}
		</style>
	</head>
	<body>
		<table style="border: 0; width: 100%; height: 100%" cellspacing="0"
			cellpadding="0">
			<tr style="line-height:25px">
				<td>&nbsp;</td>
			</tr>
			<tr>
			
				<td align=center>
					<table style="border: 1 #caa solid;line-height:25px"  cellspacing="0"
						cellpadding="2">
						<tr >
							<td class="queryTD1" style="line-height:25px" nowrap>
								评价时间：
							</td>
							<td class="queryTD2">
								<span id="txtAppraiseTime"></span>
							</td>
							<td class="queryTD1" nowrap>
								评价人：
							</td>
							<td class="queryTD2">
								<span id="txtAppraiser"></span>
							</td>
						</tr>
						<tr>
							<td class="queryTD1" style="line-height:25px" nowrap>
								评价人所在单位：
							</td>
							<td class="queryTD2">
								<span id="txtAppraiserCounty"></span>
							</td>
							<td class="queryTD1" nowrap>
								事件处理效率：
							</td>
							<td class="queryTD2">
								<span id="txtEfficiency"></span>
							</td>
						</tr>
						<tr>
							<td class="queryTD1" style="line-height:25px" style="border-bottom:0;" nowrap>
								评价内容：
							</td>
							<td class="queryTD2" colspan=3 style="border-bottom:0;line-height:25px" id="tdAppraiseContent">
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

	</body>
</html>
