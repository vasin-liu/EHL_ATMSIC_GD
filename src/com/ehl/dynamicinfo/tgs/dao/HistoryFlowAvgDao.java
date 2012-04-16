package com.ehl.dynamicinfo.tgs.dao;

import org.apache.log4j.Logger;

import com.ehl.base.BaseDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

/**
 * 历史流量平均值数据访问类
 * @author xiayx
 *
 */
public class HistoryFlowAvgDao extends BaseDao {

    /** 日志类 */
    private Logger logger = Logger.getLogger(this.getClass());

    /** 实体对象名 */
    private String ename = "流量饱和度";

    /** 表名 */
    private String tname = "T_TFM_FLOWHISTORYAVG";

    /** 表别名 */
    private String otname = "avg";

    /** 列名 */
    private String[] cnames = { "id", "bid", "hour", "direction", "flow",
	    "year" };

    /** 日期类型列名 */
    private String[] dcnames = { "time" };

    /** 序列名 */
    private String sname = "SEQ_TFM_FLOWHISTORYAVG";

    public HistoryFlowAvgDao() {
	super.setLogger(logger);
	super.setEname(ename);
	super.setTname(tname);
	super.setOtname(otname);
	super.setCnames(cnames);
	super.setDcnames(dcnames);
	super.setSname(sname);
    }

    @Override
    public String[] getFields() {
	String[] fileds = Array.insert(cnames, 2, "kkmc");
	fileds = Array.insert(fileds, 5, "dcode");
	return fileds;
    }

    @Override
    public String getSelect() {
	String[] fileds = getFields();
	fileds[2] = "(select seg.roadsegname from t_road_seginfo seg where seg.roadsegid = "+otname+".bid) kkmc";
	fileds[5] = "decode(direction,1,'入城','出城')";
	return Array.join(fileds, ",");
    }
    
    /**
     * <pre>
     * 获取卡口流量饱和度
     * 获取某一卡口当前年度0~某一小时的饱和度值
     * </pre>
     * @param kkmc
     * @param hour
     * @return 卡口饱和度
     */
    public Object[][] getHistoryAvg(String kkmc, String hour) {
	String where = "year=to_char(sysdate,'yyyy')";
	where +=" and bid=(select roadsegid from t_road_seginfo where roadsegname like '%"+kkmc+"%' and rownum <= 1)";
	where += " and hour = "+hour;
	String tname = this.tname + " " + otname;
	String sql = Sql.select(tname, getSelect(), where, null,
		"hour desc,direction");
	return FlowUtil.readMilte(sql, logger, "获取卡口流量饱和度");
    }

    public static void main(String[] args) {
	new HistoryFlowAvgDao().getHistoryAvg("LJHSKKLD", "01");
    }
}
