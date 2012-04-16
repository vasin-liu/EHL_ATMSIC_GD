package com.ehl.tfm;

import java.util.Arrays;
import java.util.HashMap;

public class GISUtil {
	/**
	 * @作者: luhaifu
	 * @版本号：
	 * @函数说明：计算中心点坐标
	 * @参数：shapes 包含坐标信息的数组，index 坐标信息的列序号
	 * @返回：中心点坐标，逗号分隔
	 * @创建日期：2009-06-29
	 * @修改者：
	 * @修改日期：
	 */
	public String executePointsCenter(Object[][] shapes,int index){
		if(shapes == null){
			return null;
		}
		float[] X ;
		float[] Y ;
		X = new float[shapes.length];
		Y = new float[shapes.length];
		String temp[];
		for(int i=0;i<X.length;i++){
			temp = shapes[i][index].toString().split(",");
			X[i] = Float.parseFloat(temp[0]);
			Y[i] = Float.parseFloat(temp[1]);	
		}
		Arrays.sort(X);
		Arrays.sort(Y);
		return String.valueOf(((X[0]+X[X.length-1])/2))+","+String.valueOf(((Y[0]+Y[Y.length-1])/2));
	}
	
	
	/**
	 * @作者: luhaifu
	 * @版本号：
	 * @函数说明：计算四角坐标
	 * @参数：shapes 包含坐标信息的数组，index 坐标信息的列序号
	 * @返回：键值 ： MaxX,MinX, MaxY, MinY
	 * @创建日期：2009-06-29
	 * @修改者：
	 * @修改日期：
	 */
	public HashMap executePointsBound(Object[][] shapes,int index){
		if(shapes == null){
			return null;
		}
		HashMap<String,Float> map = new HashMap<String,Float>();
		float[] X ;
		float[] Y ;
		X = new float[shapes.length];
		Y = new float[shapes.length];
		String temp[];
		for(int i=0;i<X.length;i++){
			temp = shapes[i][index].toString().split(",");
			X[i] = Float.parseFloat(temp[0]);
			Y[i] = Float.parseFloat(temp[1]);	
		}
		Arrays.sort(X);
		Arrays.sort(Y);
		map.put("MaxX", X[X.length-1]);
		map.put("MaxY", Y[Y.length-1]);
		map.put("MinX", X[0]);
		map.put("MinY", Y[0]);
		return map;
	}
	
	/**
	 * @作者: luhaifu
	 * @版本号：
	 * @函数说明：计算地图最合适的缩放等级
	 * @参数：
	 * @返回：地图缩放等级
	 * @创建日期：2009-06-29
	 * @修改者：
	 * @修改日期：
	 */
	public int getMaxLevel(float MaxX,float MinX,float MaxY,float MinY,int MapDivWidth,int MapDivHeight){
		TransitTOScreenPixel transit;
		int maxZoomLevel = com.appframe.common.Setting.getInt("gismaxzoomlevel");
		int i=1;
		for( i=1;i<=maxZoomLevel;i++){
			transit = new TransitTOScreenPixel(i);
			if((transit.getScreenPixelX(MaxX)-transit.getScreenPixelX(MinX))>MapDivWidth){
				break;
			}
			if((transit.getScreenPixelX(MinY)-transit.getScreenPixelX(MaxY))>MapDivHeight){
				break;
			}
		}
		return i-1;
	}
	
	/**
	 * @作者: luhaifu
	 * @版本号：
	 * @函数说明：计算地图最合适的缩放等级
	 * @参数：
	 * @返回：地图缩放等级
	 * @创建日期：2009-06-29
	 * @修改者：
	 * @修改日期：
	 */
	public int getMaxLevel(HashMap<String,Float> map,int MapDivWidth,int MapDivHeight){
		TransitTOScreenPixel transit;
		int maxZoomLevel = com.appframe.common.Setting.getInt("gismaxzoomlevel");
		int i=1;
		for( i=1;i<=maxZoomLevel;i++){
			transit = new TransitTOScreenPixel(i);
			if((transit.getScreenPixelX(map.get("MaxX"))-transit.getScreenPixelX(map.get("MinX")))>MapDivWidth){
				break;
			}
			if((transit.getScreenPixelX(map.get("MinY"))-transit.getScreenPixelX(map.get("MaxY")))>MapDivHeight){
				break;
			}
		}
		if(i!=1){
			i=i-1;
		}
		return i;
	}
}
