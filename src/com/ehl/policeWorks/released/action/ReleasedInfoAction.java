/**
 * ��Ŀ���ƣ�EHL_ATMSIC_GD <br>
 * �ļ�·����com.ehl.policeWorks.released.action <br>
 * �ļ����ƣ�ReleasedInfoAction.java <br>
 * �ļ���ţ�   <br>
 * �ļ�������  <br>
 *
 * �汾��Ϣ�� Ver 1.1 <br>
 * �������ڣ�2012-3-26 16:10:07 <br>
 * ��˾���ƣ� �����׻�¼��Ϣ�����ɷ����޹�˾  2012 Copyright(C) ��Ȩ����    <br>
 **************************************************<br>
 * �����ˣ�Vasin  <br>
 * �������ڣ�2012��3��26��16:10:16<br>
 ************* �޸���ʷ  *************<br>
 * �޸��ˣ�Vasin   <br>
 * �޸�ʱ�䣺2012��3��26��16:10:22  <br>
 * �޸ı�ע��   <br>
 */
package com.ehl.policeWorks.released.action;

import com.appframe.action.Action;
import com.appframe.action.Controller;
import com.appframe.action.mapping.ActionForward;
import com.appframe.utils.StringHelper;
import com.ehl.policeWorks.released.service.ReleasedInfoService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * ��Ŀ���ƣ�EHL_ATMSIC_GD<br>
 * ��·����com.ehl.policeWorks.released.action  <br>
 * �����ƣ�ReleasedInfoAction  <br>
 * �ļ�������������Ϣ��ѯAction   <br>
 *
 * @see <br>
 *      �汾��Ϣ��Ver 1.1 <br>
 *      ********************************<br>
 *      �����ˣ�Vasin  <br>
 *      �������ڣ�2012��3��26��16:11:03  <br>
 *      ************ �޸���ʷ  *************<br>
 *      �޸��ˣ�Vasin   <br>
 *      �޸�ʱ�䣺2012��3��26��16:11:08  <br>
 *      �޸ı�ע��     <br>
 */
public class ReleasedInfoAction extends Controller {

    private Logger logger = Logger.getLogger(ReleasedInfoAction.class);

    /**
     * �������ƣ�doGetReleasedInfo<br>
     * ������������ȡ������Ϣ for jquery jqgrid <br>
     *
     * @param action
     * @param request
     * @param response
     * @return
     * @throws Throwable �汾��Ϣ��Ver 1.1 <br>
     *                   ********************************<br>
     *                   �����ˣ�Vasin  <br>
     *                   ����ʱ�䣺2012��3��26��16:12:01 <br>
     *                   ************ �޸���ʷ  *************<br>
     *                   �޸��ˣ�Vasin   <br>
     *                   �޸�ʱ�䣺2012��3��26��16:12:05  <br>
     *                   �޸ı�ע��    <br>
     */
    public ActionForward doGetReleasedInfo(Action action, HttpServletRequest request,
                                           HttpServletResponse response) throws Throwable {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Cache-Control", "no-store");
        PrintWriter out = response.getWriter();

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, String> hashMap = new HashMap<String, String>();
//        String startDate = StringHelper.obj2str(request.getParameter("startDate"), sdf.format(now));
//        String endDate = StringHelper.obj2str(request.getParameter("endDate"), sdf.format(now));
        String startDate = StringHelper.obj2str(request.getParameter("startDate"), "");
        String endDate = StringHelper.obj2str(request.getParameter("endDate"), "");
        String iDisplayLength = StringHelper.obj2str(request.getParameter("rows"), "10");
        String iDisplayStart = StringHelper.obj2str(request.getParameter("page"), "0");
        String sortIndex = StringHelper.obj2str(request.getParameter("sidx"), "1");
        String sord = StringHelper.obj2str(request.getParameter("sord"), "desc");
        String sSearch = StringHelper.obj2str(request.getParameter("sSearch"), "");
        String releasedDeptId = StringHelper.obj2str(request.getParameter("deptId"), "");
        String infoType = StringHelper.obj2str(request.getParameter("infoType"), "");
        hashMap.put("startDate", startDate);
        hashMap.put("endDate", endDate);
        hashMap.put("iDisplayLength", iDisplayLength);
        hashMap.put("iDisplayStart", iDisplayStart);
        hashMap.put("sortIndex", sortIndex);
        hashMap.put("sord", sord);
        hashMap.put("sSearch", sSearch);
        hashMap.put("deptId", releasedDeptId);
        hashMap.put("infoType", infoType);

        ReleasedInfoService releasedInfoService = new ReleasedInfoService();

        JSONObject json = releasedInfoService.getReleasedInfo(hashMap);
        out.print(json);

        out.close();
        return null;
    }
}
