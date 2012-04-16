var baseDir = "";
var basePreDir = "";

var head = document.getElementsByTagName("head")[0];
var nodes = head.childNodes;
for (var i = 0; i < nodes.length; ++i) {
    var src = nodes.item(i).src;
    if (src) {
        var index = src.indexOf("/Util.js");
        if (index >= 0) {
            baseDir = src.substring(0, index);
        }
    }
}

basePreDir = baseDir.substring(0,baseDir.indexOf("webgis"));

/* 加载通用功能脚本 */
include(baseDir + "/Prototype.js");
include(baseDir + "/common/Navigator.js");
include(baseDir + "/common/Function.js");
include(baseDir + "/common/Action.js");
include(baseDir + "/common/Calendar.js");
include(basePreDir + "base/js/page/PageCtrl.js");
include(basePreDir + "base/js/list/FillListBox.js");
include(basePreDir + "base/js/list/SelectFastPinYin.js");

/* 加载WebGIS功能脚本 */
include(baseDir + "/MapTips.js");
include(baseDir + "/MapUtils.js");
include(baseDir + "/Map.js");
include(baseDir + "/model/MapModel.js");
include(baseDir + "/model/MapType.js");
include(baseDir + "/model/Tile.js");
include(baseDir + "/model/Zoom.js");
include(baseDir + "/model/OverLayer.js");
include(baseDir + "/geoObject/Bound.js");
include(baseDir + "/geoObject/Coordinate.js");
include(baseDir + "/geoObject/Point.js");
include(baseDir + "/geoObject/Polyline.js");
include(baseDir + "/geoObject/Rectangle.js");
include(baseDir + "/geoObject/Oval.js");
include(baseDir + "/geoObject/Marker.js");
include(baseDir + "/controls/Control.js");
include(baseDir + "/controls/OvMap.js");
include(baseDir + "/controls/NavControl.js");
include(baseDir + "/controls/ScaleControl.js");
include(baseDir + "/controls/MapControl.js");
include(baseDir + "/controls/ToolBarControl.js");
include(baseDir + "/controls/CRPanelControl.js");
include(baseDir + "/controls/CBPanelControl.js");
include(baseDir + "/toolbar/Tool.js");
include(baseDir + "/toolbar/ExtTool.js");
include(baseDir + "/toolbar/Command.js");
include(baseDir + "/panel/CPanelItem.js");
include(baseDir + "/panel/CPanelContent.js");
include(baseDir + "/panel/SearchPanel.js");
include(baseDir + "/panel/LabelPanel.js");
