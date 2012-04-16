package com.ehl.dynamicinfo.policeRemind.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.appframe.common.Setting;
import com.ehl.dispatch.cdispatch.core.AlarmCore;
import com.ehl.dispatch.cdispatch.core.CrowdRemindCore;
import com.ehl.dispatch.cdispatch.dao.AlarmDao;
import com.ehl.dispatch.duty.core.DutyCore;
import com.ehl.dynamicinfo.policeRemind.dao.PoliceRemindDao;
import com.ehl.sm.base.Constant;
import com.ehl.tira.util.XML;
import com.ehl.util.Collections;
import com.ehl.util.Json;

/**
 * ������ʾҵ���߼���
 * 
 * @author xiayouxue
 * 
 */
public class PoliceRemindCore {

	/** ��־�� */
	private Logger logger = Logger.getLogger(this.getClass());

	/** �������� */
	private String oname = "remind";

	/** ������ʾ���ݷ����� */
	private PoliceRemindDao dao = new PoliceRemindDao();

	/** ӵ�½�����ʾҵ���߼��� */
	private CrowdRemindCore crcore = new CrowdRemindCore();

	/** ����ҵ���߼��� */
	private AlarmCore alarmCore = new AlarmCore();

	/** ֵ��ҵ���߼��� */
	private DutyCore dutyCore = new DutyCore();
	/**
	 * ��ȡ������Ϣ
	 * 
	 * @return xml��ʽ��������
	 */
	public String getRollInfo(String ptype) {
		Object[][] objects = dao.getRollInfo(ptype);
		String xml = XML.getNodes(oname, dao.getRollInfoFields(), objects);
		xml = XML.getXML(xml);
		return xml;
	}

	/**
	 * <pre>
	 * ��ȡ��ϸ��Ϣ
	 * �����ӵ�£����ȡ�����Ϣ�ĳ�ʼƴ������+ӵ����ʾ��¼
	 * �������������ֱ�ӻ�ȡ������Ϣ�ĵ�ǰ״̬ƴ������
	 * </pre>
	 * 
	 * @param alarmId
	 *            ������
	 * @return xml��ʽ��ϸ��Ϣ
	 */
	public String getDetailInfo(String alarmId) {
		String xml = "";
		Object[][] objects = dao.getByAlarmId(alarmId);
		if (objects != null && objects.length > 0) {
			Object[] alarm = alarmCore.getDao().getById(alarmId);
			String alarmXml = XML.getNode("alarm", alarmCore.getDao()
					.getCnames(), alarm, null);
			Object[] object = objects[objects.length - 1];
			if (!alarm[1].equals(AlarmDao.ACC_CODE)) {
				object = objects[0];
				xml = crcore.getStringByAlarmId(alarmId);
			}
			xml = XML.getNode(oname, dao.getCnames(), object, alarmXml
					+ Constant.nvl(xml));
		}
		xml = XML.getXML(xml);
		return xml;
	}

	public String getById(String remindId) {
		Object[] object = dao.getById(remindId);
		String xml = XML.getNode(oname, dao.getCnames(), object, null);
		xml = XML.getXML(xml);
		return xml;
	}

	/**
	 * ��ȡ�˹�������Ϣ
	 * 
	 * @param source
	 *            ��Դ����
	 * @param id
	 *            ������ʾ����Ż��߾�����
	 * @param jgid
	 *            �������
	 * @return JSON������Ϣ
	 */
	public JSONObject getCorrectInfo(String source, String id, String jgid) {
		JSONObject object = new JSONObject();
		Object[][] objects = dao.getBySource(source, id);
		JSONArray reminds = Json.toJson(objects, dao.getCnames());
		object.put("remind", reminds);
		if (source.equals("2")) {
			object.put("alarm", alarmCore.getById(id));
			object.put("phone", dutyCore.getPhoneByJgid(jgid));
			object.put("xb", crcore.getByAlarmId(id));
		}
		return object;
	}
	
	/**
	 * ��ȡ��Ϣ��ͨ��������
	 * @param alarmId  ������
	 * @return xml��ʽ����
	 */
	public String getStringByAlarmId(String alarmId){
		Object[][] objects = dao.getByAlarmId(alarmId);
		String xml = XML.getNodes(oname, dao.getCnames(), objects);
		return xml;
	}
	
	/**
	 * ��ȡ��Ϣ��ͨ��������
	 * 
	 * @param alarmId
	 *            ������
	 * @return JSON������Ϣ
	 */
	public JSONObject getByAlarmId(String alarmId) {
		Object[][] objects = dao.getByAlarmId(alarmId);
		JSONArray reminds = Json.toJson(objects, dao.getCnames());
		JSONArray xbs = crcore.getByAlarmId(alarmId);
		JSONObject object = new JSONObject();
		object.put("reminds", reminds);
		object.put("xb", xbs);
		return object;
	}

	public String getByExample(Map<String, String> object) {
		Object[][] objects = dao.getByExample(object);
		String xml = XML.getNodes(oname, dao.getCnames(), objects);
		xml = XML.getXML(xml);
		return xml;
	}
	
	public String insert(Map<String, String> object) {
		return dao.insert(object);
	}

	/**
	 * <pre>
	 * ��������
	 * 1.�ʱ���ڷ�����ֹʱ���ڣ���������Ĭ�ϲ�����
	 * 2.�ʱ���ڷ�����ֹʱ���⣬Ĭ�Ϸ�����������
	 * 3.���Ҫ������飬��������Ӧ��Ϊ��ʷ���������ܺͣ�ȡΨһ��
	 * </pre>
	 * 
	 * @param rtime
	 *            �ʱ��
	 * @return �������Դ���ֵ
	 * @throws ParseException
	 */
	public String getModifyPublishType(String rtime) throws ParseException {
		String ptype = "";
		String stime = Setting.getString("policeremind_publish_stime");
		String etime = Setting.getString("policeremind_publish_etime");
		SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
		Calendar crtime = Calendar.getInstance();
		crtime.setTime(formater.parse(rtime));
		Calendar cstime = (Calendar) crtime.clone();
		cstime.setTime(formater.parse(stime));
		Calendar cetime = (Calendar) crtime.clone();
		cetime.setTime(formater.parse(etime));
		if (crtime.before(cstime) || crtime.after(cetime)) {
			ptype = "1";
		}
		return ptype;
	}
	
	/**
	 * <pre>
	 * ����������ʾ��Ϣ
	 * ��ӻ���֧������ӵ����Ϣʱ��
	 * �Զ����� �Ľ�����ʾ��Ϣ�����ܶӽ��з���
	 * </pre>
	 * @param id ������ʾ��Ϣ�������
	 * @param ptype ��������
	 * @return �Ƿ񷢲��ɹ�
	 */
	public boolean publish(String id,String ptype, String remindinfo){
		Map<String,String> object = new HashMap<String,String>();
		object.put("remindid", id);
		object.put("state","2");
		object.put("publishtype", ptype);
		object.put("remindinfo", remindinfo);
		return dao.modifyById(object);
	}
	
	/**
	 * <pre>
	 * ͬ��������ʾ��Ϣ
	 * ���ܶӷ����˽�����ʾ��Ϣ֮���ٶԸý�����ʾ�����޸�ʱ��
	 * ��Ҫͬ������ɴ�ӻ�֧���ϱ��Ľ�����ʾ��Ϣ��ʹ�䷢��״̬
	 * �ͱ��θ��µķ���״̬һ��
	 * </pre>
	 * @param id ������ʾ��Ϣ�������
	 * @param ptype ��������
	 * @return �Ƿ�ͬ���ɹ�
	 */
	public boolean synch(String id,String ptype){
		Map<String,String> object = new HashMap<String,String>();
		object.put("remindid", id);
		object.put("publishtype", ptype);
		return dao.modifyById(object);
	}

    /**
     * Modified by Liuwx 2012-4-11 14:50:27
     * <pre>
     *     ������Ϣ����״̬�����������������߻�����������ȡ������״̬��
     * </pre>
     * @param params  �����б�
     * @return boolean
     */
    public boolean updatePublishState(Map<String,String> params){
        Map<String,String> object = new HashMap<String, String>();
        object.put("remindid",params.get("remindId"));
        object.put("publishtype",params.get("publishType"));
        return dao.modifyById(object);
    }
	
	public boolean modifyById(Map<String, String> object) {
		return dao.modifyById(object);
	}

	/**
	 * <pre>
	 * ��ȡ�����������
	 * ���λ�������ʱ��Ĭ�Ϸ�������Ϊ��ʷ���������ܺͣ����ظ���
	 * �ܶӽ������ʱ��Ĭ�Ϸ�������ͬ���λ����ͬʱ������������ֵ���и���
	 * </pre>
	 * @param alarmId ������
	 * @return �����������
	 */
	public String getRelievePublishType(String alarmId) {
		Object[][] objects = dao.getByAlarmId(alarmId);
		Set<String> setPtype = new HashSet<String>();
		if (objects != null) {
			for (Object[] object : objects) {
				String strPtypes = (String) object[6];
				if (strPtypes != null) {
					String[] ptypes = strPtypes.split(",");
					for (String ptype : ptypes) {
						setPtype.add(ptype);
					}
				}
			}
		}
		return Collections.join(setPtype.iterator());
	}

	public PoliceRemindDao getDao() {
		return dao;
	}

	public void setDao(PoliceRemindDao dao) {
		this.dao = dao;
	}

	public static void main(String[] args) throws ParseException {
		Set<String> setPtype = new HashSet<String>();
		setPtype.add("1");
		setPtype.add("1");
		System.out.println(setPtype.size());
	}
}
