package com.ehl.policeWorks.assess.dao;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.sm.base.Constant;

public class ScoreRecordDao {
	
	private Logger logger = Logger.getLogger(ScoreRecordDao.class); 
	/**警情类型，1：事故；2：拥堵；3：施工占道；4：拥堵爆料*/
	public static int[] atypes = {1,2,3,4};
	/**原因类型，1：漏报|超时未核实；2：错报|未准确核实；3：超时未更新*/
	public static int[] rtypes = {1,2,3};
	/**不同警情类型对应的原因类型数量*/
	public static int[] ator = {2,3,2,2};
	
	/**
	 * 添加额外计分信息
	 * @param jgid 机构编号
	 * @param jgmc 机构描述
	 * @param date 计分警情发生时间
	 * @param items 警情类型
	 * @param reasons 扣分原因类型
	 * @param reason 计分原因简述
	 * @return
	 */
	public boolean add(String jgid, String jgmc, String date,String aid, String items,
			String reasons, String reason) {
		String aidcname = "aid,";
		if (aid == null) {
			aidcname = "";
			aid = "";
		} else {
			aid = "'" + aid + "',";
		}
		String sql = " insert into T_OA_SCORERECORD(deptid,deptdesc,jfrq,dayid,"+aidcname+"type,rtype,reason) " +
				"values('"+jgid+"','"+jgmc+"',to_date('"+date+"','yyyy-mm-dd HH24:mi:ss'),sysdate,"+aid+"'"+items+"','"+reasons+"','"+reason+"')";
		
		boolean isSuccess = false;
		try {
			isSuccess = DBHandler.execute(sql);
		} catch (Exception e) {
			System.err.println("插入计分,sql->\n"+sql);
			isSuccess = false;
			logger.error("插入额外计分信息失败！",e);
		}
		return isSuccess;
	}
	
	/**
	 * 获取计分表中不同类型、不同原因的扣分信息数量
	 * @param jgid
	 * @param start
	 * @param end
	 * @return
	 */
	public Object[][] getItemCounts(String jgid, String start, String end, boolean isSelf){
		Object[][] items = null;
		if(jgid != null && start != null && end != null){
			String sql = "select type, rtype , count(1) from t_oa_scorerecord ";
			sql += " where ";
			if(isSelf){
				sql += " deptid='" + jgid + "'";
			}else{
				sql += Constant.getSiftSelfChildsSql("deptid", jgid);
			}
			sql += " and " + Constant.defaultSift("deptid");
			if (start.length() == 10) {
				start += " 00:00:00";
			}
			if (end.length() == 10) {
				end += " 23:59:59";
			}
			sql += " and dayid>=to_date('" + start
					+ "','yyyy-mm-dd hh24:mi:ss') ";
			sql += "and dayid<=to_date('" + end
					+ "','yyyy-mm-dd hh24:mi:ss') ";
			sql += " group by type,rtype";
			sql += " order by type,rtype";
			items = FlowUtil.readMilte(sql,logger,"获取计分表中不同类型、不同原因的扣分信息数量");
		}
		return items;
	}
	
	/**
	 * <pre>
	 * 将线性数据转换为数组数据
	 * 将不同类型、不同原因的扣分信息数量数据转换成数组形式
	 * </pre>
	 * @param data 扣分信息数量数据
	 * @return 数组数据
	 */
	public int[][] changeItemCounts(Object[][] data){
		int[][] counts = new int[atypes.length][];
		for (int i = 0; i < counts.length; i++) {
			counts[i] = new int[ator[i]];
			for (int j = 0; j < counts[i].length; j++) {
				counts[i][j] = 0;
			}
		}
		if(data != null){
			int atype;
			int rtype;
			try {
				for (int i = 0; i < data.length; i++) {
					atype = Integer.parseInt(data[i][0].toString())-1;
					rtype = Integer.parseInt(data[i][1].toString())-1;
					counts[atype][rtype] = Integer.parseInt(data[i][2].toString());
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return counts;
	}
	
	/**
	 * 删除额外计分信息
	 * @param jgid 机构编号
	 * @param date 录入计分日期
	 * @return
	 */
	public boolean delete(String jgid, String date) {
		
		String sql = "delete from T_OA_SCORERECORD where deptid='" + jgid
				+ "' and dayid=to_date('" + date + "','yyyy-mm-dd HH24:mi:ss')";
		
		boolean isSuccess = false;
		try {
			isSuccess = DBHandler.execute(sql);
		} catch (Exception e) {
			System.err.println("删除计分,sql->\n"+sql);
			isSuccess = false;
			logger.error("删除额外计分信息失败！",e);
		}
		
		return isSuccess;
	}

}
