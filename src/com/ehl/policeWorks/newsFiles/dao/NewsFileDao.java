package com.ehl.policeWorks.newsFiles.dao;

import org.apache.log4j.Logger;

import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;


public class NewsFileDao {
	
	private static Logger logger = Logger.getLogger(NewsFileInitDao.class);
	/** ���� */
	public final static String tname = "T_OA_NEWSFILE";
	/** ����� */
	public final static String otname = "file";
	/** ���ݿ������ */
	public final static String[] cnames = { "NEWS_FILEID",
			"SEND_DEPARTMENT_ID", "SEND_DEPARTMENT_NAME", "SEND_PERSON",
			"SEND_TIME", "TITLE", "DETAIL_INFO", "WORD_FILEPATH",
			"STREAM_FILEPATH", "TYPE", "SBTYPE", "WRITER", 
			"OTHER_INFO","STATE","DTIME" };
	/**��Ϣ�ļ����͡�1����ͨ��Ϣ�ļ���2��������Ϣ�ļ� */
	public final static String[] types = { "1", "2" };
	/**��Ϣ�ļ��ϱ����͡�1����ʡ�����ܶӣ���2�������� */
	public final static String[] sbtypes = { "1", "2" };
	
	/**
	 * ��ȡָ����λ��һ��ʱ�䷶Χ�ڱ����õ���Ϣ�ļ�����
	 * @param jgid �������
	 * @param stime ��ʼʱ��
	 * @param etime ����ʱ��
	 * @return ��Ϣ�ļ�����
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
		return  FlowUtil.readMilte(sql, logger, "��ȡָ����λ��һ��ʱ�䷶Χ�ڱ����õ���Ϣ�ļ�����");
	}
	
	/**
	 * ��ȡ��Ϣ�ļ��б����õ����ڴ��˵�ͳ������
	 * @param stime ��ʼʱ��
	 * @param etime ��ֹʱ��
	 * @return 2ά����
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
		objects = FlowUtil.readMilte(result.toString(), logger, "��ȡ��Ϣ�ļ��б����õ����ڴ��˵�ͳ������");
	    }
	    return objects;
	}
	
	public static void main(String[] args) {
	    NewsFileDao.springStatis("2011-01-01", "2012-01-01");
	}

}
