
/**
 * 获取浏览器信息
 */
var isIE = true;
var isNN = false;
function getBrowserInfo() {
	var Browser_Name = navigator.appName;
	var Browser_Version = parseFloat(navigator.appVersion);
	var Browser_Agent = navigator.userAgent;
	var Actual_Version, Actual_Name;
	isIE = (Browser_Name == "Microsoft Internet Explorer");
	isNN = (Browser_Name == "Netscape");
	if (isIE) 
	{ 
        //upper 5.0 need to be process,lower 5.0 return directly 
		if (Browser_Version >= 5) {
			var Split_Sign = Browser_Agent.lastIndexOf("/");
			var Version = Browser_Agent.indexOf(" ", Split_Sign);
			var Bname = Browser_Agent.lastIndexOf(" ", Split_Sign);
			Actual_Version = Browser_Agent.substring(Split_Sign + 1, Version);
			Actual_Name = Browser_Agent.substring(Bname + 1, Split_Sign);
		} else {
			Actual_Version = Browser_Version;
			Actual_Name = Browser_Name;
		}
	} 
	else 
	{
		if (isIE) 
		{
			var Version_Start = Browser_Agent.indexOf("MSIE");
			var Version_End = Browser_Agent.indexOf(";", Version_Start);
			Actual_Version = Browser_Agent.substring(Version_Start + 5, Version_End);
			Actual_Name = Browser_Name;
			if (Browser_Agent.indexOf("Maxthon") != -1) {
				Actual_Name += "(Maxthon)";
			} else {
				if (Browser_Agent.indexOf("Opera") != -1) {
					Actual_Name = "Opera";
					var tempstart = Browser_Agent.indexOf("Opera");
					var tempend = Browser_Agent.length;
					Actual_Version = Browser_Agent.substring(tempstart + 6, tempend);
				}
			}
		} 
		else {
			Actual_Name = "Unknown Navigator";
			Actual_Version = "Unknown Version";
		}
	}
	navigator.Actual_Name = Actual_Name;
	navigator.Actual_Version = Actual_Version;
	this.Name = Actual_Name;
	this.Version = Actual_Version;
}
/**
 * 获取操作系统信息
 */
var isWin = true;
var isMac = false;
var isUnix = false;
function getOSInfo() 
{
	var sUserAgent = navigator.userAgent;
	isWin = (navigator.platform == "Win32") || (navigator.platform == "Windows");
	isMac = (navigator.platform == "Mac68K") || (navigator.platform == "MacPPC") || (navigator.platform == "Macintosh");
	if (isMac) {
		return "Mac";
	}
	
	isUnix = (navigator.platform == "X11") && !isWin && !isMac;
	if (isUnix) {
		return "Unix";
	}
	if (isWin) {
		var isWin95 = sUserAgent.indexOf("Win95") > -1 || sUserAgent.indexOf("Windows 95") > -1;
		if (isWin95) {
			return "Win95";
		}
		var isWin98 = sUserAgent.indexOf("Win98") > -1 || sUserAgent.indexOf("Windows 98") > -1;
		if (isWin98) {
			return "Win98";
		}
		var isWinME = sUserAgent.indexOf("Windows 9x 4.90") > -1 || sUserAgent.indexOf("Windows ME") > -1;
		if (isWinME) {
			return "WinME";
		}
		var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1 || sUserAgent.indexOf("Windows 2000") > -1;
		if (isWin2K) {
			return "Win2000";
		}
		var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1 || sUserAgent.indexOf("Windows XP") > -1;
		if (isWinXP) {
			return "WinXP";
		}
	}
	return "None";
}
getOSInfo();
getBrowserInfo();

