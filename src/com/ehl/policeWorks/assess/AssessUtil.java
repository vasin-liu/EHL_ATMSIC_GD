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
 * 考核信息处理帮助类<br/>
 * @author 
 *
 */
public class AssessUtil {

	private static Logger logger = Logger.getLogger(AssessUtil.class);
	// 事故的事件类别
	public final static String EVENTTYPE_001024 = "001024"; 
	// 拥堵的事件类别
	public final static String EVENTTYPE_001002 = "001002";
	// 施工的事件类别
	public final static String EVENTTYPE_001023 = "001023";
	// 拥堵报料采用的事件类别
	public final static String EVENTTYPE_001001 = "001001";
	// 漏报，错报扣分的事件类别
	public final static String EVENTTYPE_001003 = "001003";
	// 信息文件采用的事件类别
	public final static String EVENTTYPE_001004 = "001004";
	// 信息文件被公安部交管局采用的事件类别
	public final static String EVENTTYPE_001006 = "001006";
	// 拥堵报料超时未核实的事件类别
	public final static String EVENTTYPE_001005 = "001005";
	// 调研信息材料的事件类别
	public final static String EVENTTYPE_001008 = "001008";
	// 调研信息材料被公安部交管局采用的事件类别
	public final static String EVENTTYPE_001009 = "001009";
	// 拥堵信息报料采用
	public float Point_001001 = 0;
	// 交通拥堵
	public float Point_001002 = 0;
	// 漏报，错报扣分
	public float Point_001003 = 0;
	// 信息文件
	public float Point_001004 = 0;
	// 拥堵信息报料超时未核
	public float Point_001005 = 0;
	// 拥堵信息报料未采用
	public float Point_001007 = 0;
	// 信息文件（被公安部交管局采用）
	public float Point_001006 = 0;
	// 施工占道
	public float Point_001023 = 0;
	// 交通事故
	public float Point_001024 = 0;
	// 调研信息材料（省局采用）
	public float Point_001008 = 0;
	// 调研信息材料（部局采用）
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
	 * 取的所有的支队的id和名称<br/>
	 * 取的所有的支队的id和名称
	 * @return
	 */
	public static Object[][] getZhiDuiIdName () {
		Object[][] result = null;
		String sql = "";
		try {
			// 取的所有支队的id和名称
			//Modified by Liuwx 2011-8-12
			sql = "select jgid,jgmc from t_sys_department where substr(jgid,5,2)='00' and " + 
			" jgid not in ('440000000000','446000000000','446100000000','446200000000','446300000000')  " + 
			" order by jgid";
			//信息报送数量和计分统计加上总队
//			sql = "select jgid,jgmc from t_sys_department where substr(jgid,5,2)='00' and " + 
//			" jgid not in ('446000000000','446100000000','446200000000','446300000000')  " + 
//			" order by jgid";
			//Modification finished
			
			result = DBHandler.getMultiResult(String.valueOf(sql));
		} catch (Exception e) {
			logger.error("取得所有支队信息出错。sql： " + sql);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 取的指定支队的所有大队的id和名称<br/>
	 * 取的指定支队的所有大队的id和名称
	 * @return
	 */
	public static Object[][] getAllDaDuiByZhiDuiId (String zhiduiId) {
		Object[][] result = null;
		String sql = "";
		try {
			// 取的所有支队的id和名称
			//Modified by Liuwx 2011-8-12
//			sql = "select jgid,jgmc from t_sys_department where substr(jgid,1,4)='"+ zhiduiId.substring(0,4) + "' and " + 
//			" jgid not in ('440000000000','446000000000','446100000000','446200000000','446300000000')  " + 
//			" order by jgid";
			//信息报送数量和计分统计加上总队
			sql = "select jgid,jgmc from t_sys_department where substr(jgid,1,4)='"+ zhiduiId.substring(0,4) + "' and " + 
			" jgid not in ('446000000000','446100000000','446200000000','446300000000')  " + 
			" order by jgid";
			//Modification finished
			
			result = DBHandler.getMultiResult(String.valueOf(sql));
		} catch (Exception e) {
			logger.error("取得指定支队的所有大队信息出错。sql： " + sql);
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 取得指定支队的考核分值<br/>
	 * @param zhiduiId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String[] getOneZhiduiAssess (String zhiduiId,String DeptName, String startDate,String endDate, String type) {
		String[] returnStr = new String[10]; 
		AssessUtil assessUtil = new AssessUtil();
		TrafficNewsFeedsDao trafficNewsFeedsDao = new TrafficNewsFeedsDao();
		// 考核基准的取得
		assessUtil.getStandardInfo();
		//计分表不同信息类型、不同扣分原因的信息条数
		boolean isSelf = zhiduiId.substring(2,4).equals("00");
		ScoreRecordDao scoreDao = new ScoreRecordDao();
		Object[][] kfdata = scoreDao.getItemCounts(zhiduiId, startDate,endDate, isSelf);
		int[][] kfcounts =  scoreDao.changeItemCounts(kfdata);
		// 事故件数的取得
		Integer accCount = assessUtil.getZhiduiInfoCnt(EVENTTYPE_001024, zhiduiId, startDate, endDate);
		accCount-=kfcounts[0][1];
		returnStr[0] = String.valueOf(accCount);
		// 拥堵件数的取得
		Integer crowdCount = assessUtil.getZhiduiInfoCnt(EVENTTYPE_001002, zhiduiId, startDate, endDate);
		crowdCount -= kfcounts[1][1];
		returnStr[1] = String.valueOf(crowdCount);
		// 施工件数的取得
		Integer buildCount = assessUtil.getZhiduiInfoCnt(EVENTTYPE_001023, zhiduiId, startDate, endDate);
		buildCount -= kfcounts[2][1];
		returnStr[2] = String.valueOf(buildCount);
		//信息文件采用件数的取得
		int[][] counts = NewsFileCore.getCounts(zhiduiId, startDate, endDate);
		// 信息文件省厅采用件数的取得
		Integer ptst = counts[type_pt][sbtype_st];
		returnStr[3] = String.valueOf(ptst);
		// 信息文件部局采用件数的取得
		Integer ptbj = counts[type_pt][sbtype_bj];
		returnStr[4] = String.valueOf(ptbj);
		// 调研信息材料省厅的取得
		Integer dyst = counts[type_dy][sbtype_st];
		returnStr[5] = String.valueOf(dyst);
		// 调研信息材料部局的取得
		Integer dybj = counts[type_dy][sbtype_bj];
		returnStr[6] = String.valueOf(dybj);
		
		// 取得报料采用件数
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
		
		// 附加减分信息件数取得
//		Integer cutCount = assessUtil.getScoreRecordCnt("0", zhiduiId, startDate, endDate);
//		// 附加加分信息件数取得
//		Integer addCount = assessUtil.getScoreRecordCnt("1", zhiduiId, startDate, endDate); 
//		// 信息文件公安部采用件数
//		returnStr[4] = String.valueOf(addCount);
		// 拥堵报料采用件数
		returnStr[7] = String.valueOf(cycount);
		// 扣分的加算
		int cutCount = kfcounts[0][0] + kfcounts[0][1] + kfcounts[1][0] + kfcounts[1][1]+ kfcounts[1][2]+ kfcounts[2][0] + kfcounts[2][1];
		gqcount = kfcounts[3][0];
		wcycount = kfcounts[3][1];
		float addPoint = cutCount * assessUtil.Point_001003 + gqcount * assessUtil.Point_001005 + wcycount * assessUtil.Point_001007;
		// 信息扣分
		returnStr[8] = String.valueOf(assessUtil.cutPointFloat(addPoint));
		//警情分数
		float alarmPoint = accCount * assessUtil.Point_001024 
			 + crowdCount * assessUtil.Point_001002 
			 + cycount * assessUtil.Point_001001
			 + buildCount * assessUtil.Point_001023
			 + addPoint;
		//信息文件分数
		float newsFilePoint = ptst * assessUtil.Point_001004 
		 + ptbj * assessUtil.Point_001006
		 + dyst * assessUtil.Point_001008
		 + dybj * assessUtil.Point_001009;
		//总分数
		float totalPoint = alarmPoint + newsFilePoint;
		// 总得分
		//Modify by Xiayx 2011-10-10
		//添加考核类型->考核分值按类型计算
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
	 * 取得指定大队的考核分值<br/>
	 * @param zhiduiId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static String[] getOneDaduiAssess (String daDuiId,String DeptName,String startDate,String endDate,String type) {
		String[] returnStr = new String[10]; 
		AssessUtil assessUtil = new AssessUtil();
		TrafficNewsFeedsDao trafficNewsFeedsDao = new TrafficNewsFeedsDao();
		// 考核基准的取得
		assessUtil.getStandardInfo();
		//计分表不同信息类型、不同扣分原因的信息条数
		boolean isSelf = true;//daDuiId.substring(4,6).equals("00");
		ScoreRecordDao scoreDao = new ScoreRecordDao();
		Object[][] kfdata = scoreDao.getItemCounts(daDuiId, startDate,endDate, isSelf);
		int[][] kfcounts =  scoreDao.changeItemCounts(kfdata);
		// 事故件数的取得
		Integer accCount = assessUtil.getDaduiInfoCnt(EVENTTYPE_001024, daDuiId, startDate, endDate);
		returnStr[0] = String.valueOf(accCount-=kfcounts[0][1]);
		// 拥堵件数的取得
		Integer crowdCount = assessUtil.getDaduiInfoCnt(EVENTTYPE_001002, daDuiId, startDate, endDate);
		returnStr[1] = String.valueOf(crowdCount-=kfcounts[1][1]);
		// 施工件数的取得
		Integer buildCount = assessUtil.getDaduiInfoCnt(EVENTTYPE_001023, daDuiId, startDate, endDate);
		returnStr[2] = String.valueOf(buildCount-=kfcounts[2][1]);
		// 取得报料采用件数 和 未采用件数
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
		//信息文件采用件数的取得
		int[][] counts = NewsFileCore.getCounts(daDuiId, startDate, endDate);
		// 信息文件省厅采用件数的取得
		Integer ptst = counts[type_pt][sbtype_st];
		returnStr[3] = String.valueOf(ptst);
		// 信息文件部局采用件数的取得
		Integer ptbj = counts[type_pt][sbtype_bj];
		returnStr[4] = String.valueOf(ptbj);
		// 调研信息材料省厅的取得
		Integer dyst = counts[type_dy][sbtype_st];
		returnStr[5] = String.valueOf(dyst);
		// 调研信息材料部局的取得
		Integer dybj = counts[type_dy][sbtype_bj];
		returnStr[6] = String.valueOf(dybj);
//		Integer addCount = assessUtil.getDaduiScoreRecordCnt("1", daDuiId, startDate, endDate);
//		// 信息文件公安部采用加分件数
		// 附加减分信息件数取得
//		Integer cutCount = assessUtil.getDaduiScoreRecordCnt("0", daDuiId, startDate, endDate);
		// 拥堵报料采用件数
		returnStr[7] = String.valueOf(cycount);
		// 信息扣分的加算
		// 信息扣分
		int cutCount = kfcounts[0][0] + kfcounts[0][1] + kfcounts[1][0] + kfcounts[1][1]+ kfcounts[1][2]+ kfcounts[2][0] + kfcounts[2][1];
		gqcount = kfcounts[3][0];
		wcycount = kfcounts[3][1];
		float addPoint = cutCount * assessUtil.Point_001003 + gqcount * assessUtil.Point_001005 + wcycount * assessUtil.Point_001007;
		returnStr[8] = String.valueOf(assessUtil.cutPointFloat(addPoint));
		//警情分数
		float alarmPoint = accCount * assessUtil.Point_001024 
			 + crowdCount * assessUtil.Point_001002 
			 + cycount * assessUtil.Point_001001
			 + buildCount * assessUtil.Point_001023
			 + addPoint;
		//信息文件分数
		float newsFilePoint = ptst * assessUtil.Point_001004 
		 + ptbj * assessUtil.Point_001006
		 + dyst * assessUtil.Point_001008
		 + dybj * assessUtil.Point_001009;
		//总分数
		float totalPoint = alarmPoint + newsFilePoint;
		// 总得分
		//Modify by Xiayx 2011-10-10
		//添加考核类型->考核分值按类型计算
		float zongfen = totalPoint;
		if(type.equals("1")){
			zongfen = alarmPoint;
		}else if(type.equals("2")){
			zongfen = newsFilePoint;
		}
		//Modification finished
		zongfen = assessUtil.cutPointFloat(zongfen);
		// 总得分
		returnStr[9] = String.valueOf(assessUtil.cutPointFloat(zongfen));
		return returnStr;
	}
	
	/**
	 * 取得指定支队的待考核信息件数<br/>
	 * 取得指定支队的待考核信息件数
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
				//如果是拥堵，根据道路方向排除城区拥堵信息
				sqlBuffer.append(" and (select roadlevel from t_oa_dictdlfx where roadid = dlmc) is not null");
			}else if(eventType.equals(EVENTTYPE_001024)){
				//如果是事故，只统计总队处理过的事故信息
				sqlBuffer.append(" and eventstate in ('004031','004036','004037','004035') ");
			}
			count = DBHandler.getSingleResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			logger.error("取得支队考核信息件数出错。sql： " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}
	
	/**
	 * 取得指定大队的待考核信息件数<br/>
	 * 取得指定大队的待考核信息件数
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
				//如果是拥堵，根据道路方向排除城区拥堵信息
				sqlBuffer.append(" and (select roadlevel from t_oa_dictdlfx where roadid = dlmc) is not null");
			}else if(eventType.equals(EVENTTYPE_001024)){
				//如果是事故，只统计总队处理过的事故信息
				sqlBuffer.append(" and eventstate in ('004031','004036','004037','004035') ");
			}
			count = DBHandler.getSingleResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			logger.error("取得大队考核信息件数出错。sql： " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}
	/**
	 * 取得考核标准信息<br/>
	 * 取得考核标准信息
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
			logger.error("取得考核标准信息出错。 sql:" + sql);
		}
	}

	/**
	 * 取得画面显示的字符串<br/>
	 * 取得画面显示的字符串
	 * @param toXmlInfoStr
	 * @return 画面显示的字符串
	 */
	public static String getShowChartStr (String [][] toXmlInfoStr,String zhiDuiId,String startDate,String endDate) {
		StringBuffer showXml = new StringBuffer(); 
		// 显示所有支队考核成绩
		if("".equals(zhiDuiId)) {
			//Modified by Liuwx 2011-8-12
			showXml.append("<chart caption='各市交警支队"+startDate+"至"+endDate+"考核分析图' shownames='1' showvalues='0' decimals='0' baseFont='宋体' baseFontSize='15' formatNumberScale='0'>");
			//Modification finished			
			StringBuffer strName = new StringBuffer(); 
			StringBuffer strValue = new StringBuffer(); 
			for(int i=0; i<toXmlInfoStr.length; i++) {
				strName.append("<category label='");
				//Modified by Liuwx 2011-8-12
				strName.append(toXmlInfoStr[i][1].replaceAll("市公安局交通警察支队", "").replaceAll("广东省公安厅交通管理局", "总队"));
				//Modification finished
				strName.append("' /> ");
				strValue.append("<set value='");
				strValue.append(toXmlInfoStr[i][11]);
				strValue.append("' /> ");
				
			}
			showXml.append(" <categories> ");
			showXml.append(strName);
			showXml.append(" </categories> ");
			showXml.append(" <dataset seriesName='考核分值（单位：分）' color='8BBA00' showValues='0'> ");
			showXml.append(strValue);
			showXml.append(" </dataset> " );
			showXml.append(" </chart> ");
		} else {
			showXml.append("<chart caption='" + toXmlInfoStr[0][1]+startDate+"至"+endDate+"考核分析图' shownames='1' showvalues='0' decimals='0' baseFont='宋体' baseFontSize='15' formatNumberScale='0'>");
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
			showXml.append(" <dataset seriesName='考核分值（单位：分）' color='8BBA00' showValues='0'> ");
			showXml.append(strValue);
			showXml.append(" </dataset> " );
			showXml.append(" </chart> ");
		}
		return String.valueOf(showXml);
	}

	/**
	 * 取得画面显示的字符串<br/>
	 * 取得画面显示的字符串
	 * @param toXmlInfoStr
	 * @return 画面显示的字符串
	 */
	public static String getShowTableStr (String [][] toXmlInfoStr,String zhiDuiId,String startDate,String endDate, String type) {
		StringBuffer showXml = new StringBuffer(); 
		StringBuffer titleStr = new StringBuffer(); 
		AssessUtil assessUtil = new AssessUtil();
		// 考核基准的取得
		assessUtil.getStandardInfo();
		// 显示所有支队考核成绩
		if("".equals(zhiDuiId)) {
			//Modified by Liuwx 2011-8-12
			titleStr.append("各市交警支队信息考评表（"+startDate+"至"+endDate+"）");
			//Modification finished
		} else {
			titleStr.append(toXmlInfoStr[0][1]+startDate+"至"+endDate+"考核表");
		}
		showXml.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\"");
		showXml.append("onmouseover=\"changeto()\" onmouseout=\"changeback()\"");
		showXml.append("cellspacing=\"0\" class=\"table\" style=\"font-size:12px\">");
		showXml.append("<tr class='titleTopBack'>");
		showXml.append("<td align='center' height='20' class='td_r_b' colspan='11'>");
		showXml.append(titleStr);
		showXml.append("</td> </tr>");
		showXml.append("<tr> <td align='center' height='20' class='td_r_b'> 机构名称\\项目 </td>");
		//Modify by Xiayx 2011-10-10
		//添加考核类型->根据考核类型显示表头
//		showXml.append("<td align='center' height='20' class='td_r_b'> 交通事故<br/>（"+ assessUtil.Point_001024+"分/条）" + " </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> 交通拥堵<br/>（"+ assessUtil.Point_001002+"分/条）" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> 占道施工<br/>（"+ assessUtil.Point_001023+"分/条）" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> 省厅采用<br/>普通信息<br/>（"+ assessUtil.Point_001004+"分/篇）" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> 部局采用<br/>普通信息<br/>（"+ assessUtil.Point_001006+"分/篇）" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> 省厅采用<br/>调研信息<br/>（"+ assessUtil.Point_001008+"分/篇）" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> 部局采用<br/>调研信息<br/>（"+ assessUtil.Point_001009+"分/篇）" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> 拥堵报料<br/>核 &nbsp; &nbsp;实<br/>（"+ assessUtil.Point_001001+"分/条）" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> 信息扣分 </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> 总得分 </td> </tr>");
		if(type.equals("")){
			showXml.append("<td align='center' height='20' class='td_r_b'> 交通事故<br/>（"+ assessUtil.Point_001024+"分/条）" + " </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 交通拥堵<br/>（"+ assessUtil.Point_001002+"分/条）" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 占道施工<br/>（"+ assessUtil.Point_001023+"分/条）" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 省厅采用<br/>工作信息<br/>（"+ assessUtil.Point_001004+"分/篇）" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 部局采用<br/>工作信息<br/>（"+ assessUtil.Point_001006+"分/篇）" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 省厅采用<br/>调研信息<br/>（"+ assessUtil.Point_001008+"分/篇）" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 部局采用<br/>调研信息<br/>（"+ assessUtil.Point_001009+"分/篇）" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 拥堵报料<br/>核 &nbsp; &nbsp;实<br/>（"+ assessUtil.Point_001001+"分/条）" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 信息扣分 </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 总得分 </td> </tr>");
		}else if(type.equals("1")){
			showXml.append("<td align='center' height='20' class='td_r_b'> 交通事故<br/>（"+ assessUtil.Point_001024+"分/条）" + " </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 交通拥堵<br/>（"+ assessUtil.Point_001002+"分/条）" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 占道施工<br/>（"+ assessUtil.Point_001023+"分/条）" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 拥堵报料<br/>核 &nbsp; &nbsp;实<br/>（"+ assessUtil.Point_001001+"分/条）" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 信息扣分 </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 总得分 </td> </tr>");
		}else if(type.equals("2")){
			showXml.append("<td align='center' height='20' class='td_r_b'> 省厅采用<br/>工作信息<br/>（"+ assessUtil.Point_001004+"分/篇）" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 部局采用<br/>工作信息<br/>（"+ assessUtil.Point_001006+"分/篇）" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 省厅采用<br/>调研信息<br/>（"+ assessUtil.Point_001008+"分/篇）" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 部局采用<br/>调研信息<br/>（"+ assessUtil.Point_001009+"分/篇）" + "  </td>");
			showXml.append("<td align='center' height='20' class='td_r_b'> 总得分 </td> </tr>");
		}
		//Modification finished
		StringBuffer strValue = new StringBuffer(); 
		
		//计数器 Modified by Leisx 2011-10-17
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
			//取消总队的点击事件
		
			if("".equals(zhiDuiId) && !("00".equals(toXmlInfoStr[i][0].substring(2,4)))) {
			//Modification finished
				strValue.append("<tr> <td align='center' height='20' class='td_r_b'> <a href=\"#\" onclick=\"doAssessInfo('" + toXmlInfoStr[i][0] + "');\" >"+toXmlInfoStr[i][1].replaceAll("市公安局交通警察", "")+" </a></td>");
			} else {
				strValue.append("<tr> <td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][1].replaceAll("公安局交通警察", "交警").replace("公安厅交通管理局", "交警总队")+" </td>");
			}
			
			//Modify by Xiayx 2011-10-10
			//添加考核类型->根据考核类型显示统计数据
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][2]+"条"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][3]+"条"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][4]+"条"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][5]+"篇"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][6]+"篇"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][7]+"篇"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][8]+"篇"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][9]+"条"+"</td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> <a href=\"#\" onclick=\"showOtherInfo('" + toXmlInfoStr[i][0] + "','" + startDate  + "','" + endDate + "','" + toXmlInfoStr[i][1] +"')\"> "+toXmlInfoStr[i][10]+" </a></td>");
//			strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][11]+"</td>");
			if(type.equals("")){
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][2]+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][3]+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][4]+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][5]+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][6]+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][7]+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][8]+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][9]+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> <a href=\"#\" onclick=\"showOtherInfo('" + toXmlInfoStr[i][0] + "','" + startDate  + "','" + endDate + "','" + toXmlInfoStr[i][1] +"')\"> "+toXmlInfoStr[i][10]+" </a></td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][11]+"</td>");
			}else if(type.equals("1")){
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][2]+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][3]+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][4]+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][9]+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> <a href=\"#\" onclick=\"showOtherInfo('" + toXmlInfoStr[i][0] + "','" + startDate  + "','" + endDate + "','" + toXmlInfoStr[i][1] +"')\"> "+toXmlInfoStr[i][10]+" </a></td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][11]+"</td>");
			}else if(type.equals("2")){
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][5]+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][6]+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][7]+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[i][8]+"篇"+"</td>");
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
				strValue.append("<tr> <td align='center' height='20' class='td_r_b'>合计</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count1+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count2+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count3+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count4+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count5+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count6+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count7+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count8+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'>/</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'>/</td>");
				strValue.append("</tr>");
			//Modification finished
			} else if(type.equals("1") && i + 1 == toXmlInfoStr.length) {			
				strValue.append("<tr> <td align='center' height='20' class='td_r_b'>合计</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count1+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count2+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count3+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count8+"条"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'>/</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'>/</td>");
				strValue.append("</tr>");
			} else if(type.equals("2") && i + 1 == toXmlInfoStr.length) {
				strValue.append("<tr> <td align='center' height='20' class='td_r_b'>合计</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count4+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count5+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count6+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'> "+count7+"篇"+"</td>");
				strValue.append("<td align='center' height='20' class='td_r_b'>/</td>");
				
				strValue.append("</tr>");
			} 
		}
		
		showXml.append(strValue);
		showXml.append("</table>");
		return String.valueOf(showXml);
	}

	/**
	 * 信息文件被采用件数的取得<br/>
	 * 信息文件被采用件数的取得
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
			sqlBuffer.append("' and STATE = '" + state + "' "); // 使用状态 0：最新报送 1：重复材料  2：采用 3：不采用 4: 调研信息材料
			sqlBuffer.append(" and TYPE = '" + type + "'");
			count = DBHandler.getSingleResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			logger.error("取得支队信息文件件数出错。sql： " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}
	
	/**
	 * 信息文件被采用件数的取得<br/>
	 * 信息文件被采用件数的取得
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
			logger.error("取得支队信息文件件数出错。sql： " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}

	/**
	 * 附加分项目件数的取得<br/>
	 * 附加分项目件数的取得
	 * @param eventType : "0" 减分项目件数的取得,"1" 加分项目的取得
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
			logger.error("取得支队信息文件件数出错。sql： " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}

	/**
	 * 取得指定大队的待考核信息件数<br/>
	 * 取得指定大队的待考核信息件数
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
			logger.error("取得大队考核信息件数出错。sql： " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}

	/**
	 * 支队减分项目件数的取得<br/>
	 * 支队减分项目件数的取得
	 * @param eventType : "1"事故 ,"2" 拥堵 "3" 占道 "4" 拥堵报料
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
			logger.error("取得支队减分件数出错。sql： " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}

	/**
	 * 大队减分项目件数的取得<br/>
	 * 大队减分项目件数的取得
	 * @param eventType : "1"事故 ,"2" 拥堵 "3" 占道
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
			logger.error("取得大队减分信息件数出错。sql： " + sqlBuffer);
			logger.error(e.getMessage());
			e.printStackTrace();
		} 
		if (count == null) {
			return 0;
		}
		return Integer.valueOf(String.valueOf(count));
	}
	
	/**
	 * 判断机构类型<br/>
	 * @param DeptId 机构id
	 * @param DeptName 机构name
	 * @return
	 */
	public int getDeptLevel(String DeptId,String DeptName){
		int deptLevel = -1;
		if(!"".equals(DeptName)){
			if(DeptName.endsWith("总队")){
				return 0;
			}
			if(DeptName.endsWith("支队")){
				return 1;
			}
			if(DeptName.endsWith("大队")){
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
	 * 取得减分项目显示的串<br/>
	 * 取得减分项目显示的串
	 * @param toXmlInfoStr
	 * @return 画面显示的字符串
	 */
	public String getShowCutStr (String [] toXmlInfoStr,String startDate,String endDate,int deptLevel) {
		StringBuffer showXml = new StringBuffer(); 
		StringBuffer titleStr = new StringBuffer(); 
		AssessUtil assessUtil = new AssessUtil();
		// 考核基准的取得
		assessUtil.getStandardInfo();
		// 支队的情况
		if(deptLevel == 1) {
			titleStr.append(toXmlInfoStr[1]+startDate+"至"+endDate+"扣分信息表");
		} else {
			titleStr.append(toXmlInfoStr[1]+startDate+"至"+endDate+"扣分信息表");
		}
		showXml.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\"");
		showXml.append("onmouseover=\"changeto()\" onmouseout=\"changeback()\"");
		showXml.append("cellspacing=\"0\" class=\"table\" style=\"font-size:12px\">");
		showXml.append("<tr class='titleTopBack'>");
		showXml.append("<td align='center' height='20' class='td_r_b' colspan='8'>");
		showXml.append(titleStr);
		showXml.append("</td> </tr>");
		showXml.append("<tr> <td align='center' height='20' class='td_r_b'> 机构名称\\项目 </td>");
		showXml.append("<td align='center' height='20' class='td_r_b'> 漏报（或瞒报、错报、迟报）<br/>交通事故件数<br/>（"+ assessUtil.Point_001003+"分/条）" + " </td>");
		showXml.append("<td align='center' height='20' class='td_r_b'> 拥堵信息超时<br/>未更新件数<br/>（"+ assessUtil.Point_001003+"分/条）" + "  </td>");
		showXml.append("<td align='center' height='20' class='td_r_b'> 不报送施工占道<br/>信息件数<br/>（"+ assessUtil.Point_001003+"分/条）" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> 拥堵报料未采用件数<br/>（"+ assessUtil.Point_001007+"分/条）" + "  </td>");
//		showXml.append("<td align='center' height='20' class='td_r_b'> 拥堵报料超时未核实减分件数<br/>（"+ assessUtil.Point_001005+"分/条）" + "  </td>");
		showXml.append("<td align='center' height='20' class='td_r_b'> 对总队下发拥堵消息<br/>不及时核实或不核实清楚<br/>（"+ (assessUtil.Point_001007+assessUtil.Point_001005)/2+"分/条）" + "  </td>");
		showXml.append("<td align='center' height='20' class='td_r_b'> 总减分 </td> </tr>");
		StringBuffer strValue = new StringBuffer(); 
		if(deptLevel == 1) {
			strValue.append("<tr> <td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[1].replaceAll("市公安局交通警察", "")+" </td>");
		} else {
			strValue.append("<tr> <td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[1]+" </td>");
		}
		strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[2]+"条"+"</td>");
		strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[3]+"条"+"</td>");
		strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[4]+"条"+"</td>");
//		strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[6]+"条"+"</td>");
//		strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[5]+"条"+"</td>");
		strValue.append("<td align='center' height='20' class='td_r_b'> "+(Integer.parseInt(toXmlInfoStr[5])+Integer.parseInt(toXmlInfoStr[6]))+"条"+"</td>");
		strValue.append("<td align='center' height='20' class='td_r_b'> "+toXmlInfoStr[7]+"</td>");
		strValue.append("</tr>");
		showXml.append(strValue);
		showXml.append("</table>");
		return String.valueOf(showXml);
	}

	/**
	 * 将传入的float数保留小数点后一位<br/>
	 * 将传入的float数保留小数点后一位
	 * @param inputFolat
	 * @return
	 */
	public float cutPointFloat(float inputFolat) {
		// (这里的10就是1位小数点,如果要其它位,如4位,这里两个10改成10000)
		float afterParseFloat = (float) (Math.round(inputFolat * 10)) / 10;
		return afterParseFloat;
	}
}

//2011-08-12更新，刘维新