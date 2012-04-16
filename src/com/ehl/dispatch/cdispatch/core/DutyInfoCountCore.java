package com.ehl.dispatch.cdispatch.core;

import com.appframe.data.sql.DBHandler;
import com.ehl.dispatch.cdispatch.dao.DutyInfoCountDao;
import com.ehl.tira.duty.PatrolUtil;
import com.ehl.util.Array;

public class DutyInfoCountCore {
	
	public DutyInfoCountDao dao = new DutyInfoCountDao();
	
	/***
	 * �¹�����ͳ��
	 * @param dateS ��ʼʱ��
	 * @param dateE ����ʱ��
	 * @param statType ͳ������
	 * @param deptid �������
	 * @return
	 * Modified by Liuwx 2011-10-17
	 * ����������� sortItem
	 */
	public String accType(int statType,String dateS, String dateE, 
			String deptid, String area,String jgid,int sortItem) {
		String xml = null;
		String title = getDate(dateS, dateE);
		if(deptid.equals("") || deptid.substring(2, 4).equals("00")){
			title += "��֧��";
		}else{
			title += "�����";
		}
		title += "��ͬ�¹������ϱ��¹ʾ������ͳ��ͼ";
		String[] colTitle = {getDeptGb(statType)+",�¹ʴ���,�¹�����","����3�˼�����","Ӫ�˴�ͳ�","У��",
				"Σ��Ʒ���䳵","��۰�̨������"};
		Object[][] data = dao.accType(statType,dateS, dateE, deptid,area,jgid);
		//Modified by Liuwx 2011-10-17
		Array.sort2(data, sortItem, false);
		//Modification finished
		String[][] dataStr = PatrolUtil.changeData(data);
		dataStr = PatrolUtil.changeData(dataStr, colTitle); 
		xml = PatrolUtil.columnMultipleCol(dataStr, title);
		xml = PatrolUtil.chart(xml==null?false:true, xml);
		return xml;
	}
	
	/***
	 * ��������ͳ��
	 * @param dateS ��ʼʱ��
	 * @param dateE ����ʱ��
	 * @param statType ͳ������
	 * @param deptid �������
	 * @return
	 * Modified by Liuwx 2011-10-17
	 * ����������� sortItem
	 */
	public String alarmType(int statType, String dateS, String dateE,
			String deptid,String area,String jgid,int sortItem) {
		String xml = null;
		String title = getDate(dateS, dateE);
		if(deptid.substring(2, 4).equals("00")){
			title += "��֧��";
		}else{
			title += "�����";
		}
		title += "���ͺͷ����ش�ͨ�����·�潻ͨ��̬��Ϣͳ��ͼ";
//		String request = "&lt;br&gt;"+"�����ٹ�·����ʡ����";
//		request = "1".equals(area) ? request : "";
		String[] colTitle = { getDeptGb(statType) + ",�ϱ��������,�¹����", "��ͨ�¹�",
				"��ͨӵ��", "ʩ��ռ��", "����" };
		Object[][] data = dao.alarmType(statType,dateS, dateE, deptid,area,jgid);
		//Modified by Liuwx 2011-10-17
		Array.sort2(data, sortItem, false);
		//Modification finished
		String[][] dataStr = PatrolUtil.changeData(data);
		dataStr = PatrolUtil.changeData(dataStr, colTitle);
		xml = PatrolUtil.columnMultipleCol(dataStr, title);
		xml = PatrolUtil.chart(xml==null?false:true, xml);
		return xml;
	}
	
	public String getDept(String jgid){
		String j = null;
		//2010��10��10��~19�չ㶫ʡ��֧�Ӳ�ͬ���������ϱ��������ͳ�Ʒ���ͼ
		//ʱ�䡢�ص㡢���ࡢ����
		if(jgid != null){
			String p = "�㶫ʡ";
			int level = DutyInfoCountDao.getLevel(jgid);
			if(level == 2){
				j = p;
			}else if(level == 4){
				try {
					String sql = "select jgmc from t_sys_department where jgid='"+jgid+"'";
					String city = String.valueOf(DBHandler.getSingleResult(sql));
					city = city.substring(0,city.indexOf("��")+1);
					j = p + city;
				} catch (Exception e) {
					System.err.println("��ȡ������������");
				}
			}else if(level == 6){
				j = p;
			}
		}
		return j;
	}
	
	public static String getDeptGb(int ccbm){
		String j = "";
		String[] jgmcs = {"�ܶ�","��֧��","�����","���ִ��"};
		ccbm = ccbm / 2 - 1;
		if(ccbm >=0 && ccbm < jgmcs.length){
			j = jgmcs[ccbm];
		}
		return j;
	}
	
	
	public static String getDate(String dateS, String dateE){
		String date = null;
		String[] datedesc = {"��","��","��","ʱ","��","��"};
		if(dateS != null && dateE != null && dateS.length() == dateE.length()){
			if (dateS.length() % 3 == 1) {
				int score = (dateS.length() - 4) / 3 + 1;
				int differ = -1;
				int index;
				dateS = dateS  + " ";
				dateE = dateE + " ";
				for (int i = 0; i < score; i++) {
					index = 4 + 3 * i;
					dateS = dateS.replaceFirst(dateS
							.substring(index, index + 1), datedesc[i]);
					dateE = dateE.replaceFirst(dateE
							.substring(index, index + 1), datedesc[i]);
					if(!dateS.substring(0,4+3*i).equals(dateE.substring(0,4+3*i))){
						if(differ != -1){
							continue;
						}
						differ = i;
					}
				}
				
				date = "";
				if(differ == -1){
					date = dateS;
				}else if(differ == 0){
					date = dateS + "��" + dateE;
				}else{
					date = dateS + "��" + dateE.substring(4 + 3 *(differ-1) + 1);
				}
			}
		}
		return date;
	}
	
}
