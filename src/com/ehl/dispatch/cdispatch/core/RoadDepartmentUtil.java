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


public class RoadDepartmentUtil {
	Logger logger = Logger.getLogger(RoadDepartmentUtil.class);
	
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
	public static List<String> setRoadDepartment(HttpServletRequest request){
		
		List<String> sqlList = new ArrayList<String>();//
		List<String> roadsectioninfo = new ArrayList<String>();//反馈表信息
		String insertOrUpdate = StringHelper.obj2str(request.getParameter("insertOrUpdate"),"");
		String roadNum = StringHelper.obj2str(request.getParameter("roadNum"),"");
		String xmlStr = request.getParameter("xmlStr");
		xmlStr = "<?xml version='1.0' encoding='UTF-8'?>" + xmlStr.replace("$", "=");
		Object[][] results = PageCustomTree.getJDomResult(xmlStr, "RFWin");
		
		roadsectioninfo.add(results[0][0].toString());//
		roadsectioninfo.add(results[0][1].toString());//道路id
		roadsectioninfo.add(results[0][2].toString());//辖区id
		roadsectioninfo.add(results[0][3].toString());//
		roadsectioninfo.add(results[0][4].toString());//道路id
		roadsectioninfo.add(results[0][5].toString());//辖区id
		roadsectioninfo.add(results[0][6].toString());//
		roadsectioninfo.add(results[0][7].toString());//道路id

		String roadSectionInfoSql = modifyRoadDepartment(roadsectioninfo,insertOrUpdate,roadNum);
		sqlList.add(roadSectionInfoSql);
		return sqlList;
	}
	
	/**
	 * @说明：新增或编辑路段信息
	 * @param roadsectioninfo
	 * @return
	 */
	public static String modifyRoadDepartment(List<String> roadsectioninfo,String insertOrUpdate,String roadNum){
		StringBuffer infoSql = new StringBuffer();
		if(insertOrUpdate.equals("0")){
//			String rdId = CreateSequence.getMaxForSeq("infoSql", 12);
			infoSql.append("INSERT INTO t_oa_roadmanage_code(id,gbbm,xzqh,qslc,jslc,xqlc,xqldmc,lmjl,dlmc,xzqhmc,dldj) ");
			infoSql.append("VALUES(seq_t_oa_roadmanage_code.nextval,");
			String jgid = "";
			for (int i = 1; i < roadsectioninfo.size(); i++) {
				if (i != roadsectioninfo.size() - 1) {
					infoSql.append("'" + roadsectioninfo.get(i) + "',");
				} else {
					jgid = roadsectioninfo.get(i);
					infoSql.append("'" + roadsectioninfo.get(i) + "'");
				}
			}
			infoSql.append(",");
			infoSql.append("(select dlmc from t_oa_dictdlfx where gbdm='"+roadsectioninfo.get(1)+"')");
			infoSql.append(",");
			infoSql.append("f_get_dept('");
			infoSql.append(roadsectioninfo.get(2));
			infoSql.append("'),");
			infoSql.append("(select roadlevel from t_oa_dictdlfx where gbdm = '"+roadsectioninfo.get(1)+"'))");
		}else if(insertOrUpdate.equals("1")){
			infoSql.append("UPDATE t_oa_roadmanage_code SET ")
				.append("gbbm='"+roadsectioninfo.get(1)+"',")
				.append("dlmc=(select dlmc from t_oa_dictdlfx where gbdm='"+roadsectioninfo.get(1)+"'),")
				.append("xzqh='"+roadsectioninfo.get(2)+"',")
				.append("xzqhmc=f_get_dept('"+roadsectioninfo.get(2)+"'),")
				.append("qslc='"+roadsectioninfo.get(3)+"',")
				.append("jslc='"+roadsectioninfo.get(4)+"',")
				.append("xqlc='"+roadsectioninfo.get(5)+"',")
				.append("xqldmc='"+roadsectioninfo.get(6)+"',")
				.append("dldj=(select roadlevel from t_oa_dictdlfx where gbdm = '"+roadsectioninfo.get(1)+"'),")
				.append("lmjl='"+roadsectioninfo.get(7)+"'")
				.append(" WHERE id='").append(roadsectioninfo.get(0) + "'");
		}
		return infoSql.toString();
	}
	
	/**
	 * @说明：删除路段信息
	 * @param roadsectioninfo
	 * @return
	 */
	public static List<String> delRoadDepartment(HttpServletRequest request){
		String rdid = request.getParameter("id") == null ? "" : request.getParameter("id");
		List<String> sqlList = new ArrayList<String>();
		StringBuffer infoSql = new StringBuffer();
		infoSql.append("DELETE FROM t_oa_roadmanage_code WHERE id='" + rdid + "'");
		sqlList.add(infoSql.toString());
		return sqlList;
	}
	
	/**
	 * @说明：获取唯一路段信息
	 * @param roadsectioninfo
	 * @return
	 */
	public static String getRoadDepartment(HttpServletRequest request){
		String id = request.getParameter("id") == null ? "" : request.getParameter("id");
		String sql = "select id,gbbm,rpad(xzqh,12,'0'),qslc,jslc,xqlc,xqldmc,lmjl from t_oa_roadmanage_code where id='" + id + "'";
		return sql;
	}
}
