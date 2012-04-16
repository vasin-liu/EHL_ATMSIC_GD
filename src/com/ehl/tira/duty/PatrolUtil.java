package com.ehl.tira.duty;

import java.util.ArrayList;
import java.util.List;

/**
 * PatrolAverageMileageActionѲ��ƽ�����
 * @author xiayouxue
 *
 */
public class PatrolUtil {
	
	/*
	�������ݣ�
		�У��У�����
	��ϵ�б���
	��ʽ1:
		����\������\�·�  һ�� 
		����                                 100				
		����                                 200
	��ʽ2��	
		����\������\�·�  һ��  ����
		����                                 100	 150
	��ʽ3��	
		����\������,������\�·�  һ�� 
		����                                                 100,200	
	��ʽ3����1��			
		����\�������         ������         ������
		����                                    100     200	
	��ʽ3����2��
		�������\�·�  һ�� 
		������                      100                   
		������		  200
	-------------------------------	
	��ϵ�б���
	��ʽ1:
		����\������\�·�  һ��  ����
		����                                 100	 150			
		����                                 200  250
	��ʽ2��	
		����\������,������\�·�  һ��
		����                                                 100,200			
		����                                                 150,250
	��ʽ2����1��			
		����\�������                      ������         ������
		����                                                 100      200			
		����                                                 150      250			
	��ʽ3��	
		����\������,������\�·�     һ��              ����
		����                                                 100,200  150,250	
	��ʽ3����1��		
		�������\�·�     һ��              ����
		������                      100       150                  
		������                      200		250
	��ʽ3����1����1��		
		�·�\�������         ������  ������       
		һ��                                100     200                  
		 ����                               150		250		
	-------------------------------	
	�����ͱ���
		����\������,������\�·�  һ��		����
		����                                                 100,200	100,200		
		����                                                 150,250	150,250
	*/
	
	
	/**
	 * ��Object���Ͷ�ά����ת��ΪString�Ͷ�ά����
	 * @param data Object���Ͷ�ά����
	 * @return String�Ͷ�ά����
	 */
	public static String[][] changeData(Object[][] data){
		String[][] dataStr = null;
		if(data != null){
			dataStr = new String[data.length][];
			for (int i = 0; i < data.length; i++) {
				dataStr[i] = new String[data[i].length];
				for (int j = 0; j < data[i].length; j++) {
					if(data[i][j] == null){
						return null;
					}
					dataStr[i][j] = String.valueOf(data[i][j]);
				}
			}
		}
		return dataStr;
	}
	
	/**
	 * ����������ת��Ϊ��������</br>
	 * <pre>
	 * --��������--
	 * ʡ��	�·�	 ������
	 * ����    һ��     100
	 * ����    ����     200
	 * ����    һ��     150
	 * ����    ����     250
	 * 
	 * --��������--
	 * ʡ��\�·�		һ��		���� 
	 * ����		100		200
	 * ����		150		250
	 * colTitle:һ��  ����
	 * colNum:2(һ��	  ����)
	 * rowTitle: ����  ����
	 * rowNum:2(����	  ����)
	 * </pre> 
	 * @param dataIn ��������
	 * @return ��������
	 */
	public static String[][] changeData(String[][] dataIn){
		String[][] dataOut = null;
		if(dataIn != null && dataIn.length != 0 && dataIn[0].length == 3){
			//--<��ȡ��������
			String rowTitle = dataIn[0][0];
			if(rowTitle == null){
				return null;
			}
			List<String> colTitleL = new ArrayList<String>();
			for(int i=1;i<dataIn.length;i++){
				if(!rowTitle.equals(dataIn[i][0])){
					colTitleL.add((String)dataIn[i][1]);
					break;
				}
			}
			//-->
			//��ȡ������colNum
			int colNum = colTitleL.size();
			//��ȡ������rowNum
			int rowNum = dataIn.length / colNum;
			//--<��ȡ�������ݶ�ά����
			dataOut = new String[rowNum+1][colNum+1];
			for(int i=0;i<colNum;i++){
				dataOut[0][i+1] = colTitleL.get(i);
			}
			for(int i=0;i<rowNum;i++){
				dataOut[i+1][0] = dataIn[i*colNum][0];
				for (int j = 0; j < colNum; j++) {
					dataOut[i+1][j+1] = dataIn[i*colNum+j][2];
				}
			}
			//-->
		}
		return dataOut;
	}
	
	/**
	 * �����ݼ����б���
	 * @param data ���ݣ���������û���б���
	 * @param colTitle �б���
	 * @return ��ӹ��б��������
	 */
	public static String[][] changeData(String[][] data, String[] colTitle){
		String[][] dataRe = null;
		if(data != null && colTitle != null){
			dataRe = new String[data.length +1][colTitle.length];
			dataRe[0] = colTitle;
			System.arraycopy(data, 0, dataRe, 1, data.length);
		}
		return dataRe;
	}
	
	/**
	 * ���ɱ�ͼXML����
	 * @param data
	 * @return
	 */
	public static String pie(String[][] data){
		String chartXML = null;
		
		return chartXML;
	}
	
	/**
	 * ���ɵ�ϵ����ͼXML����
	 * @param data
	 * @return
	 */
	public static String lineSingle(String[][] data){
		String chartXML = null;
		
		return chartXML;
	}
	
	/**
	 * ���ɶ�ϵ����ͼXML����
	 * @param data
	 * @return
	 */
	public static String lineMulti(String[][] data){
		String chartXML = null;
		
		return chartXML;
	}
	
	/**
	 * ���ɵ�ϵ��״ͼXML����
	 * @param data
	 * @return
	 */
	public static String columnSingle(String[][] data){
		String chartXML = null;
		
		return chartXML;
	}
	
	/**
	 * ���ɶ�ϵ��״ͼXML����
	 * @param data
	 * @return
	 */
	public static String columnMulti(String[][] data){
		String chartXML = null;
		
		return chartXML;
	}
	
	/**
	 * ���ɵ�ϵ��״ͼXML����
	 * @param data
	 * @return
	 */
	public static String stackSingle(String[][] data){
		String chartXML = null;
		
		return chartXML;
	}
	
	/**
	 * ���ɶ�ϵ��״ͼXML����
	 * @param data
	 * @return
	 */
	public static String stackMulti(String[][] data,String[] selects, boolean isRow){
		String chartXML = null;
		/*2008��3~4 ȫ������
		����\������,������\�·�     һ��		����
		����                                                 100,200	100,200		
		����                                                 150,250	150,250
		*/
		if (data != null && data.length >= 3 && data[0].length >= 3
				&& selects != null && selects.length == 2) {
			//--<��ȡ����
			String title = data[0][0];
			String[] dataItems;
			if(title != null){
				String[] items = title.split(":");
				if(items.length == 3){
					dataItems = items[1].split(",");
					if(dataItems.length >= 2){
						title = selects[0] + items[2] + selects[1] + items[0] + items[1].replace(",", "��") + "ͳ��ͼ";
					}else{
						return null;
					}
				}else{
					return null;
				}
			}else{
				return null;
			}
			//-->
			//--<��ȡ<categories>Ԫ��
			String categories = "";
			String[] colTitles = data[0];
			for (int i = 1; i < colTitles.length; i++) {
				categories += "<category label=\""+colTitles[i]+"\" />";
			}
			categories = "<categories>" + categories + "</categories>";
			//-->
			//--<
			String[] datasetss = new String[dataItems.length];
			String datasets = "";
			for (int i = 1; i < data.length; i++) {
				for (int j = 0; j < dataItems.length; j++) {
					datasetss[j] += "<dataset seriesName=\""+data[i][0]+dataItems[j]+"\" >";
					for (int k = 1; k < colTitles.length; k++) {
						datasetss[j] += " <set value=\""+data[i][k].split(",")+"\" />";
					}
					datasets += "</dataset>";
				}
			}
			datasets = "<dataset>" + datasets + "</dataset>";
			//-->
			
		}
		return chartXML;
	}
	
	
	
	/**
	 * ȡ�õ��б���XML����
	 * @param data
	 * @param title
	 * @return
	 */
	public static String columnSingleCol(String[][] data, String title){
		String chartXML = null;
		if(data != null && data.length == 2 && data[0].length > 1 && title != null){
			chartXML = "";
			for (int i = 1; i < data[1].length; i++) {
				chartXML += "\t<set label=\""+data[0][i]+"\" value=\""+data[1][i]+"\" />\n";
			}
			String[] items;
			if(data[0][0] == null){
				items = new String[]{"",""};
			}else{
				items = data[0][0].split(",");
				if(items.length != 2){
					items = new String[]{"",""};
				}
			}
			chartXML = "<chart caption=\""+title+"\" xAxisName=\""+items[0]+"\" yAxisName=\""+items[1]+"\" showValues=\"0\" decimals=\"1\" formatNumberScale=\"0\" chartType=\"columnSingle\" >\n" 
				+ chartXML + 
				"</chart>";
		}
		return chartXML;
	}
	
	/**
	 * ȡ�õ��б���XML����
	 * @param data
	 * @param title
	 * @return
	 */
	public static String columnSingleRow(String[][] data, String title){
		String chartXML = null;
		if(data != null && data.length > 1 && data[0].length == 2 && title != null){
			chartXML = "";
			for (int i = 1; i < data.length; i++) {
				chartXML += "\t<set label=\""+data[i][0]+"\" value=\""+data[i][1]+"\" />\n";
			}
			String[] items;
			if(data[0][0] == null){
				items = new String[]{"",""};
			}else{
				items = data[0][0].split(",");
				if(items.length != 2){
					items = new String[]{"",""};
				}
			}
			chartXML = "<chart caption=\""+title+"\" xAxisName=\""+items[0]+"\" yAxisName=\""+items[1]+"\" showValues=\"0\" decimals=\"1\" formatNumberScale=\"0\">\n" 
				+ chartXML + 
				"</chart>";
		}
		return chartXML;
	}
	
	/**
	 * ȡ�ö��б���XML���ݣ�����Ϊ����(����ϵ������Ϊ�б���)
	 * @param data ��changeData�������صĶ�ά�������֮��ʽ��ͬ�Ķ�ά����
	 * @param title ������⣬��ͳ�����������������������Χ��ɵ��ַ���<br>
	 * ���磺2008��3~6�·ݺ����������н�����ͳ��ͼ	���·ݡ��С������������ʾ��Χ�����δ�			
	 * @return ���б���XML����
	 */
	public static String columnMultipleCol(String[][] data, String title) {
		String chartXML = null;
		if (data != null && data.length > 1 && title != null) {
			
			//--<�б����װ�ɱ���<dataset>Ԫ�ؿ�ʼ��ǩ
			String[] colTitles = data[0];
			for (int i = 1; i < colTitles.length; i++) {
				colTitles[i] = "<dataset seriesName=\"" + colTitles[i]
						+ "\" showValues=\"0\">\n";
			}
			//-->
			//--<�б����װ�ɱ���<category>Ԫ�ر�ǩ�����ӳɴ���ͬʱ�򱨱�<dataset>Ԫ�������<set>Ԫ��
			String categories = "";
			for (int i = 1; i < data.length; i++) {
				categories += "\t<category label=\"" + data[i][0] + "\" />\n";
				for (int j = 1; j < colTitles.length; j++) {
					colTitles[j] += "\t<set value=\"" + data[i][j] + "\" />\n";
				}
			}
			//-->
			//������<category>Ԫ�ر�ǩ���ڱ���<categories>Ԫ�ر�ǩ��
			categories = "<categories>\n" + categories + "</categories>\n";
			//--<��ӱ���<dataset>Ԫ�ؽ�����ǩ�����ӳɴ�
			String datasets = "";
			for (int i = 1; i < colTitles.length; i++) {
				datasets += colTitles[i] + "</dataset>\n";
			}
			//-->
			//������<categories>Ԫ�غͱ���<dataset>Ԫ��(���)���ڱ���<chart>Ԫ����
			chartXML = "<chart caption=\""+title+"\" baseFontSize=\"12\" shownames=\"1\" showvalues=\"0\" decimals=\"0\"  chartType=\"columnMulti\">\n"
					+ categories + datasets + "</chart>";
		}
		return chartXML;
	}
	
	/**
	 * ȡ�ö��б���XML���ݣ�����Ϊ����(����ϵ������Ϊ�б���)
	 * @param data ��changeData�������صĶ�ά�������֮��ʽ��ͬ�Ķ�ά����
	 * @param title ������⣬��ͳ�����������������������Χ��ɵ��ַ���<br>
	 * ���磺2008��3~6�·ݺ����������н�����ͳ��ͼ	���·ݡ��С������������ʾ��Χ�����δ�			
	 * @return ���б���XML����
	 */
	public static String columnMultipleRow(String[][] data, String title) {
		String chartXML = null;
		if (data != null && data.length > 1 && title != null) {
			if(data.length == 2){
				chartXML = columnSingleRow(data, title);
			}else{
				//--<�б����װ�ɱ���<categories>Ԫ��
				String[] colTitles = data[0];
				String categories = "";
				for (int i = 1; i < colTitles.length; i++) {
					categories += "\t<category label=\"" + colTitles[i] + "\" />\n";
				}
				categories = "<categories>\n" + categories + "</categories>\n";
				//-->
				//--<�б��⼰���ݷ�װ�ɱ���<dataset>Ԫ��
				String datasets = "";
				for (int i = 1; i < data.length; i++) {
					datasets += "<dataset seriesName=\"" + data[i][0]
	            					+ "\" showValues=\"0\">\n";
					for (int j = 1; j < colTitles.length; j++) {
						datasets += "\t<set value=\"" + data[i][j] + "\" />\n";
					}
					datasets += "</dataset>\n";
				}
				//-->
				//������<categories>Ԫ�غͱ���<dataset>Ԫ�ط��ڱ���<chart>Ԫ����
				chartXML = "<chart caption=\""+title+"\" baseFontSize=\"12\" shownames=\"1\" showvalues=\"0\" decimals=\"1\" formatNumberScale=\"0\">\n"
						+ categories + datasets + "</chart>";
			}
		}
		return chartXML;
	}
	
	
	
	/**
	 * ���һ���������Ե�chartXML����
	 * @param chartXML
	 * @return
	 */
	public static String changeData(String chartXML){
		String chartXMLRe = null;
		if(chartXML != null && chartXML.contains("<") && chartXML.contains(">")){
			chartXMLRe = chartXML.replace("<", "'<").replace(">", ">'+");
			chartXMLRe = chartXMLRe.substring(0,chartXMLRe.length()-1);
		}
		return chartXMLRe;
	}
	
	/**
	 * ȡ��һ��table�ַ���</br>
	 * @param data ����
	 * @return
	 */
	public static String table(Object[][] data){
		String tableHtml = null;
		
		return tableHtml;
	}
	
	public static String chart(boolean isSuccess,String chart){
		String chartResult = "<?xml version='1.0' encoding='utf-8' ?>\n";
		String success = "<success>" + isSuccess + "</success>\n";
		String chartXMl = "<charts>" + chart + "</charts>\n";
		chartResult += "<xml>\n" + success + chartXMl + "</xml>";
		return chartResult;
	}
	
	
	
	public static void main(String[] args) {
		testChart();
	}
	
	public static void testChart(){
		
	}
	
	public static void testDB(){
		Object[][] data = DBHelp.executeQuery2("select deptDesc, sum(areaMileage)/count(day), sum(patrolMileage)/count(day), sum(patrolCar)/count(day) from t_tira_analyze_pratrolm_day group by deptCode, deptDesc order by deptCode");
		String[] colTitle = {"���\\���ڲ���","Ͻ�����","Ѳ�������","Ѳ�߳���"};
		String chartXML = columnMultipleCol(changeData(changeData(data),colTitle), "");
		System.out.println(changeData(chartXML));
	}
}
