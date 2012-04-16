   /**------------------------------------------------------------------------------------------------------------------------
    * Copyright (c) 2009, EHL Technologies, Inc.
    * All right reserved.
	 * desc: 合并单元格
	 * auth:wangxiaoting
	 * date:  2009-05-27 
	 * version: V1.0
    *-------------------------------------------------------------------------------------------------------------------------
    */
 function  uniteTable(thistab,colLength){   
        var   rn=thistab.rows.length;//取得行数   
        var   rowspann=0;   
        for(var j=colLength-1;j>0;j--){//从第0列开始，合并多少列   
            for(var i=rn-1;i>0;i--){//从倒数第一行开始往上检查   
                if((thistab.rows[i].cells[j].innerHTML!="分组名称")&&thistab.rows[i].cells[j].innerText==thistab.rows[i-1].cells[j].innerText&&thistab.rows[i].cells[j].colSpan==thistab.rows[i-1].cells[j].colSpan){//与上面一行比较，如果两行相等就合计当前行的合并行数，并删除当前行。   
                     rowspann+=thistab.rows[i].cells[j].rowSpan;
                     thistab.rows[i].deleteCell(j);   
                 }else{   
                     thistab.rows[i].cells[j].rowSpan+=rowspann;//如果不等就完成当前相同数据的合并。   
                     rowspann=0;   
                 }   
           }   
                //检测无表头的表   
          if(i==0&&rowspann>0){   
                 thistab.rows[i].cells[j].rowSpan+=rowspann;//如果不等就完成当前相同数据的合并。   
                  rowspann=0;   
          }   
    
        }   
    }   
    
	//检查表格是否要合并
	function  checkTable(tb){   
        if(tb.rows.length==0)   
            return   false;   
        if(tb.rows[0].cells.length==0)   
            return   false;   
        for(var i=0;i<tb.rows.length;i++){   
           if(tb.rows[0].cells.length!=tb.rows[i].cells.length)   
           return   false;   
        }   
       return   true;   
   }   