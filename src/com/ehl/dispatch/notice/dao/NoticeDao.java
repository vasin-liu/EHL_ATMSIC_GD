package com.ehl.dispatch.notice.dao;

import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.base.util.CreateSequence;
import com.ehl.dispatch.accdept.dao.AccDeptDao;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.tira.util.Sql;


/**
 * 上传下达信息类
 * 
 * @author xulf
 * 
 */
public class NoticeDao {
	
	private  Logger logger = Logger.getLogger(getClass());
	
	/**表名前缀，主键ID前缀*/
	private final  String tnamePost = "NOTIC";
	/**表名*/
	public final  String tname = "T_OA_NOTICE";
	/**表别名*/
	public final String otname = "no";
	/**数据库表列名*/
	public static final  String[] cnames = { "id", "title","content","apath", "spdcode","spname", "stime", "state" };
	/**查询表列名*/
	
	/**序列名*/
	public final  String sname = "seq_oa_notice";
	
	public AccDeptDao addao = new AccDeptDao();
	
	/**
	 * 获取主键ID
	 * @return
	 */
	public String getId(){
		return tnamePost + CreateSequence.getMaxForSeq(sname, 20);
	}
	
	/**
	 * 得到查询列
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
	 * 添加重特大情况
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
			isOK = FlowUtil.write(sql, logger, "添加重特大情况");
		}
		return isOK;
	}
	
	/**
	 * 获取重大情况信息
	 * @param id 主键ID
	 * @return
	 */
	public Object[] getNotice(String id){
		Object[] data = null;
		if(id != null){
			String sql = Sql.select(tname, getFullCols(),"id="+FlowUtil.encapSQ(id));
			data = FlowUtil.readLine(sql, logger, "获取重大情况信息");
		}
		return data;
	}
	
	/**
	 * <pre>
	 * 查询重大情况
	 * 根据发送人机构代码、处理状态、信息状态，查找出结果
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
					"(select replace(f_get_dept(rpdcode),'公安局交通警察','交警') from t_oa_acceptdept where id=ad.adid))," +
					"decode(ad.adid,null,'无',f_get_dept(ad.rpdcode)),decode(ad.adid,null,'接收单位','转发单位'),decode(xcbk.state,'1','通报中','已撤销')," +
					"decode(ad.adid,null,'2','3'),ad.id ";
			String tableStr = tname + " xcbk," + addao.tname + " ad ";
			String whereStr = "xcbk.id = ad.aid";
			whereStr += " and ad.atype='4'";
			whereStr += " and ad.rpdcode='"+spdcode+"'";
			whereStr += " and ad.state = '" + state + "'";
			whereStr += " and ad.mstate = '" + mstate + "'";
			String sql = Sql.select(tableStr, colStr, whereStr, null, "xcbk.frtime desc");
			data = FlowUtil.readMilte(sql, logger, "查询协查布控信息");
		}
		return data;
	}
	
	/**
	 * 修改重特大情况
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
			isOK = FlowUtil.write(sql, logger, "修改重特大情况");
		}
		return isOK;
	}
	
	/**
	 * 删除重特大情况
	 * @param id 主键编号
	 * @return 操作结果
	 */
	public boolean deleteById(String id){
		boolean isOK = true;
		if(id != null){
			String sql = Sql.delete(tname, "id='"+id+"'");
			isOK = FlowUtil.write(sql, logger, "删除重特大情况");
		}
		return isOK;
	}
	
	
	
	
	/**
	 * <pre>
	 * 获取重大情况和接收单位关联信息
	 * 1.count(no.id) 
	 * </pre>
	 * @param colStr 查询列
	 * @param whereMap 筛选条件
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
			data = FlowUtil.readMilte(sql, logger, "获取重大情况和接收机构信息");
		}
		return data;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
