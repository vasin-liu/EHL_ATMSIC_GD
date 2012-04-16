package com.ehl.dispatch.bdispatch.dataAccess;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：AlarmAffairQueryCtrl.java
 * 
 * 摘 要：指挥调度监控警情事件查询数据访问类。
 * 			查询未处理警情，需关注警情
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-9
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */
public class AlarmAffairQuery
{
	private static final Logger logger = Logger.getLogger(AlarmAffairQuery.class);
	/**查询需关注警情信息，包括状态为未处理的信息
	 * 
	 * @return
	 */
	public Object [][] queryNoticeAffair(String branchCode,String msgWatchingTimeRange)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		   selectSQL.append("SELECT t.alarmid,to_char(t.alarmtime, 'dd')||'日'||to_char(t.alarmtime,'HH24:mi'),t.eventstate,tc1.name esource, tc2.name etype,tc3.name elevel,tc4.name estate,");
		   selectSQL.append(" t.eventtype ,t.eventsource, t1.supcount,t.x,t.y,t.title,t.EVENTLEVEL,t.DISPOSEPERSON FROM t_attemper_alarm t, t_attemper_code tc1,t_attemper_code tc2,t_attemper_code tc3,t_attemper_code tc4,");
		   selectSQL.append(" (select ts1.alarmid,count(ts2.superviseid) supcount ");
		   selectSQL.append(" FROM t_attemper_alarm ts1, t_attemper_supervise ts2 ");
		   selectSQL.append(" where ts1.alarmid = ts2.alarmid ");
		   selectSQL.append(" group by ts1.alarmid ");
		   selectSQL.append(" ) t1 ");
		   selectSQL.append(" WHERE tc1.id(+)=t.eventsource AND tc2.id(+)=t.eventtype AND tc3.id(+)=t.eventlevel AND tc4.id=t.eventstate ");
		   selectSQL.append(" AND ( t.mainalarmid is null) " );
		   selectSQL.append(" AND t1.alarmid(+) = t.alarmid ");
		   selectSQL.append(" AND t.alarmtime >= (sysdate-" + msgWatchingTimeRange + "/24)");
		
		   boolean temp=false;
		   if(branchCode.substring(4, 12).equals("00000000")){
			   temp=true;
		   }
		   
		if(null!= branchCode && !"".equals(branchCode)&&temp==false)
		{
//			selectSQL.append(" AND (t.ALARMREGION=(select jgmc from t_sys_department where jgid='" + branchCode + "') or t.alarmunit='" + branchCode + "' OR t.disposeunit='" + branchCode + "' OR t.acceptunit='" + branchCode + "' OR t.COMEOUTUNIT='" + branchCode + "')");
			selectSQL.append(" AND (t.ALARMREGION=(select jgmc from t_sys_department where jgid='" + branchCode + "'))");
		}
		
		  selectSQL.append(" AND (t.eventstate<>'004013' and t.eventstate<>'004020') ");
		  selectSQL.append(" AND (t.eventsource in ('002001','002007','002005','002006','002009'))");
		  selectSQL.append(" ORDER BY t.alarmtime desc");
		 
		   System.out.println("警情列表SQL="+ selectSQL.toString());
		   result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("查询需关注警情信息数据库错误!");
		}
		  return result;
	}
	
	//获取事故详细信息
	public Object [][] getAccidentDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {	
		  selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM t_attemper_acc_detail_view tv WHERE tv.alarmid='" + alarmId + "'");
		  System.out.println("select sql111=="+selectSQL);
		  result =  DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("获取事故详细信息数据库错误!");
		}
		return result;
	}
	//
	//获取大型故障车详细信息
	public Object [][] getTroubleCargoDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		  selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_CARGO_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		  result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("获取大型故障车详细信息数据库错误!");
		}
		return result;
	}
	
	//获取恶劣天气详细信息
	public Object [][] getDisasterWeatherDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try{	
		  selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_weather_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		  System.out.println("恶劣天气 SQL=="+selectSQL);
		  result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("获取恶劣天气详细信息数据库错误!");
		}
		return result;
	}
	
	//
	//获取拥堵详细信息
	public Object [][] getTrafficjamDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		  selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_BLOCK_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
	      result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("获取拥堵详细信息数据库错误!");
		}
		return result;
	}
	//
	//获取市政信息（煤气、热力泄露、自来水、停电等）详细信息
	public Object [][] getCitizenCaseDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_citizen_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("获取市政信息数据库错误!");
		}
		return result;
	}
	
	//
	////公路桥梁、泥石流塌方等地质事件详细信息
	public Object [][] getGeologicDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_geolo_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("获取市政信息数据库错误!");
		}
		return result;
	}
	
	//
	//查缉布控（嫌疑车辆）详细信息
	public Object [][] getSuspiconCarDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {	
		selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_blklist_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("查缉布控（嫌疑车辆）详细信息数据库错误!");
		}
		return result;
	}
	 
	//治安事件（案件）详细信息
	public Object [][] getSocialCaseDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_social_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("治安事件（案件）详细信息数据库错误!");
		}
		return result;
	}//
	
	//火灾爆炸详细信息
	public Object [][] getFireAndBoomDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_fire_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("火灾爆炸详细信息数据库错误!");
		}
		return result;
	}
	//设备故障详细信息
	public Object [][] getEuqpmentDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_EUQIP_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		System.out.println("euipment selectSQL==="+selectSQL);
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("设备故障详细信息数据库错误!");
		}
		return result;
	}
	public Object [][] getSubAffairIDs(String alarmId)
	{
		Object[][] result = null;
		try {
		String subSQL = "SELECT t.alarmId,t.eventtype FROM t_attemper_alarm  t WHERE  t.mainalarmid='" + alarmId + "'";
		result = DBHandler.getMultiResult(subSQL);
		}catch(Exception e) {
			logger.error("合并警情详细信息数据库错误!");
		}
		return result;
	}
}
