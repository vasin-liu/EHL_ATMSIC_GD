package com.ehl.help.Core;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.ehl.base.BaseCore;
import com.ehl.help.dao.ReplyDao;
import com.ehl.util.Json;

/**
 * �ظ�ҵ���߼���
 * 
 * @author xiayouxue
 * 
 */
public class ReplyCore extends BaseCore {

	/** ��־�� */
	private Logger logger = Logger.getLogger(this.getClass());
	
	/** �ظ����ݷ����� */
	private ReplyDao dao = new ReplyDao();
	
	/** �������� */
	private String oname = "reply";
	
	public ReplyCore() {
		super.setLogger(logger);
		super.setDao(dao);
		super.setOname(oname);
	}
	
	/**
	 * ͨ�����Ա�Ż�ȡ�ظ���Ϣ
	 * @param lid ���Ա��
	 * @return �ظ���Ϣ�б�
	 */
	public JSONArray getByLid(String lid){
		Object[][] objects = dao.getByLid(lid);
		return Json.toJson(objects, dao.getFields());
	}

	public ReplyDao getDao() {
		return dao;
	}

	public void setDao(ReplyDao dao) {
		this.dao = dao;
	}

}
