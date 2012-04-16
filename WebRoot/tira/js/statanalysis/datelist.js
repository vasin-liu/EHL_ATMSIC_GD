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
    createDate: function(ends,tag,tag2){
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
    	var dateStartId = "dateStartId";
    	var dateEndId = "dateEndId";
    	var dateDescId = "dateDescId";
    	var dateSEId = "selDateTdId";
		//var dateDesc = document.getElementById(dateDescId);
		var dateSE = document.getElementById(dateSEId);
    	if(type == 1){
    		//dateDesc.innerHTML = "年份：";
    		var inner = "<select id='"+dateStartId+"' align='left' style='width:80px;'>";
			inner += Util.prototype.createDate(new Date().getYear()-20,1,"1");
			inner += "</select>";
			dateSE.innerHTML = inner;
    	}else if(type == 2){
    		//dateDesc.innerHTML = "起止年份：";
    		var inner = "<span><select id='"+dateStartId+"' style=\"width:60px;\">";
			inner += Util.prototype.createDate(new Date().getYear()-20,0,"0");
			inner += "</select></span>";
			inner += "<span>--</span>";
			inner += "<span><select id='"+dateEndId+"' style=\"width:60px;\">";
			inner += Util.prototype.createDate(new Date().getYear()-20,1,"0");
			inner += "</select></span>";
			dateSE.innerHTML = inner;
    	}else if(type == 3){
    		//dateDesc.innerHTML = "起止日期：";
    		var today = new Date();       
        	var day = today.getDate(); 
        	if(day.toString().length<2)  
        	  day='0'+day;    
        	var month = today.getMonth() + 1;
        	if(month.toString().length<2)  
        	  month='0'+month;
        	var year = today.getYear();       
        	var date = year + "-" + month + "-" + day; 
    		var inner = "<input id='"+dateStartId+"' style=\"width:70px;\" value='"+date+"' onClick=\"SelectDate(this,0);\" readOnly/>";
			inner += "<span>--</span>";
			inner += "<span><input id='"+dateEndId+"' style=\"width:70px;\" value='"+date+"' onClick=\"SelectDate(this,0);\" readOnly/></span>";
			
			dateSE.innerHTML = inner;
    	}
    },
    showDate:function(){
    	var date = new Date();
    	var year = date.getYear();
    	var month = date.getMonth();
    	var day = date.getDate();
    	var date = year + "-" + (month+1) + "-" + day;
    	document.getElementById("dateStartId").value = (year-1) + "-" + (month+1) + "-" + day;
    	document.getElementById("dateEndId").value = date;
    }
	
};