window.onload=function ()
{
	var oTable=document.getElementById('bk_inf_table');
	var aRows=oTable.tBodies[0].rows;
	var aTbody=oTable.getElementsByTagName('tbody')[0];
	var oDelete=aTbody.getElementsByClassName('delete');
	var oReback=aTbody.getElementsByClassName('change');
	var aSpan=aTbody.getElementsByTagName('span');
	var i=0;
	
	//隔行变色
		interlaceColor();
		
		for(i=0;i<oDelete.length;i++)
		{
			
			oDelete[i].index=i;
			oDelete[i].onclick=function(){/*删除子节点tr*/
				var j=this.index;
				var id=aSpan[j].innerHTML;
				var pd=confirm("是否删除该图书？");	
				if(pd){
					$.ajax({
						type: "post",
						dataType: 'text',
						url: "/deleteBook",
						data: "id="+id,
						success: function (result) {
                            aTbody.removeChild(aRows[j]);
                            for(i=0;i<oDelete.length;i++){
                                oDelete[i].index=i;   //删除子节点后，必须重新匹配索引值
                            }
                            interlaceColor();//隔行换色
							alert(result);
                        }
					})

				}
			}
		}
	

};

//---------------------------------------------------------------------------下面是函数
	
	

function interlaceColor()  /*  隔行换色*/
{
	var oTable=document.getElementById('bk_inf_table');
	var aRows=oTable.tBodies[0].rows;
	var i=0;
	
	for(i=0;i<aRows.length;i++)
	{
		if(i%2)
		{
			aRows[i].style.background='#CFF';
		}
		else
		{
			aRows[i].style.background='';
		}
	}
} 