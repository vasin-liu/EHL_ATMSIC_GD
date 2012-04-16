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
     * <b>获取多条具有相同颜色和宽度的VML线的字符串.</b>
     * param:segsOfLine-要绘线的二维数组（第一列为id，第二列为经纬度串-以";"分隔不同的坐标对，“,”号分隔经纬度）
     * param:offsetX-经度偏移
     * offsetY：维度偏移
     * param:color-线颜色
     * param:direction-方向
     * param:lineId-连线编号
     * param:width-线宽度
     * @return xml-vml字符串.
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
     * <b>获取多条具有相同颜色和宽度的VML线的字符串.</b>
     * param:segsOfLine-要绘线的二维数组（第一列为id，第二列为经纬度串-以";"分隔不同的坐标对，“,”号分隔经纬度）
     * param:offsetX-经度偏移
     * offsetY：维度偏移
     * param:color-线颜色
     * param:direction-方向
     * param:lineId-连线编号
     * param:width-线宽度
     * eidt by lidq 增加箭头表示连线方向，
     * @return xml-vml字符串.
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
				if("0".equals(direction)){//0代表下行
					xml.append(createstroke("","block"));//上行终点增加箭头样式	
				}else{
					xml.append(createstroke("block",""));//下行起始点增加箭头样式	
				}
				xml.append("</v:polyline>");	
				
			}
		}
		return xml.toString();
	}

	
	
	/**
     * <b>获取一条VML线的字符串.</b>
     * points-经纬度串（以";"分隔不同的坐标对，“,”号分隔经纬度）
     * param:offsetX-经度偏移
     * offsetY：维度偏移
     * param:color-线颜色
     * param:direction-方向
     * param:lineId-连线编号
     * param:width-线宽度
     * @return xml-vml字符串.
     * @return xml-vml字符串.
     */
	public String createVmlPolyLine(String points,float offsetX,float offsetY,String color,String direction,String lineId,double width){
		StringBuffer xml = new StringBuffer("");
		xml.append("<?xml:namespace prefix = v ns = \"urn:schemas-microsoft-com:vml\" />");
		String[] temp;
		String[] temp2;
		String segId = "";
		segId = lineId+":" + direction+":0";//用于前端getElementById查询
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
		xml.append(createstroke("",""));//增加箭头样式	
		xml.append("</v:polyline>");	
		return xml.toString();
	}
	
	/**
	 * <p>在多义线上增加箭头风格
	 * @param:startarrow，描述线起点的箭头样式
	 * @param:endarrow，描述线终点的箭头样式
	 * 	箭头样式： none,block,classic,diamond,oval,open,chevron,doublechevron 
	 * 更多支持请参考vml教程
	 */
	private String createstroke(String startarrow,String endarrow){
		StringBuffer xml = new StringBuffer("");
		if(!"".equals(startarrow)||!"".equals(endarrow)){
			xml.append("<v:stroke startarrow='"+startarrow+"' endarrow='"+endarrow+"'/>");
		}
		return xml.toString();
	}
	
}
