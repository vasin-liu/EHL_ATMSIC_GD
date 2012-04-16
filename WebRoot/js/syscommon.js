/*创建XmlDom*/	
function createXMLDOM() {
	var arrSignatures = ["MSXML2.DOMDocument.6.0","MSXML2.DOMDocument.5.0", "MSXML2.DOMDocument.4.0", "MSXML2.DOMDocument.3.0", "MSXML2.DOMDocument", "Microsoft.XmlDom"];
	for (var i = 0; i < arrSignatures.length; i++) {
		try {
			var oXmlDom = new ActiveXObject(arrSignatures[i]);
			return oXmlDom;
		}
		catch (oError) {
	        //ignore
		}
	}
	throw new Error("你的系统没有安装MSXML");
}     
	
/*根据xml文件创建dom对象*/
function getDom(xmlFile) {
	var oXmlDom = createXMLDOM();
	oXmlDom.async = "false";
	oXmlDom.load(xmlFile);
	if (oXmlDom.parseError != 0) {
		var oError = oXmlDom.parseError;
		alert("an error occurred:\nError code:" + oError.errorCode + "\n" + "Line:" + oError.line + "\n" + "Line Pos:" + oError.linepos + "\n" + "Reason:" + oError.reason);
	}
	return oXmlDom;
}