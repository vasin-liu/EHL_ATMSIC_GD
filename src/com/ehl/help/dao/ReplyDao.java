package com.ehl.help.dao;

import org.apache.log4j.Logger;

import com.ehl.base.BaseDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

/**
 * �ظ����ݷ�����
 * 
 * @author xiayouxue
 * 
 */
public class ReplyDao extends BaseDao {

	/** ��־�� */
	private Logger logger = Logger.getLogger(this.getClass());

	/** ʵ������� */
	private String ename = "�ظ�";

	/** ���� */
	private String tname = "T_HELP_REPLY";

	/** ����� */
	private String otname = "reply";

	/** ���� */
	private String[] cnames = { "id", "jgid", "pname", "time", "content", "lid" };

	/** ������������ */
	private String[] dcnames = { "time" };

	/** ������ */
	private String sname = "SEQ_HELP_REPLY";

	public ReplyDao() {
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
		fileds[2] = "(select jgmc from t_sys_department where jgid="+otname+".jgid)";
		fileds[4] = Sql.toChar(fileds[4], 4);
		return Array.join(fileds, ",");
	}

	/**
	 * ��ȡ�ظ���Ϣ��ͨ�����Ա��
	 * 
	 * @param lid
	 *            ���Ա��
	 * @return �ظ���Ϣ
	 */
	public Object[][] getByLid(String lid) {
		String _tname = tname + " " + otname;
		String where = "lid='" + lid + "'";
		String sql = Sql.select(_tname, getSelect(), where, null, "id");
		return FlowUtil.readMilte(sql, logger, "��ȡ" + ename + "��Ϣ��ͨ��������");
	}

}
