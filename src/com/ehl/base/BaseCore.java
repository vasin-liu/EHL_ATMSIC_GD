package com.ehl.base;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.tira.util.XML;

/**
 * 基本业务逻辑类
 * 
 * @author Xiayx
 * 
 */
public abstract class BaseCore {

    /** 日志类 */
    private Logger logger;

    /** 基本数据访问类 */
    private BaseDao dao;

    /** 对象名称 */
    private String oname;

    /**
     * 添加信息
     * 
     * @param object
     *            对象信息
     * @return 主键编号
     */
    public String insert(Map<String, String> object) {
	return dao.insert(object);
    }

    /**
     * 获取信息，通过主键编号
     * 
     * @param id
     *            主键编号
     * @return 对象信息
     */
    public String getById(String id) {
	return getById(id, null);
    }

    /**
     * 获取信息，通过主键编号
     * 
     * @param id
     *            主键编号
     * @param referObject
     *            关联对象
     * @return 对象信息
     */
    public String getById(String id, String referObject) {
	String xml = null;
	Object[] object = dao.getById(id);
	if (object != null) {
	    xml = XML.getNode(getOname(), dao.getFields(), object,
		    referObject);
	}
	xml = XML.getXML(xml);
	return xml;
    }

    /**
     * 修改信息，通过主键编号
     * 
     * @param object
     *            对象信息
     * @return 是否修改成功
     */
    public boolean modifyById(Map<String, String> object) {
	return dao.modifyById(object);
    }

    /**
     * 删除信息，通过主键编号
     * 
     * @param object
     *            对象信息
     * @return 是否删除成功
     */
    public boolean deleteById(String id) {
	return dao.deleteById(id);
    }

    public Logger getLogger() {
	return logger;
    }

    public void setLogger(Logger logger) {
	this.logger = logger;
    }

    public BaseDao getDao() {
	return dao;
    }

    public void setDao(BaseDao dao) {
	this.dao = dao;
    }

    public String getOname() {
	return oname;
    }

    public void setOname(String oname) {
	this.oname = oname;
    }
}
