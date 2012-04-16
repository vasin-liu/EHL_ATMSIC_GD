package com.ehl.tfm;

import com.appframe.utils.StringHelper;

public class TfmCommon {
	/**
     * @author zhaoy [2009-07-02].
     * <b>ȥ���ַ���β��ȫ��ָ�����ַ�.</b>
     * @param str ԭ�ַ���.
     * @param Ҫȥ����β���ַ��� 

	 * @return ����ַ���.
     */
    public static String removeEndChar(String str, String target) {
    	for(int i=0;i < str.length();i++){
    		if(str.endsWith(target)){
    			str = str.substring(0, str.length()-1);
    		}
    	}
    	return str;
    }
    
    /**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵��������ƫ����
	 * @����˵����zoomLevel-��ͼ�Ŵ���,  point1-��ʼ��,  point2-��ֹ��
	 * @���أ�OffsetBean - ������ƫ����
	 * @�������ڣ�2009-7-9
	 */
 public static OffsetBean calcOffset(int zoomLevel, String point1, String point2){
		 double lng1 = Double.parseDouble(point1.split(",")[0]); 
		 double lat1 = Double.parseDouble(point1.split(",")[1]);
		 double lng2 = Double.parseDouble(point2.split(",")[0]);
		 double lat2 = Double.parseDouble(point2.split(",")[1]);
      //	���ϱ���������ƫ����
		double offset = 0.2 * Math.pow(2, zoomLevel+0.5)*(1/Math.pow(1.5,zoomLevel));
		//ƫ����
		double otherOffset = 0.04 * Math.pow(2,zoomLevel+0.5);
		
//		������ת��Ϊ����
//		PointBean coord1 = new PointBean((lng1*1e16), (lat1*1e16));
//		PointBean coord2 = new PointBean((lng2*1e16), (lat2*1e16));

		//�ɾ�γ��ת��Ļ����.
		TransitTOScreenPixel util = new TransitTOScreenPixel(zoomLevel);
		PointBean sCoord1 = new PointBean(util.getScreenPixelX(lng1),util.getScreenPixelY(lat1));		
		PointBean sCoord2 = new PointBean(util.getScreenPixelX(lng2),util.getScreenPixelY(lat2));
//		����ƫ����
  		float xOffsetUp = 0L;
  		float yOffsetUp = 0L;
  		float xOffsetDown = 0L;
  		float yOffsetDown = 0L;
  		
//  	������б�Ƕ�
  		double tangent = Math.abs(sCoord2.y - sCoord1.y) / Math.abs(sCoord2.x - sCoord1.x);

  		double yOffset = Math.sqrt(otherOffset) / (1 + Math.sqrt(tangent));
  		double xOffset = yOffset * Math.sqrt(tangent);
  		
//  		if(sCoord1.x<sCoord2.x&&sCoord1.y<sCoord2.y)
//  		{
//  			//x+y-
// 			
//  		//x-y-
//  			xOffsetUp 	-=	xOffset;
//  			xOffsetDown	-=	xOffset;
//  			
//  			yOffsetUp 	-=	yOffset;
//  			yOffsetDown	-=	yOffset;
//  			
//  		}else if(sCoord1.x>sCoord2.x&&sCoord1.y<sCoord2.y)
//  		{
//  			//x+y+
//  			
//  			//x-y+
//  			xOffsetUp 	-=	xOffset;
//  			xOffsetDown	-=	xOffset;
//  			
//  			yOffsetUp 	+=	yOffset;
//  			yOffsetDown	+=	yOffset;
//  			
//  		}else if(sCoord1.x>sCoord2.x&&sCoord1.y>sCoord2.y)
//  		{
//  			//x-y+
//  			//x+y+
//  			
//  			xOffsetUp 	+=	xOffset;
//  			xOffsetDown	+=	xOffset;
//  			
//  			yOffsetUp 	+=	yOffset;
//  			yOffsetDown	+=	yOffset;
//  		}else if(sCoord1.x<sCoord2.x&&sCoord1.y>sCoord2.y)
//  		{
//  		//x-y-
//  		//x+y-
//  			xOffsetUp 	+=	xOffset;
//  			xOffsetDown	+=	xOffset;
//  			
//  			yOffsetUp 	-=	yOffset;
//  			yOffsetDown	-=	yOffset;
//  			
//  		}
//  		
  		if(sCoord1.x<sCoord2.x&&sCoord1.y<sCoord2.y)
  		{
  			//x+y-
  			xOffsetUp 	+=	xOffset;
  			xOffsetDown	+=	xOffset;
  			
  			yOffsetUp 	-=	yOffset;
  			yOffsetDown	-=	yOffset;
  			
  		}else if(sCoord1.x>sCoord2.x&&sCoord1.y<sCoord2.y)
  		{
  			//x+y+
  			
  			xOffsetUp 	+=	xOffset;
  			xOffsetDown	+=	xOffset;
  			
  			yOffsetUp 	+=	yOffset;
  			yOffsetDown	+=	yOffset;
  			
  		}else if(sCoord1.x>sCoord2.x&&sCoord1.y>sCoord2.y)
  		{
  			//x-y+
  			xOffsetUp 	-=	xOffset;
  			xOffsetDown	-=	xOffset;
  			
  			yOffsetUp 	+=	yOffset;
  			yOffsetDown	+=	yOffset;
  			
  		}else if(sCoord1.x<sCoord2.x&&sCoord1.y>sCoord2.y)
  		{
  		//x-y-
  			xOffsetUp 	-=	xOffset;
  			xOffsetDown	-=	xOffset;
  			
  			yOffsetUp 	-=	yOffset;
  			yOffsetDown	-=	yOffset;
  			
  			
  		}
  		
  		return new OffsetBean(xOffsetUp,yOffsetUp,xOffsetDown,yOffsetDown);
 }
 
    /**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵������ͬһ·�εĲ�ͬ��¼�ϲ�;·�������ظ��������ظ��ļ�¼
	 * @�������ڣ�2008-10-16
	 */
	public static String  getLineColor(Object roadstatus){
		if(roadstatus == null){
		    return 	"gray";
		}
		String roadColor = "gray";
		String ldzk = (StringHelper.obj2str(roadstatus, "")).trim();
	    if(ldzk.equals("��ͨ") || ldzk.equals("0") ){
	      roadColor = "green";
	    }
	   if(ldzk.equals("ӵ��") || ldzk.equals("2")){
	      roadColor = "red";
	    }
	   if(ldzk.equals("ӵ��") || ldzk.equals("1")){
	      roadColor = "yellow";
	    }
	    return roadColor;
	}
	
	/**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵���������·���������
	 * @�������ڣ�2008-10-16
	 */
	public static String  getDirectionDesc(String direction){
		if(direction.equals("0")){
		    return 	"����";
		}else if(direction.equals("1")){
			return 	"����";
		}else{
			return direction;
		}
	}
	
	/**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵��������·��������
	 * @�������ڣ�2008-10-16
	 */
	public static String  getLineStatusDesc(String roadstatus){
		if(roadstatus.equals("0")||roadstatus.equals("��ͨ")){
		    return 	"��ͨ";
		}else if(roadstatus.equals("1")||roadstatus.equals("ӵ��")){
			return 	"ӵ��";
		}else if(roadstatus.equals("2")||roadstatus.equals("ӵ��")){
			return 	"ӵ��";
		}else{
			return "��״̬";
		}
	}

	/**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵�����������ż�����������߿��ֵ
	 * @�������ڣ�2009-7-15
	 */
	public static double getLineWidth(int zoomLevel){
		return  2.1*Math.pow(2, (Double.valueOf(zoomLevel)-6)*0.5);
	}
}
