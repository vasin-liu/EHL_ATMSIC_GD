package com.ehl.dispatch.notice.core;

import java.util.Map;

import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.dispatch.notice.dao.ContentDao;
import com.ehl.tira.util.XML;

public class ContentCore {
	
	public ContentDao dao = new ContentDao();
	public AccDeptDao addao = new AccDeptDao();
	
	/**
	 * 添加内容
	 * @param paramMap
	 * @param adcode 
	 * @return
	 */
	public boolean addContent(Map<String,String> paramMap) {
		boolean isOK = dao.addContent(paramMap);
		return isOK;
	}
	
	/**
	 * 添加内容
	 * @param paramMap
	 * @param adcode 
	 * @return
	 */ 
	public boolean addContent(Map<String,String> paramMap, String adcode) {
		String id = paramMap.get("id"); 
		if(id == null){
			id = dao.getId();
			paramMap.put("id", id);
		}
		String sid = paramMap.get("sid");
		boolean isOK = dao.addContent(paramMap); 
		if( adcode != null){
			Map<String,String> admap = addao.getByIdToMap(sid);
			if(admap != null){
				admap.put("adid", id);
				admap.put("rpdcode", adcode);
				isOK = addao.adds(admap);
			}
		}
		return isOK;
	}
	
	/**
	 * 获取内容信息XML格式数据
	 * @param sid
	 * @return
	 */
	public String getContent(String id){
		String xml = null;
		Object[] data = dao.getContent(id);
		if(data != null){
			xml = XML.encapContent("content","col", data);
		}
		return xml;
	}
	
	/**
	 * 获取内容信息XML格式数据
	 * @param sid
	 * @return
	 */
	public String getContents(String sid, String udcode){
		String xml = null;
		Object[][] data = dao.getContents(sid, udcode);
		if(data != null){
			xml = XML.encapContent("content", "col", data);
		}
		return xml;
	}
	
	/**
	 * 获取转发机构转发内容XML格式数据
	 * @param aid
	 * @param rpdcode
	 * @return
	 */
	public String getDeptCtnt(String aid, String rpdcode){
		String xml = null;
		Object[][] data = dao.getDeptCtnt(aid, rpdcode);
		if(data != null){
			int adclen = 11;
			String adxml = "",ctntxml = "",ctntxmls="", adctntxml = "";
			String adcode = null;
			for (int i = 0; i < data.length; i++) {
				if(!(data[i][2]+"").equals(adcode)){
					if(!(adxml+ctntxmls).equals("")){
						adctntxml += XML.encapContent("adcontent", adxml+ctntxmls);
					}
					adcode = (data[i][2]+"");
					adxml = "";
					for (int j = 0; j < adclen; j++) {
						adxml += XML.encapContent("col", data[i][j]+"");
					}
				}
				ctntxml = "";
				for (int j = adclen; j < data[i].length; j++) {
					ctntxml += XML.encapContent("col", data[i][j]+"");
				}
				ctntxml = XML.encapContent("content", ctntxml);
				ctntxmls += ctntxml;
			}
			adctntxml += XML.encapContent("adcontent", adxml+ctntxmls);
			xml = adctntxml;
		}
		return xml;
	}
	
	
}
