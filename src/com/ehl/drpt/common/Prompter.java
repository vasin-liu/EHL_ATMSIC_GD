package com.ehl.drpt.common;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.drpt.violationStat.action.ViolationStatAction;

/**
 * @======================================================================================================================================
 * @����˵��:��ܰ��ʾ
 * @�����ߣ�������
 * @�������� 2010-1-19 1:26:43
 * @======================================================================================================================================
 */
public class Prompter  extends Controller{
	Logger logger = Logger.getLogger(ViolationStatAction.class);

	/**
	 * @�汾�ţ�1.0
	 * @����˵������ܰ��ʾ��ѯ����
	 * @������
	 * @���أ���ѯ���
	 * @���ߣ�������
	 * @�������ڣ�2010-1-13 
	 * */
	public ActionForward doPrompter(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String depart = StringHelper.obj2str(request.getParameter("depart"),""); //����ID
		out.write(getData(depart));
		out.close();
		return null;
	}
	/**
	 * @����: wuyl
	 * @�汾�ţ�1.0
	 * @����˵����
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-1-19 1:29:15
	 */
	public String getData(String depart) throws Exception {

		if( "".equals(depart)){
			logger.error("[��ܰ��ʾ]"+ "��ܰ��ʾ��Ϣ" + "����depart����");
			return null;
		}

		String sql = "";
		sql = "select rzbh from t_oa_dayreport";
		sql += " where 1=1 ";
		sql += " and to_char(tjrq,'yyyy-mm-dd')=to_char(sysdate-1,'yyyy-mm-dd')";
		sql += " and substr(rzbh,1,6)="+depart.substring(0, 6);
		Object[][] rsObj=null;
		try{
			rsObj = DBHandler.getMultiResult(sql);
		}catch(Exception exp){
			logger.error("[��ܰ��ʾ]" + "��ܰ��ʾ��Ϣʱ��SQL���� \n"+sql);
			exp.printStackTrace();
			return null;
		}
		if(rsObj==null || rsObj.length<1){
			return null;
		}else{
			return CommonXML.getXML("success");
		}
	}

}
