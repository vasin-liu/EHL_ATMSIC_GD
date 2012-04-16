package com.ehl.tira.util.statanalysis;

import java.io.IOException;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ServletUser {

	public final static String strNull = "null";

	/***
	 * 获取请求的参数值
	 * 
	 * @param request
	 * @param params
	 * @return
	 */
	public final static Map<String, String> getParameters(
			HttpServletRequest request, String[] params) {
		if (Parameter.isNonObj(request) == false) {
			return null;
		}
		if (Parameter.isNonArrayWithNonObj(params) == false) {
			return null;
		}

		Map<String, String> paramM = new HashMap<String, String>();
		for (int i = 0; i < params.length; i++) {
			paramM.put(params[i], request.getParameter(params[i]));
		}

		return paramM;
	}
	
	/***
	 * servlet处理请求方法的初始化
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public final static PrintWriter doEncodingInit(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		return out;
	}

	/**
	 * 输出相应xml数据
	 * 
	 * @param request
	 * @param response
	 * @param content
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public final static void doOut(HttpServletRequest request,
			HttpServletResponse response, String content)
			throws UnsupportedEncodingException, IOException {
		PrintWriter out = doEncodingInit(request, response);
		out.write(content);
		out.flush();
		out.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getParameters(null, new String[] {});
	}

}
