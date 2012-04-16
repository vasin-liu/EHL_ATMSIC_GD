Abstract.OverLayer = function(){};
Abstract.OverLayer.prototype = {
	/**
	 * 初始化
	 * @param {Object} mapDiv
	 */
    initialize: function(mapDiv){
    },
    /**
     * 插入
     */
    insert: function(){
        if (this.model == null){
            return;
		}
        if (this.model.overlays == null){ 
            this.model.overlays = new Array();
        }
        this.model.overlays.push(this);
        this.vmlDiv.appendChild(this.vPolyline);
    },
    /**
     * 移除
     */
    remove: function(){
        if (this.model == null) {
			return;
		}
        if (this.model.overlays) {
            this.model.overlays.without(this);
            this.vmlDiv.removeChild(this.vPolyline);
        }
    }
};