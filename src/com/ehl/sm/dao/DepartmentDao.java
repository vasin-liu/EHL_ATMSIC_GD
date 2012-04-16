package com.ehl.sm.dao;

import org.apache.log4j.Logger;

import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class DepartmentDao {
	
	public static Logger logger = Logger.getLogger(DepartmentDao.class);
	/** ���� */
	public final static String tname = "t_sys_department";
	/** ���ݿ������ */
	public final static String[] cnames = { "jgid", "jgmc",  "time", "title", "content","apath","ajgid" };
	/** �����ֶ����� */
	public final static String[] fnames = { "jgid", "jgmc", "jgccbh", "jglx"};
	
	public static String getSelect(){
		return "jgid,jgmc,jgccbm,jglx";
	}
	
	/**
	 * ��ȡ������Ϣ���ݻ������
	 * @param jgid �������
	 * @return ��������
	 */
	public static Object[] getByJgid(String jgid){
		String sql = Sql.select(tname, getSelect(),"jgid='"+jgid+"'");
		return FlowUtil.readLine(sql, logger, "��ȡ������Ϣ���ݻ������");
	}
	
}
