package com.ehl.tfm.dao.dao;

import java.util.Map;

import com.ehl.tfm.dao.impl.LineFromCSDLWImpl;

public interface LineDataDao {
	/**
     * <b>��ȡ���������������ݲ�����</b>
     * param:deptId-����ID ��Ϊ�����ѯȫ��������Ϣ
     * param:roadStatus-Ҫ��ȡ��·�����ͣ�ӵ�£�ӵ������ͨ��all��
     * @return map ·����Ϣ����.
     */
    public Map getLineMap(String deptId,String roadStatus) throws Exception; 
    /**
     * <b>���ڴ��л�ȡ���������������ݲ�����</b>
     * @return map ·����Ϣ����.
     */
	public Map<String,Object[][]> getRedrawLineMap() throws Exception;
	
}
