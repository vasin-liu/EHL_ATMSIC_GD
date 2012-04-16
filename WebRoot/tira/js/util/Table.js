

function Table() {
	
}


Table.prototype.getData = function() {
	//拼table
	var table = "<table width=\"100%\" border=\"0\" cellpadding=\"0\" "
		+" onmouseover=\"changeto()\" onmouseout=\"changeback()\" cellspacing=\"0\" "
		+" class=\"table\"  style=\"font-size:15px\">";
	table += "<tr  class=\"titleTopBack\"> "
		+"<td align=\"center\" height=\"22\" class=\"td_r_b\" colspan=\""+titlespan+"\">"+title+"</td>"
		+"</tr>";//标题
	//副标题
	table += "<tr>";
	var cols = datas[0].getElementsByTagName("col");
	for (var j = 0; j < cols.length; j++) {
		table += "<td class=\"td_r_b\">"+cols[j].text+"</td>";
	}
	table += "<td class=\"td_r_b\">总数</td>";//每行总计
	table += "</tr>";
	//主体
	for (var i = 1; i < datas.length; i++) {
		var cols = datas[i].getElementsByTagName("col");
		var rowtotal = 0;
		table += "<tr>";
		table += "<td class=\"td_r_b\">"+cols[0].text+"</td>";//列首
		//数据
		for (var j = 1; j < cols.length; j++) {
			table += "<td class=\"td_r_b\">"+cols[j].text+"</td>";
			rowtotal += Number(cols[j].text);
			coltotal[j-1][i-1] = Number(cols[j].text);
		}
		table += "<td class=\"td_r_b\">"+rowtotal+"</td>";//每行总计
		table += "</tr>";
	}
	//尾行
	table += "<tr>";
	table += "<td class=\"td_r_b\">总数</td>";
	var total = 0;
	//每列数据
	for (var i = 0; i < coltotal.length; i++) {
		var colTotal = 0;
		for (var j = 0; j < coltotal[i].length; j++) {
			colTotal += coltotal[i][j];
		}
		table += "<td class=\"td_r_b\">"+colTotal+"</td>";
		total += colTotal;
	}
	table += "<td class=\"td_r_b\">"+total+"</td>";//所有数据和
	table += "</tr>";
	table += "</table>";
}

Table.prototype.showTable = function(data,pId) {
	$(pId).innerHTML = data;
}






















