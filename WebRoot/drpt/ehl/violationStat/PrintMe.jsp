<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<OBJECT id=WebBrowser classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 height=0 width=0></OBJECT>
<script language="JavaScript">
	function printMe(){
	
		var style = "";
		style+="<style type=\"text\/css\"> "+
			"<!-- "+
			".table{ "+
			"	font-size:11px; "+
			"	width:99%; "+
			"   border-top:1px solid #000000;"+
			"	border-left:1px solid #000000; "+
			"	border-right:0px solid #000000; "+
			"	border-bottom:0px solid #000000; "+
			"} "+
			".wTableTopCenter{ "+
			"	 font-weight:bold; "+
			"	 border:0px solid #000000; "+
			"    border-bottom:1px solid #000000;"+
			"	 border-right:1px solid #000000; "+
			"	 font-size:11px; "+
			"	 text-align:center;"+
			"	 text-valign:bottom;"+
			"} "+
			".wTableCenterCenter{ "+
			"	 border:0px solid #000000; "+
			"    border-bottom:1px solid #000000;"+
			"	 border-right:1px solid #000000; "+
			"	 font-size:11px; "+
			"	 text-align:center;"+
			"	 text-valign:bottom;"+
			"} "+
			"</style>";                                        
		
		var startTime = document.getElementById('startTime').value;
		var endTime = document.getElementById('endTime').value;
        var danwei =$("jgmcHidden").value;
       
        var tbsj = "";
        if (startTime==endTime){
        	tbsj = startTime;
        }else{
        	tbsj = "从"+startTime+"到"+endTime;
        }
        
		var newwin = window.open('','',''); 
		var dataHTML = document.getElementById("tdData").innerHTML; 
		var queryHTML = "<TABLE width=\"99%\">";
		queryHTML +="<p><TR>"+
			"<td colspan=\"3\" align=\"center\"  style=\"font-size:17px\"><b>"+endTime.substring(0,4)+"年春运客运车辆违法统计表</b></td>"+
			"</TR></p>"+
			"<p>"+
			"<p>"+
			"<tr><td colspan='3'></td></tr>"+
			"<TR>"+
				"<td width='15%'align=\"right\">填报单位：</td>"+
				"<td width='35%' align=\"left\">"+danwei+"</td>"+
				"<td width='50%' align=\"right\">统计日期："+tbsj+"</td>"+
			"</TR></table>";

		newwin.document.write(style+queryHTML+dataHTML); 
		newwin.document.location.reload(); 
		newwin.print(); 
		newwin.close();
		
	}

</script>