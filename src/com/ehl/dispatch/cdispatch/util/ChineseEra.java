package com.ehl.dispatch.cdispatch.util;

public class ChineseEra {
	int iYear;
	String[] sHeavenlyStems = { "¼×", "ÒÒ", "±û", "¶¡", "Îì", "¼º", "¸ı", "ĞÁ", "ÈÉ",
			"¹ï" };
	String[] sEarthlyBranches = { "×Ó", "³ó", "Òú", "Ã®", "³½", "ËÈ", "Îç", "Î´", "Éê",
			"ÓÏ", "Ğç", "º¥" };

	public ChineseEra() {
		int iYear = 1981;
	}

	public ChineseEra(int iYear) {
		if ((iYear < 2050) && (iYear > 1901))
			this.iYear = iYear;
		else
			this.iYear = 1981;
	}

	public String toString() {
		int temp;
		temp = Math.abs(iYear - 1924);
		return sHeavenlyStems[temp % 10] + sEarthlyBranches[temp % 12];
	}
}