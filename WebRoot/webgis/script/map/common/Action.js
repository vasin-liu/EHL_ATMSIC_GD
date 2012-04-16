/**
 ** 鼠标移入
 */
function mouseover(obj,color){ 
	if (obj.bgColor != color.toLowerCase()){
		obj.bgColor = "#117FC9"; 
	}
}

/**
 ** 鼠标移出
 */
function mouseout(obj,color){
	if (obj.bgColor != color.toLowerCase()){
		obj.bgColor = "#FFFFFF"; 
	}
}

/**
 ** 显示/隐藏面板
 ** @show 是否显示右侧的面板，如果为true,则显示，反之，不显示
 **if mapresize==true ,mapletResize
 */
function showRightPanel(show,mapresize)
{
    if($('rightPanel').style.display=='none'||show==true)
    {
        $('rightPanel').style.display='';
        $('leftPanel').style.marginRight='310px';
        $('middlePanel').style.marginRight='300px';
        $('middlePanel').getElementsByTagName("img")[0].src="images/navigator/show-arrow.png";
    }
    else
    {
        $('rightPanel').style.display='none';
        $('middlePanel').getElementsByTagName("img")[0].src="images/navigator/hide-arrow.png";
        $('leftPanel').style.marginRight='15px';
        $('middlePanel').style.marginRight='5px';
    }
	//if(mapresize)mapletResize();
}

/**
 ** 获取地图大小，并按照新的大小改变
 ** x:the x-distance beteen mapbar and mapbar'parent
 ** y:the y-distance beteen mapbar and mapbar'parent
 */
function mapletResize(x,y)
{
    x=x?parseInt(x):0;
    y=y?parseInt(y):20;
    var mapPNode = $("mapbar").parentNode;
	var w = mapPNode.clientWidth-x;
	var h = mapPNode.clientHeight-y;
    if(typeof maplet=="object")maplet.resize(w, h);
}