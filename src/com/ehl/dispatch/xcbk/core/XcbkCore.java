package com.ehl.dispatch.xcbk.core;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.xcbk.dao.XcbkDao;
import com.ehl.tira.util.XML;

public class XcbkCore {
	private Logger logger = Logger.getLogger(XcbkCore.class);
	private XcbkDao xcbkDao = new XcbkDao();
	private AccDeptDao adDao = new AccDeptDao();
	
	/**
	 * 添加协查布控信息，同时添加接收单位
	 * @param parammIn
	 * @return
	 */
	public String addXcbk(Map<String, String> parammIn) {
		boolean isOK = false;
		String id = xcbkDao.getId();
		String adcode = parammIn.get("adcode");
		parammIn.put("id", id);
		isOK = xcbkDao.addXcbk(parammIn);
		if(isOK){
			isOK = xcbkDao.addXcbkAccDept(id, adcode, "1");
		}
		return String.valueOf(isOK);
	}

	
	/**
	 * 查询协查布控信息
	 * @param parammIn
	 * @return
	 */
	public String searchXcbk(String rpdcode, String state, String mstate) {
		String xml = null;
		Object[][] data = xcbkDao.searchXcbk(rpdcode, state, mstate);
		if(data != null){
			xml = DataToXML.objArrayToXml(data);
		}
		return xml;
	}
	
	/**
	 * 获取协查通报信息<br>
	 * 协查通报ID、当前用户机构代码
	 * @param id
	 * @param stype
	 * @return
	 */
	public String getXcbk(String id, int stype) {
		String result = null;
		if(id != null && stype >= 1 && stype <= 3){
			result = "";
			Object[] xcbkData = null;
			Object[][] adData = null;
			Object[][] disData = null;
			String xcbkXml = null;
			String adXml = null;
			String disXml = null;
			if(stype == 1){//发送单位
				xcbkData = xcbkDao.getXcbk(id);
				adData = adDao.getAccDept(id, null, 2);
			}else if(stype == 2){//接收单位
				adData = adDao.getAccDept(id, null, 0);
				if(adData != null){
					xcbkData = xcbkDao.getXcbk(adData[0][6]+"");
				}
				disData = adDao.getAccDept(id, 2);
			}else if(stype == 3){//转发单位
				disData = adDao.getAccDept(id, null, 0);
				if(disData != null){
					xcbkData = xcbkDao.getXcbk(disData[0][6]+"");
				}
				adData = adDao.getAccDept(id, 3);
			}
			if(xcbkData != null){
				xcbkXml = XML.encapContent("xcbk", "col", xcbkData);
				result += xcbkXml;
			}
			if(adData != null){
				adXml = XML.encapContent("ad", "row","col", adData);
				result += adXml;
			}
			if(disData != null){
				disXml = XML.encapContent("dis", "row","col", disData);
				result += disXml;
			}
			result = XML.getXML(result);
		}
		return result;
	}


	/**
	 * 更新协查通报信息
	 * @param parammIn
	 * @return
	 */
	public String updateXcbk(Map<String, String> parammIn) {
		boolean isOK = false;
		if(parammIn != null){
			String id = parammIn.get("id");
			isOK = xcbkDao.updateXcbk(parammIn);
			if(isOK){
				xcbkDao.addao.updateMState(id, "2" , "1");
			}
		}
		return String.valueOf(isOK);
	}

	/**
	 * 修改协查布控
	 * @param parammIn
	 * @return
	 */
	public boolean modifyXcbk(Map<String, String> parammIn) {
		boolean isOK = false;
		isOK = xcbkDao.modifyXcbk(parammIn);
		return isOK;
	}

	/**
	 * 解除协查通报信息
	 * @param id
	 * @return
	 */
	public String cancelXcbk(String id) {
		boolean isOK = xcbkDao.cancelXcbk(id);
		if(isOK){
			isOK = xcbkDao.addao.updateMState(id, "3" , "1");
		}
		return String.valueOf(isOK);
	}


	
}
