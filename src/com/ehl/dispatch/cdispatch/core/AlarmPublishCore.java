package com.ehl.dispatch.cdispatch.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ehl.dispatch.cdispatch.dao.AlarmPublishDao;
import com.ehl.dynamicinfo.policeRemind.dao.PoliceRemindDao;
import com.ehl.sm.base.Constant;
import com.ehl.util.Array;

public class AlarmPublishCore {
	
	private AlarmPublishDao dao = new AlarmPublishDao();
	
	/**
	 * 获取所有支队年初至今发生的警情次数
	 * @return 机构警情次数信息
	 */
	public String getAllZdAllAlarmCount(){
		Object[][] data = dao.getAllZdAllAlarmCount();
		if(data != null){
			for (int i = 0; i < data.length; i++) {
				data[i][0] = "\"" + data[i][0] + "\"";
				data[i][1] = "\"" + data[i][1] + "\"";
			}
		}
		String dataStr = Array.toString(data);
		if(dataStr.equals("null")){
			dataStr = "\"\"";
		}
//		dataStr = "<script>window.parent.allZdAllAlarmCount="+dataStr+"</script>";
//		StringBuffer js = new StringBuffer();
//		js.append("function encapJgmc(jgmc){if(jgmc){")
//		.append("return \"<a target='_black' href='javascript:void(0)' style='color:rgb(0,0,0);'>\"+jgmc+\"</a>\"")
//		.append("}return \"\";");
		return dataStr;
	}
	
	/**
	 * 获取一个支队及其辖区所有大队年初至今发生的不同类型的警情次数
	 * @param jgid 机构编号
	 * @return 机构警情次数
	 */
	public String getZdAllDdAlarmCount(String jgid){
		Object[][] data = dao.getZdAllDdAlarmCount(jgid);
		if(data != null){
			for (int i = 0; i < data.length; i++) {
				data[i][0] = "\"" + data[i][0] + "\"";
				data[i][1] = "\"" + data[i][1] + "\"";
			}
		}
		String dataStr = Array.toString(data);
		if(dataStr.equals("null")){
			dataStr = "\"\"";
		}
		return dataStr;
	}
	
	private PoliceRemindDao prdao = new PoliceRemindDao();
	/**
	 * 获取交警提示发布信息
	 * @return 交警提示发布信息
	 */
	public Object[][] getPoliceRemind(String ptype) {
		Object[][] data = prdao.getRollInfo(ptype);
		if(data != null){
			Pattern pattern = Pattern.compile("(.{4})-(.{2})-(.{2}) (.{2}):(.{2})");
			Matcher matcher;
			String content,time;
			for (int i = 0; i < data.length; i++) {
				time = Constant.nvl(data[i][1]);
				matcher = pattern.matcher(time);
				data[i][1] = toChineseFormat(matcher);
				content = Constant.nvl(data[i][4]).trim();
				data[i][4] = content;
			}
		}
		return data;
	}
	
	/**
	 * 转换成中文格式
	 * @param timeMatcher 
	 * @return
	 */
	private String toChineseFormat(Matcher timeMatcher){
		if(timeMatcher == null){
			return null;
		}
		StringBuffer timeStr = new StringBuffer();
		if(timeMatcher.find()){
			String[] timeUnits = {"年","月","日","时","分"};
			int glength = timeMatcher.groupCount();
			for (int i = 0; i < glength && i < timeUnits.length; i++) {
				timeStr.append(timeMatcher.group(i+1) + timeUnits[i]);
			}
		}
		return timeStr.toString();
	}
	
}
