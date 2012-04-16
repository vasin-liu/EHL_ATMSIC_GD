package com.ehl.dispatch.dispatchTask.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.ehl.dispatch.dispatchTask.bean.TrafficnewsfeedsBean;
import com.ehl.sm.common.util.StringUtil;

public class TaskUtil {
	Logger logger = Logger.getLogger(TaskUtil.class);
	/**
	 * 根据道路名称，起始，终止里程取得管辖大队名称<br/>
	 * 根据道路名称，起始，终止里程取得管辖大队名称
	 * @param roadName
	 * @param startMileage
	 * @param endMileage
	 * @return
	 */
	public String getDepartmentNameByRoadname (String roadName,String startMileage,String endMileage) {
		Object[] resultObject = null;
		StringBuffer sqlBuffer = new StringBuffer("select jgmc from T_FW_ROAD_DEPARTMENT where "); 
		try {
			sqlBuffer.append("dlmc like '%");
			sqlBuffer.append(roadName);
			sqlBuffer.append("%' and ((START_LENGTH <= '");
			sqlBuffer.append(Integer.valueOf(startMileage));
			sqlBuffer.append("' and END_LENGTH >= '");
			sqlBuffer.append(Integer.valueOf(startMileage));
			sqlBuffer.append("') or ");
			
			sqlBuffer.append(" (START_LENGTH <= '");
			sqlBuffer.append(Integer.valueOf(endMileage));
			sqlBuffer.append("' and END_LENGTH >= '");
			sqlBuffer.append(Integer.valueOf(endMileage));
			sqlBuffer.append("')) order by jgmc ");
			
			resultObject = DBHandler.getLineResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("取得拥堵道路所管辖的辖区出错 sql:" + sqlBuffer);
		}
		if(resultObject != null) {
			return String.valueOf(resultObject[0]);
		}
		return "未取得所属辖区";
	}

	/**
	 * 取得所有浮动车采集的路况<br/>
	 * 取得所有浮动车采集的路况信息
	 * @param roadName
	 * @param startMileage
	 * @param endMileage
	 * @return
	 */
	public Object[][] getTrafficnewsfeedsInfo () {
		Object[][] resultObject = null;
		StringBuffer sqlBuffer = new StringBuffer("select "); 
		/* 编号0,道路名称1,路段方向2,起始路段里程3,终止路段里程4,起始路段经度5,起始路段纬度6,终止路段经度7
		终止路段纬度8,位置或情况描述9,路况10,起始时间11,终止时间12,路况是否结束13
		路况原因14,报料人15,报料人联系方式16,录入人17,录入时间18,管辖大队19,信息来源20,处理状态21
		核实人22,核实时间23,核实情况24,处置情况25,审查人26,审查时间27
		审查意见28,计分29,是否对外发布30,备注31,点击维持的时间32,同步主键33,交通流主键34,录入部门35,所属路段36*/
		try {
			sqlBuffer.append(" BH,DLMC,LDFX,QSLC,ZZLC,QSJD,QSWD,ZZJD,ZZWD,WZMS,LK,QSSJ,ZZSJ,");
			sqlBuffer.append(" SFJS,LKYY,BLR,LXFS,LRR,LRSJ,GXDD,XXLY,CLZT,HSR,HSSJ,HSQK,");
			sqlBuffer.append(" CZQK,SCR,SCSJ,SCYJ,JF,SFFB,BZ,WCSJ,TBZJ,JTLZJ,LRBM,SSLD");
			sqlBuffer.append(" from EXCH_T_TRAFFICNEWSFEEDS_TEMP where ");
			// 取得未结束的拥堵报料信息
			sqlBuffer.append(" SFJS='0' ");
			resultObject = DBHandler.getMultiResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("取得所有浮动车采集路况出错 sql:" + sqlBuffer);
		}
		return resultObject;
	}
	
	/**
	 * 判断该条路况信息是否为重复信息 或者 不恰当信息<br/>
	 * 判断该条路况信息是否为重复信息 或者 不恰当信息
	 * @param trafficnewsfeedsBean
	 * @return true 可以插入表的信息 false 不当数据或者重复数据
	 */
	public boolean getInsertFlg (TrafficnewsfeedsBean trafficnewsfeedsBean) {
		TaskUtil taskUtil = new TaskUtil();
		Object[][] trafficnewsfeedsInfo = taskUtil.getTrafficnewsfeedsInfo();
		// 取得非空的情况
		if(trafficnewsfeedsInfo != null) {
			// 待录入路况起始时间
			String waitQssj = trafficnewsfeedsBean.getQSSJ();
			long second = StringUtil.getTimeInterval(waitQssj, taskUtil.getSysdate());
			long miniates = second/60;
			// 与当前系统时间相差半小时的以上的拥堵路况信息为不恰当数据
			if(miniates > 30) {
				return false;
			}
			String roadName = "";
			String qslc = "";
			String zzlc = "";
			/* 编号0,道路名称1,路段方向2,起始路段里程3,终止路段里程4,起始路段经度5,起始路段纬度6,终止路段经度7
			终止路段纬度8,位置或情况描述9,路况10,起始时间11,终止时间12,路况是否结束13
			路况原因14,报料人15,报料人联系方式16,录入人17,录入时间18,管辖大队19,信息来源20,处理状态21
			核实人22,核实时间23,核实情况24,处置情况25,审查人26,审查时间27
			审查意见28,计分29,是否对外发布30,备注31,点击维持的时间32,同步主键33,交通流主键34,录入部门35,所属路段36*/
			for(int i=0; i<trafficnewsfeedsInfo.length; i++) {
				roadName = "";
				qslc = "";
				zzlc = "";
				roadName = String.valueOf(trafficnewsfeedsInfo[i][1]);
				qslc = String.valueOf(trafficnewsfeedsInfo[i][3]);
				zzlc = String.valueOf(trafficnewsfeedsInfo[i][4]);
				// 道路名称 ，起始里程 ，终止里程 一样的数据不再插入
				if((roadName.equals(trafficnewsfeedsBean.getDLMC())) &&
						(qslc.equals(trafficnewsfeedsBean.getQSLC())) &&
						(zzlc.equals(trafficnewsfeedsBean.getZZLC()))
						) {
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * 拥堵的路况信息的插入<br/>
	 * 拥堵的路况信息的插入处理
	 * @param trafficnewsfeedsBean
	 * @return
	 */
	public Object[][] insertTrafficnewsfeedsInfo (TrafficnewsfeedsBean trafficnewsfeedsBean) {
		Object[][] resultObject = null;
		/* 编号0,道路名称1,路段方向2,起始路段里程3,终止路段里程4,起始路段经度5,起始路段纬度6,终止路段经度7
		终止路段纬度8,位置或情况描述9,路况10,起始时间11,终止时间12,路况是否结束13
		路况原因14,报料人15,报料人联系方式16,录入人17,录入时间18,管辖大队19,信息来源20,处理状态21
		核实人22,核实时间23,核实情况24,处置情况25,审查人26,审查时间27
		审查意见28,计分29,是否对外发布30,备注31,点击维持的时间32,同步主键33,交通流主键34,录入部门35,所属路段36*/
		StringBuffer sqlBuffer = new StringBuffer("insert into EXCH_T_TRAFFICNEWSFEEDS_TEMP (  "); 
		sqlBuffer.append(" BH,DLMC,LDFX,QSLC,ZZLC,WZMS,LK,QSSJ,ZZSJ,");
		sqlBuffer.append(" SFJS,LKYY,GXDD,XXLY,CLZT,");
		sqlBuffer.append(" SFFB)  values (");
		try {
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getBH());
			sqlBuffer.append("','");
			sqlBuffer.append(trafficnewsfeedsBean.getDLMC());
			sqlBuffer.append("','");
			sqlBuffer.append(trafficnewsfeedsBean.getLDFX());
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(Integer.valueOf(trafficnewsfeedsBean.getQSLC()));
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(Integer.valueOf(trafficnewsfeedsBean.getZZLC()));
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getWZMS());
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getLK());
			sqlBuffer.append("',");
			sqlBuffer.append(" to_date('");
			sqlBuffer.append(trafficnewsfeedsBean.getQSSJ());
			sqlBuffer.append("','yyyy-mm-dd hh24:mi ss'),");
			sqlBuffer.append(" to_date('");
			sqlBuffer.append(trafficnewsfeedsBean.getZZSJ());
			sqlBuffer.append("','yyyy-mm-dd hh24:mi ss'),");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getSFJS());
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getLKYY());
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getGXDD());
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getXXLY());
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getCLZT());
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getSFFB());
			sqlBuffer.append("' )");
			System.out.println("***********"+sqlBuffer);
			resultObject = DBHandler.getMultiResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("取得所有浮动车采集路况出错 sql:" + sqlBuffer);
		}
		return resultObject;
	}

	/**
	 * 获取系统时间<br/>
	 * 获取当前系统服务器时间
	 * @return
	 */
	public String getSysdate () {
		Object[] resultObject = null;
		StringBuffer sqlBuffer = new StringBuffer(" select to_char(sysdate,'yyyy-MM-dd hh:mm:ss') from dual "); 
		String formatStr = "yyyy-MM-dd hh:mm:ss";
		TaskUtil taskUtil = new TaskUtil();
		try {
			resultObject = DBHandler.getLineResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("取得服务器时间出错 sql:" + sqlBuffer);
		}
		if(resultObject != null) {
			return String.valueOf(resultObject[0]);
		}
		return taskUtil.getCurrDateTime(formatStr);
	}

    /**
     * 获取当前时间<br/>
     * @param formatStr
     * @return
     */
    public String getCurrDateTime(String formatStr){
    	String strdate=null;
    	Date dNow = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
    	strdate=formatter.format(dNow);
    	return strdate;
    }
    
    public static void main (String [] args) {
    	try {
    		InputStream second = System.in;
    		BufferedReader reader = new BufferedReader(new InputStreamReader(second));
    		String readStr = reader.readLine();
    		long log = Long.valueOf(readStr);
    		long miniates = log/60;
    		System.out.println("分钟数是:" + miniates);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
