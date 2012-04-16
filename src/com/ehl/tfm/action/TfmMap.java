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
 * @����˵��: ·����Ϣ������
 * @�����ߣ�zhaoyu
 * @�������ڣ�2008-10-16
 */
public class TfmMap extends Controller {
	static Logger  logger = Logger.getLogger(TfmMap.class);
	public static Object[][]  lineInfo = null;
	public static Object[][]  segsInfo = null;

	/**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵������ȡ·��VML�ַ�������
	 * @�������ڣ�2010-3-12
	 */
	public ActionForward doReadRoadFlow(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		int zoomLevel = StringHelper.obj2int(request.getParameter("zoomLevel"), 0);
		String roadstatus = StringHelper.obj2str(request.getParameter("roadstatus"), "");
		String isRedrawStr = StringHelper.obj2str(request.getParameter("isRedraw"), "");
		String deptId = "";//�������ˣ�ȡȫ����������
		String xmlStr = "";
		boolean isRedraw = true;
		if(isRedrawStr != null && isRedrawStr.equals("false")){//�������ػ漴�������ݿ���������
			isRedraw = false;
		}
	    try{
	    	//��������ѡ��·��������ģʽ
	    	String tfmModel = Setting.getString("tfmModel");
	    	if(tfmModel == null){
	    		tfmModel = "1";//Ĭ��"1"-����ģʽ
	    		logger.error("[��ͨ����]:δ������������ģʽ��");
	    	}
	    	FlowLineEbi flowLineEbi = BusinessFactory.getFlowLineEbi(tfmModel);
	    	xmlStr = flowLineEbi.getLineVmlStr(deptId, roadstatus, zoomLevel, isRedraw);
			
		} catch (Exception ex) {
			logger.error("[��ͨ����]" + "��ȡ·��VML�ַ�������,��ȡ����vml�ַ���Ϊ��"+xmlStr);
			logger.error("doLoad fail:" +ex.getMessage());
		} 
	
	    out.write(xmlStr);
		return null;
	}
	
	/**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵����ͬ��t_tfm_linkdir,CSDLW_PLͼ���t_gis_linkdir_segs������
	 * @���أ�ͬ�������ʾ
	 * @�������ڣ�2010-5-4
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
			logger.error("[��ͨ����]" + "ͬ��t_tfm_linkdir,CSDLW_PLͼ�㵽t_gis_linkdir_segs�����ݱ���");
			ex.printStackTrace();
			logger.error("fail:" +ex.getMessage());
		} 
	    out.write(outStr);
		return null;
	}
	
	/**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵������ͬһ·�εĲ�ͬ��¼�ϲ�;·�������ظ��������ظ��ļ�¼
	 * @�������ڣ�2008-10-16
	 */
   public static Object[][] UnitRepeat(Object[][] roadArray){
	  int len = roadArray.length;
	  int mark = -1;
	  for(int i = 0; i < roadArray.length; i++){
		  for(int j = i+1; j < roadArray.length; j++){
			  //�ϲ�·����ͬ�ļ�¼
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
  //T_GIS_LCBP�����ƽ�����������侭γ������
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