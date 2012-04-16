package com.ehl.sm.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.appframe.common.Setting;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.sm.common.Constants;

public class Constant extends Constants {

    /** �û��������� */
    public final static String UNAME_VAR = "uname";
    /** �û���������� */
    public final static String PWD_VAR = "pwd";
    /** ������ű����� */
    public final static String JGID_VAR = "jgid";
    /** �������Ʊ����� */
    public final static String JGMC_VAR = "jgmc";
    /** ������α�������� */
    public final static String JGCC_VAR = "jgcc";
    /** �������ͱ����� */
    public static final String JGLX_VAR = "jglx";
    /** �Ƿ���ٴ�ӳ��� */
    public static final String ISGSDD_VAR = "isgsdd";
    /** ֵ�������������� */
    public final static String ZBRXM_VAR = "zbrxm";
    /** ֵ���쵼���������� */
    public final static String ZBLDXM_VAR = "zbldxm";
    /** Ӧ�ó�����ű����� */
    public final static String APPID_VAR = "appid";
    /** ��ӡ�ļ���ű����� */
    public final static String PFBH_VAR = "pfbh";
    /** ��¼ҳ�����Ʊ����� */
    public final static String LPNAME_VAR = "redirecturl";
    /** �����ȼ���־ */
    public final static String JGDJBZ = "00";
    /** �����ȼ���־���� */
    public final static int JGDJBZCD = JGDJBZ.length();
    /** ��׼������ų��� */
    public final static int JGIDCD = 12;
    /** ���������С������ֽ�ͨ���족�ַ��� */
    public final static String GAJJTJC = "�����ֽ�ͨ����";
    /** ���������С������ֽ�ͨ���족�ַ�����д�������� */
    public final static String JJ = "����";
    /** ���������ӵ�ַ */
    public final static String SERVER_URL_VAR = "serverurl";
    /** Ӧ�ó������� */
    public final static String APP_URL_VAR = "contextPath";
    /** �������������û��ڶ������ܹ����еĲ��� */
    public final static String OPERATE_VAR = "operate";
    /** ����Ա�û������� */
    public final static String ADMIN = "admin";
    /** ϵͳ�������ڱ����� */
    public final static String PUBLISH_TIME_VAR = "publishtime";

    /**
     * ��ȡ��¼ҳ��·��
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
     * ��ȡ��¼ҳ��·��
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
     * ��ȡ�����ȼ�
     * 1.�ܶ�
     * 2.֧��
     * 3.���
     * </pre>
     * 
     * @param jgid
     *            ��������
     * @return �����ȼ�
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
     * ��ȡ����������
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
     * ��ȡ���и�����
     * 
     * @param jgid
     *            �������
     * @return ���и�������֮����,�ָ�
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
     * ��ȡ���ڵ㸸Ԫ��
     * 
     * @param jgid
     *            �������
     * @return ��Ԫ�ر��
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
     * ��ȡ������Ĭ��ɸѡ�� �ų��ơ��ҡ��ж�
     * 
     * @param cname
     *            ����
     * @return ������Ĭ��ɸѡ
     */
    public static String defaultSift(String cname) {
	String deptShift = null;
	if (cname != null) {
	    deptShift = cname + "< '446000000000'";
	}
	return deptShift;
    }

    /**
     * ��ȡ������Ĭ��ɸѡ�� �ų��ơ��ҡ��ж�
     * 
     * @return ������Ĭ��ɸѡ
     */
    public static String defaultSift() {
	return defaultSift("jgid");
    }

    /**
     * ��ȡɸѡ������λ�����ӵ�λ��SQL���
     * 
     * @param cname
     *            ����
     * @param jgid
     *            �������
     * @return ɸѡ����λ�����ӵ�λ��SQL���
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
     * ��ȡɸѡ������λ������һ����λ��SQL���
     * 
     * @param cname
     *            ����
     * @param jgid
     *            �������
     * @return ɸѡ����λ������һ����λ��SQL���
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
     * ��ȡ�ӻ�����
     * 
     * @param jgid
     *            �������
     * @return �ӻ�����
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
     * ΪnullĬ��Ϊ�մ�
     * 
     * @param obj
     * @return
     */
    public static String nvl(Object obj) {
	return (obj == null ? "" : obj.toString());
    }

    /**
     * ��ȡ��ǰʱ��
     * 
     * @param isChinese
     *            �Ƿ�ʹ�����ĸ�ʽ��ʽ
     * @return ��ǰʱ��
     */
    public static String getCurrentTime(boolean isChinese) {
	String format = "yyyy-MM-dd HH:mm:ss";
	if (isChinese) {
	    format = "yyyy��MM��dd��HHʱmm��ss��";
	}
	return new SimpleDateFormat(format).format(new Date());
    }

    public static void main(String[] args) {
	System.out.println(getRootParent("44010101"));
    }
}
