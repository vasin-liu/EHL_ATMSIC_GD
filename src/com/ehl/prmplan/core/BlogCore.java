package com.ehl.prmplan.core;

import org.apache.log4j.Logger;

import com.ehl.base.BaseCore;
import com.ehl.prmplan.dao.BlogDao;
import com.ehl.tira.util.XML;

/**
 * 交宣微博素材业务逻辑类
 * 
 * @author xiayouxue
 * 
 */
public class BlogCore extends BaseCore {

    /** 日志类 */
    private Logger logger = Logger.getLogger(this.getClass());

    /** 对象名称 */
    private String oname = "blog";

    /** 问题数据访问类 */
    private BlogDao dao = new BlogDao();

    public BlogCore() {
	super.setLogger(logger);
	super.setDao(dao);
	super.setOname(oname);
    }

    public BlogDao getDao() {
	return dao;
    }

    public void setDao(BlogDao dao) {
	this.dao = dao;
    }
    
    /**
     * 获取统计xml数据
     * @param sdate 起始日期
     * @param edate 结束日期
     * @return xml数据
     */
    public String statis(String sdate, String edate) {
	Object[][] objects = dao.statis(sdate, edate);
	String xml = XML.getNodes(oname, new String[] { "jgid", "jgmc", "count" }, objects);
	xml = XML.getXML(xml);
	return xml;
    }

}
