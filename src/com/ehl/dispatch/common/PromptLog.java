package com.ehl.dispatch.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ehl.sm.base.Constant;

public class PromptLog {
	private static Logger logger = Logger.getLogger(PromptLog.class);

	public static void debug(HttpServletRequest request, String atype,
			String[] ptypes, Object[] counts, String result) {
		HttpSession session = request.getSession();
		String jglx = (String) session.getAttribute(Constant.JGLX_VAR);
		if (jglx != null && jglx.equals("0")) {
			String uname = (String) session.getAttribute(Constant.UNAME_VAR);
			String jgmc = (String) session.getAttribute(Constant.JGMC_VAR);
			String ip = request.getRemoteAddr();
			String info = (ip + "," + uname + "," + jgmc + ":\n��ȡ��Ϣ��ʾ��Ϣ->" + atype);
			if (ptypes != null && counts != null) {
				for (int i = 0; i < ptypes.length && i < counts.length; i++) {
					info += "\n->" + ptypes[i] + ":" + counts[i];
				}
			}
			info += "\n->result:\n" + result;
			logger.debug(info);
		}
	}

	// private PromptLog self = new PromptLog();
	// public static void insert(HttpServletRequest request, String atype,
	// String[] ptypes, Object[] counts, String result) {
	// HttpSession session = request.getSession();
	// String jglx = (String) session.getAttribute(Constant.JGLX_VAR);
	// if (jglx != null && jglx.equals("0")) {
	// String ip = request.getRemoteHost();
	// String uname = (String) session.getAttribute(Constant.UNAME_VAR);
	// String jgid = (String) session.getAttribute(Constant.JGID_VAR);
	// String zbyxm = (String) session.getAttribute(Constant.ZBRXM_VAR);
	// Map<String, String> object = new HashMap<String, String>();
	// object.put("ip", ip);
	// object.put("uname", uname);
	// object.put("jgid", jgid);
	// object.put("pname", zbyxm);
	// object.put("atype", atype);
	//
	// }
	//
	// }
	//
	// /** ʵ������� */
	// private String ename = "��Ϣ������־";
	//
	// /** ���� */
	// private String tname = "T_SYS_PROMPTLOG";
	//
	// /** ����� */
	// private String otname = "plog";
	//
	// /** ���� */
	// private String[] cnames = {"id", "uname", "jgid", "pname", "time",
	// "atype",
	// "adtype", "reponse"};
	//
	// /** ������������ */
	// private String[] dcnames = {"time"};
	//
	// /** ������ */
	// private String sname = "SEQ_HELP_LEAVE";
	//
	// public PromptLog() {
	// super.setLogger(logger);
	// super.setEname(ename);
	// super.setTname(tname);
	// super.setOtname(otname);
	// super.setCnames(cnames);
	// super.setDcnames(dcnames);
	// super.setSname(sname);
	// }
	//
	// @Override
	// public String[] getFields() {
	// return null;
	// }
	//
	// @Override
	// public String getSelect() {
	// return null;
	// }

}
