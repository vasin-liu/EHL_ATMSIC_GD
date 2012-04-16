package com.ehl.tfm.business.ebo;

import java.util.*;

import org.apache.log4j.Logger;

import com.appframe.utils.StringHelper;
import com.ehl.tfm.OffsetBean;
import com.ehl.tfm.TfmCommon;
import com.ehl.tfm.TransitTOScreenPixel;
import com.ehl.tfm.VmlPolyLine;
import com.ehl.tfm.action.TfmMap;
import com.ehl.tfm.business.ebi.FlowLineEbi;
import com.ehl.tfm.dao.DaoFactory;
import com.ehl.tfm.dao.impl.LineFromLinkGisImpl;
import com.ehl.webgis.data.SDEAccess;

public class LineFromBMZEbo implements FlowLineEbi{
	static Logger  logger = Logger.getLogger(TfmMap.class);
	/**
     * <b>获取流量线vml字符串.</b>
     * param:deptId-机构ID 不为空则查询全部流量信息 
     * param:roadStatus-要获取的路况种类（“拥堵”，“畅通”，“拥挤”）
     * param :zoomLevel- 地图放大级数
     * param:isRedraw-是否是重绘
     * @return.
     */
	public String getLineVmlStr(String deptId,String roadStatus,int zoomLevel,boolean isRedraw) throws Exception {
		VmlPolyLine vmlPolyLine = new VmlPolyLine(new TransitTOScreenPixel(zoomLevel));
		String lineStr = ""; 
		StringBuffer xmlData = new StringBuffer("");
		Map map = new HashMap();
		if(isRedraw){//如果是重绘则从内存中获取路况数据
			map = DaoFactory.getLineDataDao("2").getRedrawLineMap();//"2"-广东模式（基于t_gis_lcbpt和连线逐个路段组织流量数据）
		}else{
			map = DaoFactory.getLineDataDao("2").getLineMap(deptId, roadStatus);//"2"-广东模式（基于t_gis_lcbpt和连线逐个路段组织流量数据）
		}
		Object[][] lines = null;	
		Object[][] fieldValues = null;	
		if(map.get("lines") != null){
			lines = (Object[][])map.get("lines");	
		}
		if(map.get("linespoint") != null){
			fieldValues = (Object[][])map.get("linespoint");	
		}
		if(fieldValues != null){
			Collection perLineCol = new ArrayList();//该连线所含路段集合
			String[][] infoOfLines  = null;
			String startPoint = "";//连线起始点
			String endPoint = "";//连线结尾点
			String lineColor = "";
			String lineId = "";
			double vmlLineWidth = TfmCommon.getLineWidth(zoomLevel);
			infoOfLines = getLineInfoArray(lines,fieldValues);//组织连线信息到二维数组（道路名称，道路方向，路况，经纬度）
			//组织返回消息体
			for(int m = 0; m < infoOfLines.length; m++)
			{    
				if(roadStatus != null && roadStatus.length() > 0){
                    //过滤掉不要求显示的连线
					if(roadStatus.equals("block") && StringHelper.obj2str(infoOfLines[m][2], "").equals("拥堵")){
				        
			        }else if(roadStatus.equals("crowd") && StringHelper.obj2str(infoOfLines[m][2], "").equals("拥挤")){
			        
			        }else if(roadStatus.equals("flow") && StringHelper.obj2str(infoOfLines[m][2], "").equals("畅通")){
				        
				    }else if(roadStatus.equals("all")){
				        
				    }else{
				    	continue;
				    }
				}
                //计算连线偏移量
				startPoint = StringHelper.obj2str(infoOfLines[0][3], "").split(";")[0];//连线起点
				endPoint = StringHelper.obj2str(infoOfLines[infoOfLines.length-1][3], "").split(";")[0];//连线终点
				OffsetBean offbean =  TfmCommon.calcOffset(zoomLevel, startPoint, endPoint);//计算连线偏移量
				lineColor = TfmCommon.getLineColor(infoOfLines[m][2]);//线颜色
				lineId = StringHelper.obj2str(infoOfLines[m][0], "");//线编号
				//画线
				if(StringHelper.obj2str(infoOfLines[m][1], "").equals("上行") || StringHelper.obj2str(infoOfLines[m][1], "").equals("0"))
				{
					lineStr += vmlPolyLine.createVmlPolyLine(infoOfLines[m][3],offbean.getOffsetUpX(),offbean.getOffsetUpY(),
							lineColor,"上行",lineId,vmlLineWidth);	
				}else{
					lineStr += vmlPolyLine.createVmlPolyLine(infoOfLines[m][3],offbean.getOffsetDownX(),offbean.getOffsetDownY(),
							lineColor,"下行",lineId,vmlLineWidth);	
				}
	 			xmlData.append(infoOfLines[m][0] + "<col>");//路线编号
				xmlData.append( infoOfLines[m][0] + "<col>");//路线名称
				xmlData.append(TfmCommon.getDirectionDesc(infoOfLines[m][1]) + "<col>");//方向
				xmlData.append(TfmCommon.getLineStatusDesc(infoOfLines[m][2]) + "<col>");//路线状况
				
				if(infoOfLines == null){
					xmlData.append(0);//所含路段数量	
				}else{
					xmlData.append(infoOfLines.length);//所含路段数量	
				}
				if(m == infoOfLines.length-1){
					
				}else{
					xmlData.append("<road>");	
				}
			}
			xmlData.append("<linevml>");
			xmlData.append(lineStr);
		}
	    return xmlData.toString();
   }
	/**
     * <b>.合并连线信息为一个二维数组</b>
     * param:lines-连线信息
     * linesPoint-连线空间位置信息，按道路名称和千米值排序
     * @return 前者大于等于后者返回true.
     */
	public static String[][] getLineInfoArray(Object[][] lines,Object[][] linesPoint) throws Exception{
		 if(lines == null || linesPoint == null ){
			 return null;
		 }
		 String[][] lineInfoArray = new String[lines.length][4];
		 StringBuffer buffer = new StringBuffer();
		 String startpos = "";
		 String endpos = "";
		 String roadName = "";
		 boolean flag = false;//标识当前连线是否已找到其对应经纬度
		 for(int i = 0; i < lines.length; i ++){
			 flag = false;//重置
			 buffer.delete(0, buffer.length());
			 startpos = StringHelper.obj2str(lines[i][1], "").substring(1).split("\\+")[0];
			 endpos = StringHelper.obj2str(lines[i][2], "").substring(1).split("\\+")[0];
			 roadName = StringHelper.obj2str(lines[i][0], "");
			 lineInfoArray[i][0] = roadName+startpos+"km-"+endpos +"km";//道路名称
			 lineInfoArray[i][1] = StringHelper.obj2str(lines[i][3], "");//方向
			 lineInfoArray[i][2] = StringHelper.obj2str(lines[i][4], "");//路况
			 for(int j = 0; j < linesPoint.length; j ++){
				 if(roadName.equals(StringHelper.obj2str(linesPoint[j][0],""))){
					 flag = true;
					 if(buffer.length() == 0){
						 buffer.append(StringHelper.obj2str(linesPoint[j][1],"")+","+StringHelper.obj2str(linesPoint[j][2],"")); 
					 }else{
						 buffer.append(";"+StringHelper.obj2str(linesPoint[j][1],"")+","+StringHelper.obj2str(linesPoint[j][2],""));
					 }
				 }else{
					 if(flag){
						 break;
					 }
				 }
			 }
			 lineInfoArray[i][3] = buffer.toString();//连线经纬度字符串
		 }
		 return lineInfoArray;
		 
	}
	
	/**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：同步t_tfm_linkdir,lcb_pt图层和t_gis_linkdir_segs表数据
	 * @创建日期：2010-5-4
	 * @返回：同步结果提示语
	 */
    public String synLinkDir() throws Exception{
    	Map map = new HashMap();
    	String tipStr = "";
    	map = new LineFromLinkGisImpl().getLinkDirMap();//lines-连线基本信息 linespoint-所有路段信息
    	Object[][] lines = null;	
		Object[][] fieldValues = null;	
		int successNum = 0;
		if(map.get("lines") != null){
			lines = (Object[][])map.get("lines");	
		}
		if(map.get("linespoint") != null){
			fieldValues = (Object[][])map.get("linespoint");	
		}
		int sum = lines.length;
		if(lines != null){//存在连线数据
			Map segMap = null;
			Object[][] linkSegs = null;
			boolean flag = LineFromLinkGisImpl.doDeleteLinkdir();
			if(flag){//清空表t_gis_linkdir_segs成功
				successNum = LineFromLinkGisImpl.doSynLinkDir(lines, fieldValues);
			}
		}else{
			logger.error("连线更新失败：");
			return "连线更新失败：t_tfm_link_dir没有数据";
		}
    	return "总计道路"+sum+"条，成功更新："+successNum+"条" ;
    }
	
	/**
     * <b>.如果起始点相同，只取开始时间最近的一条</b>
     * param:lines-流量信息
     * @return 去除重复记录的流量信息数组.
     */
	public static Object[][] removeRepeat(Object[][] lines){
		Object[][] res = new Object[lines.length][];
		int len = res.length;
		Object[][] newLines = null;
		if(lines != null){
			boolean flag = true;
		    for(int i = 0; i < lines.length-1; i ++){
		        for(int j = i+1; j < lines.length; j ++){
		            if(StringHelper.obj2str(lines[i][4], "").equals(StringHelper.obj2str(lines[j][4],"")) &&
		               !comparaString(StringHelper.obj2str(lines[i][7], ""),StringHelper.obj2str(lines[j][7],""))){
		               flag = false;
		            }
		        }
		        if(flag){
		        	len --;
		        	res[i] = null;
		        }else{
		        	res[i] = lines[i];	
		        }
		    }
		    newLines = new Object[len][];
		    int currentFlag = 0;
		    for(int k = 0; k < res.length; k++){
		        if(res[k] != null){
		        	newLines[currentFlag] = res[k];	
		        	currentFlag ++;
		        }
		    }
		}
	    return newLines;	
	}
	/**
     * <b>.比较两个字符串大小</b>
     * param:lines-流量信息
     * @return 前者大于等于后者返回true.
     */
	public static boolean comparaString(String str1,String str2){
		 if(str1.compareToIgnoreCase(str2) < 0){
		    return false; 	 
		 }else if(str1.compareToIgnoreCase(str2) > 0){
			return true;
		 }else{
			 return true;
		 }
	}
	
	/**
     * <b>.Collection中一维数组转存到二维数组中</b>
     * param:
     * @return .
     */
	public static Object[][] col2arry(Collection<Object[]> col){
		 int size = col.size();
		 Object[][] newArray = new Object[size][];
		 if(col.isEmpty()){
		     	 
		 }else {
			 Iterator it = col.iterator();
	    	 int i = 0;
	    	 while(it.hasNext())
	    	 {
	    		 newArray[i] = (Object[])it.next();	
	    		i ++;
	    	 }
		 }
		 return newArray;
	}
	
	public static void main(String[] args){
//		Map map = new HashMap();
//		//System.out.println(LineFromBMZ.comparaString("1","2"));
//		Object[][] r1 = {{"12","路名2","上行","拥堵","11","K050+000","K070+000","2009-02-01 22:22:22","2009-08-08 09:09:09"},{"11","路名1","上行","拥堵","12","K050+000","K070+000","2009-02-02 22:22:22","2009-08-08 09:09:09"}};
//		Object[][] r2 = {{"11","路名1","上行","拥堵","12","K050+000","K070+000","2009-02-02 22:22:22","2009-08-08 09:09:09"}};
		//Object[][] r3 = LineFromBMZ.removeRepeat(r1);
		String tt= "KO9";
		System.out.print("K078+000".split("\\+")[0]);
		
	}
		
}
