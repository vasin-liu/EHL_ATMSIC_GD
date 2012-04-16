package com.ehl.tfm.business.ebi;

import java.util.Map;

public interface FlowLineEbi {
	
    /**
     * <b>��ȡ������vml�ַ���.</b>
     * param:deptId-����ID ��Ϊ�����ѯȫ��������Ϣ 
     * param:roadStatus-Ҫ��ȡ��·�����ࣨ��block-ӵ�¡�����flow-��ͨ������crowd-ӵ����,"all"��
     * param:zoomLevel-��ͼ�Ŵ���
     * param:isRedraw-�Ƿ��ػ�
     * @return ������vml�ַ���.
     */
    public String getLineVmlStr(String deptId,String roadStatus,int zoomLevel,boolean isRedraw) throws Exception;
    /**
	 * @���ߣ�zhaoyu
	 * @�汾�ţ�1.0
	 * @����˵����ͬ��t_tfm_linkdir,CSDLW_PLͼ���t_gis_linkdir_segs������
	 * @�������ڣ�2010-5-4
	 * @���أ�ͬ�������ʾ��
	 */
    public String synLinkDir() throws Exception;
   
}
