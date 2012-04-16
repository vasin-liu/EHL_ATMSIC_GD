/*******************************************
 ** 分页控制 linbh 2008-07-23
 *******************************************/

var pageNum = 0; //定义总页数变量
var exeSql = ""; //定义SQL语句变量
var currPage = 1; //定义当前页变量
var callBackName = ""; //回调函数名称
var navContainName = ""; //分页导航容器名称
var perPageSize = ""; //分页大小
var dataSource = ""; //数据源

PageCtrl = new Object();

/**
 * 初始化函数
 * @param contain 父类容器
 * @param dataContain 分页数据容器
 * @param navContain 分页导航容器
 * @param sql SQL语句 
 * @param callBack 回调函数
 * @param pageSize 分页大小
 * @param dsName 数据源名称
 * @example "PageCtrl.initPage('contain','dataContain','navContain','select id,n_id from t_sys_func where 1=1 order by id','callBack',10,'ora92')" 
 */
PageCtrl.initPage = function(contain,dataContain,navContain,sql,callBack,pageSize,dsName){
  
  //校验参数合法性
  if (contain == null || contain.length == 0 || sql == null || sql.length == 0){
  	 return;
  }

  //置分页导航容器缺省值
  if (dataContain == null || dataContain.length == 0){
  	 dataContain = "pageDatas";
  }

  //置分页数据容器缺省值  
  if (navContain == null || navContain.length == 0){
  	 navContain = "pageNav";
  }

  //置当前页
  currPage = 1;
  
  //总页数清零
  pageNum = 0;
   
  //保存回调函数名
  callBackName = callBack;
  
  //保存分页大小
  perPageSize = pageSize;
  
  //保存SQL语句
  exeSql = sql;

  //保存分页导航容器
  navContainName = navContain;

  //保存数据源  
  dataSource = dsName;
  
  //生成数据列表存放容器
  var strHTML = "<table width='100%'>";
  strHTML += "<tr><td id='" + dataContain + "'></td></tr>";
  
  //生成分页导航按钮
  strHTML += "<tr><td><table><tr>";
  strHTML += "<td align='center' style='align:center;width:24;font-size:9pt'><a href='javascript:void(0);' onclick='onFirstPage();'>首页</a></td>";
  strHTML += "<td align='center' style='align:center;width:1;font-size:9pt'>|</td>";
  strHTML += "<td align='center' style='align:center;width:24;font-size:9pt'><a href='javascript:void(0);' onclick='onPrevPage();'>上页</a></td>";
  strHTML += "<td align='center' style='align:center;width:1;font-size:9pt'>|</td>";
  strHTML += "<td align='center' style='align:center;width:24;font-size:9pt'><a href='javascript:void(0);' onclick='onNextPage();'>下页</a></td>";
  strHTML += "<td align='center' style='align:center;width:1;font-size:9pt'>|</td>";
  strHTML += "<td align='center' style='align:center;width:24;font-size:9pt'><a href='javascript:void(0);' onclick='onLastPage();'>末页</a></td>";
  strHTML += "<td align='right'  style='width:65;font-size:9pt'><input type='text' id='toPage' style='width:30;height:16;font-family:宋体;font-size:9pt'/>&nbsp;<a href='javascript:void(0);' onclick='onTurnToPage(toPage.value);'>跳转</a></td>";
  strHTML += "<td align='right'  style='width:85;font-size:9pt' id='" + navContainName + "'></td>";
  strHTML += "</tr></table></td></tr>";
  strHTML += "</table>";  

  var conCtrl = document.getElementById(contain);
  conCtrl.innerHTML = strHTML;
  
  //生成分页数据
  callPageCtrl(currPage,perPageSize,dataSource);
}

/**
 ** 首页函数
 */
function onFirstPage() {
  currPage = 1;
  callPageCtrl(currPage,perPageSize,dataSource);
}

/**
 ** 下页函数
 */
function onNextPage() {
  if (currPage < pageNum) {
	 currPage++;
	 callPageCtrl(currPage,perPageSize,dataSource);
  }
}

/**
 ** 上页函数
 */
function onPrevPage() {
  if (currPage > 1) {
	 currPage--;
	 callPageCtrl(currPage,perPageSize,dataSource);
  }
}

/**
 ** 尾页函数
 */
function onLastPage() {
  currPage = pageNum;
  callPageCtrl(currPage,perPageSize,dataSource);
}

/**
 ** 跳转函数
 */
function onTurnToPage(pageNo) {
  if(pageNo<1)
  {
	 currPage = 1;
  }else if(pageNo>pageNum)
  {
     currPage = pageNum;
  }else
  {
	 currPage = pageNo;
  }
  callPageCtrl(currPage,perPageSize,dataSource);
}

/**
 ** 页码刷新函数
 */
function pageLoaded() {
  document.getElementById(navContainName).innerHTML = "页码:" + currPage + "/" + pageNum;
}

/**
 ** 分页控制函数（AJAX异步调用）
 */
function callPageCtrl(pageNo,pageSize,dataSource) {
  
  var params = "currPage=" + pageNo + "&exeSql=" + convertSql(exeSql) + "&pageNum=" + pageNum;
  if (pageSize){
  	 params += "&pageSize=" + pageSize;
  }
  if (dataSource){
  	 params += "&dataSource=" + dataSource;
  }
  params = encodeURI(params);
  
  var url = "common.PageCtrl.getPageUtil.d";

  new Ajax.Request(url, {method:"post", parameters:params, onComplete:pageResponse});
}

/**
 ** 响应函数
 */
function pageResponse(xmlHttp) {
  var xmlDoc = xmlHttp.responseXML;
  
  //获取JS全局变量
  var userDatas = xmlDoc.getElementsByTagName("userdata");
  exeSql = userDatas[0].childNodes[0].nodeValue;  //SQL语句
  pageNum =  parseInt(userDatas[1].childNodes[0].nodeValue); //总页数
  currPage = parseInt(userDatas[2].childNodes[0].nodeValue);//当前页
  
  if (callBackName != ""){//执行回调函数
  	  eval(callBackName + "(xmlDoc)");
  }
           	  		
  pageLoaded();
}

/**
 * 函数功能：SQL语句置换函数.
 * 参数说明：sql-标准SQL语句.
 * 返回值：  处理后的SQL语句.
 */
function convertSql(sql) {
  if (sql == null) {
	  return sql;
  }
  sql = sql.replace(/=/g, "＝");
  sql = sql.replace(/%/g, "％");
  sql = sql.replace(/\+/, "＋");
  return sql;
}
