package com.ehl.policeWorks.assess.action;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.cdispatch.dao.TrafficNewsFeedsDao;
import com.ehl.dispatch.cdispatch.util.CommonUtility;
import com.ehl.policeWorks.assess.AssessUtil;
import com.ehl.policeWorks.assess.dao.ScoreRecordDao;
import com.ehl.util.Array;

/**
 * ������Ϣ����<br/>
 * @date 2011-3-21
 */
public class AssessStandardAction extends Controller {

	private Logger logger = Logger.getLogger(AssessStandardAction.class);
	
	private ScoreRecordDao dao = new ScoreRecordDao();

	/**
	 * ���˱�׼��¼��<br/>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyAssessStandard(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// �¹ʵ�����
		String acc_001024 = StringHelper.obj2str(request.getParameter("acc_001024"),"");
		// ӵ�µ�����
		String crowd_001002 = StringHelper.obj2str(request.getParameter("crowd_001002"),"");
		// ռ��ʩ��
		String build_001023 = StringHelper.obj2str(request.getParameter("build_001023"),"");
		// ��Ϣ�ļ�
		String news_001004 = StringHelper.obj2str(request.getParameter("news_001004"),"");
		// ��Ϣ�ļ��ӷ�
		String news_001006 = StringHelper.obj2str(request.getParameter("news_001006"),"");
		// ���ϲ���
		String baoliao_001001 = StringHelper.obj2str(request.getParameter("baoliao_001001"),"");
		// ���ϲ�����
		String baoliao_001007 = StringHelper.obj2str(request.getParameter("baoliao_001007"),"");
		// ����δ��ʵ
		String baoliao_001005 = StringHelper.obj2str(request.getParameter("baoliao_001005"),"");
		// �۷�
		String kou_001003 = StringHelper.obj2str(request.getParameter("kou_001003"),"");
		// ������Ϣ����
		String news_001008 = StringHelper.obj2str(request.getParameter("news_001008"),"");
		// ������Ϣ����
		String news_001009 = StringHelper.obj2str(request.getParameter("news_001009"),"");
		
		StringBuffer sql = new StringBuffer(); 
	 	try {
	
			// �¹�����,ӵ�µ�����,ʩ�������֣���Ϣ�ļ�ʡ�����üӷ� ����Ϣ�ļ����������üӷ� ��
	 		// ӵ�±��ϲ��� ��ӵ�±��ϳ�ʱδ��ʵ��©�� ���۷�
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(acc_001024);
			sql.append("' where ASSESS_STANDARD_ID = '001024' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(crowd_001002);
			sql.append("' where ASSESS_STANDARD_ID = '001002' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(build_001023);
			sql.append("' where ASSESS_STANDARD_ID = '001023' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(news_001004);
			sql.append("' where ASSESS_STANDARD_ID = '001004' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(news_001006);
			sql.append("' where ASSESS_STANDARD_ID = '001006' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(baoliao_001001);
			sql.append("' where ASSESS_STANDARD_ID = '001001' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(baoliao_001005);
			sql.append("' where ASSESS_STANDARD_ID = '001005' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(kou_001003);
			sql.append("' where ASSESS_STANDARD_ID = '001003' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(baoliao_001007);
			sql.append("' where ASSESS_STANDARD_ID = '001007' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(news_001008);
			sql.append("' where ASSESS_STANDARD_ID = '001008' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			sql.delete(0, sql.length());
			sql.append("update T_OA_ASSESS_CODE set ASSESS_PRJECT_VALUE = '");
			sql.append(news_001009);
			sql.append("' where ASSESS_STANDARD_ID = '001009' ");
			System.out.println("***********"+sql);
			DBHandler.execute(String.valueOf(sql));
			
			out.write("success");	
	 	} catch (Exception e) {
	 		e.printStackTrace();
	 		logger.error("���뿼�˱�׼��Ϣ���� sql:" + sql);
	 	}
		return null;
	}

	/**
	 * ȡ�ÿ��˱�׼��Ϣ<br/>
	 * ȡ�ÿ��˱�׼��Ϣ
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAssessStandardInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		StringBuffer sql = new StringBuffer();
		Object[] result = null;
		try {
			sql.append("select distinct (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001024'),");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001002') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001023') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001004') ,  ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001006') ,  ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001001') ,  ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001005') ,  ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001003') ,  ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001007') ,  ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001008') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001009')  ");
			sql.append(" from T_OA_ASSESS_CODE  ");
			result = DBHandler.getLineResult(sql.toString());
			System.out.println("getAssessStandardInfo.sql==>"+sql);
			out.write(DataToXML.objArrayToXml(result));
			System.out.println(DataToXML.objArrayToXml(result));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ȡ�ÿ��˱�׼��Ϣ���� sql:" + sql);
		}
		out.close();
		return null;
	}

	/**
	 * ȡ�ÿ�����Ϣ<br/>
	 * ȡ�ÿ�����Ϣ
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAssessInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		// ��������
		String zhiduiId = StringHelper.obj2str(request.getParameter("zhiduiId"),"");
		// ���ֿ�ʼ����
		String startDate = StringHelper.obj2str(request.getParameter("startDate"),"");
		// ���ֽ�������
		String endDate = StringHelper.obj2str(request.getParameter("endDate"),"");
		// ��������
		String type = StringHelper.obj2str(request.getParameter("type"),"");
		//������Ŀ
		int sortItem = StringHelper.obj2int(request.getParameter("sortItem"),9);
		PrintWriter out = response.getWriter();
		String [][] toXmlInfo = null; 
		try {
			String[] returnStr = null;
			// ֧��idΪ�� ��������֧�ӵĿ��˳ɼ�
			if("".equals(zhiduiId)) {
				Object[][] zhiDuiInfo = AssessUtil.getZhiDuiIdName();
				// �������е�֧��
				if(zhiDuiInfo != null) {
					toXmlInfo = new String [zhiDuiInfo.length][12]; 
					for( int i=0;i<zhiDuiInfo.length;i++) {
						// ֧��id
						toXmlInfo[i][0] = String.valueOf( zhiDuiInfo[i][0]);
						// ֧������
						toXmlInfo[i][1] = String.valueOf( zhiDuiInfo[i][1]);
						returnStr = AssessUtil.getOneZhiduiAssess(String.valueOf(zhiDuiInfo[i][0]),String.valueOf(zhiDuiInfo[i][1]),startDate,endDate,type);
						if(returnStr != null) {
							// �¹ʼ���
							toXmlInfo[i][2] = returnStr[0]; 
							// ӵ�¼���
							toXmlInfo[i][3] = returnStr[1]; 
							// ʩ������
							toXmlInfo[i][4] = returnStr[2]; 
							// ��Ϣ�ļ�ʡ�����ü���
							toXmlInfo[i][5] = returnStr[3]; 
							// ��Ϣ�ļ����������ü���
							toXmlInfo[i][6] = returnStr[4]; 
							// ������Ϣ����ʡ������
							toXmlInfo[i][7] = returnStr[5]; 
							// ������Ϣ���ϲ��ֲ���
							toXmlInfo[i][8] = returnStr[6]; 
							// ӵ�±��ϲ��ü���
							toXmlInfo[i][9] = returnStr[7]; 
							// ��Ϣ�۷�
							toXmlInfo[i][10] = returnStr[8]; 
							// �ܵ÷�
							toXmlInfo[i][11] = returnStr[9]; 
						}
					}
				}
			} else {
				Object[][] daDuiInfo = AssessUtil.getAllDaDuiByZhiDuiId(zhiduiId);
				// �������е�֧��
				if(daDuiInfo != null) {
					toXmlInfo = new String [daDuiInfo.length][12]; 
					for( int i=0;i<daDuiInfo.length;i++) {
						// ���id
						toXmlInfo[i][0] = String.valueOf( daDuiInfo[i][0]);
						// �������
						toXmlInfo[i][1] = String.valueOf( daDuiInfo[i][1]);
						returnStr = AssessUtil.getOneDaduiAssess(String.valueOf(daDuiInfo[i][0]),String.valueOf(daDuiInfo[i][1]),startDate, endDate,type);
						if(returnStr != null) {
							// �¹ʼ���
							toXmlInfo[i][2] = returnStr[0]; 
							// ӵ�¼���
							toXmlInfo[i][3] = returnStr[1]; 
							// ʩ������
							toXmlInfo[i][4] = returnStr[2]; 
							// ��Ϣ�ļ�ʡ�����ü���
							toXmlInfo[i][5] = returnStr[3]; 
							// ��Ϣ�ļ����������ü���
							toXmlInfo[i][6] = returnStr[4]; 
							// ������Ϣ����ʡ������
							toXmlInfo[i][7] = returnStr[5]; 
							// ������Ϣ���ϲ��ֲ���
							toXmlInfo[i][8] = returnStr[6]; 
							// ӵ�±��ϲ��ü���
							toXmlInfo[i][9] = returnStr[7]; 
							// ��Ϣ�۷�
							toXmlInfo[i][10] = returnStr[8]; 
							// �ܵ÷�
							toXmlInfo[i][11] = returnStr[9]; 
						}
					}
				}
			}
			StringBuffer showXml = new StringBuffer(); 
			String showChart = "";
			String showTable = "";
			if(toXmlInfo != null) {
				sortItem++;
				Array.sort(toXmlInfo, sortItem, false);//sortItem == 10
				showChart = AssessUtil.getShowChartStr(toXmlInfo, zhiduiId, startDate, endDate);
				showTable = AssessUtil.getShowTableStr(toXmlInfo, zhiduiId, startDate, endDate, type);
			} else {
				showXml.append("û��ȡ��������Ϣ");
			}
			showXml.append(showChart);
			showXml.append("+");
			showXml.append(showTable);
			out.write(String.valueOf(showXml));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ȡ�ÿ�����Ϣ����");
		}
		out.close();
		return null;
	}
	
	/**
	 * ȡ�ÿ�����ϢExcel�ļ�<br/>
	 * ȡ�ÿ�����Ϣ
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doShowExcel(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		// ��������
		String zhiduiId = StringHelper.obj2str(request.getParameter("zhiduiId"),"");
		// ���ֿ�ʼ����
		String startDate = StringHelper.obj2str(request.getParameter("startDate"),"");
		// ���ֽ�������
		String endDate = StringHelper.obj2str(request.getParameter("endDate"),"");
		// ��������
		String type = StringHelper.obj2str(request.getParameter("type"),"");
		//������Ŀ
		int sortItem = StringHelper.obj2int(request.getParameter("sortItem"),9);
		PrintWriter out = response.getWriter();
		String [][] toXmlInfo = null; 
		try {
			String[] returnStr = null;
			String fileName = "������Ϣ";
			// ֧��idΪ�� ��������֧�ӵĿ��˳ɼ�
			if("".equals(zhiduiId)) {
				Object[][] zhiDuiInfo = AssessUtil.getZhiDuiIdName();
				fileName = "���н���֧�ӿ�����Ϣ";
				// �������е�֧��
				if(zhiDuiInfo != null) {
					int length = 11;
					if(type.equals("1")){
						length = 7;
					}else if(type.equals("2")){
						length = 6;
					}
					toXmlInfo = new String [zhiDuiInfo.length][length];  
					for( int i=0;i<zhiDuiInfo.length;i++) {
//						// ֧��id
//						toXmlInfo[i][0] = String.valueOf( zhiDuiInfo[i][0]);
						// ֧������
						toXmlInfo[i][0] = String.valueOf( zhiDuiInfo[i][1]);
						returnStr = AssessUtil.getOneZhiduiAssess(String.valueOf(zhiDuiInfo[i][0]),String.valueOf(zhiDuiInfo[i][1]),startDate, endDate,type);
						if(returnStr != null) {
							if(type.equals("")){
								// �¹ʼ���
								toXmlInfo[i][1] = returnStr[0]; 
								// ӵ�¼���
								toXmlInfo[i][2] = returnStr[1]; 
								// ʩ������
								toXmlInfo[i][3] = returnStr[2]; 
								// ��Ϣ�ļ�ʡ�����ü���
								toXmlInfo[i][4] = returnStr[3]; 
								// ��Ϣ�ļ����������ü���
								toXmlInfo[i][5] = returnStr[4]; 
								// ������Ϣ����ʡ������
								toXmlInfo[i][6] = returnStr[5]; 
								// ������Ϣ���ϲ��ֲ���
								toXmlInfo[i][7] = returnStr[6]; 
								// ӵ�±��ϲ��ü���
								toXmlInfo[i][8] = returnStr[7]; 
								// ��Ϣ�۷�
								toXmlInfo[i][9] = returnStr[8]; 
								// �ܵ÷�
								toXmlInfo[i][10] = returnStr[9]; 
							}else if(type.equals("1")){
								// �¹ʼ���
								toXmlInfo[i][1] = returnStr[0]; 
								// ӵ�¼���
								toXmlInfo[i][2] = returnStr[1]; 
								// ʩ������
								toXmlInfo[i][3] = returnStr[2]; 
								// ӵ�±��ϲ��ü���
								toXmlInfo[i][4] = returnStr[7]; 
								// ��Ϣ�۷�
								toXmlInfo[i][5] = returnStr[8]; 
								// �ܵ÷�
								toXmlInfo[i][6] = returnStr[9];
							}else if(type.equals("2")){
								// ��Ϣ�ļ�ʡ�����ü���
								toXmlInfo[i][1] = returnStr[3]; 
								// ��Ϣ�ļ����������ü���
								toXmlInfo[i][2] = returnStr[4]; 
								// ������Ϣ����ʡ������
								toXmlInfo[i][3] = returnStr[5]; 
								// ������Ϣ���ϲ��ֲ���
								toXmlInfo[i][4] = returnStr[6]; 
								// �ܵ÷�
								toXmlInfo[i][5] = returnStr[9];
							}
						}
					}
				}
			} else {
				Object[][] daDuiInfo = AssessUtil.getAllDaDuiByZhiDuiId(zhiduiId);
				// �������е�֧��
				if(daDuiInfo != null) {
					toXmlInfo = new String [daDuiInfo.length][11]; 
					fileName = daDuiInfo[0][1] + "����ӿ�����Ϣ";
					for( int i=0;i<daDuiInfo.length;i++) {
//						//��ӱ��
//						toXmlInfo[i][0] = String.valueOf( daDuiInfo[i][0]);
						// �������
						toXmlInfo[i][0] = String.valueOf( daDuiInfo[i][1]);
						returnStr = AssessUtil.getOneDaduiAssess(String.valueOf(daDuiInfo[i][0]),String.valueOf(daDuiInfo[i][1]),startDate, endDate, type);
						if(returnStr != null) {
							if(type.equals("")){
								// �¹ʼ���
								toXmlInfo[i][1] = returnStr[0]; 
								// ӵ�¼���
								toXmlInfo[i][2] = returnStr[1]; 
								// ʩ������
								toXmlInfo[i][3] = returnStr[2]; 
								// ��Ϣ�ļ�ʡ�����ü���
								toXmlInfo[i][4] = returnStr[3]; 
								// ��Ϣ�ļ����������ü���
								toXmlInfo[i][5] = returnStr[4]; 
								// ������Ϣ����ʡ������
								toXmlInfo[i][6] = returnStr[5]; 
								// ������Ϣ���ϲ��ֲ���
								toXmlInfo[i][7] = returnStr[6]; 
								// ӵ�±��ϲ��ü���
								toXmlInfo[i][8] = returnStr[7]; 
								// ��Ϣ�۷�
								toXmlInfo[i][9] = returnStr[8]; 
								// �ܵ÷�
								toXmlInfo[i][10] = returnStr[9]; 
							}else if(type.equals("1")){
								// �¹ʼ���
								toXmlInfo[i][1] = returnStr[0]; 
								// ӵ�¼���
								toXmlInfo[i][2] = returnStr[1]; 
								// ʩ������
								toXmlInfo[i][3] = returnStr[2]; 
								// ӵ�±��ϲ��ü���
								toXmlInfo[i][4] = returnStr[7]; 
								// ��Ϣ�۷�
								toXmlInfo[i][5] = returnStr[8]; 
								// �ܵ÷�
								toXmlInfo[i][6] = returnStr[9];
							}else if(type.equals("2")){
								// ��Ϣ�ļ�ʡ�����ü���
								toXmlInfo[i][1] = returnStr[3]; 
								// ��Ϣ�ļ����������ü���
								toXmlInfo[i][2] = returnStr[4]; 
								// ������Ϣ����ʡ������
								toXmlInfo[i][3] = returnStr[5]; 
								// ������Ϣ���ϲ��ֲ���
								toXmlInfo[i][4] = returnStr[6]; 
								// �ܵ÷�
								toXmlInfo[i][5] = returnStr[9];
							}
						}
					}
				}
			}
			if(toXmlInfo != null) {
				Array.sort(toXmlInfo, sortItem, false);
				CommonUtility comUtility = new CommonUtility();
				AssessUtil assessUtil = new AssessUtil();
				// ���˻�׼��ȡ��
				assessUtil.getStandardInfo();
				StringBuffer tabHeader = new StringBuffer("��������,");
//				tabHeader.append("��ͨ�¹ʣ�"+ assessUtil.Point_001024+"��/����,");
//				tabHeader.append("��ͨӵ�£�"+ assessUtil.Point_001002+"��/����,");
//				tabHeader.append("ռ��ʩ����"+ assessUtil.Point_001023+"��/����,");
//				tabHeader.append("ʡ�����ù�����Ϣ��"+ assessUtil.Point_001004+"��/ƪ��,");
//				tabHeader.append("���ֲ��ù�����Ϣ��"+ assessUtil.Point_001006+"��/ƪ��,");
//				tabHeader.append("ʡ�����õ�����Ϣ��"+ assessUtil.Point_001008+"��/ƪ��,");
//				tabHeader.append("���ֲ��õ�����Ϣ��"+ assessUtil.Point_001009+"��/ƪ��,");
//				tabHeader.append("ӵ�±��Ϻ˶ԣ�"+ assessUtil.Point_001001+"��/����,");
//				tabHeader.append("��Ϣ�۷�,");
//				tabHeader.append("�ܵ÷�");
				if(type.equals("")){
					tabHeader.append("��ͨ�¹ʣ�"+ assessUtil.Point_001024+"��/����,");
					tabHeader.append("��ͨӵ�£�"+ assessUtil.Point_001002+"��/����,");
					tabHeader.append("ռ��ʩ����"+ assessUtil.Point_001023+"��/����,");
					tabHeader.append("ʡ�����ù�����Ϣ��"+ assessUtil.Point_001004+"��/ƪ��,");
					tabHeader.append("���ֲ��ù�����Ϣ��"+ assessUtil.Point_001006+"��/ƪ��,");
					tabHeader.append("ʡ�����õ�����Ϣ��"+ assessUtil.Point_001008+"��/ƪ��,");
					tabHeader.append("���ֲ��õ�����Ϣ��"+ assessUtil.Point_001009+"��/ƪ��,");
					tabHeader.append("ӵ�±��Ϻ˶ԣ�"+ assessUtil.Point_001001+"��/����,");
					tabHeader.append("��Ϣ�۷�,");
					tabHeader.append("�ܵ÷�");
				}else if(type.equals("1")){
					tabHeader.append("��ͨ�¹ʣ�"+ assessUtil.Point_001024+"��/����,");
					tabHeader.append("��ͨӵ�£�"+ assessUtil.Point_001002+"��/����,");
					tabHeader.append("ռ��ʩ����"+ assessUtil.Point_001023+"��/����,");
					tabHeader.append("ӵ�±��Ϻ˶ԣ�"+ assessUtil.Point_001001+"��/����,");
					tabHeader.append("��Ϣ�۷�,");
					tabHeader.append("�ܵ÷�");
				}else if(type.equals("2")){
					tabHeader.append("ʡ�����ù�����Ϣ��"+ assessUtil.Point_001004+"��/ƪ��,");
					tabHeader.append("���ֲ��ù�����Ϣ��"+ assessUtil.Point_001006+"��/ƪ��,");
					tabHeader.append("ʡ�����õ�����Ϣ��"+ assessUtil.Point_001008+"��/ƪ��,");
					tabHeader.append("���ֲ��õ�����Ϣ��"+ assessUtil.Point_001009+"��/ƪ��,");
					tabHeader.append("�ܵ÷�");
				}
				
				String sheetName = "������Ϣ";
				String titleName = fileName+startDate+"��"+endDate+"���˱�";
				comUtility.wirteToExcel(response, String.valueOf(tabHeader), fileName, sheetName, toXmlInfo, titleName);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ȡ�ÿ�����Ϣ����");
		}
		out.close();
		return null;
	}

	/**
	 * ȡ�ü�����ϸ��Ϣ��html����<br/>
	 * ȡ�ü�����ϸ��Ϣ
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetCutInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// ���ֲ���id
		String showJgid = StringHelper.obj2str(request.getParameter("showJgid"),"");
		// ���ֿ�ʼ����
		String startDate = StringHelper.obj2str(request.getParameter("startDate"),"");
		// ���ֽ�������
		String endDate = StringHelper.obj2str(request.getParameter("endDate"),"");
		// ��������
		String showJgmc = StringHelper.obj2str(request.getParameter("showJgmc"),"");
		AssessUtil assessUtil = new AssessUtil();
		ScoreRecordDao scoreDao = new ScoreRecordDao();
		TrafficNewsFeedsDao trafficNewsFeedsDao = new TrafficNewsFeedsDao();
		Integer gqCount = 0;
		Integer wcycount = 0;
		Integer accCount = 0;
		Integer crowdcount = 0;
		Integer buildCount = 0;
		int[][] kfcounts;
		try {
			// ���˻�׼��ȡ��
			assessUtil.getStandardInfo();
			int deptLevel = assessUtil.getDeptLevel(showJgid, showJgmc);
			// ֧�ӵ����
			boolean isSelf;
			if(deptLevel == 1) {
				isSelf = showJgid.substring(2,4).equals("00");
				
//				// �¹ʼ��ּ���
//				accCount = assessUtil.getZhiduiCutCnt("1","1", showJgid, startDate, endDate);
//				accCount = assessUtil.getZhiduiCutCnt("1","2", showJgid, startDate, endDate);
//				// ӵ�¼��ּ���
//				crowdcount = assessUtil.getZhiduiCutCnt("2","1", showJgid, startDate, endDate);
//				crowdcount = assessUtil.getZhiduiCutCnt("2","2", showJgid, startDate, endDate);
//				crowdcount = assessUtil.getZhiduiCutCnt("2","3", showJgid, startDate, endDate);
//				// ռ�����ּ���
//				buildCount = assessUtil.getZhiduiCutCnt("3","1", showJgid, startDate, endDate);
//				buildCount = assessUtil.getZhiduiCutCnt("3","2", showJgid, startDate, endDate);
			} else {
				isSelf = showJgid.substring(4,6).equals("00");
				// �¹ʼ��ּ���
//				accCount = assessUtil.getDaduiCutCnt("1","1", showJgid, startDate, endDate);
//				accCount = assessUtil.getDaduiCutCnt("1", "2",showJgid, startDate, endDate);
//				// ӵ�¼��ּ���
//				crowdcount = assessUtil.getDaduiCutCnt("2","1", showJgid, startDate, endDate);
//				crowdcount = assessUtil.getDaduiCutCnt("2","2", showJgid, startDate, endDate);
//				crowdcount = assessUtil.getDaduiCutCnt("2","3", showJgid, startDate, endDate);
//				// ռ�����ּ���
//				buildCount = assessUtil.getDaduiCutCnt("3", "1", showJgid, startDate, endDate);
//				buildCount = assessUtil.getDaduiCutCnt("3", "2", showJgid, startDate, endDate);
			}
			// ȡ�ñ��ϲ��ü��� �� �����ü���
			HashMap<String,Integer> countMap = trafficNewsFeedsDao.getFeedbackNum(showJgid, showJgmc, startDate, endDate);
			if(countMap != null) {
				wcycount = countMap.get("wcycount");
				gqCount = countMap.get("gqcount");
				if(gqCount == null) {
					gqCount = 0;
				}
				if(wcycount == null) {
					wcycount = 0;
				}
			}
			Object[][] kfdata = scoreDao.getItemCounts(showJgid, startDate, endDate, isSelf);
			kfcounts = scoreDao.changeItemCounts(kfdata);
			accCount = kfcounts[0][0]+kfcounts[0][1];
			crowdcount = kfcounts[1][0]+kfcounts[1][1]+kfcounts[1][2];
			buildCount = kfcounts[2][0]+kfcounts[2][1];
			gqCount = kfcounts[3][0];
			wcycount = kfcounts[3][1];
			String [] toHtmlStr = new String [8];  
			toHtmlStr[0] = showJgid;
			toHtmlStr[1] = showJgmc;
			toHtmlStr[2] = String.valueOf(accCount);
			toHtmlStr[3] = String.valueOf(crowdcount);
			toHtmlStr[4] = String.valueOf(buildCount);
			toHtmlStr[5] = String.valueOf(gqCount);
			toHtmlStr[6] = String.valueOf(wcycount);
			float zongCut = (accCount+crowdcount+buildCount)*assessUtil.Point_001003 + gqCount*assessUtil.Point_001005;
			toHtmlStr[7] = String.valueOf(zongCut);
			out.write(assessUtil.getShowCutStr(toHtmlStr, startDate, endDate, deptLevel));
			System.out.println(assessUtil.getShowCutStr(toHtmlStr, startDate, endDate, deptLevel));
		} catch (Exception e) {
			out.write("û��ȡ��������Ϣs");
			e.printStackTrace();
			logger.error("ȡ�ÿ��˼�����Ϣ����" );
			out.close();
			return null;
		}
		out.close();
		return null;
	}
	
	/**
	 * ����Ʒ���Ϣ¼��
	 */
	public ActionForward doScoreRecordEdit(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String jgid = request.getParameter("jgid");
		String jgmc = request.getParameter("jgmc");
		String date = request.getParameter("date");
		String aid = request.getParameter("aid");
		String items = request.getParameter("items");
		String reason = request.getParameter("reason");
		String reasons = request.getParameter("reasons");
		
		if(jgid == null || jgmc == null || date == null || items == null || reasons == null || reason == null){
			logger.error("�Ʒ���Ϣ¼��->ҳ���������ȱʧ��" );
			return null;
		}
		
		boolean isSuccess = dao.add(jgid, jgmc, date, aid, items, reasons, reason);
		
		String responseStr = "";
		if(isSuccess){
			responseStr = "success";
		}else{
			responseStr = "failed";
		}
		out.write(responseStr);
		return null;
	}

	
	/**
	 * ����Ʒ���Ϣ¼��
	 */
	public ActionForward doScoreRecordDelete(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String jgid = request.getParameter("jgid");
		String date = request.getParameter("date");
		
		if(jgid == null || date == null){
			logger.error("�Ʒ���Ϣ¼��->ҳ���������ȱʧ��" );
			return null;
		}
		
		boolean isSuccess = dao.delete(jgid, date);
		
		out.write(isSuccess+"");
		return null;
	}

}
