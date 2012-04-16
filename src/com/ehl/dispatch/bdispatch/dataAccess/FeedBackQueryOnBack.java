package com.ehl.dispatch.bdispatch.dataAccess;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
/**
 * @����: ldq
 * @�汾�ţ�1.0
 * @����˵�����ӷ�����,�¼���������ӱ��л�ȡ����������¼��Ϣ
 * @������
 * @���أ�
 * @�������ڣ�2009-04-09
 * @�޸��ߣ�
 * @�޸����ڣ�
 */
public class FeedBackQueryOnBack {
	private static final Logger logger = Logger.getLogger(FeedBackQueryOnBack.class);

	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵�����ӷ������л�ȡ����������¼��Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public static String getMesOnFeedBack(String FEEDBACKID,String eventType){
		StringBuffer strXml = new StringBuffer("");
		try {
			/**
			 * ˳�򣺱������,��������ʱ��,��������,��������,
			 * ������λ,������,�¼�״̬
			 * ����ʱ��,�����ֳ�ʱ��
			 * ������ʧ,��������,��������,��������,ץ������,
			 * �永����,��������,�Ƿ�������,�Ƿ�鴦�ΰ�����,�Ƿ����ս�
			 * �Ƿ��ƻ����°���,Ӱ���·,������Χ,���ϳ���λ,˾������,
			 */ 
			String strSql = "SELECT BACK.ALARMID,TO_CHAR(BACK.FINISHTIME,'YYYY-MM-DD HH24:MI') FINISHTIME, BACK.COMEOUTCAR,BACK.COMEOUTCOUNT," +
					" F_GET_DEPT(BACK.COMEOUTUNIT) COMEOUTUNIT,BACK.COMEOUTPERSON,BACK.STATE," +
					" TO_CHAR(BACK.COMEOUTTIME,'YYYY-MM-DD HH24:MI'),TO_CHAR(BACK.COMEOUTARRIVETIME,'YYYY-MM-DD HH24:MI'),FEEDBACKDESC," +
					" BACK.ECONOMYLOSS,BACK.FLESHWOUNDPERSONCOUNT,BACK.GBHPERSONCOUNT,BACK.DEATHPERSONCOUNT,BACK.GRAPPLEPERSONCOUNT,"+
					" BACK.DEALWITHPERSONCOUNT,BACK.SALVATIONPERSONCOUNT,BACK.ISRESOLVEDISSENSION,BACK.ISCHECKORDERCASE,BACK.ISFEEDBACKEND,"+
					" BACK.ISUNCOVERCRIMINALCASE,BACK.AFFECTROAD,BACK.AFFECTEXTENT,BACK.EXCEPTIONCARUNIT,BACK.DRIVERNAME"+
					" FROM T_ATTEMPER_FEEDBACK BACK WHERE BACK.FEEDBACKID='" + FEEDBACKID + "'";
			System.out.println("feedback detail strsql="+strSql);
			Object[] feedback = DBHandler.getLineResult(strSql);
			
			if(feedback != null){
				strXml.append( "<row id='FeedBack'>\n");
				strXml.append( "<col id='ALARMID_FEED'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='ajjssj'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='cdcl'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='cjrs'>" + StringHelper.obj2str(feedback[3],"") + "</col>");
				strXml.append( "<col id='cjdw'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='cjr'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "<col id='sjzt'>" + StringHelper.obj2str(feedback[6],"") + "</col>");
				strXml.append( "<col id='cjsj'>" + StringHelper.obj2str(feedback[7],"") + "</col>");
				strXml.append( "<col id='djsj'>" + StringHelper.obj2str(feedback[8],"") + "</col>");
				strXml.append( "<col id='cljg'>" + StringHelper.obj2str(feedback[9],"") + "</col>");
				
				strXml.append( "<col id='jjss'>" + StringHelper.obj2str(feedback[10],"") + "</col>");
				strXml.append( "<col id='qsrs'>" + StringHelper.obj2str(feedback[11],"") + "</col>");
				strXml.append( "<col id='zsrs'>" + StringHelper.obj2str(feedback[12],"") + "</col>");
				strXml.append( "<col id='swrs'>" + StringHelper.obj2str(feedback[13],"") + "</col>");
				strXml.append( "<col id='sars'>" + StringHelper.obj2str(feedback[14],"") + "</col>");
				strXml.append( "<col id='zhrs'>" + StringHelper.obj2str(feedback[15],"") + "</col>");
				strXml.append( "<col id='jzrs'>" + StringHelper.obj2str(feedback[16],"") + "</col>");
				strXml.append( "<col id='jjjf'>" + StringHelper.obj2str(feedback[17],"") + "</col>");
				strXml.append( "<col id='cczaaj'>" + StringHelper.obj2str(feedback[18],"") + "</col>");
				strXml.append( "<col id='fkzj'>" + StringHelper.obj2str(feedback[19],"") + "</col>");
				strXml.append( "<col id='phxsaj'>" + StringHelper.obj2str(feedback[20],"") + "</col>");
				strXml.append( "<col id='yxdl'>" + StringHelper.obj2str(feedback[21],"") + "</col>");
				strXml.append( "<col id='bjfw'>" + StringHelper.obj2str(feedback[22],"") + "</col>");
				strXml.append( "<col id='gzcdw_Exception'>" + StringHelper.obj2str(feedback[23],"") + "</col>");
				strXml.append( "<col id='sjxm_Exception'>" + StringHelper.obj2str(feedback[24],"") + "</col>");
				
				strXml.append( "</row>\n");
				System.out.println("strxmlaaaa=="+strXml);
			}else{
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.error("�ӷ������л�ȡ����������¼��Ϣ���ݿ����!");
		}
		return strXml.toString();
	}
	
	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵������ָ�ӵ����¼������ȡ�¼������Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public static String getMesOnAlarm(String alarmId){StringBuffer strXml = new StringBuffer("");
		try {
			/**
			 * ˳�򣺰�������,������ַ,�¹ʵȼ�,
			 * �Ƿ��������Ӿ����鲢,�¼���Դ
			 */ 
			String strSql = "SELECT ALARM.EVENTTYPE,ALARM.ALARMADDRESS,ALARM.EVENTLEVEL," +
				" ALARM.MERGEDSTATE,ALARM.EVENTSOURCE," +
				" ALARM.TITLE,to_char(ALARM.ALARMTIME,'yyyy-mm-dd hh24:mi')"+
				" FROM T_ATTEMPER_ALARM ALARM WHERE ALARM.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(strSql);
			if(feedback != null){
				strXml.append( "<row id='Alarm'>\n");
				strXml.append( "<col id='ajlx_feedBack'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='alarmState_feedBack'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='sgdj'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='jdhb'>" + StringHelper.obj2str(feedback[3],"") + "</col>");
				strXml.append( "<col id='sjly'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='eventtile'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[6],"") + "</col>");
				strXml.append( "</row>\n");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.error("��ָ�ӵ����¼������ȡ�¼������Ϣ���ݿ����!");
		}
		
		return strXml.toString();
	}
	
	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵������ָ�ӵ����¼���ͨ�¹��ӱ��ȡ��ͨ�¹������Ϣ
	 * @������alarmId �������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public String getMesOnAccident(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		try {
			/**
			 * ˳�� ������ʼʱ��
			 */ 
			String strSql = "SELECT TO_CHAR(ACCIDENT.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI') ALARMTIME " +
			" FROM T_ATTEMPER_ACCIDENT ACCIDENT WHERE ACCIDENT.ALARMID='" + alarmId + "'" ;
			Object feedback = DBHandler.getSingleResult(strSql);
			if(feedback != null){
				strXml.append( "<row id='Accident'>\n");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback,"") + "</col>");
				strXml.append( "</row>\n");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.error("��ָ�ӵ����¼���ͨ�¹��ӱ��ȡ��ͨ�¹������Ϣ���ݿ����!");
		}
		return strXml.toString();
	}
	
	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵������ָ�ӵ����¼���ͨӵ���ӱ��ȡ��ͨ�¹������Ϣ
	 * @������alarmId �������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public String getMesOnCongestion(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		try {
			/** ������ʼʱ��
			 */ 
			String strSql = "SELECT TO_CHAR(CONGESTION.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI') ALARMTIME" +
				" FROM T_ATTEMPER_CONGESTION CONGESTION WHERE CONGESTION.ALARMID='" 
				+ alarmId + "'" ;
			Object feedback = DBHandler.getSingleResult(strSql);
			if(feedback != null){
				strXml.append( "<row id='Congestion'>\n");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback,"") + "</col>");
				strXml.append( "</row>\n");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.error("��ָ�ӵ����¼���ͨӵ���ӱ��ȡ��ͨ�¹������Ϣ���ݿ����!");
		}
		return strXml.toString();
	}
	
	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵������ָ�ӵ����¼����͹��ϳ��ӱ��ȡ��ͨ�¹������Ϣ
	 * @������alarmId �������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public String getMesOnExceptionCar(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		/**
		 * ˳�򣺰�����ʼʱ��
		 */ 
		try{
			String sql = "SELECT TO_CHAR(EXCEPTIONCAR.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI')" +
					" FROM T_ATTEMPER_EXCEPTIONCAR EXCEPTIONCAR WHERE EXCEPTIONCAR.ALARMID='" + alarmId + "'" ;
			Object feedback = DBHandler.getSingleResult(sql);
			if(feedback != null){
				strXml.append( "<row id='ExceptionCar'>\n");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback,"") + "</col>");
				strXml.append( "</row>\n");
			}
	} catch (Exception e) {
		System.err.println(e.getMessage());
		logger.error("��ָ�ӵ����¼����͹��ϳ��ӱ��ȡ��ͨ�¹������Ϣ���ݿ����!");
	}
		return strXml.toString();
	}
	
	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵������ָ�ӵ����¼����ɲ��س����ӱ��ȡ��ͨ�¹������Ϣ
	 * @������alarmId �������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public String getMesOnBlackList(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		/**
		 * ˳�򣺾�������
		 */ 
		try{
			String sql = "SELECT BLACKLIST.SALVATIONPERSONCOUNT" +
					" FROM T_ATTEMPER_BLACKLIST BLACKLIST WHERE BLACKLIST.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
			if(feedback != null){
				strXml.append( "<row id='BlackList'>\n");
				strXml.append( "<col id='jzrs_BlackList'>" + StringHelper.obj2str(feedback[13],"") + "</col>");
				strXml.append( "</row>\n");
			}
	} catch (Exception e) {
		System.err.println(e.getMessage());
		logger.error("��ָ�ӵ����¼����ɲ��س����ӱ��ȡ��ͨ�¹������Ϣ���ݿ����!");
	}
		return strXml.toString();
	}
	
	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵������ָ�ӵ����¼��ֺ����������ӱ��ȡ��ͨ�¹������Ϣ
	 * @������alarmId �������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public String getMesOnWeather(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		/**
		 * ˳��·��״����Ӱ���·��
		 * ������ʼʱ��
		 */ 
		try{
			String sql = "SELECT WEATHER.ROADSITUATION,WEATHER.AFFECTROAD,TO_CHAR(WEATHER.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI')" +
					" FROM T_ATTEMPER_WEATHER WEATHER WHERE WEATHER.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
			if(feedback != null){
				strXml.append( "<row id='Weather'>\n");
				strXml.append( "<col id='lmqk_Weather'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='yxdl_Weather'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "</row>\n");
			}
	} catch (Exception e) {
		System.err.println(e.getMessage());
		logger.error("��ָ�ӵ����¼��ֺ����������ӱ��ȡ��ͨ�¹������Ϣ���ݿ����!");
	}
		return strXml.toString();
	}
	
	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵������ָ�ӵ����¼��ΰ��¼��ӱ��ȡ��ͨ�¹������Ϣ
	 * @������alarmId �������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public String getMesOnPoliceEvent(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		/**
		 * ˳��Ӱ���·��������Χ��
		 * ������ʼʱ��
		 */ 
		try{
			String sql = "SELECT POLICEEVENT.AFFECTROAD,POLICEEVENT.AFFECTEXTENT," +
					" TO_CHAR(POLICEEVENT.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI')" +
					" FROM T_ATTEMPER_POLICEEVENT POLICEEVENT WHERE POLICEEVENT.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
			if(feedback != null){
				strXml.append( "<row id='PoliceEvent'>\n");
				strXml.append( "<col id='yxdl_PoliceEvent'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='bjfw_PoliceEvent'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "</row>\n");
			}
	} catch (Exception e) {
		System.err.println(e.getMessage());
		logger.error("��ָ�ӵ����¼��ΰ��¼��ӱ��ȡ��ͨ�¹������Ϣ���ݿ����!");
	}
		return strXml.toString();
	}
	
	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵������ָ�ӵ����¼����֡���ը��Ϣ�ӱ��ȡ��ͨ�¹������Ϣ
	 * @������alarmId �������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public String getMesOnFireAndBlast(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		try {
			/**
			 * ˳�򣺰�����ʼʱ��
			 * ������ʧ,��������,��������,
			 * ��������,Ӱ���·,
			 */ 
			String strSql = "SELECT TO_CHAR(FIREANDBLAST.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI') ALARMTIME," +
				" FIREANDBLAST.ECONOMYLOSS,FIREANDBLAST.FLESHWOUNDPERSONCOUNT,FIREANDBLAST.GBHPERSONCOUNT," +
				" FIREANDBLAST.DEATHPERSONCOUNT,FIREANDBLAST.AFFECTROAD" +
				" FROM T_ATTEMPER_FIREANDBLAST FIREANDBLAST WHERE FIREANDBLAST.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(strSql);
			if(feedback != null){
				strXml.append( "<row id='FireAndBlast'>\n");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='jjss_FireAndBlast'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='qsrs_FireAndBlast'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "<col id='zsrs_FireAndBlast'>" + StringHelper.obj2str(feedback[3],"") + "</col>");
				strXml.append( "<col id='swrs_FireAndBlast'>" + StringHelper.obj2str(feedback[4],"") + "</col>");
				strXml.append( "<col id='yxdl_FireAndBlast'>" + StringHelper.obj2str(feedback[5],"") + "</col>");
				strXml.append( "</row>\n");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			logger.error("��ָ�ӵ����¼����֡���ը��Ϣ�ӱ��ȡ��ͨ�¹������Ϣ���ݿ����!");
		}
		return strXml.toString();
	}
	
	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵������ָ�ӵ����¼������ֺ��ӱ��ȡ��ͨ�¹������Ϣ
	 * @������alarmId �������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public String getMesOnGeoLogicDisaster(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		/**
		 * ˳��Ӱ���·��������Χ��
		 * ������ʼʱ��
		 */ 
		try{
			String sql = "SELECT GEOLOGICDISASTER.AFFECTROAD,GEOLOGICDISASTER.AFFECTEXTENT," +
					" TO_CHAR(GEOLOGICDISASTER.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI')" +
					" FROM T_ATTEMPER_GEOLOGICDISASTER GEOLOGICDISASTER WHERE GEOLOGICDISASTER.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
			if(feedback != null){
				strXml.append( "<row id='GeoLogicDisaster'>\n");
				strXml.append( "<col id='yxdl_GeoLogicDisaster'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='bjfw_GeoLogicDisaster'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "</row>\n");
			}
	} catch (Exception e) {
		System.err.println(e.getMessage());
		logger.error("��ָ�ӵ����¼������ֺ��ӱ��ȡ��ͨ�¹������Ϣ���ݿ����!");
	}
		return strXml.toString();
	}
	
	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵������ָ�ӵ����¼������¼��ӱ��ȡ��ͨ�¹������Ϣ
	 * @������alarmId �������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public String getMesOnTownPlan(String alarmId){
		StringBuffer strXml = new StringBuffer("");
		/**
		 * ˳��Ӱ���·��������Χ��
		 * ������ʼʱ��
		 */ 
		try{
			String sql = "SELECT TOWNPLAN.AFFECTROAD,TOWNPLAN.AFFECTEXTENT," +
					" TO_CHAR(TOWNPLAN.CASEHAPPENTIME,'YYYY-MM-DD HH24:MI')" +
					" FROM T_ATTEMPER_TOWNPLAN TOWNPLAN WHERE TOWNPLAN.ALARMID='" + alarmId + "'" ;
			Object[] feedback = DBHandler.getLineResult(sql);
			if(feedback != null){
				strXml.append( "<row id='TownPlan'>\n");
				strXml.append( "<col id='yxdl_TownPlan'>" + StringHelper.obj2str(feedback[0],"") + "</col>");
				strXml.append( "<col id='bjfw_TownPlan'>" + StringHelper.obj2str(feedback[1],"") + "</col>");
				strXml.append( "<col id='ajfssj'>" + StringHelper.obj2str(feedback[2],"") + "</col>");
				strXml.append( "</row>\n");
			}
	} catch (Exception e) {
		System.err.println(getClass().getName() + ":" + e.getMessage());
		logger.error("��ָ�ӵ����¼������¼��ӱ��ȡ��ͨ�¹������Ϣ���ݿ����!");
	}
		return strXml.toString();
	}
}
