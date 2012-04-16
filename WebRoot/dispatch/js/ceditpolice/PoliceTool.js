
AlarmTool = Class.create(); 
AlarmTool.prototype = Object.extend(new Abstract.Tool(), {
	cursorStyle: 'point',
	selected: false,
	alt: '标注事件',
		
//	clickHandler: function(e, toolbar){
//		markMap();
//	}
	clickHandler: function(e, toolbar){
		markAccident(e)
	}
});

