var SessionKeep = {
	keepSession : function(time) {
		if(!time){
			time = 60;
		}
		time = 1000 * time;
		setInterval(function() {
			SessionKeep.keepOnce();
		}, time);
	},
	keepOnce : function() {
		var url = "login.sessionKeep.showSession.d";
		new Ajax.Request(url, {
			method : "post",
			parameters : {},
			onComplete : function(xmlHttp) {
				
			}
		});
	}
}
