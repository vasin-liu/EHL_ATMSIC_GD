package com.ehl.dispatch.notice.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ehl.dispatch.accdept.core.AccDeptCore;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.dispatch.notice.dao.NoticeDao;
import com.ehl.sm.base.Constant;
import com.ehl.tira.util.Sql;
import com.ehl.tira.util.XML;

public class NoticeCore {
	
	
	public final Logger logger = Logger.getLogger(NoticeCore.class);

	public NoticeDao dao = new NoticeDao();
	public ContentCore ctcore = new ContentCore();
	public AccDeptCore adcore = new AccDeptCore();
	
	/**
	 * 添加重大情况
	 * @param noticeMap
	 * @param contentMap
	 * @param accdeptMap
	 * @return
	 */
	public String addNotice(Map<String, String> noticeMap,
			 Map<String, String> accdeptMap) {
		boolean isOK = false;
		if (noticeMap != null) {
			String id;
			if(!noticeMap.containsKey("id")){
				id = dao.getId();
				noticeMap.put("id", id);
			}else{
				id = noticeMap.get("id");
			}
			noticeMap.put("state", "3");
			isOK = dao.addNotice(noticeMap);
			if (isOK == true && accdeptMap != null) {
				accdeptMap.put("aid", id);
				accdeptMap.put("atype", "5");
				accdeptMap.put("mstate", "1");
				isOK = adcore.dao.adds(accdeptMap);
			}
		}
		return String.valueOf(isOK);
	}
	
	/**
	 * 发送其他重大情况
	 * @param aid 其他重大情况主键ID
	 * @param adcode 接收单位ID
	 * @return 发送是否成功
	 */
	public boolean send(String aid, String adcode) {
		boolean isOK = false;
		if (aid != null && adcode != null) {
			String sql = "select rpdcode from t_oa_acceptdept where";
			sql += " aid='" + aid + "' and (adid is null or adid='" + aid + "')";
			Object[][] adinfoRpdcodes = FlowUtil.readMilte(sql);
			String add,update;
			if(adinfoRpdcodes != null){
				String[] rpdcodes = new String[adinfoRpdcodes.length];
				for (int i = 0; i < adinfoRpdcodes.length; i++) {
					rpdcodes[i] = (String)adinfoRpdcodes[i][0];
				}
				String[] updateAdd = adcore.distinguishContainOrNot(rpdcodes,adcode);
				update = updateAdd[0];
				if (update != null && !update.equals("")) {
					sql = "update t_oa_acceptdept set state='1'";
					sql += " where aid='"+aid+"' and (adid is null or adid='"+aid+"') and instr("+update+",rpdcode)!=0";
					isOK = FlowUtil.write(sql);
				} else {
					isOK = true;
				}
				add = updateAdd[1];
			} else {
				add = adcode;
				isOK = true;
			}
			if (isOK) {
				if (add != null && !add.equals("")) {
					isOK = adcore.addAccDept(aid, "5", "1", add);
				} else {
					isOK = true;
				}
			}
		}
		return isOK;
	}
	
	/**
	 * <pre>
	 * 获取重大情况XML格式数据包含notice和content
	 * 形如
	 * <notice>
	 * 	<col></col>
	 *  <content>
	 *  	<col></col>
	 *  </content>
	 * </notice>
	 * </pre>
	 * @param id
	 * @return
	 */
	public String getNotice(String id){
		String xml = null;
		Object[] data = dao.getNotice(id);
		if(data != null){
			xml = XML.encapContent("col", data);
			String cxml = ctcore.getContents(id,null);
			if(cxml != null){
				xml += cxml;
			}
			xml = XML.encapContent("notice", xml);
		}
		return xml;
	}
	
	/**
	 * 
	 * @param id 警情ID
	 * @param udcode 用户机构代码
	 * @param stype 信息来源类型
	 * @return
	 */
	public String getNotice(String id, String udcode, int stype) {
		String xml = null;
		if(id != null && udcode != null && stype >= 1 && stype <= 3){
			String nxml="",adxml="",cntxml="",adcntxml="",disxml="";
			nxml = getNotice(id);//notice
			Object[][] accdepts = adcore.dao.getAccDept(id, null, 2);
			if(accdepts != null){
				String[] states = {"1","2","3"};
				String adsxml = adcore.getAccDept(accdepts, states);//adstate
				nxml += adsxml;
				if(stype == 1){//发送单位
					//<notice><col></col><content></content></notice>
					//<adstate><col></col></adstate>
					//<adcontent><col></col><content></content></adcontent>
					for (int i = 0; i < accdepts.length; i++) {
						adxml = XML.encapContent("col", accdepts[i]);
						cntxml = ctcore.getContents(accdepts[i][0]+"",udcode);
						if(cntxml != null){
							adxml += cntxml;
						}
						adcntxml += XML.encapContent("adcontent", adxml);//adcontent
					}
					Object pjgmc = "";
					String parent = Constant.getParent(udcode);
					if(parent != null){
						String sql = "select jgmc from t_sys_department where jgid='" + parent + "'";
						pjgmc = FlowUtil.readSingle(sql);
						pjgmc = pjgmc == null ? "" : pjgmc;
					}
					int count = Constant.getChildCount(udcode);
					adcntxml += XML.encapContent("jgmc", count+","+pjgmc);
				}else if(stype == 2){//接收单位 
					//<notice><col><content></content></notice>
					//<adcontent><col></col><content><col></col><adstate></adstate></content></adcontent>
					Object[][] accdepts1 = adcore.dao.getAccDept(id, udcode, 2);
					if(accdepts1 != null && accdepts1.length == 1){
						Object[] accdept = accdepts1[0];
						adxml = XML.encapContent("col", accdept);
						Object[][] contents = ctcore.dao.getContents(accdept[0]+"", null);
						String cntxmls = "";
						if(contents != null){
							String[] state = new String[]{"1","2"};
							for (int i = 0; i < contents.length; i++) {
								cntxml = XML.encapContent("col", contents[i]);
								cntxml += adcore.getDisDept(contents[i][0]+"", state);
								cntxmls += XML.encapContent("content", cntxml);
							}
						}
						adcntxml = XML.encapContent("adcontent", adxml + cntxmls);
						String ddcntxml = ctcore.getDeptCtnt(id, udcode);//获取接收单位作为转发单位
						if(ddcntxml != null){
//						adcntxml += ddcntxml;
						}
					}
				}else if(stype == 3){//转发单位
					//<notice><col><content></content></notice>
					//<adcontent><col></col><content><col></col></content></adcontent>
					//<dis><col></col></dis>
					Object[][] disdepts = adcore.dao.getAccDept(id, udcode, 3);
					if(disdepts != null){
						disxml = XML.encapContent("dis","col", disdepts[0]);
						adcntxml = ctcore.getDeptCtnt(id, udcode);
					}
				}
			}
			xml = XML.getXML(nxml+adcntxml+disxml);
		}
		return xml;
	}

	/**
	 * 更新其他重大情况，添加内容
	 * @param paramMap
	 * @param stype
	 * @return
	 */
	public String updateNotice(int stype,String udcode,String adcode,Map<String, String> paramMap) {
		boolean isOK = false;
		if(paramMap != null && stype >= 1 && stype <= 2){
			String spname = paramMap.get("spname");
			String sid = paramMap.get("sid");
			//Modified by Liuwx 2011-07-29
			String cidString = paramMap.get("cids");
			paramMap.remove("cids");
			//Modification finished
			if(stype == 1){//发送方
				isOK = ctcore.dao.addContent(paramMap);
				if(isOK){
					Map<String,String> noticeMap = new HashMap<String, String>();
					noticeMap.put("id", sid);
					noticeMap.put("state", "2");
					isOK = dao.modifyNotice(noticeMap);
				}
				if(isOK){
					isOK = adcore.updateMState(sid, "2");
				}
				if(isOK){
					isOK = adcore.signIn(adcore.dao.getWhere(sid, udcode, ""));
				}
			}else if(stype == 2){//接收方
				//如果没有指定接收单位，默认为发送人接收。 
//				if(adcode == null){
//					Object[] noticeData = dao.getNotice(sid);
//					if(noticeData != null){
//						adcode = noticeData[2] + "";
//					}
//				}
				//Modified by Liuwx 2011-07-29 
				isOK = adcore.updateAccDept(cidString, adcode);
				//Modification finished
				String content = paramMap.get("content");
				if(content != null && !content.equals("")){
					Object[][] data = adcore.dao.getAccDept(sid, udcode, 2);
					if(data != null && data.length == 1){
						sid = data[0][0] + "";
						String id = ctcore.dao.getId();
						paramMap.put("id", id);
						paramMap.put("sid", sid);                          
						isOK = ctcore.dao.addContent(paramMap);
						if(isOK && adcode != null){
							Map<String,String> disDeptMap = new HashMap<String, String>();
							disDeptMap.put("adid", id);
							disDeptMap.put("aid", data[0][6]+"");
							disDeptMap.put("atype", data[0][7]+"");
							disDeptMap.put("mstate", data[0][8]+"");
							disDeptMap.put("rpdcode", adcode);
							isOK = adcore.dao.adds(disDeptMap);
						}
						if(isOK){
							String sql = "update t_oa_acceptdept set rpname='"+spname+"' where id='"+sid+"'";
							FlowUtil.write(sql, logger, "添加接收人姓名");
							isOK = adcore.dao.updateState(sid, "3", null);
						}
					}
				}
			}
		}
		return String.valueOf(isOK);
	}
	
	
	public boolean finish(String udcode,Map<String, String> noticeMap){
		boolean isOK = false;
		if(noticeMap != null){
			String id = noticeMap.get("id");
			isOK = dao.modifyNotice(noticeMap);
			if(isOK){
				isOK = adcore.updateState(id, "3", udcode, "2", null);
			}
		}
		return isOK;
	}
	
	/**
	 * 删除重特大信息
	 * @param id 主键编号
	 * @return 操作结果
	 */
	public boolean deleteById(String id){
		return dao.deleteById(id);
	}
	
	
	public String getPromptCountSql(int ptype, String udcode,String whereStr){
		String sql = null;
		if(udcode != null && whereStr != null){
			String selectStr = ptype+",ad.id";
			String tnameStr = "t_oa_notice no,t_oa_acceptdept ad";
			whereStr = "no.id=ad.aid and ad.rpdcode='"+udcode+"' and " + whereStr;
			sql = Sql.select(tnameStr, selectStr, whereStr);
		}
		return sql;
	}
	
	/**
	 * <pre>
	 * 获取重大情况提醒
	 * 1.新信息提醒，针对于接收单位
	 * 2.超时未签收，针对于转发单位
	 * 3.超时未办结，针对于接收单位
	 * </pre>
	 * @param udcode 当前用户机构代码
	 * @return
	 */
	public String getPromptCount(String udcode){
		String wherePnew = "ad.state='1'";//ad.adid is null and
		String pnewSql = getPromptCountSql(0, udcode, wherePnew);
		String whereWsign = "no.spdcode!=ad.rpdcode and ad.adid is not null and ad.state='1' and ptime<1"
				+ " and (sysdate-(select stime from t_oa_content where id=ad.adid)>= 2/24)";
		String pwsignSql = getPromptCountSql(1, udcode, whereWsign);
		String whereWfinish = "ad.adid is null and ad.state='2' and ptime < 1"
				+ " and (sysdate-(select max(stime) from t_oa_content where sid=no.id)>= 1) ";
		String pwfinishSql = getPromptCountSql(2, udcode, whereWfinish);
		String sql = pnewSql+" union "+pwsignSql+" union "+pwfinishSql;
		Object[][] data = FlowUtil.readMilte(sql, logger, "获取重大情况提醒");
		String sep = ",";
		String[] ids = adcore.getArray(3, "");
		int[] counts = new int[3];
		int ptype;
		if(data != null){
			for (int i = 0; i < data.length; i++) {
				ptype = Integer.parseInt(data[i][0]+"");
				ids[ptype] += "," + data[i][1];
				counts[ptype]++;
			}
			adcore.updatePtime(udcode, "5");
		}
		for (int i = 0; i < ids.length; i++) {
			if(!ids[i].equals("")){
				ids[i] = ids[i].substring(sep.length());
			}
		}
		String[] countStrs = new String[3];
		for (int i = 0; i < counts.length; i++) {
			countStrs[i] = counts[i]+"";
		}
		String countXml = XML.encapContent("col", countStrs);
		String idXml = XML.encapContent("id", ids);
		return XML.getXML(countXml+idXml);
	}
	
	public String getPromptInfoSql(String ids, String stype){
		String sql = null;
		if(ids != null && stype != null){
			if(stype.equals("'2'")){
				stype = "decode(ad.adid,null,'2','3')";
			}
			String selectStr = " distinct no.id,no.title,to_char(no.stime,'yyyy-mm-dd hh24:mi'),f_get_dept(no.spdcode),f_get_dept(ad.rpdcode)," +
			"decode(ad.state,'1','未签收','2','办理中','已办结'),"+stype;
			String fromStr = "t_oa_notice no,t_oa_acceptdept ad";
			String whereStr = "no.id=ad.aid and instr('"+ids+"',ad.id)!=0";
			sql = Sql.select(fromStr, selectStr, whereStr,null,"no.id");
		}
		return sql;
	}
	
	public String getPromptInfo(String ids,String ptype){
		String pcountxml = null;
		if (ids != null && ptype!=null) {
			String stype = null;
			if (ptype.equals("1") || ptype.equals("3")) {
				stype = "'2'";
			} else if (ptype.equals("2")) {
				stype = "'3'";
			}
			String sql = getPromptInfoSql(ids, stype);
			Object[][] promptInfo = FlowUtil.readMilte(sql, logger,"获取重大情况提示信息");
			if (promptInfo != null) {
				pcountxml = XML.getXML(XML.encapContent("row", "col",promptInfo));
			}
		}
		return pcountxml;
	}

}
