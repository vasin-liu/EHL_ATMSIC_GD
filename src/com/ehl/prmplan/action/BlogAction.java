package com.ehl.prmplan.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.mapping.ActionForward;
import com.ehl.base.BaseAction;
import com.ehl.prmplan.core.BlogCore;

/**
 * ����΢���ز�ҵ�������
 * 
 * @author xiayouxue
 * 
 */
public class BlogAction extends BaseAction {

    /** ��־�� */
    private Logger logger = Logger.getLogger(this.getClass());

    /** ����ҵ���߼��� */
    private BlogCore core = new BlogCore();

    public BlogAction() {
	super.setLogger(logger);
	super.setCore(core);
    }
    
    /**
     * ͳ����Ϣ
     * 
     * @param action
     * @param request
     * @param response
     * @return
     * @throws Throwable
     */
    public ActionForward doStatis(Action action, HttpServletRequest request,
	    HttpServletResponse response) throws Throwable {
	request.setCharacterEncoding("UTF-8");
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/xml");
	PrintWriter out = response.getWriter();
	String sdate = request.getParameter("sdate");
	String edate = request.getParameter("edate");
	String xml = core.statis(sdate,edate);
	logger.debug("xml:\n"+xml);
	out.write(xml);
	out.close();
	return null;
    }
}
