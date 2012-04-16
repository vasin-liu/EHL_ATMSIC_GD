package com.ehl.dynamicinfo.crowd.action;

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
 * @����˵��: ·����Ϣ������
 * @�����ߣ�zhaoyu
 * @�������ڣ�2010-12-31
 */
public class CrowdAction extends Controller {
	static Logger  logger = Logger.getLogger(CrowdAction.class);
	
	/**
	 * @param roadid-��·���
	 * @param kmvalue-��ʼǧ��
	 * @param endkmvalue-����ǧ��
	 * @return
	 * @edit by lidq �޸�·����Ϣ�����࣬ȡ���㼯��sde��ȡģʽ����Ϊ���ݵ�·���ƴ�ת�����lcb_pt_mis���ȡ 2011-01-22
	 * @throws Throwable
	 */
	public ActionForward doGetRoadPoint(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		String roadid = StringHelper.obj2str(request.getParameter("roadid"),"");
		int kmvalue = StringHelper.obj2int(request.getParameter("kmvalue"),0);
		int endkmvalue = StringHelper.obj2int(request.getParameter("endkmvalue"),0);
		String linePointsStr = "";
		if("".equals(roadid)){
			out.write(linePointsStr);
			out.close();
			return null;
		}
		try{
			StringBuffer sqlwhere = new StringBuffer("SELECT DLMC,ZB FROM LCB_PT_MIS WHERE DLMC='"+roadid+"'");
			if(kmvalue < endkmvalue){
					sqlwhere.append(" AND to_number(QMZ)>="+kmvalue+" AND to_number(QMZ)<="+endkmvalue+"");
			}else if(kmvalue > endkmvalue){
					sqlwhere.append(" AND to_number(QMZ)>="+endkmvalue+" AND to_number(QMZ)<="+kmvalue+"");
			}else if(kmvalue == endkmvalue){
					sqlwhere.append(" AND to_number(QMZ)="+ String.valueOf(endkmvalue+1)+"");//�����ĩ����׮һ�£���һ������ʾ
			}
			sqlwhere.append(" ORDER BY TO_NUMBER(QMZ) ASC");
			
			Object[][] fieldValues = DBHandler.getMultiResult(sqlwhere.toString());
			if(fieldValues!= null && fieldValues.length>0){
				for(int j = 0; j < fieldValues.length; j ++){
					if(linePointsStr.length() == 0){
						linePointsStr = fieldValues[j][1].toString();
					}else{
						linePointsStr = fieldValues[j][1].toString();
					}
				}
			}
		}catch(Exception  ex){
			ex.printStackTrace();
			logger.error("ӵ�µ�·λ�ò�ѯ����:"+ex.getMessage());
		}finally{
			out.write(linePointsStr);
			out.close();
		}
		return null;
	}
}
