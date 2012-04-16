package com.ehl.dispatch.cdispatch.util;
 
import java.net.InetAddress;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.appframe.Console;
import com.appframe.data.sql.DBHandler;
import com.ehl.sm.common.Constants;
import com.ehl.webgis.data.SDEAccess;
import com.ehl.webgis.util.PointUtil;
import com.appframe.utils.StringHelper;

/**
 * �� Ȩ�������׻�¼��Ϣ�����ɷ����޹�˾ 2009
 * �ļ����ƣ�DispatchUtil.java
 * ժ Ҫ��ʵ�÷����ࡣ
 * ��ǰ�汾��1.0
 * �� �ߣ�LChQ  2009-4-14
 * �޸��ˣ�
 * �޸����ڣ�
 */
public class DispatchUtil{
	 
	/**
	 * ��ȡ�����û�����Ϣ���û����������ڻ����ȣ�
	 */
	public static Hashtable  getCurrentUserData(HttpServletRequest request){	
		HttpSession session = request.getSession();
		//session.setMaxInactiveInterval(-1);
		String userCode = StringHelper.obj2str(session.getAttribute(Constants.PCODE_KEY),"");
		if("".equals(userCode)){
			return null;
		}
		
		StringBuffer selectSQL = new StringBuffer("SELECT t1.jgid,t1.jgmc,t2.username, t3.xm,t3.ryid,t3.bgdh,t3.sj ");
		selectSQL.append(" FROM t_sys_department t1, ");
		selectSQL.append(" (SELECT tu.deptcode ,tu.personcode,tu.username FROM t_sys_user tu " );
		selectSQL.append(" WHERE tu.usercode='" + userCode + "') t2 , t_sys_person t3 ");
		selectSQL.append(" WHERE t2.deptcode=t1.jgid AND t3.ryid(+)=t2.personcode ");
		
		Object [][] userData = DBHandler.getMultiResult( selectSQL.toString() );
		//����һ��Properties �����������ֵ
		Hashtable<String, String> userInfo = null;
		if( null != userData ){
			userInfo = new Hashtable<String, String>();
//			InetAddress addr = InetAddress.getLocalHost();
//			String ip=addr.getHostAddress().toString();//��ñ���IP

			//�õ�����		 
			userInfo.put("BRANCHID", StringHelper.obj2str(userData[0][0],""));			
			userInfo.put("BRANCHNAME", StringHelper.obj2str(userData[0][1],""));
			userInfo.put("USERNAME", StringHelper.obj2str(userData[0][2],""));
			userInfo.put("NAME", StringHelper.obj2str(userData[0][3],"")); 
			userInfo.put("PERSONID", StringHelper.obj2str(userData[0][4],"")); 
			userInfo.put("PHONE", StringHelper.obj2str(userData[0][5],"")); 
			userInfo.put("MOBILEPHONE", StringHelper.obj2str(userData[0][6],"")); 
//			userInfo.put("SERVERIP",ip ); 
		}
		return userInfo;
	}
	
	/**
	 * @�汾�ţ�1.0
	 * @throws Throwable 
	 * @����˵�����õ�ǧ��׮��λ��Ϣ
	 * @����˵����DLBH ��·���ţ�QMZBH ǧ��׮��ţ�BMZBH ����׮���
	 * @�������ڣ�2009-05-24
	 */
	public String getQmzPoint(String DLBH,String QMZBH,String BMZBH){
		String foundPoint = "";
		SDEAccess sde = new SDEAccess(); // ����SDE
		Object[][] oResult = null;
		String layerNameForPoint = "LCB_PT"; // ����ͼ������
		String strWhere = "DLBH='" + DLBH + "' AND QMZBH='" + QMZBH + "KM' AND BMZBH='"+ BMZBH + "M'";//��ѯ����
//		strWhere = "DLMC='" + GLDH + "'";
		try {
			PointUtil pUtil = new PointUtil(sde.initConnection(), layerNameForPoint);
			oResult = pUtil.getFieldsByCondition(strWhere, "SHAPE");
			//foundPoint = pUtil.getGeometriesByCondition(strWhere);
		} catch (Exception ex) {
			Console.infoprintln("readDutyPoint fail:" + ex.getMessage());
		} finally {
			sde.closeAO();
		}
		if (oResult != null && oResult.length > 0) {
			foundPoint = StringHelper.obj2str(oResult[0][0], "");
		}
		return foundPoint;
	}
}
