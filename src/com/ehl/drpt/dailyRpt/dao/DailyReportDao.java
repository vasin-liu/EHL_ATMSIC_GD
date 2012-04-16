package com.ehl.drpt.dailyRpt.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.BaseDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.drpt.common.Constants;
import com.ehl.tira.util.Department;
import com.ehl.tira.util.Sql;
import com.ehl.util.Array;

/**
 * �����ձ���
 * 
 * @author xiayx
 * 
 */
public class DailyReportDao extends BaseDao {

    /** ��־�� */
    private Logger logger = Logger.getLogger(this.getClass());

    /** ʵ������� */
    private String ename = "�����ձ���";

    /** ���� */
    private String tname = "T_OA_DAYREPORT";

    /** ����� */
    private String otname = "dr";
    
    /** ���� */
    private String[] cnames = { "rzbh", "tbdw", "tjrq", "tbr", "shr", "lxdh",
	    "trjl", "cdjc", "cssb", "csd", "gdcsd", "ldcsd", "zqfwd",
	    "szlszqd", "jtwfhj", "csxs", "kcjy", "pljs", "jhjs", "wzjs",
	    "nycwfzk", "dxjdcjsz", "zkjtwfcl", "jljtwfjsr", "xzzyck", "ydtb",
	    "pcaqyhc", "jytzkcjsr", "qzpljsrxx", "zhcflb", "jxxchd", "bfxcgp",
	    "kdxcl", "xch", "xczl", "sjy", "dsxc", "dtxc", "bzxc", "wlxc",
	    "zhs", "jckccl", "tbkcqyzgbm", "srzyysqy", "jyysqyjsr",
	    "qdeltqyjya", "yjsdflcl", "zzwxld", "swsgzs", "swsgswrs",
	    "swsgssrs", "tdsgzs", "tdsgswrs", "tdsgssrs","zkjdcjsz" };
    /**��������*/
    private String pk = "rzbh";
    
    /** ������������ */
    private String[] dcnames = { "tjrq" };

    public DailyReportDao() {
	super.setLogger(logger);
	super.setEname(ename);
	super.setTname(tname);
	super.setOtname(otname);
	super.setCnames(cnames);
	super.setDcnames(dcnames);
	super.setPk(pk);
    }

    public String getId(String jgid) {
	if (jgid != null && jgid.length() >= 6) {
	    String id = jgid.substring(0, 6);
	    id += new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
	    return id;
	}
	return null;
    }

    @Override
    public String[] getFields() {
	return Array.insert(cnames, 2, "dwmc");
    }

    @Override
    public String getSelect() {
	String[] select = getFields();
	select[2] = "(select jgmc from t_sys_department where jgid=" + otname
		+ ".tbdw)";
	select[3] = Sql.toChar("tjrq", 2);
	return Array.join(select, ",");
    }

    @Override
    public String insert(Map<String, String> object) {
	if (object != null) {
	    String id = object.get("rzbh");
	    if (id == null) {
		id = getId(object.get("tbdw"));
		object.put("rzbh", id);
	    }
	    FlowUtil.encapMapSQ(object);
	    changeDataType(object);
	    String sql = Sql.insert(tname, object);
	    boolean isSuccess = FlowUtil.write(sql, logger, "���" + ename + "��Ϣ");
	    return isSuccess ? id : null;
	}
	return null;
    }
    
    
    public boolean modifyById(Map<String, String> object,String id) {
	if (object == null || id == null) {
	    return true;
	}
	FlowUtil.encapMapSQ(object);
	changeDataType(object);
	String sql = Sql.update(tname + " " + otname, object, pk+"='" + id + "'");
	return FlowUtil.write(sql, logger, "�޸�" + ename + "��Ϣ");
    }

    public static void main(String[] args) {
	DailyReportDao dao = new DailyReportDao();
	Map<String,String> object = new HashMap<String, String>();
	object.put("tbdw", "440101");
//	dao.insert(object);
	System.out.println("kk");
    }
    
}
