package com.ehl.assist.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class PrintDao {
	public static Logger logger = Logger.getLogger(PrintDao.class);
	/** ����ǰ׺������IDǰ׺ */
	public final static String tnamePost = "print";
	/** ���� */
	public final static String tname = "T_ASSIST_PRINT";
	/** ����� */
	public final static String otname = "print";
	/** ���ݿ������ */
	public final static String[] cnames = { "id", "rid", "jgid", "snum"};
	/** �����ֶ����� */
	public final static String[] fnames = { };
	/** ������ */
	public final static String sname = "SEQ_ASSIST_PRINT";
	
	
	/**
	 * ��ȡ����ID
	 * @return ����ID
	 */
	public static String getId() {
		return CreateSequence.getMaxForSeq(sname, 20);
	}
	
	/**
	 * ��Ӵ�ӡ����
	 * @param print ��ӡ����
	 * @return �������
	 */
	public static String insert(Map<String, String> print){
		if(print == null){
			return null;
		}
		String id = getId();
		print.put("id", id);
		FlowUtil.encapMapSQ(print);
		String sql = Sql.insert(tname, print);
		boolean isSuccess = FlowUtil.write(sql,logger,"��Ӵ�ӡ����");
		return isSuccess ? id : null;
	}
	
	/**
	 * ��ɴ�ӡ����
	 * @param rid ���������������
	 * @param jgid �������
	 * @param snum ���к�
	 * @return ��ӡ����
	 */
	public static Map<String,String> formPrint(String rid, String jgid, String snum){
		Map<String,String> print = new HashMap<String, String>();
		print.put("rid",rid);
		print.put("jgid", jgid);
		print.put("snum", snum);
		return print;
	}
	
	/**
	 * ��ȡ��ӡ���к�
	 * @param rid ���������������
	 * @param jgid �������
	 * @return ���к�
	 */
	public static String getSerialNum(String rid, String jgid){
		if(rid == null || jgid == null){
			return null;
		}
		String sql = Sql.select(tname, "snum","rid='"+rid+"' and jgid='"+jgid+"'");
		Object snum = FlowUtil.readSingle(sql,logger,"��ȡ��ӡ���к�");
		return (String)snum;
	}
	
}
