/**
 * 
 */
Bound = Class.create();
Bound.prototype = {
	/**
	 * 初始化
	 * @param {Object} minX
	 * @param {Object} maxX
	 * @param {Object} minY
	 * @param {Object} maxY
	 */
    initialize: function(minX, maxX, minY, maxY){
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.centerCoord = new Coordinate((this.minX + this.maxX) / 2, (this.minY + this.maxY) / 2);
    },
    /**
     * 获取中心坐标点
     */
    getCenterCoord: function(){
        return this.centerCoord;
    },
    /**
     * 克隆
     * @param {Object} coord
     */
    clone: function(coord){
        if (coord == null || coord.isSame(this.centerCoord)) {
            return this;
        }
        else {
            var minX = this.minX + coord.x - this.centerCoord.x;
            var maxX = this.maxX + coord.x - this.centerCoord.x;
            var minY = this.minY + coord.y - this.centerCoord.y;
            var maxY = this.maxY + coord.y - this.centerCoord.y;
            return new Bound(minX, maxX, minY, maxY);
        }
    },
    /**
     * 是否覆盖
     * @param {Object} bound
     */
    isCover: function(bound){
        if (this.getMinX() > bound.getMaxX() || this.getMaxX() < bound.getMinX() || this.getMinY() > bound.getMaxY() || this.getMaxY() < bound.getMinY()) {
            return false;
        }
        return true;
    },
    /**
     * 点是否在边界内
     * @param {Object} coord
     */
    isWithin: function(coord){
        if (coord.x < this.maxX && coord.x > this.minX && coord.y < this.maxY && coord.y > this.minY) {
            return true;
        }
        return false;
    },
    /**
     * 取得最小的X
     */
    getMinX: function(){
        return this.minX;
    },
    /**
     * 取得最大的X
     */
    getMaxX: function(){
        return this.maxX;
    },
    /**
     * 取得最小的Y
     */
    getMinY: function(){
        return this.minY;
    },
    /**
     * 取得最大的Y
     */
    getMaxY: function(){
        return this.maxY;
    },
    /**
     * 取得高度
     */
    getHeight: function(){
        return Math.abs(this.maxY - this.minY);
    },
    /**
     * 取得宽度
     */
    getWidth: function(){
        return Math.abs(this.maxX - this.minX);
    },
    /**
     * 取得像素高度
     * @param {Object} zoom
     */
    getPixelHeight: function(zoom){
        var topleft = Util.getScreenPixel(new Coordinate(this.minX, this.maxY), zoom).y;
        var bottomright = Util.getScreenPixel(new Coordinate(this.maxX, this.minY), zoom).y;
        return Math.floor(Math.abs(topleft - bottomright));
        
    },
    /**
     * 取得像素宽度
     * @param {Object} zoom
     */
    getPixelWidth: function(zoom){
        var topleft = Util.getScreenPixel(new Coordinate(this.minX, this.maxY), zoom).x;
        var bottomright = Util.getScreenPixel(new Coordinate(this.maxX, this.minY), zoom).x;
        return Math.floor(Math.abs(bottomright - topleft));
    }
}