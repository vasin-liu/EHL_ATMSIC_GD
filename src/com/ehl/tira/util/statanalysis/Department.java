package com.ehl.tira.util.statanalysis;

/**
 * 获取与机关相关sql语句
 * 
 * @author xiayx
 * @date 2010-12-19
 */
public class Department {

	/*
	 * 1.获取机关等级 2.筛选指定机关 3.筛选指定机关内包含的机关 4.排除自身 5.获取自身下子机关 6.获取group by
	 */

	/**
	 * 取得获取交警机关等级的sql语句
	 * 
	 * @param deptId
	 *            交警机关编号
	 * @return
	 */
	public static String getLevelSql(String deptId) {
		return "instr('" + deptId + "00" + "','00')";
	}

	/**
	 * 取得移除自身的sql语句
	 * 
	 * @param col
	 *            列名
	 * @param deptId
	 *            列名值
	 * @return
	 */
	public static String getRemoveSelfSql(String col, String deptId) {
		return getWhereSql(col, deptId, true, false);
	}

	/**
	 * 取得获取子机关的sql语句
	 * @param col
	 * @param deptId
	 * @return
	 */
	public static String getGetOtherSql(String col, String deptId) {
		return getWhereSql(col, deptId, false, true);
	}

	/**
	 * 取得筛选sql语句
	 * 
	 * 
	 * @param col
	 *            列名
	 * @param deptId
	 *            列名值
	 * @param isSelf
	 *            是否自身
	 * @param isGet
	 *            是否取得
	 * @return
	 */

	private static String getWhereSql(String col, String deptId,
			boolean isSelf, boolean isGet) {
		String whereSelf = isSelf ? "" : "+2";
		String whereGet = isGet ? "=" : "!=";
		return " and substr(" + col + "," + getLevelSql(deptId) + whereSelf
				+ ",2)" + whereGet + "'00'";
	}

	/**
	 * 取得筛选sql语句
	 * 
	 * @param col
	 *            列名
	 * @param deptId
	 *            列名值
	 * @return
	 */
	public static String getWhereSql(String col, String deptId) {
		return " and rpad(substr(" + col + ",1," + getLevelSql(deptId)
				+ "-1),6,'0')='" + deptId + "'";
	}

	/**
	 * 取得筛选sql语句
	 * 
	 * @param col
	 *            列名
	 * @param deptId
	 *            列名值
	 * @param isContainPdept
	 *            是否包含其本身
	 * @return
	 */
	public static String getWhereSql(String col, String deptId,
			boolean isContainSelf) {
		String wherePdept = isContainSelf ? "" : getRemoveSelfSql(col, deptId);
		String whereSql = getWhereSql(col, deptId) + wherePdept;
		return whereSql;
	}
	
	public static String getGbSql(String col, String deptId) {
		return " rpad(substr("+col+",1,"+getLevelSql(deptId)+"+1),6,'0')";
	}

	/**
	 * 取得按支队或者大队分组的sql语句
	 * 
	 * @param deptId
	 *            机关编号
	 * @param isContainSelf
	 *            是否包含自身
	 * @return
	 */
	public static String getSql(String deptId, boolean isContainSelf) {
		String whereSelf = isContainSelf ? ""
				: getRemoveSelfSql("jgid", deptId);
		String sql = "select substr(jgid,1,6),replace(jgmc,'公安局交通警察支队','交警支队')"
				+ " from t_sys_department"
				+ " where substr(jgid,1,6)<'446000' and substr(jgid,7,6)='000000' "
				+ getWhereSql("jgid", deptId) + getGetOtherSql("jgid", deptId)
				+ whereSelf + " order by jgid";
		return sql;
	}

	// ------------------------------------------------------------------------------
	// 取得移除总队
	// 取得移除支队
	// 取得得到支队
	//

	public static String getRemoveZodSql(String col) {
		return getWhereSql(col, true, false);
	}

	public static String getRemoveZdSql(String col) {
		return getWhereSql(col, false, false);
	}

	public static String getGetZdSql(String col) {
		return getWhereSql(col, false, true);
	}

	private static String getWhereSql(String col, boolean isZod, boolean isGet) {
		String whereSelf = isZod ? "" : "+2";
		String whereGet = isGet ? "=" : "!=";
		return " and substr(" + col + ",3" + whereSelf + ",2)" + whereGet
				+ "'00'";
	}

	public static String getGbZdSql(String col) {
		return " substr(" + col + ",1,4)||'00'";
	}

	public static String getGbDdSql(String col) {
		return col;
	}

	public static String getWhereZodSql(String col, String deptId) {
		return getWhereSql(col, deptId, 2);
	}

	public static String getWhereZdSql(String col, String deptId) {
		return getWhereSql(col, deptId, 4);
	}

	public static String getWhereDdSql(String col, String deptId) {
		return getWhereSql(col, deptId, 6);
	}

	public static String getWhereSql(String col, String deptId, int index) {
		if (deptId.length() < 6) {
			return null;
		}
		return " and substr(" + col + ",1," + index + ")='"
				+ deptId.substring(0, index) + "'";
	}

	/**
	 * 取得按支队分组的sql语句
	 * 
	 * @param deptId
	 * @param isContainZod
	 * @return
	 */
	public static String getZdSql(String deptId, boolean isContainZod) {
		String whereZod = isContainZod ? "" : getRemoveZodSql("jgid");
		String sql = "select substr(jgid,1,6),replace(jgmc,'公安局交通警察支队','交警支队') from t_sys_department"
				+ " where substr(jgid,1,6)<'446000' and substr(jgid,7,6)='000000'"
				+ getWhereZodSql("jgid", deptId)
				+ whereZod
				+ getGetZdSql("jgid") + " order by jgid";
		return sql;
	}

	/**
	 * 取得按大队分组的sql语句
	 * 
	 * @param deptId
	 * @param isContainZd
	 * @return
	 */
	public static String getDdSql(String deptId, boolean isContainZd) {
		String whereZd = isContainZd ? "" : getRemoveZdSql("jgid");
		String sql = "select substr(jgid,1,6),jgmc from t_sys_department"
				+ " where substr(jgid,1,6)<'446000' and substr(jgid,7,6)='000000'"
				+ getWhereZdSql("jgid", deptId) + whereZd + "  order by jgid";
		return sql;
	}

	public static void main(String[] args) {
		System.out.println(getDdSql("440100", false));
		System.out.println(getGbSql("jgid", "440100"));
	}

}
