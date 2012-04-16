package com.ehl.acctask;

import org.apache.log4j.Logger;

import com.appframe.data.sql.DBHandler;
import com.appframe.utils.StringHelper;
import com.ehl.webgis.data.SDEAccess;
import com.ehl.webgis.util.PointUtil;

public class AccSp {
	PointUtil pgs = null;//����·���
	PointUtil pacc = null;//
	private Logger loger = Logger.getLogger(AccSp.class);

	public AccSp() {
	}
	
	/**
	 * ��ѯ��Ҫ��λ���¹�
	 * @param obj
	 */
	public void spAcc(String time){
		try {
			Object acc[][] = getAcc("");
			if (acc != null && acc.length != 0) {
				//��λ��ʼ��
				System.out.println("��λ��ʼ����");
				SDEAccess sde = new SDEAccess();
				try {
					pgs = new PointUtil(sde.initConnection(), "LCB_PT");
				} catch (Exception e) {
					loger.error("��̱�ͼ���ʼ��ʧ�ܣ�"+e);
				}finally {
					sde.closeAO();
				}
				//��λ
				for (int i = 0; i < acc.length; i++) {
					addPoint(acc[i]);
				}
			}else{
				System.out.println("��ѯ����δ��λ���¹���Ϣ��");
			}
		} catch (Exception e) {
			loger.error("�¹ʶ�λʧ�ܣ�"+e);
		}
		System.out.println("�¹ʶ�λ������");
	}
	
	/**
	 * ��ȡ�¹���Ϣ
	 * @param start ��λ��ʼʱ��
	 */
	private Object [][] getAcc(String start){
		StringBuffer sb = new StringBuffer();
		Object acc[][] = null;
	
		sb.append("SELECT ACC.GLS,ACC.MS,ACC.XZQH,ACC.SGBH,ACC.SGFSSJ,ACC.SGLX,ACC.SWRS,ACC.SSRS,ACC.SWRSQ,ACC.SZRS,ACC.SGDD,ACC.JYAQ,ACC.LH,ACC.LM,ACC.XQ");
		sb.append(" FROM ACC_FILE ACC,T_OA_ROADDEPARTMENT ROAD");
		sb.append(" WHERE ACC.XZQH = ROAD.XZQH AND ACC.LH = ROAD.LH AND (ACC.GLS!=0 OR ACC.MS!=0)");
		
		if("".equals(start)){
			//Ĭ�ϲ�ѯ48Сʱ���ϱ��¹�
			sb.append(" AND ACC.SGFSSJ>=(SYSDATE-2)");
		}else{
			sb.append(" AND ACC.SGFSSJ>=TO_DATE('"+StringHelper.obj2str(start, "")+"','YYYY-MM-DD HH24:MI:SS')");
		}
		try {
			acc = DBHandler.getMultiResult(sb.toString());
		} catch (Exception e) {
			loger.error("���¹ʱ��ȡδ��λ�¹���Ϣʧ�ܣ�"+e);
		}
		return acc;
	}
	
	/**
	 * ���¹�ͼ��д���¹ʵ�
	 * @param obj
	 */
	private void addPoint(Object[] obj) {
		boolean isSpSuccess = false;
		try {
			//�õ����¹ʵ�������·��sde����id�͹���id
			Object[] DLobj = getDLBH(obj[2],obj[12]);//��ȡ���ٱ��
			if(DLobj != null && DLobj.length != 0){
				int qmz = StringHelper.obj2int(obj[0], 0);
				int bmz = StringHelper.obj2int(obj[1], 0);
				if(bmz>=0&&bmz<1000){
					if(bmz%100!=0){
						bmz = (int)bmz/100*100;
					}
				}else{
					bmz = 0;
				}
				Object[][] points = pgs.getFieldsByCondition("QMZBH ='"+qmz+"KM' AND BMZBH ='"+bmz+"M' AND DH = '"+DLobj[1]+"'", "SHAPE");
				if(points != null && points.length != 0){
					if(points[0][0] != null){
						String[] xy = points[0][0].toString().split(",");
						try {
							StringBuffer sb = new StringBuffer();
							sb.append("INSERT INTO T_TIRA_ACD_C_ACDFILE");
							sb.append(" (GLS,MS,XZQH,SGBH,SGFSSJ,SGLX,SWRS,SSRS,SWRSQ,SZRS,SGDD,JYAQ,LH,LM,XQ,X,Y)");
							sb.append(" VALUES('"+qmz+"','"+bmz+"','"+StringHelper.obj2str(obj[2], "")+"',");
							sb.append("'"+StringHelper.obj2str(obj[3], "")+"',TO_DATE('"+StringHelper.obj2str(obj[4], "")+"','YYYY-MM-DD HH24:MI:SS'),");
							sb.append("'"+StringHelper.obj2str(obj[5], "")+"','"+StringHelper.obj2str(obj[6], "")+"',");
							sb.append("'"+StringHelper.obj2str(obj[7], "")+"','"+StringHelper.obj2str(obj[8], "")+"',");
							sb.append("'"+StringHelper.obj2str(obj[9], "")+"','"+StringHelper.obj2str(obj[10], "")+"',");
							sb.append("'"+StringHelper.obj2str(obj[11], "")+"','"+StringHelper.obj2str(obj[12], "")+"',");
							sb.append("'"+StringHelper.obj2str(obj[13], "")+"','"+StringHelper.obj2str(obj[14], "")+"',");
							sb.append("'"+xy[0]+"','"+xy[1]+"'");
							isSpSuccess = DBHandler.execute(sb.toString());
						} catch (Exception e) {
							loger.error("��λ�¹���Ϣʧ�ܣ�"+e);
						}
					}
				}
			}
		} catch (Exception e) {
			loger.error("��λ�¹���Ϣʧ�ܣ�"+e);
		}
		
		/**
		 * ��λʧ��
		 */
		if(!isSpSuccess){
			loger.error("��λ�¹���Ϣʧ�ܣ�xzqh"+StringHelper.obj2str(obj[2], "")+";sgbh"+StringHelper.obj2str(obj[3], ""));
		}
	}
	
	/**
	 *  ��ȡ��·��ź͸��ٱ��
	 * @param XQ ��������;lh ·��
	 */
	private Object[] getDLBH(Object xq,Object lh){
		String sql = "SELECT DISTINCT LAYERROADID,GBID FROM T_OA_ROAD_INFO WHERE ROADID = (SELECT DISTINCT ROADID FROM T_OA_ROADDEPARTMENT WHERE XZQH ='"+xq+"' AND LH = '"+lh+"')";
		Object[] DLobj = null;
		try{
			DLobj = DBHandler.getLineResult(sql);
		} catch (Exception e) {
			loger.error("��ȡ��·��ź͸��ٱ��ʧ�ܣ�"+e+"/n"+sql+"/n");
		}
		return DLobj;
	}
}
