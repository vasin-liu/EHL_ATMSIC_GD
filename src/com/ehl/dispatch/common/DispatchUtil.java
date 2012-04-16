package com.ehl.dispatch.common;
 
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.appframe.Console;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.duty.dao.DutyDao;
import com.ehl.sm.common.Constants;
import com.ehl.webgis.data.SDEAccess;
import com.ehl.webgis.util.PointUtil;

/**
 * 版 权：北京易华录信息技术股份有限公司 2009
 * 文件名称：DispatchUtil.java
 * 摘 要：实用方法类。
 * 当前版本：1.0
 * 作 者：LChQ  2009-4-14
 * 修改人：
 * 修改日期：
 */
public class DispatchUtil{
	 
	/**
	 * 获取请求用户的信息（用户姓名，所在机构等）
	 */
	public static Hashtable  getCurrentUserData(HttpServletRequest request){	
		HttpSession session = request.getSession();
//		session.setMaxInactiveInterval(-1);
		String userCode = StringHelper.obj2str(session.getAttribute(Constants.PCODE_KEY),"");
		if("".equals(userCode)){
			return null;
		}
		//Modified by Liuwx 2011-8-25
		//查询新增一列，用户代码
		StringBuffer selectSQL = new StringBuffer("SELECT t1.jgid,t1.jgmc,t2.username, t3.xm,t3.ryid,t3.bgdh,t3.sj,t1.jglx,t1.jgccbm,t1.ZBDH1,t2.usercode ");
		selectSQL.append(" FROM t_sys_department t1, ");
		selectSQL.append(" (SELECT tu.deptcode ,tu.personcode,tu.username,tu.usercode FROM t_sys_user tu " );
		selectSQL.append(" WHERE tu.usercode='" + userCode + "') t2 , t_sys_person t3 ");
		selectSQL.append(" WHERE t2.deptcode=t1.jgid AND t3.ryid(+)=t2.personcode ");
		//Modification finished
		Object [][] userData = DBHandler.getMultiResult( selectSQL.toString() );
		System.out.println("------> " + selectSQL.toString());
		//定义一个Properties 用来存放属性值
		Hashtable<String, String> userInfo = null;
		if( null != userData ){
			userInfo = new Hashtable<String, String>();
//			InetAddress addr = InetAddress.getLocalHost();
//			String ip=addr.getHostAddress().toString();//获得本机IP

			//得到数据		 
			userInfo.put("BRANCHID", StringHelper.obj2str(userData[0][0],""));			
			userInfo.put("BRANCHNAME", StringHelper.obj2str(userData[0][1],""));
			userInfo.put("USERNAME", StringHelper.obj2str(userData[0][2],""));
			userInfo.put("NAME", StringHelper.obj2str(userData[0][3],"")); 
			userInfo.put("PERSONID", StringHelper.obj2str(userData[0][4],"")); 
			userInfo.put("PHONE", StringHelper.obj2str(userData[0][5],"")); 
			userInfo.put("MOBILEPHONE", StringHelper.obj2str(userData[0][6],"")); 
			userInfo.put("DEPTTYPE", StringHelper.obj2str(userData[0][7],""));
			userInfo.put("DEPTCCBM", StringHelper.obj2str(userData[0][8],""));
			userInfo.put("ZBDH", StringHelper.obj2str(userData[0][9],""));
			//Modified by Liuwx 2011-8-25
			//新增用户代码
			userInfo.put("USERCODE", StringHelper.obj2str(userData[0][10],""));
			//Modification finished
//			userInfo.put("SERVERIP",ip ); 
		}
		return userInfo;
	}
	
	/**
	 * @版本号：1.0
	 * @throws Throwable 
	 * @函数说明：得到千米桩定位信息
	 * @参数说明：DLBH 公路代号；QMZBH 千米桩编号；BMZBH 百米桩编号
	 * @创建日期：2009-05-24
	 */
	public String getQmzPoint(String DLBH,String QMZBH,String BMZBH){
		String foundPoint = "";
		SDEAccess sde = new SDEAccess(); // 定义SDE
		Object[][] oResult = null;
		String layerNameForPoint = "LCB_PT"; // 定义图层名称
		String strWhere = "DLBH='" + DLBH + "' AND QMZBH='" + QMZBH + "KM' AND BMZBH='"+ BMZBH + "M'";//查询条件
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
	
	/**
	 * @版本号：1.0
	 * @throws Throwable 
	 * @函数说明：根据人员编号字符串得到人员姓名字符串
	 * @参数说明：
	 * @创建日期：
	 */
	public String getPersonName(String idStr,String divideStr){
		String personStr="";
		String[] personIds=idStr.split(divideStr);
		if(personIds!=null&&personIds.length>0){
			for(int i=0;i<personIds.length;i++){
				String sql="SELECT XM FROM T_SYS_PERSON WHERE RYID='"+personIds[i]+"'";
				personStr+=StringHelper.obj2str(DBHandler.getSingleResult(sql),"")+divideStr;
			}
		}		
		return personStr;
	}

	/**
	 * 根据部门id取得当前值班人员名称<br/>
	 * 根据部门id取得当前值班人员名称
	 * @param depId
	 * @return
	 */
	public static String getDutyPersonNameByDepId(String depId){
		String personName = "";
		DutyDao dutyDao = new DutyDao();
		if(depId !=null && depId.length()>0){
				String sql = "SELECT person FROM T_OA_DUTY WHERE deptid='" + depId + 
				"' and to_date(dtime,'yyyy-mm-dd hh24:mi:ss') between ";
				sql += " to_date('" + dutyDao.getDutyTime(dutyDao.isNextDay(),"begin") + "', 'yyyy-mm-dd hh24:mi:ss') ";
				sql += " and to_date('" + dutyDao.getDutyTime(dutyDao.isNextDay(),"end") + "', 'yyyy-mm-dd hh24:mi:ss') ";
				sql += " order by dtime desc";
				personName =  StringHelper.obj2str(DBHandler.getSingleResult(sql));
		}		
		return personName;
	}
}

