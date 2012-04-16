window.onload = function() {
	if (window.allZdAllAlarmCount) {
		var pnode = document.getElementById("allZdAllAlarmCount");
		setZdAlarmCount(allZdAllAlarmCount,pnode);
	}else if(window.zdAllDdAlarmCount){
		var title = "路面交通动态信息统计表";
		var cnames = ["发布单位","交通事故信息（条）","交通拥堵信息（条）","施工占道信息（条）"," &nbsp; &nbsp; 合计 &nbsp; &nbsp; "];
		var counts = zdAllDdAlarmCount,count;
		var colTotals = ["总计",0,0,0,0];
		var contain = document.getElementById("contain");
		row = contain.insertRow(-1);
		for ( var j = 0; j < cnames.length; j++) {
			cell = row.insertCell(j);
			cell.innerHTML = cnames[j];
		}
		var zdi;
		var row, cell;
		var m = 0;
		for ( var i = 0; i < counts.length; i++) {
			count = counts[i];
			if(!zdi && zdi != 0 && count[0].substring(4,6)=="00"){
				zdi = i;
				count[1] = count[1].replace("市公安局交通警察支队","") + "值班室";
				counts.push(count);
				m = 1;
				continue;
			}
			row = contain.insertRow(-1);
			if(i % 2 == m){
				row.style.backgroundColor="#EEEEEE";
			}
			for ( var j = 1; j < count.length; j++) {
				cell = row.insertCell(j-1);
				if(j==1){
					cell.style.textAlign = "left";
				}
				cell.innerHTML = count[j];
				if(j >= 2){
					colTotals[j-1] += count[j];
				}
			}
		}
		row = contain.insertRow(-1);
		cell = row.insertCell(0);
		if(i % 2 == m){
			row.style.backgroundColor="#EEEEEE";
		}
		cell.innerHTML = colTotals[0];
		for ( var j = 1; j < colTotals.length; j++) {
			cell = row.insertCell(j);
			cell.innerHTML = colTotals[j];
		}
	}
}


//10.40.30.206
var href = "http://localhost:8080/EHL_ATMSIC_GD/dispatch.alarmPublish.getZdAllDdAlarmCount.d";
function openDetail(link){
	var jgid = link.jgid;
	var name = "zdAllDdAlarmCount"+jgid;
	var url = href + "?jgid="+jgid;
	var feature = "left=0,top=0,width=720,height=520,scrollbars=1";
	window.parent.open(url,name,feature);
}

function encapJgmc(jgid,jgmc) {
	//target='_blank' href='"+href+"?jgid="+jgid+"' 
	return jgmc ? "<a href='javascript:void(0)' jgid='"+jgid+"' style='color:rgb(0,0,0);' onclick='window.openDetail(this)' >"
			+ jgmc + "</a>"
			: "";
}

function encapCount(count) {
	return count||count==0 ? "<span style='color:red;margin-left:4px;'>" + count + "</span>" : "";
}

function clear(table){
	if(table && table.nodeName == "TABLE"){
		while(table.rows.length != 0){
			table.deleteRow(0);
		}
	}
}

function setZdAlarmCount(zdCount, node) {
	if(!node){
		return;
	}
	clear(node);
	var row, cell;
	if(zdCount){
		var colNum = 2;//列数
		var lastZdNum = zdCount.length % colNum;//最后一行的支队数
		//补足最后一行剩余的单元格
		for ( var i = 0; i < lastZdNum; i++) {
			zdCount.push( ["", "", "" ]);
		}
		//填充单元格
		for ( var i = 0; i < zdCount.length;) {
			//插入行
			row = node.insertRow(i/colNum);
			for ( var j = 0; j < colNum; j++) {
				//插入列
				cell = row.insertCell(j);
				if(j%colNum==0){
					cell.style.paddingLeft = "20px";
				}
				cell.style.width = "100px";
				cell.innerHTML = encapJgmc(zdCount[i][0],zdCount[i][1])
						+ encapCount(zdCount[i][2]);
				i++;
			}
		}
	}else{
		row = node.insertRow(0);
		cell = row.insertCell(0);
		cell.innerHTML = "暂时未取到排行榜信息";
	}
}
