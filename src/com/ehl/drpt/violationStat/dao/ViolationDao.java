package com.ehl.drpt.violationStat.dao;
/**
 * 
 * @======================================================================================================================================
 * @����˵��: ���˿��˳������ؽ�ͨΥ����Ϣ�������Action
 * @�����ߣ�wkz
 * @�������� 2010-01-13
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
	    String jgid=strObj[0];//��λ����
	    String jgmc=strObj[1];//��λ����
	    String ccbm=strObj[2];//��λ��α���
	    return "'"+jgid.substring(0, 6)+"'"+"||to_char(sysdate,'yyyyMMddHH24MISSSSS')";
	}
	
	/**
	 * @����: wkz
	 * @�汾�ţ�1.0
	 * @����˵��������Υ����Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-01-13
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
			
			System.out.println("���˿��˳������ؽ�ͨΥ����Ϣ����sql="+sql.toString());
	        logger.info("���˿��˳������ؽ�ͨΥ����Ϣ����sql="+sql.toString());
	        try {
	        	chk = DBHandler.execute(sql.toString());
			} catch (Exception e) {
				chk = false;
				logger.error("���˿��˳������ؽ�ͨΥ����Ϣ����sql����:");
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		} catch (Exception ex) {
			chk = false;
			logger.error("���˿��˳������ؽ�ͨΥ����Ϣ��������:");
			logger.error(ex.getMessage());
			ex.printStackTrace();
			return chk?"�����ɹ�":"����ʧ��";
		} 
		return chk?"�����ɹ�":"����ʧ��";
	}
	
	/**
	 * @����: wkz
	 * @�汾�ţ�1.0
	 * @����˵�����޸�Υ����Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-01-13
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
			System.out.println("���˿��˳������ؽ�ͨΥ����Ϣ�޸�sql="+sql.toString());
	        logger.info("���˿��˳������ؽ�ͨΥ����Ϣ�޸�sql="+sql.toString());
	        try {
	        	chk = DBHandler.execute(sql.toString());
			} catch (Exception e) {
				chk = false;
				logger.error("���˿��˳������ؽ�ͨΥ����Ϣ�޸�sql����:");
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		} catch (Exception ex) {
			chk = false;
			logger.error("���˿��˳������ؽ�ͨΥ����Ϣ�޸ĳ���:");
			logger.error(ex.getMessage());
			ex.printStackTrace();
			return chk?"�޸ĳɹ�":"�޸�ʧ��";
		} 
		return chk?"�޸ĳɹ�":"�޸�ʧ��";
	}
	
	/**
	 * @����: wkz
	 * @�汾�ţ�1.0
	 * @����˵�������ݲ�����ѯΥ����Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-01-13
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
	 * @����: wkz
	 * @�汾�ţ�1.0
	 * @����˵�������ݲ���ɾ��Υ����Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-01-13
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
	 * @����: wkz
	 * @�汾�ţ�1.0
	 * @����˵�������ݲ�����ѯΥ����Ϣ
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-01-13
	 */
	public Object[][] SearchExcel(String sql){
		Object[][] result = null;
	    logger.info("���˳�����EXCEL��sql="+sql.toString());
        try {
			result = DBHandler.getMultiResult(sql.toString());
		} catch (Exception e) {
			logger.error("���˳�����EXCEL����:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	public static void main(String[] args){
		 ViolationDao dao = new ViolationDao();
	}
}