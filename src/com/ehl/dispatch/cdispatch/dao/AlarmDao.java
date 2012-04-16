package com.ehl.dispatch.cdispatch.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.BaseDao;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

public class AlarmDao extends BaseDao {
	
	/**�¹ʴ���ֵ*/
	public final static String ACC_CODE = "001024";
	/**ӵ�´���ֵ*/
	public final static String CROWD_CODE = "001002";
	/**ʩ��ռ������ֵ*/
	public final static String BUILD_CODE = "001023";
	/**�¹�����ֵ*/
	public final static String ACC_DESC = "��ͨ�¹�";
	/**ӵ������ֵ*/
	public final static String CROWD_DESC = "��ͨӵ��";
	/**ʩ��ռ������ֵ*/
	public final static String BUILD_DESC = "��·ʩ��";
	/**����������Ĺ�ϵ*/
	public final static Map<String, String> codeToDesc;
	static {
		codeToDesc = new HashMap<String, String>();
		codeToDesc.put(ACC_CODE, ACC_DESC);
		codeToDesc.put(CROWD_CODE, CROWD_DESC);
		codeToDesc.put(BUILD_CODE, BUILD_DESC);
	}
	
	/** ��־�� */
	private Logger logger = Logger.getLogger(this.getClass());

	/** ʵ������� */
	private String ename = "������Ϣ";

	/** ���� */
	private String tname = "T_ATTEMPER_ALARM";

	/** ����� */
	private String otname = "alarm";

	/** ���� */
	private String[] cnames = {"alarmid", "eventtype", "eventstate",
			"caseendtime", "casehappentime","reportperson", "reportunit", "alarmregion","gzcs"};

	/** �������� */
	private String pk = "alarmid";

	/** ������������ */
	private String[] dcnames = {"caseendtime","casehappentime"};

	/** ������ */
	private String sname = "";

	public AlarmDao() {
		super.setLogger(logger);
		super.setEname(ename);
		super.setTname(tname);
		super.setOtname(otname);
		super.setCnames(cnames);
		super.setDcnames(dcnames);
		super.setSname(sname);
		super.setPk(pk);
	}
	
	public static void main(String[] args) {
		AlarmDao dao = new AlarmDao();
		String id = dao.getId("440101");
		System.out.println(id);
	}

	public String getId(String jgid) {
		String id = jgid.substring(0, 6)
				+ new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		return id;
	}

	@Override
	public String[] getFields() {
		return cnames.clone();
	}

	@Override
	public String getSelect() {
		String[] fileds = getFields();
		fileds[3] = Sql.toChar(fileds[3], 4);
		fileds[4] = Sql.toChar(fileds[4], 4);
		return Array.join(fileds, ",");
	}
	
}
