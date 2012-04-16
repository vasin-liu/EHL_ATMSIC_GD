/**
*@Fun：列表选择
*
*@Author：LChQ 2008-10-1
*
*@Version: 1.0 
*
*/

/** 定义一个Div
*	解析xml，将每一行(row)的第一列(col)作为id，第二列(col)作为名称显示
*
*/
function ListChose(clientX,clientY,srcObject,xmldoc)
{
    
    //div 容器
    var popupDiv = null;
   
    var nameTable = null; 
    var nameTableBody = null;
    
    //超过该数目时设定固定的高度
    var scrollSize = 6;

	var holderHeight = "160px";
	
	var holderWidth = "100px";
    /**清除该div
     *
     */
	var clearNames = function() 
	{
		
		var ind = nameTableBody.childNodes.length;
		for (var i = ind - 1; i >= 0 ; i--) 
		{
		     nameTableBody.removeChild(nameTableBody.childNodes[i]);
		}
		
		//popupDiv.style.border = "none";
		
		popupDiv.parentNode.removeChild(popupDiv);
	};
	
	/**初始化设定各个元素
	*
	*/
	var  initVars = function() 
	{
		popupDiv = document.getElementById('popup_div');
		nameTableBody = document.getElementById('name_table_body');
		if(popupDiv && nameTableBody)
		{
			clearNames();
		}
		
		//div 容器定义
		popupDiv = document.createElement("div");
		popupDiv.id = "popup_div";
		popupDiv.style.cssText = "position:absolute;overflow-y:scroll;z-index:5000";
		
		popupDiv.onmouseout = function() 
	    {
	    	if( event.srcElement.tagName.toLocaleLowerCase() == "div" )
	    	{
	    		//alert("div");
	    		this.parentNode.removeChild(this);
	    		//popupDiv.parentNode.removeChild(popupDiv);
	    	}
	    	/*else
	    	{
	    		alert(event.srcElement.tagName);
	    	}
	    	*/
		};
		
		//表框架定义
		nameTable = document.createElement("table");
		nameTable.id = "name_table";
		nameTable.style.cssText = "background-color:#FFFAFA;border:0;cellspacing:0;cellpadding:0";
		
		nameTableBody = document.createElement("tbody");
		nameTableBody.id = "name_table_body";
	 	nameTable.appendChild(nameTableBody);
	
		popupDiv.appendChild(nameTable);
		document.body.appendChild(popupDiv);
	};
     
    var poulateName = function(target)
    {
    	ListChose.close();
    };
    
    var cellClickHandler = poulateName;
    
    /**设置名称选择事件处理
    **
    *
    */
    this.setCellClickHandler = function( handler)
    {
    	cellClickHandler = handler;
    };
     /**计算坐标
     *
     */
     var calculateOffset = function(field, attr) 
     {
         var offset = 0;
         while(field) 
         {
           offset += field[attr]; 
           field = field.offsetParent;
         }
         return offset;
     };
     
     var calculateOffsetTop = function(field) 
     {
         return calculateOffset(field, "offsetTop");
     };
     
     var calculateOffsetLeft = function(field) 
     {
   		return calculateOffset(field, "offsetLeft");
     };
     
     var  setOffsets = function() 
     {
         //var end = inputField.offsetWidth;
         
         //设置框度
         var width = holderWidth;
       	 var field =  srcObject; //document.body;
         var left = calculateOffsetLeft(field)+  clientX; //field.offsetWidth;// +
         
         var top = calculateOffsetTop(field) + clientY; // field.offsetHeight;//

         popupDiv.style.border = "black 1px solid";
         popupDiv.style.left = left + "px";
         popupDiv.style.top = top + "px";
         nameTable.style.width = width;
         
         //alert("left=" + left +",top=" + top + "   x=" +clientX + ",y=" + clientY);
     };
     
     
    /**得到行数据，解析并生成表格元素
    *
    *@修改： 2008-1-08 添加项的 supplement 属性作为补充数据
    *
    */
	var  setNames = function(items,longtitude,latitude) 
	{            
		var size = items.length;
		var row, cell, txtNode;
		var itemId;
		var itemName;
		
		//大小设置
		if(scrollSize < size )
		{
			popupDiv.style.height = holderHeight;
		}
		
		for (var i = 0; i < size; i+=1) 
		{
			itemId   = items[i].getElementsByTagName("col")[0].firstChild.data;
        	var temp = items[i].getElementsByTagName("col")[1].firstChild;
        	itemName =  ( null == temp ? itemId:temp.data );
			
			//补充信息
			var supplement = null;
			
			if(3 <= items[i].getElementsByTagName("col").length )
			{
				temp = items[i].getElementsByTagName("col")[2].firstChild;;
				if (temp != null)
				{
					supplement = temp.nodeValue;
				}
			}
		    var nextNode = itemName;
		    row = document.createElement("tr");
		    cell = document.createElement("td");
		    
		    //设置鼠标事件发生时的样式
		    cell.onmouseout = function() 
		    {
		    	this.style.cssText = "background: #FFFAFA; color: #000000;";
		    	//this.className='mouseOver';
		    };
		    cell.onmouseover = function() 
		    {
		    	this.style.cssText =" background: #708090; color: #FFFAFA;";
		    	//this.className='mouseOut';
		    };
		    cell.setAttribute("bgcolor", "#FFFAFA");
		    cell.setAttribute("border", "0");
		    cell.onclick = function() 
		    { 
		    	cellClickHandler(this); 
		    };                             
			cell.itemId = itemId;
			cell.longtitude = longtitude;
			cell.latitude = latitude;
			
			if(supplement)
			{
				cell.supplement = supplement;
			}
			//添加节点
		    txtNode = document.createTextNode(nextNode);
		    cell.appendChild(txtNode);
		    row.appendChild(cell);
		    nameTableBody.appendChild(row);
		}
	};
     
   /**解析xmldoc 
    *
    * 生成名称列表
    */
    var buildNameList = function() 
    {
    	var theItems = xmldoc.getElementsByTagName("row");
    	var size = theItems.length;
    	
    	var coordRow = xmldoc.getElementsByTagName("coord");
    	var longtitude = "";
    	var latitude   = "";
    	if(coordRow && 1== coordRow.length )
    	{
    		var coord = coordRow[0].text;
    		if(null != coord && 1 < coord.length )
     		{
     			longtitude = coord.split(",")[0];
     			latitude = coord.split(",")[1];
     		}
    	}
    	//设置显示位置
    	setOffsets();
    	setNames(theItems,longtitude,latitude);
    };
	
	/**
	*	初始化对象，建立结构
	*
	*/
	initVars();
	
	/**
	*	建立名称列表
	*/
   	buildNameList();
   	
   	
}
/**关闭选择面板
*
*/
ListChose.close = function()
{
	var popupDiv = document.getElementById('popup_div');
	if(popupDiv)
	{
		popupDiv.parentNode.removeChild(popupDiv);
	}
};
/**
 * 函数功能：填充列表框，发送Ajax请求数据
 * 参数说明：containerID-加载列表框容器ID;listID-列表框ID;listWidth-宽度;exeSql-SQL语句;addItem-附加项描述(默认"全部"); selectedValue被选种的项的值.
 * 调用实例：DropDownList("tdsex","sex","150","SELECT dm,dmsm FROM td_code WHERE dmlb='1005'","全部",1)
 */
function DropdownList(containerID,listID,listWidth,exeSql,addItem,selectedValue)
{
	var theList = this;
	this.parentId = containerID;
	this.dpListId = listID;
	this.width    = listWidth;
	this.exeSQL   = exeSql;
	this.addtionalItem = addItem;
	this.selectedItemValue = selectedValue;
	this.selectIndexChanged;
	this.onBuildReady;
	this.initialization = function()
	{	
		var url = 'common.fillListBox.createList.d';
		url = encodeURI(url);
		var params = "&ContainerID="+containerID+"&ListID="+listID+"&ListWidth="+listWidth+"&ExeSql="+exeSql.replace(/=/g,"@")+"&AllDesc="+addItem;
		params = encodeURI(params);
		new Ajax.Request(url, {method: 'post', parameters: params, onComplete:this.showDplistResponse});
	};
	
	this.showDplistResponse = function(xmlHttp)
	{
	   var strText = xmlHttp.responseText;
	   var splitLoc = strText.split("&");
	   var parentObj = document.getElementById(theList.parentId);
	   parentObj.innerHTML = splitLoc[2];
	   var listObj = document.getElementById(theList.dpListId);
		if(theList.selectedItemValue)
		{
			//设置初始选择项

			if(listObj)
			{
				for (var i=0; i<listObj.length;i+=1)
				{
			        if(listObj.options[i].value == theList.selectedItemValue)
			        {
			        	
			            listObj.options[i].selected = true;
			        }
			     }
			}
		}
		if(theList.onBuildReady)
		{
			//调用初始化函数

			theList.onBuildReady(listObj);
		}
		if(theList.selectIndexChanged)
		{
			//选择项改变事件配置

			listObj.onchange = theList.selectIndexChanged;
		}
	};
}



