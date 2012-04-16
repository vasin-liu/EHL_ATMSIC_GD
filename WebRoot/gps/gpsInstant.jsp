<%@ page language="java" contentType="text/html; charset=utf-8"
	import="java.util.*" pageEncoding="utf-8"%>
 <%@ include file="Message.oni"%>	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>GPS车辆实时系统</title>

		<script language="javascript">
  	
  		window.onload = function()
  		{
  			document.all._left.src = "gpsCarList.jsp";
  			document.all.ifmain.src = "gps.jsp";
  		};//position:absolute;top:0;left:0;
  		
  		function disleft()
  		{
			var left=document.getElementById("leftTD");
			var img=document.getElementById("imgif");
			if(left.style.display=="")
			{		
				img.src="images/gps/right.gif";	
				left.style.display="none";
				document.frames('ifmain').location.reload();
			}
			else
			{
				img.src="images/gps/left.gif";			
				left.style.display="";
				document.frames('ifmain').location.reload();
			}
	 	}
  	</script>
 
	</head>
	<body style="margin: 0">
		<table align="right" style="width: 100%; height: 100%;" cellspacing=0
			cellpadding=0 border="0" style="border-width:0;">
			<tbody>
				<tr height="100%">
					<td style="width: 100%; height: 100%;">
						<table id="iframeTable" style="width: 100%; height: 100%;"
							border="0" style="border-width:0;">
							<tbody>
								<tr height="100%">
									<td id="leftTD" width="180px" nowrap>
										<iframe id="_left" name="_left" frameborder="0" id="top"
											name="top" scrolling="no"
											style="height: 100%; visibility: inherit; width: 100%;"></iframe>
									</td>
									<td width="3px" id="lineButton">
										<div onclick="disleft();" class="border"
											style="cursor: pointer;">
											<img id="imgif" src="images/gps/left.gif" />
										</div>
									</td>
									<td id="iframeTD">
										<iframe id="ifmain" name="ifmain" frameborder="0" id="top"
											name="top" scrolling="no"
											style="height: 100%; visibility: inherit; width: 100%;"></iframe>
										<br>
									</td>
								</tr>
							</tbody>
						</table>
					</td>
				</tr>
			</tbody>
		</table>

	</body>
</html>
