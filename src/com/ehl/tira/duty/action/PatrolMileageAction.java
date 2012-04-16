package com.ehl.tira.duty.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.tira.duty.core.PatrolMileageCore;

/**
 * PatrolMileageAction(Ѳ��,���)Ѳ����̷���
 * 
 * @author xiayouxue
 * 
 */
public class PatrolMileageAction extends Controller{
	
	private Logger logger = Logger.getLogger(PatrolMileageAction.class);
	private PatrolMileageCore pmCore = new PatrolMileageCore();
	private final String avg = "ƽ��";
	private final String total = "��";
	
	public ActionForward doTotalDept(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ʼ���ַ���
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// ��ȡ����
		String area = request.getParameter("area");
		String dateS = request.getParameter("dateS");
		String dateE = request.getParameter("dateE");
		//�жϲ���
		if (area == null || dateS == null || dateE == null ) {
			logger(total, "getParam");
			return null;
		}
		//���ݲ�����ȡ������
		String xml = "";
		xml = pmCore.totalDept(area, dateS, dateE);
		//�������
		out.write(xml);
		//ҳ����ת
		return null;
	}
	
	public ActionForward doTotalTime(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ʼ���ַ���
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// ��ȡ����
		String dept = request.getParameter("dept");
		String dateS = request.getParameter("dateS");
		String dateE = request.getParameter("dateE");
		//�жϲ���
		if (dept == null || dateS == null || dateE == null) {
			logger(total, "getParam");
			return null;
		}
		//���ݲ�����ȡ������
		String xml = "";
		xml = pmCore.totalTime(dept, dateS, dateE);
		//�������
		out.write(xml);
		//ҳ����ת
		return null;
	}
	
	public ActionForward doAvgDept(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ʼ���ַ���
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// ��ȡ����
		String area = request.getParameter("area");
		String dateS = request.getParameter("dateS");
		String dateE = request.getParameter("dateE");
		//�жϲ���
		if (area == null  || dateS == null || dateE == null) {
			logger(total, "getParam");
			return null;
		}
		//���ݲ�����ȡ������
		String xml = "";
		xml = pmCore.avgDept(area, dateS, dateE);
		//�������
		out.write(xml);
		//ҳ����ת
		return null;
	}
	
	public ActionForward doAvgTime(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ʼ���ַ���
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// ��ȡ����
		String dept = request.getParameter("dept");
		String dateS = request.getParameter("dateS");
		String dateE = request.getParameter("dateE");
		//�жϲ���
		if ( dept == null || dateS == null || dateE == null) {
			logger(total, "getParam");
			return null;
		}
		//���ݲ�����ȡ������
		String xml = "";
		xml = pmCore.avgTime(dept, dateS, dateE);
		//�������
		out.write(xml);
		//ҳ����ת
		return null;
	}
	
	
	/**
	 * Ѳ��ƽ����̷���
	 */
	public ActionForward doAnalysis(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ʼ���ַ���
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		// ��ȡ����
		String area = request.getParameter("area");
		String dept = request.getParameter("dept");
		String dateS = request.getParameter("dateS");
		String dateE = request.getParameter("dateE");
		String isAvgStr = request.getParameter("isAvg");
		if (area == null || dept == null || dateS == null || dateE == null 
				|| isAvgStr == null) {
			logger(total, "getParam");
			return null;
		}
		
		//���ݲ�����ȡ������
		String xml = "";
		xml = pmCore.createPMCX(area, dept, dateS, dateE, Boolean.parseBoolean(isAvgStr));
		//�������
		out.write(xml);
		//ҳ����ת
		return null;
	}

	
	private void logger(String action, String msg){
		Map<String,String> stepM = new HashMap<String, String>();
		stepM.put("getParam", "��ȡ����");
		logger.error("Ѳ��"+action+"��̷���->"+stepM.get(msg)+"����");
	}
}
