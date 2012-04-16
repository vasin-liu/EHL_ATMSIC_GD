var highWay =new function(){
	

var RoadDept = {
	date:{
		desc:"日期",
		kv:{}
	},
	roadName : {
		desc : "高速公路",//高速公路名称
		kv : {}
	},
	manager : {
		desc : "管辖大队",//
		kv : {}
	},
	manageMileage : {
		desc : "管辖里程",
		kv : {}
	},
	policeStrength : {
		desc : "部署警力",
		kv : {}
	},
	flow : {
		desc : "日流量",
		kv : {}
	},
	alarm : {
		desc : "日拥堵次数",
		kv : {}
	},
	accident : {
		desc : "日事故宗数",
		kv : {}
	},
	diedPerson : {
		desc : "日死亡人数",
		kv : {}
	}
}

this.StatObj = {
	StatItem:{
		code:"statItem",		
		desc:"统计项",
		statType:{
			code:"statType",
			desc:"统计类别：",
			kvs:{
				code:"statTypeSelect",
				keys:[],
				values:[]
			}
			
		},
		statTypeDetail:{
			code:"statTypeDetail",
			desc:"统计类别细分：",
			kvs:{
				code:"statTypeDetailSelect",
				keys:[],
				values:[]
			}
		}
	},
	FloatItem:{
		code:"floatItem",		
		desc:"浮动项",
		floatType:{
			code:"floatType",
			desc:"浮动类别：",
			kvs:{
				code:"floatTypeSelect",
				keys:[],
				values:[]
			}
		},
		floatTypeDetail:{
			code:"floatTypeDetail",
			desc:"浮动类别细分：",
			kvs:{
				code:"floatTypeDetailSelect",
				keys:[],
				values:[]
			}
		}
	},
	DataItem:{
		code:"floatItem",		
		desc:"浮动项",
		dataType:{
			code:"dataType",
			desc:"数据类别：",
			kvs:{
				code:"dataTypeSelect",
				keys:[],
			    values:[]
			}
		}
	},
	SelectItem:{
		code:"",
		desc:"",
		highWay:{
			code:"highWay",
			desc:"高速公路：",
			kvs:{
				code:"highWaySelect",
				keys:[],
				values:[]
			}
		},
		dateSE:{
			code:"dateSE",
			desc:"起止日期：",
			kvs:{
				code:"dateSESelect",
				keys:[],
				values:[]
			}
		}
	},
	ElseItem:{
		
	}
}

this.init = function(){
	
}

}