package com.ehl.dispatch.cdispatch.dao;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.sm.common.util.CreateSequence;
import com.ehl.sm.common.util.StringUtil;
import com.ehl.dispatch.cdispatch.util.ArrayComparator;
import com.ehl.dispatch.common.Tree;

/**
 * ��·����������
 * @author wangxt
 * @date 2009-1-16
 *
 */
public class ComplaintManageDao {
	private Logger logger = Logger.getLogger(ComplaintManageDao.class);
	private  int  length2 ;//����ڶ�ά�ĳ��ȣ���Сʱͳ�Ƴ���Ϊ26������ͳ��12�����ܺͰ���ͳ����Ҫ���㳤�� 
	private ArrayList<String> weekList;//�����ʱ���
	private HashMap dateMap;//�������
	private String[] header;//��ű�ͷ
	/**
	 * ������·��Ϣ
	 * @param res
	 * @return
	 */
	public boolean addComplaintInfo(HashMap res){
		String sql = null;
		boolean flag = false;
		try {
			String CPID = res.get("CPID").toString();
			String TSDJSJ = res.get("TSDJSJ").toString();
			String DJRID = res.get("DJRID").toString();
			String DJJGID = res.get("DJJGID").toString();
			String TSRXM = res.get("TSRXM").toString();
			
			String TSRDH = res.get("TSRDH").toString();
			String TSRJZ = res.get("TSRJZ").toString();
			String TSRCP = res.get("TSRCP").toString();
			String TSYWFLB = res.get("TSYWFLB").toString();
			String TSYWZLB = res.get("TSYWZLB").toString();
			
			String TSNR = res.get("TSNR").toString();
			String TSJG = res.get("TSJG").toString();
			String TSJH = res.get("TSJH").toString();
			String ZHKJSR = res.get("ZHKJSR").toString();
			String ZHKLD = res.get("ZHKLD").toString();
			
			String ZHKYJ = res.get("ZHKYJ").toString();
			String ZHCJSR = res.get("ZHCJSR").toString();
			String ZHCLD = res.get("ZHCLD").toString();
			String ZHCYJ = res.get("ZHCYJ").toString();
			String JLDJSR = res.get("JLDJSR").toString();
			
			String JLD = res.get("JLD").toString();
			String JLDYJ = res.get("JLDYJ").toString();
			String YWCZGBM = res.get("YWCZGBM").toString();
			String YWCJSR = res.get("YWCJSR").toString();
			String YWCZG = res.get("YWCZG").toString();
			
			String YWCYJ = res.get("YWCYJ").toString();
			String YWCCBR = res.get("YWCCBR").toString();
			String ZDBLYJ = res.get("ZDBLYJ").toString();
			String ZDJGID = res.get("ZDJGID").toString();
			String ZDLD = res.get("ZDLD").toString();
			
			String ZDYJ = res.get("ZDYJ").toString();
			String DDJGID = res.get("DDJGID").toString();
			String BLJGSM = res.get("BLJGSM").toString();
			String ISREPLYMASSES = res.get("ISREPLYMASSES").toString();
			String JBDW = res.get("JBDW").toString();
			
			String JBLXR = res.get("JBLXR").toString();
			String JBLXRDH = res.get("JBLXRDH").toString();
			String SPR = res.get("SPR").toString();
			String SPRQ = res.get("SPRQ").toString();
			String XGR = res.get("XGR").toString();
			
			String XGSJ = res.get("XGSJ").toString();
			String LZZT = res.get("LZZT").toString();
			String LXDH = res.get("LXDH").toString();
			//String CPID = DJJGID.substring(0,6)+StringUtil.getCurrDateTime("yyyyMMddHHmmssSSS");
			
			sql ="insert into t_oa_complaint(CPID,TSDJSJ,DJRID,DJJGID,TSRXM,TSRDH,TSRJZ,TSRCP,TSYWFLB,TSYWZLB,TSNR,TSJG,TSJH,ZHKJSR,LZZT)";
			sql += " values('"+CPID+"',to_date('"+TSDJSJ+"','yyyy-mm-dd hh24:mi'),'"+DJRID+"','"+DJJGID+"','"+TSRXM+"','"+TSRDH+"','"+TSRJZ+"','"+TSRCP+"','"+
			TSYWFLB+"','"+TSYWZLB+"','"+TSNR+"','"+TSJG+"','"+TSJH+"','"+ZHKJSR+"','"+LZZT+"')";
			System.out.println("insert:"+sql);
			flag = DBHandler.execute(sql);
		}catch(Exception e) {
			logger.error("����Ͷ����Ϣ����:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * �༭��·��Ϣ
	 * @param res
	 * @return
	 */
	public boolean editComplaintInfo(HashMap res) {
		String sql = null;
		boolean flag = false;
		try {
			String CPID = res.get("CPID").toString();
			String TSDJSJ = res.get("TSDJSJ").toString();
			String DJRID = res.get("DJRID").toString();
			String DJJGID = res.get("DJJGID").toString();
			String TSRXM = res.get("TSRXM").toString();
			
			String TSRDH = res.get("TSRDH").toString();
			String TSRJZ = res.get("TSRJZ").toString();
			String TSRCP = res.get("TSRCP").toString();
			String TSYWFLB = res.get("TSYWFLB").toString();
			String TSYWZLB = res.get("TSYWZLB").toString();
			
			String TSNR = res.get("TSNR").toString();
			String TSJG = res.get("TSJG").toString();
			String TSJH = res.get("TSJH").toString();
			String ZHKJSR = res.get("ZHKJSR").toString();
			String ZHKLD = res.get("ZHKLD").toString();
			
			String ZHKYJ = res.get("ZHKYJ").toString();
			String ZHCJSR = res.get("ZHCJSR").toString();
			String ZHCLD = res.get("ZHCLD").toString();
			String ZHCYJ = res.get("ZHCYJ").toString();
			String JLDJSR = res.get("JLDJSR").toString();
			
			String JLD = res.get("JLD").toString();
			String JLDYJ = res.get("JLDYJ").toString();
			String YWCZGBM = res.get("YWCZGBM").toString();
			String YWCJSR = res.get("YWCJSR").toString();
			String YWCZG = res.get("YWCZG").toString();
			
			String YWCYJ = res.get("YWCYJ").toString();
			String YWCCBR = res.get("YWCCBR").toString();
			String ZDBLYJ = res.get("ZDBLYJ").toString();
			String ZDJGID = res.get("ZDJGID").toString();
			String ZDLD = res.get("ZDLD").toString();
			
			String ZDYJ = res.get("ZDYJ").toString();
			String DDJGID = res.get("DDJGID").toString();
			String BLJGSM = res.get("BLJGSM").toString();
			String ISREPLYMASSES = res.get("ISREPLYMASSES").toString();
			String JBDW = res.get("JBDW").toString();
			
			String JBLXR = res.get("JBLXR").toString();
			String JBLXRDH = res.get("JBLXRDH").toString();
			String SPR = res.get("SPR").toString();
			String SPRQ = res.get("SPRQ").toString();
			String XGR = res.get("XGR").toString();
			
			String XGSJ = res.get("XGSJ").toString();
			String OLD_LZZT = res.get("OLD_LZZT").toString();
			//String LZZT = res.get("LZZT").toString();
			
			System.out.println("1.�����ˣ�"+ZHKJSR);
			System.out.println("2.��һ״̬��"+OLD_LZZT);
			Tree tree = new Tree();
			
			String LZZT = tree.getNextState(ZHKJSR, OLD_LZZT);
			System.out.println("3.�·���־��"+tree.getSendType(ZHKJSR));
			System.out.println("4.��ת״̬��"+LZZT);
			
			String RYID = res.get("RYID").toString();
			String JGID = res.get("JGID").toString();
			String LXDH = res.get("LXDH").toString();
			
			System.out.println("res.get('LZZT').toString():"+res.get("LZZT").toString());
			LZZT = (!res.get("LZZT").toString().equals(""))?res.get("LZZT").toString():LZZT;
			
			TOAComplaintProcessBean ComplaintBean = new TOAComplaintProcessBean();
			ComplaintBean.setYwid(CPID);
			ComplaintBean.setYwlx("����Ͷ��");
			ComplaintBean.setReportperson(RYID);
			ComplaintBean.setReportunit(JGID);
			ComplaintBean.setReportkind(LZZT);
			if(!OLD_LZZT.equals(LZZT)){
				if (LZZT.equals("000007") || LZZT.equals("000013") || LZZT.equals("000019")) {
					// ָ�ӿ�����Ĵ���
					ComplaintBean.setReporttext(ZHKYJ);
					ZHKYJ="";
				} else if (LZZT.equals("000010") || LZZT.equals("000067") || LZZT.equals("000070")) {
					// ָ�Ӵ�����Ĵ���
					ComplaintBean.setReporttext(ZHCYJ);
					ZHCYJ="";
				} else if (LZZT.equals("000064") || LZZT.equals("000016") || LZZT.equals("000076")) {
					// ���쵼����Ĵ���
					ComplaintBean.setReporttext(JLDYJ);
					JLDYJ="";
				} else if (LZZT.equals("000022") || LZZT.equals("000031") || LZZT.equals("000052") || LZZT.equals("000073")) {
					// ҵ�����ܲ�������Ĵ���
					ComplaintBean.setReporttext(YWCYJ);
					YWCYJ="";
				}
				insertProcessInfo(ComplaintBean);
			}
			sql ="update t_oa_complaint";
			sql +=" set TSDJSJ=to_date('"+TSDJSJ+"','yyyy-mm-dd hh24:mi')"
				//+",DJRID='"+DJRID+"'"
				//+",DJJGID='"+DJJGID+"'"
				+",TSRXM='"+TSRXM+"'"
				+",TSRDH='"+TSRDH+"'"
				+",TSRJZ='"+TSRJZ+"'"
				+",TSRCP='"+TSRCP+"'"
				+",TSYWFLB='"+TSYWFLB+"'"
				+",TSYWZLB='"+TSYWZLB+"'"
				+",TSNR='"+TSNR+"'"
				+",TSJG='"+TSJG+"'"
				+",TSJH='"+TSJH+"'"
				//+",ZHKJSR='"+ZHKJSR+"'"
				+",ZHKLD='"+ZHKLD+"'"
				+",ZHKYJ='"+ZHKYJ+"'"
				//+",ZHCJSR='"+ZHCJSR+"'"
				+",ZHCLD='"+ZHCLD+"'"
				+",ZHCYJ='"+ZHCYJ+"'"
				//+",JLDJSR='"+JLDJSR+"'"
				+",JLD='"+JLD+"'"
				+",JLDYJ='"+JLDYJ+"'"
				+",YWCZGBM='"+YWCZGBM+"'"
				//+",YWCJSR='"+YWCJSR+"'"
				+",YWCZG='"+YWCZG+"'"
				+",YWCYJ='"+YWCYJ+"'"
				+",YWCCBR='"+YWCCBR+"'"
				+",LXDH='"+LXDH+"'"
				+",ZDBLYJ='"+ZDBLYJ+"'"
				//+",ZDJGID='"+ZDJGID+"'"
				+",ZDLD='"+ZDLD+"'"
				+",ZDYJ='"+ZDYJ+"'"
				//+",DDJGID='"+DDJGID+"'"
				+",BLJGSM='"+BLJGSM+"'"
				+",ISREPLYMASSES='"+ISREPLYMASSES+"'"
				+",JBDW='"+JBDW+"'"
				+",JBLXR='"+JBLXR+"'"
				+",JBLXRDH='"+JBLXRDH+"'"
				+",SPR='"+SPR+"'"
				+",SPRQ=to_date('"+SPRQ+"','yyyy-mm-dd')"
				+",XGR='"+XGR+"'"
				+",XGSJ=to_date('"+XGSJ+"','yyyy-mm-dd hh24:mi')";
				if(!res.get("LZZT").toString().equals("")){
					sql += ",LZZT='"+res.get("LZZT").toString()+"'";
				}else{
					sql += tree.getUpdateSql(ZHKJSR, OLD_LZZT);
				}
				//+",LZZT='"+LZZT+"'";
			sql +=" where CPID='"+CPID+"'";
			System.out.println("update:"+sql);
			flag = DBHandler.execute(sql);
			
		}catch(Exception e) {
			logger.error("�༭Ͷ����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * ɾ����·��Ϣ
	 * @param bh
	 * @return
	 */
	public boolean delteComplaintInfo(String cpid) {
		String sql = null;
		boolean flag = false;
		try {
			sql ="delete from t_oa_complaint";
			sql +=" where CPID='"+cpid+"'";
			flag = DBHandler.execute(sql);
		}catch(Exception e) {
			logger.error("ɾ��Ͷ����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * ��ѯһ����·��Ϣ
	 * @param bh
	 * @return
	 */
	public Object[] getComplaintInfo(String cpid) {
		String sql = null;
		Object[] obj=null;
		Object[] objresult=null;
		try {
			sql ="select toc.CPID,to_char(toc.TSDJSJ,'yyyy-mm-dd HH24:mi'),toc.DJRID,toc.DJJGID,toc.TSRXM" +
					",toc.TSRDH,toc.TSYWFLB,toc.TSYWZLB,toc.TSNR,toc.ZHKLD" +
					",toc.ZHKYJ,toc.ZHCLD,toc.ZHCYJ,toc.YWCZG,toc.YWCYJ" +
					",toc.JLD,toc.JLDYJ,toc.ZDJGID,toc.ZDLD,toc.ZDYJ" +
					",toc.DDJGID,toc.BLJGSM,toc.ISREPLYMASSES,toc.JBDW,toc.JBLXR" +
					",toc.JBLXRDH,toc.SPR,to_char(toc.SPRQ,'yyyy-mm-dd'),toc.XGR,to_char(toc.XGSJ,'yyyy-mm-dd HH24:mi')" +
					",toc.LZZT,toc.TSJG,toc.TSRJZ,toc.TSRCP,toc.TSJH" +
					",toc.YWCCBR,toc.ZDBLYJ,toc.ZHKJSR,toc.ZHCJSR,toc.JLDJSR" +
					",toc.YWCJSR,toc.LXDH,(select xm from t_sys_person where ryid=toc.DJRID),(select jgmc from t_sys_department where jgid=toc.DJJGID)" +
					" from t_oa_complaint toc";
			sql +=" where toc.CPID='"+cpid+"'";
			System.out.println("getSingle:"+sql);
			obj = DBHandler.getLineResult(sql);
			String YWCCBR = "";
			String YWCCBRNAME = "";
			if (obj != null){
				if( !(obj[35] == null || "".equals(obj[35].toString().trim())) ){
					YWCCBR = StringHelper.obj2str(obj[35]);
					YWCCBRNAME = getPersonName(YWCCBR,"��");
				}
			}
			System.out.println("�а���ID��"+YWCCBR);
			System.out.println("�а��ˣ�"+YWCCBRNAME);
			objresult= new Object[obj.length+1];
			for (int j = 0; j < objresult.length-1; j++) {
				System.out.println(j+": "+obj[j]);
				objresult[j]=obj[j];
			}
			System.out.println("42: "+YWCCBRNAME);
			objresult[objresult.length-1]=YWCCBRNAME;
			System.out.println("1��"+obj.length);
			System.out.println("2��"+objresult.length);
		}catch(Exception e) {
			logger.error("��ѯһ��Ͷ����Ϣ:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return objresult;
	}
	/**
	 * @�汾�ţ�1.0
	 * @throws Throwable 
	 * @����˵����������Ա����ַ����õ���Ա�����ַ���
	 * @����˵����
	 * @�������ڣ�
	 */
	public String getPersonName(String idStr,String divideStr){
		String personStr="";
		String[] personIds=idStr.split(divideStr);
		if(personIds!=null&&personIds.length>0){
			for(int i=0;i<personIds.length;i++){
				String sql="SELECT XM FROM T_SYS_PERSON WHERE RYID='"+personIds[i]+"'";
				personStr+=StringHelper.obj2str(DBHandler.getSingleResult(sql),"")+divideStr;
			}
		}		
		return personStr;
	}
	
	public String showWord(String ID) {
		ID = StringHelper.obj2str(ID, "");
		String sql = "select ID,NAME from T_OA_CODE where ID like '" + ID + "%' order by ID asc";
		Object[][] returnObj = DBHandler.getMultiResult(sql);
		StringBuffer result = new StringBuffer();
		for(int i=0;i<returnObj.length;i++){
			result.append(StringHelper.obj2str(returnObj[i][1])+"|");
		}
		return result.toString();
	}
	
	public String insertWord(String NAME){
		String sql = null;
		boolean flag = false;
		String result = null;
		try {
			String ID = DBHandler.getSingleResult("select max(ID)+1 from t_oa_code").toString();
			sql ="insert into t_oa_code(ID,NAME) values('01" + ID.substring(1) + "','"+NAME+"')";
			System.out.println("insertWord:"+sql);
			flag = DBHandler.execute(sql);
			if(flag) result = "����������ɹ���"; else result="����������ʧ�ܣ�";
		}catch(Exception e) {
			logger.error("������������Ϣ����:");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * OAҵ����ת����Ϣ�Ĳ���<br/>
	 * OAҵ����ת����Ϣ�Ĳ��봦��
	 * 
	 * @param tOAComplaintProcessBean
	 * @return true ����ɹ� false ����ʧ��
	 */
	public boolean insertProcessInfo(
			TOAComplaintProcessBean tOAComplaintProcessBean) {
		
		// ȡ�÷������
		String reportkind = StringHelper.obj2str(tOAComplaintProcessBean.getReportkind());
		
		String maxSequence = CreateSequence.getMaxForId("T_OA_COMPLAINTPROCESS", "OPID", 10);
		// ��ˮ��,ҵ��ID,ҵ������,���յ�λ,����ʱ��,������,��Ϣ������׼��,��Ϣ������,���͵�λ,����ʱ��,�����ı�,�������,����ID
		String sql = "insert into t_oa_complaintprocess (OPID,YWID,YWLX,RECIVEUNIT,RECIVETIME," +
				"RECIVEPERSON,RESPONSIBLEPERSON,REPORTPERSON,REPORTUNIT,REPORTTIME,REPORTTEXT,REPORTKIND,OPERATEID) ";
		sql = sql + " VALUES ('" + StringHelper.obj2str(maxSequence, "") + "',";
		sql = sql + "'" + StringHelper.obj2str(tOAComplaintProcessBean.getYwid(), "") + "',";
		sql = sql + "'" + StringHelper.obj2str(tOAComplaintProcessBean.getYwlx(), "") + "',";
		sql = sql + "'" + StringHelper.obj2str(tOAComplaintProcessBean.getReciveunit(), "") + "',";
		sql = sql + "to_date('" + StringHelper.obj2str(tOAComplaintProcessBean.getRecivetime(), "") + "','yyyy-mm-dd hh24:mi'),";
		sql = sql + "'" + StringHelper.obj2str(tOAComplaintProcessBean.getReciveperson(), "") + "',";
		sql = sql + "'" + StringHelper.obj2str(tOAComplaintProcessBean.getResponsibleperson(), "") + "',";
		sql = sql + "'" + StringHelper.obj2str(tOAComplaintProcessBean.getReportperson(), "") + "',";
		sql = sql + "'" + StringHelper.obj2str(tOAComplaintProcessBean.getReportunit(), "") + "',";
		// sql = sql + "to_date('" + StringHelper.obj2str(tOAComplaintProcessBean.getReporttime(), "") + "','yyyy-mm-dd hh24:mi'),";
		sql = sql + "SYSDATE,";
		sql = sql + "'" + StringHelper.obj2str(tOAComplaintProcessBean.getReporttext(), "") + "',";
		sql = sql + "'" + reportkind+ "',";
		sql = sql + "'" + StringHelper.obj2str(tOAComplaintProcessBean.getOperateid(), "") + "')";
		System.out.println("reportkind: "+reportkind);
		if(reportkind.equals("000052")){System.out.println("yes");};
		if (reportkind != null
				&& !reportkind.equals("")
				&& ( tOAComplaintProcessBean
						.getReporttext() != null)&&!tOAComplaintProcessBean.getReporttext().equals("") ) {
			System.out.println("111: ");

			// ���ݷ�������ж���Щ�Ự��Ϣ��Ҫ����Ự��
			if (reportkind.equals("000064") || reportkind.equals("000022")
					|| reportkind.equals("000052")
					|| reportkind.equals("000031")
					|| reportkind.equals("000019")
					|| reportkind.equals("000016")
					|| reportkind.equals("000013")
					|| reportkind.equals("000010")
					|| reportkind.equals("000007")
					|| reportkind.equals("000064")
					|| reportkind.equals("000067")
					|| reportkind.equals("000070")
					|| reportkind.equals("000073")
					|| reportkind.equals("000076")
					|| reportkind.equals("000079")) {
				System.out.println("insertProcessInfo: "+sql);
				return DBHandler.execute(sql);
			}
		}

		return false;
	}
	/**
	 * ����ҵ��idȡ��ҵ����ת��Ϣ<br/>
	 * 
	 * @param ywId
	 * @return
	 */
	public Object[][] getToaProcessInfo(String ywId) {

		ywId = StringHelper.obj2str(ywId, "");
		// ���ţ�ָ�ӿƣ�+�������+����+��ְ ʱ��
		// ������˳��ҵ�����ͣ���Ϣ�����ˣ�����ʱ�䣬�����ı�������������ְλ,�������
		String sql = "select A.YWLX, f_get_person(A.REPORTPERSON) personName, "
				+ "to_char(A.REPORTTIME,'yyyy-mm-dd hh24:mi'), A.REPORTTEXT, f_get_dept(B.SSJG) departmentName, "
				+ "f_get_name('013011',B.XRZW) dutyName, A.REPORTKIND from T_OA_COMPLAINTPROCESS A, T_SYS_PERSON B where A.YWID = '"
				+ ywId
				+ "' and A.REPORTPERSON = B.RYID order by REPORTTIME asc";
		Object[][] returnObj = DBHandler.getMultiResult(sql);

		return returnObj;
	}
	
// �ţ�׷�ӣ�	
	/**
	 * ������Ϣ��Ĳ���<br|>
	 * ������Ϣ��Ĳ���Ĳ���
	 * @param complaintId
	 * @param attachmentName
	 * @param attachmentModfiy
	 * @param filePath
	 * @return true ����ɹ� false ����ʧ��
	 */
	public boolean insertTComplaintAttachment(String complaintId,
			String attachmentName, String attachmentModfiy, String filePath,String fileType) {

		// ȡ�����squence
		String maxSequence = CreateSequence.getMaxForSeq(
				"SEQ_T_OA_ATTACHMENT", 12);
		String sql = "insert into T_OA_ATTACHMENT values ('"
				+ StringHelper.obj2str(maxSequence, "") + "','"
				+ StringHelper.obj2str(complaintId, "") + "','"
				+ fileType+ "','"
				+ "" + "','"
				+ "" + "','"
				+ StringHelper.obj2str(filePath, "") +  "','"
				+ StringHelper.obj2str(attachmentModfiy, "") + "')";
		return DBHandler.execute(sql);

	}
//	 �ţ�׷�ӣ�
	
	public  String getXml(HashMap res){
		String alarmTotalType = res.get("alarmTotalType").toString();
		String timeRadioType = res.get("timeRadioType").toString();
		String STARTTIME = res.get("STARTTIME").toString();
		String ENDTIME = res.get("ENDTIME").toString();
		String startmonth = res.get("startyear").toString()+"-"+res.get("startmonth").toString();;
		String endmonth = res.get("endyear").toString()+"-"+res.get("endmonth").toString();
		String yearSelect = res.get("yearSelect").toString();
		String monthSelect = res.get("monthSelect").toString();
		String TSJGID = res.get("TSJGID").toString();
		String jgbh = res.get("jgbh").toString();
		String departType = res.get("departType").toString();
		//this.timetype = StringHelper.obj2int(timetype, 0);
		//this.stattype = StringHelper.obj2int(stattype, 0);
		analyzeLength2AndHeader(timeRadioType, alarmTotalType, STARTTIME, ENDTIME, startmonth, endmonth, yearSelect, monthSelect,res.get("startyear").toString(),res.get("endyear").toString());
		System.out.println("506: "+departType);
		Object[][] arr = getData(timeRadioType, alarmTotalType, STARTTIME, ENDTIME, startmonth, endmonth, yearSelect, TSJGID,res.get("startyear").toString(),res.get("endyear").toString(),jgbh,departType);
		arr = dataDispose(arr,timeRadioType,alarmTotalType);
		String xml = bulidXml(arr,timeRadioType, alarmTotalType, STARTTIME, ENDTIME);
		System.out.println("����xml: "+xml);
		return xml;
	}
	
	private  void analyzeLength2AndHeader(String timetype,String stattype,String starttime,String endtime,String startmonth,String endmonth,String year,String monthSelect,String startyear, String endyear){
		if(timetype == null){
			return;
		}
		if(stattype.equals("1")){//��ʱ��ͳ��
			if(timetype.equals("4")){//��
				startmonth += "-01";
				//endtime += "-01";
				dateMap = new HashMap();
				if(startmonth.equals(endmonth + "-01")){
					dateMap.put(startmonth.substring(0, 7), 1);
					length2 = dateMap.size()+2;
					header = new String[length2];
					header[0] = "";
					header[1] = startmonth.substring(0, 7);
					header[2]="С��";
					return;
				}
				ArrayList<String> tempList = new ArrayList<String>();
				String strs[] = startmonth.split("-");
				Calendar ca = Calendar.getInstance();
				ca.set(StringHelper.obj2int(strs[0],0),StringHelper.obj2int(strs[1],0)-1,StringHelper.obj2int(strs[2],0));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				int i =1;
				while(!sdf.format(ca.getTime()).equals(endmonth)){
					dateMap.put(sdf.format(ca.getTime()),i);
					tempList.add(sdf.format(ca.getTime()));
					i++;
					ca.set(Calendar.MONTH, ca.get(Calendar.MONTH)+1);
				}
				dateMap.put(endmonth, i);
				tempList.add(endmonth);
				length2 = dateMap.size()+2;
				header = new String[length2];
				header[0] = "";
				try{
				for(int j=1;j<length2-1;j++){
					header[j] = tempList.get(j-1);
				}
				}catch(Exception e){
					logger.error("[Ͷ��ͳ��]" + "analyzeLength2AndHeader()���� \n");
					e.printStackTrace();
				}
				header[length2-1] = "С��";
			}else if(timetype.equals("2")){//��
				dateMap = new HashMap();
				if(starttime.equals(endtime)){
					dateMap.put(starttime, 1);
					length2 = dateMap.size()+2;
					header = new String[length2];
					header[0] = "";
					header[1] = starttime;
					header[2]="С��";
					return;
				}
				ArrayList<String> tempList = new ArrayList<String>();
				String strs[] = starttime.split("-");
				Calendar ca = Calendar.getInstance();
				ca.set(StringHelper.obj2int(strs[0],0),StringHelper.obj2int(strs[1],0)-1,StringHelper.obj2int(strs[2],0));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				int i =1;
				while(!sdf.format(ca.getTime()).equals(endtime)){
					dateMap.put(sdf.format(ca.getTime()),i);
					tempList.add(sdf.format(ca.getTime()));
					i++;
					ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH)+1);
				}
				dateMap.put(endtime, i);
				tempList.add(endtime);
				length2 = dateMap.size()+2;
				header = new String[length2];
				header[0] = "";
				try{
				for(int j=1;j<length2-1;j++){
					header[j] = tempList.get(j-1);
				}
				}catch(Exception e){
					logger.error("[Ͷ��ͳ��]" + "analyzeLength2AndHeader()���� \n");
					e.printStackTrace();
				}
				header[length2-1] = "С��";
			}else if(timetype.equals("3")) {//��
				System.out.println("596: ");
				weekList = getWeek(year,monthSelect);
				System.out.println("597: "+weekList.size());
				length2 = (weekList.size()/2+2);
				header = new String[length2];
				header[0] = "";
				header[1] = "��һ��"+weekList.get(0).substring(5,7)+"��"+weekList.get(0).substring(8, 10)+"��"+"��"+weekList.get(1).substring(5,7)+"��"+weekList.get(1).substring(8, 10)+"��";
				header[2] = "�ڶ���"+weekList.get(2).substring(5,7)+"��"+weekList.get(2).substring(8, 10)+"��"+"��"+weekList.get(3).substring(5,7)+"��"+weekList.get(3).substring(8, 10)+"��";
				header[3] = "������"+weekList.get(4).substring(5,7)+"��"+weekList.get(4).substring(8, 10)+"��"+"��"+weekList.get(5).substring(5,7)+"��"+weekList.get(5).substring(8, 10)+"��";
				header[4] = "������"+weekList.get(6).substring(5,7)+"��"+weekList.get(6).substring(8, 10)+"��"+"��"+weekList.get(7).substring(5,7)+"��"+weekList.get(7).substring(8, 10)+"��";
				if(length2 == 7){
					header[5] = "������"+weekList.get(8).substring(5,7)+"��"+weekList.get(8).substring(8, 10)+"��"+"��"+weekList.get(9).substring(5,7)+"��"+weekList.get(9).substring(8, 10)+"��";
					header[6] = "С��";
				}else if(length2 == 8){
					header[5] = "������"+weekList.get(8).substring(5,7)+"��"+weekList.get(8).substring(8, 10)+"��"+"��"+weekList.get(9).substring(5,7)+"��"+weekList.get(9).substring(8, 10)+"��";
					header[6] = "������"+weekList.get(10).substring(5,7)+"��"+weekList.get(10).substring(8, 10)+"��"+"��"+weekList.get(11).substring(5,7)+"��"+weekList.get(11).substring(8, 10)+"��";
					header[7] = "С��";
				}else{
					header[5] = "С��";
				}
			}else if(timetype.equals("5")) {//����
				length2 = 6;
				header = new String[length2];
				header[0] = "";
				header[1] = "��һ����";
				header[2] = "�ڶ�����";
				header[3] = "��������";
				header[4] = "���ļ���";
				header[5] = "С��";
			}else if(timetype.equals("6")) {//����
				length2 = 4;
				header = new String[length2];
				header[0] = "";
				header[1] = "�ϰ���";
				header[2] = "�°���";
				header[3] = "С��";
			} else if(timetype.equals("7")) {//��
				int startY = Integer.valueOf(startyear);
				int endY = Integer.valueOf(endyear);
				dateMap = new HashMap();
				length2 = (endY - startY) + 1 + 2;
				header = new String[length2];
				int count = 1;
				for (int i=0; i<length2; i++ ) {
					if (i == 0) {
						header[i] = "";
					} else if (i > 0 && (startY <= endY) ) {
						dateMap.put(String.valueOf(startY), count);
						header[i] = startY + " ��";
						count = count + 1;
						startY = startY + 1;
					} else {
						header[i] = "С��";
					}
				}
			}
		}else if(stattype.equals("2")){//����ת״̬ͳ��
			dateMap = new HashMap();
			String sql = "SELECT DISTINCT name, name FROM t_oa_code WHERE id like '00%'";
			System.out.println("������ת״̬ͳ�Ƶı�ͷ��"+sql);
			Object[][] TSLB = DBHandler.getMultiResult(sql.toString());
			ArrayList<String> tempList = new ArrayList<String>();
			for(int k=0;k<TSLB.length;k++){
				dateMap.put(TSLB[k][1],k+1);
				tempList.add(TSLB[k][1].toString());
			}
			
			/*String strs[] = starttime.split("-");
			Calendar ca = Calendar.getInstance();
			ca.set(StringHelper.obj2int(strs[0],0),StringHelper.obj2int(strs[1],0)-1,StringHelper.obj2int(strs[2],0));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			int i =1;
			while(!sdf.format(ca.getTime()).equals(endtime)){
				dateMap.put(sdf.format(ca.getTime()),i);
				tempList.add(sdf.format(ca.getTime()));
				i++;
				ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH)+1);
			}*/
			length2 = dateMap.size()+2;
			header = new String[length2];
			header[0] = "";
			for(int j=1;j<length2-1;j++){
				header[j] = tempList.get(j-1);
			}
			header[length2-1] = "С��";
		}else if(stattype.equals("3")){//��Ͷ�����ͳ��
			dateMap = new HashMap();
			String sql = "SELECT lbid, lbms FROM t_oa_complaint_type ORDER BY lbid";
			System.out.println("����Ͷ�����ͳ�Ƶı�ͷ��"+sql);
			Object[][] TSLB = DBHandler.getMultiResult(sql.toString());
			ArrayList<String> tempList = new ArrayList<String>();
			for(int k=0;k<TSLB.length;k++){
				dateMap.put(TSLB[k][1],k+1);
				tempList.add(TSLB[k][1].toString());
			}
			
			/*String strs[] = starttime.split("-");
			Calendar ca = Calendar.getInstance();
			ca.set(StringHelper.obj2int(strs[0],0),StringHelper.obj2int(strs[1],0)-1,StringHelper.obj2int(strs[2],0));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			int i =1;
			while(!sdf.format(ca.getTime()).equals(endtime)){
				dateMap.put(sdf.format(ca.getTime()),i);
				tempList.add(sdf.format(ca.getTime()));
				i++;
				ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH)+1);
			}*/
			length2 = dateMap.size()+2;
			header = new String[length2];
			header[0] = "";
			for(int j=1;j<length2-1;j++){
				header[j] = tempList.get(j-1);
			}
			header[length2-1] = "С��";
		}
	}
	/**
	 * ����ÿ�����ܵ���ֹʱ��<br/>
	 * ����ÿ�����ܵ���ֹʱ��Ĵ���
	 * @param year
	 * @param month
	 * @return
	 */
	public  ArrayList getWeek(String year,String month){
		System.out.println("597: "+year+ "--"+month);
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.YEAR, Integer.parseInt(year));
		ca.set(Calendar.MONTH, Integer.parseInt(month)-1);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		int ina = (ca.get(Calendar.DAY_OF_WEEK)+4)%7;
		ca.set(Calendar.DAY_OF_MONTH, -ina);
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		/*�����ʱ���*/
		ArrayList<String> weekList = new ArrayList<String>();
		
		date.setTime(ca.getTimeInMillis());
		weekList.add(sdf.format(date));
		for(int i =0;i<6 ;i++){
			ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH)+1);
			if(ca.get(Calendar.YEAR) > Integer.parseInt(year)||(ca.get(Calendar.YEAR) == Integer.parseInt(year)&&(ca.get(Calendar.MONTH) + 1) > Integer.parseInt(month))){
				break;
			}
			ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH)+5);
			date.setTime(ca.getTimeInMillis());
			weekList.add(sdf.format(date));
			ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH)+1);
			date.setTime(ca.getTimeInMillis());
			weekList.add(sdf.format(date));
		}
		weekList.remove(weekList.size()-1);
		return weekList;
	}
	/*public  ArrayList getWeek(String year,String month){
		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.YEAR, Integer.parseInt(year));
		ca.set(Calendar.MONTH, Integer.parseInt(month)-1);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		int ina = (ca.get(Calendar.DAY_OF_WEEK)+4)%7;
		ca.set(Calendar.DAY_OF_MONTH, -ina);
		
		
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy��MM��dd��");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		�����ʱ���
		ArrayList<String> weekList = new ArrayList<String>();
		
		date.setTime(ca.getTimeInMillis());
		weekList.add(sdf.format(date));
		for(int i =0;i<6 ;i++){
			ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH)+1);
			if(ca.get(Calendar.YEAR) > Integer.parseInt(year)||(ca.get(Calendar.YEAR) == Integer.parseInt(year)&&(ca.get(Calendar.MONTH) + 1) > Integer.parseInt(month))){
				break;
			}
			ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH)+5);
			date.setTime(ca.getTimeInMillis());
			weekList.add(sdf.format(date));
			ca.set(Calendar.DAY_OF_MONTH, ca.get(Calendar.DAY_OF_MONTH)+1);
			date.setTime(ca.getTimeInMillis());
			weekList.add(sdf.format(date));
		}
		weekList.remove(weekList.size()-1);
		return weekList;
	}*/
	
	private  Object[][] getData(String timetype,String stattype,String starttime,String endtime,String startmonth,String endmonth,String year,String TSJGID,String startyear,String endyear,String jgbh,String departType){
		System.out.println("787: "+ stattype);
		if(stattype.equals("1")){
			return getTSJG(timetype,starttime,endtime,startmonth,endmonth,year,TSJGID,startyear,endyear,jgbh,departType);
		}else if(stattype.equals("2")){
			return getLZZT(timetype,starttime,endtime,startmonth,endmonth,year,TSJGID,jgbh,departType);
		}else{
			return getYWLB(timetype,starttime,endtime,startmonth,endmonth,year,TSJGID,jgbh,departType);
		}
	}
	private  Object[][] getTSJG(String timetype,String starttime,String endtime,String startmonth,String endmonth,String year,String TSJGID,String startyear, String endyear,String jgbh,String departType){
		String zd = new String("TSJG");
		String dd = new String("");
		if(departType.equals("2")) zd="substr(TSJG,0,4)||'00000000'";
		if(departType.equals("3")) dd=" AND substr(TSJG,5,2) != '00' ";
		StringBuffer timeWhere = new StringBuffer("");
		Object[][] temp = null;
		if(timetype.equals("2")){//����ͳ��
			timeWhere.append(" WHERE TO_CHAR(TSDJSJ,'yyyy-mm-dd') >= '");
			timeWhere.append(starttime);
			timeWhere.append("' AND ");
			timeWhere.append("TO_CHAR(TSDJSJ,'yyyy-mm-dd') <= '");
			timeWhere.append(endtime);
			
			String timeWhereStr = timeWhere.toString();
			StringBuffer sql = new StringBuffer();
			
			sql.append("SELECT "+zd+",TO_CHAR(TSDJSJ,'yyyy-mm-dd'),COUNT(*) FROM t_oa_complaint ");
			sql.append(timeWhereStr);
			sql.append("' AND '"+TSJGID+"' like '%'||TSJG||'%' AND TSJG LIKE '"+jgbh+"%'"+dd+" GROUP BY "+ (zd.length()>4?zd.substring(0, 16):zd) +",TO_CHAR(TSDJSJ,'yyyy-mm-dd') ORDER BY "+(zd.length()>4?zd.substring(0, 16):zd)+" ,TO_CHAR(TSDJSJ,'yyyy-mm-dd') ");
			try{
				System.out.println("getTXJG����ͳ�ƣ�"+sql);
				temp  = DBHandler.getMultiResult(sql.toString());
				}catch(Exception e){
					logger.error("[Ͷ��ͳ��]" + "getTXJG()��ѯ����ʱSQL���� "+sql);
				}
		}else if(timetype.equals("3")) {//����ͳ��
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT "+zd+", PART, COUNT(*) FROM  ");
			sql.append("(SELECT TSJG,");
			sql.append("(CASE WHEN TO_CHAR(TSDJSJ,'yyyy-mm-dd')>='");
			sql.append(this.weekList.get(0));
			sql.append("' AND  TO_CHAR(TSDJSJ,'yyyy-mm-dd')<='");
			sql.append(this.weekList.get(1));
			sql.append("' THEN '1' ELSE ( CASE  WHEN  TO_CHAR(TSDJSJ,'yyyy-mm-dd')>='");
			sql.append(this.weekList.get(2));
			sql.append("' AND  TO_CHAR(TSDJSJ,'yyyy-mm-dd')<='");
			sql.append(this.weekList.get(3));
			sql.append("' THEN '2' ELSE (CASE WHEN  TO_CHAR(TSDJSJ,'yyyy-mm-dd')>='");
			sql.append(this.weekList.get(4));
			sql.append("' AND  TO_CHAR(TSDJSJ,'yyyy-mm-dd')<='");
			sql.append(this.weekList.get(5));
			sql.append("' THEN '3' ELSE (CASE  WHEN  TO_CHAR(TSDJSJ,'yyyy-mm-dd')>='");
			sql.append(this.weekList.get(6));
			sql.append("' AND  TO_CHAR(TSDJSJ,'yyyy-mm-dd')<='");
			sql.append(this.weekList.get(7));
			sql.append("' THEN '4' ");
			if(this.weekList.size()==10){
				sql.append(" ELSE (CASE WHEN  TO_CHAR(TSDJSJ,'yyyy-mm-dd')>='");
				sql.append(this.weekList.get(8));
				sql.append("' AND  TO_CHAR(TSDJSJ,'yyyy-mm-dd')<='");
				sql.append(this.weekList.get(9));
				sql.append("' THEN '5'   END) END) END) END) END) PART ,TSDJSJ");
			}else if(this.weekList.size()==12){
				sql.append(" ELSE (CASE WHEN  TO_CHAR(TSDJSJ,'yyyy-mm-dd')>='");
				sql.append(this.weekList.get(8));
				sql.append("' AND  TO_CHAR(TSDJSJ,'yyyy-mm-dd')<='");
				sql.append(this.weekList.get(9));
				sql.append("' THEN '5' ELSE (CASE WHEN  TO_CHAR(TSDJSJ,'yyyy-mm-dd')>='");
				sql.append(this.weekList.get(10));
				sql.append("' AND  TO_CHAR(TSDJSJ,'yyyy-mm-dd')<='");
				sql.append(this.weekList.get(11));
				sql.append("' THEN '6'    END) END) END) END) END) END) PART ,TSDJSJ");
			}else{
				sql.append("  END) END) END) END) PART ,TSDJSJ ");
			}
			sql.append("   FROM t_oa_complaint   ");
			sql.append(" WHERE  TO_CHAR(TSDJSJ,'yyyy-mm-dd') >= '");
			sql.append(this.weekList.get(0));
			sql.append("' AND TO_CHAR(TSDJSJ,'yyyy-mm-dd')<='");
			sql.append(this.weekList.get(this.weekList.size()-1));
			sql.append("') WHERE '"+TSJGID+"' like '%'||TSJG||'%' AND TSJG LIKE '"+jgbh+"%' "+dd+" GROUP BY "+ (zd.length()>4?zd.substring(0, 16):zd) +",PART  ORDER BY "+ (zd.length()>4?zd.substring(0, 16):zd) +", PART ");
			try{
				System.out.println("��ʱ�䡯���ܲ�ѯ��"+sql);
				temp = DBHandler.getMultiResult(sql.toString());
			} catch(Exception e) {
				logger.error("[Ͷ��ͳ��]" + "getTXJG()��ѯ����ʱSQL���� "+sql);
			}
		}else if(timetype.equals("4")){//����ͳ��
			timeWhere.append(" WHERE TO_CHAR(TSDJSJ,'yyyy-mm') >= '");
			timeWhere.append(startmonth);
			timeWhere.append("' AND ");
			timeWhere.append("TO_CHAR(TSDJSJ,'yyyy-mm') <= '");
			timeWhere.append(endmonth);
			String timeWhereStr = timeWhere.toString();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT "+zd+",TO_CHAR(TSDJSJ,'yyyy-mm'),COUNT(*) FROM t_oa_complaint ");
			sql.append(timeWhereStr);
			sql.append("' AND '"+TSJGID+"' like '%'||TSJG||'%' AND TSJG LIKE '"+jgbh+"%'"+dd+" GROUP BY "+ (zd.length()>4?zd.substring(0, 16):zd) +",TO_CHAR(TSDJSJ,'yyyy-mm')  ORDER BY "+ (zd.length()>4?zd.substring(0, 16):zd) +" ,TO_CHAR(TSDJSJ,'yyyy-mm') ");
			try{
				System.out.println("getTXJG����ͳ�ƣ�"+sql);
				temp  = DBHandler.getMultiResult(sql.toString());
				}catch(Exception e){
					logger.error("[Ͷ��ͳ��]" + "getTXJG()��ѯ����ʱSQL���� "+sql);
				}
		}else if(timetype.equals("5")) {// ������ͳ��ʱ��Ϣ��ȡ�ô���
			temp = getSeasonInfo(year,TSJGID,jgbh,dd,zd);
		}else if(timetype.equals("6")) {// ������ͳ��ʱ��Ϣ��ȡ�ô���
			temp = getHalfYearInfo(year,TSJGID,jgbh,dd,zd);
		}else if(timetype.equals("7")) {// ����ͳ��ʱ��Ϣ��ȡ�ô���
			String startYm = startyear;
			String endYm = endyear;
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT "+zd+",TO_CHAR(TSDJSJ,'yyyy'),COUNT(1) ");
			sql.append(" FROM   t_oa_complaint ");
			sql.append(" where  TO_CHAR(TSDJSJ,'yyyy') >= '");
			sql.append(startYm);
			sql.append("' and  TO_CHAR(TSDJSJ,'yyyy') <= '");
			sql.append(endYm);
			sql.append("' AND '"+TSJGID+"' like '%'||TSJG||'%' AND TSJG LIKE '"+jgbh+"%'"+dd+" group by "+ (zd.length()>4?zd.substring(0, 16):zd) +",TO_CHAR(TSDJSJ,'yyyy') ");
			sql.append(" order by "+ (zd.length()>4?zd.substring(0, 16):zd) +", TO_CHAR(TSDJSJ,'yyyy') desc");
			System.out.println("905: "+sql);
			try{
				temp  = DBHandler.getMultiResult(sql.toString());
			} catch (Exception e) {
				logger.error("[Ͷ��ͳ��]" + "getTXJG()��ѯ����ʱSQL���� "+sql);
			}
		}
		return temp;
	}
	
	/**
	 * ��ȡѡ����ȸ���������<br>
	 * @return
	 */
	public Object[][] getSeasonInfo(String year,String TSJGID,String jgbh,String dd,String zd) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(" SELECT "+zd+", PART, COUNT(*)");
		strBuffer.append(" FROM (SELECT TSJG,");
		strBuffer.append(" (CASE");
		strBuffer.append(" WHEN TO_CHAR(TSDJSJ, 'yyyy-mm') >= '"+ year+"-01' AND");
		strBuffer.append(" TO_CHAR(TSDJSJ, 'yyyy-mm') <= '"+ year+"-03' THEN");
		strBuffer.append(" '1'");
		strBuffer.append(" ELSE");
		strBuffer.append(" (CASE");
		strBuffer.append(" WHEN TO_CHAR(TSDJSJ, 'yyyy-mm') >= '"+ year+"-04' AND");
		strBuffer.append(" TO_CHAR(TSDJSJ, 'yyyy-mm') <= '"+ year+"-06' THEN");
		strBuffer.append(" '2'");
		strBuffer.append(" ELSE");
		strBuffer.append(" (CASE");
		strBuffer.append(" WHEN TO_CHAR(TSDJSJ, 'yyyy-mm') >= '"+ year+"-07' AND");
		strBuffer.append(" TO_CHAR(TSDJSJ, 'yyyy-mm') <= '"+ year+"-09' THEN");
		strBuffer.append(" '3'");
		strBuffer.append(" ELSE");
		strBuffer.append(" (CASE");
		strBuffer.append(" WHEN TO_CHAR(TSDJSJ, 'yyyy-mm') >= '"+ year+"-10' AND");
		strBuffer.append(" TO_CHAR(TSDJSJ, 'yyyy-mm') <= '"+ year+"-12' THEN");
		strBuffer.append(" '4'");
		strBuffer.append(" END) END) END) END) PART,");
		strBuffer.append(" TSDJSJ");
		strBuffer.append(" FROM t_oa_complaint");
		strBuffer.append(" WHERE TO_CHAR(TSDJSJ, 'yyyy') >= '"+ year+"')");
		strBuffer.append(" WHERE '"+TSJGID+"' like '%'||TSJG||'%' AND TSJG LIKE '"+jgbh+"%'"+dd+" GROUP BY "+ (zd.length()>4?zd.substring(0, 16):zd) +", PART");
		strBuffer.append(" ORDER BY "+ (zd.length()>4?zd.substring(0, 16):zd) +", PART");
		System.out.println("������SQL��"+strBuffer);
		Object[][] temp = null;
		try{
			temp  = DBHandler.getMultiResult(strBuffer.toString());
		} catch (Exception e) {
			logger.error("[Ͷ��ͳ��]" + "getSeasonOneInfo()��ѯ����ʱSQL���� "+strBuffer);
		}
		return temp;
	}
	/**
	 * ��ȡѡ��������°�������<br>
	 * @return
	 */
	public Object[][] getHalfYearInfo(String year,String TSJGID,String jgbh,String dd,String zd) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(" SELECT "+zd+", PART, COUNT(*)");
		strBuffer.append(" FROM (SELECT TSJG,");
		strBuffer.append(" (CASE");
		strBuffer.append(" WHEN TO_CHAR(TSDJSJ, 'yyyy-mm') >= '"+ year+"-01' AND");
		strBuffer.append(" TO_CHAR(TSDJSJ, 'yyyy-mm') <= '"+ year+"-06' THEN");
		strBuffer.append(" '1'");
		strBuffer.append(" ELSE");
		strBuffer.append(" (CASE");
		strBuffer.append(" WHEN TO_CHAR(TSDJSJ, 'yyyy-mm') >= '"+ year+"-07' AND");
		strBuffer.append(" TO_CHAR(TSDJSJ, 'yyyy-mm') <= '"+ year+"-12' THEN");
		strBuffer.append(" '2'");
		strBuffer.append(" END) END) PART,");
		strBuffer.append(" TSDJSJ");
		strBuffer.append(" FROM t_oa_complaint");
		strBuffer.append(" WHERE TO_CHAR(TSDJSJ, 'yyyy') >= '2010')");
		strBuffer.append(" WHERE '"+TSJGID+"' like '%'||TSJG||'%' AND TSJG LIKE '"+jgbh+"%'"+dd+" GROUP BY "+ (zd.length()>4?zd.substring(0, 16):zd) +", PART");
		strBuffer.append(" ORDER BY "+ (zd.length()>4?zd.substring(0, 16):zd) +", PART");
		System.out.println("980: "+strBuffer);
		Object[][] temp = null;
		try{
			temp  = DBHandler.getMultiResult(strBuffer.toString());
		} catch (Exception e) {
			logger.error("[�ش���ͳ��]" + "getSeasonOneInfo()��ѯ����ʱSQL���� "+strBuffer);
		}
		return temp;
	}
	
	private  Object[][] getLZZT(String timetype,String starttime,String endtime,String startmonth,String endmonth,String year,String TSJGID,String jgbh,String departType){
		String zd = new String("TSJG");
		String dd = new String("");
		if(departType.equals("2")) zd="substr(TSJG,0,4)||'00000000'";
		if(departType.equals("3")) dd=" AND substr(TSJG,5,2) != '00' ";
		StringBuffer timeWhere = new StringBuffer("");
		Object[][] temp = null;
		if(timetype.equals("2")){//����ͳ��
			timeWhere.append(" WHERE TO_CHAR(TSDJSJ,'yyyy-mm-dd') >= '");
			timeWhere.append(starttime);
			timeWhere.append("' AND ");
			timeWhere.append("TO_CHAR(TSDJSJ,'yyyy-mm-dd') <= '");
			timeWhere.append(endtime);
			String timeWhereStr = timeWhere.toString();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT "+zd+",NAME,COUNT(*) FROM t_oa_complaint,t_oa_code ");
			sql.append(timeWhereStr);
			sql.append("' AND '"+TSJGID+"' like '%'||TSJG||'%' AND TSJG LIKE '"+jgbh+"%' AND ID like '00%' AND LZZT=ID"+dd+" GROUP BY "+ (zd.length()>4?zd.substring(0, 16):zd) +",NAME ORDER BY "+ (zd.length()>4?zd.substring(0, 16):zd) +",NAME ");
			try{
				System.out.println("getLZZT����ͳ�ƣ�"+sql);
				temp  = DBHandler.getMultiResult(sql.toString());
				}catch(Exception e){
					logger.error("[Ͷ��ͳ��]" + "getTXJG()��ѯ����ʱSQL���� "+sql);
				}
		}else if(timetype.equals("4")){//����ͳ��
			timeWhere.append(" WHERE TO_CHAR(TSDJSJ,'yyyy-mm') >= '");
			timeWhere.append(startmonth);
			timeWhere.append("' AND ");
			timeWhere.append("TO_CHAR(TSDJSJ,'yyyy-mm') <= '");
			timeWhere.append(endmonth);
			String timeWhereStr = timeWhere.toString();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT TSJG,NAME,COUNT(*) FROM t_oa_complaint,t_oa_code ");
			sql.append(timeWhereStr);
			sql.append("' AND '"+TSJGID+"' like '%'||TSJG||'%' AND TSJG LIKE '"+jgbh+"%' AND ID like '00%' AND LZZT=ID GROUP BY TSJG,NAME  ORDER BY TSJG,NAME ");
			try{
				System.out.println("getLZZT����ͳ�ƣ�"+sql);
				temp  = DBHandler.getMultiResult(sql.toString());
				}catch(Exception e){
					logger.error("[Ͷ��ͳ��]" + "getTXJG()��ѯ����ʱSQL���� "+sql);
				}
		}		
		return temp;

	}
	private  Object[][] getYWLB(String timetype,String starttime,String endtime,String startmonth,String endmonth,String year,String TSJGID,String jgbh,String departType){
		String zd = new String("TSJG");
		String dd = new String("");
		if(departType.equals("2")) zd="substr(TSJG,0,4)||'00000000'";
		if(departType.equals("3")) dd=" AND substr(TSJG,5,2) != '00' ";
		StringBuffer timeWhere = new StringBuffer("");
		Object[][] temp = null;
		if(timetype.equals("2")){//����ͳ��
			timeWhere.append(" WHERE TO_CHAR(TSDJSJ,'yyyy-mm-dd') >= '");
			timeWhere.append(starttime);
			timeWhere.append("' AND ");
			timeWhere.append("TO_CHAR(TSDJSJ,'yyyy-mm-dd') <= '");
			timeWhere.append(endtime);
			String timeWhereStr = timeWhere.toString();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT "+zd+",LBMS,COUNT(*) FROM t_oa_complaint, t_oa_complaint_type");
			sql.append(timeWhereStr);
			sql.append("' and (TSYWFLB like '%'||LBID||'%' or TSYWZLB like '%'||LBID||'%') AND '"+TSJGID+"' like '%'||TSJG||'%' AND TSJG LIKE '"+jgbh+"%'"+dd+" GROUP BY "+ (zd.length()>4?zd.substring(0, 16):zd) +", LBMS ORDER BY "+ (zd.length()>4?zd.substring(0, 16):zd) +" ,LBMS ");
			try{
				System.out.println("getYWLB����ͳ�ƣ�"+sql);
				temp  = DBHandler.getMultiResult(sql.toString());
				}catch(Exception e){
					logger.error("[Ͷ��ͳ��]" + "getTXJG()��ѯ����ʱSQL���� "+sql);
				}
		}else if(timetype.equals("4")){//����ͳ��
			timeWhere.append(" WHERE TO_CHAR(TSDJSJ,'yyyy-mm') >= '");
			timeWhere.append(startmonth);
			timeWhere.append("' AND ");
			timeWhere.append("TO_CHAR(TSDJSJ,'yyyy-mm') <= '");
			timeWhere.append(endmonth);
			String timeWhereStr = timeWhere.toString();
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT TSJG,LBMS,COUNT(*) FROM t_oa_complaint, t_oa_complaint_type");
			sql.append(timeWhereStr);
			sql.append("' and (TSYWFLB like '%'||LBID||'%' or TSYWZLB like '%'||LBID||'%') AND '"+TSJGID+"' like '%'||TSJG||'%' AND TSJG LIKE '"+jgbh+"%' GROUP BY TSJG, LBMS ORDER BY TSJG ,LBMS ");
			try{
				System.out.println("getYWLB����ͳ�ƣ�"+sql);
				temp  = DBHandler.getMultiResult(sql.toString());
				}catch(Exception e){
					logger.error("[Ͷ��ͳ��]" + "getTXJG()��ѯ����ʱSQL���� "+sql);
				}
		}
		return temp;

	}
	
	private   Object[][] dataDispose(Object[][] arr,String timetype,String stattype){
		int tempTimeType = Integer.parseInt(timetype);//ͳ��ʱ�䷽ʽ
		HashMap<String,String> map = null;
		switch(Integer.parseInt(stattype)){
			case 1:map = getTSJGmap();break;
			case 2:map = getLZZTmap();break;
			case 3:map = getYWLBmap();break;
		}
		if(arr == null){
			return null;
		}
		String[] data = new String[length2];//length2:ͳ��������������2��
		ArrayList list = new ArrayList();
		String tempLH = "";
		int sum =0;
		if(arr.length!=0){
			tempLH = StringHelper.obj2str(arr[0][0]);//��һ�е�һ�еĻ���ID
		}
		int tempIndex =1;//�����±����
		System.out.println("Ӧִ�д�����"+arr.length);
		for(int i =0;i<arr.length;i++){
			System.out.println("��"+i+"��:");
			if(arr[i][0] == null){
				continue;//���·��Ϊ�ռ����˳�����ѭ��
			}
			if(stattype.equals("1")){
				System.out.println("StringHelper.obj2str(arr[i][1])):"+StringHelper.obj2str(arr[i][1]));
				switch(tempTimeType){
					case 1:tempIndex = StringHelper.obj2int(arr[i][1],0)+1;break;//Сʱ
					case 2:tempIndex = StringHelper.obj2int(this.dateMap.get(StringHelper.obj2str(arr[i][1])), 0);break;//��
					case 3:tempIndex = StringHelper.obj2int(arr[i][1],0);break;//��
					case 4:tempIndex = StringHelper.obj2int(this.dateMap.get(StringHelper.obj2str(arr[i][1])), 0);break;//��
					case 5:tempIndex = StringHelper.obj2int(arr[i][1], 0);break;// ����
					case 6:tempIndex = StringHelper.obj2int(arr[i][1], 0);break;// ����
					case 7:tempIndex = StringHelper.obj2int(this.dateMap.get(StringHelper.obj2str(arr[i][1])), 0);break;// ��
				}
			}else if(stattype.equals("2")){
				System.out.println("StringHelper.obj2str(arr["+i+"][0])): "+StringHelper.obj2str(arr[i][0]));
				System.out.println("StringHelper.obj2str(arr["+i+"][1])): "+StringHelper.obj2str(arr[i][1]));
				System.out.println("StringHelper.obj2str(arr["+i+"][2])): "+StringHelper.obj2str(arr[i][2]));
				tempIndex = StringHelper.obj2int(this.dateMap.get(StringHelper.obj2str(arr[i][1])), 0);//��
			}else if(stattype.equals("3")){
				System.out.println("StringHelper.obj2str(arr["+i+"][0])): "+StringHelper.obj2str(arr[i][0]));
				System.out.println("StringHelper.obj2str(arr["+i+"][1])): "+StringHelper.obj2str(arr[i][1]));
				System.out.println("StringHelper.obj2str(arr["+i+"][2])): "+StringHelper.obj2str(arr[i][2]));
				tempIndex = StringHelper.obj2int(this.dateMap.get(StringHelper.obj2str(arr[i][1])), 0);//��
			}
			if(tempLH.equals(arr[i][0].toString())){
				System.out.println("1066");
				sum = sum + StringHelper.obj2int(arr[i][2],0);
				data[tempIndex] = StringHelper.obj2str(arr[i][2]);
				System.out.println("1068");
			}else{
				data[0] = map.get(tempLH);
				data[length2-1] = StringHelper.obj2str(sum);
				tempLH = StringHelper.obj2str(arr[i][0]);
				list.add(data);
				 
				data = new String[length2];
				sum = StringHelper.obj2int(arr[i][2],0);
				data[tempIndex] = StringHelper.obj2str(arr[i][2]);
				System.out.println("1077");
			}
			if(i == arr.length-1){
				data[0] = map.get(tempLH);
				data[length2-1] = StringHelper.obj2str(sum);
				list.add(data);
				System.out.println("1083");
			}
			System.out.println("i: "+i);
		}
		Object[][] tempob=  new Object[list.size()][length2];
		Object[] tt = null;
		for(int i=0;i<list.size();i++){
			tt = (Object[])list.get(i);
			for(int j =0;j<tt.length;j++){
				tempob[i][j] = tt[j];
			}
		}
		Arrays.sort(tempob, new ArrayComparator());
		tempob = subArr(tempob);
		return tempob;
	}
	private  Object[][] subArr(Object[][] arr){
		try{
		if(arr == null){
			 return null;
		}
		Object[][] temp = null;
		if(arr.length<=20){//�������ڻ����20��/
			temp = new Object[arr.length+1][length2];
			/**
			 * ����
			 */
			for(int i=0;i<arr.length;i++){
				 for(int j =0;j<length2;j++){
					 temp[i][j] = arr[i][j];
				 }
			}
			/**
			 * end
			 */
			/**
			 * �������
			 */
			int sumTemp[] = new int[length2];
			for(int m=0;m<length2;m++){
				sumTemp[m] =0;
			}
			for(int k =0;k<temp.length;k++){
				for(int n =1;n<length2;n++){
					sumTemp[n] = sumTemp[n]+StringHelper.obj2int(temp[k][n], 0);
				}
			}
			for(int w = 1;w<length2;w++){
				temp[temp.length-1][w] = sumTemp[w];
			}
			temp[temp.length-1][0] = "С��";
			/**
			 * end
			 */
		}else{//���ݴ���20��/
			temp = new Object[22][length2];
			/**
			 * ����ǰ20��
			 */
			for(int i=0;i<20;i++){
				 for(int j =0;j<length2;j++){
					 temp[i][j] = arr[i][j];
				 }
			}
			/**
			 * end
			 */
			/**
			 * ��20��֮��ļ�¼�ϲ�
			 */
			int sumTemp[] = new int[length2];
			for(int m=1;m<length2;m++){
				sumTemp[m] =0;
			}
			for(int k =20;k<arr.length;k++){
				for(int n =1;n<length2;n++){
					sumTemp[n] = sumTemp[n]+StringHelper.obj2int(arr[k][n], 0);
				}
			}
			for(int w = 1;w<length2;w++){
				temp[20][w] = sumTemp[w];
			}
			temp[20][0] = "����";
			
			/**
			 * end
			 */
			
			/**
			 * �������
			 */
			for(int m=0;m<length2;m++){
				sumTemp[m] =0;
			}
			for(int k =0;k<temp.length;k++){
				for(int n =1;n<length2;n++){
					sumTemp[n] = sumTemp[n]+StringHelper.obj2int(temp[k][n], 0);
				}
			}
			for(int w = 1;w<length2;w++){
				temp[21][w] = sumTemp[w];
			}
			temp[21][0] = "С��";
			/**
			 * end
			 */
		}
		return temp;
		}catch(Exception e){
			logger.error("[�ۺϲ�ѯ]" + "subArr()���� \n");
			e.printStackTrace();
			return null;
		}
		
	}
	public  HashMap getTSJGmap(){
		HashMap<String,String> map = new HashMap<String,String>();
		String sql = "SELECT DISTINCT JGID, JGMC FROM t_sys_department ORDER BY JGID";
		System.out.println("getTSJGmap��"+sql);
		Object[][] lhlm = DBHandler.getMultiResult(sql.toString());
		String temp = null;
		String temp2 = "";
		if(lhlm !=null){
			for(int i =0; i<lhlm.length ; i++){
				if(lhlm[i][1] == null||lhlm[i][0]==null){
					continue;//���·�Ż�·��Ϊ�ռ����˳�����ѭ��
				}
				temp = map.get(StringHelper.obj2str(lhlm[i][0]));//��ȡ�����е�·��
				if(temp == null){
					//�����в�����·�������
					map.put(StringHelper.obj2str(lhlm[i][0]),StringHelper.obj2str(lhlm[i][1]));
				}else{
					//�����д���·����Ƚ�·�����̣�����·���滻��������
					temp2 = StringHelper.obj2str(lhlm[i][1]);
					if(temp2.length()<temp.length()){
						map.put(StringHelper.obj2str(lhlm[i][0]),temp2);
					}
				}
			}
		}
		return map;
	}
	public  HashMap getLZZTmap(){
		HashMap<String,String> map = new HashMap<String,String>();
		String sql = "SELECT DISTINCT JGID, JGMC FROM t_sys_department ORDER BY JGID";
		System.out.println("getLZZTmap��"+sql);
		Object[][] lhlm = DBHandler.getMultiResult(sql.toString());
		if(lhlm !=null){
			for(int i =0; i<lhlm.length ; i++){
					map.put(StringHelper.obj2str(lhlm[i][0]),StringHelper.obj2str(lhlm[i][1]));
			}
		}
		return map;
	}
	public  HashMap getYWLBmap(){
		HashMap<String,String> map = new HashMap<String,String>();
		String sql = "SELECT DISTINCT JGID, JGMC FROM t_sys_department ORDER BY JGID";
		System.out.println("getYWLBmap��"+sql);
		Object[][] lhlm = DBHandler.getMultiResult(sql.toString());
		if(lhlm !=null){
			for(int i =0; i<lhlm.length ; i++){
					map.put(StringHelper.obj2str(lhlm[i][0]),StringHelper.obj2str(lhlm[i][1]));
			}
		}
		return map;
	}
	
	private  String bulidXml(Object[][] arr,String timetype,String stattype,String starttime,String endtime){
		StringBuffer bf = new StringBuffer("<?xml version='1.0' encoding='UTF-8'?>");
		bf.append("<rows>");
		if(header!=null){
			bf.append("<row>");
			for( int i=0; i<header.length;i++){
				bf.append("<col>");
				bf.append(header[i]);
				bf.append("</col>");
			}
			bf.append("</row>");
		}
		System.out.println("��ͷxml��"+bf);
		StringBuffer sqlTemp = new StringBuffer("");
		try{
		if(arr != null){
			for( int i=0; i<arr.length;i++){
				bf.append("<row>");
				for( int j=0; j<arr[i].length;j++){
					bf.append("<col>");
					bf.append(StringHelper.obj2str(arr[i][j],"0"));
					bf.append("</col>");
				}
				bf.append("</row>");
			}
		}
		}catch(Exception e){
			logger.error("[Ͷ��ͳ��]" + "bulidXml()���� "+sqlTemp);
			e.printStackTrace();
		}
		bf.append("</rows>");
		return bf.toString();
	}
}
