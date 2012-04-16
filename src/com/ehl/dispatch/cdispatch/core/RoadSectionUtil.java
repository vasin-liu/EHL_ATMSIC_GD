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


public class RoadSectionUtil {
	Logger logger = Logger.getLogger(RoadSectionUtil.class);
	
	/**
	 * @����: wkz
	 * @�汾�ţ�1.0
	 * @����˵��������sql����
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-4-2
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public static List<String> setRoadSectionInfo(HttpServletRequest request){
		
		List<String> sqlList = new ArrayList<String>();//
		List<String> roadsectioninfo = new ArrayList<String>();//��������Ϣ
		String insertOrUpdate = StringHelper.obj2str(request.getParameter("insertOrUpdate"),"");
		String xmlStr = request.getParameter("xmlStr");
		xmlStr = "<?xml version='1.0' encoding='UTF-8'?>" + xmlStr.replace("$", "=");
		Object[][] results = PageCustomTree.getJDomResult(xmlStr, "RFWin");
		
		roadsectioninfo.add(results[0][0].toString());//·��id
		roadsectioninfo.add(results[0][1].toString());//·������
		roadsectioninfo.add(results[0][2].toString());//��·id
		String roadSectionInfoSql = modifyRoadSectionInfo(roadsectioninfo,insertOrUpdate);
		sqlList.add(roadSectionInfoSql);
		return sqlList;
	}
	
	/**
	 * @˵����������༭·����Ϣ
	 * @param roadsectioninfo
	 * @return
	 */
	public static String modifyRoadSectionInfo(List<String> roadsectioninfo,String insertOrUpdate){
		StringBuffer infoSql = new StringBuffer();
		if(insertOrUpdate.equals("0")){
			String sectionid = CreateSequence.getMaxForSeq("SEQ_T_OA_ROADSECTION", 19);
			infoSql.append("INSERT INTO T_OA_ROADSECTION(SECTIONID,SECTIONNAME,ROADID) ");
			infoSql.append("VALUES('" + sectionid + "',");
			for (int i = 1; i < roadsectioninfo.size(); i++) {
				if (i != roadsectioninfo.size() - 1) {
					infoSql.append("'" + roadsectioninfo.get(i) + "',");
				} else {
					infoSql.append("'" + roadsectioninfo.get(i) + "')");
				}
			}
		}else if(insertOrUpdate.equals("1")){
			infoSql.append("UPDATE T_OA_ROADSECTION SET SECTIONNAME='" + roadsectioninfo.get(1) + "',ROADID='" + roadsectioninfo.get(2) + "' ");
			infoSql.append("WHERE SECTIONID='" + roadsectioninfo.get(0) + "'");
		}
		return infoSql.toString();
	}
	
	/**
	 * @˵����ɾ��·����Ϣ
	 * @param roadsectioninfo
	 * @return
	 */
	public static List<String> delRoadSectionInfo(HttpServletRequest request){
		String sectionid = request.getParameter("sectionid") == null ? "" : request.getParameter("sectionid");
		List<String> sqlList = new ArrayList<String>();
		StringBuffer infoSql = new StringBuffer();
		infoSql.append("DELETE FROM T_OA_ROADSECTION WHERE SECTIONID='" + sectionid + "'");
		sqlList.add(infoSql.toString());
		return sqlList;
	}
	
	/**
	 * @˵������ȡΨһ·����Ϣ
	 * @param roadsectioninfo
	 * @return
	 */
	public static String getRoadSectionInfo(HttpServletRequest request){
		String sectionid = request.getParameter("sectionid") == null ? "" : request.getParameter("sectionid");
		String sql = "select sct.SECTIONID,sct.SECTIONNAME,sct.ROADID,road.ROADNAME from T_OA_ROADSECTION sct,T_OA_ROAD_INFO road where sct.ROADID=road.ROADID and sct.SECTIONID='" + sectionid + "'";
		
		return sql;
	}
}
