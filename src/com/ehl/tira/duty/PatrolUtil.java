package com.ehl.tira.duty;

import java.util.ArrayList;
import java.util.List;

/**
 * PatrolAverageMileageAction巡逻平均里程
 * @author xiayouxue
 *
 */
public class PatrolUtil {
	
	/*
	报表数据：
		行，列，数据
	单系列报表：
	形式1:
		地区\降雨量\月份  一月 
		湖北                                 100				
		湖南                                 200
	形式2：	
		地区\降雨量\月份  一月  二月
		湖北                                 100	 150
	形式3：	
		地区\降雨量,日照量\月份  一月 
		湖北                                                 100,200	
	形式3变形1：			
		地区\气候参数         降雨量         日照量
		湖北                                    100     200	
	形式3变形2：
		气候参数\月份  一月 
		降雨量                      100                   
		日照量		  200
	-------------------------------	
	多系列报表：
	形式1:
		地区\降雨量\月份  一月  二月
		湖北                                 100	 150			
		湖南                                 200  250
	形式2：	
		地区\降雨量,日照量\月份  一月
		湖北                                                 100,200			
		湖南                                                 150,250
	形式2变形1：			
		地区\气候参数                      降雨量         日照量
		湖北                                                 100      200			
		湖南                                                 150      250			
	形式3：	
		地区\降雨量,日照量\月份     一月              二月
		湖北                                                 100,200  150,250	
	形式3变形1：		
		气候参数\月份     一月              二月
		降雨量                      100       150                  
		日照量                      200		250
	形式3变形1变形1：		
		月份\气候参数         降雨量  日照量       
		一月                                100     200                  
		 二月                               150		250		
	-------------------------------	
	完整型报表：
		地区\降雨量,日照量\月份  一月		二月
		湖北                                                 100,200	100,200		
		湖南                                                 150,250	150,250
	*/
	
	
	/**
	 * 将Object类型二维数组转换为String型二维数组
	 * @param data Object类型二维数组
	 * @return String型二维数组
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
	 * 将分组数据转换为单条数据</br>
	 * <pre>
	 * --分组数据--
	 * 省份	月份	 降雨量
	 * 湖北    一月     100
	 * 湖北    二月     200
	 * 湖南    一月     150
	 * 湖南    二月     250
	 * 
	 * --单条数据--
	 * 省份\月份		一月		二月 
	 * 湖北		100		200
	 * 湖南		150		250
	 * colTitle:一月  二月
	 * colNum:2(一月	  二月)
	 * rowTitle: 湖北  湖南
	 * rowNum:2(湖北	  湖南)
	 * </pre> 
	 * @param dataIn 分组数据
	 * @return 单条数据
	 */
	public static String[][] changeData(String[][] dataIn){
		String[][] dataOut = null;
		if(dataIn != null && dataIn.length != 0 && dataIn[0].length == 3){
			//--<获取列名数组
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
			//获取总列数colNum
			int colNum = colTitleL.size();
			//获取总行数rowNum
			int rowNum = dataIn.length / colNum;
			//--<获取单条数据二维数组
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
	 * 给数据加上列标题
	 * @param data 数据，单条数据没有列标题
	 * @param colTitle 列标题
	 * @return 添加过列标题的数组
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
	 * 生成饼图XML数据
	 * @param data
	 * @return
	 */
	public static String pie(String[][] data){
		String chartXML = null;
		
		return chartXML;
	}
	
	/**
	 * 生成单系折线图XML数据
	 * @param data
	 * @return
	 */
	public static String lineSingle(String[][] data){
		String chartXML = null;
		
		return chartXML;
	}
	
	/**
	 * 生成多系折线图XML数据
	 * @param data
	 * @return
	 */
	public static String lineMulti(String[][] data){
		String chartXML = null;
		
		return chartXML;
	}
	
	/**
	 * 生成单系柱状图XML数据
	 * @param data
	 * @return
	 */
	public static String columnSingle(String[][] data){
		String chartXML = null;
		
		return chartXML;
	}
	
	/**
	 * 生成多系柱状图XML数据
	 * @param data
	 * @return
	 */
	public static String columnMulti(String[][] data){
		String chartXML = null;
		
		return chartXML;
	}
	
	/**
	 * 生成单系堆状图XML数据
	 * @param data
	 * @return
	 */
	public static String stackSingle(String[][] data){
		String chartXML = null;
		
		return chartXML;
	}
	
	/**
	 * 生成多系堆状图XML数据
	 * @param data
	 * @return
	 */
	public static String stackMulti(String[][] data,String[] selects, boolean isRow){
		String chartXML = null;
		/*2008年3~4 全国部分
		地区\降雨量,日照量\月份     一月		二月
		湖北                                                 100,200	100,200		
		湖南                                                 150,250	150,250
		*/
		if (data != null && data.length >= 3 && data[0].length >= 3
				&& selects != null && selects.length == 2) {
			//--<获取标题
			String title = data[0][0];
			String[] dataItems;
			if(title != null){
				String[] items = title.split(":");
				if(items.length == 3){
					dataItems = items[1].split(",");
					if(dataItems.length >= 2){
						title = selects[0] + items[2] + selects[1] + items[0] + items[1].replace(",", "，") + "统计图";
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
			//--<获取<categories>元素
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
	 * 取得单列报表XML数据
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
	 * 取得单列报表XML数据
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
	 * 取得多列报表XML数据，以列为基础(报表系列名称为列标题)
	 * @param data 由changeData方法返回的二维数组或与之格式相同的二维数组
	 * @param title 报表标题，由统计项、浮动项、数据项和其所处范围组成的字符串<br>
	 * 例如：2008年3~6月份湖北地区各市降雨量统计图	，月份、市、降雨量和其表示范围的修饰词			
	 * @return 多列报表XML数据
	 */
	public static String columnMultipleCol(String[][] data, String title) {
		String chartXML = null;
		if (data != null && data.length > 1 && title != null) {
			
			//--<列标题封装成报表<dataset>元素开始标签
			String[] colTitles = data[0];
			for (int i = 1; i < colTitles.length; i++) {
				colTitles[i] = "<dataset seriesName=\"" + colTitles[i]
						+ "\" showValues=\"0\">\n";
			}
			//-->
			//--<行标题封装成报表<category>元素标签并连接成串，同时向报表<dataset>元素内添加<set>元素
			String categories = "";
			for (int i = 1; i < data.length; i++) {
				categories += "\t<category label=\"" + data[i][0] + "\" />\n";
				for (int j = 1; j < colTitles.length; j++) {
					colTitles[j] += "\t<set value=\"" + data[i][j] + "\" />\n";
				}
			}
			//-->
			//将报表<category>元素标签放在报表<categories>元素标签内
			categories = "<categories>\n" + categories + "</categories>\n";
			//--<添加报表<dataset>元素结束标签并连接成串
			String datasets = "";
			for (int i = 1; i < colTitles.length; i++) {
				datasets += colTitles[i] + "</dataset>\n";
			}
			//-->
			//将报表<categories>元素和报表<dataset>元素(多个)放在报表<chart>元素内
			chartXML = "<chart caption=\""+title+"\" baseFontSize=\"12\" shownames=\"1\" showvalues=\"0\" decimals=\"0\"  chartType=\"columnMulti\">\n"
					+ categories + datasets + "</chart>";
		}
		return chartXML;
	}
	
	/**
	 * 取得多列报表XML数据，以行为基础(报表系列名称为行标题)
	 * @param data 由changeData方法返回的二维数组或与之格式相同的二维数组
	 * @param title 报表标题，由统计项、浮动项、数据项和其所处范围组成的字符串<br>
	 * 例如：2008年3~6月份湖北地区各市降雨量统计图	，月份、市、降雨量和其表示范围的修饰词			
	 * @return 多列报表XML数据
	 */
	public static String columnMultipleRow(String[][] data, String title) {
		String chartXML = null;
		if (data != null && data.length > 1 && title != null) {
			if(data.length == 2){
				chartXML = columnSingleRow(data, title);
			}else{
				//--<列标题封装成报表<categories>元素
				String[] colTitles = data[0];
				String categories = "";
				for (int i = 1; i < colTitles.length; i++) {
					categories += "\t<category label=\"" + colTitles[i] + "\" />\n";
				}
				categories = "<categories>\n" + categories + "</categories>\n";
				//-->
				//--<行标题及数据封装成报表<dataset>元素
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
				//将报表<categories>元素和报表<dataset>元素放在报表<chart>元素内
				chartXML = "<chart caption=\""+title+"\" baseFontSize=\"12\" shownames=\"1\" showvalues=\"0\" decimals=\"1\" formatNumberScale=\"0\">\n"
						+ categories + datasets + "</chart>";
			}
		}
		return chartXML;
	}
	
	
	
	/**
	 * 输出一个用来测试的chartXML数据
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
	 * 取得一个table字符串</br>
	 * @param data 数据
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
		String[] colTitle = {"大队\\考勤参数","辖区里程","巡逻总里程","巡逻车辆"};
		String chartXML = columnMultipleCol(changeData(changeData(data),colTitle), "");
		System.out.println(changeData(chartXML));
	}
}
