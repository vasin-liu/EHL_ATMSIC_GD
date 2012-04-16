
/**
 * 取得当前时间减去deffer个月的时间
 * @param {Number} differ 与当前时间相差的月份
 * @return {String} 
 */
function getDate(differ) {
	var date = new Date();
	var year = date.getYear();
	var month = date.getMonth()+1;
	var day = date.getDate();
	var sep = "-";
	if (baseCheck([differ])==false){
		differ = 0;
	}
	if (month > differ) {
		month -= differ;
	}else{
		year -= 1;
		month = 12 +　month - differ; 
	}
	var encapDate = function(date) {
		date = "0"+date;
		return date.substr(date.length-2);
	}
	return year + sep + encapDate(month) + sep + encapDate(day);
}

/**
 * 设置时间
 * @param {String} dateId 时间id
 * @param {String} differ 与当前时间的月份差值
 * @return {void} 
 */
function setDate(dateId, differ) {
	if(baseCheck([dateId,$(dateId)])==false){
		return;
	}
	var date = getDate(differ);
	$(dateId).value = date;
}

/**
 * 设置起始时间和结束时间
 * @param {String} dateStartId 起始时间id
 * @param {String} dateEndId 结束时间id
 * @param {String} differ 与当前时间的月份差值
 * @return {void} 
 */
function setDateSE(dateStartId, dateEndId, differ) {
	if (baseCheck([dateStartId,dateEndId,differ])==false){
		return ;
	}
	setDate(dateStartId,differ);
	setDate(dateEndId);
}

/**
 * 
 */
function createDateTable() {
	/*
	 * 时间范围
	 * 时间类型
	 * 范围与类型的默认关系：
	 * 类型为范围类型(选择一个范围所确定的时间类型，例如，选择一天默认时间类型为天)
	 * 范围与类型的选择关系：
	 * 
	 * 
	 */
	var dateTable = 
		"<table style='font-size:12px;'>  \
			<tr> \
				<td >\
					时间类型：\
				</td>\
			</tr>\
			<tr>\
				<td>\
					<select onchange='' > \
						<option label='hh' value='时' />	\
						<option label='dd' value='天' />	\
						<option label='mm' value='月' />	\
						<option label='q' value='季  ' />	\
						<option label='yyyy' value='年' />	\
						<option label='area' value='段' />	\
					</select>\
				</td>\
			</tr>\
			<tr> \
				<td>\
					起止时间：\
				</td>\
			</tr>\
			<tr>\
				<td>\
					<input id='dateStartInputId' style='width:70px;' value='' onClick='SelectDate(this,0);' readOnly/> \
					--\
					<input id='dateEndInputId' style='width:70px;' value='' onClick='SelectDate(this,0);' readOnly/>\
				</td>\
			</tr>\
		</table>";
	
	
	




}
