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
	 * 获取主键ID
	 * @return
	 */
	public String getId(){
		return tnamePost + CreateSequence.getMaxForSeq(sname, 20);
	}
	
	/**
	 * 获取查询列
	 * @return
	 */
	public String getFullCols(){
			  //0     1      2    3                     4           5                6
		return "id,content,apath,spname,"+Sql.toChar("stime", 4)+",sid,f_get_accdept(id,2,1)";
	}
	
	/**
	 * <pre>
	 * 添加内容
	 * apath是可选的，
	 * id、stime是可以缺失，赋默认值
	 * 其他是必填的
	 * </pre>
	 * @param paramMap 包含表列和值
	 * @return 是否添加成功
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
			isOK = FlowUtil.write(sql, logger, "添加内容");
		}
		return isOK;
	}
	
	/**
	 * 获取内容信息，通过主键ID
	 * @param id
	 * @return
	 */
	public Object[] getContent(String id){
		Object[] data = null;
		if(id != null){
			String sql = Sql.select(tname, getFullCols(),"id="+FlowUtil.encapSQ(id));
			data = FlowUtil.readLine(sql, logger, "获取内容信息通过id");
		}
		return data;
	}
	
	/**
	 * 获取内容信息，通过关联SID
	 * 指定udcode，筛选出转发给该机构的内容信息
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
			data = FlowUtil.readMilte(sql, logger, "获取内容信息通过sid");
		}
		return data;
	}
	
	/**
	 * 转发单位获取转发内容
	 * @param aid 警情ID
	 * @param rpdcode 转发人机构代码
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
			data = FlowUtil.readMilte(sql, logger, "根据警情ID、机构代码获取转发内容");
		}
		return data;
	}
	
}
