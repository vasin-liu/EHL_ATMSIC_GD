/**
*@说明:字符串去掉前后空格
*/
String.prototype.trim = function(){
	var matches = this.match(/^[ \t\n\r]+/);
	var prefixLength = (matches == null) ? 0:matches[0].length;
	matches = this.match(/[ \t\r\n]+$/);
	var suffixLength =  (matches == null) ? 0:matches[0].length;
	return this.slice( prefixLength,this.length - suffixLength);
}

//通过id 获取listbox的值和文本
function getSelectedItem (itemId)
{
	var ctype = null;
	var obj = document.getElementById(itemId);
	if(obj)
	{
		for(var i=0;i<obj.options.length;i+=1)
		{
			if(obj.options[i].selected)
			{
				ctype = new Object();
				ctype.value = obj.options[i].value;
				ctype.text = obj.options[i].text;
				break;
			}
		}
	}
	return ctype;
}

//通过id和值 ,设置listbox的选中项
function setListItemByValue(itemId,itemValue)
{
	var obj = document.getElementById(itemId);
	if(obj)
	{
		for(var i=0;i<obj.options.length;i+=1)
		{
			if(obj.options[i].value == itemValue)
			{
				obj.options[i].selected = true;
				break;
			}
		}
	}
}


/**通过 id 获取radio 的选择值
*
*/
function getSelectedRadioValue(groupId)
{
   var radioValue = null;
    var group = document.getElementsByName(groupId);
    
    for (var i=0; i<group.length; i+=1)
    {
        if (group[i].checked)
        {
        	radioValue = group[i].value;
            break;
        }
    }
    return radioValue;
}

/**通过 id 设置radio 的选择值
*
*/
function setSelectedRadio(groupId,selectValue)
{
   var radioValue = null;
   var group = document.getElementsByName(groupId);
    
    for (var i=0; i<group.length; i+=1)
    {
        if (group[i].value == selectValue)
        {
        	group[i].checked = true;
            break;
        }
    }
    return radioValue;
}
/**通过 id 设置radio 的disabled属性值
*
*/
function setRadioDisabled(groupId, disabled)
{
	var group = document.getElementsByName(groupId);
    if(group)
    {
	    for (var i=0; i<group.length; i+=1)
	    {
	        group[i].disabled = disabled ;
	    }
    }
}
/**
**将日期对象转化为yyyy-mm-dd
*/
function getDateString(date)
{
	var dateString = "";	
	try
	{							
		dateString += date.getFullYear()+ "-";    					// 获取年份。
		dateString += (date.getMonth() + 1) + "-";            // 获取月份。
	  	dateString += date.getDate() ;                   // 获取日。
    }
    catch(e){}
    return  dateString;             
}
/**
**显示所属辖区选择 
*
*countyElement选择后显示数据的文本元素id
*/
function raiseCountyChoice(countyElement)
{
	var branch_param1 = '01';
	var branch_param1 = '01';
	var branch_param2 = '440000000000';
	var branch_param3 = '广东省交警总队';
	
	DhtmlBranchTree.close();
	var imageBaseSrc = "../../../sm/image";
 	var branchSelcect = new DhtmlBranchTree(branch_param1,branch_param2,branch_param3,countyElement);
 	branchSelcect.setImgRelativePath("../../../sm/image");
	branchSelcect.createHtml();
	return branchSelcect;
}
