package com.ehl.tfm;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.pcs.DepartmentManage;
import com.ehl.webgis.util.LineUtil;

public class TfmManage extends Controller{
	/**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：获取连线mis数据和Sde数据并返回
	 * @创建日期：2009-2-16
	 */
	public ActionForward doReadRoadFlow(Action action, HttpServletRequest request, HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String linkDirId = StringHelper.obj2str(request.getParameter("linkDirId"), "");
	    
		String sql = "select linkid,linkname,direction,roadstatus,roadsegid from T_TFM_LINEFLOW " +
				" where linkid ='"+linkDirId+"'";
		Object[] link = DBHandler.getLineResult(sql);
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
	    xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");	
		
		if(link != null){
			
//			try {
//				for(int m = 0; m < link.length; m++){
//					xmlData.append("<road >");
//		 			xmlData.append("<col>" + StringHelper.obj2str(link[0],"") + "</col>");//路线编号
//					xmlData.append("<col>" + StringHelper.obj2str(link[1],"") + "</col>");//路线名称
//					xmlData.append("<col>" + StringHelper.obj2str(link[2],"") + "</col>");//方向
//					xmlData.append("<col>" + StringHelper.obj2str(link[3],"")  + "</col>");//路线状况
//					
//				    for(int n = 0; n <fieldValues.length; n++){
//						
//				    	String segPoint = StringHelper.obj2str(fieldValues[n][0], "");
//				        if(StringHelper.obj2str(lines[m][4], "").indexOf(segPoint) > -1){
//				        	if(perLinePointStr.equals("")){
//				        		perLinePointStr +=  StringHelper.obj2str(fieldValues[n][1], "");
//				        	}else{
//				        		perLinePointStr += "|" + StringHelper.obj2str(fieldValues[n][1], "");	
//				        	}
//				        		
//				        }	
//				    }
//				    xmlData.append("<col>" + perLinePointStr  + "</col>");//坐标
//					xmlData.append("</road>\n");
//				}
//			} catch (Exception ex) {
//				System.out.println("doGetTGSPoints fail:" + ex.getMessage());
//			} finally {
//				sde.closeAO();
//			}
		}
		xmlData.append("</RFWin>\n");
	    xmlData.append("</rfXML>\n");
	    out.write(xmlData.toString());
		return null;
	}

}
