package com.ehl.dispatch.notice.dao;

import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;


/**
 * �ϴ��´���Ϣ��
 * 
 * @author xulf
 * 
 */
public class NoticeDao {
	
	private  Logger logger = Logger.getLogger(getClass());
	
	/**����ǰ׺������IDǰ׺*/
	private final  String tnamePost = "NOTIC";
	/**����*/
	public final  String tname = "T_OA_NOTICE";
	/**�����*/
	public final String otname = "no";
	/**���ݿ������*/
	public static final  String[] cnames = { "id", "title","content","apath", "spdcode","spname", "stime", "state" };
	/**��ѯ������*/
	
	/**������*/
	public final  String sname = "seq_oa_notice";
	
	public AccDeptDao addao = new AccDeptDao();
	
	/**
	 * ��ȡ����ID
	 * @return
	 */
	public String getId(){
		return tnamePost + CreateSequence.getMaxForSeq(sname, 20);
	}
	
	/**
	 * �õ���ѯ��
	 * @return
	 */
	public String getFullCols(){
		//       0   1     2        3     4                  5        6             7                          8
		return "id,title,content,apath,spdcode,f_get_dept(spdcode),spname,to_char(stime,'yyyy-mm-dd hh24:mi'),state";
	}
	
	/**
	 * @param map
	 */
	private void changeDate(Map<String, String> map) {
		if(map != null){
			if(map.containsKey("stime")){
				map.put("stime", Sql.toDate(FlowUtil.cancleSQ(map.get("stime"))));
			}
		}
	}
	
	/**
	 * ������ش����
	 * @param noticeMap
	 * @return
	 */
	public boolean addNotice(Map<String, String> noticeMap){
		boolean isOK = false;
		if(noticeMap != null){
			if(!noticeMap.containsKey("id")){
				noticeMap.put("id", getId());
			}
			if(!noticeMap.containsKey("state")){
				noticeMap.put("state", "2");
			}
			FlowUtil.encapMapSQ(noticeMap);
			changeDate(noticeMap);
			String sql = Sql.insert(tname, noticeMap);
			isOK = FlowUtil.write(sql, logger, "������ش����");
		}
		return isOK;
	}
	
	/**
	 * ��ȡ�ش������Ϣ
	 * @param id ����ID
	 * @return
	 */
	public Object[] getNotice(String id){
		Object[] data = null;
		if(id != null){
			String sql = Sql.select(tname, getFullCols(),"id="+FlowUtil.encapSQ(id));
			data = FlowUtil.readLine(sql, logger, "��ȡ�ش������Ϣ");
		}
		return data;
	}
	
	/**
	 * <pre>
	 * ��ѯ�ش����
	 * ���ݷ����˻������롢����״̬����Ϣ״̬�����ҳ����
	 * </pre>
	 * @param spdcode
	 * @param state
	 * @param mstate
	 * @return
	 */
	public Object[][] searchNotice(String spdcode, String state, String mstate){
		Object[][] data = null;
		if(spdcode != null && state != null && mstate != null){
			String colStr = "ad.id,no.title,f_get_dept(no.spdcode)," +
					"decode(ad.adid,null,f_get_dept(no.spdcode)," + 
					"(select replace(f_get_dept(rpdcode),'�����ֽ�ͨ����','����') from t_oa_acceptdept where id=ad.adid))," +
					"decode(ad.adid,null,'��',f_get_dept(ad.rpdcode)),decode(ad.adid,null,'���յ�λ','ת����λ'),decode(xcbk.state,'1','ͨ����','�ѳ���')," +
					"decode(ad.adid,null,'2','3'),ad.id ";
			String tableStr = tname + " xcbk," + addao.tname + " ad ";
			String whereStr = "xcbk.id = ad.aid";
			whereStr += " and ad.atype='4'";
			whereStr += " and ad.rpdcode='"+spdcode+"'";
			whereStr += " and ad.state = '" + state + "'";
			whereStr += " and ad.mstate = '" + mstate + "'";
			String sql = Sql.select(tableStr, colStr, whereStr, null, "xcbk.frtime desc");
			data = FlowUtil.readMilte(sql, logger, "��ѯЭ�鲼����Ϣ");
		}
		return data;
	}
	
	/**
	 * �޸����ش����
	 * @param noticeMap
	 * @return
	 */
	public boolean modifyNotice(Map<String,String> noticeMap){
		boolean isOK = false;
		if(noticeMap != null){
			String id = noticeMap.get("id");
			noticeMap.remove("id");
			FlowUtil.encapMapSQ(noticeMap);
			String sql = Sql.update(tname, noticeMap, "id="+FlowUtil.encapSQ(id));
			isOK = FlowUtil.write(sql, logger, "�޸����ش����");
		}
		return isOK;
	}
	
	/**
	 * ɾ�����ش����
	 * @param id �������
	 * @return �������
	 */
	public boolean deleteById(String id){
		boolean isOK = true;
		if(id != null){
			String sql = Sql.delete(tname, "id='"+id+"'");
			isOK = FlowUtil.write(sql, logger, "ɾ�����ش����");
		}
		return isOK;
	}
	
	
	
	
	/**
	 * <pre>
	 * ��ȡ�ش�����ͽ��յ�λ������Ϣ
	 * 1.count(no.id) 
	 * </pre>
	 * @param colStr ��ѯ��
	 * @param whereMap ɸѡ����
	 * @return
	 */
	public Object[][] getNoticeAccdept(String colStr,Map<String,String> whereMap,String whereStr){
		Object[][] data = null;
		if(colStr != null && whereMap != null && whereMap.size() > 0){
			String ctname = tname + " " + otname + "," + addao.tname + " " + addao.otname;
			whereMap.put(otname+".aid", addao.otname+".id");
			if(whereStr != null && !whereStr.equals("")){
				whereStr += " and " + Sql.join(whereMap, Sql.sepWhereItem);
			}else{
				whereStr = Sql.join(whereMap, Sql.sepWhereItem);
			}
			String sql = Sql.select(ctname, colStr, whereStr, null, otname+".id");
			data = FlowUtil.readMilte(sql, logger, "��ȡ�ش�����ͽ��ջ�����Ϣ");
		}
		return data;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
