package com.ehl.help.Core;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.ehl.base.BaseCore;
import com.ehl.help.dao.LeaveDao;
import com.ehl.util.Json;

/**
 * ����ҵ���߼���
 * 
 * @author xiayouxue
 * 
 */
public class LeaveCore extends BaseCore {

	/** ��־�� */
	private Logger logger = Logger.getLogger(this.getClass());

	/** �������� */
	private String oname = "leave";

	/** �������ݷ����� */
	private LeaveDao dao = new LeaveDao();

	/** �ظ����ݷ����� */
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
