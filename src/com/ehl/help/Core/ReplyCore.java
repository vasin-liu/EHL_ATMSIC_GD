package com.ehl.help.Core;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.ehl.base.BaseCore;
import com.ehl.help.dao.ReplyDao;
import com.ehl.util.Json;

/**
 * 回复业务逻辑类
 * 
 * @author xiayouxue
 * 
 */
public class ReplyCore extends BaseCore {

	/** 日志类 */
	private Logger logger = Logger.getLogger(this.getClass());
	
	/** 回复数据访问类 */
	private ReplyDao dao = new ReplyDao();
	
	/** 对象名称 */
	private String oname = "reply";
	
	public ReplyCore() {
		super.setLogger(logger);
		super.setDao(dao);
		super.setOname(oname);
	}
	
	/**
	 * 通过留言编号获取回复信息
	 * @param lid 留言编号
	 * @return 回复信息列表
	 */
	public JSONArray getByLid(String lid){
		Object[][] objects = dao.getByLid(lid);
		return Json.toJson(objects, dao.getFields());
	}

	public ReplyDao getDao() {
		return dao;
	}

	public void setDao(ReplyDao dao) {
		this.dao = dao;
	}

}
