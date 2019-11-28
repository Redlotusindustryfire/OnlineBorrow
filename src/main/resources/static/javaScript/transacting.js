window.onload=function(){
	var aTable=document.getElementById('delivery_table');
	var aTbody=aTable.tBodies[0];
	var oBtn1=aTbody.getElementsByClassName('btn1');
	var aTr=aTbody.rows;
		interlaceColor();//隔行换
		for(i=0;i<oBtn1.length;i++)
		{
			
			oBtn1[i].index=i;
			oBtn1[i].onclick=function(){
				var pd=confirm('图书是否已归还');	
					if(pd){	/*删除子节点tr*/
						aTbody.removeChild(aTr[this.index]);
						for(i=0;i<oBtn1.length;i++){
						oBtn1[i].index=i;   //删除子节点后，必须重新匹配索引值
						}
					interlaceColor();//隔行换色
					}
			}
		}
}

function interlaceColor()  /*  隔行换色*/
{
	var aTable=document.getElementById('delivery_table');
	var aRows=aTable.tBodies[0].rows;
	var i=0;
	
	for(i=0;i<aRows.length;i++)
	{
		if(i%2)
		{
			aRows[i].style.background='#FFC';
		}
		else
		{
			aRows[i].style.background='';
		}
	}
}