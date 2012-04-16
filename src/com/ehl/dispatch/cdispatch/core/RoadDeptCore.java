package com.ehl.dispatch.cdispatch.core;

import com.ehl.dispatch.cdispatch.dao.RoadDeptDao;

public class RoadDeptCore {
	
	private RoadDeptDao roadDept = new RoadDeptDao();
	
	public String getRoadByDept(String dept) {
		// TODO Auto-generated method stub
		String roads = null;
		Object[][] road = roadDept.getRoadByDept(dept);
		if(road != null){
			roads = DataToXML.objArrayToXml(road);
		}
		return roads;
	}
	
	public String getDeptByRoad(String roadName, String rsegName, String startKM, String endKM, String dldj){
		String depts = null;
		Object[][] dept = roadDept.getDeptByRoad(roadName, rsegName, startKM, endKM, dldj);
		if(dept != null){
			depts = DataToXML.objArrayToXml(dept);
		}
		return depts;
	}

	public String getDeptRoad(String roadName, String rsegName, String startKM,
			String endKM, String xzqhmc, String dldj) {
		// TODO Auto-generated method stub
		String depts = null;
		Object[][] dept = roadDept.getDeptRoad(roadName, rsegName, startKM, endKM, xzqhmc,dldj);
		if(dept != null){
			depts = DataToXML.objArrayToXml(dept);
		}
		return depts;
	}
	
	
}
