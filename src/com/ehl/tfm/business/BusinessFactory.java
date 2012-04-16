package com.ehl.tfm.business;

import com.ehl.tfm.business.ebi.FlowLineEbi;
import com.ehl.tfm.business.ebo.LineFromBMZEbo;
import com.ehl.tfm.business.ebo.LineFromCSDLWEbo;
import com.ehl.tfm.business.ebo.LineFromDatabase;
import com.ehl.tfm.business.ebo.LineFromLCB;


/**
 * @类型说明: 路况逻辑处理工厂类
 * @创建者：zhaoyu
 * @创建日期：2008-10-16
 */
public class BusinessFactory {
	/**
	 * <b>获取流量信息接口.</b>
	 * @param flag "1"-菏泽模式（基于CSDLW_PL和连线逐个路段绘制流量线）；
	 *              "2"-广东模式（基于里程碑点图层和t_tfm_currentflow_5m路况表绘制流量线）
	 *              "3"为为菏泽模式（城市道路网）改进型  "5"广东LCB_PT
	 * @return 接口实现类实例.
	 */
	public  static FlowLineEbi getFlowLineEbi(String flag){
		if(flag.equals("1")){
			return(new LineFromCSDLWEbo());		
		}else if(flag.equals("2")){
			return(new LineFromBMZEbo());	
		}else if(flag.equals("4")){
			return(new LineFromDatabase());	
		}else if(flag.equals("5")){
			return (new LineFromLCB());
		}else{
			return (new LineFromLCB());
		}
		
	}
}
