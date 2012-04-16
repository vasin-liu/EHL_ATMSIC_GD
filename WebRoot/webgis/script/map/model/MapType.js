/**
 * 地图数据源,抽象类
 */
MapType = function(){};
MapType.prototype = {
	/**
	 * 初始化
	 * @param {Object} dirSrc
	 * @param {Object} enImg
	 * @param {Object} disImg
	 * @param {Object} firstRows
	 * @param {Object} firstCols
	 */
    initialize: function(dirSrc, enImg, disImg, firstRows, firstCols){
        this.enableImg = ImageBaseDir + enImg;
        this.disableImg = ImageBaseDir + disImg;
        this.typeId = 'mapType_' + Util.createUniqueID();
        this.dirSrc = dirSrc;
        this.firstRows = firstRows;
        this.firstCols = firstCols;
    },
    /**
     * 取得数据源
     * @param {Object} level
     * @param {Object} row
     * @param {Object} column
     */
    getSrc: function(level, row, column){
        //var src = this.dirSrc + 'zoom_' + level + '/0/' + level + '_' + row + '_' + column + '.jpg';
        //var dirName = this.dirSrc + level + '/' + row;
		var dirName = this.dirSrc + level + '/' + level + '_' + Math.floor(row / 128) + '/' + row + '/' + row + '_' + Math.floor(column / 128);
        var fileName = level + '_' + row + '_' + column + '.jpg';
        var src = dirName + '/' + fileName;
        return src;
    },
    /**
     * 画
     * @param {Object} model
     * @param {Object} container
     */
    paint: function(model, container){
        this.model = model;
        this.container = container;
		var ids = new Array();
/**       
        var html = '';
        for (var i = 0; i < model.mapTypeIds.length; i++) {
            var mapType = model.mapTypes[model.mapTypeIds[i]];
            if (model.currentMapType.typeId == mapType.typeId) {
                html += '<img src="' + mapType.enableImg + '" style="cursor:pointer;"> ';
            }
            else {
                html += '<img id="Img_' + mapType.typeId + '" src="' + mapType.disableImg + '" style="cursor:pointer;"> ';
                ids.push("Img_" + mapType.typeId);
            }
        }

        if (this.model.typeBarId) {
            this.typeBarDiv = $(this.model.typeBarId);
        }
        else {
            var left = Util.getValueOfNoPX(container.style.left) + 20;
            var top = Util.getValueOfNoPX(container.style.top) + 20;
            this.model.typeBarId = Util.createUniqueID("typeBar_");
            this.typeBarDiv = Util.createDiv(this.model.typeBarId, left, top, null, null, null, 'absolute');
        }
        this.typeBarDiv.innerHTML = html;
        container.appendChild(this.typeBarDiv);

        for (var i = 0; i < ids.length; i++) {
            this.curId = ids[i];
            Event.observe($(ids[i]), 'click', this.mapTypeSwitch.bindAsEventListener(this));
        }
*/
    },
    /**
     * 地图类型交换
     * @param {Object} e
     */
    mapTypeSwitch: function(e){
        var id = this.curId.substring(4, this.curId.length);
        mapType = this.model.mapTypes[id];
        this.model.setCurrentMapType(mapType);
        this.ClearOrgMapType(this.container.childNodes[0]);
        this.model.controls[this.container.childNodes[0].id].paint(this.model, true);
        this.model.controls[this.model.ovId].paint(this.model);
        mapType.paint(this.model, this.container);
    },
    /**
     * 
     * @param {Object} container
     */
    ClearOrgMapType: function(container){
        var mapDiv = container;
        var tileNodes = mapDiv.childNodes;
        if (tileNodes) {
            for (var i = 0; i < tileNodes.length; i++) {
                mapDiv.removeChild(tileNodes[i]);
                i--;
            }
        }
    }
};

/**
 * 
 */
SZMapType = Class.create();
SZMapType.prototype = Object.extend(new MapType(), {
    /**
     * 
     * @param {Object} level
     * @param {Object} row
     * @param {Object} column
     */
	getSrc: function(level, row, column){
        //var src = this.dirSrc + 'zoom_' + level + '\\0\\' + level + '_' + row + '_' + column + '.jpg';		
        //var dirName = this.dirSrc + level + '/' + row;
		var dirName = this.dirSrc + level + '/' + level + '_' + Math.floor(row / 128) + '/' + row + '/' + row + '_' + Math.floor(column / 128);
        var fileName = level + '_' + row + '_' + column + '.jpg';
        var src = dirName + '/' + fileName;
        
        return src;
    }
});