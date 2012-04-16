/*******************************************
 ** 获得分区的“union”联合 lidq 2010-09-16
 *******************************************/
	BaseUtil = new Object();

	/**
	 * @函数说明：获得分区的“union”联合
	 * @参数：pTableName-查询数据表名；pPrefixName-分区前缀；pStartDateTime-分区起始时间；pEndDateTime-分区终止时间
	 * @返回：
	 * @example "BaseUtil.getInnerTables('T_ITGS_TGSPASSCAR','TPC','2009-05-01','2009-05-21')" 
	 */
	BaseUtil.getInnerTables = function(tableName,pPrefixName,pStartDateTime,pEndDateTime){
	   var pTableName = tableName.toUpperCase();
	   var strPartitionNames = "";
       var strSqlPartitions = "";
	   var starttime = pStartDateTime.split("-");
	   var endtimes = pEndDateTime.split("-");    
	   var intStartYear = parseInt(starttime[0]);
	   var intEndYear = parseInt(endtimes[0]);
	   var intStartMonth = parseInt(starttime[1]);
	   if(starttime[1]=='09'){
	   		intStartMonth=9;
	   }
	   var intEndMonth = parseInt(endtimes[1]);
		if(endtimes[1]=='09'){
	   		intEndMonth=9;
	   }
       if( intStartYear > intEndYear ) return ;
       if( intStartYear == intEndYear && intStartMonth > intEndMonth) return ;
       if( intStartYear < intEndYear ){
       	   var ilength =  intEndMonth;      	           
	       for(var k = 1; k <= ilength; k++){
	            var iMonth= k; 
		        if(strPartitionNames == ""){
	                 if(iMonth<10){
	                  strPartitionNames += ""+pPrefixName + endtimes[0] +"0"+ iMonth+ "";
	                 }else{
	                  strPartitionNames += ""+pPrefixName + endtimes[0] + iMonth+ "";
	                 } 
		        }else{ 	                   
	                 if(iMonth<10){
	                  strPartitionNames += ","+pPrefixName + endtimes[0] +"0"+ iMonth+ "";
	                 }else{
	                  strPartitionNames += ","+pPrefixName + endtimes[0] + iMonth+ "";
	                 }                      	            
		         }
	        } 
        }else{
      	   var ilength =  intEndMonth - intStartMonth+1;           
	       for(var j = 0; j < ilength; j++){
	            var iMonth= intStartMonth+j; 
		        if(strPartitionNames == ""){
	                 if(iMonth<10){
	                  strPartitionNames += ""+pPrefixName + starttime[0] +"0"+ iMonth+ "";
	                 }else{
	                  strPartitionNames += ""+pPrefixName + starttime[0] + iMonth+ "";
	                 } 
		        }else{ 	                   
	                 if(iMonth<10){
	                  strPartitionNames += ","+pPrefixName + starttime[0] +"0"+ iMonth+ "";
	                 }else{
	                  strPartitionNames += ","+pPrefixName + starttime[0] + iMonth+ "";
	                 }                      	            
		         }
	        }       
        }	
       // alert(strPartitionNames);
      if (strPartitionNames == ""&&strPartitionNames =="undefined")
       {
            return pTableName; 
       }else{ 
       
           var strTableNameObj =  strPartitionNames.split(",");
          //没有对应的分区就返回表名
           if (strTableNameObj[0] == null &&strTableNameObj[0] =="undefined" &&strTableNameObj[0] =="") {
           		return pTableName;
           }
          //如果只有一个分区,返回表名加分区名称

          if (strTableNameObj.length == 1){
          		 return pTableName+" PARTITION("+strTableNameObj[0]+")";
           }
           for (var i = 0; i < strTableNameObj.length; i++)
           {
	           	if(strSqlPartitions == ""){
	           		strSqlPartitions = "(select * from "+pTableName+" PARTITION("+strTableNameObj[0]+")";
	           	}else{
	           		strSqlPartitions +=  " UNION select * from "+pTableName+" PARTITION("+strTableNameObj[i]+") ";
	           	}
           }
           strSqlPartitions +=")";
           //如果是多个分区，返回多个分区的UNION语句
           return  strSqlPartitions;
       //}	 
	  }

}