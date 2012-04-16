package com.ehl.tfm.business.ebo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appframe.utils.StringHelper;
import com.ehl.tfm.OffsetBean;
import com.ehl.tfm.TfmCommon;
import com.ehl.tfm.TransitTOScreenPixel;
import com.ehl.tfm.VmlPolyLine;
import com.ehl.tfm.business.ebi.FlowLineEbi;
import com.ehl.tfm.dao.DaoFactory;
import com.ehl.tfm.dao.impl.LineFromCSDLWImpl;
import com.ehl.tfm.dao.impl.LineFromLCBMIS;
import com.ehl.tfm.dao.impl.LineFromLinkGisImpl;
import com.ehl.webgis.data.SDEAccess;

public class LineFromLCB implements FlowLineEbi{
	static Logger  logger = Logger.getLogger(LineFromCSDLWEbo.class);

	/**
     * <b>获取线段字符串.</b>
     * param:deptId-机构ID 不为空则查询全部流量信息 
     * param:roadStatus-要获取的路况种类（“block-拥堵”，“flow-畅通”，“crowd-拥挤”,"all"）
     * param:zoomLevel-地图放大级数
     * param:isRedraw-是否是重绘
     * @return.
     */
	public String  getLineVmlStr(String deptId,String roadStatus,int zoomLevel,boolean isRedraw) throws Exception {
		VmlPolyLine vmlPolyLine = new VmlPolyLine(new TransitTOScreenPixel(zoomLevel));
		String lineStr = "";
		Object[][] roadPointsArray = null;
		try{
			long start = System.currentTimeMillis();
			if(isRedraw){//如果是重绘则从内存中获取路况数据
				roadPointsArray = LineFromLCBMIS.getRoadPoints();//路段编号，路段名称，千米值，百米值
			}else{
				roadPointsArray = LineFromLCBMIS.getStaticRoadPoints();
			}
			lineStr = LineFromLCB.getVMLLineByPoints(roadPointsArray,zoomLevel);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("路况发布报错："+ex.getMessage());
		}
	    return lineStr;
   }
    /**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：获取要绘线的数组，一行一条线
	 * @参数：allpoints 道路信息：道路编号，道路名称，千米值，百米值，坐标，拥堵状态
	 * @创建日期：2010-5-4
	 * @返回：map allline-所有连线（灰色） crowdline-拥堵连线（红色）
	 */
   private static String getVMLLineByPoints(Object[][] allpoints,int zoomLevel)throws Exception{
	   Map map = new HashMap();
	   Collection crowdList = new ArrayList();
	   if(allpoints == null || allpoints.length == 0){
		   return null;
	   }
	   StringBuffer xml= new StringBuffer();
	   StringBuffer allLinePointsBuffer = new StringBuffer();//所有点集
	   StringBuffer crowdLinePointsBuffer = new StringBuffer();//拥堵点集合
	   String currentDLBH = "";//当前道路编号
	   String currentZT = "";//当前状态
	   try{
		   for(int i = 0; i < allpoints.length; i ++){
			   currentDLBH = allpoints[i][0].toString();
			   currentZT = StringHelper.obj2str(allpoints[i][5],"0");
			   //allLinePointsBuffer.append(allpoints[i][4]);
			   if(i > 0){
				   if(currentDLBH.equals(allpoints[i-1][0].toString())){
					   //该点加入绘线
					   if(allLinePointsBuffer.length() == 0){
						   allLinePointsBuffer.append(allpoints[i][4]);
						   //System.out.println(i+"----------"+allpoints[i][0]+allpoints[i][1]+"---"+allpoints[i][2]+"---"+allpoints[i][3]+allpoints[i][4]+allpoints[i][5]);
					   }else{
						   allLinePointsBuffer.append(";");
						   //System.out.println(i+"----------"+allpoints[i][0]+allpoints[i][1]+"---"+allpoints[i][2]+"---"+allpoints[i][3]+allpoints[i][4]+allpoints[i][5]);
						   allLinePointsBuffer.append(allpoints[i][4]);
					   }
					   //拥堵点加入拥堵线
					   if(currentZT.equals("2")){
						   if(crowdLinePointsBuffer.length() == 0){
							   crowdLinePointsBuffer.append(allpoints[i][4]);
						   }else{
							   crowdLinePointsBuffer.append(";");
							   crowdLinePointsBuffer.append(allpoints[i][4]);
						   }
					   }
				   }else{//重新开始一条路
					   if(crowdLinePointsBuffer.length() != 0){
						   crowdList.add(crowdLinePointsBuffer.toString());
					   }
					   crowdLinePointsBuffer = new StringBuffer();
					   allLinePointsBuffer.append("end");
					   allLinePointsBuffer.append(allpoints[i][4]);
					   //System.out.println(i+"----end------"+allpoints[i][0]+allpoints[i][1]+"---"+allpoints[i][2]+"---"+allpoints[i][3]+allpoints[i][4]+allpoints[i][5]);
				   }
			   }else{//第一个点
				   allLinePointsBuffer.append(allpoints[i][4]);
				   //System.out.println(i+"----------"+allpoints[i][0]+allpoints[i][1]+"---"+allpoints[i][2]+"---"+allpoints[i][3]+allpoints[i][4]+allpoints[i][5]);
			   }
		   }
		   xml.append("road<linevml>");
		   //绘线
		   xml.append("<?xml:namespace prefix = v ns = \"urn:schemas-microsoft-com:vml\" />");
		   String[] lineArray =  allLinePointsBuffer.toString().split("end");
		   System.out.println("-----------------------------------"+lineArray.length);
		   String[] perLineArray = null;
		   for(int i = 0; i <lineArray.length; i ++ ){
			   xml.append("<v:polyline id='all'  style=\" POSITION: absolute\"   fill='true' points = \""  );
			   //全部连线
			   perLineArray = lineArray[i].split(";");
			   String[] tem2Array = null;
			   for(int j=0;j<perLineArray.length;j++){
					tem2Array = perLineArray[j].split(",");
					xml.append(new TransitTOScreenPixel(zoomLevel).getScreenPixelX(tem2Array[0]));
					xml.append("px ,");
					xml.append(new TransitTOScreenPixel(zoomLevel).getScreenPixelY(tem2Array[1]));
					xml.append("px ,");
			   }
				xml.append("\" filled = \"f\"  strokecolor = \""+"gray"+"\"strokeweight = \"2pt\"></v:polyline>lineEnd");
		   }
		   Iterator it = crowdList.iterator();
		   String crowdLinePointStr = "";
		   String[] perCrowdLinePointArray = null;
		   while(it.hasNext()){
			   crowdLinePointStr = (String)it.next();
			   xml.append("<v:polyline   style=\" POSITION: absolute\"   fill='true' points = \""  );
			   perCrowdLinePointArray = crowdLinePointStr.split(";");
			   String[] tem2Array = null;
			   for(int j=0;j<perCrowdLinePointArray.length;j++){
					tem2Array = perCrowdLinePointArray[j].split(",");
					xml.append(new TransitTOScreenPixel(zoomLevel).getScreenPixelX(tem2Array[0]));
					xml.append("px ,");
					xml.append(new TransitTOScreenPixel(zoomLevel).getScreenPixelY(tem2Array[1]));
					xml.append("px ,");
			   }
				xml.append("\" filled = \"f\"  strokecolor = \""+"red"+"\"strokeweight = \"2pt\"></v:polyline>lineEnd");
		   }
		   
	   }catch(Exception ex){
		   ex.printStackTrace();
		   
	   }
	   System.out.println(xml.toString());
	   return xml.toString();
   }
    public static void main(String[] args){
    	String str = "22seg44seg33";
    	String[] array = str.split("seg");
    	for(int i= 0; i < array.length; i ++){
    		System.out.println(array[i]);
    		
    	}
    }
	public String synLinkDir() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}

