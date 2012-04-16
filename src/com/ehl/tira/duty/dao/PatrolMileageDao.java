package com.ehl.tira.duty.dao;

import org.apache.log4j.Logger;

import com.appframe.action.Controller;
import com.appframe.data.sql.DBHandler;
import com.ehl.tira.Tira;
import com.ehl.tira.util.Sql;
import com.ehl.tira.util.Sql;

/**
 * PatrolMileageDao(Ѳ��,���)Ѳ�����
 * 
 * @author xiayouxue
 * 
 */
public class PatrolMileageDao extends Controller {
	
	private Logger logger = Logger.getLogger(PatrolMileageDao.class);

	private final String tblName = "t_tira_analyze_pratrolm_day";// Ѳ����̷�����
	private final String day = "day";//����
	private final String deptCode = "deptCode";//��������
	private final String deptDesc = "deptDesc";//��������
	private final String areaMileage = "areaMileage";//Ͻ�����
	private final String patrolMileage = "patrolMileage";//Ѳ�����
	private final String patrolCar = "patrolCar";//Ѳ�߳�����
	private final String area = "area";//��������
	
	/*
	action
		��ȡ����		->	����ȱʧ
		�жϲ���		->	�������Ͳ�����ֵ����
		����ҵ���෽��	->	���ݲ���
		������Ӧ
	core
		�������ݷ����෽��	->	���ݲ���
		����ת��		->	...
	dao
		���ݲ�����ƴ��sql���
		��ȡ����		->	���ݲ���
	*/
	
	private final String location = "��������->��Ͻ�����ٹ�·����->Ѳ������̷���:dao��ȡ";//��������
	
	
	private String getLocation(String location){
		return Tira.root + "->" + Tira.duty + "->" + location + ":" + Tira.dao
		+ "��";
	}

	/**
	 * д������
	 * @param sql sql���
	 * @param processDesc ���̽��в�������<br>
	 * ��ҵ��ģ������+��̨���蹹�ɣ�<br>
	 * �磺<br>
	 * ��������->��Ͻ�����ٹ�·����->Ѳ������̷���:dao���ȡѲ������̷�������
	 * @return �����Ƿ�ɹ�
	 */
	public boolean writeData(String sql, String processDesc){
		boolean isSuccess = false;
		try {
			isSuccess = DBHandler.execute(sql);
		} catch (Exception e) {
			System.err.println("writeData->sql:\n" + sql);
			logger.error(processDesc + "���쳣��", e);
			e.printStackTrace();
		}
		
		if(isSuccess){
			logger.info("���ɹ���");
		}else{
			logger.info("��ʧ�ܣ�");
		}
		
		return isSuccess;
	}
	
	/**
	 * ��ȡ����
	 * @param sql sql���
	 * @param processDesc ���̽��в�������<br>
	 * ��ҵ��ģ������+��̨���蹹�ɣ�<br>
	 * �磺<br>
	 * ��������->��Ͻ�����ٹ�·����->Ѳ������̷���:dao���ȡѲ������̷�������
	 * @return ����
	 */
	public Object[][] readData(String sql, String processDesc){
		Object[][] data = null;
		try {
			data = DBHandler.getMultiResult(sql);
		} catch (Exception e) {
			System.err.println("readData->sql:\n" + sql);
			logger.error(processDesc + "���쳣��", e);
			e.printStackTrace();
		}
		
		if(data == null){
			logger.info("��δ��ȡ�����ݣ�");
		}else{
			logger.info("����ȡ��" + data.length + "����¼��");
		}
		
		return data;
	}
	//�ſ�����ͣ���ƽ��
	
	
	/**
	 * getPatrolMileageSql<br>
	 * ��ȡѲ�����ͳ������sql���
	 * @param area Ͻ��
	 * @param dept ����
	 * @param dateS ��ʼʱ��
	 * @param dateE ����ʱ��
	 * @return Ѳ�����ͳ������sql���
	 */
	public String getPatrolMileageSql(String area, String dept, String dateS,
			String dateE, boolean isAvg){
		
		String sql = "";
		if(!"".equals(dept)){
			
			String daySel = Sql.toChar(day, Sql.getDFAbbr(dateS, dateE));
			String areaMileageSel = "sum(" + areaMileage + ") ama";
			String patrolMileageSel = "sum(" + patrolMileage + ") ptma";
			String patrolCarSel = "sum(" + patrolCar + ") pca ";
			String[] cols = {daySel, areaMileageSel, patrolMileageSel,
					patrolCarSel};
			if(isAvg){
				String patrolAvgMileageAvg = "sum(" + patrolMileage + ")/sum(" + patrolCar + ") pama ";
				cols = new String[]{daySel, areaMileageSel, patrolAvgMileageAvg};
			}
			
			String select = Sql.join(cols, ",");
			
			String from = tblName;
			
			String where = "";
			where += Sql.getWhereDate(day, dateS, dateE, 3);
			if (!"".equals(area)) {
				where += " and " + this.area + "='" + area +"'";
			}
			if (!"".equals(dept)) {
				where += " and " + this.deptCode + "='" + dept +"'";
			}
			
			String groupBy = daySel;
			
			String orderBy = "ptma/pca/ama";
			if(isAvg){
				orderBy = "pama/ama";
			}
			
			sql = Sql.select(select, from, where, groupBy, orderBy);
			
		}else{
			String deptDescSel = deptDesc;
			String areaMileageSel = "sum(" + areaMileage + ") ama";///count(" + day + ")
			String patrolMileageSel = "sum(" + patrolMileage + ") ptma";
			String patrolCarSel = "sum(" + patrolCar + ") pca ";
			String[] cols = {deptDescSel, areaMileageSel, patrolMileageSel,
					patrolCarSel};
			if(isAvg){
				String patrolAvgMileageAvg = "sum(" + patrolMileage + ")/sum(" + patrolCar + ") pama ";
				cols = new String[]{deptDescSel, areaMileageSel, patrolAvgMileageAvg};
			}
			String select = Sql.join(cols,",");
			String from = tblName;
			String where = "";
			where += Sql.getWhereDate(day, dateS, dateE, 3);
			if (!"".equals(area)) {
				where += " and " + this.area + "='" + area +"'";
			}
			if (!"".equals(dept)) {
				where += " and " + this.deptCode + "='" + dept +"'";
			}
			String groupBy = deptDescSel;
			String orderBy = "ptma/pca/ama";
			if(isAvg){
				orderBy = "pama/ama";
			}
			sql = Sql.select(select, from, where, groupBy, orderBy);
		}
		
		return sql;
	}
	
	
	/**
	 * ��ȡѲ���������<br>
	 * ��ȡ���ݲ����ǵ�һ��
	 * @param area Ͻ��
	 * @param dept ����
	 * @param dateS ��ʼʱ��
	 * @param dateE ����ʱ��
	 * @return Ѳ�����ͳ������
	 */
	public Object[][] getPatrolMileageData(String area, String dept, String dateS,
			String dateE, boolean isAvg) {
		String sql = getPatrolMileageSql(area, dept, dateS, dateE, isAvg);
		Object[][] data = readData(sql, "��������->��Ͻ�����ٹ�·����->Ѳ��"+(isAvg?"��":"ƽ��")+"��̷���:dao�㣬��ȡѲ��"+(isAvg?"��":"ƽ��")+"�������"); 
		return data;
	}
	
	public Object[][] totalDept(String area2, String dateS, String dateE){
		String location = getLocation("");
		Object[][] data = null;
		String deptDescSel = deptDesc;
		String areaMileageSel = "sum(" + areaMileage + ") ama";
		String patrolMileageSel = "sum(" + patrolMileage + ") ptma";
		String patrolCarSel = "sum(" + patrolCar + ") pca ";
		String[] cols = {deptDescSel, areaMileageSel, patrolMileageSel,
				patrolCarSel};
		String select = Sql.join(cols,",");
		String from = tblName;
		String where = "";
		if (!"".equals(area2)) {
			where += " and " + this.area + "='" + area2 +"'";
		}
		where += Sql.getWhereDate(day, dateS, dateE, 3);
		String groupBy = deptDescSel;
		String orderBy = "ptma/pca/ama";
		String sql = Sql.select(select, from, where, groupBy, orderBy);
		data = readData(sql, location); 
		return data;
	}
	
	public Object[][] totalTime(String dept, String dateS, String dateE){
		Object[][] data = null;
		String daySel = Sql.toChar(day, Sql.getDFAbbr(dateS, dateE));
		String areaMileageSel = "sum(" + areaMileage + ") ama";
		String patrolMileageSel = "sum(" + patrolMileage + ") ptma";
		String patrolCarSel = "sum(" + patrolCar + ") pca ";
		String[] cols = {daySel, areaMileageSel, patrolMileageSel,
				patrolCarSel};
		String select = Sql.join(cols,",");
		String from = tblName;
		String where = "";
		if (!"".equals(dept)) {
//			where += Sql.getWhere(deptCode, dept);
		}
		
		where += Sql.getWhereDate(day, dateS, dateE, 3);
		String groupBy = daySel;
		String orderBy = "ptma/pca/ama";
		String sql = Sql.select(select, from, where, groupBy, orderBy);
		data = readData(sql, location); 
		return data;
	}
	
	public Object[][] avgDept(String area2, String dateS, String dateE){
		Object[][] data = null;
		String deptDescSel = deptDesc;
		String areaMileageSel = "sum(" + areaMileage + ") ama";
		String patrolAvgMileageAvg = "sum(" + patrolMileage + ")/sum(" + patrolCar + ") pama ";
		String[] cols = new String[]{deptDescSel, areaMileageSel, patrolAvgMileageAvg};
		String select = Sql.join(cols,",");
		String from = tblName;
		String where = "";
		if (!"".equals(area2)) {
//			where += Sql.getWhere(this.area, area2);
		}
		
		where += Sql.getWhereDate(day, dateS, dateE, 3);
		String groupBy = deptDescSel;
		String orderBy = "pama/ama";
		String sql = Sql.select(select, from, where, groupBy, orderBy);
		data = readData(sql, location); 
		return data;
	}
	
	public Object[][] avgTime(String dept, String dateS, String dateE){
		Object[][] data = null;
		String deptDescSel = Sql.toChar(day, Sql.getDFAbbr(dateS, dateE));;
		String areaMileageSel = "sum(" + areaMileage + ") ama";
		String patrolAvgMileageAvg = "sum(" + patrolMileage + ")/sum(" + patrolCar + ") pama ";
		String[] cols = new String[]{deptDescSel, areaMileageSel, patrolAvgMileageAvg};
		String select = Sql.join(cols,",");
		String from = tblName;
		String where = "";
		if (!"".equals(dept)) {
			//where += Sql.getWhere(this.deptCode, dept);
		}
		
		where += Sql.getWhereDate(day, dateS, dateE, 3);
		String groupBy = deptDescSel;
		String orderBy = "pama/ama";
		String sql = Sql.select(select, from, where, groupBy, orderBy);
		data = readData(sql, location); 
		return data;
	}
	
	
	public static void main(String[] args) {
		PatrolMileageDao dao = new PatrolMileageDao();
		Object[][] data = dao.getPatrolMileageData("", "", "", "",false);
		System.out.println(data[0][0]);
	}
}
