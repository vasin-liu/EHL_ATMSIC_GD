package com.ehl.sm.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.dispatch.common.FlowUtil;
import com.ehl.sm.entity.Code;

public class CodeDao {

	private Logger logger = Logger.getLogger(CodeDao.class);

	public Object[][] select(Map<String, Object> conditions) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select dmlb,dm,dmsm,bz,xh,sytj,sfsy");
		sql.append(" from t_sys_code");
		StringBuffer sift = new StringBuffer();
		if (conditions.containsKey("dmlb")) {
			sift.append(" and dmlb='" + conditions.get("dmlb") + "'");
		}
		sql.append(" where 1=1" + sift.toString());
		sql.append(" order by dm");
		String msg = "获取编码信息";
		return FlowUtil.readMilte(sql.toString(), logger, msg);
	}
	
	public List<Code> selectEntity(Map<String, Object> conditions) {
		List<Code> listCode = new ArrayList<Code>();
		Object[][] objects = select(conditions);
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				Code code = new Code();
				code.setDmlb((String) objects[i][0]);
				code.setDm((String) objects[i][1]);
				code.setDmsm((String) objects[i][2]);
				code.setBz((String) objects[i][3]);
				code.setXh((String) objects[i][4]);
				code.setSytj((String) objects[i][5]);
				code.setSfsy((String) objects[i][6]);
				listCode.add(code);
			}
		}
		return listCode;
	}

}
