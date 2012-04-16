package com.ehl.dispatch.cdispatch.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
/**
 * @======================================================================================================================================
 * @����˵��:ȡ�ϴ�������·��
 * @�����ߣ�dxn
 * @�������� 2010-05-31
 * @======================================================================================================================================
 */
public class Jspsmart {

	public static String getPath() {
		Properties props = new Properties();
		String path = null;
		try {
			props.load(Jspsmart.class.getResourceAsStream("/ehlhome/i18n/zh/Dispatch_zh.properties"));
			path = new String(props.getProperty("FileUpload.jsp.path").getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
