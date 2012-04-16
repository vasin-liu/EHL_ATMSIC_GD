package com.ehl.dynamicinfo.policeRemind.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.BaseDao;
import com.ehl.dispatch.cdispatch.dao.AlarmDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

/**
 * ������ʾ���ݷ�����
 * 
 * <b>
 * <pre>
 * ע�⣺
 * ����ʱ����������Ž�������
 * ����δ��ȷ���룬��������׼ȷ
 * </pre>
 * </b>
 * 
 * @author xiayx
 *
 */
public class PoliceRemindDao extends BaseDao {

	/** ��־�� */
	private Logger logger = Logger.getLogger(this.getClass());

	/** ʵ������� */
	private String ename = "������ʾ";

	/** ���� */
	private String tname = "T_OA_POLICE_REMIND";
	
	/** ����� */
	private String otname = "remind";

	/** ���� */
	private String[] cnames = {"remindid", "remindtime", "departmentid",
			"departmentname", "remindinfo",  "state", "publishtype",
			"username", "source", "alarmid", "pid"};
	
	/** �������� */
    private String pk = "remindid";

	/** ������������ */
	private String[] dcnames = { "remindtime" };

	/** ������ */
	private String sname = "SEQ_T_OA_POLICE_REMIND";
	
	public PoliceRemindDao(){
		super.setLogger(logger);
		super.setEname(ename);
		super.setTname(tname);
		super.setOtname(otname);
		super.setCnames(cnames);
		super.setDcnames(dcnames);
		super.setSname(sname);
		super.setPk(pk);
	}

	
	@Override
	public String[] getFields() {
		return cnames.clone();
	}

	@Override
	public String getSelect() {
		String[] fileds = getFields();
		fileds[1] = Sql.toChar(fileds[1], 4);
		return Array.join(fileds, ",");
	}
	
	@Override
	public String insert(Map<String, String> object) {
		String id = object.get(pk);
		if(!object.containsKey(pk)){
			id = getId();
			object.put(pk, id);
		}
		if(!object.containsKey("pid")){
			object.put("pid", id);
		}
		if(!object.containsKey("remindtime")){
			object.put("remindtime", null);
		}
		return super.insert(object);
	}


	public Object[][] getByExample(Map<String,String> object){
		StringBuffer sift = new StringBuffer("1=1");
		if(object.containsKey("remindtime")){
			sift.append(" and " + Sql.siftDate("remindtime", object.get("remindtime")));
		}
		String _tname = tname + " " + otname;
		String sql = Sql.select(_tname, getSelect(), sift.toString(), null,"remindtime desc");
		String msg = "���ݶ���ʵ����ȡ" + ename + "��Ϣ";
		return FlowUtil.readMilte(sql, logger, msg);
	}
	
	public Object[][] getByAlarmId(String alarmId){
		String sift = "alarmid='" + alarmId + "'";
		String _tname = tname + " " + otname;
		String sql = Sql.select(_tname, getSelect(), sift, null,"remindid");
		String msg = "���ݾ����Ż�ȡ" + ename + "��Ϣ";
		return FlowUtil.readMilte(sql, logger, msg);
	}
	
	public Object[][] getBySource(String source, String id) {
		String cname = source.equals("1") ? "pid" : "alarmid";
		String sift = cname + "='" + id + "'";
		String _tname = tname + " " + otname;
		String sql = Sql.select(_tname, getSelect(), sift, null,"remindtime");
		String msg = "���ݲ�ͬ�Ľ�����ʾ��Դ��ȡ" + ename + "��Ϣ";
		return FlowUtil.readMilte(sql, logger, msg);
	}

	public String[] getRollInfoFields(){
		String[] fields = getFields();
		return Array.insert(fields, fields.length, "zbdh1");
	}
	
	public Object[][] getRollInfo(String ptype){
		StringBuffer sqlRn = new StringBuffer();
		sqlRn.append("select t.*")
		//�˴���������������������ֶ���������
		.append(",ROW_NUMBER() over(partition by decode(t.source, '1', t.pid, '2', t.alarmid) order by t.remindid desc) rn");
		sqlRn.append(" from " + tname + " t");
		//ѡ��ӵ����Ϣ
		StringBuffer siftCrowd = new StringBuffer();
		siftCrowd.append(" select alarm.alarmid");
		siftCrowd.append(" from t_attemper_alarm alarm");
		siftCrowd.append(" where alarm.alarmid=t.alarmid");
		siftCrowd.append(" and alarm.eventtype = '"+AlarmDao.CROWD_CODE+"'");
		//ɸѡ���ܶ���·�涯ֱ̬�ӷ������߾�������Ϊӵ�µ���Ϣ
		String sourceSift = "(source='1' or (t.source='2' and exists("+siftCrowd+")))";
		sqlRn.append(" where "+ sourceSift)
		.append(" and instr(t.publishtype,'"+ptype+"')!=0")
		.append(" and trunc(t.remindtime,'dd')=trunc(sysdate,'dd')" );
		StringBuffer sql = new StringBuffer();
		sql.append("select " + getSelect())
		.append(",(select zbdh1 from t_sys_department where t.departmentid=jgid)");
		sql.append(" from ("+sqlRn+") t");
		sql.append(" where t.rn=1");
		sql.append(" order by t.remindid desc");
		String msg = "��ȡ����������ʾ��Ϣ";
		return FlowUtil.readMilte(sql.toString(), logger, msg);
	}
	
	public static Map<String, String> formPoliceRemind(String alarmId,
			String REPORTTIME, String REPORTUNIT, String ALARMREGION, String REPORTPERSON,
			String state, String publishType, String remindInfo, String source) {
		Map<String,String> remindMap = new HashMap<String, String>();
		remindMap.put("remindtime", REPORTTIME);
		remindMap.put("departmentid", REPORTUNIT);
		remindMap.put("departmentname", ALARMREGION);
		remindMap.put("remindinfo", remindInfo);
		remindMap.put("publishtype", publishType);
		remindMap.put("username", REPORTPERSON);
		remindMap.put("source", source);
		remindMap.put("alarmid", alarmId);
		remindMap.put("state", state);
		return remindMap;
	}
	
	public static void main(String[] args) {
//		PoliceRemindDao dao = new PoliceRemindDao();
	}
	
}
