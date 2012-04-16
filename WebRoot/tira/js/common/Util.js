Util = Class.create();

Util.prototype =
{	
	/**
     * 构造函数.
     * @param {Object}
     */
    initialize: function(){
    
    },
	
	/**
     * 创建时间选择列表.
     * @param {Object}
     */
    createYearSel: function(ends,tag,tag2){
    	var inner = "";
    	var date = new Date();
    	var year = date.getFullYear();
    	if(date.getMonth() == 0 && tag2 == "0"){
    		year = year -1;
    	}
	 	for(var start = year; start>=ends; start--) {
	 		if(start == year-2 && tag == 0){
	 			inner += "<option value='" + start + "' selected>" + start + "</option>";
	 		}else{
		 		inner += "<option value='" + start + "'>" + start + "</option>";
	 		}
	 	}
	 	return inner;
    },
    
    /**
     * 根据分析类型展示不同时间条件.
     * @param {Object}
     */
    checkTime: function(type){
		var yearDesc = document.getElementById("yearDesc");
		var yearSel = document.getElementById("year");
    	if(type == 1){
    		yearDesc.innerHTML = "年份：";
    		var inner = "<select id='yearSel' align='left' style='width:80px;'>";
			inner += Util.prototype.createYearSel(new Date().getYear()-20,1,"1");
			inner += "</select>";
			yearSel.innerHTML = inner;
    	}else if(type == 2){
    		yearDesc.innerHTML = "起止年份：";
    		var inner = "<span><select id='yearStart' style=\"width:60px;\">";
			inner += Util.prototype.createYearSel(new Date().getYear()-20,0,"0");
			inner += "</select></span>";
			inner += "<span>--</span>";
			inner += "<span><select id='yearEnds' style=\"width:60px;\">";
			inner += Util.prototype.createYearSel(new Date().getYear()-20,1,"0");
			inner += "</select></span>";
			yearSel.innerHTML = inner;
    	}else if(type == 3){
    		yearDesc.innerHTML = "起止日期：";
    		var today = new Date();       
        	var day = today.getDate(); 
        	if(day.toString().length<2)  
        	  day='0'+day;    
        	var month = today.getMonth() + 1;
        	if(month.toString().length<2)  
        	  month='0'+month;
        	var year = today.getYear();       
        	var date = year + "-" + month + "-" + day; 
    		var inner = "<input id='dateStart' style=\"width:70px;\" value='"+date+"' onClick=\"SelectDate(this,0);\" readOnly/>";
			inner += "<span>--</span>";
			inner += "<span><input id='dateEnds' style=\"width:70px;\" value='"+date+"' onClick=\"SelectDate(this,0);\" readOnly/></span>";
			
			yearSel.innerHTML = inner;
    	}
    }
	
};