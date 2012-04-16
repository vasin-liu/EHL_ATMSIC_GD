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
 * @类型说明: 菏泽模式获取路况数据接口实现类
 * @创建者：zhaoyu
 * @创建日期：2008-10-16
 */
public class LineFromCSDLWImpl implements LineDataDao{
	static Logger  logger = Logger.getLogger(LineFromCSDLWImpl.class);
	private String layerName = "CSDLW_PL"; // 定义图层名称
	private SDEAccess sde = new SDEAccess(); // 定义SDE
	public static Map lineMap = new HashMap();
	/**
     * <b>获取当天最新流量数据并返回</b>
     * param:deptId-机构ID 不为空则查询全部流量信息
     * param:roadStatus-要获取的路况类型（拥堵，拥挤，畅通，all）
     * @return map 路况信息数据.
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
		    //获取连线SDE数据
			String strFields = "LDBH,SHAPE";
			Object[][] fieldValues = pUtil.getFieldsByCondition(strWhere, strFields);	
			map.put("linespoint", fieldValues);
	    }
		lineMap = map;
		sde.closeAO();
	    return map;
	}
	
	/**
     * <b>从内存中获取当天最新流量数据并返回</b>
     * @return map 路况信息数据.
     */
	public Map<String,Object[][]> getRedrawLineMap() throws Exception {
	    return LineFromCSDLWImpl.lineMap;
	}
	/**
     * <b>获取条件字符串</b>
     * param:lines-连线数据二维数组
     * @return 条件字符串.
     */
	public static String  getSqlWhere(Object[][] lines){
		String perInStr = "";    //条件in内的字符串
		int sqlWhereNum = 0;//记录where后条件数
		int segTotalNum = 0;//记录条件后IN括号内项数
		//监测路段总数
		//int segLen = 0;
		//int sqlSegLen = 0;
		StringBuffer strWhere = new StringBuffer(" LDBH in (");
		for(int i = 0; i < lines.length; i++){
			logger.error("[交通流量连线所含路段编号::]" + lines[i][4]);
			//连接路段编号
			String idStr = "";//记录一条连线的路段编号
		    String[] perLineSegIds = StringHelper.obj2str(lines[i][4],"").split(",");
		    //segLen += perLineSegIds.length;
		    for(int j = 0; j < perLineSegIds.length; j++){//每条路线所含路段编号
			   if(j == 0 ){
	   			 idStr += "'"+StringHelper.obj2str(perLineSegIds[j],"")+"'";
	   		   }else{
	   			 idStr += ",'"+StringHelper.obj2str(perLineSegIds[j],"")+"'";
	   		   }
		    }
	    	if((segTotalNum + perLineSegIds.length) > 950){//如果条件后IN括号内多余950项，则增加OR条件
	    		segTotalNum = 0;
	    		sqlWhereNum ++;
	    		if(sqlWhereNum ==1){//第一个OR条件，结束第一个in,当前连线路段编号放在下一个in条件
	    			strWhere.append(perInStr);
	    			strWhere.append(")");
	    			perInStr = "";
	    			if(perInStr.equals("")){
	    				perInStr += idStr;			
	    			}else{
	    				perInStr += ","+idStr;			
	    			}
	    			segTotalNum += perInStr.split(",").length;
	    		}else{//结束一个in条件,当前连线路段编号放在下一个in条件
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
	    		if(i == lines.length-1){//最后一条连线
	    			if(idStr.length() == 0){
	    				perInStr += idStr;
	    			}else{
	    				perInStr += ","+idStr;
	    			}
	    			if(sqlWhereNum ==0){//只有一个IN条件
	    				strWhere.append(perInStr);
	    				strWhere.append(")"); 	
		    		}else{//多余一个in条件干脆把最后一个也做成一个in条件
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
