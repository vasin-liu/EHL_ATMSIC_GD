package com.ehl.dispatch.bdispatch.dataAccess;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
/**
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�AlarmAffairQueryCtrl.java
 * 
 * ժ Ҫ��ָ�ӵ��ȼ�ؾ����¼���ѯ���ݷ����ࡣ
 * 			��ѯδ�����飬���ע����
 *  
 * ��ǰ�汾��1.0
 * 
 * �� �ߣ�LChQ  2009-4-9
 * 
 * �޸��ˣ�
 * 
 * �޸����ڣ�
 * 
 */
public class AlarmAffairQuery
{
	private static final Logger logger = Logger.getLogger(AlarmAffairQuery.class);
	/**��ѯ���ע������Ϣ������״̬Ϊδ�������Ϣ
	 * 
	 * @return
	 */
	public Object [][] queryNoticeAffair(String branchCode,String msgWatchingTimeRange)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		   selectSQL.append("SELECT t.alarmid,to_char(t.alarmtime, 'dd')||'��'||to_char(t.alarmtime,'HH24:mi'),t.eventstate,tc1.name esource, tc2.name etype,tc3.name elevel,tc4.name estate,");
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
		 
		   System.out.println("�����б�SQL="+ selectSQL.toString());
		   result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("��ѯ���ע������Ϣ���ݿ����!");
		}
		  return result;
	}
	
	//��ȡ�¹���ϸ��Ϣ
	public Object [][] getAccidentDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {	
		  selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM t_attemper_acc_detail_view tv WHERE tv.alarmid='" + alarmId + "'");
		  System.out.println("select sql111=="+selectSQL);
		  result =  DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("��ȡ�¹���ϸ��Ϣ���ݿ����!");
		}
		return result;
	}
	//
	//��ȡ���͹��ϳ���ϸ��Ϣ
	public Object [][] getTroubleCargoDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		  selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_CARGO_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		  result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("��ȡ���͹��ϳ���ϸ��Ϣ���ݿ����!");
		}
		return result;
	}
	
	//��ȡ����������ϸ��Ϣ
	public Object [][] getDisasterWeatherDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try{	
		  selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_weather_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		  System.out.println("�������� SQL=="+selectSQL);
		  result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("��ȡ����������ϸ��Ϣ���ݿ����!");
		}
		return result;
	}
	
	//
	//��ȡӵ����ϸ��Ϣ
	public Object [][] getTrafficjamDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		  selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_BLOCK_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
	      result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("��ȡӵ����ϸ��Ϣ���ݿ����!");
		}
		return result;
	}
	//
	//��ȡ������Ϣ��ú��������й¶������ˮ��ͣ��ȣ���ϸ��Ϣ
	public Object [][] getCitizenCaseDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_citizen_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("��ȡ������Ϣ���ݿ����!");
		}
		return result;
	}
	
	//
	////��·��������ʯ�������ȵ����¼���ϸ��Ϣ
	public Object [][] getGeologicDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_geolo_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("��ȡ������Ϣ���ݿ����!");
		}
		return result;
	}
	
	//
	//�鼩���أ����ɳ�������ϸ��Ϣ
	public Object [][] getSuspiconCarDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {	
		selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_blklist_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("�鼩���أ����ɳ�������ϸ��Ϣ���ݿ����!");
		}
		return result;
	}
	 
	//�ΰ��¼�����������ϸ��Ϣ
	public Object [][] getSocialCaseDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_social_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("�ΰ��¼�����������ϸ��Ϣ���ݿ����!");
		}
		return result;
	}//
	
	//���ֱ�ը��ϸ��Ϣ
	public Object [][] getFireAndBoomDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_fire_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("���ֱ�ը��ϸ��Ϣ���ݿ����!");
		}
		return result;
	}
	//�豸������ϸ��Ϣ
	public Object [][] getEuqpmentDetailById(String alarmId,String subAlarms)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT tv.* ,'"+ subAlarms  + "' FROM T_ATTEMPER_EUQIP_DETAIL_VIEW tv WHERE tv.alarmid='" + alarmId + "'");
		System.out.println("euipment selectSQL==="+selectSQL);
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("�豸������ϸ��Ϣ���ݿ����!");
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
			logger.error("�ϲ�������ϸ��Ϣ���ݿ����!");
		}
		return result;
	}
}
