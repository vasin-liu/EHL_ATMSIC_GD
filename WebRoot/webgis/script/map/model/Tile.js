Tile = Class.create();
Tile.prototype = {
    /**
     * 初始化
     * @param {Object} row
     * @param {Object} column
     * @param {Object} level
     * @param {Object} model
     */
	initialize: function(row, column, level, model){
        this.row = row;
        this.column = column;
        this.level = level;
        this.model = model;
    },
    /**
     * 取得行
     */
    getRow: function(){
        return this.row;
    },
    /**
     * 取得列
     */
    getColumn: function(){
        return this.column;
    },
    /**
     * 取得级别
     */
    getLevel: function(){
        return this.level;
    },
    /**
     * 取得地图MODEL
     */
    getMapModel: function(){
        return this.model;
    },
    /**
     * 取得数据源
     */
    getSrc: function(){
        return this.model.getCurrentMapType().getSrc(this.level, this.row, this.column);
    }
}