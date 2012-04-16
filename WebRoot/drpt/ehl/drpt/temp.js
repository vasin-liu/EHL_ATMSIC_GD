var  osx=document.getElementById("sx");                                      
var div = document.getElementById("m_pub_wzs_progress_x");                   
function  m_pub_wzs_progress_show(pTotalCount,pCurrCount)                    
{                                                                            
	osx.innerText = pCurrCount;                                                
	var m = Math.floor(pCurrCount / pTotalCount * 30);                         
	div.style.display = "";                                                    
	var tr = div.firstChild.rows[0];                                           
	for(var i=0;i<tr.cells.length;i++)                                         
	{                                                                          
		var td=tr.cells[i];                                                      
		if(i<m)                                                                  
		 td.bgColor="#000088";                                                   
		else                                                                     
		 td.bgColor="#CCCCCC";                                                   
	}                                                                          
}                                                                            
function m_pub_wzs_progress_hide()                                           
{                                                                            
    var div = document.getElementById("m_pub_wzs_progress_x");               
    div.style.display = "none";                                              
}                                                                            