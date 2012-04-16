<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" type="text/css" href="../../css/tab.css">
		<script type="text/javascript" src="../../../base/js/prototype.js"></script>
		<script type="text/javascript" src="../../js/ceditpolice/ComplaintAwake.js"></script>
		<title>需处理投诉列表</title>
	</head>
	<style type="text/css"> 
		body { 
			font: normal 11px auto "Trebuchet MS", Verdana, Arial, Helvetica, sans-serif; 
			color: #4f6b72; 
			background: #E6EAE9; 
			} 
			
	</style>
	<body onload="dealAwakeList();">
		<div style="text-align: center">
			<table id="mytable" cellspacing="0"> 
				<tr>
					<th >
						 选择
					 </th>
					<th >
						 投诉登记时间
					 </th>
					<th >
						 投诉人
					 </th>
					<th >
						 投诉人电话
					 </th>
					<th >
						 投诉类别
					 </th>
					<th >
						 投诉辖区
					 </th>
					<th >
						 投诉警员
					 </th>
					 <th >
						 处理状态
					 </th>
				</tr>
				<tbody id="awaketbody">
				
				</tbody>
			</table>
		</div>
	</body>
</html>
