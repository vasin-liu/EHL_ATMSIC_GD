/*
 * 路况发布类
 */
vmlArray=null;//vml字符串数组
index=-1;    //渐变绘线当前路线下标
vmlDiv=null;//vml容器
TfmMap = Class.create();
showFlowType = "";//显示类型全局变量
centerx = "";//地图中心点横坐标
centery = "";//地图中心点纵坐标
TfmMap.prototype =
{ 
	/**
	 *  定义存储路段坐标数组
	 */
	roadLineList : null,
	resultLineList:null,
	timer: null,
	/**
     * 构造函数.
     * @param {Object} container
     */
    initialize: function(container)
    {
        this.container = container;
		this.selectBGColor = "#D8E8FF"; //选中背景颜色
	    this.unSelectBGColor = "#FFFFFF"; //未选中背景颜色
    },	       
   
    /**
	 *	读取道路流量数据
	 */
	readRoadFlow : function()
	{   
	    //移动并缩放地图		var centPoint = "";
		var x = getMapParameter("gisfactcenterx");//经度
		var y = getMapParameter("gisfactcentery");//经度
		var coordX = Number(x) * 1e16;
		var coordY = Number(y) * 1e16;
		centPoint = new Point(coordX, coordY);
		level = getMapParameter("gisFlowLevel");
	     //map.moveToCenter(centPoint,Number(level));
	    
	    //显示提示信息窗口	
	    var container = map.getVMLDiv();
	    var zoom =  map.getCurrentZoom();
		var coord = new Coordinate((x*1e16), (y*1e16));//将坐标转化为整形
  		var sCoord = Util.getScreenPixel(coord, zoom);//由经纬度转屏幕坐标.
		MapTips.prototype.showLoadTips(container,sCoord.x,sCoord.y); 
		
		centerx = sCoord.x;
		centery = sCoord.y;
		//读取路线
		url = 'tfm.tfmMap.readRoadFlow.d';
		var params = "zoomLevel=" + level + "&isRedraw=false";
		params = encodeURI(params);
		new Ajax.Request(url, 
		{
				method: 'post', 
				parameters: params, 
				
				//读取完成后，放入内存数组
				onComplete: TfmMap.prototype.finishReadFlow
		});			
	},	
	      
	/**
     ** 完成服务端读取后，将获取到的流量数据存入到内存数组中
     */
	finishReadFlow: function(xmlHttp)
	{  //取消进度提示
		var divPopup = document.getElementById("popup");
	    divPopup.innerHTML = ""; 
		//获取服务器返回字符串
		var xmlStr = xmlHttp.responseText;
		//测试
//		var container =  map.getVMLDiv(); ;
//		container.innerHTML = xmlStr;
//		return;
		
		var returnArray = xmlStr.split("<linevml>");
    	var roads = returnArray[0].split("<road>");
      	var linevmlStr = returnArray[1];
     	var container = document.getElementById("news");
      	 //获取地图图层和当前显示范围
    	var mapDiv = map.getVMLDiv();   	
    	var model = map.getMapModel();
    	var zoom =  map.getCurrentZoom();
    	//绘流量线
    	vmlArray = linevmlStr.split("lineEnd");
    	index = -1;
    	vmlDiv = mapDiv;
    	drawLineByStep();
        if (roads != null && roads.length > 1){
            roadLineList = new Array();
            resultLineList = new Array();
          	var points = new Array();	 
          	var height =  container.style.height;  
          	var divStr = new Array();
          	divStr.push("<div style='overflow-y:scroll'>");
	       // divStr.push("<MARQUEE id='mar' style='WIDTH: 290px;text-align:left;HEIGHT:"+height+"' SCROLLDELAY:'150' scrollAmount=2 direction=up onmouseover='TfmMap.prototype.over();' onmouseout='TfmMap.prototype.out();'>");
			var points = new Array(); 
			var pline = "";
			for (var i = 0; i < roads.length; i++)
			{
			 var results = roads[i].split("<col>");
				var rowArray = new Array(6);
				rowArray[0] = results[0];//路线编码
				rowArray[1] = results[1];//路线名称
				rowArray[2] = results[2];//方向编号
				rowArray[3] = results[3];//路段状况
				rowArray[4] = results[4];//所含路段数量
				//根据方向编号和路段状况，生成用于发布的文本字符串
				if(results[3] != "" && typeof(results[3]) != "undefined"){
				  divStr.push("<div >");
				  divStr.push("<a href=\"javascript:TfmMap.prototype.centerTheRoad('"+results[0]+":"+results[2]+":0')\">");
				  divStr.push("<font color='black'>"+results[1] + results[2] +"</font>");
				  divStr.push("<font color='"+ TfmMap.prototype.getRoadColor(results[3])+"'>"+results[3]+"</font></a>");
				  if(results[4] != "" &&  results[5] != ""){
				      divStr.push("&nbsp;<image align='right' src='../../image/map/vidicon.gif' style=\"cursor:hand\" title='"+results[5]+"' onclick=\"TfmMap.prototype.showVideo('"+results[4]+"')\"></image>&nbsp;&nbsp;");
				  }
				  if(results[6] != "" &&  results[7] != ""){
				      divStr.push("<image align='right' src='../../image/map/vidicon.gif' style=\"cursor:hand\" title='"+results[7]+"' onclick=\"TfmMap.prototype.showVideo('"+results[6]+"')\"></image>");
				  }
				  divStr.push("</div>");
				}
				roadLineList.push(rowArray);
			}
			//divStr.push("</MARQUEE>");
			divStr.push("</div >");
			if(false){
			   container.innerHTML = divStr.join("");
			}
		}else{
		     container.innerHTML = "";
		}
		//取消进度提示
		var divPopup = document.getElementById("popup");
	    divPopup.innerHTML = "";
	},
	
	over:function(){
	   var obj = document.getElementById("mar");
	   obj.stop();
	},
	
	out:function(){
	var obj = document.getElementById("mar");
	  
	   obj.start();
	},
	
	//发布信息与地图联动，居中并展示路况
	centerTheRoad:function(roadId){
	    var roadObj = document.getElementById(roadId);
	    if(roadObj != null){
	        var segpoint = roadObj.segpoints.split(";");
	        var perpoint = segpoint[0].split(",");
	        var offset = segpoint[segpoint.length-1].split(",");
	        var zoom =  map.getCurrentZoom();
	        var myLevel = zoom.getLevel();
	        //移动并缩放地图
			var centPoint = "";
			var coordX = Number(perpoint[0]) * 1e16;
			var coordY = Number(perpoint[1]) * 1e16;
			centPoint = new Point(coordX + parseFloat(offset[0]), coordY +parseFloat(offset[1]));		
			map.moveToCenter(centPoint,myLevel);
			
			var mapDiv = map.getVMLDiv();
	    	var model = map.getMapModel();
	    	var currentZoom =  map.getCurrentZoom();
			//闪烁显示卡口
			MapUtils.prototype.flashPoint(mapDiv, perpoint,currentZoom,myLevel);
            //显示选中的卡口详细信息				
			var infoCoord = new Coordinate(perpoint[0]*1e16 + parseFloat(offset[0]), perpoint[1]*1e16 + parseFloat(offset[1]));
			var scoord = Util.getScreenPixel(infoCoord, currentZoom);//由经纬度转屏幕坐标.				
			var left = scoord.x;
			var top =  scoord.y;
			this.showMapTips(null,left,top,roadId);
	    }
	},
	
	/**
	 ** 显示提示窗口窗体
	 */
	showMapTips: function(event,left,top,roadId)
	{	
	    var x = 0;
	    var y = 0;
	    var container;
	    //取得鼠标的绝对坐标
	    container = map.getVMLDiv();
	    if(event != null)
	    {	    	
			event = event ? event : (window.event ? window.event : null); 
			var source = event.srcElement || event.target;
		    x = Event.pointerX(event) - container.offsetLeft||container.scrollLeft;
			y = Event.pointerY(event) - container.offsetTop||container.scrollTop;
	    }	
	    else
	    {
	     	x = left ;
	     	y = top;
	    }		    
		//显示正在查询提示		
		MapTips.prototype.showLoadTips(container,x,y);				
		//执行循环匹配当前选中道路，并展示其详细信息
		var roadInfo = "";
		for(var i = 0; i < roadLineList.length; i ++){
		    if(roadLineList[i][0]+":"+roadLineList[i][2]+":0"== roadId){
		        roadInfo += "道路名称：" + roadLineList[i][1] +"<br>";
		        roadInfo += "道路方向：" + roadLineList[i][2] +"<br>";
		        roadInfo += "交通路况：" + roadLineList[i][3] +"<br>";
		    }
		}
		MapTips.prototype.showPopup(container,"交通路况",roadInfo,x,y)	;
	},
	/**
  * 根据路段状态编码,返回画线颜色.
  * @param  
  * @result 
  */
  getRoadColor:function(ldzk){
     var roadColor = "gray";
     if(ldzk == "畅通" ){
       roadColor = "green";
     }
    if(ldzk == "拥堵"){
       roadColor = "red";
     }
    if(ldzk == "拥挤"){
       roadColor = "yellow";
     }
     return roadColor;
  },

 	/**
	 *desc: 根据showFlowType所指状态重绘流量线(不重新读库)
	 *param: isOnlyMove-是否只是平移地图 "no"-不只是平移 "yes"-只是平移
	 */
	redrawFlow : function(isOnlyMove)
	{  
	   var container = map.getVMLDiv();
	    var zoom =  map.getCurrentZoom();
	   //如果只是平移地图，则什么也不做
	   if(isOnlyMove =="yes" && level == zoom.getLevel()){
	       return;
	   }
	   var tfmdiv = document.getElementById("tfmdiv");
	   if(tfmdiv != null){
	         TfmMap.prototype.removeVmlLine();
	   }
	   if(showFlowType == ""){
	       showFlowType = "all";   
	   }
	   //进度提示
		var centPoint = "";
		var x = getMapParameter("gisfactcenterx");//经度
		var y = getMapParameter("gisfactcentery");//经度
		var coordX = Number(x) * 1e16;
		var coordY = Number(y) * 1e16;
		
	    //显示提示信息窗口	
		var coord = new Coordinate((x*1e16), (y*1e16));//将坐标转化为整形
  		var sCoord = Util.getScreenPixel(coord, zoom);//由经纬度转屏幕坐标.
		MapTips.prototype.showLoadTips(container,sCoord.x,sCoord.y);  
		level = zoom.getLevel();
		//读取路线
		url = 'tfm.tfmMap.readRoadFlow.d';
		
		var params = "zoomLevel=" + level +"&roadstatus=" + showFlowType +"&isRedraw=true" ;;
		params = encodeURI(params);
		new Ajax.Request(url, 
		{
				method: 'post', 
				parameters: params, 
				
				//读取完成后，放入内存数组
				onComplete: TfmMap.prototype.finishReadFlow
		});			
	    		
	},
	
	
 	/* desc 根据状态过滤流量信息
 	*  param showType-要显示的流量状态
 	*/
   showScrollInfo:function (showType){
        showFlowType = showType;//显示类型全局变量
        if(typeof roadLineList == "undefined" || roadLineList == null ||roadLineList.length == 0){
            alert("路况信息未加载完或没有要发布的数据!");
            return;
        }
        var container = document.getElementById("news");
        var height =  container.style.height; 
        var mapDiv = map.getVMLDiv(); 
        var divs = mapDiv.getElementsByTagName("div");
        var len = divs.length;
        while(len > 0){
           mapDiv.removeChild(divs[len-1]);
           len--;
        }
		TfmMap.prototype.redrawFlow("no");
   },
   
   /* desc 删除流量线
 	*/
   removeVmlLine:function(){
        var mapDiv = map.getVMLDiv(); 
        var divs = mapDiv.getElementsByTagName("div");
        var len = divs.length;
        while(len > 0){
           if(divs[len-1].id == "tfmdiv"){
               mapDiv.removeChild(divs[len-1]);
           }
           len--;
        }
   },
   /**
     * 打开视频.
     */
	showVideo: function(camID,type){
	   	 var url = "../../../cctv//ehl/cctv/VideoPlay.jsp?CamID='"+ encodeURI(camID)+"'";
	     window.showModalDialog(url,"","dialogWidth:606px;dialogHeight:437px");
	},
	
	/**
	 * 作者：zhaooy
	 * 日期: 2008-9-22
	 * 作用：根据查询数据设置定时器
	*/
	refreshList:function() {
	  timer = setInterval(this.readRoadFlow, parseInt(5)*1000*60);	  
	}
 };

   function changBgImg(){
    var obj= document.getElementById("im");
    obj.src = "../../image/biggest.gif";
   }
   
   function oldBgImg(){
    var obj= document.getElementById("im");
    obj.src = "../../image/biggest2.gif";
   }
    
   //地图全屏
   function showGlobalMap(){
     window.open("tfmMapBig.jsp",'newwindow','height='+window.screen.height+',width='+window.screen.width + ",left=0,top=0,toolbar=no,menubar=no,scrollbar=no,locationn=no,status=yes"); 

     var obj= document.getElementById("im");
     obj.blur();
   }
   
   //调整地图区大小
   function adapt(){
   	   var obj = document.getElementById("map");
   	   obj.style.height = document.body.clientHeight ;
   	   obj.style.width = document.body.clientWidth;
   }
   
   //更新连线数据
   function synLinkDir(){
       var container = map.getVMLDiv();
       MapTips.prototype.showLoadTips(container,centerx,centery);  
       url = 'tfm.tfmMap.synLinkDir.d';
		var params = "";
		new Ajax.Request(url, 
		{
				method: 'post', 
				parameters: params, 
				//读取完成后，放入内存数组
				onComplete: showSynTip
		})
   }
   
   function showSynTip(xmlHttp){
       //Popup.prototype.hideTips();
       //取消进度提示
		var divPopup = document.getElementById("popup");
	    divPopup.innerHTML = "";
        alert(xmlHttp.responseText);
   }
   
	function drawLineByStep(){
	    var temArray = new Array();
		for(var i=index+1;i-index<100;i++){
		    if(i< vmlArray.length){
		        temArray.push(vmlArray[i]);
		    }
		}
		var divobj = document.createElement('div');
		divobj.setAttribute("id","tfmdiv");
		vmlDiv.appendChild(divobj);
		divobj.innerHTML = '<table>'+temArray.join('')+'</table>';
		index=--i;
		if(index<vmlArray.length){
			setTimeout(drawLineByStep,0)
		}
	}