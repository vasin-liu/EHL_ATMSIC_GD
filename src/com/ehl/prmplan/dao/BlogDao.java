package com.ehl.prmplan.dao;

import org.apache.log4j.Logger;

import com.ehl.base.BaseDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

/**
 * 交宣微博素材数据访问类
 * 
 * @author Xiayx
 * 
 */
public class BlogDao extends BaseDao {

    /** 日志类 */
    private Logger logger = Logger.getLogger(this.getClass());

    /** 实体对象名 */
    private String ename = "交宣微博素材";

    /** 表名 */
    private String tname = "T_PRMPLAN_BLOG";

    /** 表别名 */
    private String otname = "blog";

    /** 列名 */
    private String[] cnames = { "id", "jgid", "pname", "time", "title",
	    "content", "apath" };

    /** 日期类型列名 */
    private String[] dcnames = { "time" };

    /** 序列名 */
    private String sname = "SEQ_PRMPLAN_BLOG";

    public BlogDao() {
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
	return (String[]) Array.insert(cnames, 2, "jgmc");
    }

    @Override
    public String getSelect() {
	String[] fileds = getFields();
	fileds[2] = "(select jgmc from t_sys_department where jgid=" + otname
		+ ".jgid)";
	fileds[4] = Sql.toChar(fileds[4], 4);
	return Array.join(fileds, ",");
    }

    /**
     * 获取统计对象数据
     * 
     * @param sdate
     *            起始日期
     * @param edate
     *            结束日期
     * @return 统计对象数据
     */
    public Object[][] statis(String sdate, String edate) {
	Object[][] objects = null;
	if (sdate != null && edate != null) {
	    StringBuffer template = new StringBuffer();
	    template.append("select jgid,jgmc from t_sys_department where jglx='1'");
	    StringBuffer newsfile = new StringBuffer();
	    newsfile.append("select jgid jgid, count(id) account");
	    newsfile.append(" from " + tname);
	    newsfile.append(" where 1=1");
	    String siftDate = Sql.getWhereDate("time", sdate, edate, 2);
	    if (!siftDate.equals("")) {
		newsfile.append(" and " + siftDate);
	    }
	    newsfile.append(" group by jgid");
	    StringBuffer result = new StringBuffer();
	    result.append("select dept.jgid, dept.jgmc, nvl(to_char(news.account), '0')");
	    result.append(" from (" + template.toString() + ") dept");
	    result.append(" left join (" + newsfile.toString() + ") news");
	    result.append(" on dept.jgid = news.jgid");
	    result.append(" order by dept.jgid");
	    System.out.println(result.toString());
	    objects = FlowUtil.readMilte(result.toString(), logger, "获取统计对象数据");
	}
	return objects;
    }

    public static void main(String[] args) {
	BlogDao dao = new BlogDao();
	dao.statis("2011-01-01", "2012-01-01");
    }
}
