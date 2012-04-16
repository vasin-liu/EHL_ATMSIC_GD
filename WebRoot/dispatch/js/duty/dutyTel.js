function doOnload() {
	
	jQuery("#data").dataTable();
}
//得到所有值班电话
function searchResult() {
	var jgid = jQuery("#jgmcId").val();
	var url = "dutyTel.jsp";
	var params = "";
	params += "?jgid=" + jgid;
	params = encodeURI(params);
	jQuery("#jgmcId").val("");
	window.location.href = url + params;
}
//导出Excel
function showExcelInfo(){
	var jgid = jQuery("#excelId").val();
	var sql="select rownum,jgmc,zbdh1 from t_sys_department where 1=1 ";
	jgid = jgid.split("；");
	var jgidStr="";
	if (jgid!="" && jgid != null) {
		 sql += " and jgid in (" ;
	    for(var i=0;i<jgid.length;i++){
			sql+=jgid[i];
			if(i<jgid.length-2){
				sql+=",";
			}
	    }
	    sql+=")";
	}
	sql=convertSql(sql);
 	var left = (screen.availWidth-800)/2;
    var top = (screen.availHeight-500)/2; 
	var url = "dispatch.duty.showExcel.d?" + "searchSql=" + sql;
	url = encodeURI(url);
    window.open(url,"","height=500,width=800,top="+top+",left="+left+",toolbar=yes,menubar=yes,scrollbars=yes,resizable=yes,location=no,status=no");
}