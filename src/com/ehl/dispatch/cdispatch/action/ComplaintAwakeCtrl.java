package com.ehl.dispatch.cdispatch.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.base.Constants;

public class ComplaintAwakeCtrl extends Controller {
	Logger logger = Logger.getLogger(ComplaintAwakeCtrl.class);

	public ActionForward doDealAwakeList(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();
		String personsql="select personcode from t_sys_user where usercode='"+(String)session.getAttribute(Constants.PCODE_KEY)+"'";
		String personId=StringHelper.obj2str(DBHandler.getSingleResult(personsql),"");
//		String personId=StringHelper.obj2str(request.getParameter("personId"),"");
//		String sql="select cpid,to_char(TSDJSJ,'yyyy-mm-dd hh24:mi'),TSRXM,TSRDH,TSYWZLB,f_get_dept(TSJG),TSJH,LZZT from T_OA_COMPLAINT";
//		Object[][] res=DBHandler.getMultiResult(sql);
		Object[][] res=dealAwakeDao(personId);
		
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		
		if(res!=null){
			for(int i=0;i<res.length;i++){
				xmlData.append("<row i='"+i+"'>\n");
				for(int j=0;j<res[i].length;j++){
					xmlData.append("<col>");
					xmlData.append(StringHelper.obj2str(res[i][j],""));
					xmlData.append("</col>\n");
				}
				xmlData.append("</row>\n");
			}
		}
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		out.write(xmlData.toString());
		out.close();
		return null;
	}	
	
	
	public ActionForward doDealAwake(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();
		String personsql="select personcode from t_sys_user where usercode='"+(String)session.getAttribute(Constants.PCODE_KEY)+"'";
		String personId=StringHelper.obj2str(DBHandler.getSingleResult(personsql),"");
//		String personId=StringHelper.obj2str(request.getParameter("personId"),"");
//		String sql="select cpid,to_char(TSDJSJ,'yyyy-mm-dd hh24:mi'),TSRXM,TSRDH,TSYWZLB,f_get_dept(TSJG),TSJH,LZZT from T_OA_COMPLAINT";
//		Object[][] res=DBHandler.getMultiResult(sql);
		Object[][] res=dealAwakeDao(personId);	
		String have="false";
		if(res!=null){
			have="true";
		}
		out.write(have);
		out.close();
		return null;
	}	
	
	
	public ActionForward doDealListCount(Action action, HttpServletRequest request,HttpServletResponse response) throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		response.setContentType("text/xml"); 	// 指定输出内容的格式
		PrintWriter out = response.getWriter();
		String personsql="select personcode from t_sys_user where usercode='"+(String)session.getAttribute(Constants.PCODE_KEY)+"'";
		String personId=StringHelper.obj2str(DBHandler.getSingleResult(personsql),"");
//		String personId=StringHelper.obj2str(request.getParameter("personId"),"");
//		String sql="select cpid,to_char(TSDJSJ,'yyyy-mm-dd hh24:mi'),TSRXM,TSRDH,TSYWZLB,f_get_dept(TSJG),TSJH,LZZT from T_OA_COMPLAINT";
//		Object[][] res=DBHandler.getMultiResult(sql);
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		xmlData.append("<row>\n");
		Object[][] res=dealAwakeDao(personId);	
		int have=0;
		if(res!=null){
			have=res.length;
			xmlData.append("<col>"+have+"</col>");			
		}else{
			xmlData.append("<col>　</col>");
		}
		xmlData.append("\n</row>\n");
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		out.write(xmlData.toString());
		out.close();
		return null;
	}
	
	
	public Object[][] dealAwakeDao(String personId){		
		
		String zby="'000001'"; //值班员
		String zhk="'000004','000010','000016'"; //指挥科
		String zhc="'000007','0000073','000076'"; //指挥处
		String jld="'000013','000022','000070'"; //局领导
		String ywc="'000019','000064','000067'"; //业务处
		String zd="'000052','000058'";//总队承办人
		String zhidui="'000031','000034','000037','000046','000049','000079','000082'";//支队
		String dadui="'000040','000043'";//大队
		
		Object[][] res=null;
		try{
			String sql="select cpid,to_char(TSDJSJ,'yyyy-mm-dd hh24:mi'),TSRXM,TSRDH,TSYWZLB,f_get_dept(TSJG)," ;
			sql+="t.TSJH,c.name,t.LZZT from T_OA_COMPLAINT t,t_oa_code c";
			sql+=" where  t.LZZT=c.id";
			
			sql+=" and ((LZZT in ("+zby+") and DJRID like '%"+personId+"%')";
			sql+=" or (LZZT in ("+zhk+") and ZHKJSR like '%"+personId+"%')";
			sql+=" or (LZZT in ("+zhc+") and ZHCJSR like '%"+personId+"%')";
			sql+=" or (LZZT in ("+jld+") and JLDJSR like '%"+personId+"%')";
			sql+=" or (LZZT in ("+ywc+") and YWCJSR like '%"+personId+"%')";			
			sql+=" or (LZZT in ("+zd+") and YWCJSR like '%"+personId+"%')";
			sql+=" or (LZZT in ("+zhidui+") and ZDJGID like '%"+personId+"%')";
			sql+=" or (LZZT in ("+dadui+") and DDJGID like '%"+personId+"%'))";
			System.out.println("!!!!!"+sql);
			res=DBHandler.getMultiResult(sql);
		}catch(Exception e){
			logger.error("警情投诉提醒错误:"+e.getMessage());			
		}finally{
			return res;
		}
	}
}
