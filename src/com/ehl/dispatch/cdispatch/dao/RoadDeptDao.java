package com.ehl.dispatch.cdispatch.dao;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

public class RoadDeptDao {
	
	private Logger logger = Logger.getLogger(RoadDeptDao.class);
	public final static String tname = "T_OA_ROADMANAGE_CODE";
	
	public Object[][] getDeptByRoad(String roadName, String rsegName, String startKM, String endKM, String dldj){
		Object[][] depts = null;
		String sql = "select xzqh, xzqhmc, dlmc, qslc, jslc, xqldmc from " + tname ;
		String andSep = " and ", orSep = " or ";
		String where = "";
		if(!roadName.equals("")){
			where += andSep + " dlmc like '%" + roadName + "%'";
		}
		if(!rsegName.equals("")){
			where += andSep + " xqldmc like '%" + rsegName + "%'";
		}
		String whereIn = "";
		String whereBoundary = "";
		if(!startKM.equals("")){
			whereIn += andSep + " qslc >= " + startKM;
			whereBoundary += orSep + " (qslc <= " + startKM + " and jslc >= " + startKM + ")";
		}
		if(!endKM.equals("")){
			whereIn += andSep + " jslc <= " + endKM;
			whereBoundary += orSep + " (qslc <= " + endKM + " and jslc >= " + endKM + ")";
		}
		if(!whereIn.equals("") && !whereBoundary.equals("")){
			whereIn = whereIn.substring(andSep.length());
			whereIn = "(" + whereIn + ")";
			where += andSep + "("+whereIn + whereBoundary+")";
		}
		if(!dldj.equals("")){
			where += andSep + " dldj = '" + dldj + "'";
		}
		if(!where.equals("")){
			where = " where " + where.substring(andSep.length());
		}
		sql += where;
		sql += " order by sfjl desc,dlmc,qslc,xzqhmc";
		try {
			depts = DBHandler.getMultiResult(sql);
		} catch (Exception e) {
			logger.error("根据道路信息获取相关机构，出错！SQL：\n"+sql, e);
		}
		return depts;
	}

	public Object[][] getRoadByDept(String dept) {
		Object[][] roads = null;
		String sql = "select gbbm,dlmc,xzqh,xzqhmc,qslc,jslc,xqldmc from " + tname ;
		String andSep = " and ";
		String where = "";
		if(!dept.equals("")){
			where += andSep + " xzqhmc like '%" + dept + "%'";
		}
		if(!where.equals("")){
			where = " where " + where.substring(andSep.length());
		}
		sql += where;
		sql += " order by sfjl desc,dldj,dlmc,xzqhmc";
		try {
			roads = DBHandler.getMultiResult(sql);
		} catch (Exception e) {
			logger.error("根据机构名称信息获取相关道路信息，出错！SQL：\n"+sql, e);
		}
		return roads;
	}

	public Object[][] getDeptRoad(String roadName, String rsegName,
			String startKM, String endKM, String xzqhmc, String dldj) {
		Object[][] depts = null;
		String sql = "select gbbm , dlmc, qslc, jslc, xzqh, xzqhmc, xqldmc from " + tname ;
		String andSep = " and ", orSep = " or ";
		String where = "";
		if(!roadName.equals("")){
			where += andSep + " dlmc like '%" + roadName + "%'";
		}
		if(!rsegName.equals("")){
			where += andSep + " xqldmc like '%" + rsegName + "%'";
		}
		String whereIn = "";
		String whereBoundary = "";
		if(!startKM.equals("")){
			whereIn += andSep + " qslc >= " + startKM;
			whereBoundary += orSep + " (qslc <= " + startKM + " and jslc >= " + startKM + ")";
		}
		if(!endKM.equals("")){
			whereIn += andSep + " jslc <= " + endKM;
			whereBoundary += orSep + " (qslc <= " + endKM + " and jslc >= " + endKM + ")";
		}
		if(!whereIn.equals("") && !whereBoundary.equals("")){
			whereIn = whereIn.substring(andSep.length());
			whereIn = "(" + whereIn + ")";
			where += andSep + "("+whereIn + whereBoundary+")";
		}
		if(!xzqhmc.equals("")){
			where += andSep + " xzqhmc like '%" + xzqhmc + "%'";
		}
		if(!dldj.equals("")){
			where += andSep + " dldj = '" + dldj + "'";
		}
		
		if(!where.equals("")){
			where = " where " + where.substring(andSep.length());
		}
		sql += where;
		sql += " order by sfjl desc,dlmc,qslc,xzqhmc";
		try {
			depts = DBHandler.getMultiResult(sql);
		} catch (Exception e) {
			logger.error("根据道路机构信息获取相关道路机构，出错！SQL：\n"+sql, e);
		}
		return depts;
	}

}
