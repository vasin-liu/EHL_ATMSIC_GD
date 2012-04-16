<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 
 <!-- 
	 * 
	 * 版 权：北京易华录信息技术股份有限公司 2009
	 * 文件名称：accidentAndBlock.jsp
	 * 摘 要：事故和拥堵信息
	 * 当前版本：1.0
	 * 作 者：LChQ  2009-4-22
	 * 修改人：
	 * 修改日期：
 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head> 
    <title>警情明细</title> 
  </head> 
  <body>
   <TABLE cellSpacing=0 cellPadding=2 width="100%" border=0
			align="center"
			style="border-left: 1 #caa solid; border-top: 1 #caa solid;">
			<TBODY>
				<tr
					style="background-color: blue; color: white; line-height: 16px; font-size: 9pt;">
					<td align=center style="color: white">
						警情相关信息
					</td>
				</tr>
				<TR>
					<TD align="center">
						<TABLE cellSpacing=0 cellPadding=2 width="100%">
							<TBODY>
								
								<TR class="detailTR1">
									<TD class=queryTD1>
										接警单号：
									</TD>
									<TD class=queryTD2 id="tdAlarmId">

									</TD>
									<TD class=queryTD1>
										接警单位：
									</TD>
									<TD class=queryTD2  id="tdInceptCounty">

									</TD>
									<TD class=queryTD1>
										接警人：
									</TD>
									<TD class=queryTD2 id="tdInceptPerson">

									</TD>
									
								</TR>
								<TR class="detailTR1">
									<TD class=queryTD1>
										接警时间：
									</TD>
									<TD class=queryTD2 id="tdInceptTime">

									</TD>
									
									<TD class=queryTD1>
										报警时间：
									</TD>
									<TD class=queryTD2 id="tdCallTime">

									</TD>
									<TD class=queryTD1>
										报警方式：
									</TD>
									<TD class=queryTD2   id="tdInceptManner">
 									
									
								</TR>
								<TR class="detailTR1" id="">
									<TD class=queryTD1>
										报警电话：
									</TD>
									<TD class=queryTD2 id="tdReportTel">
									</TD>									
									<TD class=queryTD1>
										机主姓名：
									</TD>
									<TD class=queryTD2 id="tdPhoneOwner"></TD>
									<TD class=queryTD1>
										机主地址：
									</TD>
									<TD class=queryTD2 id="tdPhoneOwnerAddress"></TD>
									
									
								</TR>
								<TR class="detailTR1">
									<TD class=queryTD1>
										报警人：
									</TD>
									<TD class=queryTD2 id="tdReportPerson">
									</TD>
									<TD class=queryTD1>
										报警人联系电话：
									</TD>
									<TD class=queryTD2 id="tdConcatTel">
									</TD>
									
									<TD class=queryTD1>
										性别：
									</TD>
									<TD class=queryTD2   id="tdReportPersonSex">
									</TD>
									
								</TR>
								<TR class="detailTR1">
									<TD class=queryTD1>
										报警人联系住址：
									</TD>
									<TD class=queryTD2 id="tdReportPersonAddress"></TD>
									<TD class=queryTD1>
										案发地址：
									</TD>
									<TD   class=queryTD2 id="tdOccurLocation">

									</TD>
									<TD class=queryTD1>
										辖区：
									</TD>
									<TD class=queryTD2 id="tdOccurCounty">

									</TD>
								</TR>
								<TR class="detailTR1">

									<TD class=queryTD1>
										报警类型：
									</TD>
									<TD class=queryTD2 id="tdAlarmType">

									</TD>
									<TD class=queryTD1>
										报警细类：
									</TD>
									<TD class=queryTD2 colspan=3 id="tdAlarmSubType"></TD>
								</TR>
								<TR class="detailTR1">
									<TD class=queryTD1>

										报警车号牌种类：
									</TD>
									<TD class=queryTD2 id="tdVechileNumStyle">&nbsp;</TD>
									<TD class=queryTD1>
										报警车牌号：
									</TD>
									<TD class=queryTD2 colspan=3 id="tdVechileRegNumber">&nbsp;</TD>
								</TR>
								<TR class="detailTR1" id="trHurtCount">
									<TD class=queryTD1>
										受伤人数：
									</TD>
									<TD class=queryTD2 id="tdInjuredCount">&nbsp;</TD>
									<TD class=queryTD1>
										死亡人数：
									</TD>
									<TD class=queryTD2 id="tdDeadCount">&nbsp;</TD>
									<TD class=queryTD1>
										是否危化车辆：
									</TD>
									<TD class=queryTD2 id="tdIsDangerCargo">

									</TD>
								</TR>
								<TR class="detailTR1">
									<TD class=queryTD1>
										报警内容：
									</TD>
									<TD colSpan=5 class=queryTD2 id="tdAlarmContent">

									</TD>
								</TR>
								<TR>
									<TD class=queryTD1>
										处警时间：
									</TD>
									<TD class=queryTD2 id="tdHandleTime">

									</TD>
									<TD class=queryTD1>
										处警人：
									</TD>
									<TD colSpan=3 class=queryTD2 id="tdHandlePerson">
									</TD>
								</TR>
								<TR class="detailTR1">
									<TD class=queryTD1>
										处警意见：
									</TD>
									<TD colSpan=5 class=queryTD2 id="tdHandleOpinion"></TD>
								</TR>
								<TR>
									<TD class=queryTD1>
										受（出）警单位：
									</TD>
									<TD class=queryTD2 id="tdDispatchCounty">
									</TD>
									<TD class=queryTD1>
										受（出）警人：
									</TD>
									<TD colSpan=3 class=queryTD2 id="tdDispatchPerson">

									</TD>
								</TR>
								<TR class="detailTR1">
									<TD class=queryTD1>
										其他相关警情单：
									</TD>
									<TD class=queryTD2 colspan=5 id="tdRelatedAlarms">
										&nbsp;
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				<tr
					style="background-color: blue; color: white; line-height: 16px; font-size: 9pt;">
					<td align=center style="color: white">
						警情处理反馈
					</td>
				</tr>
				<TR >
					<TD align="center">
						<TABLE cellSpacing=0 cellPadding=2 width="100%">
							<TBODY>
								
								<TR style="display: none">
									<TD class=queryTD1>
										指挥中心接收人
									</TD>
									<TD class=queryTD2 id="tdCmdCenterIncepter">&nbsp;
									</TD>
									<TD class=queryTD1>
										出警人员
									</TD>
									<TD class=queryTD2 id="tdDispatchPerson02">&nbsp;

									</TD>
									<TD class=queryTD1>
										出警时间
									</TD>
									<TD class=queryTD2 id="tdDispatchTime">

									</TD>
								</TR>
								<TR>
									

									<TD class=queryTD1>
										案件类型
									</TD>
									<TD class=queryTD2 id="tdCaseType">

									</TD>
									<TD class=queryTD1>
										案件细类
									</TD>
									<TD class=queryTD2 id="tdCaseSubType" colspan=3">&nbsp;

									</TD>
								</TR>
								<TR>
									<TD class=queryTD1>
										事件程度
									</TD>
									<TD class=queryTD2 id="tdAffairRank" >&nbsp;</TD>
									<TD class=queryTD1>
										案件发生时间
									</TD>
									<TD class=queryTD2 id="tdCaseHappen"></TD>
									<TD class=queryTD1 >
										案件结束时间
									</TD>
									<TD class=queryTD2 id="tdCompleteTime">
									</TD>
								</TR>
								<TR>
									<TD class=queryTD1>
										经济损失
									</TD>
									<TD class=queryTD2 id="tdEconomyLosing">&nbsp;</TD>
									
									<TD class=queryTD1>
										影响程度
									</TD>
									<TD class=queryTD2  colspan=3 id="tdFluencyDepth">&nbsp;</TD>
									
								</TR>
								<TR id="trWoundCount">
									<TD class=queryTD1>
										轻伤人数
									</TD>
									<TD class=queryTD2 id="tdWoundedCount">&nbsp;</TD>
									<TD class=queryTD1>
										重伤人数
									</TD>
									<TD class=queryTD2 id="tdHurtCount">&nbsp;</TD>
									<TD class=queryTD1>
										死亡人数
									</TD>
									<TD class=queryTD2 id="tdDeadCount02">&nbsp;</TD>
								</TR>
								<TR style="display: none">
									<TD class=queryTD1>
										出动车辆
									</TD>
									<TD class=queryTD2 id="tdDispatchCar">&nbsp;</TD>
									<TD class=queryTD1>
										出动人数
									</TD>
									<TD colSpan=3 class=queryTD2 id="tdDispatchPersonCount">&nbsp;</TD>
								</TR>

								<TR id="trRelatedPersonCount">
									<TD class=queryTD1>
										抓获人数
									</TD>
									<TD class=queryTD2 id="tdUnderarestCount">&nbsp;</TD>
									<TD class=queryTD1>
										涉案人数
									</TD>
									<TD class=queryTD2 id="tdRelatedCount">&nbsp;</TD>
									<TD class=queryTD1>
										救助人数
									</TD>
									<TD class=queryTD2 id="tdHelpedCount">&nbsp;</TD>
								</TR>
								<TR>
									<TD class=queryTD1>
										是否破获刑事案件
									</TD>
									<TD class=queryTD2 id="tdIsLawCase">

									</TD>
									<TD class=queryTD1>
										是否查处治安案件
									</TD>
									<TD class=queryTD2 id="tdIsSocialCase">

									</TD>
									<TD class=queryTD1>
										是否解决纠纷
									</TD>
									<TD class=queryTD2 id="tdIsMistakeCase">

									</TD>
								</TR>
								<TR style="display: none">
									<TD class=queryTD1>
										处理结果
									</TD>
									<TD colSpan=5 class=queryTD2 id="tdFeedbackContent">
										&nbsp;
									</TD>
								</TR>
								<TR>
									<TD class=queryTD1>
										是否反馈终结
									</TD>
									<TD colSpan=3 class=queryTD2 id="tdIsFeedbackComplete">
										&nbsp;
									</TD>
									<TD class=queryTD1>
										是否与其他接警单归并
									</TD>
									<TD class=queryTD2 id="tdHasMergeFile">
										&nbsp;
									</TD>
								</TR>
								<tr>
									<td align="left" colSpan=6 class=queryTD3>
										出警记录：
									</td>
								</tr>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
				
				
				
				<tr>
					<td align=center>
						<div id="feedbackTableDiv">
						</div>
					</td>
				</tr>
				
				
			</TBODY>
		</TABLE>
		<div style="width:100%" align="right">
						<input type="button" value=" 打 印 "  class="Noprint" onclick="window.print();" />
						&nbsp;&nbsp;
						<input type="button" value=" 关 闭 "  class="Noprint" onclick="window.close();" />
						&nbsp;&nbsp;
						&nbsp;&nbsp;
		</div>
  </body>
</html>
