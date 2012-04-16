package com.ehl.sm.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.BaseDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

public class FuncDao extends BaseDao {

	/** ��־�� */
	private Logger logger = Logger.getLogger(this.getClass());

	/** ʵ������� */
	private String ename = "����";

	/** ���� */
	private String tname = "T_SYS_FUNC";

	/** ����� */
	private String otname = "func";

	/** ���� */
	private String[] cnames = {"id","n_id", "n_parent_id", "text", "im0", "im1",
			"im2", "n_call", "n_select", "remark", "isdisplay", "isdefault"};

	/** ������������ */
	private String[] dcnames = {"time"};

	/** ������ */
	private String sname = "SEQ_HELP_LEAVE";

	public FuncDao() {
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
		return cnames.clone();
	}

	@Override
	public String getSelect() {
		return Array.join(getFields(), ",");
	}
	
	public Object[][] getById(String id){
		StringBuffer sql = new StringBuffer();
		sql.append("select id,n_id,text");
		sql.append(" from t_sys_func");
		sql.append(" where " + Sql.siftId(id));
		sql.append(" order by id");
		String msg = "ͨ��������Ż�ȡ������Ϣ";
		return FlowUtil.readMilte(sql.toString(), logger, msg);
	}

	public Object[][] select(Map<String, Object> conditions) {
		String otname = tname + " " + this.otname;
		String sql = Sql.select(otname, getSelect(), null, null, "id");
		String msg = "��ѯ" + ename + "��Ϣ";
		return FlowUtil.readMilte(sql.toString(), logger, msg);
	}

}
