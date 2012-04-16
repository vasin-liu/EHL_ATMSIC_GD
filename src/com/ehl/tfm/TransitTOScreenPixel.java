package com.ehl.tfm;

/**
 * 
 * @======================================================================================================================================
 * @类型说明: 经纬度坐标转成屏幕像素.
 * @创建者：luhaifu
 * @创建日期 2009-06-29
 * @======================================================================================================================================
 */
public class TransitTOScreenPixel {
	/**
	 * @作者: luhaifu
	 * @版本号：1.0
	 * @参数：level  地图缩放等级
	 * @返回：返回二维数组
	 * @创建日期：2008-06-29
	 * @修改者：
	 * @修改日期：
	 */
	public TransitTOScreenPixel(int zoomLevel ){
		this.MaxY  = com.appframe.common.Setting.getFloat("gismaxy")*(float)1e16;
		this.MinX = com.appframe.common.Setting.getFloat("gisminx")*(float)1e16;
		this.MaxX  = com.appframe.common.Setting.getFloat("gismaxx")*(float)1e16;
		this.MinY = com.appframe.common.Setting.getFloat("gisminy")*(float)1e16;
		float canX = (float)Math.pow(2, zoomLevel-1)*com.appframe.common.Setting.getInt("gistilesize");
		float canX2 = MaxX-MinX;
		this.canX3 = canX/canX2;
		float canY = (float)Math.pow(2, zoomLevel-1)*com.appframe.common.Setting.getInt("gistilesize");
		float canY2 = MaxY-MinY;
		this.canY3 = canY/canY2;
		
	}
	private float canX3;
	private float canY3;
	private float MaxY;
	private float MinX;
	private float MaxX;
	private float MinY;
	/**
	 * @作者: luhaifu
	 * @版本号：1.0
	 * @函数说明：经纬度坐标转成屏幕像素
	 * @参数： 
	 * @返回：返回二维数组
	 * @创建日期：2008-06-29
	 * @修改者：
	 * @修改日期：
	 */
	public double getScreenPixelX(String x){
		return ((Float.parseFloat(x)*1e16 - MinX) * canX3);
	}
	public double getScreenPixelX(double x){
		return ((x*1e16 - MinX) * canX3);
	}
	/**
	 * @作者: luhaifu
	 * @版本号：1.0
	 * @函数说明：经纬度坐标转成屏幕像素
	 * @参数： 
	 * @返回：返回二维数组
	 * @创建日期：2008-06-29
	 * @修改者：
	 * @修改日期：
	 */
	public double getScreenPixelY(String y){
		return ((MaxY-Float.parseFloat(y)*1e16) * canY3);
	}
	public double getScreenPixelY(double y){
		return ((MaxY-y*1e16) * canY3);
	}
}
