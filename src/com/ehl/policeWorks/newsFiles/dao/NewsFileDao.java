package com.ehl.policeWorks.newsFiles.dao;

import org.apache.log4j.Logger;

import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;


public class NewsFileDao {
	
	private static Logger logger = Logger.getLogger(NewsFileInitDao.class);
	/** 表名 */
	public final static String tname = "T_OA_NEWSFILE";
	/** 表别名 */
	public final static String otname = "file";
	/** 数据库表列名 */
	public final static String[] cnames = { "NEWS_FILEID",
			"SEND_DEPARTMENT_ID", "SEND_DEPARTMENT_NAME", "SEND_PERSON",
			"SEND_TIME", "TITLE", "DETAIL_INFO", "WORD_FILEPATH",
			"STREAM_FILEPATH", "TYPE", "SBTYPE", "WRITER", 
			"OTHER_INFO","STATE","DTIME" };
	/**信息文件类型。1：普通信息文件；2：调研信息文件 */
	public final static String[] types = { "1", "2" };
	/**信息文件上报类型。1：报省厅（总队）；2：报部局 */
	public final static String[] sbtypes = { "1", "2" };
	
	/**
	 * 获取指定单位在一定时间范围内被采用的信息文件数据
	 * @param jgid 机构编号
	 * @param stime 起始时间
	 * @param etime 结束时间
	 * @return 信息文件数据
	 */
	public static Object[][] getCounts(String jgid, String stime, String etime){
		if(jgid == null || stime == null || etime == null){
			return null;
		}
		String select = "type,sbtype,count(*)";
		String where = "send_department_id='"+jgid+"' and state in ('2','4') and " 
			+ Sql.getWhereDate("dtime", stime, etime, 2);
		String groupBy = "type,sbtype";
		String sql = Sql.select(tname, select, where, groupBy);
		return  FlowUtil.readMilte(sql, logger, "获取指定单位在一定时间范围内被采用的信息文件数据");
	}
	
	/**
	 * 获取信息文件中被采用的属于春运的统计数据
	 * @param stime 起始时间
	 * @param etime 终止时间
	 * @return 2维数组
	 */
	public static Object[][] springStatis(String stime, String etime){
	    Object[][] objects = null;
	    if(stime != null && etime != null){
		StringBuffer template = new StringBuffer();
		template.append("select jgid,jgmc from t_sys_department where jglx='1'");
		StringBuffer newsfile = new StringBuffer();
		newsfile.append("select send_department_id jgid, count(news_fileid) account");
		newsfile.append(" from t_oa_newsfile");
		newsfile.append(" where iszxgz = '1' and state = '2'");
		String siftDate = Sql.getWhereDate("dtime", stime, etime, 2);
		if(!siftDate.equals("")){
		    newsfile.append(" and "+siftDate);
		}
		newsfile.append(" group by send_department_id");
		StringBuffer result = new StringBuffer();
		result.append("select dept.jgid, dept.jgmc, nvl(to_char(news.account), '0')");
		result.append(" from ("+template.toString()+") dept");
		result.append(" left join ("+newsfile.toString()+") news");
		result.append(" on dept.jgid = news.jgid");
		result.append(" order by dept.jgid");
		objects = FlowUtil.readMilte(result.toString(), logger, "获取信息文件中被采用的属于春运的统计数据");
	    }
	    return objects;
	}
	
	public static void main(String[] args) {
	    NewsFileDao.springStatis("2011-01-01", "2012-01-01");
	}

}
