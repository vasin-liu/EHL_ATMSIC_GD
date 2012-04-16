package com.ehl.dispatch.common;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.ehl.sm.base.Constant;

/**
 * �ļ��ϴ�����
 * @author xiayx
 *
 */
public class FileUpDownLoad extends Controller {
	
	private Logger logger = Logger.getLogger(FileUpDownLoad.class);
	
	/**�ϴ��ļ��ڷ���������ĸ�·��������Ŀ�ļ���λ��ͬһĿ¼�£�*/
	public final static String ROOT_PATH = "EHL_Upload";
	
	/**
	 * <pre>
	 * ��ȡ�������
	 * ָ�����ȴ����������ʱ��Ϊ�������
	 * </pre>
	 * @param size ָ�����������
	 * @return �����
	 */
	private static String getRandomLimit(int size) {
		String random = null;
		if (size >= 1) {
			random = String.valueOf(Math.random());
			random = random.replace(".", "");
			int sindex = 1;
			int eindex = sindex + size;
			if (eindex > random.length()) {
				eindex = random.length();
			}
			random = random.substring(sindex, eindex);
		}
		return random;
	}
	
	/**
	 * <pre>
	 * ��ȡ�����
	 * </pre>
	 * @param size ָ�����������
	 * @return �����
	 */
	public static String getRandom(int size) {
		String random = getRandomLimit(size);
		if (random != null) {
			String temp = random;
			while (temp.length() < size) {
				size -= temp.length();
				temp = getRandomLimit(size);
				random += temp;
			}
		}
		return random;
	}
	
	/**
	 * ��ȡ�ϴ��ļ��ڷ������ı���Ŀ¼
	 * 
	 * @param request
	 *            �������
	 * @return ����Ŀ¼
	 */
	private static String getFileOnServerPath(HttpServletRequest request) {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		realPath = realPath.substring(0, realPath.length() - 1);
		int pathIndex = realPath.lastIndexOf("\\");
		realPath = realPath.substring(0, pathIndex + 1);
		realPath += ROOT_PATH;
		return realPath;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public ActionForward doUpload(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		boolean isOK = false;
		String msg = "�ϴ�����ʧ�ܣ�";
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			List<String> fpathList = new ArrayList<String>();
			String realPath = getFileOnServerPath(request);
			String tempPath = realPath + "\\temp";
			File tempFile = new File(tempPath);
			if (!tempFile.exists()) {
				tempFile.mkdirs();
			}
			
			List<FileItem> fileItems = null;
			if (tempFile.exists()) {
				// �������̹��������ù�����ʵ���ڴ����ݴ���������ʱ����·��
				DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 4, tempFile);
				// �������ֻ�������ڴ��д洢������,��λ:�ֽ�
				// factory.setSizeThreshold(4096);
				// �����ļ���ʱ�洢·��
				// factory.setRepository(new File("D:\\Temp"));
				// ����һ�µ��ļ��ϴ������ʽ
				ServletFileUpload upload = new ServletFileUpload(factory);
				// ����·�����ļ������ַ���
				upload.setHeaderEncoding("UTF-8");
				// ���������û��ϴ��ļ���С,��λ:�ֽ�
				upload.setSizeMax(1024 * 1024 * 50);
				// �������󣬿�ʼ��ȡ����
				// Iterator<FileItem> iter = upload.getItemIterator(request);
				// �õ����еı�������Ŀǰ��������FileItem
				try {
					fileItems = upload.parseRequest(request);
				} catch (Exception e) {
					fileItems = null;
					msg = "����������С���ܳ���50�ף�";
				}
			}
			if (fileItems != null) {
				String jgid = (String)request.getSession().getAttribute(Constant.JGID_VAR);
				String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
				String dbpath = "\\"+ jgid + "\\"+time;
				// ���δ�������
				Iterator<FileItem> iter = fileItems.iterator();
				////���÷��ļ��ϴ�����
				Map<String,String> params = new HashMap<String, String>();
				params.put("folder", dbpath);//Ĭ������
				while (iter.hasNext()) {
					FileItem item = iter.next();
					//����б�����Ϊfolder��������Ĭ�ϵ�����
					String name = item.getFieldName();
					String value = item.getString("UTF-8");
					if (!item.isFormField()) {
						name += "[file]";
						value = item.getName();
					}
					params.put(name, value);
				}
				logger.info("params:\n"+JSONObject.fromObject(params));
				String svpath = realPath + params.get("folder").replace("/", "\\");
				File folderOnServer = new File(svpath);
				if(!folderOnServer.exists()){
					folderOnServer.mkdirs();
				}
				svpath += "\\";
				iter = fileItems.iterator();
				while (iter.hasNext()) {
					FileItem item = iter.next();
					if (!item.isFormField()) {
						// ���item���ļ��ϴ�����
						// ����ļ�����·��
						String fileName = item.getName();
						if (fileName != null && !fileName.equals("")) {
							// ����ļ��������ϴ�
							File fileOnServer = new File( svpath + fileName.substring(fileName.lastIndexOf("\\") + 1));
							item.write(fileOnServer);
							fpathList.add(params.get("folder") + "/"+ fileOnServer.getName());
						}
					}
				}
			}
			if (fpathList.size() != 0) {
				isOK = true;
				msg = "";
				for (int i = 0; i < fpathList.size(); i++) {
					msg += ",\""+ROOT_PATH + fpathList.get(i) + "\"";
				}
				msg = "[" + msg.substring(1).replace("\\", "/") + "]";
			}
		}
		if(!isOK){
			msg = "'" + msg + "'";
		}
		logger.info("Upload:\n'" + isOK + "';" + msg);
		String cbjs = "parent.UDload.result=" + msg;
		cbjs += ";parent.UDload.callBack('" + isOK + "')";
		cbjs = "<script>" + cbjs + "</script>";
		PrintWriter out = response.getWriter();
		out.println(cbjs);
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * ��ȡ��������ǰ��ĿĬ�������ļ����·��
	 * @param request
	 * @return
	 */
	private String getServerFilePath(HttpServletRequest request) {
		String svpath = null;
		if(request != null){
			svpath = request.getSession().getServletContext()
			.getRealPath("/").replace("\\", "/");
			svpath = svpath.substring(0, svpath.length() - 1);
			svpath = svpath.substring(0, svpath.lastIndexOf("/") + 1);
		}
		return svpath;
	}
	
	/**
	 * <pre>
	 * fileName�ļ�����abc.txt
	 * filePath�ļ�·��(����)��c:\abc\abc.txt
	 * filePartPath�ļ�����·����abc\abc.txt
	 * folderPath�ļ���·��(����)��c:\abc\
	 * folderPartPath�ļ��в���·����abc\
	 * sfpPath(server file part path)��������Ĭ�������ļ�����ļ���·��
	 * �����ļ�����·��
	 * �ļ�����
	 * 1.��ȡ�ļ�
	 * 1.1��ȡ�ļ���
	 * 1.2��ȡ�ļ�
	 * 2.��ȡ�ļ�����Ŀ¼
	 * 2.1�������ط�ʽ��ֱ�Ӵ򿪻��߱��棩
	 * 2.2���ÿͻ��������ļ�Ĭ������
	 * 2.3ѡ���ļ�����Ŀ¼
	 *    ǰ����ѡ���ˡ����桱�ļ������ط�ʽ��������������Ĭ�ϵ���ʱ�ļ�����Ŀ¼
	 * 3.�����ļ�
	 * 3.1��ȡ�ļ�������
	 * 3.2��ȡ�ļ������
	 * 3.3��ͻ���д������
	 * </pre>
	 * @param action
	 * @param request
	 * @param response
	 * @return
	 * @throws Throwable
	 */
	public ActionForward doDownload(Action action, HttpServletRequest request,
			HttpServletResponse response) throws Throwable {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		//1.��ȡ�ļ�
		//1.1��ȡ�ļ�����·����EHL_Upload/00124343/abc.txt
		String filePartPath = request.getParameter("fileName");
		if(filePartPath != null){
			//1.2��ȡ�ļ�
			String svpath = getServerFilePath(request);
			filePartPath = filePartPath.replace("\\", "/");
			String filePath = svpath + filePartPath;
			File file = new File(filePath);
			if(file.exists()){
				//2.��ȡ�ļ�����·��
				//2.1 �Ƿ�ֱ�Ӵ�
				String isOpenStr = request.getParameter("isOpen");
				if(isOpenStr == null || isOpenStr.equals("true")){
					response.setHeader("Pragma","no-cache"); 
					response.setHeader("Cache-Control","no-cache"); 
					response.setDateHeader("Expires", 0);
				}
				//2.2���ÿͻ���ѡ���ļ�ʱ���ļ�Ĭ������
				//display file name
				String dfname = filePartPath.substring(filePartPath.lastIndexOf("/")+1);
				dfname = encodingFileName(dfname);
				//2.3ѡ���ļ����ط�ʽ���ļ�����Ŀ¼
				response.addHeader("Content-Type","application/x-msdownload");
				response.addHeader("Content-Disposition","attachment;filename="+dfname);
				//3.1��ȡ�ļ�������
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				//3.2��ȡ�ļ������
				BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
				//3.3��ͻ���д������
				byte[] cache = new byte[1024*1024*1];//1mb
				int size;
				while((size=bis.read(cache))!=-1){
					bos.write(cache,0,size);
				}
				bos.flush();
				bos.close();
			}else{
				logger.error("�����ļ�����Ч");
				PrintWriter out = response.getWriter();
				String msg = "<script>(window.parent.UDload||window.UDload).showError()</script>";
				out.write(msg);
			}
		}else{
			logger.error("δ��ȡ�������ļ���");
		}
		
		return null;
	}
	
	public  String encodingFileName(String fileName) {  
        String returnFileName = "";  
        try {  
            returnFileName = URLEncoder.encode(fileName, "UTF-8");  
            returnFileName = StringUtils.replace(returnFileName, "+", "%20");
            //IE6֧�ֵ���󳤶Ȳ�����150
            if (returnFileName.length() > 150) {  
                returnFileName = new String(fileName.getBytes("GB2312"), "ISO8859-1");  
                returnFileName = StringUtils.replace(returnFileName, " ", "%20");  
            } 
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
            logger.info("Don't support this encoding ...");  
        }
        return returnFileName;  
    }

}
