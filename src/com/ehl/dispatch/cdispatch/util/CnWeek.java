package com.ehl.dispatch.cdispatch.util;

public class CnWeek extends Week {
	private String sCnWeek[] = { "��", "һ", "��", "��", "��", "��", "��" };

	public CnWeek() {
		super();
	}

	public CnWeek(int iWeek) {
		super(iWeek);
	}

	public String toString() {
		return "����" + sCnWeek[this.iWeek];
	}
}
