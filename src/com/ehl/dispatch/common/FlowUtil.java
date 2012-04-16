package com.ehl.dispatch.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

/**
 * <pre>
 * ���̹�����
 * ������������֮����Ҫ���õ�һЩ����
 * dao������ݷ���
 * core������ݷ�װ
 * action��Ĳ�����ȡ
 * �Լ�һЩ��������ת���ķ�������������
 * </pre>
 * 
 * @author xiayx
 * @since 2011-08-03
 */
public class FlowUtil {

    private static Logger logger = Logger.getLogger(FlowUtil.class);

    /**
     * �����������ƻ�ȡ��һ������ֵ
     * 
     * @param seqName
     *            ��������
     * @return ��һ������ֵ
     */
    public static String getNextId(String seqName) {
	String sql = "select " + seqName + ".nextval from dual";
	String result = null;
	try {
	    result = String.valueOf(DBHandler.getSingleResult(sql));
	} catch (Exception e) {
	    logger.error("��ȡ����ֵ�쳣", e);
	}
	return result;
    }

    /**
     * ��ȡ����ֵ
     * 
     * @param sql
     *            sql���
     * @param logger
     *            ��־����
     * @param msg
     *            �û������쳣��Ϣ
     * @return ����ֵ
     */
    public static Object readSingle(String sql, Logger logger, String msg) {
	Object data = null;
	if (sql != null) {
	    if (logger == null) {
		logger = FlowUtil.logger;
	    }
	    try {
		logger.info("sql:\n" + sql);
		data = DBHandler.getSingleResult(sql);
	    } catch (Exception e) {
		logger.error(msg, e);
	    }
	}
	return data;
    }

    /**
     * ��ȡ����ֵ
     * 
     * @param sql
     *            sql���
     * @return ����ֵ
     */
    public static Object readSingle(String sql) {
	return readSingle(sql, null, null);
    }

    /**
     * ��ȡһά������ʽ����������Ϣ
     * 
     * @param sql
     *            sql���
     * @param logger
     *            ��־����
     * @param msg
     *            �û������쳣��Ϣ
     * @return һά������ʽ����������Ϣ
     */
    public static Object[] readLine(String sql, Logger logger, String msg) {
	Object[] data = null;
	if (sql != null) {
	    if (logger == null) {
		logger = FlowUtil.logger;
	    }
	    try {
		logger.info("sql:\n" + sql);
		data = DBHandler.getLineResult(sql);
	    } catch (Exception e) {
		logger.error(msg, e);
	    }
	}
	return data;
    }

    /**
     * ��ȡһά������ʽ����������Ϣ
     * 
     * @param sql
     *            sql���
     * @return һά������ʽ����������Ϣ
     */
    public static Object[] readLine(String sql) {
	return readLine(sql, null, null);
    }

    /**
     * ��ȡ��ά������ʽ���������Ϣ
     * 
     * @param sql
     *            sql���
     * @param logger
     *            ��־����
     * @param msg
     *            �û������쳣��Ϣ
     * @return ��ά������ʽ���������Ϣ
     */
    public static Object[][] readMilte(String sql, Logger logger, String msg) {
	Object[][] data = null;
	if (sql != null) {
	    if (logger == null) {
		logger = FlowUtil.logger;
	    }
	    try {
		logger.info("sql:\n" + sql);
		data = DBHandler.getMultiResult(sql);
	    } catch (Exception e) {
		logger.error(msg, e);
	    }
	}
	return data;
    }

    /**
     * ��ȡ��ά������ʽ���������Ϣ
     * 
     * @param sql
     *            sql���
     * @return ��ά������ʽ���������Ϣ
     */
    public static Object[][] readMilte(String sql) {
	return readMilte(sql, null, null);
    }

    /**
     * �����ݿ�д������
     * 
     * @param sql
     *            sql���
     * @param logger
     *            ��־����
     * @param msg
     *            �û������쳣��Ϣ
     * @return �����Ƿ�ɹ�
     */
    public static boolean write(String sql, Logger logger, String msg) {
	boolean isOK = false;
	if (sql != null) {
	    if (logger == null) {
		logger = FlowUtil.logger;
	    }
	    try {
		logger.info("sql:\n" + sql);
		isOK = DBHandler.execute(sql);
	    } catch (Exception e) {
		 logger.error(msg + "ʧ�ܣ�", e);
	    }
	}
	return isOK;
    }

    /**
     * �����ݿ�д������
     * 
     * @param sql
     *            sql���
     * @return �����Ƿ�ɹ�
     */
    public static boolean write(String sql) {
	return write(sql, null, null);
    }

    /**
     * ��request�л�ȡָ��������Ӧ��ֵ���������map��
     * 
     * @param request
     *            request����
     * @param paramsIn
     *            ָ�������б�
     * @param iscnull
     *            �Ƿ����ֵΪnull�Ĳ���
     * @return ����������ֵ��map
     */
    public static Map<String, String> getParams(HttpServletRequest request, String[] paramsIn,
	    boolean iscnull) {
	Map<String, String> pimap = null;
	if (request != null && paramsIn != null) {
	    pimap = new HashMap<String, String>();
	    String value;
	    for (int i = 0; i < paramsIn.length; i++) {
		value = request.getParameter(paramsIn[i]);
		if (iscnull) {
		    if (value != null) {
			pimap.put(paramsIn[i], value);
		    }
		} else {
		    pimap.put(paramsIn[i], value);
		}
	    }
	    if (pimap.size() == 0) {
		pimap = null;
	    }
	}
	return pimap;
    }

    /**
     * ���ϵ�����
     * 
     * @param value
     *            ֵ
     * @return ���ϵ����ź��ֵ
     */
    public static String encapSQ(String value) {
	return "'" + (value == null ? "" : value) + "'";
    }

    /**
     * ȡ��������
     * 
     * @param value
     *            ֵ
     * @return ɾ�������ź��ֵ
     */
    public static String cancleSQ(String value) {
	if (value != null && value.length() >= 2 && value.startsWith("'") && value.endsWith("'")) {
	    value = value.substring(1, value.length() - 1);
	}
	return value;
    }

    /**
     * ��ԭ�ַ����Ҳ�׷��ָ��������ָ���ַ���
     * 
     * @param str
     *            ԭ�ַ���
     * @param pstr
     *            ָ���ַ���
     * @param time
     *            ָ������
     * @return
     */
    public static String rpadding(String str, String pstr, int time) {
	if (str != null && pstr != null && time >= 0) {
	    for (int i = 0; i < time; i++) {
		str += pstr;
	    }
	}
	return str;
    }

    /**
     * ��map��ֵ׷�ӵ�����
     * 
     * @param map
     *            ׷�ӵ����ź��ֵ
     */
    public static void encapMapSQ(Map<String, String> map) {
	if (map != null) {
	    for (Map.Entry<String, String> entry : map.entrySet()) {
		map.put(entry.getKey(), encapSQ(entry.getValue()));
	    }
	}
    }

    /**
     * ��map��ָ��key��ֵ����key�����µ�map��
     * 
     * @param keys
     *            key����
     * @param newKeys
     *            ��key����
     * @param map
     *            ԭmap
     * @return ��map
     */
    public static Map<String, String> copyMap(String[] keys, String[] newKeys,
	    Map<String, String> map) {
	Map<String, String> cmap = null;
	if (keys != null && newKeys != null && keys.length == newKeys.length) {
	    cmap = new HashMap<String, String>();
	    for (int i = 0; i < keys.length; i++) {
		cmap.put(newKeys[i], map.get(keys[i]));
	    }
	}
	return cmap;
    }

    /**
     * ����map
     * 
     * @param map
     *            map
     * @return map
     */
    public static Map<String, String> copyMap(Map<String, String> map) {
	Map<String, String> cmap = null;
	if (map != null) {
	    cmap = new HashMap<String, String>();
	    for (Map.Entry<String, String> entry : map.entrySet()) {
		cmap.put(entry.getKey(), entry.getValue());
	    }
	}
	return cmap;
    }

    /**
     * ���ݹ��ܽڵ�Id��ȡ��������
     * 
     * @param id
     *            ���ܽڵ�Id
     * @return ���ܽڵ�����
     */
    public static String getFuncText(String id) {
	String sql = "select text from t_sys_func where id='" + id + "'";
	Object text = readSingle(sql, logger, "��ȡ�ڵ�����");
	return (text == null ? "" : text.toString());
    }

}
