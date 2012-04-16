package com.ehl.dispatch.accdept.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;
import com.ehl.tira.util.XML;

public class AccDeptCore {
	
	public AccDeptDao dao = new AccDeptDao();

	
	/**
	 * 添加接收单位
	 * @param id 警情id
	   @param atype 警情类型
	 * @param mstate 信息状态
	 * @param adcode 接收单位机构代码
	 * @return
	 */
	public boolean addAccDept(String id, String atype, String mstate, String adcode){
		return addAccDept(id, atype, mstate, null, adcode);
	}
	
	/**
	 * 添加接收单位
	 * @param id 警情id
	   @param atype 警情类型
	 * @param mstate 信息状态
	 * @param adcode 接收单位机构代码
	 * @return
	 */
	public boolean addAccDept(String id, String atype, String mstate,String adid, String adcode){
		boolean isOK = false;
		if(id != null && atype != null && mstate != null && adcode != null ){
			Map<String,String> admap = new HashMap<String, String>();
			admap.put("aid", id);
			admap.put("atype", atype);
			admap.put("mstate", mstate);
			admap.put("rpdcode", adcode);
			if (adid != null) {
				admap.put("adid", adid);
			}
			isOK = dao.adds(admap);
		}
		return isOK;
	}
	
	/**
	 * <pre>
	 * 添加转发机构
	 * </pre>
	 * @param aid 接收单位ID
	 * @param adcode 转发机构代码
	 * @param adcode 
	 * @return
	 */
	public String addAccDept(String aid, String udcode, String adcode){
		boolean isOK = false;
		if(aid != null && adcode != null){
			//获取接收机构信息
			Object[][] data = dao.getAccDept(aid, udcode, 2);
			if(data == null){
				data = dao.getAccDept(aid, null, 0);
			}
			if(data != null && data.length == 1){
				aid = data[0][6] + "";
				String id = data[0][0] + "";
				String atype = data[0][7] + "";
				String mstate = data[0][8] + "";
				//获取转发机构信息
				Object[][] dataDis = dao.getAccDept(id, 2);
				//获取转发机构id和机构代码
				String sep = ",";
				String ids = "", disDepts = "";
				int index;
				if(dataDis != null){
					adcode = sep + adcode;
					for (int i = 0; i < dataDis.length; i++) {
						index = adcode.indexOf(dataDis[i][1]+"");
						if(index != -1){
							ids += sep+ dataDis[i][0]+"";
							disDepts = sep + dataDis[i][1];
							adcode = adcode.replace(disDepts, "");//移除已存在的机构
						}
					}
					//更新已经转发过的单位，查看状态为1未查看
					if(!ids.equals("")){
						ids = ids.substring(sep.length());
						isOK = dao.updateState(ids, "1", null);
					}
					if(!adcode.equals("")){
						adcode = adcode.substring(sep.length());
					}
				}
				
				if(!adcode.equals("")){
					//添加转发机构信息
					Map<String,String> paramsIn = new HashMap<String, String>();
					paramsIn.put("aid", aid);
					paramsIn.put("rpdcode", adcode);
					paramsIn.put("adid", id);
					paramsIn.put("mstate", mstate);
					paramsIn.put("atype", atype);
					isOK = dao.adds(paramsIn);
				}
			}
		}
		return String.valueOf(isOK);
	}
	
	/**
	 * 获取接收单位状态描述
	 * @param id 
	 * @param states
	 * @return
	 */
	public String getAccDept(String id, String[] states){
		String xml = null;
		Object[][] accdepts = dao.getAccDept(id, null, 2);
		if(accdepts != null){
			xml = getAccDept(accdepts, states);
		}
		return xml;
	}
	
	/**
	 * 获取转发单位状态描述
	 * @param id 
	 * @param states
	 * @return
	 */
	public String getDisDept(String id, String[] states){
		String xml = null;
		Object[][] accdepts = dao.getAccDept(id, 2);
		if(accdepts != null){
			xml = getAccDept(accdepts, states);
		}
		return xml;
	}
	
	
	/**
	 * <pre>
	 * 获取接收单位、不同状态下接收单位XML格式数据
	 * </pre>
	 * @param accdepts 接收单位数组
	 * @param states 处理状态 。"1","2","3"中一个或多个组合，不重复
	 * @return
	 */
	public String getAccDept(Object[][] accdepts, String[] states){
		String xml = null;
		if(accdepts != null && states != null){
			String[] adstates = getArray(states.length,"");
			String sep = "，";
			String state;
			for (int i = 0; i < accdepts.length; i++) {
				state = accdepts[i][5] + "";
				for (int j = 0; j < states.length; j++) {
					if(state.equals(states[j])){
						adstates[j] += sep + accdepts[i][2];
						break;
					}
				}
			}
			String accdeptStr = "";//所有接收单位
			String adstateStr = "";//不同状态接收单位
			for (int i = 0; i < adstates.length; i++) {
				accdeptStr += adstates[i];
				if(!adstates[i].equals("")){
					adstates[i] = adstates[i].substring(sep.length());
				}
				adstateStr += XML.encapContent("col", adstates[i]);
			}
			if(!accdeptStr.equals("")){
				accdeptStr = accdeptStr.substring(sep.length());
				accdeptStr = XML.encapContent("col", accdeptStr);
			}
			xml = accdeptStr + adstateStr;
			xml = XML.encapContent("adstate", xml);
		}
		return xml;
	}
	
	/**
	 * 获取消息提示框提醒数量<br>
	 * jgid机构编号、atype警情类型、state处理状态、mstate信息状态
	 * @param paramsIn
	 * @return
	 */
	public String[] searchCount(Map<String, String> paramsIn) {
		String[] counts = null;
		if(paramsIn != null){
			String atype = paramsIn.get("atype");
			int atypeint = Integer.parseInt(atype)-1;
			counts = getCountForm(dao.promptForm[atypeint]);
			Object[][] countData = dao.searchCount(paramsIn);
			if(countData != null){
				int ptyped;
				for (int i = 0; i < countData.length; i++) {
					ptyped = Integer.parseInt(countData[i][0]+"")-1;
					counts[ptyped] = countData[i][1]+"";
				}
			}
		}
		return counts;
	}
	
	
	/**
	 * 更新接收单位状态<br>
	 *  aid,state
	 * @param state 
	 * @param state2 
	 * @param udcode 
	 * @param pimap
	 * @return
	 */
	public boolean updateState(String id, String stype, String udcode, String state, Map<String,String> paramMap) {
		if(stype != null){
			Object[][] data = null;
			if(stype.equals("2")){
				data = dao.getAccDept(id, udcode, 2);
			}else if(stype.equals("3")){
				data = dao.getAccDept(id, udcode, 3);
			}
			if(data != null && data.length >= 1 ){
				id = "";
				for (int i = 0; i < data.length; i++) {
					id += "," + data[i][0];
				}
				id=id.substring(1);
			}
		}
		boolean isOK = dao.updateState(id, state,paramMap);
		return isOK;
	}
	
	
	public String[] getCountForm(int ptypes){
		return getArray(ptypes, "0");
	}
	
	public static String[] getArray(int length, String fvalue){
		String[] form = null;
		if (length > 0) {
			form = new String[length];
			for (int i = 0; i < form.length; i++) {
				form[i] = fvalue;
			}
		}
		return form;
	}
	
	/**
	 * 更新提醒次数，每次提醒+1
	 * @param udcode 当前用户机构代码
	 * @param atype 警情类型
	 * @return
	 */
	public boolean updatePtime(String udcode, String atype){
		boolean isOK = false;
		if(udcode != null){
			String setStr = "ptime=ptime+1";
			String whereStr = "rpdcode='"+udcode+"'";
			if(atype != null){
				whereStr += " and atype='"+atype+"'";
			}
			isOK = dao.modify(setStr, whereStr);
		}
		return isOK;
	}
	
	/**
	 * <pre>
	 * 更新信息状态
	 * 1.更新mstate
	 * 2.更新state=1
	 * 3.更新ptime=0
	 * </pre>
	 * @param mstate 
	 * @param whereMap id/rpdcode、aid、adid
	 * @return
	 */
	public boolean updateMState(String aid,String mstate){
		boolean isOK = false;
		Map<String,String> mspMap = new HashMap<String, String>();
		mspMap.put("mstate", mstate);
		mspMap.put("state", "1");
		mspMap.put("ptime", "0");
		String whereStr = "aid='" + aid + "'";
		isOK = dao.modify(mspMap,whereStr);
		return isOK;
	}
	
	/**
	 * <pre>
	 * 更新处理状态
	 * 1.更新state
	 * 2.更新ptime=0
	 * </pre>
	 * @param mstate 
	 * @param whereMap id/rpdcode、aid、adid
	 * @return
	 */
	private boolean updateState(String state, String fpname, String ftime, String whereStr){
		boolean isOK = false;
		if(state != null){
			Map<String,String> setMap = new HashMap<String, String>();
			setMap.put("state", state);
			if(fpname != null && ftime != null){
				setMap.put("rpname", fpname);
				setMap.put("rtime",ftime);
			}
			setMap.put("ptime", "0");
			isOK = dao.modify(setMap, whereStr);
		}
		return isOK;
	}
	
	/**
	 * 签收
	 * @param fpname
	 * @param ftime
	 * @param whereStr
	 * @return
	 */
	public boolean signIn(String fpname, String ftime,String whereStr){
		return updateState("2",fpname, ftime, whereStr);
	}
	public boolean signIn(String whereStr){
		return signIn(null, null, whereStr);
	}
	public boolean signIn(String fpname, String ftime,Map<String,String> whereMap){
		return signIn(fpname, ftime, dao.getWhere(whereMap));
	}
	
	/**
	 * 办结
	 * @param fpname
	 * @param ftime
	 * @param whereMap
	 * @return
	 */
	public boolean finish(String fpname, String ftime,String whereStr){
		return updateState("3", fpname, ftime, whereStr);
	}
	public boolean finish(String whereStr){
		return finish(null, null, whereStr);
	}
	public boolean finish(String fpname, String ftime,Map<String,String> whereMap){
		return finish(fpname, ftime, dao.getWhere(whereMap));
	}
	
	public static void main(String[] args) {
		String[] abc = {"","a"};
		System.out.println(Arrays.toString(abc));
		
	}
	
	
	//-----------------------------------------------------------------------
	public final static String attrSep = ",";
	public final static String objSep = ";";
	
	public boolean updateAccDept(String adcntntsStr,String rpdcodeNews){
		boolean isOK = false;
		if(adcntntsStr != null && rpdcodeNews != null ){
			String[] adcntnts = adcntntsStr.split(objSep);
			String[] adcntnt;
			for (int i = 0; i < adcntnts.length; i++) {
				adcntnt = adcntnts[i].split(attrSep);
				if(adcntnt.length == 4){
					String adid = adcntnt[3];
					String sql = "select rpdcode from t_oa_acceptdept where adid='"+adid+"'";
					Object[][] rpdcodeOlds = FlowUtil.readMilte(sql);
					String rpdcodeOld;
					String rpdcodeExists = "";
					String rpdcodeNotExists = attrSep + rpdcodeNews;
					if (rpdcodeOlds != null) {
						for (int j = 0; j < rpdcodeOlds.length; j++) {
							rpdcodeOld = rpdcodeOlds[j][0].toString();
							rpdcodeOld = attrSep + rpdcodeOld;
							if(rpdcodeNotExists.indexOf(rpdcodeOld)!=-1){
								rpdcodeExists += rpdcodeOld;
								rpdcodeNotExists = rpdcodeNotExists.replace(rpdcodeOld, "");
							}
						}
					}
					String whereExist;
					if(!rpdcodeExists.equals("")){
						rpdcodeExists = rpdcodeExists.substring(attrSep.length());
						whereExist = Sql.whereIn("rpdcode", Sql.toIn(rpdcodeExists));
						sql = "update t_oa_acceptdept set state = '1' where adid='"+adid+"' and " + whereExist;
						isOK = FlowUtil.write(sql);
					}
					if(!rpdcodeNotExists.equals("")){
						rpdcodeNotExists = rpdcodeNotExists.substring(attrSep.length());
						isOK = addAccDept(adcntnt[0], adcntnt[1], adcntnt[2], adid, rpdcodeNotExists);
					}
				}
			}
		}
		return isOK;
	}
	
	
	
	/**
	 * 区分出一个字符串被指定数组包含的部分和不包含的部分
	 * @param sorts 区分数组
	 * @param beSorts 被区分字符串
	 * @return 一维数组固定长度为2，索引0存放包含的字符串，索引1存放不包含的字符串
	 */
	public static String[] distinguishContainOrNot (String[] sorts, String beSorts){
		String[] afSorts = new String[2];
		if(beSorts == null || beSorts.equals("")){
			afSorts[0] = beSorts;
			afSorts[1] = beSorts;
		}else{
			if(sorts == null || sorts.length == 0){
				if(sorts == null){
					afSorts[0] = null;
				}else{
					afSorts[0] = "";
				}
				afSorts[1] = beSorts;
			}else{
				String sep = ",";
				beSorts = sep + beSorts;
				String sort;
				String sortExists = "";
				for (int i = 0; i < sorts.length; i++) {
					sort = sep + sorts[i];
					if(beSorts.contains(sort)){
						sortExists += sort;
						beSorts = beSorts.replace(sort, "");
					}
				}
				if(!beSorts.equals("")){
					beSorts = beSorts.substring(sep.length());
				}
				if(!sortExists.equals("")){
					sortExists = sortExists.substring(sep.length());
				}
				afSorts[0] = sortExists;
				afSorts[1] = beSorts;
			}
		}
		return afSorts;
	}
	
	
	/**
	 * 签收
	 * @param set
	 * @return
	 */
	public boolean signIn(Map<String, String> set) {
		boolean isOK = false;
		if (set != null) {
			String aid = set.get("aid");
			String rpdcode = set.get("rpdcode");
			if (aid != null && rpdcode != null) {
				set.remove("aid");
				set.remove("rpdcode");
				String state = set.get("state");
				if(state == null){
					set.put("state", "2");
				}
				String whereStr = "aid='" + aid + "' and rpdcode='" + rpdcode + "'";
				isOK = dao.modify(set, whereStr);
			}
		}
		return isOK;
	}

	/**
	 * 获取机构对当前警情信息的接收情况
	 * @param jgid 机构编号
	 * @param alarmId 警情编号
	 * @param atype 警情类型
	 * @return 接收单位信息
	 */
	public String getCurrent(String jgid,String alarmId,String atype){
		Object[] object = dao.getCurrent(jgid, alarmId, atype);
		return XML.getNode("accdept", dao.getSelectFields(), object, null);
	}
	
	
	
	
}
