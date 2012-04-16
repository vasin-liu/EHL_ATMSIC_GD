/***********************************************************************

版 权：北京易华录信息技术股份有限公司 2009

文件名称：videoInspectMap.js
摘 要：	视频监控地图显示

当前版本：1.0
作 者：LChQ  2009-4-14 

***************************************************************************/

function VideoInspectMap()
{
	VideoInspect.call(this);
	
	this.showLevel = 4;		//在达到多少级别时才显示视频设备点信息
	
	this.symbolPrefix = 'videoPoint_' ;	//视频点元素ID前缀
	
	this.m_videoQueryData = null;
	this.queryItem = ['videoId','equipText','equipSite','countyText','useStatus','longtitude','latitude', "equipType","sid"];  
	
	this.perPageCount = 12;	//查询结果记录分页，页记录数

	
	this.CCTV = 'CCTV';
	this.UTC = 'UTC';
	
	
}

VideoInspectMap.prototype = new VideoInspect();
VideoInspectMap.prototype.readVideoPointList = function()
{
	 this.readCountyVideo();
};



VideoInspectMap.prototype.showVideoInfo = function()
{
	//判断是否处在需要显示的级别上.
	var currLevel = map.getCurrLevel();
	if (currLevel < this.showLevel)
	{
		return;
	}
			
	//判断是否为空或者对象未被实例化
   	if (typeof this.m_videoList == "undefined" ||  this.m_videoList == null)
   	{
   		return;
   	}
	//获取地图图层和当前显示范围

   	var mapDiv = map.getVMLDiv();
   	var currentZoom =  map.getCurrentZoom();
   
	for(var i = 0; i < this.m_videoList.length; i+=1)
	{			
		var longtitude = this.m_videoList[i].longtitude;
		var latitude   = this.m_videoList[i].latitude;
		if("" == longtitude || ""==latitude)
		{
			continue;
		}
		var infoCoord = new Coordinate((longtitude *1e16 ), (latitude * 1e16));//将坐标转化为整形
		
		//在地图上当前的坐标绘制出该视频设备点
		this.showVideoPoint(mapDiv, infoCoord, currentZoom, i,this.m_videoList[i].videoId,this.m_videoList[i].equipType,this.m_videoList[i].symbolId);
	}
	
};

//显示视频信息点到指定位置
VideoInspectMap.prototype.showVideoPoint = function(mapDiv, infoCoord, zoom, index,videoId,equipType,symbolId)
{
	var scoord = Util.getScreenPixel(infoCoord, zoom);//由经纬度转屏幕坐标.		
  	var strSymbolId = this.symbolPrefix + videoId;
  		
	//判断是否已经存在
	var pointSymbol = document.getElementById(strSymbolId);
	if(pointSymbol != null)
	{
		pointSymbol.style.left = (scoord.x) + "px";
		pointSymbol.style.top =  (scoord.y) + "px";	
	}
	else
	{	
		pointSymbol = document.createElement("div");
		pointSymbol.id = strSymbolId;
		pointSymbol.style.position = "absolute";
		pointSymbol.style.border = "0px solid #999999";
		pointSymbol.style.fontSize = "12px";
		pointSymbol.style.padding = "1px";
		pointSymbol.style.zIndex = 11;
		pointSymbol.style.left = (scoord.x) + "px";
		pointSymbol.style.top =  (scoord.y) + "px";	
	
		var image = null;
		if(this.CCTV == equipType )
		{ 
			image = Util.createImg("ps", 0, 0, 16, 10, '../../images/holder/camera.gif');	
		}
		if(this.UTC == equipType )
		{
			image = Util.createImg("ps", 0, 0, 16, 10, '../../images/holder/xhj.gif');
		}
		image.style.cursor ="pointer";
		image.position = infoCoord;
		image.videoId = videoId;
		image.sid = symbolId;		
		image.equipType = equipType;
		
		//添加图片点击处理事件，显示气泡窗体

		Event.observe(image, "click", this.showMapTips.bindAsEventListener(image));
		
		pointSymbol.appendChild(image);
		pointSymbol.style.display = "";
		mapDiv.appendChild(pointSymbol);
		
		//设置标图ID
		videoMapObject.m_videoList[index].symbolId = strSymbolId;
	}
};


VideoInspectMap.prototype.showMapTips = function(event,videoId,sid,left,top,position)
{
	if(sid==undefined){
		sid=this.sid;
	}
	var x = 0;
	var y = 0;
	//获取当前对象的编程值：对象编号、经纬度坐标信息
	var infoCoord ;
	    
	//取得鼠标的绝对坐标

	var   container = map.getVMLDiv();
	if(event != null)
	{	    	
		event = event ? event : (window.event ? window.event : null); 
		var source = event.srcElement || event.target;
   		x = Event.pointerX(event) - container.offsetLeft||container.scrollLeft;
		y = Event.pointerY(event) - container.offsetTop||container.scrollTop;
		videoId = this.videoId;
	}	
	else
	{
		if( left && top)
		{
	 		x = left + 8;
	 		y = top;
	 	}
	}	
	if(0!= x && 0 != y )
	{
		//显示正在查询提示		
		MapTips.prototype.showLoadTips(container,x,y);				
	}
	//执行查询获取当前选中的视频点的信息

//	videoMapObject.getVideoDataByID(videoId);
	videoMapObject.getVideoDataByID(sid);
};

//显示设备信息明细
VideoInspectMap.prototype.showVideoDetail = function()
{
	var detailHtml = "";
	if( null != this.m_videoData )
	{
		detailHtml = this.generatBriefHtml();
	}
	var strTitle = '';
	detailHtml += "<hr><table valign=top  width=100% align=center  height=30px><tr><td align=right >";
	if(this.CCTV == this.m_videoData.equipType )
	{ 
		strTitle = '视频设备信息';
		detailHtml += " <image src='../../images/holder/cksp.gif' onclick=\"openVideoWindow('" + this.m_videoData.ip +  "','" + this.m_videoData.videoId + "');\"> ";
	}
	if(this.UTC == this.m_videoData.equipType )
	{
		strTitle = '信号机设备信息';
		detailHtml += " <image src='../../images/holder/ckxw.gif' onclick=\"openPhasicWindow('" + this.m_videoData.videoId + "');\"> ";
	}
	detailHtml += "</img> </td><td align=center valign=top>";
	detailHtml +="<image src='../../images/holder/gb.gif' onclick='MapTips.prototype.hidePopup(true)'></img></td></tr></table>";
	
	var infoCoord = null;
	var scoord = null;
	if( "" == this.m_videoData.longtitude || "" == this.m_videoData.latitude )
	{
		infoCoord = map.getMapModel().getViewCenterCoord();
		if( 0 == infoCoord.x || 0 == infoCoord.y)
		{				
			var centerCoord = map.getMapModel().defaultCenterPoint;//getViewCenterCoord();
			infoCoord = new Coordinate(( centerCoord.x * 1e16), (centerCoord.y  * 1e16));
		}
		//移动到屏幕中心

		map.moveToCenter(new Point(infoCoord.x, infoCoord.y));
		scoord = Util.getScreenPixel(infoCoord, map.getCurrentZoom());
		strTitle += "<span style='color:red'>(未定位)</span>"
	}
	else
	{
		infoCoord = new Coordinate((this.m_videoData.longtitude *1e16 ), (this.m_videoData.latitude * 1e16));//将坐标转化为整形
		var zoom =  map.getCurrentZoom();
		scoord = Util.getScreenPixel(infoCoord, zoom);//由经纬度转屏幕坐标.	
	}
	var   container = map.getVMLDiv();
    //显示提示信息窗口			   
    MapTips.prototype.showPopup(container,strTitle,detailHtml,scoord.x + 6,scoord.y);
};

VideoInspectMap.prototype.generatBriefHtml = function()
{
	var htmlBuffer = "<table>";
	var titles = ["设备编号：","设备名称：" ,"安装位置：" ,"所属区域：" ,"设备状态：" ];
	//["videoId","equipText","ip","serverId","longtitude","latitude","equipSite","branch","equipStatus"];
	var contents = new Array();
	contents.push(this.m_videoData.videoId);
	contents.push( this.m_videoData.equipText);
	contents.push(this.m_videoData.equipSite);
	contents.push(this.m_videoData.branch);
	contents.push(this.m_videoData.equipStatus);
	
	//建立行

	for(var i=0;i < titles.length; i+=1)
	{
		var trHtml =  "<tr>"
		trHtml += "<td align='right' style='font-weight:bold'>" +  titles[i] + "</td>"  + "<td align='left'>" + ( contents[i]?contents[i]:"&nbsp;") + "</td>"; 	
		trHtml +=  "</tr>"
		htmlBuffer += trHtml;
	}	
	htmlBuffer += "</table>";
	return htmlBuffer;
};
//查询视频信息
VideoInspectMap.prototype.videoDataQuery = function()
{
	//形成查询条件
	var local = $('txtdLocalQ').value;        //视频位置
	var county = $('txtdeviceCountyQ').value;   //所属辖区


	var countyCode  = $('txtdeviceCountyQ').branchId;
	var devicecode = $('txtdCodeQ').value;      //设备编号
	var devicename = $('txtdNameQ').value;      	  //设备名称
	var deviceType = $('txtdTypeQ').options[$('txtdTypeQ').selectedIndex].value; 		   //设备类型
														   
	var queryCond = "";
				
	//判断输入，得到查询参数

	if (local != null && local.length>0)
	{
	   queryCond = " AND t1.azdd like '%"+ local +  "%' ";
	}
		if (devicecode != null && devicecode.length>0)
		{
		   queryCond +=  " AND t1.glid like '%" + devicecode + "%' ";
		}
		if (devicename != null && devicename.length>0)
		{
		   queryCond +=  " AND t1.cpmc like '%" + devicename  + "%' ";
		}
		
		if (deviceType != null && deviceType.length>0)
		{
		   	queryCond +=  " AND t1.ssxt='" + deviceType + "' ";
		}
		else
		{
			queryCond +=  " AND ( t1.ssxt='" + this.CCTV + "' OR t1.ssxt='" + this.UTC + "') ";
		}
		
		if(countyCode && countyCode.length>0)	//辖区信息查询控制
		{
			if(-1 != userCountyCode.indexOf( this.DETACHMENT ))	
			{
				//支队可查询所有辖区

				 queryCond += " AND t1.ssjg='" + countyCode + "'";
			}
			else
			{
				//不是支队，只能查询本辖区
				queryCond += " AND t1.ssjg='" + userCountyCode + "'";
			}
		}
		else
		{
			if(-1 == userCountyCode.indexOf( this.DETACHMENT ))		
			{
				//不是支队，只能查询本辖区
				 queryCond += " AND t1.ssjg='" + userCountyCode + "'";
			}
		}
		//查询SQL
		var selectSQL = "SELECT DISTINCT t1.glid,t1.cpmc,t1.azdd,t2.jgmc,t3.value,t1.longitude,t1.latitude,t1.ssxt,t1.sid "+
			"	FROM atms_equipment_zb t1, t_sys_department t2, " +
			"( SELECT  id,value FROM  atms_equipment_dict  WHERE type='使用状态' ) t3 " +
			"	WHERE t1.syzt=t3.id(+) AND t2.jgid(+)=t1.ssjg   ";
		selectSQL += queryCond;
		pagingPosition = new DhtmlPaging(0,-1,this.perPageCount,selectSQL,$('vedioBriefPaging'),this.parseQueryResult);
		pagingPosition.getPageData();
};

//处理查询得到的数据结果

VideoInspectMap.prototype.parseQueryResult = function(xmlHttp)
{
	var xmlDoc = xmlHttp.responseXML;
	
	videoMapObject.m_videoQueryData = null;
	videoMapObject.m_videoQueryData = new Array();		//视频信息列表 
	var results = xmlDoc.getElementsByTagName("row");
	 
	if(0 < results.length)
	{	
		//循环获取各信息项
		for (var i = 0; i < results.length; i+=1)
		{
			var newRecord = results[i].getElementsByTagName("cell");
			var videoData = {};
			for (var j = 0; j < newRecord.length; j+=1)
			{
		 		videoData[videoMapObject.queryItem[j]] = newRecord[j].text;
			}
			videoMapObject.m_videoQueryData.push(videoData);
		}
	}
	videoMapObject.htmlVideoGRID();
};

//构造查询结果数据表格

VideoInspectMap.prototype.htmlVideoGRID = function()
{
	if( null == this.m_videoQueryData )
	{
		return ;
	}
	 
	var strTable = "<table id=\"tabVedioList\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 border=0>";
		
	//生成表头
	var heads  = ['产品名称','使用状态','所属辖区'];
	var colWidths =['120px','60px','100'];

	strTable += "<tr style='backgroud-color:silver'><th align=center width=15%>选择</th>";
	for(var i = 0 ; i < heads.length; i+=1)
	{
		var subHead = heads[i];
		strTable += "<th align=\"center\" width=" + colWidths[i] + " nowrap>";
		strTable += subHead;
		strTable += "</th>";
	}											
	strTable += "</tr>";			
	//生成结果页面
	 
	strTable += this.getHtmlDataRow();
	strTable += "</table>";
	
	//添加到结果面板上
    var conCtrl= $('vedioQueryResult');
    conCtrl.innerHTML = strTable;	
};

VideoInspectMap.prototype.getHtmlDataRow = function()
{
	var rowHtml = "";
	for (var i = 0; i < this.m_videoQueryData.length; i+=1)
	{
		var dataRecord = this.m_videoQueryData[i];
		var coord = this.m_videoQueryData[i].longtitude +  "," + this.m_videoQueryData[i].latitude;
	
	    rowHtml += "<tr class=\"rowstyle\" nowrap onclick=\"videoMapObject.queryRowClickHandler('"+ dataRecord.videoId +"','"+ coord +"',"+(i+1)+",'"+ dataRecord.sid +"');\">";	
	    rowHtml += "<td  align=\"center\">";
	    rowHtml += "<input type=\"checkbox\" id=\"chkSel"+i+"\" value=\""+coord+"\" /> </td>";
	      	 
//	    rowHtml += "<td>"  + ("" == dataRecord.videoId?'&nbsp;':dataRecord.videoId) + "</td>";
		rowHtml += "<td>"  + ("" == dataRecord.equipText ?'&nbsp;':dataRecord.equipText ) + "</td>";
		rowHtml += "<td>"  + ("" == dataRecord.useStatus  ?'&nbsp;':dataRecord.useStatus  ) + "</td>";
		rowHtml += "<td>"  + ("" == dataRecord.countyText ?'&nbsp;':dataRecord.countyText ) + "</td>";
	    rowHtml += "</tr>";						
	}
	return rowHtml;
};
//查询结果GRID中行点击事件处理
VideoInspectMap.prototype.queryRowClickHandler = function(videoId,coord,index,sid)
{
	//控制选中状态

	var tableObj = $("tabVedioList");	
	try
	{
		var checkboxObj = tableObj.rows[index].cells[0].firstChild;
		for(var i=1; i<tableObj.rows.length; i+=1)
		{
			var tCheckBox = tableObj.rows[i].cells[0].firstChild;
					
			if (i != index && tCheckBox.checked)
			{
				tableObj.rows[i].bgColor = "";			
			}
			if(i == index)
			{
				tCheckBox.checked = true;
				tableObj.rows[i].bgColor = "silver";	
			}
			else
			{
				tCheckBox.checked = false;
			}
		}
	}
	catch(e){}
	//置视频点标注信息
	if (checkboxObj.checked)
	{
		//居中显示视频点，并显示详细信息

		this.centerVedioPoint(videoId,sid);
	}
};
VideoInspectMap.prototype.centerVedioPoint = function(videoId,sid)
{
 
	var getIndex = -1;
	if(typeof(videoMapObject.m_videoList)=="undefined" || videoMapObject.m_videoList == null) 
	{
		return;
	}

	for(var i = 0; i < videoMapObject.m_videoList.length; i++)
	{		
//	    if(videoMapObject.m_videoList[i].videoId == videoId)
//	    {
//	    	getIndex = i;
//	    	break;
//	    }	

	    if(videoMapObject.m_videoList[i].serverId == sid)
	    {
	    	getIndex = i;
	    	break;
	    }	
	}
		
	if(-1 != getIndex)
	{
		var mapDiv = map.getVMLDiv();
    	var model = map.getMapModel();
    	var currentZoom =  map.getCurrentZoom();
		var coordArray =[ videoMapObject.m_videoList[getIndex].longtitude ,videoMapObject.m_videoList[getIndex].latitude ];
		//闪烁显示视频点

		MapUtils.prototype.flashPoint(mapDiv, coordArray,currentZoom,2);	
		
		//显示选中的视频点详细信息				
		var infoCoord = new Coordinate(coordArray[0]*1e16 , coordArray[1]*1e16);
		var scoord = Util.getScreenPixel(infoCoord, currentZoom);//由经纬度转屏幕坐标.				
		var left = scoord.x;
		var top =  scoord.y;			
		this.showMapTips(null,videoMapObject.m_videoList[getIndex].videoId,sid,left,top,infoCoord);
	}
	else
	{
		this.showMapTips(null,videoId,sid);
	}
};
//查询面板 
VideoInspectMap.prototype.outputHTML = function()
{
	var htmlBuffer = '\
	    <div id="divVideoQuery" class="queryDiv" style="offsetTop:0px">\
		  <table cellspacing=0 cellspadding=0>\
			<tbody>\
				<tr>\
					<td align="right">&nbsp;&nbsp;设备类型：</td>\
					<td><select  id="txtdTypeQ" style="width:100px" />\
						<option value="">全部</option>\
						<option value="CCTV">视频</option>\
						<option value="UTC">信号机</option>\
						</select>\
					 </td>\
					<td></td>\
					<td align="right">&nbsp;&nbsp; </td>\
					<td >&nbsp;</td>\
				</tr>\
				<tr>\
					<td>&nbsp;&nbsp;所属辖区：</td>\
					<td ><input type="text" id="txtdeviceCountyQ"  style="width:100px" readOnly/></td>\
					<td align="left"><img id="imgBtnBranchChoose" src="../../../sm/image/popup/btnselect.gif" style="cursor:hand;"  alt="选择机构"  onclick="raiseCountyChoice(\'txtdeviceCountyQ\')">\
					</td>\
					<td align="right">&nbsp;&nbsp;设备编号：</td>\
					<td><input type="text" id="txtdCodeQ"  style="width:100px" /> </td>\
				</tr>\
				<tr>\
					<td align="right">&nbsp;&nbsp;设备名称：</td>\
					<td><input type="text" id="txtdNameQ" style="width:100px" /> </td>\
					<td></td>\
					<td align="right">&nbsp;&nbsp;设备位置：</td>\
					<td ><input type="text" id="txtdLocalQ" style="width:100px" /></td>\
				</tr>\
				<tr>\
					<td colspan=5 align="right">\
						&nbsp;&nbsp;\
						<img id=\"btnSearch\" src=\"../../images/button/search.gif\" style=\"cursor:hand\" onclick=\"videoMapObject.videoDataQuery()\" />\
					</td>\
				</tr>\
			</tbody>\
		  </table>\
		</div>\
		<hr />\
		<div id="vedioBriefPaging"  style="offsetTop:0px; zindex:-1;text-align:center"></div>\
		<div id="vedioQueryResult" class="scrollDiv"  ></div>';
		
	return htmlBuffer;
};

//打开视频查看窗口
function openVideoWindow(videoIP,videoId)
{
	if(! videoIP)
	{
		alert('没有该视频设备的视频服务,查看视频失败!');
		return;
	}
	 window.open('../videoViewer/videoViewer.jsp?VIDEOIP=' + videoIP + '&VIDEOID=' + videoId ,'win_video','directories=no,location=no,menubar=no,toolbar=no,resizable=yes,width=700,height=600');
// 	var uri = "../../../cctv/ehl/cctv/VideoPlay.jsp?CamID='"+videoId + "'";
//	uri = encodeURI(uri);
//	window.open( uri , "showvideo", "location=no,toolbar=no,status=no,directories=no,menubar=no,width=620,height=406,resizable:no");
 
// 	var div="<OBJECT ID=\"EhlSingleView\" CLASSID=\"CLSID:ABDD105A-E830-417F-A0AF-DE5D803E98C1\" CODEBASE=\"http://localhost:8001/CmdDispatch/dispatch/ehl/videoViewer/PrjHuasanOcx.cab\"></OBJECT>"
// 	new xWin("random",570,590,6,90,"警卫方案",div);
// 	 var obj = document.getElementById("EhlSingleView");
// 	 var videoURL="";
// 	 var videoId="";
//    obj.ShowView(videoURL,videoId,"admin");
//	var uri = "../../../cctv/ehl/cctv/VideoPlay.jsp?CamID='"+videoId + "'";
////	uri = encodeURI(uri);
//	window.open( uri , "showvideo", "location=no,toolbar=no,status=no,directories=no,menubar=no,width=620,height=406,resizable:no");
}

//查看相位
function openPhasicWindow(videoId)
{
	if(! videoId)
	{
		alert('没有得到信号机信息标识，查看相位失败!');
		return;
	}
	window.open( hrefPhasic + "?pUTCID="+videoId ,'win_video','directories=no,location=no,menubar=no,toolbar=no,resizable=yes,width=330,height=355');
}


