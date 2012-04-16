package com.ehl.dispatch.cdispatch.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.BaseDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

/**
 * ӵ���������ݷ�����
 * 
 * @author xiayx
 *
 */
public class CrowdRemindDao extends BaseDao {

	/** ��־�� */
	private Logger logger = Logger.getLogger(this.getClass());

	/** ʵ������� */
	private String ename = "ӵ������";

	/** ���� */
	private String tname = "T_OA_CROWD_REMINDINFO";
	
	/** ����� */
	private String otname = "crowdremind";

	/** ���� */
	private String[] cnames = { "id", "alarmid", "remindinfo","renewal", "reminddate",
			"reminddepartment", "remindperson" };
	
	/** ������������ */
	private String[] dcnames = { "reminddate" };

	/** ������ */
	private String sname = "SEQ_CROWD_REMINDINFO";
	
	public CrowdRemindDao(){
		super.setLogger(logger);
		super.setEname(ename);
		super.setTname(tname);
		super.setOtname(otname);
		super.setCnames(cnames);
		super.setDcnames(dcnames);
		super.setSname(sname);
	}

	
	@Override
	public String[] getFields() {
		return cnames.clone();
	}

	@Override
	public String getSelect() {
		String[] fileds = getFields();
		fileds[4] = Sql.toChar(fileds[4], 4);
		return Array.join(fileds, ",");
	}
	
	public Object[][] getByAlarmId(String alarmId){
		String _tname = tname + " " + otname;
		String sql = Sql.select(_tname, getSelect(), "alarmid='" + alarmId + "'", null, "id");
		String msg = "��ȡ" + ename + "ͨ���������";
		return FlowUtil.readMilte(sql, logger, msg);
	}
	
	public static Map<String, String> formCrowdRemind(String ALARMID,
			String reminddate, String ALARMREGION, String REPORTPERSON,
			String remindInfo, String renewal) {
		Map<String,String> crowdRemind = new HashMap<String, String>();
		crowdRemind.put("alarmid", ALARMID);
		crowdRemind.put("remindinfo", remindInfo);
		crowdRemind.put("renewal", renewal);
		crowdRemind.put("reminddate", reminddate);
		crowdRemind.put("reminddepartment", ALARMREGION);
		crowdRemind.put("remindperson", REPORTPERSON);
		return crowdRemind;
	}

}
