package com.ehl.dispatch.notice.dao;

import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class ContentDao {
private  Logger logger = Logger.getLogger(ContentDao.class);
	
	private final  String tnamePost = "CONTE";
	public final  String tname = "T_OA_CONTENT";
	public final String[] cols = { "id", "content", "apath","spname", "stime","sid" };
	public final  String sname = "seq_oa_content";
	
	public AccDeptDao addao = new AccDeptDao();
	/**
	 * ��ȡ����ID
	 * @return
	 */
	public String getId(){
		return tnamePost + CreateSequence.getMaxForSeq(sname, 20);
	}
	
	/**
	 * ��ȡ��ѯ��
	 * @return
	 */
	public String getFullCols(){
			  //0     1      2    3                     4           5                6
		return "id,content,apath,spname,"+Sql.toChar("stime", 4)+",sid,f_get_accdept(id,2,1)";
	}
	
	/**
	 * <pre>
	 * �������
	 * apath�ǿ�ѡ�ģ�
	 * id��stime�ǿ���ȱʧ����Ĭ��ֵ
	 * �����Ǳ����
	 * </pre>
	 * @param paramMap �������к�ֵ
	 * @return �Ƿ���ӳɹ�
	 */
	public boolean addContent(Map<String, String> paramMap){
		boolean isOK = false;
		if(paramMap != null){
			if(!paramMap.containsKey("id")){
				paramMap.put("id", getId());
			}
			if(!paramMap.containsKey("stime")){
				paramMap.put("stime", "sysdate");
			}else{
				paramMap.put("stime", Sql.toDate(paramMap.get("stime")));
			}
			FlowUtil.encapMapSQ(paramMap);
			paramMap.put("stime", FlowUtil.cancleSQ(paramMap.get("stime")));
			String sql = Sql.insert(tname, paramMap);
			isOK = FlowUtil.write(sql, logger, "�������");
		}
		return isOK;
	}
	
	/**
	 * ��ȡ������Ϣ��ͨ������ID
	 * @param id
	 * @return
	 */
	public Object[] getContent(String id){
		Object[] data = null;
		if(id != null){
			String sql = Sql.select(tname, getFullCols(),"id="+FlowUtil.encapSQ(id));
			data = FlowUtil.readLine(sql, logger, "��ȡ������Ϣͨ��id");
		}
		return data;
	}
	
	/**
	 * ��ȡ������Ϣ��ͨ������SID
	 * ָ��udcode��ɸѡ��ת�����û�����������Ϣ
	 * @param sid
	 * @return
	 */
	public Object[][] getContents(String sid, String udcode){
		Object[][] data = null;
		if(sid != null){
			String whereStr = "sid="+FlowUtil.encapSQ(sid);
			if(udcode != null){
				whereStr += " and ("+Sql.select(addao.tname, "count(id)","adid = ctnt.id and rpdcode="+FlowUtil.encapSQ(udcode))+")!=0";
			}
			String sql = Sql.select(tname+" ctnt", getFullCols(),whereStr,null,"ctnt.stime asc");
			data = FlowUtil.readMilte(sql, logger, "��ȡ������Ϣͨ��sid");
		}
		return data;
	}
	
	/**
	 * ת����λ��ȡת������
	 * @param aid ����ID
	 * @param rpdcode ת���˻�������
	 * @return
	 */
	public Object[][] getDeptCtnt(String aid, String rpdcode){
		Object[][] data = null;
		if(aid != null && rpdcode != null){
//			                     0      1                      2           3                     4
			String colStr = "ad.id,ad.rpdcode,f_get_dept(ad.rpdcode),ad.rpname,"+Sql.toChar("ad.rtime", 4)+
//			                   5        6     7         8        9       10
						",ad.state,ad.aid,ad.atype,ad.mstate,ad.adid,ad.ptime," +
//					      11        12         13           14                        15          16           17
					"ctnt.id,ctnt.content,ctnt.apath,ctnt.spname," + Sql.toChar("ctnt.stime",4)+",ctnt.sid, dis.state";
			String tnameStr = "t_oa_acceptdept ad, t_oa_content ctnt, t_oa_acceptdept dis";
			String whereStr = "ad.id = ctnt.sid and dis.adid = ctnt.id"
				+ " and dis.rpdcode=" + FlowUtil.encapSQ(rpdcode)
				+ " and dis.aid = " + FlowUtil.encapSQ(aid);
			String sql = Sql.select(tnameStr, colStr, whereStr, null,"ad.rpdcode,ctnt.stime");
			data = FlowUtil.readMilte(sql, logger, "���ݾ���ID�����������ȡת������");
		}
		return data;
	}
	
}
