/**
 * 
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 文件名称：affairQuery.js
 * 摘 要：警情信息查询。


 * 当前版本：1.0
 * 作 者：LChQ  2009-4-14
 * 修改人：
 * 修改日期：
 */
var queryDB = new QueryAffair();

function QueryAffair()
{
	this.m_queryParams = null;	//查询参数
}

//window.attachEvent("onload",initPageData);
//
//function initPageData()
//{
//	mygridt_affair_query.setOnRowSelectHandler( queryDB.rowClickedHandler, true);
//}

//获取查询参数
QueryAffair.prototype.getPageCondition = function()
{
	this.m_queryParams = null;
	this.m_queryParams = {};
	this.m_queryParams['EVENTTYPE'] =$('txtAffairType').options[$('txtAffairType').selectedIndex].value;	//事件类型
	this.m_queryParams['EVENTSOURCE'] =$('txtAlarmSource').options[$('txtAlarmSource').selectedIndex].value;//事件来源
	this.m_queryParams['EVENTSTATE'] =$('txtAlarmState').options[$('txtAlarmState').selectedIndex].value;//事件状态
	this.m_queryParams['ALARMTIMEBG'] = $F('txtBgAlarmTake');	//接警时间
	this.m_queryParams['ALARMTIMETMN'] = $F('txtTmnAlarmTake');
	
};


//获取查询参数
QueryAffair.prototype.requestQueryData = function()
{
	this.getPageCondition();
	var sqlBuffer = "";
	if( this.m_queryParams['EVENTTYPE'] && 0<this.m_queryParams['EVENTTYPE'].length)
	{
		sqlBuffer += " AND t.EVENTTYPE='" + this.m_queryParams['EVENTTYPE'] + "'";
	}
	
	if( this.m_queryParams['EVENTSOURCE'] && 0<this.m_queryParams['EVENTSOURCE'].length)
	{
		sqlBuffer += " AND EVENTSOURCE='" + this.m_queryParams['EVENTSOURCE'] + "'";
	}
	
	if( this.m_queryParams['EVENTSTATE'] && 0<this.m_queryParams['EVENTSTATE'].length)
	{
		sqlBuffer += " AND EVENTSTATE='" + this.m_queryParams['EVENTSTATE'] + "'";
	}
	
	if( this.m_queryParams['ALARMTIMEBG'] && 0<this.m_queryParams['ALARMTIMEBG'].length)
	{
		sqlBuffer += " AND t.ALARMTIME>=to_date('" + this.m_queryParams['ALARMTIMEBG'] + "','yyyy-MM-dd HH24:mi')";
	}
	if( this.m_queryParams['ALARMTIMETMN'] && 0<this.m_queryParams['ALARMTIMETMN'].length)
	{
		sqlBuffer += " AND t.ALARMTIME<=to_date('" + this.m_queryParams['ALARMTIMETMN'] + "','yyyy-MM-dd HH24:mi')";
	}
	
	
	//add by wangxt 2009-5-10
    selectSql = selectSQL.substring(0,selectSQL.indexOf('order'));
    sqlBuffer += " order by t.alarmtime desc "
	pagingObject.setParams(0,-1,15,selectSql + sqlBuffer);
	pagingObject.getPageData();
};


$('btnConfirm').onclick = function()
{
	queryDB.requestQueryData();
};

//定义GRID对象
mygridt_affair_query = new dhtmlXGridObject('t_affair_query');
mygridt_affair_query.setSkin('gray');
pagingObject = null;

// add by wangxt 2009-5-10 编码转换机构名称
//设置初始SQL
selectSQL = "SELECT t.alarmid,concat(concat('＆lt;a href=# onclick=showAlarmInfo(this);＆gt;' , t.title), '＆lt;/a＆gt;'),t.eventtype,to_char( t.alarmtime,'yyyy-MM-dd HH24:mi'),";
selectSQL += " tc1.name esource, tc2.name etype,tc4.name estate,t.alarmregion ";
//selectSQL += " to_char( t.disposetime,'yyyy-MM-dd HH24:mi'),t.disposeperson,f_get_dept(t.disposeunit), t.alarmdesc  ";
selectSQL += " FROM  t_attemper_alarm t,t_attemper_code tc1,t_attemper_code tc2,t_attemper_code tc3,t_attemper_code tc4 ";
selectSQL += " where t.eventsource=tc1.id(+) AND t.eventtype=tc2.id(+) AND t.eventlevel=tc3.id(+) AND t.eventstate=tc4.id(+) " ;
selectSQL +=" and t.ALARMREGION=f_get_dept("+deptcode+")" ;
selectSQL +="order by t.alarmtime desc " ;

function showAlarmInfo(linkObject)
{
	try
	{
		var alarmType = linkObject.parentNode.parentNode.cells[2].innerHTML;
		var alarmid = linkObject.parentNode.parentNode.cells[0].innerHTML;
		window.open("alarmInfoQuery.jsp?ALARMID=" + alarmid  + "&ALARMTYPE=" + alarmType, 'alarmInfo','width=710,height=460,resizable=1,scrollbars=1');
		
	}
	catch(e)
	{
		alert("查看警情详细信息失败！");
		
	}
}


//Grid构造
function getT_affair_query()
{
	mygridt_affair_query.setImagePath('../../../sm/image/table/');
	mygridt_affair_query.setHeader('1,报警内容,1,报警时间,事件来源,事件类型,事件状态,事件辖区');
	mygridt_affair_query.setColSorting('str,str,str,str,str,str,str,str');
	mygridt_affair_query.setColTypes('ro,ro,ro,ro,ro,ro,ro,ro');
	mygridt_affair_query.setInitWidths('0,280,0,110,100,78,90,100');
	mygridt_affair_query.setColAlign('left,left,left,left,left,left,left,left');
	mygridt_affair_query.init();

	pagingObject = new DhtmlPaging(0,-1,15,selectSQL,$('divPagingButton'),pagingSetting);
	pagingObject.setDataGrid(mygridt_affair_query);
 	pagingObject.getPageData();
}

//设置换页信息
function pagingSetting()
{
	var pageIndex = mygridt_affair_query.getUserData('','pageIndex');
	var pageCount = mygridt_affair_query.getUserData('','pageCount');
	var pageSize = mygridt_affair_query.getUserData('','pageSize');
	var selectSQL =  mygridt_affair_query.getUserData('','selectSQL');
	pagingObject.setParams(pageIndex,pageCount,pageSize,selectSQL);
};
getT_affair_query();


