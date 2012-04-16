package com.ehl.dispatch.bdispatch.ctrl;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.DBFactory;
import com.appframe.data.db.Database;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.bdispatch.business.FeedBackMgr;
import com.ehl.dispatch.bdispatch.util.PoliceTime;
import com.ehl.sm.common.util.StringUtil;

/**
 * @����˵��:�¼�����ҵ������
 * @�����ߣ�ldq
 * @�������� 2009-04-14
 */
public class FeedBackModify extends Controller {
	
	public static final String CJ = "004006"; //���ɾ�
	public static final String DDXC = "004014";//�ɾ����ֳ�
	public static final String CLWB = "004013";//�������
	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵������ͨ�¹��¼�ҵ������
	 * @������
	 * @���أ�
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public ActionForward doEditForAccident (Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String ALARMID = StringHelper.obj2str(request.getParameter("ALARMID"), "");
		if(ALARMID.equals("")){
			out.write("��������������!");
			return null;
		}
		
		String time = StringHelper.obj2str(request.getParameter("time"), "");
		
		String eventType = StringHelper.obj2str(request.getParameter("eventType"), "");
		String FEEDBACKID = StringHelper.obj2str(request.getParameter("FEEDBACKID"), "");
		String fkdw = StringHelper.obj2str(request.getParameter("fkdw"), "");
		String fkr = StringHelper.obj2str(request.getParameter("fkr"), "");
		String cljg = StringHelper.obj2str(request.getParameter("cljg"), "");
		String cjsj = StringHelper.obj2str(request.getParameter("cjsj"), "");
		
		
		String ajfssj = StringHelper.obj2str(request.getParameter("ajfssj"), "");
		String ajjssj = StringHelper.obj2str(request.getParameter("ajjssj"), "");
		String ajlx = StringHelper.obj2str(request.getParameter("ajlx"), "");
		
		String alarmState = StringHelper.obj2str(request.getParameter("alarmState"), "");
		String sgdj = StringHelper.obj2str(request.getParameter("sgdj"), "");
		String cdcl = StringHelper.obj2str(request.getParameter("cdcl"), "");
		String cjrs = StringHelper.obj2str(request.getParameter("cjrs"), "");
		String cjdw = StringHelper.obj2str(request.getParameter("cjdw"), "");
		String cjr = StringHelper.obj2str(request.getParameter("cjr"), "");
		
		String djsj = StringHelper.obj2str(request.getParameter("djsj"), "");
//		String jdhb = StringHelper.obj2str(request.getParameter("jdhb"), "");
		String sjly = StringHelper.obj2str(request.getParameter("sjly"), "");
//		�¼�Ԥ����״̬
		String EVENTSTATE = StringHelper.obj2str(request.getParameter("EVENTSTATE"), "");
//		�¼���ǰ״̬
		String EVENTSTATE_NOW = StringHelper.obj2str(request.getParameter("EVENTSTATE_NOW"), "");
		
		String feedBackSql = "";
		
		/* �������¼��ֶ�*/
		String jjss = StringHelper.obj2str(request.getParameter("jjss"), "");
		String qsrs = StringHelper.obj2str(request.getParameter("qsrs"), "");
		String zsrs = StringHelper.obj2str(request.getParameter("zsrs"), "");
		String swrs = StringHelper.obj2str(request.getParameter("swrs"), "");
		String sars = StringHelper.obj2str(request.getParameter("sars"), "");
		String zhrs = StringHelper.obj2str(request.getParameter("zhrs"), "");
		String jzrs = StringHelper.obj2str(request.getParameter("jzrs"), "");
		/* �Ƿ��ƻ����°���*/
		String phxsaj = StringHelper.obj2str(request.getParameter("phxsaj"), "");
		/*�Ƿ�鴦�ΰ����� */
		String cczaaj = StringHelper.obj2str(request.getParameter("cczaaj"), "");
		/* �Ƿ�������*/
		String jjjf = StringHelper.obj2str(request.getParameter("jjjf"), "");
		/* �Ƿ����ս�*/
		String fkzj = StringHelper.obj2str(request.getParameter("fkzj"), "");
		/*Ӱ���·*/
		String yxdl = StringHelper.obj2str(request.getParameter("yxdl"), "");
		/*������Χ*/
		String bjfw = StringHelper.obj2str(request.getParameter("bjfw"), "");
		/*���ϳ���λ*/
		String gzcdw= StringHelper.obj2str(request.getParameter("gzcdw"), "");
		/*˾������*/
		String sjxm = StringHelper.obj2str(request.getParameter("sjxm"), "");
		/*·��״��*/
		String lmqk = StringHelper.obj2str(request.getParameter("lmqk"), "");
		
//		//-->���͹��ϳ���Ϣ
//		String sjxm_Exception = StringHelper.obj2str(request.getParameter("sjxm_Exception"), "");
//		String gzcdw_Exception = StringHelper.obj2str(request.getParameter("gzcdw_Exception"), "");
//	  //String alarmState_Exception = StringHelper.obj2str(request.getParameter("alarmState_Exception"), "");
//		//���͹��ϳ���Ϣ<--
//		
//		//-->�ֺ�����
//		String yxdl_Weather = StringHelper.obj2str(request.getParameter("yxdl_Weather"), "");
//		String lmqk_Weather = StringHelper.obj2str(request.getParameter("lmqk_Weather"), "");
//		//�ֺ�����<--
//		
//		//-->�ΰ��¼�
//		String yxdl_PoliceEvent = StringHelper.obj2str(request.getParameter("yxdl_PoliceEvent"), "");
//		String bjfw_PoliceEvent = StringHelper.obj2str(request.getParameter("bjfw_PoliceEvent"), "");
//		//�ΰ��¼�<--
//		
//		//--->���ɳ���
//		String jzrs_BlackList =  StringHelper.obj2str(request.getParameter("jzrs_BlackList"), "");
//		//���ɳ���<---
//		
//		//-->�����ֺ�
//		String yxdl_GeoLogicDisaster = StringHelper.obj2str(request.getParameter("yxdl_GeoLogicDisaster"), "");
//		String bjfw_GeoLogicDisaster = StringHelper.obj2str(request.getParameter("bjfw_GeoLogicDisaster"), "");
//		//�����ֺ�<--
//		
//		//-->�����¼�
//		String yxdl_TownPlan = StringHelper.obj2str(request.getParameter("yxdl_TownPlan"), "");
//		String bjfw_TownPlan = StringHelper.obj2str(request.getParameter("bjfw_TownPlan"), "");
		//�����¼�<--
		
		//-->���ֱ�ը
//		String jjss_FireAndBlast = StringHelper.obj2str(request.getParameter("jjss_FireAndBlast"), "");
//		String qsrs_FireAndBlast = StringHelper.obj2str(request.getParameter("qsrs_FireAndBlast"), "");
//		String zsrs_FireAndBlast = StringHelper.obj2str(request.getParameter("zsrs_FireAndBlast"), "");
//		String swrs_FireAndBlast = StringHelper.obj2str(request.getParameter("swrs_FireAndBlast"), "");
//		String yxdl_FireAndBlast = StringHelper.obj2str(request.getParameter("yxdl_FireAndBlast"), "");
//		
		//���·�����
		if(!EVENTSTATE.equals(CLWB)){
			if(FEEDBACKID.equals("")){
				FEEDBACKID=StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS");
				feedBackSql = " INSERT INTO T_ATTEMPER_FEEDBACK (FEEDBACKID,ALARMID,FEEDBACKTIME,FEEDBACKUNIT,FEEDBACKPERSON,FEEDBACKDESC," +
				" COMEOUTUNIT,COMEOUTPERSON,COMEOUTTIME,COMEOUTARRIVETIME,COMEOUTCAR,COMEOUTCOUNT,STATE," 
				+ "ECONOMYLOSS,FLESHWOUNDPERSONCOUNT,GBHPERSONCOUNT,DEATHPERSONCOUNT,GRAPPLEPERSONCOUNT,DEALWITHPERSONCOUNT,SALVATIONPERSONCOUNT,"
				+ " ISRESOLVEDISSENSION,ISCHECKORDERCASE,ISFEEDBACKEND,ISUNCOVERCRIMINALCASE,AFFECTROAD,AFFECTEXTENT,EXCEPTIONCARUNIT,DRIVERNAME,ROADSITUATION,FINISHTIME)"
				+ " VALUES ('" + FEEDBACKID + "','"+ ALARMID + "',TO_DATE('" + time +"','YYYY-MM-DD HH24:MI'),'"+ fkdw +"','" + fkr +"','" 
				+ cljg +"','" + cjdw +"','"+ cjr + "',TO_DATE('" + cjsj +"','YYYY-MM-DD HH24:MI'),TO_DATE('" + djsj +"','YYYY-MM-DD HH24:MI'),'"
				+ cdcl +"','" + cjrs +"','" + EVENTSTATE +"',"
				+ "'"+jjss+"','"+qsrs+"','"+zsrs+"','"+swrs+"','"+sars+"','"+zhrs+"','"+jzrs+"','"+jjjf+"','"+cczaaj+"','"+fkzj+"',"
				+"'"+phxsaj+"','"+yxdl+"','"+bjfw+"','"+gzcdw+"','"+sjxm+"','"+lmqk+"',TO_DATE('" + ajjssj +"','YYYY-MM-DD HH24:MI'))";
				
			}else{
				feedBackSql = "UPDATE T_ATTEMPER_FEEDBACK SET FEEDBACKTIME=TO_DATE('" + time +"','YYYY-MM-DD HH24:MI'),FEEDBACKUNIT='"+ fkdw +"'," +
						" FEEDBACKPERSON='" + fkr +"',FEEDBACKDESC='" + cljg +"',COMEOUTUNIT='"+ cjdw +"',COMEOUTPERSON='"+ cjr + "'," +
						" COMEOUTTIME=TO_DATE('" + cjsj +"','YYYY-MM-DD HH24:MI'),COMEOUTARRIVETIME=TO_DATE('" + djsj +"','YYYY-MM-DD HH24:MI')," +
						" COMEOUTCAR='"+ cdcl +"',COMEOUTCOUNT='" + cjrs +"',STATE='" + EVENTSTATE +"'," +
						" ECONOMYLOSS='"+jjss+"',FLESHWOUNDPERSONCOUNT='"+qsrs+"',GBHPERSONCOUNT='"+zsrs+"',DEATHPERSONCOUNT='"+swrs+"',"+
						" GRAPPLEPERSONCOUNT='"+sars+"',DEALWITHPERSONCOUNT='"+zhrs+"',SALVATIONPERSONCOUNT='"+jzrs+"',ISRESOLVEDISSENSION='"+jjjf+"',"+
						" ISCHECKORDERCASE='"+cczaaj+"',ISFEEDBACKEND='"+fkzj+"',ISUNCOVERCRIMINALCASE='"+phxsaj+"',AFFECTROAD='"+yxdl+"',"+
						" AFFECTEXTENT='"+bjfw+"',EXCEPTIONCARUNIT='"+gzcdw+"',DRIVERNAME='"+sjxm+"',ROADSITUATION='"+lmqk+"',"+
						" FINISHTIME=TO_DATE('" + ajjssj +"','YYYY-MM-DD HH24:MI') WHERE FEEDBACKID='" + FEEDBACKID + "'";
			}			
		}else{
			
		}
		System.out.println("feedBackSql="+feedBackSql);
		String alarmSql = "";
		String complateSql = "";
		if(EVENTSTATE.equals(CLWB)){
			//�޸ķ�������¼����з���״̬Ϊ�������
			complateSql = "UPDATE T_ATTEMPER_FEEDBACK SET STATE='" + EVENTSTATE +"' WHERE ALARMID='" + ALARMID + "'";
//			����������,�������з����ĳ�������,����������д�뱨������
			String strCdcl = "",strCjrs = "",strCjr = "";
			String getFeedBackSql = "SELECT T1.COMEOUTPERSON,T1.COMEOUTCAR," +
					" (SELECT SUM(T2.COMEOUTCOUNT) FROM T_ATTEMPER_FEEDBACK T2  WHERE T2.ALARMID='" + ALARMID + "') " +
					" FROM T_ATTEMPER_FEEDBACK T1 WHERE T1.ALARMID='" + ALARMID + "'";
			Object[][] feedback = DBHandler.getMultiResult(getFeedBackSql);
			
			if(feedback != null){
				strCjrs = StringHelper.obj2str(feedback[0][2], "");
				for(int i=0;i<feedback.length;i++){
					if(feedback[i][0] != null){
						strCjr += StringHelper.obj2str(feedback[i][0]) + ",";
					}
					if(feedback[i][1] != null){
						strCdcl += StringHelper.obj2str(feedback[i][1]) + ",";
					}
				}
				if(!strCdcl.equals("")){
					strCdcl = strCdcl.substring(0, strCdcl.lastIndexOf(","));
				}
				if(!strCjr.equals("")){
					strCjr = strCjr.substring(0, strCjr.lastIndexOf(","));
				}
			}
			alarmSql = "UPDATE T_ATTEMPER_ALARM ALARM SET  ALARM.FINISHTIME=TO_DATE('" + ajjssj +"','YYYY-MM-DD HH24:MI')," +
			" ALARM.EVENTTYPE='" + ajlx + "'," ;
			if(!alarmState.equals("")){
				alarmSql += " ALARM.ALARMADDRESS='"+alarmState+"',";//�����ص�
			}
			if(!sgdj.equals("")){
				alarmSql += " ALARM.EVENTLEVEL='" + sgdj + "',";//�¹ʵȼ�
			}
			alarmSql += " ALARM.COMEOUTCAR='" + strCdcl + "',ALARM.COMEOUTCOUNT='"+ strCjrs + "'," ;
			alarmSql += " ALARM.COMEOUTPERSON='" + strCjr + "',ALARM.EVENTSTATE='" + EVENTSTATE + "'";
			alarmSql += " WHERE ALARM.ALARMID='" + ALARMID + "'" ;

		}else{
			//���±�������
			alarmSql = "UPDATE T_ATTEMPER_ALARM ALARM SET  ALARM.EVENTTYPE='" + ajlx + "'" ;
			if(!alarmState.equals("")){
				alarmSql += " ,ALARM.ALARMADDRESS='"+alarmState+"'";//�����ص�
			}
			if(!sgdj.equals("")){
				alarmSql += " ,ALARM.EVENTLEVEL='" + sgdj + "'";//�¹ʵȼ�
			}
			
			if(StringHelper.obj2int(EVENTSTATE_NOW,0)<StringHelper.obj2int(CJ,0)){
				alarmSql += " ,ALARM.EVENTSTATE='" + EVENTSTATE + "'";//�¼�״̬
			}else if(isUpdate(EVENTSTATE,EVENTSTATE_NOW)){
				alarmSql += " ,ALARM.EVENTSTATE='" + EVENTSTATE + "'";//�¼�״̬
			}
			alarmSql += " WHERE ALARM.ALARMID='" + ALARMID + "'" ;
		}
		
		
		
		//-->���ֱ�ը
		
		String eventSql = "select sysdate from dual";
		if(eventType.equals("")){
			
		}else{
			int type = StringHelper.obj2int(eventType.substring(3, 6),0);
			switch (type) {
				case 1:
					//��ͨ�¹�
					eventSql = "UPDATE T_ATTEMPER_ACCIDENT ACCIDENT SET ACCIDENT.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI'),ACCIDENT.ECONOMYLOSS='"+ jjss +"',ACCIDENT.FLESHWOUNDPERSONCOUNT='" + qsrs + "'," +
					" ACCIDENT.GBHPERSONCOUNT='" + zsrs + "',ACCIDENT.DEATHPERSONCOUNT='" + swrs + "',ACCIDENT.DEALWITHPERSONCOUNT='" + sars + "'," +
					" ACCIDENT.GRAPPLEPERSONCOUNT='" + zhrs +"',ACCIDENT.SALVATIONPERSONCOUNT='" + jzrs +"',ACCIDENT.ISUNCOVERCRIMINALCASE='" + phxsaj + "'," +
					" ACCIDENT.ISCHECKORDERCASE='" + cczaaj + "',ACCIDENT.ISRESOLVEDISSENSION='" + jjjf + "',ACCIDENT.ISFEEDBACKEND='"+ fkzj+"'" +
					" WHERE ACCIDENT.ALARMID='"+ALARMID+"'" ;
					break;
				case 2:
					//��ͨӵ��
					eventSql = "UPDATE T_ATTEMPER_CONGESTION CONGESTION SET CONGESTION.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI'),CONGESTION.ECONOMYLOSS='"+ jjss +"',CONGESTION.FLESHWOUNDPERSONCOUNT='" + qsrs + "'," +
					" CONGESTION.GBHPERSONCOUNT='" + zsrs + "',CONGESTION.DEATHPERSONCOUNT='" + swrs + "',CONGESTION.DEALWITHPERSONCOUNT='" + sars + "'," +
					" CONGESTION.GRAPPLEPERSONCOUNT='" + zhrs +"',CONGESTION.SALVATIONPERSONCOUNT='" + jzrs +"',CONGESTION.ISUNCOVERCRIMINALCASE='" + phxsaj + "'," +
					" CONGESTION.ISCHECKORDERCASE='" + cczaaj + "',CONGESTION.ISRESOLVEDISSENSION='" + jjjf + "',CONGESTION.ISFEEDBACKEND='"+ fkzj+"'" +
					" WHERE CONGESTION.ALARMID='"+ALARMID+"'" ;
					break;
				case 5:
					//���ɳ���
					eventSql = "UPDATE T_ATTEMPER_BLACKLIST BLACKLIST SET BLACKLIST.SALVATIONPERSONCOUNT=' " + jzrs +
					"' WHERE BLACKLIST.ALARMID='"+ALARMID+"'" ;
					break;
				case 6:
					//�ֺ�����
					eventSql = "UPDATE T_ATTEMPER_WEATHER WEATHER SET WEATHER.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI')," +
					" WEATHER.ROADSITUATION='" + lmqk + "'," +
					" WEATHER.AFFECTROAD='" + yxdl + "' WHERE WEATHER.ALARMID='"+ALARMID+"'" ;
					break;
				case 7:
					//�ΰ��¼�
					eventSql = "UPDATE T_ATTEMPER_POLICEEVENT POLICEEVENT SET POLICEEVENT.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI')," +
					" POLICEEVENT.AFFECTEXTENT='" + bjfw + "'," +
					" POLICEEVENT.AFFECTROAD='" + yxdl+ "' WHERE POLICEEVENT.ALARMID='"+ALARMID+"'" ;
					break;
				case 8:
					//���ͳ�����
					eventSql = "UPDATE T_ATTEMPER_EXCEPTIONCAR EXCEPTIONCAR SET EXCEPTIONCAR.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI')," +
					" EXCEPTIONCAR.DriverName='" + sjxm + "'," +
					" EXCEPTIONCAR.ExceptionCarUnit='" + gzcdw + "' WHERE EXCEPTIONCAR.ALARMID='"+ALARMID+"'" ;
					break;
				case 10:
					//�����ֺ�<��·������������ʯ��>
					eventSql = "UPDATE T_ATTEMPER_GEOLOGICDISASTER GEOLOGICDISASTER SET GEOLOGICDISASTER.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI')," +
					" GEOLOGICDISASTER.AFFECTEXTENT='" + bjfw + "'," +
					" GEOLOGICDISASTER.AFFECTROAD='" + yxdl + "' WHERE GEOLOGICDISASTER.ALARMID='"+ALARMID+"'" ;
					break;
				case 11:
					//�����¼�<ú��������й¶������ˮ��ˮ��ͣ��>
					eventSql = "UPDATE T_ATTEMPER_TOWNPLAN TOWNPLAN SET TOWNPLAN.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI')," +
					" TOWNPLAN.AFFECTEXTENT='" + bjfw + "'," +
					" TOWNPLAN.AFFECTROAD='" + yxdl + "' WHERE TOWNPLAN.ALARMID='"+ALARMID+"'" ;
					break;
				case 12:
					//���ֱ�ը
					eventSql = "UPDATE T_ATTEMPER_FIREANDBLAST FIREANDBLAST SET FIREANDBLAST.CASEHAPPENTIME = TO_DATE('" + ajfssj +"','YYYY-MM-DD HH24:MI')," +
					" FIREANDBLAST.ECONOMYLOSS='"+ jjss +"',FIREANDBLAST.FLESHWOUNDPERSONCOUNT='" + qsrs + "'," +
					" FIREANDBLAST.GBHPERSONCOUNT='" + zsrs + "',FIREANDBLAST.DEATHPERSONCOUNT='" + swrs + "', " +
					" FIREANDBLAST.AFFECTROAD='" + yxdl + "'WHERE FIREANDBLAST.ALARMID='"+ALARMID+"'" ;
					break;
				default:
					eventSql = "select sysdate from dual";
				break;
			}
		}
		
		ArrayList<String> list = new ArrayList<String>(); 
		if(!feedBackSql.equals("")){
			list.add(feedBackSql); 
		}
		list.add(alarmSql); 
		list.add(eventSql); 
		if(!complateSql.equals("")){
			list.add(complateSql);
		}
		System.out.println("feedBackSql="+feedBackSql);
		System.out.println("eventSql="+eventSql);
		System.out.println("alarmSql="+alarmSql);
		//ִ�и��²���
		boolean isOk = excuteSql(list);
		if(isOk){
			out.write("T");
			
			PoliceTime policeTime=new PoliceTime();
			String longLatsql="select x||','||y from t_attemper_alarm where  alarmid='"+ALARMID+"'";
			String longLat=DBHandler.getSingleResult(longLatsql).toString();
			String car=cdcl.split(";")[0];
			if(!longLat.equals(",")){
				policeTime.stop();
				policeTime.start(car, longLat, FEEDBACKID);
			}
			if(isUpdate(EVENTSTATE,EVENTSTATE_NOW)){
				FeedBackMgr.SendFeedBackMsg(ALARMID,sjly,ajlx,fkdw,EVENTSTATE,time);
				if(sjly.equals("002001")){
					//������̨��
					new FeedBackMgr().SendFeedBackMsg(ALARMID,"0");
				}
			}
		}else{
			out.write("F");
		}
		out.close();
		return null;
	}
	
	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵�����ж��¼�Ԥ����״̬�����Ƿ�����¼���ǰ״̬
	 * @������alarmState:�¼�Ԥ����״̬��alarmState_now���¼���ǰ״̬
	 * @���أ�false/true
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public static boolean isUpdate(String eventState ,String eventState_now){
		boolean isOk = false;
//		�����ǰ�¼�״̬Ϊ��,�����¼�״̬���ɾ����ֳ��Ҳ����ڵ�ǰ�¼�״̬д�뱨������
		if(StringHelper.obj2int(eventState_now,0) < StringHelper.obj2int(CJ,0)){
			isOk = true;
		}else if(!eventState.equals(eventState_now) && (eventState.equals(DDXC)||eventState.equals(CLWB))){
			isOk = true;
		}
		return isOk;
	}
	/**
	 * @����: ldq
	 * @�汾�ţ�1.0
	 * @����˵������ͨ�¹��¼�ҵ����
	 * @������feedBackSql:���·�����eventSql�������¼��ӱ�alarmSql�����±�������
	 * @���أ�false or true
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public static boolean excuteSql(ArrayList list){
//		��ʼ����
		boolean bOk = true;
		if(list == null){
			return bOk;
		}
		Database db = null;
		
		try {
			db = DBFactory.newDatabase();
			db.beginTrans();
			
			String sql = "";
			for(int i=0;i<list.size();i++){
				sql = StringHelper.obj2str(list.get(i),"");
				if(!sql.equals("") && 0 <db.executeUpdate(sql)){
				}else{
					bOk = false;
					break;
				}
			}
		} catch(Exception e) {
			bOk = false;
			System.out.println(e.getMessage());
		}finally {
			if(null != db) {
				if(bOk) {
					db.commitTrans(false);
				} else {
					db.commitTrans(true);
				}
				//�ر����ݶ���
				DBFactory.closeDatabase(db);
			}
		}
		return bOk;
	}
}