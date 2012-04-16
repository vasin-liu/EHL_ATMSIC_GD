package com.ehl.base;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

/**
 * �������ݷ�����
 * 
 * @author Xiayx
 * 
 */
public abstract class BaseDao {

    /** ��־�� */
    private Logger logger;

    /** ʵ������� */
    private String ename;

    /** ���� */
    private String tname;

    /** ����� */
    private String otname;

    /** ���� */
    private String[] cnames;
    
    /** �������� */
    private String pk = "id";
    
    /** ������������ */
    private String[] dcnames;

    /** ������ */
    private String sname;

    public BaseDao() {

    }

    /**
     * �����Ϣ
     * 
     * @param object
     *            ������Ϣ
     * @return �������
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
	boolean isSuccess = FlowUtil.write(sql, logger, "���" + ename + "��Ϣ");
	id = isSuccess ? id : null;
	return id;
    }

    /**
     * ��ȡ�������
     * 
     * @return �������
     */
    public String getId() {
	return CreateSequence.getMaxForSeq(sname, 20);
    }

    /**
     * ת����������
     * 
     * @param object
     *            ������Ϣ
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
     * ת��ʱ�� ���ַ���ʱ��ת������������ʱ��
     * 
     * @param time
     *            �ַ���ʱ��
     * @return ��������ʱ��
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
     * ��ȡ������Ϣ��ͨ���������
     * 
     * @param id
     *            �������
     * @return ������Ϣ
     */
    public Object[] getById(String id) {
	if (id == null) {
	    return null;
	}
	String _tname = tname + " " + otname;
	String sql = Sql.select(_tname, getSelect(), pk+"='" + id + "'");
	return FlowUtil.readLine(sql, logger, "��ȡ" + ename + "ͨ���������");
    }

    /**
     * ��ȡ�����ֶ���
     * 
     * @return �����ֶ���
     */
    public abstract String[] getFields();

    /**
     * ��ȡ��ѯ�ַ��� ƥ������ֶ���
     * 
     * @return ��ѯ�ַ���
     */
    public abstract String getSelect();

    /**
     * �޸���Ϣ��ͨ���������
     * 
     * @param object
     *            ������Ϣ
     * @return �Ƿ��޸ĳɹ�
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
	return FlowUtil.write(sql, logger, "�޸�" + ename + "��Ϣ");
    }

    /**
     * ɾ����Ϣ��ͨ���������
     * 
     * @param id
     *            �������
     * @return �Ƿ�ɾ���ɹ�
     */
    public boolean deleteById(String id) {
	if (id == null) {
	    return true;
	}
	String sql = Sql.delete(tname, pk+"='" + id + "'");
	return FlowUtil.write(sql, logger, "ɾ��" + ename + "��Ϣ");
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
