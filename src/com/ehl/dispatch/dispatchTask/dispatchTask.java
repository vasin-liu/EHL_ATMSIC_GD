package com.ehl.dispatch.dispatchTask;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.log4j.Logger;

public class dispatchTask {
	private Logger loger = Logger.getLogger(dispatchTask.class);
	

	/**
	 * 调用方伟公司WebService取得拥堵信息
	 * 调用方伟公司WebService取得拥堵信息<br/>
	 * @returnID
	 * @throws Exceptionx
	 */
	public static String[] gerCrowdInfoByFw() throws Exception {
		String service_url = "http://10.40.30.38/ri/WS_TrafficTransportationInfo_BizCtrlImpl.asmx?wsdl";
		Service service = new Service();
		Call call;
		call = (Call) service.createCall();

		// 设置service所在URL
		call.setTargetEndpointAddress(new java.net.URL(service_url));
		call.setUseSOAPAction(true);
		String soapActionURI = "http://WS.MOI.DirectTurning/GetYiHuaLuData_Ety";
		// call.setSOAPActionURI(nameSpace + "GetYiHuaLuData_Ety");//URI格式：命名空间+方法名
		call.setSOAPActionURI(soapActionURI);
		String nameSpace = "WS.MOI.DirectTurning";
		call.setOperationName(new QName(nameSpace, "GetYiHuaLuData_Ety"));

		String [] query_Ety = new String [11]; 
		String companySign = "";
		// 需要传递参数时，一定要写上参数的变量名称，也是用带命名空间的QName表示。加了参数后要设置返回类型（可能只是axis）。
		call.addParameter(new QName(nameSpace, "query_Ety"), XMLType.XSD_ANYTYPE, ParameterMode.IN);
		call.addParameter(new QName(nameSpace, "companySign"), XMLType.XSD_STRING, ParameterMode.IN);
		call.setReturnType(XMLType.XSD_ANYTYPE);

		// 调用结果的取得
		Object result = call.invoke(new Object[] { query_Ety, companySign});
		return (String[]) result;
	}

}
