/*
 * 地点：交通警情发生的地点
 * 位置：起止千米值、百米值范围
 * 方向：起点、终点和方向
 * 获取道路（道路等级、辖区）
 * 展示道路（），形式：列表
 * 获取路段（道路、辖区）
 * 展示路段（）
 * 获取道路方向（道路）
 * 展示道路方向（）
 * 获取位置（道路、辖区）
 * 展示位置
 */

function Place(road, section, direction, sk, skm, ek, ekm) {
	this.road = road;
	this.section = section;
	this.direction = new Direction(direction);
	this.sk = sk;
	this.skm = skm;
	this.ek = ek;
	this.ekm = ekm;
}

Place.prototype = {
	getRoadData : function(level, jgid) {
		var sql;
		if (jgid) {
			if (level) {
				sql = "select distinct r.gbdm,r.dlmc";
				sql += " from t_oa_dictdlfx r,t_oa_roadmanage_code rj";
				sql += " where r.dlmc=rj.dlmc";
				sql += " and r.roadlevel='" + level + "'";
				sql += " and rj.xzqh='" + jgid + "'";
			} else {
				sql = "select distinct rj.gbbm,rj.dlmc";
				sql += " from t_oa_roadmanage_code rj";
				sql += " where substr(rj.xzqh,1," + jgid.length + ")='" + jgid
						+ "'";
				sql += " order by rj.dlmc";
			}
		} else {
			sql = "select r.gbdm,r.dlmc from t_oa_dictdlfx r where 1=1";
			if (level) {
				sql += " and roadlevel='" + level + "'";
			}
			sql += " order by r.dlmc";
		}
		var objects = new SimpleRequest(sql).load(true);
		return objects;
	},
	showRoad : function(pid, id, roads) {
		roads = roads || [];
		roads.unshift([ "", "请选择道路" ]);
		Place.util.multiShow(pid, id, roads);
	}
};

/** 道路方向 */
function Direction(code, begin, end) {
	this.code = code;
	this.begin = begin;
	this.end = end;
};

Direction.prototype = {
	// 获取方向数据
	getData : function(road) {
		var object = "";
		if (road) {
			var sql = "select begin,end";
			sql += " from t_oa_dictdlfx";
			sql += " where gbdm='" + road + "'";
			object = new SimpleRequest(sql).load(true);
		}
		return object;
	},
	// 设置方向起始点
	setData : function(object) {
		if (object instanceof Array && object.length == 2) {
			this.begin = object[0];
			this.end = object[1];
		}
	},
	// 将起始点转换为下拉列表数据
	changeData : function() {
		var infos = [ [ "", "请选择方向" ] ];
		if (this.begin && this.end) {
			infos.push([ "1", this.begin + "往" + this.end ]);
			infos.push([ "2", this.end + "往" + this.begin ]);
			infos.push([ "3", "双          向" ]);
		}
		return infos;
	},
	// 获取下拉列表数据
	getShowData : function(road) {
		var object = this.getData(road);
		this.setData(object);
		return this.changeData();
	},
	// 显示下拉列表
	show : function(id, object, pid) {
		if (!object) {
			object = this.changeData();
		} else if (typeof object == "string") {
			object = this.getShowData(object);
		}
		Place.util.multiShow(pid, id, object, this.code);
	},
	// 获取HTML
	getHTML : function(id, object) {
		if (!object) {
			object = this.changeData();
		} else if (typeof object == "string") {
			object = this.getShowData(object);
		}
		return Place.util.getHTML(id, object, this.code);
	}
};

Place.util = {
	multiShow : function(pid, id, infos, selected) {
		var pel = $(pid);
		var el = $(id);
		if (pel || el) {
			if (el) {
				Place.util.setNode(el, infos, selected);
			} else {
				Place.util.setHTML(pel, id, infos, selected);
			}
		} else {
			alert("容器和元素至少存在一个");
		}
	},
	getHTML : function(id, objects, selected) {
		objects = objects || [];
		var object;
		var options = "";
		for ( var i = 0; i < objects.length; i++) {
			object = objects[i];
			options += "<option value='" + object[0] + "'";
			if (object[0] == selected) {
				options += " selected ";
			}
			options += ">" + object[1] + "</option>";
		}
		var select = "<select id='" + id + "' class='select'>";
		select += options + "</select>";
		return select;
	},
	setHTML : function(pel, id, infos, selected) {
		var select = Place.util.getHTML(id, infos, selected);
		pel.innerHTML = select;
	},
	setNode : function(el, objects, selected) {
		el.length = 0;
		for ( var i = 0; i < objects.length; i++) {
			var option = document.createElement("option");
			option.value = objects[i][0];
			if (objects[i][0] === selected) {
				option.selected = true;
			}
			option.text = objects[i][1];
			el.add(option);
		}
	}
};

/** 道路等级 */
var RoadLevel = {
	/** 道路等级数据 */
	data : function() {
		return [ {
			innerHTML : "高速",
			value : "1"
		}, {
			innerHTML : "国道",
			value : "2"
		}, {
			innerHTML : "省道",
			value : "3"
		} ];
	},
	/** 道路等级数据包含提示 */
	dataWithPrompt : function() {
		var objects = RoadLevel.data();
		objects.unshift(DOM.Option.emptyAttrs("道路等级"));
		return objects;
	},
	/** 列表元素 */
	select : function(attrs, value) {
		var objects = RoadLevel.dataWithPrompt();
		return DOM.Select.elementSpeedy(attrs, objects, value);
	},
	/** 日报表辖区最大流量列表元素 */
	selectReport : function(attrs, value) {
		var objects = RoadLevel.dataWithPrompt();
		objects.splice(3, 1);
		return DOM.Select.elementSpeedy(attrs, objects, value);
	}
};
