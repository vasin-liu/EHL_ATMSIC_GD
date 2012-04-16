package com.ehl.dynamicinfo.pcs.action;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.tfm.TransitTOScreenPixel;
import com.ehl.webgis.data.SDEAccess;
import com.ehl.webgis.util.PointUtil;
import com.ehl.webgis.util.PolygonUtil;

public class PcsAction extends Controller {
	Logger logger = Logger.getLogger(PcsAction.class);
	
	/**
	 * @作者：wangw
	 * @版本号：1.0
	 * @函数说明：辖区警力信息字符串返回
	 * @创建日期：2010-10-27
	 */
	public ActionForward doPcsData(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		//int zoomLevel = StringHelper.obj2int(request.getParameter("zoomLevel"), 0); 
		SDEAccess sde = new SDEAccess(); // 定义SDE
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		try{ 
		    //准备写入SDE数据格式 
			PolygonUtil pUtil = new PolygonUtil(sde.initConnection(), "XZQH_PG"); 
			Object[][] foundPoint =  pUtil.getFieldsByCondition("","XZQDM,SHAPE,XZQMC"); 
			
			 
			String sql ="";
			String points=null; 
			if(foundPoint!=null){
				xmlData.append("<rows>");
				for(int i=0;i<foundPoint.length;i++){
					int num=0; 
					if(foundPoint[i][0]!=null && !"".equals(foundPoint[i][0])){
						sql="select count(ssjg) from t_sys_person where ssjg like '"+foundPoint[i][0].toString().substring(0,4)+"%'";
						System.out.println(foundPoint[i][0]);
						num =StringHelper.obj2int(DBHandler.getLineResult(sql)[0], 0);
					}  
					//由经纬度转屏幕坐标. 
					points = foundPoint[i][1].toString();
					points = points.substring(points.lastIndexOf(";")+1);  
					xmlData.append("<row>"); 
						xmlData.append("<col>" +StringHelper.obj2str(num,"") + "</col>");
						xmlData.append("<col>" +StringHelper.obj2str(points,"") + "</col>");
						xmlData.append("<col>" +StringHelper.obj2str(foundPoint[i][2],"") + "</col>");
					xmlData.append("</row>" );
					 
				} 
				xmlData.append("</rows>");  
			}
			out.write(xmlData.toString());
		}catch (Exception ex) {
			ex.printStackTrace();
			logger.error("辖区警力信息字符串返回Action出错："+ex.getMessage());
		} finally {
			sde.closeAO();
		}
			
		return null;
	}
}
