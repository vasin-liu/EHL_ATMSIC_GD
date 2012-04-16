package com.ehl.dispatch.cdispatch.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.appframe.data.DBFactory;
import com.appframe.data.db.Database;
import com.appframe.data.sql.DBHandler;
/**
 * 
 * @author wxt
 * 
 *
 */
public class TrafficContrlData {
	
	private static final Logger logger = Logger.getLogger(TrafficContrlData.class);
	//插入施工占道信息
    public boolean insertTrafficRoadInfo(Object[] res) throws Throwable {
    	// 插入指挥调度事件记录表
		//String inalarmsql = insertMainAlarmInfo(res);
		String inalarmsql = null;
		String sonsql = null;
		String alarmid = res[0].toString();
		int count = isSameRoadBuild(alarmid);
		if(count==0) {
			inalarmsql =  insertMainAlarmInfo(res);
			sonsql = insertTrafficRoadInfoSql(res);
		}
		else {
			inalarmsql =  editMainAlarmInfo(res);
			sonsql = editTrafficRoadInfoSql(res);
		}
		System.out.println("插入施工占道信息 sql>>>>"+sonsql);
		return executeData(inalarmsql, sonsql);
    }
    /**
     * 编辑施工占道主表SQL
     * @param res
     * @return
     */
    public String editMainAlarmInfo(Object[] res) {
    	String inalarmsql = "";
    	
    	inalarmsql += "update T_Attemper_Alarm" ;
    	inalarmsql += " set AlarmTime=to_date('" + res[1]+ "','yyyy-mm-dd hh24:mi'),";
    	inalarmsql += " ALARMDESC='"+res[2]+"',";
    	inalarmsql += " EVENTSTATE='"+res[3]+"',";
    	inalarmsql += " REPORTPERSON='"+res[4]+"',";
    	inalarmsql += " ALARMREGION='1',EVENTSOURCE='1',EVENTTYPE='001008',ALARMUNIT='1',TITLE='1',ALARMREGIONID='1'";
    	inalarmsql += " where alarmid='"+res[0]+"'";
		System.out.println("maininalarmsql==="+inalarmsql);
		return inalarmsql;
    	
    }
    /**
     * 编辑施工占道SQL
     * @param res
     * @return
     */
    public String editTrafficRoadInfoSql(Object[] res){
    	String sonsql = "insert into T_ATTEMPER_ROADBUILD(ALARMID,ROID,ROADBH,PROPOSER,PRINCIPAL,PHONE_P,ROADSECTION,AFTRANGE,PERMITTYPE,STARTTIME,ENDTIME,PLAN";
		sonsql += ")";
		sonsql = "update T_ATTEMPER_ROADBUILD set ROADBH='"+res[8]+"',";
		sonsql +=" PROPOSER='"+res[5]+"',PRINCIPAL='"+res[6]+"',PHONE_P='"+res[7]+"',";
		sonsql +=" ROADSECTION='"+res[9]+"',AFTRANGE='"+res[10]+"',PERMITTYPE='"+res[11]+"',";
		sonsql +=" STARTTIME=to_date('" + res[12] + "','yyyy-mm-dd hh24:mi'),";
		sonsql +=" ENDTIME=to_date('" + res[13] + "','yyyy-mm-dd hh24:mi'),";
		sonsql +=" PLAN='1'";
		sonsql += " where roid='"+res[0]+"'";
		return sonsql;
    }
    /**
     * 插入施工占道SQL
     * @param res
     * @return
     */
    public String insertTrafficRoadInfoSql(Object[] res) {
    	// 插入子表
    	String sonsql = "insert into T_ATTEMPER_ROADBUILD(ALARMID,ROID,ROADBH,PROPOSER,PRINCIPAL,PHONE_P,ROADSECTION,AFTRANGE,PERMITTYPE,STARTTIME,ENDTIME,PLAN";
		sonsql += ")";
		sonsql += " values('" + res[0] + "',";
		//sonsql += "to_date('" + res[24] + "','yyyy-mm-dd hh24:mi'),";
		sonsql += " '"+res[0] +"','"+res[8]+"','"+res[5]+"','"+res[6]+"','"+res[7]+"','"+res[9]+"','"+res[10]+"',";
		sonsql +=" '"+res[11]+"',";
		sonsql += "to_date('" + res[12] + "','yyyy-mm-dd hh24:mi'),";
		sonsql += "to_date('" + res[13] + "','yyyy-mm-dd hh24:mi'),";
		sonsql += "'1'";
		sonsql += ")";
		return sonsql;
    }
    
    /**
     * 插入施工占道主表SQL
     * @param res
     * @return
     * @throws Throwable
     */
    public String insertMainAlarmInfo(Object[] res) throws Throwable {
    	String inalarmsql = "";
		//String deptid = res[14].toString();
		//String SUPERUNIT = deptid.substring(0, 6) + StringUtil.fill("0", deptid.length()-6); //所属机构
		// String title=res[1]+res[13]+res[19]+res[18];
		//add by wangxt 090515 增加一个SUBEVENTSOURCE字段
		
		//add by LDQ 091216 增加[ALARMREGION-报警辖区],[ALARMREGIONID-报警辖区id],[SUPERUNIT-所属单位，对应事件上报机构(如果是支队)或其所属支队id]字段
		
    	inalarmsql += "insert into T_Attemper_Alarm" +
				"(AlarmID,AlarmTime,ALARMDESC,EVENTSTATE,REPORTPERSON,ALARMREGION,EVENTSOURCE,EVENTTYPE,ALARMUNIT,TITLE,ALARMREGIONID)";
		inalarmsql += " values('" + res[0] + "',";
		inalarmsql += "to_date('" + res[1]+ "','yyyy-mm-dd hh24:mi'),";
		inalarmsql +=  "'" + res[2] + "','" + res[3] + "','"+ res[4] +"','1','1','001008','1','1','1')";
		System.out.println("maininalarmsql==="+inalarmsql);
		return inalarmsql;
    }
 
    /**
	 * 插入数据事务操作
	 */
	public boolean executeData(String mainSql, String sonSql) throws Throwable {
		boolean msg = false;
		Database db = null;
		try {
			db = DBFactory.newDatabase();
			db.beginTrans();
			int in = db.executeUpdate(mainSql);
			int conestion = 0;
			if(!sonSql.equals("")){
				conestion = db.executeUpdate(sonSql);
			}
			if (in >= 0 && conestion >= 0) {
				db.commitTrans(false);
				msg = true;
			} else {
				db.commitTrans(true);
			}
		} catch (Exception e) {
			throw new RuntimeException();
		} finally {
			DBFactory.closeDatabase(db);
		}

		return msg;
	}
  
  /**
   * 编辑施工占道字表信息
   */
  public String editTrafficRoadSql(Object[] res) {
	  String insertsql = null;
	   try {
		   //道路编号
	      // String roadid = CreateSequence.getMaxForId("T_ATTEMPER_TTATBL", "roid", 12);
		   //填报时间
	       String reporttime = res[0].toString();
	       //填保人
		   String reportpeople = res[1].toString();
		   //占道单位
		   String roadunit = res[2].toString();
		   //负责人
		   String principal = res[3].toString();
		   //联系电话
		   String telphone = res[4].toString();
		   //占用道路
		   String addressdes = res[5].toString();
		   //相关路段
		   String transactor = res[6].toString();
		   //占用情况
		   String eventstate = res[7].toString();
		   //准行情况
		   String possstate = res[8].toString();
		   
		   //占道开始时间
		   String roadstarttime = res[9].toString();
		   //占道结束时间
		   String roadendtime = res[10].toString();
		   //警情状态
		   String alarmstate = res[11].toString();
		   //事件描述
		   String eventdes = res[12].toString();
		   String sonsql = "insert into T_ATTEMPER_ROADBUILD(ALARMID,ROID,ROADBH,PROPOSER,PRINCIPAL,PHONE_P,ROADSECTION,AFTRANGE,PERMITTYPE,STARTTIME,ENDTIME,PLAN";
		   insertsql = "update T_ATTEMPER_ROADBUILD set ROID='"+reporttime+"',ROADBH='"+reportpeople+"'";
		   insertsql += ",PROPOSER='"+roadunit+"',PRINCIPAL='"+principal+"',PHONE_P='"+telphone+"'";
		   insertsql += "ROADSECTION = '"+transactor+"',AFTRANGE='"+possstate+"',PERMITTYPE='"+possstate+"',STARTTIME='"+roadstarttime+"',ENDTIME='"+roadendtime+"',PLAN='1'";
	   }catch(Exception e) {
		   e.printStackTrace();
		   logger.error("");
	   }
	   return insertsql;
  }
  
  /**
   * 删除施工占道信息
   * @param roadid
   * @return
   */
  public boolean deleteTrafficRoadInfo(String roadid) {
	   boolean flag = false;
	   try {
		   String delsql = "delete from T_ATTEMPER_ROADBUILD where roadid='"+roadid+"'";
		   flag = DBHandler.execute(delsql);
	   }catch(Exception e) {
		   e.printStackTrace();
	   }
	   return flag;
  }
  
  /**
	 * 
	 * @param alarmid
	 * @return
	 */
	public int isSameRoadBuild(String alarmid) {
		int num  = 0;
		try {
			String sql = "select count(*) from T_ATTEMPER_ROADBUILD where roid='"+alarmid+"'";
			String count = DBHandler.getSingleResult(sql).toString();
		    num = Integer.parseInt(count);
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		return num;
	}
	
	/**
	 * 
	 * @param alarmid
	 * @return
	 */
	public int isSameTrafficContrl(String alarmid) {
		int num  = 0;
		try {
			String sql = "select count(*) from T_ATTEMPER_TRAFFICRESTRAIN where roid='"+alarmid+"'";
			String count = DBHandler.getSingleResult(sql).toString();
		    num = Integer.parseInt(count);
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		return num;
	}
	/**
	 * 编辑交通管制
	 * @param res
	 * @return
	 * @throws Throwable
	 */
	public boolean editTrafficContrl(HashMap res) throws Throwable {
		String inalarmsql = null;
		String sonsql = null;
        
		String alarmid = res.get("alarmId_TrafficRestrain").toString();
		int count = isSameTrafficContrl(alarmid);
		if(count==0) {
			inalarmsql =  insertTrafficContrlMainSql(res);
			sonsql = insertTrafficContrlSql(res);
		}
		else {
			inalarmsql = editTrafficContrlMainSql(res);
			sonsql = editTrafficContrlSql(res);
		}
		System.out.println("插入交通管制信息主表 sql>>>>"+inalarmsql);
		System.out.println("插入交通管制信息子表 sql>>>>"+sonsql);
		return executeData(inalarmsql, sonsql);
    }
		
	
	/**
	 * 插入交通管制SQL
	 * @param res
	 * @return
	 */
	public String insertTrafficContrlSql(HashMap res) {
		String sql = null;
		try {
			String alarmId_TrafficRestrain = res.get("alarmId_TrafficRestrain").toString();
			//填报时间
			String alarmTime_TrafficRestrain = res.get("alarmTime_TrafficRestrain").toString();
			//上报单位
			String alarmDept_TrafficRestrain = res.get("alarmDept_TrafficRestrain").toString();
			//填报人
			String reportPerson_TrafficRestrain = res.get("reportPerson_TrafficRestrain").toString();
			//填报单位
			String reportDept_TrafficRestrain =  res.get("reportDept_TrafficRestrain").toString();
			//联系电话
			String telpone_TrafficRestrain = res.get("telpone_TrafficRestrain").toString();
			//管制原因
			String reson_TrafficRestrain = res.get("reson_TrafficRestrain").toString();
			//事发路段
			String accsection_TrafficRestrain = res.get("accsection_TrafficRestrain").toString();
			//公里
			String mvalue_TrafficRestrain = res.get("mvalue_TrafficRestrain").toString();
			//管制地点
			String address_TrafficRestrain =res.get("address_TrafficRestrain").toString();
			//管制方向
			String direction_TrafficRestrain_td = res.get("direction_TrafficRestrain").toString();
			//管制类型
			String type_TrafficRestrain =res.get("type_TrafficRestrain").toString();
			//管制开始时间
			String starttime_TrafficRestrain = res.get("starttime_TrafficRestrain").toString();
			//管制结束时间
			String endtime_TrafficRestrain = res.get("endtime_TrafficRestrain").toString();
			//备注
			String desc_TrafficRestrain = res.get("desc_TrafficRestrain").toString();
			sql = "insert into T_ATTEMPER_TRAFFICRESTRAIN(ROID,PHONE,ARARMID,REASON,CONTROLPLACE,DIRECTION,TYPE,REPORTDEPT,STARTTIME,ENDTIME,REMARK)";
			sql +="values('"+alarmId_TrafficRestrain+"','"+telpone_TrafficRestrain+"','"+alarmId_TrafficRestrain+"',";
			sql += "'"+reson_TrafficRestrain+"','"+address_TrafficRestrain+"','"+direction_TrafficRestrain_td+"',";
			sql +="'"+type_TrafficRestrain+"',";
			sql +="'"+alarmDept_TrafficRestrain+"',";
		    sql +="to_date('"+starttime_TrafficRestrain+"','yyyy-mm-dd hh24:mi'),";
		    sql +="to_date('"+endtime_TrafficRestrain+"','yyyy-mm-dd hh24:mi'),";
		    sql +="'"+desc_TrafficRestrain+"')";
		}catch(Exception e){
			e.printStackTrace();
		}
		return sql;
	} 
	/**
	 * 插入交通管制警情主表SQL
	 * @param res
	 * @return
	 */
	public String insertTrafficContrlMainSql(HashMap res) {
		String sql = null;
		try {
		//警情编号
		String alarmId_TrafficRestrain = res.get("alarmId_TrafficRestrain").toString();
		//填报人
		String reportPerson_TrafficRestrain = res.get("reportPerson_TrafficRestrain").toString();
		//填报时间
		String alarmTime_TrafficRestrain = res.get("alarmTime_TrafficRestrain").toString();
		//填报单位
		String reportDept_TrafficRestrain =  res.get("reportDept_TrafficRestrain").toString();
		//事发路段
		String accsection_TrafficRestrain = res.get("accsection_TrafficRestrain").toString();
		String mvalue_TrafficRestrain = res.get("mvalue_TrafficRestrain").toString();
		sql = "insert into T_ATTEMPER_ALARM(ALARMID,REPORTPERSON,REPORTUNIT,ROADID,KMVALUE,EVENTTYPE,REPORTTIME)";
		sql += "values('"+alarmId_TrafficRestrain+"','"+reportPerson_TrafficRestrain+"',";
		sql += "'"+reportDept_TrafficRestrain+"','"+accsection_TrafficRestrain+"','"+mvalue_TrafficRestrain+"','001007',";
		sql +="to_date('"+alarmTime_TrafficRestrain+"','yyyy-mm-dd hh24:mi'))";
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sql;
	}

	
	/**
	 * 插入交通管制警情主表SQL
	 * @param res
	 * @return
	 */
	public String editTrafficContrlMainSql(HashMap res) {
		String sql = null;
		try {
		//警情编号
		String alarmId_TrafficRestrain = res.get("alarmId_TrafficRestrain").toString();
		//填报人
		String reportPerson_TrafficRestrain = res.get("reportPerson_TrafficRestrain").toString();
		//填报时间
		String alarmTime_TrafficRestrain = res.get("alarmTime_TrafficRestrain").toString();
		//填报单位
		String reportDept_TrafficRestrain =  res.get("reportDept_TrafficRestrain").toString();
		//事发路段
		String accsection_TrafficRestrain = res.get("accsection_TrafficRestrain").toString();
		//公里
		String mvalue_TrafficRestrain = res.get("mvalue_TrafficRestrain").toString();
		
		sql = "update T_ATTEMPER_ALARM";
		sql +=" set REPORTPERSON='"+reportPerson_TrafficRestrain+"',";
		sql +=" REPORTUNIT='"+reportDept_TrafficRestrain+"',";
		sql +=" ROADID='"+accsection_TrafficRestrain+"',";
		sql +=" KMVALUE='"+mvalue_TrafficRestrain+"',";
		//sql += "REPORTTIME = '"+alarmTime_TrafficRestrain+"'";
		sql +="REPORTTIME=to_date('"+alarmTime_TrafficRestrain+"','yyyy-mm-dd hh24:mi')";
		sql +=" where ALARMID='"+alarmId_TrafficRestrain+"'";
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return sql;
	}
	
	
	/**
	 * 编辑交通管制SQL
	 * @param res
	 * @return
	 */
	public String editTrafficContrlSql(HashMap res) {
		String sql = null;
		try {
			String alarmId_TrafficRestrain = res.get("alarmId_TrafficRestrain").toString();
			
			//上报单位
			String alarmDept_TrafficRestrain = res.get("alarmDept_TrafficRestrain").toString();
			//填报人
			String reportPerson_TrafficRestrain = res.get("reportPerson_TrafficRestrain").toString();
			//填报单位
			String reportDept_TrafficRestrain =  res.get("reportDept_TrafficRestrain").toString();
			//联系电话
			String telpone_TrafficRestrain = res.get("telpone_TrafficRestrain").toString();
			//管制原因
			String reson_TrafficRestrain = res.get("reson_TrafficRestrain").toString();
			//事发路段
			String accsection_TrafficRestrain = res.get("accsection_TrafficRestrain").toString();
			//公里
			String mvalue_TrafficRestrain = res.get("mvalue_TrafficRestrain").toString();
			//管制地点
			String address_TrafficRestrain =res.get("address_TrafficRestrain").toString();
			//管制方向
			String direction_TrafficRestrain_td = res.get("direction_TrafficRestrain").toString();
			//管制类型
			String type_TrafficRestrain =res.get("type_TrafficRestrain").toString();
			//管制开始时间
			String starttime_TrafficRestrain = res.get("starttime_TrafficRestrain").toString();
			//管制结束时间
			String endtime_TrafficRestrain = res.get("endtime_TrafficRestrain").toString();
			//备注
			String desc_TrafficRestrain = res.get("desc_TrafficRestrain").toString();
			sql ="update T_ATTEMPER_TRAFFICRESTRAIN";
		    sql +=" set PHONE='"+telpone_TrafficRestrain+"',REASON='"+reson_TrafficRestrain+"',";
		    sql +=" CONTROLPLACE='"+address_TrafficRestrain+"',DIRECTION='"+direction_TrafficRestrain_td+"',";
		    sql +=" TYPE='"+type_TrafficRestrain+"',REPORTDEPT='"+alarmDept_TrafficRestrain+"',";
		    sql +=" STARTTIME=to_date('"+starttime_TrafficRestrain+"','yyyy-mm-dd hh24:mi'),";
		    sql +=" ENDTIME=to_date('"+endtime_TrafficRestrain+"','yyyy-mm-dd hh24:mi'),";
		    sql +=" REMARK='"+desc_TrafficRestrain+"'";
		    sql +=" where roid='"+alarmId_TrafficRestrain+"'";
		}catch(Exception e){
			e.printStackTrace();
		}
		return sql;
	} 
	/**
	 * 根据警情编号查询交通管制详细信息
	 * @param alarmid
	 * @return
	 */
	public Object[] getTrafficContrl(String alarmid) {
		Object[] res = null;
		try {
		//String sql = "select ROID,STARTTIME,ENDTIME,ARARMID,CONTROLPLACE,DIRECTION,REASON,TYPE,REPORTDEPT,REMARK from T_ATTEMPER_TRAFFICRESTRAIN";
	   
		String sql = "select taa.alarmid,to_char(taa.REPORTTIME,'YYYY-MM-DD HH24:MI'),taa.REPORTUNIT,taa.REPORTPERSON,";
		sql += " tat.REPORTDEPT,tat.PHONE,tat.REASON,taa.ROADID,taa.KMVALUE,tat.CONTROLPLACE,";
		sql += " tat.DIRECTION,tat.TYPE,to_char(tat.STARTTIME,'YYYY-MM-DD HH24:MI'),to_char(tat.ENDTIME,'YYYY-MM-DD HH24:MI'),tat.REMARK";
		sql +=" from T_ATTEMPER_ALARM taa,T_ATTEMPER_TRAFFICRESTRAIN tat";
		sql +=" where 1=1 and taa.alarmid=tat.ROID";
		sql +=" and alarmid='"+alarmid+"'";
	    res = DBHandler.getLineResult(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}
	
	
	/**
	 * 根据警情编号查询施工占道详细信息
	 * @param alarmid
	 * @return
	 */
	public Object[] getTrafficRoadInfo(String alarmid) {
		Object[] res = null;
		try {
		String sql = "select taa.alarmid,to_char(taa.REPORTTIME,'YYYY-MM-DD HH24:MI'),taa.REPORTPERSON,";
		sql += " tat.PROPOSER,tat.PRINCIPAL,tat.PHONE_P,tat.ROADBH,tat.ROADSECTION,tat.AFTRANGE,";
		sql += " to_char(tat.STARTTIME,'YYYY-MM-DD HH24:MI'),to_char(tat.ENDTIME,'YYYY-MM-DD HH24:MI'),tat.PERMITTYPE,tat.TITLE";
		sql +=" from T_ATTEMPER_ALARM taa,T_ATTEMPER_ROADBUILD tat";
		sql +=" where 1=1 and taa.alarmid=tat.ROID";
		sql +=" and taa.alarmid='"+alarmid+"'";
	    res = DBHandler.getLineResult(sql);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return res;
		
	}
	
	/**
	 * 删除交通管制,施工占道信息
	 * @param alarmid
	 * @return
	 * @throws Throwable 
	 */
	public boolean deleteTrafficContrl(String alarmid,String maintalbe,String sontable) throws Throwable {
		boolean flag = false;
		try {
			String mainsql = "delete from '"+maintalbe+"' where alarmid=''"+alarmid+"''";
			String sonsql = "delete from '"+sontable+"' where roid='"+alarmid+"'";
			flag = executeData(mainsql,sonsql);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	

}
