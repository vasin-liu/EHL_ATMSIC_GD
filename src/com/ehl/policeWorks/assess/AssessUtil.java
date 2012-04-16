package com.ehl.policeWorks.assess;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.ehl.dispatch.cdispatch.dao.TrafficNewsFeedsDao;
import com.ehl.policeWorks.assess.dao.ScoreRecordDao;
import com.ehl.policeWorks.newsFiles.core.NewsFileCore;
import com.ehl.policeWorks.newsFiles.dao.NewsFileDao;
import com.ehl.util.Array;

/**
 * ������Ϣ���������<br/>
 * @author 
 *
 */
public class AssessUtil {

	private static Logger logger = Logger.getLogger(AssessUtil.class);
	// �¹ʵ��¼����
	public final static String EVENTTYPE_001024 = "001024"; 
	// ӵ�µ��¼����
	public final static String EVENTTYPE_001002 = "001002";
	// ʩ�����¼����
	public final static String EVENTTYPE_001023 = "001023";
	// ӵ�±��ϲ��õ��¼����
	public final static String EVENTTYPE_001001 = "001001";
	// ©�������۷ֵ��¼����
	public final static String EVENTTYPE_001003 = "001003";
	// ��Ϣ�ļ����õ��¼����
	public final static String EVENTTYPE_001004 = "001004";
	// ��Ϣ�ļ������������ֲܾ��õ��¼����
	public final static String EVENTTYPE_001006 = "001006";
	// ӵ�±��ϳ�ʱδ��ʵ���¼����
	public final static String EVENTTYPE_001005 = "001005";
	// ������Ϣ���ϵ��¼����
	public final static String EVENTTYPE_001008 = "001008";
	// ������Ϣ���ϱ����������ֲܾ��õ��¼����
	public final static String EVENTTYPE_001009 = "001009";
	// ӵ����Ϣ���ϲ���
	public float Point_001001 = 0;
	// ��ͨӵ��
	public float Point_001002 = 0;
	// ©�������۷�
	public float Point_001003 = 0;
	// ��Ϣ�ļ�
	public float Point_001004 = 0;
	// ӵ����Ϣ���ϳ�ʱδ��
	public float Point_001005 = 0;
	// ӵ����Ϣ����δ����
	public float Point_001007 = 0;
	// ��Ϣ�ļ��������������ֲܾ��ã�
	public float Point_001006 = 0;
	// ʩ��ռ��
	public float Point_001023 = 0;
	// ��ͨ�¹�
	public float Point_001024 = 0;
	// ������Ϣ���ϣ�ʡ�ֲ��ã�
	public float Point_001008 = 0;
	// ������Ϣ���ϣ����ֲ��ã�
	public float Point_001009 = 0;
	
	//Modify by Xiayx 2011-9-6
	public static int type_pt;
	public static int type_dy;
	public static int sbtype_st;
	public static int sbtype_bj;
	
	static{
		type_pt = Array.getIndex(NewsFileDao.types, "1");
		type_dy = Array.getIndex(NewsFileDao.types, "2");
		sbtype_st = Array.getIndex(NewsFileDao.sbtypes, "1");
		sbtype_bj = Array.getIndex(NewsFileDao.sbtypes, "2");
	}
	//Modification finished
	
	
	

	/**
	 * ȡ�����е�֧�ӵ�id������<br/>
	 * ȡ�����е�֧�ӵ�id������
	 * @return
	 */
	public static Object[][] getZhiDuiIdName () {
		Object[][] result = null;
		String sql = "";
		try {
			// ȡ������֧�ӵ�id������
			//Modified by Liuwx 2011-8-12
			sql = "select jgid,jgmc from t_sys_department where substr(jgid,5,2)='00' and " + 
			" jgid not in ('440000000000','446000000000','446100000000','446200000000','446300000000')  " + 
			" order by jgid";
			//��Ϣ���������ͼƷ�ͳ�Ƽ����ܶ�
//			sql = "select jgid,jgmc from t_sys_department where substr(jgid,5,2)='00' and " + 
//			" jgid not in ('446000000000','446100000000','446200000000','446300000000')  " + 
//			" order by jgid";
			//Modification finished
			
			result = DBHandler.getMultiResult(String.valueOf(sql));
		} catch (Exception e) {
			logger.error("ȡ������֧����Ϣ����sql�� " + sql);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ȡ��ָ��֧�ӵ����д�ӵ�id������<br/>
	 * ȡ��ָ��֧�ӵ����д�ӵ�id������
	 * @return
	 */
	public static Object[][] getAllDaDuiByZhiDuiId (String zhiduiId) {
		Object[][] result = null;
		String sql = "";
		try {
			// ȡ������֧�ӵ�id������
			//Modified by Liuwx 2011-8-12
//			sql = "select jgid,jgmc from t_sys_department where substr(jgid,1,4)='"+ zhiduiId.substring(0,4) + "' and " + 
//			" jgid not in ('440000000000','446000000000','446100000000','446200000000','446300000000')  " + 
//			" order by jgid";
			//��Ϣ���������ͼƷ�ͳ�Ƽ����ܶ�
			sql = "select jgid,jgmc from t_sys_department where substr(jgid,1,4)='"+ zhiduiId.substring(0,4) + "' and " + 
			" jgid not in ('446000000000','446100000000','446200000000','446300000000')  " + 
			" order by jgid";
			//Modification finished
			
			result = DBHandler.getMultiResult(String.valueOf(sql));
		} catch (Exception e) {
			logger.error("ȡ��ָ��֧�ӵ����д����Ϣ����sql�� " + sql);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * ȡ��ָ��֧�ӵĿ��˷�ֵ<br/>
	 * @param zhiduiId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String[] getOneZhiduiAssess (String zhiduiId,String DeptName, String startDate,String endDate, String type) {
		String[] returnStr = new String[10]; 
		AssessUtil assessUtil = new AssessUtil();
		TrafficNewsFeedsDao trafficNewsFeedsDao = new TrafficNewsFeedsDao();
		// ���˻�׼��ȡ��
		assessUtil.getStandardInfo();
		//�Ʒֱ�ͬ��Ϣ���͡���ͬ�۷�ԭ�����Ϣ����
		boolean isSelf = zhiduiId.substring(2,4).equals("00");
		ScoreRecordDao scoreDao = new ScoreRecordDao();
		Object[][] kfdata = scoreDao.getItemCounts(zhiduiId, startDate,endDate, isSelf);
		int[][] kfcounts =  scoreDao.changeItemCounts(kfdata);
		// �¹ʼ�����ȡ��
		Integer accCount = assessUtil.getZhiduiInfoCnt(EVENTTYPE_001024, zhiduiId, startDate, endDate);
		accCount-=kfcounts[0][1];
		returnStr[0] = String.valueOf(accCount);
		// ӵ�¼�����ȡ��
		Integer crowdCount = assessUtil.getZhiduiInfoCnt(EVENTTYPE_001002, zhiduiId, startDate, endDate);
		crowdCount -= kfcounts[1][1];
		returnStr[1] = String.valueOf(crowdCount);
		// ʩ��������ȡ��
		Integer buildCount = assessUtil.getZhiduiInfoCnt(EVENTTYPE_001023, zhiduiId, startDate, endDate);
		buildCount -= kfcounts[2][1];
		returnStr[2] = String.valueOf(buildCount);
		//��Ϣ�ļ����ü�����ȡ��
		int[][] counts = NewsFileCore.getCounts(zhiduiId, startDate, endDate);
		// ��Ϣ�ļ�ʡ�����ü�����ȡ��
		Integer ptst = counts[type_pt][sbtype_st];
		returnStr[3] = String.valueOf(ptst);
		// ��Ϣ�ļ����ֲ��ü�����ȡ��
		Integer ptbj = counts[type_pt][sbtype_bj];
		returnStr[4] = String.valueOf(ptbj);
		// ������Ϣ����ʡ����ȡ��
		Integer dyst = counts[type_dy][sbtype_st];
		returnStr[5] = String.valueOf(dyst);
		// ������Ϣ���ϲ��ֵ�ȡ��
		Integer dybj = counts[type_dy][sbtype_bj];
		returnStr[6] = String.valueOf(dybj);
		
		// ȡ�ñ��ϲ��ü���
		HashMap<String,Integer> countMap = trafficNewsFeedsDao.getFeedbackNum(zhiduiId, DeptName, startDate, endDate);
		Integer cycount = 0;
		Integer gqcount = 0;
		Integer wcycount = 0;
		if(countMap != null) {
			cycount = countMap.get("cycount");
			gqcount = countMap.get("gqcount");
			wcycount = countMap.get("wcycount");
			if(cycount == null) {
				cycount = 0;
			}
			if(gqcount == null) {
				gqcount = 0;
			}
			if(wcycount == null) {
				wcycount = 0;
			}
		}
		
		// ���Ӽ�����Ϣ����ȡ��
//		Integer cutCount = assessUtil.getScoreRecordCnt("0", zhiduiId, startDate, endDate);
//		// ���Ӽӷ���Ϣ����ȡ��
//		Integer addCount = assessUtil.getScoreRecordCnt("1", zhiduiId, startDate, endDate); 
//		// ��Ϣ�ļ����������ü���
//		returnStr[4] = String.valueOf(addCount);
		// ӵ�±��ϲ��ü���
		returnStr[7] = String.valueOf(cycount);
		// �۷ֵļ���
		int cutCount = kfcounts[0][0] + kfcounts[0][1] + kfcounts[1][0] + kfcounts[1][1]+ kfcounts[1][2]+ kfcounts[2][0] + kfcounts[2][1];
		gqcount = kfcounts[3][0];
		wcycount = kfcounts[3][1];
		float addPoint = cutCount * assessUtil.Point_001003 + gqcount * assessUtil.Point_001005 + wcycount * assessUtil.Point_001007;
		// ��Ϣ�۷�
		returnStr[8] = String.valueOf(assessUtil.cutPointFloat(addPoint));
		//�������
		float alarmPoint = accCount * assessUtil.Point_001024 
			 + crowdCount * assessUtil.Point_001002 
			 + cycount * assessUtil.Point_001001
			 + buildCount * assessUtil.Point_001023
			 + addPoint;
		//��Ϣ�ļ�����
		float newsFilePoint = ptst * assessUtil.Point_001004 
		 + ptbj * assessUtil.Point_001006
		 + dyst * assessUtil.Point_001008
		 + dybj * assessUtil.Point_001009;
		//�ܷ���
		float totalPoint = alarmPoint + newsFilePoint;
		// �ܵ÷�
		//Modify by Xiayx 2011-10-10
		//��ӿ�������->���˷�ֵ�����ͼ���
		float zongfen = totalPoint;
		if(type.equals("1")){
			zongfen = alarmPoint;
		}else if(type.equals("2")){
			zongfen = newsFilePoint;
		}
		//Modification finished
		zongfen = assessUtil.cutPointFloat(zongfen);
		returnStr[9] = String.valueOf(zongfen);
		return returnStr;
	}
	
	/**
	 * ȡ��ָ����ӵĿ��˷�ֵ<br/>
	 * @param zhiduiId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String[] getOneDaduiAssess (String daDuiId,String DeptName,String startDate,String endDate,String type) {
		String[] returnStr = new String[10]; 
		AssessUtil assessUtil = new AssessUtil();
		TrafficNewsFeedsDao trafficNewsFeedsDao = new TrafficNewsFeedsDao();
		// ���˻�׼��ȡ��
		assessUtil.getStandardInfo();
		//�Ʒֱ�ͬ��Ϣ���͡���ͬ�۷�ԭ�����Ϣ����
		boolean isSelf = true;//daDuiId.substring(4,6).equals("00");
		ScoreRecordDao scoreDao = new ScoreRecordDao();
		Object[][] kfdata = scoreDao.getItemCounts(daDuiId, startDate,endDate, isSelf);
		int[][] kfcounts =  scoreDao.changeItemCounts(kfdata);
		// �¹ʼ�����ȡ��
		Integer accCount = assessUtil.getDaduiInfoCnt(EVENTTYPE_001024, daDuiId, startDate, endDate);
		returnStr[0] = String.valueOf(accCount-=kfcounts[0][1]);
		// ӵ�¼�����ȡ��
		Integer crowdCount = assessUtil.getDaduiInfoCnt(EVENTTYPE_001002, daDuiId, startDate, endDate);
		returnStr[1] = String.valueOf(crowdCount-=kfcounts[1][1]);
		// ʩ��������ȡ��
		Integer buildCount = assessUtil.getDaduiInfoCnt(EVENTTYPE_001023, daDuiId, startDate, endDate);
		returnStr[2] = String.valueOf(buildCount-=kfcounts[2][1]);
		// ȡ�ñ��ϲ��ü��� �� δ���ü���
		HashMap<String,Integer> countMap = trafficNewsFeedsDao.getFeedbackNum(daDuiId, DeptName, startDate, endDate);
		Integer cycount = 0;
		Integer gqcount = 0;
		Integer wcycount = 0;
		if(countMap != null) {
			cycount = countMap.get("cycount");
			gqcount = countMap.get("gqcount");
			wcycount = countMap.get("wcycount");
			if(cycount == null) {
				cycount = 0;
			}
			if(gqcount == null) {
				gqcount = 0;
			}
			if(wcycount == null) {
				wcycount = 0;
			}
		}
		//��Ϣ�ļ����ü�����ȡ��
		int[][] counts = NewsFileCore.getCounts(daDuiId, startDate, endDate);
		// ��Ϣ�ļ�ʡ�����ü�����ȡ��
		Integer ptst = counts[type_pt][sbtype_st];
		returnStr[3] = String.valueOf(ptst);
		// ��Ϣ�ļ����ֲ��ü�����ȡ��
		Integer ptbj = counts[type_pt][sbtype_bj];
		returnStr[4] = String.valueOf(ptbj);
		// ������Ϣ����ʡ����ȡ��
		Integer dyst = counts[type_dy][sbtype_st];
		returnStr[5] = String.valueOf(dyst);
		// ������Ϣ���ϲ��ֵ�ȡ��
		Integer dybj = counts[type_dy][sbtype_bj];
		returnStr[6] = String.valueOf(dybj);
//		Integer addCount = assessUtil.getDaduiScoreRecordCnt("1", daDuiId, startDate, endDate);
//		// ��Ϣ�ļ����������üӷּ���
		// ���Ӽ�����Ϣ����ȡ��
//		Integer cutCount = assessUtil.getDaduiScoreRecordCnt("0", daDuiId, startDate, endDate);
		// ӵ�±��ϲ��ü���
		returnStr[7] = String.valueOf(cycount);
		// ��Ϣ�۷ֵļ���
		// ��Ϣ�۷�
		int cutCount = kfcounts[0][0] + kfcounts[0][1] + kfcounts[1][0] + kfcounts[1][1]+ kfcounts[1][2]+ kfcounts[2][0] + kfcounts[2][1];
		gqcount = kfcounts[3][0];
		wcycount = kfcounts[3][1];
		float addPoint = cutCount * assessUtil.Point_001003 + gqcount * assessUtil.Point_001005 + wcycount * assessUtil.Point_001007;
		returnStr[8] = String.valueOf(assessUtil.cutPointFloat(addPoint));
		//�������
		float alarmPoint = accCount * assessUtil.Point_001024 
			 + crowdCount * assessUtil.Point_001002 
			 + cycount * assessUtil.Point_001001
			 + buildCount * assessUtil.Point_001023
			 + addPoint;
		//��Ϣ�ļ�����
		float newsFilePoint = ptst * assessUtil.Point_001004 
		 + ptbj * assessUtil.Point_001006
		 + dyst * assessUtil.Point_001008
		 + dybj * assessUtil.Point_001009;
		//�ܷ���
		float totalPoint = alarmPoint + newsFilePoint;
		// �ܵ÷�
		//Modify by Xiayx 2011-10-10
		//��ӿ�������->���˷�ֵ�����ͼ���
		float zongfen = totalPoint;
		if(type.equals("1")){
			zongfen = alarmPoint;
		}else if(type.equals("2")){
			zongfen = newsFilePoint;
		}
		//Modification finished
		zongfen = assessUtil.cutPointFloat(zongfen);
		// �ܵ÷�
		returnStr[9] = String.valueOf(assessUtil.cutPointFloat(zongfen));
		return returnStr;
	}
	
	/**
	 * ȡ��ָ��֧�ӵĴ�������Ϣ����<br/>
	 * ȡ��ָ��֧�ӵĴ�������Ϣ����
	 * @param eventType
	 * @param zhiduiId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Integer getZhiduiInfoCnt (String eventType,String zhiduiId,String startDate,String endDate) {
		StringBuffer sqlBuffer = new StringBuffer(); 
		Object count = null;
		try {
			sqlBuffer.append(" select count(1) from");
			if(eventType.equals(EVENTTYPE_001024)){
				sqlBuffer.append(" t_attemper_alarm_zd");
			}else{
				sqlBuffer.append(" t_attemper_alarm");
			}
			sqlBuffer.append(" where eventtype = '");
			sqlBuffer.append(eventType);
			sqlBuffer.append("' and substr(REPORTUNIT, 1, 4) = substr('");
			sqlBuffer.append(zhiduiId);
			sqlBuffer.append("',1,4)");
			sqlBuffer.append(" and to_char(ALARMTIME,'yyyy-mm-dd') >= '");
			sqlBuffer.append(startDate);
			sqlBuffer.append("' ");
			sqlBuffer.append(" and to_char(ALARMTIME,'yyyy-mm-dd') <= '");
			sqlBuffer.append(endDate);
			sqlBuffer.append("' ");
			if(eventType.equals(EVENTTYPE_001002)){
				//�����ӵ�£����ݵ�·�����ų�����ӵ����Ϣ
				sqlBuffer.append(" and (select roadlevel from t_oa_dictdlfx where roadid = dlmc) is not null");
			}else if(eventType.equals(EVENTTYPE_001024)){
				//������¹ʣ�ֻͳ���ܶӴ�������¹���Ϣ
				sqlBuffer.append(" and eventstate in ('004031','004036','004037','004035') ");
			}
			count = DBHandler.getSingleResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			logger.error("ȡ��֧�ӿ�����Ϣ��������sql�� " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}
	
	/**
	 * ȡ��ָ����ӵĴ�������Ϣ����<br/>
	 * ȡ��ָ����ӵĴ�������Ϣ����
	 * @param eventType
	 * @param zhiduiId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Integer getDaduiInfoCnt (String eventType,String daDuiId,String startDate,String endDate) {
		StringBuffer sqlBuffer = new StringBuffer(); 
		Object count = null;
		try {
			sqlBuffer.append(" select count(1) from");
			if(eventType.equals(EVENTTYPE_001024)){
				sqlBuffer.append(" t_attemper_alarm_zd");
			}else{
				sqlBuffer.append(" t_attemper_alarm");
			}
			sqlBuffer.append(" where eventtype = '");
			sqlBuffer.append(eventType);
			sqlBuffer.append("' and substr(REPORTUNIT,1,6) = '");
			sqlBuffer.append(daDuiId.substring(0,6));
			sqlBuffer.append("'");
//			sqlBuffer.append("' and " +Constant.getSiftSelfChildsSql("REPORTUNIT", daDuiId));
			sqlBuffer.append(" and to_char(ALARMTIME,'yyyy-mm-dd') >= '");
			sqlBuffer.append(startDate);
			sqlBuffer.append("' ");
			sqlBuffer.append(" and to_char(ALARMTIME,'yyyy-mm-dd') <= '");
			sqlBuffer.append(endDate);
			sqlBuffer.append("' ");
			if(eventType.equals(EVENTTYPE_001002)){
				//�����ӵ�£����ݵ�·�����ų�����ӵ����Ϣ
				sqlBuffer.append(" and (select roadlevel from t_oa_dictdlfx where roadid = dlmc) is not null");
			}else if(eventType.equals(EVENTTYPE_001024)){
				//������¹ʣ�ֻͳ���ܶӴ�������¹���Ϣ
				sqlBuffer.append(" and eventstate in ('004031','004036','004037','004035') ");
			}
			count = DBHandler.getSingleResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			logger.error("ȡ�ô�ӿ�����Ϣ��������sql�� " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
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
	public void getStandardInfo() {
		StringBuffer sql = new StringBuffer();
		Object[] result = null;
		try {
			sql.append("select distinct (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001001') ,");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001002') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001003') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001004') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001005') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001006') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001023') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001024') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001007') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001008') , ");
			sql.append(" (select ASSESS_PRJECT_VALUE from T_OA_ASSESS_CODE  where ASSESS_STANDARD_ID = '001009')  ");
			sql.append(" from T_OA_ASSESS_CODE  ");
			result = DBHandler.getLineResult(sql.toString());
			System.out.println("getAssessStandardInfo.sql==>"+sql);
			if( result != null) {
				this.Point_001001 = Float.valueOf(String.valueOf(result[0]));
				this.Point_001002 = Float.valueOf(String.valueOf(result[1]));
				this.Point_001003 = Float.valueOf(String.valueOf(result[2]));
				this.Point_001004 = Float.valueOf(String.valueOf(result[3]));
				this.Point_001005 = Float.valueOf(String.valueOf(result[4]));
				this.Point_001006 = Float.valueOf(String.valueOf(result[5]));
				this.Point_001023 = Float.valueOf(String.valueOf(result[6]));
				this.Point_001024 = Float.valueOf(String.valueOf(result[7]));
				this.Point_001007 = Float.valueOf(String.valueOf(result[8]));
				this.Point_001008 = Float.valueOf(String.valueOf(result[9]));
				this.Point_001009 = Float.valueOf(String.valueOf(result[10]));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ȡ�ÿ��˱�׼��Ϣ���� sql:" + sql);
		}
	}

	/**
	 * ȡ�û�����ʾ���ַ���<br/>
	 * ȡ�û�����ʾ���ַ���
	 * @param toXmlInfoStr
	 * @return ������ʾ���ַ���
	 */
	public static String getShowChartStr (String [][] toXmlInfoStr,String zhiDuiId,String startDate,String endDate) {
		StringBuffer showXml = new StringBuffer(); 
		// ��ʾ����֧�ӿ��˳ɼ�
		if("".equals(zhiDuiId)) {
			//Modified by Liuwx 2011-8-12
			showXml.append("<chart caption='���н���֧��"+startDate+"��"+endDate+"���˷���ͼ' shownames='1' showvalues='0' decimals='0' baseFont='����' baseFontSize='15' formatNumberScale='0'>");
			//Modification finished			
			StringBuffer strName = new StringBuffer(); 
			StringBuffer strValue = new StringBuffer(); 
			for(int i=0; i<toXmlInfoStr.length; i++) {
				strName.append("<category label='");
				//Modified by Liuwx 2011-8-12
				strName.append(toXmlInfoStr[i][1].replaceAll("�й����ֽ�ͨ����֧��", "").replaceAll("�㶫ʡ��������ͨ�����", "�ܶ�"));
				//Modification finished
				strName.append("' /> ");
				strValue.append("<set value='");
				strValue.append(toXmlInfoStr[i][11]);
				strValue.append("' /> ");
				
			}
			showXml.append(" <categories> ");
			showXml.append(strName);
			showXml.append(" </categories> ");
			showXml.append(" <dataset seriesName='���˷�ֵ����λ���֣�' color='8BBA00' showValues='0'> ");
			showXml.append(strValue);
			showXml.append(" </dataset> " );
			showXml.append(" </chart> ");
		} else {
			showXml.append("<chart caption='" + toXmlInfoStr[0][1]+startDate+"��"+endDate+"���˷���ͼ' shownames='1' showvalues='0' decimals='0' baseFont='����' baseFontSize='15' formatNumberScale='0'>");
			StringBuffer strName = new StringBuffer(); 
			StringBuffer strValue = new StringBuffer(); 
			for(int i=1; i<toXmlInfoStr.length; i++) {
				strName.append("<category label='");
				strName.append(toXmlInfoStr[i][1]);
				strName.append("' /> ");
				strValue.append("<set value='");
				strValue.append(toXmlInfoStr[i][11]);
				strValue.append("' /> ");
				
			}
			showXml.append(" <categories> ");
			showXml.append(strName);
			showXml.append(" </categories> ");
			showXml.append(" <dataset seriesName='���˷�ֵ����λ���֣�' color='8BBA00' showValues='0'> ");
			showXml.append(strValue);
			showXml.append(" </dataset> " );
			showXml.append(" </chart> ");
		}
		return String.valueOf(showXml);
	}

	/**
	 * ȡ�û�����ʾ���ַ���<br/>
	 * ȡ�û�����ʾ���ַ���
	 * @param toXmlInfoStr
	 * @return ������ʾ���ַ���
	 */
	public static String getShowTableStr (String [][] toXmlInfoStr,String zhiDuiId,String startDate,String endDate, String type) {
		StringBuffer showXml = new StringBuffer(); 
		StringBuffer titleStr = new StringBuffer(); 
		AssessUtil assessUtil = new AssessUtil();
		// ���˻�׼��ȡ��
		assessUtil.getStandardInfo();
		// ��ʾ����֧�ӿ��˳ɼ�
		if("".equals(zhiDuiId)) {
			//Modified by Liuwx 2011-8-12
			titleStr.append("���н���֧����Ϣ������"+startDate+"��"+endDate+"��");
			//Modification finished
		} else {
			titleStr.append(toXmlInfoStr[0][1]+startDate+"��"+endDate+"���˱�");
		}
		showXml.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\"");
		showXml.append("onmouseover=\"changeto()\" onmouseout=\"changeback()\"");
		showXml.append("cellspacing=\"0\" class=\"table\" style=\"font-size:12px\">");
		showXml.append("<tr class='titleTopBack'>");
		showXml.append("<td align='center' height='20' class='td_r_b' colspan='11'>");
		showXml.append(titleStr);
		showXml.append("</td> </tr>");
		showXml.append("<tr> <td align='center' height='20' class='td_r_b'> ��������\\��Ŀ </td>");
		//Modify by Xiayx 2011-10-10
		//��ӿ�������->���ݿ���������ʾ��ͷ
//		showXml.append("<td align='center' height='20' class='td_r_b'> ��ͨ�¹�<br/>��"+ assessUtil.Point_001024+"��/����" + " </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> ��ͨӵ��<br/>��"+ assessUtil.Point_001002+"��/����" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> ռ��ʩ��<br/>��"+ assessUtil.Point_001023+"��/����" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> ʡ������<br/>��ͨ��Ϣ<br/>��"+ assessUtil.Point_001004+"��/ƪ��" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> ���ֲ���<br/>��ͨ��Ϣ<br/>��"+ assessUtil.Point_001006+"��/ƪ��" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> ʡ������<br/>������Ϣ<br/>��"+ assessUtil.Point_001008+"��/ƪ��" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> ���ֲ���<br/>������Ϣ<br/>��"+ assessUtil.Point_001009+"��/ƪ��" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> ӵ�±���<br/>�� &nbsp; &nbsp;ʵ<br/>��"+ assessUtil.Point_001001+"��/����" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> ��Ϣ�۷� </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> �ܵ÷� </td> </tr>");
		if(type.equals("")){
			showXml.append("<td align='center' height='20' class='td_r_b'> ��ͨ�¹�<br/>��"+ assessUtil.Point_001024+"��/����" + " </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ��ͨӵ��<br/>��"+ assessUtil.Point_001002+"��/����" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ռ��ʩ��<br/>��"+ assessUtil.Point_001023+"��/����" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ʡ������<br/>������Ϣ<br/>��"+ assessUtil.Point_001004+"��/ƪ��" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ���ֲ���<br/>������Ϣ<br/>��"+ assessUtil.Point_001006+"��/ƪ��" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ʡ������<br/>������Ϣ<br/>��"+ assessUtil.Point_001008+"��/ƪ��" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ���ֲ���<br/>������Ϣ<br/>��"+ assessUtil.Point_001009+"��/ƪ��" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ӵ�±���<br/>�� &nbsp; &nbsp;ʵ<br/>��"+ assessUtil.Point_001001+"��/����" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ��Ϣ�۷� </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> �ܵ÷� </td> </tr>");
		}else if(type.equals("1")){
			showXml.append("<td align='center' height='20' class='td_r_b'> ��ͨ�¹�<br/>��"+ assessUtil.Point_001024+"��/����" + " </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ��ͨӵ��<br/>��"+ assessUtil.Point_001002+"��/����" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ռ��ʩ��<br/>��"+ assessUtil.Point_001023+"��/����" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ӵ�±���<br/>�� &nbsp; &nbsp;ʵ<br/>��"+ assessUtil.Point_001001+"��/����" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ��Ϣ�۷� </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> �ܵ÷� </td> </tr>");
		}else if(type.equals("2")){
			showXml.append("<td align='center' height='20' class='td_r_b'> ʡ������<br/>������Ϣ<br/>��"+ assessUtil.Point_001004+"��/ƪ��" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ���ֲ���<br/>������Ϣ<br/>��"+ assessUtil.Point_001006+"��/ƪ��" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ʡ������<br/>������Ϣ<br/>��"+ assessUtil.Point_001008+"��/ƪ��" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> ���ֲ���<br/>������Ϣ<br/>��"+ assessUtil.Point_001009+"��/ƪ��" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> �ܵ÷� </td> </tr>");
		}
		//Modification finished
		StringBuffer strValue = new StringBuffer(); 
		
		//������ Modified by Leisx 2011-10-17
		int count1 = 0;
		int count2 = 0;
		int count3 = 0;
		int count4 = 0;
		int count5 = 0;
		int count6 = 0;
		int count7 = 0;
		int count8 = 0;
		
		for(int i=0; i<toXmlInfoStr.length; i++) {
			//Modified by Liuwx 2011-8-14
			//ȡ���ܶӵĵ���¼�
		
			if("".equals(zhiDuiId) && !("00".equals(toXmlInfoStr[i][0].substring(2,4)))) {
			//Modification finished
				strValue.append("<tr> <td align='center' height='20' class='td_r_b'> <a href=\"#\" onclick=\"doAssessInfo('" + toXmlInfoStr[i][0] + "');\" >"+toXmlInfoStr[i][1].replaceAll("�й����ֽ�ͨ����", "")+" </a></td>");
			} else {
				strValue.append("<tr> <td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][1].replaceAll("�����ֽ�ͨ����", "����").replace("��������ͨ�����", "�����ܶ�")+" </td>");
			}
			
			//Modify by Xiayx 2011-10-10
			//��ӿ�������->���ݿ���������ʾͳ������
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][2]+"��"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][3]+"��"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][4]+"��"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][5]+"ƪ"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][6]+"ƪ"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][7]+"ƪ"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][8]+"ƪ"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][9]+"��"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> <a href=\"#\" onclick=\"showOtherInfo('" + toXmlInfoStr[i][0] + "','" + startDate  + "','" + endDate + "','" + toXmlInfoStr[i][1] +"')\"> "+toXmlInfoStr[i][10]+" </a></td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][11]+"</td>");
			if(type.equals("")){
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][2]+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][3]+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][4]+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][5]+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][6]+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][7]+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][8]+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][9]+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> <a href=\"#\" onclick=\"showOtherInfo('" + toXmlInfoStr[i][0] + "','" + startDate  + "','" + endDate + "','" + toXmlInfoStr[i][1] +"')\"> "+toXmlInfoStr[i][10]+" </a></td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][11]+"</td>");
			}else if(type.equals("1")){
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][2]+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][3]+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][4]+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][9]+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> <a href=\"#\" onclick=\"showOtherInfo('" + toXmlInfoStr[i][0] + "','" + startDate  + "','" + endDate + "','" + toXmlInfoStr[i][1] +"')\"> "+toXmlInfoStr[i][10]+" </a></td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][11]+"</td>");
			}else if(type.equals("2")){
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][5]+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][6]+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][7]+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][8]+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][11]+"</td>");
			}
			
			//Modification finished
			strValue.append("</tr>");
			
			//Modify by Leisx 2011-10-17
			
			
			count1 += Integer.valueOf(toXmlInfoStr[i][2]);
			count2 += Integer.valueOf(toXmlInfoStr[i][3]);
			count3 += Integer.valueOf(toXmlInfoStr[i][4]);
			count4 += Integer.valueOf(toXmlInfoStr[i][5]);
			count5 += Integer.valueOf(toXmlInfoStr[i][6]);
			count6 += Integer.valueOf(toXmlInfoStr[i][7]);
			count7 += Integer.valueOf(toXmlInfoStr[i][8]);
			count8 += Integer.valueOf(toXmlInfoStr[i][9]);

			if(i + 1 == toXmlInfoStr.length && type.equals("")) {
				strValue.append("<tr> <td align='center' height='20' class='td_r_b'>�ϼ�</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count1+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count2+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count3+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count4+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count5+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count6+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count7+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count8+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'>/</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'>/</td>");
				strValue.append("</tr>");
			//Modification finished
			} else if(type.equals("1") && i + 1 == toXmlInfoStr.length) {			
				strValue.append("<tr> <td align='center' height='20' class='td_r_b'>�ϼ�</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count1+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count2+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count3+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count8+"��"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'>/</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'>/</td>");
				strValue.append("</tr>");
			} else if(type.equals("2") && i + 1 == toXmlInfoStr.length) {
				strValue.append("<tr> <td align='center' height='20' class='td_r_b'>�ϼ�</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count4+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count5+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count6+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count7+"ƪ"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'>/</td>");
				
				strValue.append("</tr>");
			} 
		}
		
		showXml.append(strValue);
		showXml.append("</table>");
		return String.valueOf(showXml);
	}

	/**
	 * ��Ϣ�ļ������ü�����ȡ��<br/>
	 * ��Ϣ�ļ������ü�����ȡ��
	 * @param eventType
	 * @param zhiduiId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Integer getZhiduiNewsInfoCnt (String eventType,String zhiduiId,String startDate,String endDate,String state, String type) {
		StringBuffer sqlBuffer = new StringBuffer(); 
		Object count = null;
		try {
			sqlBuffer.append(" select count(1) from T_OA_NEWSFILE where ");
			sqlBuffer.append(" substr(SEND_DEPARTMENT_ID, 1, 4) = substr('");
			sqlBuffer.append(zhiduiId);
			sqlBuffer.append("',1,4)");
			sqlBuffer.append(" and to_char(SEND_TIME,'yyyy-mm-dd') >= '");
			sqlBuffer.append(startDate);
			sqlBuffer.append("' ");
			sqlBuffer.append(" and to_char(SEND_TIME,'yyyy-mm-dd') <= '");
			sqlBuffer.append(endDate);
			sqlBuffer.append("' and STATE = '" + state + "' "); // ʹ��״̬ 0�����±��� 1���ظ�����  2������ 3�������� 4: ������Ϣ����
			sqlBuffer.append(" and TYPE = '" + type + "'");
			count = DBHandler.getSingleResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			logger.error("ȡ��֧����Ϣ�ļ���������sql�� " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}
	
	/**
	 * ��Ϣ�ļ������ü�����ȡ��<br/>
	 * ��Ϣ�ļ������ü�����ȡ��
	 * @param eventType
	 * @param zhiduiId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Integer getDaduiNewsInfoCnt (String eventType,String daduiId,String startDate,String endDate,String state,String type) {
		StringBuffer sqlBuffer = new StringBuffer(); 
		Object count = null;
		try {
			sqlBuffer.append(" select count(1) from T_OA_NEWSFILE where ");
			sqlBuffer.append(" SEND_DEPARTMENT_ID = '");
			sqlBuffer.append(daduiId);
			sqlBuffer.append("' ");
			sqlBuffer.append(" and to_char(SEND_TIME,'yyyy-mm-dd') >= '");
			sqlBuffer.append(startDate);
			sqlBuffer.append("' ");
			sqlBuffer.append(" and to_char(SEND_TIME,'yyyy-mm-dd') <= '");
			sqlBuffer.append(endDate);
			sqlBuffer.append("' and STATE = '" + state + "' ");
			sqlBuffer.append(" and TYPE='"+type+"'");
			count = DBHandler.getSingleResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			logger.error("ȡ��֧����Ϣ�ļ���������sql�� " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}

	/**
	 * ���ӷ���Ŀ������ȡ��<br/>
	 * ���ӷ���Ŀ������ȡ��
	 * @param eventType : "0" ������Ŀ������ȡ��,"1" �ӷ���Ŀ��ȡ��
	 * @param zhiduiId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Integer getScoreRecordCnt (String eventType,String zhiduiId,String startDate,String endDate) {
		StringBuffer sqlBuffer = new StringBuffer(); 
		Object count = null;
		try {
			sqlBuffer.append(" select count(1) from T_OA_SCORERECORD where ");
			sqlBuffer.append(" substr(DEPTID, 1, 4) = substr('");
			sqlBuffer.append(zhiduiId);
			sqlBuffer.append("',1,4)");
			sqlBuffer.append(" and to_char(DAYID,'yyyy-mm-dd') >= '");
			sqlBuffer.append(startDate);
			sqlBuffer.append("' ");
			sqlBuffer.append(" and to_char(DAYID,'yyyy-mm-dd') <= '");
			sqlBuffer.append(endDate);
			if("0".equals(eventType)) {
				sqlBuffer.append("' and TYPE in('1','2','3')  ");
			} else {
				sqlBuffer.append("' and TYPE = '4' ");
			}
			count = DBHandler.getSingleResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			logger.error("ȡ��֧����Ϣ�ļ���������sql�� " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}

	/**
	 * ȡ��ָ����ӵĴ�������Ϣ����<br/>
	 * ȡ��ָ����ӵĴ�������Ϣ����
	 * @param eventType
	 * @param zhiduiId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Integer getDaduiScoreRecordCnt (String eventType,String daDuiId,String startDate,String endDate) {
		StringBuffer sqlBuffer = new StringBuffer(); 
		Object count = null;
		try {
			sqlBuffer.append(" select count(1) from T_OA_SCORERECORD where ");
			sqlBuffer.append(" DEPTID = '");
			sqlBuffer.append(daDuiId);
			sqlBuffer.append("'");
			sqlBuffer.append(" and to_char(DAYID,'yyyy-mm-dd') >= '");
			sqlBuffer.append(startDate);
			sqlBuffer.append("' ");
			sqlBuffer.append(" and to_char(DAYID,'yyyy-mm-dd') <= '");
			sqlBuffer.append(endDate);
			if("0".equals(eventType)) {
				sqlBuffer.append("' and TYPE in('1','2','3')  ");
			} else {
				sqlBuffer.append("' and TYPE = '4' ");
			}
			count = DBHandler.getSingleResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			logger.error("ȡ�ô�ӿ�����Ϣ��������sql�� " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}

	/**
	 * ֧�Ӽ�����Ŀ������ȡ��<br/>
	 * ֧�Ӽ�����Ŀ������ȡ��
	 * @param eventType : "1"�¹� ,"2" ӵ�� "3" ռ�� "4" ӵ�±���
	 * @param zhiduiId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Integer getZhiduiCutCnt (String eventType,String rtype,String zhiduiId,String startDate,String endDate) {
		StringBuffer sqlBuffer = new StringBuffer(); 
		Object count = null;
		try {
			sqlBuffer.append(" select count(1) from T_OA_SCORERECORD where ");
			sqlBuffer.append(" substr(DEPTID, 1, 4) = substr('");
			sqlBuffer.append(zhiduiId);
			sqlBuffer.append("',1,4)");
			sqlBuffer.append(" and to_char(DAYID,'yyyy-mm-dd') >= '");
			sqlBuffer.append(startDate);
			sqlBuffer.append("' ");
			sqlBuffer.append(" and to_char(DAYID,'yyyy-mm-dd') <= '");
			sqlBuffer.append(endDate);
			sqlBuffer.append("' ");
			if(eventType != null) {
				sqlBuffer.append(" and TYPE in('"+eventType+"')");
			}
			if(rtype != null){
				sqlBuffer.append(" and rtype in ('"+rtype+"')");
			}
			count = DBHandler.getSingleResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			logger.error("ȡ��֧�Ӽ��ּ�������sql�� " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}

	/**
	 * ��Ӽ�����Ŀ������ȡ��<br/>
	 * ��Ӽ�����Ŀ������ȡ��
	 * @param eventType : "1"�¹� ,"2" ӵ�� "3" ռ��
	 * @param zhiduiId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Integer getDaduiCutCnt (String eventType,String rtype,String daDuiId,String startDate,String endDate) {
		StringBuffer sqlBuffer = new StringBuffer(); 
		Object count = null;
		try {
			sqlBuffer.append(" select count(1) from T_OA_SCORERECORD where ");
			sqlBuffer.append(" DEPTID = '");
			sqlBuffer.append(daDuiId);
			sqlBuffer.append("'");
			sqlBuffer.append(" and to_char(DAYID,'yyyy-mm-dd') >= '");
			sqlBuffer.append(startDate);
			sqlBuffer.append("' ");
			sqlBuffer.append(" and to_char(DAYID,'yyyy-mm-dd') <= '");
			sqlBuffer.append(endDate);
			sqlBuffer.append("' ");
			if(eventType != null) {
				sqlBuffer.append(" and TYPE in('"+eventType+"')");
			}
			if(rtype != null){
				sqlBuffer.append(" and rtype in ('"+rtype+"')");
			}
			count = DBHandler.getSingleResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			logger.error("ȡ�ô�Ӽ�����Ϣ��������sql�� " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}
	
	/**
	 * �жϻ�������<br/>
	 * @param DeptId ����id
	 * @param DeptName ����name
	 * @return
	 */
	public int getDeptLevel(String DeptId,String DeptName){
		int deptLevel = -1;
		if(!"".equals(DeptName)){
			if(DeptName.endsWith("�ܶ�")){
				return 0;
			}
			if(DeptName.endsWith("֧��")){
				return 1;
			}
			if(DeptName.endsWith("���")){
				return 2;
			}
		}
		
		if(!"".equals(DeptId)){
			if("0000".equals(DeptId.substring(2, 6))){
				return 0;
			}else if("00".equals(DeptId.substring(2, 4))){
				return 1;
			}else if("0000".equals(DeptId.substring(4, 6))){
				return 2;
			}
		}
		return deptLevel;
	}


	/**
	 * ȡ�ü�����Ŀ��ʾ�Ĵ�<br/>
	 * ȡ�ü�����Ŀ��ʾ�Ĵ�
	 * @param toXmlInfoStr
	 * @return ������ʾ���ַ���
	 */
	public String getShowCutStr (String [] toXmlInfoStr,String startDate,String endDate,int deptLevel) {
		StringBuffer showXml = new StringBuffer(); 
		StringBuffer titleStr = new StringBuffer(); 
		AssessUtil assessUtil = new AssessUtil();
		// ���˻�׼��ȡ��
		assessUtil.getStandardInfo();
		// ֧�ӵ����
		if(deptLevel == 1) {
			titleStr.append(toXmlInfoStr[1]+startDate+"��"+endDate+"�۷���Ϣ��");
		} else {
			titleStr.append(toXmlInfoStr[1]+startDate+"��"+endDate+"�۷���Ϣ��");
		}
		showXml.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\"");
		showXml.append("onmouseover=\"changeto()\" onmouseout=\"changeback()\"");
		showXml.append("cellspacing=\"0\" class=\"table\" style=\"font-size:12px\">");
		showXml.append("<tr class='titleTopBack'>");
		showXml.append("<td align='center' height='20' class='td_r_b' colspan='8'>");
		showXml.append(titleStr);
		showXml.append("</td> </tr>");
		showXml.append("<tr> <td align='center' height='20' class='td_r_b'> ��������\\��Ŀ </td>");
		showXml.append("<td align='center' height='20' class='td_r_b'> ©�����������������ٱ���<br/>��ͨ�¹ʼ���<br/>��"+ assessUtil.Point_001003+"��/����" + " </td>");
		showXml.append("<td align='center' height='20' class='td_r_b'> ӵ����Ϣ��ʱ<br/>δ���¼���<br/>��"+ assessUtil.Point_001003+"��/����" + "  </td>");
		showXml.append("<td align='center' height='20' class='td_r_b'> ������ʩ��ռ��<br/>��Ϣ����<br/>��"+ assessUtil.Point_001003+"��/����" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> ӵ�±���δ���ü���<br/>��"+ assessUtil.Point_001007+"��/����" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> ӵ�±��ϳ�ʱδ��ʵ���ּ���<br/>��"+ assessUtil.Point_001005+"��/����" + "  </td>");
		showXml.append("<td align='center' height='20' class='td_r_b'> ���ܶ��·�ӵ����Ϣ<br/>����ʱ��ʵ�򲻺�ʵ���<br/>��"+ (assessUtil.Point_001007+assessUtil.Point_001005)/2+"��/����" + "  </td>");
		showXml.append("<td align='center' height='20' class='td_r_b'> �ܼ��� </td> </tr>");
		StringBuffer strValue = new StringBuffer(); 
		if(deptLevel == 1) {
			strValue.append("<tr> <td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[1].replaceAll("�й����ֽ�ͨ����", "")+" </td>");
		} else {
			strValue.append("<tr> <td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[1]+" </td>");
		}
		strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[2]+"��"+"</td>");
		strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[3]+"��"+"</td>");
		strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[4]+"��"+"</td>");
//		strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[6]+"��"+"</td>");
//		strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[5]+"��"+"</td>");
		strValue.append("<td align='center' height='20' class='td_r_b'> "+(Integer.parseInt(toXmlInfoStr[5])+Integer.parseInt(toXmlInfoStr[6]))+"��"+"</td>");
		strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[7]+"</td>");
		strValue.append("</tr>");
		showXml.append(strValue);
		showXml.append("</table>");
		return String.valueOf(showXml);
	}

	/**
	 * �������float������С�����һλ<br/>
	 * �������float������С�����һλ
	 * @param inputFolat
	 * @return
	 */
	public float cutPointFloat(float inputFolat) {
		// (�����10����1λС����,���Ҫ����λ,��4λ,��������10�ĳ�10000)
		float afterParseFloat = (float) (Math.round(inputFolat * 10)) / 10;
		return afterParseFloat;
	}
}

//2011-08-12���£���ά��