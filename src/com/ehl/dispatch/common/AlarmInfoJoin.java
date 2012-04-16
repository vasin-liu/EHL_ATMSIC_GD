package com.ehl.dispatch.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.ehl.dispatch.cdispatch.dao.AlarmDao;
import com.ehl.dynamicinfo.policeRemind.core.PoliceRemindCore;
import com.ehl.dynamicinfo.policeRemind.dao.PoliceRemindDao;
import com.ehl.sm.base.Constant;

/**
 * <pre>
 * 警情信息拼接类
 * 将警情信息各个字段拼接成一段话，用于做交警提示的提示内容。
 * 同时，也可以直接插入结束类型的交警提示信息。
 * <b>语句拼接规则：</b>
 * 字段为null或者空字符的，返回空字符以便追加拼接；
 * 否则，在字段前面或者后面加上修饰，再根据需要在末尾加上符号
 * <b>交警提示信息组成结构：</b>
 * 在【时间(警情开始或者结束时间)，】【地点(包括道路名称、管辖单位、路段名称、道路方向、里程数)，】
 * 发生【警情类型(包括事故、拥堵、施工占道)】【附加信息(例如拥堵原因、分流管制措施等等)】
 * 例如：
 * 2011-08-22 18:55，在广深高速广州番禺大队管辖的媒合路段北京往深圳方向K10+50米至K60+900米处，
 * 发生了一起交通拥堵。拥堵原因包括：事故、施工。交警提示内容为前方拥堵，请绕行。
 * </pre>
 * @version 1.0
 * @author xiayx
 * @see com.ehl.dispatch.cdispatch.action.CrowdManageAction
 */
public class AlarmInfoJoin {
	
	public static Logger logger = Logger.getLogger(AlarmInfoJoin.class);
	public static PoliceRemindCore prcore = new PoliceRemindCore();
	
	/**
	 * <pre>
	 * 获取时间拼接描述
	 * </pre>
	 * @param time 时间
	 * @return 时间拼接描述
	 */
	public static String getTime(String time, String type) {
		String desc = "";
//		if (type.equals(AlarmDao.BUILD_CODE)) {
//			desc = arround(time, "自", "开始，");
//		} else {
//			desc = time;
//		}
		desc = time;
		return desc;
	}
	
	/**
	 * 获取路段拼接描述
	 * @param rsection 路段名称
	 * @return 路段拼接描述
	 */
	public static String getRoadSection(String rsection) {
		if (rsection != null && !rsection.equals("")) {
			if (!rsection.endsWith("路段")) {
				rsection += "路段";
			}
			return rsection;
		}
		return "";
	}
	
	/**
	 * 获取道路方向为双向时的拼接描述
	 * @param rdcode 道路方向代码
	 * @return 道路方向拼接描述
	 */
	public static String getDirection(String rdcode) {
		return rdcode.equals("2") ? "双向" : "";
	}
	
	/**
	 * 获取道路方向为单向时的拼接描述
	 * @param rname 道路名称
	 * @param rdcode 道路方向代码
	 * @return 道路方向拼接描述
	 */
	public static String getDirection(String rname, String rdcode){
		String desc = "";
		if(rdcode != null && !rdcode.equals("2")){
			if(rdcode.equals("0") || rdcode.equals("1")){
				if(rname != null){
					String sql  = "select begin,end from t_oa_dictdlfx where dlmc='"+rname+"'";
					Object[] beginEnd = FlowUtil.readLine(sql);
					if(beginEnd != null){
						int rd = Integer.parseInt(rdcode);
						desc = beginEnd[rd] + "往" + beginEnd[beginEnd.length - 1 - rd]+"方向";
					}
				}
			}else{
				rdcode = rdcode.trim();
				if(!rdcode.equals("") && !rdcode.endsWith("方向")){
					desc = rdcode + "方向";
				}
			}
		}
		return desc;
	}
	
	/**
	 * <pre>
	 * 获取道路里程拼接描述
	 * 道路里程拼接格式：K[xx]+[xx]米
	 * </pre>
	 * @param km 千米值
	 * @param m 百米值
	 * @return 道路里程拼接描述
	 */
	public static String getLcb(String km, String m) {
		String lcb = "";
		if (km != null && !km.equals("")) {
			lcb += "K" + km;
		}
		if (m != null && !m.equals("")) {
			if (!lcb.equals("")) {
				lcb += "+";
			}
			lcb += m + "米";
		}
		return lcb;
	}
	
	/**
	 * <pre>
	 * 获取道路区域里程拼接描述
	 * 道路区域里程拼接格式：K[xx]+[xx]米至K[xx]+[xx]米处
	 * </pre>
	 * @param skm 起始千米值
	 * @param sm 起始百米值
	 * @param ekm 结束千米值
	 * @param em 结束百米值
	 * @return 道路区域里程拼接描述
	 */
	public static String getLcbRange(String skm, String sm, String ekm, String em){
		String lcbRange = getLcb(skm, sm);
		String erange = getLcb(ekm, em);
		if(!erange.equals("")){
			if(!lcbRange.equals("")){
				lcbRange += "至";
			}
			lcbRange += erange;
		}
		if(lcbRange.equals("K0+0米至K0+0米")){
			lcbRange = "";
		}
		if(!lcbRange.equals("")){
			lcbRange = "（"+lcbRange + "）";
		}
		return lcbRange;
	}
	
	/**
	 * 获取警情地点拼接描述
	 * @param jgmc 机构名称
	 * @param rname 道路名称
	 * @param rsname 路段名称
	 * @param rdirection 道路方向
	 * @param skm 起始千米值
	 * @param sm 起始百米值
	 * @param ekm 结束千米值
	 * @param em 结束百米值
	 * @return 警情地点拼接描述
	 */
	public static String getPlace(String jgmc,  String rname,
			String rsname, String rdirection, String skm, String sm,
			String ekm, String em){
		StringBuffer place = new StringBuffer();
		place.append(Constant.nvl(rname))
		.append(getRoadSection(rsname))
		.append(getLcbRange(skm, sm, ekm, em))
		.append(getDirection(rname, rdirection));
		return place.toString();
	}
	
	/**
	 * 获取警情地点拼接描述
	 * @param stime 警情开始或者结束时间
	 * @param jgmc 机构名称
	 * @param rname 道路名称
	 * @param rsname 路段名称
	 * @param rdirection 道路方向
	 * @param skm 起始千米值
	 * @param sm 起始百米值
	 * @param ekm 结束千米值
	 * @param em 结束百米值
	 * @return 警情地点拼接描述
	 */
	public static String getTimePlace(String stime, String jgmc,  String rname,
			String rsname, String rdirection, String skm, String sm,
			String ekm, String em, String type) {
		String place = getPlace(jgmc, rname, rsname, rdirection, skm, sm, ekm, em);
		return getTimePlace(stime, place, type);
	}
	
	public static String getTimePlace(String stime, String place, String type) {
		StringBuffer timePlace = new StringBuffer();
		timePlace.append(getTime(stime, type));
		//将时间和以英文字母或者数字开头的地点以空格分开
		String first = place.substring(0,1);
		if(first.matches("[\\d|\\w]")){
			place = " " + place;
		}
		timePlace.append(place + "，");
		return timePlace.toString();
	}
	
	/**
	 * 获取警情发生的简单描述
	 * @param decorate 警情修饰
	 * @param etype 警情类型代码
	 * @return 警情发生的简单描述
	 */
	public static String getStartDesc(String decorate, String etype){
		String desc = "";
		if (AlarmDao.codeToDesc.containsKey(etype)) {
			String symbol;
			String action;
			String type = AlarmDao.codeToDesc.get(etype);
			if (etype.equals(AlarmDao.BUILD_CODE)) {
				action = "进行";
				if(decorate == null || decorate.equals("")){
					action = "";
					type = "施工";
				}
				symbol = "，";
			} else  if(etype.equals(AlarmDao.CROWD_CODE)) {
				action = "造成";
				symbol = "。";
			}else {
				action = "发生一起";
				symbol = "。";
			}
			desc += action;
			if (decorate != null) {
				desc += decorate;
			}
			desc += type;
			desc += symbol;
		}
		return desc;
	}
	
	/**
	 * 获取警情结束的简单描述
	 * @param decorate 警情修饰
	 * @param etype 警情类型代码
	 * @param etime 结束时间
	 * @return 警情结束的简单描述
	 */
	public static String getOverDesc(String decorate, String etype, String etime) {
		String desc = "";
		if (AlarmDao.codeToDesc.containsKey(etype)) {
			String action;
			String result;
			if (etype.equals(AlarmDao.BUILD_CODE)) {
				action = "的";
				result = "完成了";
			} else {
				action = "的";
				result = "结束了";
			}
			desc += action;
			if (decorate != null) {
				desc += decorate;
			}
			desc += AlarmDao.codeToDesc.get(etype);
			desc += "已经" + result + "。";
		}
		return desc;
	}
	
	/**
	 * 获取拥堵原因拼接描述
	 * @param reason 拥堵原因
	 * @return 拥堵原因拼接描述
	 */
	public static String getCrowdReason(String reason){
		if(reason != null && !reason.equals("")){
			reason = reason.replace(" ", "、");
			if(reason.endsWith("、")){
				reason = reason.substring(0,reason.length()-1);
			}
			reason = "因" + reason;
		}else{
			reason = "";
		}
		return reason;
	}
	
	public static String getCrowdDegree(String skm, String sm, String ekm,
			String em) {
		String degree = "";
//		if(skm != null && !skm.equals("") && ekm != null && !ekm.equals("")){
//			try {
//				int iskm = Integer.parseInt(skm) * 1000;
//				if (sm != null && !sm.equals("")) {
//					int ism = Integer.parseInt(sm);
//					iskm += ism;
//				}
//				int iekm = Integer.parseInt(ekm) * 1000;
//				if (em != null && !em.equals("")) {
//					int iem = Integer.parseInt(em);
//					iekm += iem;
//				}
//				float range = (iekm - iskm) / 1000f;
//				range = Math.round(range*10)/10f;
//				degree = "阻塞长达约"+range+"公里的";
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		return degree;
	}
	
	/**
	 * 获取拥堵交警提示拼接描述
	 * @param remind 拥堵交警提示
	 * @return 拥堵交警提示拼接描述
	 */
	public static String getCrowdRemind(String remind){
		if (remind != null && !(remind = remind.trim()).equals("")) {
			remind = "交警提示：" + remind;
			if(!remind.endsWith("。")){
				remind += "。";
			}
			return remind;
		}
		return "";
	}
	
	/**
	 * 获取拥堵拼接描述
	 * @param stime 开始时间
	 * @param jgmc 机构名称
	 * @param rname 道路名称
	 * @param rsname 路段名称
	 * @param skm 起始千米值
	 * @param sm 起始百米值
	 * @param ekm 结束千米值
	 * @param em 结束百米值
	 * @param rdirection 道路方向代码
	 * @param reason 拥堵原因
	 * @return 拥堵原因拼接描述
	 */
	public static String getStartCrowd( String stime,String jgmc, String rname, String rsname, String skm, String sm, String ekm, String em, String rdirection, String reason){
		StringBuffer crowd = new StringBuffer();
		crowd.append(getTimePlace( stime, jgmc,rname, rsname, rdirection, skm, sm, ekm, em,AlarmDao.CROWD_CODE))
		.append(getCrowdReason(reason))
		.append(getStartDesc(getCrowdDegree(skm, sm, ekm, em)+getDirection(rdirection),AlarmDao.CROWD_CODE));
		return crowd.toString();
	}
	
	/**
	 * 获取拥堵拼接描述
	 * @param stime 开始时间
	 * @param jgmc 机构名称
	 * @param rname 道路名称
	 * @param rsname 路段名称
	 * @param skm 起始千米值
	 * @param sm 起始百米值
	 * @param ekm 结束千米值
	 * @param em 结束百米值
	 * @param rdirection 道路方向代码
	 * @param reason 拥堵原因
	 * @param overTime 拥堵预计结束或结束时间
	 * @return 拥堵原因拼接描述
	 */
	public static String getStartCrowd( String stime,String jgmc, String rname, String rsname, String skm, String sm, String ekm, String em, String rdirection, String reason, String gzcs, String overTime){
		StringBuffer crowd = new StringBuffer();
		crowd.append(getTimePlace( stime, jgmc,rname, rsname, rdirection, skm, sm, ekm, em,AlarmDao.CROWD_CODE))
		.append(getCrowdReason(reason))
		.append(getStartDesc(getCrowdDegree(skm, sm, ekm, em)+getDirection(rdirection),AlarmDao.CROWD_CODE))
		.append(gzcs + "，")
		.append(getPlanOverTime(overTime, AlarmDao.CROWD_CODE));
		return crowd.toString();
	}
	
	/**
	 * 获取拥堵拼接描述
	 * @param stime 开始时间
	 * @param jgmc 机构名称
	 * @param rname 道路名称
	 * @param rsname 路段名称
	 * @param skm 起始千米值
	 * @param sm 起始百米值
	 * @param ekm 结束千米值
	 * @param em 结束百米值
	 * @param rdirection 道路方向代码
	 * @param reason 拥堵原因
	 * @param overTime 拥堵预计结束或结束时间
	 * @param premind 拥堵交警提示
	 * @return 拥堵原因拼接描述
	 */
	public static String getStartCrowd( String stime,String jgmc, String rname, String rsname, String skm, String sm, String ekm, String em, String rdirection, String reason, String gzcs, String overTime, String premind){
		StringBuffer crowd = new StringBuffer();
		crowd.append(getStartCrowd( stime, jgmc, rname, rsname,  skm,  sm,  ekm,  em,  rdirection,  reason,gzcs, overTime))
		.append(getCrowdRemind(premind));
		return crowd.toString();
	}
	
	/**
	 * <pre>
	 * 获取施工占道预计时间拼接描述
	 * </pre>
	 * @param etime 预计时间
	 * @return 时间拼接描述
	 */
	public static String getPlanOverTime(String etime, String type){
		String over = "恢复正常交通";
		if (type.equals(AlarmDao.BUILD_CODE)) {
			over = "完成";
		}
		return arround(etime, "预计在", over + "。");
	}
	
	/**
	 * 获取占用车道拼接描述
	 * @param crnum 占用车道数
	 * @return 占用车道拼接描述
	 */
	public static String getCarRoad(String crnum) {
		String carRoad = "封闭全部车道";
		if (crnum != null && !crnum.equals("")) {
			carRoad = "占用" + crnum + "条车道";
		}
		return carRoad;
	}
	
	/**
	 * 获取分流措施拼接描述
	 * @param plan 分流措施
	 * @return 分流措施拼接描述
	 */
	public static String getPlan(String plan) {
		if (plan != null && !plan.equals("")) {
			return "分流管制措施：" + plan;
		}
		return "";
	}
	
	/**
	 * 获取近期路况提示拼接描述
	 * @param recentRoadState 近期路况提示
	 * @return 近期路况提示拼接描述
	 */
	public static String getRecentRoadState(String recentRoadState) {
		String desc = "";
		if (recentRoadState != null && !recentRoadState.equals("")) {
			desc = "近期路况提示：" + recentRoadState;
		}
		return desc;
	}
	
	
	/**
	 * 获取开始施工拼接描述
	 * @param stime 开始时间
	 * @param jgmc 机构名称
	 * @param rname 道路名称
	 * @param rsname 路段名称
	 * @param skm 起始千米值
	 * @param sm 起始百米值
	 * @param ekm 结束千米值
	 * @param em 结束百米值
	 * @param rdirection 道路方向代码
	 * @param crnum 占用车道数
	 * @param roadState 路况
	 * @param plan 分流措施
	 * @return 施工拼接描述
	 */
	public static String getStartBulid( String stime,String jgmc, String rname, String rsname, String skm, String sm, String ekm, String em, String rdirection, String petime, String crnum, String roadState, String plan){
		StringBuffer build = new StringBuffer();
		build.append(getTimePlace( stime, jgmc,rname, rsname, rdirection, skm, sm, ekm, em,AlarmDao.BUILD_CODE))
		.append(getCarRoad(crnum))
		.append(getStartDesc(getDirection(rdirection),AlarmDao.BUILD_CODE))
		.append(arround(roadState, "", "，"))
		.append(getPlanOverTime(petime, AlarmDao.BUILD_CODE))
		.append(getPlan(plan));
		return build.toString();
	}
	
	/**
	 * 获取死亡人数受伤人数的拼接描述
	 * @param wnum 受伤人数
	 * @param dnum 死亡人数
	 * @return 受伤人数死亡人数的拼接描述
	 */
	public static String getDieWound( String dnum,String wnum) {
		String woundDie = "";
		if (dnum != null && !dnum.equals("")) {
			if (dnum.equals("0")) {
				woundDie += "无人死亡";
			} else {
				woundDie += "死亡" + dnum + "人";
			}
		}
		if (wnum != null && !wnum.equals("")) {
			if (!woundDie.equals("")) {
				woundDie += "、";
			}
			if (wnum.equals("0")) {
				woundDie += "无人受伤";
			} else {
				woundDie += "受伤" + wnum + "人";
			}
		}
		if(!woundDie.equals("")){
			woundDie += "的";
		}
		return woundDie;
	}
	
	
	/**
	 * 获取开始事故拼接描述
	 * @param stime 开始时间
	 * @param jgmc 机构名称
	 * @param rname 道路名称
	 * @param rsname 路段名称
	 * @param skm 起始千米值
	 * @param sm 起始百米值
	 * @param ekm 结束千米值
	 * @param em 结束百米值
	 * @param rdirection 道路方向代码
	 * @param wnum 受伤
	 * @param dnum 死亡
	 * @return 事故拼接描述
	 */
	public static String getStartAcc(String stime, String jgmc, String rname,
			String rsname, String skm, String sm, String ekm, String em,
			String rdirection, String dnum, String wnum) {
		StringBuffer acc = new StringBuffer();
		acc.append( getTimePlace(stime, jgmc, rname, rsname, rdirection, skm, sm, ekm, em,AlarmDao.ACC_CODE))
		   .append( getStartDesc( getDieWound(dnum,wnum) + getDirection(rdirection), AlarmDao.ACC_CODE));
		return acc.toString();
	}
	
	/**
	 * 获取警情结束拼接描述
	 * @param stime 开始时间
	 * @param jgmc 机构名称
	 * @param rname 道路名称
	 * @param rsname 路段名称
	 * @param skm 起始千米值
	 * @param sm 起始百米值
	 * @param ekm 结束千米值
	 * @param em 结束百米值
	 * @param rdirection 道路方向代码
	 * @param type 警情类型代码
	 * @param etime 结束时间
	 * @return 警情结束拼接描述
	 */
	public static String getOverAlarm(String stime, String jgmc, String rname, String rsname, String skm, String sm, String ekm, String em, String rdirection, String type,String etime){
		StringBuffer alarm = new StringBuffer();
		alarm.append(getTimePlace(stime, jgmc, rname, rsname, rdirection, skm, sm, ekm, em,type));
		alarm.deleteCharAt(alarm.length()-1);
		alarm.append(getOverDesc(getDirection(rdirection),type,etime));
		return alarm.toString();
	}
	
	/**
	 * 更新结束交警提示
	 * @param aid 警情编号
	 * @param ptype 发布类型
	 * @return 是否更新成功
	 */
	public static boolean updateOverRemind(String aid, String ptype) {
		boolean isSuccess = false;
		String sql = "select a.alarmregion,a.reportunit,"//0,1
				+ "a.reportperson,a.roadid,a.roadname,"//2,3,4
				+ "a.roaddirection,a.kmvalue,a.mvalue,"//5,6,7
				+ "a.endkmvalue,a.endmvalue,to_char(a.casehappentime,'yyyy-mm-dd hh24:mi'),"//8,9,10
				+ "to_char(a.CaseEndTime,'yyyy-mm-dd hh24:mi'),a.eventtype" //11,12
				+ " from t_attemper_alarm a"
				+ " where a.alarmid='" + aid + "'";
		Object[] alarm = DBHandler.getLineResult(sql);
		if (alarm != null) {
			String rtime = Constant.getCurrentTime(false).substring(0,16);
			String stime = Constant.nvl(alarm[10]);
			stime = simplifyTime(rtime, stime);
			String remindInfo = AlarmInfoJoin.getOverAlarm(stime, Constant.nvl(alarm[0]), Constant
					.nvl(alarm[3]), Constant.nvl(alarm[4]), Constant
					.nvl(alarm[6]), Constant.nvl(alarm[7]), Constant
					.nvl(alarm[8]), Constant.nvl(alarm[9]), Constant
					.nvl(alarm[5]), Constant.nvl(alarm[12]),Constant.nvl(alarm[11]));
			//Modify by Xiayx 2012-2-24
			//每次警情结束时，更新交警提示信息改为新增交警提示信息
			isSuccess = prcore.insert(PoliceRemindDao.formPoliceRemind(aid, rtime, (String)alarm[1], (String)alarm[0], (String)alarm[2],"1", ptype, remindInfo,"2")) != null;
			//Modification finished
		}
		return isSuccess;
	}
	
	public static String arround(String info, String before, String after){
		String desc = "";
		if(info != null && !info.equals("")){
			desc = before + info + after;
		}
		return desc;
	}
	
	/**
	 * <pre>
	 * 简化时间
	 * 忽略结束时间中和填报时间相同的部分，精确至小时
	 * </pre>
	 * @param rtime 填报时间
	 * @param etime 结束时间
	 * @return 简化时间
	 * @throws ParseException 
	 */
	public static String simplifyTime(String rtime, String etime){
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Calendar crtime = Calendar.getInstance();
		Calendar cetime = Calendar.getInstance();
		try {
			crtime.setTime(formater.parse(rtime));
			cetime.setTime(formater.parse(etime));
		} catch (ParseException e) {
			logger.error("简化警情中相关时间时发生异常",e);
			return etime;
		}
		int[] units = {Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,Calendar.HOUR_OF_DAY,Calendar.MINUTE};
		int[] unitIndexs = {0, 5, 8, 11, 14};
		String sformat = "yyyy年M月d日H:m";
		int[] sunitIndexs = {0, 5, 7, 9, 11};
		int limit = 3;
		String time = etime.substring(unitIndexs[limit]);
		for (int i = 0; i < units.length && i < limit; i++) {
			if(crtime.get(units[i]) != cetime.get(units[i])){
				int index = sunitIndexs[i];
				if(units[i] == Calendar.DAY_OF_MONTH){
					index = sunitIndexs[i-1];
				}
				formater.applyPattern(sformat.substring(index));
				time = formater.format(cetime.getTime());
				break;
			}
		}
		return time;
	}
	
	
	public static void main(String[] args) throws Exception {
		
	}
}
