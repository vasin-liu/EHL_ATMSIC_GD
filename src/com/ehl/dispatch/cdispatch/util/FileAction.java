package com.ehl.dispatch.cdispatch.util;

import com.appframe.data.sql.DBHandler;
import com.ehl.sm.common.util.CreateSequence;
import com.jspsmart.upload.SmartFile;
import com.jspsmart.upload.SmartUpload;

public class FileAction{ 
	
	public boolean doSaveFile(String ywid, String ywlx, String fjms, String spath, SmartFile myFile) throws Throwable{
		//得先判断有没有！！
		try{   //新增
			String fjId = CreateSequence.getMaxForSeq("SEQ_T_OA_ATTACHMENT", 12);
			String fjwz = spath + fjId;
			myFile.saveAs(fjwz,SmartUpload.SAVE_PHYSICAL);

			StringBuffer sql= new StringBuffer("insert into T_OA_ATTACHMENT(FJID,YWID,YWLX,FJMC,FJLX,FJWZ")
				.append(",FJMS) values('").append(fjId).append("','").append(ywid).append("','").append(ywlx)
				.append("','").append(myFile.getFileName()).append("','").append(myFile.getFileExt())
				.append("','").append(fjId).append("','").append(fjms).append("')");
			System.out.println("FileAction.doSaveFile.sql=" + sql.toString());
			return DBHandler.execute(sql.toString());
		}catch(Exception e){
			System.out.println("上传文件出错！");
			e.printStackTrace();
			return false;
		}
	}
}