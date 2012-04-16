package com.ehl.prmplan.core;

import org.apache.log4j.Logger;

import com.ehl.base.BaseCore;
import com.ehl.prmplan.dao.BlogDao;
import com.ehl.tira.util.XML;

/**
 * ����΢���ز�ҵ���߼���
 * 
 * @author xiayouxue
 * 
 */
public class BlogCore extends BaseCore {

    /** ��־�� */
    private Logger logger = Logger.getLogger(this.getClass());

    /** �������� */
    private String oname = "blog";

    /** �������ݷ����� */
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
     * ��ȡͳ��xml����
     * @param sdate ��ʼ����
     * @param edate ��������
     * @return xml����
     */
    public String statis(String sdate, String edate) {
	Object[][] objects = dao.statis(sdate, edate);
	String xml = XML.getNodes(oname, new String[] { "jgid", "jgmc", "count" }, objects);
	xml = XML.getXML(xml);
	return xml;
    }

}
