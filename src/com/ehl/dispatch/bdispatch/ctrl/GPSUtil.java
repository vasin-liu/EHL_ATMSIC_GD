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
 * @����˵��:��GPS�����ľ�ƫ�㷨.
 * @�����ߣ�wwj
 * @�������� 2008-9-27
 * @======================================================================================================================================
 */
public class GPSUtil {
	private SDEAccess sde = null;
	private FeatureClass linelayer = null; // Ҫ������ͼ��
	double dvalues[][] = null;
	Object finiPoint[] = null;
	/**
	 * 
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * 
	 * @���ߣ�wwj
	 * @�汾�ţ�
	 * @����˵����
	 * @������
	 * @�������ڣ�2008-9-27
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
	 * @���ߣ�wwj
	 * @�汾�ţ�
	 * @����˵����
	 * @������coord-->����һ�Ծ�γ������.angle-->�������н�����.
	 * @���أ�����������һ�Ծ�γ������.
	 * @�������ڣ�2008-9-27
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public double[] rectify(double longitude, double latitude, double angle) throws Throwable {
		Point p = new Point();

		p.setX(longitude);
		p.setY(latitude);

		try {
			//��50�׷�Χ������
			IFeature f[] = this.getFeatureByPosition(p, 0.0005);
			
			//Ѱ��һ��������С,�ǶȽӽ�ƽ�е���.
			if(f != null && f.length > 0){
				dvalues = new double[f.length][3];
				finiPoint = new Object[f.length];
				for (int i=0; i<f.length; i++){
					//����.
					IGeometry nowLine = f[i].getShape();
					
					double nowDis = p.returnDistance(nowLine);
					dvalues[i][0] = nowDis;
//					Console.infoprintln("����:" + nowDis);

					//�����·�ĽǶ�.
					IGeometry bufferGeo = p.buffer(nowDis + 0.000001);
					IGeometry interLine = this.getGeometryByIntersect(nowLine, bufferGeo);	//����.

					int geotype = interLine.getGeometryType();
//					Console.infoprintln("�������ͣ�" + geotype);
					
					double dNowAngle = calcPolylineAngle(i, interLine);
					dvalues[i][1] = dNowAngle;
					//TODO:���ݵ�·�ĽǶȺͳ�����ʻ�ĽǶ�
				}
				
				// ���ҵ��ľ���ͽǶ���Ѱ������ƥ��.���������,�Ƕ���ӽ���ֵ.
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
				//����.
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
	 * @���ߣ�wwj
	 * @�汾�ţ�
	 * @����˵����������Ҫ�صĽǶ�.
	 * @������
	 * @���أ�
	 * @�������ڣ�2008-9-27
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	private double calcPolylineAngle(int i, IGeometry interLine)throws Throwable{
		//ȡ���ߵ������˵�, ����Ƕ�.
		Polyline interPLine = (Polyline)interLine;
		Multipoint points = new Multipoint();
		points.setSpatialReferenceByRef(interPLine.getSpatialReference());
		points.addPointCollection(interPLine);
		
		if(points.getPointCount() <2){
			return 0.0D;
//			throw new RuntimeException("��������Ϊ2������.");
		}
		IPoint pStart = points.getPoint(0);
		IPoint pEnd = points.getPoint(points.getPointCount()-1);
		
		double sx = pStart.getX();
		double sy = pStart.getY();
		finiPoint[i] = new double[]{sx, sy};
		
		double ex = pEnd.getX();
		double ey = pEnd.getY();
		Console.infoprintln("�յ����꣺(" + ex + "," + ey + ")");
		
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
	 * @���ߣ�wwj
	 * @�汾�ţ�
	 * @����˵������������ѯ.
	 * @������
	 * @���أ�
	 * @�������ڣ�2008-9-27
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	private IFeature[] getFeatureByPosition(Point ptt, double radius) throws Throwable {
		// ����ǰ��Ļ�����
		IGeometry bufferGeo = ptt.buffer(radius);
		// ��ѯ��ǰͼ��
		SpatialFilter filter = new SpatialFilter();
		filter.setGeometryByRef(bufferGeo);
		String shapeFieldString = linelayer.getShapeFieldName();
		filter.setGeometryField(shapeFieldString);
		filter.setWhereClause("");
		filter.setSpatialRel(esriSpatialRelEnum.esriSpatialRelIntersects);

		// ��ȡ��ѯ��¼����
		int intCount = linelayer.featureCount(filter);
		IFeature selectFeature[] = null;
		if (intCount > 0) {
			selectFeature = new IFeature[intCount];
			IFeatureCursor cursor = linelayer.search(filter, false);
			IFeature feature = cursor.nextFeature();

			// ѭ����ȡ��ѯ���������
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
	 * @���ߣ�wwj
	 * @�汾�ţ�
	 * @����˵����ȡ������Ҫ�صĽ���
	 * @������
	 * @���أ�
	 * @�������ڣ�2008-9-27
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