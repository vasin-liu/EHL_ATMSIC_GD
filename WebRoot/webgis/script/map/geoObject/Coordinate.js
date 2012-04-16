/**
 * 
 */
Coordinate = Class.create();
Coordinate.prototype = {
    /**
     * 初始化
     * @param {Object} x
     * @param {Object} y
     */
	initialize: function(x, y){
        this.x = x;
        this.y = y;
    },
    /**
     * 是否相同
     * @param {Object} coord
     */
    isSame: function(coord){
        if (this.x == coord.x && this.y == coord.y) 
            return true;
        return false;
    },
    /**
     * 取得边界
     * @param {Object} width
     * @param {Object} height
     */
    getBound: function(width, height){
        return new Bound(this.x - width / 2, this.x + width / 2, this.y - height / 2, this.y + height / 2);
    },
    /**
     * 
     */
    getPoint: function(){
        return new Point(this.x / 1e16, this.y / 1e16);
    },
    
    toString: function(){
        return 'X:' + this.x + ',Y:' + this.y;
    }
}