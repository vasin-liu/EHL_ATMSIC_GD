package com.ehl.tfm.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.util.CreateSequence;
import com.ehl.webgis.data.SDEAccess;
import com.ehl.webgis.util.LineUtil;

public class LineFromLinkGisImpl {
	static Logger  logger = Logger.getLogger(LineFromLinkGisImpl.class);
	private static Object[][] linkDirGisArray = null;
	/**
     * <b>获取连线及所含路段，路段点集</b>
     * @return map 连线数据.lines-连线id，连线名称，方向，占位空串，所含路段编号（“;”相隔），所属机构编号，机构名称，所属连线编号
     *                     linespoint-所有路段信息，路段编号和路段点集（点间“;”相隔）
     */
	public  Map<String,Object[][]> getLinkDirMap() throws Exception {
		String layerName = "LL_PL"; // 定义图层名称
		SDEAccess sde = new SDEAccess(); // 定义SDE
		Map map = new HashMap();
		String sqlWhere = "";
		String sql = "select linkdirid,linkdirname,linkdirtype,'',getroadsegids(t.linkid),regionid,regionname,linkid from t_tfm_link_dir t WHERE 1=1  " ;
		Object[][] lines = DBHandler.getMultiResult(sql);
		logger.error("获取用于同步连线信息"+lines.length+"条");
		LineUtil pUtil = null;
		String strWhere = "";
		if(lines != null){
			map.put("lines", lines);
			pUtil = new LineUtil(sde.initConnection(), layerName);
			sqlWhere = LineFromCSDLWImpl.getSqlWhere(lines);
		    //获取连线SDE数据
			String strFields = "LDBH,SHAPE";
			Object[][] fieldValues = pUtil.getFieldsByCondition("", strFields);
			logger.error("获取用于同步地图数据"+fieldValues.length+"条");
			map.put("linespoint", fieldValues);
	    }
		sde.closeAO();
	    return map;
	}
	/**
     * <b>清空T_TFM_ROADSEGPOINTS中全部数据</b>
     * @return 清空结果标志.
     */
	public static boolean doDeleteLinkdir(){
	    String sql = "DELETE FROM T_TFM_ROADSEGPOINTS";	
	    boolean res = DBHandler.execute(sql);
	    return res;
	}
	
	  /**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：获取连线对应的点集Map
	 * @参数：
	 * @创建日期：2010-5-4
	 * @返回：Map 连线数据（key-linkdirid,value-点集）
	 */
    private static Map fillLinkPoint(Object[][] links,Object[][] segPoints) {
    	if(links == null || segPoints == null){
    		return null;
    	}
    	Map map = new HashMap();
    	StringBuffer pointBuffer = new StringBuffer();
    	for(int i = 0; i < links.length; i++){
    		for(int j = 0; j < segPoints.length; j ++){
    			String segId = StringHelper.obj2str(segPoints[j][0], "");
		        if(StringHelper.obj2str(links[i][4], "").indexOf(segId) > -1){
		        	if(pointBuffer.length() == 0){
		        		pointBuffer.append(segPoints[j][1].toString());
		        	}else{
		        		pointBuffer.append("seg"+segPoints[j][1].toString());
		        	}
		        }		        
    		}
    		map.put(links[i][0].toString(), pointBuffer.toString());
    		pointBuffer.delete(0, pointBuffer.length());
    	}
    	logger.error("连线对应地图信息完成 "+map.size()+" 条");
    	return map;
    }
    /**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：向t_gis_linkdir_segs插入整理后的数据
	 * @参数：
	 * @创建日期：2010-5-4
	 * @返回：num-成功插入的记录数
	 */
    public static int doSynLinkDir(Object[][] lines,Object[][] fieldValues){
    	//Map segMap = fillLinkPoint(lines,fieldValues);
    	Map map = setToMap(fieldValues,0,1);;
		//逐条连线更新
		String sql = "";
		boolean flag = true;
		int num = 0;
		String[] seges = null;
		String roadSegIdStr = "";
		String segPontsStr = "";
		StringBuffer temSegPointBuffer = new StringBuffer();//存储插入标志的路段点集
		String[] temSegPointArray = null;
        for(int i = 0; i < lines.length; i ++){
        	flag = true;
        	//更新路段空间位置信息
        	seges = StringHelper.obj2str(lines[i][4], "").split(",");
        	for(int j = 0; j < seges.length; j ++){
        		temSegPointBuffer = new StringBuffer();
        		segPontsStr = StringHelper.obj2str(map.get(seges[j]), "");
        		if(segPontsStr.length() > 4000){
        			System.out.println("====================================="+segPontsStr);
        			getSplitSegpoint(temSegPointBuffer,segPontsStr);
        			temSegPointArray = temSegPointBuffer.toString().split("@");
        			for(int k = 0; k < temSegPointArray.length; k ++){
        				roadSegIdStr = CreateSequence.getMaxForId("T_TFM_ROADSEGPOINTS","ID", 12);
                		sql = "INSERT INTO T_TFM_ROADSEGPOINTS (ID,Linkdirid,ROADID,ROADPOINTS) VALUES ('"+roadSegIdStr+"','"+lines[i][0]+"','"+seges[j]+"','"+temSegPointArray[k]+"')";
        			}
        			continue;
        		}else{
        			roadSegIdStr = CreateSequence.getMaxForId("T_TFM_ROADSEGPOINTS","ID", 12);
            		sql = "INSERT INTO T_TFM_ROADSEGPOINTS (ID,Linkdirid,ROADID,ROADPOINTS) VALUES ('"+roadSegIdStr+"','"+lines[i][0]+"','"+seges[j]+"','"+map.get(seges[j])+"')";
        		}
        		//logger.error("segpoints==============="+StringHelper.obj2str(map.get(seges[j]), "").length());
        		if(!DBHandler.execute(sql)){
        			flag = false;
        		}
        	}
        	if(flag){
        		num ++;
        	}
        }
        return num;
    }
    /**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：处理点多的路段,100个点之间插入标志
	 * @参数：
	 * @创建日期：2010-08-26
	 * @返回：插入标志的路段点集
	 */
    public static String getSplitSegpoint(StringBuffer splitSegpointStr,String segpointStr){
    	if(segpointStr != null){
    		int index =  segpointStr.indexOf(";", 30*100);
    		if(segpointStr.split(";").length >100){
    			splitSegpointStr.append(segpointStr.substring(0,index+1));
    			splitSegpointStr.append("@");
    			getSplitSegpoint(splitSegpointStr,segpointStr.substring(index+1));
    		}else{
    			splitSegpointStr.append(segpointStr);
    		}
    	}
    	
    	return splitSegpointStr.toString();
    }
    
	/**
	 * <b>获取全部数据</b>
	 * param: 无
	 * @return res-二维数组:连线编号，连线名称，连线方向，路段编号，路段点集，路况,第一视频编号，第二视频编号
	 */
	public static Object[][] getSegsGIS(){
	    String sql = "SELECT LINKID,LINKNAME,DIRECTION,ROADSEGID,'',ROADSTATUS,STARTVIDEOID,ENDVIDEOID FROM V_TFM_LINKDIRFLOW";
	    //添加sql过滤条件 lidq 2011年1月22日
	    String segpointsSql = "SELECT LINKDIRID,ROADPOINTS FROM T_TFM_ROADSEGPOINTS WHERE ROADPOINTS !='null' AND ROADPOINTS IS NOT NULL";
	    Object[][] lineInfo = DBHandler.getMultiResult(sql);
	    Object[][] segInfo = DBHandler.getMultiResult(segpointsSql);
	    //填充连线空间信息
        //wujh 2010年 8月18日
	    StringBuffer buffer = new StringBuffer();
	    if(lineInfo != null){
            for(int i = 0; i < lineInfo.length; i ++){
            	buffer = new StringBuffer();
                //形成连线空间信息
        	    if(segInfo != null){
                    for(int j = 0; j < segInfo.length; j ++){
                    	if(!segInfo[j][0].toString().equals(lineInfo[i][0].toString())){
                    		continue;
                    	}
                    	if(buffer.length() == 0){
                    		buffer.append(segInfo[j][1]);
                    	}else{
                    		buffer.append("seg");
                    		buffer.append(segInfo[j][1]);
                    	}
                    }
        	    }
            	lineInfo[i][4] = buffer.toString();
            }
	    }
	    linkDirGisArray = lineInfo;//存入内存
	    return lineInfo;
	}
	/**
	 * <b>数组存入Map</b>
	 * param: res-要存入Map的二维数组;keyIndex-key值列下标 valueIndex－value值列下标
	 * @return
	 */
	public static Map setToMap(Object[][] res,int  keyIndex,int  valueIndex){
		Map map = new HashMap();
		if(res != null){
			for(int i = 0; i < res.length; i ++ ){
				if(res[i][valueIndex] == null){
					continue;
				}
				map.put(res[i][keyIndex], res[i][valueIndex]);
			}
		}
	    return map;
	}
	
	/**
	 * <b>获取路段空间信息</b>
	 * param: linkDirIdStr-连线编号
	 * @return res-二维数组:连线编号，连线名称，连线方向，路段编号，路段点集，路况
	 */
	public static String getSegPoints(String linkDirIdStr){
	    String sql = "SELECT roadId,roadpoints FROM T_TFM_ROADSEGPOINTS WHERE linkdirid='"+linkDirIdStr+"'";	
	    Object[][]  segPoints = DBHandler.getMultiResult(sql);
	    StringBuffer buffer = new StringBuffer();
	    //形成连线空间信息
	    if(segPoints != null){
            for(int i = 0; i < segPoints.length; i ++){
            	if(segPoints[i][1] == null || segPoints[i][1].toString().equals("null")){
            		continue;
            	}
            	if(i == segPoints.length-1){
            		buffer.append(segPoints[i][1]);
            	}else{
            		buffer.append(segPoints[i][1] +"seg");
            	}
            }
	    }
	    return buffer.toString();
	}
	
	/**
	 * <b>获取内存中连线数据（包括对应点集）</b>
	 * param: 无
	 * @return res-二维数组.
	 */
	public static Object[][] getStaticSegsGIS(){
	    return linkDirGisArray;
	}
	
	//从拥堵事件表和lcb mis表读取相关信息
	public static Object[][] getInfoOnLcbMis(){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT TAA.ROADID,TAA.ROADNAME,TAA.ROADDIRECTION, '44','55','拥堵','77',taa.alarmid");
		sb.append(" FROM T_ATTEMPER_ALARM TAA WHERE EVENTTYPE='001002' AND EVENTSTATE='570001'");
		Object[][] lineInfo = DBHandler.getMultiResult(sb.toString());//所有拥堵路段
		StringBuffer segpointsSql = new StringBuffer();
		segpointsSql.append("SELECT LCB.DLBH,LCB.DLMC,LCB.ZB,taa.alarmid,lcb.qmz FROM LCB_PT_MIS LCB,T_ATTEMPER_ALARM TAA");
		segpointsSql.append(" WHERE  TAA.ROADID=LCB.DLMC and taa.EVENTTYPE = '001002'   AND taa.EVENTSTATE = '570001'   AND to_number(LCB.QMZ)>=to_number(TAA.KMVALUE) AND to_number(LCB.QMZ)<=to_number(TAA.ENDKMVALUE)+1");
		segpointsSql.append(" ORDER BY LCB.DLBH,LCB.DLMC,TO_NUMBER(LCB.QMZ) ASC");//拥堵时间范围要加上 介于开始和结束内
		Object[][] segInfo = DBHandler.getMultiResult(segpointsSql.toString());
		
		StringBuffer buffer = new StringBuffer();
	    if(lineInfo != null){
//	    	String roadid = "";
//	    	String roadname = "";
	    	String alarmid = "";
	    	//String dlbh = "";
	    	//String dlmc = "";
	    	String segalarmid = "";
            for(int i = 0; i < lineInfo.length; i ++){
            	buffer = new StringBuffer();
                //形成连线空间信息
        	    if(segInfo != null){
        	    	alarmid = StringHelper.obj2str(lineInfo[i][7],"");
//        	    	roadname = StringHelper.obj2str(lineInfo[i][1],"");
        	    	if("".equals(alarmid)){
        	    		continue;
        	    	}
        	    	
                    for(int j = 0; j < segInfo.length; j++){
                    	//dlbh = StringHelper.obj2str(segInfo[j][0],"");
                    	segalarmid = StringHelper.obj2str(segInfo[j][3],"");
                    	if(segalarmid.equals(alarmid)){
                    		if(buffer.length() == 0){
                    			buffer.append(segInfo[j][2]);
                    		}else{
                    			buffer.append(";");
                    			buffer.append(segInfo[j][2]);
                    		}
                    	}
                    }
        	    }
            	lineInfo[i][4] = buffer.toString();
            }
	    }
	    linkDirGisArray = lineInfo;//存入内存
	    return lineInfo;
	}
}
