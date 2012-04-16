package com.ehl.dynamicinfo.tgs.dao;

import org.apache.log4j.Logger;

import com.ehl.base.BaseDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

/**
 * ��ʷ����ƽ��ֵ���ݷ�����
 * @author xiayx
 *
 */
public class HistoryFlowAvgDao extends BaseDao {

    /** ��־�� */
    private Logger logger = Logger.getLogger(this.getClass());

    /** ʵ������� */
    private String ename = "�������Ͷ�";

    /** ���� */
    private String tname = "T_TFM_FLOWHISTORYAVG";

    /** ����� */
    private String otname = "avg";

    /** ���� */
    private String[] cnames = { "id", "bid", "hour", "direction", "flow",
	    "year" };

    /** ������������ */
    private String[] dcnames = { "time" };

    /** ������ */
    private String sname = "SEQ_TFM_FLOWHISTORYAVG";

    public HistoryFlowAvgDao() {
	super.setLogger(logger);
	super.setEname(ename);
	super.setTname(tname);
	super.setOtname(otname);
	super.setCnames(cnames);
	super.setDcnames(dcnames);
	super.setSname(sname);
    }

    @Override
    public String[] getFields() {
	String[] fileds = Array.insert(cnames, 2, "kkmc");
	fileds = Array.insert(fileds, 5, "dcode");
	return fileds;
    }

    @Override
    public String getSelect() {
	String[] fileds = getFields();
	fileds[2] = "(select seg.roadsegname from t_road_seginfo seg where seg.roadsegid = "+otname+".bid) kkmc";
	fileds[5] = "decode(direction,1,'���','����')";
	return Array.join(fileds, ",");
    }
    
    /**
     * <pre>
     * ��ȡ�����������Ͷ�
     * ��ȡĳһ���ڵ�ǰ���0~ĳһСʱ�ı��Ͷ�ֵ
     * </pre>
     * @param kkmc
     * @param hour
     * @return ���ڱ��Ͷ�
     */
    public Object[][] getHistoryAvg(String kkmc, String hour) {
	String where = "year=to_char(sysdate,'yyyy')";
	where +=" and bid=(select roadsegid from t_road_seginfo where roadsegname like '%"+kkmc+"%' and rownum <= 1)";
	where += " and hour = "+hour;
	String tname = this.tname + " " + otname;
	String sql = Sql.select(tname, getSelect(), where, null,
		"hour desc,direction");
	return FlowUtil.readMilte(sql, logger, "��ȡ�����������Ͷ�");
    }

    public static void main(String[] args) {
	new HistoryFlowAvgDao().getHistoryAvg("LJHSKKLD", "01");
    }
}
