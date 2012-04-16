package com.ehl.tfm.business;

import com.ehl.tfm.business.ebi.FlowLineEbi;
import com.ehl.tfm.business.ebo.LineFromBMZEbo;
import com.ehl.tfm.business.ebo.LineFromCSDLWEbo;
import com.ehl.tfm.business.ebo.LineFromDatabase;
import com.ehl.tfm.business.ebo.LineFromLCB;


/**
 * @����˵��: ·���߼���������
 * @�����ߣ�zhaoyu
 * @�������ڣ�2008-10-16
 */
public class BusinessFactory {
	/**
	 * <b>��ȡ������Ϣ�ӿ�.</b>
	 * @param flag "1"-����ģʽ������CSDLW_PL���������·�λ��������ߣ���
	 *              "2"-�㶫ģʽ��������̱���ͼ���t_tfm_currentflow_5m·������������ߣ�
	 *              "3"ΪΪ����ģʽ�����е�·�����Ľ���  "5"�㶫LCB_PT
	 * @return �ӿ�ʵ����ʵ��.
	 */
	public  static FlowLineEbi getFlowLineEbi(String flag){
		if(flag.equals("1")){
			return(new LineFromCSDLWEbo());		
		}else if(flag.equals("2")){
			return(new LineFromBMZEbo());	
		}else if(flag.equals("4")){
			return(new LineFromDatabase());	
		}else if(flag.equals("5")){
			return (new LineFromLCB());
		}else{
			return (new LineFromLCB());
		}
		
	}
}
