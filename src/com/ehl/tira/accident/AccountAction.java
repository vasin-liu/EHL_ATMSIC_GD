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
 * 行政区划和事故起数、死亡人数统计分析
 * 
 * @author xiayx
 * @date 2010-12-15
 */
public class AccountAction extends Controller {

	private Logger logger = Logger.getLogger(AccountAction.class);

	public ActionForward doAnalysis(Action action, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// 参数处理
		String dateSE = request.getParameter("dateSE");
		String diedPersonNum = request.getParameter("diedPersonNum");
		String deptId = request.getParameter("deptId");
		String deptName = request.getParameter("deptName");
		if (dateSE == null || diedPersonNum == null || deptId == null
				|| deptName == null) {
			logger.error(getLoggerStr() + "页面传递参数列表不全");
			return null;
		}

		// 时间
		String dateOfTitle = "";
		String whereDate = " and sgfssj between to_date(to_char(sysdate,'yyyy";
		if (dateSE.equals("year")) {
			whereDate += "')||'-01'";
			dateOfTitle = "今年截至目前";
		} else if (dateSE.equals("month")) {
			whereDate += "-mm')";
			dateOfTitle = "本月至今天";
		} else {
			logger.error("事故分析，起止时间数值不在编码范围之内！");
			return null;
		}
		whereDate += "||'-01','yyyy-mm-dd') and sysdate";

		// 死亡人数
		String diedPersonNumOfTitle = "";
		String whereDiedPersonNum = " and swrs >= "
				+ Integer.parseInt(diedPersonNum);
		if (diedPersonNum.equals("0")) {
			diedPersonNumOfTitle = "";
		} else {
			if ("35".indexOf(diedPersonNum) == -1) {
				logger.error("事故分析，死亡人数数值不在编码范围之内！");
			} else {
				diedPersonNumOfTitle = "死亡" + diedPersonNum + "人及以上";
			}
		}

		// 交警机关筛选
		if (deptId.length() != 6) {
			logger.error("事故分析，行政区划代码值长度为" + deptId.length() + "位，而不是6位");
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
			addressOfTitle = deptName + "所辖各大队";
		} else {
			whereDept += " substr(jgid,1,2)='" + deptId.substring(0, 2) + "'";
			gbDept = "substr(acddept.jgid,1,4)||'00'";
			addressOfTitle += deptName + "所辖各支队";
		}
		selStatItem = gbDept;

		// 默认
		String selDataItem = ", decode(count(1),0,null,count(1)), sum(swrs)";
		String whereRef = " acd.lh=acddept.lh";
		// 数据sql语句拼装
		String sql = Sql.select(
				"S_ACD_C_ACDFILE acd, T_OA_ROADDEPARTMENT acddept", selStatItem
						+ selDataItem, whereRef + whereDate
						+ whereDiedPersonNum + whereDept, gbDept, gbDept);

		System.out.println("AccountAction，sql:" + sql);
		logger.debug("事故分析，数据搜索sql语句:" + sql);

		String[] colDescs = { "交警机关\\数量", "事故(宗)", "死亡(人)" };
		Object[][] data = DBHandler.getMultiResult(sql);
		if (data == null) {
			logger.error("事故分析，数据数组为null，可能无数据或sql语句拼接错误！");
			return null;
		}

		// 处理数据
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

		// 取得总计
		float[] acounts = Table.getRowTotalAcount(dataChange);
		String[] rowOfData = new String[acounts.length];
		rowOfData[0] = deptName;
		for (int i = 1; i < rowOfData.length; i++) {
			rowOfData[i] = acounts[i - 1] + "";
		}

		// 拼xml
		String title = dateOfTitle + addressOfTitle + diedPersonNumOfTitle
				+ "事故宗数、死亡人数";
		String barTitle = title + "统计柱状图";
		String singleBarTitle = dateOfTitle + deptName + diedPersonNumOfTitle
				+ "统计柱状图";
		String dataTitle = title + "统计数据表格";
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
		System.out.println("AccountAction，xml:" + xml);
		ServletUser.doOut(request, response, xml);

		return null;
	}

	public static String getLoggerStr() {
		return "综合分析-事故分析-行政区划和事故起数、死亡人数";
	}

}
