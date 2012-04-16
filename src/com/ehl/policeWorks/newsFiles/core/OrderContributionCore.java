package com.ehl.policeWorks.newsFiles.core;

import java.util.HashMap;
import java.util.Map;

import com.ehl.dispatch.accdept.core.AccDeptCore;
import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.policeWorks.newsFiles.dao.OrderContributionDao;
import com.ehl.tira.util.XML;
import com.ehl.util.Array;

public class OrderContributionCore {
	
	
	/**
	 * 添加约稿信息
	 * @param order 约稿信息
	 * @return 主键编号
	 */
	public static String add(Map<String, String> order){
		return OrderContributionDao.insert(order);
	}
	
	/**
	 * 添加约稿信息
	 * @param order 约稿信息
	 * @param ajgids 接收单位编号
	 * @return 是否添加成功
	 */
	public static String add(Map<String, String> order, String ajgids){
		String id = add(order);
		if(id != null){
			id = addAccDept(id, ajgids) ? id : null;
		}
		return id;
	}
	
	/**
	 * 添加约稿接收单位
	 * @param adid 接收单位关联编号
	 * @param ajgids 接收单位编号
	 * @return 是否添加成功
	 */
	public static boolean addAccDept(String adid, String ajgids) {
		boolean isSuccess = false;
		if (adid != null && ajgids != null) {
			Map<String, String> accdepts = new HashMap<String, String>();
			accdepts.put("rpdcode", ajgids);
			accdepts.put("state", "1");
			accdepts.put("aid", adid);
			accdepts.put("atype", "6");
			accdepts.put("mstate", "1");
			accdepts.put("adid", adid);
			accdepts.put("ptime", "0");
			isSuccess = new AccDeptDao().adds(accdepts);
		}
		return isSuccess;
	}
	
	/**
	 *  查询未签收的约稿信息
	 * @param jgid 机构编号
	 * @return 未签收的约稿信息
	 */
	public static String searchUnSign(String jgid){
		String xml = null;
		Object[][] datas = OrderContributionDao.searchUnSign(jgid);
		if(datas != null){
			xml = XML.encapContent("order", "row", "col", datas);
		}
		return xml;
	}

	/**
	 * 获取约稿信息
	 * @param id 主键编号
	 * @return 约稿信息
	 */
	public static String get(String id) {
		String orderXml = null;
		Object[] order = OrderContributionDao.getById(id);
		if(order != null){
			orderXml = XML.getNode("order", OrderContributionDao.cnames, order, null);
		}
		orderXml = XML.getXML(orderXml);
		return orderXml;
	}
	
	/**
	 * 获取约稿信息
	 * @param id 主键编号
	 * @param jgid 当前用户机构编号
	 * @return 约稿信息
	 */
	public static String get(String id, String jgid) {
		String orderXml = null;
		Object[] order = OrderContributionDao.getById(id);
		if(order != null && jgid != null){
			String ocjgid = (String)order[1];
			if(ocjgid.equals(jgid)){
				String ajginfos = getAccDept(id);
				order = Array.insert(order, order.length, ajginfos);
			}else{
				
			}
			orderXml = XML.getNode("order", OrderContributionDao.fnames, order, null);
		}
		orderXml = XML.getXML(orderXml);
		return orderXml;
	}
	
	/**
	 * 获取接收单位状态信息
	 * @param adid 关联编号
	 * @return 接收单位状态信息
	 */
	public static String getAccDept(String adid) {
		String ajginfos = null;
		if (adid != null) {
			String sql = "select ad.state,(select jgmc from t_sys_department where jgid= ad.rpdcode) from t_oa_acceptdept ad where ad.adid='"
					+ adid + "' order by state, rpdcode";
			Object[][] accdepts = FlowUtil.readMilte(sql);
			if (accdepts != null) {
				String[] states = AccDeptCore.getArray(3, "");
				int state;
				String rpdcode;
				for (int i = 0; i < accdepts.length; i++) {
					state = Integer.parseInt((String) accdepts[i][0]);
					rpdcode = (String) accdepts[i][1];
					if (!states[state - 1].equals("")) {
						rpdcode = states[state - 1] + "，" + rpdcode;
					}
					states[state - 1] = rpdcode;
				}
				ajginfos = Array.join(states, ";");
			}
		}
		return ajginfos;
	}

	/**
	 * 修改约稿信息
	 * @param order 约稿信息
	 * @param ajgids 接收单位信息
	 * @return 是否修改成功
	 */
	public static boolean modify(Map<String, String> order) {
		return OrderContributionDao.modify(order);
	}
	
	/**
	 * 修改约稿信息
	 * @param order 约稿信息
	 * @param ajgids 接收单位信息
	 * @return 是否修改成功
	 */
	public static boolean modify(Map<String, String> order, String ajgids) {
		boolean isSuccess = false;
		if(order != null){
			String id = order.get("id");
			if(id != null){
				if(order.size() == 1){
					isSuccess = true;
				}else{
					isSuccess = modify(order);
				}
				if(isSuccess && ajgids != null){
					String[] rpdcodes = AccDeptDao.getRpdcodes(id);
					String[] ajgidsBeSort = AccDeptCore.distinguishContainOrNot(rpdcodes, ajgids);
					String exist = ajgidsBeSort[0];
					if(exist != null && !exist.equals("")){
						isSuccess = AccDeptDao.modifyState(id, exist);
					}
					String notExist = ajgidsBeSort[1];
					if(notExist != null && !notExist.equals("")){
						isSuccess = addAccDept(id, notExist);
					}
				}
			}
		}
		return isSuccess;
	}

	
	public static void main(String[] args) {
		
	}
	

}
