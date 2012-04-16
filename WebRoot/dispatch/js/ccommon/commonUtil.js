function getSearchPage() {
	if(document.getElementById("radioBtn1").checked) {
		window.open("materialInfoQuery.jsp","moduletarget");
	} else if(document.getElementById("radioBtn2").checked) {
		window.open("TrafficCrowdQuery.jsp","moduletarget");
	} else if(document.getElementById("radioBtn3").checked) {
		window.open("xcbkSearch.jsp","moduletarget");
	} else if(document.getElementById("radioBtn4").checked) {
		window.open("RoadBuildQuery.jsp","moduletarget");
	} else if(document.getElementById("radioBtn5").checked) {
		window.open("NoticeQuery.jsp","moduletarget");
	}
}