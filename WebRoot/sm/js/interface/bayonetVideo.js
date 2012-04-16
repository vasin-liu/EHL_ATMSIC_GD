/** 卡口流量 */
function BayonetVideo() {

}
BayonetVideo.prototype = {
	init : function() {
		var _this = this;
		$("#btnQuery").bind("click",function(){_this.query.call(_this);});
	},
	query : function() {
		this.state = $("#state").val();
		this.watchVideoState("tdData","bayonet");
	},
	watchVideoState : function(pelId,elId,id){
		$("#"+pelId).html("<table id='"+elId+"' style='font-size:13px;'></table>");
		var table = $("#"+elId);
		this.bayonet = table;
		var colModel = [
		 {name:'id',label:"卡口编号",hidden:true},
         {name:'kkmc',label:"卡口名称",width:300,sortable:false},
         {name:'state',label:"状态",width:120,sortable:false,formatter:function(value){return value=="1"?"正常":"<span style='color:red;'>异常</span>";}}
         ];
		var caption = "卡口视频监测";
		caption = "<div style='text-align:center;width:100%;font-size:15px;color:black;'>"+caption+"<div>";
		table.jqGrid({
			mtype : "post",
			datatype : "json",
			url : "dynamicinfo.bayonetFlow.watchVideoState.d",
			postData : {state:this.state},
			jsonReader:{root:"rows",cell:"cell"},
			colModel : colModel,
			caption : caption,
			autowidth:true,
			height : 350,
			loadonce:true,
			rownumbers : true,
			rowNum : 0,
			hidegrid:false,
			loadComplete : function(data){
				if(!data.rows){
					if (jQuery(".norecords").html() == null) {
						jQuery(this).parent().append(
								"<div class=\"norecords\">没有符合的数据！</div>");
					}
					jQuery(".norecords").show();
				}else{
					jQuery(".norecords").hide();
				}
			}
		});
		
	}
};
var bayonetVideo = new BayonetVideo();
$(function() {
	bayonetVideo.init();
	bayonetVideo.query();
});
