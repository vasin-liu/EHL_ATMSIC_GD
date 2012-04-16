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
	public final static String[] states = { "未查看/未签收", "已查看/已签收/签收办理中", "已办结" };
	public final static String[] atypes = { "事故", "拥堵", "施工占道", "协查通报", "其他重特大情况","约稿" };
	public final static String[] mstates = { "新增", "修改", "删除" };
	public final static int[] promptForm = { 0, 3, 3, 3, 2, 1 };

	
	
	public String[] getSelectFields(){
		String[] fields = cnames.clone();
		return Array.insert(fields, 2, "rpddesc");
	}
	/**
	 * 获取完整的列名字段<br>
	 * 在接收人单位代码后追加了接收人单位描述字段，<br>
	 * 将列字段数组连接成字符串
	 * 
	 * @return
	 */
	public static String getFullCols() {
		return "id,rpdcode,f_get_dept(rpdcode),rpname,"
				+ Sql.toChar("rtime", Sql.getDFFull(5))
				+ ",state,aid,atype,mstate,adid,ptime";
	}

	/**
	 * 获取主键ID值
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
	 * 添加接收单位信息 rpdcode,aid,atype,mstate,[adid]
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
			isOK = FlowUtil.write(sql, logger2, "添加接收单位信息");
		}
		return isOK;
	}

	/**
	 * 添加接收单位信息 rpdcode,aid,atype,[adid]
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
	 * 获取接收单位信息，以Map形式
	 * 
	 * @param id
	 *            接收单位主键ID
	 * @return 接收单位信息
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
	 * 修改接收单位
	 * 
	 * @param setStr
	 * @param whereStr
	 * @return
	 */
	public boolean modify(String setStr, String whereStr) {
		boolean isOK = false;
		if (setStr != null && whereStr != null) {
			String sql = Sql.update(tname, setStr, whereStr);
			System.out.println("SQL：\n" + sql);
			isOK = FlowUtil.write(sql, logger2, "修改接收单位");
		}
		return isOK;
	}

	/**
	 * 修改接收单位信息
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
	 * 修改接收单位提醒次数
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
			isOK = FlowUtil.write(sql, logger2, "修改接收单位提醒次数");
		}
		return isOK;
	}

	public boolean modifyPtime(String rpdcode, String atype) {
		return modifyPtime(rpdcode, atype, null);
	}

	/**
	 * 更新接收单位关联警情信息状态
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
			isOK = FlowUtil.write(sql, logger2, "更新接收单位关联警情信息状态为" + mstate);
		}
		return isOK;
	}

	/**
	 * 更新接收单位对信息处理状态
	 * 
	 * @param id
	 *            接收单位主键ID
	 * @param state
	 *            处理状态
	 * @param paramMap
	 *            包含填报人和填报时间，
	 * @return
	 */
	public boolean updateState(String id, String state,
			Map<String, String> paramMap) {
		boolean isOK = false;
		if (id != null && state != null) {
			FlowUtil.encapMapSQ(paramMap);
			String setStr = "ptime='0',state = " + FlowUtil.encapSQ(state);
			String whereStr;
			if (id.indexOf(",") != -1) {// 同时更新多个机构
				whereStr = " instr(" + FlowUtil.encapSQ(id) + ",id) != 0";
			} else {
				whereStr = " id= " + FlowUtil.encapSQ(id);
			}
			if (paramMap != null) {// 同时更新填报人和填报时间
				if (paramMap.containsKey("rtime")) {
					paramMap.put("rtime", Sql.toDate(FlowUtil
							.cancleSQ(paramMap.get("rtime"))));
				}
				setStr += "," + Sql.join(paramMap, Sql.sepItem);
			}
			String sql = Sql.update(tname, setStr, whereStr);
			isOK = FlowUtil.write(sql, logger2, "更新接收单位关联警情信息状态为" + state);
		}
		return isOK;
	}

	/**
	 * 更新接收单位信息
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
					.write(sql, logger2, "更新接收单位信息，设置状态为" + states[state]);
		}
		return isOK;
	}

	/**
	 * 查找警情提醒数量rpdcode机构编号、atype警情类型、state处理状态、mstate信息状态
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
			count = FlowUtil.readMilte(sql, logger2, "获取警情提示数量");
		}
		return count;
	}

	/**
	 * <pre>
	 * 获取接收单位信息
	 * 1.通过警情ID，获取接收单位或者转发单位信息
	 * 2.通过警情ID、接收单位机构代码，获取接收单位信息
	 * 3.通过警情ID、转发单位机构代码，获取转发单位信息
	 * 4.通过接收单位ID，获取接收单位信息
	 * </pre>
	 * 
	 * @param id
	 *            警情ID
	 * @param pdcode
	 *            人员机构代码
	 * @param stype
	 *            信息来源类型。1：接收单位和转发单位；2：接收单位；3：转发单位。 0标记id为接收单位ID。
	 * @return 接收单位信息
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
				data = FlowUtil.readMilte(sql, logger2, "获取接收单位信息");
			}
		}
		return data;
	}

	/**
	 * <pre>
	 * 获取支队下发接收单位信息
	 * 1.通过警情ID，获取接收单位或者转发单位信息
	 * 2.通过警情ID、接收单位机构代码，获取接收单位信息
	 * 3.通过警情ID、转发单位机构代码，获取转发单位信息
	 * 4.通过接收单位ID，获取接收单位信息
	 * </pre>
	 * 
	 * @param id
	 *            警情ID
	 * @param pdcode
	 *            人员机构代码
	 * @param stype
	 *            信息来源类型。1：接收单位和转发单位；2：接收单位；3：转发单位。 0标记id为接收单位ID。
	 * @return 接收单位信息
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
	 * 获取总队转发单位信息
	 * 1.通过警情ID，获取接收单位或者转发单位信息
	 * 2.通过警情ID、接收单位机构代码，获取接收单位信息
	 * 3.通过警情ID、转发单位机构代码，获取转发单位信息
	 * 4.通过接收单位ID，获取接收单位信息
	 * </pre>
	 * 
	 * @param id
	 *            警情ID
	 * @param pdcode
	 *            人员机构代码
	 * @param stype
	 *            信息来源类型。1：接收单位和转发单位；2：接收单位；3：转发单位。 0标记id为接收单位ID。
	 * @return 接收单位信息
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
	 * 获取接收单位或者转发单位
	 * 1.通过接收单位ID，获取转发单位信息
	 * 2.通过转发单位ID，获取接收单位信息
	 * </pre>
	 * 
	 * @param id
	 *            接收单位ID
	 * @param stype
	 *            信息来源类型。2：接收单位；3：转发单位
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
				msg = "通过接收机构ID获取转发机构信息";
			} else if (stype == 3) {
				msg = "通过转发机构ADID获取接收机构信息";
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
	 * 获取查询SQL语句
	 * 
	 * @param cnames
	 *            列名
	 * @param wheres
	 *            筛选
	 * @return SQL语句
	 */
	public String getSelectSql(String cnames, String wheres) {
		cnames = cnames == null ? getFullCols() : cnames;
		return Sql.select(tname, cnames, wheres, null, "id");
	}

	/**
	 * 获取多个对象数据
	 * 
	 * @param colStr
	 *            列名
	 * @param whereStr
	 *            筛选
	 * @return 多个对象数据
	 */
	public Object[][] gets(String colStr, String whereStr) {
		Object[][] data = null;
		if (colStr != null && whereStr != null) {
			String sql = getSelectSql(colStr, whereStr);
			data = FlowUtil.readMilte(sql, logger2, "查询接收单位");
		}
		return data;
	}

	/**
	 * 获取单个对象数据
	 * 
	 * @param colStr
	 *            列名
	 * @param whereStr
	 *            筛选
	 * @return 多个对象数据
	 */
	public Object[] get(String colStr, String whereStr) {
		Object[] data = null;
		if (whereStr != null) {
			String sql = getSelectSql(colStr, whereStr);
			data = FlowUtil.readLine(sql, logger2, "查询接收单位");
		}
		return data;
	}

	/**
	 * 根据主键ID，获取接收单位信息
	 * 
	 * @param cnames
	 *            列名
	 * @param id
	 *            主键ID
	 * @return 接收单位信息
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
	 * 根据主键ID，获取接收单位信息
	 * 
	 * @param id
	 *            主键ID
	 * @return 接收单位信息
	 */
	public Object[] getById(String id) {
		return getById(null, id);
	}
	
//	------------------------------------------------------------
	private static Logger logger = Logger.getLogger(AccDeptDao.class);
	
	/**
	 * 获取接收单位根据关联编号
	 * @param adid 关联编号
	 * @return 接收单位
	 */
	public static String[] getRpdcodes(String adid) {
		String[] rpdcodes = null;
		if (adid != null) {
			String sql = Sql.select(tname, "rpdcode", "adid='" + adid + "'",
					null, "id");
			Object[][] accdept = FlowUtil.readMilte(sql, logger, "获取接收单位信息，通过关联编号");
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
	 * 修改处理状态
	 * @param adid 关联编号
	 * @param rpdcodes 接收单位
	 * @return 是否修改成功
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
			isSuccess = FlowUtil.write(sql,logger,"修改处理状态");
		}
		return isSuccess;
	}
	
	/**
	 * 获取机构对当前警情信息的接收情况
	 * @param jgid 机构编号
	 * @param alarmId 警情编号
	 * @param atype 警情类型
	 * @return 接收单位信息
	 */
	public Object[] getCurrent(String jgid, String alarmId, String atype){
		StringBuffer sift = new StringBuffer();
		sift.append("t.rpdcode='"+jgid+"'")
		.append(" and t.aid='"+alarmId+"'")
		.append(" and t.atype='"+atype+"'");
		String sql = Sql.select(tname + " t", getFullCols(), sift.toString());
		String msg = "获取机构对当前警情信息的接收情况";
		return FlowUtil.readLine(sql,logger,msg);
	}
	
}
