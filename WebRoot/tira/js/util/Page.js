

var page = new Page();

function Page(projectname,statobjname,statItem) {
	
	this.statobjnameId = "statobjnameId";
	
	this.itemDetailTdId = "itemDetailTdId";
	this.statItemDetailTdId = "statItemDetailTdId";
	this.statItemDetailDescTdId = "statItemDetailDescTdId";
	this.statItemDetailCodeTdId = "statItemDetailCodeTdId";
	this.statItemDetailCodeSelId = "statItemDetailCodeSelId";
	this.floatItemDetailTdId = "floatItemDetailTdId";
	this.floatItemDetailDescTdId = "floatItemDetailDescTdId";
	this.floatItemDetailCodeTdId = "floatItemDetailCodeTdId";
	this.floatItemDetailCodeSelId = "floatItemDetailCodeSelId";

	
	this.itemTdId = "itemTdId";
	this.statItemTdId = "statItemTdId";
	this.statItemDescTdId = "statItemDescTdId";
	this.statItemCodeTdId = "statItemCodeTdId";
	this.statItemCodeSelId = "statItemCodeSelId";
	this.statItemValuesTdId = "statItemValuesTdId";
	this.statItemValuesSelId = "statItemValuesSelId";
	this.floatItemTdId = "floatItemTdId";
	this.floatItemDescTdId = "floatItemDescTdId";
	this.floatItemCodeTdId = "floatItemCodeTdId";
	this.floatItemCodeSelId = "floatItemCodeSelId";
	this.floatItemValuesTdId = "floatItemValuesTdId";
	this.floatItemValuesSelId = "floatItemValuesSelId";
	this.dataItemTdId = "dataItemTdId";
	this.dataItemDescTdId = "dataItemDescTdId";
	this.dataItemCodeTdId = "dataItemCodeTdId";
	this.dataItemCodeSelId = "dataItemCodeSelId";
	
	this.selId = "selId";
	this.selDivisionId = "selDivisionId";
	this.selDivisionDescId = "selDivisionDescId";
	this.selDivisionCodeId = "selDivisionCodeId";
	this.selDateId = "selDateId";
	this.selDateDescId = "selDateDescId";
	this.selDateCodeId = "selDateCodeId";
	
	this.importPart = "";
	this.label = "您当前的位置";
	this.projectname = "综合分析系统";
	this.statobjname = "事故分析";
	this.statItem = "事故原因";
	this.statItemDescPost = "分析";
	this.labelSep = " -> ";
	this.labelDesc = this.projectname+this.labelSep+"<span id='"+this.statobjnameId+"'>"+this.statobjname+"</span>"
					+this.statItemDescPost+this.labelSep+"<span>"+this.statItem+"</span>"
					+this.statItemDescPost;
	this.headPart = 
		"<!-- 头部 -->\
		<body style='background:none; padding-top:0px;' >\
		<table width='100%' height='100%' border='0' cellspacing='0' cellpadding='0'>\
		<tr>\
			<td height='30' class='wTableTopCenter'>\
				<table width='100%' border='0' cellspacing='0' cellpadding='0'>\
					<tr>\
						<td width='12' height='30' class='wTableTopLeft'></td>\
						<td>\
							<table width='100%' border='0' cellspacing='0' cellpadding='0'>\
								<tr>\
									<td width='46%' valign='middle'>\
										<table width='100%' border='0' cellspacing='0'\
											cellpadding='0'>\
											<tr>\
												<td width='5%'>\
													<div align='center'>\
														<img src='../../../base/image/cssimg/table/tb.gif' width='16' height='16' alt='img' />\
													</div>\
												</td>\
												<td width='70%' class='currentLocation'>\
													<span  class='currentLocationBold'>"+this.label+"</span>：" + this.labelDesc +"\
												</td>\
											</tr>\
										</table>\
									</td>\
								</tr>\
							</table>\
						</td>\
						<td width='16' class='wTableTopRight'></td>\
					</tr>\
				</table>\
			</td>\
		</tr>\
		<!-- 头部end -->";
	this.dataStartPart = 
		"<!-- 数据 -->\
		<tr>\
			<td>\
				<table height='100%' width='100%' border='0' cellspacing='0' cellpadding='0'>\
					<tr>\
						<td class='wTableCenterLeft'></td>\
						<td class='wTableCenterCenter' valign='top'>\
							<table width='100%' border='0' cellspacing='0' cellpadding='0'>\
								<tr>\
									<td align='left' class='height'></td>\
								</tr>\
								<tr>\
									<td align='left'>\
										<table width='141' border='0' cellspacing='0' cellpadding='0'>\
											<tr>\
												<td class='sleek textB'>分析条件</td>\
											</tr>\
										</table>\
									</td>\
								</tr>";
		
	this.statItemDetailPart = 
		"<table border='0'>\
			<tr style='font-size:12px;'>\
				<td id='"+this.statItemDetailDescTdId+"'>\
						事故原因类型：\
				</td>	\
				<td id='"+this.statItemDetailCodeTdId+"'>\
						<select id='"+this.statItemDetailCodeSelId+"' style='width:90px;'>\
							<option value='base'>基本类型</option>\
							<option value='base'>小类</option>\
							<option value='base'>大类</option>\
						</select>\
				</td>	\
			</tr>\
		</table>";
	this.floatItemDetailPart = 
		"<table border='0'>\
			<tr style='font-size:12px;'>\
				<td id='"+this.floatItemDetailDescTdId+"'>\
					日期类型：\
				</td>	\
				<td id='"+this.floatItemDetailCodeTdId+"'>\
					<select>\
							<option value='base'>时</option>\
							<option value='base'>日</option>\
							<option value='base'>周</option>\
							<option value='base'>月</option>\
							<option value='base'>季</option>\
							<option value='base'>年</option>\
							<option value='base'>段</option>\
					</select>\
				</td>	\
			</tr>\
		</table>";

	this.itemDetailPart = 
		"<table border='0' width='100%'>\
			<tr>\
				<td id='"+this.statItemDetailTdId+"'  align='left' >\
					"+this.statItemDetailPart+"\
				</td>\
				<td id='"+this.floatItemDetailTdId+"'  align='left'>\
					"+this.floatItemDetailPart+"\
		        </td>\
       		</tr>\
	     </table>";
	
    this.statItemPart = 
    	"<table border='0'>\
			<tr style='font-size:12px;'>\
				<td id='"+this.statItemDescTdId+"'> \
					事 故 原 因 ：\
				</td>	\
				<td id='"+this.statItemCodeTdId+"'> \
					<select>\
							<option value='a' label='机动车失控' /> \
							<option value='b' label='酒后驾驶' /> \
							<option value='c' label='超速' /> \
							<option value='d' label='恶意违法' /> \
					</select>\
				</td>	\
			</tr>\
		</table>";
    this.floatItemPart = 
    	"<table border='0'>\
			<tr style='font-size:12px;'>\
				<td id='"+this.floatItemDescTdId+"'>\
					起止日期：\
				</td>	\
				<td id='"+this.floatItemCodeTdId+"'>\
					<input id='dateStartId' style='width:70px;' value='起始日期'  onClick='SelectDate(this,0);' readOnly/> -  \
					<input id='dateEndId' style='width:70px;' value='结束日期'   onClick='SelectDate(this,0);' readOnly/> \
				</td>	\
			</tr>\
		</table>";
			
			
    this.dataItemPart = 
		"<table border='0'>\
			<tr style='font-size:12px;'>\
				<td id='"+this.dataItemDescId+"'>\
					数据类型：\
				</td>	\
				<td id='"+this.dataItemCodeId+"'>\
					<select>\
							<option value='base'>事故起数</option>\
							<option value='base'>受伤人数</option>\
							<option value='base'>死亡人数</option>\
							<option value='base'>财产损失</option>\
					</select>\
				</td>	\
			</tr>\
		</table>";
    this.itemPart = 
		"<table border='0' width='100%'>\
			<tr>\
				<td id='"+this.statItemTdId+"' align='left' >\
					"+this.statItemPart+"\
				</td>\
				<td id='"+this.floatItemTdId+"' align='left'>\
					"+this.floatItemPart+"\
		        </td>\
		        <td id='"+this.dataItemTdId+"' align='left' >\
				    "+this.dataItemPart+"\
		        </td>\
       		</tr>\
	     </table>";
   
    this.selDivisionPart = 
    	"<table border='0'>\
			<tr style='font-size:12px;'>\
				<td id='"+this.selDivisionDescId+"'>\
					行 政 区 划 ：\
				</td>	\
				<td id='"+this.selDivisionCodeId+"'>\
					<script>\
				     	division.createTable('"+this.selDivisionCodeId+"');\
						division.dmlb = dmlb;\
						division.getJgidByUname(uname);\
					</script>\
				</td>	\
			</tr>\
		</table>";
    this.selDatePart = 
    	"<table border='0'>\
			<tr style='font-size:12px;'>\
				<td id='"+this.floatItemDescTdId+"'>\
					起止日期：\
				</td>	\
				<td id='"+this.floatItemCodeTdId+"'>\
					<input id='dateStartId' style='width:70px;' value='起始日期'  onClick='SelectDate(this,0);' readOnly/> -  \
					<input id='dateEndId' style='width:70px;' value='结束日期'   onClick='SelectDate(this,0);' readOnly/> \
				</td>	\
			</tr>\
		</table>";
       			
		
    this.selPart = 
    	"<table border='0' width='100%'>\
			<tr>\
				<td id='"+this.selDivisionId+"' align='left' >\
					"+this.selDivisionPart+"\
				</td>\
       		</tr>\
       		<td id='"+this.selDateId+"' align='left'>\
				\
	       </td>\
	     </table>";
  
    this.selectPart = 
		"<!-- 查询条件部分 -->\
		<tr>\
			<td class='sleektd'>\
				<table width='100%' border='0' cellspacing='0' cellpadding='0'>\
					<tr height='25' style='font-size:12px;'>\
						<td rowspan='3'>&nbsp; </td>\
						<td id='"+this.itemDetailTdId+"'   >\
						    "+this.itemDetailPart+"\
		                </td>\
		                <td rowspan='3' width='100px;' align='center' valign='center'>\
		                	<div class='search' align='right'>\
								<a href='javascript:accCase.getData()'><span class='lbl'>分析</span></a>\
							</div>\
						</td>\
				    </tr>\
				    <tr height='25' style='font-size:12px;'>\
				    	<td id='"+this.itemTdId+"'  >\
							"+this.itemPart+"\
						</td>\
				    </tr>\
				    <tr height='25' style='font-size:12px;'>\
				    	<td id='"+this.selId+"'  >\
							"+this.selPart+"\
						</td>\
				    </tr>\
				</table>\
			</td>\
		</tr>\
		<!-- 查询条件部分end -->";
	this.showDataPart = 
		"<!-- 数据显示部分end -->\
		<tr>\
			<td class='height'></td>\
		</tr>\
		<tr>\
			<td id='show' class='sleektd'>\
				\
			</td>\
		</tr>\
		<tr>\
			<td class='height'></td>\
		</tr>\
		<!-- 数据显示部分end -->";
	this.dataEndPart = 
						"	</table>\
						</td>\
						<td class='wTableCenterRight'></td>\
					</tr>\
				</table>\
			</td>\
		</tr>\
		<!-- 数据end -->";
	this.endPart =
		"<!-- 尾部 -->\
		<tr>\
			<td height='35' class='wTableBottomCenter'>\
				<table width='100%' border='0' cellspacing='0' cellpadding='0'>\
					<tr>\
						<td width='12' height='35' class='wTableBottomLeft'></td>\
						<td width='16' class='wTableBottomRight'></td>\
					</tr>\
				</table>\
			</td>\
		</tr>\
	</table>\
	</body>\
	<!-- 尾部end -->";

}

Page.prototype.initPage = function() {
	
	var bodyStr = this.headPart + this.dataStartPart + this.selectPart
			 + this.showDataPart + this.dataEndPart + this.endPart;
	document.write(this.importPart+bodyStr);
}





























































