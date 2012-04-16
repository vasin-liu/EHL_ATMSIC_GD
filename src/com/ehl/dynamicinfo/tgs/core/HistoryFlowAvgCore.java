package com.ehl.dynamicinfo.tgs.core;

import org.apache.log4j.Logger;

import com.ehl.base.BaseCore;
import com.ehl.dynamicinfo.tgs.dao.HistoryFlowAvgDao;

/**
 * 历史流量平均值业务逻辑类
 * 
 */
public class HistoryFlowAvgCore extends BaseCore {
    /** 日志类 */
    private Logger logger = Logger.getLogger(this.getClass());

    /** 对象名称 */
    private String oname = "leave";

    /** 问题数据访问类 */
    private HistoryFlowAvgDao dao = new HistoryFlowAvgDao();

    public HistoryFlowAvgCore() {
	super.setLogger(logger);
	super.setDao(dao);
	super.setOname(oname);
    }
    
    /**
     * <pre>
     * 获取增长率（入城、出城、双向）
     * 获取某个卡口某一个小时对比该卡口该小时历史平均值的增长率
     * </pre>
     * @param kkmc 卡口名称
     * @param hour 小时
     * @param inNew 入城流量
     * @param outNew 出城流量
     * @return 入城、出城、双向流量增长率
     */
    public String[] getGrowth(String kkmc, String hour, int inNew, int outNew) {
	String[] growths = { "--", "--", "--","--","--","--"};
	Object[][] objects = dao.getHistoryAvg(kkmc, hour);
	if (objects != null && objects.length > 0) {
	    int length = objects.length;
	    if (length == 1) {
		String direction = (String) objects[0][4];
		int flow = Integer.parseInt(String.valueOf(objects[0][6]));
		if(flow != 0){
		    int index = Integer.parseInt(direction) - 1;
		    int[] inoutNew = { inNew, outNew };
		    growths[index] = getGrowth(inoutNew[index], flow);
		    growths[2] = growths[index];
		    growths[index+3] = String.valueOf(flow);
		    growths[5] = growths[index+3];
		}
	    } else {
		int inOri = Integer.parseInt(String.valueOf(objects[0][6]));
		int outOri = Integer.parseInt(String.valueOf(objects[1][6]));
		if(inOri != 0){
		    growths[0] = getGrowth(inNew, inOri);
		    growths[3] = String.valueOf(inOri);
		}
		if(outOri != 0){
		    growths[1] = getGrowth(outNew, outOri);
		    growths[4] = String.valueOf(outOri);
		}
		int ori = inOri + outOri;
		if(ori != 0){
		    growths[2] = getGrowth(inNew + outNew, ori);
		    growths[5] = String.valueOf(ori);
		}
	    }
	}
	return growths;
    }
    
    private static String getGrowth(int flowNew, int flowOri) {
	float growth = (float) (flowNew - flowOri) / flowOri;
	return Math.round(growth * 1000) / 10f + "%";
    }
    
}
