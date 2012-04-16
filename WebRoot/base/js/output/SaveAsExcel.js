/**
 * 函数功能:转存Excel.
 * 参数说明:title-标题;fileName-文件名;gridObj-Grid对象.
 * 调用实例:saveAsExcel('卡口流量','tgsFlow',mygridt_tgsfl_s)
 */
function saveAsExcel(title,fileName,gridObj){
	var iRowsNum = gridObj.getRowsNum();//总行数	var iColsNum = gridObj.getColumnCount();//总列数
	var tabHeader = new Array();//表头
	var tabData = new Array();//表数据
    for (var col = 1; col < iColsNum; col++) {
	   tabHeader.push(gridObj.getHeaderCol(col));
    }	  
	
	var tabData = gridObj.getUserData('','exeSql');
	
	var url = "base.saveAsExcel.toExcel.d?title=" + title + "&fileName=" + fileName + "&tabHeader=" + tabHeader + "&tabData=" + tabData;
	url = encodeURI(url);
 	var left = (screen.availWidth-800)/2;
    var top = (screen.availHeight-500)/2;
     
    window.open(url,"","height=500,width=800,top="+top+",left="+left+",toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=no,status=no");   
}

/**
 * 函数功能:定制表字段转存Excel.
 * 参数说明:title-标题;fileName-文件名;header-表头;获取转存内容的SQL.
 * 调用实例:saveAsExcel('卡口流量','tgsFlow','卡口名称,所属机构,卡口编号','select kkmc,ssjg,kkbh from tgsinfo')
 */
function saveFieldsAsExcel(title,fileName,header,dataSql){
	
	var url = "base.saveAsExcel.fieldsToExcel.d?title=" + title + "&fileName=" + fileName + "&tabHeader=" + header + "&dataSql=" + dataSql;
	url = encodeURI(url);
 	var left = (screen.availWidth-800)/2;
    var top = (screen.availHeight-500)/2;
     
    window.open(url,"","height=500,width=800,top="+top+",left="+left+",toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=no,status=no");   
}

