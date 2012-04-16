//可以打包为js文件;    
var x0=0,y0=0,x1=0,y1=0;    
var offx=6,offy=6;    
var moveable=false;    
var hover='#336699',normal='#336699';//color;    
var index=10000;//z-index;    
//开始拖动;    
function startDrag(obj)    
{    
	if(event.button==1)    
	{    
		//锁定标题栏;    
		obj.setCapture();    
		//定义对象;    
		var win = obj.parentNode;    
		var sha = win.nextSibling;    
		//记录鼠标和层位置;    
		x0 = event.clientX;    
		y0 = event.clientY;    
		x1 = parseInt(win.style.left);    
		y1 = parseInt(win.style.top);    
		//记录颜色;
		normal = obj.style.backgroundColor;    
		//改变风格;    
		obj.style.backgroundColor = hover;    
		win.style.borderColor = hover;    
		obj.nextSibling.style.color = hover;    
		sha.style.left = x1 + offx;    
		sha.style.top = y1 + offy;    
		moveable = true;    
	}    
}    
//拖动;    
function drag(obj)    
{    
	if(moveable)    
	{    
		var win = obj.parentNode;    
		var sha = win.nextSibling;    
		win.style.left = x1 + event.clientX - x0;    
		win.style.top = y1 + event.clientY - y0;    
		sha.style.left = parseInt(win.style.left) + offx;    
		sha.style.top = parseInt(win.style.top) + offy;    
	}    
}
//停止拖动;    
function stopDrag(obj)    
{
	if(moveable)    
	{    
		var win = obj.parentNode;    
		var sha = win.nextSibling;    
		var msg = obj.nextSibling;    
		win.style.borderColor = normal;    
		obj.style.backgroundColor = normal;    
		msg.style.color = normal;    
		sha.style.left = obj.parentNode.style.left;    
		sha.style.top = obj.parentNode.style.top;    
		obj.releaseCapture();    
		moveable = false;    
	}    
}
//获得焦点;    
function getFocus(obj)    
{
	if(obj.style.zIndex!=index)    
	{    
		index = index + 2;    
		var idx = index;    
		obj.style.zIndex=idx;    
		obj.nextSibling.style.zIndex=idx-1;    
	}    
}    
//最小化;    
function min(obj)    
{    
	var win = obj.parentNode.parentNode;    
	var sha = win.nextSibling;    
	var tit = obj.parentNode;    
	var msg = tit.nextSibling;    
	var flg = msg.style.display=="none";    
	if(flg)    
	{    
		win.style.height = parseInt(msg.style.height) + parseInt(tit.style.height) + 2*2;    
		sha.style.height = win.style.height;    
		msg.style.display = "block";    
		obj.innerHTML = "0";    
	}    
	else   
	{    
		win.style.height = parseInt(tit.style.height) + 2*2;    
		sha.style.height = win.style.height;    
		obj.innerHTML = "2";    
		msg.style.display = "none";    
	}    
}    
//创建一个对象; 
/**
 * param id 生成的窗口对象id
 * param w  窗口宽度
 * param h  窗口高度
 * param l  左边距
 * param t  顶边距
 * param tit 窗口标题
 * param msg 窗口内信息
 */   
function xWin(id,w,h,l,t,tit,msg)    
{    
	var divwindow=document.getElementById("xMsg"+id);
	var bgwindow=document.getElementById("xMsg"+id+"bg");	
	if(divwindow){
		divwindow.parentNode.removeChild(divwindow);
	}
	if(bgwindow){
		bgwindow.parentNode.removeChild(bgwindow);
	}
	
	
	index = index+2;    
	this.id = id;    
	this.width = w;    
	this.height = h;    
	this.left = l;    
	this.top = t;    
	this.zIndex = index;    
	this.title = tit;    
	this.message = msg;    
	this.obj = null;    
	this.bulid = bulid;    
	this.bulid();    
}    
//初始化;    
function bulid()    
{    
	var str = ""   
	+ "<div id=xMsg" + this.id + " "   
	+ "style='"   
	+ "z-index:" + this.zIndex + ";"   
	+ "width:" + this.width + ";"   
	+ "height:" + this.height + ";"   
	+ "left:" + this.left + ";"   
	+ "top:" + this.top + ";"   
	+ "background-color:" + normal + ";"   
	+ "color:" + normal + ";"   
	+ "font-size:8pt;"   
	+ "font-family:Tahoma;"   
	+ "position:absolute;"   
	+ "cursor:default;"   
	+ "border:2px solid " + normal + ";"   
	+ "' "   
	+ "onmousedown='getFocus(this)'>"   
	+ "<div "   
	+ "style='"   
	+ "background-color:" + normal + ";"   
	+ "width:" + (this.width-2*2) + ";"   
	+ "height:20;"   
	+ "color:white;"   
	+ "' "   
	+ "onmousedown='startDrag(this)' "   
	+ "onmouseup='stopDrag(this)' "   
	+ "onmousemove='drag(this)' "   
	+ "ondblclick='min(this.childNodes[1])'"   
	+ ">"   
	+ "<span style='width:" + (this.width-2*12-4) + ";padding-left:3px;'>" + this.title + "</span>"   
	+ "<span style='width:12;border-width:0px;color:white;font-family:webdings;' onclick='min(this)'>0</span>"   
	+ "<span style='width:12;border-width:0px;color:white;font-family:webdings;' onclick='ShowHide(\""+this.id+"\",null)'>r</span>"   
	+ "</div>"   
	+ "<div style='"   
	+ "width:100%;"   
	+ "height:" + (this.height-20-4) + ";"   
	+ "background-color:white;"   
	+ "line-height:14px;"   
	+ "word-break:break-all;"   
	+ "padding:3px;"
	+" overflow: auto;"   
	+ "'>" + this.message + "</div>"   
	+ "</div>"   
	+ "<div id=xMsg" + this.id + "bg style='"   
	+ "width:" + this.width + ";"   
	+ "height:" + this.height + ";"   
	+ "top:" + this.top + ";"   
	+ "left:" + this.left + ";"   
	+ "z-index:" + (this.zIndex-1) + ";"   
	+ "position:absolute;"   
	+ "background-color:black;"   
	+ "filter:alpha(opacity=40);"   
	+ "'>"	
	+"</div>";    
	document.body.insertAdjacentHTML("beforeEnd",str);    
}    
//显示隐藏窗口    
function ShowHide(id,dis){    
//	var bdisplay = (dis==null)?((document.getElementById("xMsg"+id).style.display=="")?"none":""):dis    
//	document.getElementById("xMsg"+id).style.display = bdisplay;    
//	document.getElementById("xMsg"+id+"bg").style.display = bdisplay;    

	var windowDiv=document.getElementById("xMsg"+id);
	var bgDiv=document.getElementById("xMsg"+id+"bg");
	windowDiv.parentNode.removeChild(windowDiv);
	bgDiv.parentNode.removeChild(bgDiv);
}    
//-->    
 
<!--    
function initialize()    
{    
	var a = new xWin("1",160,200,200,200,"窗口1","这是窗口1");    
	var b = new xWin("2",240,200,100,100,"窗口2","这是窗口2");     
	var c = new xWin("3",200,160,250,50,"窗口3","这是窗口3");    
	ShowHide("1","none");//隐藏窗口1    
}    
//window.onload = initialize;   
// 

function openChkDivWindow(obj,top,left,width,height,okmethod,canclemethod,title){
	
	var useDiv = document.createElement("div");
	useDiv.id="usedivs";
//	useDiv.style.backgroundColor="#DFEAF7";
	useDiv.style.width=width;
	useDiv.style.height=height;
	useDiv.style.textAlign="center";
	useDiv.style.zIndex = 100000;
	useDiv.style.position="absolute";
 	if(top==null||top==""){
 		top=0;
 	}
 	if(left==null||left==""){
 		left=0;
 	}
 	useDiv.style.top=top;
 	useDiv.style.left=left; 	
 	
 	useDiv.innerHTML='\
           <div >\
           <table id="poptable" width="100%" height="100%" style="border=0" class="popup" cellpadding="0" cellspacing="0">\
            <tbody>\
              <tr height="5%" class="scrollColThead">\
                <td class="corner" id="topleft"></td>\
                <td class="top">' + title + '</td>\
                <td class="top" align="right" valign="middle">\
                  <img src="../../../sm/image/popup/ok.gif" border="0" style="cursor:pointer" onclick="' + okmethod + '">\
                  <img src="../../../sm/image/popup/cancel.gif" border="0" style="cursor:pointer" onclick="' + canclemethod + '">\
                </td>\
                <td class="corner" id="topright"></td>\
              </tr>\
              <tr height="92%" valign="top">\
                <td class="left"></td>\
                <td id="popup-contents" bgcolor="#DFEAF7" colspan="2">\
                   <div style="weight:180;height:298;overflow-y:scroll;" id="tdcontain"></div>\
                </td>\
                <td class="right"></td>\
              </tr>\
              <tr height="3%">\
                <td id="bottomleft" class="corner"></td>\
                <td class="bottom" colspan="2"></td>\
                <td id="bottomright" class="corner"></td>\
              </tr>\
            </tbody>\
            </table>\
          </div>';	
	document.body.appendChild(useDiv);
	$("tdcontain").style.scrollbarBaseColor="#DFEAF7";
	$("tdcontain").appendChild(obj);	
}	

function closeDivWindow(){
	var divPopup = document.getElementById("useDivs");
	if((typeof(divPopup) != "undefined") && (divPopup != null)){
    	divPopup.parentNode.removeChild(divPopup);
    }
}


