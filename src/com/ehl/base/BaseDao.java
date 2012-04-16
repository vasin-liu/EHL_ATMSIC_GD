package com.ehl.base;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

/**
 * 基本数据访问类
 * 
 * @author Xiayx
 * 
 */
public abstract class BaseDao {

    /** 日志类 */
    private Logger logger;

    /** 实体对象名 */
    private String ename;

    /** 表名 */
    private String tname;

    /** 表别名 */
    private String otname;

    /** 列名 */
    private String[] cnames;
    
    /** 主键列名 */
    private String pk = "id";
    
    /** 日期类型列名 */
    private String[] dcnames;

    /** 序列名 */
    private String sname;

    public BaseDao() {

    }

    /**
     * 添加信息
     * 
     * @param object
     *            对象信息
     * @return 主键编号
     */
    public String insert(Map<String, String> object) {
	if (object == null) {
	    return null;
	}
	String id = object.get(pk);
	if (id == null || id.equals("")) {
	    id = getId();
	    object.put(pk, id);
	}
	FlowUtil.encapMapSQ(object);
	changeDataType(object);
	String sql = Sql.insert(tname, object);
	boolean isSuccess = FlowUtil.write(sql, logger, "添加" + ename + "信息");
	id = isSuccess ? id : null;
	return id;
    }

    /**
     * 获取主键编号
     * 
     * @return 主键编号
     */
    public String getId() {
	return CreateSequence.getMaxForSeq(sname, 20);
    }

    /**
     * 转换数据类型
     * 
     * @param object
     *            对象信息
     */
    public void changeDataType(Map<String, String> object) {
	String time, dcname;
	for (int i = 0; i < dcnames.length; i++) {
	    dcname = dcnames[i];
	    if (object.containsKey(dcname)) {
		time = object.get(dcname);
		object.put(dcname, changeTime(time));
	    }
	}
    }

    /**
     * 转换时间 将字符串时间转换成日期类型时间
     * 
     * @param time
     *            字符串时间
     * @return 日期类型时间
     */
    public String changeTime(String time) {
	if (time == null || time.equals("''")) {
	    time = "sysdate";
	} else {
	    time = FlowUtil.cancleSQ(time);
	    time = Sql.toDate(time);
	}
	return time;
    }

    /**
     * 获取对象信息，通过主键编号
     * 
     * @param id
     *            主键编号
     * @return 对象信息
     */
    public Object[] getById(String id) {
	if (id == null) {
	    return null;
	}
	String _tname = tname + " " + otname;
	String sql = Sql.select(_tname, getSelect(), pk+"='" + id + "'");
	return FlowUtil.readLine(sql, logger, "获取" + ename + "通过主键编号");
    }

    /**
     * 获取对象字段名
     * 
     * @return 对象字段名
     */
    public abstract String[] getFields();

    /**
     * 获取查询字符串 匹配对象字段名
     * 
     * @return 查询字符串
     */
    public abstract String getSelect();

    /**
     * 修改信息，通过主键编号
     * 
     * @param object
     *            对象信息
     * @return 是否修改成功
     */
    public boolean modifyById(Map<String, String> object) {
	if (object == null || !object.containsKey(pk)) {
	    return true;
	}
	FlowUtil.encapMapSQ(object);
	String id = object.get(pk);
	object.remove(pk);
	changeDataType(object);
	String sql = Sql.update(tname + " " + otname, object, pk+"=" + id );
	return FlowUtil.write(sql, logger, "修改" + ename + "信息");
    }

    /**
     * 删除信息，通过主键编号
     * 
     * @param id
     *            主键编号
     * @return 是否删除成功
     */
    public boolean deleteById(String id) {
	if (id == null) {
	    return true;
	}
	String sql = Sql.delete(tname, pk+"='" + id + "'");
	return FlowUtil.write(sql, logger, "删除" + ename + "信息");
    }

    public Logger getLogger() {
	return logger;
    }

    public void setLogger(Logger logger) {
	this.logger = logger;
    }

    public String getEname() {
	return ename;
    }

    public void setEname(String ename) {
	this.ename = ename;
    }

    public String getTname() {
	return tname;
    }

    public void setTname(String tname) {
	this.tname = tname;
    }

    public String getOtname() {
	return otname;
    }

    public void setOtname(String otname) {
	this.otname = otname;
    }

    public String[] getCnames() {
	return cnames;
    }

    public void setCnames(String[] cnames) {
	this.cnames = cnames;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String[] getDcnames() {
	return dcnames;
    }

    public void setDcnames(String[] dcnames) {
	this.dcnames = dcnames;
    }

    public String getSname() {
	return sname;
    }

    public void setSname(String sname) {
	this.sname = sname;
    }

}
