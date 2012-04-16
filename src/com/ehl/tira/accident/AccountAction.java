package com.ehl.tira.accident;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.ehl.tira.util.Chart;
import com.ehl.tira.util.Sql;
import com.ehl.tira.util.Table;
import com.ehl.tira.util.XML;
import com.ehl.tira.util.statanalysis.Department;
import com.ehl.tira.util.statanalysis.ServletUser;
import com.ehl.tira.util.statanalysis.StatAnalysis;

/**
 * �����������¹���������������ͳ�Ʒ���
 * 
 * @author xiayx
 * @date 2010-12-15
 */
public class AccountAction extends Controller {

	private Logger logger = Logger.getLogger(AccountAction.class);

	public ActionForward doAnalysis(Action action, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// ��������
		String dateSE = request.getParameter("dateSE");
		String diedPersonNum = request.getParameter("diedPersonNum");
		String deptId = request.getParameter("deptId");
		String deptName = request.getParameter("deptName");
		if (dateSE == null || diedPersonNum == null || deptId == null
				|| deptName == null) {
			logger.error(getLoggerStr() + "ҳ�洫�ݲ����б�ȫ");
			return null;
		}

		// ʱ��
		String dateOfTitle = "";
		String whereDate = " and sgfssj between to_date(to_char(sysdate,'yyyy";
		if (dateSE.equals("year")) {
			whereDate += "')||'-01'";
			dateOfTitle = "�������Ŀǰ";
		} else if (dateSE.equals("month")) {
			whereDate += "-mm')";
			dateOfTitle = "����������";
		} else {
			logger.error("�¹ʷ�������ֹʱ����ֵ���ڱ��뷶Χ֮�ڣ�");
			return null;
		}
		whereDate += "||'-01','yyyy-mm-dd') and sysdate";

		// ��������
		String diedPersonNumOfTitle = "";
		String whereDiedPersonNum = " and swrs >= "
				+ Integer.parseInt(diedPersonNum);
		if (diedPersonNum.equals("0")) {
			diedPersonNumOfTitle = "";
		} else {
			if ("35".indexOf(diedPersonNum) == -1) {
				logger.error("�¹ʷ���������������ֵ���ڱ��뷶Χ֮�ڣ�");
			} else {
				diedPersonNumOfTitle = "����" + diedPersonNum + "�˼�����";
			}
		}

		// ��������ɸѡ
		if (deptId.length() != 6) {
			logger.error("�¹ʷ�����������������ֵ����Ϊ" + deptId.length() + "λ��������6λ");
			return null;
		}
		String selStatItem = "substr(jgid,1,6)";
		String whereDept = "and";
		String gbDept = "";
		String addressOfTitle = "";
		if (!deptId.substring(4).equals("00")) {
			whereDept += " xzqh='" + deptId + "'";
			gbDept = ", xzqh";
		} else if (!deptId.substring(2, 4).equals("00")) {
			whereDept += " substr(jgid,1,4)='" + deptId.substring(0, 4) + "'";
			gbDept = " substr(acddept.jgid,1,6)";
			addressOfTitle = deptName + "��Ͻ�����";
		} else {
			whereDept += " substr(jgid,1,2)='" + deptId.substring(0, 2) + "'";
			gbDept = "substr(acddept.jgid,1,4)||'00'";
			addressOfTitle += deptName + "��Ͻ��֧��";
		}
		selStatItem = gbDept;

		// Ĭ��
		String selDataItem = ", decode(count(1),0,null,count(1)), sum(swrs)";
		String whereRef = " acd.lh=acddept.lh";
		// ����sql���ƴװ
		String sql = Sql.select(
				"S_ACD_C_ACDFILE acd, T_OA_ROADDEPARTMENT acddept", selStatItem
						+ selDataItem, whereRef + whereDate
						+ whereDiedPersonNum + whereDept, gbDept, gbDept);

		System.out.println("AccountAction��sql:" + sql);
		logger.debug("�¹ʷ�������������sql���:" + sql);

		String[] colDescs = { "��������\\����", "�¹�(��)", "����(��)" };
		Object[][] data = DBHandler.getMultiResult(sql);
		if (data == null) {
			logger.error("�¹ʷ�������������Ϊnull�����������ݻ�sql���ƴ�Ӵ���");
			return null;
		}

		// ��������
		Object[][] statItemValues = DBHandler.getMultiResult(Department.getSql(
				deptId, false));
		Map<String, String> statItemValueM = StatAnalysis
				.changeArrayMap(statItemValues);
		for (int i = 0; i < data.length; i++) {
			data[i][0] = statItemValueM.get(data[i][0]);
		}
		// String[][] initData = StatAnalysis.initDataTable(statItemValues,
		// colDescs);
		// data = StatAnalysis.fillDataTable(initData, data);

		String[][] dataChange = Table.changeRC(data);

		// ȡ���ܼ�
		float[] acounts = Table.getRowTotalAcount(dataChange);
		String[] rowOfData = new String[acounts.length];
		rowOfData[0] = deptName;
		for (int i = 1; i < rowOfData.length; i++) {
			rowOfData[i] = acounts[i - 1] + "";
		}

		// ƴxml
		String title = dateOfTitle + addressOfTitle + diedPersonNumOfTitle
				+ "�¹���������������";
		String barTitle = title + "ͳ����״ͼ";
		String singleBarTitle = dateOfTitle + deptName + diedPersonNumOfTitle
				+ "ͳ����״ͼ";
		String dataTitle = title + "ͳ�����ݱ��";
		String barChartDataXml = Chart.getMultiBarChartData(barTitle,
				dataChange, colDescs);
		String singleBarChartDataXML = "";
		if (deptId.substring(2).equals("0000")) {
			singleBarChartDataXML = Chart.getSingleBarChartData(singleBarTitle,
					rowOfData, colDescs);
		}
		String tableXML = Table.getTableData(dataTitle, colDescs, data,
				dataChange, false);
		String xml = XML.getXML(barChartDataXml + singleBarChartDataXML
				+ tableXML);
		System.out.println("AccountAction��xml:" + xml);
		ServletUser.doOut(request, response, xml);

		return null;
	}

	public static String getLoggerStr() {
		return "�ۺϷ���-�¹ʷ���-�����������¹���������������";
	}

}
