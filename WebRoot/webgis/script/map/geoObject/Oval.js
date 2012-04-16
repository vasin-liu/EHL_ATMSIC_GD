/**
 * 椭圆（linbh add）
 * param points 经纬度坐标数组（经度和纬度以逗号分隔 ）
 */
Oval = Class.create();
Oval.prototype = Object.extend(new Abstract.OverLayer(), {
	/**
	 * 初始化
	 * @param {Object} id 线ID
	 * @param {Object} points 点集
	 * @param {Object} color 线颜色
	 * @param {Object} size 线宽
	 * @param {Object} fillColor 填充颜色
	 * @param {Object} isCircle 是否画园
	 */
    initialize: function(id, points, color, size, fillColor, isCircle){
		this.id = id;
        this.points = points;
        this.color = color;
        this.size = size;
        this.fillColor = fillColor;
        this.isCircle = isCircle;
        this.bound = this.buildExtent();		
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
	 * 创建椭圆.
	 * @param:pointsStr-->描述VML点集的串.
	 */	
	createOval: function(left,top,width,height){
		var posStyle = "absolute";
		var vOval = document.createElement("v:oval");
		vOval.id = this.id;
	    vOval.filled = false;
		vOval.style.position = posStyle;
		vOval.strokecolor = this.color;
		vOval.strokeweight = this.size;
		vOval.style.left = left;
		vOval.style.top = top;
		vOval.style.width = width;
		vOval.style.height = height;
		
		if (this.fillColor){
	        vOval.filled = true;
	        vOval.fillcolor = this.fillColor;
		}

		return vOval;
	},
    /**
     * 设置到地图上
     * @param {Object} mapDiv
     * @param {Object} model
     */
    setToMap: function(mapDiv, model){
        this.mapDiv = mapDiv;
        this.model = model;
        var curZoom = model.getZoom();

		//准备数据
        var leftTop = Util.getScreenPixel(new Coordinate(this.points[0].getCoord().x, this.points[0].getCoord().y), curZoom);
		var rightBottom = Util.getScreenPixel(new Coordinate(this.points[1].getCoord().x, this.points[1].getCoord().y), curZoom);
		var width = rightBottom.x - leftTop.x;
		var height = rightBottom.y - leftTop.y;
		if (this.isCircle){
			width = Math.max(width,height);
			height = width;
		}
		this.vOval = this.createOval(leftTop.x,leftTop.y,width,height);
		this.mapDiv.appendChild(this.vOval);

		return this.vOval;
    }
});

/**
 * 椭圆（linbh add）
 */
OvalByPixel = Class.create();
OvalByPixel.prototype = Object.extend(new Abstract.OverLayer(), {
	/**
	 * 初始化
	 * @param {Object} id 线ID
	 * @param {Object} points 点集（每个点的横纵坐标之间以逗号分隔,点之间以分号分隔）
	 * @param {Object} color 线颜色
	 * @param {Object} size 线宽
	 * @param {Object} fillColor 填充颜色
	 */
    initialize: function(id, points, color, size, fillColor){
		this.id = id;
        this.points = points;
        this.color = color;
        this.size = size;
        this.fillColor = fillColor;
    },

	/**
	 * 创建椭圆.
	 * @param:pointsStr-->描述VML点集的串.
	 */	
	createOval: function(left,top,width,height){
		var posStyle = "absolute";
		var vOval = document.createElement("v:oval");
		vOval.id = this.id;
	    vOval.filled = false;
		vOval.style.position = posStyle;
		vOval.strokecolor = this.color;
		vOval.strokeweight = this.size;
		vOval.style.left = left;
		vOval.style.top = top;
		vOval.style.width = width;
		vOval.style.height = height;
		
		if (this.fillColor){
	        vOval.filled = true;
	        vOval.fillcolor = this.fillColor;
		}

		return vOval;
	},
    /**
     * 设置到地图上
     * @param {Object} mapDiv
     */
    setToMap: function(mapDiv){
        this.mapDiv = mapDiv;

		//准备数据
		var arrPoints = this.points.split(";");
		var leftTop = arrPoints[0].split(",");
		var rightBottom = arrPoints[1].split(",");

		var width = parseInt(rightBottom[0]) - parseInt(leftTop[0]);
		var height = parseInt(rightBottom[1]) - parseInt(leftTop[1]);

		this.vOval = this.createOval(leftTop[0],leftTop[1],width,height);
		this.mapDiv.appendChild(this.vOval);

		return this.vOval;
    }
});

/**
 * 圆（linbh add）
 * param point 中心点经纬度坐标（经度和纬度以逗号分隔 ）
 * param radius 半径
 */
Circle = Class.create();
Circle.prototype = Object.extend(new Abstract.OverLayer(), {
	/**
	 * 初始化
	 * @param {Object} id 线ID
	 * @param {Object} point 中心点
	 * @param {Object} radius 半径（绘固定圆需给出半径，单位为pixel）
	 * @param {Object} color 线颜色
	 * @param {Object} size 线宽
	 * @param {Object} fillColor 填充颜色
	 */
    initialize: function(id, point, radius, color, size, fillColor){
		this.id = id;
        this.point = point;
        this.radius = radius;
        this.color = color;
        this.size = size;
        this.fillColor = fillColor;
    },

	/**
	 * 创建圆.
	 */	
	createCircle: function(left,top,width,height){
		var posStyle = "absolute";
		var vCircle = document.createElement("v:oval");
		vCircle.id = this.id;
	    vCircle.filled = false;
		vCircle.style.position = posStyle;
		vCircle.strokecolor = this.color;
		vCircle.strokeweight = this.size;
		vCircle.style.left = left;
		vCircle.style.top = top;
		vCircle.style.width = width;
		vCircle.style.height = height;
		
		if (this.fillColor){
	        vCircle.filled = true;
	        vCircle.fillcolor = this.fillColor;
		}

		return vCircle;
	},
    /**
     * 设置到地图上
     * @param {Object} mapDiv
     * @param {Object} model
     */
    setToMap: function(mapDiv, model){
        this.mapDiv = mapDiv;
        this.model = model;
        var curZoom = model.getZoom();

		//准备数据
		var coord = this.point.split(",");
        var center = Util.getScreenPixel(new Coordinate(coord[0], coord[1]), curZoom);
		var width = this.radius * 2;
		var height = this.radius * 2;		
		this.vCircle = this.createCircle(center.x - this.radius,center.y - this.radius,width,height);
		this.mapDiv.appendChild(this.vCircle);

		return this.vCircle;
    }
});
