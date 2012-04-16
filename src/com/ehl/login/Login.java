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
 * @����˵��:��ϵͳ��¼��֤�������û����ʺš����������Ϣ��֤
 * @�����ߣ�wangyali
 * @�������� 2007-10-11
 * @======================================================================================================================================
 */
public class Login extends Controller {
	Logger logger = Logger.getLogger(Login.class);
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @���ߣ�wangyali
	 * @�汾�ţ�1.0
	 * @����˵�����û���¼�����û�������ʺš����뼰�������֤�������Ϣ��֤����������Ӧ�Ĵ���
	 * @������
	 * @���أ�
	 * @�������ڣ�2007-10-11
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
	    String strPname = pname.trim();                       //�����ַ����ĸ���������ǰ���հ׺�β���հס�
	    Object returnObj = null;
	    try{
			String sql      = "SELECT user_.USERCODE,user_.DEPTCODE,DEPT.JGMC,user_.APPID,DEPT.JGLX,DEPT.JGCCBM,DEPT.ISGSDD FROM T_SYS_USER user_,T_SYS_DEPARTMENT DEPT WHERE user_.DEPTCODE=DEPT.JGID AND user_.USERNAME='"+strPname+"'";
			Object[] userInfo    = DBHandler.getLineResult(sql);
			String pcode = null;
			boolean checkUserResult = false;
			String returnStr = "success";
			if (userInfo != null && (pcode = String.valueOf(userInfo[0])) != null
					&& !pcode.equals("")) {
				//���ýӿ���֤�û���Ч��
				//UInterfaceImple ui = new UInterfaceImple();
				UInterface ui = UInterfaceFactory.getInstance();
				checkUserResult = ui.checkUser(strPname,pwd);
			}else{
				Console.infoprintln("isno");
				returnStr="failure";
			}
			if(checkUserResult){	//��½�ɹ�
				//��֤�Ƿ񳬹��û�ͬʱ������������
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
						session.setAttribute(Constants.ERRMESSUSER_KEY, "��ǰ�û�����������������!");
						returnStr = "failure";
						isAllowLogin = false;
					}
				}
				if(isAllowLogin){
					//��session������û���Ϣ
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
					//����session����ʱ��
					//ֵ���û��Ự�����ڣ����óɲ����ڻᵼ���޷�����û���¼���ݣ����ûỰʱ��Ϊ1�� 
					//�����û��Ự120���ӹ��ڣ�����Ĭ��ֵ
					if(appid.equals("1001")){
						session.setMaxInactiveInterval(60 * 60 * 24);
					}
					String personName = getPersonNameByUserCode(pcode);
					//��־����
					String LCODE = CreateSequence.getMaxForId("t_sys_log", "LCode", 20);
					session.setAttribute(Constants.LCODE_KEY, LCODE);
					//����Session
					String clientIP = request.getRemoteAddr();
					String id =CreateSequence.getMaxForId("T_SYS_USERSTATE","id",12);//��ȡ����
					session.setAttribute("SessionListener", new com.ehl.login.SessionListener(id,pcode,strPname,LCODE,clientIP));
					String ipAddress = GetIpAddress.getClientAddress(request);
					//����LogManage��updateData����д��־
					LogManage.updateData(LCODE,strPname,ipAddress,"","");
					if(personName == null || personName.length() == 0){
						personName = pname;
					}
					String deptInfoStr = DepartmentManage.getDeptInfo(request, "1");
					out.write(pname +";"+ deptInfoStr+";" + personName);
				}
			}else{
				Object ucode = session.getAttribute(Constants.LCODE_KEY);
				session.setAttribute(Constants.ERRMESSUSER_KEY, "�û����������!");
				String lcode = "";
				if (ucode!=null){
					lcode = (String)ucode;
					//д��־
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
	    	logger.error("��վ��¼�쳣:"+ex.getMessage());
	    	return null;
	    }
		return (ActionForward)returnObj;
	}
	
	
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵������session�л�ȡ�û���¼��Ϣ������
	 * @������
	 * @���أ�
	 * @�������ڣ�2008-10-29
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
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵������session�л�ȡ�û���¼��Ϣ������
	 * @������
	 * @���أ�
	 * @�������ڣ�2008-10-29
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
//			StateListener.deleteUserState(pnameObj.toString(),clientIP);//��ո��û���¼��Ϣ
			session.removeAttribute("SessionListener");
			out.write("success");
			return null;
		}
	}
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵������֤�û��Ƿ���ϵͳ��������Ȩ��
	 * @������
	 * @�������ڣ�2009-7-6
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
		if(checkUserResult){//�ѵ�¼
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
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵�������ݵ�½�û�����ȡ��Ӧ��Ա����
	 * @������
	 * @���أ�
	 * @�������ڣ�2009-10-15
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
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵�������ݵ�½�û���Ż�ȡ��Ӧ��Ա����
	 * @������
	 * @���أ�
	 * @�������ڣ�2009-10-15
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 */
	public static String getPersonNameByUserCode(String usercode){
		String sql = "SELECT XM FROM t_sys_person WHERE RYID= (SELECT PERSONCODE FROM T_SYS_USER WHERE USERCODE='"+usercode+"')";
		Object res = DBHandler.getSingleResult(sql);
		return StringHelper.obj2str(res, "");
	}
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵������ȡ��¼�û���Ϣ(Ȩ���ڵ�ϵͳ���,��Ӧ��Ա����������������)
	 * @������
	 * @���أ�
	 * @�������ڣ�2010-08-10
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
		//��ȡȨ���ڵ�ϵͳ���
		UInterface ui = UInterfaceFactory.getInstance();
		Object[][] sysCode = ui.getPermitSysCode(userName);
		//��ȡ��¼�û�����������Ϣ
		String deptInfoStr = DepartmentManage.getDeptInfo(request, "1");
		String[] deptInfo = deptInfoStr.split(",");
		String jgmc = "";
		if(deptInfo.length > 1){
			jgmc = deptInfo[1];
		}
        //��ȡ��¼�û���Ӧ��Ա
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
		logger.info("��ȡ��¼��Ϣ��\n"+xmlData.toString());
		request.getSession().setAttribute("sCode", sCode.trim());
		out.write(xmlData.toString());	
	    return null;
	}
	
	/**
	 * @----------------------------------------------------------------------------------------------------------------------------------
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵���������û����͵�¼ip�޸ĵ�ǰ�û���¼��Ϣ
	 * @������sysId-�û���¼ip
	 * @���أ�
	 * @�������ڣ�2010-9-14
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
			logger.error("�޸ĵ�ǰ�û���¼״̬����:"+ex.getMessage()+" sql=="+sql);
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
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵�����жϵ�ǰ��¼�û��Ƿ��ָ��ϵͳ����Ȩ��
	 * @������isOpen-�Ƿ�ֻ��֤�Ƿ��¼ "yes"-��
	 * @���أ�"noLogin"-��δ��¼ "true"-��Ȩ�� "false"-û��Ȩ��
	 * @�������ڣ�2010-09-15
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
		if(isOpen.equals("yes")){//ֻ��֤�Ƿ��¼
			returnStr = "true";
		}else if(sysId.equals("")){
			returnStr = "true";
		}else{
        //��ȡȨ���ڵ�ϵͳ���
			UInterface ui = UInterfaceFactory.getInstance();
			Object[][] sysCode = ui.getPermitSysCode(loginUserName.toString());
			for(int i = 0; i < sysCode.length; i ++){
				if(StringHelper.obj2str(sysCode[i][0],"").equals(sysId)){
					returnStr = "true";
					break;
				}
			}
			/*��¼�û���¼��ϵͳ*/
			if(returnStr.equals("true")){
				StateListener.updateUserState(StringHelper.obj2str(loginUserName), sysId, sysName, loginIp);
			}
		}
		out.write(returnStr);	
	    return null;
	}
	
}
