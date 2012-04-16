package com.ehl.tfm.business.ebo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.log4j.Logger;
import com.appframe.utils.StringHelper;
import com.ehl.tfm.OffsetBean;
import com.ehl.tfm.TfmCommon;
import com.ehl.tfm.TransitTOScreenPixel;
import com.ehl.tfm.VmlPolyLine;
import com.ehl.tfm.business.ebi.FlowLineEbi;
import com.ehl.tfm.dao.DaoFactory;
import com.ehl.tfm.dao.impl.LineFromCSDLWImpl;
import com.ehl.tfm.dao.impl.LineFromLinkGisImpl;

public class LineFromCSDLWEbo implements FlowLineEbi{
	
	static Logger  logger = Logger.getLogger(LineFromCSDLWEbo.class);
	
	/**
     * <b>��ȡ������vml�ַ���.</b>
     * param:deptId-����ID ��Ϊ�����ѯȫ��������Ϣ 
     * param:roadStatus-Ҫ��ȡ��·�����ࣨ��block-ӵ�¡�����flow-��ͨ������crowd-ӵ����,"all"��
     * param:zoomLevel-��ͼ�Ŵ���
     * param:isRedraw-�Ƿ����ػ�
     * @return.
     */
	public String  getLineVmlStr(String deptId,String roadStatus,int zoomLevel,boolean isRedraw) throws Exception {
		VmlPolyLine vmlPolyLine = new VmlPolyLine(new TransitTOScreenPixel(zoomLevel));
		String lineStr = ""; 
		StringBuffer xmlData = new StringBuffer("");
		Map map = new HashMap();
		if(isRedraw){//������ػ�����ڴ��л�ȡ·������
			map = DaoFactory.getLineDataDao("1").getRedrawLineMap();//"1"-����ģʽ������CSDLW_PL���������·����֯�������ݣ�
		}else{
			map = DaoFactory.getLineDataDao("1").getLineMap(deptId, roadStatus);//"1"-����ģʽ������CSDLW_PL���������·����֯�������ݣ�
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
			Object[][] segsOfLine  = null;
			String startSeg = "";
			String endSeg = "";
			String lineColor = "";
			String lineId = "";
			double vmlLineWidth = TfmCommon.getLineWidth(zoomLevel);
			for(int m = 0; m < lines.length; m++)
			{    
				if(roadStatus != null && roadStatus.length() > 0){
                    //���˵���Ҫ����ʾ������
					if(roadStatus.equals("block") && StringHelper.obj2str(lines[m][3], "").equals("ӵ��")){
				        
			        }else if(roadStatus.equals("crowd") && StringHelper.obj2str(lines[m][3], "").equals("ӵ��")){
			        
			        }else if(roadStatus.equals("flow") && StringHelper.obj2str(lines[m][3], "").equals("��ͨ")){
				        
				    }else if(roadStatus.equals("all")){
				        
				    }else{
				    	continue;
				    }
				}
				//��֯��������·�ε���ά����
			    for(int n = 0; n <fieldValues.length; n++)
			    {
			    	String segPoint = StringHelper.obj2str(fieldValues[n][0], "");
			        if(StringHelper.obj2str(lines[m][4], "").indexOf(segPoint) > -1){
			        	perLineCol.add(fieldValues[n]);
			        }	
			    }
			    if(!perLineCol.isEmpty())
			    {
			    	Iterator it = perLineCol.iterator();
			    	segsOfLine = new Object[perLineCol.size()][];
			    	int i = 0;
			    	while(it.hasNext())
			    	{
			    		segsOfLine[i] = (Object[])it.next();	
			    		i ++;
			    	}
                    //��������ƫ����
					startSeg = StringHelper.obj2str(segsOfLine[0][1], "").split(";")[0];
					endSeg = StringHelper.obj2str(segsOfLine[segsOfLine.length-1][1], "").split(";")[0];
					OffsetBean offbean =  TfmCommon.calcOffset(zoomLevel, startSeg, endSeg);
					lineColor = TfmCommon.getLineColor(lines[m][3]);
					lineId = StringHelper.obj2str(lines[m][0], "");
					//����
					if(StringHelper.obj2str(lines[m][2], "").equals("����") || StringHelper.obj2str(lines[m][2], "").equals("0"))
					{
						lineStr += vmlPolyLine.createVmlPolyLines(segsOfLine,offbean.getOffsetUpX(),offbean.getOffsetUpY(),
								lineColor,StringHelper.obj2str(lines[m][2], ""),lineId,vmlLineWidth);	
					}else{
						lineStr += vmlPolyLine.createVmlPolyLines(segsOfLine,offbean.getOffsetDownX(),offbean.getOffsetDownY(),
								lineColor,StringHelper.obj2str(lines[m][2], ""),lineId,vmlLineWidth);	
					}
				}
	 			xmlData.append(StringHelper.obj2str(lines[m][0],"") + "<col>");//·�߱��
				xmlData.append( StringHelper.obj2str(lines[m][1],"") + "<col>");//·������
				xmlData.append(TfmCommon.getDirectionDesc(StringHelper.obj2str(lines[m][2],"")) + "<col>");//����
				xmlData.append(TfmCommon.getLineStatusDesc(StringHelper.obj2str(lines[m][3],"")) + "<col>");//·��״��
				
				if(segsOfLine == null){
					xmlData.append(0);//����·������	
				}else{
					xmlData.append(segsOfLine.length);//����·������	
				}
				if(m == lines.length-1){
					
				}else{
					xmlData.append("<road>");	
				}
				segsOfLine = null;
			    perLineCol.clear();
			    
			}
			xmlData.append("<linevml>");
			xmlData.append(lineStr);
		}
	    return xmlData.toString();
   }
	
    
  
    public static void main(String[] args){
    	String[][] str = {{"1","2"},{"3","4"}};
    	String[][] str2 = new String[2][6];
    	for(int i =0 ; i < str.length; i++){
    		str2[i] = str[i];
    	}
    	for(int j = 0; j < str.length; j ++){
    		for(int k=0; k < str2[j].length; k ++){
    			System.out.println(str2[j][k]);
    		}
    		System.out.println("------------------");
    	}
    }



	
	public String synLinkDir() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
