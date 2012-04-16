package com.ehl.policeWorks.newsFiles.action;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.dispatch.cdispatch.core.DataToXML;
import com.ehl.dispatch.common.FlowUtil;
import com.ehl.policeWorks.assess.bean.NewsFilesInfoBean;
import com.ehl.policeWorks.newsFiles.core.NewsFileCore;
import com.ehl.policeWorks.newsFiles.util.FileUtil;
import com.ehl.sm.base.Constant;

/**
 * ��Ϣ�ļ����������
 *
 */
public class newsFilesAction extends Controller{

	private Logger logger = Logger.getLogger(newsFilesAction.class);

	/**
	 * ��Ϣ�ļ�¼����ļ��ϴ�����<br>
	 * ��Ϣ�ļ�¼����ļ��ϴ�����
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doInsertNewsFiles(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		final int size = 50 * 1024 * 1024;
		String realPath = request.getSession().getServletContext().getRealPath("/");
		realPath = realPath.substring(0, realPath.length() - 1);
		int pathIndex = realPath.lastIndexOf("\\");
		realPath = realPath.substring(0, pathIndex + 1);
		String encoding = request.getCharacterEncoding();

		// ȡ�õ�ǰ����
		SimpleDateFormat mat = new SimpleDateFormat("yyyyMMdd");
		String nowDate = mat.format(new Date());

		if (encoding == null || encoding == "") {
			encoding = "UTF-8";
		}

		String uploadPath = "";
		String databaseFileName = "";
		String tempPath = realPath + "��Ϣ�ļ�\\��ʱ�ļ�\\";
		String returnMsg = "";
		// ��Ϣ�ļ���ϢBean
		NewsFilesInfoBean newsFilesInfoBean = new NewsFilesInfoBean();
		String fwname = "success";
		request.setCharacterEncoding(encoding);
		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(size);
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
				returnMsg = "�ϴ��������ܳ���50�ף������±���!";
				out.println("<script type=\"text/javascript\">alert('"+returnMsg+"');window.close();</script>");
				session.setAttribute("resultMsg", returnMsg);
				ActionForward forward = action.findForward(fwname);
				return forward;
			}

			Iterator ite = list.iterator();
			int count = 0;

			try {
				// ���δ���ÿһ���ļ���
				while (ite.hasNext()) {
					uploadPath = "";
					databaseFileName = "";

					FileItem fi = (FileItem) ite.next();
					if (fi == null) {
						continue;
					}
					if (fi.isFormField()) {
						if (fi.getFieldName().equals( "jgbh")) {
							newsFilesInfoBean.setJgbh(fi.getString(encoding));
						} else if (fi.getFieldName().equals( "jgid")) {
							newsFilesInfoBean.setJgid(fi.getString(encoding));
						} else if (fi.getFieldName().equals( "title")) {
							newsFilesInfoBean.setTitle(fi.getString(encoding));
						} else if (fi.getFieldName().equals( "sendTime")) {
							newsFilesInfoBean.setSendTime(fi.getString(encoding));
						} else if (fi.getFieldName().equals( "sendUnit")) {
							newsFilesInfoBean.setSendUnit(fi.getString(encoding));
						} else if (fi.getFieldName().equals( "sendPeople")) {
							newsFilesInfoBean.setSendPeople(fi.getString(encoding));
						} else if (fi.getFieldName().equals( "modifyContent")) {
							newsFilesInfoBean.setModifyContent(fi.getString(encoding));
						} else if (fi.getFieldName().equals("type")){
							newsFilesInfoBean.setType(fi.getString(encoding));
						} else if(fi.getFieldName().equals("sbtype")){
							newsFilesInfoBean.setSbType(fi.getString(encoding));
							if(fi.getString(encoding).equals("2")){
								fwname = "successZd";
							}
						} else if(fi.getFieldName().equals("writer")){
							newsFilesInfoBean.setWriter(fi.getString(encoding));
						}
					} else if (!fi.isFormField()) {
						// ȡ���ļ���
						String fileName = fi.getName();
						// ����ļ���������ļ�������·����
						if ( fileName != null && fileName.length() != 0 && fileName != "") {
							int index;
							index = fileName.lastIndexOf("\\");
							fileName = fileName.substring(index + 1, fileName.length());
							// �ϴ��ļ�·��
							uploadPath = realPath + "��Ϣ�ļ�\\" + newsFilesInfoBean.getSendUnit() + "\\" + nowDate + "\\";
							// �ϴ��ļ����ļ�·�����ļ���
							String newFileName = uploadPath + fileName;
							// �������ݱ��е��ļ�·�� ("��Ϣ�ļ�/" + ��λ���� + ��ǰYYYYMMDD + �ļ��� )
							databaseFileName = "��Ϣ�ļ�/" + newsFilesInfoBean.getSendUnit() + "/" + nowDate + "/" + fileName;
							// �ܶ�ʱ���Ͳ����ܾ�ʱ·���Ķ���
							if(newsFilesInfoBean.getJgbh().length() == 2) {
								uploadPath = realPath + "��Ϣ�ļ�\\" + "���Ͳ�����Ϣ\\" + newsFilesInfoBean.getSendUnit() + "\\" + nowDate + "\\";
								newFileName = uploadPath + fileName;
								databaseFileName = "��Ϣ�ļ�/" +  "���Ͳ�����Ϣ/" + newsFilesInfoBean.getSendUnit() + "/" + nowDate + "/" + fileName;
							}
							if(count == 0) {
								newsFilesInfoBean.setWordFilePath(databaseFileName);
							} else {
								newsFilesInfoBean.setStreamFilePath(databaseFileName);
							}
							
							// �ж��Ƿ�����ͬ�ļ����ļ�����
							if (new File(newFileName).exists() && new File(newFileName).isFile()) {
								newFileName = FileUtil.changeSameFile(newFileName);
								int idx;
								idx = newFileName.lastIndexOf("\\");
								newFileName = newFileName.substring(idx + 1, newFileName.length());
								// �������ݱ��е��ļ�·�� ("��Ϣ�ļ�/" + ��λ���� + ��ǰYYYYMMDD + �ļ��� )
								databaseFileName = "��Ϣ�ļ�/" + newsFilesInfoBean.getSendUnit() + "/" + nowDate + "/" + newFileName;
								// �ϴ��ļ����ļ�·�����ļ���
								String newFilePathAndName =  realPath + "��Ϣ�ļ�\\" + newsFilesInfoBean.getSendUnit() + "\\" + nowDate + "\\" + newFileName;
								
								if(newsFilesInfoBean.getJgbh().length() == 2) {
									databaseFileName = "��Ϣ�ļ�/" + "���Ͳ�����Ϣ/" +  newsFilesInfoBean.getSendUnit() + "/" + nowDate + "/" + newFileName;
									newFilePathAndName =  realPath + "��Ϣ�ļ�\\" + "���Ͳ�����Ϣ\\" + newsFilesInfoBean.getSendUnit() + "\\" + nowDate + "\\" + newFileName;
								}
								File file = new File(uploadPath);
								if(count == 0) {
									newsFilesInfoBean.setWordFilePath(databaseFileName);
								} else {
									newsFilesInfoBean.setStreamFilePath(databaseFileName);
								}
								// �ж��ϴ��ļ�·���Ƿ����
								if (!file.exists()) {
									file.mkdirs();
									// �����ļ�
									fi.write(new File(newFilePathAndName));
								} else {
									// �����ļ�
									fi.write(new File(newFilePathAndName));
								}
							} else {
								File file = new File(uploadPath);
								// �ж��ϴ��ļ�·���Ƿ����
								if (!file.exists()) {
									file.mkdirs();
									// �����ļ�
									fi.write(new File(newFileName));
								} else {
									// �����ļ�
									fi.write(new File(newFileName));
								}
							}
						}
						count = count + 1;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				returnMsg = "��Ϣ�ļ������ļ��ϴ�ʧ�ܣ�";
				session.setAttribute("resultMsg", returnMsg);
				logger.error("��Ϣ�ļ������ļ��ϴ�ʧ�ܣ�");
				out.println("<script type=\"text/javascript\">alert('"+returnMsg+"');window.close();</script>");
				out.close();
				ActionForward forward = action.findForward(fwname);
				return forward;
			}

			newsFilesInfoBean.setState("0");
			// ���ݿ�����ļ��ɹ��ļ�¼
			boolean successFlg = FileUtil.addTOaNewsfileInfo(newsFilesInfoBean);
			
			if(!successFlg) {
				returnMsg = "��Ϣ�ļ����ݱ���ʧ�ܣ�";
				session.setAttribute("resultMsg", returnMsg);
				logger.error("��Ϣ�ļ����ݱ���ʧ�ܣ�");
				out.println("<script type=\"text/javascript\">alert('"+returnMsg+"');window.close();</script>");
				out.close();
				ActionForward forward = action.findForward(fwname);
				return forward;
			}
		}
		returnMsg = "��Ϣ�ļ�����ɹ���";
		session.setAttribute("resultMsg", returnMsg);
		out.println("<script type=\"text/javascript\">alert('"+returnMsg+"');window.close();</script>");
		out.close();
		ActionForward forward = action.findForward(fwname);
		return forward;
	}

	/**
	 * ȡ����Ϣ�ļ���Ϣ<br/>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doGetNewsFileInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		
		String newsFileid = StringHelper.obj2str(request.getParameter("newsFileid"),"");
	 	
		// ������,��·����,ʩ������,X,Y
		String sql = "  select NEWS_FILEID,TITLE,to_char(SEND_TIME, 'yyyy-mm-dd HH24:mi'),SEND_DEPARTMENT_NAME,SEND_PERSON,WORD_FILEPATH, " + 
		  "   STREAM_FILEPATH,DETAIL_INFO,SEND_DEPARTMENT_ID ,OTHER_INFO,STATE,TYPE,WRITER,ISZXGZ  from T_OA_NEWSFILE " + 
		  "  where NEWS_FILEID = '" + newsFileid + 
		  "'";
		System.out.println("***********"+sql);
		Object[][] res = DBHandler.getMultiResult(sql);
		String str = DataToXML.objArrayToXml(res);
		out.write(str);
		return null;
	}
	
	/**
	 * ������Ϣ�ļ���Ϣ<br/>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doUpdateNewsFilesInfo(Action action,
			HttpServletRequest request, HttpServletResponse response)
	throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml"); // ָ��������ݵĸ�ʽ
		PrintWriter out = response.getWriter();
		String newsFileid = StringHelper.obj2str(request.getParameter("newsFileid"),"");
		String otherInfo = StringHelper.obj2str(request.getParameter("otherInfo"),"");
		String state = StringHelper.obj2str(request.getParameter("state"),"");
//		Modify by Xiayx 2011-08-14
//		��Ϣ�ļ�����
		String type = null;
		//�޸�ר���
		String setZxgz = "";
		//��ȡ���������
		String atype = StringHelper.obj2str(request.getParameter("atype"),"");
		//ʡ�����
		if(atype.equals("1")){
		    setZxgz=",isZxgz=''";
			//��ͨ��Ϣ
			type = "1";
			//������Ϣ������
			if(state.equals("4")){
				//������Ϣ
				type = "2";
				//������Ϣ��ʡ������
				state = "2";
			}
			//ר�������
			else if(state.equals("5")){
			    state = "2";
			    setZxgz = ",isZxgz='1'";
			}
		}
		String siftDtime = ",dtime=";
		if(state.equals("1")||state.equals("3")){
			siftDtime += "''";
		}else{
			siftDtime += "nvl(dtime,sysdate)";
		}
		String siftType = "";
		if (type != null) {
			siftType = ",TYPE='" + type + "'";
		}
//		Modify finished
		try {
			// �����ܶ���д�ı�ע��Ϣ �� ����״̬
			String sql = "  update T_OA_NEWSFILE set OTHER_INFO ='" + otherInfo + "',STATE = '" + state +"'" + siftType+siftDtime+setZxgz+   
			"  where NEWS_FILEID = '" + newsFileid + "'";
			System.out.println("***********"+sql);
			DBHandler.execute(sql);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("�����ܶ���д�ı�ע��Ϣ �� ����״̬ʧ�ܣ�");
			out.write("false");
			out.close();
		}
		out.write("success");
		out.close();
		return null;
	}
	
	
	/**
	 * ��Ϣ�ļ�¼����ļ��ϴ�����<br>
	 * ��Ϣ�ļ�¼����ļ��ϴ�����
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doModify(Action action,
			HttpServletRequest request, HttpServletResponse response)
			throws Throwable{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// ��Ϣ�ļ�
		Map<String,String> newsFile = new HashMap<String, String>();
		//�Ƿ��ϴ����ļ�
		boolean isUploadWord = false;
		boolean isUploadStream = false;
//		------------------------------------------------------
		final int size = 50 * 1024 * 1024;
		String realPath = request.getSession().getServletContext().getRealPath("/");
		realPath = realPath.substring(0, realPath.length() - 1);
		int pathIndex = realPath.lastIndexOf("\\");
		realPath = realPath.substring(0, pathIndex + 1);
		String encoding = request.getCharacterEncoding();

		// ȡ�õ�ǰ����
		SimpleDateFormat mat = new SimpleDateFormat("yyyyMMdd");
		String nowDate = mat.format(new Date());

		if (encoding == null || encoding == "") {
			encoding = "UTF-8";
		}
		
		String uploadPath = "";
		String databaseFileName = "";
		String tempPath = realPath + "��Ϣ�ļ�\\��ʱ�ļ�\\";
		String returnMsg = "";
		request.setCharacterEncoding(encoding);
		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(size);
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
				returnMsg = "�ϴ��������ܳ���50�ף������±���!";
				out.println("<script type=\"text/javascript\">parent.alert('"+returnMsg+"');</script>");
				return null;
			}

			Iterator ite = list.iterator();
			int count = 0;

			try {
				// ���δ���ÿһ���ļ���
				while (ite.hasNext()) {
					uploadPath = "";
					databaseFileName = "";

					FileItem fi = (FileItem) ite.next();
					if (fi == null) {
						continue;
					}
					if(fi.isFormField()){
						newsFile.put(fi.getFieldName(), fi.getString(encoding));
					}
					else if (!fi.isFormField()) {
						// ȡ���ļ���
						String fileName = fi.getName();
						//Modify by Xiayx 2011-8-31
						//�ж��Ƿ��޸��˸���
						String fieldName = fi.getFieldName();
						if(fieldName != null){
							if(fieldName.equals("wordFile")) {
								isUploadWord = true;
							} else if(fieldName.equals("streamFile")) {
								isUploadStream = true;
							}
						}
						//Modification finished
						// ����ļ���������ļ�������·����
						if ( fileName != null && fileName.length() != 0 && fileName != "") {
							int index;
							index = fileName.lastIndexOf("\\");
							fileName = fileName.substring(index + 1, fileName.length());
							// �ϴ��ļ�·��
							uploadPath = realPath + "��Ϣ�ļ�\\" + newsFile.get("sendUnit") + "\\" + nowDate + "\\";
							// �ϴ��ļ����ļ�·�����ļ���
							String newFileName = uploadPath + fileName;
							// �������ݱ��е��ļ�·�� ("��Ϣ�ļ�/" + ��λ���� + ��ǰYYYYMMDD + �ļ��� )
							databaseFileName = "��Ϣ�ļ�/" + newsFile.get("sendUnit") + "/" + nowDate + "/" + fileName;
							// �ܶ�ʱ���Ͳ����ܾ�ʱ·���Ķ���
							if(newsFile.get("jgbh").length() == 2) {
								uploadPath = realPath + "��Ϣ�ļ�\\" + "���Ͳ�����Ϣ\\" + newsFile.get("sendUnit") + "\\" + nowDate + "\\";
								newFileName = uploadPath + fileName;
								databaseFileName = "��Ϣ�ļ�/" +  "���Ͳ�����Ϣ/" + newsFile.get("sendUnit") + "/" + nowDate + "/" + fileName;
							}
							if(fieldName.equals("wordFile")) {
								newsFile.put("wordPath", databaseFileName);
							} else if(fieldName.equals("streamFile")) {
								newsFile.put("streamPath", databaseFileName);
							}
							
							// �ж��Ƿ�����ͬ�ļ����ļ�����
							if (new File(newFileName).exists() && new File(newFileName).isFile()) {
								newFileName = FileUtil.changeSameFile(newFileName);
								int idx;
								idx = newFileName.lastIndexOf("\\");
								newFileName = newFileName.substring(idx + 1, newFileName.length());
								// �������ݱ��е��ļ�·�� ("��Ϣ�ļ�/" + ��λ���� + ��ǰYYYYMMDD + �ļ��� )
								databaseFileName = "��Ϣ�ļ�/" + newsFile.get("sendUnit") + "/" + nowDate + "/" + newFileName;
								// �ϴ��ļ����ļ�·�����ļ���
								String newFilePathAndName =  realPath + "��Ϣ�ļ�\\" + newsFile.get("sendUnit") + "\\" + nowDate + "\\" + newFileName;
								
								if(newsFile.get("jgbh").length() == 2) {
									databaseFileName = "��Ϣ�ļ�/" + "���Ͳ�����Ϣ/" +  newsFile.get("sendUnit") + "/" + nowDate + "/" + newFileName;
									newFilePathAndName =  realPath + "��Ϣ�ļ�\\" + "���Ͳ�����Ϣ\\" +newsFile.get("sendUnit") + "\\" + nowDate + "\\" + newFileName;
								}
								File file = new File(uploadPath);
								if(fieldName.equals("wordFile")) {
									newsFile.put("wordPath", databaseFileName);
								} else if(fieldName.equals("streamFile")) {
									newsFile.put("streamPath", databaseFileName);
								}
								
								// �ж��ϴ��ļ�·���Ƿ����
								if (!file.exists()) {
									file.mkdirs();
									// �����ļ�
									fi.write(new File(newFilePathAndName));
								} else {
									// �����ļ�
									fi.write(new File(newFilePathAndName));
								}
							} else {
								File file = new File(uploadPath);
								// �ж��ϴ��ļ�·���Ƿ����
								if (!file.exists()) {
									file.mkdirs();
									// �����ļ�
									fi.write(new File(newFileName));
								} else {
									// �����ļ�
									fi.write(new File(newFileName));
								}
							}
						}
						count = count + 1;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				returnMsg = "�ļ��ϴ�ʧ�ܣ�";
				logger.error("�ļ��ϴ�ʧ�ܣ�");
				out.println("<script type=\"text/javascript\">parent.alert('"+returnMsg+"');</script>");
				out.close();
				return null;
			}
			
			// ���ݿ�����ļ��ɹ��ļ�¼
			boolean successFlg = false;
			String set = "title='"+newsFile.get("title")+"',detail_info='"+newsFile.get("modifyContent")+"'";
			if(isUploadWord){
				set += ",word_filepath='"+Constant.nvl(newsFile.get("wordPath"))+"'";
			}
			if(isUploadStream){
				set += ",stream_filepath='"+Constant.nvl(newsFile.get("streamPath"))+"'";
			}
			if(newsFile.containsKey("writer")){
				set += ",writer='"+newsFile.get("writer")+"'";
			}
			if(newsFile.containsKey("type")){
				set += ",type='"+newsFile.get("type")+"'";
			}
			if(newsFile.containsKey("state")){
				set += ",state='"+newsFile.get("state")+"'";
			}
			if(newsFile.containsKey("otherInfo")){
				set += ",other_info='"+newsFile.get("otherInfo")+"'";
			}
			if(newsFile.containsKey("dtime")){
				set += ",dtime='to_char('"+newsFile.get("dtime")+"','yyyy-mm-dd hh24:mi')";
			}else{
				set += ",dtime=sysdate";
			}
			if(newsFile.containsKey("jgid")){
				set += ",send_department_id='"+newsFile.get("jgid")+"'";
			}
			if(newsFile.containsKey("sendUnit")){
				set += ",send_department_name='"+newsFile.get("sendUnit")+"'";
			}
			if(newsFile.containsKey("pname")){
				set += ",send_person='"+newsFile.get("sendPeople")+"'";
			}
			String sql = "update t_oa_newsfile set "+set+" where news_fileid='"+newsFile.get("id")+"'";
			successFlg = FlowUtil.write(sql,logger,"�޸���Ϣ�ļ�");
			if(successFlg) {
				returnMsg = "��Ϣ�ļ�����ɹ���";
			}else{
				returnMsg = "��Ϣ�ļ�����ʧ�ܣ�";
			}
			logger.info(returnMsg);
			out.println("<script type=\"text/javascript\">parent.alert('"+returnMsg+"');parent.close();</script>");
			out.close();
			return null;
		}
		return null;
	}
	
    
    public ActionForward doSpringStatis(Action action,
	    HttpServletRequest request, HttpServletResponse response)
	    throws Throwable {
	String stime = request.getParameter("stime");
	String etime = request.getParameter("etime");
	String xml = NewsFileCore.springStatis(stime, etime);
	response.setContentType("text/xml");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	out.write(xml);
	out.flush();
	out.close();
	return null;
    }

}