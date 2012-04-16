/**
 * 
 */
package com.ehl.dispatch.cdispatch.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.cdispatch.dao.ComplaintManageDao;

/**
 * 投诉管理控制类
 * @author wkz
 * @date 2010-4-27
 *
 */
public class ComplaintManageAction extends Controller{
	/**
	 * 编辑投诉信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModifyComplaintInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		int insertOrUpdate = StringHelper.obj2int(request.getParameter("insrtOrUpdt"), 0);
		
		String CPID = StringHelper.obj2str(request.getParameter("CPID"),"");
		String TSDJSJ = StringHelper.obj2str(request.getParameter("TSDJSJ"),"");
		String DJRID = StringHelper.obj2str(request.getParameter("DJRID"),"");
		String DJJGID = StringHelper.obj2str(request.getParameter("DJJGID"),"");
		String TSRXM = StringHelper.obj2str(request.getParameter("TSRXM"),"");
		
		String TSRDH = StringHelper.obj2str(request.getParameter("TSRDH"),"");
		String TSRJZ = StringHelper.obj2str(request.getParameter("TSRJZ"),"");
		String TSRCP = StringHelper.obj2str(request.getParameter("TSRCP"),"");
		String TSYWFLB = StringHelper.obj2str(request.getParameter("TSYWFLB"),"");
		String TSYWZLB = StringHelper.obj2str(request.getParameter("TSYWZLB"),"");
		
		String TSNR = StringHelper.obj2str(request.getParameter("TSNR"),"");
		String TSJG = StringHelper.obj2str(request.getParameter("TSJG"),"");
		String TSJH = StringHelper.obj2str(request.getParameter("TSJH"),"");
		String ZHKJSR = StringHelper.obj2str(request.getParameter("ZHKJSR"),"");
		String ZHKLD = StringHelper.obj2str(request.getParameter("ZHKLD"),"");
		
		String ZHKYJ = StringHelper.obj2str(request.getParameter("ZHKYJ"),"");
		String ZHCJSR = StringHelper.obj2str(request.getParameter("ZHCJSR"),"");
		String ZHCLD = StringHelper.obj2str(request.getParameter("ZHCLD"),"");
		String ZHCYJ = StringHelper.obj2str(request.getParameter("ZHCYJ"),"");
		String JLDJSR = StringHelper.obj2str(request.getParameter("JLDJSR"),"");
		
		String JLD = StringHelper.obj2str(request.getParameter("JLD"),"");
		String JLDYJ = StringHelper.obj2str(request.getParameter("JLDYJ"),"");
		String YWCZGBM = StringHelper.obj2str(request.getParameter("YWCZGBM"),"");
		String YWCJSR = StringHelper.obj2str(request.getParameter("YWCJSR"),"");
		String YWCZG = StringHelper.obj2str(request.getParameter("YWCZG"),"");
		
		String YWCYJ = StringHelper.obj2str(request.getParameter("YWCYJ"),"");
		String YWCCBR = StringHelper.obj2str(request.getParameter("YWCCBR"),"");
		String ZDBLYJ = StringHelper.obj2str(request.getParameter("ZDBLYJ"),"");
		String ZDJGID = StringHelper.obj2str(request.getParameter("ZDJGID"),"");
		String ZDLD = StringHelper.obj2str(request.getParameter("ZDLD"),"");
		
		String ZDYJ = StringHelper.obj2str(request.getParameter("ZDYJ"),"");
		String DDJGID = StringHelper.obj2str(request.getParameter("DDJGID"),"");
		String BLJGSM = StringHelper.obj2str(request.getParameter("BLJGSM"),"");
		String ISREPLYMASSES = StringHelper.obj2str(request.getParameter("ISREPLYMASSES"),"");
		String JBDW = StringHelper.obj2str(request.getParameter("JBDW"),"");
		
		String JBLXR = StringHelper.obj2str(request.getParameter("JBLXR"),"");
		String JBLXRDH = StringHelper.obj2str(request.getParameter("JBLXRDH"),"");
		String SPR = StringHelper.obj2str(request.getParameter("SPR"),"");
		String SPRQ = StringHelper.obj2str(request.getParameter("SPRQ"),"");
		String XGR = StringHelper.obj2str(request.getParameter("XGR"),"");
		
		String XGSJ = StringHelper.obj2str(request.getParameter("XGSJ"),"");
		String LZZT = StringHelper.obj2str(request.getParameter("LZZT"),"");
		
		String RYID = StringHelper.obj2str(request.getParameter("RYID"),"");
		String JGID = StringHelper.obj2str(request.getParameter("JGID"),"");
		String OLD_LZZT = StringHelper.obj2str(request.getParameter("OLD_LZZT"),"");
		String LXDH = StringHelper.obj2str(request.getParameter("LXDH"),"");
		
		HashMap hm = new HashMap();
		hm.put("CPID", CPID);
		hm.put("TSDJSJ", TSDJSJ);
		hm.put("DJRID", DJRID);
		hm.put("DJJGID", DJJGID);
		hm.put("TSRXM", TSRXM);
		
		hm.put("TSRDH", TSRDH);
		hm.put("TSRJZ", TSRJZ);
		hm.put("TSRCP", TSRCP);
		hm.put("TSYWFLB", TSYWFLB);
		hm.put("TSYWZLB", TSYWZLB);
		
		hm.put("TSNR", TSNR);
		hm.put("TSJG", TSJG);
		hm.put("TSJH", TSJH);
		hm.put("ZHKJSR", ZHKJSR);
		hm.put("ZHKLD", ZHKLD);
		
		hm.put("ZHKYJ", ZHKYJ);
		hm.put("ZHCJSR", ZHCJSR);
		hm.put("ZHCLD", ZHCLD);
		hm.put("ZHCYJ", ZHCYJ);
		hm.put("JLDJSR", JLDJSR);
		
		hm.put("JLD", JLD);
		hm.put("JLDYJ", JLDYJ);
		hm.put("YWCZGBM", YWCZGBM);
		hm.put("YWCJSR", YWCJSR);
		hm.put("YWCZG", YWCZG);
		
		hm.put("YWCYJ", YWCYJ);
		hm.put("YWCCBR", YWCCBR);
		hm.put("ZDBLYJ", ZDBLYJ);
		hm.put("ZDJGID", ZDJGID);
		hm.put("ZDLD", ZDLD);
		
		hm.put("ZDYJ", ZDYJ);
		hm.put("DDJGID", DDJGID);
		hm.put("BLJGSM", BLJGSM);
		hm.put("ISREPLYMASSES", ISREPLYMASSES);
		hm.put("JBDW", JBDW);
		
		hm.put("JBLXR", JBLXR);
		hm.put("JBLXRDH", JBLXRDH);
		hm.put("SPR", SPR);
		hm.put("SPRQ", SPRQ);
		hm.put("XGR", XGR);
		
		hm.put("XGSJ", XGSJ);
		hm.put("LZZT", LZZT);
		
		hm.put("RYID", RYID);
		hm.put("JGID", JGID);
		hm.put("OLD_LZZT", OLD_LZZT);
		hm.put("LXDH", LXDH);
		ComplaintManageDao cmd = new ComplaintManageDao();
		boolean flag = false;
		if(insertOrUpdate==0 || (insertOrUpdate==4 && OLD_LZZT.equals("000000"))) {
			flag = cmd.addComplaintInfo(hm);
			if(flag) {
				out.write("新增投诉信息成功！");
			}else {
				out.write("新增投诉信息失败！");
			}
		} else {
			
			flag = cmd.editComplaintInfo(hm);
			if(flag) {
				out.write("操作成功！");
			}else {
				out.write("失败！");
			}
		}
		out.close();
		return null;
	} 
	/**
	 * 删除道路信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doDeleteComplaintInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String CPID = StringHelper.obj2str(request.getParameter("CPID"),"");
		ComplaintManageDao cmd = new ComplaintManageDao();
		boolean flag = cmd.delteComplaintInfo(CPID);
		if(flag) {
			out.write("success");
		}else {
			out.write("fail");
		}
		out.close();
		return null;
	}
	
	/**
	 * 查询道路信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetComplaintInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String CPID = StringHelper.obj2str(request.getParameter("CPID"),"");
		ComplaintManageDao cmd = new ComplaintManageDao();
		Object[] obj = cmd.getComplaintInfo(CPID);
		String res = DataToXML.objArrayToXml(obj);
		out.write(res);
		out.close();
		return null;
	}
	
	public ActionForward doShowWord(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String ID = StringHelper.obj2str(request.getParameter("ID"),"");
		ComplaintManageDao cmd = new ComplaintManageDao();
		String result = cmd.showWord(ID);
		out.write(result);
		out.close();
		return null;
	}
	
	public ActionForward doInsertWord(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		String NAME = StringHelper.obj2str(request.getParameter("NAME"),"");
		ComplaintManageDao cmd = new ComplaintManageDao();
		String result = cmd.insertWord(NAME);
		out.write(result);
		out.close();
		return null;
	}
	
	/**
	 * 取得会话信息<br>
	 * 会话信息取得处理
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetTrafficNotion(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		String searchId = StringHelper
				.obj2str(request.getParameter("YWID"), "");

		// 初期化Dao
		ComplaintManageDao complaintManageDao = new ComplaintManageDao();
		
		// 业务会话信息取得的呼出
		Object[][] oaProcessResult = complaintManageDao
				.getToaProcessInfo(searchId);

		StringBuffer xmlData = new StringBuffer(
				"<?xml version='1.0' encoding='UTF-8'?>\n");
		xmlData.append("<rfXML>\n");
		xmlData.append("<RFWin>\n");
		// 数据项顺序：业务类型，信息发送人，发送时间，发送文本，所属机构，职位，发送类别
		for (int i = 0; i < oaProcessResult.length; i++) {
			xmlData.append("<row id='" + i + "'>");
			for (int j = 0; j < oaProcessResult[i].length; j++) {
				xmlData.append("<col>"
						+ StringHelper.obj2str(oaProcessResult[i][j], "")
						+ "</col>");
			}
			xmlData.append("</row>\n");
		}
		xmlData.append("</RFWin>\n");
		xmlData.append("</rfXML>\n");

		// 检索结果写入out对象
		out.write(xmlData.toString());
		out.close();

		return null;
	}
	
//	 杜（追加）

	/**
	 * 投诉多附件文件上传<br>
	 * 投诉多个附件上传的实现
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doSaveComplaintInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		final int size = 20 * 1024 * 1024;
		String realPath = request.getSession().getServletContext().getRealPath(
				"/");
		realPath = realPath.substring(0, realPath.length() - 1);
		int pathIndex = realPath.lastIndexOf("\\");
		realPath = realPath.substring(0, pathIndex + 1);
		String encoding = request.getCharacterEncoding();


		String uploadPath = realPath + "EHL_Upload\\";
		String tempPath = realPath + "EHL_Upload\\temp\\";

		if (encoding == null || encoding == "") {
			encoding = "UTF-8";
		}

		String databasePath = "EHL_Upload/";
		String databaseFileName = null;
		// 投诉编号
		String complaintId = "";
		String strRerutn = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head>";
		strRerutn += "<body onload='window.close();'></body></html>";
		request.setCharacterEncoding(encoding);

		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(10240);
			File tempFile = new File(tempPath);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			factory.setRepository(tempFile);

			FileUpload upload = new FileUpload(factory);
			upload.setSizeMax(size);
			List list = null;
			try {
				upload.setHeaderEncoding("utf-8");
				list = upload.parseRequest(request);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("文件上传失败");
				out.write(strRerutn);
				out.close();
				return null;
			}

			Iterator ite = list.iterator();

			// 附件描述
			String [] fileModfiy = new String[10];
			int inde = 0;
			// 初期化Dao
			ComplaintManageDao complaintManageDao = new ComplaintManageDao();
			int fileCount = 0;

			try {
				// 依次处理每一个文件：
				while (ite.hasNext()) {

					FileItem fi = (FileItem) ite.next();
					if (fi == null) {
						continue;
					}
					if (fi.isFormField()) {
						if (fi.getFieldName().equals("fileInfo" + fileCount)) {
							fileModfiy[fileCount] = fi.getString(encoding);
							fileCount = fileCount + 1;
						} else if (fi.getFieldName().equals(
								"insertYwid")) {
							complaintId = fi.getString(encoding);
						}
					} else if (!fi.isFormField()) {

						// 取得文件名
						String fileName = fi.getName();
						// 获得文件名，这个文件名包括路径：
						if (fileName.length() != 0 && fileName != null
								&& fileName != "") {
							int index;
							index = fileName.lastIndexOf("\\");
							fileName = fileName.substring(index + 1,
									fileName.length());

							databaseFileName = databasePath + fileName;
							// 在这里可以记录用户和文件信息
							// ...
							// 写入文件，暂定文件名为a.txt，可以从fileName中提取文件名：
							String newFileName = uploadPath + fileName;
							// 判断是否有相同文件名文件存在
							if (new File(newFileName).exists()
									&& new File(newFileName).isFile()) {
								double doubleRandom = Math.random()*100000;
								databaseFileName = databasePath + doubleRandom + "/" + fileName;
								uploadPath = uploadPath + doubleRandom + "\\";
								newFileName = uploadPath + fileName;
								File file = new File(uploadPath);
								// 判断上传文件路径是否存在
								if (!file.exists()) {
									file.mkdirs();
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									complaintManageDao
											.insertTComplaintAttachment(
													complaintId, "",
													fileModfiy[inde],
													databaseFileName,
													"警情投诉" );
									inde = inde + 1;
								} else {
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									complaintManageDao
											.insertTComplaintAttachment(
													complaintId, "",
													fileModfiy[inde],
													databaseFileName,
													"警情投诉" );
									inde = inde + 1;
								}
							} else {
								File file = new File(uploadPath);
								// 判断上传文件路径是否存在
								if (!file.exists()) {
									file.mkdirs();
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									complaintManageDao
											.insertTComplaintAttachment(
													complaintId, "",
													fileModfiy[inde],
													databaseFileName,
													"警情投诉" );
									inde = inde + 1;
								} else {
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									complaintManageDao
											.insertTComplaintAttachment(
													complaintId, "",
													fileModfiy[inde],
													databaseFileName,
													"警情投诉" );
									inde = inde + 1;
								}
							}
						}
					}
				}
			} catch (Exception e) {
				System.out.println("文件上传失败");
				out.write(strRerutn);
				out.close();
				return null;
			}
		}
		out.write(strRerutn);
		out.close();
		return null;
	}
	
	/**
	 * 支队文件上传<br>
	 * 支队文件上传的实现
	 * 
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doSaveZdFileInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		final int size = 20 * 1024 * 1024;
		String realPath = request.getSession().getServletContext().getRealPath(
				"/");
		realPath = realPath.substring(0, realPath.length() - 1);
		int pathIndex = realPath.lastIndexOf("\\");
		realPath = realPath.substring(0, pathIndex + 1);
		String encoding = request.getCharacterEncoding();


		String uploadPath = realPath + "EHL_Upload\\";
		String tempPath = realPath + "EHL_Upload\\temp\\";

		if (encoding == null || encoding == "") {
			encoding = "UTF-8";
		}

		String databasePath = "EHL_Upload/";
		String databaseFileName = null;
		// 投诉编号
		String complaintId = "";
		String strRerutn = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=utf-8'/></head>";
		strRerutn += "<body onload='window.close();'></body></html>";

		request.setCharacterEncoding(encoding);

		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(10240);
			File tempFile = new File(tempPath);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			factory.setRepository(tempFile);

			FileUpload upload = new FileUpload(factory);
			upload.setSizeMax(size);
			List list = null;
			try {
				upload.setHeaderEncoding("utf-8");
				list = upload.parseRequest(request);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("文件上传失败");
				out.write(strRerutn);
				out.close();
				return null;
			}

			Iterator ite = list.iterator();

			// 附件描述
			String [] fileModfiy = new String[10];
			int inde = 0;
			// 初期化Dao
			ComplaintManageDao complaintManageDao = new ComplaintManageDao();
			int fileCount = 0;

			try {
				// 依次处理每一个文件：
				while (ite.hasNext()) {

					FileItem fi = (FileItem) ite.next();
					if (fi == null) {
						continue;
					}
					if (fi.isFormField()) {
						if (fi.getFieldName().equals("fileInfo" + fileCount)) {
							fileModfiy[fileCount] = fi.getString(encoding);
							fileCount = fileCount + 1;
						} else if (fi.getFieldName().equals(
								"insertYwid")) {
							complaintId = fi.getString(encoding);
						}
					} else if (!fi.isFormField()) {

						// 取得文件名
						String fileName = fi.getName();
						// 获得文件名，这个文件名包括路径：
						if (fileName.length() != 0 && fileName != null
								&& fileName != "") {
							int index;
							index = fileName.lastIndexOf("\\");
							fileName = fileName.substring(index + 1,
									fileName.length());

							databaseFileName = databasePath + fileName;
							// 在这里可以记录用户和文件信息
							// ...
							// 写入文件，暂定文件名为a.txt，可以从fileName中提取文件名：
							String newFileName = uploadPath + fileName;
							// 判断是否有相同文件名文件存在
							if (new File(newFileName).exists()
									&& new File(newFileName).isFile()) {
								double doubleRandom = Math.random()*100000;
								databaseFileName = databasePath + doubleRandom + "/" + fileName;
								uploadPath = uploadPath + doubleRandom + "\\";
								newFileName = uploadPath + fileName;
								File file = new File(uploadPath);
								// 判断上传文件路径是否存在
								if (!file.exists()) {
									file.mkdirs();
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									complaintManageDao
											.insertTComplaintAttachment(
													complaintId, "",
													fileModfiy[inde],
													databaseFileName,
													"保存支队办理结果附件" );
									inde = inde + 1;
								} else {
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									complaintManageDao
											.insertTComplaintAttachment(
													complaintId, "",
													fileModfiy[inde],
													databaseFileName,
													"保存支队办理结果附件" );
									inde = inde + 1;
								}
							} else {
								File file = new File(uploadPath);
								// 判断上传文件路径是否存在
								if (!file.exists()) {
									file.mkdirs();
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									complaintManageDao
											.insertTComplaintAttachment(
													complaintId, "",
													"",
													databaseFileName,
													"保存支队办理结果附件" );
									inde = inde + 1;
								} else {
									// 作成文件
									fi.write(new File(newFileName));
									// 上传附件信息的登录
									complaintManageDao
											.insertTComplaintAttachment(
													complaintId, "",
													"",
													databaseFileName,
													"保存支队办理结果附件" );
									inde = inde + 1;
								}
							}
						}
					}
				}
			} catch (Exception e) {
				System.out.println("文件上传失败");
				out.write(strRerutn);
				out.close();
				return null;
			}
		}
		out.write(strRerutn);
		out.close();
		return null;
	}
	
	/**
	 *显示附件信息<br> 
	 *显示附件信息
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetAttachmentInfo(Action action,
		HttpServletRequest request, HttpServletResponse response)
		throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
	
		String complaintId = request.getParameter("complaintId");
		// 取得附件信息的SQL
		String searchSql = "select FJWZ, FJMS ,FJID,YWLX from T_OA_ATTACHMENT where YWID = '" + complaintId + "'";
		Object[][] objList = DBHandler.getMultiResult(searchSql);
		// 检索结果xml方式数据的存放
		if (objList != null) {
			StringBuffer xmlData = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>\n");
			xmlData.append("<rfXML>\n");
			xmlData.append("<RFWin>\n");
			// 数据项顺序：附件路径 附件描述 附件id 业务类型
			for (int i = 0; i < objList.length; i++) {
				xmlData.append("<row id='" + i + "'>");
				for (int j = 0; j < objList[i].length; j++) {
					xmlData.append("<col>"
							+ StringHelper.obj2str(objList[i][j], "")
							+ "</col>");
				}
				xmlData.append("</row>\n");
			}
			xmlData.append("</RFWin>\n");
			xmlData.append("</rfXML>\n");
			// 检索结果写入out对象
			out.write(xmlData.toString());
		}
		return null;
	}
	
	public ActionForward doStatInfo(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String alarmTotalType = StringHelper.obj2str(request.getParameter("alarmTotalType"),"");
		String timeRadioType = StringHelper.obj2str(request.getParameter("timeRadioType"),"");
		String STARTTIME = StringHelper.obj2str(request.getParameter("STARTTIME"),"");
		String ENDTIME = StringHelper.obj2str(request.getParameter("ENDTIME"),"");
		String startyear = StringHelper.obj2str(request.getParameter("startyear"),"");
		String startmonth = StringHelper.obj2str(request.getParameter("startmonth"),"");
		String endyear = StringHelper.obj2str(request.getParameter("endyear"),"");
		String endmonth = StringHelper.obj2str(request.getParameter("endmonth"),"");
		String yearSelect = StringHelper.obj2str(request.getParameter("yearSelect"),"");
		String monthSelect = StringHelper.obj2str(request.getParameter("monthSelect"),"");
		String TSJGID = StringHelper.obj2str(request.getParameter("TSJGID"),"");
		String jgbh = StringHelper.obj2str(request.getParameter("jgbh"),"");
		String departType = StringHelper.obj2str(request.getParameter("departType"),"");
		
		HashMap hm = new HashMap();
		hm.put("alarmTotalType", alarmTotalType);
		hm.put("timeRadioType", timeRadioType);
		hm.put("STARTTIME", STARTTIME);
		hm.put("ENDTIME", ENDTIME);
		hm.put("startyear", startyear);
		hm.put("startmonth", startmonth);
		hm.put("endyear", endyear);
		hm.put("endmonth", endmonth);
		hm.put("yearSelect", yearSelect);
		hm.put("monthSelect", monthSelect);
		hm.put("TSJGID", TSJGID);
		hm.put("jgbh", jgbh);
		hm.put("departType", departType);
		ComplaintManageDao cmd = new ComplaintManageDao();
		String xml = cmd.getXml(hm);
		out.write(xml);
		out.close();
		return null;
	}
}