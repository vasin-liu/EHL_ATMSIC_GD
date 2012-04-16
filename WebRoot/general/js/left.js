/** 轮询管理器，节点name属性和树中节点的itemId属性相对应 */
function LoopManager(tree, nodes, index) {
	this.tree = tree;
	this.nodes = nodes || [];
	this.index = index || 0;
	this.isLooping = false;
}
/** 管理器节点 */
LoopManager.Node = function(id, name, delay, defaultChecked) {
	this.id = id;
	this.name = name;
	this.delay = delay || 10000;
	this.defaultChecked = defaultChecked || false;
};
LoopManager.prototype = {
	addNode : function(id, name, delay, defaultChecked) {
		var node = new LoopManager.Node(id, name, delay, defaultChecked);
		this.nodes.push(node);
	},
	setIndex : function(itemId) {
		this.index = this.getIndex(itemId);
	},
	start : function() {
		this.setDefaultChecked();
		var _this = this;
		this.tree.setOnCheckHandler(function(itemId) {
			if (!_this.isLooping) {
				_this.index = _this.getIndex(itemId);
				_this.loop();
			}
		});
		this.loop();
	},
	loop : function() {
		var node = this.nodes[this.index];
		if (node) {
			this.isLooping = true;
			this.openSelected(node.name);
			var _this = this;
			setTimeout(function() {
				_this.index = _this.getNextIndex(node.name);
				_this.loop();
			}, node.delay);
		} else {
			this.isLooping = false;
		}
	},
	getNextIndex : function(itemId) {
		var checkeds = tree.getAllChecked();
		if (!checkeds || checkeds == itemId) {
			return -1;
		}
		return this.getNextIndexByLoop(checkeds);
	},
	getNextIndexByLoop : function(checkeds, isToIndex) {
		var index = this.index + 1;
		var length = this.nodes.length;
		isToIndex = (isToIndex || false);
		if (isToIndex) {
			index = 0;
			length = this.index;
		}
		var nodes = this.nodes, node;
		for ( var i = index; i < length; i++) {
			node = nodes[i];
			if (checkeds.indexOf(node.name) != -1) {
				return i;
			}
		}
		if (!isToIndex) {
			return this.getNextIndexByLoop(checkeds, !isToIndex);
		}
		return -1;
	},
	openSelected : function(itemId) {
		tree.selectItem(itemId);
		onNodeSelect(itemId);
	},
	getIndex : function(itemId) {
		var length = this.nodes.length;
		for ( var i = 0; i < length; i++) {
			if (this.nodes[i].name == itemId) {
				return i;
			}
		}
		return -1;
	},
	setDefaultChecked : function() {
		var length = this.nodes.length, node;
		for ( var i = 0; i < length; i++) {
			node = this.nodes[i];
			this.tree.setCheck(node.name, node.defaultChecked);
		}
	}
};
