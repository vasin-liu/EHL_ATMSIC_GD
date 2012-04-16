package com.ehl.dispatch.common;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;

/**
 * 快捷获取数据
 * 通过传递sql语句，来返回字符串结果
 * @author xyx
 *
 */
public class ShortcutAction extends Controller {
	
	public ActionForward doGetSimpleResult(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		String result = null;
		String sql = request.getParameter("sql");
		if(sql != null){
			Object[][] datas = DBHandler.getMultiResult(sql);
			if(datas != null){
				if(datas.length == 1){
					Object[] data = datas[0];
					if(data.length > 0){
						result = data[0]+"";
						for(int i=1;i<data.length;i++){
							result += "," + data[i];
						}
					}
				}
//				else if (datas.length > 1){
//					for(int i=0;i<datas.length;i++){
//						for (int j = 0; j < datas[i].length; j++) {
//							
//						}
//					}
//				}
			}
		}
		out.write(result);
		out.flush();
		out.close();
		return null;
	}
	

}
