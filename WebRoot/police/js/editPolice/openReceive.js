/**
 *author:wangxt
 *desc:编辑接报信息
 *date:2010-1-11
*/
function receiveReport(res,feedbackid,alarmid) {
   var returnValuestr = window.showModalDialog("../policeEdit/receiveReport.jsp?feedbackid="+feedbackid+"&alarmid="+alarmid, "", "dialogWidth:400px;dialogHeight:350px"); 
   //window.open("../policeEdit/receiveReport.jsp?feedbackid='"+feedbackid+"'&alarmid='"+alarmid+"'","","height=300,width=350,left="+eval(screen.Width-800)/2+",top="+eval(screen.Height-560)/2+"");
   //alert(nowdate);
   //$("receive_time").value = nowdate;
}



