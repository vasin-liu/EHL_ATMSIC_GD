package com.ehl.drpt.dailyRpt.core;

import org.apache.log4j.Logger;

import com.ehl.base.BaseCore;
import com.ehl.drpt.dailyRpt.dao.MaxRoadFlowDao;
import com.ehl.tira.util.XML;

public class MaxRoadFlowCore extends BaseCore {
    /** ��־�� */
    private Logger logger = Logger.getLogger(this.getClass());

    /** �������� */
    private String oname = "flow";

    /** �������ݷ����� */
    private MaxRoadFlowDao dao = new MaxRoadFlowDao();

    public MaxRoadFlowCore() {
	super.setLogger(logger);
	super.setDao(dao);
	super.setOname(oname);
    }

    public MaxRoadFlowDao getDao() {
	return dao;
    }

    /**
     * ��ȡͳ��xml����
     * 
     * @param sdate
     *            ��ʼ����
     * @param edate
     *            ��������
     * @return xml����
     */
    public String statis(String jgid, String sdate, String edate) {
	Object[][] objects = dao.statis(jgid, sdate, edate);
	String[] cnames = { "gbdm", "dlmc", "jgid", "jgmc", "date", "count" };
	String xml = XML.getNodes(oname, cnames, objects);
	xml = XML.getXML(xml);
	return xml;
    }
}
