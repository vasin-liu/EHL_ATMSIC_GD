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
/**
 * @����˵��: �����ͼ�����ݶ�ȡ��
 * @�����ߣ�������
 * @�������ڣ�2008-10-16
 */
public class VidiconInfoMap extends Controller {
	Logger logger = Logger.getLogger(VidiconInfoMap.class);
	
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
		String jgccbm = StringHelper.obj2str(request.getParameter("jgccbm"),"");
		StringBuffer xmlData = new StringBuffer();
	
		StringBuffer sb = new StringBuffer();
		if(!jgccbm.equals("")){
			//��ȡ�������Ϣ
			Object[][] oResult = null;
			try {
				sb.append("SELECT DISTINCT A.GLID,A.LONGITUDE ||','|| A.LATITUDE,A.SSXT FROM ATMS_EQUIPMENT_ZB A,T_SYS_DEPARTMENT B");
				sb.append(" WHERE A.SSJG=B.JGID AND A.LONGITUDE*A.LATITUDE!=0");
				switch (CctvTools.MODELTYPE) {
					case CctvTools.MODEL_CCTV:
						sb.append(" AND A.SSXT='CCTV'");
						break;
					case CctvTools.MODEL_TGS:
						sb.append(" AND A.SSXT='TGS'");
						break;
					case CctvTools.MODEL_ALL:
						sb.append(" AND (A.SSXT='CCTV' OR A.SSXT='TGS')");
						break;
					default:
						sb.append(" AND A.SSXT='CCTV'");
				}
				sb.append(" AND B.JGCCBM LIKE '"+jgccbm+"%'");
				
				oResult = DBHandler.getMultiResult(sb.toString());
			} catch (Exception e) {
				logger.error("[CCTV]" + "��ȡ�������Ϣ����:"+e);
				e.printStackTrace();
			}
			
			//����XML��Ϣ
			xmlData.append("<?xml version='1.0' encoding='UTF-8'?>\n");
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
		}else{
			logger.error("[CCTV]" + "���������α���Ϊ��");
		}

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
		PrintWriter out = response.getWriter();
		String camid = StringHelper.obj2str(request.getParameter("camid"),"");
		String ssxt = StringHelper.obj2str(request.getParameter("ssxt"),"");
		//������ʾ��Ϣ
		StringBuffer sbXml = new StringBuffer();
		
		if(!camid.equals("")){
			Object[] oResult = null;
			//��ȡ�ĵ�ǰ���ѡ������������Ϣ
			try {
				StringBuffer sb = new StringBuffer();
				sb.append("SELECT GLID,CPMC,SSXT FROM ATMS_EQUIPMENT_ZB WHERE GLID='" + camid + "'");
				sb.append(" AND SSXT='" + ssxt + "'");
				
				oResult = DBHandler.getLineResult(sb.toString());
				if(oResult != null){
					String camID = StringHelper.obj2str(oResult[0],"");
					String camNAME = StringHelper.obj2str(oResult[1],"");
					if (oResult != null){
						sbXml.append("<table class='popup-contents' style='margin-top:10px ;' width='100%' cellSpacing='0' cellPadding='0' border='0'>");
						sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='80'>��������룺</td><td width='130'>" + camID + "</td></tr>");
						sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>��������ƣ�</td><td>" + camNAME + "</td></tr>");
						sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>����ϵͳ��</td><td>" + ssxt + "</td></tr>");
						sbXml.append("<tr style='padding:1px 1px 1px 1px'>");
						sbXml.append("<td>&nbsp;</td>");
						sbXml.append("<td>");
						if(isVideoBack()){
							sbXml.append("<input type='image' src='../../../cctv/image/button/btnvideoplayback.gif' align='right' onClick=\"vidiconmap.showVideo('"+ camID +"','back','"+ ssxt +"');\"/>");
						}
						sbXml.append("<input type='image' src='../../../cctv/image/button/btnvideoplay.gif' align='right' onClick=\"vidiconmap.showVideo('"+ camID+"','play','"+ ssxt +"');\"/>");
						sbXml.append("</td>");
						sbXml.append("</tr></table>");
					}
				}
			} catch (Exception e) {
				out.write("δ��ѯ����ϸ��Ϣ");
				logger.error("[CCTV]" + "��ȡ���������Ϣ����:"+e);
				out.close();
				return null;
			} 
		}else{
			logger.error("[CCTV]" + "����camidΪ��");
		}

		//�ش����������Ϣ
		if (sbXml.length() == 0) {
			out.write("δ��ѯ����ϸ��Ϣ");
		} else {
			out.write(sbXml.toString());
		}
		out.close();
		return null;
	}
	
	//�ж��Ƿ���ʾ��Ƶ�ط�
	private boolean isVideoBack(){
		boolean bShow = false;
		String isVideoBack = com.appframe.common.Setting.getString("isVideoBack"); 
		if("true".equals(isVideoBack)){
			bShow = true;
		}
		return bShow;
	}
	
	/**
	 * @�汾�ţ�1.0
	 * @����˵��������camid��ȡ��Ƶͨ����Ϣ
	 * @�������ڣ�2009-09-20
	 */
	public ActionForward doGetChannelInfo(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		//��ȡ���͹����ľ�γ����Ϣ
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String camid = StringHelper.obj2str(request.getParameter("camid"),""); 
//		camid="441202";
		StringBuffer xmlData = new StringBuffer();
		
		if(!"".equals(camid)){
			Object[] oResult = null;
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT REMOTEIP,REMOTEPORT,CHANNLUSER,REMOTEPWD,INPORTNUMBER,(SELECT CPMC FROM ATMS_EQUIPMENT_ZB WHERE ssxt='TGS' AND GLID='" + camid + "') FROM T_CCTV_CHANNEL");
			sb.append(" WHERE CHLID=(SELECT CHANNELID FROM T_CCTV_CAM WHERE CAMID='" + camid + "')");
			try {
				oResult = DBHandler.getLineResult(sb.toString());
	//			����XML��Ϣ
				xmlData.append("<?xml version='1.0' encoding='UTF-8'?>\n");
				xmlData.append("<rfXML>\n");
				xmlData.append("<RFWin>\n");
				
				if (oResult != null) {
					xmlData.append("<row id='0'>");
					if(oResult != null){
						for (int i = 0; i < oResult.length; i++) {			
							xmlData.append("<col>" + StringHelper.obj2str(oResult[i],"") + "</col>");	
						}
					}
					xmlData.append("</row>\n");
				}
			} catch (Exception e) {
				logger.error("[CCTV]" + "��ȡChannelInfo����:"+e);
				e.printStackTrace();
			} 
			xmlData.append("</RFWin>\n");
			xmlData.append("</rfXML>\n");
		}else{
			logger.error("[CCTV-��ȡChannelInfo��]" + "����camidΪ��");
		}
		out.write(xmlData.toString());
		out.close();
		return null;
	}
}
