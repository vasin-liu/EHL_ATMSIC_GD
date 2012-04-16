package com.ehl.drpt.dailyRpt.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.BaseDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Department;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

/**
 * 最大车流量
 * 
 * @author xiayx
 * 
 */
public class MaxRoadFlowDao extends BaseDao {

    /** 日志类 */
    private Logger logger = Logger.getLogger(this.getClass());

    /** 实体对象名 */
    private String ename = "最大车流量";

    /** 表名 */
    private String tname = "T_OA_MAXROADFLOW";

    /** 表别名 */
    private String otname = "maxflow";

    /** 列名 */
    private String[] cnames = { "bh", "dlbh", "ldmc", "dlfx", "carnum", "kc",
	    "zjc", "avg", "lrratio", "rzbh" };


    public MaxRoadFlowDao() {
	super.setLogger(logger);
	super.setEname(ename);
	super.setTname(tname);
	super.setOtname(otname);
	super.setCnames(cnames);
    }

    @Override
    public String[] getFields() {
	String[] attrs = Array.insert(cnames, 2, "dlmc");
	attrs = Array.insert(attrs, 3, "level");
	attrs = Array.insert(attrs, 6, "fxmc");
	return attrs;
    }

    @Override
    public String getSelect() {
	String[] select = getFields();
	select[1] = "(select distinct ndlbh from t_oa_dict_road where dlbh="+tname+".dlbh)";
	select[1] = "nvl("+select[1]+",dlbh)";
	select[2] = "(select dlmc from t_oa_dictdlfx where gbdm="+select[1]+")";
	select[3] = "(select roadlevel from t_oa_dictdlfx where gbdm="+select[1]+")";
	return Array.join(select, ",");
    }

    @Override
    public String insert(Map<String, String> object) {
	if (object != null) {
	    String id = object.get("bh");
	    FlowUtil.encapMapSQ(object);
	    String sql = Sql.insert(tname, object);
	    boolean isSuccess = FlowUtil.write(sql, logger, "添加" + ename + "信息");
	    return isSuccess ? id : null;
	}
	return null;
    }
    
    private String getSql() {
	StringBuffer flowRoad = new StringBuffer();
	flowRoad.append("select flow.rzbh,flow.tbdw")
	.append(",(select jgmc from t_sys_department where jgid = flow.tbdw) tbdwmc")
	.append(",to_char(flow.tjrq,'yyyy-mm-dd'),flow.bh,road.gbdm,road.dlmc,road.roadlevel")
	.append(",flow.ldmc,flow.carnum,flow.kc,flow.zjc,flow.avg,flow.lrratio");
	flowRoad.append(" from t_oa_dictdlfx road, v_springtrans_maxroadflow flow");
	flowRoad.append(" where flow.dlbh=road.gbdm");
	return flowRoad.toString();
    }


    private String getSqlByJgid(String jgid, String sdate, String edate) {
	if (jgid != null && sdate != null && edate != null) {
	    String sql = getSql();
	    sql += " and to_char(flow.tjrq, 'yyyy-mm-dd') between '" + sdate + "' and '" + edate + "'";
	    String siftJgid = "flow.tbdw='" + jgid + "'";
	    if (jgid.substring(4, 6).equals("00")) {
		siftJgid = Department.siftChild("flow.tbdw", jgid);
	    }
	    sql += " and " + siftJgid;
	    return sql;
	}
	return null;
    }

    public Object[][] getByJgid(String jgid,String sdate,String edate){
        String sql = getSqlByJgid(jgid, sdate, edate);
        if(sql != null){
            sql += " order by road.roadlevel,flow.carnum desc";
            return FlowUtil.readMilte(sql, logger, "根据机构编号获取最大流量道路");
        }
        return null;
    }

    public Object[][] getByJgid(String jgid, String sdate, String edate,
            int piece) {
        String sql = getSqlByJgid(jgid, sdate, edate);
        if (sql != null) {
            sql += " order by road.gbdm,flow.carnum desc";
            StringBuffer paging = new StringBuffer(sql);
            //Modify by Xiayx 2012-1-10
	    //取消筛选前20条记录
//            paging.append("select *");
//            paging.append(" from (" + sql + ")");
//            paging.append(" where  rownum <=" + piece);
	    //Modify finished
            return FlowUtil.readMilte(paging.toString(), logger,"根据机构编号获取最大流量道路");
        }
        return null;
    }

    public Object[][] getByRzbh(String rzbh) {
	if (rzbh != null) {
	    String sql = getSql();
	    sql += " and flow.rzbh='"+rzbh+"'";
	    sql += " order by flow.carnum desc";
	    return FlowUtil.readMilte(sql, logger, "根据报表日志编号获取最大流量道路");
	}
	return null;
    }
    
    /**
     * 流量统计
     * @param jgid 机构名称
     * @param sdate 起始日期
     * @param edate 结束日期
     * @return 统计数据
     */
    public Object[][] statis(String jgid,String sdate,String edate){
	Object[][] objects = null;
	if(jgid != null){
	    //最大车流量基本语句
	    ////筛选机构
	    String siftJgid = "flow.tbdw='" + jgid + "'";
	    if (jgid.substring(4, 6).equals("00")) {
		siftJgid = Department.siftChild("flow.tbdw", jgid);
	    }
	    ////筛选日期
	    String siftDate = Sql.getWhereDate("flow.tjrq", sdate, edate, 2);
	    if(!siftDate.equals("")){
		siftDate = " and " + siftDate;
	    }
	    ////sql语句
	    StringBuffer base = new StringBuffer();
	    base.append("select flow.dlbh,flow.tbdw,flow.tjrq,flow.carnum")
	    .append(",row_number() over(partition by flow.dlbh, flow.tbdw order by flow.dlbh, flow.tbdw, flow.carnum desc) rn");
	    base.append(" from v_springtrans_maxroadflow flow");
	    base.append(" where "+siftJgid+siftDate);
	    //辖区内最大车流量
	    StringBuffer maxXq = new StringBuffer();
	    maxXq.append("select flow.dlbh, flow.tbdw, flow.tjrq, flow.carnum")
	    .append(",row_number() over(partition by flow.dlbh order by flow.dlbh, flow.carnum desc) rn");
	    maxXq.append(" from ("+base+") flow");
	    maxXq.append(" where flow.rn=1");
	    //道路内前4名辖区最大车流量
	    StringBuffer maxRoad = new StringBuffer();
	    maxRoad.append("select flow.dlbh, flow.tbdw, flow.tjrq, flow.carnum, flow.rn");
	    maxRoad.append(" from ("+maxXq+") flow");
	    maxRoad.append(" where flow.rn <= 4");
	    //道路模板
	    StringBuffer template = new StringBuffer();
	    template.append("select gbdm,dlmc,roadlevel");
	    template.append(" from t_oa_dictdlfx");
	    template.append(" where (roadlevel = '1' and substr(gbdm, 1, 1) = 'G')")
	    	.append(" or roadlevel = '2'");
	    //最终sql语句
	    StringBuffer sql = new StringBuffer();
	    sql.append("select road.gbdm,road.dlmc,flow.tbdw")
	    .append(",(select jgmc from t_sys_department where jgid=flow.tbdw) jgmc")
	    .append(",to_char(flow.tjrq,'yyyy-mm-dd') tjrq,to_char(flow.carnum) account");
	    sql.append(" from ("+template+") road");
	    sql.append(" left join ("+maxRoad+") flow");
	    sql.append(" on road.gbdm=flow.dlbh");
	    sql.append(" order by road.roadlevel,road.dlmc,flow.carnum desc");
	    objects = FlowUtil.readMilte(sql.toString(),logger,"");
	}
	return objects;
    }
    
    public boolean deleteByRzbh(String rzbh) {
        if (rzbh == null) {
            return true;
        }
        String sql = Sql.delete(tname, "rzbh='" + rzbh + "'");
        return FlowUtil.write(sql, logger, "根据日志编号，删除" + ename + "信息");
    }

    public static void main(String[] args) {
	MaxRoadFlowDao dao = new MaxRoadFlowDao();
	dao.statis("440000000000","2010-01-01", "2012-01-01");
    }

}
