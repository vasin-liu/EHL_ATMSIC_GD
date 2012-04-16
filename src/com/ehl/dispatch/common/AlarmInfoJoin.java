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
 * ������Ϣƴ����
 * ��������Ϣ�����ֶ�ƴ�ӳ�һ�λ���������������ʾ����ʾ���ݡ�
 * ͬʱ��Ҳ����ֱ�Ӳ���������͵Ľ�����ʾ��Ϣ��
 * <b>���ƴ�ӹ���</b>
 * �ֶ�Ϊnull���߿��ַ��ģ����ؿ��ַ��Ա�׷��ƴ�ӣ�
 * �������ֶ�ǰ����ߺ���������Σ��ٸ�����Ҫ��ĩβ���Ϸ���
 * <b>������ʾ��Ϣ��ɽṹ��</b>
 * �ڡ�ʱ��(���鿪ʼ���߽���ʱ��)�������ص�(������·���ơ���Ͻ��λ��·�����ơ���·���������)����
 * ��������������(�����¹ʡ�ӵ�¡�ʩ��ռ��)����������Ϣ(����ӵ��ԭ�򡢷������ƴ�ʩ�ȵ�)��
 * ���磺
 * 2011-08-22 18:55���ڹ�����ٹ��ݷ�خ��ӹ�Ͻ��ý��·�α��������ڷ���K10+50����K60+900�״���
 * ������һ��ͨӵ�¡�ӵ��ԭ��������¹ʡ�ʩ����������ʾ����Ϊǰ��ӵ�£������С�
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
	 * ��ȡʱ��ƴ������
	 * </pre>
	 * @param time ʱ��
	 * @return ʱ��ƴ������
	 */
	public static String getTime(String time, String type) {
		String desc = "";
//		if (type.equals(AlarmDao.BUILD_CODE)) {
//			desc = arround(time, "��", "��ʼ��");
//		} else {
//			desc = time;
//		}
		desc = time;
		return desc;
	}
	
	/**
	 * ��ȡ·��ƴ������
	 * @param rsection ·������
	 * @return ·��ƴ������
	 */
	public static String getRoadSection(String rsection) {
		if (rsection != null && !rsection.equals("")) {
			if (!rsection.endsWith("·��")) {
				rsection += "·��";
			}
			return rsection;
		}
		return "";
	}
	
	/**
	 * ��ȡ��·����Ϊ˫��ʱ��ƴ������
	 * @param rdcode ��·�������
	 * @return ��·����ƴ������
	 */
	public static String getDirection(String rdcode) {
		return rdcode.equals("2") ? "˫��" : "";
	}
	
	/**
	 * ��ȡ��·����Ϊ����ʱ��ƴ������
	 * @param rname ��·����
	 * @param rdcode ��·�������
	 * @return ��·����ƴ������
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
						desc = beginEnd[rd] + "��" + beginEnd[beginEnd.length - 1 - rd]+"����";
					}
				}
			}else{
				rdcode = rdcode.trim();
				if(!rdcode.equals("") && !rdcode.endsWith("����")){
					desc = rdcode + "����";
				}
			}
		}
		return desc;
	}
	
	/**
	 * <pre>
	 * ��ȡ��·���ƴ������
	 * ��·���ƴ�Ӹ�ʽ��K[xx]+[xx]��
	 * </pre>
	 * @param km ǧ��ֵ
	 * @param m ����ֵ
	 * @return ��·���ƴ������
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
			lcb += m + "��";
		}
		return lcb;
	}
	
	/**
	 * <pre>
	 * ��ȡ��·�������ƴ������
	 * ��·�������ƴ�Ӹ�ʽ��K[xx]+[xx]����K[xx]+[xx]�״�
	 * </pre>
	 * @param skm ��ʼǧ��ֵ
	 * @param sm ��ʼ����ֵ
	 * @param ekm ����ǧ��ֵ
	 * @param em ��������ֵ
	 * @return ��·�������ƴ������
	 */
	public static String getLcbRange(String skm, String sm, String ekm, String em){
		String lcbRange = getLcb(skm, sm);
		String erange = getLcb(ekm, em);
		if(!erange.equals("")){
			if(!lcbRange.equals("")){
				lcbRange += "��";
			}
			lcbRange += erange;
		}
		if(lcbRange.equals("K0+0����K0+0��")){
			lcbRange = "";
		}
		if(!lcbRange.equals("")){
			lcbRange = "��"+lcbRange + "��";
		}
		return lcbRange;
	}
	
	/**
	 * ��ȡ����ص�ƴ������
	 * @param jgmc ��������
	 * @param rname ��·����
	 * @param rsname ·������
	 * @param rdirection ��·����
	 * @param skm ��ʼǧ��ֵ
	 * @param sm ��ʼ����ֵ
	 * @param ekm ����ǧ��ֵ
	 * @param em ��������ֵ
	 * @return ����ص�ƴ������
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
	 * ��ȡ����ص�ƴ������
	 * @param stime ���鿪ʼ���߽���ʱ��
	 * @param jgmc ��������
	 * @param rname ��·����
	 * @param rsname ·������
	 * @param rdirection ��·����
	 * @param skm ��ʼǧ��ֵ
	 * @param sm ��ʼ����ֵ
	 * @param ekm ����ǧ��ֵ
	 * @param em ��������ֵ
	 * @return ����ص�ƴ������
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
		//��ʱ�����Ӣ����ĸ�������ֿ�ͷ�ĵص��Կո�ֿ�
		String first = place.substring(0,1);
		if(first.matches("[\\d|\\w]")){
			place = " " + place;
		}
		timePlace.append(place + "��");
		return timePlace.toString();
	}
	
	/**
	 * ��ȡ���鷢���ļ�����
	 * @param decorate ��������
	 * @param etype �������ʹ���
	 * @return ���鷢���ļ�����
	 */
	public static String getStartDesc(String decorate, String etype){
		String desc = "";
		if (AlarmDao.codeToDesc.containsKey(etype)) {
			String symbol;
			String action;
			String type = AlarmDao.codeToDesc.get(etype);
			if (etype.equals(AlarmDao.BUILD_CODE)) {
				action = "����";
				if(decorate == null || decorate.equals("")){
					action = "";
					type = "ʩ��";
				}
				symbol = "��";
			} else  if(etype.equals(AlarmDao.CROWD_CODE)) {
				action = "���";
				symbol = "��";
			}else {
				action = "����һ��";
				symbol = "��";
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
	 * ��ȡ��������ļ�����
	 * @param decorate ��������
	 * @param etype �������ʹ���
	 * @param etime ����ʱ��
	 * @return ��������ļ�����
	 */
	public static String getOverDesc(String decorate, String etype, String etime) {
		String desc = "";
		if (AlarmDao.codeToDesc.containsKey(etype)) {
			String action;
			String result;
			if (etype.equals(AlarmDao.BUILD_CODE)) {
				action = "��";
				result = "�����";
			} else {
				action = "��";
				result = "������";
			}
			desc += action;
			if (decorate != null) {
				desc += decorate;
			}
			desc += AlarmDao.codeToDesc.get(etype);
			desc += "�Ѿ�" + result + "��";
		}
		return desc;
	}
	
	/**
	 * ��ȡӵ��ԭ��ƴ������
	 * @param reason ӵ��ԭ��
	 * @return ӵ��ԭ��ƴ������
	 */
	public static String getCrowdReason(String reason){
		if(reason != null && !reason.equals("")){
			reason = reason.replace(" ", "��");
			if(reason.endsWith("��")){
				reason = reason.substring(0,reason.length()-1);
			}
			reason = "��" + reason;
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
//				degree = "��������Լ"+range+"�����";
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		return degree;
	}
	
	/**
	 * ��ȡӵ�½�����ʾƴ������
	 * @param remind ӵ�½�����ʾ
	 * @return ӵ�½�����ʾƴ������
	 */
	public static String getCrowdRemind(String remind){
		if (remind != null && !(remind = remind.trim()).equals("")) {
			remind = "������ʾ��" + remind;
			if(!remind.endsWith("��")){
				remind += "��";
			}
			return remind;
		}
		return "";
	}
	
	/**
	 * ��ȡӵ��ƴ������
	 * @param stime ��ʼʱ��
	 * @param jgmc ��������
	 * @param rname ��·����
	 * @param rsname ·������
	 * @param skm ��ʼǧ��ֵ
	 * @param sm ��ʼ����ֵ
	 * @param ekm ����ǧ��ֵ
	 * @param em ��������ֵ
	 * @param rdirection ��·�������
	 * @param reason ӵ��ԭ��
	 * @return ӵ��ԭ��ƴ������
	 */
	public static String getStartCrowd( String stime,String jgmc, String rname, String rsname, String skm, String sm, String ekm, String em, String rdirection, String reason){
		StringBuffer crowd = new StringBuffer();
		crowd.append(getTimePlace( stime, jgmc,rname, rsname, rdirection, skm, sm, ekm, em,AlarmDao.CROWD_CODE))
		.append(getCrowdReason(reason))
		.append(getStartDesc(getCrowdDegree(skm, sm, ekm, em)+getDirection(rdirection),AlarmDao.CROWD_CODE));
		return crowd.toString();
	}
	
	/**
	 * ��ȡӵ��ƴ������
	 * @param stime ��ʼʱ��
	 * @param jgmc ��������
	 * @param rname ��·����
	 * @param rsname ·������
	 * @param skm ��ʼǧ��ֵ
	 * @param sm ��ʼ����ֵ
	 * @param ekm ����ǧ��ֵ
	 * @param em ��������ֵ
	 * @param rdirection ��·�������
	 * @param reason ӵ��ԭ��
	 * @param overTime ӵ��Ԥ�ƽ��������ʱ��
	 * @return ӵ��ԭ��ƴ������
	 */
	public static String getStartCrowd( String stime,String jgmc, String rname, String rsname, String skm, String sm, String ekm, String em, String rdirection, String reason, String gzcs, String overTime){
		StringBuffer crowd = new StringBuffer();
		crowd.append(getTimePlace( stime, jgmc,rname, rsname, rdirection, skm, sm, ekm, em,AlarmDao.CROWD_CODE))
		.append(getCrowdReason(reason))
		.append(getStartDesc(getCrowdDegree(skm, sm, ekm, em)+getDirection(rdirection),AlarmDao.CROWD_CODE))
		.append(gzcs + "��")
		.append(getPlanOverTime(overTime, AlarmDao.CROWD_CODE));
		return crowd.toString();
	}
	
	/**
	 * ��ȡӵ��ƴ������
	 * @param stime ��ʼʱ��
	 * @param jgmc ��������
	 * @param rname ��·����
	 * @param rsname ·������
	 * @param skm ��ʼǧ��ֵ
	 * @param sm ��ʼ����ֵ
	 * @param ekm ����ǧ��ֵ
	 * @param em ��������ֵ
	 * @param rdirection ��·�������
	 * @param reason ӵ��ԭ��
	 * @param overTime ӵ��Ԥ�ƽ��������ʱ��
	 * @param premind ӵ�½�����ʾ
	 * @return ӵ��ԭ��ƴ������
	 */
	public static String getStartCrowd( String stime,String jgmc, String rname, String rsname, String skm, String sm, String ekm, String em, String rdirection, String reason, String gzcs, String overTime, String premind){
		StringBuffer crowd = new StringBuffer();
		crowd.append(getStartCrowd( stime, jgmc, rname, rsname,  skm,  sm,  ekm,  em,  rdirection,  reason,gzcs, overTime))
		.append(getCrowdRemind(premind));
		return crowd.toString();
	}
	
	/**
	 * <pre>
	 * ��ȡʩ��ռ��Ԥ��ʱ��ƴ������
	 * </pre>
	 * @param etime Ԥ��ʱ��
	 * @return ʱ��ƴ������
	 */
	public static String getPlanOverTime(String etime, String type){
		String over = "�ָ�������ͨ";
		if (type.equals(AlarmDao.BUILD_CODE)) {
			over = "���";
		}
		return arround(etime, "Ԥ����", over + "��");
	}
	
	/**
	 * ��ȡռ�ó���ƴ������
	 * @param crnum ռ�ó�����
	 * @return ռ�ó���ƴ������
	 */
	public static String getCarRoad(String crnum) {
		String carRoad = "���ȫ������";
		if (crnum != null && !crnum.equals("")) {
			carRoad = "ռ��" + crnum + "������";
		}
		return carRoad;
	}
	
	/**
	 * ��ȡ������ʩƴ������
	 * @param plan ������ʩ
	 * @return ������ʩƴ������
	 */
	public static String getPlan(String plan) {
		if (plan != null && !plan.equals("")) {
			return "�������ƴ�ʩ��" + plan;
		}
		return "";
	}
	
	/**
	 * ��ȡ����·����ʾƴ������
	 * @param recentRoadState ����·����ʾ
	 * @return ����·����ʾƴ������
	 */
	public static String getRecentRoadState(String recentRoadState) {
		String desc = "";
		if (recentRoadState != null && !recentRoadState.equals("")) {
			desc = "����·����ʾ��" + recentRoadState;
		}
		return desc;
	}
	
	
	/**
	 * ��ȡ��ʼʩ��ƴ������
	 * @param stime ��ʼʱ��
	 * @param jgmc ��������
	 * @param rname ��·����
	 * @param rsname ·������
	 * @param skm ��ʼǧ��ֵ
	 * @param sm ��ʼ����ֵ
	 * @param ekm ����ǧ��ֵ
	 * @param em ��������ֵ
	 * @param rdirection ��·�������
	 * @param crnum ռ�ó�����
	 * @param roadState ·��
	 * @param plan ������ʩ
	 * @return ʩ��ƴ������
	 */
	public static String getStartBulid( String stime,String jgmc, String rname, String rsname, String skm, String sm, String ekm, String em, String rdirection, String petime, String crnum, String roadState, String plan){
		StringBuffer build = new StringBuffer();
		build.append(getTimePlace( stime, jgmc,rname, rsname, rdirection, skm, sm, ekm, em,AlarmDao.BUILD_CODE))
		.append(getCarRoad(crnum))
		.append(getStartDesc(getDirection(rdirection),AlarmDao.BUILD_CODE))
		.append(arround(roadState, "", "��"))
		.append(getPlanOverTime(petime, AlarmDao.BUILD_CODE))
		.append(getPlan(plan));
		return build.toString();
	}
	
	/**
	 * ��ȡ������������������ƴ������
	 * @param wnum ��������
	 * @param dnum ��������
	 * @return ������������������ƴ������
	 */
	public static String getDieWound( String dnum,String wnum) {
		String woundDie = "";
		if (dnum != null && !dnum.equals("")) {
			if (dnum.equals("0")) {
				woundDie += "��������";
			} else {
				woundDie += "����" + dnum + "��";
			}
		}
		if (wnum != null && !wnum.equals("")) {
			if (!woundDie.equals("")) {
				woundDie += "��";
			}
			if (wnum.equals("0")) {
				woundDie += "��������";
			} else {
				woundDie += "����" + wnum + "��";
			}
		}
		if(!woundDie.equals("")){
			woundDie += "��";
		}
		return woundDie;
	}
	
	
	/**
	 * ��ȡ��ʼ�¹�ƴ������
	 * @param stime ��ʼʱ��
	 * @param jgmc ��������
	 * @param rname ��·����
	 * @param rsname ·������
	 * @param skm ��ʼǧ��ֵ
	 * @param sm ��ʼ����ֵ
	 * @param ekm ����ǧ��ֵ
	 * @param em ��������ֵ
	 * @param rdirection ��·�������
	 * @param wnum ����
	 * @param dnum ����
	 * @return �¹�ƴ������
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
	 * ��ȡ�������ƴ������
	 * @param stime ��ʼʱ��
	 * @param jgmc ��������
	 * @param rname ��·����
	 * @param rsname ·������
	 * @param skm ��ʼǧ��ֵ
	 * @param sm ��ʼ����ֵ
	 * @param ekm ����ǧ��ֵ
	 * @param em ��������ֵ
	 * @param rdirection ��·�������
	 * @param type �������ʹ���
	 * @param etime ����ʱ��
	 * @return �������ƴ������
	 */
	public static String getOverAlarm(String stime, String jgmc, String rname, String rsname, String skm, String sm, String ekm, String em, String rdirection, String type,String etime){
		StringBuffer alarm = new StringBuffer();
		alarm.append(getTimePlace(stime, jgmc, rname, rsname, rdirection, skm, sm, ekm, em,type));
		alarm.deleteCharAt(alarm.length()-1);
		alarm.append(getOverDesc(getDirection(rdirection),type,etime));
		return alarm.toString();
	}
	
	/**
	 * ���½���������ʾ
	 * @param aid ������
	 * @param ptype ��������
	 * @return �Ƿ���³ɹ�
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
			//ÿ�ξ������ʱ�����½�����ʾ��Ϣ��Ϊ����������ʾ��Ϣ
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
	 * ��ʱ��
	 * ���Խ���ʱ���к��ʱ����ͬ�Ĳ��֣���ȷ��Сʱ
	 * </pre>
	 * @param rtime �ʱ��
	 * @param etime ����ʱ��
	 * @return ��ʱ��
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
			logger.error("�򻯾��������ʱ��ʱ�����쳣",e);
			return etime;
		}
		int[] units = {Calendar.YEAR,Calendar.MONTH,Calendar.DAY_OF_MONTH,Calendar.HOUR_OF_DAY,Calendar.MINUTE};
		int[] unitIndexs = {0, 5, 8, 11, 14};
		String sformat = "yyyy��M��d��H:m";
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
