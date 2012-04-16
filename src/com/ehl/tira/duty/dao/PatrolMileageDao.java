package com.ehl.tira.duty.dao;

import org.apache.log4j.Logger;

import com.appframe.action.Controller;
import com.appframe.data.sql.DBHandler;
import com.ehl.tira.Tira;
import com.ehl.tira.util.Sql;
import com.ehl.tira.util.Sql;

/**
 * PatrolMileageDao(巡逻,里程)巡逻里程
 * 
 * @author xiayouxue
 * 
 */
public class PatrolMileageDao extends Controller {
	
	private Logger logger = Logger.getLogger(PatrolMileageDao.class);

	private final String tblName = "t_tira_analyze_pratrolm_day";// 巡逻里程分析表
	private final String day = "day";//日期
	private final String deptCode = "deptCode";//机构代码
	private final String deptDesc = "deptDesc";//机构描述
	private final String areaMileage = "areaMileage";//辖区里程
	private final String patrolMileage = "patrolMileage";//巡逻里程
	private final String patrolCar = "patrolCar";//巡逻车辆数
	private final String area = "area";//所属区域
	
	/*
	action
		获取参数		->	参数缺失
		判断参数		->	参数类型不符，值不符
		调用业务类方法	->	数据不符
		发送响应
	core
		调用数据访问类方法	->	数据不符
		数据转换		->	...
	dao
		根据参数，拼接sql语句
		获取数据		->	数据不符
	*/
	
	private final String location = "分析研判->各辖区高速公路分析->巡逻总里程分析:dao获取";//所属区域
	
	
	private String getLocation(String location){
		return Tira.root + "->" + Tira.duty + "->" + location + ":" + Tira.dao
		+ "，";
	}

	/**
	 * 写入数据
	 * @param sql sql语句
	 * @param processDesc 流程进行步骤描述<br>
	 * 由业务模块名称+后台步骤构成，<br>
	 * 如：<br>
	 * 分析研判->各辖区高速公路分析->巡逻总里程分析:dao层获取巡逻总里程分析数据
	 * @return 操作是否成功
	 */
	public boolean writeData(String sql, String processDesc){
		boolean isSuccess = false;
		try {
			isSuccess = DBHandler.execute(sql);
		} catch (Exception e) {
			System.err.println("writeData->sql:\n" + sql);
			logger.error(processDesc + "，异常！", e);
			e.printStackTrace();
		}
		
		if(isSuccess){
			logger.info("，成功！");
		}else{
			logger.info("，失败！");
		}
		
		return isSuccess;
	}
	
	/**
	 * 读取数据
	 * @param sql sql语句
	 * @param processDesc 流程进行步骤描述<br>
	 * 由业务模块名称+后台步骤构成，<br>
	 * 如：<br>
	 * 分析研判->各辖区高速公路分析->巡逻总里程分析:dao层获取巡逻总里程分析数据
	 * @return 数据
	 */
	public Object[][] readData(String sql, String processDesc){
		Object[][] data = null;
		try {
			data = DBHandler.getMultiResult(sql);
		} catch (Exception e) {
			System.err.println("readData->sql:\n" + sql);
			logger.error(processDesc + "，异常！", e);
			e.printStackTrace();
		}
		
		if(data == null){
			logger.info("，未读取到数据！");
		}else{
			logger.info("，读取到" + data.length + "条记录！");
		}
		
		return data;
	}
	//排开，求和，求平均
	
	
	/**
	 * getPatrolMileageSql<br>
	 * 获取巡逻里程统计数据sql语句
	 * @param area 辖区
	 * @param dept 机关
	 * @param dateS 起始时间
	 * @param dateE 结束时间
	 * @return 巡逻里程统计数据sql语句
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
	 * 获取巡逻里程数据<br>
	 * 获取数据并不是单一的
	 * @param area 辖区
	 * @param dept 机关
	 * @param dateS 起始时间
	 * @param dateE 结束时间
	 * @return 巡逻里程统计数据
	 */
	public Object[][] getPatrolMileageData(String area, String dept, String dateS,
			String dateE, boolean isAvg) {
		String sql = getPatrolMileageSql(area, dept, dateS, dateE, isAvg);
		Object[][] data = readData(sql, "分析研判->各辖区高速公路分析->巡逻"+(isAvg?"总":"平均")+"里程分析:dao层，获取巡逻"+(isAvg?"总":"平均")+"里程数据"); 
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
