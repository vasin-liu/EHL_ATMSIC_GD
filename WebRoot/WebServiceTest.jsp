<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="org.apache.axis.client.Service"%>
<%@page import="org.apache.axis.client.Call"%>
<%@page import="javax.xml.namespace.QName"%>
<%@page import="org.apache.axis.encoding.XMLType"%>
<%@page import="javax.xml.rpc.ParameterMode"%>

<%
String xmlStr = "";
try{
	String service_url = "http://10.40.30.207:808/tfmservice/Service.asmx?wsdl";
	Service service = new Service();
	Call call = (Call) service.createCall();
	// 设置service所在URL
	call.setTargetEndpointAddress(new java.net.URL(service_url));
	call.setUseSOAPAction(true);
	String soapActionURI = "http://tempuri.org/ManualUpdateLinkDirStatusOnRoadIdSEPosUserName";
	// call.setSOAPActionURI(namespace + "getFileInfo");//URI格式：命名空间+方法名
	call.setSOAPActionURI(soapActionURI);
	String space = "http://tempuri.org/";
	call.setOperationName(new QName(space,
			"ManualUpdateLinkDirStatusOnRoadIdSEPosUserName"));
	String pRoadID = "G4_JZGS";
	String pStartPos = "1KM";
	String pEndPos = "10KM";
	String pLinkDirType = "上行";
	String pCongestStatus = "拥堵";
	String pCongestReason = "事故 修路";
	int pManualMinute = 200;
	String pUpdateUserName = "霞霞";
	// 路段编号
	call.addParameter(new QName(space, "pRoadID"), XMLType.XSD_STRING,
			ParameterMode.IN);
	// 检测器检测点 开始公里数 + "KM"
	call.addParameter(new QName(space, "pStartPos"),
			XMLType.XSD_STRING, ParameterMode.IN);
	// 检测器检测点 结束公里数 + "KM"
	call.addParameter(new QName(space, "pEndPos"), XMLType.XSD_STRING,
			ParameterMode.IN);
	// 道路方向
	call.addParameter(new QName(space, "pLinkDirType"),
			XMLType.XSD_STRING, ParameterMode.IN);
	// 拥堵状态
	call.addParameter(new QName(space, "pCongestStatus"),
			XMLType.XSD_STRING, ParameterMode.IN);
	// 拥堵原因
	call.addParameter(new QName(space, "pCongestReason"),
			XMLType.XSD_STRING, ParameterMode.IN);
	// 干涉时间
	call.addParameter(new QName(space, "pManualMinute"),
			XMLType.XSD_INT, ParameterMode.IN);
	// 修改人姓名
	call.addParameter(new QName(space, "pUpdateUserName"),
			XMLType.XSD_STRING, ParameterMode.IN);

	// 方法的返回值类型
	call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);

	xmlStr = call.invoke(
			new Object[] { pRoadID, pStartPos, pEndPos, pLinkDirType,
					pCongestStatus, pCongestReason, pManualMinute,
					pUpdateUserName }).toString();
	if ("0".equals(xmlStr)) {
		System.out.println("更新拥堵信息失败！！");
	} else if ("1".equals(xmlStr)) {
		System.out.println("更新拥堵信息成功！！");
	} else {
		System.out.println("未更新拥堵信息成功！！");
	}
}catch(Exception e){
	e.printStackTrace();
}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	
	</head>

	<body>
		<%="xmlStr:"+xmlStr%>
	</body>
</html>
