/**
 * 项目名称：EHL_ATMSIC_GD <br>
 * 文件路径：com.ehl.policeWorks.released.dao <br>
 * 文件名称：ReleasedInfoDao.java <br>
 * 文件编号：   <br>
 * 文件描述：  <br>
 *
 * 版本信息： Ver 1.1 <br>
 * 创建日期：2012-3-26 <br>
 * 公司名称： 北京易华录信息技术股份有限公司  2012 Copyright(C) 版权所有    <br>
 **************************************************<br>
 * 创建人：Vasin  <br>
 * 创建日期：2012年3月26日16:15:31<br>
 ************* 修改历史  *************<br>
 * 修改人：Vasin   <br>
 * 修改时间：2012年3月26日16:15:35  <br>
 * 修改备注：   <br>
 */
package com.ehl.policeWorks.released.dao;

import com.appframe.data.sql.DBHandler;
import org.apache.log4j.Logger;

/**
 * 项目名称：EHL_ATMSIC_GD<br>
 * 包路径：com.ehl.policeWorks.released.dao  <br>
 * 类名称：ReleasedInfoDao  <br>
 * 文件描述：发布信息Dao   <br>
 *
 * @see <br>
 *      版本信息：Ver 1.1 <br>
 *      ********************************<br>
 *      创建人：Vasin  <br>
 *      创建日期：2012年3月26日16:15:48  <br>
 *      ************ 修改历史  *************<br>
 *      修改人：Vasin   <br>
 *      修改时间：2012年3月26日16:15:52  <br>
 *      修改备注：     <br>
 */
public class ReleasedInfoDao {

    Logger logger = Logger.getLogger(ReleasedInfoDao.class);

    /**
     * 方法名称：getInfo<br>
     * 方法描述： 获取信息列表 <br>
     *
     * @param sql
     * @return Object[][]<br>
     *         版本信息：Ver 1.1 <br>
     *         ********************************<br>
     *         创建人：Vasin  <br>
     *         创建时间：2012年3月26日16:16:26 <br>
     *         ************ 修改历史  *************<br>
     *         修改人：Vasin   <br>
     *         修改时间：2012年3月26日16:16:39  <br>
     *         修改备注：    <br>
     */
    public Object[][] getInfo(String sql) {
        Object[][] result = null;
        if (sql != null && !("".equals(sql))) {
            result = DBHandler.getMultiResult(sql);
        } else {
            logger.error("参数错误=====>SQL:" + sql);
        }
        return result;
    }

    /**
     * 方法名称：getCount<br>
     * 方法描述： 获取信息总数 <br>
     *
     * @param sql
     * @return Object<br>
     *         版本信息：Ver 1.1 <br>
     *         ********************************<br>
     *         创建人：Vasin  <br>
     *         创建时间：2012年3月26日16:16:08 <br>
     *         ************ 修改历史  *************<br>
     *         修改人：Vasin   <br>
     *         修改时间：2012年3月26日16:16:14  <br>
     *         修改备注：    <br>
     */
    public Object getCount(String sql) {
        Object result = null;
        if (sql != null && !("".equals(sql))) {
            result = DBHandler.getSingleResult(sql);
        } else {
            logger.error("参数错误=====>SQL:" + sql);
        }
        return result;
    }
}
