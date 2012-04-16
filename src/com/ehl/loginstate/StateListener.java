package com.ehl.loginstate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.base.util.CreateSequence; 

public class StateListener {
	static Logger logger = Logger.getLogger(StateListener.class);
	
	/*
	 * @˵��������û�״̬
	 * @����: userName �û�����, sysID ��½ϵͳID, sysName ��½ϵͳ����, ip �û�ip��ַ
	 * @ʱ�䣺 2010-9-13 
	 * @���أ�boolean: true�����ɹ���false����ʧ��
	 */ 
	public static boolean addUserState(String id,String userName,String sysID,String sysName,String ip){
		boolean res =false;
		try{
			if(userName !=null && userName.length()>0 && ip !=null && ip.length()>0){
				//��ȡ�û��������������
				String getDeptSql="select deptcode,f_get_dept(deptcode) from t_sys_user where username='"+userName+"' ";
				Object[] resDept = DBHandler.getLineResult(getDeptSql);
				if(resDept !=null){ //�ж��Ƿ�������
					//Modify by Xiayx 2011-10-12
					//����û���ͬһIP���ظ���¼������¼��¼״̬��Ϣ
//					String sql="";
//					if(sysID!=null && sysID.length()>0 && sysName!=null && sysName.length()>0){
//						sql="insert into T_SYS_USERSTATE(id,Username,Deptid,Deptname,Sysid,Sysname,ip) ";
//						sql+="values('"+id+"','"+userName+"','"+resDept[0]+"','"+resDept[1]+"','"+sysID+"','"+sysName+"','"+ip+"')";
//					}else{
//						sql="insert into T_SYS_USERSTATE(id,Username,Deptid,Deptname,ip) ";
//						sql+="values('"+id+"','"+userName+"','"+resDept[0]+"','"+resDept[1]+"','"+ip+"')";
//					}
//					res = DBHandler.execute(sql);
					String ustateSql = "select count(*) from t_sys_userstate where username='"+userName+"' and ip='"+ip+"'";
					Object count = DBHandler.getSingleResult(ustateSql);
					if(String.valueOf(count).equals("0")){
						String sql="";
						if(sysID!=null && sysID.length()>0 && sysName!=null && sysName.length()>0){
							sql="insert into T_SYS_USERSTATE(id,Username,Deptid,Deptname,Sysid,Sysname,ip) ";
							sql+="values('"+id+"','"+userName+"','"+resDept[0]+"','"+resDept[1]+"','"+sysID+"','"+sysName+"','"+ip+"')";
						}else{
							sql="insert into T_SYS_USERSTATE(id,Username,Deptid,Deptname,ip) ";
							sql+="values('"+id+"','"+userName+"','"+resDept[0]+"','"+resDept[1]+"','"+ip+"')";
						}
						res = DBHandler.execute(sql);
					}else{
						String sql = "update t_sys_userstate set id='"+id+"' where username='"+userName+"' and ip='"+ip+"'";
						DBHandler.execute(sql);
					}
					//Modification finished
				}else{
					logger.error("����û�״̬�����û�����Ӧ����Ϊ�գ�sql:"+getDeptSql);
				}
				
			}else{
				logger.error("����û�״̬�����û�����IPΪ�գ�");
			}
		}catch(Exception ex){
			logger.error("����û�״̬" +ex.getMessage()+ " \n����");
			res=false;
		}
		return res;
	}
	
	/*
	 * @˵����ɾ���û�״̬
	 * @������ username �û�����, ip �û�ip��ַ
	 * @ʱ�䣺2010-9-13
	 * @���أ�boolean: true�����ɹ���false����ʧ��
	 */
	public static boolean deleteUserState(String id,String userName,String ip){
		boolean res =false;
		String sql="";
		try{
			sql = "delete from T_SYS_USERSTATE where id='"+id+"' and Username='"+userName+"' and ip='"+ip+"'";
			res=DBHandler.execute(sql);
		}catch(Exception ex){
			logger.error("ɾ���û�״̬" +ex.getMessage()+ " \n����sql��䣺"+sql);
			res=false;
		}
		return res;
	}
	
	/*
	 * @˵�����޸��û�״̬
	 * @����: userName �û�����, sysID ��½ϵͳID, sysName ��½ϵͳ����, ip �û�ip��ַ
	 * @ʱ�䣺 2010-9-14 
	 * @���أ�boolean: true�����ɹ���false����ʧ��
	 */ 
	public static boolean updateUserState(String userName,String sysID,String sysName,String ip){
		boolean res =false;
		String sql="";
		try{
			if(userName !=null && userName.length()>0 && ip !=null && ip.length()>0){
				sql="update T_SYS_USERSTATE set sysid='"+sysID+"',sysname='"+sysName+"' where username='"+userName+"' and ip ='"+ip+"'";
				res = DBHandler.execute(sql);
			}else{
				logger.error("����û�״̬�����û�����IPΪ�գ�");
			}
		}catch(Exception ex){
			logger.error("����û�״̬" +ex.getMessage()+ " \n����sql��䣺"+sql);
			res=false;
		}
		return res;
	}
	
	/**
	 * ������е�¼�û�
	 * @return
	 */
	public static boolean clearUserState(){
		boolean isOK = false;
		String sql = "delete from t_sys_userstate";
		try {
			isOK = DBHandler.execute(sql);
		} catch (Exception e) {
			logger.error("����û���¼���߼�¼�����쳣",e);
		}
		return isOK;
	}

	
	/*
	 * @˵����ͳ�Ƶ�½�û�
	 * @������ deptid �ܻ�������,deptid ���˻�������,sysid ����ϵͳ���
	 * @ʱ�䣺2010-9-13
	 * @���أ�Object[][]: ��ѯ���ؽ��
	 */ 
	private  StringBuffer xmlBuffer= new StringBuffer();
	private Object[][] allDept = null;
	private Object[][] allPerson = null;
	public String getStatisticUser(String deptId,String deptid,String sysid){
		try{
			String personSql = "select count(*) from t_sys_userstate where deptid=s.jgid ";
			if(sysid.trim().length()>0){
				personSql+="and sysid='"+sysid+"' ";
			}
			if(deptid.trim().length()>0){
				personSql+="and deptid='"+deptid+"' ";
			}
			String whereStr = "jgid<=445331000000 and length(jgid)=12 and substr(jgid,7)='000000'";//�ų��ơ��ҡ��ж�
			String sql="select jgid,jgmc||'&nbsp;('||("+personSql+")||')',jgccbm,jglx from t_sys_department s where "+whereStr+" and jgid='"+deptId+"' order by jgid";
			Object[] res=DBHandler.getLineResult(sql); 
			String allDeptSql="select jgid,jgmc||'&nbsp;('||("+personSql+")||')',jgccbm,jglx from t_sys_department s where "+whereStr+" order by jgid";
			allDept = DBHandler.getMultiResult(allDeptSql);
			xmlBuffer.append("<?xml version='1.0' encoding='UTF-8'?>\n");
			xmlBuffer.append("<tree>\n");
			xmlBuffer.append("<dept id='"+res[0]+"' text='"+res[1]+"' jgccbm='"+res[2]+"'>\n");
			 
			Object[][] persons = getPersonByDept(res[0].toString());
			xmlBuffer.append(setPersonXml(persons,res));
			setDeptPersonXml(res);
			xmlBuffer.append("</dept>\n");
			xmlBuffer.append("</tree>\n");
		}catch(Exception ex){
			ex.printStackTrace();
			logger.equals("��ȡ������Ա������:"+ex.getMessage());
		}
		return xmlBuffer.toString();
	}
	 
	
	/**
	 * ��ȡ��������Ա
	 * @param deptId
	 * @return
	 */
	private Object[][] getPersonByDept(String deptId){
		Object[][] personOfDept = null ;
		int resLen = 0;
		int j = 0;
		Collection list = new ArrayList();
		try{
			if(allPerson != null){
				for(int i = 0; i < allPerson.length; i ++){
					if(StringHelper.obj2str(allPerson[i][2], "").equals(deptId)){
						list.add(allPerson[i]);
					}
				}
			}
			resLen = list.size();
			personOfDept = new Object[resLen][3];
			Iterator it = list.iterator();
			while(it.hasNext()){
				personOfDept[j] = (Object[])it.next();
				j ++;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("PersonTree��ȡ���ŵ�������Ա�쳣:"+ex.getMessage());
		}
		return personOfDept;
	}
	
	/**
	 * ������Աxml
	 * @param persons
	 * @param dept
	 * @return
	 */
	private String setPersonXml(Object[][] persons,Object[] dept) throws Exception{
		StringBuffer personXml= new StringBuffer();
		if(persons!=null){
			for(int i=0;i<persons.length;i++){
				if(persons[i] == null){
					break;
				}
				personXml.append("<person id='");
				personXml.append(persons[i][0]);
				personXml.append("' name='");
				personXml.append(persons[i][1]);
				personXml.append("' deptId='");
				personXml.append(dept[0]);
				personXml.append("' ccbm='");
				personXml.append(dept[2]);
				personXml.append("'>\n");
				personXml.append("</person>\n");
			}
		}	
		return personXml.toString();
	}
	
	/**
	 * ���ɻ�����Աxml�ַ���(�Ľ���) zhaoyu 2010-09-25
	 * @param dept-�������,����,��α��,���� 
	 * @return
	 */
	private String setDeptPersonXml(Object[] dept) throws  Exception{
		if(dept == null){
			return null;
		}
		Object[][] persons = null;
		Object[][] res = childsDept(dept[2].toString());
		if(res!=null){
			for(int i=0;i<res.length;i++){
				xmlBuffer.append("<dept id='");
				xmlBuffer.append(res[i][0]);
				xmlBuffer.append("' text='");
				xmlBuffer.append(res[i][1]);
				xmlBuffer.append("' jgccbm='");
				xmlBuffer.append(res[i][2]);
				xmlBuffer.append("'>\n");
				persons = getPersonByDept(res[i][0].toString());
				xmlBuffer.append(setPersonXml(persons,res[i]));
				setDeptPersonXml(res[i]);
				xmlBuffer.append("</dept>\n");
			}
		}
		return xmlBuffer.toString();
	}
	/**
	 * ��ȡ���ŵ���������
	 * @param ccbm-������α���
	 * @return
	 * @modify zhaoyu 2010-9-21
	 */
	private Object[][] childsDept(String ccbm){
		Object[][] res = null ;
		int len = ccbm.length();
		int resLen = 0;
		int j = 0;
		String jgccbm = "";
		Collection list = new ArrayList();
		try{
			if(allDept != null){
				for(int i = 0; i < allDept.length; i ++){
					jgccbm = allDept[i][2].toString();
					if(jgccbm.length()==(len+2) && ccbm.equals(jgccbm.substring(0, len))){
						list.add(allDept[i]);
					}
				}
			}
			resLen = list.size();
			res = new Object[resLen][4];
			Iterator it = list.iterator();
			while(it.hasNext()){
				res[j] = (Object[])it.next();
				j ++;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			logger.error("PersonTree��ȡ���ŵ����������쳣:"+ex.getLocalizedMessage());
		}
		return res;
	}

	/**
	 * ȡ�õ�ǰȫ����������<br/>
	 * ȡ�õ�ǰȫ����������
	 * @return
	 */
	public String getAllUserCount(){
		Object[] res = null;
		try{
			String userCountSql = "select count(*) from t_sys_userstate";
			res = DBHandler.getLineResult(userCountSql); 
		}catch(Exception ex){
			ex.printStackTrace();
			logger.equals("��ȡȡ�õ�ǰȫ��������������:"+ex.getMessage());
		}
		return StringHelper.obj2str(res[0]);
	}
}
