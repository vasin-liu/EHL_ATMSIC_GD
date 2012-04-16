/**
 * 函数功能:显示统计图表,发送Ajax请求数据.
 * 参数说明:title-标题;xIndex-X轴列号;yIndex-Y轴列号;zIndex-Z轴列号;chartType-图表类型(pie-饼形图,bar-柱状图,line-折线图);gridObj-Grid对象.
 * 调用实例:buildChart('卡口流量','3','4','2','bar',mygridt_tgsfl_s)
 */
function buildChart(title,xIndex,yIndex,zIndex,chartType,gridObj){
	var xDesc = gridObj.getHeaderCol(xIndex);//X轴描述
	var yDesc = gridObj.getHeaderCol(yIndex);//Y轴描述
	var sqlstr = gridObj.getUserData('','exeSql'); //SQL语句
	sqlstr = convertSql(sqlstr) + ":" + xIndex + ":" + yIndex + ":" + zIndex;
	
	var url = 'base.buildChart.chart.d';
	url = encodeURI(url);
	var params = "title=" + title + "&xDesc=" + xDesc + "&yDesc=" + yDesc + "&chartType=" + chartType + "&sqlstr=" + sqlstr;
	params = encodeURI(params);
    new Ajax.Request(url, {method: 'post', parameters: params, onComplete:showImage});
}

/**
 * 函数功能：Ajax回调,解析请求返回的数据并展示出来.
 */
function showImage(xmlHttp){
   var strText = xmlHttp.responseText;
   var splitLoc = strText.indexOf("|");
   var fileName = strText.substring(0,splitLoc);
   var graphURL = strText.substring(splitLoc+1);
   var strlink = "../../../sm/ChartView.jsp?fileName="+fileName+"&graphURL="+graphURL;
   Popup.prototype.hideTips();
   window.showModalDialog(strlink,"","dialogWidth:790px;dialogHeight:500px");

}
