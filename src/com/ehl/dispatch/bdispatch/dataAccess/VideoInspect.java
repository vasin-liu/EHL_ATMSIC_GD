package com.ehl.dispatch.bdispatch.dataAccess;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 
 * 文件名称：VideoInspect.java
 * 
 * 摘 要：视频监控数据库访问类。
 * 			
 *  
 * 当前版本：1.0
 * 
 * 作 者：LChQ  2009-4-14
 * 
 * 修改人：
 * 
 * 修改日期：
 * 
 */
public class VideoInspect
{
	private String CCTV = "CCTV";	//设备所属类型
	private String UTC = "UTC";	//设备所属类型
	private static final Logger logger = Logger.getLogger(VideoInspect.class);
	/**根据设备标识获取视频设备摘要信息
	 * 
	 */
	public Object [][] getVideoDataById(String videoId)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT t1.glid,t1.cpmc,t1.ip,t1.sid,t1.longitude,t1.latitude,t1.azdd,t2.jgmc,t3.value,t1.ssxt ");
		selectSQL.append("  FROM atms_equipment_zb t1, ");
		selectSQL.append("t_sys_department t2,");
		selectSQL.append(" ( SELECT  id,value FROM  atms_equipment_dict  WHERE type='使用状态' ) t3 " );
		 
		selectSQL.append("	WHERE  t1.syzt=t3.id(+) AND t2.jgid(+)=t1.ssjg   " );
		selectSQL.append(" AND t1.sid='" + videoId + "'");
		
		com.appframe.Console.infoprintln("getVideo  SQL -------->"  + selectSQL.toString());
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("获取视频设备摘要信息数据库错误!");
		}
		return result;
	}
	/**根据设备标识获取视频设备摘要信息
	 * 
	 */
	public Object [][] getVideoDataList(String countyId)
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT t.glid,t.cpmc,t.ip,t.sid,t.longitude,t.latitude ,t.ssxt  FROM  atms_equipment_zb t WHERE ");
		selectSQL.append(" ( t.ssxq='" + countyId + "' OR t.ssjg='" + countyId + "')");
		selectSQL.append(" AND t.isdeleted <>'1' AND (  t.ssxt='" + this.CCTV  + "' or t.ssxt='" + this.UTC + "' ) ");
		
		com.appframe.Console.infoprintln("getVideoList  SQL -------->"  + selectSQL.toString());
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("获取视频设备摘要信息数据库错误!");
		}
		return result;
	}
	/**根据设备标识获取视频设备摘要信息
	 * 
	 */
	public Object [][] getVideoDataList()
	{
		StringBuffer selectSQL = new StringBuffer();
		Object[][] result = null;
		try {
		selectSQL.append("SELECT t.glid,t.cpmc,t.ip,t.sid,t.longitude,t.latitude,t.ssxt,t.sid  FROM  atms_equipment_zb t WHERE ");
		selectSQL.append("  t.isdeleted <>'1' AND (  t.ssxt='" + this.CCTV  + "' OR t.ssxt='" + this.UTC + "' ) ");
		com.appframe.Console.infoprintln("getVideoList  SQL -------->"  + selectSQL.toString());
		result = DBHandler.getMultiResult(selectSQL.toString()); 
		}catch(Exception e) {
			logger.error("获取视频设备摘要信息数据库错误!");
		}
		return result;
	}
	
	//getVideoDataList
}
