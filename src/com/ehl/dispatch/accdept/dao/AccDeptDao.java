package com.ehl.dispatch.accdept.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

public class AccDeptDao {
	private Logger logger2 = Logger.getLogger(AccDeptDao.class);

	public final static String tnamePost = "ACCDE";
	public final static String tname = "T_OA_ACCEPTDEPT";
	public final static String otname = "ad";
	public final static String[] cnames = { "id", "rpdcode", "rpname", "rtime",
			"state", "aid", "atype", "mstate", "adid", "ptime" };
	public final static String sname = "SEQ_OA_ACCEPTDEPT";
	public final static String[] states = { "δ�鿴/δǩ��", "�Ѳ鿴/��ǩ��/ǩ�հ�����", "�Ѱ��" };
	public final static String[] atypes = { "�¹�", "ӵ��", "ʩ��ռ��", "Э��ͨ��", "�������ش����","Լ��" };
	public final static String[] mstates = { "����", "�޸�", "ɾ��" };
	public final static int[] promptForm = { 0, 3, 3, 3, 2, 1 };

	
	
	public String[] getSelectFields(){
		String[] fields = cnames.clone();
		return Array.insert(fields, 2, "rpddesc");
	}
	/**
	 * ��ȡ�����������ֶ�<br>
	 * �ڽ����˵�λ�����׷���˽����˵�λ�����ֶΣ�<br>
	 * �����ֶ��������ӳ��ַ���
	 * 
	 * @return
	 */
	public static String getFullCols() {
		return "id,rpdcode,f_get_dept(rpdcode),rpname,"
				+ Sql.toChar("rtime", Sql.getDFFull(5))
				+ ",state,aid,atype,mstate,adid,ptime";
	}

	/**
	 * ��ȡ����IDֵ
	 * 
	 * @return
	 */
	public  String getId() {
		return tnamePost + CreateSequence.getMaxForSeq(sname, 20);
	}

	/**
	 * 
	 * @param map
	 */
	private void changeDate(Map<String, String> map) {
		if (map != null) {
			if (map.containsKey("rtime")) {
				map.put("rtime", Sql
						.toDate(FlowUtil.cancleSQ(map.get("rtime"))));
			}else{
				map.put("rtime", "sysdate");
			}
		}
	}

	/**
	 * ��ӽ��յ�λ��Ϣ rpdcode,aid,atype,mstate,[adid]
	 * 
	 * @param map
	 * @return
	 */
	public boolean add(Map<String, String> map) {
		boolean isOK = false;
		if (map != null) {
			if (!map.containsKey("id")) {
				map.put("id", getId());
			}
			if (!map.containsKey("state")) {
				map.put("state", "1");
			}
			if (!map.containsKey("ptime")) {
				map.put("ptime", "0");
			}
			FlowUtil.encapMapSQ(map);
			changeDate(map);
			String sql = Sql.insert(tname, map);
			isOK = FlowUtil.write(sql, logger2, "��ӽ��յ�λ��Ϣ");
		}
		return isOK;
	}

	/**
	 * ��ӽ��յ�λ��Ϣ rpdcode,aid,atype,[adid]
	 * 
	 * @param map
	 * @return
	 */
	public boolean adds(Map<String, String> map) {
		boolean isOK = false;
		if (map != null) {
			String rpdcode = map.get("rpdcode");
			if (rpdcode != null) {
				String[] rpdcodes = rpdcode.split(",");
				for (int i = 0; i < rpdcodes.length; i++) {
					Map<String, String> temp = FlowUtil.copyMap(map);
					temp.put("rpdcode", rpdcodes[i]);
					isOK = add(temp);
					if (!isOK) {
						break;
					}
				}
			}
		}
		return isOK;
	}

	/**
	 * ��ȡ���յ�λ��Ϣ����Map��ʽ
	 * 
	 * @param id
	 *            ���յ�λ����ID
	 * @return ���յ�λ��Ϣ
	 */
	public Map<String, String> getByIdToMap(String id) {
		Map<String, String> admap = null;
		Object[] adinfo = getById(id);
		if (adinfo != null) {
			admap = new HashMap<String, String>();
			admap.put("aid", adinfo[6].toString());
			admap.put("atype", adinfo[7].toString());
			admap.put("mstate", adinfo[8].toString());
		}
		return admap;
	}

	public String getWhere(String id) {
		String whereId = null;
		if (id != null) {
			whereId = "id='" + id + "'";
		}
		return whereId;
	}

	public String getWhere(String aid, String rpdcode, String adid) {
		String whereStr = null;
		if (aid != null && rpdcode != null) {
			whereStr = "aid='" + aid + "'";
			whereStr += " and rpdcode='" + rpdcode + "'";
			whereStr += " and adid";
			if (adid == null) {
				whereStr += " is null";
			} else if (adid.equals("")) {
				whereStr += " is not null";
			} else {
				whereStr += " = '" + adid + "'";
			}
		}
		return whereStr;
	}

	public String getWhere(Map<String, String> whereMap) {
		String whereStr = null;
		if (whereMap != null) {
			String id = whereMap.get("id");
			if (id != null) {
				whereStr = getWhere(id);
			} else {
				String aid = whereMap.get("aid");
				String rpdcode = whereMap.get("rpdcode");
				String adid = whereMap.get("adid");
				whereStr = getWhere(aid, rpdcode, adid);
			}
		}
		return whereStr;
	}

	/**
	 * �޸Ľ��յ�λ
	 * 
	 * @param setStr
	 * @param whereStr
	 * @return
	 */
	public boolean modify(String setStr, String whereStr) {
		boolean isOK = false;
		if (setStr != null && whereStr != null) {
			String sql = Sql.update(tname, setStr, whereStr);
			System.out.println("SQL��\n" + sql);
			isOK = FlowUtil.write(sql, logger2, "�޸Ľ��յ�λ");
		}
		return isOK;
	}

	/**
	 * �޸Ľ��յ�λ��Ϣ
	 * 
	 * @param setMap
	 * @param whereMap
	 * @return
	 */
	public boolean modify(Map<String, String> setMap, String whereStr) {
		boolean isOK = false;
		if (setMap != null) {
			FlowUtil.encapMapSQ(setMap);
			changeDate(setMap);
			String setStr = Sql.join(setMap, Sql.sepItem);
			isOK = modify(setStr, whereStr);
		}
		return isOK;
	}

	/**
	 * �޸Ľ��յ�λ���Ѵ���
	 * 
	 * @param ptime
	 * @param atype
	 * @param mstate
	 * @return
	 */
	public boolean modifyPtime(String ptime, String atype, String mstate) {
		boolean isOK = false;
		if (ptime != null && atype != null) {
			String setStr = "ptime=ptime+1";
			String whereStr = "rpdcode='" + ptime + "' and atype='" + atype
					+ "'";
			if (mstate != null) {
				whereStr += " and mstate='" + mstate + "'";
			}
			String sql = Sql.update(tname, setStr, whereStr);
			isOK = FlowUtil.write(sql, logger2, "�޸Ľ��յ�λ���Ѵ���");
		}
		return isOK;
	}

	public boolean modifyPtime(String rpdcode, String atype) {
		return modifyPtime(rpdcode, atype, null);
	}

	/**
	 * ���½��յ�λ����������Ϣ״̬
	 * 
	 * @param aid
	 * @param mstate
	 * @return
	 */
	public boolean updateMState(String aid, String mstate, String state) {
		boolean isOK = false;
		if (aid != null && mstate != null) {
			String setStr = "mstate = " + FlowUtil.encapSQ(mstate);
			if (state != null) {
				setStr += ",state = " + FlowUtil.encapSQ(state);
			}
			String whereStr = "aid = " + FlowUtil.encapSQ(aid);
			String sql = Sql.update(tname, setStr, whereStr);
			isOK = FlowUtil.write(sql, logger2, "���½��յ�λ����������Ϣ״̬Ϊ" + mstate);
		}
		return isOK;
	}

	/**
	 * ���½��յ�λ����Ϣ����״̬
	 * 
	 * @param id
	 *            ���յ�λ����ID
	 * @param state
	 *            ����״̬
	 * @param paramMap
	 *            ������˺��ʱ�䣬
	 * @return
	 */
	public boolean updateState(String id, String state,
			Map<String, String> paramMap) {
		boolean isOK = false;
		if (id != null && state != null) {
			FlowUtil.encapMapSQ(paramMap);
			String setStr = "ptime='0',state = " + FlowUtil.encapSQ(state);
			String whereStr;
			if (id.indexOf(",") != -1) {// ͬʱ���¶������
				whereStr = " instr(" + FlowUtil.encapSQ(id) + ",id) != 0";
			} else {
				whereStr = " id= " + FlowUtil.encapSQ(id);
			}
			if (paramMap != null) {// ͬʱ������˺��ʱ��
				if (paramMap.containsKey("rtime")) {
					paramMap.put("rtime", Sql.toDate(FlowUtil
							.cancleSQ(paramMap.get("rtime"))));
				}
				setStr += "," + Sql.join(paramMap, Sql.sepItem);
			}
			String sql = Sql.update(tname, setStr, whereStr);
			isOK = FlowUtil.write(sql, logger2, "���½��յ�λ����������Ϣ״̬Ϊ" + state);
		}
		return isOK;
	}

	/**
	 * ���½��յ�λ��Ϣ
	 * 
	 * @param id
	 * @param state
	 * @return
	 */
	public boolean updateAccDept(String id, int state) {
		boolean isOK = false;
		if (id != null && state >= 0 && state < states.length) {
			String setStr = "state='" + state + "'";
			String whereStr = "id='" + id + "'";
			String sql = Sql.update(tname, setStr, whereStr);
			isOK = FlowUtil
					.write(sql, logger2, "���½��յ�λ��Ϣ������״̬Ϊ" + states[state]);
		}
		return isOK;
	}

	/**
	 * ���Ҿ�����������rpdcode������š�atype�������͡�state����״̬��mstate��Ϣ״̬
	 * 
	 * @param paramsIn
	 * @return
	 */
	public Object[][] searchCount(Map<String, String> paramsIn) {
		Object[][] count = null;
		if (paramsIn != null) {
			FlowUtil.encapMapSQ(paramsIn);
			String whereStr = Sql.join(paramsIn, Sql.sepWhereItem);
			String sql = Sql.select(tname, "mstate,count(id)", whereStr,
					"mstate", "mstate");
			count = FlowUtil.readMilte(sql, logger2, "��ȡ������ʾ����");
		}
		return count;
	}

	/**
	 * <pre>
	 * ��ȡ���յ�λ��Ϣ
	 * 1.ͨ������ID����ȡ���յ�λ����ת����λ��Ϣ
	 * 2.ͨ������ID�����յ�λ�������룬��ȡ���յ�λ��Ϣ
	 * 3.ͨ������ID��ת����λ�������룬��ȡת����λ��Ϣ
	 * 4.ͨ�����յ�λID����ȡ���յ�λ��Ϣ
	 * </pre>
	 * 
	 * @param id
	 *            ����ID
	 * @param pdcode
	 *            ��Ա��������
	 * @param stype
	 *            ��Ϣ��Դ���͡�1�����յ�λ��ת����λ��2�����յ�λ��3��ת����λ�� 0���idΪ���յ�λID��
	 * @return ���յ�λ��Ϣ
	 */
	public Object[][] getAccDept(String id, String pdcode, int stype) {
		Object[][] data = null;
		if (id != null) {
			String colStr = getFullCols();
			String whereStr = null;
			if (stype == 0) {
				whereStr = " id =" + FlowUtil.encapSQ(id);
			} else if (stype >= 1 && stype <= 3) {
				stype -= 2;
				String[] wheres = { "adid is null", "adid is not null" };
				whereStr = "aid=" + FlowUtil.encapSQ(id) + " and "
						+ wheres[stype];
				if (pdcode != null) {
					whereStr += " and rpdcode=" + FlowUtil.encapSQ(pdcode);
				}
			}
			if (whereStr != null) {
				String sql = Sql.select(tname, colStr, whereStr, null,
						"state,id");
				data = FlowUtil.readMilte(sql, logger2, "��ȡ���յ�λ��Ϣ");
			}
		}
		return data;
	}

	/**
	 * <pre>
	 * ��ȡ֧���·����յ�λ��Ϣ
	 * 1.ͨ������ID����ȡ���յ�λ����ת����λ��Ϣ
	 * 2.ͨ������ID�����յ�λ�������룬��ȡ���յ�λ��Ϣ
	 * 3.ͨ������ID��ת����λ�������룬��ȡת����λ��Ϣ
	 * 4.ͨ�����յ�λID����ȡ���յ�λ��Ϣ
	 * </pre>
	 * 
	 * @param id
	 *            ����ID
	 * @param pdcode
	 *            ��Ա��������
	 * @param stype
	 *            ��Ϣ��Դ���͡�1�����յ�λ��ת����λ��2�����յ�λ��3��ת����λ�� 0���idΪ���յ�λID��
	 * @return ���յ�λ��Ϣ
	 */
	public Object[][] getZDAccDept(String id) {
		Object[][] data = null;
		if (id != null) {
			String sql = "select distinct ac.rpdcode from t_attemper_alarm al, t_oa_acceptdept ac "; 
				sql += "where al.eventtype = '001024' and al.alarmid = ac.aid and adid is not null and al.alarmid = '";
				sql += id + "' and adid LIKE '%CONTE%' and state='1' and ac.rpdcode != '440000000000'order by ac.rpdcode";
			System.out.println("getZDAccDept----->" + sql);
			data = DBHandler.getMultiResult(sql);
		}
		return data;
	}
	
	/**
	 * <pre>
	 * ��ȡ�ܶ�ת����λ��Ϣ
	 * 1.ͨ������ID����ȡ���յ�λ����ת����λ��Ϣ
	 * 2.ͨ������ID�����յ�λ�������룬��ȡ���յ�λ��Ϣ
	 * 3.ͨ������ID��ת����λ�������룬��ȡת����λ��Ϣ
	 * 4.ͨ�����յ�λID����ȡ���յ�λ��Ϣ
	 * </pre>
	 * 
	 * @param id
	 *            ����ID
	 * @param pdcode
	 *            ��Ա��������
	 * @param stype
	 *            ��Ϣ��Դ���͡�1�����յ�λ��ת����λ��2�����յ�λ��3��ת����λ�� 0���idΪ���յ�λID��
	 * @return ���յ�λ��Ϣ
	 */
	public Object[][] getZODAccDept(String id) {
		Object[][] data = null;
		if (id != null) {
			String sql = "select distinct ac.rpdcode from t_attemper_alarm al, t_oa_acceptdept ac "; 
				sql += "where al.eventtype = '001024' and al.alarmid = ac.aid and adid is not null and al.alarmid = '";
				sql += id + "' and adid LIKE '%ACCDE%' and state='1' order by ac.rpdcode";
			System.out.println("getZODAccDept----->" + sql);
			data = DBHandler.getMultiResult(sql);
		}
		return data;
	}

	/**
	 * <pre>
	 * ��ȡ���յ�λ����ת����λ
	 * 1.ͨ�����յ�λID����ȡת����λ��Ϣ
	 * 2.ͨ��ת����λID����ȡ���յ�λ��Ϣ
	 * </pre>
	 * 
	 * @param id
	 *            ���յ�λID
	 * @param stype
	 *            ��Ϣ��Դ���͡�2�����յ�λ��3��ת����λ
	 * @return
	 */
	public Object[][] getAccDept(String id, int stype) {
		Object[][] data = null;
		if (id != null && stype >= 2 && stype <= 3) {
			stype -= 2;
			String colStr = getFullCols();
			String[] wheres = { "adid = ", "adid is null and id = " };
			String whereStr = wheres[stype] + FlowUtil.encapSQ(id);
			String sql = Sql.select(tname, colStr, whereStr);
			String msg = "";
			if (stype == 2) {
				msg = "ͨ�����ջ���ID��ȡת��������Ϣ";
			} else if (stype == 3) {
				msg = "ͨ��ת������ADID��ȡ���ջ�����Ϣ";
			}
			data = FlowUtil.readMilte(sql, logger2, msg);
		}
		return data;
	}

	// ------------------------------------------------------------------------------------------------
	public Object[][] getByAidRpdcodeAdid(String colStr, String aid,
			String rpdcode, String adid) {
		Object[][] data = null;
		if (aid != null && rpdcode != null) {
			String whereStr = "aid='" + aid + "'";
			if (rpdcode != null) {
				whereStr += " and rpdcode='" + rpdcode + "'";
			}
			whereStr += " and adid";
			if (adid == null) {
				whereStr += " is null";
			} else if (adid.equals("")) {
				whereStr += " is not null";
			} else {
				whereStr += " = '" + adid + "'";
			}
			data = gets(colStr, whereStr);
		}
		return data;
	}


	/**
	 * ��ȡ��ѯSQL���
	 * 
	 * @param cnames
	 *            ����
	 * @param wheres
	 *            ɸѡ
	 * @return SQL���
	 */
	public String getSelectSql(String cnames, String wheres) {
		cnames = cnames == null ? getFullCols() : cnames;
		return Sql.select(tname, cnames, wheres, null, "id");
	}

	/**
	 * ��ȡ�����������
	 * 
	 * @param colStr
	 *            ����
	 * @param whereStr
	 *            ɸѡ
	 * @return �����������
	 */
	public Object[][] gets(String colStr, String whereStr) {
		Object[][] data = null;
		if (colStr != null && whereStr != null) {
			String sql = getSelectSql(colStr, whereStr);
			data = FlowUtil.readMilte(sql, logger2, "��ѯ���յ�λ");
		}
		return data;
	}

	/**
	 * ��ȡ������������
	 * 
	 * @param colStr
	 *            ����
	 * @param whereStr
	 *            ɸѡ
	 * @return �����������
	 */
	public Object[] get(String colStr, String whereStr) {
		Object[] data = null;
		if (whereStr != null) {
			String sql = getSelectSql(colStr, whereStr);
			data = FlowUtil.readLine(sql, logger2, "��ѯ���յ�λ");
		}
		return data;
	}

	/**
	 * ��������ID����ȡ���յ�λ��Ϣ
	 * 
	 * @param cnames
	 *            ����
	 * @param id
	 *            ����ID
	 * @return ���յ�λ��Ϣ
	 */
	public Object[] getById(String cnames, String id) {
		Object[] adinfo = null;
		if (id != null) {
			id = "id='" + id + "'";
			adinfo = get(cnames, id);
		}
		return adinfo;
	}

	/**
	 * ��������ID����ȡ���յ�λ��Ϣ
	 * 
	 * @param id
	 *            ����ID
	 * @return ���յ�λ��Ϣ
	 */
	public Object[] getById(String id) {
		return getById(null, id);
	}
	
//	------------------------------------------------------------
	private static Logger logger = Logger.getLogger(AccDeptDao.class);
	
	/**
	 * ��ȡ���յ�λ���ݹ������
	 * @param adid �������
	 * @return ���յ�λ
	 */
	public static String[] getRpdcodes(String adid) {
		String[] rpdcodes = null;
		if (adid != null) {
			String sql = Sql.select(tname, "rpdcode", "adid='" + adid + "'",
					null, "id");
			Object[][] accdept = FlowUtil.readMilte(sql, logger, "��ȡ���յ�λ��Ϣ��ͨ���������");
			if (accdept != null) {
				rpdcodes = new String[accdept.length];
				for (int i = 0; i < accdept.length; i++) {
					rpdcodes[i] = (String) accdept[i][0];
				}
			}
		}
		return rpdcodes;
	}
	
	/**
	 * �޸Ĵ���״̬
	 * @param adid �������
	 * @param rpdcodes ���յ�λ
	 * @return �Ƿ��޸ĳɹ�
	 */
	public static boolean modifyState(String adid, String rpdcodes) {
		boolean isSuccess = false;
		if (adid != null && rpdcodes != null) {
			String whereStr = "adid='" + adid + "'";
			if (rpdcodes.indexOf(",") != -1) {
				whereStr += " and instr('" + rpdcodes + "',rpdcode)!=0";
			} else {
				whereStr += " and rpdcode='" + rpdcodes + "'";
			}
			String sql = Sql.update(tname, "state='2',ptime='0'", "");
			isSuccess = FlowUtil.write(sql,logger,"�޸Ĵ���״̬");
		}
		return isSuccess;
	}
	
	/**
	 * ��ȡ�����Ե�ǰ������Ϣ�Ľ������
	 * @param jgid �������
	 * @param alarmId ������
	 * @param atype ��������
	 * @return ���յ�λ��Ϣ
	 */
	public Object[] getCurrent(String jgid, String alarmId, String atype){
		StringBuffer sift = new StringBuffer();
		sift.append("t.rpdcode='"+jgid+"'")
		.append(" and t.aid='"+alarmId+"'")
		.append(" and t.atype='"+atype+"'");
		String sql = Sql.select(tname + " t", getFullCols(), sift.toString());
		String msg = "��ȡ�����Ե�ǰ������Ϣ�Ľ������";
		return FlowUtil.readLine(sql,logger,msg);
	}
	
}
