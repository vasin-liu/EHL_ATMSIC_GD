<%@ page language="java" pageEncoding="UTF-8" session="true" buffer="8kb" autoFlush="true" isThreadSafe="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>交通管理综合信息中心</title>
		<script type="text/javascript" src="sm/js/common/prototype.js"></script>
		<script type="text/javascript" src="./base/js/global.js"></script>
		<script type="text/javascript" src="./base/js/list/FillListBox.js"></script>
		<script type="text/javascript" src="./base/js/popup/Popup.js"></script>
		<script type="text/javascript">
			function syn() {
				if (!$("DH").value) {
					alert("请选择道路");
					return false;
				}
				var lmText = $("DH").options($("DH").selectedIndex).text;
				if (!lmText&&lmText=="请选择") {
					return false;
				}
				var container = document.getElementById("tdid");
				Popup.prototype.showTips(container, "同步数据需要几十分钟，请耐心等待");
				var strUrl = "synaction.synaction.synLayerData.d?DH=" + $("DH").value
						+ "&LM=" + lmText;
				strUrl = encodeURI(strUrl);
				var params = "";
				new Ajax.Request(strUrl, {
					method : "post",
					parameters : params,
					onComplete : showResponseLogout
				});
			}
			function showResponseLogout(xmlhttp) {
				var divPopup = document.getElementById("showTips");
				divPopup.innerHTML = "";
				alert(xmlhttp.responseText);
			}
		</script>
	</head>
	<body>
		<table width="100%" height="100%">
			<tr>
				<td id="DL">
					<script type="text/javascript">
						fillListBox("DL", "DH", "120","SELECT DH,DLMC FROM SDE.LCB_PT GROUP BY DH,DLMC","请选择");
					</script>
				</td>
				<td id="tdid">
					<input type="button" value="同步LCB_PT到LCB_PT_MIS表" onclick="syn()" />
				</td>
			</tr>
		</table>
	</body>
</html>

