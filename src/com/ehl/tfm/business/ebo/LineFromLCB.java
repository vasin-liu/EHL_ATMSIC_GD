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
     * <b>��ȡ�߶��ַ���.</b>
     * param:deptId-����ID ��Ϊ�����ѯȫ��������Ϣ 
     * param:roadStatus-Ҫ��ȡ��·�����ࣨ��block-ӵ�¡�����flow-��ͨ������crowd-ӵ����,"all"��
     * param:zoomLevel-��ͼ�Ŵ���
     * param:isRedraw-�Ƿ����ػ�
     * @return.
     */
	public String  getLineVmlStr(String deptId,String roadStatus,int zoomLevel,boolean isRedraw) throws Exception {
		VmlPolyLine vmlPolyLine = new VmlPolyLine(new TransitTOScreenPixel(zoomLevel));
		String lineStr = "";
		Object[][] roadPointsArray = null;
		try{
			long start = System.currentTimeMillis();
			if(isRedraw){//������ػ�����ڴ��л�ȡ·������
				roadPointsArray = LineFromLCBMIS.getRoadPoints();//·�α�ţ�·�����ƣ�ǧ��ֵ������ֵ
			}else{
				roadPointsArray = LineFromLCBMIS.getStaticRoadPoints();
			}
			lineStr = LineFromLCB.getVMLLineByPoints(roadPointsArray,zoomLevel);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("·����������"+ex.getMessage());
		}
	    return lineStr;
   }
    /**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵������ȡҪ���ߵ����飬һ��һ����
	 * @������allpoints ��·��Ϣ����·��ţ���·���ƣ�ǧ��ֵ������ֵ�����꣬ӵ��״̬
	 * @�������ڣ�2010-5-4
	 * @���أ�map allline-�������ߣ���ɫ�� crowdline-ӵ�����ߣ���ɫ��
	 */
   private static String getVMLLineByPoints(Object[][] allpoints,int zoomLevel)throws Exception{
	   Map map = new HashMap();
	   Collection crowdList = new ArrayList();
	   if(allpoints == null || allpoints.length == 0){
		   return null;
	   }
	   StringBuffer xml= new StringBuffer();
	   StringBuffer allLinePointsBuffer = new StringBuffer();//���е㼯
	   StringBuffer crowdLinePointsBuffer = new StringBuffer();//ӵ�µ㼯��
	   String currentDLBH = "";//��ǰ��·���
	   String currentZT = "";//��ǰ״̬
	   try{
		   for(int i = 0; i < allpoints.length; i ++){
			   currentDLBH = allpoints[i][0].toString();
			   currentZT = StringHelper.obj2str(allpoints[i][5],"0");
			   //allLinePointsBuffer.append(allpoints[i][4]);
			   if(i > 0){
				   if(currentDLBH.equals(allpoints[i-1][0].toString())){
					   //�õ�������
					   if(allLinePointsBuffer.length() == 0){
						   allLinePointsBuffer.append(allpoints[i][4]);
						   //System.out.println(i+"----------"+allpoints[i][0]+allpoints[i][1]+"---"+allpoints[i][2]+"---"+allpoints[i][3]+allpoints[i][4]+allpoints[i][5]);
					   }else{
						   allLinePointsBuffer.append(";");
						   //System.out.println(i+"----------"+allpoints[i][0]+allpoints[i][1]+"---"+allpoints[i][2]+"---"+allpoints[i][3]+allpoints[i][4]+allpoints[i][5]);
						   allLinePointsBuffer.append(allpoints[i][4]);
					   }
					   //ӵ�µ����ӵ����
					   if(currentZT.equals("2")){
						   if(crowdLinePointsBuffer.length() == 0){
							   crowdLinePointsBuffer.append(allpoints[i][4]);
						   }else{
							   crowdLinePointsBuffer.append(";");
							   crowdLinePointsBuffer.append(allpoints[i][4]);
						   }
					   }
				   }else{//���¿�ʼһ��·
					   if(crowdLinePointsBuffer.length() != 0){
						   crowdList.add(crowdLinePointsBuffer.toString());
					   }
					   crowdLinePointsBuffer = new StringBuffer();
					   allLinePointsBuffer.append("end");
					   allLinePointsBuffer.append(allpoints[i][4]);
					   //System.out.println(i+"----end------"+allpoints[i][0]+allpoints[i][1]+"---"+allpoints[i][2]+"---"+allpoints[i][3]+allpoints[i][4]+allpoints[i][5]);
				   }
			   }else{//��һ����
				   allLinePointsBuffer.append(allpoints[i][4]);
				   //System.out.println(i+"----------"+allpoints[i][0]+allpoints[i][1]+"---"+allpoints[i][2]+"---"+allpoints[i][3]+allpoints[i][4]+allpoints[i][5]);
			   }
		   }
		   xml.append("road<linevml>");
		   //����
		   xml.append("<?xml:namespace prefix = v ns = \"urn:schemas-microsoft-com:vml\" />");
		   String[] lineArray =  allLinePointsBuffer.toString().split("end");
		   System.out.println("-----------------------------------"+lineArray.length);
		   String[] perLineArray = null;
		   for(int i = 0; i <lineArray.length; i ++ ){
			   xml.append("<v:polyline id='all'  style=\" POSITION: absolute\"   fill='true' points = \""  );
			   //ȫ������
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

