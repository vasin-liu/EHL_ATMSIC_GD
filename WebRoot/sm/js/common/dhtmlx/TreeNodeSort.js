
/**
 * 排序警情系统菜单节点次序
 */
function sortZdAlarmTreeNode(dhtmlObject){
	var rootNode = dhtmlObject.xmlDoc.responseXML.getElementsByTagName("tree")[0];
	var node = rootNode.cloneNode(true);
	treeNodeSort(node,["570700","570600"]);
	var tbNode = node.childNodes[1];
	treeNodeSort(tbNode,["570605","570604","570603"]);
	treeNodeChange(tbNode.childNodes,{"570605":"值班日志"})
	dhtmlObject.xmlDoc.responseXML.replaceChild(node, rootNode);
}

Array.prototype.contain = function(value){
	var isContain = false;
	for(var i=0;i<this.length;i++){
		if(this[i] == value){
			isContain = true;
			break;
		}
	}
	return isContain;
}

function treeNodeSort(node,sysids){
	var nodeClone = node.cloneNode(true);
	var cnodes = nodeClone.childNodes;
	var scindexs = [];//系统当前所在索引
	var swindexs = [];//系统欲替换索引
	var sid;
	for ( var i = 0; i < cnodes.length; i++) {
		sid = cnodes[i].getAttribute("sid");
		if (sysids.contain(sid)) {
			scindexs.push(i);
		}
	}
	for ( var i = 0; i < sysids.length; i++) {
		for ( var j = 0; j < cnodes.length; j++) {
			sid = cnodes[j].getAttribute("sid");
			if (sid == sysids[i]) {
				swindexs.push(j);
				nodeClone.removeChild(cnodes[j]);
			}
		}
	}
	cnodes = node.cloneNode(true).childNodes;
	var nodes = node.childNodes;
	for(var i=0;i<swindexs.length&&i<scindexs.length;i++){
		node.replaceChild(cnodes[swindexs[i]],nodes[scindexs[i]]);
	}
}


function treeNodeChange(nodes,sysids){
	var nsysid;
	for(var i=0;i<nodes.length;i++){
		nsysid = nodes[i].getAttribute("sid");
		for(var sysid in sysids){
			if(nsysid == sysid){
				nodes[i].setAttribute("text",sysids[sysid]);
			}
		}
	}
}
