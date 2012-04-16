package com.ehl.base;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.tira.util.XML;

/**
 * ����ҵ���߼���
 * 
 * @author Xiayx
 * 
 */
public abstract class BaseCore {

    /** ��־�� */
    private Logger logger;

    /** �������ݷ����� */
    private BaseDao dao;

    /** �������� */
    private String oname;

    /**
     * �����Ϣ
     * 
     * @param object
     *            ������Ϣ
     * @return �������
     */
    public String insert(Map<String, String> object) {
	return dao.insert(object);
    }

    /**
     * ��ȡ��Ϣ��ͨ���������
     * 
     * @param id
     *            �������
     * @return ������Ϣ
     */
    public String getById(String id) {
	return getById(id, null);
    }

    /**
     * ��ȡ��Ϣ��ͨ���������
     * 
     * @param id
     *            �������
     * @param referObject
     *            ��������
     * @return ������Ϣ
     */
    public String getById(String id, String referObject) {
	String xml = null;
	Object[] object = dao.getById(id);
	if (object != null) {
	    xml = XML.getNode(getOname(), dao.getFields(), object,
		    referObject);
	}
	xml = XML.getXML(xml);
	return xml;
    }

    /**
     * �޸���Ϣ��ͨ���������
     * 
     * @param object
     *            ������Ϣ
     * @return �Ƿ��޸ĳɹ�
     */
    public boolean modifyById(Map<String, String> object) {
	return dao.modifyById(object);
    }

    /**
     * ɾ����Ϣ��ͨ���������
     * 
     * @param object
     *            ������Ϣ
     * @return �Ƿ�ɾ���ɹ�
     */
    public boolean deleteById(String id) {
	return dao.deleteById(id);
    }

    public Logger getLogger() {
	return logger;
    }

    public void setLogger(Logger logger) {
	this.logger = logger;
    }

    public BaseDao getDao() {
	return dao;
    }

    public void setDao(BaseDao dao) {
	this.dao = dao;
    }

    public String getOname() {
	return oname;
    }

    public void setOname(String oname) {
	this.oname = oname;
    }
}
