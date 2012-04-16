/**
 * 点
 */
Point = Class.create();
Point.prototype = {
    /**
     * 初始化
     * @param {Object} x
     * @param {Object} y
     */
	initialize: function(x, y){
        this.x = x;
        this.y = y;
        this.coord = new Coordinate(x * 1e16, y * 1e16);
    },
    /**
     * 取得坐标
     */
    getCoord: function(){
        return this.coord;
    },
    /**
     * 设置坐标
     * @param {Object} coord
     */
    setCoord: function(coord){
        this.coord = coord;
    },
    /**
     * 计算距离
     * @param {Object} point
     */
    calcuDistance: function(point){
        return Util.distanceByLnglat(this.x, this.y, point.x, point.y);
    },
    /**
     * TOsTRING
     */
    toString: function(){
        return 'X:' + this.x + ',Y:' + this.y;
    }
}