<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 2009-12-25 added by likai -->
<OBJECT id=WebBrowser classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2
 height=0 width=0></OBJECT>
<script language="JavaScript">
	var HKEY_Root,HKEY_Path,HKEY_Key; 
	HKEY_Root="HKEY_CURRENT_USER"; 
	HKEY_Path="\\Software\\Microsoft\\Internet Explorer\\PageSetup\\"; 
	
	function ok(){
		windows.close();
	}
	
	function PageSetup_Null() 
	{ 
		 try 
		 { 
		 	var Wsh=new ActiveXObject("WScript.Shell"); 
		  	HKEY_Key="header"; 
		  	Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,"");
		  	HKEY_Key="footer"; 
		  	Wsh.RegWrite(HKEY_Root+HKEY_Path+HKEY_Key,""); 
		 } 
	 	 catch(e){} 
	}
	 
	function printFor(){
	/*
		PageSetup_Null();// pagesetup_null();
		document.all("printno").style.display="none";
		document.all("printno2").style.display="none";
		document.all("print").style.display="block";
		prompt("",document.all("print").innerHTML);
		
   document.all.WebBrowser.ExecWB(8,1);
   document.all.WebBrowser.ExecWB(7,1);
		//document.all("print").style.display="none";	
		document.all("printno").style.display="";
		document.all("printno2").style.display="";
		*/
		var style = "";
		style+="<style type=\"text\/css\"> "+
			"<!-- "+
			"table{ "+
			"	font-size:11px; "+
			"} "+
			".tableInput{ "+
			"	border:1px solid #000000; "+
			"} "+
			".td_r_b1{ "+
			"   border-bottom:1px solid #000000; "+
			"	border-right:1px solid #000000; "+
			"	text-align:left; "+
			"} "+
			".td_b{ "+
			"   border-bottom:1px solid #000000; "+
			"	border-top1px solid #000000; "+
			"	border-right:1px solid #000000; "+
			"	border-left:1px solid #000000; "+
			"	text-align:left; "+
			"} "+
			
			".td_r_tfm{ "+
			"    border-bottom:1px solid #000000;"+
			"	 border-right:0px solid #000000; "+
			"	 text-align:center;"+
			"}"+
			".td_r_zh{"+ 
			"    border-bottom:1px solid #000000; "+
			"	 border-right:0px solid #000000;"+ 
			"	 text-align:center;"+
			"}"+
			".td_r_lastline{ "+
			"    border-bottom:0px solid #000000; "+
			"	 border-right:1px solid #000000; "+
			"	 text-align:center;"+
			"}"+
			".td_r_lastline2{"+ 
			"    border-bottom:0px solid #000000; "+
			"	 border-right:1px solid #000000; "+
			"	 text-align:left;"+
			"}"+
			".td_r_b{ "+
			"   border-bottom:1px solid #000000; "+
			"	border-right:1px solid #000000; "+
			"	text-align:center; "+
			"} "+
			".td_b{"+ 
			"   border-bottom:1px solid #000000;"+ 
			"	border-right:0px solid #000000; "+
			"	border-left:0px solid #000000; "+
			"	text-align:center;"+
			"}"+
			".STYLE2 { "+
			"	color: #FF0000; "+
			"} "+
			".input1{ "+
			"	border:0; "+
			"	width:40px; "+
			"	text-align:center; "+
			"} "+
			".input2{ "+
			"	border:0; "+
			"	width:40px; "+
			"	 "+
			"} "+
			".input3{ "+
			"	border:0; "+
			"	width:100px; "+
			"} "+
			"*{ "+
			"	margin:0; "+
			"	pading:0; "+
			"} "+
			"--> "+
			"</style>";                                        
		
		var startTime = document.getElementById('startTime').value;
		var endTime = document.getElementById('endTime').value;
        var danwei ="广东省交警总队";
       
        var tbsj = "";
        if (startTime==endTime){
        	tbsj = startTime;
        }else{
        	tbsj = "从"+startTime+"到"+endTime;
        }
        
		var newwin = window.open('','',''); 
		var dataHTML = document.getElementById("print").innerHTML; 
		var queryHTML = "<TABLE  id=titleTable cellSpacing=0 cellPadding=0 width=\99%\" align=center>";
		queryHTML +="<p><TR>"+
			"<td colspan=\"6\" align=\"center\"  style=\"font-size:17px\"><b>"+endTime.substring(0,4)+"年春运道路交通安全管理周报表</b></td>"+
			"</TR></p>"+
			"<p>"+
			"<p>"+
			"<tr><td colspan='6'></td></tr>"+
			"<tr><td colspan='6'></td></tr>"+
			"<TR>"+
				"<td width='10%'>填报单位：</td>"+
				"<td width='40%' colspan='2' align=\"left\">"+danwei+"</td>"+
				"<td width='50%' colspan=\"3\" align=\"right\">统计日期："+tbsj+"</td>"+
			"</TR></table>";

		//prompt('test',style+queryHTML+titleHTML);
		newwin.document.write(style+queryHTML+dataHTML); 
		newwin.document.location.reload(); 
		newwin.print(); 
		newwin.close();
		
	}

</script>

<script language="VBScript">
	
		dim hkey_root,hkey_path,hkey_key
		hkey_root="HKEY_CURRENT_USER"
		hkey_path="\Software\Microsoft\Internet Explorer\PageSetup"
		
		'//设置网页打印的页眉页脚为空
		function pagesetup_null()
				on error resume next
				Set RegWsh = CreateObject("WScript.Shell")
				hkey_key="\header" 
				RegWsh.RegWrite hkey_root+hkey_path+hkey_key,""
				hkey_key="\footer"
				RegWsh.RegWrite hkey_root+hkey_path+hkey_key,""
		end function
		
		'//设置网页打印的页眉页脚为默认值
		function pagesetup_default()
				on error resume next
				Set RegWsh = CreateObject("WScript.Shell")
				hkey_key="\header" 
				RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"&w&b页码，&p/&P"
				hkey_key="\footer"
				RegWsh.RegWrite hkey_root+hkey_path+hkey_key,"&u&b&d"
		end function
</script>	