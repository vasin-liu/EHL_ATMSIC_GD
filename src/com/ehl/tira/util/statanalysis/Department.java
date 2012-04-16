package com.ehl.tira.util.statanalysis;

/**
 * ��ȡ��������sql���
 * 
 * @author xiayx
 * @date 2010-12-19
 */
public class Department {

	/*
	 * 1.��ȡ���صȼ� 2.ɸѡָ������ 3.ɸѡָ�������ڰ����Ļ��� 4.�ų����� 5.��ȡ�������ӻ��� 6.��ȡgroup by
	 */

	/**
	 * ȡ�û�ȡ�������صȼ���sql���
	 * 
	 * @param deptId
	 *            �������ر��
	 * @return
	 */
	public static String getLevelSql(String deptId) {
		return "instr('" + deptId + "00" + "','00')";
	}

	/**
	 * ȡ���Ƴ������sql���
	 * 
	 * @param col
	 *            ����
	 * @param deptId
	 *            ����ֵ
	 * @return
	 */
	public static String getRemoveSelfSql(String col, String deptId) {
		return getWhereSql(col, deptId, true, false);
	}

	/**
	 * ȡ�û�ȡ�ӻ��ص�sql���
	 * @param col
	 * @param deptId
	 * @return
	 */
	public static String getGetOtherSql(String col, String deptId) {
		return getWhereSql(col, deptId, false, true);
	}

	/**
	 * ȡ��ɸѡsql���
	 * 
	 * 
	 * @param col
	 *            ����
	 * @param deptId
	 *            ����ֵ
	 * @param isSelf
	 *            �Ƿ�����
	 * @param isGet
	 *            �Ƿ�ȡ��
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
	 * ȡ��ɸѡsql���
	 * 
	 * @param col
	 *            ����
	 * @param deptId
	 *            ����ֵ
	 * @return
	 */
	public static String getWhereSql(String col, String deptId) {
		return " and rpad(substr(" + col + ",1," + getLevelSql(deptId)
				+ "-1),6,'0')='" + deptId + "'";
	}

	/**
	 * ȡ��ɸѡsql���
	 * 
	 * @param col
	 *            ����
	 * @param deptId
	 *            ����ֵ
	 * @param isContainPdept
	 *            �Ƿ�����䱾��
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
	 * ȡ�ð�֧�ӻ��ߴ�ӷ����sql���
	 * 
	 * @param deptId
	 *            ���ر��
	 * @param isContainSelf
	 *            �Ƿ��������
	 * @return
	 */
	public static String getSql(String deptId, boolean isContainSelf) {
		String whereSelf = isContainSelf ? ""
				: getRemoveSelfSql("jgid", deptId);
		String sql = "select substr(jgid,1,6),replace(jgmc,'�����ֽ�ͨ����֧��','����֧��')"
				+ " from t_sys_department"
				+ " where substr(jgid,1,6)<'446000' and substr(jgid,7,6)='000000' "
				+ getWhereSql("jgid", deptId) + getGetOtherSql("jgid", deptId)
				+ whereSelf + " order by jgid";
		return sql;
	}

	// ------------------------------------------------------------------------------
	// ȡ���Ƴ��ܶ�
	// ȡ���Ƴ�֧��
	// ȡ�õõ�֧��
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
	 * ȡ�ð�֧�ӷ����sql���
	 * 
	 * @param deptId
	 * @param isContainZod
	 * @return
	 */
	public static String getZdSql(String deptId, boolean isContainZod) {
		String whereZod = isContainZod ? "" : getRemoveZodSql("jgid");
		String sql = "select substr(jgid,1,6),replace(jgmc,'�����ֽ�ͨ����֧��','����֧��') from t_sys_department"
				+ " where substr(jgid,1,6)<'446000' and substr(jgid,7,6)='000000'"
				+ getWhereZodSql("jgid", deptId)
				+ whereZod
				+ getGetZdSql("jgid") + " order by jgid";
		return sql;
	}

	/**
	 * ȡ�ð���ӷ����sql���
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
