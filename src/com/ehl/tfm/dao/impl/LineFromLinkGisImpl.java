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
     * <b>��ȡ���߼�����·�Σ�·�ε㼯</b>
     * @return map ��������.lines-����id���������ƣ�����ռλ�մ�������·�α�ţ���;�������������������ţ��������ƣ��������߱��
     *                     linespoint-����·����Ϣ��·�α�ź�·�ε㼯����䡰;�������
     */
	public  Map<String,Object[][]> getLinkDirMap() throws Exception {
		String layerName = "LL_PL"; // ����ͼ������
		SDEAccess sde = new SDEAccess(); // ����SDE
		Map map = new HashMap();
		String sqlWhere = "";
		String sql = "select linkdirid,linkdirname,linkdirtype,'',getroadsegids(t.linkid),regionid,regionname,linkid from t_tfm_link_dir t WHERE 1=1  " ;
		Object[][] lines = DBHandler.getMultiResult(sql);
		logger.error("��ȡ����ͬ��������Ϣ"+lines.length+"��");
		LineUtil pUtil = null;
		String strWhere = "";
		if(lines != null){
			map.put("lines", lines);
			pUtil = new LineUtil(sde.initConnection(), layerName);
			sqlWhere = LineFromCSDLWImpl.getSqlWhere(lines);
		    //��ȡ����SDE����
			String strFields = "LDBH,SHAPE";
			Object[][] fieldValues = pUtil.getFieldsByCondition("", strFields);
			logger.error("��ȡ����ͬ����ͼ����"+fieldValues.length+"��");
			map.put("linespoint", fieldValues);
	    }
		sde.closeAO();
	    return map;
	}
	/**
     * <b>���T_TFM_ROADSEGPOINTS��ȫ������</b>
     * @return ��ս����־.
     */
	public static boolean doDeleteLinkdir(){
	    String sql = "DELETE FROM T_TFM_ROADSEGPOINTS";	
	    boolean res = DBHandler.execute(sql);
	    return res;
	}
	
	  /**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵������ȡ���߶�Ӧ�ĵ㼯Map
	 * @������
	 * @�������ڣ�2010-5-4
	 * @���أ�Map �������ݣ�key-linkdirid,value-�㼯��
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
    	logger.error("���߶�Ӧ��ͼ��Ϣ��� "+map.size()+" ��");
    	return map;
    }
    /**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵������t_gis_linkdir_segs��������������
	 * @������
	 * @�������ڣ�2010-5-4
	 * @���أ�num-�ɹ�����ļ�¼��
	 */
    public static int doSynLinkDir(Object[][] lines,Object[][] fieldValues){
    	//Map segMap = fillLinkPoint(lines,fieldValues);
    	Map map = setToMap(fieldValues,0,1);;
		//�������߸���
		String sql = "";
		boolean flag = true;
		int num = 0;
		String[] seges = null;
		String roadSegIdStr = "";
		String segPontsStr = "";
		StringBuffer temSegPointBuffer = new StringBuffer();//�洢�����־��·�ε㼯
		String[] temSegPointArray = null;
        for(int i = 0; i < lines.length; i ++){
        	flag = true;
        	//����·�οռ�λ����Ϣ
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
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵�����������·��,100����֮������־
	 * @������
	 * @�������ڣ�2010-08-26
	 * @���أ������־��·�ε㼯
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
	 * <b>��ȡȫ������</b>
	 * param: ��
	 * @return res-��ά����:���߱�ţ��������ƣ����߷���·�α�ţ�·�ε㼯��·��,��һ��Ƶ��ţ��ڶ���Ƶ���
	 */
	public static Object[][] getSegsGIS(){
	    String sql = "SELECT LINKID,LINKNAME,DIRECTION,ROADSEGID,'',ROADSTATUS,STARTVIDEOID,ENDVIDEOID FROM V_TFM_LINKDIRFLOW";
	    //���sql�������� lidq 2011��1��22��
	    String segpointsSql = "SELECT LINKDIRID,ROADPOINTS FROM T_TFM_ROADSEGPOINTS WHERE ROADPOINTS !='null' AND ROADPOINTS IS NOT NULL";
	    Object[][] lineInfo = DBHandler.getMultiResult(sql);
	    Object[][] segInfo = DBHandler.getMultiResult(segpointsSql);
	    //������߿ռ���Ϣ
        //wujh 2010�� 8��18��
	    StringBuffer buffer = new StringBuffer();
	    if(lineInfo != null){
            for(int i = 0; i < lineInfo.length; i ++){
            	buffer = new StringBuffer();
                //�γ����߿ռ���Ϣ
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
	    linkDirGisArray = lineInfo;//�����ڴ�
	    return lineInfo;
	}
	/**
	 * <b>�������Map</b>
	 * param: res-Ҫ����Map�Ķ�ά����;keyIndex-keyֵ���±� valueIndex��valueֵ���±�
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
	 * <b>��ȡ·�οռ���Ϣ</b>
	 * param: linkDirIdStr-���߱��
	 * @return res-��ά����:���߱�ţ��������ƣ����߷���·�α�ţ�·�ε㼯��·��
	 */
	public static String getSegPoints(String linkDirIdStr){
	    String sql = "SELECT roadId,roadpoints FROM T_TFM_ROADSEGPOINTS WHERE linkdirid='"+linkDirIdStr+"'";	
	    Object[][]  segPoints = DBHandler.getMultiResult(sql);
	    StringBuffer buffer = new StringBuffer();
	    //�γ����߿ռ���Ϣ
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
	 * <b>��ȡ�ڴ����������ݣ�������Ӧ�㼯��</b>
	 * param: ��
	 * @return res-��ά����.
	 */
	public static Object[][] getStaticSegsGIS(){
	    return linkDirGisArray;
	}
	
	//��ӵ���¼����lcb mis���ȡ�����Ϣ
	public static Object[][] getInfoOnLcbMis(){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT TAA.ROADID,TAA.ROADNAME,TAA.ROADDIRECTION, '44','55','ӵ��','77',taa.alarmid");
		sb.append(" FROM T_ATTEMPER_ALARM TAA WHERE EVENTTYPE='001002' AND EVENTSTATE='570001'");
		Object[][] lineInfo = DBHandler.getMultiResult(sb.toString());//����ӵ��·��
		StringBuffer segpointsSql = new StringBuffer();
		segpointsSql.append("SELECT LCB.DLBH,LCB.DLMC,LCB.ZB,taa.alarmid,lcb.qmz FROM LCB_PT_MIS LCB,T_ATTEMPER_ALARM TAA");
		segpointsSql.append(" WHERE  TAA.ROADID=LCB.DLMC and taa.EVENTTYPE = '001002'   AND taa.EVENTSTATE = '570001'   AND to_number(LCB.QMZ)>=to_number(TAA.KMVALUE) AND to_number(LCB.QMZ)<=to_number(TAA.ENDKMVALUE)+1");
		segpointsSql.append(" ORDER BY LCB.DLBH,LCB.DLMC,TO_NUMBER(LCB.QMZ) ASC");//ӵ��ʱ�䷶ΧҪ���� ���ڿ�ʼ�ͽ�����
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
                //�γ����߿ռ���Ϣ
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
	    linkDirGisArray = lineInfo;//�����ڴ�
	    return lineInfo;
	}
}
