/*
 * 定义一个摄像机显示类
 */
VidiconInfoMap = Class.create();
VidiconInfoMap.prototype =
{ 
	/**
	 *  定义存储摄像机坐标数组
	 */
	vidiconList : null,
		 
	/**
     * 构造函数.
     * @param {Object} container
     */
    initialize: function(container){
        this.container = container;       
        this.vidiconID = ""; //摄像机编码      
        this.vidiconName = ""; //摄像机名称
        this.ssxt="";//所属系统
        this.rowID = 0; //选中行号
        this.isClickedCheckBox = false; //是否点击CheckBox标记
		this.selectBGColor = "#EDF3FE"; //选中背景颜色
	    this.unSelectBGColor = "#FFFFFF"; //未选中背景颜色
	    this.marked = "已标注";
    },
        
    /**
     * 获取摄像机编号
     */
	getVidiconID: function(){
		return this.vidiconID; 
	},
    /**
     * 获取摄像机名称
     */
	getVidiconName: function(){
		return this.vidiconName; 
	},
	/**
     * 获取所属系统
     */
	getSsxt: function(){
		return this.ssxt; 
	},    
    /**
	 * 读取图层数据
	 */
	readVidiconPoints: function(){
		var url = 'cctv.vidicon.readVidiconPoints.d';
		var params = "jgccbm="+jgccbm+"&minx=" + FullExtent.getMinX()/1e16 + "&maxy=" + FullExtent.getMaxY()/1e16 + "&maxx=" + FullExtent.getMaxX()/1e16 + "&miny=" + FullExtent.getMinY()/1e16;
		params = encodeURI(params);
		new Ajax.Request(url, 
		{
				method: 'post', 
				parameters: params, 
				//读取完成后，放入内存数组
				onComplete: VidiconInfoMap.prototype.finishReadPoints
				
		});
	},
	
	/**
     * 完成服务端读取后，将获取到的数据存入到内存数组中
     */
	finishReadPoints: function(xmlHttp){
		var xmlDoc = xmlHttp.responseXML;
		var results = xmlDoc.getElementsByTagName("row");
		vidiconList = new Array();
		var points = new Array();
		
		for (var i = 0; i < results.length; i++){
			var rowResult = results[i].childNodes;
			var rowArray = new Array(4);
			
			var id = "";					
			var idObj = rowResult[0].firstChild;
			if (idObj != null){
				id = idObj.nodeValue;
			}
			
			var coord = "";
			var coordObj = rowResult[1].firstChild;
			if (coordObj != null){
				coord = coordObj.nodeValue;			
			}
			
			var ssxt = "";
			var ssxtObj = rowResult[2].firstChild;
			if (ssxtObj != null){
				ssxt = ssxtObj.nodeValue;			
			}
			rowArray[0] = id;
			rowArray[1] = coord;
			rowArray[2] = "";
			rowArray[3] = ssxt;
			points[i] = rowArray[1];
			vidiconList[i] = rowArray; 
		}
		//移动并缩放地图
	    MapUtils.prototype.moveToAreaCenter(deptidForCenter);	
		
		VidiconInfoMap.prototype.showAllVidiconPoint();
	},
	
	/**
     * 在地图上显示所有摄像机点
     */
    showAllVidiconPoint: function()
    {
    	//判断是否为空或者对象未被实例化
    	if (typeof vidiconList == "undefined" || vidiconList == null){
    		return;
    	}
		
		//获取地图图层和当前显示范围
    	var mapDiv = map.getVMLDiv();
    	var currentZoom =  map.getCurrentZoom();
    	
    	//摄像机图层中摄像机的坐标数据
		for(var i = 0; i < vidiconList.length; i++){			
			var coordxyArr = vidiconList[i][1].split(",");//转化为坐标对
			var infoCoord = new Coordinate((coordxyArr[0]*1e16), (coordxyArr[1]*1e16));//将坐标转化为整形
			this.showVidiconSymbol(mapDiv, infoCoord, currentZoom, i,vidiconList[i][0],vidiconList[i][2],vidiconList[i][3]);//在地图上绘制出当前的坐标
		}
   	},  
	
	/**
     * 构造函数.
     * @param {Object} mapDiv 地图图层对象
     * @param {Object} infoCoord 经纬度坐标信息
     * @param {Object} zoom 当前屏幕显示区域
     * @param {Integer} order 标注顺序号
	 * @id：摄像机编码
	 * @name：摄像机名称
     */
 	showVidiconSymbol:function(mapDiv, infoCoord, zoom, order,id,name,ssxt){
  		var scoord = Util.getScreenPixel(infoCoord, zoom);//由经纬度转屏幕坐标.		
  		var strSymbolId = id;
  		
  		//判断是否已经存在
  		var pointSymbol = document.getElementById(strSymbolId);
  		if(pointSymbol != null){
  		   pointSymbol.parentNode.removeChild(pointSymbol);
  		}
		//判断是否处在需要显示的级别上.
		var currLevel = map.getCurrLevel();
		if (currLevel < Number(getMapParameter("gisDeviceLevel"))){
//			return;
		}
  		
		pointSymbol = document.createElement("div");
		pointSymbol.id = id;
		pointSymbol.style.position = "absolute";
		//pointSymbol.style.background = "#FFFFFF";		
		pointSymbol.style.border = "0px solid #999999";
		pointSymbol.style.fontSize = "12px";
		pointSymbol.style.padding = "1px";
		pointSymbol.style.zIndex = 11;
		pointSymbol.style.left = scoord.x + "px";
		pointSymbol.style.top =  scoord.y + "px";	
		var image =	Util.createImg(id, 0, 0, 16, 16, "../../image/map/symbol/vidicon.gif");	
		image.title = name;
		image.id = id;
		image.ssxt = ssxt;
		image.style.cursor ="pointer";
		image.position = infoCoord;

		//添加双击处理事件和鼠标移开事件，双击显示气泡窗体，移开隐藏显示
		Event.observe(image, "click", this.showMapTips.bindAsEventListener(image));		
		pointSymbol.appendChild(image);
		pointSymbol.style.display = "";
		mapDiv.appendChild(pointSymbol);
		vidiconList[order][2] = strSymbolId;
 	},
	
	/**
	 * 显示提示窗口窗体
	 */
	showMapTips: function(event,left,top,position){	
	    var x = 0;
	    var y = 0;
	    var container;
	   
	    //获取当前对象的编程值：对象编号、经纬度坐标信息
	    var infoCoord ;
	    
	    //取得鼠标的绝对坐标
	    container = map.getVMLDiv();
	    if(event != null){	    	
				event = event ? event : (window.event ? window.event : null); 
				var source = event.srcElement || event.target;
		    x = Event.pointerX(event) - container.offsetLeft||container.scrollLeft;
				y = Event.pointerY(event) - container.offsetTop||container.scrollTop;
	    } else{
	     	x = left + 8;
	     	y = top;
	    }		    
	    if(typeof(position) == "undefined" || position == null){	    	
			infoCoord = this.position;
	    } else{
	    	infoCoord = position;
	    }	    
		
		//显示正在查询提示		
		MapTips.prototype.showLoadTips(container,x,y);				
		
		//执行查询获取当前选中的摄像机的信息
		var url = 'cctv.vidicon.getVidiconInfo.d';	
		var params = "";	
	    if(!this.vidiconID){
	    	params = "camid=" + this.id;
	    }else{
	    	params = "camid=" + this.vidiconID;
	    }
		params += "&ssxt=" + this.ssxt;
		//查询到结果后显示在气泡中		
		new Ajax.Request(url, 
		    {
				method: 'post', 
				parameters: params, 
				
				onComplete: function(xmlHttp){
					var strResponseText = xmlHttp.responseText;
					if(strResponseText == null){
						return;
					}					
				    //显示提示信息窗口			   
				    MapTips.prototype.showPopup(container,"摄像机信息",strResponseText,x-10,y-20)        
				}				
	  		}
	  	);
	},
	
   	/**
	 * 获取摄像机列表
	 */
	getVidiconList: function(){ 
		//形成查询条件
		var vidiconID = document.getElementById('vidiconSelect').value;

		var queryCond = "";
		
		if (vidiconID != null && vidiconID.length>0){
		   queryCond += " AND GLID ='" + vidiconID + "'";
		}
		if (deptidForCenter != ""){
		   queryCond += " AND B.JGCCBM LIKE '"+jgccbm+"%'";
		}

		//形成SQL语句
		var strSql = "SELECT DISTINCT GLID,CPMC FROM ATMS_EQUIPMENT_ZB A,T_SYS_DEPARTMENT B ";
		strSql += " WHERE A.SSJG=B.JGID AND SSXT='CCTV' AND LONGITUDE IS NOT NULL AND LATITUDE IS NOT NULL";
		if (queryCond != "") {
			strSql += queryCond;
		}
		strSql += " ORDER BY GLID";

		//调用分页控制类
		PageCtrl.initPage("queryResultCCTV","vidPageData","vidPageNav",convertSql(strSql),"vidiconmap.callBackVidiconList",15);		
 	},

   	/**
	 * 获取摄像机列表回调函数
	 * @xmlDoc: Ajax调用的回传数据
	 */
	callBackVidiconList: function(xmlDoc)
	{		    	
		var strTable = "<table id=\"tableList\" class=\"scrollTable\" width=100% cellSpacing=0 cellPadding=0 border=0 style=\"border:1px solid #97BECE;\">";			
		
		//生成表头
		strTable += "<tr class=\"scrollColThead\">";
		strTable += "<th class=\"scrollRowThead scrollCR\" align=center width=15% background=\"../../image/back/title_back.gif\">选择</th>";
		strTable += "<th style=\"display:none;\" align=\"center\" width=0% class=\"title\">摄像机编号</th>";
		strTable += "<th align=\"center\" width=60% class=\"title\">摄像机名称</th>";
		strTable += "</tr>";	
		
		//生成结果页面
		var results = xmlDoc.getElementsByTagName("row");
		for (var i = 0; i < results.length; i++){
			var rowResult = results[i].childNodes;							
			var vidiconID = "";					
			var vidiconIDObj = rowResult[0].firstChild;
			if (vidiconIDObj != null){
				vidiconID = vidiconIDObj.nodeValue;
			}
				
			var vidiconName = "";
			var vidiconNameObj = rowResult[1].firstChild;
			if (vidiconNameObj != null){
				vidiconName = vidiconNameObj.nodeValue;
			}
			

			strTable += "<tr class=\"rowstyle\" onmouseover=\"mouseover(this,vidiconmap.selectBGColor)\" onmouseout=\"mouseout(this,vidiconmap.selectBGColor)\" onclick=\"vidiconmap.setVidiconInfo('"+vidiconID+"','"+vidiconName+"',"+(i+1)+");\">";					    
		    strTable += "<td class=\"scrollRowThead\" align=\"center\"><input type=\"checkbox\" id=\"chkSel"+i+"\" onclick=\"vidiconmap.isClickedCheckBox = true;\"/></td>";
		    strTable += "<td style=\"display:none;\">" + vidiconID + "</td>";
		    strTable += "<td>" + vidiconName + "</td>";
		    strTable += "</tr>";						
		}
		strTable += "</table>";

       //添加到结果面板上
        var conCtrl= document.getElementById("vidPageData");
        conCtrl.innerHTML = strTable;	       	
    },

	/**
	 * 居中显示摄像机
	 * @id：摄像机编码
	 */
 	centerVidiconInfo : function(id){
 		var blnFind = false;
 		var index = -1;
 		if(typeof(vidiconList)=="undefined" || vidiconList == null){
 			return;
 		}
 
 		for(var i = 0; i < vidiconList.length; i++){				
		    if(vidiconList[i][0] == id){
		    	blnFind = true;
		    	index = i;
		    	break;
		    }	
		}
		
		if(blnFind == true){
	    	var mapDiv = map.getVMLDiv();
	    	var model = map.getMapModel();
	    	var currentZoom =  map.getCurrentZoom();

			//闪烁显示摄像机
			MapUtils.prototype.flashPoint(mapDiv, vidiconList[index][1].split(","),currentZoom,2);	
			
			//显示选中的摄像机详细信息				
			var coordArray = vidiconList[index][1].split(",");
			var infoCoord = new Coordinate(coordArray[0]*1e16 , coordArray[1]*1e16);
			var scoord = Util.getScreenPixel(infoCoord, currentZoom);//由经纬度转屏幕坐标.				
			var left = scoord.x;
			var top =  scoord.y;			
			this.showMapTips(null,left,top,infoCoord);
		}
 	},
 	
   	/**
	 * 设置摄像机标注信息.
	 * @id：摄像机编码
	 * @name：摄像机名称
	 * @index：列表序号
	 */
	setVidiconInfo: function(id,name,index){	
		
		//控制选中状态
		var tableObj = document.getElementById("tableList");	
		var checkboxObj = tableObj.rows[index].cells[0].firstChild;

		if (index == vidiconmap.rowID){		
		    if (this.isClickedCheckBox){
				this.isClickedCheckBox = false;					
		        if (checkboxObj.checked){
					tableObj.rows[index].bgColor = this.selectBGColor;			
				}else{
					tableObj.rows[index].bgColor = this.unSelectBGColor;			
				}
			}else{	
		        if (checkboxObj.checked){
					checkboxObj.checked = false;
					tableObj.rows[index].bgColor = this.unSelectBGColor;			
				}else{
					checkboxObj.checked = true;	
					tableObj.rows[index].bgColor = this.selectBGColor;			
				}
			}			
		}else{	
			checkboxObj.checked = true;
			tableObj.rows[index].bgColor = this.selectBGColor;			
		}

		for(var i=1; i<tableObj.rows.length; i++){	
           if (i != index){
           	  tableObj.rows[i].cells[0].firstChild.checked = false;
			  tableObj.rows[i].bgColor = this.unSelectBGColor;			
           }
 		}

        //置摄像机标注信息
        if (checkboxObj.checked){
	        this.vidiconID = id;       
	        this.vidiconName = name;
			this.rowID = index;
		}
		
 		//居中显示摄像机，并显示详细信息
 		this.centerVidiconInfo(id);	
 	},
 	
 	/**
	 * 构建摄像机查询面板.
	 */
 	outputHTML: function(){
	  htmlTxt = '\
	    <div id="queryCond" class="queryDiv" style="offsetTop:0px">\
		  <table class="top_table">\
			<tbody>\
				<tr height="5">\
					<td colspan="4" align="center">&nbsp;</td>\
				</tr>\
				<tr style="display:none">\
					<td colspan="4" align="center"><a href="FileDown.jsp?file=cctv/activex/EhlCctvActiveX.CAB&fileName=EhlCctvActiveX.CAB">下载视频控件，解压后运行setup.exe安装</a></td>\
				</tr>\
				<tr>\
					<td width="10">&nbsp;</td>\
					<td align="right">摄像机名称：</td>\
					<td id="vidiconName"></td>\
					<td align="center"><img id="btnSearch" src="../../image/button/btnquery.gif" style="cursor:hand" onclick="vidiconmap.getVidiconList();"></td>\
				</tr>\
			</tbody>\
		  </table>\
		</div>\
		<div id="queryResultCCTV" class="scrollDiv" style="offsetTop:0px;position: absolute;zindex:-1"></div>';

	  return htmlTxt;
	},
	
	/**
     * 打开视频.
     * devID-设备编号[tgsId或者camId];
     * ctrlType-控制类型[(play-查看 默认) 或者 back-回放)]
     * ssxt-所属系统 [目前仅支持(CCTV 默认)和TGS,对应ZB表所属系统]
     */
	showVideo: function(devID,ctrlType,ssxt){
		if(!ctrlType){
	  		ctrlType = 'play';
	  	}
		if(!ssxt){
			ssxt = 'CCTV';
		}
		if(ssxt=="CCTV"){
			var url = "../../../cctv/ehl/cctv/CctvViewer.jsp?CamID=" + encodeURI(devID) + "&type="+ctrlType;
		 	window.showModalDialog(url,"","dialogWidth:815px;dialogHeight:556px");
		}else if(ssxt=="TGS"){
			if (devID != "") {
				var url = "cctv.vidicon.getChannelInfo.d?camid="+devID;
				url = encodeURI(url);
				var params = "";
				url = encodeURI(url);		
				new Ajax.Request(url, 
					{
						method:"post", parameters:params, 
						onComplete:function(xmlHttp){
							var xmldoc   = xmlHttp.responseXML;
						
							var results = xmldoc.getElementsByTagName("col");
							if(results!=null&&results.length>0){
								var DeviceIP = results[0].text;
								var DevicePort = results[1].text;
								var DeviceUsername = results[2].text;
								var DevicePasswd = results[3].text;
								var ChannelList = results[4].text;
								var cpmc = results[5].text;
								
						    	var url = "../../../cctv/ehl/cctv/GdTgs.jsp?DeviceIP='" + DeviceIP+ "'&DevicePort='"+DevicePort 
						    			  + "'&DeviceUsername='"+DeviceUsername+"'&DevicePasswd='"+DevicePasswd+"'&ChannelList="+ChannelList+"&cpmc="+cpmc;
						    	url = encodeURI(url);
						    	var wLeft = (screen.availWidth - 680) / 2;
						    	var wTop = (screen.availHeight - 560) / 2;
						    	window.open(url,"","height=300,width=400,left=" + wLeft +",top=" + wTop + ",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=yes");
							}else{
								alert("卡口视频通道异常！");
							}
						}
					}
				);
			}else{
				alert("卡口视频通讯异常！");
			}
	  }
	}
};