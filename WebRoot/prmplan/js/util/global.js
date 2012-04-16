/** 
    * desc:  日期比较
    * param: start 开始时间，ends 结束时间
    * return:
    * author：ldq
    * date:   2008-09-19
    * version: 1.0
    */

function compareDate(start, ends) {
	var arr = start.split("-");
	var starttime = new Date(arr[0], arr[1], arr[2]);
	var starttimes = starttime.getTime();
	var arrs = ends.split("-");
	var endtime = new Date(arrs[0], arrs[1], arrs[2]);
	var endtimes = endtime.getTime();
	if (starttimes >= endtimes) {
		alert("开始时间大于结束时间，请检查!");
		return false;
	} else {
		return true;
	}
}

/** 
 * desc:  旅行时间所用全局变量
 * author：zhaoy
 * date:   2008-09-24
 * version: 1.0
 */
  var  myInterval;
