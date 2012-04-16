package com.ehl.drpt.dailyRpt.core;

import org.apache.log4j.Logger;

import com.ehl.base.BaseCore;
import com.ehl.drpt.dailyRpt.dao.MaxRoadFlowDao;
import com.ehl.tira.util.XML;

public class MaxRoadFlowCore extends BaseCore {
    /** 日志类 */
    private Logger logger = Logger.getLogger(this.getClass());

    /** 对象名称 */
    private String oname = "flow";

    /** 流量数据访问类 */
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
     * 获取统计xml数据
     * 
     * @param sdate
     *            起始日期
     * @param edate
     *            结束日期
     * @return xml数据
     */
    public String statis(String jgid, String sdate, String edate) {
	Object[][] objects = dao.statis(jgid, sdate, edate);
	String[] cnames = { "gbdm", "dlmc", "jgid", "jgmc", "date", "count" };
	String xml = XML.getNodes(oname, cnames, objects);
	xml = XML.getXML(xml);
	return xml;
    }
}
