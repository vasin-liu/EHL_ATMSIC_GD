package com.ehl.sm.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.appframe.common.Setting;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.sm.common.Constants;

public class Constant extends Constants {

    /** 用户名变量名 */
    public final static String UNAME_VAR = "uname";
    /** 用户密码变量名 */
    public final static String PWD_VAR = "pwd";
    /** 机构编号变量名 */
    public final static String JGID_VAR = "jgid";
    /** 机构名称变量名 */
    public final static String JGMC_VAR = "jgmc";
    /** 机构层次编码变量名 */
    public final static String JGCC_VAR = "jgcc";
    /** 机构类型变量名 */
    public static final String JGLX_VAR = "jglx";
    /** 是否高速大队常量 */
    public static final String ISGSDD_VAR = "isgsdd";
    /** 值班人姓名变量名 */
    public final static String ZBRXM_VAR = "zbrxm";
    /** 值班领导姓名变量名 */
    public final static String ZBLDXM_VAR = "zbldxm";
    /** 应用场景编号变量名 */
    public final static String APPID_VAR = "appid";
    /** 打印文件编号变量名 */
    public final static String PFBH_VAR = "pfbh";
    /** 登录页面名称变量名 */
    public final static String LPNAME_VAR = "redirecturl";
    /** 机构等级标志 */
    public final static String JGDJBZ = "00";
    /** 机构等级标志长度 */
    public final static int JGDJBZCD = JGDJBZ.length();
    /** 标准机构编号长度 */
    public final static int JGIDCD = 12;
    /** 机构名称中“公安局交通警察”字符串 */
    public final static String GAJJTJC = "公安局交通警察";
    /** 机构名称中“公安局交通警察”字符串缩写“交警” */
    public final static String JJ = "交警";
    /** 服务器链接地址 */
    public final static String SERVER_URL_VAR = "serverurl";
    /** 应用程序名称 */
    public final static String APP_URL_VAR = "contextPath";
    /** 操作变量名，用户在对象上能够进行的操作 */
    public final static String OPERATE_VAR = "operate";
    /** 管理员用户名常量 */
    public final static String ADMIN = "admin";
    /** 系统上线日期变量名 */
    public final static String PUBLISH_TIME_VAR = "publishtime";

    /**
     * 获取登录页面路径
     * 
     * @param request
     * @param setting
     * @return
     */
    public static String getLoginPage(HttpServletRequest request,
	    String loginPageName) {
	String loginPagePath = null;
	if (request != null && loginPageName != null) {
	    String url = String.valueOf(request.getRequestURL());
	    String uri = request.getRequestURI();
	    int lpnIndex = loginPageName.lastIndexOf("/");
	    if (lpnIndex != -1) {
		loginPageName = loginPageName.substring(lpnIndex);
	    } else if (lpnIndex != 0) {
		loginPageName = "/" + loginPageName;
	    }
	    loginPagePath = url.substring(0, url.length() - uri.length());
	    loginPagePath += "/" + request.getContextPath();
	    loginPagePath += loginPageName;
	}
	return loginPagePath;
    }

    /**
     * 获取登录页面路径
     * 
     * @param request
     * @return
     */
    public static String getLoginPage(HttpServletRequest request) {
	return getLoginPage(request, Setting.getString(LPNAME_VAR));
    }

    public static String getBasePath(HttpServletRequest request) {
	return request.getScheme() + "://" + request.getServerName() + ":"
		+ request.getServerPort();
    }

    /**
     * <pre>
     * 获取机构等级
     * 1.总队
     * 2.支队
     * 3.大队
     * </pre>
     * 
     * @param jgid
     *            机构代码
     * @return 机构等级
     */
    public static int getLevel(String jgid) {
	int level = -1;
	if (jgid != null) {
	    jgid += JGDJBZ;
	    int jlength = jgid.length() / JGDJBZCD;
	    for (int i = 0; i < jlength; i++) {
		if (jgid.substring(i * JGDJBZCD, (i + 1) * JGDJBZCD).equals(
			JGDJBZ)) {
		    level = i;
		    break;
		}
	    }
	}
	return level;
    }

    /**
     * 获取父机构代码
     * 
     * @param jgid
     * @return
     */
    public static String getParent(String jgid) {
	String parent = null;
	int level = getLevel(jgid);
	if (level > 1) {
	    parent = jgid.substring(0, (level - 1) * JGDJBZCD);
	    parent += JGDJBZ;
	    parent += jgid.substring(level * JGDJBZCD);
	}
	return parent;
    }

    /**
     * 获取所有父机构
     * 
     * @param jgid
     *            机构编号
     * @return 所有父机构，之间以,分隔
     */
    public static String getParents(String jgid) {
	String parents = null;
	jgid = getParent(jgid);
	if (jgid != null) {
	    parents = jgid;
	    while ((jgid = getParent(jgid)) != null) {
		parents += "," + jgid;
	    }
	}
	return parents;
    }

    /**
     * 获取根节点父元素
     * 
     * @param jgid
     *            机构编号
     * @return 父元素编号
     */
    public static String getRootParent(String jgid) {
	String parent = getParent(jgid);
	if (parent == null) {
	    return jgid;
	} else {
	    jgid = parent;
	    while ((parent = getParent(jgid)) != null) {
		jgid = parent;
	    }
	    return jgid;
	}
    }

    /**
     * 获取机构的默认筛选， 排除科、室、中队
     * 
     * @param cname
     *            列名
     * @return 机构的默认筛选
     */
    public static String defaultSift(String cname) {
	String deptShift = null;
	if (cname != null) {
	    deptShift = cname + "< '446000000000'";
	}
	return deptShift;
    }

    /**
     * 获取机构的默认筛选， 排除科、室、中队
     * 
     * @return 机构的默认筛选
     */
    public static String defaultSift() {
	return defaultSift("jgid");
    }

    /**
     * 获取筛选出本单位和其子单位的SQL语句
     * 
     * @param cname
     *            列名
     * @param jgid
     *            机构编号
     * @return 筛选本单位和其子单位的SQL语句
     */
    public static String getSiftSelfChildsSql(String cname, String jgid) {
	String sql = null;
	int level = getLevel(jgid);
	if (cname != null && level >= 1) {
	    sql = cname + " like '" + jgid.substring(0, level * JGDJBZCD)
		    + "%'";
	}
	return sql;
    }

    /**
     * 获取筛选出本单位和其子一级单位的SQL语句
     * 
     * @param cname
     *            列名
     * @param jgid
     *            机构编号
     * @return 筛选本单位和其子一级单位的SQL语句
     */
    public static String getSiftSelfChildSql(String cname, String jgid) {
	String sql = getSiftSelfChildsSql(cname, jgid);
	if (sql != null) {
	    int level = getLevel(jgid);
	    sql += " and substr(" + cname + "," + ((level + 1) * JGDJBZCD + 1)
		    + ")='" + jgid.substring((level + 1) * JGDJBZCD) + "'";
	}
	return sql;
    }

    /**
     * 获取子机构数
     * 
     * @param jgid
     *            机构编号
     * @return 子机构数
     */
    public static int getChildCount(String jgid) {
	int count = -1;
	String shiftChild = getSiftSelfChildSql("jgid", jgid);
	if (shiftChild != null) {
	    String sql = "select count(jgid) from t_sys_department where "
		    + shiftChild + " and " + defaultSift();
	    Object countObj = FlowUtil.readSingle(sql);
	    if (countObj != null) {
		count = Integer.parseInt(countObj.toString());
		count--;
	    }
	}
	return count;
    }

    /**
     * 为null默认为空串
     * 
     * @param obj
     * @return
     */
    public static String nvl(Object obj) {
	return (obj == null ? "" : obj.toString());
    }

    /**
     * 获取当前时间
     * 
     * @param isChinese
     *            是否使用中文格式方式
     * @return 当前时间
     */
    public static String getCurrentTime(boolean isChinese) {
	String format = "yyyy-MM-dd HH:mm:ss";
	if (isChinese) {
	    format = "yyyy年MM月dd日HH时mm分ss秒";
	}
	return new SimpleDateFormat(format).format(new Date());
    }

    public static void main(String[] args) {
	System.out.println(getRootParent("44010101"));
    }
}
