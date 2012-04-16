function onLoadToolbar(voidOptStr){
	aToolBar = new dhtmlXToolbarObject("toolbar_zone", "100%", 25, "");//��������8ʵ��
	aToolBar.setOnClickHandler(onButtonClick);
	 //���ù���8�ĵ���¼�onButtonClick,����onButtomClick��ֵ����dhtmlXProtobar.js�е�defaultAction,������8ʱ���в���
	aToolBar.loadXML("sm/xml/toolbar.xml");                   //����xml��ɹ���8
	aToolBar.showBar();
    var url = "common.toolbar.createToolbar.d?funcid="+getFunccode()+"voidOptList="+voidOptStr;
	url = encodeURI(url);	
	var params = "";
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showToolbarResponse});
}
function showToolbarResponse(xmlHttp){
    var xmlHttp = xmlHttp;
	var xmldoc = xmlHttp.responseXML;
    var results = xmldoc.getElementsByTagName("col");
	for(var i=0;i<results.length; i++){
	}
}
function disable(bar, arr) {
	var barr = arr;
	var str = barr.split(":");
	for (var i = 0; i < str.length; i++) {
		bar.disableItem(str[i]);
	}
}
function getFunccode() {
	var url = window.location.href;//���û��ȡ��� URL Ϊ�ַ�
	var inner = url.indexOf(".jsp");//indexOf()��������ָ��ֵ���ַ��е�һ�γ��ֵ�λ��
	
	var useURL = "";
	var target = url.substring(0, inner).split("/");
	for (var i = 5; i < target.length; i++) {
		if (i == (target.length) - 1) {
			useURL += target[i];
		} else {
			useURL += target[i] + "/";
		}
	}
	
	return useURL;
}