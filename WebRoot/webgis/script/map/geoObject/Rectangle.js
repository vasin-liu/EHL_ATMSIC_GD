/**
 * 矩形区域
 */
Rectangle = Class.create();
Rectangle.prototype = {
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
        this.bound = new Bound(minX * 1e16, maxX * 1e16, minY * 1e16, maxY * 1e16);
    },
    /**
     * 取得像素宽度
     * @param {Object} zoom
     */
    getPixelWidth: function(zoom){
        var topleft = Util.getScreenPixel((new Point(this.minX, this.maxY)).getCoord(), zoom).x;
        var bottomright = Util.getScreenPixel((new Point(this.maxX, this.minY)).getCoord(), zoom).x;
        return Math.floor(Math.abs(bottomright - topleft));
    },
    /**
     * 取得像素高度
     * @param {Object} zoom
     */
    getPixelHeight: function(zoom){
        var topleft = Util.getScreenPixel((new Point(this.minX, this.maxY)).getCoord(), zoom).y;
        var bottomright = Util.getScreenPixel((new Point(this.maxX, this.minY)).getCoord(), zoom).y;
        return Math.floor(Math.abs(topleft - bottomright));
    },
    /**
     * 取得边界
     */
    getBound: function(){
        return this.bound;
    },
    /**
     * 取得中心点
     */
    getCenter: function(){
        return this.bound.getCenterCoord();
    }
}