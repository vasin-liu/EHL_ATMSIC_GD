package com.ehl.dynamicinfo.tgs.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dynamicinfo.tgs.core.HistoryFlowAvgCore;

public class tgsAllAction extends Controller {
	Logger logger = Logger.getLogger(tgsAllAction.class);
	
	private HistoryFlowAvgCore historyAvg = new HistoryFlowAvgCore();
	/**
	 * ��ǰʱ���Ƿ������
	 * @return ��/��
	 */
	private static boolean isInZero(){
		String hour = new SimpleDateFormat("H").format(Calendar.getInstance().getTime());
		return hour.equals("0");
	}
	
	/**
	 * ��ȡ����sql���
	 * @param kkmc ��������
	 * @param delay ����ƫ��
	 * @return ����sql���
	 */
	private static String getTfmSql(String kkmc, String delay){
		//ɸѡ���� 1~23
		String siftToday = "trunc(c.datetime, 'dd') = trunc(sysdate + " + delay//to_date('2010-07-07','yyyy-mm-dd')
				+ ", 'dd')" + " and to_char(c.datetime, 'hh24') != '00'";
		//ɸѡ����0��
		delay = String.valueOf((Integer.parseInt(delay) + 1));
		String siftTomorrow = "trunc(c.datetime, 'dd') = trunc(sysdate + "
				+ delay + ", 'dd')" + " and to_char(c.datetime, 'hh24') = '00'";
		//ɸѡ��������
		String siftDate = "((" + siftToday + ") or (" + siftTomorrow + "))";
		//����sql���
		StringBuffer sql = new StringBuffer();
		sql.append("select to_char(c.datetime, 'hh24') sj")
		.append(",decode(instr(b.bianma, 1, 1), '1', sum(c.volume), 0) rc")//���
		.append(",decode(instr(b.bianma, 1, 1), '2', sum(c.volume), 0) cc")//����
		.append(",sum(c.volume) sx")//˫��
		.append(",decode(instr(b.bianma, 1, 1), '1',1,0) rcnum")//��Ǽ���
		.append(",decode(instr(b.bianma, 1, 1), '2',1,0) ccnum");//���Ǽ���
		sql.append(" from t_road_seginfo a, t_tfm_driveway b, t_tfm_driveway_1h_flow c");
		sql.append(" where a.roadsegid = b.roadsegid and b.id = c.id")
		.append(" and a.roadsegname like '%"+kkmc+"%'")
		.append(" and instr(b.bianma, 1, 1) in ('1', '2')")
		.append(" and "+siftDate);
		sql.append(" group by c.datetime, b.bianma");
		String temp = sql.toString();
		sql.setLength(0);
		sql.append("select sj, sum(rc), sum(cc), sum(sx),sum(rcnum),sum(ccnum)");
		sql.append(" from (" + temp + ")");
		sql.append(" group by sj");
		sql.append(" order by decode(sj,'00',1,2),sj desc");
		return sql.toString();
	}
	
	/**
	 * ��ȡ��������
	 * @param tgsName ��������
	 * @return ��������
	 */
	private static String getTgsName(String tgsName){
		String sql = "select roadsegname  from t_road_seginfo where roadsegname like '%"+tgsName+"%' ";
		Object[] data = DBHandler.getLineResult(sql);
		if(data == null || data.length != 1){
			return tgsName;
		}
		return String.valueOf(data[0]);
	}
	

	/**
	 * ��ȡ��������
	 * 
	 * @param kkmc
	 *            ��������
	 * @param flag
	 *            ��־
	 * @return ��������
	 */
	private static Object[][] getTfm(String kkmc,String flag) {
		if (kkmc == null || flag == null) {
			return null;
		}
		return DBHandler.getMultiResult(getTfmSql(kkmc, flag));
	}
	
	/**
	 * ���㵱�����������Լ�����������ЧСʱ��<br/>
	 * @param tfm
	 * @return �����ܼƽ��
	 */
	private static int[] getTotalTfm(Object[][] tfm) {
		int inTotalTfm = 0, outTotalTfm = 0;
		int inCount = 0, outCount = 0;
		for (int i = 0; i < tfm.length; i++) {
			inTotalTfm += Integer.valueOf(String.valueOf(tfm[i][1]));
			outTotalTfm += Integer.valueOf(String.valueOf(tfm[i][2]));
			inCount += Integer.valueOf(String.valueOf(tfm[i][4]));
			outCount += Integer.valueOf(String.valueOf(tfm[i][5]));
		}
		return new int[] { inTotalTfm, outTotalTfm, inCount, outCount };
	}
	
	/**
	 * ��ȡ��������ֵ���������������<br>
	 * ������ͬʱ������ֵ�����
	 * @param tfm ����
	 * @return Сʱ���������������������˫������
	 * @author xiayx
	 */
	private static String[] getMaxTfm(Object[][] tfm){
		if(tfm == null){
			return null;
		}
		int totalTfm, maxInTfm = 0, maxOutTfm = 0, maxTotalTfm = 0;
		String date = "";
		for (int i = 0; i < tfm.length; i++) {
			totalTfm = Integer.parseInt(String.valueOf(tfm[i][3]));
			if(totalTfm >= maxTotalTfm){
				maxTotalTfm = totalTfm;
				maxInTfm = Integer.parseInt(String.valueOf(tfm[i][1]));;
				maxOutTfm = Integer.parseInt(String.valueOf(tfm[i][2]));
				date = (String)tfm[i][0];
			}
		}
		return new String[]{date,String.valueOf(maxInTfm),String.valueOf(maxOutTfm),String.valueOf(maxTotalTfm)};
	}

	/**
	 * ��Сʱ����һ
	 * @param hour Сʱ
	 * @return ��һ���Сʱ��
	 * @author xiayx
	 */
	private static String minusOneHour(String hour){
		int hourInt;
		try {
			hourInt = Integer.parseInt(hour);
		} catch (NumberFormatException e) {
			return hour;
		}
		if(hourInt < 0 || hourInt > 23){
			return hour;
		}
		hourInt--;
		if(hourInt == -1){
			hourInt = 23;
		}
		hour = String.valueOf(hourInt);
		if(hourInt < 10){
			hour = "0" + hour;
		}
		return hour;
	}
	
	/**
	 * ��ȡ������Ϣ<br/>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetTgsInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
	
		//��ȡ���͹����ľ�γ����Ϣ
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		String tgsName = StringHelper.obj2str(request.getParameter("tgsName"), "");
		String camID = StringHelper.obj2str(request.getParameter("GLID"), "");
		String ssxt = StringHelper.obj2str(request.getParameter("SSXT"), "");
		// "0" : ���� "-1":ǰһ�� "-2":ǰ����
		String searchDayFly = StringHelper.obj2str(request.getParameter("searchDayFly"), "0");
		if ("0".equals(searchDayFly)) {
			searchDayFly = "-0"; 
		}
		
		
		Calendar calendar = Calendar.getInstance();
		boolean isInZero = isInZero();
		if(isInZero){
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			searchDayFly = "-1";
		}
		String date = new SimpleDateFormat("yyyy��MM��dd��").format(calendar.getTime());
		
		// ������ʾ��Ϣ
		StringBuffer sbXml = new StringBuffer();
		try {
			// ����
			Object[][] tfm = getTfm(tgsName, searchDayFly);
			if (tfm != null) {
				//Modify by Xiayx 2011-9-30
				//���ʱ��ĳɼ������
				sbXml.append("<table class='popup-contents' style='margin-top:10px ;' width='100%' cellSpacing='0' cellPadding='0' border='0'>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>�������ƣ�</td><td width='200px' style='word-wrap: break-word; word-break: break-all;' >" + getTgsName(tgsName) + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>������ڣ�</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>" + date + "</td></tr>");
				//Modification finished
				
				//Modify by Xiayx 2011-9-30
				//��ǰ�������
				String hour = (String)tfm[0][0];
				sbXml.append("<tr height='10px;'><td colspan='2'></td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>��&nbsp;ǰ&nbsp;��&nbsp;��&nbsp;��&nbsp;�⣺</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>��ʡ���� &nbsp; " + tfm[0][1] + "��/Сʱ" + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' valign='top' rowspan='2'>��"+minusOneHour(hour)+":00~"+hour+":00��</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>��ʡ���� &nbsp; " + tfm[0][2] + "��/Сʱ" + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>˫ &nbsp; &nbsp; &nbsp;�� &nbsp; " + tfm[0][3]  + "��/Сʱ" + "</td></tr>");
				//Modification finished
				
				//Modify by Xiayx 2011-9-30
				//�����ۼ�������ƽ������
				int [] totalTfm = getTotalTfm(tfm);
				sbXml.append("<tr height='10px;'><td colspan='2'></td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>�����ۼ�������</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>��ʡ���� &nbsp; " + totalTfm[0]  + "��/��" + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' valign='top' rowspan='2'>������"+hour+":00��</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>��ʡ���� &nbsp; " + totalTfm[1]  + "��/��" + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>˫ &nbsp; &nbsp; &nbsp;�� &nbsp; " + (totalTfm[0]+totalTfm[1])  + "��/��" + "</td></tr>");
				//ÿСʱƽ������
				int inAvgTfm = totalTfm[2] == 0 ? 0 : totalTfm[0]/totalTfm[2];
				int outAvgTfm = totalTfm[3] == 0 ? 0 : totalTfm[1]/totalTfm[3];
				sbXml.append("<tr height='10px;'><td colspan='2'></td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' valign='top' rowspan='2'>����ÿСʱƽ��������</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>��ʡ���� &nbsp; " + inAvgTfm  + "��/Сʱ" + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>��ʡ���� &nbsp; " + outAvgTfm  + "��/Сʱ" + "</td></tr>");
				//������߷�����
				String[] maxTfm = getMaxTfm(tfm);
				if(maxTfm != null){
					sbXml.append("<tr height='10px;'><td colspan='2'></td></tr>");
					sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>������߷�������</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>˫ &nbsp; &nbsp; &nbsp;�� &nbsp; " + maxTfm[3]  + "��/ʱ" + "</td></tr>");
					sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' valign='top' rowspan='2'>��"+minusOneHour(maxTfm[0])+":00~"+maxTfm[0]+":00��</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>��ʡ���� &nbsp; " + maxTfm[1]  + "��/ʱ" + "</td></tr>");
					sbXml.append("<tr style='padding:1px 1px 1px 1px'><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>��ʡ���� &nbsp; " + maxTfm[2]  + "��/ʱ" + "</td></tr>");
				}
				//Modification finished
				
				//Modify by Xiayx 2011-12-21
				//�����ʷƽ��ֵ������
				String[] growths = historyAvg.getGrowth(tgsName, hour, Integer.parseInt(String.valueOf(tfm[0][1])), Integer.parseInt(String.valueOf(tfm[0][2])));
				sbXml.append("<tr height='10px;'><td colspan='2'></td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>�Ա���ʷƽ��ֵ��</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>��ʡ���� &nbsp; " + growths[0]  + "��"+growths[3]+"��</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' valign='top'>����ǰСʱ������</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>��ʡ���� &nbsp; " + growths[1]  + "��"+growths[4]+"��</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' valign='top'></td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>˫ &nbsp; &nbsp; &nbsp;�� &nbsp; " + growths[2]  + "��"+growths[5]+"��</td></tr>");
				//Modify finished
				
				//Modify by Xiayx 2011-9-30
				//��Ƶ���ӣ��ӿ���
				sbXml.append("<tr height='10px;'><td colspan='2'></td></tr>");
				//id='tdVidicon'����jsȨ�޿�����ʹ�õ�
				sbXml.append("<tr id='tdVidicon' style='padding:1px 1px 1px 1px'><td></td><td><input type='image' src='../../../cctv/image/button/btnvideoplay.gif' align='right' onClick=\"vidiconmap.showVideo('"+ camID+"','play','"+ ssxt +"');\"/></td></tr>");
				//Modification finished
				sbXml.append("<tr  style='padding:1px 1px 1px 1px'><td colspan='2'>&nbsp;</td></tr>");
				sbXml.append("</table>");
			}else{
				sbXml.append("<table class='popup-contents' style='margin-top:10px ;' width='100%' cellSpacing='0' cellPadding='0' border='0'>");
				//Modify by zhoucr 2011-12-26  ���˴���ִ�ڵ��������Ϣ
				if (!tgsName.substring(0,5).equals("����ִ�ڵ�")){
				    sbXml.append("<tr style='padding:5 0'><td></td><td >δȡ�øÿ���������Ϣ��</td></tr>");
				}
				sbXml.append("<tr id='tdVidicon' style='padding:1px 1px 1px 1px'><td></td><td><input type='image' src='../../../cctv/image/button/btnvideoplay.gif' align='right' onClick=\"vidiconmap.showVideo('"+ camID+"','play','"+ ssxt +"');\"/></td></tr>");
				sbXml.append("</table>");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	
		PrintWriter out = response.getWriter();
	
		out.write(sbXml.toString());
		out.close();
		return null;
	}

	/**
	 * ��ȡȫ��������Ϣ<br/>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAllTgsInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
	throws Throwable {
		
		//��ȡ���͹����ľ�γ����Ϣ
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		// ��������
		String sql1 = " SELECT SID, CPMC, LONGITUDE, LATITUDE,GLID,SSXT,SYZT FROM ATMS_EQUIPMENT_ZB " + 
						" WHERE SSXT = 'TGS' AND LONGITUDE != 0 AND LATITUDE != 0 ORDER BY SID ASC"; 
		// ȡ�ý�������
		Object[][] oResult2 = DBHandler.getMultiResult(sql1);
		String str = DataToXML.objArrayToXml(oResult2);
		out.write(str);
		out.close();
		return null;
	}
	
	/**
	 *����ǰ���ڼӼ�n������ �紫���ַ���"-5" ��Ϊ����ǰ���ڼ�ȥ5������� 
	 * �紫���ַ���"5" ��Ϊ����ǰ���ڼ���5�������� �����ִ�
	 * ��(1999-02-03) 
	 * @param to
	 * @return
	 */
	public static String dateAdd(String to) {
		// ���ڴ���ģ�� (�����ڼ���ĳЩ����ȥ����)�����ַ���
		int strTo;
		try {
			strTo = Integer.parseInt(to);
		} catch (Exception e) {
			System.out.println("���ڱ�ʶת������! : \n:::" + to + "����תΪ������");
			e.printStackTrace();
			strTo = 0;
		}
		Calendar strDate = Calendar.getInstance(); // java.util��,���õ�ǰʱ��
		strDate.add(Calendar.DATE, strTo); // ���ڼ� ����������Ὣ�±䶯 //���� (��-��-��)
		String meStrDate = strDate.get(Calendar.YEAR) + "-"
				+ String.valueOf(strDate.get(Calendar.MONTH) + 1) + "-"
				+ strDate.get(Calendar.DATE);
		return meStrDate;
	}

	public static void main(String[] args) {
		//System.out.println("dsd");
	}
}
