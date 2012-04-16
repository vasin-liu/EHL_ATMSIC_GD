/**
 * 线型:
 * <!-- solid,dot,dash,dashdot,longdash,longdashdot,longdashdotdot,shortdot,shortdash,shortdashdot,shortdashdotdot -->
 * 箭头风格:
 * <!-- none,block,classic,diamond,oval,open,chevron,doublechevron -->
 */
Polyline = Class.create();
Polyline.prototype = Object.extend(new Abstract.OverLayer(), {
	/**
	 * 初始化
	 * @param {Object} points 点集
	 * @param {Object} color 线颜色
	 * @param {Object} size 线宽
	 * @param {Object} offset 偏移量
	 */
    initialize: function(id, points, color, size, offset, bStroke, dashStyle, startArrow, endArrow, bMeasure){
		this.id = id;
        this.points = points;
        this.color = color;
        this.size = size;
        this.bound = this.buildExtent();		
		this.bStroke = bStroke;
		this.dashStyle = dashStyle;
		this.startArrow = startArrow;
		this.endArrow = endArrow;
		this.bMeasure = bMeasure;
		this.offset = offset;	
    },
    /**
     * 建立EXTENT
     */
    buildExtent: function(){
        var minX = 180e16, maxX = 0, minY = 90e16, maxY = 0;
        
        for (var i = 0; i < this.points.length; i++) {
            if (this.points[i].getCoord().x < minX){
				minX = this.points[i].getCoord().x;
			} 
            if (this.points[i].getCoord().x > maxX) {
				maxX = this.points[i].getCoord().x;
			}
            if (this.points[i].getCoord().y < minY) {
				minY = this.points[i].getCoord().y;
			}
            if (this.points[i].getCoord().y > maxY) {
				maxY = this.points[i].getCoord().y;
			}
        }

        return new Bound(minX, maxX, minY, maxY);
    },
    /**
     * 取得EXTENT
     */
    getExtent: function(){
        return this.bound;
    },
    /**
     * 设置EXTENT
     * @param {Object} extent
     */
    setExtent: function(extent){
        this.bound = extent;
    },
    /**
     * 取得中心坐标
     */
    getCenterCoord: function(){
        return this.getExtent().getCenterCoord();
    },
    /**
     * 取得长度
     */
    getLength: function(){
        if (this.points.length <= 1){
			return 0;
		} 
        var len = 0;
        for (var i = 0; i < this.points.length - 1; i++) {
            len += this.points[i].calcuDistance(this.points[i + 1]);
        }
        return len;
    },
	/**
	 * 创建一条多义线.
	 * @param:pointsStr-->描述VML点集的串.
	 */	
	createPolyline: function(pointsStr){
		var posStyle = "absolute";
		var vPolyline = document.createElement("v:polyline");
		vPolyline.id = this.id;
		vPolyline.filled = false;
		vPolyline.style.position = posStyle;
		vPolyline.strokecolor = this.color;
		vPolyline.strokeweight = this.size;
		vPolyline.points = pointsStr;
		
		if (this.bStroke){
			var vStroke = document.createElement("v:stroke");
			vStroke.dashstyle = this.dashStyle;
			vStroke.startarrow = this.startArrow;
			vStroke.endarrow = this.endArrow;	
			vPolyline.appendChild(vStroke);	
		}

		return vPolyline;
	},
    /**
     * 设置到地图上
     * @param {Object} mapDiv
     * @param {Object} model
     * @param {Object} overLayerDiv
     */
    setToMap: function(mapDiv, model, overLayerDiv){
        this.mapDiv = mapDiv;
        this.model = model;
        this.vmlDiv = map.getVMLDiv();
        var curZoom = model.getZoom();
        var pixel = Util.getScreenPixel(new Coordinate(this.getExtent().getMinX(), this.getExtent().getMaxY()), curZoom);//经纬度转屏幕
        var lines = new Array();

		//点
        for (var i = 0; i < this.points.length; i++) {
            var sPoint = Util.getScreenPixel(new Coordinate(this.points[i].getCoord().x, this.points[i].getCoord().y), curZoom);
            if (this.offset){
            	lines.push(Math.floor(sPoint.x + this.offset.x) + ',' + Math.floor(sPoint.y + this.offset.y) + ',');
            }else{
            	lines.push(Math.floor(sPoint.x) + ',' + Math.floor(sPoint.y) + ',');
            }
        }

		this.vPolyline = this.createPolyline(lines.join(""));
		if (!this.bMeasure) {
			this.mapDiv.appendChild(this.vPolyline);
		}
        else {
            this.insert();
        }
		return this.vPolyline;
    }
});

/**
 * 多义线（linbh add）
 * param points 平面坐标字符串（点与点之间以分号分隔，每个点的横纵坐标以逗号分隔）
 */
PolylineByPixel = Class.create();
PolylineByPixel.prototype = Object.extend(new Abstract.OverLayer(), {
	/**
	 * 初始化
	 * @param {Object} points 点集
	 * @param {Object} color 线颜色
	 * @param {Object} size 线宽
	 */
    initialize: function(id, points, color, size, bStroke, dashStyle, startArrow, endArrow, bMeasure){
		this.id = id;
        this.points = points;
        this.color = color;
        this.size = size;
		this.bStroke = bStroke;
		this.dashStyle = dashStyle;
		this.startArrow = startArrow;
		this.endArrow = endArrow;
		this.bMeasure = bMeasure;
    },
	/**
	 * 创建一条多义线.
	 * @param:pointsStr-->描述VML点集的串.
	 */	
	createPolyline: function(pointsStr){
		var posStyle = "absolute";
		var vPolyline = document.createElement("v:polyline");
		vPolyline.id = this.id;
		vPolyline.filled = false;
		vPolyline.style.position = posStyle;
		vPolyline.strokecolor = this.color;
		vPolyline.strokeweight = this.size;
		vPolyline.points = pointsStr;
		
		if (this.bStroke){
			var vStroke = document.createElement("v:stroke");
			vStroke.dashstyle = this.dashStyle;
			vStroke.startarrow = this.startArrow;
			vStroke.endarrow = this.endArrow;	
			vPolyline.appendChild(vStroke);	
		}

		return vPolyline;
	},
    /**
     * 设置到地图上
     * @param {Object} mapDiv
     */
    setToMap: function(mapDiv){
        this.mapDiv = mapDiv;
        var lines = new Array();

		this.vPolyline = this.createPolyline(this.points);
		if (!this.bMeasure) {
			this.mapDiv.appendChild(this.vPolyline);
		}
        else {
            this.insert();
        }
		return this.vPolyline;
    }
});
