(function($){
	$(function(){
		$("<div id='jqmsg'></div>").css({"position":"absolute","left":"0px","top":"0px","background-color":"white"}).appendTo(document.body);
		var leave = new Leave();
		$.each($operates, function(attr, op) {
			if (op.page == baseInfo.pageType){
				leave[attr](op);
				return false;
			}
		});
	});
	
	function operateImg(cellValue,options,row){
		var jgid = row[1];
		var img = [], otype = options.colModel.name,
		operate = $operates[otype],opes = operate.code;
		var titles = baseInfo.titles.slice();
		titles[titles.length - 1] = operate.desc;
		var userOpes = baseInfo.operate.split(",");userOpes.unshift();
		var isValid = false,click = "";
//		$.each(userOpes,function(i,v){if(opes.indexOf(v)>-1){isValid=true;return false;}});
		if(otype == "watch" || (baseInfo.jglx == "0" || (baseInfo.jglx != "0" && baseInfo.jgid == jgid))){
			isValid = true;
			click = "window.openCenter('"
				+ "help.leave.toDetailPage.d"
				+ "?pageType="+operate.page
				+ "&id="+options.rowId
				+ "&title="+titles.join("&title=")
				+ "&funcId=940402"
				+ "')";
			click = click.replace(/'/g,'"');
		}
		var attr = {
			src : $operates.getImage(otype,isValid),
			click : click,
			alt : operate.desc
		};
		img.push("src='" + attr.src + "'");
		img.push("onclick='" + attr.click + "'");
		img.push("alt='" + attr.alt + "'");
		img.push("style='cursor:hand;'");
		return "<img " + img.join(" ") + "/>";
	}
	
	function operateFeedback(ope,result){
		$prompts.showPrompt(ope,"留言",String(result));
	}
	
	
	function Leave(){
	}
	Leave.prototype = {
		setFunc : function(options){
			var opts = options || {};
			var funcs = baseInfo.data.funcs.slice();
			$.map(funcs, function(item) {
				item.pId = Department.getParent(item.id);
				item.name = item.text;
			});
			var setFuncDesc = function(treeNode){
				var names = [treeNode.name];
				$("#func").val(treeNode.id);
				while(treeNode = treeNode.getParentNode()){
					names.push(treeNode.name);
				}
				$("#funcDesc").val(names.reverse().join("->"));
			};
			var setting = {
				data : {
					simpleData : { enable : true }
				},
				callback : {
					onClick : function(e, treeId, treeNode){ !treeNode.isParent && setFuncDesc(treeNode); }
				}
			};
			
			var treeObj = $.fn.zTree.init($("#ulFunc").text(""), setting, funcs);
			
			var func = $("#func").val();
			if(func){
				var node = treeObj.getNodeByParam("id",func);
				treeObj.selectNode(node,false);
				setFuncDesc(node);
			}
			
			var closeDialog = function(event) {
				var dialog = $("#divFunc").dialog("widget");
				if (event.target.id != "imgFunc" && event.target != dialog[0] && 
						$(event.target).parents(".ui-dialog:has('#divFunc')").length == 0) {
					$("#divFunc").dialog("close");
				}
			};
			var func = $("#funcDesc"),loc = func.offset();loc.top += func.outerHeight();
			var dopts = {
				resizable:false,
				draggable:false,
				autoOpen : false,
				position:[loc.left,loc.top],
				width : opts.width || func.outerWidth(),
				open:function(event,ui){
					$(document.body).mousedown(closeDialog);
					$("#divFunc").css({"height":"220"}).dialog("widget").css(loc);
				},
				beforeClose : function(event, ui) { $(document.body).unbind("mousedown", closeDialog); }
			};
			$("#divFunc").dialog(dopts).dialog("widget").css({"font-size":"12px"});
			$("#imgFunc").click(function() {$("#divFunc").dialog("open");});
			return this;
		},
		setFklx : function(){
			var fcolspan = $("#tdFklx").attr("colspan") || 1,
				fucolspan = parseInt($("#tdFuncDesc").attr("colspan")||1) + parseInt($("#tdFunc").attr("colspan")||1);
			$("#fklx").change(function(){
				var is = this.value && ["000001","000002"].join(",").indexOf(this.value) > -1;
				$("#tdFuncDesc,#tdFunc").css("display", is ? "inline" : "none");
				$("#tdFklx").attr("colspan", is ? fcolspan : fcolspan + fucolspan);
				!is && $("#funcDesc,#func").val("");
			}).change();
			return this;
		},
		setApath : function(){
			if(baseInfo.pageType == "0"){
				$("#apathDesc").ehluploadify();
			}else{
				var apath = $("#apath").val();
				var filePaths = apath.split(",");
				filePaths = $.map(filePaths,function(item){ return item.split("/").pop(); });
				$("#apathDesc").css({"width":$("#title").outerWidth()}).val(filePaths.join("、"));
			}
			return this;
		},
		setState : function(){
			var $state = $("#state");
			baseInfo.jgid != $("#jgid").val() && $state.val() == "000001" && $state.val("000002");
			$("#ckState").attr("checked",$state.val() == "000003")
				.click(function(){$("#state").val(this.checked?"000003":"000002");});
			return this;
		},
		add : function(){
			this.setFunc().setFklx().setApath();//、、.test();
			var form = $("#formLeave");
			form.find("#pname,#jjcd,#fklx,#title,#content").attr("disabled",false)
			.end().find("#funcDesc,#apathDesc").attr("disabled",false).attr("readonly",true);
			$("#imgFunc").show();
			
			form.validate({
				submitHandler : function(form) {
					$(form).ajaxSubmit({
						url : "help.leave.insert.d",
						type : "post",
						success : function(result) {
							operateFeedback("新增", result);
							result && window.location.reload();
						}
					});
				}				
			});
			form.find(":input").hover(function(ele){});
			$("#btnAdd").show().click(function(){ form.submit(); });
		},
		query : function(){
			this.setFunc({width:200}).setFklx();
			$("#jgmc").bind("propertychange",function(){event.propertyName=="value" && $("#jgid").val(window.G_jgID||baseInfo.jgid);});
			var form = $("#formLeave");
			var codeFormatter = function(cell,options,row){
				return $.map(baseInfo.data[options.colModel.name+"s"], function(item) {return item.dm == cell ? item.dmsm : null;});
			};
			var codeFormatterJjcd = function(cell,options,row){
				var jjcds = {"000001":"blue","000002":"green","000003":"red"};
				var desc = codeFormatter(cell,options,row);
				return "<span style='color:"+jjcds[cell]+"'>"+desc+"</span>";
			};
			var colModel = [
    		 {name:"id",label:"主键编号",hidden:true},
    		 {name:"jgid",label:"机构编号",hidden:true},
    		 {name:"jgmc",label:"机构名称",width:200,index:"jgid"},
    		 {name:"pname",label:"值班员",width:100},
    		 {name:"time",label:"填报时间",width:150},
    		 {name:"title",label:"标题",width:200},
             {name:"content",label:"内容",hidden:true},
             {name:"apath",label:"附件",hidden:true},
             {name:"fklx",label:"反馈类型",formatter:codeFormatter},
             {name:"func",label:"功能节点",hidden:true},
             {name:"jjcd",label:"紧急程度",width:150,formatter:codeFormatterJjcd},
             {name:"state",label:"处理状态",width:150,align:"center",formatter:codeFormatter},
             {name:"rnum",label:"序号",hidden:true},
             {name:"watch",label:"查看",width:80,align:"center",formatter:operateImg,sortable:false},
             {name:"deal",label:"处理",width:80,align:"center",formatter:operateImg,sortable:false},
             ];
    		window.jqGrid = $("#tblData").jqGrid({
    			caption:"查询结果",
    			colModel : colModel,
    			autowidth : true,
    			height : "250px",
    			hidegrid:false,
    			rownumbers:true,
    			mtype : "post",
    			url :"help.leave.select.d?"+form.formSerialize(),
    			datatype:"json",
    			sortname:"id",
    			sortorder:"desc",
    			pager : "#pager",
    		    viewrecords: true,
    			rowNum : 10,
    			rowList:[10,20,30]
    		});
    		form.validate({
				submitHandler : function() {
					$("#tblData").setGridParam({
						url : "help.leave.select.d?"+form.formSerialize(),
	    				page:1
	    			}).trigger("reloadGrid");
				},
				errorPlacement : function(error, element) {
					var pos = element.offset();
					error.addClass("errorinfo").appendTo(element.parent())
					  .css({ left : pos.left, top : pos.top - error.outerHeight() - 6 })
					  .bgiframe();
				},
				errorElement : "div"
			});
    		$("#btnQuery").click(function(){form.submit();});
		},
		watch : function(){
			this.setFklx().setFunc().setApath().setState();
			$("#formLeave,#formReply")
				.find(":input").css({border:"none"})
				.filter("textarea").css({"overflow":"auto"})
				.filter("[name='content']").eq(1).remove();
			$("#state :first:selected").length == 0 && $("#trState,#trReply").show();
			$("#btnClose").show();
		},
		deal : function() {
			window.baseInfo.operate = (window.dialogArguments||window.opener).baseInfo.operate;
			this.setFunc().setFklx().setApath();
			baseInfo.operate.indexOf($operates.modify.code) > 0 && this.modify();
			baseInfo.operate.indexOf($operates.reply.code) > 0 && this.reply();
			$("#btnClose").show();
		},
		modify : function(){
			var form = $("#formLeave");
			form.find("#pname,#jjcd,#fklx,#title,#content").attr("disabled",false)
			.end().find("#funcDesc,#apathDesc").attr({disabled:false,readonly:true});
			$("#imgFunc").show();
			form.validate({
				submitHandler : function(form) {
					$(form).ajaxSubmit({
						url : "help.leave.modifyById.d",
						type : "post",
						beforeSubmit : function(arr, form, options) {
							var is = "000003,000004".indexOf($("#fklx").val()) > -1;
							is && $.map(arr, function(item) {
								item.name == "func" && (item.value = "");
							});
						},
						success : function(result) {
							operateFeedback("修改",result);
							if(result){
								window.close();
								window.opener.jQuery("#tblData").trigger("reloadGrid");
							}
						}
					});
				},
				errorPlacement : function(error, element) {
					var pos = element.offset();
					error.attr("class", "error").appendTo(element.parent());
					error.css({ left : pos.left, top : pos.top - error.outerHeight() - 6});
				}
			});
			$("#btnModify").show().click(function(){$("#formLeave").submit();});
		},
		reply : function(){
			this.setState();
			$("#trState,#trReply").show().find("#ckState").attr("disabled",false);
			$("#formReply").validate({
				submitHandler : function(form) {
					$(form).ajaxSubmit({
						url : "help.leave.reply.d",
						type : "post",
						success : function(result) {
							operateFeedback("回复",result);
							if(result){
								window.close();
								window.opener.jQuery("#tblData").trigger("reloadGrid");
							}
						}
					});
				},
				errorPlacement : function(error, element) {
					var pos = element.offset();
					error.attr("class", "error").appendTo(element.parent());
					error.css({ left : pos.left, top : pos.top - error.outerHeight() - 6});
				}
			});
			$("#btnReply").show().click(function(){$("#formReply").submit();});
		},
		test : function() {
			$("#pname").val("张三");
			$("#title").val("好好的测试");
			$("#content").val("规范的填写内容");
			//$("#fklx").val($("#fklx :eq(2)").val()).change();
		}
	};
})(jQuery);
//var tips = {
//		"title":"请输入标题",
//		"content":"请输入内容",
//		"apath":"请选择附件"
//	};
//	$.each(tips,function(attr,value){
//		$("#"+attr).poshytip({
//			className : "tip-darkgray",
//			content:value,
//			alignTo :"target",
//			alignX :"center",
//			alignY :"top"
//		});
//	});