package com.ehl.sm.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.sm.dao.CodeDao;
import com.ehl.sm.entity.Code;

public class CodeCore {
	private Logger logger = Logger.getLogger(CodeCore.class);
	private CodeDao dao = new CodeDao();
	
	public List<Code> select(String dmlb){
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("dmlb", dmlb);
		return dao.selectEntity(conditions);
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public CodeDao getDao() {  
		return dao;
	}

	public void setDao(CodeDao dao) {
		this.dao = dao;
	}
	
}
