/*******************************************
 ** 基于dhtmlXGrid的分页控制 linbh 2009-04-11
 *******************************************/


PageCtrlGrid = Class.create();
PageCtrlGrid.prototype={
	url : "base.PageCtrl.getPageUtil.d",
	pageNum : 0, //定义总页数变量
	exeSql : "", //定义SQL语句变量
    exeCountSql : "", //定义计算总数SQL语句变量
	currPage : 1, //定义当前页变量
	pageSize : "", //分页大小
	dataSource : "", //数据源
	impClassName : "",//实现类名称
	mygrid : null,//保存页面grid对象
	initialize: function()
    {
    },
	/**
	 * 初始化函数
	 * @param contain 父类容器
	 * @param sql SQL语句 
	 * @param grid 页面grid对象
	 * @param icName 实现类名称
	 * @param customSize 自定义页大小
	 * @param dsName 数据源 	 * @param countSql 计算总数SQL语句 
	 * @example "PageCtrlGrid.initPage('pageContain','select id,n_id from t_func where 1=1 order by id','d_grid_s','impClassName',12,'','select count(id) from t_sys_func where 1=1')" 
	 */
	initPage : function(contain,sql,grid,className,customSize,dsName,countSql){
		
	  PageCtrlGrid.prototype.mygrid = grid;
	  
	  //总页数清零
	  PageCtrlGrid.prototype.pageNum = 0;
	   
	  //保存SQL语句
	  PageCtrlGrid.prototype.exeSql = sql;
	
	  //保存统计总数SQL语句
	  exeCountSql = countSql;

	  //保存分页大小
	  PageCtrlGrid.prototype.pageSize = customSize;
	
	  //保存数据源  
	  PageCtrlGrid.prototype.dataSource = dsName;
	
	  //保存实现类名称  
	  PageCtrlGrid.prototype.impClassName = className;
	  
	  //生成数据列表存放容器
	  var strHTML = "<table width='100%'>";
	  strHTML += "<tr><td id='pageDatas'></td></tr>";
	  
	  //生成分页导航按钮
	  strHTML += "<tr><td><table><tr>";
	  strHTML += "<td align='center' style='width:24;font-size:9pt'><a href='javascript:void(0);' onclick='PageCtrlGrid.prototype.onFirstPage();'>首页</a></td>";
	  strHTML += "<td align='center' style='width:1;font-size:9pt'>|</td>";
	  strHTML += "<td align='center' style='width:24;font-size:9pt'><a href='javascript:void(0);' onclick='PageCtrlGrid.prototype.onPrevPage();'>上页</a></td>";
	  strHTML += "<td align='center' style='width:1;font-size:9pt'>|</td>";
	  strHTML += "<td align='center' style='width:24;font-size:9pt'><a href='javascript:void(0);' onclick='PageCtrlGrid.prototype.onNextPage();'>下页</a></td>";
	  strHTML += "<td align='center' style='width:1;font-size:9pt'>|</td>";
	  strHTML += "<td align='center' style='width:24;font-size:9pt'><a href='javascript:void(0);' onclick='PageCtrlGrid.prototype.onLastPage();'>末页</a></td>";
	  strHTML += "<td align='right'  style='width:65;font-size:9pt'><input type='text' id='toPage' style='width:30;height:16;font-family:宋体;font-size:9pt'/>&nbsp;<a href='javascript:void(0);' onclick='PageCtrlGrid.prototype.onTurnToPage(toPage.value);'>跳转</a></td>";
	  strHTML += "<td align='right'  style='width:85;font-size:9pt' id='pageInfo'></td>";
	  strHTML += "</tr></table></td></tr>";
	  strHTML += "</table>";  
	
	  var conCtrl = document.getElementById(contain);
	  conCtrl.innerHTML = strHTML;
	  
	  //生成分页数据
	  this.callPageCtrl();
	},
	/**
	 ** 首页函数
	 */
	onFirstPage:function () {
	  this.currPage = 1;
	  this.callPageCtrl();
	},
	/**
	 ** 下页函数
	 */
	onNextPage : function () {
	  if (this.currPage < this.pageNum) {
		 this.currPage++;
		 this.callPageCtrl();
	  }
	},
	/**
	 ** 上页函数
	 */
	onPrevPage : function () {
	  if (this.currPage > 1) {
		 this.currPage--;
		 this.callPageCtrl();
	  }
	},
	/**
	 ** 尾页函数
	 */
	onLastPage : function () {
	  this.currPage = this.pageNum;
	  this.callPageCtrl();
	},
	/**
	 ** 跳转函数
	 */
	onTurnToPage : function (pageNo) {
	  if(pageNo<1)
	  {
		 this.currPage = 1;
	  }else if(pageNo>this.pageNum)
	  {
	     this.currPage = this.pageNum;
	  }else
	  {
		 this.currPage = pageNo;
	  }
	  this.callPageCtrl();
	},
	/**
	 ** 页码刷新函数
	 */
	pageLoaded : function () {
	  PageCtrlGrid.prototype.currPage = parseInt(PageCtrlGrid.prototype.mygrid.getUserData("","currPage"));
	  PageCtrlGrid.prototype.pageNum = parseInt(PageCtrlGrid.prototype.mygrid.getUserData("","pageNum"));
	  PageCtrlGrid.prototype.exeSql = PageCtrlGrid.prototype.mygrid.getUserData("","exeSql");
	  if(PageCtrlGrid.prototype.pageNum == 0){
	  	 PageCtrlGrid.prototype.currPage=0;
	  };
	  document.getElementById("pageInfo").innerHTML = "页码:" + PageCtrlGrid.prototype.currPage + "/" + PageCtrlGrid.prototype.pageNum;
	},
	/**
	 ** 分页控制函数（AJAX异步调用）
	 */
	callPageCtrl : function () {
		var params = "currPage=" + this.currPage + "&exeSql=" + this.convertSql(this.exeSql) + "&pageNum=" + this.pageNum;
		if (this.pageSize){
	  	   params += "&pageSize=" + this.pageSize;
	    }
	    if (this.dataSource){
	  	   params += "&dataSource=" + this.dataSource;
	    }
		if (this.impClassName){
	  	   params += "&impClassName=" + this.impClassName;	
		}else{
			params += "&impClassName=com.ehl.base.page.PageCtrlUIImple";
		}
	    if (exeCountSql){
	  	   params += "&exeCountSql=" + this.convertSql(exeCountSql);
	    }
	    params = encodeURI(params);
		this.mygrid.clearAll(); 
		this.mygrid.loadXML(this.url + "?" + params,this.pageLoaded);
		
	},
	/**
	 * 函数功能：SQL语句置换函数.
	 * 参数说明：sql-标准SQL语句.
	 * 返回值：  处理后的SQL语句.
	 */
	convertSql : function (sql) {
	  if (sql == null) {
		  return sql;
	  }
	  sql = sql.replace(/=/g, "＝");
	  sql = sql.replace(/%/g, "％");
	  sql = sql.replace(/\+/, "＋");
	  return sql;
	}
}



