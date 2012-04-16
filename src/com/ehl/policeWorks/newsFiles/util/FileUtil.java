package com.ehl.policeWorks.newsFiles.util;

import java.io.File;


import com.appframe.data.sql.DBHandler;
import com.ehl.base.util.CreateSequence;
import com.ehl.policeWorks.assess.bean.NewsFilesInfoBean;

public class FileUtil {

	/**
	 * 改变相同文件名的上传文件的文件名<br/>
	 * 改变相同文件名的上传文件的文件名
	 * @param fileName
	 * @return
	 */
	public static String changeSameFile (String fileName) {
		String returnStr = fileName;
		if (new File(fileName).exists() && new File(fileName).isFile()) {
			int index  = fileName.lastIndexOf(".");
			String str1 = fileName.substring(0, index);
			String str2 = fileName.substring(index, fileName.length());
			returnStr = str1 + "I" + str2;
			FileUtil.changeSameFile(returnStr);
		} else {
			return returnStr;
		}
		return FileUtil.changeSameFile(returnStr);
	}

	/**
	 * 记录插入的信息文件<br/> 
	 * @param newsFilesInfoBean
	 * @return
	 */
	public static boolean addTOaNewsfileInfo(NewsFilesInfoBean newsFilesInfoBean) {
		try {
			StringBuffer sqlBuffer = new StringBuffer(); 
			sqlBuffer.append("insert into T_OA_NEWSFILE ( NEWS_FILEID,TITLE,SEND_TIME,SEND_DEPARTMENT_NAME,SEND_PERSON,WORD_FILEPATH,STREAM_FILEPATH,DETAIL_INFO,SEND_DEPARTMENT_ID ,OTHER_INFO,STATE,TYPE,SBTYPE,WRITER) values (");
			sqlBuffer.append("'");
			sqlBuffer.append(CreateSequence.getMaxForSeq("SEQ_T_OA_NEWSFILE", 20));
			sqlBuffer.append("',");
			sqlBuffer.append("'");
			sqlBuffer.append(newsFilesInfoBean.getTitle());
			sqlBuffer.append("',");
			sqlBuffer.append("to_date('");
			sqlBuffer.append(newsFilesInfoBean.getSendTime());
			sqlBuffer.append("','yyyy-mm-dd hh24:mi'),");
			sqlBuffer.append("'");
			sqlBuffer.append(newsFilesInfoBean.getSendUnit());
			sqlBuffer.append("',");
			sqlBuffer.append("'");
			sqlBuffer.append(newsFilesInfoBean.getSendPeople());
			sqlBuffer.append("',");
			sqlBuffer.append("'");
			sqlBuffer.append(newsFilesInfoBean.getWordFilePath());
			sqlBuffer.append("',");
			sqlBuffer.append("'");
			sqlBuffer.append(newsFilesInfoBean.getStreamFilePath());
			sqlBuffer.append("',");
			sqlBuffer.append("'");
			sqlBuffer.append(newsFilesInfoBean.getModifyContent());
			sqlBuffer.append("',");
			sqlBuffer.append("'");
			sqlBuffer.append(newsFilesInfoBean.getJgid());
			sqlBuffer.append("',");
			sqlBuffer.append("'");
			sqlBuffer.append("");
			sqlBuffer.append("',");
			sqlBuffer.append("'");
			sqlBuffer.append(newsFilesInfoBean.getState());
			sqlBuffer.append("',");
			sqlBuffer.append("'");
			sqlBuffer.append(newsFilesInfoBean.getType());
			sqlBuffer.append("','");
			sqlBuffer.append(newsFilesInfoBean.getSbType());
			sqlBuffer.append("','");
			sqlBuffer.append(newsFilesInfoBean.getWriter());
			sqlBuffer.append("')");
			DBHandler.execute(String.valueOf(sqlBuffer));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main (String [] args) {
		System.out.println(FileUtil.changeSameFile("F:\\asdf.asdd.f\\pa.trolMileage.html"));
	}
}
