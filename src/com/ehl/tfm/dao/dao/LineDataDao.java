package com.ehl.tfm.dao.dao;

import java.util.Map;

import com.ehl.tfm.dao.impl.LineFromCSDLWImpl;

public interface LineDataDao {
	/**
     * <b>获取当天最新流量数据并返回</b>
     * param:deptId-机构ID 不为空则查询全部流量信息
     * param:roadStatus-要获取的路况类型（拥堵，拥挤，畅通，all）
     * @return map 路况信息数据.
     */
    public Map getLineMap(String deptId,String roadStatus) throws Exception; 
    /**
     * <b>从内存中获取当天最新流量数据并返回</b>
     * @return map 路况信息数据.
     */
	public Map<String,Object[][]> getRedrawLineMap() throws Exception;
	
}
