package com.ehl.tfm.business.ebo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.appframe.utils.StringHelper;
import com.ehl.tfm.OffsetBean;
import com.ehl.tfm.TfmCommon;
import com.ehl.tfm.TransitTOScreenPixel;
import com.ehl.tfm.VmlPolyLine;
import com.ehl.tfm.business.ebi.FlowLineEbi;
import com.ehl.tfm.dao.impl.LineFromLinkGisImpl;

public class LineFromDatabase implements FlowLineEbi{
	static Logger  logger = Logger.getLogger(LineFromDatabase.class);

	/**
     * <b>��ȡ�߶��ַ���.</b>
     * param:deptId-����ID ��Ϊ�����ѯȫ��������Ϣ 
     * param:roadStatus-Ҫ��ȡ��·�����ࣨ��block-ӵ�¡�����flow-��ͨ������crowd-ӵ����,"all"��
     * param:zoomLevel-��ͼ�Ŵ���
     * param:isRedraw-�Ƿ����ػ�
     * @return.
     */
	public String  getLineVmlStr(String deptId,String roadStatus,int zoomLevel,boolean isRedraw) throws Exception {
		VmlPolyLine vmlPolyLine = new VmlPolyLine(new TransitTOScreenPixel(zoomLevel));
		StringBuffer lineStr = new StringBuffer("");
		StringBuffer xmlData = new StringBuffer("");
		Object[][] linkDirFlowArray = null;
		try{
			if(isRedraw){//������ػ�����ڴ��л�ȡ·������
				linkDirFlowArray = LineFromLinkGisImpl.getStaticSegsGIS();//���߱�ţ��������ƣ����߷���·�α�ţ�·�ε㼯��·������һ��Ƶ��ţ��ڶ���Ƶ���
			}else{
				linkDirFlowArray = LineFromLinkGisImpl.getInfoOnLcbMis();
			}

			if(linkDirFlowArray != null){
				logger.error("·��������ȡ��������Ϣ��"+linkDirFlowArray.length+"��");
				Collection perLineCol = new ArrayList();//����������·�μ���
				Object[] pointsOfLine  = null;
				String linePoints = "";
				Object[][] segsOfLine = null;
				String lineStartPoint = "";
				String lineEndSeg = "";
				String lineColor = "";
				String lineId = "";
				String[]lineAll=null;
				double vmlLineWidth = TfmCommon.getLineWidth(zoomLevel);
			
				for(int m = 0; m < linkDirFlowArray.length; m++){    
					if(roadStatus != null && roadStatus.length() > 0){
	                    //���˵���Ҫ����ʾ������
						if(roadStatus.equals("block") && StringHelper.obj2str(linkDirFlowArray[m][5], "").equals("ӵ��")){
					        
				        }else if(roadStatus.equals("crowd") && StringHelper.obj2str(linkDirFlowArray[m][5], "").equals("ӵ��")){
				        
				        }else if(roadStatus.equals("flow") && StringHelper.obj2str(linkDirFlowArray[m][5], "").equals("��ͨ")){
					        
					    }else if(roadStatus.equals("all")){
					        
					    }else{
					    	continue;
					    }
					}
					xmlData.append(StringHelper.obj2str(linkDirFlowArray[m][0],"") + "<col>");//·�߱��
					xmlData.append(StringHelper.obj2str(linkDirFlowArray[m][1],"") + "<col>");//·������
					xmlData.append(TfmCommon.getDirectionDesc(StringHelper.obj2str(linkDirFlowArray[m][2],"")) + "<col>");//����
					xmlData.append(TfmCommon.getLineStatusDesc(StringHelper.obj2str(linkDirFlowArray[m][5],"")) + "<col>");//·��״��
					xmlData.append(StringHelper.obj2str(linkDirFlowArray[m][6], "") + "<col>");//��һ��Ƶ���
					xmlData.append("��Ƶһ" + "<col>");//��һ��Ƶ����
					xmlData.append(StringHelper.obj2str(linkDirFlowArray[m][7], "") + "<col>");//�ڶ���Ƶ���
					xmlData.append("��Ƶ��"+ "<col>"); //�ڶ���Ƶ����
					
					//����
					linePoints = StringHelper.obj2str(linkDirFlowArray[m][4], "");
					if(linePoints.length() == 0 || linePoints.equals("null")){//����û�ж�Ӧ�ռ�����
						continue;
					}
					pointsOfLine = linePoints.split("seg");
					segsOfLine = arrayTo2Array(pointsOfLine,"");
					ArrayList<OffsetBean> obArr =new ArrayList<OffsetBean>();
	                //��������ƫ���� 
					for(int i=0;i<pointsOfLine.length;i++){
						lineAll=StringHelper.obj2str(pointsOfLine[i], "").split(";");
						lineStartPoint = lineAll[0];
						lineEndSeg = lineAll[lineAll.length-1];
						//�����ǵ͹���׮-->�߹���׮ 0�������Ǹ߹���׮-->�͹���׮  1 ���ھ�γ����������ͼ����ԭ�㲻ͬ����Ҫ�ߵ�����ϵ����ʼ���յ㻥��
						if(StringHelper.obj2str(linkDirFlowArray[m][2],"").equals("1")){
							obArr.add(TfmCommon.calcOffset(zoomLevel, lineStartPoint, lineEndSeg));
						}else{
							obArr.add(TfmCommon.calcOffset(zoomLevel, lineEndSeg, lineStartPoint));
						}
					}
					  
					
					lineColor = TfmCommon.getLineColor(linkDirFlowArray[m][5]); 
					
					lineId = StringHelper.obj2str(linkDirFlowArray[m][0], "");
					//����
					if(StringHelper.obj2str(linkDirFlowArray[m][2], "").equals("1") //0 ���� 1����
								|| StringHelper.obj2str(linkDirFlowArray[m][2], "").equals("0")){
						lineStr.append(vmlPolyLine.createVmlPolyLines(segsOfLine,obArr,true,
						lineColor,StringHelper.obj2str(linkDirFlowArray[m][2], ""),lineId,vmlLineWidth));	
						if(m != linkDirFlowArray.length-1){
							lineStr.append("lineEnd");
						}
						
					}else{
						lineStr.append(vmlPolyLine.createVmlPolyLines(segsOfLine,obArr,false,
								lineColor,StringHelper.obj2str(linkDirFlowArray[m][2], ""),lineId,vmlLineWidth));	
						if(m != linkDirFlowArray.length-1){
							lineStr.append("lineEnd");
						}
					}
					if(segsOfLine == null){
						xmlData.append(0);//����·������	
					}else{
						xmlData.append(segsOfLine.length);//����·������	
					}
					if(m == linkDirFlowArray.length-1){
						
					}else{
						xmlData.append("<road>");//���߼��á�<road>���ָ�	
					}
					segsOfLine = null;
				    perLineCol.clear();
				    
				}
				xmlData.append("<linevml>");//��·��Ϣ�ͻ����ַ����á�<linevml>�����
				xmlData.append(lineStr.toString());
				
			}else{
				return "noLinkDirData";
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("·����������"+ex.getMessage());
		}
	    return xmlData.toString();
   }
	/**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵����ͬ��t_tfm_linkdir,CSDLW_PLͼ���t_gis_linkdir_segs������
	 * @�������ڣ�2010-5-4
	 * @���أ�ͬ�������ʾ��
	 */
    public String synLinkDir() throws Exception{
    	Map map = new HashMap();
    	map = new LineFromLinkGisImpl().getLinkDirMap();//lines-���߻�����Ϣ linespoint-����·����Ϣ
    	Object[][] lines = null;	
		Object[][] fieldValues = null;	
		int successNum = 0;
		if(map.get("lines") != null){
			lines = (Object[][])map.get("lines");	
		}
		if(map.get("linespoint") != null){
			fieldValues = (Object[][])map.get("linespoint");	
		}
		int sum = lines.length;
		if(lines != null){//������������
			boolean flag = LineFromLinkGisImpl.doDeleteLinkdir();
			if(flag){//��ձ�t_gis_linkdir_segs�ɹ�
				successNum = LineFromLinkGisImpl.doSynLinkDir(lines, fieldValues);
			}
		}else{
			logger.error("���߸���ʧ�ܣ�");
			return "���߸���ʧ�ܣ�t_tfm_link_dirû������";
		}
    	return "�ܼƵ�·"+sum+"�����ɹ����£�"+successNum+"��" ;
    }
    /**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵����һά����ת��Ϊ��ά���飬��һ����ָ���������
	 * @������array-Ҫת����һά���飻fillObj-ָ����������
	 * @�������ڣ�2010-5-4
	 * @���أ�returnArray-ת�����ά����
	 */
   private static Object[][] arrayTo2Array(Object[] array,Object fillObj)throws Exception{
	   if(array == null){
		   return null;
	   }
	   Object[][] returnArray = new Object[array.length][2];
	   for(int i = 0; i < array.length; i++){
		   returnArray[i][0] = fillObj;
		   returnArray[i][1] = array[i];
	   }
	   return returnArray;
   }
}

