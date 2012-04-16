package com.ehl.dynamicinfo.accaccHistory.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;

public class accaccHistoryAction extends Controller {
	Logger loger = Logger.getLogger(accaccHistoryAction.class);
	
	/**
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAccHistoryInfo(Action action, HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		String sgid = StringHelper.obj2str(request.getParameter("sgid"), "");
		
		StringBuffer sbXml = new StringBuffer();
		if(!"".equals(sgid)){
			// �¹ʱ�ţ��¹ʷ���ʱ�䣬�¹ʵص�,��������������������ʧ������
			String sql = " SELECT SGBH,SGFSSJ,SGDD,SWRS,SSRS,SZRS FROM T_TIRA_ACD_C_ACDFILE WHERE XZQH='"+sgid.split(",")[0]+"' AND SGBH='"+sgid.split(",")[1]+"'";
			// ������ʾ��Ϣ
			SimpleDateFormat sdf0 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			try {
				Object[] oResult = DBHandler.getLineResult(sql);
				if (oResult != null) {
					String str = sdf0.format(oResult[1]);
					String showStr =  str.substring(0,4) + "��" + str.substring(4,6) + "��" + str.substring(6,8) + "��" + str.substring(8,10) + "ʱ" + str.substring(10,12) + "��";  
					sbXml.append("<table class='popup-contents' style='margin-top:10px ;' width='100%' cellSpacing='0' cellPadding='0' border='0'>");
					sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>�¹ʷ���ʱ�䣺</td><td width='200px' style='word-wrap: break-word; word-break: break-all;' >" + StringHelper.obj2str(showStr,"") + "</td></tr>");
					sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>�¹ʵص㣺</td><td width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[2],"") + "</td></tr>");
					sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>����������</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[3],"") + "</td></tr>");
					sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>����������</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[4],"") + "</td></tr>");
					sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>ʧ��������</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>" + StringHelper.obj2str(oResult[5],"") + "</td></tr>");
					sbXml.append("<tr style='padding:1px 1px 1px 1px'><td colspan='2'>&nbsp;</td></tr>");
					sbXml.append("</table>");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		PrintWriter out = response.getWriter();
		out.write(sbXml.toString());
		out.close();
		return null;

	}
	
	/**
	 * ��λ�¹���Ϣ
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAccInfo(Action action, HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			Object acc[][] = getAcc("");//�ɶ�λ�¹���Ϣ
			if (acc != null && acc.length != 0) {
				String pointObj = null;//�¹ʵ�
				for (int i = 0; i < acc.length; i++) {
					pointObj = getPointByCondition(acc[i]);
					if(pointObj != null){
						addPoint(acc[i],pointObj);//��λ
					}
				}
			}
		} catch(Exception e) {
			loger.error("��λ�쳣��"+e);
		}finally{
			String sql ="SELECT XZQH||','||SGBH,F_TIRA_GET_NAME('300403',SGLX),TO_CHAR(SGFSSJ,'YYYYMMDDHH24MISS'),X,Y FROM T_TIRA_ACD_C_ACDFILE WHERE SGFSSJ>=(SYSDATE-2) AND X != 0 AND Y != 0";
			try {
				Object[][] res = DBHandler.getMultiResult(sql);
				String str = DataToXML.objArrayToXml(res);
				out.write(str);
			} catch (Exception e2) {
				loger.error("��ȡ��ʷ�¹���Ϣ�쳣��"+e2);
			}
		}


		//out.write(xmlData.toString());
		out.close();
		return null;

	}
	
	/**
	 * ���¹�Դ���ȡ�¹���Ϣ
	 * @param start ��λ��ʼʱ��
	 */
	private Object [][] getAcc(String start){
		StringBuffer sb = new StringBuffer();
		Object acc[][] = null;
		
		sb.append("SELECT ACC.GLS,ACC.MS,ACC.XZQH,ACC.SGBH,TO_CHAR(ACC.SGFSSJ,'YYYY-MM-DD HH24:MI:SS'),ACC.SGLX,ACC.SWRS,ACC.SSRS,ACC.SWRSQ,ACC.SZRS,ACC.SGDD,ACC.JYAQ,ACC.LH,ACC.LM,ACC.XQ");
		sb.append(" FROM ACC_FILE ACC WHERE LH IS NOT NULL AND LM IS NOT NULL AND (ACC.GLS!=0 OR ACC.MS!=0)");
		sb.append(" AND ACC.SGFSSJ>=NVL((SELECT MAX(ACC.SGFSSJ) FROM T_TIRA_ACD_C_ACDFILE),(SYSDATE-2))");//Ĭ�ϲ�ѯ48Сʱ���ϱ��¹�
		try {
			acc = DBHandler.getMultiResult(sb.toString());
		} catch (Exception e) {
			loger.error("���¹ʱ��ȡδ��λ�¹���Ϣʧ�ܣ�"+e);
		}
		return acc;
	}
	
	/**
	 * д���¹���Ϣ���¹ʵ�
	 * @param accObj һ������λ�¹���Ϣ��points �¹ʵ�(X,Y)��ʽ
	 */
	private void addPoint(Object[] accObj,String points) {
		boolean isSpSuccess = false;
		try {
			if(!"".equals(points) && points.length()>2){
				String[] xy = points.split(",");
				try {
					StringBuffer sb = new StringBuffer();
					sb.append("INSERT INTO T_TIRA_ACD_C_ACDFILE");
					sb.append(" (GLS,MS,XZQH,SGBH,SGFSSJ,SGLX,SWRS,SSRS,SWRSQ,SZRS,SGDD,JYAQ,LH,LM,XQ,X,Y)");
					sb.append(" VALUES('"+accObj[0]+"','"+accObj[1]+"','"+StringHelper.obj2str(accObj[2], "")+"',");
					sb.append("'"+StringHelper.obj2str(accObj[3], "")+"',TO_DATE('"+StringHelper.obj2str(accObj[4], "")+"','YYYY-MM-DD HH24:MI:SS'),");
					sb.append("'"+StringHelper.obj2str(accObj[5], "")+"','"+StringHelper.obj2str(accObj[6], "")+"',");
					sb.append("'"+StringHelper.obj2str(accObj[7], "")+"','"+StringHelper.obj2str(accObj[8], "")+"',");
					sb.append("'"+StringHelper.obj2str(accObj[9], "")+"','"+StringHelper.obj2str(accObj[10], "")+"',");
					sb.append("'"+StringHelper.obj2str(accObj[11], "")+"','"+StringHelper.obj2str(accObj[12], "")+"',");
					sb.append("'"+StringHelper.obj2str(accObj[13], "")+"','"+StringHelper.obj2str(accObj[14], "")+"',");
					sb.append("'"+xy[0]+"','"+xy[1]+"')");
					isSpSuccess = DBHandler.execute(sb.toString());
				} catch (Exception e) {
					loger.error("��λ�¹���Ϣʧ�ܣ�"+e);
				}
			}
		} catch (Exception e) {
			loger.error("��λ�¹���Ϣʧ�ܣ�"+e);
		}
		
		/**
		 * ��λʧ��
		 */
		if(!isSpSuccess){
			loger.error("��λ�¹���Ϣʧ�ܣ�xzqh:"+StringHelper.obj2str(accObj[2], "")+";sgbh:"+StringHelper.obj2str(accObj[3], ""));
		}
	}
	
	/**
	 *  ���ݵ�·��ŵ�·����ģ����λ
	 * @param lh ·�ţ�lm ·����qmz������ֵ
	 */
	private String getPointByCondition(Object[] accObj){
		Object pointObj[] = null;
		String sgdd = StringHelper.obj2str(accObj[10],"");
		String lh = StringHelper.obj2str(accObj[12],"");
		String lm = StringHelper.obj2str(accObj[13],"");
		if("".equals(lh)&&"".equals(lm)){
			return "";
		}
		int qmz = StringHelper.obj2int(accObj[0], 0);
		int bmz = 0;//StringHelper.obj2int(accObj[1], 0); //��ʱ�����ǰ���ֵ
//		if(bmz>=0&&bmz<1000){
//			if(bmz%100!=0){
//				bmz = (int)bmz/100*100;
//			}
//		}else{
//			bmz = bmz%1000;
//		}
		StringBuffer sql = new StringBuffer();
		/**
		 *  1������·���ƻ��·�����ǧ��ֵ����ֵ���������¹ʵص�ƥ�䵽
		 *  2�����¹ʱ��·������·�Ź�����������ƥ��
		 */
		sql.append("SELECT ZB FROM LCB_PT_MIS WHERE ((INSTR('").append(sgdd).append("',DLMC)!=0 OR INSTR('").append(sgdd).append("',DLBH)!=0)");
		sql.append(" AND INSTR('").append(sgdd).append("',QMZ||'K')!=0 AND BMZ='").append(bmz).append("' AND ROWNUM=1)");
		sql.append(" OR (INSTR('"+lm+"',DLMC)!=0 AND QMZ='"+qmz+"' AND BMZ='"+bmz+"')");
		sql.append(" OR (INSTR('"+lh+"',DLBH)!=0 AND QMZ='"+qmz+"' AND BMZ='"+bmz+"')");
		try{
			pointObj = DBHandler.getLineResult(sql.toString());
		} catch (Exception e) {
			loger.error("��ȡ��·��ź͸��ٱ��ʧ�ܣ�"+e);
		}
		if(pointObj!=null && pointObj.length>0){
			return StringHelper.obj2str(pointObj[0],"");
		}else{
			return "";
		}
	}
}
