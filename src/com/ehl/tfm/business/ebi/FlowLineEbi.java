package com.ehl.tfm.business.ebi;

import java.util.Map;

public interface FlowLineEbi {
	
    /**
     * <b>获取流量线vml字符串.</b>
     * param:deptId-机构ID 不为空则查询全部流量信息 
     * param:roadStatus-要获取的路况种类（“block-拥堵”，“flow-畅通”，“crowd-拥挤”,"all"）
     * param:zoomLevel-地图放大级数
     * param:isRedraw-是否重绘
     * @return 流量线vml字符串.
     */
    public String getLineVmlStr(String deptId,String roadStatus,int zoomLevel,boolean isRedraw) throws Exception;
    /**
	 * @作者：zhaoyu
	 * @版本号：1.0
	 * @函数说明：同步t_tfm_linkdir,CSDLW_PL图层和t_gis_linkdir_segs表数据
	 * @创建日期：2010-5-4
	 * @返回：同步结果提示语
	 */
    public String synLinkDir() throws Exception;
   
}
