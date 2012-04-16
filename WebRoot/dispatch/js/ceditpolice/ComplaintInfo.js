var _000000= {	title:"省厅交管局群众投诉登记表",
			hide:[3,9,10,11,12,13,14,16,17,19],
			writeable:['TSRXM','TSRDH','TSNR','TSRJZ','TSRCP','TSJH','fileInfo0'],
			checkable:['TSYWLB','TSJG','mfile0'],
			cursor:"TSRXM",
			state:"000001",
			btn_3:["保存",modify,"0"],
			btn_4:["发送",setTree,"440000000000,ZHKJSR,000000,0,190,350,299,modify('000004');"],
			btn_5:true,
			btn_6:false
		}
var _000001= {	title:"省厅交管局群众投诉登记表",
			hide:[3,9,10,11,12,13,14,16,17,19],
			writeable:['TSRXM','TSRDH','TSNR','TSRJZ','TSRCP','TSJH'],
			checkable:['TSYWLB','TSJG','mfile0'],
			cursor:"TSRXM",
			state:"000001",
			btn_3:["保存",modify,"000001"],
			btn_4:["发送",setTree,"440000000000,ZHKJSR,000001,0,190,350,299,modify('000004');"],
			btn_5:true,
			btn_6:false
		}
var _000004 = {	title:"指挥科审核",
			hide:[9,10,11,12,13,14,16,17,19],
			writeable:['ZHKYJ'],
			checkable:[],
			cursor:"ZHKYJ",
			state:"000004",
			btn_3:["保存",modify,"000004"],
			btn_4:["发送",setTree,"440000000000,ZHKJSR,000004,0,190,350,299,modify('000019');"],
			btn_5:true,
			btn_6:false
		}
var _000007 = {	title:"指挥处审核",
			hide:[9,10,11,12,13,14,16,17,19],
			writeable:['ZHCYJ'],
			checkable:[],
			cursor:"ZHCYJ",
			state:"000007",
			btn_3:["保存",modify,"000007"],
			btn_4:["发送",setTree,"440000000000,ZHKJSR,000007,0,190,350,299,modify('000010');"],
			btn_5:true,
			btn_6:false
		}
var _000073 = {	title:"指挥处审核",
			hide:[9,10,11,12,13,14,16,17,19],
			writeable:['ZHCYJ'],
			checkable:[],
			cursor:"ZHCYJ",
			state:"000073",
			btn_3:["保存",modify,"000073"],
			btn_4:["发送",setTree,"440000000000,ZHKJSR,000067,0,190,350,299,modify('000010');"],
			btn_5:true,
			btn_6:false
		}
var _000076 = {	title:"指挥处审核",
			hide:[9,10,11,12,13,14,16,17,19],
			writeable:['ZHCYJ'],
			checkable:[],
			cursor:"ZHCYJ",
			state:"000076",
			btn_3:["保存",modify,"000076"],
			btn_4:["发送",setTree,"440000000000,ZHKJSR,000007,0,190,350,299,modify('000010');"],
			btn_5:true,
			btn_6:false
		}
var _000010 = {	title:"指挥科审核",
			hide:[9,10,11,12,13,14,16,17,19],
			writeable:['ZHKYJ'],
			checkable:[],
			cursor:"ZHKYJ",
			state:"000010",
			btn_1:["保存",modify,"000010"],
			btn_2:["发送交通指挥处领导",setTree,"440000000000,ZHCJSR,210,100,400,300,modify('000007');"],
			btn_3:["发送局领导",setTree,"440000000000,JLDJSR,210,100,400,300,modify('000013');"],
			btn_4:["发送业务处主管部门",setTree,"440000000000,YWCJSR,210,100,400,300,modify('000019');"],
			btn_5:true,
			btn_6:false
		}
var _000013 = {	title:"局长审核",
			hide:[9,10,11,12,13,14,16,17,19],
			writeable:['JLDYJ'],
			checkable:[],
			cursor:"JLDYJ",
			state:"000013",
			btn_3:["保存",modify,"000013"],
			btn_4:["发送",setTree,"440000000000,ZHKJSR,0,190,350,299,modify('000016');"],
			btn_5:true,
			btn_6:false
		}
var _000070 = {	title:"局长审核",
			hide:[9,10,11,12,13,14,16,17,19],
			writeable:['JLDYJ'],
			checkable:[],
			cursor:"JLDYJ",
			state:"000070",
			btn_3:["保存",modify,"000070"],
			btn_4:["发送",setTree,"440000000000,ZHKJSR,000070,0,190,350,299,modify('000016');"],
			btn_5:true,
			btn_6:false
		}
var _000016 = {	title:"指挥科审核",
			hide:[9,10,11,12,13,14,16,17,19],
			writeable:['ZHKYJ'],
			checkable:[],
			cursor:"ZHKYJ",
			state:"000016",
			btn_1:["保存",modify,"000016"],
			btn_2:["发送交通指挥处领导",setTree,"440000000000,ZHCJSR,210,100,400,300,modify('000007');"],
			btn_3:["发送局领导",setTree,"440000000000,JLDJSR,210,100,400,300,modify('000013');"],
			btn_4:["发送业务处主管部门",setTree,"440000000000,YWCJSR,210,100,400,300,modify('000019');"],
			btn_5:true,
			btn_6:false
		}
var _000019 = {	title:"业务处审核",
			hide:[9,10,12,13,14,16,17,19],
			writeable:['YWCYJ'],
			checkable:[],
			cursor:"YWCYJ",
			state:"000019",
			btn_1:["发送局领导",setTree,"440000000000,JLDJSR,210,100,400,300,modify('000022');"],
			btn_2:["发送业务处其他领导",setTree,"440000000000,YWCJSR,210,100,400,300,modify('000019');"],
			btn_3:["转支队核查",showTr,{	step:'_000028',
										title:"业务处审核",
										hide:[10,12,13,14,16,17,19],
										writeable:['ZDBLYJ','YWCCBR','LXDH'],
										checkable:[],
										cursor:"ZDBLYJ",
										state:"000019",
										btn_1:["发送",setTree,"440000000000,ZDJGID,210,100,400,300,modify('000031');"]}],
			btn_4:["填写办理结果",showTr,{		step:'_000025',
											title:"业务处审核",
											hide:[9,10,12,13,14,16,17,19],
											writeable:['BLJGSM','JBDW','JBLXR','JBLXRDH','SPR','SPRQ'],
											checkable:['ISREPLYMASSES'],
											cursor:"BLJGSM",
											state:"000019",
											btn_1:["发送",modify,"000052"],
											btn_2:false}],
			btn_5:true,
			btn_6:false
		}
var _000064 = {	title:"业务处审核",
			hide:[9,10,12,13,14,16,17,19],
			writeable:['YWCYJ'],
			checkable:[],
			cursor:"YWCYJ",
			state:"000064",
			btn_2:["发送",setTree,"440000000000,ZHKJSR,000064,100,15,450,300,modify('000019');"],
			btn_3:["转支队核查",showTr,{	step:'_000028',
										title:"业务处审核",
										hide:[10,12,13,14,16,17,19],
										writeable:['ZDBLYJ','YWCCBR','LXDH'],
										checkable:[],
										cursor:"ZDBLYJ",
										state:"000019",
										btn_1:["发送",setTree,"440000000000,ZHKJSR,000085,100,15,450,300,modify('000064');"]}],
			btn_4:["填写办理结果",showTr,{		step:'_000025',
											title:"业务处审核",
											hide:[9,10,12,13,14,16,17,19],
											writeable:['BLJGSM','JBDW','JBLXR','JBLXRDH','SPR','SPRQ'],
											checkable:['ISREPLYMASSES'],
											cursor:"BLJGSM",
											state:"000019",
											btn_1:["发送",modify,"000052"],
											btn_2:false}],
			btn_5:true,
			btn_6:false
		}
var _000067 = {	title:"业务处审核",
			hide:[9,10,12,13,14,16,17,19],
			writeable:['YWCYJ'],
			checkable:[],
			cursor:"YWCYJ",
			state:"000067",
			btn_2:["发送",setTree,"440000000000,ZHKJSR,000067,100,15,450,300,modify('000019');"],
			btn_3:["转支队核查",showTr,{	step:'_000028',
										title:"业务处审核",
										hide:[10,12,13,14,16,17,19],
										writeable:['ZDBLYJ','YWCCBR','LXDH'],
										checkable:[],
										cursor:"ZDBLYJ",
										state:"000019",
										btn_4:["发送",setTree,"440000000000,ZHKJSR,000085,100,15,450,300,modify('000067');"]}],
			btn_4:["填写办理结果",showTr,{		step:'_000025',
											title:"业务处审核",
											hide:[9,10,12,13,14,16,17,19],
											writeable:['BLJGSM','JBDW','JBLXR','JBLXRDH','SPR','SPRQ'],
											checkable:['ISREPLYMASSES'],
											cursor:"BLJGSM",
											state:"000019",
											btn_4:["发送",modify,"000052"]}],
			btn_5:true,
			btn_6:false
		}
var _000022 = {	title:"局长审核",
			hide:[9,10,11,12,13,14,16,17,19],
			writeable:['JLDYJ'],
			checkable:[],
			cursor:"JLDYJ",
			state:"000022",
			btn_3:["保存",modify,"000022"],
			btn_4:["发送",setTree,"440000000000,ZHKJSR,000022,210,100,400,300,modify('000064');"],
			btn_5:true,
			btn_6:false
		}
/*
var _000031 = {	title:"支队承办",
			hide:[2,5,6,7,8,9,10,12,13,14,17,19],
			writeable:[],
			checkable:[],
			state:"000031",
			btn_4:["签收",showTr,{	step:'_000034',
										title:"支队承办",
										hide:[2,5,6,7,8,9,12,13,14,17],
										writeable:['ZDYJ'],
										checkable:['ZDAttachmentFile'],
										cursor:"ZDYJ",
										state:"000034",
										btn_3:["保存",modify,"000034"],
										btn_4:["回复省厅交管局",showTr,{	step:'_000037',
																		title:"支队承办",
																		hide:[2,5,6,7,8,9,12,13,14,17],
																		writeable:['ZDYJ','BLJGSM','JBDW','JBLXR','JBLXRDH','SPR','SPRQ'],
																		checkable:['ISREPLYMASSES','ZDAttachmentFile'],
																		state:"000037",
																		btn_4:["上报",modify,"000052"],
																		btn_5:true,
																		btn_6:false}],
										//btn_4:["转大队核查",setTree,"440000000000,ZHKJSR,000034,210,100,400,300,modify('000040');"],
										btn_5:true,
										btn_6:true}],
			btn_5:true,
			btn_6:false
		}
var _000079 = {	title:"支队承办",
			hide:[2,5,6,7,8,9,10,12,13,14,17,19],
			writeable:[],
			checkable:[],
			state:"000079",
			btn_4:["签收",showTr,{	step:'_000034',
										title:"支队承办",
										hide:[2,5,6,7,8,9,12,13,14,17],
										writeable:['ZDYJ'],
										checkable:['ZDAttachmentFile'],
										cursor:"ZDYJ",
										state:"000034",
										btn_3:["保存",modify,"000034"],
										btn_4:["回复省厅交管局",showTr,{	step:'_000037',
																		title:"支队承办",
																		hide:[2,5,6,7,8,9,12,13,14,17],
																		writeable:['ZDYJ','BLJGSM','JBDW','JBLXR','JBLXRDH','SPR','SPRQ'],
																		checkable:['ISREPLYMASSES','ZDAttachmentFile'],
																		state:"000037",
																		btn_4:["上报",modify,"000052"],
																		btn_5:true,
																		btn_6:false}],
										//btn_4:["转大队核查",setTree,"440000000000,ZHKJSR,000034,210,100,400,300,modify('000040');"],
										btn_5:true,
										btn_6:true}],
			btn_5:true,
			btn_6:false
		}
*/
var _000031 = {	title:"支队承办",
			hide:[2,5,6,7,8,12,13,14,17],
			writeable:['ZDYJ'],
			checkable:['ZDAttachmentFile'],
			cursor:"ZDYJ",
			state:"000034",
			btn_3:["保存",modify,"000034"],
			btn_4:["回复省厅交管局",showTr,{	step:'_000037',
											title:"支队承办",
											hide:[2,5,6,7,8,12,13,14,17],
											writeable:['ZDYJ','BLJGSM','JBDW','JBLXR','JBLXRDH','SPR','SPRQ'],
											checkable:['ISREPLYMASSES','ZDAttachmentFile'],
											state:"000037",
											btn_4:["上报",modify,"000052"],
											btn_5:true,
											btn_6:false}],
			//btn_4:["转大队核查",setTree,"440000000000,ZHKJSR,000034,210,100,400,300,modify('000040');"],
			btn_5:true,
			btn_6:true
		}
var _000079 = {	title:"支队承办",
			hide:[2,5,6,7,8,12,13,14,17],
			writeable:['ZDYJ'],
			checkable:['ZDAttachmentFile'],
			cursor:"ZDYJ",
			state:"000034",
			btn_3:["保存",modify,"000034"],
			btn_4:["回复省厅交管局",showTr,{	step:'_000037',
											title:"支队承办",
											hide:[2,5,6,7,8,12,13,14,17],
											writeable:['ZDYJ','BLJGSM','JBDW','JBLXR','JBLXRDH','SPR','SPRQ'],
											checkable:['ISREPLYMASSES','ZDAttachmentFile'],
											state:"000037",
											btn_4:["上报",modify,"000052"],
											btn_5:true,
											btn_6:false}],
			//btn_4:["转大队核查",setTree,"440000000000,ZHKJSR,000034,210,100,400,300,modify('000040');"],
			btn_5:true,
			btn_6:true
		}
var _000034 = {	title:"支队承办",
			hide:[2,5,6,7,8,12,13,14,17],
			writeable:['ZDYJ'],
			checkable:['ZDAttachmentFile'],
			cursor:"ZDYJ",
			state:"000034",
			btn_3:["保存",modify,"000034"],
			btn_4:["回复省厅交管局",showTr,{	step:'_000037',
											title:"支队承办",
											hide:[2,5,6,7,8,12,13,14,17],
											writeable:['ZDYJ','BLJGSM','JBDW','JBLXR','JBLXRDH','SPR','SPRQ'],
											checkable:['ISREPLYMASSES','ZDAttachmentFile'],
											state:"000037",
											btn_4:["上报",modify,"000052"],
											btn_5:true,
											btn_6:false}],
			//btn_4:["转大队核查",setTree,"440000000000,ZHKJSR,000034,210,100,400,300,modify('000040');"],
			btn_5:true,
			btn_6:true
		}
var _000040 = {	title:"大队承办",
			hide:[2,5,6,7,8,9,11,12,13,14,17],
			writeable:[],
			checkable:[],
			state:"000040",
			btn_4:["签收",showTr,{	step:'_000043',
										title:"大队承办",
										hide:[2,5,6,7,8,9,12,13,14,17],
										writeable:['BLJGSM','JBDW','JBLXR','JBLXRDH','SPR','SPRQ'],
										checkable:['ISREPLYMASSES'],
										cursor:"BLJGSM",
										state:"000043",
										btn_3:["保存",modify,"000043"],
										btn_4:["回复支队",modify,"000046"],
										btn_5:true,
										btn_6:true}],
			btn_5:true,
			btn_6:false
		}
var _000043 = {	title:"大队承办",
			hide:[2,5,6,7,8,9,12,13,14,17],
			writeable:['BLJGSM','JBDW','JBLXR','JBLXRDH','SPR','SPRQ'],
			checkable:['ISREPLYMASSES'],
			cursor:"BLJGSM",
			state:"000043",
			btn_3:["保存",modify,"000043"],
			btn_4:["回复支队",modify,"000046"],
			btn_5:true,
			btn_6:true
		}
var _000046 = {	title:"支队签收",
			hide:[2,5,6,7,8,9,12,13,14,17],
			writeable:[],
			checkable:[],
			state:"000046",
			btn_3:["确认办理结果回复总队",modify,"000052"],
			btn_4:["修正补充办理结果",showTr,{	step:'_000055',
											title:"支队签收",
											hide:[2,5,6,7,8,9,12,13,17],
											writeable:['BLJGSM','JBDW','JBLXR','JBLXRDH','SPR','SPRQ','XGR','XGSJ'],
											checkable:['ISREPLYMASSES'],
											state:"000055",
											btn_4:["上报",modify,"000052"],
											btn_5:true,
											btn_6:false}],
			btn_5:true,
			btn_6:true
		}
var _000052 = {	title:"总队签收",
			hide:[12,13,16,17],
			writeable:[],
			checkable:[],
			state:"000052",
			btn_4:["签收",modify,"000061"],
			btn_5:true,
			btn_6:true
		}
var _000058 = {	title:"总队签收",
			hide:[12,13,16,17],
			writeable:[],
			checkable:['ISREPLYMASSES'],
			state:"000058",
			btn_4:["提交",modify,"000061"],
			btn_5:true,
			btn_6:true
		}
var _000061 = {	title:"明细",
			hide:[12,13,16,17],
			writeable:[],
			checkable:[],
			btn_5:true,
			btn_6:true
		}

var TSYWLB = {	'TSYWLB_1':['TSYWLB_2','TSYWLB_3','TSYWLB_4'],
				'TSYWLB_5':['TSYWLB_6','TSYWLB_7','TSYWLB_8'],
				'TSYWLB_9':['TSYWLB_10','TSYWLB_11','TSYWLB_12'],
				'TSYWLB_13':[]
			}

var maxtr = 19;

var step_o;

function getInfo(step){
	if($(OLD_CPID).value!=""){
		var url = "complaint.complaintmanage.getComplaintInfo.d"
		url = encodeURI(url);	
		params="CPID="+$(OLD_CPID).value;
		if(step=='000061'){
			new Ajax.Request(url, {method:"post", parameters:params, onComplete:showViewResponse});
		}else{
			new Ajax.Request(url, {method:"post", parameters:params, onComplete:showGetXMLResponse});
		}
		// 杜（追加）
		doloadPage($(OLD_CPID).value);
	}else{
		init(eval("_"+step));
	}
	setTimeout(changeSize,100);
}

function changeSize(){
	var width = $('fieldset').offsetWidth;
	var height = $('fieldset').offsetHeight
	width = eval(width + 25);    
	height = eval(height + 70);  
	window.dialogHeight = height+"px";
	window.dialogWidth = width+"px";
}

function init(step){
	if(step_o){
		if(step_o.btn_1)	removeEvent("btn_1","click",step_o.btn_1[1]);
		if(step_o.btn_2)	removeEvent("btn_2","click",step_o.btn_2[1]);
		if(step_o.btn_3)	removeEvent("btn_3","click",step_o.btn_3[1]);
		if(step_o.btn_4)	removeEvent("btn_4","click",step_o.btn_4[1]);
		removeEvent("btn_5","click",print);
	}
	step_o = step;
	var title = step.title;
	var hide = step.hide;
	var writeable = step.writeable;
	var checkable = step.checkable;
	var cursor = step.cursor;
	var state = step.state;
	var btn_1 = step.btn_1;
	var btn_2 = step.btn_2;
	var btn_3 = step.btn_3;
	var btn_4 = step.btn_4;
	var btn_5 = step.btn_5;
	
	var input;
	var select;
	var textarea;
	var ele;
	var eles;
	
	input = document.getElementsByTagName("input");
	select = document.getElementsByTagName("select");
	textarea = document.getElementsByTagName("textarea");
	
	$('title_text').innerHTML = title;
	$('LZZT').value = state;
	for (var i = 1; i <= maxtr; i++) {
		$('tr_' + i).show();
	}
	
	for (var i = 0; i < hide.length; i++) {
		$('tr_' + hide[i]).hide();
	}
	
	for (var i = 0; i < input.length; i++) {
		ele = $(input[i]);
		if(ele.type == 'text'){
			ele.readOnly = true;
			ele.style.backgroundColor = '#ffffff';
		}else if(ele.type == 'checkbox' || ele.type == 'radio'){
			ele.disabled = true;
		}else if(ele.type == 'file'){
			ele.disabled = true;
		}
	}
	
	for (var i = 0; i < select.length; i++){
		$(select[i]).disabled = true;
	}
	
	for (var i = 0; i < textarea.length; i++){
		ele = $(textarea[i]);
		ele.readOnly = true;
		ele.style.backgroundColor = '#ffffff';
	}
	
	for (var i = 0; i < writeable.length; i++){
		ele = $(writeable[i]);
		if(writeable[i]!="SPRQ" && writeable[i]!="XGSJ"){
			ele.readOnly = false;
		}
		ele.style.backgroundColor = '';
	}
	
	for (var i = 0; i < checkable.length; i++){
		eles = document.getElementsByName(checkable[i]);
		for(var j = 0; j < eles.length; j++){
			$(eles[j]).disabled = false;
		}
	}
	
	for(ele in TSYWLB){
		if(!$(ele).checked){
			$(ele + '_d').hide();
		}
	}
	
	if($('JBDW').readOnly == false){
		$('JBDW').value=$('JGMC').value;
	};
	
	if(TSRJZ.value!="" || TSRCP.value!="" || TSJH.value!=""){
		$('tr_3').show();
	}
	
	if(btn_1){
		$('btn_1').show();
		$("btn_1").value = btn_1[0];
		addEvent("btn_1","click",btn_1[1],btn_1[2]);
	}else{
		$('btn_1').hide();
	}
	
	if(btn_2){
		$('btn_2').show();
		$("btn_2").value = btn_2[0];
		addEvent("btn_2","click",btn_2[1],btn_2[2]);
	}else{
		$('btn_2').hide();
	}
	
	if(btn_3){
		$('btn_3').show();
		$("btn_3").value = btn_3[0];
		addEvent("btn_3","click",btn_3[1],btn_3[2]);
	}else{
		$('btn_3').hide();
	}
	
	if(btn_4){
		$('btn_4').show();
		$("btn_4").value = btn_4[0];
		addEvent("btn_4","click",btn_4[1],btn_4[2]);
	}else{
		$('btn_4').hide();
	}
	
	if(btn_5){
		$('btn_5').show();
		addEvent("btn_5","click",print,step);
	}else		$('btn_5').hide();
	
	if(cursor) {$(cursor).show();if($(cursor+"insert")) $(cursor+"insert").show();$(cursor).focus();}
	setTimeout(changeSize,100);
}

function optionCtrl(id){
	if($(id).checked)	{$(id + '_d').show();$('tr_3').show();}
	else				{$(id + '_d').hide();if( (!$('TSYWLB_1').checked) && (!$('TSYWLB_5').checked) && (!$('TSYWLB_9').checked)) $('tr_3').hide();};
	
	eles = TSYWLB[id];
	for (var i = 0; i < eles.length; i++){
		$(eles[i]).checked = false;
	}
	setTimeout(changeSize,100);
}

function checked(){
	flag = false;
	for(ele in TSYWLB){
		if(!$(ele).checked)	continue;
		
		eles = TSYWLB[ele];
		for (var i = 0; i < eles.length; i++){
			if($(eles[i]).checked){
				flag = true;
				continue;
			}
		}
		
		if(!flag){
			//alert($(ele).value);
			//alert($(ele).nextSibling.nodeValue);
			alert("请选择 ‘" + $(ele).nextSibling.nodeValue + "’ 的子选项");
			return flag;
		}
	}
	if(!flag){
		alert("至少选择一种投诉业务类别！");
		return flag;
	}
	return flag;
}

function reset(step){
	/*
	var writeable = step.writeable;
	var checkable = step.checkable;
	
	for (var i = 0; i < writeable.length; i++){
		$(writeable[i]).value = '';
	}
	
	for (var i = 0; i < checkable.length; i++){
		eles = document.getElementsByName(checkable[i]);
		for(var j = 0; j < eles.length; j++){
			$(eles[j]).checked = false;
		}
	}
	*/
}

function print() {
	var SHOW_CPID = encodeURI($("SHOW_CPID")?$("SHOW_CPID").value:" ");
	var TSDJSJ = encodeURI($("TSDJSJ")?$("TSDJSJ").value:" ");
	var SENDUNIT = encodeURI($("DJJGMC")?$("DJJGMC").value:" ");
	var SENDPEOPLE = encodeURI($("DJR_XM")?$("DJR_XM").value:" ");
	var RECEIVEUNIT = encodeURI($("UNITNAME")?$("UNITNAME").value:" ");
	var RECEIVEPEOPLE = encodeURI($("PERSONNAME")?$("PERSONNAME").value:" ");
	var TSRXM = encodeURI($("TSRXM")?$("TSRXM").value:" ");
	var TSRDH = encodeURI($("TSRDH")?$("TSRDH").value:" ");
	var TSRJZ = encodeURI($("TSRJZ")?$("TSRJZ").value:" ");
	if($("TSRCP_AERA").selectedIndex==-1){
		var TSRCP = encodeURI($("TSRCP_AERA").options(0).text+$("TSRCP").value);
	}else{
		var TSRCP = encodeURI($("TSRCP_AERA").options($("TSRCP_AERA").selectedIndex).text+$("TSRCP").value);
	}
	var TSJG = encodeURI($("TSJG").options($("TSJG").selectedIndex).text);
	var TSJH = encodeURI($("TSJH")?$("TSJH").value:" ");
	var TSYWLB = '';
	for(i=1;i<14;i++){
		if(i==1 || i==5 || i==9 || i==13){
			TSYWLB += $("TSYWLB_"+i).checked?$("TSYWLB_"+i).tagName+" ":"";
		}else{
			TSYWLB += $("TSYWLB_"+i).checked?$("TSYWLB_"+i).tagName+" ":"";
		}
	}
	TSYWLB = encodeURI(TSYWLB);
	var TSNR = encodeURI($("TSNR")?$("TSNR").value:" ");
	window.open("printComplaint.jsp?bh=" + SHOW_CPID +"&jstime=" + TSDJSJ + "&bsdw=" + SENDUNIT + "&bsr=" + SENDPEOPLE + "&jsdw=" + RECEIVEUNIT 
		+ "&jsr=" + RECEIVEPEOPLE + "&TSRXM=" + TSRXM + "&TSRDH=" + TSRDH + "&TSRJZ=" + TSRJZ + "&TSRCP=" + TSRCP + "&TSJG=" + TSJG
		+ "&TSJH=" + TSJH + "&TSYWLB=" + TSYWLB + "&TSNR=" + TSNR);
}
function oldprint(){
	var block = 'fieldset';
	var valueOld = document.all.block.innerHTML;
	var otable = document.getElementById("table1");
	try{
		for(i=0;i<otable.rows.length;i++){
			otable.rows[i].deleteCell(10);
			otable.rows[i].deleteCell(9);
		}
	}
	catch(err){
	}
	var value = document.all.block.innerHTML;
    var printdetail = window.open("","printDetail","");
	printdetail.document.open();
	printdetail.document.write("<HTML><head></head>" +
			"<link rel='stylesheet' type='text/css' href='../../css/pagetag/pagetag.css'>" +
		 	"<link rel='stylesheet' type='text/css' href='../../css/Global.css'>" +
		 	"<link rel='stylesheet' type='text/css' href='../../css/tree.css'>" +
			"<script language=javascript>function preview() {window.clipboardData.setData('Text',document.all('table1').outerHTML);try{var ExApp = new ActiveXObject('Excel.Application'); var ExWBk = ExApp.workbooks.add(); var ExWSh = ExWBk.worksheets(1); ExApp.DisplayAlerts = false; ExApp.visible = true}catch(e){alert('您的电脑没有安装Microsoft Excel软件！');return false}ExWBk.worksheets(1).Paste;}" +
			"function test(){document.getElementById('tr_15').style.display='none';}" +
			"<"+"/script>" +
			"<style type='text/css'>" +
				"body { background-color: #FFFFFF;margin-left:0;margin-right:0;}" +
				".table{ border-top:1px solid #000;border-left:1px solid #000;font-size:11px}" +
				".table tr th{border-right:1px solid #000;border-bottom:1px solid #000;text-align:center;}" +
				".table tr td{border-right:1px solid #000;border-bottom:1px solid #000;text-align:center;}" +
				"td.kd   {width:11%;}.titleTopBack{font-weight:900}" +
				".tdtitle_a{border-top: 0 solid #a5diec;border-right: 1 solid #a5diec;border-bottom: 1 solid #a5diec;border-left: 1 solid #a5diec;line-height: 16px;color: #000000;border-collapse : separate;empty-cells:show;text-align: right;}" +
				".tdvalue_a{border-top: 0 solid #a5diec;border-right: 1 solid #a5diec;border-bottom: 1 solid #a5diec;border-left: 1 solid #a5diec;line-height: 16px;color: #000000;border-collapse : separate;empty-cells:show;text-align: left;}" +
			"</style>" +
			"<style media=print> <!--display:none 隐藏加上, 上面的 media=print 就是在打 印时隐藏--> .Noprint{display:none;}</style> " +
			"<BODY onload='test();window.print();'>"); 
	printdetail.document.write("<PRE>"); 
	printdetail.document.write("<div style='width:100%;height:30px;font-size:20px;font-weight:900;line-height:30px;' align='center'>警情投诉</div>");
	printdetail.document.write(value); 
	printdetail.document.write("</PRE>");
	printdetail.document.close("</BODY></HTML>");
	document.all.block.innerHTML = valueOld; 
}

function showTr(o){ //支队承办回复总队交管局
	//var step = o.step;
	var step = o;
	if(step.step == '_000028')	step.title = "省厅交管局投诉登记表";
	if(step == 'g')	step = gg;
	if(step == 'h')	step = h;
	var hide = o.hide;
	if(hide)	step.hide = hide;
	var writeable = o.writeable;
	if(writeable)	step.writeable = writeable;
	var checkable = o.checkable;
	if(checkable)	step.checkable = checkable;
	var state = o.state;
	if(state)	step.state = state;
	var btn_1 = o.btn_1;
	if(btn_1)	step.btn_1 = btn_1;
	var btn_2 = o.btn_2;
	if(btn_2)	step.btn_2 = btn_2;
	var btn_3 = o.btn_3;
	if(btn_3)	step.btn_3 = btn_3;
	var btn_4 = o.btn_4;
	if(btn_4)	step.btn_4 = btn_4;
	init(step);
}

function addEvent(element,eventType,listener){
	var argument = new Array();
	var fun = listener;
	if(!(element = $(element))){
		return false;
	}
	if(typeof(element) == 'string'){
		return $(element);	
	}
	if(arguments.length > 3){
		for(i=3;i<arguments.length;i++){
			argument[i-3] = arguments[i];
		}
		fun = function(event){
			listener.apply(event,argument);
		}
	}
	if (element.addEventListener) {
		// W3C method
		element.addEventListener( eventType, fun, false);
		return true;
	} else if(element.attachEvent) {
		// MSIE method
		element['e'+eventType+listener] = fun;
		element[eventType+listener] = function(){element['e'+eventType+listener](window.event);}
		element.attachEvent( 'on'+eventType, element[eventType+listener] );
		return true;
	}
	return false;
}

function removeEvent(element,eventType,listener){
	if(!(element = $(element))) return false;
	if (element.removeEventListener) {
		element.removeEventListener( eventType, listener, false );
		return true;
	} else if (element.detachEvent) {
		// MSIE method
		element.detachEvent( 'on'+eventType, element[eventType+listener] );
		element[eventType+listener] = null;
		return true;
	}
	return false;
}


function selectTag(showContent,selfObj){
	  // 操作标签
		  var tag = document.getElementById("tagsd").getElementsByTagName("li");
		  var taglength = tag.length;
		  
		  for(i=0; i<taglength; i++){
		    tag[i].className = "";
		    tag[i].style.background="url('../../images/sysoption/bg.gif')  right bottom";
		  }
		  selfObj.parentNode.className = "selectTag";
		   selfObj.parentNode.style.background="url('../../images/sysoption/bg2.gif')  right bottom";
		  // 操作内容
		  j=document.getElementById("tagContentdNo").getElementsByTagName("div");
		  for(i=0; i<j.length; i++){
		    j[i].style.display = "none";
		  }
		  var selectDiv = document.getElementById(showContent);
		  var selectDivChilds = selectDiv.getElementsByTagName("div");
		   for(i=0; i<selectDivChilds.length; i++){
		    selectDivChilds[i].style.display = "block";
		  }
		  selectDiv.style.display = "block";
		 selfObj.style.background = "left bottom";
		 selectDiv.childNodes[0].focus();
		 
		 $('HFSTJGJ').show();
		$('HFSTJGJ1').hide();
		$('HFSTJGJ2').hide();
		$('HFSTJGJ3').hide();
		$('HFSTJGJ4').hide();
		 
		 $('DDQS').show();
		 $('DDQS1').hide();
		$('DDQS2').hide();
		$('DDQS3').hide();
		$('DDQS4').hide();
		
		$('QRBLJGHFZD').show();
		$('QRBLJGHFZD1').hide();
		$('QRBLJGHFZD2').hide();
	}
	
/**
 *author:wkz
 *desc:编辑投诉信息
 *date:2010-3-15
*/
function doOnLoad(pid){
	var jgbh = $("jgbh").value;
	var sql = "select toc.CPID,toc.TSRXM,toc.TSRDH,toc.TSNR,(select NAME from T_OA_CODE where ID=toc.LZZT),toc.LZZT,to_char(toc.TSDJSJ,'yyyy-mm-dd hh24:mi')";
	sql +=" from T_OA_COMPLAINT toc";
	if(jgbh.length==2){
		//alert("总队用户");
		sql +=" where toc.CPID like '" + jgbh + "%'";
	}else if(jgbh.length==4){
		//alert("支队用户");
		sql +=" where toc.ZDJGID like '%" + pid + "%'";
	}else if(jgbh.length==6){
		//alert("大队用户");
		sql +=" where toc.DDJGID like '%" + pid + "%'";
	}
	sql +=" order by toc.TSDJSJ DESC";
	//prompt(sql,sql);
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showResultsPage","10");
}
function doSearch(jgbh){
	if(!validateInput()) return;
	//obj.options[obj.selectedIndex].text
	var sql = "select toc.CPID,toc.TSRXM,toc.TSRDH,toc.TSNR,(select NAME from T_OA_CODE where ID=toc.LZZT),toc.LZZT,to_char(toc.TSDJSJ,'yyyy-mm-dd hh24:mi')";
	sql +=" from T_OA_COMPLAINT toc,T_OA_CODE";
	if(jgbh.length==2){
		//alert("总队用户");
		sql +=" where toc.CPID like '" + jgbh + "%'";
	}else if(jgbh.length==4){
		//alert("支队用户");
		sql +=" where toc.ZDJGID like '%" + $('pid').value + "%'";
	}else if(jgbh.length==6){
		//alert("大队用户");
		sql +=" where toc.DDJGID like '%" + $('pid').value + "%'";
	}
	sql +=" and toc.lzzt = T_OA_CODE.ID";
    sql +=$("TSRXM").value==""?"":(" and toc.TSRXM like '%" + $("TSRXM").value + "%'");
    sql +=$("TSRDH").value==""?"":(" and toc.TSRDH like '%" + $("TSRDH").value + "%'");
    sql +=$("TSNR").value==""?"":(" and toc.TSNR like '%" + $("TSNR").value + "%'");
    sql +=$("TSDJSJ").value==""?"":(" and to_char(toc.TSDJSJ,'yyyy-mm-dd') = '" + $("TSDJSJ").value + "'");
    sql +=$("SearchLZZT").value==""?"":(" and T_OA_CODE.NAME = '" + $("SearchLZZT").value + "'");
    sql +=" order by toc.TSDJSJ DESC";
	//prompt(sql,sql);
	PageCtrl.initPage("tdData","pageData","pageNav",convertSql(sql),"showResultsPage","10");
}
function showResultsPage(xmldoc){
	var rows = xmldoc.getElementsByTagName("row");	
	var results = null;
	
	var str = "<div id='block'><div style='text-align:center;width:100%;line-height:22px; float:left;'><span class='currentLocationBold'>查询结果</span></div><table width='100%' border='0' cellpadding='0' cellspacing='0' class='table' id='table1'>";
		str += "<tr class='titleTopBack'>";
		str += "<td width='5%' class='td_r_b td_font'>序号</td>";
		str += "<td width='10%' class='td_r_b td_font'>投诉人</td>";
	    str += "<td width='15%' class='td_r_b td_font'>投诉人电话</td>";
	    str += "<td width='15%' class='td_r_b td_font'>投诉登记时间</td>";
		str += "<td width='25%' class='td_r_b td_font'>投诉内容</td>";
		str += "<td width='20%' class='td_r_b td_font'>流转状态</td>";
		str += "<td width='5%' class='td_r_b td_font'>明细</td>";
		str += "<td width='5%' class='td_r_b td_font'>处理</td>";	
		//str += "<td width='5%' class='td_r_b td_font'>删除</td>";
	    str += "</tr>";
	    if(rows.length<=0){
		   str += "<tr class='titleTopBack'>";
		   str += "<td class='td_r_b td_font' colspan='8' align='center'>此条件下没有数据</td>";
		   str += "</tr>";
	    }else{
	      for(var  i=0;i<rows.length;i++){			
			results = rows[i].childNodes;
			str += "<tr align='center'>";
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'>"+(i+1)+"</td>";
			str += "<td width='10%' class='td_r_b td_font' align=\'center\'>"+(results[1].text=="null"?"":results[1].text)+"</td>";
			str += "<td width='15%' class='td_r_b td_font' align=\'center\'>"+(results[2].text=="null"?"":results[2].text)+"</td>";
			str += "<td width='15%' class='td_r_b td_font' align=\'center\'>"+(results[6].text=="null"?"":results[6].text)+"</td>";
			str += "<td width='25%' class='td_r_b td_font' align=\'center\'>"+(results[3].text=="null"?"":results[3].text)+"</td>";
			str += "<td width='20%' class='td_r_b td_font' align=\'center\'>"+(results[4].text=="null"?"":results[4].text)+"</td>";
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/para.gif\" onClick=\"onButtonClick('view','"+results[0].text+"','000061')\"></td>"
			str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/update.gif\" onClick=\"onButtonClick('edit','"+results[0].text+"','"+results[5].text+"')\"></td>"
			//str += "<td width='5%' class='td_r_b td_font' align=\'center\'><input type=\"image\" src=\"../../images/button/btndelete1.gif\" onClick=\"onButtonClick('delete','"+results[0].text+"','"+results[5].text+"')\"></td>"
			str += "</tr>";			
	        }
	      }
		str +="</table></div>";
		var pagingObj = $('pageData').up('tr').next('tr').down('td');
		pagingObj.style.textAlign = "center";
		var tabOjb = document.getElementById("pageData");
		tabOjb.innerHTML = str;
		Popup.prototype.hideTips();
}

/** 
    * desc:增加、编辑 --绑定前端数据到后端进行业务处理 编辑道路信息
    * param:
    * return:
    * author：wkz
    * date: 2010-4-14
    * version:
    */
function modify(insrtOrUpdt) {
	returnValue = "insertOrUpdate";
	
	var CPID = $('CPID');
	var TSDJSJ = $('TSDJSJ');
	var DJRID = $('DJRID');
	var DJJGID = $('DJJGID');
	var TSRXM = $('TSRXM');
	if (TSRXM.value == "") {
		alert("请填写投诉人姓名！");
		TSRXM.focus();
		return;
	}
	
	var TSRDH = $('TSRDH');
	if (TSRDH.value == "") {
		alert("请填写投诉人电话！");
		TSRDH.focus();
		return;
	}
	var TSRJZ = $('TSRJZ');
	if(!(checkIdcard($("TSRJZ").value)=="为空或验证通过!")) {alert(checkIdcard($("TSRJZ").value));$("TSRJZ").focus();return};
	//var TSRCP = $('TSRCP');
	if($("TSRCP_AERA").selectedIndex==-1){
		var TSRCP = $("TSRCP_AERA").options(0).text+$("TSRCP").value;
	}else{
		var TSRCP = $("TSRCP_AERA").options($("TSRCP_AERA").selectedIndex).text+$("TSRCP").value;
	}
	TSRCP = TSRCP.toUpperCase( );
	if($("TSRCP").value != ""){
		if(!isCarNumber($("TSRCP").value)) {$("TSRCP").focus();return;}
	}else{
		TSRCP="";
	}
	
	var TSYWFLB = '';
	var TSYWZLB = '';
	for(i=1;i<14;i++){
		if(i==1 || i==5 || i==9 || i==13){
			TSYWFLB += $("TSYWLB_"+i).checked?$("TSYWLB_"+i).value+" ":"";
		}else{
			TSYWZLB += $("TSYWLB_"+i).checked?$("TSYWLB_"+i).value+" ":"";
		}
	}
	
	var TSNR = $('TSNR');
	if (TSNR.value == "") {
		alert("请填写投诉内容！");
		TSNR.focus();
		return;
	}
	var TSJG = $('TSJG');
	if (TSJG.value == "") {
		alert("请选择被投诉辖区！");
		TSJG.focus();
		return;
	}
	var TSJH = $('TSJH');
	var ZHKJSR = $('ZHKJSR');
	var ZHKLD = $('ZHKLD');
	
	var ZHKYJ = $('ZHKYJ');
	var ZHCJSR = $('ZHCJSR');
	var ZHCLD = $('ZHCLD');
	var ZHCYJ = $('ZHCYJ');
	var JLDJSR = $('JLDJSR');
	
	var JLD = $('JLD');
	var JLDYJ = $('JLDYJ');
	var YWCZGBM = $('YWCZGBM');
	var YWCJSR = $('YWCJSR');
	var YWCZG = $('YWCZG');
	
	var YWCYJ = $('YWCYJ');
	var YWCCBR = $('YWCCBR');
	var ZDBLYJ = $('ZDBLYJ');
	var ZDJGID = $('ZDJGID');
	var ZDLD = $('ZDLD');
	
	var ZDYJ = $('ZDYJ');
	var DDJGID = $('DDJGID');
	var BLJGSM = $('BLJGSM');
	var ISREPLYMASSES = '';
	for(i=1;i<3;i++){
		ISREPLYMASSES += $("ISREPLYMASSES_"+i).checked?$("ISREPLYMASSES_"+i).value:"";
	}
	var JBDW = $('JBDW');
	var JBLXR = $('JBLXR');
	var JBLXRDH = $('JBLXRDH');
	var SPR = $('SPR');
	var SPRQ = $('SPRQ');
	var XGR = $('XGR');
	
	var XGSJ = $('XGSJ');
	/*
	var ROADID = $('alarmRoad_TrafficCrowd');
	if (ROADID.value == "") {
		alert("请选择道路！");
		ROADID.focus();
		return;
	}
	*/
	var LZZT = $('LZZT');
	//检查if(!checked()) return;
	var LXDH = $('LXDH');
	
	var params = {};
	params["CPID"] = CPID.value;
	params["TSDJSJ"] = TSDJSJ.value;
	params["DJRID"] = DJRID.value;
	params["DJJGID"] = DJJGID.value;
	params["TSRXM"] = TSRXM.value;
	
	params["TSRDH"] = TSRDH.value;
	params["TSRJZ"] = TSRJZ.value;
	//params["TSRCP"] = TSRCP.value;
	params["TSRCP"] = TSRCP;
	params["TSYWFLB"] = TSYWFLB;
	params["TSYWZLB"] = TSYWZLB;
	
	params["TSNR"] = TSNR.value;
	params["TSJG"] = TSJG.value;
	params["TSJH"] = TSJH.value;
	params["ZHKJSR"] = ZHKJSR.personId;
	params["ZHKLD"] = ZHKLD.value;
	
	params["ZHKYJ"] = ZHKYJ.value;
	params["ZHCJSR"] = ZHCJSR.personId;
	params["ZHCLD"] = ZHCLD.value;
	params["ZHCYJ"] = ZHCYJ.value;
	params["JLDJSR"] = JLDJSR.personId;
	
	params["JLD"] = JLD.value;
	params["JLDYJ"] = JLDYJ.value;
	params["YWCZGBM"] = YWCZGBM.value;
	params["YWCJSR"] = YWCJSR.personId;
	params["YWCZG"] = YWCZG.value;
	
	params["YWCYJ"] = YWCYJ.value;
	params["YWCCBR"] = YWCCBR.personId;
	params["ZDBLYJ"] = ZDBLYJ.value;
	params["ZDJGID"] = ZDJGID.personId;
	params["ZDLD"] = ZDLD.value;
	
	params["ZDYJ"] = ZDYJ.value;
	params["DDJGID"] = DDJGID.personId;
	params["BLJGSM"] = BLJGSM.value;
	params["ISREPLYMASSES"] = ISREPLYMASSES;
	params["JBDW"] = JBDW.value;
	
	params["JBLXR"] = JBLXR.value;
	params["JBLXRDH"] = JBLXRDH.value;
	params["SPR"] = SPR.value;
	params["SPRQ"] = SPRQ.value;
	params["XGR"] = XGR.value;
	
	params["XGSJ"] = XGSJ.value;
	params["insrtOrUpdt"] = insrtOrUpdt;
	
	params["RYID"] = $('RYID').value;
	params["JGID"] = $('JGID').value;
	params["OLD_LZZT"] = $('OLD_LZZT').value;
	params["LXDH"] = LXDH.value;
	
	if(insrtOrUpdt!="0"){
		/*
		if(insrtOrUpdt=='000061'){
			(ISREPLYMASSES=='0')?(params["LZZT"] = '000058'):(params["LZZT"] = '000061');
		}else{
			params["LZZT"]=insrtOrUpdt;
		}
		*/
		if(insrtOrUpdt=='000001'){
			params["LZZT"]='000001';
		}
		if(insrtOrUpdt=='000004'){
			if($('OLD_LZZT').value!='000001'){
				params["LZZT"]='000004';
			}
			// 杜 （追加）
			var formObj = document.getElementById("complaintEditForm");
			var fileObj = document.getElementById("fileInfo0");
			if (fileObj != null && fileObj != "undefined" && fileObj != "") {
				var fileName = fileObj.value;
				if (fileName != null) {
					formObj.submit();
				}
			}
		}
		if(insrtOrUpdt=='000052'){
			params["LZZT"]='000052';
		}
		if(insrtOrUpdt=='000061'){
			params["LZZT"]='000061';
		}
		if(insrtOrUpdt=='000034'){
			params["LZZT"]='000034';
		}
		if(insrtOrUpdt=='000034' || insrtOrUpdt=='000040' || insrtOrUpdt=='000052'){
			//zd
			// 杜 （追加）
			var formObjZd = document.getElementById("ZdFileUploadForm");
			var fileObjZd = document.getElementById("ZDAttachmentFile");
			//var fileNameZd = fileObjZd.value ;
			formObjZd.submit();
//			// 检查文件后缀是否正确
//			if (CheckFile(fileNameZd)) {
//				return;
//			}
			/*if(fileNameZd != null){
				   formObjZd.submit();	
			}*/
		}
	}else{
		params["LZZT"]='000001';
		//new
		// 杜 （追加）
		var formObj = document.getElementById("complaintEditForm");
		var fileObj = document.getElementById("fileInfo0");
		var fileName = fileObj.value ;
//		// 检查文件后缀是否正确
//		if (CheckFile(fileName)) {
//			return;
//		}
		if(fileName != null){
			   formObj.submit();	
		}
	}
	if(insrtOrUpdt=='000052'){
		if(XGR.readOnly==false && XGR.value==""){
			alert("请填写修改人！");
			XGR.focus();
			return;
		}
		if(XGR.readOnly==false && XGSJ.value==""){
			alert("请选择修改时间！");
			XGSJ.focus();
			return;
		}
	}
	if(insrtOrUpdt=='000064'||insrtOrUpdt=='000067'){
		/*if(YWCCBR.personId==""){
			alert("请选择总队联系人！");
			YWCCBR.focus();
			return;
		}*/
	}
	if(insrtOrUpdt=='000061'){
		if(ISREPLYMASSES=="0"){
			params["insrtOrUpdt"] = "000058";
			params["LZZT"]='000058';
		}
	}
	/*
	if(insrtOrUpdt=='000043'||insrtOrUpdt=='000046'||insrtOrUpdt=='000052'){
		if(SPR.value==""){
			alert("请填写审批人！");
			SPR.focus();
			return;
		}
	}
	if(insrtOrUpdt=='000007'||insrtOrUpdt=='000013'){
		if($('OLD_LZZT').value=='000004'){
			if(ZHKYJ.value==""){
				alert("请填写意见！");
				ZHKYJ.focus();
				return;
			}
		}
	}
	if(insrtOrUpdt=='000010'){
		if(ZHCYJ.value==""){
			alert("请填写意见！");
			ZHCYJ.focus();
			return;
		}
	}
	if(insrtOrUpdt=='000016'){
		if(JLDYJ.value==""){
			alert("请填写意见！");
			JLDYJ.focus();
			return;
		}
	}
	if(insrtOrUpdt=='000022'){
		if(YWCYJ.value==""){
			alert("请填写意见！");
			YWCYJ.focus();
			return;
		}
	}
	if(insrtOrUpdt=='000019'){
		if($('OLD_LZZT').value=='000019'){
			if(YWCYJ.value==""){
				alert("请填写意见！");
				YWCYJ.focus();
				return;
			}
		}else{
			if(ZHKYJ.value==""){
				alert("请填写意见！");
				ZHKYJ.focus();
				return;
			}
		}
	}
	*/
	if(!validateInput()) return;
	var url = "complaint.complaintmanage.modifyComplaintInfo.d";
	url = encodeURI(url);
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:showModifyResult});
}
function showModifyResult(xmlHttp) {
	var xmlDoc = xmlHttp.responseText;	
	if(xmlDoc.indexOf('成功')>-1){
		alert(xmlDoc);
		window.close();
	}else{
		alert('操作失败，请重试！');
	}
	/*if(xmlDoc=="success"){
		alert("操作成功!");
		window.close();
	}else{
		alert(xmlDoc);
	}*/
}
function validateInput() {
	var input;
	var select;
	var textarea;
	var reg =  /^[^~@#\$\^&]*$/;
	input = document.getElementsByTagName("input");
	textarea = document.getElementsByTagName("textarea");
	for (var i = 0; i < input.length - 3; i++) {
		var item = input[i];
		if (item.type != "button" && !item.readOnly) {
			if(!reg.test(item.value)){
				alert("不可输入特殊字符,包括: ~、@、#、$、^、&")
				item.focus();
				return false;	
			}
		}
	}
	for (var i = 0; i < textarea.length; i++) {
		var item = textarea[i];
		if (item.type != "button" && !item.readOnly) {
			if(!reg.test(item.value)){
				alert("不可输入特殊字符,包括: ~、@、#、$、^、&")
				item.focus();
				return false;	
			}
		}
	}
	return true;
}
function isCarNumber(value){
	var reg = /^[0-9a-zA-Z]+$/; 
	if(!reg.test(value)){ 
		alert("车牌号只能输入字母和数字！"); 
		return false;
	}
	if(value.length!=6){
		alert("车牌号为6位字母或数字的组合！"); 
		return false;
	}
	return true;
}
//驾驶证号码检验函数 
function checkIdcard(idcard){
	var Errors=new Array(
		"为空或验证通过!",
		"驾驶证号码位数不对!",
		"驾驶证号码出生日期超出范围或含有非法字符!",
		"驾驶证号码校验错误!",
		"驾驶证地区非法!"
	);
	var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}
	var idcard,Y,JYM;
	var S,M;
	var idcard_array = new Array();
	idcard_array = idcard.split("");
	//为空校验
	if(idcard=="") return Errors[0];
	//地区检验
	if(area[parseInt(idcard.substr(0,2))]==null) return Errors[4];
	//身份号码位数及格式检验
	switch(idcard.length){
		case 15:
		if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){
			ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性
		} else {
			ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性
		}
		if(ereg.test(idcard)) return Errors[0];
		else return Errors[2];
		break;
		case 18:
		//18位身份号码检测
		//出生日期的合法性检查 
		//闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
		//平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
		if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){
			ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式
		} else {
			ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式
		}
		if(ereg.test(idcard)){//测试出生日期的合法性
			//计算校验位
			S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
			+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
			+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
			+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
			+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
			+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
			+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
			+ parseInt(idcard_array[7]) * 1 
			+ parseInt(idcard_array[8]) * 6
			+ parseInt(idcard_array[9]) * 3 ;
			Y = S % 11;
			M = "F";
			JYM = "10X98765432";
			M = JYM.substr(Y,1);//判断校验位
			if(M.toLowerCase() == idcard_array[17].toLowerCase()) return Errors[0]; //检测ID的校验位
			else return Errors[3];
		}
		else return Errors[2];
		break;
		default:
		return Errors[1];
		break;
	}
}

/** 
    * desc:删除
    * param:
    * return:
    * author：wxt
    * date: 2010-01-16
    * version:
    */
function doDelete(cpid) {
	if (confirm("\u60a8\u786e\u5b9a\u5220\u9664\u6b64\u9879\u8bb0\u5f55\u5417?")) { //提示用户是否删除记录                                       
		var strUrl = "complaint.complaintmanage.deleteComplaintInfo.d";  //把参数传给后端的java
		strUrl = encodeURI(strUrl);
		var params = "CPID=" + cpid;
		new Ajax.Request(strUrl, {method:"post", parameters:params, onComplete:showResponseDelete});
	} else {  
		return;
	}
}
function showResponseDelete(xmlHttp) {
	if(xmlHttp.responseText=='success') {
	  alert("删除成功！");
	}
	else {
	  alert("删除失败!");
	}
	doOnLoad();
}

function showViewResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	
	var CPID = $('CPID');
	var TSDJSJ = $('TSDJSJ');
	var TSDJSJ_Y = $('TSDJSJ_Y');
	var TSDJSJ_M = $('TSDJSJ_M');
	var TSDJSJ_D = $('TSDJSJ_D');
	var DJRID = $('DJRID');
	var DJR_XM = $('DJR_XM');
	var DJJGID = $('DJJGID');
	var DJJGMC = $('DJJGMC');
	var TSRXM = $('TSRXM');
	var TSRDH = $('TSRDH');
	var TSYWFLB;
	var TSYWZLB;
	var TSNR = $('TSNR');
	var ZHKLD = $('ZHKLD');
	var ZHKYJ = $('ZHKYJ');
	var ZHCLD = $('ZHCLD');
	var ZHCYJ = $('ZHCYJ');
	var YWCZG = $('YWCZG');
	var YWCYJ = $('YWCYJ');
	var YWCCBR = $('YWCCBR');
	var ZDBLYJ = $('ZDBLYJ');
	var JLD = $('JLD');
	var JLDYJ = $('JLDYJ');
	var ZDJGID = $('ZDJGID');
	var ZDLD = $('ZDLD');
	var ZDYJ = $('ZDYJ');
	var DDJGID = $('DDJGID');
	var BLJGSM = $('BLJGSM');
	var ISREPLYMASSES;
	var JBDW = $('JBDW');
	var JBLXR = $('JBLXR');
	var JBLXRDH = $('JBLXRDH');
	var SPR = $('SPR');
	var SPRQ = $('SPRQ');
	var XGR = $('XGR');
	var XGSJ = $('XGSJ');
	var LZZT = $('LZZT');
	var LXDH = $('LXDH');
	
	fillListBox("TSJGS","TSJG","150","select jgid,jgmc from t_sys_department order by jgid","请选择","onChange('TSJG','"+results[31].text+"','"+results[30].text+"')");
	var TSRJZ = $('TSRJZ');
	var TSRCP = $('TSRCP');
	var TSJH = $('TSJH');
	
	CPID.value = results[0].text;
	TSDJSJ.value = results[1].text;
	TSDJSJ_Y.value = results[1].text.substring(0,4);
	TSDJSJ_M.value = results[1].text.substring(5,7);
	TSDJSJ_D.value = results[1].text.substring(8,10);
	DJRID.value = results[2].text;
	DJJGID.value = results[3].text;
	TSRXM.value = results[4].text.replace(/^　$/,'');
	TSRDH.value = results[5].text.replace(/^　$/,'');
	TSYWFLB = results[6].text;
	TSYWZLB = results[7].text;
	var TSYWZLB_INPUT = "";
	for(i=1;i<14;i++)
	{
		if(i==1 ||i==5 ||i==9 ||i==13){
			if(TSYWFLB.indexOf($("TSYWLB_"+i).value)>-1){
				$("TSYWLB_"+i).checked = true;
			}
		}else{
			if(TSYWZLB.indexOf($("TSYWLB_"+i).value)>-1){
				$("TSYWLB_"+i).checked = true;
				TSYWZLB_INPUT += $("TSYWLB_"+i).nextSibling.nodeValue+" ";
			}
		}
	}
	$("TSYWZLB").value = TSYWZLB_INPUT;
	TSNR.value = results[8].text.replace(/^　$/,'');
	ZHKLD.value = results[9].text;
	ZHKYJ.value = results[10].text.replace(/^　$/,'');
	ZHCLD.value = results[11].text;
	ZHCYJ.value = results[12].text.replace(/^　$/,'');
	YWCZG.value = results[13].text;
	YWCYJ.value = results[14].text.replace(/^　$/,'');
	JLD.value = results[15].text;
	JLDYJ.value = results[16].text.replace(/^　$/,'');
	ZDJGID.value = results[17].text;
	ZDLD.value = results[18].text;
	ZDYJ.value = results[19].text.replace(/^　$/,'');
	DDJGID.value = results[20].text;
	BLJGSM.value = results[21].text;
	ISREPLYMASSES = results[22].text;
	for(i=1;i<3;i++)
	{
		if(ISREPLYMASSES.indexOf($("ISREPLYMASSES_"+i).value)>-1){
			$("ISREPLYMASSES_"+i).checked = true;
		}
	}
	JBDW.value = results[23].text;
	JBLXR.value = results[24].text;
	JBLXRDH.value = results[25].text;
	SPR.value = results[26].text;
	SPRQ.value = results[27].text.replace(/^　$/,'');
	XGR.value = results[28].text;
	XGSJ.value = results[29].text.replace(/^　$/,'');
	LZZT.value = results[30].text;
	
	TSRJZ.value = results[32].text;
	
	var deafultArea = $('deafultArea');
	var TSRCP_AERA = results[33].text.substring(0,1);
	if(deafultArea.value==TSRCP_AERA){
		fillListBox("TSRCP_TD","TSRCP_AERA","40","select distinct substr(dm, 0, 1) as a,substr(dm, 0, 1) from t_sys_code where dmlb = '011006' and dm not like '"+deafultArea.value+"%' order by a",deafultArea.value,"disabled()");
	}else{
		fillListBox("TSRCP_TD","TSRCP_AERA","40","select distinct substr(dm, 0, 1) as a,substr(dm, 0, 1) from t_sys_code where dmlb = '011006' and dm not like '"+deafultArea.value+"%' order by a",deafultArea.value,"onChangeAndDisabled('TSRCP_AERA','"+TSRCP_AERA+"')");
	}
	TSRCP.value = results[33].text.substring(1);
	
	TSJH.value = results[34].text;
	YWCCBR.personId = results[35].text.replace(/^　$/,'');
	YWCCBR.value = results[42].text.replace(/^　$/,'');
	ZDBLYJ.value = results[36].text;
	LXDH.value = results[41].text;
	DJR_XM.value = results[42].text.replace(/^　$/,'');
	DJJGMC.value = results[43].text.replace(/^　$/,'');
	//if(LZZT.value=="_990007"){init(_990007);};
	//alert(LZZT.value+":"+eval("_"+LZZT.value));
	//alert(window["_"+LZZT.value])
	getTrafficNotion(results[0].text);
	init(eval("_000061"));
}
/**
    根据警情编号，事件类型查询详细信息
*/
function showGetXMLResponse(xmlHttp) {
	var xmlDoc = xmlHttp.responseXML;
	var results = xmlDoc.getElementsByTagName("col");
	
	var CPID = $('CPID');
	var TSDJSJ = $('TSDJSJ');
	var TSDJSJ_Y = $('TSDJSJ_Y');
	var TSDJSJ_M = $('TSDJSJ_M');
	var TSDJSJ_D = $('TSDJSJ_D');
	var DJRID = $('DJRID');
	var DJR_XM = $('DJR_XM');
	var DJJGID = $('DJJGID');
	var DJJGMC = $('DJJGMC');
	var TSRXM = $('TSRXM');
	var TSRDH = $('TSRDH');
	var TSYWFLB;
	var TSYWZLB;
	var TSNR = $('TSNR');
	var ZHKLD = $('ZHKLD');
	var ZHKYJ = $('ZHKYJ');
	var ZHCLD = $('ZHCLD');
	var ZHCYJ = $('ZHCYJ');
	var YWCZG = $('YWCZG');
	var YWCYJ = $('YWCYJ');
	var YWCCBR = $('YWCCBR');
	var ZDBLYJ = $('ZDBLYJ');
	var JLD = $('JLD');
	var JLDYJ = $('JLDYJ');
	var ZDJGID = $('ZDJGID');
	var ZDLD = $('ZDLD');
	var ZDYJ = $('ZDYJ');
	var DDJGID = $('DDJGID');
	var BLJGSM = $('BLJGSM');
	var ISREPLYMASSES;
	var JBDW = $('JBDW');
	var JBLXR = $('JBLXR');
	var JBLXRDH = $('JBLXRDH');
	var SPR = $('SPR');
	var SPRQ = $('SPRQ');
	var XGR = $('XGR');
	var XGSJ = $('XGSJ');
	var LZZT = $('LZZT');
	
	fillListBox("TSJGS","TSJG","150","select jgid,jgmc from t_sys_department order by jgid","请选择","onChange('TSJG','"+results[31].text+"','"+results[30].text+"')");
	var TSRJZ = $('TSRJZ');
	var TSRCP = $('TSRCP');
	var TSJH = $('TSJH');
	
	var ZHKJSR = $('ZHKJSR');
	var ZHCJSR = $('ZHCJSR');
	var JLDJSR = $('JLDJSR');
	var YWCJSR = $('YWCJSR');
	var LXDH = $('LXDH');
	
	CPID.value = results[0].text;
	TSDJSJ.value = results[1].text;
	TSDJSJ_Y.value = results[1].text.substring(0,4);
	TSDJSJ_M.value = results[1].text.substring(5,7);
	TSDJSJ_D.value = results[1].text.substring(8,10);
	//DJRID.value = results[2].text.replace(/^　$/,'');
	//DJJGID.value = results[3].text.replace(/^　$/,'');
	TSRXM.value = results[4].text;
	TSRDH.value = results[5].text;
	TSYWFLB = results[6].text;
	TSYWZLB = results[7].text;
	var TSYWZLB_INPUT = "";
	for(i=1;i<14;i++)
	{
		if(i==1 ||i==5 ||i==9 ||i==13){
			if(TSYWFLB.indexOf($("TSYWLB_"+i).value)>-1){
				$("TSYWLB_"+i).checked = true;
			}
		}else{
			if(TSYWZLB.indexOf($("TSYWLB_"+i).value)>-1){
				$("TSYWLB_"+i).checked = true;
				TSYWZLB_INPUT += $("TSYWLB_"+i).nextSibling.nodeValue+" ";
			}
		}
	}
	$("TSYWZLB").value = TSYWZLB_INPUT;
	TSNR.value = results[8].text;
	ZHKLD.value = results[9].text.replace(/^　$/,'');
	ZHKYJ.value = results[10].text.replace(/^　$/,'');
	ZHCLD.value = results[11].text.replace(/^　$/,'');
	ZHCYJ.value = results[12].text.replace(/^　$/,'');
	YWCZG.value = results[13].text.replace(/^　$/,'');
	YWCYJ.value = results[14].text.replace(/^　$/,'');
	JLD.value = results[15].text.replace(/^　$/,'');
	JLDYJ.value = results[16].text.replace(/^　$/,'');
	ZDJGID.personId = results[17].text.replace(/^　$/,'');
	ZDLD.value = results[18].text.replace(/^　$/,'');
	ZDYJ.value = results[19].text.replace(/^　$/,'');
	DDJGID.personId = results[20].text.replace(/^　$/,'');
	BLJGSM.value = results[21].text.replace(/^　$/,'');
	ISREPLYMASSES = results[22].text;
	for(i=1;i<3;i++)
	{
		if(ISREPLYMASSES.indexOf($("ISREPLYMASSES_"+i).value)>-1){
			$("ISREPLYMASSES_"+i).checked = true;
		}
	}
	JBDW.value = results[23].text.replace(/^　$/,'');
	JBLXR.value = results[24].text.replace(/^　$/,'');
	JBLXRDH.value = results[25].text.replace(/^　$/,'');
	SPR.value = results[26].text.replace(/^　$/,'');
	SPRQ.value = results[27].text.replace(/^　$/,'');
	XGR.value = results[28].text.replace(/^　$/,'');
	XGSJ.value = results[29].text.replace(/^　$/,'');
	LZZT.value = results[30].text;
	
	TSRJZ.value = results[32].text.replace(/^　$/,'');
	TSRCP.value = results[33].text.substring(1).replace(/^　$/,'');
	TSJH.value = results[34].text.replace(/^　$/,'');
	YWCCBR.personId = results[35].text.replace(/^　$/,'');
	YWCCBR.value = results[42].text.replace(/^　$/,'');
	ZDBLYJ.value = results[36].text.replace(/^　$/,'');
	
	//ZHKJSR.personId = results[37].text;
	ZHCJSR.personId = results[38].text;
	JLDJSR.personId = results[39].text;
	YWCJSR.personId = results[40].text.replace(/^　$/,'');
	LXDH.value = results[41].text.replace(/^　$/,'');
	DJR_XM.value = results[42].text.replace(/^　$/,'');
	DJJGMC.value = results[43].text.replace(/^　$/,'');
	//alert(results[42].text.replace(/^　$/,''));
	//if(LZZT.value=="_990007"){init(_990007);};
	//alert(LZZT.value+":"+eval("_"+LZZT.value));
	//alert(window["_"+LZZT.value])
	getTrafficNotion(results[0].text);
	//判断当前状态下的当前用户是否可以走流程，还是只能看明细
	//alert($('RYID').value);
	//alert("当前状态："+LZZT.value+";  "+"登录人ID："+$('DJRID').value+";");
	if(LZZT.value=="000001"){
		results[2].text.replace(/^　$/,'')==$('DJRID').value?"":LZZT.value="000061";
	}
	if(LZZT.value=="000004" || LZZT.value=="000010" || LZZT.value=="000016"){
		results[37].text.replace(/^　$/,'').indexOf($('DJRID').value)!=-1?"":LZZT.value="000061";
	}
	if(LZZT.value=="000007" || LZZT.value=="000073" || LZZT.value=="000076"){
		results[38].text.replace(/^　$/,'').indexOf($('DJRID').value)!=-1?"":LZZT.value="000061";
	}
	if(LZZT.value=="000013" || LZZT.value=="000022" || LZZT.value=="000070"){
		results[39].text.replace(/^　$/,'').indexOf($('DJRID').value)!=-1?"":LZZT.value="000061";
	}
	if(LZZT.value=="000019" || LZZT.value=="000064" || LZZT.value=="000067"){
		results[40].text.replace(/^　$/,'').indexOf($('DJRID').value)!=-1?"":LZZT.value="000061";
	}
	if(LZZT.value=="000052" || LZZT.value=="000058"){
		results[40].text.replace(/^　$/,'').indexOf($('DJRID').value)!=-1?"":LZZT.value="000061";
	}
	if(LZZT.value=="000031" || LZZT.value=="000034" || LZZT.value=="000037" || LZZT.value=="000046" || LZZT.value=="000049" || LZZT.value=="000079" || LZZT.value=="000082"){
		results[17].text.replace(/^　$/,'').indexOf($('DJRID').value)!=-1?"":LZZT.value="000061";
	}
	if(LZZT.value=="000040" || LZZT.value=="000043"){
		results[20].text.replace(/^　$/,'').indexOf($('DJRID').value)!=-1?"":LZZT.value="000061";
	}
	var deafultArea = $('deafultArea');
	var TSRCP_AERA = results[33].text.substring(0,1);
	if(deafultArea.value==TSRCP_AERA){
		if($('insrtOrUpdt').value == "1" && LZZT.value=="000001"){
			fillListBox("TSRCP_TD","TSRCP_AERA","40","select distinct substr(dm, 0, 1) as a,substr(dm, 0, 1) from t_sys_code where dmlb = '011006' and dm not like '"+deafultArea.value+"%' order by a",deafultArea.value);
		}else{
			fillListBox("TSRCP_TD","TSRCP_AERA","40","select distinct substr(dm, 0, 1) as a,substr(dm, 0, 1) from t_sys_code where dmlb = '011006' and dm not like '"+deafultArea.value+"%' order by a",deafultArea.value,"disabled()");
		}
	}else{
		if($('insrtOrUpdt').value == "1" && LZZT.value=="000001"){
			fillListBox("TSRCP_TD","TSRCP_AERA","40","select distinct substr(dm, 0, 1) as a,substr(dm, 0, 1) from t_sys_code where dmlb = '011006' and dm not like '"+deafultArea.value+"%' order by a",deafultArea.value,"onChangeValue('TSRCP_AERA','"+TSRCP_AERA+"')");
		}else{
			fillListBox("TSRCP_TD","TSRCP_AERA","40","select distinct substr(dm, 0, 1) as a,substr(dm, 0, 1) from t_sys_code where dmlb = '011006' and dm not like '"+deafultArea.value+"%' order by a",deafultArea.value,"onChangeAndDisabled('TSRCP_AERA','"+TSRCP_AERA+"')");
		}
	}
	init(eval("_"+LZZT.value));
}
function onChange(obj,value1,value2){
	$(obj).value = value1;
	if(value2=="000000" || value2=="000001"){
		$(obj).disabled=false;
	}else{
		$(obj).disabled=true;
	}
}
function onChangeValue(obj,value){
	$(obj).value = value;
}
function onChangeAndDisabled(obj,value){
	$(obj).value = value;
	$(obj).disabled=true;
}
function disabled(){
	/*if($('insrtOrUpdt').value == "2"){
		$('TSRCP_AERA').disabled=true;
	}*/
	$('TSRCP_AERA').disabled=true;
}

/** 
    * desc:新增或查询的跳转
    * param:
    * return:
    * author：wkz
    * date: 2010-1-12
    * version:
    */
function onButtonClick(itemId, itemValue1, itemValue2) {
	var id = itemId;
	if (id == "new") {
		var returnValuestr = window.showModalDialog("complaintEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=0", "", "dialogWidth:740px;dialogHeight:550px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "edit") {
		var returnValuestr = window.showModalDialog("complaintEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=1&cpid=" + itemValue1 + "&lzzt=" + itemValue2 + "", "", "dialogWidth:740px;dialogHeight:700px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "view") {
		var returnValuestr = window.showModalDialog("complaintEdit.jsp?tmp=" + Math.random()+"&insrtOrUpdt=2&cpid=" + itemValue1 + "&lzzt=000061", "", "dialogWidth:740px;dialogHeight:1000px");
		if (returnValuestr == "insertOrUpdate") {
			doOnLoad();
		}
	}
	if (id == "search") {
		doSearch(itemValue1);
	}
	if (id == "delete") {
		doDelete(itemValue1);
	}
}

function single(para){
	if(para=='1'){
		$("ISREPLYMASSES_1").checked = true;
		$("ISREPLYMASSES_2").checked = false;
	}else{
		$("ISREPLYMASSES_1").checked = false;
		$("ISREPLYMASSES_2").checked = true;
	}
}

function test(paraStr){
	var paraary=paraStr.split(";");
	if(paraary!=null){		
		var setTreePara=paraary[0]; 
		alert(setTreePara);
		var modifyPara=paraary[1];	
	}
	setTree(setTreePara);
	//modify(modifyPara);
}

/**
 * 取得会话信息
 */
function getTrafficNotion(searchId) {
	var url = "complaint.complaintmanage.getTrafficNotion.d"
	url = encodeURI(url);
	params = "YWID=" + searchId;
	new Ajax.Request(url, {
				method : "post",
				parameters : params,
				onComplete : showTablerNotion
			});
}

/**
 * 编辑显示会话信息
 * @param {} xmlHttp
 */
function showTablerNotion(xmlHttp) {

	var xmldoc = xmlHttp.responseXML;
	
	// 交通指挥科意见
	var trafficKeNotion = "";
	var trafficKeNotionDiv = $("trafficKeNotion");
	var flg1 = false;
	// 交通指挥处意见
	var trafficChuNotion = "";
	var trafficChuNotionDiv = $("trafficChuNotion");
	var flg2 = false;
	// 局领导意见
	var leadingNotion = "";
	var leadingNotionDiv = $("leadingNotion");
	var flg3 = false;
	// 业务处主管部门意见
	var departmentNotion = "";
	var departmentNotionDiv = $("departmentNotion");
	var flg4 = false;
	
	if (xmldoc != null) {
		var rows = xmldoc.getElementsByTagName("row");
		// 遍历取得结果
		for (var i = 0; i < rows.length; i++) {
			// 数据项顺序：业务类型0，信息发送人名称1，发送时间2，发送文本3，所属机构名称4，职位5，发送类别6
			commonStr = rows[i].childNodes;
			// 取得数据为警情投诉编辑显示信息
			if (commonStr[0].text == "警情投诉") {
				// 判断发送类别是否是（交通指挥科意见，交通指挥处意见，局领导意见，业务处主管部门意见）
				if (commonStr[6].text == "000007"
						|| commonStr[6].text == "000013"
						|| commonStr[6].text == "000019") {
					// 指挥科意见的处理
					trafficKeNotion += "<tr>";
					trafficKeNotion += "<td>";
					// 部门（指挥科）+审批意见+人名+官职 时间
					trafficKeNotion += commonStr[4].text + " "
							+ commonStr[3].text + " " + commonStr[1].text + " "
							+ commonStr[5].text + " " + commonStr[2].text;
					trafficKeNotion += "</td>";
					trafficKeNotion += "</tr>";
					flg1 = true;
				} else if (commonStr[6].text == "000010" 
								|| commonStr[6].text == "000067"
								|| commonStr[6].text == "000070") {
					// 指挥处意见的处理
					trafficChuNotion += "<tr>";
					trafficChuNotion += "<td>";
					// 部门（指挥科）+审批意见+人名+官职 时间
					trafficChuNotion += commonStr[4].text + " "
							+ commonStr[3].text + " " + commonStr[1].text + " "
							+ commonStr[5].text + " " + commonStr[2].text;
					trafficChuNotion += "</td>";
					trafficChuNotion += "</tr>";
					flg2 = true;
				} else if (commonStr[6].text == "000064"
						|| commonStr[6].text == "000016"
						|| commonStr[6].text == "000076") {
					// 局领导意见的处理
					leadingNotion += "<tr>";
					leadingNotion += "<td>";
					// 部门（指挥科）+审批意见+人名+官职 时间
					leadingNotion += commonStr[4].text + " "
							+ commonStr[3].text + " " + commonStr[1].text + " "
							+ commonStr[5].text + " " + commonStr[2].text;
					leadingNotion += "</td>";
					leadingNotion += "</tr>";
					flg3 = true;
				} else if (commonStr[6].text == "000022" || commonStr[6].text == "000031" || commonStr[6].text == "000052" || commonStr[6].text == "000073") {
					// 业务处主管部门意见的处理
					departmentNotion += "<tr>";
					departmentNotion += "<td>";
					// 部门（指挥科）+审批意见+人名+官职 时间
					departmentNotion += commonStr[4].text + " "
							+ commonStr[3].text + " " + commonStr[1].text + " "
							+ commonStr[5].text + " " + commonStr[2].text;
					departmentNotion += "</td>";
					departmentNotion += "</tr>";
					flg4 = true;
				}
			}
		}
	}

	// 根据标志设定显示内容
	if (flg1) {
		var table = '<table width="100%" border="1" cellpadding="1" cellspacing="1">';
		table = table + trafficKeNotion;
		table = table + "</table>";
		trafficKeNotionDiv.innerHTML = table;
	}
	if (flg2) {
		var table = '<table width="100%" border="1" cellpadding="1" cellspacing="1">';
		table = table + trafficChuNotion;
		table = table + "</table>";
		trafficChuNotionDiv.innerHTML = table;
	}
	if (flg3) {
		var table = '<table width="100%" border="1" cellpadding="1" cellspacing="1">';
		table = table + leadingNotion;
		table = table + "</table>";
		leadingNotionDiv.innerHTML = table;
	}
	if (flg4) {
		var table = '<table width="100%" border="1" cellpadding="1" cellspacing="1">';
		table = table + departmentNotion;
		table = table + "</table>";
		departmentNotionDiv.innerHTML = table;
	}
	Popup.prototype.hideTips();
}


var str="";
var personAry=new Array();

function setTree(paraStr){
	personAry=new Array();
	var paraary=paraStr.split(",");
	if(paraary!=null&&paraary.length>2){		
		var deptId=paraary[0]; 
		var divId=paraary[1];
		var state=paraary[2];
		var top=paraary[3];
		var left=paraary[4];
		var width=paraary[5];
		var height=paraary[6];
		var okmethod=paraary[7];		
	}
	str="";
	var url = "dispatch.PersonTree.setTree.d";	
	url = encodeURI(url);	
	var params = {};
	params["deptId"]=deptId;
	params["divId"]=divId;
	params["state"]=state;
	new Ajax.Request(url, {method:"post", parameters:params, onComplete:function (xmlHttp){
		var xmlDoc = xmlHttp.responseXML;			
		var tree=xmlDoc.getElementsByTagName("tree");
		
		setHtml(tree[0]);
		
		var div=document.createElement("div");
		var table=document.createElement("table");
		table.style.width="100%";
		table.style.height="100%";
		var tbody=document.createElement("tbody");
		var tr=document.createElement("tr");
		var tdtree=document.createElement("td");
		tdtree.style.width="60%";
		var tdchoose=document.createElement("td");
		tdchoose.style.width="40%";
		tdchoose.style.height="100%";
	//	tdchoosexm.style.verticalAlign="text-top";
		var treediv=document.createElement("div");
		treediv.style.border="1px solid #CCCCCC";
		treediv.style.width="100%";
		treediv.style.height="100%";
		treediv.style.overflow="auto";
		var choosediv=document.createElement("div");
		choosediv.id="choosediv";
		choosediv.style.border="1px solid #CCCCCC";
		choosediv.style.height="100%";
		treediv.innerHTML=str;	
				
		var choosetable=document.createElement("table");
		choosetable.className="list_table";
		choosetable.style.width="100%";
	//	choosetable.style.height="100%";	
		var choosetitletbody=document.createElement("tbody");
		var choosetbody=document.createElement("tbody");
		choosetbody.id="choosetbody";
		var choosetr=document.createElement("tr");
	//	choosetr.style.height="20px";
		var tdchoosexm=document.createElement("td");
		tdchoosexm.className="list_thead";	
		tdchoosexm.style.verticalAlign="top";
		tdchoosexm.style.width="75%";	
		tdchoosexm.innerHTML="&nbsp;姓名";	
		var tdchoosechk=document.createElement("td");	
		tdchoosechk.className="list_thead";	
		tdchoosechk.style.width="25%";
		tdchoosechk.style.verticalAlign="top";
		tdchoosechk.innerHTML="&nbsp;删除";		
		choosetr.appendChild(tdchoosexm);
		choosetr.appendChild(tdchoosechk);
		choosetitletbody.appendChild(choosetr);
		choosetable.appendChild(choosetitletbody);	
		choosetable.appendChild(choosetbody);	
		choosediv.appendChild(choosetable);
		
		
		tdtree.appendChild(treediv);
		tdchoose.appendChild(choosediv);
		tr.appendChild(tdtree);
		tr.appendChild(tdchoose);
		tbody.appendChild(tr);
		table.appendChild(tbody);
		div.appendChild(table);
		if(okmethod&&okmethod!=""){
			openDivWindow(div,top,left,width,height,"getChoosedPerson('"+divId+"');"+okmethod+";closeDivWindow();","closeDivWindow()","请选择人员");
		}else{
			openDivWindow(div,top,left,width,height,"getChoosedPerson('"+divId+"');closeDivWindow();","closeDivWindow()","请选择人员");
		}
	}
	});

}

//function chooseNull(divId,okmethod){
//	if(document.getElementById(divId).value==""){
//		alert("请选择人员！");
//	}else{
//		eval(okmethod);
//		closeDivWindow();
//	}
//}

//function setTree(){
//	var deptId="150200000000";	
//	var url = "tree.tree.setTree.d";	
//	url = encodeURI(url);	
//	var params = {};
//	params["deptId"]=deptId;
//	new Ajax.Request(url, {method:"post", parameters:params, onComplete:setTreeRes});
//}
//function setTreeRes(xmlHttp){
//	var xmlDoc = xmlHttp.responseXML;		
//
//	var tree=xmlDoc.getElementsByTagName("tree");
//	
//	setHtml(tree[0]);
//	
////	var div=document.createElement("div");
////	div.innerHTML=str;	
//	
////	$("bodydiv").appendChild(div);
//
//
//	
//	var div=document.createElement("div");
//	var table=document.createElement("table");
//	table.style.width="100%";
//	table.style.height="100%";
//	var tbody=document.createElement("tbody");
//	var tr=document.createElement("tr");
//	var tdtree=document.createElement("td");
//	tdtree.style.width="60%";
//	var tdchoose=document.createElement("td");
//	tdchoose.style.width="40%";
//	tdchoose.style.height="100%";
////	tdchoosexm.style.verticalAlign="text-top";
//	var treediv=document.createElement("div");
//	treediv.style.border="1px solid #CCCCCC";
//	treediv.style.width="100%";
//	treediv.style.height="100%";
//	treediv.style.overflow="auto";
//	var choosediv=document.createElement("div");
//	choosediv.id="choosediv";
//	choosediv.style.border="1px solid #CCCCCC";
//	choosediv.style.height="100%";
//	treediv.innerHTML=str;		
//	
//	var choosetable=document.createElement("table");
//	choosetable.className="list_table";
//	choosetable.style.width="100%";
////	choosetable.style.height="100%";	
//	var choosetitletbody=document.createElement("tbody");
//	var choosetbody=document.createElement("tbody");
//	choosetbody.id="choosetbody";
//	var choosetr=document.createElement("tr");
////	choosetr.style.height="20px";
//	var tdchoosexm=document.createElement("td");
//	tdchoosexm.className="list_thead";	
//	tdchoosexm.style.verticalAlign="top";
//	tdchoosexm.style.width="75%";	
//	tdchoosexm.innerHTML="&nbsp;姓名";	
//	var tdchoosechk=document.createElement("td");	
//	tdchoosechk.className="list_thead";	
//	tdchoosechk.style.width="25%";
//	tdchoosechk.style.verticalAlign="top";
//	tdchoosechk.innerHTML="&nbsp;删除";		
//	choosetr.appendChild(tdchoosexm);
//	choosetr.appendChild(tdchoosechk);
//	choosetitletbody.appendChild(choosetr);
//	choosetable.appendChild(choosetitletbody);	
//	choosetable.appendChild(choosetbody);	
//	choosediv.appendChild(choosetable);	
//	
//	tdtree.appendChild(treediv);
//	tdchoose.appendChild(choosediv);
//	tr.appendChild(tdtree);
//	tr.appendChild(tdchoose);
//	tbody.appendChild(tr);
//	table.appendChild(tbody);
//	div.appendChild(table);
//		
//	openDivWindow(div,10,10,400,300,"test();closeDivWindow();","closeDivWindow()","请选择人员")
//}


//在右侧选择后的人员表格中添加tr
function createChkdTable(pid,pname,pdeptid){
	var tbody=document.getElementById("choosetbody");
	var tr=document.createElement("tr");	
	var tdxm=document.createElement("td");
	var tdchk=document.createElement("td");
	tdxm.className="list_td";
	tdchk.className="list_td";
	
	tdxm.innerHTML="&nbsp;"+pname;
	tdchk.innerHTML="<input type=checkbox id=\"chkd_"+pid+"\" onclick=\"removePerson('"+pid+"','"+pname+"','"+pdeptid+"')\">";
	
	tr.appendChild(tdxm);
	tr.appendChild(tdchk);
	tbody.appendChild(tr);
}

function choosePerson(pid,pname,pdeptid){
	var person =document.getElementById(pid);
	var chkdperson=document.getElementById("chkd_"+pid);
	if(chkdperson){
		if(person&&(person.checked==false)){	
			removePerson(pid,pname,pdeptid);
		}		
	}else{
		if(person&&person.checked){		
			var str=pid+"#"+pname+"#"+pdeptid;
			personAry.push(str);		
			createChkdTable(pid,pname,pdeptid);
		}
	}
}



function removePerson(pid,pname,pdeptid){
	
	var choosediv=document.getElementById("choosediv");
	choosediv.innerHTML="";
	var choosetable=document.createElement("table");
	choosetable.className="list_table";
	choosetable.style.width="100%";
//	choosetable.style.height="100%";	
	var choosetitletbody=document.createElement("tbody");
	var choosetbody=document.createElement("tbody");
	choosetbody.id="choosetbody";
	var choosetr=document.createElement("tr");
//	choosetr.style.height="20px";
	var tdchoosexm=document.createElement("td");
	tdchoosexm.className="list_thead";	
	tdchoosexm.style.verticalAlign="top";
	tdchoosexm.style.width="75%";	
	tdchoosexm.innerHTML="&nbsp;姓名";	
	var tdchoosechk=document.createElement("td");	
	tdchoosechk.className="list_thead";	
	tdchoosechk.style.width="25%";
	tdchoosechk.style.verticalAlign="top";
	tdchoosechk.innerHTML="&nbsp;删除";		
	choosetr.appendChild(tdchoosexm);
	choosetr.appendChild(tdchoosechk);
	choosetitletbody.appendChild(choosetr);
	choosetable.appendChild(choosetitletbody);	
	choosetable.appendChild(choosetbody);	
	choosediv.appendChild(choosetable);
		
	var str=pid+"#"+pname+"#"+pdeptid;
	var tempAry=new Array();
//	var tbody=document.getElementById("choosetbody");
	
	if(personAry!=null&&personAry.length>0){
		for(var i=0;i<personAry.length;i++){
			var splitstr=personAry[i].split("#");
			if(personAry[i]!=str){
				tempAry.push(personAry[i]);
				createChkdTable(splitstr[0],splitstr[1],splitstr[2]);
			}
		}
	}
	personAry=tempAry;	
}

function test(divId){
	alert(personAry.length);
	var personStr="";
	var personIdStr="";
	for(var i=0;i<personAry.length;i++){
		alert(personAry[i])
		var splitstr=personAry[i].split("#");
		personStr+=splitstr[1]+"；";
		personIdStr+=splitstr[0]+"；";
	}
	
	
}


function getChoosedPerson(divId){
	var personStr="";
	var personIdStr="";
	for(var i=0;i<personAry.length;i++){
		var splitstr=personAry[i].split("#");
		personStr+=splitstr[1]+"；";
		personIdStr+=splitstr[0]+"；";
	}
	
	$(divId).value=personStr;
	$(divId).personId=personIdStr;
}


//生成树html
function setHtml(dept,bm){	
	var space="";		
	if(bm){
		for(var i=0;i<bm.length;i++){
			space+="&nbsp;&nbsp;"		
		}
	}
	var deptson=dept.childNodes;		
	
	for(var j=0;j<deptson.length;j++){	
		var ccbm="";		
		var aid="";
		if(deptson[j].tagName =="dept"){
			ccbm=deptson[j].getAttribute("jgccbm");					
			aid=deptson[j].getAttribute("id");				
			var deptName=deptson[j].getAttribute("text")
			str+=space+"<img id='img_"+aid+"' src='../../../sm/image/tree/tree_open.bmp' onclick=\"Expand('"+aid+"');\" /><input type=checkbox id='chk_"+aid+"' ccbm='"+ccbm+"' name='"+aid+"' onclick=\"clickr('chk_"+aid+"','"+ccbm+"');\"><img id='i_"+aid+"' src='../../../sm/image/tree/folderOpen.gif' /><font  onclick=Expand('"+aid+"');>"+deptName+"<br></font>";
		}		
		
		if(deptson[j].tagName =="person"){		
			ccbm=deptson[j].getAttribute("ccbm");	
			var personid=deptson[j].getAttribute("id");		
			var personname=deptson[j].getAttribute("name");
			var perdept=deptson[j].getAttribute("deptId"); 
			str+=space+"<font style='width:13px'></font><input type=checkbox onclick=\"choosePerson('"+personid+"','"+personname+"','"+perdept+"')\" id='"+personid+"' xm='"+personname+"' ssjg='"+perdept+"' ccbm='"+ccbm+"' name='personchks'><img src='../../../sm/image/tree/book_titel.gif' />"+personname+"<br>";						
		}	
		
		if(aid!=""){			
			str+="<div id='d_"+aid+"'>"		
			setHtml(deptson[j],ccbm);	
			str+="</div>";	
		}
	}	
		
}

//点击选择部门下所有人员

function clickr(oid,dname){
	var chk=document.getElementById(oid);

	var inputs=document.getElementsByTagName("input");

	for(var i=0;i<inputs.length;i++){
		if(inputs[i].type == "checkbox"){
			if(inputs[i].ccbm&&inputs[i].ccbm.length>=dname.length){
				if(inputs[i].ccbm.substring(0,dname.length)==dname){
					if(chk.checked==true){
						inputs[i].checked=true;
						if(inputs[i].name=="personchks"){
							choosePerson(inputs[i].id,inputs[i].xm,inputs[i].ssjg);
						}						
					}else{
						inputs[i].checked=false;
						if(inputs[i].name=="personchks"){
							removePerson(inputs[i].id,inputs[i].xm,inputs[i].ssjg);
						}
					}
				}
			}
		}
	}

}

//机构树展开关闭
function Expand(sid){
	if(document.getElementById("d_"+sid).style.display==""){
		document.getElementById("img_"+sid).src="../../../sm/image/tree/tree_close.bmp";
		document.getElementById("i_"+sid).src="../../../sm/image/tree/folderClosed.gif";
		document.getElementById("d_"+sid).style.display="none";
	}else{
		document.getElementById("img_"+sid).src="../../../sm/image/tree/tree_open.bmp";
		document.getElementById("i_"+sid).src="../../../sm/image/tree/folderOpen.gif";
		document.getElementById("d_"+sid).style.display="";
	}
}	



//获取人员选中信息
function getChkPerson(divId){
	var persons=document.getElementsByName("personchks");
	var personStr="";
	var personIdStr="";
	if(persons!=null){
		for(var i=0;i<persons.length;i++){
			if(persons[i].checked==true){
				personStr+=persons[i].xm+"；";
				personIdStr+=persons[i].id+"；";
			}
		}
		$(divId).value=personStr;
		$(divId).personId=personIdStr;
	}	
}

	
function openDivWindow(obj,top,left,width,height,okmethod,canclemethod,title){
	var useDiv = document.createElement("div");
	useDiv.id="usedivs";
//	useDiv.style.backgroundColor="#DFEAF7";
	useDiv.style.width=width;
	useDiv.style.height=height;
	useDiv.style.textAlign="center";
	useDiv.style.zIndex = 1000;
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
           <iframe style="position:absolute;z-index:9;width:400;height:300;top:expression(this.nextSibling.offsetTop);left:expression(this.nextSibling.offsetLeft);" frameborder="0" ></iframe>\
           <div width="100%" height="100%">\
           <table id="poptable" width="100%" height="100%" style="border=0;z-index:10;" class="popup" cellpadding="0" cellspacing="0">\
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
	if((typeof(divPopup) != "undefined") && (divPopup != null))
	{
    	divPopup.parentNode.removeChild(divPopup);
    }
}	

// 杜（追加）

var file_num=0;
function addFile() {
	if (file_num >= 9)
		return alert("一次只允许上传10个文件！");
	for (i = 1; i <= file_num; i++) {
		if($("mfile"+i).value=="") return alert("请浏览文件后点击继续添加按钮！");
	}
	file_num = file_num + 1;
	document.getElementById("fileList")
			.insertAdjacentHTML(
					'beforeEnd',
					'<input id="mfile'
							+ (file_num)
							+ '" name="mfile'
							+ (file_num)
							+ '" type="file" style="width:200px;" onkeydown="return false;"/> <input type="button" id="delete" value="删除" onClick="doDelete()" /><br/>');
	document.getElementById("fileInfoList").insertAdjacentHTML(
			'beforeEnd',
			'<input id="fileInfo' + (file_num) + '" name="fileInfo'
					+ (file_num) + '"type="text"class="textwidth" />');

	
}

function setDisable() {
	$("buttonId").disabled = false;
}
function doDelete() {

	// $("fileList").innerHTML='<input ID="mfile0" type="file" name="mfile0"
	// style="width:200px;" onkeydown="return false;"/><input type="button"
	// value="继续添加" onclick="addFile();" />';
	var strFileList = '<input ID="mfile0" type="file" name="mfile0" style="width:200px;" onkeydown="return false;"/>'
			+ '<input type="button" value="继续添加" onclick="addFile();" /> <br/>';
	var fileInfoList = '<input type="hidden" id="insertYwid" name="insertYwid" value="<%=cpid %>"/>'
			+ '<input type="text" id="fileInfo0" name="fileInfo0" maxlength="128" class="textwidth" />';
	file_num = file_num - 1;
	for (i = 1; i <= file_num; i++) {
		strFileList = strFileList
				+ '<input id="mfile'
				+ i
				+ '" name="mfile'
				+ i
				+ '" type="file" style="width:200px;" onkeydown="return false;"/> <input type="button" id="delete" value="删除" onClick="doDelete()" /><br/>'
		fileInfoList = fileInfoList + '<input id="fileInfo' + (file_num)
				+ '" name="fileInfo' + +(file_num)
				+ '"type="text"class="textwidth" />';
	}
	$("fileList").innerHTML = strFileList;
	$("fileInfoList").innerHTML = fileInfoList;
}
/**
 * 显示附件信息
 */
function doloadPage(complaintId) {

	var url = "complaint.complaintmanage.getAttachmentInfo.d";
	url = encodeURI(url);
	var params = "complaintId="+complaintId;
	new Ajax.Request(url, {
				method : "post",
				parameters : params,
				onComplete : showAttachmentInfo
			})
}
/**
 * 显示附件信息回调函数<br>
 * @param {} xmlHttp
 */
function showAttachmentInfo(xmlHttp) {
	var xmldoc = xmlHttp.responseXML;
	var rows = xmldoc.getElementsByTagName("row");
	var filePath = "";
	var fileModfiy = "";
	showflg1 = false;
	showflg2 = false;
	for (var i = 0; i < rows.length; i++) {
		var resultCols = new Array();
		resultCols = rows[i].getElementsByTagName("col");

		var attachmentFilepath = resultCols[0].text;
		var attachmentModfiy = resultCols[1].text;
		var ywlx = resultCols[3].text;
		// 支队办理附件的显示
		if(ywlx == "保存支队办理结果附件"){
						// 附件路径存在的情况
			if (attachmentFilepath != null && attachmentFilepath != "") {
				var str1 = attachmentFilepath;
				var regstr = "/";
				var regresult = new RegExp(regstr);
				var sss = str1.split(regresult, '100');
				var need = sss[sss.length - 1];
				var a = need.split('.');
				var show = " <a href=\"#\"  onclick=\"openWPS('"
						+ attachmentFilepath + "')\" >" + a[0] + "." + a[1]
						+ "</a><br/>";
				// + "</a> <input type='button' onClick='delFile('" +
				// resultCols[2].text + "');' value='删 除...' />" +
				// "<br/>";
				$("fileZdResult").innerHTML = show;
				showflg1 = true;
			}
		// 支队以外附件的显示
		} else if (ywlx == "警情投诉"){
			// 附件路径存在的情况
			if (attachmentFilepath != null && attachmentFilepath != "") {
				var str1 = attachmentFilepath;
				var regstr = "/";
				var regresult = new RegExp(regstr);
				var sss = str1.split(regresult, '100');
				var need = sss[sss.length - 1];
				var a = need.split('.');
				var show = " <a href=\"#\"  onclick=\"openWPS('"
						+ attachmentFilepath + "')\" >" + a[0] + "." + a[1]
						+ "</a><br/>";
				// + "</a> <input type='button' onClick='delFile('" +
				// resultCols[2].text + "');' value='删 除...' />" +
				// "<br/>";
				filePath = filePath + show;
				showflg2 = true;

				var showModfiy = ' <input id="fileInfo' + (i)
						+ '" name="fileInfo' + (i)
						+ '"type="text"class="textwidth" value=" '
						+ attachmentModfiy + '" /> ';
				fileModfiy = fileModfiy + showModfiy;
			}
		}


	}
	if (showflg2) {
		$("fileList").innerHTML = filePath;
		$("fileInfoList").innerHTML = fileModfiy;
	} else {
		$("fileList").innerHTML = "无上传文件";
		$("fileInfoList").innerHTML = "无附件描述";
	}
//	if (groupStep == '000031' || groupStep == '000049' || groupStep == '000079') {
//		$("fileZdResult").innerHTML = '<input type="file" name="ZDAttachmentFile"' 
//									+ 'id="ZDAttachmentFile" class="textwidth"'
//									+ 'onkeydown="return false;" />';
//	} else {
//		$("fileZdResult").innerHTML = '<input type="file" name="ZDAttachmentFile"' 
//									+ 'id="ZDAttachmentFile" class="textwidth"'
//									+ 'onkeydown="return false;" disabled />';
//	}
}
/**
 * * 显示附件信息的链接<br>
 */
function openWPS(fileName) {

	var widthv = 400;
	var heightv = 200;
	var xposition = (screen.availWidth - widthv)/2;
	var yposition = (screen.availHeight - heightv)/2;
	var feature = 'height='+heightv+',width='+widthv+',top='+yposition+',left='+xposition+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no';	
	var param= "../FileDownload.jsp?fileName="+fileName;
	param=encodeURI(param);
	window.open(param, "",feature);
 	}
 	
// 杜（追加）	