package com.ehl.dynamicinfo.gpsCar.action;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.MultiDBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;

public class gpsCarAction extends Controller {
	Logger logger = Logger.getLogger(gpsCarAction.class);
	
	/**
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAllgpsCarInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"),"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"),"");
		String departName = StringHelper.obj2str(request.getParameter("departName"),"");
		
		String whereStr = " and to_char(GPSTIME, 'yyyymmdd') = to_char(sysdate, 'yyyymmdd') " + 
   			" and to_char(GPSTIME, 'hh24') <= to_char(sysdate, 'hh24') " + 
   			" and to_number(to_char(sysdate, 'hh24')) < (to_number(to_char(GPSTIME, 'hh24')) + 2)";
	 	
		// �������,���ƺ���,��������,����״̬,X,Y
			String sql;
   			if (departName == "") {
   				sql = "select CARCODE,CARNUMBER,substr(DEPARTMENT,0,2) depart,CARSTATE,LONGTITUDE,LATITUDE from T_GPS_CARINFO where " +
				"LONGTITUDE != 0 and LATITUDE !=0 " + whereStr +
				"order by depart";
   			}
   			else {
   				sql = "select CARCODE,CARNUMBER,substr(DEPARTMENT,0,2) depart,CARSTATE,LONGTITUDE,LATITUDE from T_GPS_CARINFO where " +
				"LONGTITUDE != 0 and LATITUDE !=0 " + whereStr + 
				"order by depart";
   			}
		
		System.out.println("***********"+sql);
		Object[][] res = MultiDBHandler.getMultiResult("gps",sql);
		String str = DataToXML.objArrayToXml(res);
		out.write(str);
		System.out.println(str);
		return null;
	}
	
	/**
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetGpscarInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {

		// ��ȡ���͹����ľ�γ����Ϣ
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"),"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"),"");
		String carCode = StringHelper.obj2str(request.getParameter("carCode"),"");
		
		String whereStr = "";
	 	
		// �������,���ƺ���,��������,����״̬,X,Y
		String sql = "select CARCODE,CARNUMBER,DEPARTMENT,CARSTATE,LONGTITUDE,LATITUDE,GPSTIME from T_GPS_CARINFO where CARCODE = '" + carCode + "'" +
				" and LONGTITUDE != 0 and LATITUDE !=0 " + whereStr + 
				" order by CARNUMBER";
		System.out.println("***********"+sql);

		// ������ʾ��Ϣ
		StringBuffer sbXml = new StringBuffer();
		try {
			Object[][] oResult = MultiDBHandler.getMultiResult("gps",sql);
			
			if (oResult != null) {
				sbXml.append("<table class='popup-contents' style='margin-top:10px ;' width='100%' cellSpacing='0' cellPadding='0' border='0'>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>���ƺ��룺</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[0][1],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>�������ţ�</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[0][2],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>GPSʱ�䣺</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[0][6],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>����״̬��</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[0][3],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td colspan='2'>&nbsp;</td></tr>");
				sbXml.append("</table>");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

		PrintWriter out = response.getWriter();

		out.write(sbXml.toString());
		out.close();
		return null;

	}
	
	/**
	 * ȡ��ӵ��gps�����Ĳ���<br/>
	 * ȡ��ӵ��gps�����Ĳ���
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAllgpsCarDepartInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
	throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"),"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"),"");
   			// �������,���ƺ���,��������,����״̬,X,Y
   			String sql = "select distinct substr(DEPARTMENT,0,2) depart from T_GPS_CARINFO where " +
   			"LONGTITUDE != 0 and LATITUDE !=0 " + "" + 
   			"order by depart";
   			System.out.println("***********"+sql);
   			Object[][] res = MultiDBHandler.getMultiResult("gps",sql);
   			String str = DataToXML.objArrayToXml(res);
   			out.write(str);
   			System.out.println(str);
   			return null;
	}
	/**
	 * ȡ��ָ������ӵ�е�����gps�����Ĳ���<br/>
	 * ȡ��ָ������ӵ�е�����gps�����Ĳ���
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetDepartCarLst(Action action,
			HttpServletRequest request, HttpServletResponse response)
	throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"),"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"),"");
		// �������,���ƺ���,��������,����״̬,X,Y
		String sql = "select distinct substr(DEPARTMENT,0,2) depart from T_GPS_CARINFO where " +
		"LONGTITUDE != 0 and LATITUDE !=0 " + "" + 
		"order by depart";
		System.out.println("***********"+sql);
		Object[][] res = MultiDBHandler.getMultiResult("gps",sql);
		String str = DataToXML.objArrayToXml(res);
		out.write(str);
		return null;
	}
	
	//��webservice��ȡgps�ϱ���Ϣ
	public String getGpsReport(){
		String service_url = "http://10.40.30.207:808/tfmservice/Service.asmx?wsdl";
		String nameSpace = "http://fw.com/";
		String method = "ManualUpdateLinkDirStatusOnRoadIdSEPosUserName";
		
		Object[][] paramArray = {
				{"department",XMLType.XSD_STRING},
				{"userName",XMLType.XSD_STRING},
				{"password",XMLType.XSD_STRING},
				{"carTeam",XMLType.XSD_STRING},
				{"startDate",XMLType.XSD_STRING},
				{"endDate",XMLType.XSD_STRING}
		};
		String department = "�׻�¼";
		String userName = "yhl";
		String password = "yhl";
		String carTeam = "��ɽ�и��ٹ�·���";
		String startDate = "2011-02-01";
		String endDate = "2011-03-01";
		
		Object[] paramValue = {department,userName,password,carTeam,startDate,endDate};
		
		QName returnType = XMLType.XSD_STRING;
		String xmlStr = "";
		try {
			xmlStr = callDoNetWebService(service_url,nameSpace,method,paramArray,paramValue,returnType);
			System.out.println(xmlStr);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return xmlStr;
	}
	
	/**
	 * ����donet webservice������װ<p/>
	 * @param String service_url webservice��ַ
	 * @param String nameSpace �����ռ�
	 * @param String method ����ķ���
	 * @param String[][] paramArray ��ȡ�����ݽڵ㼯�� 
	 * 	paramArray[0][0] ������;paramArray[0][1] ��������<org.apache.axis.encoding.XMLType>
	 * @param String [] paramValue �����������
	 * @param returnType ����ķ�������
	 * @throws ServiceException
	 * @throws MalformedURLException
	 * @throws RemoteException
	 */
	public static String callDoNetWebService(String service_url,String nameSpace,String method,Object[][] paramArray,Object[] paramValue,QName returnType)
				throws ServiceException,MalformedURLException, RemoteException{
		if("".equals(service_url)||"".equals(method)){
			return "ȱ��WebService��ַ�򷽷�����";
		}
		if("".equals(nameSpace)){
			nameSpace = "http://tempuri.org/";
		}
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		// ����service����URL
		call.setTargetEndpointAddress(new java.net.URL(service_url));
		call.setUseSOAPAction(true);
		String soapActionURI = nameSpace + method;
		// call.setSOAPActionURI(namespace + "getFileInfo");//URI��ʽ�������ռ�+������
		call.setSOAPActionURI(soapActionURI);
		call.setOperationName(new QName(nameSpace,method));
		
		QName name = XMLType.XSD_STRING;
		String parameter = "";
		if(paramArray!=null && paramArray.length>0){
			for(int i=0;i<paramArray.length;i++){
				parameter = StringHelper.obj2str(paramArray[i][0]);
				name = (QName)paramArray[i][1];
				call.addParameter(new QName(nameSpace,parameter),name,ParameterMode.IN);
			}
		}

		// �����ķ���ֵ����
		call.setReturnType(returnType);
		
		String xmlStr = call.invoke(paramValue).toString();
		return xmlStr;
	}
}
