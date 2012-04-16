package com.ehl.help.Core;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ehl.base.BaseCore;
import com.ehl.help.dao.LeaveDao;
import com.ehl.util.Json;

/**
 * 留言业务逻辑类
 * 
 * @author xiayouxue
 * 
 */
public class LeaveCore extends BaseCore {

	/** 日志类 */
	private Logger logger = Logger.getLogger(this.getClass());

	/** 对象名称 */
	private String oname = "leave";

	/** 问题数据访问类 */
	private LeaveDao dao = new LeaveDao();

	/** 回复数据访问类 */
	private ReplyCore replyCore = new ReplyCore();

	public LeaveCore() {
		super.setLogger(logger);
		super.setDao(dao);
		super.setOname(oname);
	}

	public ReplyCore getReplyCore() {
		return replyCore;
	}

	public void setReplyCore(ReplyCore replyCore) {
		this.replyCore = replyCore;
	}

	public JSONObject select(Map<String, Object> params) {
		List<Object> list = dao.select(params);
		JSONObject objJson = Json.fromData((Object[][]) list.get(0));
		int rowTotal = Integer.parseInt((String) list.get(1));
		int rowNum = Integer.parseInt((String) params.get("rows"));
		int pageNum = rowTotal / rowNum + (rowTotal % rowNum == 0 ? 0 : 1);
		objJson.put("records", rowTotal);
		objJson.put("total", pageNum);
		objJson.put("page", params.get("page"));
		return objJson;
	}

	public JSONObject getDetail(String id) {
		Object[] object = dao.getById(id);
		JSONObject objJson = Json.toJson(object, dao.getFields());
		objJson.put("replys", replyCore.getByLid(id));
		return objJson;
	}

}
