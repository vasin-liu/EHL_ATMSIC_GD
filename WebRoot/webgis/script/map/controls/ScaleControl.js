//Scale Control
ScaleControl = Class.create();
ScaleControl.prototype = {
    initialize: function(container){
        this.id = Util.createUniqueID('Scale_');
        this.scaleDiv = this.create(container);
        container.appendChild(this.scaleDiv);
    },
    
    create: function(container){
        var left = Util.getValueOfNoPX(container.style.left) + 300;
        var top = Util.getValueOfNoPX(container.style.top) + Util.getValueOfNoPX(container.style.height) - 30;
        var div = Util.createDiv(this.scaleId, left, top, null, null, null, 'absolute');
        var scaleInfo = Util.createDiv(null, left - 8, top, 150, null, ImageBaseDir + 'scale.gif', 'absolute');
        container.appendChild(scaleInfo);
        div.style.fontSize = "12px";
        div.innerHTML = '<div id="scaleInfo" style="padding:3px;z-index:10;vertical-align:bottom;">&nbsp;</div>';
        return div;
    }
};