package com.ehl.tfm;

import com.appframe.utils.StringHelper;

public class TfmCommon {
	/**
     * @author zhaoy [2009-07-02].
     * <b>去除字符串尾部全部指定的字符.</b>
     * @param str 原字符串.
     * @param 要去除的尾部字符串 

	 * @return 结果字符串.
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
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：计算偏移量
	 * @参数说明：zoomLevel-地图放大级数,  point1-起始点,  point2-终止点
	 * @返回：OffsetBean - 上下行偏移量
	 * @创建日期：2009-7-9
	 */
 public static OffsetBean calcOffset(int zoomLevel, String point1, String point2){
		 double lng1 = Double.parseDouble(point1.split(",")[0]); 
		 double lat1 = Double.parseDouble(point1.split(",")[1]);
		 double lng2 = Double.parseDouble(point2.split(",")[0]);
		 double lat2 = Double.parseDouble(point2.split(",")[1]);
      //	正南北或正东西偏移量
		double offset = 0.2 * Math.pow(2, zoomLevel+0.5)*(1/Math.pow(1.5,zoomLevel));
		//偏移量
		double otherOffset = 0.04 * Math.pow(2,zoomLevel+0.5);
		
//		将坐标转化为整形
//		PointBean coord1 = new PointBean((lng1*1e16), (lat1*1e16));
//		PointBean coord2 = new PointBean((lng2*1e16), (lat2*1e16));

		//由经纬度转屏幕坐标.
		TransitTOScreenPixel util = new TransitTOScreenPixel(zoomLevel);
		PointBean sCoord1 = new PointBean(util.getScreenPixelX(lng1),util.getScreenPixelY(lat1));		
		PointBean sCoord2 = new PointBean(util.getScreenPixelX(lng2),util.getScreenPixelY(lat2));
//		计算偏移量
  		float xOffsetUp = 0L;
  		float yOffsetUp = 0L;
  		float xOffsetDown = 0L;
  		float yOffsetDown = 0L;
  		
//  	计算倾斜角度
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
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：将同一路段的不同记录合并;路线名称重复但方向不重复的记录
	 * @创建日期：2008-10-16
	 */
	public static String  getLineColor(Object roadstatus){
		if(roadstatus == null){
		    return 	"gray";
		}
		String roadColor = "gray";
		String ldzk = (StringHelper.obj2str(roadstatus, "")).trim();
	    if(ldzk.equals("畅通") || ldzk.equals("0") ){
	      roadColor = "green";
	    }
	   if(ldzk.equals("拥堵") || ldzk.equals("2")){
	      roadColor = "red";
	    }
	   if(ldzk.equals("拥挤") || ldzk.equals("1")){
	      roadColor = "yellow";
	    }
	    return roadColor;
	}
	
	/**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：翻译道路方向成描述
	 * @创建日期：2008-10-16
	 */
	public static String  getDirectionDesc(String direction){
		if(direction.equals("0")){
		    return 	"上行";
		}else if(direction.equals("1")){
			return 	"下行";
		}else{
			return direction;
		}
	}
	
	/**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：翻译路况成描述
	 * @创建日期：2008-10-16
	 */
	public static String  getLineStatusDesc(String roadstatus){
		if(roadstatus.equals("0")||roadstatus.equals("畅通")){
		    return 	"畅通";
		}else if(roadstatus.equals("1")||roadstatus.equals("拥挤")){
			return 	"拥挤";
		}else if(roadstatus.equals("2")||roadstatus.equals("拥堵")){
			return 	"拥堵";
		}else{
			return "无状态";
		}
	}

	/**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：根据缩放级别计算流量线宽度值
	 * @创建日期：2009-7-15
	 */
	public static double getLineWidth(int zoomLevel){
		return  2.1*Math.pow(2, (Double.valueOf(zoomLevel)-6)*0.5);
	}
}
