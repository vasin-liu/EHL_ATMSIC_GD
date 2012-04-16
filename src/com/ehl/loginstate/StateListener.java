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
	 * @说明：添加用户状态
	 * @参数: userName 用户名称, sysID 登陆系统ID, sysName 登陆系统名称, ip 用户ip地址
	 * @时间： 2010-9-13 
	 * @返回：boolean: true操作成功，false操作失败
	 */ 
	public static boolean addUserState(String id,String userName,String sysID,String sysName,String ip){
		boolean res =false;
		try{
			if(userName !=null && userName.length()>0 && ip !=null && ip.length()>0){
				//获取用户机构编码和名称
				String getDeptSql="select deptcode,f_get_dept(deptcode) from t_sys_user where username='"+userName+"' ";
				Object[] resDept = DBHandler.getLineResult(getDeptSql);
				if(resDept !=null){ //判断是否有数据
					//Modify by Xiayx 2011-10-12
					//如果用户在同一IP上重复登录，不记录登录状态信息
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
					logger.error("添加用户状态错误：用户名对应机构为空！sql:"+getDeptSql);
				}
				
			}else{
				logger.error("添加用户状态错误：用户名或IP为空！");
			}
		}catch(Exception ex){
			logger.error("添加用户状态" +ex.getMessage()+ " \n错误：");
			res=false;
		}
		return res;
	}
	
	/*
	 * @说明：删除用户状态
	 * @参数： username 用户名称, ip 用户ip地址
	 * @时间：2010-9-13
	 * @返回：boolean: true操作成功，false操作失败
	 */
	public static boolean deleteUserState(String id,String userName,String ip){
		boolean res =false;
		String sql="";
		try{
			sql = "delete from T_SYS_USERSTATE where id='"+id+"' and Username='"+userName+"' and ip='"+ip+"'";
			res=DBHandler.execute(sql);
		}catch(Exception ex){
			logger.error("删除用户状态" +ex.getMessage()+ " \n错误：sql语句："+sql);
			res=false;
		}
		return res;
	}
	
	/*
	 * @说明：修改用户状态
	 * @参数: userName 用户名称, sysID 登陆系统ID, sysName 登陆系统名称, ip 用户ip地址
	 * @时间： 2010-9-14 
	 * @返回：boolean: true操作成功，false操作失败
	 */ 
	public static boolean updateUserState(String userName,String sysID,String sysName,String ip){
		boolean res =false;
		String sql="";
		try{
			if(userName !=null && userName.length()>0 && ip !=null && ip.length()>0){
				sql="update T_SYS_USERSTATE set sysid='"+sysID+"',sysname='"+sysName+"' where username='"+userName+"' and ip ='"+ip+"'";
				res = DBHandler.execute(sql);
			}else{
				logger.error("添加用户状态错误：用户名或IP为空！");
			}
		}catch(Exception ex){
			logger.error("添加用户状态" +ex.getMessage()+ " \n错误：sql语句："+sql);
			res=false;
		}
		return res;
	}
	
	/**
	 * 清除所有登录用户
	 * @return
	 */
	public static boolean clearUserState(){
		boolean isOK = false;
		String sql = "delete from t_sys_userstate";
		try {
			isOK = DBHandler.execute(sql);
		} catch (Exception e) {
			logger.error("清除用户登录在线记录数据异常",e);
		}
		return isOK;
	}

	
	/*
	 * @说明：统计登陆用户
	 * @参数： deptid 总机构编码,deptid 过滤机构编码,sysid 过滤系统编号
	 * @时间：2010-9-13
	 * @返回：Object[][]: 查询返回结果
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
			String whereStr = "jgid<=445331000000 and length(jgid)=12 and substr(jgid,7)='000000'";//排除科、室、中队
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
			logger.equals("获取机构人员树报错:"+ex.getMessage());
		}
		return xmlBuffer.toString();
	}
	 
	
	/**
	 * 获取部门内人员
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
			logger.error("PersonTree获取部门的下属人员异常:"+ex.getMessage());
		}
		return personOfDept;
	}
	
	/**
	 * 生成人员xml
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
	 * 生成机构人员xml字符串(改进型) zhaoyu 2010-09-25
	 * @param dept-机构编号,名称,层次编号,类型 
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
	 * 获取部门的下属机构
	 * @param ccbm-机构层次编码
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
			logger.error("PersonTree获取部门的下属机构异常:"+ex.getLocalizedMessage());
		}
		return res;
	}

	/**
	 * 取得当前全部在线人数<br/>
	 * 取得当前全部在线人数
	 * @return
	 */
	public String getAllUserCount(){
		Object[] res = null;
		try{
			String userCountSql = "select count(*) from t_sys_userstate";
			res = DBHandler.getLineResult(userCountSql); 
		}catch(Exception ex){
			ex.printStackTrace();
			logger.equals("获取取得当前全部在线人数出错:"+ex.getMessage());
		}
		return StringHelper.obj2str(res[0]);
	}
}
