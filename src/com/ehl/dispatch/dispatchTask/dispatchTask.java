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
	 * ���÷�ΰ��˾WebServiceȡ��ӵ����Ϣ
	 * ���÷�ΰ��˾WebServiceȡ��ӵ����Ϣ<br/>
	 * @returnID
	 * @throws Exceptionx
	 */
	public static String[] gerCrowdInfoByFw() throws Exception {
		String service_url = "http://10.40.30.38/ri/WS_TrafficTransportationInfo_BizCtrlImpl.asmx?wsdl";
		Service service = new Service();
		Call call;
		call = (Call) service.createCall();

		// ����service����URL
		call.setTargetEndpointAddress(new java.net.URL(service_url));
		call.setUseSOAPAction(true);
		String soapActionURI = "http://WS.MOI.DirectTurning/GetYiHuaLuData_Ety";
		// call.setSOAPActionURI(nameSpace + "GetYiHuaLuData_Ety");//URI��ʽ�������ռ�+������
		call.setSOAPActionURI(soapActionURI);
		String nameSpace = "WS.MOI.DirectTurning";
		call.setOperationName(new QName(nameSpace, "GetYiHuaLuData_Ety"));

		String [] query_Ety = new String [11]; 
		String companySign = "";
		// ��Ҫ���ݲ���ʱ��һ��Ҫд�ϲ����ı������ƣ�Ҳ���ô������ռ��QName��ʾ�����˲�����Ҫ���÷������ͣ�����ֻ��axis����
		call.addParameter(new QName(nameSpace, "query_Ety"), XMLType.XSD_ANYTYPE, ParameterMode.IN);
		call.addParameter(new QName(nameSpace, "companySign"), XMLType.XSD_STRING, ParameterMode.IN);
		call.setReturnType(XMLType.XSD_ANYTYPE);

		// ���ý����ȡ��
		Object result = call.invoke(new Object[] { query_Ety, companySign});
		return (String[]) result;
	}

}
