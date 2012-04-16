package com.ehl.dispatch.dispatchTask.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.ehl.dispatch.dispatchTask.bean.TrafficnewsfeedsBean;
import com.ehl.sm.common.util.StringUtil;

public class TaskUtil {
	Logger logger = Logger.getLogger(TaskUtil.class);
	/**
	 * ���ݵ�·���ƣ���ʼ����ֹ���ȡ�ù�Ͻ�������<br/>
	 * ���ݵ�·���ƣ���ʼ����ֹ���ȡ�ù�Ͻ�������
	 * @param roadName
	 * @param startMileage
	 * @param endMileage
	 * @return
	 */
	public String getDepartmentNameByRoadname (String roadName,String startMileage,String endMileage) {
		Object[] resultObject = null;
		StringBuffer sqlBuffer = new StringBuffer("select jgmc from T_FW_ROAD_DEPARTMENT where "); 
		try {
			sqlBuffer.append("dlmc like '%");
			sqlBuffer.append(roadName);
			sqlBuffer.append("%' and ((START_LENGTH <= '");
			sqlBuffer.append(Integer.valueOf(startMileage));
			sqlBuffer.append("' and END_LENGTH >= '");
			sqlBuffer.append(Integer.valueOf(startMileage));
			sqlBuffer.append("') or ");
			
			sqlBuffer.append(" (START_LENGTH <= '");
			sqlBuffer.append(Integer.valueOf(endMileage));
			sqlBuffer.append("' and END_LENGTH >= '");
			sqlBuffer.append(Integer.valueOf(endMileage));
			sqlBuffer.append("')) order by jgmc ");
			
			resultObject = DBHandler.getLineResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ȡ��ӵ�µ�·����Ͻ��Ͻ������ sql:" + sqlBuffer);
		}
		if(resultObject != null) {
			return String.valueOf(resultObject[0]);
		}
		return "δȡ������Ͻ��";
	}

	/**
	 * ȡ�����и������ɼ���·��<br/>
	 * ȡ�����и������ɼ���·����Ϣ
	 * @param roadName
	 * @param startMileage
	 * @param endMileage
	 * @return
	 */
	public Object[][] getTrafficnewsfeedsInfo () {
		Object[][] resultObject = null;
		StringBuffer sqlBuffer = new StringBuffer("select "); 
		/* ���0,��·����1,·�η���2,��ʼ·�����3,��ֹ·�����4,��ʼ·�ξ���5,��ʼ·��γ��6,��ֹ·�ξ���7
		��ֹ·��γ��8,λ�û��������9,·��10,��ʼʱ��11,��ֹʱ��12,·���Ƿ����13
		·��ԭ��14,������15,��������ϵ��ʽ16,¼����17,¼��ʱ��18,��Ͻ���19,��Ϣ��Դ20,����״̬21
		��ʵ��22,��ʵʱ��23,��ʵ���24,�������25,�����26,���ʱ��27
		������28,�Ʒ�29,�Ƿ���ⷢ��30,��ע31,���ά�ֵ�ʱ��32,ͬ������33,��ͨ������34,¼�벿��35,����·��36*/
		try {
			sqlBuffer.append(" BH,DLMC,LDFX,QSLC,ZZLC,QSJD,QSWD,ZZJD,ZZWD,WZMS,LK,QSSJ,ZZSJ,");
			sqlBuffer.append(" SFJS,LKYY,BLR,LXFS,LRR,LRSJ,GXDD,XXLY,CLZT,HSR,HSSJ,HSQK,");
			sqlBuffer.append(" CZQK,SCR,SCSJ,SCYJ,JF,SFFB,BZ,WCSJ,TBZJ,JTLZJ,LRBM,SSLD");
			sqlBuffer.append(" from EXCH_T_TRAFFICNEWSFEEDS_TEMP where ");
			// ȡ��δ������ӵ�±�����Ϣ
			sqlBuffer.append(" SFJS='0' ");
			resultObject = DBHandler.getMultiResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ȡ�����и������ɼ�·������ sql:" + sqlBuffer);
		}
		return resultObject;
	}
	
	/**
	 * �жϸ���·����Ϣ�Ƿ�Ϊ�ظ���Ϣ ���� ��ǡ����Ϣ<br/>
	 * �жϸ���·����Ϣ�Ƿ�Ϊ�ظ���Ϣ ���� ��ǡ����Ϣ
	 * @param trafficnewsfeedsBean
	 * @return true ���Բ�������Ϣ false �������ݻ����ظ�����
	 */
	public boolean getInsertFlg (TrafficnewsfeedsBean trafficnewsfeedsBean) {
		TaskUtil taskUtil = new TaskUtil();
		Object[][] trafficnewsfeedsInfo = taskUtil.getTrafficnewsfeedsInfo();
		// ȡ�÷ǿյ����
		if(trafficnewsfeedsInfo != null) {
			// ��¼��·����ʼʱ��
			String waitQssj = trafficnewsfeedsBean.getQSSJ();
			long second = StringUtil.getTimeInterval(waitQssj, taskUtil.getSysdate());
			long miniates = second/60;
			// �뵱ǰϵͳʱ������Сʱ�����ϵ�ӵ��·����ϢΪ��ǡ������
			if(miniates > 30) {
				return false;
			}
			String roadName = "";
			String qslc = "";
			String zzlc = "";
			/* ���0,��·����1,·�η���2,��ʼ·�����3,��ֹ·�����4,��ʼ·�ξ���5,��ʼ·��γ��6,��ֹ·�ξ���7
			��ֹ·��γ��8,λ�û��������9,·��10,��ʼʱ��11,��ֹʱ��12,·���Ƿ����13
			·��ԭ��14,������15,��������ϵ��ʽ16,¼����17,¼��ʱ��18,��Ͻ���19,��Ϣ��Դ20,����״̬21
			��ʵ��22,��ʵʱ��23,��ʵ���24,�������25,�����26,���ʱ��27
			������28,�Ʒ�29,�Ƿ���ⷢ��30,��ע31,���ά�ֵ�ʱ��32,ͬ������33,��ͨ������34,¼�벿��35,����·��36*/
			for(int i=0; i<trafficnewsfeedsInfo.length; i++) {
				roadName = "";
				qslc = "";
				zzlc = "";
				roadName = String.valueOf(trafficnewsfeedsInfo[i][1]);
				qslc = String.valueOf(trafficnewsfeedsInfo[i][3]);
				zzlc = String.valueOf(trafficnewsfeedsInfo[i][4]);
				// ��·���� ����ʼ��� ����ֹ��� һ�������ݲ��ٲ���
				if((roadName.equals(trafficnewsfeedsBean.getDLMC())) &&
						(qslc.equals(trafficnewsfeedsBean.getQSLC())) &&
						(zzlc.equals(trafficnewsfeedsBean.getZZLC()))
						) {
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * ӵ�µ�·����Ϣ�Ĳ���<br/>
	 * ӵ�µ�·����Ϣ�Ĳ��봦��
	 * @param trafficnewsfeedsBean
	 * @return
	 */
	public Object[][] insertTrafficnewsfeedsInfo (TrafficnewsfeedsBean trafficnewsfeedsBean) {
		Object[][] resultObject = null;
		/* ���0,��·����1,·�η���2,��ʼ·�����3,��ֹ·�����4,��ʼ·�ξ���5,��ʼ·��γ��6,��ֹ·�ξ���7
		��ֹ·��γ��8,λ�û��������9,·��10,��ʼʱ��11,��ֹʱ��12,·���Ƿ����13
		·��ԭ��14,������15,��������ϵ��ʽ16,¼����17,¼��ʱ��18,��Ͻ���19,��Ϣ��Դ20,����״̬21
		��ʵ��22,��ʵʱ��23,��ʵ���24,�������25,�����26,���ʱ��27
		������28,�Ʒ�29,�Ƿ���ⷢ��30,��ע31,���ά�ֵ�ʱ��32,ͬ������33,��ͨ������34,¼�벿��35,����·��36*/
		StringBuffer sqlBuffer = new StringBuffer("insert into EXCH_T_TRAFFICNEWSFEEDS_TEMP (  "); 
		sqlBuffer.append(" BH,DLMC,LDFX,QSLC,ZZLC,WZMS,LK,QSSJ,ZZSJ,");
		sqlBuffer.append(" SFJS,LKYY,GXDD,XXLY,CLZT,");
		sqlBuffer.append(" SFFB)  values (");
		try {
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getBH());
			sqlBuffer.append("','");
			sqlBuffer.append(trafficnewsfeedsBean.getDLMC());
			sqlBuffer.append("','");
			sqlBuffer.append(trafficnewsfeedsBean.getLDFX());
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(Integer.valueOf(trafficnewsfeedsBean.getQSLC()));
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(Integer.valueOf(trafficnewsfeedsBean.getZZLC()));
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getWZMS());
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getLK());
			sqlBuffer.append("',");
			sqlBuffer.append(" to_date('");
			sqlBuffer.append(trafficnewsfeedsBean.getQSSJ());
			sqlBuffer.append("','yyyy-mm-dd hh24:mi ss'),");
			sqlBuffer.append(" to_date('");
			sqlBuffer.append(trafficnewsfeedsBean.getZZSJ());
			sqlBuffer.append("','yyyy-mm-dd hh24:mi ss'),");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getSFJS());
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getLKYY());
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getGXDD());
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getXXLY());
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getCLZT());
			sqlBuffer.append("',");
			sqlBuffer.append(" '");
			sqlBuffer.append(trafficnewsfeedsBean.getSFFB());
			sqlBuffer.append("' )");
			System.out.println("***********"+sqlBuffer);
			resultObject = DBHandler.getMultiResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ȡ�����и������ɼ�·������ sql:" + sqlBuffer);
		}
		return resultObject;
	}

	/**
	 * ��ȡϵͳʱ��<br/>
	 * ��ȡ��ǰϵͳ������ʱ��
	 * @return
	 */
	public String getSysdate () {
		Object[] resultObject = null;
		StringBuffer sqlBuffer = new StringBuffer(" select to_char(sysdate,'yyyy-MM-dd hh:mm:ss') from dual "); 
		String formatStr = "yyyy-MM-dd hh:mm:ss";
		TaskUtil taskUtil = new TaskUtil();
		try {
			resultObject = DBHandler.getLineResult(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ȡ�÷�����ʱ����� sql:" + sqlBuffer);
		}
		if(resultObject != null) {
			return String.valueOf(resultObject[0]);
		}
		return taskUtil.getCurrDateTime(formatStr);
	}

    /**
     * ��ȡ��ǰʱ��<br/>
     * @param formatStr
     * @return
     */
    public String getCurrDateTime(String formatStr){
    	String strdate=null;
    	Date dNow = new Date();
    	SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
    	strdate=formatter.format(dNow);
    	return strdate;
    }
    
    public static void main (String [] args) {
    	try {
    		InputStream second = System.in;
    		BufferedReader reader = new BufferedReader(new InputStreamReader(second));
    		String readStr = reader.readLine();
    		long log = Long.valueOf(readStr);
    		long miniates = log/60;
    		System.out.println("��������:" + miniates);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
