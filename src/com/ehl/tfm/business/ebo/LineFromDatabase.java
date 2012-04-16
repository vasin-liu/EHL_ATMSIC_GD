package com.ehl.tfm.business.ebo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appframe.utils.StringHelper;
import com.ehl.tfm.OffsetBean;
import com.ehl.tfm.TfmCommon;
import com.ehl.tfm.TransitTOScreenPixel;
import com.ehl.tfm.VmlPolyLine;
import com.ehl.tfm.business.ebi.FlowLineEbi;
import com.ehl.tfm.dao.impl.LineFromLinkGisImpl;

public class LineFromDatabase implements FlowLineEbi{
	static Logger  logger = Logger.getLogger(LineFromDatabase.class);

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
		StringBuffer lineStr = new StringBuffer("");
		StringBuffer xmlData = new StringBuffer("");
		Object[][] linkDirFlowArray = null;
		try{
			if(isRedraw){//如果是重绘则从内存中获取路况数据
				linkDirFlowArray = LineFromLinkGisImpl.getStaticSegsGIS();//连线编号，连线名称，连线方向，路段编号，路段点集，路况，第一视频编号，第二视频编号
			}else{
				linkDirFlowArray = LineFromLinkGisImpl.getInfoOnLcbMis();
			}

			if(linkDirFlowArray != null){
				logger.error("路况发布获取到连线信息："+linkDirFlowArray.length+"条");
				Collection perLineCol = new ArrayList();//该连线所含路段集合
				Object[] pointsOfLine  = null;
				String linePoints = "";
				Object[][] segsOfLine = null;
				String lineStartPoint = "";
				String lineEndSeg = "";
				String lineColor = "";
				String lineId = "";
				String[]lineAll=null;
				double vmlLineWidth = TfmCommon.getLineWidth(zoomLevel);
			
				for(int m = 0; m < linkDirFlowArray.length; m++){    
					if(roadStatus != null && roadStatus.length() > 0){
	                    //过滤掉不要求显示的连线
						if(roadStatus.equals("block") && StringHelper.obj2str(linkDirFlowArray[m][5], "").equals("拥堵")){
					        
				        }else if(roadStatus.equals("crowd") && StringHelper.obj2str(linkDirFlowArray[m][5], "").equals("拥挤")){
				        
				        }else if(roadStatus.equals("flow") && StringHelper.obj2str(linkDirFlowArray[m][5], "").equals("畅通")){
					        
					    }else if(roadStatus.equals("all")){
					        
					    }else{
					    	continue;
					    }
					}
					xmlData.append(StringHelper.obj2str(linkDirFlowArray[m][0],"") + "<col>");//路线编号
					xmlData.append(StringHelper.obj2str(linkDirFlowArray[m][1],"") + "<col>");//路线名称
					xmlData.append(TfmCommon.getDirectionDesc(StringHelper.obj2str(linkDirFlowArray[m][2],"")) + "<col>");//方向
					xmlData.append(TfmCommon.getLineStatusDesc(StringHelper.obj2str(linkDirFlowArray[m][5],"")) + "<col>");//路线状况
					xmlData.append(StringHelper.obj2str(linkDirFlowArray[m][6], "") + "<col>");//第一视频编号
					xmlData.append("视频一" + "<col>");//第一视频名称
					xmlData.append(StringHelper.obj2str(linkDirFlowArray[m][7], "") + "<col>");//第二视频编号
					xmlData.append("视频二"+ "<col>"); //第二视频名称
					
					//绘线
					linePoints = StringHelper.obj2str(linkDirFlowArray[m][4], "");
					if(linePoints.length() == 0 || linePoints.equals("null")){//该线没有对应空间数据
						continue;
					}
					pointsOfLine = linePoints.split("seg");
					segsOfLine = arrayTo2Array(pointsOfLine,"");
					ArrayList<OffsetBean> obArr =new ArrayList<OffsetBean>();
	                //计算连线偏移量 
					for(int i=0;i<pointsOfLine.length;i++){
						lineAll=StringHelper.obj2str(pointsOfLine[i], "").split(";");
						lineStartPoint = lineAll[0];
						lineEndSeg = lineAll[lineAll.length-1];
						//下行是低公里桩-->高公里桩 0，上行是高公里桩-->低公里桩  1 由于经纬度坐标与切图坐标原点不同，需要颠倒坐标系，开始和终点互换
						if(StringHelper.obj2str(linkDirFlowArray[m][2],"").equals("1")){
							obArr.add(TfmCommon.calcOffset(zoomLevel, lineStartPoint, lineEndSeg));
						}else{
							obArr.add(TfmCommon.calcOffset(zoomLevel, lineEndSeg, lineStartPoint));
						}
					}
					  
					
					lineColor = TfmCommon.getLineColor(linkDirFlowArray[m][5]); 
					
					lineId = StringHelper.obj2str(linkDirFlowArray[m][0], "");
					//画线
					if(StringHelper.obj2str(linkDirFlowArray[m][2], "").equals("1") //0 下行 1上行
								|| StringHelper.obj2str(linkDirFlowArray[m][2], "").equals("0")){
						lineStr.append(vmlPolyLine.createVmlPolyLines(segsOfLine,obArr,true,
						lineColor,StringHelper.obj2str(linkDirFlowArray[m][2], ""),lineId,vmlLineWidth));	
						if(m != linkDirFlowArray.length-1){
							lineStr.append("lineEnd");
						}
						
					}else{
						lineStr.append(vmlPolyLine.createVmlPolyLines(segsOfLine,obArr,false,
								lineColor,StringHelper.obj2str(linkDirFlowArray[m][2], ""),lineId,vmlLineWidth));	
						if(m != linkDirFlowArray.length-1){
							lineStr.append("lineEnd");
						}
					}
					if(segsOfLine == null){
						xmlData.append(0);//所含路段数量	
					}else{
						xmlData.append(segsOfLine.length);//所含路段数量	
					}
					if(m == linkDirFlowArray.length-1){
						
					}else{
						xmlData.append("<road>");//连线间用“<road>”分隔	
					}
					segsOfLine = null;
				    perLineCol.clear();
				    
				}
				xmlData.append("<linevml>");//道路信息和绘线字符串用“<linevml>”相隔
				xmlData.append(lineStr.toString());
				
			}else{
				return "noLinkDirData";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("路况发布报错："+ex.getMessage());
		}
	    return xmlData.toString();
   }
	/**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：同步t_tfm_linkdir,CSDLW_PL图层和t_gis_linkdir_segs表数据
	 * @创建日期：2010-5-4
	 * @返回：同步结果提示语
	 */
    public String synLinkDir() throws Exception{
    	Map map = new HashMap();
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
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：一维数组转化为二维数组，第一列以指定对象填充
	 * @参数：array-要转化的一维数组；fillObj-指定的填充对象
	 * @创建日期：2010-5-4
	 * @返回：returnArray-转化后二维数组
	 */
   private static Object[][] arrayTo2Array(Object[] array,Object fillObj)throws Exception{
	   if(array == null){
		   return null;
	   }
	   Object[][] returnArray = new Object[array.length][2];
	   for(int i = 0; i < array.length; i++){
		   returnArray[i][0] = fillObj;
		   returnArray[i][1] = array[i];
	   }
	   return returnArray;
   }
}

