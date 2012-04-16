/** 卡口流量 */
function BayonetFlow() {
}
BayonetFlow.prototype = {
	init : function() {
		var _this = this;
		var date = baseInfo.time.substring(0, 10);
		$("#sdate").val(date);
		$("#edate").val(date);
		this.setBayonet();
		$("#btnQuery").bind("click",function(){_this.query.call(_this);});
	},
	setBayonet : function(){
		var _this = this;
		var url = "dynamicinfo.bayonetFlow.getBayonet.d";
		$.getJSON(url,null,function(data){
			var bayonet = $("#selectBayonet");
			for ( var i = 0; i < data.length; i++) {
				var option = document.createElement("option");
				option.value = data[i].id;
				option.innerText = data[i].name;
				bayonet.append(option);
			}
			bayonet.get(0).options[0].selected = true;
			var max = 5;
			var msg = "最多选择" + max + "条记录";
			bayonet.multiselect({
				header:msg,
				selectedList:1,
				minWidth:"auto",
				click : function(){
				   if( $(this).multiselect("widget").find("input:checked").length > max ){
			           return false;
			       }
				}
			});
			_this.query();
		});
	},
	query : function() {
		this.sdate = $("#sdate").val();
		this.edate = $("#edate").val();
		this.state = $("#state").val();
		this.id = $("#selectBayonet").multiselect("getChecked").map(function(){
			return this.value;
		}).get().join(",") ;
		if(this.queryCheck()){
			this.statisWrongDays("tdData","bayonet");
		}
	},
	queryCheck : function() {
		var time = baseInfo.time.substring(0, 10);
		if (baseInfo.publishTime > this.sdate || time < this.edate) {
			if(baseInfo.publishTime > this.sdate){
				alert("起始时间不能小于" + baseInfo.publishTime);
				$("#sdate").val(baseInfo.publishTime);
			}
			if(time < this.edate){
				alert("结束时间不能大于" + time);
				$("#edate").val(time);
			}
			return false;
		}
		
		var max = 31;
		if(Math.abs(this.dayDiffer()) + 1 > max ){
			alert("日期间隔不超过"+max+"天！");
			return false;
		}
		
		if (!this.id) {
			alert("请选择卡口！");
			return false;
		}
		return true;
	},
	errorStyle : function(value){
		return "<span style='color:red;'>"+value+"</span>"; 
	},
	statisWrongDays : function(pelId,elId,id){
		var _this = this;
		$("#"+pelId).html("<table id='"+elId+"' style='font-size:13px;'></table>");
		var table = $("#"+elId);
		this.bayonet = table;
		var colNames = ["卡口编号","卡口名称","日期","入省","出省"];
		var colModel = [
		 {name:'id',label:"卡口编号",hidden:true},
         {name:'kkmc',label:"卡口名称",width:300,sortable:false},
         {name:'date',label:"日期",hidden:true,width:120,sortable:false},
         {name:'in',label:"入省",width:150,align:"center", sortable:false,formatter:function(value){return value=="0"?value:_this.errorStyle(value);}},
         {name:'out',label:"出省",width:150,align:"center",sortable:false,formatter:function(value){return value=="0"?value:_this.errorStyle(value);}} 
         ];
		table.jqGrid({
			mtype : "post",
			datatype : "json",
			url : "dynamicinfo.bayonetFlow.statisWrongDays.d",
			postData : {id : this.id, date : this.sdate + "," +  this.edate,state: this.state },
			jsonReader:{root:"rows",cell:"cell"},
			colNames:colNames,
			colModel : colModel,
			caption : "卡口流量监测",
			autowidth:true,
			height : 320,
			loadonce:true,
			rownumbers : true,
			rowNum : 0,
			shrinkToFit:false,
			hidegrid:false,
			subGrid:true,
			subGridRowExpanded:function(subgridId,rowId){
				_this.bayonetId = rowId;
				_this.statisWrongHours.call(_this,subgridId,subgridId+"-days",rowId);
			},
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
		this.setCaption();
		this.setLabel(table,"异常天数");
	},
	setCaption : function(){
		var html = "卡口流量监测";
		html += "（共计"+(Math.abs(this.dayDiffer())+1)+"天）";
		html = "<div style='text-align:center;width:100%;font-size:15px;color:black;'>"+html+"<div>";
		this.bayonet.setCaption(html);
	},
	setLabel : function(table,desc){
		this.setForwardLabel(table,"in",desc); 
		this.setForwardLabel(table,"out",desc);
	},
	setForwardLabel : function(table,forward,desc){
		var colModel = table.getGridParam("colModel");
		var label = "";
		for(var i=0;i<colModel.length;i++){
			if(colModel[i].name==forward){
				label = colModel[i].label;
				break;
			}
		}
		label += "<span style='font-size:12px;'>（"+desc+"）</span>";
		table.setLabel(forward,label);
	},
	dayDiffer : function(){
		var date = new Date();
		var dates = this.sdate.split("-");
		date.setFullYear(dates[0], dates[1], dates[2]);
		var sms = date.getTime();
		dates = this.edate.split("-");
		date.setFullYear(dates[0], dates[1], dates[2]);
		var ems = date.getTime();
		return (sms-ems)/1000/60/60/24;
	},
	statisWrongHours : function(pelId,elId,id) {
		var _this = this;
		$("#"+pelId).html("<table id='"+elId+"' style='font-size:13px;'></table>");
		var table = $("#"+elId);
		this.days = table;
		var colModel = [
//		    {name:'id',label:"卡口编号",hidden:true},
//            {name:'kkmc',label:"卡口名称",hidden:true},
            {name:'date',label:"日期",width:250,sortable:false},
            {name:'in',label:"入省",width:150,align:"center", sortable:false,formatter:function(value){return value=="0"?value:_this.errorStyle(value);}},
            {name:'out',label:"出省",width:150,align:"center",sortable:false,formatter:function(value){return value=="0"?value:_this.errorStyle(value);}} 
        ];
		table.jqGrid({
			mtype : "post",
			datatype : "json",
			url : "dynamicinfo.bayonetFlow.statisWrongHours.d",
			postData : {
				id : id,
				date : this.sdate + "," + this.edate
			},
			jsonReader:{root:"rows",cell:"cell"},
			colModel : colModel,
			autowidth:true,
			height : "100%",
			loadonce:true,
			rownumbers:true,
			rowNum : 0,
			shrinkToFit:false,
			subGrid:true,
			subGridRowExpanded:function(subgridId,rowId){
				_this.dayId = rowId;
				_this.showFlow.call(_this,subgridId,subgridId+"-hours",rowId);
			}
		});
		this.setLabel(table,"异常小时数");
	},
	showFlow : function(pelId,elId,id){
		var _this = this;
		$("#"+pelId).html("<table id='"+elId+"' style='font-size:13px;'></table>");
		var table = $("#"+elId);
		this.hours = table;
		var colModel = [
            {name:'date',label:"小时",width:120,sortable:false},
            {name:'in',label:"入省",width:80,align:"center", sortable:false,formatter:function(value){return value == "--" ? _this.errorStyle("异常"):value;}},
            {name:'out',label:"出省",width:80,align:"center",sortable:false,formatter:function(value){return value == "--" ? _this.errorStyle("异常"):value;}} 
        ];
		table.jqGrid({
			mtype : "post",
			datatype : "json",
			url : "dynamicinfo.bayonetFlow.getFlow.d",
			postData : {id:this.bayonetId, date : this.dayId,state: this.state },
			jsonReader:{root:"rows",cell:"cell"},
			colModel : colModel,
			autowidth:true,
			height : "100%",
			loadonce:true,
			rownumbers : true,
			rowNum : 0
		});
		this.setLabel(table,"车流量");
	}
};
var bayonetFlow = new BayonetFlow();
$(function() {
	bayonetFlow.init();
});
