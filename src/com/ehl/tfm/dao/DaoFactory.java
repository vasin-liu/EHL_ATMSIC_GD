package com.ehl.tfm.dao;

import com.ehl.tfm.dao.dao.LineDataDao;
import com.ehl.tfm.dao.impl.LineFromBMZImpl;
import com.ehl.tfm.dao.impl.LineFromCSDLWImpl;

public class DaoFactory {
	/**
	 * <b>��ȡ������Ϣ�ӿ�ʵ��.</b>
	 @param flag "1"-����ģʽ������CSDLW_PL���������·����֯�������ݣ�
	 *           ��2��-�㶫ģʽ��������̱���ͼ���t_tfm_currentflow_5m·������֯�������ݣ�
	 * @return ���ݽӿ�ʵ��.
	 */
	public  static LineDataDao getLineDataDao(String flag){
		if(flag.equals("1")){
			return new LineFromCSDLWImpl();		
		}else{
			return new LineFromBMZImpl();	
		}
		
	}
}
