package com.ehl.dynamicinfo.tgs.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.appframe.common.Setting;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;
import com.ehl.util.Datex;

/**
 * �����������ݷ�����
 * 
 * @author xiayx
 * 
 */
public class BayonetFlowDao {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * <pre>
	 * ��ȡ���ڣ�Ĭ�ϸ�ʽΪ"��ʼ����,��������"
	 * ���ڷ�Χ��ϵͳ����������֮��
	 * </pre>
	 * @param date ����
	 * @return ����
	 */
	private String getDate(String date) {
		if (date == null) {
			date = ",";
		}
		if (date.indexOf(",") != -1) {
			String sdate = Setting.getString("publish_time");
			Calendar calendar = Calendar.getInstance();
			String edate = new SimpleDateFormat("yyyy-MM-dd").format(calendar .getTime());
			String[] dates = date.split(",");
			if (dates[0].equals("") || Datex.dayDiffer(dates[0], sdate) < 0) {
				dates[0] = sdate;
			}
			if (dates[1].equals("") || Datex.dayDiffer(dates[1], edate) > 0) {
				dates[1] = edate;
			}
			if(Datex.dayDiffer(dates[0], dates[1]) > 0){
				dates[0] = dates[1];
			}
			date = dates[0] + "," + dates[1];
		}
		return date;
	}
	
	/**
	 * ɸѡ�ӿ�״̬
	 * @param sql sql���
	 * @param state �ӿ�״̬
	 * @param name ����
	 * @param differ �����
	 */
	private void siftState(StringBuffer sql,String state,String name,int differ){
		String siftState = "";
		if(state.equals("1")){
			siftState += name+".incity="+differ+" and "+name+".outcity="+differ;
		}else if(state.equals("2")){
			siftState += " nvl("+name+".incity,0)!="+differ+" or nvl("+name+".outcity,0)!="+differ;
		}
		if(!siftState.equals("")){
			sql.append(" where " + siftState);
		}
	}
	
	/**
	 * ͳ��һ�������ڣ���������δ��ȡ������������
	 * @param id ���ڱ��
	 * @param date ����
	 * @param state �ӿ�״̬ 0��ȫ����1��������2���쳣
	 * @return ��������
	 */
	public Object[][] statisWrongDays(String id,String date, String state) {
		//��������ɸѡ�Լ����������������
		int differ = 0;
		String siftDate = "";
		date = getDate(date);
		if(date.indexOf(",")==-1){
			siftDate = "to_char(time,'yyyy-mm-dd')='"+date+"'";
		}else{
			String[] dates = date.split(",");
			differ = Math.abs(Datex.dayDiffer(dates[0], dates[1]));
			siftDate = Sql.getWhereDate("time", dates[0], dates[1], 2);
		}
		differ++;
		/*
		//ͳ�Ƹ�������ÿ���Сʱ��
		StringBuffer hourCount = new StringBuffer();
		hourCount.append("select kkid, to_char(time, 'yyyy-mm-dd') time")
		.append(", fx, count(flow) num");
		hourCount.append(" from v_tfm_flow");
		hourCount.append(" where " + siftDate + " and " + Sql.siftId("kkid", id));
		hourCount.append(" group by kkid, to_char(time, 'yyyy-mm-dd'), fx");
		hourCount.append(" having count(flow) = 24");
		//ͳ�Ƹ�������������һ��24Сʱ�������ݣ�����������Ϊ��������ת����׼��
		StringBuffer dayCount = new StringBuffer();
		dayCount.append("select kkid,decode(fx,1,count(kkid),0) incity")
		.append(",decode(fx,2,count(kkid),0) outcity");
		dayCount.append(" from ("+hourCount+")");
//		dayCount.append(" where num = 24");
		dayCount.append(" group by kkid, fx");
		*/
		//ͳ�Ƹ�������һ�������ڣ�ÿ�����24��Сʱ��������Сʱ��
		StringBuffer hoursCount = new StringBuffer();
		hoursCount.append("select kkid,decode(fx, 1, count(kkid), 0) incity")
		.append(",decode(fx, 2, count(kkid), 0) outcity");
		hoursCount.append(" from v_tfm_flow");
		hoursCount.append(" where " + siftDate + " and " + Sql.siftMulti("kkid", id));
		hoursCount.append(" group by kkid, to_char(time, 'yyyy-mm-dd'), fx");
		hoursCount.append(" having count(flow) = 24");
		//������ת�в���Сʱ����������
		StringBuffer columnline = new StringBuffer();
		columnline.append(" select kkid,sum(incity)/24 incity, sum(outcity)/24 outcity");
		columnline.append(" from ("+hoursCount+")");
		columnline.append(" group by kkid");
		//���п���
		StringBuffer allBayonet = new StringBuffer();
		allBayonet.append(" select roadsegid kkid,roadsegname kkmc");
		allBayonet.append(" from t_road_seginfo");
		allBayonet.append(" where roadid = 'SJKK'" + " and " + Sql.siftMulti("roadsegid", id));
		//���п��ڵ�ͳ������
		StringBuffer allCount = new StringBuffer();
		date = date.replace(",", " - ");
		allCount.append(" select kk.kkid,kk.kkmc,'"+date+"' time")
		.append(",nvl2(num.incity,"+differ+"-num.incity,"+differ+") incity")
		.append(",nvl2(num.outcity,"+differ+"-num.outcity,"+differ+") outcity");
		allCount.append(" from ("+allBayonet+") kk");
		allCount.append(" left join ("+columnline+") num");
		allCount.append(" on kk.kkid=num.kkid");
		////ɸѡ�ӿ�״̬
		siftState(allCount, state, "num", differ);
		allCount.append(" order by kk.kkmc");
		String msg = "ͳ��һ�������ڣ���������δ��ȡ������������";
		return FlowUtil.readMilte(allCount.toString(), logger, msg);
	}
	
	/**
	 * ͳ��һ�������ڣ�ĳ������ÿ��δ��ȡ��������Сʱ��
	 * @param id ���ڱ��
	 * @param date ����
	 * @param state �ӿ�״̬
	 * @return ��������
	 */
	public Object[][] statisWrongHours(String id,String date) {
		Object[][] objects = null;
		if (id != null) {
			//��������ɸѡ�Լ����������������
			int differ = 0;
			String siftDate = "";
			date = getDate(date);
			String[] dates = new String[2];
			if(date.indexOf(",")==-1){
				dates[0] = date;
				dates[1] = date;
				siftDate = "to_char(time,'yyyy-mm-dd')='"+date+"'";
			}else{
				dates = date.split(",");
				differ = Math.abs(Datex.dayDiffer(dates[0], dates[1]));
				siftDate = Sql.getWhereDate("time", dates[0], dates[1], 2);
			}
			differ++;
			//ͳ��ÿ���Сʱ��
			StringBuffer hourCount = new StringBuffer();
			hourCount.append("select to_char(time, 'yyyy-mm-dd') time")
			.append(",decode(fx, '1', count(flow), 0) incity")
			.append(",decode(fx, '2', count(flow), 0) outcity");
			hourCount.append(" from v_tfm_flow");
			String siftId = id == null ? "" : " and kkid='" + id + "'";
			hourCount.append(" where "+siftDate+siftId);
			hourCount.append(" group by to_char(time, 'yyyy-mm-dd'), fx");
			//������ת��
			StringBuffer columnLine = new StringBuffer();
			columnLine.append(" select time,sum(incity) incity,sum(outcity) outcity");
			columnLine.append(" from ("+hourCount+") num");
			columnLine.append("  group by time");
			//�������ڷ�Χ�������б�
			StringBuffer days = new StringBuffer();
			days.append(" select to_char(to_date('"+dates[0]+"','yyyy-mm-dd')+level-1,'yyyy-mm-dd') day");
			days.append(" from dual");
			days.append(" connect by level <= "+differ);
			//ÿ����Ч��Сʱ���б�
			StringBuffer daysCount = new StringBuffer();
			daysCount.append("select days.day, nvl2(num.incity,24-num.incity,24) incity, nvl2(num.outcity,24-num.outcity,24) outcity");
			daysCount.append(" from ("+days+") days");
			daysCount.append(" left join ("+columnLine+") num");
			daysCount.append(" on days.day=num.time");
			daysCount.append(" order by days.day");
			String msg = "ͳ��һ�������ڣ�ĳ������ÿ��δ��ȡ��������Сʱ��";
			objects = FlowUtil.readMilte(daysCount.toString(), logger, msg);
		}
		return objects;
	}
	
	/**
	 * ��ȡĳ������ĳ�����Сʱ������ֵ
	 */
	public Object[][] getFlow(String id, String date) {
		Object[][] objects = null;
		if(id != null && date != null){
			//ĳ������ĳ�������
			StringBuffer flow = new StringBuffer();
			flow.append("select to_char(time, 'hh24') hour,decode(fx, '1', flow, 0) incity")
			.append(",decode(fx, '2', flow, 0) outcity");
			flow.append(" from v_tfm_flow");
			flow.append(" where kkid = '"+id+"' and to_char(time, 'yyyy-mm-dd') = '"+date+"'");
			//��������ת��
			StringBuffer columnLine = new StringBuffer();
			columnLine.append(" select flow.hour,sum(incity) incity,sum(outcity) outcity");
			columnLine.append(" from ("+flow+") flow");
			columnLine.append(" group by flow.hour");
			//24��Сʱ
			StringBuffer hours = new StringBuffer();
			hours.append(" select lpad(level-1, 2, '0') hour");
			hours.append(" from dual");
			hours.append(" connect by level <= 24");
			//24��Сʱ������
			StringBuffer hoursFlow = new StringBuffer();
			hoursFlow.append(" select hours.hour,nvl(to_char(flow.incity), '--') incity")
			.append(",nvl(to_char(flow.outcity), '--') outcity");
			hoursFlow.append(" from ("+hours+") hours");
			hoursFlow.append(" left join ("+columnLine+") flow");
			hoursFlow.append(" on hours.hour=flow.hour");
			hoursFlow.append(" order by hours.hour");
			String msg = "��ȡĳ������ĳ�����������";
			objects = FlowUtil.readMilte(hoursFlow.toString(), logger, msg);
		}
		return objects;
	}

	/**
	 *  ��ȡ������Ƶ״̬��Ϣ
	 * @param state �ӿ�״̬
	 */
	public Object[][] getVideoState(String state) {
		StringBuffer video = new StringBuffer();
		video.append("select t.cpmc, t.syzt");
		video.append(" from atms_equipment_zb t");
		video.append(" where t.ssxt='TGS'");
		StringBuffer bayonet = new StringBuffer();
		bayonet.append("select t.roadsegid,t.roadsegname");
		bayonet.append(" from t_road_seginfo t where t.roadid = 'SJKK'");
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct kk.roadsegid,kk.roadsegname,video.syzt");
		sql.append(" from ("+bayonet+") kk");
		sql.append(" left join ("+video+") video");
		sql.append(" on kk.roadsegname like '%'||video.cpmc||'%'");
		String siftState = "";
		if(state.equals("1")){
			siftState = " video.syzt = '1'";
		}else if(state.equals("2")){
			siftState = " video.syzt = '0' or video.syzt is null";
		}
		if(!siftState.equals("")){
			siftState = " where" + siftState;
		}
		sql.append(siftState);
		sql.append(" order by kk.roadsegname");
		String msg = "��ȡ������Ƶ״̬��Ϣ";
		return FlowUtil.readMilte(sql.toString(), logger, msg);
	}
	
	/**
	 * ��ȡ������Ϣ
	 * @return ����
	 */
	public Object[][] getBayonet(){
		StringBuffer sql = new StringBuffer();
		sql.append("select roadsegid id,roadsegname name");
		sql.append(" from t_road_seginfo");
		sql.append(" where roadid='SJKK'");
		sql.append(" order by name");
		String msg = "��ȡ������Ϣ";
		return FlowUtil.readMilte(sql.toString(), logger, msg);
	}

	public static void main(String[] args) {
	//		BayonetFlowDao dao = new BayonetFlowDao();
	//		dao.getFlow("LPXSPSJKKLD", "2010-01-01,2012-01-01");
		}
}
