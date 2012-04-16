package com.ehl.login;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.appframe.Console;
import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.common.Setting;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.loginstate.StateListener;
import com.ehl.sm.base.Constant;
import com.ehl.sm.common.Constants;
import com.ehl.sm.common.ui.UInterfaceFactory;
import com.ehl.sm.common.ui.uinterface.UInterface;
import com.ehl.sm.common.util.CreateSequence;
import com.ehl.sm.common.util.GetIpAddress;
import com.ehl.sm.pcs.DepartmentManage;
import com.ehl.sm.sysmanage.LogManage;

/**
 * @======================================================================================================================================
 * @类型说明:子系统登录验证，根据用户的帐号、密码进行信息验证
 * @创建者：wangyali
 * @创建日期 2007-10-11
 * @======================================================================================================================================
 */
public class Login extends Controller {
	Logger logger = Logger.getLogger(Login.class);
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @作者：wangyali
	 * @版本号：1.0
	 * @函数说明：用户登录，将用户输入的帐号、密码及输入的验证码进行信息验证，并做出相应的处理
	 * @参数：
	 * @返回：
	 * @创建日期：2007-10-11
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public ActionForward doLogin(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String pname = request.getParameter("pname") == null ? "" : request.getParameter("pname");
		String pwd   = request.getParameter("pwd") == null ? "" : request.getParameter("pwd");
		String loginModel   = request.getParameter("loginModel") == null ? "" : request.getParameter("loginModel");
	    String strPname = pname.trim();                       //返回字符串的副本，忽略前导空白和尾部空白。
	    Object returnObj = null;
	    try{
			String sql      = "SELECT user_.USERCODE,user_.DEPTCODE,DEPT.JGMC,user_.APPID,DEPT.JGLX,DEPT.JGCCBM,DEPT.ISGSDD FROM T_SYS_USER user_,T_SYS_DEPARTMENT DEPT WHERE user_.DEPTCODE=DEPT.JGID AND user_.USERNAME='"+strPname+"'";
			Object[] userInfo    = DBHandler.getLineResult(sql);
			String pcode = null;
			boolean checkUserResult = false;
			String returnStr = "success";
			if (userInfo != null && (pcode = String.valueOf(userInfo[0])) != null
					&& !pcode.equals("")) {
				//调用接口验证用户有效性
				//UInterfaceImple ui = new UInterfaceImple();
				UInterface ui = UInterfaceFactory.getInstance();
				checkUserResult = ui.checkUser(strPname,pwd);
			}else{
				Console.infoprintln("isno");
				returnStr="failure";
			}
			if(checkUserResult){	//登陆成功
				//验证是否超过用户同时在线人数限制
				String jgid = String.valueOf(userInfo[1]);
				String mark = "00";
				String appid = String.valueOf(userInfo[3]);
				appid = appid == null ? "1002" : appid;
				String[] jgstrs = { "zod", "zd", "dd" };
				String[] jgcodes = { jgid.substring(2, 4),
						jgid.substring(4, 6), jgid.substring(6, 8) };
				String[] rolestrs = { "cx", "ld", "zb" };
				String[] rolecodes = { "1002", "1005", "1001" };
				int limit = -1;
				for (int i = 0; i < jgcodes.length; i++) {
					if (jgcodes[i].equals(mark)) {
						for (int j = 0; j < rolecodes.length; j++) {
							if (rolecodes[j].equals(appid)) {
								limit = Setting.getInt(jgstrs[i] + rolestrs[j]);
							}
						}
					}
				}
				boolean isAllowLogin = true;
				if(limit != -1){
					sql = "select count(id) from t_sys_userstate where username='"+strPname+"'";
					int fact = Integer.parseInt(String.valueOf(DBHandler.getSingleResult(sql)));
					if(fact >= limit){
						session.setAttribute(Constants.ERRMESSUSER_KEY, "当前用户在线人数超过限制!");
						returnStr = "failure";
						isAllowLogin = false;
					}
				}
				if(isAllowLogin){
					//向session中添加用户信息
					session.setAttribute(Constant.UNAME_VAR, strPname);
					session.setAttribute(Constant.PWD_VAR, pwd);
					session.setAttribute(Constant.JGID_VAR, jgid);
					session.setAttribute(Constant.JGMC_VAR, (String)userInfo[2]);
					session.setAttribute(Constant.APPID_VAR, appid);
					session.setAttribute(Constant.JGLX_VAR, (String)userInfo[4]);
					session.setAttribute(Constant.JGCC_VAR, (String)userInfo[5]);
					session.setAttribute(Constant.ISGSDD_VAR, (String)userInfo[6]);
					session.setAttribute(Constants.PNAME_KEY, strPname);
					session.setAttribute(Constants.PCODE_KEY, pcode);
					String basePath = Constant.getBasePath(request);
					session.setAttribute(Constant.SERVER_URL_VAR, basePath+"/");
					basePath =  request.getContextPath() + "/";
					session.setAttribute(Constant.APP_URL_VAR, basePath);
					session.setAttribute(Constant.PUBLISH_TIME_VAR, Setting.getString("publish_time"));
					//设置session过期时间
					//值班用户会话不过期，设置成不过期会导致无法清除用户登录数据，设置会话时间为1天 
					//其他用户会话120分钟过期，保持默认值
					if(appid.equals("1001")){
						session.setMaxInactiveInterval(60 * 60 * 24);
					}
					String personName = getPersonNameByUserCode(pcode);
					//日志编码
					String LCODE = CreateSequence.getMaxForId("t_sys_log", "LCode", 20);
					session.setAttribute(Constants.LCODE_KEY, LCODE);
					//监听Session
					String clientIP = request.getRemoteAddr();
					String id =CreateSequence.getMaxForId("T_SYS_USERSTATE","id",12);//获取编码
					session.setAttribute("SessionListener", new com.ehl.login.SessionListener(id,pcode,strPname,LCODE,clientIP));
					String ipAddress = GetIpAddress.getClientAddress(request);
					//调用LogManage的updateData方法写日志
					LogManage.updateData(LCODE,strPname,ipAddress,"","");
					if(personName == null || personName.length() == 0){
						personName = pname;
					}
					String deptInfoStr = DepartmentManage.getDeptInfo(request, "1");
					out.write(pname +";"+ deptInfoStr+";" + personName);
				}
			}else{
				Object ucode = session.getAttribute(Constants.LCODE_KEY);
				session.setAttribute(Constants.ERRMESSUSER_KEY, "用户或密码错误!");
				String lcode = "";
				if (ucode!=null){
					lcode = (String)ucode;
					//写日志
					LogManage.updateData(lcode,"","","","1");
				}
				out.write("failure");	
				returnStr="failure";
			}
			returnObj = action.findForward(returnStr);
			if(loginModel.equals("GD")){
				returnObj = null;
			}
	    }catch(Exception ex){
	    	if(ex.getMessage().indexOf("could not establish the connection") != -1){
	    		out.write("noConnection");
	    		
	    	}else{
	    		out.write("exception");
	    	}
	    	logger.error("网站登录异常:"+ex.getMessage());
	    	return null;
	    }
		return (ActionForward)returnObj;
	}
	
	
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：从session中获取用户登录信息并返回
	 * @参数：
	 * @返回：
	 * @创建日期：2008-10-29
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public ActionForward doIsLogin(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Object pnameObj = session.getAttribute(Constants.PNAME_KEY);
		String pname = "";
		if(pnameObj != null){
			pname = StringHelper.obj2str(pnameObj);
		}
		out.write(pname);	
	return null;	
	}
	
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：从session中获取用户登录信息并返回
	 * @参数：
	 * @返回：
	 * @创建日期：2008-10-29
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public ActionForward doLogout(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Object pnameObj = (String)session.getAttribute(Constants.PNAME_KEY);
//		String clientIP= request.getRemoteAddr();
		if(pnameObj == null){
			//session.invalidate();
			out.write("noLogin");	
			 return null;
		}else{
//			String LCODE = (String)session.getAttribute(Constants.LCODE_KEY);
//          LogManage.updateData(LCODE,"","","","1");
			session.removeAttribute(Constants.PCODE_KEY);
			session.removeAttribute(Constants.PNAME_KEY);
			session.removeAttribute(Constants.LCODE_KEY);
//			StateListener.deleteUserState(pnameObj.toString(),clientIP);//清空该用户登录信息
			session.removeAttribute("SessionListener");
			out.write("success");
			return null;
		}
	}
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：验证用户是否有系统管理中心权限
	 * @参数：
	 * @创建日期：2009-7-6
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public ActionForward doHasThePrive(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String pname = StringHelper.obj2str(session.getAttribute(Constants.PNAME_KEY), "");
		String funcCode = StringHelper.obj2str(request.getParameter("funcCode"), "");
		boolean hasThePrive = false;
		
		String sql      = "SELECT USERCODE FROM T_sys_USER WHERE USERNAME='"+pname.trim()+"'";
		String pcode    = StringHelper.obj2str(DBHandler.getSingleResult(sql));
		boolean checkUserResult = false;
		if(pcode.length() > 0){
			
			checkUserResult = true;
		}
		if(checkUserResult){//已登录
			if(pname.trim().equals("admin")){
				hasThePrive = true;	
				out.write("yes");	
				return null;
			}
		    String funsql = "select funccode from t_sys_privfunc where PrivCode in "
				+" (select privcode from t_sys_rolepriv where rolecode in"
				+" (select rolecode from t_sys_userrole where usercode=(select Usercode from t_sys_user " 
				+ " where username='" +pname.trim()+"'))) and funccode= '"+funcCode+"'" ;
		    Object[][] fun = DBHandler.getMultiResult(funsql);
		    if(fun != null){
		    	hasThePrive = true;	
		    }
		}
		if(hasThePrive){
			out.write("yes");		
		}else{
			out.write("no");		
		}
	    return null;
	}
	
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：根据登陆用户名获取对应人员姓名
	 * @参数：
	 * @返回：
	 * @创建日期：2009-10-15
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public static String getPersonNameByUserName(String username){
		String sql = "SELECT XM FROM t_sys_person WHERE RYID= (SELECT PERSONCODE FROM T_SYS_USER WHERE USERNAME='"+username+"')";
		Object res = DBHandler.getSingleResult(sql);
		if(res == null){
			res = username;
		}
		return StringHelper.obj2str(res, "");
	}
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：根据登陆用户编号获取对应人员姓名
	 * @参数：
	 * @返回：
	 * @创建日期：2009-10-15
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public static String getPersonNameByUserCode(String usercode){
		String sql = "SELECT XM FROM t_sys_person WHERE RYID= (SELECT PERSONCODE FROM T_SYS_USER WHERE USERCODE='"+usercode+"')";
		Object res = DBHandler.getSingleResult(sql);
		return StringHelper.obj2str(res, "");
	}
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：获取登录用户信息(权限内的系统编号,对应人员姓名和所属机构名)
	 * @参数：
	 * @返回：
	 * @创建日期：2010-08-10
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public ActionForward doGetLoginInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
//		HttpSession session = request.getSession();
		String userName = StringHelper.obj2str(request.getParameter("userName"), "");
		StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		//获取权限内的系统编号
		UInterface ui = UInterfaceFactory.getInstance();
		Object[][] sysCode = ui.getPermitSysCode(userName);
		//获取登录用户所属机构信息
		String deptInfoStr = DepartmentManage.getDeptInfo(request, "1");
		String[] deptInfo = deptInfoStr.split(",");
		String jgmc = "";
		if(deptInfo.length > 1){
			jgmc = deptInfo[1];
		}
        //获取登录用户对应人员
		String sCode = "";
		String personName = getPersonNameByUserName(userName);
		if(sysCode != null){
			for(int i = 0 ; i < sysCode.length; i ++){
				sCode += sysCode[i][0].toString() + " ";
				xmlData.append("<syscode>"+sysCode[i][0]+"</syscode>\n");
			}
		}
		xmlData.append("<dept>"+jgmc+"</dept>\n");
		xmlData.append("<person>"+personName+"</person>\n");
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");
		logger.info("获取登录信息：\n"+xmlData.toString());
		request.getSession().setAttribute("sCode", sCode.trim());
		out.write(xmlData.toString());	
	    return null;
	}
	
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：根据用户名和登录ip修改当前用户登录信息
	 * @参数：sysId-用户登录ip
	 * @返回：
	 * @创建日期：2010-9-14
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public ActionForward doUpdateLoginState(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		String sysid = request.getParameter("sysId");
		String sysname = request.getParameter("sysName");
		String username = request.getParameter("userName");
		PrintWriter out = response.getWriter();
		String sql = "";
		Boolean flag = true;
		try{
			String loginIP = request.getRemoteAddr();
			sql = "UPDATE T_SYS_USERSTATE SET SYSID='"+sysid+"' ,SYSNAME='"+sysname+"' WHERE USERNAME='"+username+"' AND  IP='"+loginIP+"'";
			flag = DBHandler.execute(sql);
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("修改当前用户登录状态报错:"+ex.getMessage()+" sql=="+sql);
		}
		if(flag){
			out.write("success");
		}else{
			out.write("failure");
		}
			
	return null;	
	}
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：判断当前登录用户是否对指定系统具有权限
	 * @参数：isOpen-是否只验证是否登录 "yes"-是
	 * @返回："noLogin"-尚未登录 "true"-有权限 "false"-没有权限
	 * @创建日期：2010-09-15
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public ActionForward doHasTheSysFun(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String sysId = StringHelper.obj2str(request.getParameter("sysId"), "");
		String sysName = StringHelper.obj2str(request.getParameter("sysName"), "");
		String isOpen = StringHelper.obj2str(request.getParameter("isOpen"), "");
		String loginIp = request.getRemoteAddr();
		String returnStr = "false";
		Object loginUserName = session.getAttribute(Constants.PNAME_KEY);
		if(loginUserName == null){
			out.write("noLogin");
			return null;
		}
		if(isOpen.equals("yes")){//只验证是否登录
			returnStr = "true";
		}else if(sysId.equals("")){
			returnStr = "true";
		}else{
        //获取权限内的系统编号
			UInterface ui = UInterfaceFactory.getInstance();
			Object[][] sysCode = ui.getPermitSysCode(loginUserName.toString());
			for(int i = 0; i < sysCode.length; i ++){
				if(StringHelper.obj2str(sysCode[i][0],"").equals(sysId)){
					returnStr = "true";
					break;
				}
			}
			/*记录用户登录子系统*/
			if(returnStr.equals("true")){
				StateListener.updateUserState(StringHelper.obj2str(loginUserName), sysId, sysName, loginIp);
			}
		}
		out.write(returnStr);	
	    return null;
	}
	
}
