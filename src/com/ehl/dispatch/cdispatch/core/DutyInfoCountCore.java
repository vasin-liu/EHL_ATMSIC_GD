package com.ehl.dispatch.cdispatch.core;

import com.appframe.data.sql.DBHandler;
import com.ehl.dispatch.cdispatch.dao.DutyInfoCountDao;
import com.ehl.tira.duty.PatrolUtil;
import com.ehl.util.Array;

public class DutyInfoCountCore {
	
	public DutyInfoCountDao dao = new DutyInfoCountDao();
	
	/***
	 * 事故类型统计
	 * @param dateS 起始时间
	 * @param dateE 结束时间
	 * @param statType 统计类型
	 * @param deptid 机构编号
	 * @return
	 * Modified by Liuwx 2011-10-17
	 * 新增排序参数 sortItem
	 */
	public String accType(int statType,String dateS, String dateE, 
			String deptid, String area,String jgid,int sortItem) {
		String xml = null;
		String title = getDate(dateS, dateE);
		if(deptid.equals("") || deptid.substring(2, 4).equals("00")){
			title += "各支队";
		}else{
			title += "各大队";
		}
		title += "不同事故类型上报事故警情次数统计图";
		String[] colTitle = {getDeptGb(statType)+",事故次数,事故类型","死亡3人及以上","营运大客车","校车",
				"危化品运输车","涉港澳台及涉外"};
		Object[][] data = dao.accType(statType,dateS, dateE, deptid,area,jgid);
		//Modified by Liuwx 2011-10-17
		Array.sort2(data, sortItem, false);
		//Modification finished
		String[][] dataStr = PatrolUtil.changeData(data);
		dataStr = PatrolUtil.changeData(dataStr, colTitle); 
		xml = PatrolUtil.columnMultipleCol(dataStr, title);
		xml = PatrolUtil.chart(xml==null?false:true, xml);
		return xml;
	}
	
	/***
	 * 警情类型统计
	 * @param dateS 起始时间
	 * @param dateE 结束时间
	 * @param statType 统计类型
	 * @param deptid 机构编号
	 * @return
	 * Modified by Liuwx 2011-10-17
	 * 新增排序参数 sortItem
	 */
	public String alarmType(int statType, String dateS, String dateE,
			String deptid,String area,String jgid,int sortItem) {
		String xml = null;
		String title = getDate(dateS, dateE);
		if(deptid.substring(2, 4).equals("00")){
			title += "各支队";
		}else{
			title += "各大队";
		}
		title += "报送和发布重大交通警情和路面交通动态信息统计图";
//		String request = "&lt;br&gt;"+"（高速公路、国省道）";
//		request = "1".equals(area) ? request : "";
		String[] colTitle = { getDeptGb(statType) + ",上报警情次数,事故类别", "交通事故",
				"交通拥堵", "施工占道", "其他" };
		Object[][] data = dao.alarmType(statType,dateS, dateE, deptid,area,jgid);
		//Modified by Liuwx 2011-10-17
		Array.sort2(data, sortItem, false);
		//Modification finished
		String[][] dataStr = PatrolUtil.changeData(data);
		dataStr = PatrolUtil.changeData(dataStr, colTitle);
		xml = PatrolUtil.columnMultipleCol(dataStr, title);
		xml = PatrolUtil.chart(xml==null?false:true, xml);
		return xml;
	}
	
	public String getDept(String jgid){
		String j = null;
		//2010年10月10日~19日广东省各支队不同警情类型上报警情次数统计分析图
		//时间、地点、分类、数量
		if(jgid != null){
			String p = "广东省";
			int level = DutyInfoCountDao.getLevel(jgid);
			if(level == 2){
				j = p;
			}else if(level == 4){
				try {
					String sql = "select jgmc from t_sys_department where jgid='"+jgid+"'";
					String city = String.valueOf(DBHandler.getSingleResult(sql));
					city = city.substring(0,city.indexOf("市")+1);
					j = p + city;
				} catch (Exception e) {
					System.err.println("获取机构描述出错！");
				}
			}else if(level == 6){
				j = p;
			}
		}
		return j;
	}
	
	public static String getDeptGb(int ccbm){
		String j = "";
		String[] jgmcs = {"总队","各支队","各大队","部分大队"};
		ccbm = ccbm / 2 - 1;
		if(ccbm >=0 && ccbm < jgmcs.length){
			j = jgmcs[ccbm];
		}
		return j;
	}
	
	
	public static String getDate(String dateS, String dateE){
		String date = null;
		String[] datedesc = {"年","月","日","时","分","秒"};
		if(dateS != null && dateE != null && dateS.length() == dateE.length()){
			if (dateS.length() % 3 == 1) {
				int score = (dateS.length() - 4) / 3 + 1;
				int differ = -1;
				int index;
				dateS = dateS  + " ";
				dateE = dateE + " ";
				for (int i = 0; i < score; i++) {
					index = 4 + 3 * i;
					dateS = dateS.replaceFirst(dateS
							.substring(index, index + 1), datedesc[i]);
					dateE = dateE.replaceFirst(dateE
							.substring(index, index + 1), datedesc[i]);
					if(!dateS.substring(0,4+3*i).equals(dateE.substring(0,4+3*i))){
						if(differ != -1){
							continue;
						}
						differ = i;
					}
				}
				
				date = "";
				if(differ == -1){
					date = dateS;
				}else if(differ == 0){
					date = dateS + "—" + dateE;
				}else{
					date = dateS + "—" + dateE.substring(4 + 3 *(differ-1) + 1);
				}
			}
		}
		return date;
	}
	
}
