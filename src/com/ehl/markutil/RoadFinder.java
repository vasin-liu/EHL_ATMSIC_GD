package com.ehl.markutil;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

public class RoadFinder {
	static Logger  logger = Logger.getLogger(RoadFinder.class);
	 /**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵������ȡ����������һ����·��Ϣ������
	 * @������roadid-��·��ţ�roadname-��·���ƣ�startkm-��ʼǧ�ף�startbm-��ʼ���ף�endkm-����ǧ��, endbm-��������
	 * @�������ڣ�2011-1-20
	 * @���أ���·�����ַ��� ��֮�䡰;�� ��γ��֮�䡰,��
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
				//ȥ��·���С����١�
				roadname = roadname.substring(0, roadname.indexOf("����")) +roadname.substring(roadname.indexOf("����")+2);
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
			logger.error("���ݵ�·��ŵ���Ϣ��ȡ��·λ�ñ���"+ex.getMessage());
		}
		
		return roadBuffer.toString();
	}
}
