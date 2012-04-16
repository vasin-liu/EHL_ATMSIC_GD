package com.ehl.drpt.violationStat.dao;
/**
 * 
 * @======================================================================================================================================
 * @类型说明: 春运客运车辆严重交通违法信息管理操作Action
 * @创建者：wkz
 * @创建日期 2010-01-13
 * @======================================================================================================================================
 */
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import com.appframe.Console;
import com.appframe.data.db.Database;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.drpt.common.CommonXML;
import com.ehl.sm.common.Constants;
import com.ehl.sm.common.tree.PageCustomTree;
import com.ehl.sm.common.util.CreateSequence;
import com.ehl.sm.sysmanage.LogManage;
	
public class ViolationDao {
	private Logger logger = Logger.getLogger(ViolationDao.class);
	
	public String createSequence(String[] strObj){
	    String jgid=strObj[0];//单位编码
	    String jgmc=strObj[1];//单位名称
	    String ccbm=strObj[2];//单位层次编码
	    return "'"+jgid.substring(0, 6)+"'"+"||to_char(sysdate,'yyyyMMddHH24MISSSSS')";
	}
	
	/**
	 * @作者: wkz
	 * @版本号：1.0
	 * @函数说明：新增违法信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-13
	 */
	public String add(String xmlStr,String[] strObj){
		boolean chk;
		try {
			xmlStr = "<?xml version='1.0' encoding='UTF-8'?>" + xmlStr.replace("$", "=");
			Object[][] results = PageCustomTree.getJDomResult(xmlStr, "RFWin");
			StringBuffer sql = new StringBuffer("INSERT INTO T_OA_ILLEGALVEICLE(BH,HPHM,CSDW,WFZL,CCSJ,CCLD,JSR,JSZ,CLQK,HZRS,SZRS) VALUES(")
				.append(createSequence(strObj)+",'"+ results[0][1] + "','"+ results[0][3] + "','"+ results[0][2] 
				+ "',to_date('"+ results[0][4] + "','yyyy-MM-dd'),'"+ results[0][5] + "','"+ results[0][6] + "','"
				+ results[0][7] + "','"+ results[0][8] + "','" + results[0][9] + "','"+ results[0][10] + "')");
			
			System.out.println("春运客运车辆严重交通违法信息新增sql="+sql.toString());
	        logger.info("春运客运车辆严重交通违法信息新增sql="+sql.toString());
	        try {
	        	chk = DBHandler.execute(sql.toString());
			} catch (Exception e) {
				chk = false;
				logger.error("春运客运车辆严重交通违法信息新增sql出错:");
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		} catch (Exception ex) {
			chk = false;
			logger.error("春运客运车辆严重交通违法信息新增出错:");
			logger.error(ex.getMessage());
			ex.printStackTrace();
			return chk?"新增成功":"新增失败";
		} 
		return chk?"新增成功":"新增失败";
	}
	
	/**
	 * @作者: wkz
	 * @版本号：1.0
	 * @函数说明：修改违法信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-13
	 */
	public String update(String xmlStr){
		boolean chk;
		try {
			xmlStr = "<?xml version='1.0' encoding='UTF-8'?>" + xmlStr.replace("$", "=");
			Object[][] results = PageCustomTree.getJDomResult(xmlStr, "RFWin");
			StringBuffer sql = new StringBuffer("UPDATE T_OA_ILLEGALVEICLE SET " +
					"BH='" + results[0][0].toString().substring(0, 6) + "'||to_char(sysdate,'yyyyMMddHH24MISSSSS')," +
					"HPHM='" + results[0][1] +"',CSDW='" + results[0][3] +"',WFZL='" + results[0][2] +"',CCSJ=to_date('" + results[0][4] + "','yyyy-MM-dd')," +
					"CCLD='" + results[0][5] +"',JSR='" + results[0][6] +"',JSZ='" + results[0][7] +"',CLQK='" + results[0][8] +"'," +
					"HZRS='" + results[0][9] +"',SZRS='" + results[0][10] +"' " +
					"WHERE BH='" + results[0][0] +"'");
			System.out.println("春运客运车辆严重交通违法信息修改sql="+sql.toString());
	        logger.info("春运客运车辆严重交通违法信息修改sql="+sql.toString());
	        try {
	        	chk = DBHandler.execute(sql.toString());
			} catch (Exception e) {
				chk = false;
				logger.error("春运客运车辆严重交通违法信息修改sql出错:");
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		} catch (Exception ex) {
			chk = false;
			logger.error("春运客运车辆严重交通违法信息修改出错:");
			logger.error(ex.getMessage());
			ex.printStackTrace();
			return chk?"修改成功":"修改失败";
		} 
		return chk?"修改成功":"修改失败";
	}
	
	/**
	 * @作者: wkz
	 * @版本号：1.0
	 * @函数说明：根据参数查询违法信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-13
	 */
	public String getDateById(String bh){
		String sql = "SELECT BH,HPHM,WFZL,CSDW,to_char(CCSJ,'yyyy-MM-dd'),CCLD,JSR,JSZ,CLQK,HZRS,SZRS,JGMC FROM T_OA_ILLEGALVEICLE,T_SYS_DEPARTMENT WHERE BH='" + bh + "' AND substr(BH,0,6)=substr(JGID,0,6)";
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
	
	/**
	 * @作者: wkz
	 * @版本号：1.0
	 * @函数说明：根据参数删除违法信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-13
	 */
	@SuppressWarnings("finally")
	public boolean delete(String bh){
		boolean chk = true;
		Database db = null;
		try{
//			db = DBFactory.newDatabase();
//			db.beginTrans();
//			String sql = "DELETE FROM T_OA_ILLEGALVEICLE WHERE BH='" + bh + "'";
//			if (db.executeUpdate(sql) >= 0) {
//				db.commitTrans(false);
//			} else {
//				db.commitTrans(true);
//				chk = false;
//			}
			
			
			String sql = "DELETE FROM T_OA_ILLEGALVEICLE WHERE BH='" + bh + "'";
			
			
			DBHandler.execute(sql);
			
			
			System.out.println(chk);
		}catch (Exception e) {
			chk = false;
		} finally {
			//DBFactory.closeDatabase(db);
			return chk;
		}
	}
	
	/**
	 * @作者: wkz
	 * @版本号：1.0
	 * @函数说明：根据参数查询违法信息
	 * @参数：
	 * @返回：
	 * @创建日期：2010-01-13
	 */
	public Object[][] SearchExcel(String sql){
		Object[][] result = null;
	    logger.info("客运车导出EXCEL的sql="+sql.toString());
        try {
			result = DBHandler.getMultiResult(sql.toString());
		} catch (Exception e) {
			logger.error("客运车导出EXCEL出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args){
		 ViolationDao dao = new ViolationDao();
	}
}