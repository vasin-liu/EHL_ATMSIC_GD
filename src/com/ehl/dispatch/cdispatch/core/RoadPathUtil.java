package com.ehl.dispatch.cdispatch.core;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.tree.PageCustomTree;
import com.ehl.sm.common.util.CreateSequence;
import com.ehl.sm.common.util.StringUtil;


public class RoadPathUtil {
	Logger logger = Logger.getLogger(RoadPathUtil.class);
	
	/**
	 * @作者: dxn
	 * @版本号：1.0
	 * @函数说明：生产sql集合
	 * @参数：
	 * @返回：
	 * @创建日期：2010-4-2
	 * @修改者：
	 * @修改日期：
	 */
	public static List<String> setRoadPath(HttpServletRequest request){
		
		List<String> sqlList = new ArrayList<String>();//
		List<String> roadPath = new ArrayList<String>();//反馈表信息
		String insertOrUpdate = StringHelper.obj2str(request.getParameter("insertOrUpdate"),"");
		String xmlStr = request.getParameter("xmlStr");
		xmlStr = "<?xml version='1.0' encoding='UTF-8'?>" + xmlStr.replace("$", "=");
		Object[][] results = PageCustomTree.getJDomResult(xmlStr, "RFWin");
		
		roadPath.add(results[0][0].toString());//
		roadPath.add(results[0][1].toString());//道路辖区id
		roadPath.add(results[0][2].toString());//上下行
		roadPath.add(results[0][3].toString());//方向名称

		String roadPathSql = modifyRoadPath(roadPath,insertOrUpdate);
		
		System.out.println("------------------------");
		System.out.println("roadPathSql-->" + insertOrUpdate);
		System.out.println("------------------------");
		
		sqlList.add(roadPathSql);
		return sqlList;
	}
	
	/**
	 * @说明：新增或编辑路段信息
	 * @param roadsectioninfo
	 * @return
	 */
	public static String modifyRoadPath(List<String> roadPath,String insertOrUpdate){
		StringBuffer infoSql = new StringBuffer();
		if(insertOrUpdate.equals("0")){
			String rpId = CreateSequence.getMaxForSeq("SEQ_T_OA_ROADPATH", 12);
			infoSql.append("INSERT INTO T_OA_ROADPATH(RPID,RDID,SXXX,FXMC) ");
			infoSql.append("VALUES('" + rpId + "',");
			for (int i = 1; i < roadPath.size(); i++) {
				if (i != roadPath.size() - 1) {
					infoSql.append("'" + roadPath.get(i) + "',");
				} else {
					infoSql.append("'" + roadPath.get(i) + "')");
				}
			}
		}else if(insertOrUpdate.equals("1")){
			infoSql.append("UPDATE T_OA_ROADPATH SET RDID='")
				.append(roadPath.get(1)).append("',SXXX='")
				.append(roadPath.get(2)).append("',FXMC='")
				.append(roadPath.get(3)).append("' ")
				.append("WHERE RPID='").append(roadPath.get(0) + "'");
		}
		return infoSql.toString();
	}
	
	/**
	 * @说明：删除路段信息
	 * @param roadsectioninfo
	 * @return
	 */
	public static List<String> delRoadPath(HttpServletRequest request){
		String rpId = request.getParameter("rpId") == null ? "" : request.getParameter("rpId");
		List<String> sqlList = new ArrayList<String>();
		StringBuffer infoSql = new StringBuffer();
		infoSql.append("DELETE FROM T_OA_ROADPATH WHERE rpid='" + rpId + "'");
		sqlList.add(infoSql.toString());
		return sqlList;
	}
	
	/**
	 * @说明：获取唯一路段信息
	 * @param roadsectioninfo
	 * @return
	 */
	public static String getRoadPath(HttpServletRequest request){
		String rpId = request.getParameter("rpId") == null ? "" : request.getParameter("rpId");
		String sql = "select rp.rpid, rd.roadid, rd.rdid, rp.sxxx, rp.fxmc from T_OA_ROADPATH rp, T_OA_ROADDEPARTMENT rd where rp.rdid = rd.rdid and rp.rpid='" + rpId + "'";
		return sql;
	}

	public static String getRoadPaths(HttpServletRequest request){
		String roadId = request.getParameter("roadId") == null ? "" : request.getParameter("roadId");
		String jgId = request.getParameter("jgId") == null ? "" : request.getParameter("jgId");
		String sql = "select rp.rpid, rd.roadid, rd.rdid, rp.sxxx, rp.fxmc from T_OA_ROADPATH rp, T_OA_ROADDEPARTMENT rd where rp.rdid = rd.rdid and rd.roadId ='" + roadId
			+ "' and rd.jgId ='" + jgId + "'";
		System.out.println("getRoadPaths==>" + sql);
		return sql;
	}
}
