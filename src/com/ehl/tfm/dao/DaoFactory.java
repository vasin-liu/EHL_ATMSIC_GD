package com.ehl.tfm.dao;

import com.ehl.tfm.dao.dao.LineDataDao;
import com.ehl.tfm.dao.impl.LineFromBMZImpl;
import com.ehl.tfm.dao.impl.LineFromCSDLWImpl;

public class DaoFactory {
	/**
	 * <b>获取流量信息接口实例.</b>
	 @param flag "1"-菏泽模式（基于CSDLW_PL和连线逐个路段组织流量数据）
	 *           “2”-广东模式（基于里程碑点图层和t_tfm_currentflow_5m路况表组织流量数据）
	 * @return 数据接口实例.
	 */
	public  static LineDataDao getLineDataDao(String flag){
		if(flag.equals("1")){
			return new LineFromCSDLWImpl();		
		}else{
			return new LineFromBMZImpl();	
		}
		
	}
}
