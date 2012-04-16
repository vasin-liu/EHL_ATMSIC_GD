package com.ehl.dispatch.cdispatch.util;

//自定义日期类
public class MyDate {
	public int iYear;
	public int iMonth;
	public int iDay;

	private static int checkYear(int iYear) {
		if ((iYear > 1901) && (iYear < 2050))
			return iYear;
		else {
			System.out.println("The Year out of range, I think you want 1981");
			return 1981;
		}
	}

	public MyDate(int iYear, int iMonth, int iDay) {
		this.iYear = checkYear(iYear);
		this.iMonth = iMonth;
		this.iDay = iDay;
	}

	public MyDate(int iYear, int iMonth) {
		this.iYear = checkYear(iYear);
		this.iMonth = iMonth;
		this.iDay = 1;
	}

	public MyDate(int iYear) {
		this.iYear = checkYear(iYear);
		this.iMonth = 1;
		this.iDay = 1;
	}

	public MyDate() {
		this.iYear = 1981;
		this.iMonth = 1;
		this.iDay = 1;
	}

	public String toString() {
		return "" + this.iYear
				+ (this.iMonth > 9 ? "" + this.iMonth : "0" + this.iMonth)
				+ (this.iDay > 9 ? "" + this.iDay : "0" + this.iDay);
	}

	public boolean equals(MyDate md) {
		return ((md.iDay == this.iDay) && (md.iMonth == this.iMonth) && (md.iYear == this.iYear));
	}
}