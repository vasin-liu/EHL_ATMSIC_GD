package com.ehl.dispatch.cdispatch.util;

public class CnWeek extends Week {
	private String sCnWeek[] = { "日", "一", "二", "三", "四", "五", "六" };

	public CnWeek() {
		super();
	}

	public CnWeek(int iWeek) {
		super(iWeek);
	}

	public String toString() {
		return "星期" + sCnWeek[this.iWeek];
	}
}
