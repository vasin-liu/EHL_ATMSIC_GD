package com.ehl.tfm.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.common.Setting;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.tfm.business.BusinessFactory;
import com.ehl.tfm.business.ebi.FlowLineEbi;
import com.ehl.tfm.business.ebo.LineFromLinkGisEbo;
import com.ehl.webgis.project.ProjectionTransform;
import com.esri.arcgis.geometry.Point;

/**
 * @类型说明: 路况信息发布类
 * @创建者：zhaoyu
 * @创建日期：2008-10-16
 */
public class TfmMap extends Controller {
	static Logger  logger = Logger.getLogger(TfmMap.class);
	public static Object[][]  lineInfo = null;
	public static Object[][]  segsInfo = null;

	/**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：获取路况VML字符串返回
	 * @创建日期：2010-3-12
	 */
	public ActionForward doReadRoadFlow(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		int zoomLevel = StringHelper.obj2int(request.getParameter("zoomLevel"), 0);
		String roadstatus = StringHelper.obj2str(request.getParameter("roadstatus"), "");
		String isRedrawStr = StringHelper.obj2str(request.getParameter("isRedraw"), "");
		String deptId = "";//机构过滤，取全部流量数据
		String xmlStr = "";
		boolean isRedraw = true;
		if(isRedrawStr != null && isRedrawStr.equals("false")){//不进行重绘即读入数据库最新数据
			isRedraw = false;
		}
	    try{
	    	//根据配置选择路况发布的模式
	    	String tfmModel = Setting.getString("tfmModel");
	    	if(tfmModel == null){
	    		tfmModel = "1";//默认"1"-菏泽模式
	    		logger.error("[交通流量]:未配置流量发布模式！");
	    	}
	    	FlowLineEbi flowLineEbi = BusinessFactory.getFlowLineEbi(tfmModel);
	    	xmlStr = flowLineEbi.getLineVmlStr(deptId, roadstatus, zoomLevel, isRedraw);
			
		} catch (Exception ex) {
			logger.error("[交通流量]" + "获取路况VML字符串报错,获取到的vml字符串为："+xmlStr);
			logger.error("doLoad fail:" +ex.getMessage());
		} 
	
	    out.write(xmlStr);
		return null;
	}
	
	/**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：同步t_tfm_linkdir,CSDLW_PL图层和t_gis_linkdir_segs表数据
	 * @返回：同步结果提示
	 * @创建日期：2010-5-4
	 */
	public ActionForward doSynLinkDir(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String outStr = "";
	    try{
	    	outStr = new LineFromLinkGisEbo().synLinkDir();
		} catch (Exception ex) {
			logger.error("[交通流量]" + "同步t_tfm_linkdir,CSDLW_PL图层到t_gis_linkdir_segs表数据报错：");
			ex.printStackTrace();
			logger.error("fail:" +ex.getMessage());
		} 
	    out.write(outStr);
		return null;
	}
	
	/**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：将同一路段的不同记录合并;路线名称重复但方向不重复的记录
	 * @创建日期：2008-10-16
	 */
   public static Object[][] UnitRepeat(Object[][] roadArray){
	  int len = roadArray.length;
	  int mark = -1;
	  for(int i = 0; i < roadArray.length; i++){
		  for(int j = i+1; j < roadArray.length; j++){
			  //合并路段相同的记录
			  if(StringHelper.obj2str(roadArray[i][1],"").equals(StringHelper.obj2str(roadArray[j][1],"")) && 
					  !StringHelper.obj2str(roadArray[i][2],"").equals(StringHelper.obj2str(roadArray[j][2])) 
			  ){
				  roadArray[j][2] = StringHelper.obj2str(roadArray[i][2],"")+","+StringHelper.obj2str(roadArray[j][2],"");
				  roadArray[j][3] = StringHelper.obj2str(roadArray[i][3],"")+","+StringHelper.obj2str(roadArray[j][3],"");
				  roadArray[j][4] = StringHelper.obj2str(roadArray[i][4],"")+","+StringHelper.obj2str(roadArray[j][4],"");
				  roadArray[j][5] = StringHelper.obj2str(roadArray[i][5],"")+","+StringHelper.obj2str(roadArray[j][5],"");
				  len--;
				  roadArray[i] = null; 
				  break;
			  }
		  }
	  }
	  Object[][] newArray = new Object[len][];
	  for(int i = 0; i < roadArray.length; i++){
		  if(roadArray[i] != null){
			  mark ++;
			  newArray[mark] = roadArray[i];
		  }
	  }
	  return newArray;
   }
  //T_GIS_LCBP表根据平面坐标计算填充经纬度坐标
  public  void trasferToXY()throws Throwable{
		String sql = "select dlmc,eminx,eminy,qmz from T_GIS_LCBPT";
		Object[][] oResult = DBHandler.getMultiResult(sql);
		
		ProjectionTransform pf = new ProjectionTransform();
		Point pt = null;
		String sql2 = "";
		for(int i = 0; i < oResult.length; i ++){
			pt = pf.plainToGeoPoint(Double.valueOf(oResult[i][1].toString()),Double.valueOf(oResult[i][2].toString()));
			sql2 = "update T_GIS_LCBPT set LONGITUDE='"+pt.getX()+"',LATITUDE='"+pt.getY()+"' where dlmc='"+oResult[i][0]+"' and qmz='"+oResult[i][3]+"'";
			DBHandler.execute(sql2);
		}
  }
}