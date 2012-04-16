
	/** 
	* 项目名称：EHL_ATMSIC_GD<br>
	* 文件路径：notices/js<br>  
	* 文件名称：LightOver.js<br>
	* 文件编号：   <br>
	* 文件描述：  <br>
	*
	* 版本信息： Ver 1.1<br>  
	* 创建日期：2012-2-19   <br>
	* 公司名称： 北京易华录信息技术股份有限公司  2012 Copyright(C) 版权所有    <br>
	**************************************************<br>
	* 创建人：Vasin   <br>
	* 创建日期：2012-2-19 上午12:56:56  <br>
	************* 修改历史  *************<br>
	* 修改人：Vasin   <br>
	* 修改时间：2012-2-19 上午12:56:56   <br>
	* 修改备注：   <br>
	*/

LightOver = {
	checkElement : function(id){
		return document.getElementById(id) || false;
	},
	
	AddLightOver : function(id,parentId,zIndex){
		var newDiv;
		if(!this.checkElement(id)){
			newDiv = document.createElement("div");
		}else{
			newDiv = checkElement(id);
		}
		
		newDiv.id = id?id:"LightOverDivId";
		newDiv.style.position = "absolute";
		newDiv.style.zIndex = zIndex?zIndex:"9999";
		_scrollWidth = Math.ceil(document.body.clientWidth);
		_scrollHeight =Math.ceil(document.body.clientHeight);
		newDiv.style.width = _scrollWidth + "px";
		newDiv.style.height = _scrollHeight + "px";
		newDiv.style.top = "0px";
		newDiv.style.left = "0px";
		newDiv.style.background = "#33393C";
		newDiv.style.filter = "alpha(opacity=50)";
		newDiv.style.opacity = "0.50";
		if(parentId && this.checkElement(parentId)){
			this.checkElement(parentId).appendChild(newDiv);
		}else{
			document.body.appendChild(newDiv);
		}
	},
	CloseLightOver : function(id){
		if(id && this.checkElement(id)){
			document.body.removeChild(this.checkElement(id));
		}else if(this.checkElement("LightOverDivId")){
			document.body.removeChild(this.checkElement("LightOverDivId"));
		}
	}
}