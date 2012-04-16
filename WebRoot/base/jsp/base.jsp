<%-- <%@page pageEncoding="UTF-8"%> --%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.ehl.sm.core.FuncCore"%>
<%@page import="com.ehl.util.Array"%>
<%@page import="net.sf.json.JSONObject"%>
<%@page import="com.ehl.util.Collections"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.ehl.sm.base.Constant"%>
<%
//清除页面缓存
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", -10);

Object $pname = session.getAttribute(Constant.PNAME_KEY);
if($pname == null){
	String toLoginPage = Constant.getLoginPage(request);
	toLoginPage = "<script>top.location.href='"+toLoginPage+"';</script>";
	out.write(toLoginPage);
}else{
	Logger logger = Logger.getLogger(this.getClass());
	try{
	    String uri = request.getRequestURI();
	    String pageName = uri.substring(0, uri.lastIndexOf(".jsp"));
	    String QUERY = "Query";
	    //页面类型。0：新增；1：查询；2：查看；3：处理
	    String pageType = request.getParameter("pageType");
	    if (pageType == null) {
			pageType = pageName.endsWith(QUERY) ? "1" : "0";
	    }
	    //设置页面
	    pageName = pageName.substring(pageName.lastIndexOf("/")+1);
	    String info = pageName, query = pageName + QUERY;
	    if (pageName.endsWith(QUERY)) {
			info = pageName.substring(0,pageName.length()-QUERY.length());
			query = info;
	    }
	    //设置标题，标题根据左树做多2级节点，设置为父标题和当前标题
	    String[] titles = request.getParameterValues("title");
	    String funcId = request.getParameter("funcId");
	    if(titles == null && funcId != null && funcId.length() >= 4){
	    	FuncCore funcCore = new FuncCore();
	    	titles = FuncCore.toString(funcCore.getTitles(funcId)).split(",");
	    }
	    String title = Array.join(titles, " > ");
	    String titleSelf = "";
	    if(titles != null && titles.length >= 1){
	    	titleSelf = titles[titles.length-1];
	    }
	    pageContext.setAttribute("titleSelf",titleSelf);
	    pageContext.setAttribute("title",title);
	    
	    String time = Constant.getCurrentTime(false);
	    pageContext.setAttribute("time",time);
	    //输出全局变量
	    Map jsvars = new HashMap();
	    jsvars.put("contextPath", request.getContextPath() + "/");
	    jsvars.put("time", time);
	    jsvars.put("publishTime", (String) session.getAttribute(Constant.PUBLISH_TIME_VAR));
	    jsvars.put("pageType", pageType);
	    jsvars.put("jgid", (String) session.getAttribute(Constant.JGID_VAR));
	    jsvars.put("jgmc", (String) session.getAttribute(Constant.JGMC_VAR));
	    jsvars.put("jgcc", (String) session.getAttribute(Constant.JGCC_VAR));
	    jsvars.put("jglx", (String) session.getAttribute(Constant.JGLX_VAR));
	    jsvars.put("appid",(String) session.getAttribute(Constant.APPID_VAR));
	    jsvars.put("operate", (String) request.getAttribute(Constant.OPERATE_VAR));
	    jsvars.put("pname", (String) session.getAttribute(Constant.ZBRXM_VAR));
	    jsvars.put("infoPage", info);
	    jsvars.put("queryPage", query);
	    jsvars.put("titleSelf", titleSelf);
	    jsvars.put("titles", titles);
	    String script = JSONObject.fromObject(jsvars).toString();
	    script = "window.baseInfo=" + script+";";
	    ////输出请求参数
	    Map map = request.getParameterMap();
	    map = Collections.changeSingleArray(map);
	    script += "window.baseInfo.params="+JSONObject.fromObject(map);
	    script = "<script>"+script+"</script>";
	    out.println(script);
	    logger.info("script:"+script);
	}catch(Exception e){
		logger.error("base.jsp Page Error！", e);
	}
}
%>