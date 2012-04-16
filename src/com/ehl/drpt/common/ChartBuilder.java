package com.ehl.drpt.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.TextAnchor;

import com.appframe.action.Controller;
import com.appframe.common.Setting;
import com.ehl.base.util.StringUtil;

/**
 * @����: zhaoyu
 * @�汾�ţ�1.0
 * @˵����ͳ��ͼ������
 * @�������ڣ�2009-10-30
 */	
public class ChartBuilder extends Controller{
	private static String chartDefaultPath = Setting.getString("tomcatURL") +"webapps\\EHL_TIRA\\tira\\temp\\";
	private static int chartNO = 0;
	public ChartBuilder(HttpServletRequest request){
		chartDefaultPath =request.getSession().getServletContext().getRealPath("/")+"\\tira\\temp\\";
	}
//    //����ʾ��
//    public ActionForward doDraw(Action action,HttpServletRequest request, HttpServletResponse response) throws Throwable {
//		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
//		
//		response.setContentType("image/jpeg");
//		PrintWriter out = response.getWriter();
//		ChartBuilder chartBuilder = new ChartBuilder(response.getOutputStream()); 
//		chartBuilder.setHeight(StringHelper.obj2int(request.getParameter("height"), 600)); 
//		chartBuilder.width = StringHelper.obj2int(request.getParameter("width"), 600); 
//		chartBuilder.chartTitle = "";
//		double[][] data1 = new double[][]{   
//	            {672, 766, 223, 540, 126},   
//	            {325, 521, 210, 340, 106},   
//	            {332, 256, 523, 240, 526}   
//	        };   
//        String[] rowKeys1 = {"ƻ��", "����", "����"};   
//        String[] columnKeys1 = {"����", "�Ϻ�", "����", "�ɶ�", "����"};   
//		if("����ͼ".equals("����ͼ")){
//			CategoryDataset dataset = chartBuilder.getBarData(data1, rowKeys1, columnKeys1);   
//			chartBuilder.createLineChart(chartBuilder.chartTitle, "x��", "y��", dataset);   	
//		}
//		if("��ͼ".equals("��ͼͼ")){
//			double[] data = {9, 91};   
//	        String[] xDesc = {"ʧ����", "�ɹ���"};   
//	  
//	        createPieChart(chartBuilder.getDataPieSetByUtil(data, xDesc), chartBuilder.chartTitle);  
//		}
//		if("������״ͼ".equals("������״ͼ")){
//			 double[][] data = new double[][]{   
//			            {672, 766, 223, 540, 126}   
//			        };   
//	        String[] rowKeys = {"ƻ��"};   
//	        String[] columnKeys = {"����", "�Ϻ�", "����", "�ɶ�", "����"};   
//	        CategoryDataset dataset = chartBuilder.getBarData(data, rowKeys, columnKeys);   
//	        chartBuilder.createBarChart(dataset, "x����", "y����", chartBuilder.chartTitle);    
//		}
//		if("������״ͼ".equals("������״ͼ")){
//			double[][] data = new double[][]{   
//		            {672, 766, 223, 540, 126},   
//		            {325, 521, 210, 340, 106},   
//		            {332, 256, 523, 240, 526}   
//	        };   
//	        String[] rowKeys = {"ƻ��", "����", "����"};   
//	        String[] columnKeys = {"����", "�Ϻ�", "����", "�ɶ�", "����"};   
//	        CategoryDataset dataset = getBarData(data, rowKeys, columnKeys);   
//	        createBarChart(dataset, "x����", "y����", chartBuilder.chartTitle); 
//		}
//		if("������״ͼ".equals("������״ͼ")){
//			double[][] data = new double[][]{   
//		            {672, 766, 223, 540, 126},   
//		            {325, 521, 210, 340, 106},   
//		            {332, 256, 523, 240, 526}   
//	        };   
//	        String[] rowKeys = {"ƻ��", "����", "����"};   
//	        String[] columnKeys = {"����", "�Ϻ�", "����", "�ɶ�", "����"};   
//	        CategoryDataset dataset = getBarData(data, rowKeys, columnKeys);   
//	        createBarChart(dataset, "x����", "y����", chartBuilder.chartTitle); 
//		}
//		if("�ѻ���״ͼ".equals("�ѻ���״ͼ")){
//			double[][] data = new double[][]{   
//		            {0.21, 0.66, 0.23, 0.40, 0.26},   
//		            {0.25, 0.21, 0.10, 0.40, 0.16}   
//	        };   
//	        String[] rowKeys = {"ƻ��", "����"};   
//	        String[] columnKeys = {"����", "�Ϻ�", "����", "�ɶ�", "����"};   
//	        CategoryDataset dataset = getBarData(data, rowKeys, columnKeys);   
//	        createStackedBarChart(dataset, "x����", "y����", "��״ͼ");  
//		}
//		
//		
//		return null;
//    }
    
  
    /**  
     *  ��״ͼ,����ͼ ���ݼ� 
     *  @param data Ҫ����ͼ��Ķ�ά����
     *  @param 
     */ 
    public CategoryDataset getBarData(double[][] data, String[] rowKeys,   
            String[] columnKeys) throws Exception{   
        return DatasetUtilities.createCategoryDataset(rowKeys, columnKeys, data);   
  
    }   
  
    // ��״ͼ ���ݼ�   
    public PieDataset getDataPieSetByUtil(double[] data,   
            String[] datadescription) throws Exception{   
  
        if (data != null && datadescription != null) {   
            if (data.length == datadescription.length) {   
                DefaultPieDataset dataset = new DefaultPieDataset();   
                for (int i = 0; i < data.length; i++) {   
                    dataset.setValue(datadescription[i], data[i]);   
                }   
                return dataset;   
            }   
  
        }   
  
        return null;   
    }   
    /**  
     * ��״ͼ  
     * @desc: ����ͼ����Ӧ���ͻ���
     * @param dataset ���ݼ�  
     * @param chartTitle ͼ����  
     * @param xTitle x����⣨�����࣬���еȣ�  
     * @param yTitle y����⣨���ٶȣ������ȣ�  
     * @param width  ͼ����
     * @param height ͼ��߶�
     * @param target ��Ӧ�����  
     * @return  
     */  
    public void createBarChart(CategoryDataset dataset, String chartTitle,String xTitle,   
            String yTitle,int width,int height,OutputStream target)throws Exception{
    	
    	this.buildBarChart(dataset, chartTitle, xTitle, yTitle, width, height, target);;
 	   
    }
    /**  
     * ��״ͼ  
     * @desc: ����ͼ��Ĭ��·��
     * @param dataset ���ݼ�  
     * @param chartTitle ͼ����  
     * @param xTitle x����⣨�����࣬���еȣ�  
     * @param yTitle y����⣨���ٶȣ������ȣ�  
     * @param width  ͼ����
     * @param height ͼ��߶� 
     * @return  ͼ��ȫ·��
     */  
    public String createBarChart(CategoryDataset dataset, String chartTitle,String xTitle,   
            String yTitle,int width,int height) throws Exception{
    	
	   String filePathAndName = this.buildBarChart(dataset, chartTitle, xTitle, yTitle, width, height, null);
	   return filePathAndName;
 	   
    }
  
    /**  
     * ��״ͼ  
     *   
     *@param dataset ���ݼ�  
     * @param xName x����⣨�����࣬���еȣ�  
     * @param yName y����⣨���ٶȣ������ȣ�  
     * @param chartTitle ͼ����  
     * @param stream ��Ӧ�����  
     * @return  
     */  
    private  String  buildBarChart(CategoryDataset dataset, String chartTitle,String xTitle,   
            String yTitle,int width,int height,Object targetOutputStream) throws Exception{   
        JFreeChart chart = ChartFactory.createBarChart3D(chartTitle, // ͼ�����   
        		xTitle, // Ŀ¼�����ʾ��ǩ   
                yTitle, // ��ֵ�����ʾ��ǩ   
                dataset, // ���ݼ�   
                PlotOrientation.VERTICAL, // ͼ����ˮƽ����ֱ   
                true, // �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)   
                false, // �Ƿ����ɹ���   
                false // �Ƿ�����URL����   
                );   
        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);   
        /*  
         * VALUE_TEXT_ANTIALIAS_OFF��ʾ�����ֵĿ���ݹر�,  
         * ʹ�õĹرտ���ݺ����御��ѡ��12��14�ŵ�������,���������������ÿ�  
         */  
        chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);   
        chart.setTextAntiAlias(false);   
        chart.setBackgroundPaint(Color.white); 
        // create plot   
        CategoryPlot plot = chart.getCategoryPlot(); 
        // ���ú����߿ɼ�   
        plot.setRangeGridlinesVisible(true);   
        // ����ɫ��   
        plot.setRangeGridlinePaint(Color.gray);   
  
        // �����ᾫ��   
        NumberAxis vn = (NumberAxis) plot.getRangeAxis();   
        // vn.setAutoRangeIncludesZero(true);   
        DecimalFormat df = new DecimalFormat("#0");   
        vn.setNumberFormatOverride(df); // ���������ݱ�ǩ����ʾ��ʽ   
        // x������   
  
        CategoryAxis domainAxis = plot.getDomainAxis();   
        domainAxis.setLabelFont(labelFont);// �����   
  
        domainAxis.setTickLabelFont(labelFont);// ����ֵ   
  
        // Lable��Math.PI/3.0������б   
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions   
         .createUpRotationLabelPositions(Math.PI / 3.0));   
  
        domainAxis.setMaximumCategoryLabelWidthRatio(0.9f);// �����ϵ� Lable �Ƿ�������ʾ   
  
        // ���þ���ͼƬ��˾���   
        domainAxis.setLowerMargin(0.05);   
        // ���þ���ͼƬ�Ҷ˾���   
        domainAxis.setUpperMargin(0.05);   
        // ���� columnKey �Ƿ�����ʾ   
        // domainAxis.setSkipCategoryLabelsToFit(true);   
  
        plot.setDomainAxis(domainAxis);   
        // ������ͼ����ɫ��ע�⣬ϵͳȡɫ��ʱ��Ҫʹ��16λ��ģʽ���鿴��ɫ���룬�����Ƚ�׼ȷ��   
        plot.setBackgroundPaint(new Color(255, 255, 204));   
        
        plot.setNoDataMessage("��ͳ�����ݣ�");   
        // y������   
        ValueAxis rangeAxis = plot.getRangeAxis();   
        rangeAxis.setLabelFont(labelFont);   
        rangeAxis.setTickLabelFont(labelFont);   
        // ������ߵ�һ�� Item ��ͼƬ���˵ľ���   
        rangeAxis.setUpperMargin(0.15);   
        // ������͵�һ�� Item ��ͼƬ�׶˵ľ���   
        rangeAxis.setLowerMargin(0.15);   
        plot.setRangeAxis(rangeAxis);   
        //      ע�⣺�˾�ܹؼ������޴˾䣬�����ֵ���ʾ�ᱻ���ǣ���������û����ʾ���������� 
        BarRenderer3D  renderer = new BarRenderer3D(); 
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition( 
        		ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER)); 
        renderer.setItemLabelAnchorOffset(10D);// ��������ͼ�ϵ�����ƫ��ֵ 

        // �������ӿ��   
        renderer.setMaximumBarWidth(0.05);   
        // �������Ӹ߶�   
        renderer.setMinimumBarLength(0.2);   
        // �������ӱ߿���ɫ   
        renderer.setBaseOutlinePaint(Color.BLACK);   
        // �������ӱ߿�ɼ�   
        renderer.setDrawBarOutline(true);   
  
        // // ����������ɫ   
        renderer.setSeriesPaint(0, new Color(204, 255, 255));   
        renderer.setSeriesPaint(1, new Color(153, 204, 255));   
        renderer.setSeriesPaint(2, new Color(51, 204, 204));   
  
        // ����ÿ��������������ƽ������֮�����   
        renderer.setItemMargin(0.0);   
  
        // ��ʾÿ��������ֵ�����޸ĸ���ֵ����������   
        renderer.setIncludeBaseInRange(true);   
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());   
        renderer.setBaseItemLabelsVisible(true);   
  
        plot.setRenderer(renderer);   
        // ��������͸����   
        plot.setForegroundAlpha(1.0f); 
        FileOutputStream fos_jpg = null;
        String chartPathName = "";
        if(targetOutputStream == null){//����Ĭ��ͼƬ�洢·��
        	chartPathName = ChartBuilder.createChartPathName();
        	isChartPathExist(chartDefaultPath);//�ж�·���Ƿ���ڣ�û���򴴽�
        	fos_jpg = new FileOutputStream(chartDefaultPath + chartPathName); 
        	targetOutputStream = fos_jpg;
        }
        ChartUtilities.writeChartAsPNG((OutputStream)targetOutputStream, chart, width, height, true, 10);  
        fos_jpg.close();
        return "/EHL_TIRA/tira/temp/"+chartPathName;
    }
    
    
    /**  
     * ��״ͼ    
     * @desc: ����ͼ����Ӧ���ͻ���
     * @param dataset ���ݼ�  
     * @param chartTitle ͼ����  
     * @param xTitle x����⣨�����࣬���еȣ�  
     * @param yTitle y����⣨���ٶȣ������ȣ�  
     * @param width  ͼ����
     * @param height ͼ��߶�
     * @param target ��Ӧ�����  
     * @return  
     */  
    public void createPieChart(PieDataset dataset,   
            String chartTitle,int width,int height, OutputStream targetOutputStream)throws Exception{
    	
    	this.buildPieChart(dataset,   
                 chartTitle, width, height, targetOutputStream);
 	   
    }
    /**  
     * ��״ͼ  
     * @desc: ����ͼ��Ĭ��·��
     * @param dataset ���ݼ�  
     * @param chartTitle ͼ����  
     * @param xTitle x����⣨�����࣬���еȣ�  
     * @param yTitle y����⣨���ٶȣ������ȣ�  
     * @param width  ͼ����
     * @param height ͼ��߶� 
     * @return  ͼ��ȫ·��
     */  
    public String createPieChart(PieDataset dataset,   
            String chartTitle,int width,int height ) throws Exception{
    	
	   String filePathAndName = this.buildPieChart(dataset,   
               chartTitle, width, height, null);
	   return filePathAndName;
 	   
    }
  
    /**  
     * ��״ͼ  
     *   
     * @param dataset ���ݼ�  
     * @param chartTitle ͼ����  
     * @param charName ����ͼ������  
     * @param width ͼ����
     * @param height ͼ��߶�
     * @return  
     */  
    private String buildPieChart(PieDataset dataset,   
            String chartTitle,int width,int height,Object targetOutputStream) throws Exception{   
    	JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, // chart   
                // title   
                dataset,// data   
                false,// include legend   
                true, true);   
  
        // ʹ��˵����ǩ��������,ȥ���������   
        // chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);��Ч��
        chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);   
        chart.setTextAntiAlias(false);   
        chart.setBackgroundPaint(Color.white);   
        
        chart.setTextAntiAlias(false);   
        // ͼƬ����ɫ   
        chart.setBackgroundPaint(Color.white);   
        // ����ͼ�����������������title   
        Font font = new Font("SansSerif", Font.BOLD, 18);   
        TextTitle title = new TextTitle(chartTitle);   
        title.setFont(font);   
        chart.setTitle(title);   
  
        PiePlot3D plot = (PiePlot3D) chart.getPlot();   
        // ͼƬ����ʾ�ٷֱ�:Ĭ�Ϸ�ʽ   
  
        // ָ����ͼ�����ߵ���ɫ   
        // plot.setBaseSectionOutlinePaint(Color.BLACK);   
        // plot.setBaseSectionPaint(Color.BLACK);   
//      �趨���� ("link.jsp","section"));//sectionΪ����������д��Ĭ��Ϊcategory
//        plot.setURLGenerator(new StandardPieURLGenerator("link.jsp","section"));

        // ����������ʱ����Ϣ   
        plot.setNoDataMessage("��ͳ�����ݣ�");   
  
        // ����������ʱ����Ϣ��ʾ��ɫ   
        // plot.setNoDataMessagePaint(Color.red);   
  
        // ͼƬ����ʾ�ٷֱ�:�Զ��巽ʽ��{0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ���� ,С�������λ   
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(   
                "{0}={1}({2})", NumberFormat.getNumberInstance(),   
                new DecimalFormat("0.00%")));   
        // ͼ����ʾ�ٷֱ�:�Զ��巽ʽ�� {0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ����   
        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(   
                "{0}={1}({2})"));   
  
        plot.setLabelFont(new Font("SansSerif", Font.TRUETYPE_FONT, 12));   
  
        // ָ��ͼƬ��͸����(0.0-1.0)   
        plot.setForegroundAlpha(1.0f);   
        // ָ����ʾ�ı�ͼ��Բ��(false)����Բ��(true)   
        plot.setCircular(true,true);   
  
        // ���õ�һ�� ����section �Ŀ�ʼλ�ã�Ĭ����12���ӷ���   
        plot.setStartAngle(90);   
  
        // // ���÷ֱ���ɫ   
//        plot.setSectionPaint(xDesc[0], new Color(244, 194, 144));   
//        plot.setSectionPaint(xDesc[1], new Color(144, 233, 144));   
  
        // �߿������Ӱ����Բ��ͼ����״  
        //ͼ�����
        FileOutputStream fos_jpg = null;
        String chartPathName = "";
        if(targetOutputStream == null){//����Ĭ��ͼƬ�洢·��
        	chartPathName = ChartBuilder.createChartPathName();
        	isChartPathExist(chartDefaultPath);//�ж�·���Ƿ���ڣ�û���򴴽�
        	fos_jpg = new FileOutputStream(chartDefaultPath+chartPathName); 
        	targetOutputStream = fos_jpg;
        }
        ChartUtilities.writeChartAsPNG((OutputStream)targetOutputStream, chart, width, height);   
        fos_jpg.close();
        return "/EHL_TIRA/tira/temp/"+chartPathName;
    }   
  
    /**  
     * ����ͼ  
     * @desc: ����ͼ����Ӧ���ͻ���
     * @param dataset ���ݼ�  
     * @param chartTitle ͼ����  
     * @param xTitle x����⣨�����࣬���еȣ�  
     * @param yTitle y����⣨���ٶȣ������ȣ�  
     * @param width  ͼ����
     * @param height ͼ��߶�
     * @param target ��Ӧ�����  
     * @return  
     */  
    public void createLineChart(String chartTitle, String x, String y,int width,int height,   
            CategoryDataset xyDataset,OutputStream targetOutputStream)throws Exception{
    	
    	this.buildLineChart( chartTitle,  x,  y, width, height,   
                 xyDataset, targetOutputStream);
 	   
    }
    /**  
     * ����ͼ  
     * @desc: ����ͼ��Ĭ��·��
     * @param dataset ���ݼ�  
     * @param chartTitle ͼ����  
     * @param xTitle x����⣨�����࣬���еȣ�  
     * @param yTitle y����⣨���ٶȣ������ȣ�  
     * @param width  ͼ����
     * @param height ͼ��߶� 
     * @return  ͼ��ȫ·��
     */  
    public String createLineChart(String chartTitle, String x, String y,int width,int height,   
            CategoryDataset xyDataset) throws Exception{
    	
	   String filePathAndName = this.buildLineChart(chartTitle,  x,  y, width, height,   
               xyDataset, null);
	   return filePathAndName;
 	   
    } 
  
    /**  
     * ����ͼ  
     *   
     * @param chartTitle  - ͼ�����
     * @param x ����� 
     * @param y �����
     * @param width ͼ����
     * @param height ͼ��߶�
     * @param xyDataset - ������������ͼ�����ݼ� 
     * @param response  
     * @return  ͼ��·��
     */  
    private String buildLineChart(String chartTitle, String x, String y,int width,int height,   
            CategoryDataset xyDataset,Object targetOutputStream) throws Exception{   
  
        JFreeChart chart = ChartFactory.createLineChart(chartTitle, x, y,   
                xyDataset, PlotOrientation.VERTICAL, true, true, false);   
  
        chart.setTextAntiAlias(false);   
        chart.setBackgroundPaint(Color.WHITE);   
        // ����ͼ�����������������title   
        Font font = new Font("����", Font.BOLD, 25);   
        TextTitle title = new TextTitle(chartTitle);   
        title.setFont(font);   
        chart.setTitle(title);   
        // �����������   
        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);   
  
        chart.setBackgroundPaint(Color.WHITE);   
  
        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();   
        // x�� // �����������Ƿ�ɼ�   
        categoryplot.setDomainGridlinesVisible(true);   
        // y�� //�����������Ƿ�ɼ�   
        categoryplot.setRangeGridlinesVisible(true);   
  
        categoryplot.setRangeGridlinePaint(Color.WHITE);// ����ɫ��   
  
        categoryplot.setDomainGridlinePaint(Color.WHITE);// ����ɫ��   
  
        categoryplot.setBackgroundPaint(Color.lightGray);   
  
        // ����������֮��ľ���   
        // categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));   
  
        CategoryAxis domainAxis = categoryplot.getDomainAxis();   
  
        domainAxis.setLabelFont(labelFont);// �����   
  
        domainAxis.setTickLabelFont(labelFont);// ����ֵ   
  
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // �����ϵ�   
        // Lable   
        // 45����б   
        // ���þ���ͼƬ��˾���   
  
        domainAxis.setLowerMargin(0.0);   
        // ���þ���ͼƬ�Ҷ˾���   
        domainAxis.setUpperMargin(0.0);   
  
        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();   
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());   
        numberaxis.setAutoRangeIncludesZero(true);   
  
        // ���renderer ע���������������͵�lineandshaperenderer����   
        LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot.getRenderer();   
  
        lineandshaperenderer.setBaseShapesVisible(true); // series �㣨�����ݵ㣩�ɼ�   
  
        lineandshaperenderer.setBaseLinesVisible(true); // series �㣨�����ݵ㣩�������߿ɼ�   
  
        // ��ʾ�۵�����   
        // lineandshaperenderer.setBaseItemLabelGenerator(new   
        // StandardCategoryItemLabelGenerator());   
        // lineandshaperenderer.setBaseItemLabelsVisible(true);   
        // ��������Ϊpng�ļ�  
//      ͼ�����
        FileOutputStream fos_jpg = null;
        String chartPathName = "";
        if(targetOutputStream == null){//����Ĭ��ͼƬ�洢·��
        	chartPathName = ChartBuilder.createChartPathName();
        	isChartPathExist(chartDefaultPath);//�ж�·���Ƿ���ڣ�û���򴴽�
        	fos_jpg = new FileOutputStream(chartDefaultPath + chartPathName); 
        	targetOutputStream = fos_jpg;
        }
        ChartUtilities.writeChartAsPNG((OutputStream)targetOutputStream, chart, width, height);  
        fos_jpg.close();
        return "/EHL_TIRA/tira/temp/"+chartPathName;
    }   
    
    /**  
     * ����ͼ  
     * @desc: ����ͼ����Ӧ���ͻ���
     * @param dataset ���ݼ�  
     * @param chartTitle ͼ����  
     * @param xTitle x����⣨�����࣬���еȣ�  
     * @param yTitle y����⣨���ٶȣ������ȣ�  
     * @param width  ͼ����
     * @param height ͼ��߶�
     * @param target ��Ӧ�����  
     * @return  
     */  
    public void createStackedBarChart(CategoryDataset dataset, String chartTitle,String xName,   
            String yName, int width,int height,OutputStream targetOutputStream)throws Exception{
    	
    	this.buildStackedBarChart(  dataset,  chartTitle, xName,   
                 yName,  width, height, targetOutputStream);
 	   
    }
    /**  
     * ����ͼ  
     * @desc: ����ͼ��Ĭ��·��
     * @param dataset ���ݼ�  
     * @param chartTitle ͼ����  
     * @param xTitle x����⣨�����࣬���еȣ�  
     * @param yTitle y����⣨���ٶȣ������ȣ�  
     * @param width  ͼ����
     * @param height ͼ��߶� 
     * @return  ͼ��ȫ·��
     */  
    public String createStackedBarChart(CategoryDataset dataset, String chartTitle,String xName,   
            String yName, int width,int height) throws Exception{
    	
	   String filePathAndName = this.buildStackedBarChart(dataset,  chartTitle, xName,   
               yName,  width, height, null);
	   return filePathAndName;
 	   
    } 
    /**  
     * ��ջ��״ͼ  
     *   
     * @param dataset Ҫ����ͼ������ݼ� 
     * @param chartTitle  ͼ�����
     * @param xName  x�����
     * @param yName  y�����
     * @param width  ͼ����
     * @param height  ͼ��߶�
     * @param targetOutputStream ��Ӧ�����  
     * @return  
     */  
    private String buildStackedBarChart(CategoryDataset dataset, String chartTitle,String xName,   
            String yName, int width,int height,Object targetOutputStream)throws Exception {   
        // 1:�õ� CategoryDataset   
  
        // 2:JFreeChart����   
        JFreeChart chart = ChartFactory.createStackedBarChart(chartTitle, // ͼ�����   
                xName, // Ŀ¼�����ʾ��ǩ   
                yName, // ��ֵ�����ʾ��ǩ   
                dataset, // ���ݼ�   
                PlotOrientation.VERTICAL, // ͼ����ˮƽ����ֱ   
                true, // �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)   
                false, // �Ƿ����ɹ���   
                false // �Ƿ�����URL����   
                );   
        // ͼ����������   
        chart.setTextAntiAlias(false);   
  
        chart.setBackgroundPaint(Color.WHITE);   
  
        // 2 ��2 ��������� ����������� TextTitle ����   
        chart.setTitle(new TextTitle(chartTitle, new Font("����", Font.BOLD,   
                25)));   
        // 2 ��2.1:��������   
        // x,y����������   
        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);   
  
        // 2 ��3 Plot ���� Plot ������ͼ�εĻ��ƽṹ����   
        CategoryPlot plot = chart.getCategoryPlot();   
  
        // ���ú����߿ɼ�   
        plot.setRangeGridlinesVisible(true);   
        // ����ɫ��   
        plot.setRangeGridlinePaint(Color.gray);   
  
        // �����ᾫ��   
        NumberAxis vn = (NumberAxis) plot.getRangeAxis();   
        // �������ֵ��1   
        vn.setUpperBound(1);   
        // ���������������0��ʼ   
        // vn.setAutoRangeIncludesZero(true);   
        // ������ʾ��ʽ�ǰٷֱ�   
        DecimalFormat df = new DecimalFormat("0.00%");   
        vn.setNumberFormatOverride(df); // ���������ݱ�ǩ����ʾ��ʽ   
        // DomainAxis �������ᣬ�൱�� x �ᣩ�� RangeAxis ����Χ�ᣬ�൱�� y �ᣩ   
  
        CategoryAxis domainAxis = plot.getDomainAxis();   
  
        domainAxis.setLabelFont(labelFont);// �����   
  
        domainAxis.setTickLabelFont(labelFont);// ����ֵ   
  
        // x������̫��������������б���������ַ�ʽѡ��һ������Ч����ͬ   
        // ��б��1�������ϵ� Lable 45����б   
        // domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);   
        // ��б��2��Lable��Math.PI 3.0������б   
        // domainAxis.setCategoryLabelPositions(CategoryLabelPositions   
        // .createUpRotationLabelPositions(Math.PI / 3.0));   
  
        domainAxis.setMaximumCategoryLabelWidthRatio(0.6f);// �����ϵ� Lable �Ƿ�������ʾ   
  
        plot.setDomainAxis(domainAxis);   
  
        // y������   
        ValueAxis rangeAxis = plot.getRangeAxis();   
        rangeAxis.setLabelFont(labelFont);   
        rangeAxis.setTickLabelFont(labelFont);   
        // ������ߵ�һ�� Item ��ͼƬ���˵ľ���   
        rangeAxis.setUpperMargin(0.15);   
        // ������͵�һ�� Item ��ͼƬ�׶˵ľ���   
        rangeAxis.setLowerMargin(0.15);   
        plot.setRangeAxis(rangeAxis);   
  
        // Renderer ������ͼ�εĻ��Ƶ�Ԫ   
        StackedBarRenderer renderer = new StackedBarRenderer();   
        // �������ӿ��   
        renderer.setMaximumBarWidth(0.05);   
        // �������Ӹ߶�   
        renderer.setMinimumBarLength(0.1);   
        //�������ı߿���ɫ   
        renderer.setBaseOutlinePaint(Color.BLACK);   
        //�������ı߿�ɼ�   
        renderer.setDrawBarOutline(true);   
  
        // // ����������ɫ(���趨Ҳ��Ĭ��)   
        renderer.setSeriesPaint(0, new Color(204, 255, 204));   
        renderer.setSeriesPaint(1, new Color(255, 204, 153));   
  
        // ����ÿ��������������ƽ������֮�����   
        renderer.setItemMargin(0.4);   
  
        plot.setRenderer(renderer);   
        // ��������͸����(�����3D�ı������ò��ܴﵽ����Ч���������2D��������ʹ��ɫ�䵭)   
        // plot.setForegroundAlpha(0.65f);   
        //ͼ�����
        FileOutputStream fos_jpg = null;
        String chartPathName = "";
        if(targetOutputStream == null){//����Ĭ��ͼƬ�洢·��
        	chartPathName = ChartBuilder.createChartPathName();
        	isChartPathExist(chartDefaultPath);//�ж�·���Ƿ���ڣ�û���򴴽�
        	fos_jpg = new FileOutputStream(chartDefaultPath +chartPathName); 
        	targetOutputStream = fos_jpg;
        }
        ChartUtilities.writeChartAsPNG((OutputStream)targetOutputStream, chart, width, height, true, 10); 
        fos_jpg.close();
        return "/tira/temp/"+chartPathName;
    }
	
    /**  
     * ����ͼ��ȫ��
     * @param   
     */  
    private static String createChartPathName(){
    	++chartNO;
    	String chartName = StringUtil.getCurrDateTime("yyyyMMddHHmmss") + chartNO +".jpg";
    	return chartName;
    }
    /**  
     * �ж��ļ����Ƿ���ڣ�������������½�  
     * @param chartPath  
     */  
    private static void isChartPathExist(String chartPath) {   
        File file = new File(chartPath);   
        if (!file.exists()) {   
            file.mkdirs();   
        // log.info("CHART_PATH="+CHART_PATH+"create.");   
        }   
    }  
	public static void main(String[] args){
		System.out.println(StringUtil.getCurrDateTime("yyyyMMddHHmmss"));
	}
}
