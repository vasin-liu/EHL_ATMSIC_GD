package com.ehl.dynamicinfo.tgs.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dynamicinfo.tgs.core.HistoryFlowAvgCore;

public class tgsAllAction extends Controller {
	Logger logger = Logger.getLogger(tgsAllAction.class);
	
	private HistoryFlowAvgCore historyAvg = new HistoryFlowAvgCore();
	/**
	 * 当前时间是否在零点
	 * @return 是/否
	 */
	private static boolean isInZero(){
		String hour = new SimpleDateFormat("H").format(Calendar.getInstance().getTime());
		return hour.equals("0");
	}
	
	/**
	 * 获取流量sql语句
	 * @param kkmc 卡口名称
	 * @param delay 日期偏移
	 * @return 流量sql语句
	 */
	private static String getTfmSql(String kkmc, String delay){
		//筛选当日 1~23
		String siftToday = "trunc(c.datetime, 'dd') = trunc(sysdate + " + delay//to_date('2010-07-07','yyyy-mm-dd')
				+ ", 'dd')" + " and to_char(c.datetime, 'hh24') != '00'";
		//筛选明日0点
		delay = String.valueOf((Integer.parseInt(delay) + 1));
		String siftTomorrow = "trunc(c.datetime, 'dd') = trunc(sysdate + "
				+ delay + ", 'dd')" + " and to_char(c.datetime, 'hh24') = '00'";
		//筛选当日流量
		String siftDate = "((" + siftToday + ") or (" + siftTomorrow + "))";
		//流量sql语句
		StringBuffer sql = new StringBuffer();
		sql.append("select to_char(c.datetime, 'hh24') sj")
		.append(",decode(instr(b.bianma, 1, 1), '1', sum(c.volume), 0) rc")//入城
		.append(",decode(instr(b.bianma, 1, 1), '2', sum(c.volume), 0) cc")//出城
		.append(",sum(c.volume) sx")//双向
		.append(",decode(instr(b.bianma, 1, 1), '1',1,0) rcnum")//入城计数
		.append(",decode(instr(b.bianma, 1, 1), '2',1,0) ccnum");//出城计数
		sql.append(" from t_road_seginfo a, t_tfm_driveway b, t_tfm_driveway_1h_flow c");
		sql.append(" where a.roadsegid = b.roadsegid and b.id = c.id")
		.append(" and a.roadsegname like '%"+kkmc+"%'")
		.append(" and instr(b.bianma, 1, 1) in ('1', '2')")
		.append(" and "+siftDate);
		sql.append(" group by c.datetime, b.bianma");
		String temp = sql.toString();
		sql.setLength(0);
		sql.append("select sj, sum(rc), sum(cc), sum(sx),sum(rcnum),sum(ccnum)");
		sql.append(" from (" + temp + ")");
		sql.append(" group by sj");
		sql.append(" order by decode(sj,'00',1,2),sj desc");
		return sql.toString();
	}
	
	/**
	 * 获取卡口名称
	 * @param tgsName 卡口名称
	 * @return 卡口名称
	 */
	private static String getTgsName(String tgsName){
		String sql = "select roadsegname  from t_road_seginfo where roadsegname like '%"+tgsName+"%' ";
		Object[] data = DBHandler.getLineResult(sql);
		if(data == null || data.length != 1){
			return tgsName;
		}
		return String.valueOf(data[0]);
	}
	

	/**
	 * 获取流量数据
	 * 
	 * @param kkmc
	 *            卡口名称
	 * @param flag
	 *            标志
	 * @return 流量数据
	 */
	private static Object[][] getTfm(String kkmc,String flag) {
		if (kkmc == null || flag == null) {
			return null;
		}
		return DBHandler.getMultiResult(getTfmSql(kkmc, flag));
	}
	
	/**
	 * 计算当日总流量，以及出入流量有效小时数<br/>
	 * @param tfm
	 * @return 流量总计结果
	 */
	private static int[] getTotalTfm(Object[][] tfm) {
		int inTotalTfm = 0, outTotalTfm = 0;
		int inCount = 0, outCount = 0;
		for (int i = 0; i < tfm.length; i++) {
			inTotalTfm += Integer.valueOf(String.valueOf(tfm[i][1]));
			outTotalTfm += Integer.valueOf(String.valueOf(tfm[i][2]));
			inCount += Integer.valueOf(String.valueOf(tfm[i][4]));
			outCount += Integer.valueOf(String.valueOf(tfm[i][5]));
		}
		return new int[] { inTotalTfm, outTotalTfm, inCount, outCount };
	}
	
	/**
	 * 获取最大的流量值（出入城总流量）<br>
	 * 不考虑同时多个最大值的情况
	 * @param tfm 流量
	 * @return 小时、入城流量、出城流量、双向流量
	 * @author xiayx
	 */
	private static String[] getMaxTfm(Object[][] tfm){
		if(tfm == null){
			return null;
		}
		int totalTfm, maxInTfm = 0, maxOutTfm = 0, maxTotalTfm = 0;
		String date = "";
		for (int i = 0; i < tfm.length; i++) {
			totalTfm = Integer.parseInt(String.valueOf(tfm[i][3]));
			if(totalTfm >= maxTotalTfm){
				maxTotalTfm = totalTfm;
				maxInTfm = Integer.parseInt(String.valueOf(tfm[i][1]));;
				maxOutTfm = Integer.parseInt(String.valueOf(tfm[i][2]));
				date = (String)tfm[i][0];
			}
		}
		return new String[]{date,String.valueOf(maxInTfm),String.valueOf(maxOutTfm),String.valueOf(maxTotalTfm)};
	}

	/**
	 * 将小时数减一
	 * @param hour 小时
	 * @return 减一后的小时数
	 * @author xiayx
	 */
	private static String minusOneHour(String hour){
		int hourInt;
		try {
			hourInt = Integer.parseInt(hour);
		} catch (NumberFormatException e) {
			return hour;
		}
		if(hourInt < 0 || hourInt > 23){
			return hour;
		}
		hourInt--;
		if(hourInt == -1){
			hourInt = 23;
		}
		hour = String.valueOf(hourInt);
		if(hourInt < 10){
			hour = "0" + hour;
		}
		return hour;
	}
	
	/**
	 * 获取卡口信息<br/>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetTgsInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
	
		//获取传送过来的经纬度信息
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		String tgsName = StringHelper.obj2str(request.getParameter("tgsName"), "");
		String camID = StringHelper.obj2str(request.getParameter("GLID"), "");
		String ssxt = StringHelper.obj2str(request.getParameter("SSXT"), "");
		// "0" : 当日 "-1":前一日 "-2":前两日
		String searchDayFly = StringHelper.obj2str(request.getParameter("searchDayFly"), "0");
		if ("0".equals(searchDayFly)) {
			searchDayFly = "-0"; 
		}
		
		
		Calendar calendar = Calendar.getInstance();
		boolean isInZero = isInZero();
		if(isInZero){
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			searchDayFly = "-1";
		}
		String date = new SimpleDateFormat("yyyy年MM月dd日").format(calendar.getTime());
		
		// 生成显示信息
		StringBuffer sbXml = new StringBuffer();
		try {
			// 流量
			Object[][] tfm = getTfm(tgsName, searchDayFly);
			if (tfm != null) {
				//Modify by Xiayx 2011-9-30
				//监测时间改成监测日期
				sbXml.append("<table class='popup-contents' style='margin-top:10px ;' width='100%' cellSpacing='0' cellPadding='0' border='0'>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' width='100px'>卡口名称：</td><td width='200px' style='word-wrap: break-word; word-break: break-all;' >" + getTgsName(tgsName) + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>监测日期：</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>" + date + "</td></tr>");
				//Modification finished
				
				//Modify by Xiayx 2011-9-30
				//当前流量监测
				String hour = (String)tfm[0][0];
				sbXml.append("<tr height='10px;'><td colspan='2'></td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>当&nbsp;前&nbsp;流&nbsp;量&nbsp;监&nbsp;测：</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>入省方向 &nbsp; " + tfm[0][1] + "辆/小时" + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' valign='top' rowspan='2'>（"+minusOneHour(hour)+":00~"+hour+":00）</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>出省方向 &nbsp; " + tfm[0][2] + "辆/小时" + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>双 &nbsp; &nbsp; &nbsp;向 &nbsp; " + tfm[0][3]  + "辆/小时" + "</td></tr>");
				//Modification finished
				
				//Modify by Xiayx 2011-9-30
				//当日累计流量和平均流量
				int [] totalTfm = getTotalTfm(tfm);
				sbXml.append("<tr height='10px;'><td colspan='2'></td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>当日累计流量：</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>入省方向 &nbsp; " + totalTfm[0]  + "辆/日" + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' valign='top' rowspan='2'>（截至"+hour+":00）</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>出省方向 &nbsp; " + totalTfm[1]  + "辆/日" + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>双 &nbsp; &nbsp; &nbsp;向 &nbsp; " + (totalTfm[0]+totalTfm[1])  + "辆/日" + "</td></tr>");
				//每小时平均流量
				int inAvgTfm = totalTfm[2] == 0 ? 0 : totalTfm[0]/totalTfm[2];
				int outAvgTfm = totalTfm[3] == 0 ? 0 : totalTfm[1]/totalTfm[3];
				sbXml.append("<tr height='10px;'><td colspan='2'></td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' valign='top' rowspan='2'>当日每小时平均流量：</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>入省方向 &nbsp; " + inAvgTfm  + "辆/小时" + "</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>出省方向 &nbsp; " + outAvgTfm  + "辆/小时" + "</td></tr>");
				//当日最高峰流量
				String[] maxTfm = getMaxTfm(tfm);
				if(maxTfm != null){
					sbXml.append("<tr height='10px;'><td colspan='2'></td></tr>");
					sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>当日最高峰流量：</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>双 &nbsp; &nbsp; &nbsp;向 &nbsp; " + maxTfm[3]  + "辆/时" + "</td></tr>");
					sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' valign='top' rowspan='2'>（"+minusOneHour(maxTfm[0])+":00~"+maxTfm[0]+":00）</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>入省方向 &nbsp; " + maxTfm[1]  + "辆/时" + "</td></tr>");
					sbXml.append("<tr style='padding:1px 1px 1px 1px'><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>出省方向 &nbsp; " + maxTfm[2]  + "辆/时" + "</td></tr>");
				}
				//Modification finished
				
				//Modify by Xiayx 2011-12-21
				//添加历史平均值增长率
				String[] growths = historyAvg.getGrowth(tgsName, hour, Integer.parseInt(String.valueOf(tfm[0][1])), Integer.parseInt(String.valueOf(tfm[0][2])));
				sbXml.append("<tr height='10px;'><td colspan='2'></td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right'>对比历史平均值：</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>入省方向 &nbsp; " + growths[0]  + "（"+growths[3]+"）</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' valign='top'>（当前小时流量）</td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>出省方向 &nbsp; " + growths[1]  + "（"+growths[4]+"）</td></tr>");
				sbXml.append("<tr style='padding:1px 1px 1px 1px'><td align='right' valign='top'></td><td  width='200px' style='word-wrap: break-word; word-break: break-all;'>双 &nbsp; &nbsp; &nbsp;向 &nbsp; " + growths[2]  + "（"+growths[5]+"）</td></tr>");
				//Modify finished
				
				//Modify by Xiayx 2011-9-30
				//视频监视，加空行
				sbXml.append("<tr height='10px;'><td colspan='2'></td></tr>");
				//id='tdVidicon'会在js权限控制中使用到
				sbXml.append("<tr id='tdVidicon' style='padding:1px 1px 1px 1px'><td></td><td><input type='image' src='../../../cctv/image/button/btnvideoplay.gif' align='right' onClick=\"vidiconmap.showVideo('"+ camID+"','play','"+ ssxt +"');\"/></td></tr>");
				//Modification finished
				sbXml.append("<tr  style='padding:1px 1px 1px 1px'><td colspan='2'>&nbsp;</td></tr>");
				sbXml.append("</table>");
			}else{
				sbXml.append("<table class='popup-contents' style='margin-top:10px ;' width='100%' cellSpacing='0' cellPadding='0' border='0'>");
				//Modify by zhoucr 2011-12-26  过滤春运执勤点的流量信息
				if (!tgsName.substring(0,5).equals("春运执勤点")){
				    sbXml.append("<tr style='padding:5 0'><td></td><td >未取得该卡口流量信息！</td></tr>");
				}
				sbXml.append("<tr id='tdVidicon' style='padding:1px 1px 1px 1px'><td></td><td><input type='image' src='../../../cctv/image/button/btnvideoplay.gif' align='right' onClick=\"vidiconmap.showVideo('"+ camID+"','play','"+ ssxt +"');\"/></td></tr>");
				sbXml.append("</table>");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	
		PrintWriter out = response.getWriter();
	
		out.write(sbXml.toString());
		out.close();
		return null;
	}

	/**
	 * 获取全部卡口信息<br/>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAllTgsInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
	throws Throwable {
		
		//获取传送过来的经纬度信息
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();

		// 出城流量
		String sql1 = " SELECT SID, CPMC, LONGITUDE, LATITUDE,GLID,SSXT,SYZT FROM ATMS_EQUIPMENT_ZB " + 
						" WHERE SSXT = 'TGS' AND LONGITUDE != 0 AND LATITUDE != 0 ORDER BY SID ASC"; 
		// 取得进城流量
		Object[][] oResult2 = DBHandler.getMultiResult(sql1);
		String str = DataToXML.objArrayToXml(oResult2);
		out.write(str);
		out.close();
		return null;
	}
	
	/**
	 *将当前日期加减n天数。 如传入字符型"-5" 意为将当前日期减去5天的日期 
	 * 如传入字符型"5" 意为将当前日期加上5天后的日期 返回字串
	 * 例(1999-02-03) 
	 * @param to
	 * @return
	 */
	public static String dateAdd(String to) {
		// 日期处理模块 (将日期加上某些天或减去天数)返回字符串
		int strTo;
		try {
			strTo = Integer.parseInt(to);
		} catch (Exception e) {
			System.out.println("日期标识转换出错! : \n:::" + to + "不能转为数字型");
			e.printStackTrace();
			strTo = 0;
		}
		Calendar strDate = Calendar.getInstance(); // java.util包,设置当前时间
		strDate.add(Calendar.DATE, strTo); // 日期减 如果不够减会将月变动 //生成 (年-月-日)
		String meStrDate = strDate.get(Calendar.YEAR) + "-"
				+ String.valueOf(strDate.get(Calendar.MONTH) + 1) + "-"
				+ strDate.get(Calendar.DATE);
		return meStrDate;
	}

	public static void main(String[] args) {
		//System.out.println("dsd");
	}
}
