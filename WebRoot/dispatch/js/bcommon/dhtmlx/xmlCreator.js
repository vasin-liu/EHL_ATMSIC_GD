function element(name,attribute,content)
{
	if(attribute)
	{
		if(content)
		{
			var xmlElement='<'+name+' '+attribute+'>'+content+'</'+name+'>';
			return xmlElement;
		}
		else if(!content)
		{
			var xmlElement='<'+name+' '+attribute+'/>';
			return xmlElement;
		}
		
	}
	else if(!attribute)
	{
		if(!content)
		{
			var xmlElement='<'+name+'>'+'</'+name+'>';
			return xmlElement;
		}
		else if(content)
		{
			var xmlElement='<'+name+'>'+content+'</'+name+'>';
			return xmlElement;
		}
	}
	return null;
	
}

function createXMLbody(rowArray,xmlName)
{
	var xmlResult = '<'+xmlName+'>';
	for(var i=0;i<rowArray.length;i++)
	{
		var rowAtt = "id='"+i+"'";
		var rowEle = '<row '+rowAtt+'>';
		xmlResult = xmlResult + rowEle;
		var colArray = rowArray[i];
		for(var j=0;j<colArray.length;j++)
		{
			var colEle = element('col','',colArray[j]);
			xmlResult = xmlResult + colEle;
		}
		xmlResult = xmlResult + '</row>'
	}
	xmlResult = xmlResult + '</'+xmlName+'>';
	return xmlResult;
}

function createLineXMLBody(lineArray,xmlName)
{
	var xmlResult = '<'+xmlName+'>';
	xmlResult += "<row id='0'>";
	for(var i=0;i<lineArray.length;i++)
	{
		var colEle = element('col','',lineArray[i]);
		xmlResult += colEle;
	}
	
	xmlResult = xmlResult + '</row>'+'</'+xmlName+'>';
	return xmlResult;
}


function createFullXMLBody(xmlBody)
{
	var xmlTitle = "<rfXML>";
	var xmlTail = "</rfXML>";
	var fullXMLBody = xmlTitle + xmlBody + xmlTail;
	return fullXMLBody;
}

