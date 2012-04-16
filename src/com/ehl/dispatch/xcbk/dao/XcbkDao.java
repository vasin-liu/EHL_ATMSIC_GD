package com.ehl.dispatch.xcbk.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;

public class XcbkDao {
	
	private Logger logger = Logger.getLogger(XcbkDao.class);
	
	private final static String tnamePost = "XCBK";
	public final static String tname = "T_OA_XCBK";
	public final static String[] cols = {
		"id","carnumber","cartype","carcolor","content","lpdname","lpname","lpphone",
		"frpdcode","frpname","apname","frtime","state","mstate","adcode"
	};
	public final static String sname = "seq_oa_xcbk";
	
	public AccDeptDao addao = new AccDeptDao();
	
	/**
	 * ��ȡ����ֵ
	 * @return
	 */
	public String getId(){
		return tnamePost + CreateSequence.getMaxForSeq(sname, 20);
	}
	
	/**
	 * ���Э�鲼����Ϣ
	 * @param map
	 * @return
	 */
	public boolean addXcbk(Map<String, String> map){
		boolean isOK = false;
		if(map != null){
			if(!map.containsKey("id")){
				map.put("id", getId());
			}
			map.put("state", "1");
			map.put("mstate", "1");
			FlowUtil.encapMapSQ(map);
			map.put("frtime", Sql.toDate(FlowUtil.cancleSQ(map.get("frtime"))));
			String sql = Sql.insert(tname, map);
			isOK = FlowUtil.write(sql, logger, "���Э�鲼����Ϣ");
		}
		return isOK;
	}
	
	/**���Э�鲼�ؽ��յ�λ
	 * @param id
	 * @param adcode
	 */
	public boolean addXcbkAccDept(String id, String adcode, String mstate) {
		boolean isOK = false;
		if(id != null && adcode != null && !adcode.equals("") && mstate != null ){
			Map<String,String> admap = new HashMap<String, String>();
			admap.put("rpdcode", adcode);
			admap.put("mstate", mstate);
			admap.put("aid", id);
			admap.put("atype", "4");
			isOK = addao.adds(admap);
		}
		return isOK;
	}
	
	/**
	 * ��ѯЭ�鲼����Ϣ
	 * @param xcols Э����ֶ�
	 * @param acols ���յ�λ�ֶ�
	 * @param map ��ѯ������������rpdcode��
	 * @return
	 */
	public Object[][] searchXcbk(String rpdcode, String state, String mstate){
		Object[][] data = null;
		if(rpdcode != null && state != null && mstate != null){
			String colStr = "ad.id,xcbk.carnumber,replace(f_get_dept(xcbk.frpdcode),'�����ֽ�ͨ����','����')," +
					"decode(ad.adid,null,replace(f_get_dept(ad.rpdcode),'�����ֽ�ͨ����','����')," +
					"(select replace(f_get_dept(rpdcode),'�����ֽ�ͨ����','����') from t_oa_acceptdept where id=ad.adid))," +
					"decode(ad.adid,null,'��',f_get_dept(ad.rpdcode)),decode(ad.adid,null,'���յ�λ','ת����λ'),decode(xcbk.state,'1','ͨ����','�ѳ���')," +
					"decode(ad.adid,null,'2','3'),ad.id ";
			String tableStr = tname + " xcbk," + addao.tname + " ad ";
			String whereStr = "xcbk.id = ad.aid";
			whereStr += " and ad.atype='4'";
			whereStr += " and ad.rpdcode='"+rpdcode+"'";
			whereStr += " and ad.state = '" + state + "'";
			whereStr += " and ad.mstate = '" + mstate + "'";
			String sql = Sql.select(tableStr, colStr, whereStr, null, "xcbk.frtime desc");
			data = FlowUtil.readMilte(sql, logger, "��ѯЭ�鲼����Ϣ");
		}
		return data;
	}
	
	/**
	 * ��ȡЭ��ͨ����Ϣ<br>
	 * ���Э��ͨ����Ϣ
	 * @param aid
	 */
	public Object[] getXcbk(String aid) {
		Object[] data  =  null;
		if(aid != null){
			String[] cols = {
					"id","carnumber","cartype","f_get_name('011001',cartype)",
					"carcolor","f_get_name('011007',carcolor)","content","lpdname",
					"lpname","lpphone","frpdcode","f_get_dept(frpdcode)","frpname","apname",
					"to_char(frtime,'yyyy-mm-dd hh24:mi')","state","adcode"
				};
			String colStr = Sql.join(cols, ",");
			String whereStr = "id=" + FlowUtil.encapSQ(aid);
			String sql = Sql.select(tname, colStr, whereStr);
			data = FlowUtil.readLine(sql, logger, "��ȡЭ�鲼����Ϣ");
		}
		return data;
	}

	
	/**
	 * �޸�Э�鲼����Ϣ
	 * @param id
	 * @param map
	 * @return
	 */
	public boolean modifyXcbk(Map<String,String> map){
		boolean isOK = false;
		String id = map.get("id");
		map.remove(id);
		map.put("mstate", "2");
		FlowUtil.encapMapSQ(map);
		if(map.containsKey("frtime")){
			map.put("frtime", Sql.toDate((FlowUtil.cancleSQ(map.get("frtime")))));
		}
		String sql = Sql.update(tname, map, "id='"+id+"'");
		isOK = FlowUtil.write(sql, logger, "�޸�Э�鲼����Ϣ");
		return isOK;
	}
	
	/**
	 * ����Э�鲼����Ϣ<br>
	 * content
	 * @param parammIn
	 * @return
	 */
	public boolean updateXcbk(Map<String, String> parammIn) {
		boolean isOK = false;
		if(parammIn != null){
			String id = parammIn.get("id");
			parammIn.remove("id");
			FlowUtil.encapMapSQ(parammIn);
			String sql = Sql.update(tname, parammIn, "id="+FlowUtil.encapSQ(id));
			isOK = FlowUtil.write(sql, logger, "����Э�鲼����Ϣ");
		}
		return isOK;
	}
	
	/**
	 * ���Э��ͨ����Ϣ
	 * @param id
	 * @return
	 */
	public boolean cancelXcbk(String id) {
		boolean isOK = false;
		if(id != null){
			String setStr = "state='2'";
			String whereStr = "id=" + FlowUtil.encapSQ(id);
			String sql = Sql.update(tname, setStr, whereStr);
			isOK = FlowUtil.write(sql, logger, "���Э��ͨ����Ϣ");
		}
		return isOK;
	}
	
	/**
	 * ���Э��ͨ����Ϣ
	 * @param id
	 * @return
	 */
	public boolean update(String id) {
		boolean isOK = false;
		if(id != null){
			String setStr = "state='2'";
			String whereStr = "id=" + FlowUtil.encapSQ(id);
			String sql = Sql.update(tname, setStr, whereStr);
			isOK = FlowUtil.write(sql, logger, "���Э��ͨ����Ϣ");
		}
		return isOK;
	}
	
	
	
	
	
}