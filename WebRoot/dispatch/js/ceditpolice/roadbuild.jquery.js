(function($) {
	function RoadState() {
		return RoadState.prototype.init();
	}
	RoadState.prototype = {
		init : function(){
			this.roadState = $("#recentRoadState");
//			$.ehl.widget.list({
//				id : "roadstate",
//				data : { "1" : "正常", "2" : "缓慢", "3" : "拥堵", "4" : "其他" },
//				type : "radio",
//				parent : "#tdRoadState"
//			});
//			$("input[name='rd_roadstate']:not('#rd_roadstate_4')").bind("click", function() {
//				roadState.attr("disabled", this.checked);
//				roadState.val("车辆行驶比较" + $(this).next().html());
//			});
//			$("#rd_roadstate_4").bind("click",function(){
//				roadState.attr("disabled", !this.checked).val("").focus();
//			});
			return this;
		},
		get : function(){
			return this.roadState.val();
		},
		check : function(){
			if(!this.roadState.val()){
//				if($("input[name='rd_roadstate']").length == 0){
//					alert("请选择近期路况提示！");
//					$("#rd_roadstate_1").focus();
//				}else{
					alert("请输入近期路况提示！");
					this.roadState.focus();
//				}
				return false;
			}
			return true;
		}
	};
	
	$(function() {
		window.$roadState = RoadState();
//		defaults();
	});
	
	function defaults(){
		$("#ROADNAME").val("媒合西口");
		$("#KMVALUE").val("1");
		$("#MVALUE").val("2");
		$("#EndKMVALUE").val("1");
		$("#EndMVALUE").val("200");
		$("#ROLANENUM").val("3");
		var date = new Date();
		date.setDate(date.getDate()-1);
		$("#CaseHappenTime").val(date.format_(5));
		date.setDate(date.getDate() + 20);
		$("#CaseEndTime").val(date.format_(5));
		$("#PLAN").val("施工道路前后已树立路牌，引导行人向其他方向");
		$("#rd_roadstate_1").trigger("click");
	}
})(jQuery);