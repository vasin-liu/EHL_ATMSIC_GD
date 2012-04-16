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
	
	/**事故代码值*/
	public final static String ACC_CODE = "001024";
	/**拥堵代码值*/
	public final static String CROWD_CODE = "001002";
	/**施工占道代码值*/
	public final static String BUILD_CODE = "001023";
	/**事故描述值*/
	public final static String ACC_DESC = "交通事故";
	/**拥堵描述值*/
	public final static String CROWD_DESC = "交通拥堵";
	/**施工占道描述值*/
	public final static String BUILD_DESC = "道路施工";
	/**代码和描述的关系*/
	public final static Map<String, String> codeToDesc;
	static {
		codeToDesc = new HashMap<String, String>();
		codeToDesc.put(ACC_CODE, ACC_DESC);
		codeToDesc.put(CROWD_CODE, CROWD_DESC);
		codeToDesc.put(BUILD_CODE, BUILD_DESC);
	}
	
	/** 日志类 */
	private Logger logger = Logger.getLogger(this.getClass());

	/** 实体对象名 */
	private String ename = "警情信息";

	/** 表名 */
	private String tname = "T_ATTEMPER_ALARM";

	/** 表别名 */
	private String otname = "alarm";

	/** 列名 */
	private String[] cnames = {"alarmid", "eventtype", "eventstate",
			"caseendtime", "casehappentime","reportperson", "reportunit", "alarmregion","gzcs"};

	/** 主键列名 */
	private String pk = "alarmid";

	/** 日期类型列名 */
	private String[] dcnames = {"caseendtime","casehappentime"};

	/** 序列名 */
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
