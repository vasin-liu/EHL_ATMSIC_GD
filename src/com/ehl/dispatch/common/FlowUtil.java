package com.ehl.dispatch.common;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

/**
 * <pre>
 * 流程工具类
 * 包含三个流程之间需要常用的一些方法
 * dao层的数据访问
 * core层的数据封装
 * action层的参数获取
 * 以及一些数据类型转换的方法和其他方法
 * </pre>
 * 
 * @author xiayx
 * @since 2011-08-03
 */
public class FlowUtil {

    private static Logger logger = Logger.getLogger(FlowUtil.class);

    /**
     * 根据序列名称获取下一个序列值
     * 
     * @param seqName
     *            序列名称
     * @return 下一个序列值
     */
    public static String getNextId(String seqName) {
	String sql = "select " + seqName + ".nextval from dual";
	String result = null;
	try {
	    result = String.valueOf(DBHandler.getSingleResult(sql));
	} catch (Exception e) {
	    logger.error("获取序列值异常", e);
	}
	return result;
    }

    /**
     * 获取单个值
     * 
     * @param sql
     *            sql语句
     * @param logger
     *            日志对象
     * @param msg
     *            用户定义异常信息
     * @return 单个值
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
     * 获取单个值
     * 
     * @param sql
     *            sql语句
     * @return 单个值
     */
    public static Object readSingle(String sql) {
	return readSingle(sql, null, null);
    }

    /**
     * 获取一维数组形式单个对象信息
     * 
     * @param sql
     *            sql语句
     * @param logger
     *            日志对象
     * @param msg
     *            用户定义异常信息
     * @return 一维数组形式单个对象信息
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
     * 获取一维数组形式单个对象信息
     * 
     * @param sql
     *            sql语句
     * @return 一维数组形式单个对象信息
     */
    public static Object[] readLine(String sql) {
	return readLine(sql, null, null);
    }

    /**
     * 获取二维数组形式多个对象信息
     * 
     * @param sql
     *            sql语句
     * @param logger
     *            日志对象
     * @param msg
     *            用户定义异常信息
     * @return 二维数组形式多个对象信息
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
     * 获取二维数组形式多个对象信息
     * 
     * @param sql
     *            sql语句
     * @return 二维数组形式多个对象信息
     */
    public static Object[][] readMilte(String sql) {
	return readMilte(sql, null, null);
    }

    /**
     * 向数据库写入数据
     * 
     * @param sql
     *            sql语句
     * @param logger
     *            日志对象
     * @param msg
     *            用户定义异常信息
     * @return 操作是否成功
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
		 logger.error(msg + "失败！", e);
	    }
	}
	return isOK;
    }

    /**
     * 向数据库写入数据
     * 
     * @param sql
     *            sql语句
     * @return 操作是否成功
     */
    public static boolean write(String sql) {
	return write(sql, null, null);
    }

    /**
     * 从request中获取指定参数对应的值，并存放在map中
     * 
     * @param request
     *            request对象
     * @param paramsIn
     *            指定参数列表
     * @param iscnull
     *            是否清除值为null的参数
     * @return 包含参数和值的map
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
     * 加上单引号
     * 
     * @param value
     *            值
     * @return 加上单引号后的值
     */
    public static String encapSQ(String value) {
	return "'" + (value == null ? "" : value) + "'";
    }

    /**
     * 取消单引号
     * 
     * @param value
     *            值
     * @return 删除单引号后的值
     */
    public static String cancleSQ(String value) {
	if (value != null && value.length() >= 2 && value.startsWith("'") && value.endsWith("'")) {
	    value = value.substring(1, value.length() - 1);
	}
	return value;
    }

    /**
     * 在原字符串右侧追加指定次数的指定字符串
     * 
     * @param str
     *            原字符串
     * @param pstr
     *            指定字符串
     * @param time
     *            指定次数
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
     * 将map中值追加单引号
     * 
     * @param map
     *            追加单引号后的值
     */
    public static void encapMapSQ(Map<String, String> map) {
	if (map != null) {
	    for (Map.Entry<String, String> entry : map.entrySet()) {
		map.put(entry.getKey(), encapSQ(entry.getValue()));
	    }
	}
    }

    /**
     * 将map中指定key的值和新key放入新的map中
     * 
     * @param keys
     *            key数组
     * @param newKeys
     *            新key数组
     * @param map
     *            原map
     * @return 新map
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
     * 拷贝map
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
     * 根据功能节点Id获取功能描述
     * 
     * @param id
     *            功能节点Id
     * @return 功能节点描述
     */
    public static String getFuncText(String id) {
	String sql = "select text from t_sys_func where id='" + id + "'";
	Object text = readSingle(sql, logger, "获取节点描述");
	return (text == null ? "" : text.toString());
    }

}
