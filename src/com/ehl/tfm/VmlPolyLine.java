package com.ehl.tfm;

import java.util.ArrayList;
import java.util.HashMap;

public class VmlPolyLine {
	public VmlPolyLine(TransitTOScreenPixel transitTOScreenPixel){
		this.transit = transitTOScreenPixel;
	}
	private TransitTOScreenPixel transit;
	public String createSingleVmlPolyLine(String shape,HashMap map){
		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml:namespace prefix = v ns = \"urn:schemas-microsoft-com:vml\" />");
		String[] temp;
		String[] temp2;
				xml.append("<v:polyline style=\"FILTER: alpha(opacity=50); POSITION: absolute\" points = \"" );
				temp = shape.toString().split(";");
				for(int j=0;j<temp.length-1;j++){
					temp2 = temp[j].split(",");
					xml.append(this.transit.getScreenPixelX(temp2[0]));
					xml.append("px ,");
					xml.append(this.transit.getScreenPixelY(temp2[1]));
					xml.append("px ,");
				}
				temp2 = temp[0].split(",");
				xml.append(this.transit.getScreenPixelX(temp2[0]));
				xml.append("px ,");
				xml.append(this.transit.getScreenPixelY(temp2[1]));
				xml.append("px ");
				xml.append("\" filled = \"t\" fillcolor = \"green\" strokecolor = \"red\" strokeweight = \"1pt\"></v:polyline> ");
			return xml.toString();
	}
	/**
     * <b>��ȡ����������ͬ��ɫ�Ϳ�ȵ�VML�ߵ��ַ���.</b>
     * param:segsOfLine-Ҫ���ߵĶ�ά���飨��һ��Ϊid���ڶ���Ϊ��γ�ȴ�-��";"�ָ���ͬ������ԣ���,���ŷָ���γ�ȣ�
     * param:offsetX-����ƫ��
     * offsetY��ά��ƫ��
     * param:color-����ɫ
     * param:direction-����
     * param:lineId-���߱��
     * param:width-�߿��
     * @return xml-vml�ַ���.
     */
	public String createVmlPolyLines(Object[][] segsOfLine,float offsetX,float offsetY,String color,String direction,String lineId,double width){
		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml:namespace prefix = v ns = \"urn:schemas-microsoft-com:vml\" />");
		String[] temp;
		String[] temp2;
		String segId = "";
		if(segsOfLine!=null){
			for(int i=0;i<segsOfLine.length;i++){
				segId = lineId+":" + direction +":" + i;
				xml.append("<v:polyline id='"+segId+"'  style=\" POSITION: absolute\"   fill='true' points = \""  );
				temp = segsOfLine[i][1].toString().split(";");
				for(int j=0;j<temp.length;j++){
					temp2 = temp[j].split(",");
					xml.append(this.transit.getScreenPixelX(temp2[0]) + offsetX);
					xml.append("px ,");
					xml.append(this.transit.getScreenPixelY(temp2[1]) + offsetY);
					xml.append("px ,");
				}
				xml.append("\" filled = \"f\"  strokecolor = \""+color+"\" segpoints = \""+segsOfLine[i][1].toString()+";"+offsetX+","+offsetY+"\" " +
						"strokeweight = \"2pt\"></v:polyline> ");
				
			}
		}
		//System.out.println("==vml==================="+xml.toString());
		return xml.toString();
	}
	
	/**
     * <b>��ȡ����������ͬ��ɫ�Ϳ�ȵ�VML�ߵ��ַ���.</b>
     * param:segsOfLine-Ҫ���ߵĶ�ά���飨��һ��Ϊid���ڶ���Ϊ��γ�ȴ�-��";"�ָ���ͬ������ԣ���,���ŷָ���γ�ȣ�
     * param:offsetX-����ƫ��
     * offsetY��ά��ƫ��
     * param:color-����ɫ
     * param:direction-����
     * param:lineId-���߱��
     * param:width-�߿��
     * eidt by lidq ���Ӽ�ͷ��ʾ���߷���
     * @return xml-vml�ַ���.
     */
	public String createVmlPolyLines(Object[][] segsOfLine,ArrayList<OffsetBean> obArr,boolean upDown,String color,String direction,String lineId,double width){
		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml:namespace prefix = v ns = \"urn:schemas-microsoft-com:vml\" />");
		String[] temp;
		String[] temp2;
		String segId = "";
		if(segsOfLine!=null){
			for(int i=0;i<segsOfLine.length;i++){
				segId = lineId+":" + direction +":" + i;
				xml.append("<v:polyline id='"+segId+"'  style=\"POSITION: absolute\"   fill='true' points = \""  );
				temp = segsOfLine[i][1].toString().split(";");
				for(int j=0;j<temp.length;j++){
					temp2 = temp[j].split(",");
					xml.append(this.transit.getScreenPixelX(temp2[0]) + (upDown==true?obArr.get(i).getOffsetUpX():obArr.get(i).getOffsetDownX()));
					xml.append("px ,");
					xml.append(this.transit.getScreenPixelY(temp2[1]) + (upDown==true?obArr.get(i).getOffsetUpY():obArr.get(i).getOffsetDownY()));
					xml.append("px ,");
				}
				xml.append("\" filled=\"f\"  strokecolor=\""+color+"\" segpoints=\""+segsOfLine[i][1].toString()+";"+(upDown==true?obArr.get(0).getOffsetUpX():obArr.get(0).getOffsetDownX())+","+(upDown==true?obArr.get(0).getOffsetUpY():obArr.get(0).getOffsetDownY())+"\" " +
						"strokeweight=\"2pt\">");
				if("0".equals(direction)){//0��������
					xml.append(createstroke("","block"));//�����յ����Ӽ�ͷ��ʽ	
				}else{
					xml.append(createstroke("block",""));//������ʼ�����Ӽ�ͷ��ʽ	
				}
				xml.append("</v:polyline>");	
				
			}
		}
		return xml.toString();
	}

	
	
	/**
     * <b>��ȡһ��VML�ߵ��ַ���.</b>
     * points-��γ�ȴ�����";"�ָ���ͬ������ԣ���,���ŷָ���γ�ȣ�
     * param:offsetX-����ƫ��
     * offsetY��ά��ƫ��
     * param:color-����ɫ
     * param:direction-����
     * param:lineId-���߱��
     * param:width-�߿��
     * @return xml-vml�ַ���.
     * @return xml-vml�ַ���.
     */
	public String createVmlPolyLine(String points,float offsetX,float offsetY,String color,String direction,String lineId,double width){
		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml:namespace prefix = v ns = \"urn:schemas-microsoft-com:vml\" />");
		String[] temp;
		String[] temp2;
		String segId = "";
		segId = lineId+":" + direction+":0";//����ǰ��getElementById��ѯ
		xml.append("<v:polyline id='"+segId+"'  style=\"POSITION: absolute\"  fill='true'  points=\""  );
		temp = points.split(";");
		for(int j=0;j<temp.length;j++){
			temp2 = temp[j].split(",");
				xml.append(this.transit.getScreenPixelX(temp2[0]) + offsetX);
				xml.append("px ,");
				xml.append(this.transit.getScreenPixelY(temp2[1]) + offsetY);
				xml.append("px ,");
		}
		xml.append("\" filled = 't'  strokecolor = \""+color+"\" segpoints = \""+points+";"+offsetX+","+offsetY+"\" strokeweight = \""+width+"pt\">");
		xml.append(createstroke("",""));//���Ӽ�ͷ��ʽ	
		xml.append("</v:polyline>");	
		return xml.toString();
	}
	
	/**
	 * <p>�ڶ����������Ӽ�ͷ���
	 * @param:startarrow�����������ļ�ͷ��ʽ
	 * @param:endarrow���������յ�ļ�ͷ��ʽ
	 * 	��ͷ��ʽ�� none,block,classic,diamond,oval,open,chevron,doublechevron 
	 * ����֧����ο�vml�̳�
	 */
	private String createstroke(String startarrow,String endarrow){
		StringBuffer xml = new StringBuffer("");
		if(!"".equals(startarrow)||!"".equals(endarrow)){
			xml.append("<v:stroke startarrow='"+startarrow+"' endarrow='"+endarrow+"'/>");
		}
		return xml.toString();
	}
	
}
