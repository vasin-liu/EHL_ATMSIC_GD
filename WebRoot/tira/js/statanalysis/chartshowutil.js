/**
 * ifValidPieChartDataXML<br/>
 * 检查饼状图的数据集是否全部为0
 * @param 饼状图dataxml
 * @return 布尔值
 */
function ifValidPCDX(sets) {
	var ifValid = false;
	var set;
	var attrs;
	for (var i = 0; i < sets.length; i++) {
		attrs = sets[i].attributes;
		if (attrs.length>1) {
			set = attrs.getNamedItem("value");
			if (set != null && set.nodeValue != "0") {
				ifValid = true;
				break;
			}
		}
	}
	return ifValid;
}

/**
 * 是否是有效的xml数据格式<br/>
 * 只能验证xml文件的格式，不能排除格式正确，但全部数据都为0的情况
 * @param {Object} xmlDoc
 * @return {TypeName} 
 */
function ifValidDataXML(xmlDoc) {alert()
	var nulldataxml = "";
	if (xmlDoc == null || xmlDoc.xml == undefined || xmlDoc.xml == nulldataxml) {
		Popup.prototype.hideTips();//别忘了导入引用js
		setTimeout(showPrompt,1);//避免提示框和进度加载条显示在一起
		return false;
	}
	return true;
}

function showPrompt() {
	alert("没有可以显示的数据！");
}
