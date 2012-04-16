package com.ehl.dispatch.cdispatch.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.appframe.Console;
import com.appframe.data.DBFactory;
import com.appframe.data.db.Database;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.util.StringUtil;


public class RoadDepartmentDao {
	Logger logger = Logger.getLogger(AccDao.class);
	
	/**
	 * @作者: dxn
	 * @版本号：1.0
	 * @函数说明：道路方向编辑事务处理
	 * @参数：sqlList
	 * @返回：false or true
	 * @创建日期：2009-04-09
	 * @修改者：
	 * @修改日期：
	 */
	public boolean excuteSql(List<String> sqlList){
//		开始事务
		boolean bOk = true;
		if(sqlList == null){
			return bOk;
		}
		Database db = null;
		
		try {
//			db = DBFactory.newDatabase();
//			db.beginTrans();
			
			String sql = "";
			for(int i=0;i<sqlList.size();i++){
				sql = StringHelper.obj2str(sqlList.get(i),"");
				
				System.out.println("----------------------");
				System.out.println("sql===>" + sql);
				System.out.println("----------------------");
				if(!sql.equals("") && DBHandler.execute(sql)){
					
				}else{
					bOk = false;
					break;
				}
			}
		} catch(Exception e) {
			bOk = false;
			logger.error("【警情系统】-->编辑道路辖区处理错误：" + e.getMessage());
			System.out.println(e.getMessage());
		}finally {
			if(null != db) {
				if(bOk) {
					db.commitTrans(false);
				} else {
					db.commitTrans(true);
				}
				//关闭数据对象
				DBFactory.closeDatabase(db);
			}
		}
		return bOk;
	}
	
	/**
	 * 从数据库获取路段信息
	 */
	public String getRoadDepartment(String sql){
		Object[] res = DBHandler.getLineResult(sql);
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		for (int i = 0; i < res.length; i++) {
			xmlData.append("<col>" + StringHelper.obj2str(res[i],"") + "</col>");
		}
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		Console.infoprintln("XML---------------------->" + xmlData.toString());
		return xmlData.toString();
	}
}