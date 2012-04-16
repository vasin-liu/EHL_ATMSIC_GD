Abstract.Control = function(){};
Abstract.Control.prototype = {
	initialize: function(){
    },
    paint: function(){
    },
    loadTiles: function(model, container, mapDiv, isTracing, isMap){
        var curZoom = model.getZoom();
        var viewBound = curZoom.getViewBound(mapDiv).clone(model.getViewCenterCoord());
        var tiles = curZoom.getTiles(model, container);
        var oldTiles = new Array();
        var tileDivs = mapDiv.childNodes;
        if (mapDiv.id.indexOf('Ov_') == -1) {
            var scale = curZoom.getScale() * 1.5 / 100;
            if (scale < 1000) {
                scale = parseInt(scale) + " ";
            }
            else {
                scale = parseInt(scale / 1000 * 100) / 100 + " ";
            }
            $('scaleInfo').innerHTML = scale;
        }
        if (isTracing){
            model.traces[model.traceIndex] = {
                coord: model.getViewCenterCoord(),
                level: curZoom.getLevel()
            };
            model.curIndex = model.traceIndex;
			model.traceIndex += 1;
        }
        var n = 0;
        if (tileDivs){
            for (var i = 0; i < tileDivs.length; i++){
                oldTiles.push(tileDivs[i]);
            }
        }
        if (tiles){
            for (var i = 0; i < tiles.length; i++){
                var tileId = "map_" + model.getId() + "_zoom_" + model.getZoom().getLevel() + "_tile_" + tiles[i].getRow() + "_" + tiles[i].getColumn();                
				var isExist = false;
                for (var j = 0; j < oldTiles.length; j++){
                    if (oldTiles[j] != null && oldTiles[j].getAttribute("id") == tileId){
                        isExist = true;
                        oldTiles[j] = null;
                        break;
                    }
                }
                if (!isExist){
                    var deltaX = tiles[i].getColumn() * TileSize;
                    var deltaY = tiles[i].getRow() * TileSize;
                    var tile = document.createElement("div");
                    tile.id = tileId;
                    tile.style.position = "absolute";
                    tile.style.left = deltaX + "px";
                    tile.style.top = deltaY + "px";
                    tile.onmousedown = null;
                    mapDiv.appendChild(tile);
                    var tileImage = document.createElement("img");
                    tileImage.src = tiles[i].getSrc();
                    tileImage.galleryImg = 'no';
                    tileImage.onmousedown = null;
                    tileImage.width = TileSize;
                    tileImage.height = TileSize;
                    tile.appendChild(tileImage);
                    n++;
                }
            }
        }
        for (var i = 0; i < oldTiles.length; i++){
            if (oldTiles[i] != null && oldTiles[i].getAttribute("id").indexOf("Over_") > -1){
                continue;
            }
            if (oldTiles[i] != null){
                mapDiv.removeChild(oldTiles[i]);
            }
        }
        oldTiles = null;
        tiles = null;
        tileDivs = null;
		if(isMap){
			fireTileChangeListener();
		}
    }
};