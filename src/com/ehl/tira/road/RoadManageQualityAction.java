package com.ehl.tira.road;

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
import com.ehl.tira.util.statanalysis.Parameter;
import com.ehl.tira.util.statanalysis.ServletUser;

public class RoadManageQualityAction extends Controller {

	private Logger logger = Logger.getLogger(RoadManageQualityAction.class);

	public ActionForward doAnalysis(Action action, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			// ����
			String[] params = { "dateStart", "dateEnd", "roadCode", "roadDesc",
					"roadLevel", "analysisTargetCode", "analysisTargetDesc",
					"statType" };
			Map<String, String> paramM = ServletUser.getParameters(request,
					params);
			// String emsg = null;
			if (Parameter.isNonMapWithNonObj(paramM) == false) {
				return null;
			}

			// sqlĬ�����ֵ
			String selStatItem = "roadname/jgmc";
			String selFloatItem = "";
			String selDataItems = "sum(xqfw),sum(policestrength),sum(flowaccount)"
					+ "/xqfw,policestrength,flowaccount";
			String from = "T_TIRA_ROAD_DEPT_ACC_ALARM rdaa, T_OA_ROADDEPARTMENT rd";
			String whereRef = " rdaa.rdid=rd.rdid";
			String whereDate = "rdaa.dayid";
			String whereRoad = "rd.roadid";
			String whereRoadLevel = "rdaa.roadlevel";
			String wherePS = "d.policeStrength";// wherePoliceStrength
			String gbStatItem = "roadname/jgmc";
			String obStatItem = "roadname/jgmc";
			// ����Ĭ�����ֵ
			String titleDate = "";
			String titleAddress = "";
			String titleContent = "����Чͳ��";
			// String titleChart = "";

			// ����ָ��
			String[] choiceDataItems = { "rdaa.accaccount", "rdaa.swrsaccount",
					"rdaa.alarmaccount" };
			String choiceDataItem = choiceDataItems[Integer.parseInt(paramM
					.get("analysisTargetCode"))];

			// ʱ��
			whereDate = " and " + Sql.getWhereDate("rdaa.dayid", paramM.get("dateStart"),
					paramM.get("dateEnd"), 3);
			titleDate = paramM.get("dateStart") + "��" + paramM.get("dateEnd");

		
			// ��·�ȼ�
			whereRoadLevel = " AND rdaa.roadlevel=" + paramM.get("roadLevel");
			if(paramM.get("roadLevel").equals("0")){
				whereRoad = " AND instr(rd.roadid,'_')=0";
			} else if(paramM.get("roadLevel").equals("1")){
				whereRoad = "";
			} else if(paramM.get("roadLevel").equals("2")){
				whereRoad = "";
			} else {
				System.out.println("roadLevel����ָ����Χ֮�ڣ�"); 
				return null;
			}
			
			//��·
			long days = Sql.dateDiffer(paramM.get("dateStart"), paramM
					.get("dateEnd"), Sql.getDFFull(2))+1;
			
			if (paramM.get("roadCode").equals("")) {
				if (paramM.get("statType").equals("roadName")) {
					selStatItem = "rdaa.roadname";
					selDataItems = ", round(sum(rd.xqfw)/" + days + ",3)"
							+ ", round(sum(rd.policestrength)/" + days
							+ "), round(sum(rdaa.flowaccount)/" + days
							+ "), round(sum(" + choiceDataItem + ")/" + days
							+ ")";
					whereRoad += "";
					wherePS = "";
					gbStatItem = "rdaa.roadname";
					obStatItem = "";
					titleAddress = "ȫʡ���ٹ�·";
				} else if (paramM.get("statType").equals("jgmc")) {
					selStatItem = ", replace(replace(d.jgmc,'����',''),'��·','')";
					selDataItems = ", round(sum(d.manageMileage)/" + days
							+ ",3)" + ", round(sum(d.policeStrength )/" + days
							+ "), round(sum(rdaa.flowaccount)/" + days
							+ "), round(sum(" + choiceDataItem + ")/" + days
							+ ")";
					from += ", T_SYS_DEPARTMENT d";
					whereRef += " AND d.jgid=rd.jgid";
					whereRoad = "";
					wherePS = " AND d.policeStrength is not null";
					gbStatItem = ", d.jgmc";
					obStatItem = "";
					titleAddress = "ȫʡ���ٹ�·���";
				}
			} else {
				selStatItem = " replace(replace(rdaa.jgmc,'����',''),'��·','')";
				selDataItems = ", round(sum(rd.xqfw)/" + days + ",3)"
						+ ", round(sum(rd.policestrength)/" + days
						+ "), round(sum(rdaa.flowaccount)/" + days
						+ "), round(sum(" + choiceDataItem + ")/" + days + ")";
				whereRoad += "  AND rd.roadid='" + paramM.get("roadCode") + "'";
				wherePS = "";
				gbStatItem = " rdaa.jgmc";
				obStatItem = "";
				titleAddress = paramM.get("roadDesc");
			}
			// ͳ�����

			// ����
			// //sql���ƴװ
			String sql = Sql.select(from, selStatItem + selFloatItem + selDataItems,
					whereRef + whereDate + whereRoad + whereRoadLevel
							+ wherePS, gbStatItem, obStatItem);
			System.out.println("sql:" + sql);
			Object[][] data = DBHandler.getMultiResult(sql);
			if (data == null) {
				logger.error(getLoggerStr("?") + "δȡ������");
				return null;
			}
			
			// //�����������������������
			String[] selDataItemDescs = { "�¹�(��)", "����(��)", "ӵ��(��)" };
			String[] roadLevels = { "���ٹ�·", "����", "ʡ��" };
			String roadLevel = roadLevels[Integer.parseInt(paramM
					.get("roadLevel"))];
			String[] dataItemDescs = {
					roadLevel + "\\��Чָ��",
					"�����(ǧ��)",
					"����(��)",
					"�վ�����(��)",
					"�վ�"
							+ selDataItemDescs[Integer.parseInt(paramM
									.get("analysisTargetCode"))] };

			String[][] dataChange = Table.changeRC(data);

			// ����
			String title = titleDate + titleAddress + titleContent;
			String barTitle = title + "��״ͼ";
			String dataTitle = title + "���ݱ��";

			String barChartDataXML = Chart.getMultiBarChartData(barTitle,
					dataChange, dataItemDescs);
			String tableXML = Table.getTableData(dataTitle, dataItemDescs,
					data, dataChange, false);
			String xml = XML.getXML(barChartDataXML + tableXML);
			System.out.println("xml:" + xml);
			ServletUser.doOut(request, response, xml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getLoggerStr(String roadLevel) {
		return "�ۺϷ���-��·����-" + roadLevel + "�����ߴ�ӣ�";
	}



}
