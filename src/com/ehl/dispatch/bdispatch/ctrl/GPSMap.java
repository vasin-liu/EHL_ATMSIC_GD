package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.Console;
import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
 
import com.appframe.data.sql.MultiDBHandler;
import com.appframe.utils.StringHelper;

import com.ehl.dispatch.bdispatch.util.ConstDefine;
import com.ehl.dispatch.bdispatch.util.*;
/**
 * 
 * @======================================================================================================================================
 * 
 * @����˵��: GPS������Ϣ.
 * @�����ߣ�wwj
 * @��������: 2008-8-20
 * @=========================================== ====================================================
 */
public class GPSMap extends Controller 
{
	 /**
	 * 
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * @���ߣ�wwj
	 * @�汾�ţ�
	 * @����˵������ȡָ����Χ�����е�GPS������Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2008-8-20
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public ActionForward doReadPoints(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable 
	{
		// ��ȡ���͹����ľ�γ����Ϣ
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
	 
		// ��ȡ������Ϣ
		String sql =null;
		
		String userCountyCode = StringHelper.obj2str( request.getParameter("USERCOUNTYCODE"),"");
		
		sql = "SELECT DISTINCT tg.carcode, tg.cityid,tg.carnumber,tg.cartype,tg.department,tg.driver," +
				"tg.longtitude,tg.latitude,tg.angle,tg.cartelnumber,tg.isonline " 
			 + " FROM t_gps_carinfo tg  " 
			 + " WHERE 1=1 ";
		if( -1 == userCountyCode.indexOf( ConstDefine.DETACHMENT) )
		{
			//
			sql += "  AND tg.DEPARTMENTID='" + userCountyCode + "'";
			
		}
		
		Object[][] oResult = MultiDBHandler.getMultiResult("gps46", sql);
		
		// ����XML��Ϣ
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");

		if (oResult != null) 
		{
			for (int i = 0; i < oResult.length; i++) 
			{
				xmlData.append("<row id='" + i + "'>");
				for (int j = 0; j < oResult[i].length; j++) 
				{
					String strResult = StringHelper.obj2str(oResult[i][j], "");
					xmlData.append("<col>" + strResult + "</col>");
				}
  				xmlData.append("</row>\n");
			}
		}
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");

		// ���������Ϣ
		PrintWriter out = response.getWriter();
		out.write(xmlData.toString());
		out.close();

		return null;
	}
 
	
 
	
	/**
	 * 
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * @���ߣ�wwj
	 * @�汾�ţ�
	 * @����˵������ȡGPS�����б�.
	 * @������
	 * @���أ�
	 * @�������ڣ�2008-8-21
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public ActionForward doReadTracePoints(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// ��ȡ���͹����ľ�γ����Ϣ
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		String carcode = request.getParameter("carcode");
		String start = null == request.getParameter("start")? "":request.getParameter("start");
		String terminal = null== request.getParameter("terminal")? "":request.getParameter("terminal");
		// ��ȡ������Ϣ

		String sql = "select ID,CARCODE,CITYID,CARTELNUMBER,VELOCITY,LONGTITUDE,LATITUDE,to_char(gpstime,'yyyy-MM-dd hh24:mi') ";
		sql += " from t_gps_record ";
		sql += " where carcode = '"+carcode+"'";
		
		if(  "".equals(start)  && "".equals(terminal))
		{
			//û��˵��������ʷ�켣����Ĭ��Ϊ��2Сʱ�ڵĹ켣
			sql += "  AND (sysdate-gpstime)*24<=2 ";
//			sql += "  AND to_char(gpstime,'yyyy-MM-dd')=to_char(sysdate,'yyyy-MM-dd') ";
		}
		else
		{
			if(  !"".equals(start)  )
			{
				sql += "  AND gpstime >= to_date('" + start + ":00" + "','yyyy-MM-dd hh24:mi:ss') ";
			}
			if(  !"".equals(terminal)  )
			{
				//û��˵��������ʷ�켣����Ĭ��Ϊ���չ켣
				sql += "  AND gpstime <= to_date('" + terminal + ":59" + "','yyyy-MM-dd hh24:mi:ss') ";
			}
		}
		sql += " order by id";
		System.out.println(sql);
		Object[][] oResult = MultiDBHandler.getMultiResult("gps46", sql);

		// ����XML��Ϣ
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");

		if (oResult != null) {
			for (int i = 0; i < oResult.length; i++) {
				xmlData.append("<row id='" + i + "'>");
				for (int j = 0; j < oResult[i].length; j++) {
					String strResult = StringHelper.obj2str(oResult[i][j], "");
					xmlData.append("<col>" + strResult + "</col>");
				}
				xmlData.append("</row>\n");
			}
		}

		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");

		// ���������Ϣ
		PrintWriter out = response.getWriter();
		out.write(xmlData.toString());
		out.close();

		return null;
	}
	/**
	 * 
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * @���ߣ�wwj
	 * @�汾�ţ�
	 * @����˵������ȡ����GPS������Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2008-8-20
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public ActionForward doGetGPSInfoByPosition(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		// ��ȡ���͹����ľ�γ����Ϣ
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		String id = request.getParameter("id");

		String sql = "select cityid,carnumber,cartype,department,driver,cartelnumber ";
		sql += " from t_gps_carinfo ";
		sql += " where carcode= '" + id + "'";
		sql += " order by id";
		Object[][] oResult = MultiDBHandler.getMultiResult("gps46", sql);

		// ������ʾ��Ϣ
		StringBuffer sbXml = new StringBuffer();

		if (oResult != null) {
			sbXml.append("���ڳ���" + oResult[0][0] + "<br/>");
			sbXml.append("�������ƣ�" + oResult[0][1] + "<br/>");
			sbXml.append("�������ͣ�" + oResult[0][2] + "<br/>");
			sbXml.append("�������ţ�" + oResult[0][3] + "<br/>");
			sbXml.append("ִ���񾯣�" + oResult[0][4] + "<br/>");
			sbXml.append("���к��룺" + oResult[0][5] + "<br/>");
		}

		PrintWriter out = response.getWriter();
		if (sbXml.length() == 0) {
			out.write("δ��ѯ����ϸ��Ϣ");
		} else {
			out.write(sbXml.toString());
		}
		out.close();
		return null;
	}
	
	/**
	 * @author LChQ 2008-9-24
	 * 
	 * <li>��ѯGPS������Ϣ </li>
	 * 
	 */
	public ActionForward doQueryGPSCar(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");

		// ��ȡ·����Ϣ
		Object[][] oResult = null;
		StringBuilder xmlData = new StringBuilder();
		boolean succeed = true;
		
		//���ƺ���
		String regnumber = StringHelper.obj2str(request.getParameter("regnumber"), "");
		try 
		{
			String owner = StringHelper.obj2str(request.getParameter("county"), "");
			
			String  strWhere = " 1=1 "; 
			
			//�¼�����
			if(! "".equals(regnumber))
			{
					strWhere += " AND CARNUMBER like '%" + regnumber + "%'";
			}
			if(!"".equals(owner))
			{
				strWhere += " AND department like '%" + owner + "%'";
			}
			
		 	String selectSQL = "SELECT  CARCODE,ID,CARNUMBER,CARTELNUMBER,DRIVER,department,LONGTITUDE,LATITUDE ";
				selectSQL += "FROM t_gps_carinfo WHERE " + strWhere;
			//selectSQL += " AND T_DEPARTMENT.DEPTID = t_gps_carinfo.departmentid";
				//PLACE,UPDOWN,TLEVEL,
			Console.infoprintln(" query info " + selectSQL);
			oResult = MultiDBHandler.getMultiResult("gps46",selectSQL);
			
		}
		catch (Exception ex) 
		{
			Console.infoprintln("doQueryIncident fail:" + ex.getMessage());
			succeed = false;
		}
		if(succeed &&  null != oResult)
		{
			xmlData.append("<?xml version='1.0' encoding='UTF-8'?>\n");
			xmlData.append("<rfXML>\n");
			xmlData.append("<RFWin>\n");
			//����xml����
			for(int i=0; i< oResult.length;i++)
			{
				xmlData.append("<row  id='");
				xmlData.append(i);
				xmlData.append("'>");
				for (int j = 0; j < oResult[i].length; j++) 
				{
					xmlData.append("<col>");
					String colString = StringHelper.obj2str(oResult[i][j],"");
					xmlData.append(colString);
					xmlData.append("</col>");
				}
				xmlData.append("</row>\n");
			}
			xmlData.append("</RFWin>\n");
			xmlData.append("</rfXML>\n");
		}
		// ����¹���Ϣ
		PrintWriter out = response.getWriter();
		out.write(xmlData.toString());
		out.close();
		return null;
	}
	
}