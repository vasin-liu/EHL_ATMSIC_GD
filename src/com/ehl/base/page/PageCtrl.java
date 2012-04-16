package com.ehl.base.page;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.Console;
import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.data.sql.MultiDBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.base.util.BaseUtil;
import com.ehl.base.util.StringUtil;

// Referenced classes of package com.ehl.base.page:
//            PageCtrlUI

public class PageCtrl extends Controller {

	public PageCtrl() {
		logger = Logger.getLogger(PageCtrl.class);
	}

	public ActionForward doGetPageUtil(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		int pageSize = StringHelper
				.obj2int(request.getParameter("pageSize"), 0);
		if (pageSize == 0)
			pageSize = StringHelper.obj2int(BaseUtil
					.getSysOption("01", "01001"), 12);
		int currPage = StringHelper
				.obj2int(request.getParameter("currPage"), 1);
		String strSQL = StringUtil.convertSql(request.getParameter("exeSql"));
		String strCountSQL = StringUtil.convertSql(request
				.getParameter("exeCountSql"));
		String strDataSource = StringHelper.obj2str(request
				.getParameter("dataSource"), "");
		String strImpClassName = StringHelper.obj2str(request
				.getParameter("impClassName"), "");
		int pageNum = StringHelper.obj2int(request.getParameter("pageNum"), 0);
		long totalCount = getTotalCount(strSQL, strCountSQL, strDataSource);
		if (pageNum == 0)
			pageNum = getPageNum(totalCount, pageSize);
		String strPageSQL = getPerPageSql(strSQL, currPage, pageSize);
		try {
			Object oResult[][] = (Object[][]) null;
			if (strDataSource.length() > 0)
				oResult = MultiDBHandler.getMultiResult(strDataSource,
						strPageSQL);
			else
				oResult = DBHandler.getMultiResult(strPageSQL);
			StringBuffer xmlData = new StringBuffer(
					"<?xml version='1.0' encoding='UTF-8'?>\n");
			xmlData.append("<rows>\n");
			xmlData.append((new StringBuilder("<userdata name='exeSql'>"))
					.append(StringUtil.xmlDataFilter(strSQL)).append(
							"</userdata>").toString());
			xmlData.append((new StringBuilder("<userdata name='exeCountSql'>"))
					.append(StringUtil.xmlDataFilter(strCountSQL)).append(
							"</userdata>").toString());
			xmlData.append((new StringBuilder("<totalCount>"))
					.append(totalCount).append(
							"</totalCount>").toString());
			if (oResult == null) {
				xmlData.append("<userdata name='pageNum'>0</userdata>");
				xmlData.append("<userdata name='currPage'>0</userdata>\n");
			} else {
				xmlData.append((new StringBuilder("<userdata name='pageNum'>"))
						.append(String.valueOf(pageNum)).append("</userdata>")
						.toString());
				xmlData
						.append((new StringBuilder("<userdata name='currPage'>"))
								.append(String.valueOf(currPage)).append(
										"</userdata>\n").toString());
				if (strImpClassName.length() > 0) {
					Class impClassName = Class.forName(strImpClassName);
					PageCtrlUI ui = (PageCtrlUI) impClassName.newInstance();
					String strGridDatas = ui.getGridDatas(oResult);
					xmlData.append(strGridDatas);
				} else {
					for (int j = 0; j < oResult.length; j++) {
						xmlData.append((new StringBuilder("<row id='")).append(
								j).append("'>").toString());
						for (int i = 0; i < oResult[j].length; i++)
							xmlData.append((new StringBuilder("<col>")).append(
									StringHelper.obj2str(oResult[j][i]))
									.append("</col>").toString());

						xmlData.append("</row>\n");
					}

				}
			}
			xmlData.append("</rows>\n");
			out.write(xmlData.toString());
		} catch (Exception e) {
			Console
					.infoprintln((new StringBuilder(
							"[\u7CFB\u7EDF\u7BA1\u7406\u4E2D\u5FC3]-\u5206\u9875\u7C7B\u51FA\u5F02\u5E38:"))
							.append(e.getMessage()).toString());
			logger
					.error((new StringBuilder(
							"[\u7CFB\u7EDF\u7BA1\u7406\u4E2D\u5FC3]-\u5206\u9875\u7C7B\u51FA\u5F02\u5E38:"))
							.append(e.getMessage()).append(
									" \n\u9519\u8BEFSQL\u8BED\u53E5\uFF1A")
							.append(strPageSQL).toString());
			return null;
		}
		out.close();
		return null;
	}

	private static String getPerPageSql(String sql, int currPage, int pageSize) {
		long beginRow = (currPage - 1) * pageSize + 1;
		long endRow = currPage * pageSize;
		String perPageSql = (new StringBuilder(
				"SELECT * FROM (SELECT a.*,rownum row_num FROM (")).append(sql)
				.append(")a WHERE rownum<=").append(endRow).append(
						")b WHERE b.row_num>=").append(beginRow).toString();
		return perPageSql;
	}

	private static int getPageNum(String sql, String countSql, int pageSize,
			String dataSource) {
		long totalRows = getTotalCount(sql, countSql, dataSource);
		return getPageNum(totalRows, pageSize);
	}
	
	private static int getPageNum(long totalRows, int pageSize) {
		int pageNum = 0;
		if (totalRows % (long) pageSize == 0L)
			pageNum = (int) totalRows / pageSize;
		else
			pageNum = (int) totalRows / pageSize + 1;
		int maxPageNum = StringHelper.obj2int(BaseUtil.getSysOption("01",
				"01002"), 500);
		if (pageNum > maxPageNum)
			pageNum = maxPageNum;
		return pageNum;
	}

	/**
	 * @param sql
	 * @param countSql
	 * @param dataSource
	 * @return
	 */
	private static long getTotalCount(String sql, String countSql,
			String dataSource) {
		String strSql = "";
		if (countSql.length() == 0) {
			HashMap map = StringUtil.splitSQL(sql);
			if (map.get("GROUP") == null
					&& ((String) map.get("SELECT")).indexOf("DISTINCT") == -1
					&& StringUtil.replaceI(sql, "union", "UNION").indexOf(
							"UNION") == -1)
				strSql = (new StringBuilder("SELECT /*+ALL_ROWS*/ COUNT(1) "))
						.append((String) map.get("FROM")).append(
								StringHelper.obj2str(map.get("WHERE"), ""))
						.append(StringHelper.obj2str(map.get("GROUP"), ""))
						.toString();
			else
				strSql = (new StringBuilder(
						"SELECT /*+ALL_ROWS*/ COUNT(1) FROM (")).append(sql)
						.append(")").toString();
		} else {
			strSql = countSql;
		}
		long totalRows = 0L;
		if (dataSource.length() > 0) {
			Object oResult[][] = MultiDBHandler.getMultiResult(dataSource,
					strSql);
			totalRows = StringHelper.obj2long(oResult[0][0], 0L);
		} else {
			totalRows = StringHelper.obj2long(
					DBHandler.getSingleResult(strSql), 0L);
		}
		return totalRows;
	}

	Logger logger;
}


/*
	DECOMPILATION REPORT

	Decompiled from: E:\work\myeclipse\EHL_ATMSIC_GD\WebRoot\WEB-INF\lib\Ehl_Base_1.0.0.7.jar
	Total time: 32 ms
	Jad reported messages/errors:
The class file version is 49.0 (only 45.3, 46.0 and 47.0 are supported)
	Exit status: 0
	Caught exceptions:
*/