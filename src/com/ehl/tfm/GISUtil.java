package com.ehl.tfm;

import java.util.Arrays;
import java.util.HashMap;

public class GISUtil {
	/**
	 * @����: luhaifu
	 * @�汾�ţ�
	 * @����˵�����������ĵ�����
	 * @������shapes ����������Ϣ�����飬index ������Ϣ�������
	 * @���أ����ĵ����꣬���ŷָ�
	 * @�������ڣ�2009-06-29
	 * @�޸��ߣ�
	 * @�޸����ڣ�
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
	 * @����: luhaifu
	 * @�汾�ţ�
	 * @����˵���������Ľ�����
	 * @������shapes ����������Ϣ�����飬index ������Ϣ�������
	 * @���أ���ֵ �� MaxX,MinX, MaxY, MinY
	 * @�������ڣ�2009-06-29
	 * @�޸��ߣ�
	 * @�޸����ڣ�
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
	 * @����: luhaifu
	 * @�汾�ţ�
	 * @����˵���������ͼ����ʵ����ŵȼ�
	 * @������
	 * @���أ���ͼ���ŵȼ�
	 * @�������ڣ�2009-06-29
	 * @�޸��ߣ�
	 * @�޸����ڣ�
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
	 * @����: luhaifu
	 * @�汾�ţ�
	 * @����˵���������ͼ����ʵ����ŵȼ�
	 * @������
	 * @���أ���ͼ���ŵȼ�
	 * @�������ڣ�2009-06-29
	 * @�޸��ߣ�
	 * @�޸����ڣ�
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
