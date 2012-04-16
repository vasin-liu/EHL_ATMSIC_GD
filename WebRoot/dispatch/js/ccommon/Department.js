/**机构*/
var Department = {
	/**机构等级标志 level mark*/
	lm : "00",
	/**机构等级标志长度 level mark length*/
	lml : 2,
	/**
	 * 获取机构等级 
	 * @param {String} jgid 机构编号
	 * @return {int} -1：异常；1：总队；2：支队；3：大队
	 */
	getLevel : function(jgid) {
		var lm = Department.lm;
		var lml = lm.length;
		var l = -1;
		if (jgid) {
			jgid += lm;
			var jl = jgid.length / lml;
			for ( var i = 0; i < jl; i++) {
				if (jgid.substring(i * lml, (i + 1) * lml) == lm) {
					l = i;
					break;
				}
			}
			l = l == 0 ? -1 : l;
		}
		return l;
	},
	/**
	 * 获取父机构，父一级机构
	 * @param {String} jgid 机构编号
	 * @return {String} 父机构 
	 */
	getParent : function(jgid) {
		var parent;
		var lm = Department.lm;
		var lml = lm.length;
		var l = Department.getLevel(jgid);
		if (l > 1) {
			parent = jgid.substring(0, (l - 1) * lml);
			parent += lm;
			parent += jgid.substring(l * lml);
		}
		return parent;
	},
	/**
	 * 获取所有父机构
	 * @param {String} jgid 机构编号
	 * @return {String} 所有父机构 
	 */
	getParents : function(jgid) {
		var parents;
		jgid = Department.getParent(jgid);
		if (jgid) {
			parents = jgid;
			while ((jgid = Department.getParent(jgid))) {
				parents += "," + jgid;
			}
		}
		return parents;
	},
	/**
	 * 筛选自己和子机构，所有子机构
	 * @param {String} jgid 机构编号
	 * @param {String} cname 机构列名
	 * @return {String} 筛选sql语句
	 */
	siftChilds : function(jgid,cname){
		var lm = Department.lm;
		var lml = lm.length;
		var sift;
		cname = cname || "jgid";
		var l = Dept.getLevel(jgid);
		if (l != -1) {
			sift = "substr(" + cname + ",1," + l * lml + ")='"
					+ jgid.substring(0, l * lml) + "'";
		}
		return sift;
	},
	/**
	 * 筛选自己和子机构，子一级机构
	 * @param {String} jgid 机构编号
	 * @param {String} cname 机构列名
	 * @return {String} 筛选sql语句
	 */
	siftChild : function(jgid, cname) {
		var lm = Department.lm;
		var lml = lm.length;
		var sift;
		cname = cname || "jgid";
		var l = Dept.getLevel(jgid);
		if (l != -1) {
			sift = "substr(" + cname + ",1," + l * lml + ")='"
					+ jgid.substring(0, l * lml) + "'";
			sift += " and substr(" + cname + "," + ((l + 1) * lml + 1) + ","
					+ lml + ")='" + lm + "'";
		}
		return sift;
	}
}

Department.lml = Department.lm.length;
window.Dept = Department;


