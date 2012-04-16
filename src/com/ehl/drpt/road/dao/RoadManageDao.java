package com.ehl.drpt.road.dao;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.ehl.sm.common.util.CreateSequence;

/**
 * ��·����������
 * @author wangxt
 * @date 2009-1-16
 *
 */
public class RoadManageDao {
	private Logger logger = Logger.getLogger(RoadManageDao.class);
	 
	/**
	 * ������·��Ϣ
	 * @param res
	 * @return
	 */
	public boolean addRoadInfo(HashMap res){
		String sql = null;
		boolean flag = false;
		try {
			//String bh= res.get("BH").toString();
			String bh =  CreateSequence.getMaxForId("T_OA_DICT_ROAD", "BH", 12);
			String jgid = res.get("JGID").toString();
			String dlbh =  res.get("DLBH").toString();
			String dlmc = res.get("DLMC").toString();
			sql ="insert into T_OA_DICT_ROAD(BH,DLBH,DLMC,JGID)";
			sql += "values('"+bh+"','"+dlbh+"','"+dlmc+"','"+jgid+"')";
			flag = DBHandler.execute(sql);
		}catch(Exception e) {
			logger.error("������·��Ϣ����:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * �༭��·��Ϣ
	 * @param res
	 * @return
	 */
	public boolean editRoadInfo(HashMap res) {
		String sql = null;
		boolean flag = false;
		try {
			String bh= res.get("BH").toString();
			String jgid = res.get("JGID").toString();
			String dlbh =  res.get("DLBH").toString();
			String dlmc = res.get("DLMC").toString();
			sql ="update T_OA_DICT_ROAD";
			sql +=" set DLBH='"+dlbh+"',DLMC='"+dlmc+"',JGID='"+jgid+"'";
			sql +=" where BH='"+bh+"'";
			flag = DBHandler.execute(sql);
		}catch(Exception e) {
			logger.error("�༭��·��Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * ɾ����·��Ϣ
	 * @param bh
	 * @return
	 */
	public boolean delteRoadInfo(String bh) {
		String sql = null;
		boolean flag = false;
		try {
			sql ="delete from T_OA_DICT_ROAD";
			sql +=" where BH='"+bh+"'";
			flag = DBHandler.execute(sql);
		}catch(Exception e) {
			logger.error("ɾ����·��Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * ��ѯһ����·��Ϣ
	 * @param bh
	 * @return
	 */
	public Object[] getRoadInfo(String bh) {
		String sql = null;
		Object[] obj=null;
		try {
			sql ="select BH,DLBH,DLMC,JGID from T_OA_DICT_ROAD";
			sql +=" where BH='"+bh+"'";
			obj = DBHandler.getLineResult(sql);
			
		}catch(Exception e) {
			logger.error("��ѯһ����·��Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}
	
}
