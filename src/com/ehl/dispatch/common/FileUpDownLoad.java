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
 * 文件上传下载
 * @author xiayx
 *
 */
public class FileUpDownLoad extends Controller {
	
	private Logger logger = Logger.getLogger(FileUpDownLoad.class);
	
	/**上传文件在服务器保存的根路径（和项目文件夹位于同一目录下）*/
	public final static String ROOT_PATH = "EHL_Upload";
	
	/**
	 * <pre>
	 * 获取随机数，
	 * 指定长度大于随机长度时，为随机长度
	 * </pre>
	 * @param size 指定随机数长度
	 * @return 随机数
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
	 * 获取随机数
	 * </pre>
	 * @param size 指定随机数长度
	 * @return 随机数
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
	 * 获取上传文件在服务器的保存目录
	 * 
	 * @param request
	 *            请求对象
	 * @return 保存目录
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
		String msg = "上传附件失败！";
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
				// 创建磁盘工厂，利用构造器实现内存数据储存量和临时储存路径
				DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 4, tempFile);
				// 设置最多只允许在内存中存储的数据,单位:字节
				// factory.setSizeThreshold(4096);
				// 设置文件临时存储路径
				// factory.setRepository(new File("D:\\Temp"));
				// 产生一新的文件上传处理程式
				ServletFileUpload upload = new ServletFileUpload(factory);
				// 设置路径、文件名的字符集
				upload.setHeaderEncoding("UTF-8");
				// 设置允许用户上传文件大小,单位:字节
				upload.setSizeMax(1024 * 1024 * 50);
				// 解析请求，开始读取数据
				// Iterator<FileItem> iter = upload.getItemIterator(request);
				// 得到所有的表单域，它们目前都被当作FileItem
				try {
					fileItems = upload.parseRequest(request);
				} catch (Exception e) {
					fileItems = null;
					msg = "单个附件大小不能超过50兆！";
				}
			}
			if (fileItems != null) {
				String jgid = (String)request.getSession().getAttribute(Constant.JGID_VAR);
				String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
				String dbpath = "\\"+ jgid + "\\"+time;
				// 依次处理请求
				Iterator<FileItem> iter = fileItems.iterator();
				////设置非文件上传表单域
				Map<String,String> params = new HashMap<String, String>();
				params.put("folder", dbpath);//默认设置
				while (iter.hasNext()) {
					FileItem item = iter.next();
					//如果有表单名称为folder，将覆盖默认的设置
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
						// 如果item是文件上传表单域
						// 获得文件名及路径
						String fileName = item.getName();
						if (fileName != null && !fileName.equals("")) {
							// 如果文件存在则上传
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
	 * 获取服务器当前项目默认下载文件存放路径
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
	 * fileName文件名，abc.txt
	 * filePath文件路径(完整)，c:\abc\abc.txt
	 * filePartPath文件部分路径，abc\abc.txt
	 * folderPath文件夹路径(完整)，c:\abc\
	 * folderPartPath文件夹部分路径，abc\
	 * sfpPath(server file part path)，服务器默认下载文件存放文件夹路径
	 * 下载文件部分路径
	 * 文件下载
	 * 1.获取文件
	 * 1.1获取文件名
	 * 1.2获取文件
	 * 2.获取文件下载目录
	 * 2.1设置下载方式（直接打开或者保存）
	 * 2.2设置客户端下载文件默认名称
	 * 2.3选择文件保存目录
	 *    前提是选择了“保存”文件的下载方式，否则会有浏览器默认的临时文件下载目录
	 * 3.下载文件
	 * 3.1获取文件输入流
	 * 3.2获取文件输出流
	 * 3.3向客户端写出数据
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
		
		//1.获取文件
		//1.1获取文件部分路径，EHL_Upload/00124343/abc.txt
		String filePartPath = request.getParameter("fileName");
		if(filePartPath != null){
			//1.2获取文件
			String svpath = getServerFilePath(request);
			filePartPath = filePartPath.replace("\\", "/");
			String filePath = svpath + filePartPath;
			File file = new File(filePath);
			if(file.exists()){
				//2.获取文件下载路径
				//2.1 是否直接打开
				String isOpenStr = request.getParameter("isOpen");
				if(isOpenStr == null || isOpenStr.equals("true")){
					response.setHeader("Pragma","no-cache"); 
					response.setHeader("Cache-Control","no-cache"); 
					response.setDateHeader("Expires", 0);
				}
				//2.2设置客户端选择文件时，文件默认名称
				//display file name
				String dfname = filePartPath.substring(filePartPath.lastIndexOf("/")+1);
				dfname = encodingFileName(dfname);
				//2.3选择文件下载方式和文件下载目录
				response.addHeader("Content-Type","application/x-msdownload");
				response.addHeader("Content-Disposition","attachment;filename="+dfname);
				//3.1获取文件输入流
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				//3.2获取文件输出流
				BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
				//3.3向客户端写出数据
				byte[] cache = new byte[1024*1024*1];//1mb
				int size;
				while((size=bis.read(cache))!=-1){
					bos.write(cache,0,size);
				}
				bos.flush();
				bos.close();
			}else{
				logger.error("下载文件名无效");
				PrintWriter out = response.getWriter();
				String msg = "<script>(window.parent.UDload||window.UDload).showError()</script>";
				out.write(msg);
			}
		}else{
			logger.error("未获取到下载文件名");
		}
		
		return null;
	}
	
	public  String encodingFileName(String fileName) {  
        String returnFileName = "";  
        try {  
            returnFileName = URLEncoder.encode(fileName, "UTF-8");  
            returnFileName = StringUtils.replace(returnFileName, "+", "%20");
            //IE6支持的最大长度不超过150
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
