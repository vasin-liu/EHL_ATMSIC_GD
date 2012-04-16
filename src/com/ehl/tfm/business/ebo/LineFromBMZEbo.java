package com.ehl.tfm.business.ebo;

import java.util.*;

import org.apache.log4j.Logger;

import com.appframe.utils.StringHelper;
import com.ehl.tfm.OffsetBean;
import com.ehl.tfm.TfmCommon;
import com.ehl.tfm.TransitTOScreenPixel;
import com.ehl.tfm.VmlPolyLine;
import com.ehl.tfm.action.TfmMap;
import com.ehl.tfm.business.ebi.FlowLineEbi;
import com.ehl.tfm.dao.DaoFactory;
import com.ehl.tfm.dao.impl.LineFromLinkGisImpl;
import com.ehl.webgis.data.SDEAccess;

public class LineFromBMZEbo implements FlowLineEbi{
	static Logger  logger = Logger.getLogger(TfmMap.class);
	/**
     * <b>��ȡ������vml�ַ���.</b>
     * param:deptId-����ID ��Ϊ�����ѯȫ��������Ϣ 
     * param:roadStatus-Ҫ��ȡ��·�����ࣨ��ӵ�¡�������ͨ������ӵ������
     * param :zoomLevel- ��ͼ�Ŵ���
     * param:isRedraw-�Ƿ����ػ�
     * @return.
     */
	public String getLineVmlStr(String deptId,String roadStatus,int zoomLevel,boolean isRedraw) throws Exception {
		VmlPolyLine vmlPolyLine = new VmlPolyLine(new TransitTOScreenPixel(zoomLevel));
		String lineStr = ""; 
		StringBuffer xmlData = new StringBuffer("");
		Map map = new HashMap();
		if(isRedraw){//������ػ�����ڴ��л�ȡ·������
			map = DaoFactory.getLineDataDao("2").getRedrawLineMap();//"2"-�㶫ģʽ������t_gis_lcbpt���������·����֯�������ݣ�
		}else{
			map = DaoFactory.getLineDataDao("2").getLineMap(deptId, roadStatus);//"2"-�㶫ģʽ������t_gis_lcbpt���������·����֯�������ݣ�
		}
		Object[][] lines = null;	
		Object[][] fieldValues = null;	
		if(map.get("lines") != null){
			lines = (Object[][])map.get("lines");	
		}
		if(map.get("linespoint") != null){
			fieldValues = (Object[][])map.get("linespoint");	
		}
		if(fieldValues != null){
			Collection perLineCol = new ArrayList();//����������·�μ���
			String[][] infoOfLines  = null;
			String startPoint = "";//������ʼ��
			String endPoint = "";//���߽�β��
			String lineColor = "";
			String lineId = "";
			double vmlLineWidth = TfmCommon.getLineWidth(zoomLevel);
			infoOfLines = getLineInfoArray(lines,fieldValues);//��֯������Ϣ����ά���飨��·���ƣ���·����·������γ�ȣ�
			//��֯������Ϣ��
			for(int m = 0; m < infoOfLines.length; m++)
			{    
				if(roadStatus != null && roadStatus.length() > 0){
                    //���˵���Ҫ����ʾ������
					if(roadStatus.equals("block") && StringHelper.obj2str(infoOfLines[m][2], "").equals("ӵ��")){
				        
			        }else if(roadStatus.equals("crowd") && StringHelper.obj2str(infoOfLines[m][2], "").equals("ӵ��")){
			        
			        }else if(roadStatus.equals("flow") && StringHelper.obj2str(infoOfLines[m][2], "").equals("��ͨ")){
				        
				    }else if(roadStatus.equals("all")){
				        
				    }else{
				    	continue;
				    }
				}
                //��������ƫ����
				startPoint = StringHelper.obj2str(infoOfLines[0][3], "").split(";")[0];//�������
				endPoint = StringHelper.obj2str(infoOfLines[infoOfLines.length-1][3], "").split(";")[0];//�����յ�
				OffsetBean offbean =  TfmCommon.calcOffset(zoomLevel, startPoint, endPoint);//��������ƫ����
				lineColor = TfmCommon.getLineColor(infoOfLines[m][2]);//����ɫ
				lineId = StringHelper.obj2str(infoOfLines[m][0], "");//�߱��
				//����
				if(StringHelper.obj2str(infoOfLines[m][1], "").equals("����") || StringHelper.obj2str(infoOfLines[m][1], "").equals("0"))
				{
					lineStr += vmlPolyLine.createVmlPolyLine(infoOfLines[m][3],offbean.getOffsetUpX(),offbean.getOffsetUpY(),
							lineColor,"����",lineId,vmlLineWidth);	
				}else{
					lineStr += vmlPolyLine.createVmlPolyLine(infoOfLines[m][3],offbean.getOffsetDownX(),offbean.getOffsetDownY(),
							lineColor,"����",lineId,vmlLineWidth);	
				}
	 			xmlData.append(infoOfLines[m][0] + "<col>");//·�߱��
				xmlData.append( infoOfLines[m][0] + "<col>");//·������
				xmlData.append(TfmCommon.getDirectionDesc(infoOfLines[m][1]) + "<col>");//����
				xmlData.append(TfmCommon.getLineStatusDesc(infoOfLines[m][2]) + "<col>");//·��״��
				
				if(infoOfLines == null){
					xmlData.append(0);//����·������	
				}else{
					xmlData.append(infoOfLines.length);//����·������	
				}
				if(m == infoOfLines.length-1){
					
				}else{
					xmlData.append("<road>");	
				}
			}
			xmlData.append("<linevml>");
			xmlData.append(lineStr);
		}
	    return xmlData.toString();
   }
	/**
     * <b>.�ϲ�������ϢΪһ����ά����</b>
     * param:lines-������Ϣ
     * linesPoint-���߿ռ�λ����Ϣ������·���ƺ�ǧ��ֵ����
     * @return ǰ�ߴ��ڵ��ں��߷���true.
     */
	public static String[][] getLineInfoArray(Object[][] lines,Object[][] linesPoint) throws Exception{
		 if(lines == null || linesPoint == null ){
			 return null;
		 }
		 String[][] lineInfoArray = new String[lines.length][4];
		 StringBuffer buffer = new StringBuffer();
		 String startpos = "";
		 String endpos = "";
		 String roadName = "";
		 boolean flag = false;//��ʶ��ǰ�����Ƿ����ҵ����Ӧ��γ��
		 for(int i = 0; i < lines.length; i ++){
			 flag = false;//����
			 buffer.delete(0, buffer.length());
			 startpos = StringHelper.obj2str(lines[i][1], "").substring(1).split("\\+")[0];
			 endpos = StringHelper.obj2str(lines[i][2], "").substring(1).split("\\+")[0];
			 roadName = StringHelper.obj2str(lines[i][0], "");
			 lineInfoArray[i][0] = roadName+startpos+"km-"+endpos +"km";//��·����
			 lineInfoArray[i][1] = StringHelper.obj2str(lines[i][3], "");//����
			 lineInfoArray[i][2] = StringHelper.obj2str(lines[i][4], "");//·��
			 for(int j = 0; j < linesPoint.length; j ++){
				 if(roadName.equals(StringHelper.obj2str(linesPoint[j][0],""))){
					 flag = true;
					 if(buffer.length() == 0){
						 buffer.append(StringHelper.obj2str(linesPoint[j][1],"")+","+StringHelper.obj2str(linesPoint[j][2],"")); 
					 }else{
						 buffer.append(";"+StringHelper.obj2str(linesPoint[j][1],"")+","+StringHelper.obj2str(linesPoint[j][2],""));
					 }
				 }else{
					 if(flag){
						 break;
					 }
				 }
			 }
			 lineInfoArray[i][3] = buffer.toString();//���߾�γ���ַ���
		 }
		 return lineInfoArray;
		 
	}
	
	/**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵����ͬ��t_tfm_linkdir,lcb_ptͼ���t_gis_linkdir_segs������
	 * @�������ڣ�2010-5-4
	 * @���أ�ͬ�������ʾ��
	 */
    public String synLinkDir() throws Exception{
    	Map map = new HashMap();
    	String tipStr = "";
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
			Map segMap = null;
			Object[][] linkSegs = null;
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
     * <b>.�����ʼ����ͬ��ֻȡ��ʼʱ�������һ��</b>
     * param:lines-������Ϣ
     * @return ȥ���ظ���¼��������Ϣ����.
     */
	public static Object[][] removeRepeat(Object[][] lines){
		Object[][] res = new Object[lines.length][];
		int len = res.length;
		Object[][] newLines = null;
		if(lines != null){
			boolean flag = true;
		    for(int i = 0; i < lines.length-1; i ++){
		        for(int j = i+1; j < lines.length; j ++){
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
     * <b>.�Ƚ������ַ�����С</b>
     * param:lines-������Ϣ
     * @return ǰ�ߴ��ڵ��ں��߷���true.
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
     * <b>.Collection��һά����ת�浽��ά������</b>
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
//		Object[][] r1 = {{"12","·��2","����","ӵ��","11","K050+000","K070+000","2009-02-01 22:22:22","2009-08-08 09:09:09"},{"11","·��1","����","ӵ��","12","K050+000","K070+000","2009-02-02 22:22:22","2009-08-08 09:09:09"}};
//		Object[][] r2 = {{"11","·��1","����","ӵ��","12","K050+000","K070+000","2009-02-02 22:22:22","2009-08-08 09:09:09"}};
		//Object[][] r3 = LineFromBMZ.removeRepeat(r1);
		String tt= "KO9";
		System.out.print("K078+000".split("\\+")[0]);
		
	}
		
}
