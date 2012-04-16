/*用于前台cookie操作
/*------------------------------------------------------------*/

//设置命名为name的cookie的值为value
function setCookie(name,value)
{
    var exp  = new Date();
    exp.setTime(exp.getTime() + 30*60*1000);
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

//获取指定name的cookie值
function getCookie(name)
{
    var arr = document.cookie.match(new RegExp("(^| )"+name+"=([^;]*)(;|$)"));
    if(arr != null)
    {
     	return unescape(arr[2]);
    } 
    return null;
}

//删除指定name的cookie
function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null) document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}

