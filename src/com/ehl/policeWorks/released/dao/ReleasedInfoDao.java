/**
 * ��Ŀ���ƣ�EHL_ATMSIC_GD <br>
 * �ļ�·����com.ehl.policeWorks.released.dao <br>
 * �ļ����ƣ�ReleasedInfoDao.java <br>
 * �ļ���ţ�   <br>
 * �ļ�������  <br>
 *
 * �汾��Ϣ�� Ver 1.1 <br>
 * �������ڣ�2012-3-26 <br>
 * ��˾���ƣ� �����׻�¼��Ϣ�����ɷ����޹�˾  2012 Copyright(C) ��Ȩ����    <br>
 **************************************************<br>
 * �����ˣ�Vasin  <br>
 * �������ڣ�2012��3��26��16:15:31<br>
 ************* �޸���ʷ  *************<br>
 * �޸��ˣ�Vasin   <br>
 * �޸�ʱ�䣺2012��3��26��16:15:35  <br>
 * �޸ı�ע��   <br>
 */
package com.ehl.policeWorks.released.dao;

import com.appframe.data.sql.DBHandler;
import org.apache.log4j.Logger;

/**
 * ��Ŀ���ƣ�EHL_ATMSIC_GD<br>
 * ��·����com.ehl.policeWorks.released.dao  <br>
 * �����ƣ�ReleasedInfoDao  <br>
 * �ļ�������������ϢDao   <br>
 *
 * @see <br>
 *      �汾��Ϣ��Ver 1.1 <br>
 *      ********************************<br>
 *      �����ˣ�Vasin  <br>
 *      �������ڣ�2012��3��26��16:15:48  <br>
 *      ************ �޸���ʷ  *************<br>
 *      �޸��ˣ�Vasin   <br>
 *      �޸�ʱ�䣺2012��3��26��16:15:52  <br>
 *      �޸ı�ע��     <br>
 */
public class ReleasedInfoDao {

    Logger logger = Logger.getLogger(ReleasedInfoDao.class);

    /**
     * �������ƣ�getInfo<br>
     * ���������� ��ȡ��Ϣ�б� <br>
     *
     * @param sql
     * @return Object[][]<br>
     *         �汾��Ϣ��Ver 1.1 <br>
     *         ********************************<br>
     *         �����ˣ�Vasin  <br>
     *         ����ʱ�䣺2012��3��26��16:16:26 <br>
     *         ************ �޸���ʷ  *************<br>
     *         �޸��ˣ�Vasin   <br>
     *         �޸�ʱ�䣺2012��3��26��16:16:39  <br>
     *         �޸ı�ע��    <br>
     */
    public Object[][] getInfo(String sql) {
        Object[][] result = null;
        if (sql != null && !("".equals(sql))) {
            result = DBHandler.getMultiResult(sql);
        } else {
            logger.error("��������=====>SQL:" + sql);
        }
        return result;
    }

    /**
     * �������ƣ�getCount<br>
     * ���������� ��ȡ��Ϣ���� <br>
     *
     * @param sql
     * @return Object<br>
     *         �汾��Ϣ��Ver 1.1 <br>
     *         ********************************<br>
     *         �����ˣ�Vasin  <br>
     *         ����ʱ�䣺2012��3��26��16:16:08 <br>
     *         ************ �޸���ʷ  *************<br>
     *         �޸��ˣ�Vasin   <br>
     *         �޸�ʱ�䣺2012��3��26��16:16:14  <br>
     *         �޸ı�ע��    <br>
     */
    public Object getCount(String sql) {
        Object result = null;
        if (sql != null && !("".equals(sql))) {
            result = DBHandler.getSingleResult(sql);
        } else {
            logger.error("��������=====>SQL:" + sql);
        }
        return result;
    }
}
