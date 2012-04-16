package com.ehl.dispatch.cdispatch.dao;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;

public class RoadManageDao
{

	private Logger logger;


	public boolean addRoadInfo(HashMap res)
	{
		String sql = null;
		boolean flag = false;
		try
		{
			String ROADID = res.get("ROADID").toString();
			String ROADNAME = res.get("ROADNAME").toString();
			String ROADLEVEL = res.get("ROADLEVEL").toString();
			String START = res.get("START").toString();
			String END = res.get("END").toString();
			sql = "insert into t_oa_dictdlfx(ID,GBDM,DLMC,BEGIN,END,ROADLEVEL)";
			sql = (new StringBuilder(String.valueOf(sql))).append("values(seq_t_oa_dictdlfx.nextval,'")
			.append(ROADID).append("','").append(ROADNAME)
			.append("', '").append(START).append("', '").append(END)
			.append("', '").append(ROADLEVEL)
			.append("')").toString();
			flag = DBHandler.execute(sql);
		}
		catch (Exception e)
		{
			logger.error("新增道路信息出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	public boolean editRoadInfo(HashMap res)
	{
		String sql = null;
		boolean flag = false;
		try
		{
			String ID = res.get("ID").toString();
			String ROADID = res.get("ROADID").toString();
			String ROADNAME = res.get("ROADNAME").toString();
			String ROADLEVEL = res.get("ROADLEVEL").toString();
			String START = res.get("START").toString();
			String END = res.get("END").toString();
			sql = "update t_oa_dictdlfx";
			sql = (new StringBuilder(String.valueOf(sql))).append(" set GBDM='").append(ROADID).append("',DLMC='")
			.append(ROADNAME).append("',BEGIN='").append(START).append("', END='")
			.append(END).append("',ROADLEVEL='").append(ROADLEVEL).append("'").toString();
			sql = (new StringBuilder(String.valueOf(sql))).append(" where ID='").append(ID).append("'").toString();
			System.out.println(sql);
			flag = DBHandler.execute(sql);
		}
		catch (Exception e)
		{
			logger.error("编辑道路信息:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	public boolean delteRoadInfo(String id)
	{
		String sql = null;
		boolean flag = false;
		try
		{
			sql = "delete from t_oa_dictdlfx";
			sql = (new StringBuilder(String.valueOf(sql))).append(" where ID='").append(id).append("'").toString();
			flag = DBHandler.execute(sql);
		}
		catch (Exception e)
		{
			logger.error("删除道路信息:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}

	public Object[] getRoadInfo(String id)
	{
		String sql = null;
		Object obj[] = (Object[])null;
		try
		{
			sql = "select ID,GBDM,DLMC,BEGIN,END,ROADLEVEL from t_oa_dictdlfx";
			sql = (new StringBuilder(String.valueOf(sql))).append(" where ID='").append(id).append("'").toString();
			obj = DBHandler.getLineResult(sql);
		}
		catch (Exception e)
		{
			logger.error("查询一条道路信息:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return obj;
	}

	public boolean checkSingleRoad(String ROADID)
	{
		String sql = null;
		boolean flag = false;
		Object obj[] = (Object[])null;
		try
		{
			sql = "select * from t_oa_dictdlfx";
			sql = (new StringBuilder(String.valueOf(sql))).append(" where GBDM='").append(ROADID).append("'").toString();
			System.out.println(sql);
			obj = DBHandler.getLineResult(sql);
			if (obj == null)
			{
				System.out.println("唯一！！");
				flag = true;
			}
		}
		catch (Exception e)
		{
			logger.error("判断道路编号唯一性出错:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
}