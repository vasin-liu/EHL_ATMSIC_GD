package com.ehl.dispatch.cdispatch.dao;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;

public class DutyInfoCountDao {
	
	private Logger logger = Logger.getLogger(DutyInfoCountDao.class);
	
	public Object[][] getData(String eventtype,String sTARTTIME,String eNDTIME,String departIds,String depName,String jglx,String jgid,String deptType){
		Object[][] dutyInfo = null;
		StringBuffer sql = new StringBuffer();
		
		if("".equals(eventtype)){
			if("0".equals(deptType)){
				sql.append("SELECT F_GET_DEPT(SUBSTR(ALARMUNIT,0,4)||'00000000'),COUNT(1),EVENTTYPE FROM T_ATTEMPER_ALARM_ZD WHERE 1=1 ");
			}else if("1".equals(deptType)){
				sql.append("SELECT F_GET_DEPT(SUBSTR(ALARMUNIT,0,6)||'000000'),COUNT(1),EVENTTYPE FROM T_ATTEMPER_ALARM_ZD WHERE 1=1 ");
				if("".equals(jgid)){
					sql.append("AND SUBSTR(ALARMUNIT,0,6)||'000000'='"+jgid+"' ");
				}
			}
		}else{
			
		}
		
		if(!"".equals(sTARTTIME) && !"".equals(eNDTIME)){
			sql.append("AND ALARMTIME>=TO_DATE('"+sTARTTIME+"','YYYY-MM-DD') AND ALARMTIME<=TO_DATE('"+eNDTIME+"','YYYY-MM-DD') ");
		}else if(!"".equals(sTARTTIME) && "".equals(eNDTIME)){
			sql.append("AND ALARMTIME>=TO_DATE('"+sTARTTIME+"','YYYY-MM-DD') ");
		}else if("".equals(sTARTTIME) && !"".equals(eNDTIME)){
			sql.append("AND ALARMTIME<=TO_DATE('"+eNDTIME+"','YYYY-MM-DD') ");
		}
		if("0".equals(deptType)){
			sql.append("GROUP BY SUBSTR(ALARMUNIT, 0, 4),EVENTTYPE ");
			sql.append("ORDER BY SUBSTR(ALARMUNIT, 0, 4),EVENTTYPE");
		}else if("1".equals(deptType)){
			sql.append("GROUP BY SUBSTR(ALARMUNIT, 0, 6),EVENTTYPE ");
			sql.append("ORDER BY SUBSTR(ALARMUNIT, 0, 6),EVENTTYPE");
		}
		
		try {
			dutyInfo = DBHandler.getMultiResult(sql.toString());
		} catch (Exception e) {
			
		}
		return dutyInfo;
	}

	public String[] getDeptData(String eventtype,String sTARTTIME,String eNDTIME,String jglx,String jgid,String deptType){
		Object[][] deptArr = null;
		String [] dept = null;
		StringBuffer sql = new StringBuffer();
		if("0".equals(deptType)){
			sql.append("SELECT DISTINCT F_GET_DEPT(SUBSTR(ALARMUNIT,0,4)||'00000000') FROM T_ATTEMPER_ALARM_ZD WHERE 1=1 ");
		}else if("1".equals(deptType)){
			sql.append("SELECT DISTINCT F_GET_DEPT(SUBSTR(ALARMUNIT,0,6)||'000000') FROM T_ATTEMPER_ALARM_ZD WHERE SUBSTR(ALARMUNIT,2,4)!='00' ");
			if("".equals(jgid)){
				sql.append("AND SUBSTR(ALARMUNIT,0,6)||'000000'='"+jgid+"' ");
			}
		}
		
		if(!"".equals(sTARTTIME) && !"".equals(eNDTIME)){
			sql.append("AND ALARMTIME>=TO_DATE('"+sTARTTIME+"','YYYY-MM-DD') AND ALARMTIME<=TO_DATE('"+eNDTIME+"','YYYY-MM-DD') ");
		}else if(!"".equals(sTARTTIME) && "".equals(eNDTIME)){
			sql.append("AND ALARMTIME>=TO_DATE('"+sTARTTIME+"','YYYY-MM-DD') ");
		}else if("".equals(sTARTTIME) && !"".equals(eNDTIME)){
			sql.append("AND ALARMTIME<=TO_DATE('"+eNDTIME+"','YYYY-MM-DD') ");
		}
		
		if(!"".equals(eventtype)){
			sql.append("AND EVENTTYPE='"+eventtype+"' ");
		}
		try {
			deptArr = DBHandler.getMultiResult(sql.toString());
			if(deptArr != null && deptArr.length>0){
				dept = new String[deptArr.length];
				for (int i = 0; i < deptArr.length; i++) {
					dept[i]=StringHelper.obj2str(deptArr[i][0],"");
				}
			}
		} catch (Exception e) {
			
		}
		return dept;
	}
	
	
	
	
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
	
	
	public static String getDate(String col,String dateS, String dateE){
		String date = "";
		col = "to_char("+col+",'yyyy-mm-dd')";
		if(!"".equals(dateS)){
			date += " and " + col + ">='" + dateS+"'";
		}
		if(!"".equals(dateE)){
			date += " and " + col + "<='" + dateE+"'";
		}
		if(!"".equals(date)){
			date = date.substring(" and ".length());
		}
		return date;
	}
	
	
	
	public static int getLevel(String jgid) {
		int level = 0;
		jgid = jgid + "";
		for (int i = 0; i < jgid.length(); i += 2) {
			if (jgid.substring(i, i + 2).equals("00")) {
				level = i;
				break;
			}
		}
		return level;
	}

	/**
	 * ɸѡ��ָ��������Χ�ڵ����ݣ������û��������������ӻ���
	 * @param col
	 * @param jgid
	 * @return
	 */
	public static String getArea(String col, String jgid) {
		jgid = jgid + "00";
		int level = getLevel(jgid);
		return "substr(" + col + ",1," + level + ")='"
				+ jgid.substring(0, level) + "'";
	}

	/**
	 *  ��ȡһ��������䣬�����û��������������ӻ���������<br>
	 *  ���磺���ܶӷ��࣬�������ܶ����ݡ�֧�����ݺʹ������
	 * @param col
	 * @param gb 2->�ܶӣ�4->֧�ӣ�6->���
	 * @return
	 */
	public static String getGB(String col, int gb) {
		return "rpad(substr(" + col + ",1," + gb + "),12,'0')";
	}
	
	
	/**
	 * ɸѡ���ܶӡ�֧�ӡ����
	 * @param col
	 * @param gb 2->�ܶӣ�4->֧�ӣ�6->���
	 * @return
	 */
	public static String getWhere(String col, int gb) {
		return "substr(" + col + "," + (gb - 1) + ",2) != '00' and substr("
				+ col + "," + (gb + 1) + ",2) = '00'";
	}
	
	/**
	 * ȡ��֧�������е�����
	 * @param jgid ֧�ӻ������
	 * @return ֧������������
	 */
	public String getJgmcByJgid(String jgid){
		
		String jgmc = null;
		String sql = "select jgmc from t_sys_department where jgid='"+jgid+"'";
		jgmc = String.valueOf(DBHandler.getSingleResult(sql));
		if(jgmc != null){
			int index = jgmc.indexOf("��");
			if(index != -1){
				jgmc = jgmc.substring(0,index+1);
			}else{
				jgmc = null;
			}
		}
		
		return jgmc;
	}
	
	public String join(String[] strs, String sep) {
		String joinStr = null;
		if (strs != null && strs.length > 0 && sep != null) {
			joinStr = "";
			for (int i = 0; i < strs.length; i++) {
				joinStr += sep + strs[i];
			}
			joinStr = joinStr.substring(sep.length());
		}
		return joinStr;
	}
	
	public static String getSx(String jgid, String deptid){
		String where = "";
		if(deptid != null){
			if( deptid.indexOf("'") != -1){
				where = jgid +" in (" + deptid+")";
			}else{
				where = getArea(jgid, deptid);
			}
		}
		return where;
	}
	
	/**
	 * ȡ��ָ�������ȼ�����������
	 * @param statType �����ȼ�
	 * @param deptid ����ɸѡ
	 * @param isIn �Ƿ����in��ʽɸѡ����
	 * @return
	 */
	public String getDeptSql(int statType, String deptid) {
		String deptSql = "select jgid,jgmc from t_sys_department";
		deptSql += " where ";
		deptSql += "jgid<'446000000000'";//�ų��ơ�������
		deptSql += " and ";
		deptSql += getSx("jgid", deptid);
		//Modify by Xiayx 2011-10-12
		//��ͳ�ƹ���->ͳ��֧��Ͻ����Χʱ����ʾ֧������
		//deptSql += " and " + getWhere("jgid", statType);
		if(!deptid.substring(2, 4).equals("00") 
				&& deptid.substring(4, 6).equals("00")){
			deptSql += " and substr(jgid," + (statType + 1) + ",2) = '00'";
		}else{
			deptSql += " and " + getWhere("jgid", statType);
		}
		//Modification finished
		deptSql = "(" + deptSql + ") dept";
		return deptSql;
	}

	/**
	 * 
	 * @param statType �����ȼ�λ����2�����ܶ�ͳ�ƣ�4����֧��ͳ�ƣ�6�������ͳ�ƣ�ͳ�����ݽ԰����û��������ӻ�������
	 * @param dateS ��ʼʱ��
	 * @param dateE ����ʱ��
	 * @param deptid ɸѡ������ɸѡ������Χ�����û��������ӻ���
	 * @param isIn	ɸѡ��ʽ���Ƿ����in () ��ʽ
	 * @return
	 */
	public String getAccTypeSql(int statType, String dateS, String dateE, String deptid,String area, String cjgid){
		//��ȡ�¹�����sql���
		String jgid = getGB("reportunit", statType)+ " jgid";//��������
		String sw = "SUM(DECODE(sign(ACC.DEATHPERSONCOUNT-2), 1,1,0)) sw";//һ������3�����Ͻ�ͨ�¹�
		String yydkc = "SUM(TO_NUMBER(ACC.ISBUS)) yydkc";//Ӫ�˴�ͳ��¹�
		String xc = "SUM(TO_NUMBER(ACC.ISSCHOOLBUS)) xc";//У���¹�
		String whp = "SUM(TO_NUMBER(ACC.ISDANAGERCAR)) whp";//Σ��Ʒ���䳵��ͨ�¹�
		String sgst = "SUM(TO_NUMBER(ACC.ISFOREIGNAFFAIRS)) sgst";//��۰�̨�������¹�
		String[] cols = {jgid,sw,yydkc,xc,whp,sgst};
		String select = join(cols, ",");
		String from = "T_ATTEMPER_ACCIDENT_ZD ACC,T_ATTEMPER_ALARM_ZD ALARM";//����������¹ʱ�(֧�Ӱ汾)
		String where = "ACC.ALARMID=ALARM.ALARMID";
		where += " and " + getDate("reporttime", dateS, dateE);
		where += " and " + getSx("reportunit", deptid);
		//Modify by Xiayx 2011-10-12
		//��ͳ�ƹ���->ͳ��֧��Ͻ����Χʱ����ʾ֧������
		//where += " and substr(reportunit,"+(statType-1)+",2)!='00'";
		if(!deptid.substring(4, 6).equals("00")){
			where += " and substr(reportunit,"+(statType-1)+",2)!='00'";
		}
		//Modification finished
		//Modify by Xiayx 2011-10-13
		//��ͳ�ƹ���->��ͬ�����ȼ����û��鿴��ͬ�����ݣ�����λ���յ�����Ϣ��
		if(cjgid.substring(2, 4).equals("00")){
			String siftState = " and eventstate in ('004043','004031','004036','004037','004035')";
			//�¹ʣ����ش�ͷ����ش�
			if("1".equals(area)){
				siftState = " and eventstate in ('004031','004036','004037','004035')";
			}
			where += siftState;
		}else if(cjgid.substring(4, 6).equals("00")){
			where += " and eventstate not in ('004032','004033')";
		}else {
			where += " and eventstate != '004032'";
		}
		//Modification finished
		
		String groupby = getGB("reportunit", statType);
		String accSql = "select " + select;
		accSql += " from " + from;
		accSql += " where " + where;
		accSql += " group by " + groupby;
		accSql = "(" + accSql + ") acc";
		//��ȡ����sql��䣬����û�����ݵĻ���
		String deptSql = getDeptSql(statType, deptid);
		// ��sql���
		String sql = "select ";
		sql += "replace(dept.jgmc,'�����ֽ�ͨ����','����'),";
		sql += "decode(acc.sw,null,0,acc.sw),";
		sql += "decode(acc.yydkc,null,0,acc.yydkc),";
		sql += "decode(acc.xc,null,0,acc.xc),";
		sql += "decode(acc.whp,null,0,acc.whp),";
		//Modified by Liuwx 2011-10-17
		sql += "decode(acc.sgst,null,0,acc.sgst),";
		sql += "(decode(acc.sw, null, 0, acc.sw)+decode(acc.yydkc, null, 0, acc.yydkc)+" +
				"decode(acc.xc, null, 0, acc.xc)+decode(acc.whp, null, 0, acc.whp)+" +
				"decode(acc.sgst, null, 0, acc.sgst)) total ";
		//Modification finished
		sql += " from " + deptSql;
		sql += " left join " + accSql;
		sql += " on " + "dept.jgid=acc.jgid";
		sql += " order by dept.jgid";
		return sql;
	}

	public Object[][] accType(int statType, String dateS, String dateE,
			String deptid,String area, String cjgid) {
		String sql = getAccTypeSql(statType, dateS, dateE, deptid,area, cjgid);
		Object[][] data = readData(sql, "�¹�����ͳ�Ƴ���");
		return data;
	}
	
	public String getAlarmTypeSql(int statType, String dateS, String dateE, 
			String deptid, String from, String eventType, String area,String cjgid){
		String jgid = getGB("reportunit", statType)+" jgid";
		String num = "count(reportunit) num";
		String where = "eventtype='"+eventType+"'";
		where += " and " + getDate("reporttime", dateS, dateE);
		where += " and " + getSx("reportunit", deptid);
		//Modify by Xiayx 2011-10-12
		//��ͳ�ƹ���->ͳ��֧��Ͻ����Χʱ����ʾ֧������
		//where += " and substr(reportunit,"+(statType-1)+",2)!='00'";
		if(!deptid.substring(4, 6).equals("00")){
			where += " and substr(reportunit,"+(statType-1)+",2)!='00'";
		}
		//Modification finished
		//Modify by Xiayx 2011-10-11
		//��ͳ�ƹ���->���ɸѡ����->�¹ʡ�ӵ�º�ʩ��ռ��
		if(eventType.equals("001024")){
			if(cjgid.substring(2, 4).equals("00")){
				String siftState = " and eventstate in ('004043','004031','004036','004037','004035')";
				//�¹ʣ����ش�ͷ����ش�
				if("1".equals(area)){
					siftState = " and eventstate in ('004031','004036','004037','004035')";
				}
				where += siftState;
			}else if(cjgid.substring(4, 6).equals("00")){
				where += " and eventstate not in ('004032','004033')";
			}else {
				where += " and eventstate != '004032'";
			}
		}else if(eventType.equals("001002")){
			//ӵ�£������ͷǳ���
			if("1".equals(area)){
				where += " and (select roadlevel from t_oa_dictdlfx where roadid = dlmc) is not null";
			}
		}
		//Modification finished
		String groupby = getGB("reportunit", statType);
		String sql = "select " + join(new String[] { jgid, num }, ",")
				+ " from " + from + " where " + where + " group by " + groupby;
		return sql;
	}

	/**
	 * 
	 * @param statType �����ȼ�λ����2�����ܶ�ͳ�ƣ�4����֧��ͳ�ƣ�6�������ͳ�ƣ�ͳ�����ݽ԰����û��������ӻ�������
	 * @param dateS ��ʼʱ��
	 * @param dateE ����ʱ��
	 * @param deptid ɸѡ������ɸѡ������Χ�����û��������ӻ���
	 * @return
	 */
	public String getAlarmTypeSql(int statType, String dateS, String dateE, String deptid, String area,String cjgid){
		//�¹�
		String accSql = getAlarmTypeSql(statType, dateS, dateE, deptid, "t_attemper_alarm_zd", "001024", area,cjgid);
		accSql = "(" + accSql + ") acc";
		//ӵ��
		String crowSql = getAlarmTypeSql(statType, dateS, dateE, deptid, "t_attemper_alarm", "001002", area,cjgid);
		crowSql = "(" + crowSql + ") crow";
		//ʩ��ռ��
		String buildSql = getAlarmTypeSql(statType, dateS, dateE, deptid, "t_attemper_alarm", "001023", area,cjgid);
		buildSql = "(" + buildSql + ") build";
		//����(ֵ����־)
		String jgid = getGB("spdcode", statType)+" jgid";
		String num = "count(spdcode) num";
		String from = "t_oa_notice no";
		String where = "";
		where += getDate("stime", dateS, dateE);
		where += " and ";
		where += getSx("spdcode", deptid);
		//Modify by Xiayx 2011-10-12
		//��ͳ�ƹ���->ͳ��֧��Ͻ����Χʱ����ʾ֧������
		//where += " and substr(spdcode,"+(statType-1)+",2)!='00'";
		if(!deptid.substring(4, 6).equals("00")){
			where += " and substr(spdcode,"+(statType-1)+",2)!='00'";
		}
		//Modification finished
		
		//Modify by Xiayx 2011-10-11
		//��ͳ�ƹ���->���ɸѡ����->���������ձ���λ���ͺ�������λ���͵�����λ����
//		if(cjgid.substring(2, 4).equals("00")){
//			where += " and '"+cjgid+"' in (select rpdcode from t_oa_acceptdept where aid=no.id)";
//		}
		if("1".equals(area)){
			where += " and '440000000000' in (select rpdcode from t_oa_acceptdept where aid=no.id)";
		}
		//Modification finished
		String groupby = getGB("spdcode", statType);
		String otherSql = "select " + join(new String[] { jgid, num }, ",")
				+ " from " + from + " where " + where + " group by " + groupby;
		otherSql = "(" + otherSql + ") other";
		//����
		String deptSql = getDeptSql(statType, deptid);
		// ��sql���
		String sql = "select ";
		sql += "replace(dept.jgmc,'�����ֽ�ͨ����','����'),";
		sql += "decode(acc.num,null,0,acc.num),";
		sql += "decode(crow.num,null,0,crow.num),";
		sql += "decode(build.num,null,0,build.num),";
		//Modified by Liuwx 2011-10-17
		sql += "decode(other.num,null,0,other.num),";
		sql += "(decode(acc.num, null, 0, acc.num)+decode(crow.num, null, 0, crow.num)+" +
				"decode(build.num, null, 0, build.num)+decode(other.num, null, 0, other.num)) total ";
		//Modification finished
		sql += " from " + deptSql;
		sql += " left join " + accSql + " on dept.jgid=acc.jgid";
		sql += " left join " + crowSql + " on dept.jgid=crow.jgid";
		sql += " left join " + buildSql + " on dept.jgid=build.jgid";
		sql += " left join " + otherSql + " on dept.jgid=other.jgid";
		sql += " order by dept.jgid";
		return sql;
	}
	
	public Object[][] alarmType(int statType, String dateS, String dateE,
			String deptid, String area,String jgid) {
		String sql = getAlarmTypeSql(statType, dateS, dateE, deptid, area, jgid);
		Object[][] data = readData(sql, "��������ͳ�Ƴ���");
		return data;
	}
	
	
	
}
