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
		response.setContentType("text/xml"); // 指定输出内容的格式
		PrintWriter out = response.getWriter();
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"),"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"),"");
		String departName = StringHelper.obj2str(request.getParameter("departName"),"");
		
		String whereStr = " and to_char(GPSTIME, 'yyyymmdd') = to_char(sysdate, 'yyyymmdd') " + 
   			" and to_char(GPSTIME, 'hh24') <= to_char(sysdate, 'hh24') " + 
   			" and to_number(to_char(sysdate, 'hh24')) < (to_number(to_char(GPSTIME, 'hh24')) + 2)";
	 	
		// 车辆编号,车牌号码,部门名称,车辆状态,X,Y
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

		// 获取传送过来的经纬度信息
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"),"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"),"");
		String carCode = StringHelper.obj2str(request.getParameter("carCode"),"");
		
		String whereStr = "";
	 	
		// 车辆编号,车牌号码,部门名称,车辆状态,X,Y
		String sql = "select CARCODE,CARNUMBER,DEPARTMENT,CARSTATE,LONGTITUDE,LATITUDE,GPSTIME from T_GPS_CARINFO where CARCODE = '" + carCode + "'" +
				" and LONGTITUDE != 0 and LATITUDE !=0 " + whereStr + 
				" order by CARNUMBER";
		System.out.println("***********"+sql);

		// 生成显示信息
		StringBuffer sbXml = new StringBuffer();
		try {
			Object[][] oResult = MultiDBHandler.getMultiResult("gps",sql);
			
			if (oResult != null) {
				sbXml.append("<table class='popup-contents' style='margin-top:10px ;' width='100%' cellSpacing='0' cellPadding='0' border='0'>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>车牌号码：</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[0][1],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>所属部门：</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[0][2],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>GPS时间：</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[0][6],"") + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>车辆状态：</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[0][3],"") + "</td></tr>");
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
	 * 取得拥有gps车辆的部门<br/>
	 * 取得拥有gps车辆的部门
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
		response.setContentType("text/xml"); // 指定输出内容的格式
		PrintWriter out = response.getWriter();
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"),"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"),"");
   			// 车辆编号,车牌号码,部门名称,车辆状态,X,Y
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
	 * 取得指定部门拥有的所有gps车辆的部门<br/>
	 * 取得指定部门拥有的所有gps车辆的部门
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
		response.setContentType("text/xml"); // 指定输出内容的格式
		PrintWriter out = response.getWriter();
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"),"");
		String jgid = StringHelper.obj2str(request.getParameter("jgid"),"");
		// 车辆编号,车牌号码,部门名称,车辆状态,X,Y
		String sql = "select distinct substr(DEPARTMENT,0,2) depart from T_GPS_CARINFO where " +
		"LONGTITUDE != 0 and LATITUDE !=0 " + "" + 
		"order by depart";
		System.out.println("***********"+sql);
		Object[][] res = MultiDBHandler.getMultiResult("gps",sql);
		String str = DataToXML.objArrayToXml(res);
		out.write(str);
		return null;
	}
	
	//从webservice获取gps上报信息
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
		String department = "易华录";
		String userName = "yhl";
		String password = "yhl";
		String carTeam = "佛山市高速公路大队";
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
	 * 调用donet webservice方法封装<p/>
	 * @param String service_url webservice地址
	 * @param String nameSpace 命名空间
	 * @param String method 请求的方法
	 * @param String[][] paramArray 获取的数据节点集合 
	 * 	paramArray[0][0] 参数名;paramArray[0][1] 参数类型<org.apache.axis.encoding.XMLType>
	 * @param String [] paramValue 请求参数对象
	 * @param returnType 请求的返回类型
	 * @throws ServiceException
	 * @throws MalformedURLException
	 * @throws RemoteException
	 */
	public static String callDoNetWebService(String service_url,String nameSpace,String method,Object[][] paramArray,Object[] paramValue,QName returnType)
				throws ServiceException,MalformedURLException, RemoteException{
		if("".equals(service_url)||"".equals(method)){
			return "缺少WebService地址或方法名！";
		}
		if("".equals(nameSpace)){
			nameSpace = "http://tempuri.org/";
		}
		
		Service service = new Service();
		Call call = (Call) service.createCall();
		// 设置service所在URL
		call.setTargetEndpointAddress(new java.net.URL(service_url));
		call.setUseSOAPAction(true);
		String soapActionURI = nameSpace + method;
		// call.setSOAPActionURI(namespace + "getFileInfo");//URI格式：命名空间+方法名
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

		// 方法的返回值类型
		call.setReturnType(returnType);
		
		String xmlStr = call.invoke(paramValue).toString();
		return xmlStr;
	}
}
