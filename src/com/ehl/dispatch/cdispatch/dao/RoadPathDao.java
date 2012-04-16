package com.ehl.dispatch.cdispatch.dao;

import java.util.List;

import org.apache.log4j.Logger;

import com.appframe.Console;
import com.appframe.data.DBFactory;
import com.appframe.data.db.Database;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.util.StringUtil;


public class RoadPathDao {
	Logger logger = Logger.getLogger(AccDao.class);
	
	/**
	 * @����: dxn
	 * @�汾�ţ�1.0
	 * @����˵������·����༭������
	 * @������sqlList
	 * @���أ�false or true
	 * @�������ڣ�2009-04-09
	 * @�޸��ߣ�
	 * @�޸����ڣ�
	 */
	public boolean excuteSql(List<String> sqlList){
//		��ʼ����
		boolean bOk = true;
		if(sqlList == null){
			return bOk;
		}
		Database db = null;
		
		try {
			db = DBFactory.newDatabase();
			db.beginTrans();
			
			String sql = "";
			for(int i=0;i<sqlList.size();i++){
				sql = StringHelper.obj2str(sqlList.get(i),"");

				if(!sql.equals("") && db.executeUpdate(sql)>=0){
					
				}else{
					bOk = false;
					break;
				}
			}
		} catch(Exception e) {
			bOk = false;
			logger.error("������ϵͳ��-->�༭��·����������" + e.getMessage());
			System.out.println(e.getMessage());
		}finally {
			if(null != db) {
				if(bOk) {
					db.commitTrans(false);
				} else {
					db.commitTrans(true);
				}
				//�ر����ݶ���
				DBFactory.closeDatabase(db);
			}
		}
		return bOk;
	}
	
	/**
	 * �����ݿ��ȡ·����Ϣ
	 */
	public String getRoadPath(String sql){
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
	
	public String getRoadPaths(String sql){
		Object[][] res = DBHandler.getMultiResult(sql);
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		for (int i = 0; i < res.length; i++) {
			xmlData.append("<RFWin>\n");
			for (int j = 0; j < res[i].length; j++) {
				xmlData.append("<col>" + StringHelper.obj2str(res[i][j],"") + "</col>");
			}
			xmlData.append("</RFWin>\n");
		}
		xmlData.append("</rfXML>\n");
		Console.infoprintln("XML---------------------->" + xmlData.toString());
		return xmlData.toString();
	}
}