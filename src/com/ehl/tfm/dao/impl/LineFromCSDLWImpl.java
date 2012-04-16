package com.ehl.tfm.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.tfm.dao.dao.LineDataDao;
import com.ehl.webgis.data.SDEAccess;
import com.ehl.webgis.util.LineUtil;
/**
 * @����˵��: ����ģʽ��ȡ·�����ݽӿ�ʵ����
 * @�����ߣ�zhaoyu
 * @�������ڣ�2008-10-16
 */
public class LineFromCSDLWImpl implements LineDataDao{
	static Logger  logger = Logger.getLogger(LineFromCSDLWImpl.class);
	private String layerName = "CSDLW_PL"; // ����ͼ������
	private SDEAccess sde = new SDEAccess(); // ����SDE
	public static Map lineMap = new HashMap();
	/**
     * <b>��ȡ���������������ݲ�����</b>
     * param:deptId-����ID ��Ϊ�����ѯȫ��������Ϣ
     * param:roadStatus-Ҫ��ȡ��·�����ͣ�ӵ�£�ӵ������ͨ��all��
     * @return map ·����Ϣ����.
     */
	public Map<String,Object[][]> getLineMap(String deptId,String roadStatus) throws Exception {
		Map map = new HashMap();
		String sqlWhere = "";
		if(roadStatus.length() > 0){
			sqlWhere += " AND  roadstatus = '"+roadStatus+"'";
		}
		String sql = "select linkid,linkname,direction,roadstatus,roadsegid from V_TFM_LINEFLOW WHERE 1=1  " ;
		Object[][] lines = DBHandler.getMultiResult(sql);
		LineUtil pUtil = null;
		String strWhere = "";
		if(lines != null){
			map.put("lines", lines);
			pUtil = new LineUtil(sde.initConnection(), layerName);
			sqlWhere = getSqlWhere(lines);
		    //��ȡ����SDE����
			String strFields = "LDBH,SHAPE";
			Object[][] fieldValues = pUtil.getFieldsByCondition(strWhere, strFields);	
			map.put("linespoint", fieldValues);
	    }
		lineMap = map;
		sde.closeAO();
	    return map;
	}
	
	/**
     * <b>���ڴ��л�ȡ���������������ݲ�����</b>
     * @return map ·����Ϣ����.
     */
	public Map<String,Object[][]> getRedrawLineMap() throws Exception {
	    return LineFromCSDLWImpl.lineMap;
	}
	/**
     * <b>��ȡ�����ַ���</b>
     * param:lines-�������ݶ�ά����
     * @return �����ַ���.
     */
	public static String  getSqlWhere(Object[][] lines){
		String perInStr = "";    //����in�ڵ��ַ���
		int sqlWhereNum = 0;//��¼where��������
		int segTotalNum = 0;//��¼������IN����������
		//���·������
		//int segLen = 0;
		//int sqlSegLen = 0;
		StringBuffer strWhere = new StringBuffer(" LDBH in (");
		for(int i = 0; i < lines.length; i++){
			logger.error("[��ͨ������������·�α��::]" + lines[i][4]);
			//����·�α��
			String idStr = "";//��¼һ�����ߵ�·�α��
		    String[] perLineSegIds = StringHelper.obj2str(lines[i][4],"").split(",");
		    //segLen += perLineSegIds.length;
		    for(int j = 0; j < perLineSegIds.length; j++){//ÿ��·������·�α��
			   if(j == 0 ){
	   			 idStr += "'"+StringHelper.obj2str(perLineSegIds[j],"")+"'";
	   		   }else{
	   			 idStr += ",'"+StringHelper.obj2str(perLineSegIds[j],"")+"'";
	   		   }
		    }
	    	if((segTotalNum + perLineSegIds.length) > 950){//���������IN�����ڶ���950�������OR����
	    		segTotalNum = 0;
	    		sqlWhereNum ++;
	    		if(sqlWhereNum ==1){//��һ��OR������������һ��in,��ǰ����·�α�ŷ�����һ��in����
	    			strWhere.append(perInStr);
	    			strWhere.append(")");
	    			perInStr = "";
	    			if(perInStr.equals("")){
	    				perInStr += idStr;			
	    			}else{
	    				perInStr += ","+idStr;			
	    			}
	    			segTotalNum += perInStr.split(",").length;
	    		}else{//����һ��in����,��ǰ����·�α�ŷ�����һ��in����
	    			strWhere.append(" or LDBH in (");
	    			strWhere.append(perInStr);
	    			strWhere.append(")"); 
	    			perInStr = "";
	    			if(perInStr.equals("")){
	    				perInStr += idStr;			
	    			}else{
	    				perInStr += ","+idStr;			
	    			}
	    			segTotalNum += perInStr.split(",").length;
	    		}
	    	 }else{
	    		if(i == lines.length-1){//���һ������
	    			if(idStr.length() == 0){
	    				perInStr += idStr;
	    			}else{
	    				perInStr += ","+idStr;
	    			}
	    			if(sqlWhereNum ==0){//ֻ��һ��IN����
	    				strWhere.append(perInStr);
	    				strWhere.append(")"); 	
		    		}else{//����һ��in�����ɴ�����һ��Ҳ����һ��in����
		    			strWhere.append(" or LDBH in (");
		    			strWhere.append(perInStr);
		    			strWhere.append(")"); 	
		    		}
	    		}else{
	    			if(perInStr.equals("")){
	    				perInStr += idStr;			
	    			}else{
	    				perInStr += ","+idStr;			
	    			}
	    			segTotalNum += perInStr.split(",").length;
	    		}
	    	}
		}
		//System.out.println("len===="+segLen +"sqlSegLen================="+strWhere.toString().split(",").length+"where==="+strWhere.toString());
		return strWhere.toString();
	}
	
	

	
}
