package com.ehl.tira.util.statanalysis;

import com.appframe.data.sql.DBHandler;

public class Division {
	
	/*
	 * 440000
	 * ʡ���С�����
	 * substr(dmz,1,2)��substr(dmz,3,2)��substr(dmz,5,2)
	 * substr(dmz,1,2)||'0000'��substr(dmz,1,4)||'00'��substr(dmz,1,6)||''->ѡ��һ����Χ
	 * 
	 * 
	 */
//	public static String 
	/**
	 * �õ���ȡ��������dmz->dmsm1��sql���
	 * @param division
	 * @param dmlb
	 * @return
	 */
	public static String getSql(String division,String dmlb) {
		if (division == null || dmlb == null) {
			return null;
		}
		String sql = 
			"select "+
				"dmz,"+
				"rtrim(dmsm1,'��*��') "+
			"from "+
				"t_tira_code "+
			"where "+
				"dmlb='"+dmlb+"' and ";
		if (!division.substring(4).equals("00")) {
			sql += "dmz='"+division+"' ";
		}else if (!division.substring(2,4).equals("00")) {
			sql += "substr(dmz,1,4)||'00'='"+division+"' ";
		}else {
			sql += "substr(dmz,5,2) = '00' and substr(dmz,1,2)||'0000'='"+division+"' ";
		}
		sql += " order by dmz";
		return sql;
	}
	
	/**
	 * �õ�������������
	 * @param division
	 * @param dmlb
	 * @return
	 */
	public static Object[][] getData(String division,String dmlb) {
		if (division == null || dmlb == null) {
			return null;
		}
		return DBHandler.getMultiResult(getSql(division, dmlb));
	}
	
	public static String getGroupBySql(String division, String col) {
		if (division == null || col == null) {
			return null;
		}
		String gbSql = "";
		if (!division.substring(4).equals("00")) {
			gbSql += col;
		}else if (!division.substring(2,4).equals("00")) {
			gbSql += "substr("+col+",1,4)";
		}else {
			gbSql += "substr(dmz,5,2) = '00' and substr(dmz,1,2)||'0000'='"+division+"' ";
		}
		return gbSql;
	}
}
