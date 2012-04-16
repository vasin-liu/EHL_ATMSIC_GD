package com.ehl.cctv;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.webgis.data.SDEAccess;
import com.ehl.webgis.project.ProjectionTransform;
import com.ehl.webgis.util.PointUtil;
/**
 * @����˵��: �����ͼ�����ݶ�ȡ��
 * @�����ߣ�������
 * @�������ڣ�2008-10-16
 */
public class VidiconInfoMapOnGis extends Controller {
	private String layerName = "CCTV_PT"; // ����ͼ������
	private SDEAccess sde = new SDEAccess(); // ����SDE
	Logger logger = Logger.getLogger(VidiconInfoMapOnGis.class);
	
	/**
	 * @���ߣ�linbh
	 * @�汾�ţ�1.0
	 * @����˵������ȡBuffer�뾶.
	 * @���أ���������
	 * @�������ڣ�2009-01-21
	 */
	public static String getRadius() throws Throwable {		
		String radius = "";
		
		if (ProjectionTransform.isUseProject()){
			radius="50";
		}else{
			radius="0.0001";
		}
		return radius;
	}	
	
	/**
	 * @�汾�ţ�1.0
	 * @����˵������ȡָ����Χ�����е����������Ϣ
	 * @�������ڣ�2008-09-20
	 */
	public ActionForward doReadVidiconPoints(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		//��ȡ���͹����ľ�γ����Ϣ
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");

		//��ȡ�������Ϣ
		Object[][] oResult = null;
		try {
			PointUtil pUtil = new PointUtil(sde.initConnection(), layerName);
			oResult = pUtil.getFieldsByCondition("", "ID,SHAPE");
		} catch (Exception e) {
			logger.error("[CCTV]" + "��ȡ�������Ϣ����:");
			logger.error(e.getMessage());
		} finally {
			sde.closeAO();
		}

		//����XML��Ϣ
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

		//����������Ϣ
		PrintWriter out = response.getWriter();
		out.write(xmlData.toString());
		out.close();

		return null;
	}

	/**
	 * @�汾�ţ�1.0
	 * @����˵��������λ�û�ȡ�������������Ϣ
	 * @�������ڣ�2009-09-20
	 */
	public ActionForward doGetVidiconInfo(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		//��ȡ���͹����ľ�γ����Ϣ
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		String strLongtitude = request.getParameter("longtiude");
		String strLatitude = request.getParameter("latitude");

		//��ȡҪ��ѯ��������ֶ���Ŀ
		String strFields = "ID";

		//������ʾ��Ϣ
		StringBuffer sbXml = new StringBuffer();

		//��ȡ�ĵ�ǰ���ѡ������������Ϣ
		try {
			PointUtil pUtil = new PointUtil(sde.initConnection(), layerName);
			Object[][] vidiconPoint = pUtil.getFieldsByPosition(strLongtitude + "," + strLatitude, getRadius(), strFields);
			if (vidiconPoint != null) {
				String id = StringHelper.obj2str(vidiconPoint[0][0], "");
				if (!id.equals("")) {
					Object[] oResult = getVidiconInfoFromMIS(id);
					String camID = id;
					if (oResult != null) {
						sbXml.append("<table class='popup-contents' style='margin-top:10px ;' width='100%' cellSpacing='0' cellPadding='0' border='0'>");
						sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='80'>��������룺</td><td width='130'>" + StringHelper.obj2str(oResult[0],"") + "</td></tr>");
						sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>��������ƣ�</td><td>" + StringHelper.obj2str(oResult[1],"") + "</td></tr>");
						sbXml.append("<tr style='padding:1px 1px 1px 1px'><td colspan='2'>&nbsp;</td></tr>");
						sbXml.append("<tr style='padding:1px 1px 1px 1px'>");
						sbXml.append("<td>&nbsp;</td><td><input type='image' src='../../image/button/btnvideoplayback.gif' align='right' onClick=\"vidiconmap.showVideo('"+ camID +"','back');\"/>");
						sbXml.append("<input type='image' src='../../image/button/btnvideoplay.gif' align='right' onClick=\"vidiconmap.showVideo('"+ camID+"','play');\"/></td>");
						sbXml.append("</tr></table>");
					}
				}
			}
		} catch (Exception e) {
			logger.error("[CCTV]" + "��ȡ���������Ϣ����:");
			logger.error(e.getMessage());
		} finally {
			sde.closeAO();
		}

		//�ش����������Ϣ
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
	 * @�汾�ţ�1.0
	 * @����˵������ȡ�����MIS������Ϣ
	 * @�������ڣ�2008-09-20
	 */
	private Object[] getVidiconInfoFromMIS(String id) {
		Object[] oResult = null;
		//��ѯSQL���
		String strSql = "select camid, name from t_cctv_cam cam where camid='" + id + "'";
		try {
			//�γɲ�ѯ�����
			oResult = DBHandler.getLineResult(strSql);
		} catch (Exception e) {
			logger.error("[CCTV]" + "��ȡ�����MIS��Ϣ����:");
			logger.error(e.getMessage());
		}

		return oResult;
	}

	/**
	 * @�汾�ţ�1.0
	 * @����˵��������������ȡ�������������Ϣ
	 * @�������ڣ�2008-09-20
	 */
	public ActionForward doGetVidiconInfoByCond(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		//��ȡ���͹����ľ�γ����Ϣ
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		String strVidiconId = request.getParameter("id");

		StringBuffer sbXml = new StringBuffer();

		//��ȡҪ��ѯ��������ֶ���Ŀ
		String strFields = "ID,NAME";

		//��ȡ�ĵ�ǰ���ѡ������������Ϣ
		Object[][] vidiconPoint = null;
		try {
			PointUtil pointUtil = new PointUtil(sde.initConnection(), layerName);
			String strWhereClause = "ID='" + strVidiconId + "'";
			vidiconPoint = pointUtil.getFieldsByCondition(strWhereClause, strFields);
		} catch (Exception e) {
			logger.error("[CCTV]" + "��ȡ���������Ϣ����:");
			logger.error(e.getMessage());
		} finally {
			sde.closeAO();
		}

		//����XML��Ϣ
		if (vidiconPoint != null) {
			sbXml.append("<?xml version='1.0' encoding='UTF-8'?>\n");
			sbXml.append("<rfXML>\n");
			sbXml.append("<RFWin>\n");

			for (int i = 0; i < vidiconPoint.length; i++) {
				sbXml.append("<row id='");
				sbXml.append(i);
				sbXml.append("'>");
				for (int j = 0; j < vidiconPoint[i].length; j++) {
					sbXml.append("<col>");
					sbXml.append(vidiconPoint[i][j]);
					sbXml.append("</col>");
				}
				sbXml.append("</row>\n");
			}

			sbXml.append("</RFWin>\n");
			sbXml.append("</rfXML>\n");
		}

		//�ش����������Ϣ
		PrintWriter out = response.getWriter();
		out.write(sbXml.toString());
		out.close();

		return null;
	}

	/**
	 * @�汾�ţ�1.0
	 * @����˵�������������������Ϣ��ȡ������ľ�γ������
	 * @�������ڣ�2008-09-20
	 */
	public ActionForward doGetPointFromID(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String strID = StringHelper.obj2str(request.getParameter("vidiconID"), ""); // ��ȡ���������

		if (!strID.equals("")) {

			try {
				PointUtil pUtil = new PointUtil(sde.initConnection(), layerName);
				String strWhere = "ID='" + strID + "'";
				String strPoint = pUtil.getGeometriesByCondition(strWhere);
				PrintWriter out = response.getWriter();
				out.write(strPoint);
				out.close();

			} catch (Exception e) {
				logger.error("[CCTV]" + "��ȡ������ľ�γ���������:");
				logger.error(e.getMessage());
			} finally {
				sde.closeAO();
			}
		}
		return null;
	}

}
