package com.ehl.dispatch.cdispatch.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
/**
 * @======================================================================================================================================
 * @类型说明:取上传附件的路径
 * @创建者：dxn
 * @创建日期 2010-05-31
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
