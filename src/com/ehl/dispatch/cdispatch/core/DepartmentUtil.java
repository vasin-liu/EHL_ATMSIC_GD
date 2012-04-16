package com.ehl.dispatch.cdispatch.core;

import com.appframe.data.sql.DBHandler;

public class DepartmentUtil {
	/**
	 * 根据 jgid 取 上级机构 jgid，大队得到的结果是支队，支队得到的是总队。
	 * 特殊情况：大队直属总队，如：888888，无 888800，则取得结果为 880000。
	 * @author dxn
	 * @param jgid(自身jgid)
	 * @return jgid(上级jgid)
	 * @date 2010-05-16
	 */
	public static String getSuperUnit(String jgid){
		StringBuffer sql = new StringBuffer("select (case when substr('");
		sql.append(jgid).append("', 3, 4) = '0000' then '").append(jgid).append("' when substr('")
			.append(jgid).append("', 5, 2) = '00' then substr('").append(jgid).append("', 1, 2) ")
			.append("|| '0000000000' when (select jgid from t_sys_department where jgid = substr('")
			.append(jgid).append("', 1, 4) || '00000000') is null then substr('").append(jgid)
			.append("', 1, 2) || '0000000000' else substr('").append(jgid).append("', 1, 4) || '")
			.append("00000000' end) from dual");

		return DBHandler.getSingleResult(sql.toString()).toString();
	}
}
