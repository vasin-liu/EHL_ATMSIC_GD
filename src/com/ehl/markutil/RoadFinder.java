package com.ehl.markutil;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

public class RoadFinder {
	static Logger  logger = Logger.getLogger(RoadFinder.class);
	 /**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：获取给定条件的一条道路信息的坐标
	 * @参数：roadid-道路编号，roadname-道路名称，startkm-开始千米，startbm-开始百米，endkm-结束千米, endbm-结束百米
	 * @创建日期：2011-1-20
	 * @返回：道路坐标字符串 点之间“;” 经纬度之间“,”
	 */
	public static String getRoadPointStr(String roadid,String roadname,String startkm,String startbm,String endkm,String endbm){
		StringBuffer roadBuffer = new StringBuffer();
		Object[][] res = null;
		StringBuffer sqlWhereBuffer = new StringBuffer();
		try{
			if(roadid != null && roadid.length() != 0){
				sqlWhereBuffer.append(" AND DLBH='"+roadid+"'");
			}
			if(roadname != null && roadname.length() != 0){
				//去除路名中”高速”
				roadname = roadname.substring(0, roadname.indexOf("高速")) +roadname.substring(roadname.indexOf("高速")+2);
				sqlWhereBuffer.append(" AND DLMC LIKE '%"+roadname+"%'");
			}
			if(startkm != null  ){
				sqlWhereBuffer.append(" AND QMZ*1000+BMZ*100 >='"+(Integer.parseInt(startkm)*1000+Integer.parseInt(startbm)*100)+"'");
			}
			if(endkm != null  ){
				sqlWhereBuffer.append(" AND QMZ*1000+BMZ*100 <='"+(Integer.parseInt(endkm)*1000+Integer.parseInt(endbm)*100)+"'");
			}
			String sql = "SELECT ZB FROM LCB_PT_MIS WHERE 1=1 "+sqlWhereBuffer.toString();
			res = DBHandler.getMultiResult(sql);
			if(res != null){
				for(int i = 0; i < res.length; i ++){
					if(roadBuffer.length() == 0){
						roadBuffer.append(res[i][0]);
					}else{
						roadBuffer.append(";");
						roadBuffer.append(res[i][0]);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("根据道路编号等信息获取道路位置报错："+ex.getMessage());
		}
		
		return roadBuffer.toString();
	}
}
