package com.ehl.tira.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 辅助拼装sql语句
 * 
 * @author xiayx
 * @date 2010-12-19
 */
public class Sql {

	public final static String insert = "insert into ";
	public final static String select = "select ";
	public final static String update = "update ";
	public final static String delete = "delete from ";
	public final static String from = " from ";
	public final static String values = " values ";
	public final static String set = " set ";
	public final static String where = " where ";
	public final static String groupBy = " group by ";
	public final static String orderBy = " order by ";
	public final static String having = " having ";
	public final static String sepItem = ", ";// 非where条件，各项分隔符
	public final static String sepWhereItem = " and ";// where条件，各项分隔符
	public final static String sepChoice = "/";// 选择分隔符，并列多项选择一项的分隔符
	public final static String dateformat = "yyyy-mm-dd hh24:mi:ss";
	private final static String[] dfabbr = {"yyyy","mm","dd","hh24","mi","ss"};//"w","q"
	private final static String[] dffull = { "yyyy", "yyyy-mm",
			"yyyy-mm-dd", "yyyy-mm-dd hh24", "yyyy-mm-dd hh24:mi",
			"yyyy-mm-dd hh24:mi:ss"};
	
	public static String insert(String tname, String column, String value) {
		return insert + tname + "(" + column + ")" + values + "(" + value + ")";
	}

	public static String insert(String tname, String[] column, String[] value) {
		String columnStr = join(column, sepItem);
		String valueStr = join(value, sepItem);
		return insert(tname, columnStr, valueStr);
	}

	public static String insert(String tname, Map<String, String> kv) {
		String sql = null;
		if (kv != null && kv.size() > 0) {
			String columnStr = "";
			String valueStr = "";
			for (Map.Entry<String, String> entry : kv.entrySet()) {
				columnStr += sepItem + entry.getKey();
				valueStr += sepItem + entry.getValue();
			}
			columnStr = columnStr.substring(sepItem.length());
			valueStr = valueStr.substring(sepItem.length());
			sql = insert(tname, columnStr, valueStr);
		}
		return sql;
	}

	public static String select(String tname, String column) {
		return select + column + from + tname;
	}

	public static String select(String tname, String column, String whereStr) {
		whereStr = (whereStr == null || "".equals(whereStr.trim())) ? ""
				: where + whereStr;
		return select(tname, column) + whereStr;
	}

	public static String select(String tname, String column, String whereStr,
			String groupby) {
		groupby = (groupby == null || "".equals(groupby.trim())) ? "" : groupBy
				+ groupby;
		return select(tname, column, whereStr) + groupby;
	}

	public static String select(String tname, String column, String whereStr,
			String groupby, String orderby) {
		orderby = (orderby == null || "".equals(orderby.trim())) ? "" : orderBy
				+ orderby;
		return select(tname, column, whereStr, groupby) + orderby;
	}

	public static String update(String tname, String setStr) {
		return update + tname + set + setStr;
	}
	
	public static String update(String tname, String setStr, String whereStr) {
		whereStr = (whereStr == null || "".equals(whereStr)) ? "" : where
				+ whereStr;
		return update(tname, setStr) + whereStr;
	}
	
	public static String update(String tname, Map<String, String> kv,
			String whereStr) {
		return update(tname, join(kv, sepItem), whereStr);
	}

	public static String delete(String tname) {
		return delete + tname;
	}

	public static String delete(String tname, String whereStr) {
		whereStr = (whereStr == null || "".equals(whereStr.trim())) ? ""
				: where + whereStr;
		return delete(tname) + whereStr;
	}

	public static String toString(String col) {
		return "'" + col + "'";
	}

	public static String getWhereDate(String col, String dateStart,
			String dateEnd, int dateType) {
		String whereDate = "";
		col = toChar(col, getDFFull(dateType));
		if (!"".equals(dateStart)) {
			whereDate += sepWhereItem + col + " >= '" + dateStart + "'";
		}
		if (!"".equals(dateEnd)) {
			whereDate += sepWhereItem + col + " <= '" + dateEnd + "'";
		}
		if(!"".equals(whereDate)){
			whereDate = whereDate.substring(sepWhereItem.length());
		}
		return whereDate;
	}
	
	public static String toIn(String[] values){
		String in = null;
		if(values != null){
			in = "";
			int vlength = values.length;
			for (int i = 0; i < vlength; i++) {
				in += sepItem + "'" + values[i] + "'";
			}
			if (!in.equals("")) {
				in = in.substring(sepItem.length());
			}
			in = "(" + in + ")";
		}
		return in;
	}
	
	public static String toIn(String values){
		String in = null;
		if(values != null){
			values = values.replace(",", "','");
			in = "('" + values + "')";
		}
		return in;
	}
	
	public static String whereIn(String cname, String value){
		String whereIn = null;
		if(cname != null && value != null){
			whereIn = cname + " in " + value;
		}
		return whereIn;
	}
	
	/**
	 * @deprecated 请使用ehl/util/Array
	 */
	public static String join(String[] strs, String sep) {
		String joinStr = null;
		if (strs != null && strs.length > 0 && sep != null) {
			joinStr = "";
			for (int i = 0; i < strs.length; i++) {
				joinStr += sep + strs[i];
			}
			joinStr = joinStr.substring(sep.length());
		}
		return joinStr;
	}
	
	//set,where
	public static String join(Map<String,String> kv, String sep){
		String str = null;
		if (kv != null && kv.size() > 0) {
			str = "";
			for (Map.Entry<String, String> entry : kv.entrySet()) {
				str += sep + entry.getKey() + " = " + entry.getValue();
			}
			if(!str.equals("")){
				str = str.substring(sep.length());
			}
		}
		return str;
	}

	public static Map<String, String> arrayToMap(String[] keys, String[] values) {
		Map<String, String> map = null;
		if (keys != null && values != null) {
			map = new HashMap<String, String>();
			for (int i = 0; i < keys.length && i < values.length; i++) {
				map.put(keys[i], values[i]);
			}
		}
		return map;
	}
	
	public static long dateDiffer(String timeS, String timeE, String df) {
		long quot = 0;
		SimpleDateFormat ft = new SimpleDateFormat(df);
		try {
			Date dateS = ft.parse(timeS);
			Date dateE = ft.parse(timeE);
			quot = dateE.getTime() - dateS.getTime();
			quot = quot / 1000 / 60 / 60 / 24;
		} catch (ParseException e) {
			quot = -1;
		}
		return quot;
	}
	
	public static String toDate(String date, String format) {
		return " to_date('" + date + "','" + format + "')";
	}
	
	public static String toDate(String time) {
		String date = null;
		date = toDate(time, getDFFull(getDFType(time)));
		return date;
	}
	
	public static String toChar(String col, String format) {
		return " to_char(" + col + ",'" + format + "')";
	}
	
	public static String toChar(String col, int dateType){
		return toChar(col, getDFFull(dateType));
	}
	
	public static String getDFAbbr(int dateType){
		String shor = null;
		if(dateType >= 0 && dateType < dfabbr.length){
			shor = dfabbr[dateType];
		}
		return shor;
	}

	public static String getDFFull(int dateType){
		String full = null;
		if(dateType >= 0 && dateType < dffull.length){
			full = dffull[dateType];
		}
		return full;
	}
	
	public static int getDFType(String time) {
		int type = -1;
		if (time != null) {
			//单循环分段，循环范围，影响因素
			int[] sections = { 0, 3, dffull.length};//0~3->0;3~6->2
			int[] effects = { 0, 2 };
			for (int i = 0; i < sections.length - 1; i++) {
				for (int j = sections[i]; j < sections[i + 1]; j++) {
					if (time.length() + effects[i] == dffull[j].length()) {
						type = j;
						break;
					}
				}
			}
		}
		return type;
	}
	
	public static String[] toDateArray(String time) {
		String[] dates = null;
		
		if(time != null && time.length()>= dffull[0].length()){
			
			int[] sizes = new int[dffull.length+1];
			sizes[0] = -1;
			for (int i = 0; i < 3; i++) {
				sizes[i+1] = dffull[i].length();
			}
			for (int i = 3; i < dffull.length; i++) {
				sizes[i+1] = dffull[i].length()-2;
			}
			
			int type = 0;
			for (int i = 1; i < sizes.length; i++) {
				if(sizes[i] == time.length()){
					type = i;
					break;
				}
			}
			
			dates = new String[type];
			for (int i = 0; i < dates.length; i++) {
				dates[i] = time.substring(sizes[i]+1,sizes[i+1]);
			}
		}
		return dates;
	}
	
	
	public static String getDFAbbr(String dateS, String dateE){
		String shor = null;
		if (dateS != null && dateE != null) {
			String[] ds = toDateArray(dateS);
			String[] de = toDateArray(dateE);
			if(ds != null && de != null){
				if(dateS.equals(dateE)){
					shor = getDFAbbr(ds.length);
				}else{
					for (int i = 0; i < ds.length && i < de.length; i++) {
						if(!ds[i].equals(de[i])){
							shor = getDFAbbr(i);
							break;
						}
					}
					if(shor == null){
						shor = getDFAbbr(ds.length);
					}
				}
			}
		}
		return shor;
	}
	
	public static String toString(String[] array){
		String arrayStr = null;
		if(array != null){
			arrayStr = "";
			if (array.length > 0) {
				String sep = ", ";
				for (int i = 0; i < array.length; i++) {
					arrayStr += sep + array[i];  
				}
				arrayStr = arrayStr.substring(sep.length());
				arrayStr = "[" + arrayStr + "]";
			}
		}
		return arrayStr;
	}
	
//	追加
//---------------------------------------
	
	
	
	public static String appendSift(String sift1, String sift2){
		return sift1 + Sql.sepWhereItem + sift2;
	}
	
	/**
	 * <pre>
	 * surround with single quotes
	 * 左右两侧加上单引号
	 * </pre>
	 * @param value 值
	 * @return 加上单引号后的值
	 */
	public static String swsq(String value) {
		return "'" + value + "'";
	}
	
	public static String[] swsqs(String[] values){
		if(values != null){
			int vlength = values.length;
			for (int i = 0; i < vlength; i++) {
				values[i] = swsq(values[i]);
			}
		}
		return values;
	}
	
	/**
	 * <pre>
	 * sift equals
	 * 筛选等于
	 * </pre>
	 * @param cname
	 * @param cvalue
	 * @return
	 */
	public static String se(String cname, String cvalue) {
		return cname + "=" + cvalue;
	}
	
	/**
	 * <pre>
	 * sift equals with single quotes
	 * 筛选等于，值左右两侧加上单引号
	 * </pre>
	 * @param cname
	 * @param cvalue
	 * @return
	 */
	public static String sewsq(String cname, String cvalue) {
		return se(cname, swsq(cvalue));
	}
	
	
	public static String ses(String[] cnames, String[] cvalues){
		String ses = null;
		if(cnames != null && cvalues != null){
			ses = "";
			int nlength = cnames.length;
			int vlength = cvalues.length;
			for (int i = 0; i < nlength && i < vlength; i++) {
				ses += Sql.sepWhereItem + se(cnames[i],cvalues[i]);
			}
			if (!ses.equals("")) {
				ses = ses.substring(Sql.sepWhereItem.length());
			}
		}
		return ses;
	}
	
	public static String sewsqs(String[] cnames, String[] cvalues){
		return ses(cnames,swsqs(cvalues));
	}
	
	/**
	 * 筛选一个或多个值
	 * @param name 列名
	 * @param id 主键值
	 * @return 筛选语句
	 */
	public static String siftMulti(String name, String id){
		String siftId;
		if (id.indexOf(",") == -1) {
			siftId = name + "='" + id + "'";
		} else {
			id = "'" + id.replaceAll(",", "','") + "'";
			siftId = name + " in (" + id + ")";
		}
		return siftId;
	}
	
	/**
	 * 筛选主键编号
	 * @param id 主键值
	 * @return 筛选语句
	 */
	public static String siftId(String id){
		return siftMulti("id", id);
	}

	public static String siftDate(String name, String date) {
		String siftDate = "";
		if (date.indexOf(",") == -1) {
			siftDate = toChar(name, getDFType(date)) + "='" + date + "'";
		} else {
			String[] dates = date.split(",");
			String sdate = "";
			String edate = "";
			if(dates.length >= 1){
				sdate = dates[0];
			}
			if(dates.length >= 2){
				edate = dates[1];
			}
			siftDate = getWhereDate(name, sdate, edate,getDFType(sdate));
		}
		return siftDate;
	}
	
	public static void main(String[] args) {
		System.out.println(siftDate("name", ","));
	}

}
