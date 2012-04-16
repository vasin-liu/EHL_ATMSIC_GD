package com.ehl.markutil;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.util.CreateSequence;
import com.ehl.webgis.data.SDEAccess;
import com.ehl.webgis.util.PointUtil;

public class Synaction extends Controller{
	static Logger  logger = Logger.getLogger(Synaction.class);
	private String layerName = "LCB_PT"; // ����ͼ������
	private SDEAccess sde = new SDEAccess(); // ����SDE
	
	 /**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵����ͬ��ͼ��LCB_PT���ݵ�LCB_PT_MIS���ݿ��
	 * @������
	 * @�������ڣ�2011-1-20
	 * @���أ�
	 */
	public ActionForward doSynLayerData(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		String dh = StringHelper.obj2str(request.getParameter("DH"),"");
		String dlmc = StringHelper.obj2str(request.getParameter("LM"),"");
		String id = "";
		String sql = "";
		String whereCase = "";
		String clearSql = "";
		Object[][] fieldValues = null;
		try{
			String strFields = "DH,DLMC,QMZ,BMZ,SHAPE";
			PointUtil pUtil = new PointUtil(sde.initConnection(), layerName);
			if(!"".equals(dh)&&!"".equals(dlmc)){
				whereCase = "DH='"+dh+"' AND DLMC='"+dlmc+"'";
				clearSql = "DELETE LCB_PT_MIS WHERE DLBH='"+dh+"' AND DLMC='"+dlmc+"'";
			}
			fieldValues = pUtil.getFieldsByCondition(whereCase, strFields);
			if(fieldValues!= null){
				if(!"".equals(dh)){
					DBHandler.execute(clearSql);
				}
				for(int i = 0; i < fieldValues.length; i ++){
					id = CreateSequence.getMaxForId("LCB_PT_MIS","CODE", 12);
					sql = " INSERT INTO LCB_PT_MIS (CODE,DLBH,DLMC,QMZ,BMZ,ZB) VALUES ('"+id+"','"+fieldValues[i][0]+"','"+fieldValues[i][1]+"','"+fieldValues[i][2]+"','"+fieldValues[i][3]+"','"+fieldValues[i][4]+"')";
					System.err.println(sql);
				    DBHandler.execute(sql);
				}
				out.write("ͬ���ɹ�,����¼��"+fieldValues.length);
			}else{
				out.write("ͬ��ʧ�ܣ�");
			}
		}catch(Throwable ex){
			out.write("ͬ��ʧ��");
			ex.printStackTrace();
			logger.error("ͬ��ͼ��LCB_PT���ݵ�LCB_PT_MIS���ݿ����:"+ex.getMessage()+" sql="+sql);
		}finally{
			sde.closeAO();
		}
		return null;
	}
}
