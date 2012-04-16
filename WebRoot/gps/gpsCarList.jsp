<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     
     <title>GPS车辆树</title>
     <script type="text/javascript" src="../webgis/script/map/Prototype.js"></script>
     <script type="text/javascript"  src="js/GPSHelper.js"></script>
	 <script type="text/javascript"  src="js/Atms45GPSTree.js"></script>
	<script type="text/javascript">
		window.onload = function()
		{	 
			var gpsTree = new Atms45GPSTree();
			gpsTree.init('divGPSCarTree','0',carNodeClicked);
			gpsTree.loadXmlData('gps.GPSTree.readBranchCarTree.d','');
		};
		
		function carNodeClicked(itemId)
		{
			if(itemId)
			{
				window.parent.ifmain.gpsmap.centerCarInfo(itemId)
			}
		}
	</script>
  </head>
  
  <body style="margin:0">
    <table style="width:100%;height:100%;">
    		<tr>
    			<td align="center">
    				<div id='divGPSCarTree' style="width:100%;height:100%;text-align:left;overflow-y:auto">
    				</div>
    			</td>
    		</tr>
    </table>
  </body>
</html>
