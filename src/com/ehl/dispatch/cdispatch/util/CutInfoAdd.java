package com.ehl.dispatch.cdispatch.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.common.AlarmInfoJoin;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.policeWorks.assess.dao.ScoreRecordDao;
import com.ehl.sm.base.Constant;

public class CutInfoAdd {
	
	private Logger logger = Logger.getLogger(CutInfoAdd.class);
	/**服务器启动加载当前类的时间*/
	private final static String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	/**拥堵报料最近一次执行减分时，所减分的报料信息的最大时间，默认为startTime*/
	private static String ydblLastMaxTime = startTime;
	/**
	 * 四十分钟以上未处理扣分处理<br/>
	 *
	 */
	public void cutInfoAdd () {
		try {
			// 取得拥堵信息为拥堵中且为报送40分钟后任然没有解除的拥堵信息的件数
			StringBuffer sqlBuffer = new StringBuffer("select taa.ALARMID,to_char(taa.REPORTTIME,'yyyy-mm-dd HH24:mi'),taa.alarmregion,taa.REPORTPERSON,taa.REPORTUNIT,");
			sqlBuffer.append(" taa.roadid, taa.kmvalue, taa.endkmvalue, taa.casehappentime, tcc.CONGESTIONREASON ");
			sqlBuffer.append(" from T_ATTEMPER_ALARM taa, T_ATTEMPER_CONGESTION tcc ");
			sqlBuffer.append(" where taa.EVENTTYPE = '001002' ");
			sqlBuffer.append(" and taa.EVENTSTATE = '570001' ");
			sqlBuffer.append(" and tcc.UPDATE_DATE+40/24/60 < sysdate  ");
			sqlBuffer.append(" and tcc.CUTFLG = '0' ");
			sqlBuffer.append(" and taa.alarmid = tcc.ALARMID ");
			System.out.println("3:" + sqlBuffer);
			Object[][] obj = DBHandler.getMultiResult(String.valueOf(sqlBuffer));
			
			if (obj != null ) {
				for(int i=0; i<obj.length; i++) {
					String alarmId = String.valueOf(obj[i][0]);
					String reason = "由于"+String.valueOf(obj[i][1]) + "上报，发生在" + String.valueOf(obj[i][5])
					 +AlarmInfoJoin.getLcbRange(Constant.nvl(obj[i][6]), null, Constant.nvl(obj[i][7]), null)+ 
					 "的拥堵信息，上报后40 分钟内没有进行更新或者解除，因此记录一次扣分。";
					String sql = " insert into T_OA_SCORERECORD(deptid,deptdesc,jfrq,dayid,aid,type,rtype,reason) " +
					"values('"+String.valueOf(obj[i][4])+"','"+String.valueOf(obj[i][2])+"',to_date('"+String.valueOf(obj[i][1])
					+"','yyyy-mm-dd HH24:mi'),sysdate,'"+alarmId+"','2','3','"+reason+"')";
					DBHandler.execute(sql);
					
					StringBuffer sqlBuffer1 = new StringBuffer(" update T_ATTEMPER_CONGESTION set CUTFLG = '"); 
					sqlBuffer1.append("1' where ALARMID = '");
					sqlBuffer1.append(alarmId);
					sqlBuffer1.append("'");
					DBHandler.execute(String.valueOf(sqlBuffer1));
				}
			}
		} catch(Exception e ) {
			e.printStackTrace();
			logger.equals("扣分处理失败！");
		}
	}
	
	/**
	 * 三十分钟以上未核实拥堵报料信息，扣分一次
	 */
	public static void ydblCutInfoAdd (){
		String jgidSql = "(select jgid from t_sys_department where jgmc=gxdd or othername=gxdd)";
		String sql = "select bh," + jgidSql + ",gxdd,to_char(zdxfsj,'yyyy\"年\"MM\"月\"dd\"日\" hh24\"时\"mi\"分\"'),dlmc,ldmc,qslc,zzlc,to_char(zdxfsj,'yyyy-MM-dd hh24:mi:ss')";
		sql += " from exch_t_trafficnewsfeeds_temp ";
		sql += " where sysdate-zdxfsj > 1/24/2 and jf is null";
		sql += " and (select count(1) from t_oa_scorerecord where aid=bh) = 0";
		//排除上次已经更新过的信息
		sql += " and zdxfsj > to_date('"+ydblLastMaxTime+"','yyyy-mm-dd hh24:mi:ss')";
		sql += " order by zdxfsj";
		Object[][] data = FlowUtil.readMilte(sql);
		if (data != null) {
			ydblLastMaxTime = StringHelper.obj2str(data[data.length-1][8],ydblLastMaxTime);
			ScoreRecordDao scoreDao = new ScoreRecordDao();
			String bh,jgid,jgmc,xfsj,dlmc,ldmc,qslc,zzlc,reason;
			for (int i = 0; i < data.length; i++) {
				bh = StringHelper.obj2str(data[i][0]);
				jgid = StringHelper.obj2str(data[i][1]);
				jgmc = StringHelper.obj2str(data[i][2]);
				xfsj = StringHelper.obj2str(data[i][3]);
				dlmc = StringHelper.obj2str(data[i][4]);
				ldmc = StringHelper.obj2str(data[i][5],"");
//				if (!ldmc.equals("") && !ldmc.endsWith("路段")) {
//					ldmc += "路段";
//				}
				qslc = StringHelper.obj2str(data[i][6],"");
//				if(!qslc.equals("")){
//					qslc += "千米";
//				}
				zzlc = StringHelper.obj2str(data[i][7],"");
//				if(!zzlc.equals("")){
//					zzlc += "千米";
//					if(!qslc.equals("")){
//						zzlc = "至" + zzlc;
//					}
//				}
//				String lc = "";
//				if(!qslc.equals("") || !zzlc.equals("")){
//					lc = qslc + zzlc + "处";
//				}
				String location = AlarmInfoJoin.getPlace("", dlmc, ldmc, null, qslc, null, zzlc, null);
				reason = "总队在"+xfsj+"发布的一条发生在"+location+"的拥堵报料信息，由于三十分钟之内没有核实，因此扣分一次！";
				xfsj = StringHelper.obj2str(data[i][8]);
				scoreDao.add(jgid, jgmc, xfsj, bh, "4", "1", reason);
			}
		}
	}
}
