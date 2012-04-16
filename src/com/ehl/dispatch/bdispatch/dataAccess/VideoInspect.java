package com.ehl.dispatch.bdispatch.dataAccess;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

/**
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * 
 * �ļ����ƣ�VideoInspect.java
 * 
 * ժ Ҫ����Ƶ������ݿ�����ࡣ
 * 			
 *  
 * ��ǰ�汾��1.0
 * 
 * �� �ߣ�LChQ  2009-4-14
 * 
 * �޸��ˣ�
 * 
 * �޸����ڣ�
 * 
 */
public class VideoInspect
{
	private String CCTV = "CCTV";	//�豸��������
	private String UTC = "UTC";	//�豸��������
	private static final Logger logger = Logger.getLogger(VideoInspect.class);
	/**�����豸��ʶ��ȡ��Ƶ�豸ժҪ��Ϣ
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
		selectSQL.append(" ( SELECT  id,value FROM  atms_equipment_dict  WHERE type='ʹ��״̬' ) t3 " );
		 
		selectSQL.append("	WHERE  t1.syzt=t3.id(+) AND t2.jgid(+)=t1.ssjg   " );
		selectSQL.append(" AND t1.sid='" + videoId + "'");
		
		com.appframe.Console.infoprintln("getVideo  SQL -------->"  + selectSQL.toString());
		result = DBHandler.getMultiResult(selectSQL.toString());
		}catch(Exception e) {
			logger.error("��ȡ��Ƶ�豸ժҪ��Ϣ���ݿ����!");
		}
		return result;
	}
	/**�����豸��ʶ��ȡ��Ƶ�豸ժҪ��Ϣ
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
			logger.error("��ȡ��Ƶ�豸ժҪ��Ϣ���ݿ����!");
		}
		return result;
	}
	/**�����豸��ʶ��ȡ��Ƶ�豸ժҪ��Ϣ
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
			logger.error("��ȡ��Ƶ�豸ժҪ��Ϣ���ݿ����!");
		}
		return result;
	}
	
	//getVideoDataList
}
