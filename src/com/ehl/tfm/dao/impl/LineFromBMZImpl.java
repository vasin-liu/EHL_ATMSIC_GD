package com.ehl.tfm.dao.impl;

import java.util.*;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.tfm.action.TfmMap;
import com.ehl.tfm.dao.dao.LineDataDao;
import com.ehl.webgis.data.SDEAccess;
import com.ehl.webgis.util.PointUtil;

public class LineFromBMZImpl implements LineDataDao{
	static Logger  logger = Logger.getLogger(TfmMap.class);
	private String layerName = "LCB_PT"; // 定义图层名称
	private SDEAccess sde = new SDEAccess(); // 定义SDE
	public static Map lineMap = null;
	/**
     * <b>获取当天最新流量数据并返回,.</b>
     * param:deptId-机构ID 不为空则查询全部流量信息
     * @return map lines-道路信息基本数据数组 linespoint-空间数据数组.
     */
	public Map<String,Object[][]> getLineMap(String deptId,String roadStatus) throws Exception {
		Map map = new HashMap();
		String sql = "SELECT NAME,STARTPOS,ENDPOS,decode(DIRECTION,'0','上行','1','下行') DIRECTION" +
				",decode(status,'0','畅通','1','拥挤','2','拥堵','') roadstatus FROM T_TFM_CURRENTFLOW_5M";
		Object[][] lines = DBHandler.getMultiResult(sql);
		PointUtil pUtil = null;
		String sqlWhereStr = "";
		Object[][] res = null;
		if(lines != null){
			sqlWhereStr = getSqlWhere(lines);
//			pUtil = new PointUtil(sde.initConnection(), layerName);
//			Object[][] oResult = pUtil.getFieldsByCondition(sqlWhereStr, "dlmc,qmz");
//			sde.closeAO();	
			String lineInfoSql = "SELECT DLMC, LONGITUDE,LATITUDE FROM T_GIS_LCBPT WHERE " +sqlWhereStr +"ORDER BY DLMC,QMZ"; 
			res = DBHandler.getMultiResult(lineInfoSql);
		}
		map.put("lines", lines);
		map.put("linespoint", res);
		lineMap = map;
	    return map;
    }
	/**
     * <b>从内存中获取当天最新流量数据并返回</b>
     * @return map 路况信息数据.
     */
	public Map<String, Object[][]> getRedrawLineMap() throws Exception {
		return LineFromBMZImpl.lineMap;
	}
	
	/**
     * <b>获取条件字符串</b>
     * @return String.
     */
	public static String getSqlWhere(Object[][] lines) throws Exception {
		StringBuffer  sqlWhereBuffer = new StringBuffer();
		if(lines == null || lines.length ==0){
			return "";
		}
		String QMZStr = "";
		String startQMZStr = "";
		String endQMZStr = "";
		for(int i = 0; i < lines.length; i ++){
			startQMZStr = StringHelper.obj2str(lines[i][1], "").substring(1).split("\\+")[0];
			endQMZStr = StringHelper.obj2str(lines[i][2], "").substring(1).split("\\+")[0];
			if(i == 0){
				sqlWhereBuffer.append(" (DLMC='"+lines[i][0]+"' AND QMZ >= '"+startQMZStr+"' AND QMZ <= '"+endQMZStr+"')");
			}else{
				sqlWhereBuffer.append(" OR (DLMC='"+lines[i][0]+"' AND QMZ >= '"+startQMZStr+"' AND QMZ <= '"+endQMZStr+"')");
			}
		}
		return sqlWhereBuffer.toString();
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
		        	System.out.println("---------------------"+i+"------"+j);
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
