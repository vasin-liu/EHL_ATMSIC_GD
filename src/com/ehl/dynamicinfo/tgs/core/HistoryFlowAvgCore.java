package com.ehl.dynamicinfo.tgs.core;

import org.apache.log4j.Logger;

import com.ehl.base.BaseCore;
import com.ehl.dynamicinfo.tgs.dao.HistoryFlowAvgDao;

/**
 * ��ʷ����ƽ��ֵҵ���߼���
 * 
 */
public class HistoryFlowAvgCore extends BaseCore {
    /** ��־�� */
    private Logger logger = Logger.getLogger(this.getClass());

    /** �������� */
    private String oname = "leave";

    /** �������ݷ����� */
    private HistoryFlowAvgDao dao = new HistoryFlowAvgDao();

    public HistoryFlowAvgCore() {
	super.setLogger(logger);
	super.setDao(dao);
	super.setOname(oname);
    }
    
    /**
     * <pre>
     * ��ȡ�����ʣ���ǡ����ǡ�˫��
     * ��ȡĳ������ĳһ��Сʱ�Աȸÿ��ڸ�Сʱ��ʷƽ��ֵ��������
     * </pre>
     * @param kkmc ��������
     * @param hour Сʱ
     * @param inNew �������
     * @param outNew ��������
     * @return ��ǡ����ǡ�˫������������
     */
    public String[] getGrowth(String kkmc, String hour, int inNew, int outNew) {
	String[] growths = { "--", "--", "--","--","--","--"};
	Object[][] objects = dao.getHistoryAvg(kkmc, hour);
	if (objects != null && objects.length > 0) {
	    int length = objects.length;
	    if (length == 1) {
		String direction = (String) objects[0][4];
		int flow = Integer.parseInt(String.valueOf(objects[0][6]));
		if(flow != 0){
		    int index = Integer.parseInt(direction) - 1;
		    int[] inoutNew = { inNew, outNew };
		    growths[index] = getGrowth(inoutNew[index], flow);
		    growths[2] = growths[index];
		    growths[index+3] = String.valueOf(flow);
		    growths[5] = growths[index+3];
		}
	    } else {
		int inOri = Integer.parseInt(String.valueOf(objects[0][6]));
		int outOri = Integer.parseInt(String.valueOf(objects[1][6]));
		if(inOri != 0){
		    growths[0] = getGrowth(inNew, inOri);
		    growths[3] = String.valueOf(inOri);
		}
		if(outOri != 0){
		    growths[1] = getGrowth(outNew, outOri);
		    growths[4] = String.valueOf(outOri);
		}
		int ori = inOri + outOri;
		if(ori != 0){
		    growths[2] = getGrowth(inNew + outNew, ori);
		    growths[5] = String.valueOf(ori);
		}
	    }
	}
	return growths;
    }
    
    private static String getGrowth(int flowNew, int flowOri) {
	float growth = (float) (flowNew - flowOri) / flowOri;
	return Math.round(growth * 1000) / 10f + "%";
    }
    
}
