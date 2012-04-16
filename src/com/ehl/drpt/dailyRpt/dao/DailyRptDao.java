package com.ehl.drpt.dailyRpt.dao;
/**
 * 
 * @======================================================================================================================================
 * @类型说明: 春运道路交通安全管理日报操作Dao
 * @创建者：Jason
 * @创建日期 2010-01-11
 * @======================================================================================================================================
 */  
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;

import com.appframe.data.DBFactory;
import com.appframe.data.db.Database;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.base.Constants;
import com.ehl.drpt.common.CommonXML;

public class DailyRptDao {
    private MaxRoadFlowDao maxFlowDao = new MaxRoadFlowDao();
    private DailyReportDao dailyReportDao = new DailyReportDao();
	private Logger logger = Logger.getLogger(DailyRptDao.class);
//	 取出当前的年
	int year = new GregorianCalendar().get(GregorianCalendar.YEAR); 
	/**
	 * @作者:Jason
	 * @版本号：1.0
	 * @函数说明：春运道路交通安全管理日报统计,统计前24项。
	 * @参数：String departmentID:单位编码：
	 *   如果是总队，请传辖区代码的前两位，
	 *   如果是支队，请传辖区代码的前四位，
	 *   如果是大队，请传辖区代码的六位，
	 * @参数：String begDate:开始日期
	 * @参数：String endDat:结束日期
	 * @返回：
	 * @创建日期：2009-11-13
	 */
	
	public String statistics(String departmentID,String begDate,String endDate){
		Object[][] result = null;
		//PrintWriter out = response.getWriter();
		StringBuffer sql = new StringBuffer("select ")
			.append("SUM(TRJL) TRJL,")//TRJL
			.append("SUM(CDJC) CDJC,")//
			.append("SUM(CSSB) CSSB,")
			.append("SUM(GDCSD) GDCSD,")
			.append("SUM(LDCSD) LDCSD,")
			.append("SUM(ZQFWD) ZQFWD,")
			.append("SUM(JTWFHJ) JTWFHJ,")
			.append("SUM(CSXS) CSXS,")
			.append("SUM(KCJY) KCJY,")
			.append("SUM(PLJS) PLJS,")
			.append("SUM(JHJS) JHJS,")
			.append("SUM(WZJS) WZJS,")
			.append("SUM(NYCWFZK) NYCWFZK,")
			.append("SUM(DXJDCJSZ) DXJDCJSZ,")
			.append("SUM(ZKJTWFCL) ZKJTWFCL,")
			.append("SUM(JLJTWFJSR) JLJTWFJSR,")
			.append("SUM(JCKCCL) JCKCCL,")
			.append("SUM(TBKCQYZGBM) TBKCQYZGBM,")
			.append("SUM(SRZYYSQY) SRZYYSQY,")
			.append("SUM(JYYSQYJSR) JYYSQYJSR,")
			.append("SUM(QDELTQYJYA) QDELTQYJYA,")
			.append("SUM(YJSDFLCL) YJSDFLCL,")
			.append("SUM(ZZWXLD) ZZWXLD,")
			.append("SUM(XZZYCK) XZZYCK,")
			.append("SUM(YDTB) YDTB,")
			.append("SUM(PCAQYHC) PCAQYHC,")
			.append("SUM(JYTZKCJSR) JYTZKCJSR,")
			.append("SUM(QZPLJSRXX) QZPLJSRXX,")
			.append("SUM(ZHCFLB) ZHCFLB,")
			.append("SUM(JXXCHD) JXXCHD,")
			.append("SUM(BFXCGP) BFXCGP,")
			.append("SUM(KDXCL) KDXCL,")
			.append("SUM(XCH) XCH,")
			.append("SUM(XCZL) XCZL,")
			.append("SUM(SJY) SJY,")
			.append("SUM(DSXC) DSXC,")
			.append("SUM(DTXC) DTXC,")
			.append("SUM(BZXC) BZXC,")
			.append("SUM(WLXC) WLXC,")
			.append("SUM(ZHS) ZHS,")
			.append("SUM(SWSGZS) SWSGZS,")
			.append("SUM(SWSGSWRS) SWSGSWRS,")
			.append("SUM(SWSGSSRS) SWSGSSRS,")
			.append("SUM(TDSGZS) TDSGZS,")
			.append("SUM(TDSGSWRS) TDSGSWRS,")
			.append("SUM(TDSGSSRS) TDSGSSRS,")
			.append("SUM(SZLSZQD) SZLSZQD,")
			.append("SUM(CSD) CSD,")
			.append("SUM(ZKJDCJSZ) ZKJDCJSZ");
		sql.append(" FROM T_OA_DAYREPORT");
        sql.append(" WHERE 1=1 ");
        if(!"".equals(departmentID)){
        	//sql.append(" AND RZBH LIKE ").append(" '").append(departmentID).append("%'");
        	sql.append(departmentID);
        }
        if(!"".equals(begDate)){
        	sql.append(" AND to_char(TJRQ,'yyyy-mm-dd') >= ").append("'").append(begDate).append("'");
        }
        if(!"".equals(endDate)){
        	sql.append(" AND to_char(TJRQ,'yyyy-mm-dd') <= ").append("'").append(endDate).append("'");
        }
        System.out.println("春运道路交通安全管理日报统计sql="+sql.toString());
        logger.info("春运道路交通安全管理日报统计sql="+sql.toString());
        try {
			result = DBHandler.getMultiResult(sql.toString());
		} catch (Exception e) {
			logger.error("春运道路交通安全管理统计出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return CommonXML.getXML(result);
	}
	
	/**
	 * @作者:Jason
	 * @版本号：1.0
	 * @函数说明：春运道路交通安全管理日报统计,统计前24项。
	 * @参数：String departmentID:单位编码：
	 *   如果是总队，请传辖区代码的前两位，
	 *   如果是支队，请传辖区代码的前四位，
	 *   如果是大队，请传辖区代码的六位，
	 * @参数：String begDate:开始日期
	 * @参数：String endDat:结束日期
	 * @返回：
	 * @创建日期：2009-11-13
	 */
	
	public Object[][] statExcel(String departmentID,String begDate,String endDate){
		Object[][] result = null;
		//PrintWriter out = response.getWriter();
		StringBuffer sql = new StringBuffer("select ")
			.append("SUM(TRJL) TRJL,")
			.append("SUM(CDJC) CDJC,")
			.append("SUM(CSSB) CSSB,")
			.append("SUM(GDCSD) GDCSD,")
			.append("SUM(LDCSD) LDCSD,")
			.append("SUM(ZQFWD) ZQFWD,")
			.append("SUM(JTWFHJ) JTWFHJ,")
			.append("SUM(CSXS) CSXS,")
			.append("SUM(KCJY) KCJY,")
			.append("SUM(PLJS) PLJS,")
			.append("SUM(JHJS) JHJS,")
			.append("SUM(WZJS) WZJS,")
			.append("SUM(NYCWFZK) NYCWFZK,")
			.append("SUM(DXJDCJSZ) DXJDCJSZ,")
			.append("SUM(ZKJTWFCL) ZKJTWFCL,")
			.append("SUM(JLJTWFJSR) JLJTWFJSR,")
			.append("SUM(JCKCCL) JCKCCL,")
			.append("SUM(TBKCQYZGBM) TBKCQYZGBM,")
			.append("SUM(SRZYYSQY) SRZYYSQY,")
			.append("SUM(JYYSQYJSR) JYYSQYJSR,")
			.append("SUM(QDELTQYJYA) QDELTQYJYA,")
			.append("SUM(YJSDFLCL) YJSDFLCL,")
			.append("SUM(ZZWXLD) ZZWXLD,")
			.append("SUM(XZZYCK) XZZYCK,")
			.append("SUM(YDTB) YDTB,")
			.append("SUM(PCAQYHC) PCAQYHC,")
			.append("SUM(JYTZKCJSR) JYTZKCJSR,")
			.append("SUM(QZPLJSRXX) QZPLJSRXX,")
			.append("SUM(ZHCFLB) ZHCFLB,")
			.append("SUM(JXXCHD) JXXCHD,")
			.append("SUM(BFXCGP) BFXCGP,")
			.append("SUM(KDXCL) KDXCL,")
			.append("SUM(XCH) XCH,")
			.append("SUM(XCZL) XCZL,")
			.append("SUM(SJY) SJY,")
			.append("SUM(DSXC) DSXC,")
			.append("SUM(DTXC) DTXC,")
			.append("SUM(BZXC) BZXC,")
			.append("SUM(WLXC) WLXC,")
			.append("SUM(ZHS) ZHS,")
			.append("SUM(SWSGZS) SWSGZS,")
			.append("SUM(SWSGSWRS) SWSGSWRS,")
			.append("SUM(SWSGSSRS) SWSGSSRS,")
			.append("SUM(TDSGZS) TDSGZS,")
			.append("SUM(TDSGSWRS) TDSGSWRS,")
			.append("SUM(TDSGSSRS) TDSGSSRS,")
			.append("SUM(SZLSZQD) SZLSZQD");
		sql.append(" FROM T_OA_DAYREPORT");
        sql.append(" WHERE 1=1 ");
        if(!"".equals(departmentID)){
        	//sql.append(" AND RZBH LIKE ").append(" '").append(departmentID).append("%'");
        	sql.append(departmentID);
        }
        if(!"".equals(begDate)){
        	sql.append(" AND to_char(TJRQ,'yyyy-mm-dd') >= ").append("'").append(begDate).append("'");
        }
        if(!"".equals(endDate)){
        	sql.append(" AND to_char(TJRQ,'yyyy-mm-dd') <= ").append("'").append(endDate).append("'");
        }
        System.out.println("春运道路交通安全管理日报统计sql="+sql.toString());
        logger.info("春运道路交通安全管理日报统计sql="+sql.toString());
        try {
			result = DBHandler.getMultiResult(sql.toString());
		} catch (Exception e) {
			logger.error("春运道路交通安全管理统计出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * @作者:Jason
	 * @版本号：1.0
	 * @函数说明：春运道路交通安全管理周报统计，用于总队上报公安部。
	 * @参数：String departmentID:单位编码sql：
	 * @参数：String begDate:开始日期
	 * @参数：String endDat:结束日期
	 * @返回：
	 * @创建日期：2009-11-13
	 */
	
	public String weekStat(String departmentID,String begDate,String endDate){
		Object[][] result = null;
		//PrintWriter out = response.getWriter();
		StringBuffer sql = new StringBuffer("select ")
			.append("SUM(TRJL) TRJL,")       //投入警力
			.append("SUM(CDJC) CDJC,")       //触动警车
			.append("SUM(ZQFWD) ZQFWD,")     //启动检查服务站，对应于表中的“设置春运执勤服务点”字段。
			.append("SUM(SZLSZQD) SZLSZQD,") // "设置临时执勤点"
			.append("SUM(JCKCCL) JCKCCL,")   //检查客车车辆
			.append("SUM(JTWFHJ) JTWFHJ,")   //交通违法汇总
			.append("SUM(CSXS) CSXS,")       //超速行驶
			.append("SUM(KCJY) KCJY,")       //客车超员
			.append("SUM(PLJS) PLJS,")       //疲劳驾驶
			.append("SUM(JHJS) JHJS,")       //酒后驾车
			.append("SUM(DXJDCJSZ) + SUM(ZKJDCJSZ) DXZKJDCJSZ,")  //吊销驾照
			.append("SUM(XZZYCK) XZZYCK,")      //卸车转运
			.append("SUM(TBKCQYZGBM) TBKCQYZGBM,")   //通报客运企业主管部门
			.append("SUM(SRZYYSQY) SRZYYSQY,")
			.append("SUM(PCAQYHC) PCAQYHC,")//教育运输企业驾驶人JYYSJSR  2011年改为统计 排查隐患车辆PCAQYHC
			.append("(SUM(DSXC)+SUM(DTXC))  GBDS,")//通过广播，电视开展宣传（次）
			.append("(SUM(KDXCL)+SUM(XCH)+SUM(XCZL)) XCZL,")//发放宣传材料，设置宣传提示牌（份、块）
			.append("SUM(QDELTQYJYA) QDELTQYJYA,")
			.append("SUM(YJSDFLCL) YJSDFLCL,")
			.append("SUM(ZZWXLD) ZZWXLD");
			
		sql.append(" FROM T_OA_DAYREPORT");
        sql.append(" WHERE 1=1 ");
        if(!"".equals(departmentID)){
        	sql.append(departmentID);
        }
        if(!"".equals(begDate)){
        	sql.append(" AND to_char(TJRQ,'yyyy-mm-dd') >= ").append("'").append(begDate).append("'");
        }
        if(!"".equals(endDate)){
        	sql.append(" AND to_char(TJRQ,'yyyy-mm-dd') <= ").append("'").append(endDate).append("'");
        }
        System.out.println("春运道路交通安全管理周报统计sql="+sql.toString());
        logger.info("春运道路交通安全管理周报统计sql="+sql.toString());
        try {
			result = DBHandler.getMultiResult(sql.toString());
		} catch (Exception e) {
			logger.error("春运道路交通安全管理周报统计出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return CommonXML.getXML(result);
	}
	
	public String infoCalBack(String departmentID,String begDate,String endDate){
		Object[][] result = null;
		//PrintWriter out = response.getWriter();
		StringBuffer sql = new StringBuffer("select ")
			.append("SUM(TRJL) TRJL,")
			.append("SUM(CDJC) CDJC,")
			.append("SUM(CSSB) CSSB,")
			.append("SUM(GDCSD) GDCSD,")
			.append("SUM(LDCSD) LDCSD,")
			.append("SUM(ZQFWD) ZQFWD,")
			.append("SUM(JTWFHJ) JTWFHJ,")
			.append("SUM(CSXS) CSXS,")
			.append("SUM(KCJY) KCJY,")
			.append("SUM(PLJS) PLJS,")
			.append("SUM(JHJS) JHJS,")
			.append("SUM(WZJS) WZJS,")
			.append("SUM(NYCWFZK) NYCWFZK,")
			.append("SUM(DXJDCJSZ) DXJDCJSZ,")
			.append("SUM(ZKJTWFCL) ZKJTWFCL,")
			.append("SUM(JLJTWFJSR) JLJTWFJSR,")
			.append("SUM(JCKCCL) JCKCCL,")
			.append("SUM(TBKCQYZGBM) TBKCQYZGBM,")
			.append("SUM(SRZYYSQY) SRZYYSQY,")
			.append("SUM(JYYSQYJSR) JYYSQYJSR,")
			.append("SUM(QDELTQYJYA) QDELTQYJYA,")
			.append("SUM(YJSDFLCL) YJSDFLCL,")
			.append("SUM(ZZWXLD) ZZWXLD,")
			.append("SUM(XZZYCK) XZZYCK,")
			.append("SUM(YDTB) YDTB,")
			.append("SUM(PCAQYHC) PCAQYHC,")
			.append("SUM(JYTZKCJSR) JYTZKCJSR,")
			.append("SUM(QZPLJSRXX) QZPLJSRXX,")
			.append("SUM(ZHCFLB) ZHCFLB,")
			.append("SUM(JXXCHD) JXXCHD,")
			.append("SUM(BFXCGP) BFXCGP,")
			.append("SUM(KDXCL) KDXCL,")
			.append("SUM(XCH) XCH,")
			.append("SUM(XCZL) XCZL,")
			.append("SUM(SJY) SJY,")
			.append("SUM(DSXC) DSXC,")
			.append("SUM(DTXC) DTXC,")
			.append("SUM(BZXC) BZXC,")
			.append("SUM(WLXC) WLXC,")
			.append("SUM(ZHS) ZHS,")
			.append("SUM(SWSGZS) SWSGZS,")
			.append("SUM(SWSGSWRS) SWSGSWRS,")
			.append("SUM(SWSGSSRS) SWSGSSRS,")
			.append("SUM(TDSGZS) TDSGZS,")
			.append("SUM(TDSGSWRS) TDSGSWRS,")
			.append("SUM(TDSGSSRS) TDSGSSRS,")
			.append("SUM(SZLSZQD) SZLSZQD,")//设置临时执勤点
			.append("SUM(CSD) CSD,")
			.append("SUM(ZKJDCJSZ) ZKJDCJSZ");
		sql.append(" FROM T_OA_DAYREPORT");
        sql.append(" WHERE 1=1 ");
        if(!"".equals(departmentID)){
        	//sql.append(" AND RZBH LIKE ").append(" '").append(departmentID).append("%'");
        	sql.append(departmentID);
        }
        if(!"".equals(begDate)){
        	sql.append(" AND to_char(TJRQ,'yyyy-mm-dd') >= ").append("'").append(begDate).append("'");
        }
        if(!"".equals(endDate)){
        	sql.append(" AND to_char(TJRQ,'yyyy-mm-dd') <= ").append("'").append(endDate).append("'");
        }
        System.out.println("春运道路交通安全管理日报回显sql="+sql.toString());
        logger.info("春运道路交通安全管理日报回显sql="+sql.toString());
        try {
			result = DBHandler.getMultiResult(sql.toString());
		} catch (Exception e) {
			logger.error("春运道路交通安全管理回显出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return CommonXML.getXML(result);
	}
	
	/**
	 * @作者:Jason
	 * @版本号：1.0
	 * @函数说明：春运道路交通安全管理日报统计,统计第25项：车流量。
	 * @参数：String departmentID:单位编码：
	 *   如果是总队，请传辖区代码的前两位，
	 *   如果是支队，请传辖区代码的前四位，
	 *   如果是大队，请传辖区代码的六位，
	 * @参数：String begDate:开始日期
	 * @参数：String endDat:结束日期
	 * @返回：
	 * @创建日期：2009-11-13
	 */
	public String tfmStat(String departmentID,String begDate,String endDate){
//		Object[][] result = null;
	    Object[][] result = maxFlowDao.getByJgid(departmentID, begDate, endDate,com.ehl.drpt.common.Constants.MAX_QUERY_LINE);
	    String xml = CommonXML.getXML(result);
	    return xml;
	}
	
	/**
	 * @作者:Jason
	 * @版本号：1.0
	 * @函数说明：春运道路交通安全管理日报统计,统计第25项：车流量。
	 * @参数：String departmentID:单位编码：
	 *   如果是总队，请传辖区代码的前两位，
	 *   如果是支队，请传辖区代码的前四位，
	 *   如果是大队，请传辖区代码的六位，
	 * @参数：String begDate:开始日期
	 * @参数：String endDat:结束日期
	 * @返回：
	 * @创建日期：2009-11-13
	 */
	public Object[][] tfmStatExcel(String departmentID,String begDate,String endDate){
		Object[][] result = null;
		StringBuffer sql = new StringBuffer("select t.dlmc,t.ldmc,t.carnum,t.kc,t.zjc from (")
		.append("select  distinct mrf.bh,");
		sql.append(" dr.dlmc,mrf.ldmc,mrf.carnum,mrf.kc,mrf.zjc")
		   .append(" from")
		   .append(" t_oa_maxroadflow mrf,")
		   .append(" t_oa_dict_road   dr")
		   .append(" where mrf.dlbh=dr.dlbh")
		   .append(" and mrf.rzbh in(")
		   .append(" select drpt.rzbh from t_oa_dayreport drpt")
           .append(" WHERE 1=1 ");
       
        if(!"".equals(departmentID)){
        	//sql.append(" AND drpt.RZBH LIKE ").append(" '").append(departmentID).append("%'");
        	sql.append(departmentID);
        }
        if(!"".equals(begDate)){
        	sql.append(" AND to_char(drpt.TJRQ,'yyyy-mm-dd') >= ").append("'").append(begDate).append("'");
        }
        if(!"".equals(endDate)){
        	sql.append(" AND to_char(drpt.TJRQ,'yyyy-mm-dd') <= ").append("'").append(endDate).append("'");
        }
        
	    sql.append(")")
	       .append(" order by mrf.carnum desc ");
	    sql.append(") t");
	    sql.append(" WHERE rownum <")
	       .append(com.ehl.drpt.common.Constants.MAX_QUERY_LINE+1);
        System.out.println("春运道路交通安全管理日报车流量统计sql="+sql.toString());
	    logger.info("春运道路交通安全管理日报车流量统计sql="+sql.toString());
        try {
			result = DBHandler.getMultiResult(sql.toString());
		} catch (Exception e) {
			logger.error("春运道路交通安全管理统计出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	public String tfmCalBack(String departmentID,String begDate,String endDate){
		Object[][] result = maxFlowDao.getByJgid(departmentID, begDate, endDate);
		return CommonXML.getXML(result);
	}
	
	
	/**
	 * @作者:dxn
	 * @版本号：1.0
	 * @函数说明：春运道路交通安全管理日报新增界面获取填报单位
	 * @参数：
	 * @返回：填报单位id,名称
	 * @创建日期：2010-01-11
	 */
	
	public Object[] getRptDpt(String username) {
		Object[] result = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select jgid, jgmc from t_sys_department where jgid = (select")
			.append(" deptcode from t_sys_user where username='").append(username)
			.append("')");
		System.out.println("getRptDpt.sql===>" + sql.toString());
		logger.info("春运道路交通安全管理日报获取填报单位sql=" + sql.toString());
		try {
			result = DBHandler.getLineResult(sql.toString());
		} catch (Exception e) {
			logger.error("春运道路交通安全管理统计出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	public String createSequence(String jgid){
	    return "'"+jgid.substring(0, 6)+"'"+"||to_char(sysdate,'yyyyMMddHH24MISSSSS')";
	}
	
	/**
	 * @作者:dxn
	 * @版本号：1.0
	 * @函数说明：春运道路交通安全管理日报新增
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-13
	 */
	
	public boolean addDailyRpt(Object[][] result, String RZBH) {
		boolean chk = false;
		StringBuffer sql = new StringBuffer();
		for(int i = 0; i < result.length; i ++){
			/*for(int j = 0; j < result[i].length; j ++){
				result[i][j] = result[i][j]
			}*/
//			if(!"".equals(result[i][40])){
//				sql.append("insert into T_OA_MAXROADFLOW (BH, DLBH, LDMC, DLFX,CARNUM, ")
//					.append("KC, ZJC, RZBH) values ('").append(RZBH.substring(0,RZBH.length()-1) + "1")
//					.append("', '").append(result[i][40]).append("', '").append(result[i][41])
//					.append("', ").append(result[i][74]).append(", ").append(result[i][42]).append(", ")
//					.append(result[i][43])
//					.append(", ").append(result[i][44]).append(", '").append(RZBH).append("')");
//				System.out.println("1 sql ==>" + sql.toString());
//				chk = DBHandler.execute(sql.toString());
//				if(!chk)	return false;
//				sql.delete(0,sql.length()); 
//			}
//			if(!"".equals(result[i][45])){
//				sql.append("insert into T_OA_MAXROADFLOW (BH, DLBH, LDMC, DLFX, CARNUM, ")
//					.append("KC, ZJC, RZBH) values ('").append(RZBH.substring(0,RZBH.length()-1) + "2")
//					.append("', '").append(result[i][45]).append("', '").append(result[i][46])
//					.append("', ").append(result[i][75]).append(", ").append(result[i][47]).append(", ").append(result[i][48])
//					.append(", ").append(result[i][49]).append(", '").append(RZBH).append("')");
//				System.out.println("2 sql ==>" + sql.toString());
//				chk = DBHandler.execute(sql.toString());
//				if(!chk)	return false;
//				sql.delete(0,sql.length()); 
//			}
//			if(!"".equals(result[i][50])){
//				sql.append("insert into T_OA_MAXROADFLOW (BH, DLBH, LDMC, DLFX, CARNUM, ")
//					.append("KC, ZJC, RZBH) values ('").append(RZBH.substring(0,RZBH.length()-1) + "3")
//					.append("', '").append(result[i][50]).append("', '").append(result[i][51]).append("', ")
//					.append(result[i][76])
//					.append(", ").append(result[i][52]).append(", ").append(result[i][53])
//					.append(", ").append(result[i][54]).append(", '").append(RZBH).append("')");
//				System.out.println("3 sql ==>" + sql.toString());
//				chk = DBHandler.execute(sql.toString());
//				if(!chk)	return false;
//				sql.delete(0,sql.length()); 
//			}
//			if(!"".equals(result[i][55])){
//				sql.append("insert into T_OA_MAXROADFLOW (BH, DLBH, LDMC, DLFX, CARNUM, ")
//					.append("KC, ZJC, RZBH) values ('").append(RZBH.substring(0,RZBH.length()-1) + "4")
//					.append("', '").append(result[i][55]).append("', '").append(result[i][56]).append("', ")
//					.append(result[i][77]).append(", ").append(result[i][57])
//					.append(", ").append(result[i][58])
//					.append(", ").append(result[i][59]).append(", '").append(RZBH).append("')");
//				System.out.println("4 sql ==>" + sql.toString());
//				chk = DBHandler.execute(sql.toString());
//				if(!chk)	return false;
//				sql.delete(0,sql.length()); 
//			}
		    if(!insertMaxRoadFlow(result[i], RZBH)){
			return false;
		    }
		    Map<String,String> map = getDailyReport(result[i], RZBH);
		    chk = dailyReportDao.insert(map)!=null;
//			sql.append("insert into T_OA_DAYREPORT (RZBH, TRJL, CDJC, CSSB, GDCSD, LDCSD, ")
//				.append("ZQFWD, JTWFHJ, CSXS, KCJY, PLJS, JHJS, WZJS, NYCWFZK, DXJDCJSZ, ")
//				.append("ZKJTWFCL, JLJTWFJSR, XZZYCK, YDTB, PCAQYHC, JYTZKCJSR, QZPLJSRXX,")
//				.append(" ZHCFLB, JXXCHD, BFXCGP, KDXCL, XCH, XCZL, SJY, DSXC, DTXC, BZXC,")
//				.append(" WLXC, ZHS, TBDW, TJRQ, TBR, SHR, LXDH, JCKCCL, TBKCQYZGBM, SRZYYSQY,")
//				.append(" JYYSQYJSR, QDELTQYJYA, YJSDFLCL, ZZWXLD, SWSGZS, SWSGSWRS, SWSGSSRS,")
//				.append(" TDSGZS, TDSGSWRS, TDSGSSRS, SZLSZQD,CSD) values ('").append(RZBH)
//				.append("', ").append(result[i][4]).append(", ").append(result[i][5])
//				.append(", ").append(result[i][6]).append(", ").append(result[i][7])
//				.append(", ").append(result[i][8]).append(", ").append(result[i][9])
//				.append(", ").append(result[i][10]).append(", ").append(result[i][11])
//				.append(", ").append(result[i][12]).append(", ").append(result[i][13])
//				.append(", ").append(result[i][14]).append(", ").append(result[i][15])
//				.append(", ").append(result[i][16]).append(", ").append(result[i][17])
//				.append(", ").append(result[i][18]).append(", ").append(result[i][19])
//				.append(", ").append(result[i][20]).append(", ").append(result[i][21])
//				.append(", ").append(result[i][22]).append(", ").append(result[i][23])
//				.append(", ").append(result[i][24]).append(", ").append(result[i][25])
//				.append(", ").append(result[i][26]).append(", ").append(result[i][27])
//				.append(", ").append(result[i][28]).append(", ").append(result[i][29])
//				.append(", ").append(result[i][30]).append(", ").append(result[i][31])
//				.append(", ").append(result[i][32]).append(", ").append(result[i][33])
//				.append(", ").append(result[i][34]).append(", ").append(result[i][35])
//				.append(", ").append(result[i][36]).append(", '").append(result[i][1])
//				.append("', to_date('"+year+"-").append(result[i][2]).append("-")
//				.append(result[i][3]).append("', 'YYYY-MM-DD'), '").append(result[i][37])
//				.append("', '").append(result[i][38]).append("', '").append(result[i][39])
//				.append("', ").append(result[i][60]).append(", ").append(result[i][61])
//				.append(", ").append(result[i][62]).append(", ").append(result[i][63])
//				.append(", ").append(result[i][64]).append(", ").append(result[i][65])
//				.append(", ").append(result[i][66]).append(", ").append(result[i][67])
//				.append(", ").append(result[i][68]).append(", ").append(result[i][69])
//				.append(", ").append(result[i][70]).append(", ").append(result[i][71])
//				.append(", ").append(result[i][72]).append(", ").append(result[i][73])
//				.append(",").append(result[i][40])
//				.append(")");
//			System.out.println("5 sql ==>" + sql.toString());
//			logger.info("春运道路交通安全管理日报新增sql="+sql.toString());
//			try {
//				chk = DBHandler.execute(sql.toString());
//			} catch (Exception e) {
//				logger.error("春运道路交通安全管理日报新增:");
//				logger.error(e.getMessage());
//				e.printStackTrace();
//				return false;
//			}
		}
		return chk;
	}
	
	private  Map<String, String> getDailyReport(Object[] objects,String rzbh){
	    Map<String, String> map = new HashMap<String, String>();
	    String[] cnames = {"rzbh","tbdw","tjrq","trjl","cdjc","cssb","gdcsd","ldcsd","zqfwd",
		"jtwfhj","csxs","kcjy","pljs","jhjs","wzjs","nycwfzk",
		"dxjdcjsz","zkjtwfcl","jljtwfjsr","xzzyck","ydtb",
		"pcaqyhc","jytzkcjsr","qzpljsrxx","zhcflb","jxxchd",
		"bfxcgp","kdxcl","xch","xczl","sjy","dsxc","dtxc","bzxc",
		"wlxc","zhs","tbr","shr","lxdh","csd","zkjdcjsz","jckccl","tbkcqyzgbm",
		"srzyysqy","jyysqyjsr","qdeltqyjya","yjsdflcl","zzwxld",
		"swsgzs","swsgswrs","swsgssrs","tdsgzs","tdsgswrs","tdsgssrs","szlszqd"};
	    map.put("rzbh", rzbh);
	    map.put("tbdw", (String)objects[1]);
	    map.put("tjrq", year+"-"+objects[2]+"-"+objects[3]);
	    int s1 = 4,e1=41;
	    for (int i = s1; i <= e1; i++) {
		map.put(cnames[i-1], String.valueOf(objects[i]));
	    }
	    int s2=60,e2=73;
	    int differ = s2 - e1;
	    for (int i = s2; i <= e2; i++) {
		map.put(cnames[i-differ], String.valueOf(objects[i]));
	    }
	    return map;
	}
	
    private List<Map<String, String>> getMaxRoadFlow(Object[] objects,
	    String rzbh) {
	List<Map<String, String>> objectList = new ArrayList<Map<String, String>>();
	String[] cnames = { "dlbh", "ldmc", "carnum", "kc", "zjc", 
		"avg", "lrratio" };
	int length = cnames.length;
	String bh = rzbh.substring(0,rzbh.length()-1);
	int start = 74;
	for (int i = start; i < objects.length;) {
	    Map<String, String> object = new HashMap<String, String>();
	    object.put("rzbh", rzbh);
	    object.put("bh", bh + ((i-start)/length + 1));
	    for (int j = 0; j < cnames.length; j++) {
		object.put(cnames[j], String.valueOf(objects[i]));
		i++;
	    }
	    objectList.add(object);
	}
	return objectList;
    }
    
    private boolean insertMaxRoadFlow(Object[] objects, String rzbh) {
	List<Map<String, String>> objectList = getMaxRoadFlow(objects, rzbh);
	for (Map<String, String> object : objectList) {
	    if (maxFlowDao.insert(object) == null) {
		return false;
	    }
	}
	return true;
    }
	
	
	/**
	 * @作者:dxn
	 * @版本号：1.0
	 * @函数说明：春运道路交通安全管理日报编辑
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-13
	 */
	
	public boolean updateDailyRpt(Object[][] result, String RZBH) {
		boolean chk = false;
		StringBuffer sql = new StringBuffer();
		for(int i = 0; i < result.length; i ++){
			String RZBH0 = result[i][0].toString();
//			sql.append("delete from T_OA_MAXROADFLOW where rzbh='").append(RZBH0).append("'");
//			//严禁使用DBFactory.newDatabase().executeUpdate(sql.toString());
//			DBHandler.execute(sql.toString());
//			sql.delete(0,sql.length()); 
//			if(!"".equals(result[i][40])){
//				sql.append("insert into T_OA_MAXROADFLOW (BH, DLBH, LDMC, DLFX,CARNUM, ")
//					.append("KC, ZJC, RZBH) values ('").append(RZBH.substring(0,RZBH.length()-1) + "1")
//					.append("', '").append(result[i][40]).append("', '").append(result[i][41])
//					.append("', ").append(result[i][74]).append(", ").append(result[i][42]).append(", ")
//					.append(result[i][43])
//					.append(", ").append(result[i][44]).append(", '").append(RZBH).append("')");
//				System.out.println("1 sql ==>" + sql.toString());
//				chk = DBHandler.execute(sql.toString());
//				if(!chk)	return false;
//				sql.delete(0,sql.length()); 
//			}
//			if(!"".equals(result[i][45])){
//				sql.append("insert into T_OA_MAXROADFLOW (BH, DLBH, LDMC, DLFX, CARNUM, ")
//					.append("KC, ZJC, RZBH) values ('").append(RZBH.substring(0,RZBH.length()-1) + "2")
//					.append("', '").append(result[i][45]).append("', '").append(result[i][46])
//					.append("', ").append(result[i][75]).append(", ").append(result[i][47]).append(", ").append(result[i][48])
//					.append(", ").append(result[i][49]).append(", '").append(RZBH).append("')");
//				System.out.println("2 sql ==>" + sql.toString());
//				chk = DBHandler.execute(sql.toString());
//				if(!chk)	return false;
//				sql.delete(0,sql.length()); 
//			}
//			if(!"".equals(result[i][50])){
//				sql.append("insert into T_OA_MAXROADFLOW (BH, DLBH, LDMC, DLFX, CARNUM, ")
//					.append("KC, ZJC, RZBH) values ('").append(RZBH.substring(0,RZBH.length()-1) + "3")
//					.append("', '").append(result[i][50]).append("', '").append(result[i][51]).append("', ")
//					.append(result[i][76])
//					.append(", ").append(result[i][52]).append(", ").append(result[i][53])
//					.append(", ").append(result[i][54]).append(", '").append(RZBH).append("')");
//				System.out.println("3 sql ==>" + sql.toString());
//				chk = DBHandler.execute(sql.toString());
//				if(!chk)	return false;
//				sql.delete(0,sql.length()); 
//			}
//			if(!"".equals(result[i][55])){
//				sql.append("insert into T_OA_MAXROADFLOW (BH, DLBH, LDMC, DLFX, CARNUM, ")
//					.append("KC, ZJC, RZBH) values ('").append(RZBH.substring(0,RZBH.length()-1) + "4")
//					.append("', '").append(result[i][55]).append("', '").append(result[i][56]).append("', ")
//					.append(result[i][77]).append(", ").append(result[i][57])
//					.append(", ").append(result[i][58])
//					.append(", ").append(result[i][59]).append(", '").append(RZBH).append("')");
//				System.out.println("4 sql ==>" + sql.toString());
//				chk = DBHandler.execute(sql.toString());
//				if(!chk)	return false;
//				sql.delete(0,sql.length()); 
//			}
			maxFlowDao.deleteByRzbh(RZBH0);
			if(!insertMaxRoadFlow(result[i], RZBH)){
			    return false;
			}
			Map<String,String> map = getDailyReport(result[i], RZBH);
			chk = dailyReportDao.modifyById(map,RZBH0);
//			sql.append("update T_OA_DAYREPORT set RZBH='").append(RZBH)
//				.append("', TRJL=").append(result[i][4])
//				.append(", CDJC=").append(result[i][5]).append(", CSSB=")
//				.append(result[i][6]).append(", GDCSD=").append(result[i][7])
//				.append(", LDCSD=").append(result[i][8]).append(", ZQFWD=")
//				.append(result[i][9]).append(", JTWFHJ=").append(result[i][10])
//				.append(", CSXS=").append(result[i][11]).append(", KCJY=")
//				.append(result[i][12]).append(", PLJS=").append(result[i][13])
//				.append(", JHJS=").append(result[i][14]).append(", WZJS=")
//				.append(result[i][15]).append(", NYCWFZK=").append(result[i][16])
//				.append(", DXJDCJSZ=").append(result[i][17]).append(", ZKJTWFCL=")
//				.append(result[i][18]).append(", JLJTWFJSR=").append(result[i][19])
//				.append(", XZZYCK=").append(result[i][20]).append(", YDTB=")
//				.append(result[i][21]).append(", PCAQYHC=").append(result[i][22])
//				.append(", JYTZKCJSR=").append(result[i][23]).append(", QZPLJSRXX=")
//				.append(result[i][24]).append(", ZHCFLB=").append(result[i][25])
//				.append(", JXXCHD=").append(result[i][26]).append(", BFXCGP=")
//				.append(result[i][27]).append(", KDXCL=").append(result[i][28])
//				.append(", XCH=").append(result[i][29]).append(", XCZL=")
//				.append(result[i][30]).append(", SJY=").append(result[i][31])
//				.append(", DSXC=").append(result[i][32]).append(", DTXC=")
//				.append(result[i][33]).append(", BZXC=").append(result[i][34])
//				.append(", WLXC=").append(result[i][35]).append(", ZHS=")
//				.append(result[i][36]).append(", TBDW='").append(result[i][1])
//				.append("', TJRQ=").append("to_date('"+year+"-").append(result[i][2])
//				.append("-").append(result[i][3]).append("', 'YYYY-MM-DD'), TBR='")
//				.append(result[i][37]).append("',SHR='").append(result[i][38])
//				.append("', LXDH='").append(result[i][39]).append("', JCKCCL=")
//				.append(result[i][60]).append(", TBKCQYZGBM=").append(result[i][61])
//				.append(", SRZYYSQY=").append(result[i][62]).append(", JYYSQYJSR=")
//				.append(result[i][63]).append(", QDELTQYJYA=").append(result[i][64])
//				.append(", YJSDFLCL=").append(result[i][65]).append(", ZZWXLD=")
//				.append(result[i][66]).append(", SWSGZS=").append(result[i][67])
//				.append(", SWSGSWRS=").append(result[i][68]).append(", SWSGSSRS=")
//				.append(result[i][69]).append(", TDSGZS=").append(result[i][70])
//				.append(", TDSGSWRS=").append(result[i][71]).append(", TDSGSSRS=")
//				.append(result[i][72]).append(", SZLSZQD=").append(result[i][73])
//				.append(",CSD=").append(result[i][40])
//				.append(" where RZBH='").append(RZBH0).append("'");
//			
//			System.out.println("-------------" + sql.toString());
//			logger.info("春运道路交通安全管理日报编辑sql="+sql.toString());
//			try {
//				chk = DBHandler.execute(sql.toString());
//			} catch (Exception e) {
//				logger.error("春运道路交通安全管理日报编辑:");
//				logger.error(e.getMessage());
//				e.printStackTrace();
//				return false;
//			}
		}
		return chk;
	}
	
	/**
	 * @作者:dxn
	 * @版本号：1.0
	 * @函数说明：春运道路交通安全管理日报回显
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-13
	 */
	
	public String query(String RZBH){
		Object[][] result = null;
		//PrintWriter out = response.getWriter();
		
		StringBuffer sql = new StringBuffer("select RZBH, TRJL, CDJC, CSSB, GDCSD, ")
			.append(" LDCSD, ZQFWD, JTWFHJ, CSXS, KCJY, PLJS, JHJS, WZJS, NYCWFZK,")
			.append("DXJDCJSZ, ZKJTWFCL, JLJTWFJSR, XZZYCK, YDTB, PCAQYHC, JYTZKCJSR, ")
			.append("QZPLJSRXX, ZHCFLB, JXXCHD, BFXCGP, KDXCL, XCH, XCZL, SJY, DSXC, ")
			.append("DTXC, BZXC, WLXC, ZHS, TBDW, to_char(tjrq,'yyyy-mm-dd') as tjrq, ")
			.append("TBR, SHR, LXDH, (select jgmc from t_sys_department where jgid =")
			.append(" tbdw) as tbdwmc, JCKCCL, TBKCQYZGBM, SRZYYSQY, JYYSQYJSR, ")
			.append("QDELTQYJYA, YJSDFLCL, ZZWXLD, SWSGZS, SWSGSWRS, SWSGSSRS,")
			.append(" TDSGZS, TDSGSWRS, TDSGSSRS, SZLSZQD,CSD,ZKJDCJSZ from t_oa_dayreport where RZBH='")
			.append(RZBH).append("'");
        
		System.out.println("春运道路交通安全管理日报回显sql="+sql.toString());
        logger.info("春运道路交通安全管理日报回显sql="+sql.toString());
        try {
			result = DBHandler.getMultiResult(sql.toString());
		} catch (Exception e) {
			logger.error("春运道路交通安全管理回显出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return CommonXML.getXML(result);
	}
	
	public String queryRoad(String RZBH){
	    
	    Object[][] result = maxFlowDao.getByRzbh(RZBH);
	    String xml = CommonXML.getXML(result);
	    return xml;
	}
	
	/**
	 * @作者:dxn
	 * @版本号：1.0
	 * @函数说明：春运道路交通安全管理日报删除
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-13
	 */
	
	@SuppressWarnings("finally")
	public boolean delete(String RZBH){
		boolean chk = true;
		Database db = null;
		try{
//			db = DBFactory.newDatabase();
//			db.beginTrans();
			StringBuffer sql0 = new StringBuffer("delete from T_OA_MAXROADFLOW where rzbh='")
				.append(RZBH).append("'");
			StringBuffer sql = new StringBuffer("delete from t_oa_dayreport where RZBH='")
				.append(RZBH).append("'");
//			if (db.executeUpdate(sql0.toString())>=0 && db.executeUpdate(sql.toString()) >= 0) {
//				db.commitTrans(false);
//			} else {
//				db.commitTrans(true);
//				chk = false;
//			}
			
			DBHandler.execute(sql0.toString());
			 DBHandler.execute(sql.toString());
			
		
			System.out.println(chk);
		}catch (Exception e) {
			chk = false;
		} finally {
			//DBFactory.closeDatabase(db);
			return chk;
		}
	}
	
	public boolean chkRzbh(String rzbh_0,String rzbh_1) {
		boolean flag = false;
		String sql = "select * from t_oa_dayreport where rzbh > '" + rzbh_0 + "' and rzbh < '" + rzbh_1 + "'";
		try {
			flag = null == DBHandler.getMultiResult(sql.toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * <p></p>
	 * @param jgid 机构id
	 * @return boolean
	 * 
	 */
	public int getHourPrmt(){
		String sql = "select paravalue from t_sys_para where paraid='90011'";
		int p_hour = 10;
		try {
			p_hour = Integer.parseInt(DBHandler.getSingleResult(sql).toString());
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return p_hour;
	}
	
	/**
	 * <p>根据机构id判断是否已填报当天日报</p>
	 * @param jgid 机构id
	 * @return boolean
	 * 
	 */
	public boolean chkTimeWithDept(String jgid,String tjrq){
		//总队
		boolean flag = false;
		Object res = null;
		String sql = "SELECT RZBH FROM T_OA_DAYREPORT WHERE TBDW='" + jgid 
			+ "' and  TJRQ=to_date('" + tjrq + "','yyyy-mm-dd')";
		System.out.println("---------------chkTimeWithDept=" + sql);
		try {
			res = DBHandler.getSingleResult(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(res == null){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * <p>根据统计日期判断是否可编辑日报</p>
	 * @param 
	 * @return boolean
	 * 
	 */
	public boolean chkTimeWithTjrq(String gwbh, String RZBH,String tjrq){
		if("0000".equals(gwbh.substring(2, 6))){
			return true;
		}
		StringBuffer sql = new StringBuffer("SELECT to_char(tjrq,'yyyy-mm-dd') FROM T_OA_DAYREPORT WHERE rzbh='"+RZBH  +"'");
		Object res = null;
		try {
			res = DBHandler.getSingleResult(sql.toString());
		} catch (Exception e) {		
			e.printStackTrace();
		}
		if(res != null){
			return checkOverTime(StringHelper.obj2str(res, ""),gwbh);
		}
		return false;
		
		/*
		boolean flag = false;
		Object res = null;
		StringBuffer sql = new StringBuffer("SELECT RZBH FROM T_OA_DAYREPORT WHERE TBDW ='" + gwbh + "'");
		sql.append("AND to_char((TJRQ+1),'yyyy-mm-dd')||' '|| lpad('"
			+ getHourPrmt() + "',2,'0') > to_char(SYSDATE,'yyyy-mm-dd HH24') and rzbh = '" + RZBH +"'");
		System.out.println(sql);
		try {
			res = DBHandler.getSingleResult(sql.toString());
		} catch (Exception e) {		
			e.printStackTrace();
		}
		if(res != null){
			flag = true;
		}
		return flag;
		*/
	
		
	}
	
	/*
	 * 检查是否时间越界 统计日期 格式为 yyyy-MM-dd true 过期 wujh
	 */
	public boolean checkOverTime(String tjrq,String jgid){
		if("0000".equals(jgid.substring(2, 6))||99 == getHourPrmt()){
			return true;
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-ddHHmmss");
		
		try{
			Date dt_tjrq = null;
			if(tjrq == null || tjrq.equals("")){
				return true;
			}else{
				dt_tjrq = sf.parse(tjrq + "000000");
			}
			
			Calendar ca=Calendar.getInstance();
			Date dt_now = new Date(System.currentTimeMillis());
			
			ca.setTime(dt_tjrq) ;     //设置时间
			
			ca.add(Calendar.HOUR,24+getHourPrmt()); //计算过期时间
			Date dt_overtime = ca.getTime(); //得到过期时间
			ca.clear();
			if (dt_now.before(dt_overtime)) {
				return true;
			}
		} catch(Exception ee) {
			ee.printStackTrace();
		}
		return false;
	}
	/*********************************导出Excel后台类 begin******************************************/
	/**
	 * @版本号：1.0
	 * @函数说明：导出Excel后台入口类
	 * @参数：fileName 转存后的文件名 title 文件标题 tabHeader 文件表头 dataSql 获取转存内容的SQL
	 * @返回：
	 * @作者：Jason
	 * @创建日期：2010-01-18 
	 * */
//	public ActionForward doOutExcel(Action action,HttpServletRequest request,HttpServletResponse response) throws Throwable {
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		
//		//接受数据
//		String fileName = ""; //文件名称
//		String titleName = ""; //标题		
//		String tabHeader = "上报时间,信息报送单位,月报送数量,当年累计报送,月采用数量,当年累计采用,信息快报,要事快报,决策参考,其它"; //表头
//		String startTime = StringHelper.obj2str(request.getParameter("startTime"),""); //开始时间
//		String endTime = StringHelper.obj2str(request.getParameter("endTime"),""); //结束时间
//		String timeType = StringHelper.obj2str(request.getParameter("timeType"),""); //日期形式 1：年 2:月
//		String depart = StringHelper.obj2str(request.getParameter("depart"),""); //分局
//		String publicationType = StringHelper.obj2str(request.getParameter("publicationType"),""); //刊物类别
//		String departmentType = StringHelper.obj2str(request.getParameter("departmentType"),""); //分局区分
//		if("".equals(startTime) || "".equals(endTime) || "".equals(timeType)){
//			logger.error("[要情统计]" + "startTime || endTime || timeType错误");
//			return null;
//		}
//
//		String sql = "";
//		if("1".equals(timeType)){
//			fileName ="要情在"+startTime+"年至"+endTime+"年的统计";
//			titleName ="要情统计结果（日期："+startTime+"年至"+endTime+"年）";
//			sql = "select  a.mon, a.unit, titol, titol1, TYPE1,TYPE2,TYPE3,TYPE9 from ";
//			sql += " (SELECT to_char(REPORT_TIME, 'yyyy') mon, f_get_dept(REPORT_UNIT) unit,REPORT_UNIT, count(LOGIC_ID) titol FROM T_ICRS_CASE WHERE ((DEALWITH_STATE != 3 and DEALWITH_STATE != 5) or DEALWITH_STATE is null) and REPORT_STATE = '1'  group BY REPORT_UNIT, to_char(REPORT_TIME, 'yyyy'))a, ";
//			sql += " (SELECT to_char(REPORT_TIME, 'yyyy') mon, f_get_dept(REPORT_UNIT) unit, count(LOGIC_ID) titol1 FROM T_ICRS_CASE WHERE dealwith_state='1' and REPORT_STATE = '1' group BY REPORT_UNIT, to_char(REPORT_TIME, 'yyyy'))b, ";
//			sql += " (SELECT to_char(REPORT_TIME, 'yyyy') mon, f_get_dept(REPORT_UNIT) unit, count(LOGIC_ID) TYPE1 FROM T_ICRS_CASE WHERE dealwith_state='1' and REPORT_STATE = '1' and ADOPT_PUBLICATION_TYPE='01' group BY REPORT_UNIT, to_char(REPORT_TIME, 'yyyy')) k , ";
//			sql += " (SELECT to_char(REPORT_TIME, 'yyyy') mon, f_get_dept(REPORT_UNIT) unit, count(LOGIC_ID) TYPE2 FROM T_ICRS_CASE WHERE dealwith_state='1' and REPORT_STATE = '1' and ADOPT_PUBLICATION_TYPE='02' group BY REPORT_UNIT, to_char(REPORT_TIME, 'yyyy')) l, ";
//			sql += " (SELECT to_char(REPORT_TIME, 'yyyy') mon, f_get_dept(REPORT_UNIT) unit, count(LOGIC_ID) TYPE3 FROM T_ICRS_CASE WHERE dealwith_state='1' and REPORT_STATE = '1' and ADOPT_PUBLICATION_TYPE='03' group BY REPORT_UNIT, to_char(REPORT_TIME, 'yyyy')) m, ";
//			sql += " (SELECT to_char(REPORT_TIME, 'yyyy') mon, f_get_dept(REPORT_UNIT) unit, count(LOGIC_ID) TYPE9 FROM T_ICRS_CASE WHERE dealwith_state='1' and REPORT_STATE = '1' and ADOPT_PUBLICATION_TYPE='09' group BY REPORT_UNIT, to_char(REPORT_TIME, 'yyyy')) n ";
//			sql += " where a.mon=b.mon(+) and a.mon=k.mon(+) and a.mon=l.mon(+) and a.mon=m.mon(+) and a.mon=n.mon(+) ";
//			sql += " and a.unit=b.unit(+) and a.unit=k.unit(+) and a.unit=l.unit(+) and a.unit=m.unit(+) and a.unit=n.unit(+) ";
//			sql += " and substr(report_unit,1,7)= "+departmentType+" ";
//			sql += " and a.mon >= '"+startTime+"' ";
//			sql += " and a.mon <= '"+endTime+"' ";
//		}else if("2".equals(timeType)){
//			fileName ="要情在"+startTime+"月至"+endTime+"月的统计";
//			titleName ="要情统计结果（日期："+startTime+"月至"+endTime+"月）";
//			sql = "select  a.mon, a.unit, titol, titol1, TYPE1,TYPE2,TYPE3,TYPE9 from ";
//			sql += " (SELECT to_char(REPORT_TIME, 'yyyy-mm') mon, f_get_dept(REPORT_UNIT) unit,REPORT_UNIT, count(LOGIC_ID) titol FROM T_ICRS_CASE WHERE ((DEALWITH_STATE != 3 and DEALWITH_STATE != 5) or DEALWITH_STATE is null) and REPORT_STATE = '1' group BY REPORT_UNIT, to_char(REPORT_TIME, 'yyyy-mm'))a, ";
//			sql += " (SELECT to_char(REPORT_TIME, 'yyyy-mm') mon, f_get_dept(REPORT_UNIT) unit, count(LOGIC_ID) titol1 FROM T_ICRS_CASE WHERE dealwith_state='1' and REPORT_STATE = '1' and dealwith_state='1' group BY REPORT_UNIT, to_char(REPORT_TIME, 'yyyy-mm'))b, ";
//			sql += " (SELECT to_char(REPORT_TIME, 'yyyy-mm') mon, f_get_dept(REPORT_UNIT) unit, count(LOGIC_ID) TYPE1 FROM T_ICRS_CASE WHERE dealwith_state='1' and REPORT_STATE = '1' and ADOPT_PUBLICATION_TYPE='01' group BY REPORT_UNIT, to_char(REPORT_TIME, 'yyyy-mm')) k , ";
//			sql += " (SELECT to_char(REPORT_TIME, 'yyyy-mm') mon, f_get_dept(REPORT_UNIT) unit, count(LOGIC_ID) TYPE2 FROM T_ICRS_CASE WHERE dealwith_state='1' and REPORT_STATE = '1' and ADOPT_PUBLICATION_TYPE='02' group BY REPORT_UNIT, to_char(REPORT_TIME, 'yyyy-mm')) l, ";
//			sql += " (SELECT to_char(REPORT_TIME, 'yyyy-mm') mon, f_get_dept(REPORT_UNIT) unit, count(LOGIC_ID) TYPE3 FROM T_ICRS_CASE WHERE dealwith_state='1' and REPORT_STATE = '1' and ADOPT_PUBLICATION_TYPE='03' group BY REPORT_UNIT, to_char(REPORT_TIME, 'yyyy-mm')) m, ";
//			sql += " (SELECT to_char(REPORT_TIME, 'yyyy-mm') mon, f_get_dept(REPORT_UNIT) unit, count(LOGIC_ID) TYPE9 FROM T_ICRS_CASE WHERE dealwith_state='1' and REPORT_STATE = '1' and ADOPT_PUBLICATION_TYPE='09' group BY REPORT_UNIT, to_char(REPORT_TIME, 'yyyy-mm')) n ";
//			sql += " where a.mon=b.mon(+) and a.mon=k.mon(+) and a.mon=l.mon(+) and a.mon=m.mon(+) and a.mon=n.mon(+) ";
//			sql += " and a.unit=b.unit(+) and a.unit=k.unit(+) and a.unit=l.unit(+) and a.unit=m.unit(+) and a.unit=n.unit(+) ";
//			sql += " and substr(report_unit,1,7)= "+departmentType+" ";
//			sql += " and a.mon >= '"+startTime+"' ";
//			sql += " and a.mon <= '"+endTime+"' ";
//		}else{
//			logger.error("[要情统计]" + "timeType错误");
//			return null;
//		}
//		if(depart !=null && depart.length()>0){
//			sql += " and a.REPORT_UNIT = '"+depart+"' ";
//		}
//		sql += " order by a.unit,a.mon ";
//		System.out.println(sql);
//		if (tabHeader == null || sql == null)
//		{
//			return null;
//		}
//		String sheetName = startTime+"至"+endTime;
//		//数据写入Excel
//		try{
//			if(!wirteToExcel(response,tabHeader,fileName,sheetName,sql,titleName,publicationType)){
//				return null;
//			}
//		}
//	    catch (Exception e) {
//			Console.infoprintln("doFieldsToExcel fail:" + e.getMessage());
//			logger.error("[要情统计]" + "转存Excel：生成列表框的HTML代码出错误");
//			return null;
//        }
//		
//		return null;
//	}
//
//	/**
//	 * @版本号：1.0
//	 * @函数说明：写Excel文件
//	 * @参数：是否执行成功.true：成功；false:失败.
//	 * @返回：是否加载成功,true-成功;false-失败.
//	 * @作者：吴玉良
//	 * @创建日期：2009-12-8 
//	 * */
//	public boolean wirteToExcel(HttpServletResponse response,String tabHeader,String fileName,String sheetName,String tabData,String titleName, String publicationType) throws Exception {
//		boolean bResult = false; //执行结果
//				
//		List lstTabHeader = StringUtil.divide(tabHeader, ","); //表头转换为List
//		short columnCount = (short) lstTabHeader.size(); //表头列数
//		int titol=0;
//		int titol1=0;
//	    
//		if (lstTabHeader == null || columnCount == 0 ) //没有表头
//		{	
//			return false;
//		}
//		fileName = new String(fileName.getBytes(),"ISO8859_1");
//		try {
//			response.reset();
//	  	    response.setContentType("application/vnd.ms-excel; charset=utf-8");
//	  	    response.setHeader("content-disposition","filename=" + fileName +".xls");
//	  	    OutputStream out = new BufferedOutputStream(response.getOutputStream());
//			
//	  	    Object[][] oResult = DBHandler.getMultiResult(tabData);
//	  	    if (oResult == null || oResult.length == 0 || oResult[0].length==0 ) //没统计结果
//			{	
//				return false;
//			}
//	  	    Object[][] oResultAdd = new Object[oResult.length][oResult[0].length+2];
//	  	    for(int i=0,j=0,k=0;i<oResult.length;i++){
//	  	    	for (j=0,k=0;j<oResult[0].length;j++,k++){
//	  	    		if(oResult[i][j]==null || "".equals(oResult[i][j])){
//	  	    			oResult[i][j]="0";
//	  	    		}
//	  	    		if(j<2){
//	  	    			oResultAdd[i][k]=oResult[i][j];
//	  	    		}else if(j==2){
//	  	    			oResultAdd[i][k]=oResult[i][j];
//	  	    			k++;
//	  	    			if(i>0 && oResult[i][1].equals(oResult[i-1][1])){
//	  	    				titol += Integer.parseInt(String.valueOf(oResult[i][j]));
//	  	    			}else{
//	  	    				titol = Integer.parseInt(String.valueOf(oResult[i][j]));
//	  	    			}
//	  	    			oResultAdd[i][k]= new Integer(titol);
//	  	    		}else if(j==3){
//	  	    			oResultAdd[i][k]=oResult[i][j];
//	  	    			k++;
//	  	    			if(i>0 && oResult[i][1].equals(oResult[i-1][1])){
//	  	    				titol1 += Integer.parseInt(String.valueOf(oResult[i][j]));
//	  	    			}else{
//	  	    				titol1 = Integer.parseInt(String.valueOf(oResult[i][j]));
//	  	    			}
//	  	    			oResultAdd[i][k]=new Integer(titol1);
//	  	    		}else{
//
//	  					if(!"".equals(publicationType)){
//	  						if("01".equals(publicationType)){
//	  							oResult[i][4]="0";
//	  							oResult[i][6]="0";
//	  							oResult[i][7]="0";
//	  						}else if("02".equals(publicationType)){
//	  							oResult[i][5]="0";
//	  							oResult[i][6]="0";
//	  							oResult[i][7]="0";
//	  						}else if("03".equals(publicationType)){
//	  							oResult[i][4]="0";
//	  							oResult[i][5]="0";
//	  							oResult[i][7]="0";
//	  						}else if("09".equals(publicationType)){
//	  							oResult[i][4]="0";
//	  							oResult[i][5]="0";
//	  							oResult[i][6]="0";
//	  						}
//	  					}
//	  	    			oResultAdd[i][k]=oResult[i][j];
//	  	    		}
//	  	    	}
//	  	    }
//	  	    
//	  	    bResult=executeToExcel(oResultAdd,out,sheetName,titleName,columnCount, lstTabHeader);
//	  	    out.close();
//	  	    	  	    
//		}
//	    catch (Exception e) {
//	    	Console.infoprintln("wirteToExcel Fail:"+e.getMessage());
//	    	return false;
//        }
//	    
//	    return  bResult;
//
//	}
//
//	/**
//	 * @版本号：1.0
//	 * @函数说明：数据转换执行函数
//	 * @参数：oResult 结果集. out OutputStream. sheetName Sheet名 titleName 标题名 lstTabHeader 数值列表表头名称。
//	 * @返回：是否加载成功,true-成功;false-失败.
//	 * @作者：吴玉良
//	 * @创建日期：2009-12-8 
//	 * */
//	private boolean executeToExcel(Object[][] oResult,OutputStream out,String sheetName,String titleName,short columnCount,List lstTabHeader) throws Exception {
//		final short ColumnWidth=4000; //列宽
//		
//		try {
//
//			//创建HSSFWorkBook对象
//		    HSSFWorkbook hwb = new HSSFWorkbook();
//		    
//		    //创建HSSFSheet对象
//		    HSSFSheet sheet = hwb.createSheet();
//		    hwb.setSheetName(0, sheetName, HSSFWorkbook.ENCODING_UTF_16);
//		    
//		    //将日期和页数写入页脚
//		    HSSFFooter footer = sheet.getFooter();
//		    footer.setCenter("Page " + HSSFFooter.page() + " of " + HSSFFooter.numPages());
//		    footer.setRight(HSSFFooter.font("Stencil-Normal", "Italic") + HSSFFooter.fontSize( (short) 10) + HSSFFooter.date());
//		    
//		    //创建标题字体
//		    HSSFFont headerFont = hwb.createFont();
//		    headerFont.setFontHeightInPoints( (short) 16);
//		    headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		    
//		    //标题样式
//		    HSSFCellStyle headerStyle = hwb.createCellStyle();
//		    headerStyle.setFont(headerFont);
//		    headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//
//			//用工作表第一行做标题
//			HSSFRow row = sheet.createRow(0);
//			row.setHeight( (short) 500);
//			HSSFCell cel = row.createCell( (short) 0);  
//			cel.setEncoding(HSSFCell.ENCODING_UTF_16);
//			cel.setCellStyle(headerStyle);
//			cel.setCellValue(titleName);
//			
//			//定义字体
//		    HSSFFont columnNameFont = hwb.createFont();
//		    columnNameFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
//		    
//		    //列名单元格样式
//		    HSSFCellStyle style = hwb.createCellStyle();
//		    style.setFont(columnNameFont);
//		    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		    style.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		    
//		    //工作表第二行作列名
//		    row = sheet.createRow(1);
//		    
//		    //将列名写入第二行的单元格中,合并标题单元格	    			    
//		    sheet.addMergedRegion(new Region(0,(short)0,0,(short)(columnCount-1)));
//		    String strColumnName = new String("");
//		    for (short i = 0; i < columnCount; i++) {
//		      strColumnName =(String) lstTabHeader.get(i);
//		      if (strColumnName.equals("1")){
//			      sheet.setColumnWidth( (short) i,(short)0); //设置列宽		    	  
//		      }else{
//			      sheet.setColumnWidth( (short) i, (short)ColumnWidth); //设置列宽		    	  		    	  
//		      }
//		      cel = row.createCell(i);
//		      cel.setCellStyle(style);
//		      cel.setEncoding(HSSFCell.ENCODING_UTF_16);
//		      cel.setCellValue(strColumnName);
//		    }
//		    	    
//		    //数据列单元格样式
//		    HSSFCellStyle dbStyle = hwb.createCellStyle();
//		    dbStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		    dbStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		    dbStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		    dbStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		    
//		    //数据类型为字符的样式
//		    HSSFCellStyle strStyle = hwb.createCellStyle();
//		    strStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
//		    strStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//		    strStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		    strStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		    strStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		    strStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		    		    
//		    //向sheet中写入数据列
//			if (oResult != null)
//			{
//			    for (int i = 0; i < oResult.length; i++) {
//					row = sheet.createRow(i+2);
//					for (short j = 0; j < oResult[i].length; j++) {
//						cel = row.createCell(j);
//						//按照字符处理
//						cel.setEncoding(HSSFCell.ENCODING_UTF_16); //支持中文
//						cel.setCellStyle(dbStyle);//设置单元格样式
//						cel.setCellValue(StringHelper.obj2str(oResult[i][j],"")); //设置单元格值	
//					}
//				}
//			}
//		    hwb.write(out);
//	    }
//	    catch (Exception e) {
//	    	Console.infoprintln("executeToExcel Fail:"+e.getMessage());
//	    	return false;
//        }
//	    return true;
//	}
	/***********************************导出Excel后台类 end****************************************/
}
