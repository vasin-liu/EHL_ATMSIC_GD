package com.ehl.dispatch.bdispatch.ctrl;

import com.appframe.Console;
import com.ehl.webgis.data.SDEAccess;
import com.esri.arcgis.geometry.IGeometry;
import com.esri.arcgis.geometry.esriGeometryDimension;

import com.esri.arcgis.geometry.IPoint;
import com.esri.arcgis.geometry.Multipoint;
import com.esri.arcgis.geometry.Point;
import com.esri.arcgis.geometry.Polyline;
import com.esri.arcgis.geodatabase.IFeature;
import com.esri.arcgis.geodatabase.FeatureClass;
import com.esri.arcgis.geodatabase.SpatialFilter;
import com.esri.arcgis.geodatabase.esriSpatialRelEnum;
import com.esri.arcgis.geodatabase.IFeatureCursor;

import java.lang.Math;

/**
 * 
 * @======================================================================================================================================
 * 
 * @类型说明:对GPS车辆的纠偏算法.
 * @创建者：wwj
 * @创建日期 2008-9-27
 * @======================================================================================================================================
 */
public class GPSUtil {
	private SDEAccess sde = null;
	private FeatureClass linelayer = null; // 要操作的图层
	double dvalues[][] = null;
	Object finiPoint[] = null;
	/**
	 * 
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * @作者：wwj
	 * @版本号：
	 * @构造说明：
	 * @参数：
	 * @创建日期：2008-9-27
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public GPSUtil(SDEAccess sde, String layerName) throws Throwable {
		this.sde = sde;
		this.linelayer = new FeatureClass(sde.initConnection().openFeatureClass(layerName));
	}
	/**
	 * 
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * @作者：wwj
	 * @版本号：
	 * @函数说明：
	 * @参数：coord-->给定一对经纬度坐标.angle-->车辆的行进方向.
	 * @返回：经过纠正的一对经纬度坐标.
	 * @创建日期：2008-9-27
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public double[] rectify(double longitude, double latitude, double angle) throws Throwable {
		Point p = new Point();

		p.setX(longitude);
		p.setY(latitude);

		try {
			//在50米范围内搜索
			IFeature f[] = this.getFeatureByPosition(p, 0.0005);
			
			//寻找一个距离最小,角度接近平行的线.
			if(f != null && f.length > 0){
				dvalues = new double[f.length][3];
				finiPoint = new Object[f.length];
				for (int i=0; i<f.length; i++){
					//距离.
					IGeometry nowLine = f[i].getShape();
					
					double nowDis = p.returnDistance(nowLine);
					dvalues[i][0] = nowDis;
//					Console.infoprintln("距离:" + nowDis);

					//计算道路的角度.
					IGeometry bufferGeo = p.buffer(nowDis + 0.000001);
					IGeometry interLine = this.getGeometryByIntersect(nowLine, bufferGeo);	//求交线.

					int geotype = interLine.getGeometryType();
//					Console.infoprintln("交线类型：" + geotype);
					
					double dNowAngle = calcPolylineAngle(i, interLine);
					dvalues[i][1] = dNowAngle;
					//TODO:根据道路的角度和车辆行驶的角度
				}
				
				// 从找到的距离和角度上寻找最优匹配.即距离最短,角度最接近的值.
				// dvalues[i][0] * (Math.abs(dvalues[i][1] - angle))
				double d = 0.0D;
				double tmp = 10000.0D;
				int icurr = 0;
				for (int i=0; i<dvalues.length; i++){
//					if (angle >= 0 && angle < 90){
//					}
//					else if (angle >= 90 && angle < 180){
//						angle = angle- 180;
//					}
//					else if (angle >= 180 && angle < 270){
//						angle = angle - 180;
//					}
//					else if (angle >= 270 && angle < 360){
//						angle = angle - 360;
//					}
					d = dvalues[i][0] * (Math.abs(dvalues[i][1] - angle));
					if (d < tmp){
						tmp = d;
						icurr = i;
					}
				}
				//距离.
				return (double[])finiPoint[icurr];
			}
			
		} catch (Exception ex) {
			Console.infoprintln(ex.getMessage());
		}
		
		return null;
	}
	/**
	 * 
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * @作者：wwj
	 * @版本号：
	 * @函数说明：计算线要素的角度.
	 * @参数：
	 * @返回：
	 * @创建日期：2008-9-27
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	private double calcPolylineAngle(int i, IGeometry interLine)throws Throwable{
		//取得线的两个端点, 计算角度.
		Polyline interPLine = (Polyline)interLine;
		Multipoint points = new Multipoint();
		points.setSpatialReferenceByRef(interPLine.getSpatialReference());
		points.addPointCollection(interPLine);
		
		if(points.getPointCount() <2){
			return 0.0D;
//			throw new RuntimeException("交点至少为2个方可.");
		}
		IPoint pStart = points.getPoint(0);
		IPoint pEnd = points.getPoint(points.getPointCount()-1);
		
		double sx = pStart.getX();
		double sy = pStart.getY();
		finiPoint[i] = new double[]{sx, sy};
		
		double ex = pEnd.getX();
		double ey = pEnd.getY();
		Console.infoprintln("终点坐标：(" + ex + "," + ey + ")");
		
		double xdiff = (ex * 1.0E16 - sx * 1.0E16);
		double ydiff = (ey * 1.0E16 - sy * 1.0E16);
		
		double rNowAngle = Math.atan(ydiff / xdiff);//ydiff / sqrt;
		double dNowAngle = Math.toDegrees(rNowAngle);
		return dNowAngle;
	}
	/**
	 * 
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * @作者：wwj
	 * @版本号：
	 * @函数说明：缓冲区查询.
	 * @参数：
	 * @返回：
	 * @创建日期：2008-9-27
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	private IFeature[] getFeatureByPosition(Point ptt, double radius) throws Throwable {
		// 作当前点的缓冲区
		IGeometry bufferGeo = ptt.buffer(radius);
		// 查询当前图层
		SpatialFilter filter = new SpatialFilter();
		filter.setGeometryByRef(bufferGeo);
		String shapeFieldString = linelayer.getShapeFieldName();
		filter.setGeometryField(shapeFieldString);
		filter.setWhereClause("");
		filter.setSpatialRel(esriSpatialRelEnum.esriSpatialRelIntersects);

		// 获取查询记录总数
		int intCount = linelayer.featureCount(filter);
		IFeature selectFeature[] = null;
		if (intCount > 0) {
			selectFeature = new IFeature[intCount];
			IFeatureCursor cursor = linelayer.search(filter, false);
			IFeature feature = cursor.nextFeature();

			// 循环获取查询到结果集合
			int i = 0;
			while (feature != null) {
				selectFeature[i++] = feature;
				feature = cursor.nextFeature();
			}
		}

		return selectFeature;
	}
	/**
	 * 
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * @作者：wwj
	 * @版本号：
	 * @函数说明：取得两个要素的交集
	 * @参数：
	 * @返回：
	 * @创建日期：2008-9-27
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	private IGeometry getGeometryByIntersect(IGeometry base, IGeometry compare) throws Throwable {
		IGeometry intersect = ((Polyline)base).intersect(compare, esriGeometryDimension.esriGeometry1Dimension);
		return intersect;
	}
	private IGeometry getGeometryByIntersect2(IGeometry base, IGeometry compare) throws Throwable {
		IGeometry intersect = ((Polyline)base).intersect(compare, esriGeometryDimension.esriGeometry0Dimension);
		return intersect;
	}
}